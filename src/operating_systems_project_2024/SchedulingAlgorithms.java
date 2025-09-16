
package Operating_Systems_Project_2024;


import java.util.*;

enum ProcessState {
    READY, RUNNING, COMPLETED
}

class Process {
    int processNumber;
    int arrivalTime;
    int burstTime;
    int priority;
    int remainingBurstTime;
    int waitingTime;
    int turnaroundTime;
    ProcessState state;

    public Process(int processNumber, int arrivalTime, int burstTime, int priority) {
        this.processNumber = processNumber;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.remainingBurstTime = burstTime;
        this.waitingTime = 0;
        this.turnaroundTime = 0;
        this.state = ProcessState.READY;
    }
}

public class SchedulingAlgorithms {
    
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int numProcesses = scanner.nextInt();

        List<Process> processesFCFS = new ArrayList<>();
        List<Process> processesSJF = new ArrayList<>();
        List<Process> processesRR = new ArrayList<>();
        List<Process> processesPriorityNonPreemptive = new ArrayList<>();
        List<Process> processesPriorityPreemptive = new ArrayList<>();

        for (int i = 1; i <= numProcesses; i++) {
            System.out.println("Enter details for Process " + i + ":");
            System.out.print("Arrival Time: ");
            int arrivalTime = scanner.nextInt();
            System.out.print("Burst Time: ");
            int burstTime = scanner.nextInt();
            System.out.print("Priority: ");
            int priority = scanner.nextInt();

            Process processFCFS = new Process(i, arrivalTime, burstTime, priority);
            Process processSJF = new Process(i, arrivalTime, burstTime, priority);
            Process processRR = new Process(i, arrivalTime, burstTime, priority);
            Process processPriorityNonPreemptive = new Process(i, arrivalTime, burstTime, priority);
            Process processPriorityPreemptive = new Process(i, arrivalTime, burstTime, priority);

            processesFCFS.add(processFCFS);
            processesSJF.add(processSJF);
            processesRR.add(processRR);
            processesPriorityNonPreemptive.add(processPriorityNonPreemptive);
            processesPriorityPreemptive.add(processPriorityPreemptive);
        }

        System.out.print("Enter time quantum for Round Robin: ");
        int timeQuantum = scanner.nextInt();

        System.out.println("\nExecuting FCFS Scheduling:");
        executeFCFS(processesFCFS);
        printAverageTimes(processesFCFS);

        System.out.println("\nExecuting SJF Scheduling:");
        executeSJF(processesSJF);
        printAverageTimes(processesSJF);

        System.out.println("\nExecuting Round Robin Scheduling:");
        executeRoundRobin(processesRR, timeQuantum);
        printAverageTimes(processesRR);

        System.out.println("\nExecuting Priority Non-Preemptive Scheduling:");
        executePriorityNonPreemptive(processesPriorityNonPreemptive);
        printAverageTimes(processesPriorityNonPreemptive);

        System.out.println("\nExecuting Priority Preemptive Scheduling:");
        executePriorityPreemptive(processesPriorityPreemptive);
        printAverageTimes(processesPriorityPreemptive);

