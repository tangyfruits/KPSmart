package main;

public class Tuple {
	/**
	 * A simple class to represent a tuple with Origin and Destination.
	 * 
	 * @author Donald Tang Edited by Pri Bhula
	 */

	private String origin;
	private String destination;

	/**
	 * Constructor
	 * 
	 * @param origin
	 * @param destination
	 */

	public Tuple(String origin, String destination) {
		this.origin = origin;
		this.destination = destination;
	}

	/**
	 * Getter
	 * 
	 * @return name
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * Getter
	 * 
	 * @return name
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * Setter
	 * 
	 * @param origin
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * Setter
	 * 
	 * @param destination
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((destination == null) ? 0 : destination.hashCode());
		result = prime * result + ((origin == null) ? 0 : origin.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tuple other = (Tuple) obj;
		if (destination == null) {
			if (other.destination != null)
				return false;
		} else if (!destination.equals(other.destination))
			return false;
		if (origin == null) {
			if (other.origin != null)
				return false;
		} else if (!origin.equals(other.origin))
			return false;
		return true;
	}
}