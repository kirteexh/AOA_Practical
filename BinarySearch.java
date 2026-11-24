import java.util.Arrays;
import java.util.Scanner;

public class BinarySearch {

    public static int binarySearch(int[] arr, int target) {
        int low = 0;
        int high = arr.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of elements: ");
        if (sc.hasNextInt()) {
            int n = sc.nextInt();
            int[] arr = new int[n];
            System.out.println("Enter " + n + " sorted elements:");
            for (int i = 0; i < n; i++) {
                arr[i] = sc.nextInt();
            }
            System.out.print("Enter target to search: ");
            int target = sc.nextInt();
            int result = binarySearch(arr, target);
            if (result != -1) {
                System.out.println("Element found at index " + result);
            } else {
                System.out.println("Element not found");
            }
        } else {
            int[] arr = {12, 24, 32, 45, 53, 67, 78, 89, 99};
            int target = 67;
            System.out.println("Array: " + Arrays.toString(arr));
            System.out.println("Target: " + target);
            int result = binarySearch(arr, target);
            System.out.println("Index: " + result);
        }
        sc.close();
    }
}
