package Hillclimber;

import java.util.*;

public class Hillclimber {

	private int numberOfCities;
	private List<City> roundtrip;
	private double[][] distanceTable;
	
	public Hillclimber(int numberOfCities) {
		this.numberOfCities = numberOfCities;
		this.distanceTable = new double[this.numberOfCities][this.numberOfCities];
		
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
		double optimalDistance = getRoundtripDistance();
		
		for(int i = 0; i < 200000; i++) {
			
			int cityA = (int)(Math.random()*numberOfCities);
			int cityB = (int)(Math.random()*numberOfCities);
			
			swapCities(cityA, cityB);
			
			double currentDistance = getRoundtripDistance();
			
			if(currentDistance*-1 > optimalDistance*-1) {
				optimalDistance = currentDistance;
				System.out.format("%d: %.2f%n", i, optimalDistance);
			}else {
				swapCities(cityA, cityB);
			}
			
		}
	}
	

}
