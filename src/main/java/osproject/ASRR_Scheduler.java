/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package osproject;

import java.util.*;

class ProcessA {
    String pid;
    int burstTime;
    int remainingTime;
    int completionTime;
    int waitingTime;
    int turnaroundTime;
    boolean isCompleted = false;

    ProcessA(String pid, int burstTime) {
        this.pid = pid;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }
}

public class ASRR_Scheduler {

    public static void main(String[] args) {
        List<ProcessA> processes = Arrays.asList(
                new ProcessA("P1", 22),
                new ProcessA("P2", 18),
                new ProcessA("P3", 9),
                new ProcessA("P4", 10),
                new ProcessA("P5", 5)
        );

        runASRR(processes);
    }

    static void runASRR(List<ProcessA> processes) {
        int currentTime = 0;
        List<String> gantt = new ArrayList<>();

        while (true) {
            List<ProcessA> ready = new ArrayList<>();
            for (ProcessA p : processes) {
                if (!p.isCompleted) {
                    ready.add(p);
                }
            }

            if (ready.isEmpty()) break;

            ready.sort(Comparator.comparingInt(p -> p.remainingTime));

            int medianTQ = computeMedian(ready);
            int TQ = Math.max(medianTQ, 2);

            for (ProcessA p : ready) {
                gantt.add(p.pid);
                if (p.remainingTime <= TQ) {
                    currentTime += p.remainingTime;
                    p.remainingTime = 0;
                    p.completionTime = currentTime;
                    p.isCompleted = true;
                } else {
                    currentTime += TQ;
                    p.remainingTime -= TQ;
                }
            }
        }

        double totalWT = 0, totalTAT = 0;
        for (ProcessA p : processes) {
            p.turnaroundTime = p.completionTime;
            p.waitingTime = p.turnaroundTime - p.burstTime;
            totalWT += p.waitingTime;
            totalTAT += p.turnaroundTime;
        }

        System.out.println("Gantt chart: " + String.join(" | ", gantt));
        System.out.printf("Average WT: %.2f ms\n", totalWT / processes.size());
        System.out.printf("Average TAT: %.2f ms\n", totalTAT / 
                processes.size());
    }

    static int computeMedian(List<ProcessA> ready) {
        List<Integer> rbt = new ArrayList<>();
        for (ProcessA p : ready) rbt.add(p.remainingTime);
        Collections.sort(rbt);
        int size = rbt.size();
        if (size % 2 == 1) {
            return rbt.get(size / 2);
        } else {
            return (rbt.get(size / 2 - 1) + rbt.get(size / 2)) / 2;
        }
    }
}

