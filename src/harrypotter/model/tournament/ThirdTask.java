package harrypotter.model.tournament;

import harrypotter.exceptions.InCooldownException;
import harrypotter.exceptions.InvalidTargetCellException;
import harrypotter.exceptions.OutOfBordersException;
import harrypotter.model.character.Champion;
import harrypotter.model.character.Wizard;
import harrypotter.model.world.Cell;
import harrypotter.model.world.ChampionCell;
import harrypotter.model.world.CupCell;
import harrypotter.model.world.Direction;
import harrypotter.model.world.EmptyCell;
import harrypotter.model.world.PhysicalObstacle;
import harrypotter.model.world.ObstacleCell;
import harrypotter.model.world.WallCell;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ThirdTask extends Task {

	public ThirdTask(ArrayList<Champion> champions) throws IOException {

		super(champions);
		generateMap();

		setCurrentChamp(getChampions().get(0));

	}

	public void generateMap() throws IOException {

		initializeAllEmpty();
		readMap("task3map.csv");
		allocatePotions();

	}

	private void readMap(String filePath) throws IOException {

		Cell[][] map = getMap();
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line = br.readLine();

		int i = 0;
		while (line != null) {

			String[] content = line.split(",");

			for (int j = 0; j < content.length; j++) {

				char cellType = content[j].charAt(0);

				switch (cellType) {
				case '0':

					map[i][j] = new EmptyCell();
					break;

				case '5':

					map[i][j] = new WallCell();
					break;

				case '6':

					int hp = (int) ((Math.random() * 101) + 200);
					map[i][j] = new ObstacleCell(new PhysicalObstacle(hp));
					break;

				case '7':

					map[i][j] = new CupCell();
					break;

				case '1':
				case '2':
				case '3':
				case '4':

					int c = Character.getNumericValue(cellType);
					if (c <= getChampions().size()) {

						map[i][j] = new ChampionCell(getChampions().get(c - 1));
						((Wizard) getChampions().get(c - 1))
								.setLocation(new Point(i, j));

					}

					break;

				default:

					break;

				}

			}

			i++;
			line = br.readLine();

		}

		br.close();

	}

	public void moveForward() throws IOException,OutOfBordersException, InvalidTargetCellException {

		Wizard current = (Wizard) getCurrentChamp();

		Point location = current.getLocation();

		Cell next = getMap()[location.x - 1][location.y];

		if (next instanceof CupCell) {

			getMap()[location.x][location.y] = new EmptyCell();
			getMap()[location.x - 1][location.y] = new EmptyCell();
			current.setLocation(new Point(location.x - 1, location.y));

			getChampions().remove(current);

			if (getListener() != null)
				getListener().onFinishingThirdTask((Champion) current);

		} else {
			super.moveForward();
		}
	}

	public void moveBackward() throws IOException,OutOfBordersException, InvalidTargetCellException {

		Wizard current = (Wizard) getCurrentChamp();

		Point location = current.getLocation();

		Cell next = getMap()[location.x + 1][location.y];

		if (next instanceof CupCell) {

			getMap()[location.x][location.y] = new EmptyCell();
			getMap()[location.x + 1][location.y] = new EmptyCell();
			current.setLocation(new Point(location.x + 1, location.y));

			getChampions().remove(current);

			if (getListener() != null)
				getListener().onFinishingThirdTask((Champion) current);

		} else {
			super.moveBackward();
		}
	}

	public void moveLeft() throws IOException,OutOfBordersException, InvalidTargetCellException {

		Wizard current = (Wizard) getCurrentChamp();

		Point location = current.getLocation();

		Cell next = getMap()[location.x][location.y - 1];

		if (next instanceof CupCell) {

			getMap()[location.x][location.y] = new EmptyCell();
			getMap()[location.x][location.y - 1] = new EmptyCell();
			current.setLocation(new Point(location.x, location.y - 1));

			getChampions().remove(current);

			if (getListener() != null)
				getListener().onFinishingThirdTask((Champion) current);

		} else {
			super.moveLeft();
		}
	}

	public void moveRight() throws IOException,OutOfBordersException, InvalidTargetCellException {

		Wizard current = (Wizard) getCurrentChamp();

		Point location = current.getLocation();

		Cell next = getMap()[location.x][location.y + 1];

		if (next instanceof CupCell) {

			getMap()[location.x][location.y] = new EmptyCell();
			getMap()[location.x][location.y + 1] = new EmptyCell();
			current.setLocation(new Point(location.x, location.y + 1));

			getChampions().remove(current);

			if (getListener() != null)
				getListener().onFinishingThirdTask((Champion) current);

		} else {
			super.moveRight();
		}
	}

	public void onSlytherinTrait(Direction d) throws IOException, InCooldownException, InvalidTargetCellException, OutOfBordersException {

		Wizard current = (Wizard) getCurrentChamp();
		current.setTraitCooldown(10);
		super.onSlytherinTrait(d);

	}

	public Object onRavenclawTrait() {

		Wizard current = (Wizard) getCurrentChamp();

		ArrayList<Direction> result = new ArrayList<Direction>();

		int x = 0;
		int y = 0;
		for (int i = 0; i < getMap().length; i++) {
			for (int j = 0; j < getMap()[i].length; j++) {

				Cell c = getMap()[i][j];

				if (c instanceof CupCell) {

					x = i;
					y = j;
					break;

				}
			}
		}

		int currentX = current.getLocation().x;
		int currentY = current.getLocation().y;

		if (y > currentY)
			result.add(Direction.RIGHT);
		else if (y < currentY)
			result.add(Direction.LEFT);

		if (x > currentX)
			result.add(Direction.BACKWARD);
		else if (x < currentX)
			result.add(Direction.FORWARD);

		setTraitActivated(true);

		current.setTraitCooldown(7);

		return result;

	}
}
