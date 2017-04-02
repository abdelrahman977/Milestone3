package harrypotter.exceptions;

public class NotEnoughIPException extends NotEnoughResourcesException{
	private int requiredIP;
	private int remainingIP;
	public NotEnoughIPException(int requiredIP, int remainingIP) {
		super(""+requiredIP+remainingIP);
		this.remainingIP = remainingIP;
		this.requiredIP = requiredIP;
		
	}
	public int getRequiredIP() {
		return requiredIP;
	}
	public int getRemainingIP() {
		return remainingIP;
	}

}
