package harrypotter.model.character;

public class RavenclawWizard extends Wizard implements Champion {

	public RavenclawWizard(String name) {
		super(name, 750, 700);
	}

	public void useTrait() {
		if (getListener() != null)
			getListener().onRavenclawTrait();
	}
}
