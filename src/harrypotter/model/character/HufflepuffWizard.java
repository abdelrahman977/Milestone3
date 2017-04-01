package harrypotter.model.character;

public class HufflepuffWizard extends Wizard implements Champion {

	public HufflepuffWizard(String name) {
		super(name, 1000, 450);
	}

	public void useTrait() {
		if (getListener() != null)
			getListener().onHufflepuffTrait();
	}
}
