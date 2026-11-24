import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class JobSequencing {

    static class Job {
        String id;
        int deadline;
        int profit;

        public Job(String id, int deadline, int profit) {
            this.id = id;
            this.deadline = deadline;
            this.profit = profit;
        }
    }

    public static void scheduleJobs(Job[] jobs) {
        Arrays.sort(jobs, new Comparator<Job>() {
            @Override
            public int compare(Job a, Job b) {
                return Integer.compare(b.profit, a.profit);
            }
        });

        int maxDeadline = 0;
        for (Job j : jobs) {
            if (j.deadline > maxDeadline) {
                maxDeadline = j.deadline;
            }
        }

        String[] slot = new String[maxDeadline + 1];
        int totalProfit = 0;

        for (Job job : jobs) {
            for (int t = Math.min(maxDeadline, job.deadline); t >= 1; t--) {
                if (slot[t] == null) {
                    slot[t] = job.id;
                    totalProfit += job.profit;
                    break;
                }
            }
        }

        System.out.print("Scheduled Jobs: ");
        for (int t = 1; t <= maxDeadline; t++) {
            if (slot[t] != null) {
                System.out.print(slot[t] + " ");
            }
        }
        System.out.println();
        System.out.println("Maximum Profit = " + totalProfit);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of jobs: ");
        if (sc.hasNextInt()) {
            int n = sc.nextInt();
            Job[] jobs = new Job[n];
            for (int i = 0; i < n; i++) {
                System.out.print("Enter ID, deadline, profit for job " + (i + 1) + ": ");
                String id = sc.next();
                int d = sc.nextInt();
                int p = sc.nextInt();
                jobs[i] = new Job(id, d, p);
            }
            scheduleJobs(jobs);
        } else {
            Job[] jobs = {
                new Job("J1", 2, 100),
                new Job("J2", 1, 19),
                new Job("J3", 2, 27),
                new Job("J4", 1, 25),
                new Job("J5", 3, 15)
            };
            scheduleJobs(jobs);
        }
        sc.close();
    }
}
