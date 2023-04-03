package Package_Main;

import java.util.Random;

public class Individual {
    private final int[] genotype;
    private final Problem problem;
    private final Random rand = new Random();

    public Individual(int[] genotype, Problem problem) {
        this.genotype = genotype;
        this.problem = problem;
    }

    int calculateAdaptation() {
        return problem.calculateValue(genotype);
    }

    void mutate() {
        int mutationPoint = rand.nextInt(genotype.length);
        genotype[mutationPoint] = genotype[(mutationPoint) ^ 1];
    }

    Individual[] crossover(Individual other) {
        int[] child1Genotype = genotype.clone();
        int[] child2Genotype = other.genotype.clone();
        Individual[] newPair = new Individual[2];

        int crossoverPoint = rand.nextInt(genotype.length);

        for (int i = crossoverPoint; i < genotype.length; i++) {
            child1Genotype[i] = other.genotype[i];
            child2Genotype[i] = genotype[i];

        }
        newPair[0] = new Individual(child1Genotype, problem);
        newPair[1] = new Individual(child2Genotype, problem);
        return newPair;

    }

    public int[] getGenotype() {
        return genotype;
    }
}