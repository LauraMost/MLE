package genetische_algorithmen;

public class Individual implements Comparable<Individual> {

	/** fitness of individual is number of bits minus hamming distance */
	private int fitness;
	
	/** Bitstring of individual */
	private String individual;

	/**
	 * constructor creats random bitstring
	 * @param stringLength how long string should be
	 */
	public Individual(int stringLength) {

		individual = "";
		for (int i = 0; i < stringLength; i++) {
			double randNumber = Math.random();	
			if (randNumber < 0.5) {
				individual = individual.concat("1");
			} else {
				individual = individual.concat("0");
			}
		}
		// if there's a zero at the beginning and strings has more than one char change first character to one
		if(individual.charAt(0)== '0' && individual.length() > 1) {
			individual = individual.substring(1, individual.length()-1);
			individual= "1".concat(individual);
		}else if(individual.charAt(0)== '0' && individual.length()==1) {
			individual = "1";
		}
		// calculate fitness
		calculateFitnessforIndividual();
	}
	
	/** 
	 * creates Individual with passed string and checks if it's a valid string
	 * @param string contains bitstring to create individual 
	 */
	public Individual(String string) {
		for(int i = 0; i < string.length(); i++) {
			if(!(string.charAt(i) == '0' || string.charAt(i) == '1')) {
				throw new IllegalArgumentException("String may only contain 1 or 0");
			}
		}
		individual = string;
		calculateFitnessforIndividual();
	}	

	/**
	 * Methode returns fitness of this individual
	 * @return fitness of this individual
	 */
	public int getFitness() {
		return fitness;
	}

	/**
	 * Methode returns string of this individual
	 * @return string of individual
	 */
	public String getIndividualString() {
		return individual;
	}
	
	/**
	 * used for setting string of existing individual after mutation 
	 * @param individ mutated string
	 */
	public void setIndividualString(String individ) {
		individual =  individ;
		calculateFitnessforIndividual();
	}

	/**
	 * calculate fitness of individual
	 */
	private void calculateFitnessforIndividual() {

		String optimum = GeneticAlgorithm.getOptimumString();
		int hammingDistance = 0;
		
		//first get length of individual and optimum and calculate difference of lengths
		int lengthString = individual.length();
		int lengthOpt = optimum.length();
		hammingDistance += Math.abs(lengthString - lengthOpt);

		//then start comparing character for character for differences and start at the end with this comparison
		while (lengthString > 0 && lengthOpt > 0) {

			if (individual.charAt(lengthString - 1) != optimum.charAt(lengthOpt - 1)) {
				hammingDistance++;
			}

			lengthOpt--;
			lengthString--;
		}
		// substract hamming distance from length of optimum
		fitness = optimum.length() - hammingDistance;
	}
	
	@Override	
	public int compareTo(Individual other) {

		int otherFitness = other.getFitness();

		if (this.fitness > otherFitness) {
			return -1;
		} else if (this.fitness == otherFitness) {
			return 0;
		} else {
			return 1;
		}
	}

}
