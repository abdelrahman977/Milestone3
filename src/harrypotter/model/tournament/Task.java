package harrypotter.model.tournament;

import harrypotter.exceptions.InvalidActionException;
import harrypotter.exceptions.OutOfBordersException;
import harrypotter.model.character.Champion;
import harrypotter.model.character.HufflepuffWizard;
import harrypotter.model.character.Wizard;
import harrypotter.model.character.WizardListener;
import harrypotter.model.magic.DamagingSpell;
import harrypotter.model.magic.HealingSpell;
import harrypotter.model.magic.Potion;
import harrypotter.model.magic.RelocatingSpell;
import harrypotter.model.magic.Spell;
import harrypotter.model.world.Cell;
import harrypotter.model.world.ChampionCell;
import harrypotter.model.world.CollectibleCell;
import harrypotter.model.world.Direction;
import harrypotter.model.world.EmptyCell;
import harrypotter.model.world.Obstacle;
import harrypotter.model.world.ObstacleCell;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Task implements WizardListener {

	private TaskListener listener;
	private ArrayList<Champion> champions;
	private Cell[][] map;
	private Champion currentChamp;
	private int allowedMoves;
	private boolean traitActivated;
	private ArrayList<Potion> potions;

	public Task(ArrayList<Champion> champions) throws IOException {

		this.champions = champions;
		map = new Cell[10][10];
		potions = new ArrayList<Potion>();
		loadPotions("Potions.csv");
		allowedMoves = 1;
		traitActivated = false;

		for (int i = 0; i < champions.size(); i++) {

			Wizard current = (Wizard) champions.get(i);
			current.setListener(this);
			current.setHp(current.getDefaultHp());
			current.setIp(current.getDefaultIp());
			current.setTraitCooldown(0);

			for (int j = 0; j < current.getSpells().size(); j++) {
				current.getSpells().get(j).setCoolDown(0);
			}

		}

	}

	public abstract void generateMap() throws IOException;

	private void loadPotions(String filePath) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line = br.readLine();

		while (line != null) {

			String[] content = line.split(",");
			potions.add(new Potion(content[0], Integer.parseInt(content[1])));
			line = br.readLine();

		}

		br.close();

	}

	public void initializeAllEmpty() {

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j] = new EmptyCell();
			}
		}

	}

	public void allocateChampions() {

		for (int i = 0; i < champions.size(); i++) {

			Champion champ = champions.get(i);
			if (i == 0) {
				map[9][0] = new ChampionCell(champ);
				((Wizard) champ).setLocation(new Point(9, 0));
			} else if (i == 1) {
				map[9][9] = new ChampionCell(champ);
				((Wizard) champ).setLocation(new Point(9, 9));
			} else if (i == 2) {
				map[0][9] = new ChampionCell(champ);
				((Wizard) champ).setLocation(new Point(0, 9));
			} else {
				map[0][0] = new ChampionCell(champ);
				((Wizard) champ).setLocation(new Point(0, 0));
			}
		}
	}

	public void allocatePotions() {

		int i = 0;

		while (i < 10) {

			int randomX = (int) (Math.random() * 10);
			int randomY = (int) (Math.random() * 10);
			if (map[randomX][randomY] instanceof EmptyCell) {
				int r = (int) (Math.random() * potions.size());
				map[randomX][randomY] = new CollectibleCell(potions.get(r));
				i++;
			}

		}
	}

	public void moveForward() throws IOException,OutOfBordersException {

		Wizard current = (Wizard) currentChamp;
		Point location = current.getLocation();

		getMap()[location.x][location.y] = new EmptyCell();
		Point newLocation = new Point(location.x - 1, location.y);
		current.setLocation(newLocation);

		if (getMap()[newLocation.x][newLocation.y] instanceof CollectibleCell)
			current.getInventory().add(
					((CollectibleCell) getMap()[newLocation.x][newLocation.y])
							.getCollectible());

		getMap()[newLocation.x][newLocation.y] = new ChampionCell(currentChamp);

		finalizeAction();

	}

	public void moveBackward() throws IOException,OutOfBordersException {

		Wizard current = (Wizard) currentChamp;
		Point location = current.getLocation();

		getMap()[location.x][location.y] = new EmptyCell();
		Point newLocation = new Point(location.x + 1, location.y);
		current.setLocation(newLocation);

		if (getMap()[newLocation.x][newLocation.y] instanceof CollectibleCell)
			current.getInventory().add(
					((CollectibleCell) getMap()[newLocation.x][newLocation.y])
							.getCollectible());

		getMap()[newLocation.x][newLocation.y] = new ChampionCell(currentChamp);

		finalizeAction();

	}

	public void moveLeft() throws IOException,OutOfBordersException {

		Wizard current = (Wizard) currentChamp;
		Point location = current.getLocation();

		getMap()[location.x][location.y] = new EmptyCell();
		Point newLocation = new Point(location.x, location.y - 1);
		current.setLocation(newLocation);

		if (getMap()[newLocation.x][newLocation.y] instanceof CollectibleCell)
			current.getInventory().add(
					((CollectibleCell) getMap()[newLocation.x][newLocation.y])
							.getCollectible());

		getMap()[newLocation.x][newLocation.y] = new ChampionCell(currentChamp);

		finalizeAction();

	}

	public void moveRight() throws IOException,OutOfBordersException {

		Wizard current = (Wizard) currentChamp;
		Point location = current.getLocation();

		getMap()[location.x][location.y] = new EmptyCell();
		Point newLocation = new Point(location.x, location.y + 1);
		current.setLocation(newLocation);

		if (getMap()[newLocation.x][newLocation.y] instanceof CollectibleCell)
			current.getInventory().add(
					((CollectibleCell) getMap()[newLocation.x][newLocation.y])
							.getCollectible());

		getMap()[newLocation.x][newLocation.y] = new ChampionCell(currentChamp);

		finalizeAction();

	}

	public Point getTargetPoint(Direction d) throws OutOfBordersException {

		Point target = null;
		Point currentLocation = ((Wizard) currentChamp).getLocation();

		if (d == Direction.FORWARD)
			target = new Point(currentLocation.x - 1, currentLocation.y);
		else if (d == Direction.BACKWARD)
			target = new Point(currentLocation.x + 1, currentLocation.y);
		else if (d == Direction.LEFT)
			target = new Point(currentLocation.x, currentLocation.y - 1);
		else if (d == Direction.RIGHT)
			target = new Point(currentLocation.x, currentLocation.y + 1);

		return target;

	}

	public void castDamagingSpell(DamagingSpell spell, Direction d)
			throws IOException,OutOfBordersException  {

		Point target = getTargetPoint(d);

		if (map[target.x][target.y] instanceof ObstacleCell) {

			Obstacle obstacle = ((ObstacleCell) map[target.x][target.y])
					.getObstacle();
			int newHp = obstacle.getHp() - spell.getDamageAmount();

			if (newHp <= 0) {
				map[target.x][target.y] = new EmptyCell();
			} else {
				obstacle.setHp(newHp);
			}

		} else if (map[target.x][target.y] instanceof ChampionCell) {

			Wizard opponent = (Wizard) (((ChampionCell) map[target.x][target.y])
					.getChamp());
			int newHp = 0;

			if (this instanceof ThirdTask
					&& opponent instanceof HufflepuffWizard) {
				newHp = opponent.getHp() - (spell.getDamageAmount() / 2);
			} else {
				newHp = opponent.getHp() - spell.getDamageAmount();
			}

			if (newHp <= 0) {
				opponent.setHp(0);
				map[target.x][target.y] = new EmptyCell();
				champions.remove((Champion) opponent);
			} else {
				opponent.setHp(newHp);
			}
		}

		useSpell(spell);

		finalizeAction();
	}

	public void castRelocatingSpell(RelocatingSpell spell, Direction d,
			Direction t, int range) throws IOException,OutOfBordersException {

		Point target = getTargetPoint(d);
		int newX = ((Wizard) currentChamp).getLocation().x;
		int newY = ((Wizard) currentChamp).getLocation().y;

		if (t == Direction.FORWARD)
			newX = newX - range;
		else if (t == Direction.BACKWARD)
			newX = newX + range;
		else if (t == Direction.LEFT)
			newY = newY - range;
		else if (t == Direction.RIGHT)
			newY = newY + range;

		if (map[target.x][target.y] instanceof ObstacleCell) {

			Obstacle obstacle = ((ObstacleCell) map[target.x][target.y])
					.getObstacle();
			map[newX][newY] = new ObstacleCell(obstacle);

		} else if (map[target.x][target.y] instanceof ChampionCell) {

			Champion opponent = ((ChampionCell) map[target.x][target.y])
					.getChamp();
			map[newX][newY] = new ChampionCell(opponent);
			((Wizard) opponent).setLocation(new Point(newX, newY));

		}

		map[target.x][target.y] = new EmptyCell();

		useSpell(spell);

		finalizeAction();

	}

	public void castHealingSpell(HealingSpell spell) throws IOException {

		Wizard current = (Wizard) currentChamp;
		int newHp = current.getHp() + spell.getHealingAmount();

		if (newHp > current.getDefaultHp())
			newHp = current.getDefaultHp();

		current.setHp(newHp);

		useSpell(spell);

		finalizeAction();

	}

	public void useSpell(Spell spell) {

		spell.setCoolDown(spell.getDefaultCooldown());

		((Wizard) currentChamp).setIp(((Wizard) currentChamp).getIp()
				- spell.getCost());

		if (((Wizard) currentChamp).getIp() <= 0)
			((Wizard) currentChamp).setIp(0);

	}

	public void finalizeAction() throws IOException {

		allowedMoves--;
		if (allowedMoves == 0)
			endTurn();

	}

	public void endTurn() throws IOException {

		if (champions.contains(currentChamp))
			champions.add(champions.remove(0));

		allowedMoves = 1;

		currentChamp = champions.get(0);

		traitActivated = false;

		coolDown();

	}

	public void coolDown() {

		Wizard current = (Wizard) currentChamp;

		for (int i = 0; i < current.getSpells().size(); i++) {

			Spell currentSpell = current.getSpells().get(i);
			if (currentSpell.getCoolDown() > 0)
				currentSpell.setCoolDown(currentSpell.getCoolDown() - 1);

		}

		if (current.getTraitCooldown() > 0)
			current.setTraitCooldown(current.getTraitCooldown() - 1);

	}

	public void usePotion(Potion p) {

		Wizard current = ((Wizard) currentChamp);
		current.setIp(current.getIp() + p.getAmount());
		current.getInventory().remove(p);

	}

	public void onGryffindorTrait() {

		allowedMoves = 2;
		((Wizard) currentChamp).setTraitCooldown(4);
		traitActivated = true;

	}

	public void onSlytherinTrait(Direction d) throws IOException {

		Wizard current = (Wizard) currentChamp;
		int newX = current.getLocation().x;
		int newY = current.getLocation().y;

		map[newX][newY] = new EmptyCell();
		if (d == Direction.FORWARD)
			newX -= 2;
		else if (d == Direction.BACKWARD)
			newX += 2;
		else if (d == Direction.LEFT)
			newY -= 2;
		else if (d == Direction.RIGHT)
			newY += 2;

		map[newX][newY] = new ChampionCell(currentChamp);
		current.setLocation(new Point(newX, newY));

		traitActivated = true;

		finalizeAction();

	}

	public void onHufflepuffTrait() {

		traitActivated = true;
		((Wizard) currentChamp).setTraitCooldown(3);

	}

	public abstract Object onRavenclawTrait();

	public ArrayList<Champion> getChampions() {
		return champions;
	}

	public Cell[][] getMap() {
		return map;
	}

	public Champion getCurrentChamp() {
		return currentChamp;
	}

	public ArrayList<Potion> getPotions() {
		return potions;
	}

	public int getAllowedMoves() {
		return allowedMoves;
	}

	public void setAllowedMoves(int allowedMoves) {
		this.allowedMoves = allowedMoves;
	}

	public boolean isTraitActivated() {
		return traitActivated;
	}

	public TaskListener getListener() {
		return listener;
	}

	public void setListener(TaskListener listener) {
		this.listener = listener;
	}

	public void setCurrentChamp(Champion currentChamp) {
		this.currentChamp = currentChamp;
	}

	public void setTraitActivated(boolean traitActivated) {
		this.traitActivated = traitActivated;
	}

}
