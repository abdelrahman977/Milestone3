package harrypotter.exceptions;

public class OutOfRangeException extends InvalidActionException{
	private int allowedRange;
	public int getAllowedRange() {
		return allowedRange;
	}
	public OutOfRangeException(int allowedRange) {
		super(""+allowedRange);
		this.allowedRange = allowedRange;
	}

}
