package Package_Main;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Problem problem = new KnapsackProblem("problem_instance.txt");
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(1000, 0.6, 0.1, problem);

        geneticAlgorithm.run(5000);

        int[] bestSolution = geneticAlgorithm.getBestSolution();

        int bestProfit = problem.calculateValue(bestSolution);

        System.out.println("Best profit: " + bestProfit);

        System.out.println(Arrays.toString(bestSolution));
    }
}