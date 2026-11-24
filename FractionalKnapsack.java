import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class FractionalKnapsack {

    static class Item {
        int id;
        double weight;
        double value;
        double ratio;

        public Item(int id, double weight, double value) {
            this.id = id;
            this.weight = weight;
            this.value = value;
            this.ratio = value / weight;
        }
    }

    public static double getMaxValue(Item[] items, double capacity) {
        Arrays.sort(items, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return Double.compare(o2.ratio, o1.ratio);
            }
        });

        double currentCapacity = capacity;
        double totalProfit = 0.0;

        for (Item item : items) {
            if (currentCapacity == 0) {
                break;
            }
            if (item.weight <= currentCapacity) {
                currentCapacity -= item.weight;
                totalProfit += item.value;
                System.out.printf("Item %d: taken 100%% (Weight: %.1f, Profit: %.1f)\n", 
                                  item.id, item.weight, item.value);
            } else {
                double fraction = currentCapacity / item.weight;
                double addedValue = item.value * fraction;
                totalProfit += addedValue;
                System.out.printf("Item %d: taken %.2f%% (Weight: %.1f, Profit: %.1f)\n", 
                                  item.id, fraction * 100, currentCapacity, addedValue);
                currentCapacity = 0;
            }
        }
        return totalProfit;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of items: ");
        if (sc.hasNextInt()) {
            int n = sc.nextInt();
            Item[] items = new Item[n];
            for (int i = 0; i < n; i++) {
                System.out.print("Enter weight and profit for item " + (i + 1) + ": ");
                double w = sc.nextDouble();
                double p = sc.nextDouble();
                items[i] = new Item(i + 1, w, p);
            }
            System.out.print("Enter capacity: ");
            double cap = sc.nextDouble();
            double maxProfit = getMaxValue(items, cap);
            System.out.printf("Maximum Profit = %.2f\n", maxProfit);
        } else {
            Item[] items = {
                new Item(1, 10, 60),
                new Item(2, 40, 40),
                new Item(3, 20, 100),
                new Item(4, 30, 120)
            };
            double cap = 50;
            double maxProfit = getMaxValue(items, cap);
            System.out.printf("Maximum Profit = %.2f\n", maxProfit);
        }
        sc.close();
    }
}
