package harrypotter.model.character;

import java.io.IOException;

import harrypotter.model.world.Direction;

public class SlytherinWizard extends Wizard implements Champion {
	private Direction traitDirection;

	public SlytherinWizard(String name) {
		super(name, 850, 550);
	}

	public Direction getTraitDirection() {
		return traitDirection;
	}

	public void setTraitDirection(Direction traitDirection) {
		this.traitDirection = traitDirection;
	}

	public void useTrait() throws IOException {
		if (getListener() != null)
			getListener().onSlytherinTrait(traitDirection);
	}

}
