

# ⚙️ Adaptive Sorted Round Robin (ASRR) — Operating Systems Project

> **Sir Syed University of Engineering & Technology (SSUET)**
> Department of Software Engineering
> **Course:** SE-204L – Operating Systems (Lab)
> **Semester:** 4th
> **Batch:** 2023F
> **Section:** “F”

---

## 🧠 Project Title

### **Adaptive Sorted Round Robin (ASRR) CPU Scheduling Algorithm**

An enhanced CPU scheduling algorithm designed to **improve average waiting time (WT)** and **turnaround time (TAT)** over the standard **Round Robin (RR)** algorithm by making the **Time Quantum (TQ) adaptive and dynamic** using **median-based sorting**.
<img width="450" height="335" alt="image" src="https://github.com/user-attachments/assets/9183dd35-9fcb-4569-9c43-4233073d1f06" />


---

## 👨‍💻 Team Members

| Name                      | Roll No.      | Responsibilities                                           |
| ------------------------- | ------------- | ---------------------------------------------------------- |
| **Muhammad Tariq Sohail** | 2023F-BSE-343 | Suggested the core idea of sorting & median-based TQ       |
| **Muhammad Safwan**       | 2023F-BSE-282 | Designed algorithm, tested it & wrote project report       |
| **Syed Azan Amir**        | 2023F-BSE-272 | Data analysis, Gantt chart design & performance comparison |
| **Shaheer Abbasi**        | 2023F-BSE-289 | Developed simulation code & implemented visualization      |

---

## 📘 Abstract

The **Adaptive Sorted Round Robin (ASRR)** algorithm enhances traditional Round Robin scheduling by dynamically adjusting the **Time Quantum (TQ)** in every cycle based on the **median of remaining burst times**.

By sorting processes before each round and using the formula:

> **TQ = max(median(RBT), 2)**

the algorithm achieves:

* Reduced **context switching**
* Improved **CPU utilization**
* Lower **average waiting and turnaround times**
* Balanced scheduling between short and long tasks

ASRR outperforms traditional **RR** and **OMDRR (Optimum Multilevel Dynamic RR)** in both fairness and efficiency.

---

## 🧩 Algorithm Workflow

### 🔸 Steps

1. Insert all processes into the ready queue.
2. Sort processes by their **Remaining Burst Time (RBT)**.
3. Calculate **Time Quantum (TQ)** using:

   ```text
   TQ = max(median(RBT), 2)
   ```
4. For each process:

   * If RBT ≤ TQ → run to completion
   * Else → allocate CPU for TQ, reduce RBT, and requeue
5. Repeat steps 2–4 until all processes are finished.

---

## ⚙️ Software & Hardware Requirements

### 🖥️ Software

* **Operating System:** Windows 10
* **Development Tool:** Visual Studio (C++ / C# simulation)
* **Diagram Tool:** draw.io / diagrams.net
* **Project Management:** Microsoft Project (Gantt Chart)

### 💾 Hardware

* **Processor:** ≥ 1 GHz (Recommended: 2 GHz+)
* **RAM:** ≥ 4 GB
* **Storage:** ≥ 32 GB (Recommended: 64 GB+)
* **Network:** Ethernet/Wi-Fi for collaboration

---

## 🧮 Performance Results

### **Experiment 1**

| Process | Burst Time |
| ------- | ---------- |
| P1      | 25         |
| P2      | 15         |
| P3      | 7          |
| P4      | 10         |
| P5      | 5          |

| Algorithm              | Avg. Waiting Time | Avg. Turnaround Time |
| ---------------------- | ----------------- | -------------------- |
| **Round Robin (TQ=5)** | 27.6 ms           | 40 ms                |
| **ASRR (TQ=10)**       | **16.2 ms**       | **28.6 ms**          |

---

### **Experiment 2**

| Process | Burst Time |
| ------- | ---------- |
| P1      | 30         |
| P2      | 18         |
| P3      | 8          |
| P4      | 12         |
| P5      | 6          |
| P6      | 15         |

| Algorithm              | Avg. Waiting Time | Avg. Turnaround Time |
| ---------------------- | ----------------- | -------------------- |
| **Round Robin (TQ=5)** | 40.17 ms          | 55 ms                |
| **ASRR (TQ≈13)**       | **30.83 ms**      | **45.67 ms**         |

📊 **Improvement:**
ASRR shows an average **20–25% reduction** in both waiting and turnaround times compared to RR.

---

## 🧰 Implementation Details

* Developed simulation code in **Visual Studio**
* GUI allows user to:

  * Input process count, burst time, and optional arrival time
  * Run scheduler
  * View results (Gantt chart + stats)
* Automatically computes and displays:

  * Average Waiting Time
  * Average Turnaround Time
  * Graphical Comparisons (RR vs OMDRR vs ASRR)

---

## 📊 Visualization

The program displays graphical charts showing comparative results:

| Metric                      | Chart Description                              |
| --------------------------- | ---------------------------------------------- |
| **Average Waiting Time**    | Shows improved waiting times across test cases |
| **Average Turnaround Time** | Demonstrates efficiency in process completion  |
| **Gantt Chart Simulation**  | Displays execution sequence of processes       |

*(See figures 8.7–8.10 in project report for visual outputs.)*

---

## 🧠 Advantages of ASRR

✅ Dynamic Time Quantum based on workload
✅ Reduces CPU idle time
✅ Prevents starvation
✅ Balanced scheduling for short & long processes
✅ Less context switching

---

## 🔬 Future Work

* Extend ASRR for **multi-core processors**
* Apply in **real-time operating systems**
* Integrate with **I/O-bound process handling**
* Implement **thread-level scheduling simulation**

---

## 🏁 Conclusion

ASRR efficiently addresses the weaknesses of the Round Robin algorithm by adapting the **time quantum dynamically** using a **median-based approach**.
It significantly **reduces waiting and turnaround times** while maintaining fairness and stability in CPU scheduling.

---

## 📜 License

This project was developed as part of **Operating Systems (SE-204L)** coursework at
**Sir Syed University of Engineering & Technology (SSUET)**.

© 2025 – Team 2023F-BSE-F. All rights reserved.

---

## 👨‍🏫 Supervisors

**Dr. Farheen Qazi**
**Engr. Samrah Shafiq**

---

## 👥 Contributors

**Muhammad Tariq Sohail** – Core Algorithm & Concept
**Muhammad Safwan** – Report & Implementation
**Syed Azan Amir** – Analysis, Gantt Chart, Performance Metrics
**Shaheer Abbasi** – Simulation & Visualization

]
