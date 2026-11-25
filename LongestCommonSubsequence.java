import java.util.Scanner;

public class LongestCommonSubsequence {

    public static void lcs(String X, String Y) {
        int m = X.length();
        int n = Y.length();

        int[][] L = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (X.charAt(i - 1) == Y.charAt(j - 1)) {
                    L[i][j] = L[i - 1][j - 1] + 1;
                } else {
                    L[i][j] = Math.max(L[i - 1][j], L[i][j - 1]);
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        int i = m, j = n;
        while (i > 0 && j > 0) {
            if (X.charAt(i - 1) == Y.charAt(j - 1)) {
                sb.append(X.charAt(i - 1));
                i--;
                j--;
            } else if (L[i - 1][j] >= L[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }

        String lcsStr = sb.reverse().toString();
        System.out.println("LCS Length = " + L[m][n]);
        System.out.println("LCS String = " + lcsStr);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter string X: ");
        if (sc.hasNext()) {
            String X = sc.next();
            System.out.print("Enter string Y: ");
            String Y = sc.next();
            lcs(X, Y);
        } else {
            String X = "LONGEST";
            String Y = "STONE";
            System.out.println("X = " + X + ", Y = " + Y);
            lcs(X, Y);
        }
        sc.close();
    }
}
