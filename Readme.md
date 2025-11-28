# Operating Systems Project - CPU Scheduling Algorithms

This project implements several CPU scheduling algorithms in Java, including:
- First-Come, First-Served (FCFS)
- Shortest Job First (SJF)
- Round Robin (RR)
- Priority Scheduling (Preemptive and Non-Preemptive)

## Project Structure

- `src/operating_systems_project_2024/SchedulingAlgorithms.java`: Main source code implementing the scheduling algorithms.
- `build.xml`: Ant build script for compiling and running the project.
- `manifest.mf`: Manifest file for JAR packaging.
- `build/`: Directory for compiled classes (generated after build).
- `nbproject/`: NetBeans project configuration files.

## Requirements

- Java Development Kit (JDK) 8 or higher
- Apache Ant (for building via `build.xml`)

## How to Build

1. Open a terminal in the project root directory.
2. Run the following command to compile the project using Ant:

   ```sh
   ant clean
   ant build
   ```

   The compiled classes will be placed in the `build/classes` directory.

## How to Run

You can run the project using Ant:

```sh
ant run
```

Or, run the compiled class directly using the Java command:

```sh
java -cp build/classes Operating_Systems_Project_2024.SchedulingAlgorithms
```

## Usage

1. When prompted, enter the number of processes.
2. For each process, enter:
   - Arrival Time
   - Burst Time
   - Priority
3. Enter the time quantum for Round Robin scheduling.
4. The program will execute each scheduling algorithm and display the results, including waiting times and turnaround times.

## Notes

- Make sure your terminal/command prompt is in the project root directory before running the above commands.
- This project is designed for educational purposes and demonstrates basic CPU scheduling concepts.

---

For any issues, please contact the project maintainer.
