package Package_Main;

import java.util.ArrayList;
import java.util.Random;

public class GeneticAlgorithm {
    private final int populationSize;
    private final double crossoverProbability;
    private final double mutationProbability;
    private final Problem problem;
    private final ArrayList<Individual> population;
    private final Random rand = new Random();
    private int[] bestSolution;

    public GeneticAlgorithm(int populationSize_, double crossoverProbability_, double mutationProbability_,
                            Problem problem_) {
        populationSize = populationSize_;
        crossoverProbability = crossoverProbability_;
        mutationProbability = mutationProbability_;
        problem = problem_;
        population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            population.add(new Individual(generateRandomSolution(), problem));
        }
    }

    void run(int maxIterations) {
        for (int i = 0; i < maxIterations; i++) {
            Individual parent1 = selectParent();
            Individual parent2 = selectParent();

            if (rand.nextDouble() < crossoverProbability) {
                Individual[] children = parent1.crossover(parent2);
                parent1 = children[0];
                parent2 = children[1];
            }
            if (rand.nextDouble() < mutationProbability) {
                parent1.mutate();
            }
            if (rand.nextDouble() < mutationProbability) {
                parent2.mutate();
            }
            replaceWorst(parent1);
            replaceWorst(parent2);
            updateBestSolution();
        }
    }

    int[] getBestSolution() {
        return bestSolution;
    }


    Individual selectParent() {
        int randomIndex1 = rand.nextInt(populationSize);
        int randomIndex2 = rand.nextInt(populationSize);
        if (population.get(randomIndex1).calculateAdaptation() > population.get(randomIndex2).calculateAdaptation()) {
            return population.get(randomIndex1);
        } else return population.get(randomIndex2);
    }

//    Individual selectParent() {
//        int bestAdaptation = 0;
//        Individual bestParent = population.get(0);
//        ArrayList<Individual> individuals = new ArrayList<>();
//        for (int i = 0; i < 0.2 * populationSize; i++) {
//            int randomIndex = rand.nextInt(populationSize);
//            individuals.add(population.get(randomIndex));
//        }
//        for (Individual individual : individuals) {
//            int adaptation = individual.calculateAdaptation();
//            if (adaptation > bestAdaptation) {
//                bestAdaptation = adaptation;
//                bestParent = individual;
//            }
//        }
//        return bestParent;
//    }

    void replaceWorst(Individual child) {
        int worstIndex = 0;
        int worstAdaptation = population.get(0).calculateAdaptation();
        for (int i = 1; i < populationSize; i++) {
            int adaptation = population.get(i).calculateAdaptation();
            if (adaptation < worstAdaptation) {
                worstIndex = i;
                worstAdaptation = adaptation;
            }
        }
        population.set(worstIndex, child);
    }

    void updateBestSolution() {
        bestSolution = population.get(0).getGenotype();
        int bestAdaptation = problem.calculateValue(bestSolution);
        for (Individual i : population) {
            int adaptation = problem.calculateValue(i.getGenotype());
            if (adaptation > bestAdaptation) {
                bestSolution = i.getGenotype();
                bestAdaptation = adaptation;
            }
        }
    }

    int[] generateRandomSolution() {
        int[] genotype = new int[problem.getProblemSize()];
        for (int i = 0; i < problem.getProblemSize(); i++) {
            genotype[i] = rand.nextInt(2);
        }
        return genotype;
    }
}