        scanner.close();
    }

    public static void executeFCFS(List<Process> processes) {
        int currentTime = 0;

        for (Process process : processes) {
            process.state = ProcessState.RUNNING;

            // Wait until the process arrives
            if (currentTime < process.arrivalTime) {
                currentTime = process.arrivalTime;
            }

            // Execute the process
            process.waitingTime = currentTime - process.arrivalTime;
            int completionTime = currentTime + process.burstTime;
            process.turnaroundTime = completionTime - process.arrivalTime;

            System.out.println("Executing Process " + process.processNumber +
                    " (Burst Time: " + process.burstTime +
                    ", Waiting Time: " + process.waitingTime +
                    ", Turnaround Time: " + process.turnaroundTime +
                    ", Completion Time: " + completionTime);

            currentTime = completionTime;
            process.state = ProcessState.COMPLETED;
        }
    }

    public static void executeSJF(List<Process> processes) {
        // Sort processes by burst time and arrival time
        processes.sort(Comparator.comparingInt(p -> p.burstTime));

        int currentTime = 0;

        for (Process process : processes) {
            process.state = ProcessState.RUNNING;

            // Wait until the process arrives
            if (currentTime < process.arrivalTime) {
                currentTime = process.arrivalTime;
            }

            // Execute the process
            process.waitingTime = currentTime - process.arrivalTime;
            int completionTime = currentTime + process.burstTime;
            process.turnaroundTime = completionTime - process.arrivalTime;

            System.out.println("Executing Process " + process.processNumber +
                    " (Burst Time: " + process.burstTime +
                    ", Waiting Time: " + process.waitingTime +
                    ", Turnaround Time: " + process.turnaroundTime +
                    ", Completion Time: " + completionTime);

            currentTime = completionTime;
            process.state = ProcessState.COMPLETED;
        }
    }

    public static void executeRoundRobin(List<Process> processes, int timeQuantum) {
        Queue<Process> readyQueue = new LinkedList<>();
        int currentTime = 0;

        while (!processes.isEmpty() || !readyQueue.isEmpty()) {
            // Move arriving processes to the ready queue
            while (!processes.isEmpty() && processes.get(0).arrivalTime <= currentTime) {
                readyQueue.add(processes.remove(0));
            }

            if (!readyQueue.isEmpty()) {
                Process currentProcess = readyQueue.poll();
                currentProcess.state = ProcessState.RUNNING;

                int executionTime = Math.min(timeQuantum, currentProcess.remainingBurstTime);
                currentTime += executionTime;
                currentProcess.remainingBurstTime -= executionTime;
                
               
                System.out.println("Executing Process " + currentProcess.processNumber +
                        " (Burst Time: " + executionTime +
                        ", Remaining Burst Time: " + currentProcess.remainingBurstTime +
                        ", Current Time: " + currentTime);

                if (currentProcess.remainingBurstTime > 0) {
                    readyQueue.add(currentProcess);
                } else {
                    currentProcess.turnaroundTime = currentTime - currentProcess.arrivalTime;
                    currentProcess.state = ProcessState.COMPLETED;
                }
            } else {
                currentTime++;
            }
        }
    }

    public static void executePriorityNonPreemptive(List<Process> processes) {
        // Sort processes by priority and arrival time
        processes.sort(Comparator.comparingInt(p -> p.priority));

        int currentTime = 0;

        for (Process process : processes) {
            process.state = ProcessState.RUNNING;

            // Wait until the process arrives
            if (currentTime < process.arrivalTime) {
                currentTime = process.arrivalTime;
            }

            // Execute the process
            process.waitingTime = currentTime - process.arrivalTime;
            int completionTime = currentTime + process.burstTime;
            process.turnaroundTime = completionTime - process.arrivalTime;

            System.out.println("Executing Process " + process.processNumber +
                    " (Burst Time: " + process.burstTime +
                    ", Waiting Time: " + process.waitingTime +
                    ", Turnaround Time: " + process.turnaroundTime +
                    ", Completion Time: " + completionTime);

            currentTime = completionTime;
            process.state = ProcessState.COMPLETED;
        }
    }

    public static void executePriorityPreemptive(List<Process> processes) {
        int currentTime = 0;

        while (!processes.isEmpty()) {
            // Find the process with the highest priority
            Process currentProcess = processes.get(0);

            for (Process process : processes) {
                if (process.arrivalTime <= currentTime && process.priority < currentProcess.priority) {
                    currentProcess = process;
                }
            }

            // Wait until the process arrives
            if (currentTime < currentProcess.arrivalTime) {
                currentTime = currentProcess.arrivalTime;
            }

            // Execute the process
            currentProcess.state = ProcessState.RUNNING;
            int executionTime = currentProcess.remainingBurstTime;
            currentTime += executionTime;
            currentProcess.remainingBurstTime = 0;
            currentProcess.turnaroundTime = currentTime - currentProcess.arrivalTime;

            System.out.println("Executing Process " + currentProcess.processNumber +
                    " (Burst Time: " + executionTime +
                    ", Turnaround Time: " + currentProcess.turnaroundTime +
                    ", Completion Time: " + currentTime);

            processes.remove(currentProcess);
            currentProcess.state = ProcessState.COMPLETED;
        }
    }

    public static void printAverageTimes(List<Process> processes) {
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;

        for (Process process : processes) {
            totalWaitingTime += process.waitingTime;
            totalTurnaroundTime += process.turnaroundTime;

            System.out.println("Process " + process.processNumber +
                    " (Waiting Time: " + process.waitingTime +
                    ", Turnaround Time: " + process.turnaroundTime +
                    ", State: " + process.state + ")");
        }

        double averageWaitingTime = (double) totalWaitingTime / processes.size();
        double averageTurnaroundTime = (double) totalTurnaroundTime / processes.size();

        System.out.println("Average Waiting Time: " + averageWaitingTime);
        System.out.println("Average Turnaround Time: " + averageTurnaroundTime);
    }
}
