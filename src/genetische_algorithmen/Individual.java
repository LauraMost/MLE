package genetische_algorithmen;

public class Individual implements Comparable<Individual> {

	private int fitness;
	private String individual;

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
		if(individual.charAt(0)== '0' && individual.length() > 1) {
			individual = individual.substring(1, individual.length()-1);
			individual= "1".concat(individual);
		}else if(individual.charAt(0)== '0' && individual.length()==1) {
			individual = "1";
		}
		calculateFitnessforIndividual();
	}
	
	public Individual(String string) {
		for(int i = 0; i < string.length(); i++) {
			if(!(string.charAt(i) == '0' || string.charAt(i) == '1')) {
				throw new IllegalArgumentException("String may only contain 1 or 0");
			}
		}
		individual = string;
		calculateFitnessforIndividual();
	}	

	public int getFitness() {
		return fitness;
	}

	public String getIndividualString() {
		return individual;
	}
	
	public void setIndividualString(String individ) {
		individual =  individ;
		calculateFitnessforIndividual();
	}

	private void calculateFitnessforIndividual() {

		String optimum = GeneticAlgorithm.getOptimumString();
		int hammingDistance = 0;

		int lengthString = individual.length();
		int lengthOpt = optimum.length();

		hammingDistance += Math.abs(lengthString - lengthOpt);

		while (lengthString > 0 && lengthOpt > 0) {

			if (individual.charAt(lengthString - 1) != optimum.charAt(lengthOpt - 1)) {
				hammingDistance++;
			}

			lengthOpt--;
			lengthString--;
		}
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
