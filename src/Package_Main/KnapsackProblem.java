package Package_Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class KnapsackProblem extends Problem {
    private final Random rand = new Random();
    private int n;
    private int capacity;
    private ArrayList<Integer> values;
    private ArrayList<Integer> sizes;

    public KnapsackProblem(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            n = scanner.nextInt();
            capacity = scanner.nextInt();
            values = new ArrayList<>(n);
            sizes = new ArrayList<>(n);
            for (int i = 0; i < n; i++) {
                values.add(scanner.nextInt());
                sizes.add(scanner.nextInt());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error: Could not open file " + filename);
            System.exit(1);
        }
    }

    @Override
    int calculateValue(int[] genotype) {
        int value = 0;
        int size = 0;
        for (int i = 0; i < n; i++) {
            if (genotype[i] == 1) {
                value += values.get(i);
                size += sizes.get(i);
            }
            if (size > capacity) return -1;
        }
        return value;
    }

    @Override
    public int getProblemSize() {
        return n;
    }
}
