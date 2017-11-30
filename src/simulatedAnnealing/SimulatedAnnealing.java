package simulatedAnnealing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SimulatedAnnealing {

	private int numberOfCities;
	private List<City> roundtrip;
	private double[][] distanceTable;
	private double temperature;
	private double epsilon;
	
	public  SimulatedAnnealing(int numberOfCities, int temperature, double epsilon) {
		this.numberOfCities = numberOfCities;
		this.distanceTable = new double[this.numberOfCities][this.numberOfCities];
		this.epsilon = epsilon;
		this.temperature = temperature;
		
		Set<City> tempSetOfCities = new HashSet<City>();
		while(tempSetOfCities.size() < this.numberOfCities){
			int x = (int)(Math.random()*100);
			int y = (int)(Math.random()*100);
			tempSetOfCities.add(new City(x,y));
		}
		
		this.roundtrip = new ArrayList<City>(tempSetOfCities);
		
		initializeDistanceTable();
	}
	
	public void initializeDistanceTable() {
		
		for(int i = 0; i<this.numberOfCities-1; i++) {
			this.distanceTable[i][i] = 0;
			
			for(int j = i+1; j < this.numberOfCities; j++) {
				this.distanceTable[i][j] = this.roundtrip.get(i).getDistanceTo(this.roundtrip.get(j));
				this.distanceTable[j][i] = this.distanceTable[i][j];
			}
			
		}
		
	}
	
	public void swapCities(int a, int b) {
		City temp = this.roundtrip.get(a);
		this.roundtrip.set(a, this.roundtrip.get(b));
		this.roundtrip.set(b, temp);
	}
	
	public double getRoundtripDistance() {
		double distance = 0;
		for(int i = 0; i< this.numberOfCities-1; i++) {
			distance += this.roundtrip.get(i).getDistanceTo(this.roundtrip.get(i+1));
		}
		distance += this.roundtrip.get(this.numberOfCities-1).getDistanceTo(this.roundtrip.get(0));
		return distance;
	}
	
	public void startHillclimber() {
		double optimalDistance = getRoundtripDistance() *-1;
		
		while(temperature > epsilon) {
			
			int cityA = (int)(Math.random()*numberOfCities);
			int cityB = (int)(Math.random()*numberOfCities);
			
			swapCities(cityA, cityB);
			
			double currentDistance = getRoundtripDistance()*-1;
			double randNumber = Math.random();
			
			double probability = Math.exp((currentDistance-optimalDistance)/temperature);
					
			if(currentDistance > optimalDistance) {
				optimalDistance = currentDistance;
				System.out.format("%f: %.2f%n", temperature, optimalDistance*-1);
				
			}else if(randNumber < probability) {
				
				System.out.format("%f: %.2f\t %f %f%n", temperature, currentDistance*-1, randNumber, probability);
				optimalDistance = currentDistance;
			}else {
				System.out.println(randNumber +" "+ probability);
				swapCities(cityA, cityB);
			}
			
			temperature -= epsilon;
		}
	}
	
}
