package genetische_algorithmen;

import java.util.*;

public class Population {

	
	/** maxLength is the maximum length of our bit strings */
	private int maxLength;

	/** population contains all individuals (genes) */
	private List<Individual> individuals;

	/**
	 * constructor sets max length of bitstrings and sets up individuals
	 * @param maxLength max length of bitstrings
	 */
	public Population(int maxLength) {
		
		this.maxLength = maxLength;
		this.individuals = new ArrayList<Individual>(GeneticAlgorithm.getPopulationSize());
		
	}

	public Population() {
		
		this.maxLength = 0;
		this.individuals = new ArrayList<Individual>(GeneticAlgorithm.getPopulationSize());
		
	}
	
	/**
	 * create individuals to get population of defined size
	 */
	 public void initialisePopulation() {
		
		for (int i = 0; i < GeneticAlgorithm.getPopulationSize(); i++) {
			int stringSize = (int) (Math.random() * maxLength);
			individuals.add(new Individual(stringSize+1));
		}
		
	}
	 
	 /**
	  * add single individual to population
	  * @param i individual to be added to population
	  */
	 public void addIndividualToPopulation(Individual i) {
		 individuals.add(i);
	 }
	 
	 /**
	  * get individual at specific index of population
	  * @param index index of individual
	  * @return individual
	  */
	 public Individual getIndividualAtIndex(int index) {
		 return individuals.get(index);
	 }
	 
	 /**
	  * Method to get best fitness in population is found by sorting 
	  * individuals according to their fitness and returning first 
	  * individual in population
	  * @return best fitness found in population
	  */
	 public double getBestFitness() {
			Collections.sort(individuals);
			return individuals.get(0).getFitness();
	}
}
