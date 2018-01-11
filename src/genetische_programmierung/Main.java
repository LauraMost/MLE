package genetische_programmierung;

import java.util.ArrayList;
import java.util.HashSet;

public class Main {

	static int populationSize = 100;
	//VM Instance
	static VM vm = new VM();
	//Programme/Memory's
	static int[][] mem = new int[populationSize][vm.MAX];
	// best found program
	static int[] bestMem = new int[vm.MAX];
	//temporary programs for selection and mutation, will be copied back into mem
	static int[][] newMem = new int[populationSize][vm.MAX];
	//fitness's for every program
	static int[] fitnesses = new int[populationSize];
	//created prime numbers of best program
	static HashSet bestPrimes;
	// best reached fitness
	static int maxFitness = 0;
	//how many mutations on one program per run
	static int mutationPerRun = 1;
	//the chance of a mutation
	static double mutationRate = 1;
	static int threshold = 1000;
	//sum of all fitness's in the current population
	static int fitnessSum = 0;
	//multiplier for average fitness
	static int fitMul = 3;
	//maximum value of mem
	static int baseNumber = 99; //7 or 99


	public static void main(String[] args) {
		//initiate mem with random numbers between 0 and baseNumber
		initiate();
		int counter = 0;
		while (maxFitness < threshold) {
			//iterate over each memory, simulate it and check if its better then the best mem
			for (int x = 0; x < mem.length; x++) {
				//reset the vm
				vm.reset();
				//copy memory in vm
				vm.setMemAndResizeMAX(mem[x]);
				//simulate
				vm.simulate();
				ArrayList primes = vm.getPrimeNumbers();
				//unique prime numbers
				int fit = fitness(primes);
				// save fitness for later selection
				fitnesses[x] = fit;
				//add fitnessSum for average
				fitnessSum += fit;


				if (fit > maxFitness) {
					maxFitness = fit;
					bestPrimes = new HashSet(vm.getPrimeNumbers());
					bestMem = mem[x].clone();
					//optimize();
				}

			}

			//reset fitnessSum
			fitnessSum = 0;
			//
			selection();
			mutate();

			counter++;
			if (counter % 100 == 0) {
				System.out.println("current iteration: " + counter);
				System.out.println("Best fitness is: " + maxFitness);
				System.out.println(bestPrimes);
			}

			//10% of population gets the best variation

		}
		System.out.println("reached " + maxFitness + " prime numbers: ");
		vm.setMemAndResizeMAX(bestMem);
		vm.simulate();
		ArrayList bestPrimes = vm.getPrimeNumbers();
		System.out.println(bestPrimes);


	}

	/**
	 * initiates programs with random numbers between 0 and baseNumbers
	 */
	static void initiate() {
		for (int x = 0; x < mem.length; x++) {
			for (int y = 0; y < mem[x].length; y++) {
				mem[x][y] = (int) (Math.random() * baseNumber);
			}
		}

	}

	/**
	 * mutates part of the population with mutationRate-chance
	 */
	static void mutate() {
		for (int x = 1; x < mem.length; x++) {
			if (Math.random() <= mutationRate) {
				for (int i = 0; i < mutationPerRun; i++) {
					int rand = (int) (Math.random() * mem[x].length);
					int mutated = (int) (Math.random() * baseNumber);
					mem[x][rand] = mutated;
				}
			}
		}
	}

	static void optimize() {
		for (int x = 0; x < mem.length * 0.1; x++) {
			for (int y = 0; y < mem[x].length; y++) {
				mem[x] = bestMem.clone();
			}
		}
	}

	/**
	 * get the number of unique numbers
	 * @param x ArrayList with primenumbers
	 * @return number of unique primes
	 */
	static int fitness(ArrayList x) {
		HashSet y = new HashSet(x);
		return y.size();
	}

	/**
	 * selects individual, mem's with higher fitness
	 * have a higher chance of getting picked
	 * @return index of chosen individual
	 */
	static int selectIndividual() {
		double randNum = Math.random();
		double sum = 0;
		int index = (int) (Math.random() * populationSize);
		do {
			index++;
			index = index % populationSize;
			sum += probability(index);
		} while (sum < randNum);
		return index;
	}

	/**
	 *
	 * @param index index of individual
	 * @return probability that this individual gets selected
	 */
	static double probability(int index) {
        /*double all = 0.0;
        for (int x = 0; x < fitnesses.length; x++) {
            all += fitnesses[x];
        }*/
		double average = fitnessSum / populationSize;

		return (fitnesses[index] / (average * fitMul));
	}

	/**
	 * Selects a part of the population by using the fitness
	 * Overwrites old population with selected population
	 */
	static void selection() {
		newMem = new int[populationSize][vm.MAX];
		newMem[0] = bestMem.clone();
		for (int x = 1; x < mem.length; x++) {
			int z = selectIndividual();
            /*for (int y = 0; y < vm.MAX; y++) {
                newMem[x][y] = mem[z][y];
            }*/
			newMem[x] = mem[z].clone();
		}

		for (int y = 0; y < mem.length; y++) {
			mem[y] = newMem[y];
		}
	}

}
