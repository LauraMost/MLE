package genetische_algorithmen;

import java.util.*;

/**
 * @author Laura
 *
 */
public class Population {

	/**
	 * p sei die Anzahl der Hypothesen(=Individuen) in der Population P -> kann mal
	 * bei so 100 sein r sei der Anteil, der in jedem Schritt durch Crossover
	 * ersetzt wird -> kann auch relativ klein sein m sei Mutationsrate kann realtiv
	 * klein sein Initialisiere die Population (erzeuge Zufallshypothesen) Errechne
	 * die Fitness für alle Hypothesen
	 */
	
	/** maxLength is the maximum length of our bit strings */
	private int maxLength;

	/** population contains all individuals */
	private List<Individual> genes;

	
	public Population(int maxLength) {
		
		this.maxLength = maxLength;
		this.genes = new ArrayList<Individual>(GeneticAlgorithm.getPopulationSize());
		
	}
	
	public Population() {
		
		this.maxLength = 0;
		this.genes = new ArrayList<Individual>(GeneticAlgorithm.getPopulationSize());
		
	}
	
	 public void initialisePopulation() {
		
		for (int i = 0; i < GeneticAlgorithm.getPopulationSize(); i++) {
			int stringSize = (int) (Math.random() * maxLength);
			genes.add(new Individual(stringSize+1));
		}
		
	}
	 
	 public void addIndividualToPopulation(Individual i) {
		 genes.add(i);
	 }
	 
	 public Individual getIndividualAtIndex(int index) {
		 return genes.get(index);
	 }
	 
	 public double getBestFitness() {
			Collections.sort(genes);
			return genes.get(0).getFitness();
	}
}
