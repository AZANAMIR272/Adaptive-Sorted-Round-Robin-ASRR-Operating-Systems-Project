/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package osproject;

import java.util.*;

class ProcessB {
    String pid;
    int arrivalTime;
    int burstTime;
    int remainingTime;
    int completionTime;
    int waitingTime;
    int turnaroundTime;
    boolean isCompleted = false;

    ProcessB(String pid, int arrivalTime, int burstTime) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }
}

public class Assr_Scheduler2 {

    public static void main(String[] args) {
        List<ProcessB> processes = Arrays.asList(
                new ProcessB("P1", 0, 4),
                new ProcessB("P2", 2, 7),
                new ProcessB("P3", 5, 5),
                new ProcessB("P4", 6, 8),
                new ProcessB("P5", 8, 9)
        );

        runASRR(processes);
    }

    static void runASRR(List<ProcessB> processes) {
        int currentTime = 0;
        List<String> gantt = new ArrayList<>();

        while (true) {
            List<ProcessB> ready = new ArrayList<>();
            for (ProcessB p : processes) {
                if (!p.isCompleted && p.arrivalTime <= currentTime) {
                    ready.add(p);
                }
            }

            if (ready.isEmpty()) {
                OptionalInt nextArrival = processes.stream()
                        .filter(p -> !p.isCompleted)
                        .mapToInt(p -> p.arrivalTime)
                        .min();
                if (nextArrival.isPresent()) {
                    currentTime = Math.max(currentTime + 1,
                            nextArrival.getAsInt());
                } else {
                    break;
                }
                continue;
            }

            ready.sort(Comparator.comparingInt(p -> p.remainingTime));

            int medianTQ = computeMedian(ready);
            int TQ = Math.max(medianTQ, 2);

            for (ProcessB p : ready) {
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
        for (ProcessB p : processes) {
            p.turnaroundTime = p.completionTime - p.arrivalTime;
            p.waitingTime = p.turnaroundTime - p.burstTime;
            totalWT += p.waitingTime;
            totalTAT += p.turnaroundTime;
        }

        System.out.println("Gantt chart: " + String.join(" | ", gantt));
        System.out.printf("Average WT: %.2f ms\n", totalWT / processes.size());
        System.out.printf("Average TAT: %.2f ms\n", totalTAT /
                processes.size());
    }

    static int computeMedian(List<ProcessB> ready) {
        List<Integer> rbt = new ArrayList<>();
        for (ProcessB p : ready) rbt.add(p.remainingTime);
        Collections.sort(rbt);
        int size = rbt.size();
        if (size % 2 == 1) {
            return rbt.get(size / 2);
        } else {
            return (rbt.get(size / 2 - 1) + rbt.get(size / 2)) / 2;
        }
    }
}

