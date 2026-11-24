import java.util.Scanner;

public class MasterMethod {

    public static void solveMasterTheorem(double a, double b, double k, double p) {
        if (a < 1 || b <= 1) {
            System.out.println("Invalid parameters: a must be >= 1 and b must be > 1");
            return;
        }

        double c_crit = Math.log(a) / Math.log(b);
        double eps = 1e-6;

        System.out.printf("a = %.2f, b = %.2f, k = %.2f, p = %.2f\n", a, b, k, p);
        System.out.printf("log_b(a) = %.4f\n", c_crit);

        if (k < c_crit - eps) {
            System.out.println("Case 1: k < log_b(a)");
            System.out.printf("Complexity = Theta(n^%.4f)\n", c_crit);
        } else if (Math.abs(k - c_crit) <= eps) {
            System.out.println("Case 2: k == log_b(a)");
            if (p > -1 + eps) {
                double newP = p + 1;
                String logTerm = (newP == 1) ? "log n" : "log^" + (int)newP + " n";
                if (k == 0) {
                    System.out.printf("Complexity = Theta(%s)\n", logTerm);
                } else if (k == 1) {
                    System.out.printf("Complexity = Theta(n %s)\n", logTerm);
                } else {
                    System.out.printf("Complexity = Theta(n^%.2f %s)\n", k, logTerm);
                }
            } else if (Math.abs(p - (-1)) <= eps) {
                System.out.printf("Complexity = Theta(n^%.2f log(log n))\n", k);
            } else {
                System.out.printf("Complexity = Theta(n^%.2f)\n", k);
            }
        } else {
            System.out.println("Case 3: k > log_b(a)");
            if (k == 1 && p == 0) {
                System.out.println("Complexity = Theta(n)");
            } else if (k == 2 && p == 0) {
                System.out.println("Complexity = Theta(n^2)");
            } else {
                String pStr = (p == 0) ? "" : " log^" + (int)p + " n";
                System.out.printf("Complexity = Theta(n^%.2f%s)\n", k, pStr);
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a, b, k, p: ");
        if (sc.hasNextDouble()) {
            double a = sc.nextDouble();
            double b = sc.nextDouble();
            double k = sc.nextDouble();
            double p = sc.nextDouble();
            solveMasterTheorem(a, b, k, p);
        } else {
            solveMasterTheorem(2, 2, 1, 0);
        }
        sc.close();
    }
}
