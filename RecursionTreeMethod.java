import java.util.Scanner;

public class RecursionTreeMethod {

    public static void analyzeRecursionTree(double a, double b, double c, double k, double n) {
        System.out.println("T(n) = " + (int)a + "T(n/" + (int)b + ") + " + (c == 1.0 ? "" : c) + "n^" + (int)k);
        System.out.println("n = " + (int)n);

        double logb_a = Math.log(a) / Math.log(b);
        int height = (int) Math.round(Math.log(n) / Math.log(b));

        System.out.printf("%-8s %-12s %-14s %-14s\n", "Level", "Nodes", "Size", "Cost");

        double totalCost = 0;
        double ratio = a / Math.pow(b, k);

        for (int i = 0; i <= height; i++) {
            double nodes = Math.pow(a, i);
            double size = n / Math.pow(b, i);
            double cost = nodes * (c * Math.pow(size, k));
            totalCost += cost;

            System.out.printf("%-8d %-12.0f %-14.2f %-14.2f\n", i, nodes, size, cost);
        }

        System.out.printf("Total Cost = %.2f\n", totalCost);

        if (Math.abs(ratio - 1.0) < 1e-6) {
            System.out.println("Complexity = O(n^" + (int)k + " log n)");
        } else if (ratio < 1.0) {
            System.out.println("Complexity = O(n^" + (int)k + ")");
        } else {
            System.out.printf("Complexity = O(n^%.2f)\n", logb_a);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a, b, c, k, n: ");
        if (sc.hasNextDouble()) {
            double a = sc.nextDouble();
            double b = sc.nextDouble();
            double c = sc.nextDouble();
            double k = sc.nextDouble();
            double n = sc.nextDouble();
            analyzeRecursionTree(a, b, c, k, n);
        } else {
            analyzeRecursionTree(2, 2, 1, 1, 16);
        }
        sc.close();
    }
}
