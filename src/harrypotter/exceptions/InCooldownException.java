package harrypotter.exceptions;

public class InCooldownException extends NotEnoughResourcesException{
	private int remainingTurns;
	
	public InCooldownException(int remainingTurns) {
		super(""+remainingTurns);
		this.remainingTurns = remainingTurns;
		
	}
	public int getRemainingTurns() {
		return remainingTurns;
	}

}
