package simulatedAnnealing;

public class City {

	private int xCoordinate;
	private int yCoordinate;
	
	public City(int x, int y) {
		this.xCoordinate = x;
		this.yCoordinate = y;
	}
	
	public int getXCoordinate() {
		return xCoordinate;
	}
	
	public int getYCoordinate() {
		return yCoordinate; 
	}
	
	public double getDistanceTo(City other) {
		double distance = 0;
		distance = Math.pow((other.getXCoordinate() - this.xCoordinate), 2) + Math.pow((other.getYCoordinate()-this.yCoordinate),2);
		distance = Math.sqrt(distance);
		return distance;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + xCoordinate;
		result = prime * result + yCoordinate;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		City other = (City) obj;
		if (xCoordinate != other.xCoordinate) {
			return false;
		}
		if (yCoordinate != other.yCoordinate) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		String s = "x: " + xCoordinate + " y: " + yCoordinate;
		return s;
	}
	
	
	
}
