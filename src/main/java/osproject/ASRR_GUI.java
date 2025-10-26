package osproject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

class ProcessGUI {
    String pid;
    double arrivalTime;
    double burstTime;
    double remainingTime;
    double completionTime;
    double waitingTime;
    double turnaroundTime;
    boolean isCompleted = false;

    ProcessGUI(String pid, double arrivalTime, double burstTime) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }
}

public class ASRR_GUI extends JFrame {

    private DefaultTableModel tableModel;
    private JTextArea outputArea;
    private boolean hasArrivalTime = false;

    public ASRR_GUI() {
        setTitle("ASRR Scheduler GUI");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Font poppinsFont = new Font("Poppins", Font.PLAIN, 12);
        Font monospaceFont = new Font("Monospaced", Font.PLAIN, 12);

        // Table
        tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);
        JScrollPane scrollTable = new JScrollPane(table);

        // Buttons
        JButton configButton = new JButton("Configure Input");
        configButton.setFont(poppinsFont);

        JButton runButton = new JButton("Run Scheduler");
        runButton.setFont(poppinsFont);

        configButton.addActionListener(e -> {
            configureInput();
        });

        runButton.addActionListener(e -> {
            if (tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Please configure and enter processes first.");
                return;
            }
            runScheduler();
        });

        JPanel btnPanel = new JPanel();
        btnPanel.add(configButton);
        btnPanel.add(runButton);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(monospaceFont);
        JScrollPane scrollOutput = new JScrollPane(outputArea);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollTable, scrollOutput);
        splitPane.setDividerLocation(250);

        add(splitPane, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private void configureInput() {
        int choice = JOptionPane.showConfirmDialog(this, "Do you want to provide arrival times?", "Arrival Time", JOptionPane.YES_NO_OPTION);
        hasArrivalTime = (choice == JOptionPane.YES_OPTION);

        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);

        if (hasArrivalTime) {
            tableModel.setColumnIdentifiers(new String[]{"PID", "Arrival Time", "Burst Time"});
        } else {
            tableModel.setColumnIdentifiers(new String[]{"PID", "Burst Time"});
        }

        String input = JOptionPane.showInputDialog(this, "Enter number of processes:");
        if (input == null) return;

        try {
            int n = Integer.parseInt(input.trim());
            for (int i = 1; i <= n; i++) {
                String pid = "P" + i;
                double at = 0;
                if (hasArrivalTime) {
                    String atStr = JOptionPane.showInputDialog(this, "Enter arrival time for " + pid + ":");
                    if (atStr == null) return;
                    at = Double.parseDouble(atStr.trim());
                    if (at < 0) throw new NumberFormatException();
                }

                String btStr = JOptionPane.showInputDialog(this, "Enter burst time for " + pid + ":");
                if (btStr == null) return;
                double bt = Double.parseDouble(btStr.trim());
                if (bt <= 0) throw new NumberFormatException();

                if (hasArrivalTime) {
                    tableModel.addRow(new Object[]{pid, at, bt});
                } else {
                    tableModel.addRow(new Object[]{pid, bt});
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input! Use positive numbers.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void runScheduler() {
        List<ProcessGUI> processes = new ArrayList<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String pid = tableModel.getValueAt(i, 0).toString();
            double at = 0;
            double bt;
            if (hasArrivalTime) {
                at = Double.parseDouble(tableModel.getValueAt(i, 1).toString());
                bt = Double.parseDouble(tableModel.getValueAt(i, 2).toString());
            } else {
                bt = Double.parseDouble(tableModel.getValueAt(i, 1).toString());
            }
            processes.add(new ProcessGUI(pid, at, bt));
        }

        List<String> gantt = new ArrayList<>();
        double currentTime = 0;

        while (true) {
            List<ProcessGUI> ready = new ArrayList<>();
            for (ProcessGUI p : processes) {
                if (!p.isCompleted && (!hasArrivalTime || p.arrivalTime <= currentTime)) {
                    ready.add(p);
                }
            }

            if (ready.isEmpty()) {
                if (hasArrivalTime) {
                    OptionalDouble nextArrival = processes.stream()
                            .filter(p -> !p.isCompleted)
                            .mapToDouble(p -> p.arrivalTime)
                            .min();
                    if (nextArrival.isPresent()) {
                        currentTime = Math.max(currentTime + 0.01, nextArrival.getAsDouble());
                    } else {
                        break;
                    }
                } else {
                    break;
                }
                continue;
            }

            ready.sort(Comparator.comparingDouble(p -> p.remainingTime));

            double tq = Math.max(computeMedian(ready), 2.0);

            for (ProcessGUI p : ready) {
                gantt.add(p.pid);
                if (p.remainingTime <= tq) {
                    currentTime += p.remainingTime;
                    p.remainingTime = 0;
                    p.completionTime = currentTime;
                    p.isCompleted = true;
                } else {
                    currentTime += tq;
                    p.remainingTime -= tq;
                }
            }
        }

        double totalWT = 0, totalTAT = 0;
        StringBuilder result = new StringBuilder();
        result.append("Gantt Chart:\n");
        result.append(String.join(" | ", gantt)).append("\n\n");

        result.append(String.format("%-6s %-12s %-10s %-12s %-10s %-10s\n", "PID", "Arrival", "Burst", "Completion", "TAT", "WT"));
        for (ProcessGUI p : processes) {
            p.turnaroundTime = p.completionTime - p.arrivalTime;
            p.waitingTime = p.turnaroundTime - p.burstTime;
            totalWT += p.waitingTime;
            totalTAT += p.turnaroundTime;
            result.append(String.format("%-6s %-12.2f %-10.2f %-12.2f %-10.2f %-10.2f\n",
                    p.pid, p.arrivalTime, p.burstTime, p.completionTime, p.turnaroundTime, p.waitingTime));
        }

        result.append(String.format("\nAverage WT: %.2f ms\n", totalWT / processes.size()));
        result.append(String.format("Average TAT: %.2f ms\n", totalTAT / processes.size()));

        outputArea.setText(result.toString());
    }

    private double computeMedian(List<ProcessGUI> ready) {
        List<Double> rbt = new ArrayList<>();
        for (ProcessGUI p : ready) rbt.add(p.remainingTime);
        Collections.sort(rbt);
        int sz = rbt.size();
        if (sz % 2 == 1) {
            return rbt.get(sz / 2);
        } else {
            return (rbt.get(sz / 2 - 1) + rbt.get(sz / 2)) / 2.0;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ASRR_GUI().setVisible(true));
    }
}
