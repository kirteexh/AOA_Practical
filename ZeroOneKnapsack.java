import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ZeroOneKnapsack {

    public static void knapsack(int[] wt, int[] val, int W) {
        int n = wt.length;
        int[][] dp = new int[n + 1][W + 1];

        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= W; w++) {
                if (wt[i - 1] <= w) {
                    dp[i][w] = Math.max(dp[i - 1][w], val[i - 1] + dp[i - 1][w - wt[i - 1]]);
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        System.out.println("DP Table:");
        for (int i = 0; i <= n; i++) {
            for (int w = 0; w <= W; w++) {
                System.out.printf("%4d", dp[i][w]);
            }
            System.out.println();
        }

        System.out.println("Maximum Profit = " + dp[n][W]);

        int w = W;
        List<Integer> items = new ArrayList<>();
        for (int i = n; i > 0; i--) {
            if (dp[i][w] != dp[i - 1][w]) {
                items.add(i);
                w -= wt[i - 1];
            }
        }

        System.out.print("Selected Items: ");
        for (int i = items.size() - 1; i >= 0; i--) {
            System.out.print(items.get(i) + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of items: ");
        if (sc.hasNextInt()) {
            int n = sc.nextInt();
            int[] wt = new int[n];
            int[] val = new int[n];
            for (int i = 0; i < n; i++) {
                System.out.print("Enter weight and profit for item " + (i + 1) + ": ");
                wt[i] = sc.nextInt();
                val[i] = sc.nextInt();
            }
            System.out.print("Enter capacity: ");
            int W = sc.nextInt();
            knapsack(wt, val, W);
        } else {
            int[] wt = {2, 3, 4, 5};
            int[] val = {3, 4, 5, 6};
            int W = 5;
            knapsack(wt, val, W);
        }
        sc.close();
    }
}
