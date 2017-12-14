package genetische_algorithmen;

public class Main {

	public static void main(String[] args) {
		
		/* maxFitness is maximum fitness of current population - meaning best matched string
		 * fitnessThreshold is number of bits in optimum string
		 * */
		GeneticAlgorithm geneticAlg = new GeneticAlgorithm(100, 0.25, 0.015, "1011101000", 20);
		int generationCount = 0;
		do {
			geneticAlg.evolvePopluation();
			System.out.format("Generation number: %d \tbest fitness: %.0f \tbest individual: %s \n", generationCount, geneticAlg.getBestFitness(), geneticAlg.getBestIndividualOfGeneration());
			generationCount++;
		}while(geneticAlg.getBestFitness() < geneticAlg.getFitnessThreshold());
	
	}

}
