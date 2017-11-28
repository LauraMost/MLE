package genetische_algorithmen;


public class GeneticAlgorithm {

	/** population size is the number of individuals in population */
	private static int populationSize; // p

	/** population contains all individuals */
	private Population population;

	/**
	 * crossover rate describes the percentage of individuals that are replaced
	 * through crossover
	 */
	private double crossoverrate; // r

	/** mutation rate describes the percentage of random mutation */
	private double mutationrate; // m
	
	/** this is our optimum String which we want to create through mutation and crossover*/
	private static String optimumString;
	
	
	
	/**
	 * @param p size of population
	 * @param r crossover rate per generation
	 * @param m mutation rate per generation
	 * @param o optimum gene, that should be discovered 
	 * @param maxLength maximum length of genes
	 */
	public GeneticAlgorithm(int p, double r, double m, String o, int maxLength) {
		populationSize = p;
		crossoverrate = r;
		mutationrate = m;
		optimumString = o;
		population = new Population(maxLength);
		population.initialisePopulation();
		for(int i = 0; i < o.length(); i++) {
			if(!(o.charAt(i) == '0' || o.charAt(i) == '1')) {
				throw new IllegalArgumentException("String may only contain 1 or 0");
			}
		}
		
		
	}
	
	public static String getOptimumString() {
		if(GeneticAlgorithm.optimumString != null) {
			return GeneticAlgorithm.optimumString;	
		}else {
			return null;
		}
	}
	
	/**
	 * select individual with probability Pr(index) = fitness(index) / sum(fitness)
	 * 
	 * @return index of selected individual
	 */
	public int selectIndividual() {

		// this is roulette selection
		double randNumber = Math.random();
		double sum = 0;
		int index = (int) (Math.random() * populationSize);

		double totalFitness = getTotalFitness();

		do {
			index += 1;
			index = index % populationSize;
			sum = sum + (population.getIndividualAtIndex(index).getFitness() / totalFitness);

		} while (sum < randNumber);

		return index;
	}
	
	public double getTotalFitness() {
		double fitness = 0;
		for(int i = 0; i < populationSize; i++) {
			fitness += population.getIndividualAtIndex(i).getFitness();
		}
		 return fitness;
	}
	
	public void evolvePopluation() {
		
		Population newPopulation = new Population(populationSize);
		
		for(int i = 0; i < (1-crossoverrate)*populationSize; i++) {
			
			newPopulation.addIndividualToPopulation(population.getIndividualAtIndex(selectIndividual()));
			
		}
		
		for(int j = 0; j < crossoverrate*populationSize/2; j++) {
			Individual individual1 = population.getIndividualAtIndex(selectIndividual());
			Individual individual2 = population.getIndividualAtIndex(selectIndividual());
			crossover(individual1, individual2, newPopulation);
		}
		
		for(int h = 0; h < mutationrate*populationSize; h++) {
			// mutate
			int randomIndividual = (int)(Math.random()*populationSize);
			mutate(newPopulation.getIndividualAtIndex(randomIndividual));
			
		}
		
		population = newPopulation;
		
	}
	
	private void crossover(Individual individual1, Individual individual2, Population newPopulation) {
		String individ1 = individual1.getIndividualString();
		String individ2 = individual2.getIndividualString();
		
		int factor = (individ1.length() < individ2.length())? individ1.length():individ2.length() ;
		
		int randomIndex = (int)(Math.random()*factor);
		
		String recombinedString1 = individ1.substring(0, randomIndex).concat(individ2).substring(randomIndex);
		String recombinedString2 = individ2.substring(0, randomIndex).concat(individ1).substring(randomIndex);
		
		newPopulation.addIndividualToPopulation(new Individual(recombinedString1));
		newPopulation.addIndividualToPopulation(new Individual(recombinedString2));
	}
	
	private void mutate(Individual individual) {
		String individ = individual.getIndividualString();
		
		int randomIndex = (int)(Math.random()*individ.length());
		
		char[] temp = individ.toCharArray();
		
		if(temp[randomIndex] == '0') {
			temp[randomIndex] = '1';
		}else {
			temp[randomIndex] = '0';
		}
		
		String mutatedIndividual = new String(temp);
		
		individual.setIndividualString(mutatedIndividual);
		
	}
	
	public double getBestFitness() {
		return population.getBestFitness();
	}
	
	public String getBestIndividualOfGeneration() {
		Individual bestIndividual = population.getIndividualAtIndex(0);
		return bestIndividual.getIndividualString();
	}
	
	public double getFitnessThreshold() {
		return GeneticAlgorithm.optimumString.length();
	}
	
	public static int getPopulationSize() {
		return populationSize;
	}
}
