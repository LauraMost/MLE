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
	 * setup genetic algorithm
	 * @param populationsize size of population
	 * @param crossoverrate crossover rate per generation
	 * @param mutationrate mutation rate per generation
	 * @param optimum optimum gene, that should be discovered 
	 * @param maxLength maximum length of genes
	 */
	public GeneticAlgorithm(int populationsize, double crossoverrate, double mutationrate, String optimum, int maxLength) {
		this.populationSize = populationsize;
		this.crossoverrate = crossoverrate;
		this.mutationrate = mutationrate;
		optimumString = optimum;
		population = new Population(maxLength);
		population.initialisePopulation();
		for(int i = 0; i < optimum.length(); i++) {
			if(!(optimum.charAt(i) == '0' || optimum.charAt(i) == '1')) {
				throw new IllegalArgumentException("String may only contain 1 or 0");
			}
		}
		
		
	}
	
	/**
	 * returns optimum string
	 * @return optimum string
	 */
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
	
	/**
	 * returns summaraised fitness of all individuals 
	 * @return summarised fitness of all individuals
	 */
	public double getTotalFitness() {
		double fitness = 0;
		for(int i = 0; i < populationSize; i++) {
			fitness += population.getIndividualAtIndex(i).getFitness();
		}
		 return fitness;
	}
	
	/**
	 * evolve population by choosing individuals for new population and creating new 
	 * individuals through crossover and mutation of individuals in next generation  
	 */
	public void evolvePopluation() {
		
		Population nextGeneration = new Population(populationSize);
		
		for(int i = 0; i < (1-crossoverrate)*populationSize; i++) {
			
			nextGeneration.addIndividualToPopulation(population.getIndividualAtIndex(selectIndividual()));
			
		}
		
		for(int j = 0; j < crossoverrate*populationSize/2; j++) {
			Individual individual1 = population.getIndividualAtIndex(selectIndividual());
			Individual individual2 = population.getIndividualAtIndex(selectIndividual());
			crossover(individual1, individual2, nextGeneration);
		}
		
		for(int h = 0; h < mutationrate*populationSize; h++) {
			// mutate
			int randomIndividual = (int)(Math.random()*populationSize);
			mutate(nextGeneration.getIndividualAtIndex(randomIndividual));
			
		}
		
		population = nextGeneration;
		
	}
	
	/**
	 * takes to individuals and recombines them through crossover
	 * adds this recombined individuals to next generation of population
	 * @param individual1 first individual for crossover
	 * @param individual2 second individual for crossover
	 * @param newPopulation next generation of population to which recombined individuals should be added
	 */
	private void crossover(Individual individual1, Individual individual2, Population newPopulation) {
		String individ1 = individual1.getIndividualString();
		String individ2 = individual2.getIndividualString();
		
		int randomIndex1 = (int)(Math.random()*individ1.length());
		int randomIndex2 = (int)(Math.random()*individ2.length());
		
		String recombinedString1 = individ1.substring(0, randomIndex1);
		String temp = individ2.substring(randomIndex2);
		recombinedString1 = recombinedString1.concat(temp);
		
		String recombinedString2 = individ2.substring(0, randomIndex2);
		temp = individ1.substring(randomIndex1);
		recombinedString2 = recombinedString2.concat(temp);
		
		newPopulation.addIndividualToPopulation(new Individual(recombinedString1));
		newPopulation.addIndividualToPopulation(new Individual(recombinedString2));
	}
	
	/**
	 * takes an individual and randomly mutates one position of it's string
	 * @param individual the individual which should be mutated  
	 */
	private void mutate(Individual individual) {
		String individualString = individual.getIndividualString();
		
		int randomIndex = (int)(Math.random()*individualString.length());
		
		char[] temp = individualString.toCharArray();
		
		if(temp[randomIndex] == '0') {
			temp[randomIndex] = '1';
		}else {
			temp[randomIndex] = '0';
		}
		
		String mutatedIndividual = new String(temp);
		
		individual.setIndividualString(mutatedIndividual);
		
	}
	
	/**
	 * returns best fitness of individual in population
	 * @return best fitness
	 */
	public double getBestFitness() {
		return population.getBestFitness();
	}
	
	/**
	 * returns the string of the best individual in the population 
	 * @return string of best individual in population
	 */
	public String getBestIndividualOfGeneration() {
		Individual bestIndividual = population.getIndividualAtIndex(0);
		return bestIndividual.getIndividualString();
	}
	
	/**
	 * returns fitness threshold, which is the length of optimum string
	 * @return fitness threshold
	 */
	public double getFitnessThreshold() {
		return GeneticAlgorithm.optimumString.length();
	}
	
	/**
	 * returns size of population
	 * @return size of population
	 */
	public static int getPopulationSize() {
		return populationSize;
	}
}
