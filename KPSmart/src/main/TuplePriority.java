package main;

/**
 * This is a simple class to represent a Tuple that has an origin, destination
 * and priority.
 * 
 * @author Donald Tang
 */

public class TuplePriority {
	String origin;
	String destination;
	String priority;

	/**
	 * Constructor
	 * 
	 * @param origin
	 * @param destination
	 * @param priority
	 */

	public TuplePriority(String origin, String destination, String priority) {
		this.origin = origin;
		this.destination = destination;
		this.priority = priority;
	}

	/**
	 * Getter
	 * 
	 * @return the origin
	 */
	public String getOrigin() {
		return origin;
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
	 * Getter
	 * 
	 * @return destination
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * Setter
	 * 
	 * @param destination
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * Getter
	 * 
	 * @return priority
	 */
	public String getPriority() {
		return priority;
	}

	/**
	 * Setter
	 * 
	 * @param priority
	 */
	public void setPriority(String priority) {
		this.priority = priority;
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
		result = prime * result + ((priority == null) ? 0 : priority.hashCode());
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
		TuplePriority other = (TuplePriority) obj;
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
		if (priority == null) {
			if (other.priority != null)
				return false;
		} else if (!priority.equals(other.priority))
			return false;
		return true;
	}
}
