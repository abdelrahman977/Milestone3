package harrypotter.tests;

import harrypotter.model.character.*;
import harrypotter.model.magic.*;
import harrypotter.model.tournament.*;
import harrypotter.model.world.*;

import java.awt.Point;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.Assert.*;

import org.junit.Test;

public class M2PrivateTest {

	boolean hasCalled = false;
	Champion w;
	SecondTask secondTask;
	ThirdTask thirdTask;
	Direction direction;
	ArrayList<Champion> winnersList;

	@Test(timeout = 1000)
	public void testAddChampionMethodIntournament() throws Exception {
		testMethodExistsInClass(Tournament.class, "addChampion", true,
				Void.TYPE, Champion.class);

		Tournament t = new Tournament();
		RavenclawWizard r = new RavenclawWizard("raven");
		t.addChampion(r);

		assertTrue(
				"Method addChampion in Tournament class is not working correctly",
				t.getChampions().contains(r));

	}

	@Test(timeout = 1000)
	public void testCastDamagingSpellCallsFinalizeAction() throws Exception {
		hasCalled = false;
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);
		FirstTask task1 = new FirstTask(e) {
			public void finalizeAction() {
				hasCalled = true;
			}
		};

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				task1.getMap()[i][j] = new EmptyCell();

		task1.setCurrentChamp(h);
		task1.getMap()[4][3] = new ChampionCell(h);
		PhysicalObstacle p = new PhysicalObstacle(100);
		task1.getMap()[5][3] = new ObstacleCell(p);

		((Wizard) task1.getCurrentChamp()).setLocation(new Point(4, 3));

		DamagingSpell dmg = new DamagingSpell("Sectumsempra", 150, 5, 300);
		h.getSpells().add(dmg);

		task1.castDamagingSpell(dmg, Direction.BACKWARD);

		assertTrue(
				"castDamagingSpell method in FirstTask should call finalizeAction method",
				hasCalled);

		hasCalled = false;
		SecondTask task2 = new SecondTask(e) {
			public void finalizeAction() {
				hasCalled = true;
			}
		};

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				task2.getMap()[i][j] = new EmptyCell();

		task2.setCurrentChamp(h);
		task2.getMap()[4][3] = new ChampionCell(h);
		p = new PhysicalObstacle(100);
		task2.getMap()[5][3] = new ObstacleCell(p);

		((Wizard) task2.getCurrentChamp()).setLocation(new Point(4, 3));

		dmg = new DamagingSpell("Sectumsempra", 150, 5, 300);

		h.getSpells().add(dmg);
		task2.castDamagingSpell(dmg, Direction.BACKWARD);

		assertTrue(
				"castDamagingSpell method in SecondTask should call finalizeAction method",
				hasCalled);

		hasCalled = false;
		ThirdTask task3 = new ThirdTask(e) {
			public void finalizeAction() {
				hasCalled = true;
			}
		};

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				task3.getMap()[i][j] = new EmptyCell();

		task3.setCurrentChamp(h);
		task3.getMap()[4][3] = new ChampionCell(h);
		p = new PhysicalObstacle(100);
		task3.getMap()[5][3] = new ObstacleCell(p);

		((Wizard) task3.getCurrentChamp()).setLocation(new Point(4, 3));

		dmg = new DamagingSpell("Sectumsempra", 150, 5, 300);

		h.getSpells().add(dmg);
		task3.castDamagingSpell(dmg, Direction.BACKWARD);

		assertTrue(
				"castDamagingSpell method in ThirdTask should call finalizeAction method",
				hasCalled);

	}

	@Test(timeout = 1000)
	public void testCastDamagingSpellCallsUseSpell() throws Exception {
		hasCalled = false;
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);
		FirstTask task1 = new FirstTask(e) {
			public void useSpell(Spell spell) {
				hasCalled = true;
			}
		};

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				task1.getMap()[i][j] = new EmptyCell();

		task1.setCurrentChamp(h);
		task1.getMap()[4][3] = new ChampionCell(h);
		PhysicalObstacle p = new PhysicalObstacle(100);
		task1.getMap()[5][3] = new ObstacleCell(p);

		((Wizard) task1.getCurrentChamp()).setLocation(new Point(4, 3));

		DamagingSpell dmg = new DamagingSpell("Sectumsempra", 150, 5, 300);

		task1.castDamagingSpell(dmg, Direction.BACKWARD);

		assertTrue(
				"castDamagingSpell method in FirstTask should call useSpell method",
				hasCalled);

		hasCalled = false;
		SecondTask task2 = new SecondTask(e) {
			public void useSpell(Spell spell) {
				hasCalled = true;
			}
		};

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				task2.getMap()[i][j] = new EmptyCell();

		task2.setCurrentChamp(h);
		task2.getMap()[4][3] = new ChampionCell(h);
		p = new PhysicalObstacle(100);
		task2.getMap()[5][3] = new ObstacleCell(p);

		((Wizard) task2.getCurrentChamp()).setLocation(new Point(4, 3));

		dmg = new DamagingSpell("Sectumsempra", 150, 5, 300);

		task2.castDamagingSpell(dmg, Direction.BACKWARD);

		assertTrue(
				"castDamagingSpell method in SecondTask should call useSpell method",
				hasCalled);

		hasCalled = false;
		ThirdTask task3 = new ThirdTask(e) {
			public void useSpell(Spell spell) {
				hasCalled = true;
			}
		};

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				task3.getMap()[i][j] = new EmptyCell();

		task3.setCurrentChamp(h);
		task3.getMap()[4][3] = new ChampionCell(h);
		p = new PhysicalObstacle(100);
		task3.getMap()[5][3] = new ObstacleCell(p);

		((Wizard) task3.getCurrentChamp()).setLocation(new Point(4, 3));

		dmg = new DamagingSpell("Sectumsempra", 150, 5, 300);

		task3.castDamagingSpell(dmg, Direction.BACKWARD);

		assertTrue(
				"castDamagingSpell method in ThirdTask should call useSpell method",
				hasCalled);

	}

	@Test(timeout = 1000)
	public void testCastDamagingSpellOnChampionCell2() throws Exception {
		testMethodExistsInClass(Task.class, "castDamagingSpell", true,
				Void.TYPE, DamagingSpell.class, Direction.class);

		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);
		FirstTask task = new FirstTask(e);

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				task.getMap()[i][j] = new EmptyCell();

		task.setCurrentChamp(h);
		task.getMap()[4][3] = new ChampionCell(h);

		task.getMap()[5][3] = new ChampionCell(r);

		((Wizard) task.getCurrentChamp()).setLocation(new Point(4, 3));

		r.setLocation(new Point(5, 3));

		DamagingSpell dmg = new DamagingSpell("Sectumsempra", 150, 5, 300);
		r.setHp(200);
		task.castDamagingSpell(dmg, Direction.BACKWARD);

		assertTrue(
				"In firstTask, when the current champion casts a damaging spell on a Champion cell, and the hp of the champion in the cell becomes zero, the champion should be removed from the cell",
				task.getMap()[5][3] instanceof EmptyCell);
		assertFalse(
				"In firstTask, when the current champion casts a damaging spell on a Champion cell, and the hp of the champion in the cell becomes zero, the champion should be removed from the champions list",
				task.getChampions().contains(r));

		SecondTask task2 = new SecondTask(e);

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				task2.getMap()[i][j] = new EmptyCell();

		task2.setCurrentChamp(h);
		task2.getMap()[4][3] = new ChampionCell(h);
		task2.getMap()[5][3] = new ChampionCell(g);
		((Wizard) task2.getCurrentChamp()).setLocation(new Point(4, 3));
		g.setLocation(new Point(5, 3));
		g.setHp(200);

		dmg = new DamagingSpell("Sectumsempra", 150, 5, 300);

		task2.castDamagingSpell(dmg, Direction.BACKWARD);

		assertTrue(
				"In SecondTask, when the current champion casts a damaging spell on a Champion cell, and the hp of the champion in the cell becomes zero, the champion should be removed from the cell",
				task2.getMap()[5][3] instanceof EmptyCell);
		assertFalse(
				"In SecondTask, when the current champion casts a damaging spell on a Champion cell, and the hp of the champion in the cell becomes zero, the champion should be removed from the champions list",
				task2.getChampions().contains(r));

		ThirdTask task3 = new ThirdTask(e);

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				task3.getMap()[i][j] = new EmptyCell();

		task3.setCurrentChamp(h);
		task3.getMap()[4][3] = new ChampionCell(h);
		task3.getMap()[5][3] = new ChampionCell(s);

		((Wizard) task3.getCurrentChamp()).setLocation(new Point(4, 3));

		s.setLocation(new Point(5, 3));
		s.setHp(200);
		dmg = new DamagingSpell("Sectumsempra", 150, 5, 300);

		task3.castDamagingSpell(dmg, Direction.BACKWARD);

		assertTrue(
				"In ThirdTask, when the current champion casts a damaging spell on a Champion cell, and the hp of the champion in the cell becomes zero, the champion should be removed from the cell",
				task3.getMap()[5][3] instanceof EmptyCell);
		assertFalse(
				"In ThirdTask, when the current champion casts a damaging spell on a Champion cell, and the hp of the champion in the cell becomes zero, the champion should be removed from the champions list",
				task3.getChampions().contains(r));

	}

	@Test(timeout = 1000)
	public void testCastDamagingSpellOnObstacleCell2() throws Exception {
		testMethodExistsInClass(Task.class, "castDamagingSpell", true,
				Void.TYPE, DamagingSpell.class, Direction.class);

		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);
		FirstTask task = new FirstTask(e);

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				task.getMap()[i][j] = new EmptyCell();

		task.setCurrentChamp(h);
		task.getMap()[4][3] = new ChampionCell(h);
		PhysicalObstacle p = new PhysicalObstacle(1000);
		task.getMap()[5][3] = new ObstacleCell(p);

		((Wizard) task.getCurrentChamp()).setLocation(new Point(4, 3));

		DamagingSpell dmg = new DamagingSpell("Sectumsempra", 150, 5, 1000);
		task.castDamagingSpell(dmg, Direction.BACKWARD);

		assertTrue(
				"In FirstTask, when the current champion casts a damaging spell on an Obstacle cell, and the hp of the obstacle becomes zero, the obstacle should be removed from the cell",
				task.getMap()[5][3] instanceof EmptyCell);

		SecondTask task2 = new SecondTask(e);

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				task2.getMap()[i][j] = new EmptyCell();

		task2.setCurrentChamp(h);
		task2.getMap()[4][3] = new ChampionCell(h);

		((Wizard) task2.getCurrentChamp()).setLocation(new Point(4, 3));

		Merperson Merperson = new Merperson(10, 1000);
		task2.getMap()[5][3] = new ObstacleCell(Merperson);
		dmg = new DamagingSpell("Sectumsempra", 150, 5, 1000);
		task2.castDamagingSpell(dmg, Direction.BACKWARD);

		assertTrue(
				"In SecondTask, when the current champion casts a damaging spell on a Obstacle cell, and the hp of the obstacle becomes zero, the obstacle should be removed from the cell",
				task2.getMap()[5][3] instanceof EmptyCell);

		ThirdTask task3 = new ThirdTask(e);

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				task3.getMap()[i][j] = new EmptyCell();

		task3.setCurrentChamp(h);
		task3.getMap()[4][3] = new ChampionCell(h);
		p = new PhysicalObstacle(1000);
		task3.getMap()[5][3] = new ObstacleCell(p);

		((Wizard) task3.getCurrentChamp()).setLocation(new Point(4, 3));

		dmg = new DamagingSpell("Sectumsempra", 150, 5, 1000);

		task3.castDamagingSpell(dmg, Direction.BACKWARD);

		assertTrue(
				"In ThirdTask, when the current champion casts a damaging spell on a Obstacle cell, and the hp of the obstacle becomes zero, the obstacle should be removed from the cell",
				task3.getMap()[5][3] instanceof EmptyCell);

	}

	@Test(timeout = 1000)
	public void testCastRelocatingSpellOnChampionCell() throws Exception {
		testMethodExistsInClass(Task.class, "castRelocatingSpell", true,
				Void.TYPE, RelocatingSpell.class, Direction.class,
				Direction.class, int.class);

		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);
		FirstTask task = new FirstTask(e);

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				task.getMap()[i][j] = new EmptyCell();

		task.setCurrentChamp(h);
		task.getMap()[7][8] = new ChampionCell(h);
		task.getMap()[8][8] = new ChampionCell(r);

		((Wizard) task.getCurrentChamp()).setLocation(new Point(7, 8));

		r.setLocation(new Point(8, 8));

		RelocatingSpell rel = new RelocatingSpell("Accio", 100, 1, 1);
		h.getSpells().add(rel);
		task.castRelocatingSpell(rel, Direction.BACKWARD, Direction.FORWARD, 1);

		assertTrue(
				"In FirstTask, when a relocating spell is applied on an champion cell, the target cell should be an champion cell",
				task.getMap()[6][8] instanceof ChampionCell);
		assertTrue(
				"In FirstTask, when a relocating spell is applied on an champion cell, the champion should be moved to the target cell in the correct direction",
				((ChampionCell) task.getMap()[6][8]).getChamp().equals(r));
		assertTrue(
				"In FirstTask, when a relocating spell is applied on an champion cell, the cell should become an Empty cell after the relocation",
				task.getMap()[8][8] instanceof EmptyCell);

		SecondTask task2 = new SecondTask(e);

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				task2.getMap()[i][j] = new EmptyCell();

		task2.setCurrentChamp(h);
		task2.getMap()[2][3] = new ChampionCell(h);
		task2.getMap()[2][2] = new ChampionCell(s);

		((Wizard) task2.getCurrentChamp()).setLocation(new Point(2, 3));

		s.setLocation(new Point(2, 2));

		rel = new RelocatingSpell("Accio", 100, 1, 3);
		h.getSpells().add(rel);
		task2.castRelocatingSpell(rel, Direction.LEFT, Direction.RIGHT, 3);

		assertTrue(
				"In SecondTask, when a relocating spell is applied on an champion cell, the target cell should be an champion cell",
				task2.getMap()[2][6] instanceof ChampionCell);
		assertTrue(
				"In SecondTask, when a relocating spell is applied on an champion cell, the obstacle should be moved to the target cell in the correct direction",
				((ChampionCell) task2.getMap()[2][6]).getChamp().equals(s));
		assertTrue(
				"In SecondTask, when a relocating spell is applied on an champion cell, the cell should become an Empty cell after the relocation",
				task2.getMap()[2][2] instanceof EmptyCell);

		ThirdTask task3 = new ThirdTask(e);

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				task3.getMap()[i][j] = new EmptyCell();

		task3.setCurrentChamp(h);
		task3.getMap()[1][5] = new ChampionCell(h);
		task3.getMap()[0][5] = new ChampionCell(r);

		((Wizard) task3.getCurrentChamp()).setLocation(new Point(1, 5));

		r.setLocation(new Point(0, 5));

		rel = new RelocatingSpell("Accio", 100, 1, 2);
		h.getSpells().add(rel);
		task3.castRelocatingSpell(rel, Direction.FORWARD, Direction.RIGHT, 2);

		assertTrue(
				"In ThirdTask, when a relocating spell is applied on an champion cell, the target cell should be an champion cell",
				task3.getMap()[1][7] instanceof ChampionCell);
		assertTrue(
				"In ThirdTask, when a relocating spell is applied on an champion cell, the obstacle should be moved to the target cell in the correct direction",
				((ChampionCell) task3.getMap()[1][7]).getChamp().equals(r));
		assertTrue(
				"In ThirdTask, when a relocating spell is applied on an champion cell, the cell should become an Empty cell after the relocation",
				task3.getMap()[0][5] instanceof EmptyCell);

	}

	@Test(timeout = 1000)
	public void testChampionsListenerIsSetCorrectly() throws Exception {
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(s);
		e.add(h);
		e.add(r);
		e.add(g);

		FirstTask task1 = new FirstTask(e);
		ArrayList<Champion> championsList = task1.getChampions();

		for (int i = 0; i < championsList.size(); i++) {
			Wizard champ = (Wizard) championsList.get(i);
			if (i == 0) {
				assertEquals(
						"The instance variable listener of the first champion should be set correctly after the intialization of the FirstTask.",
						task1, champ.getListener());
			}

			else if (i == 1) {
				assertEquals(
						"The instance variable listener of the second champion should be set correctly after the intialization of the FirstTask.",
						task1, champ.getListener());

			} else if (i == 2) {
				assertEquals(
						"The instance variable listener of the third champion should be set correctly after the intialization of the FirstTask.",
						task1, champ.getListener());

			} else {
				assertEquals(
						"The instance variable listener of the fourth champion should be set correctly after the intialization of the FirstTask.",
						task1, champ.getListener());

			}
		}

		SecondTask task2 = new SecondTask(e);
		championsList = task2.getChampions();

		for (int i = 0; i < championsList.size(); i++) {
			Wizard champ = (Wizard) championsList.get(i);
			if (i == 0) {
				assertEquals(
						"The instance variable listener of the first champion should be set correctly after the intialization of the SecondTask.",
						task2, champ.getListener());
			}

			else if (i == 1) {
				assertEquals(
						"The instance variable listener of the second champion should be set correctly after the intialization of the SecondTask.",
						task2, champ.getListener());

			} else if (i == 2) {
				assertEquals(
						"The instance variable listener of the third champion should be set correctly after the intialization of the SecondTask.",
						task2, champ.getListener());

			} else {
				assertEquals(
						"The instance variable listener of the fourth champion should be set correctly after the intialization of the SecondTask.",
						task2, champ.getListener());

			}
		}

		ThirdTask task3 = new ThirdTask(e);
		championsList = task3.getChampions();

		for (int i = 0; i < championsList.size(); i++) {
			Wizard champ = (Wizard) championsList.get(i);
			if (i == 0) {
				assertEquals(
						"The instance variable listener of the first champion should be set correctly after the intialization of the ThirdTask.",
						task3, champ.getListener());
			}

			else if (i == 1) {
				assertEquals(
						"The instance variable listener of the second champion should be set correctly after the intialization of the ThirdTask.",
						task3, champ.getListener());

			} else if (i == 2) {
				assertEquals(
						"The instance variable listener of the third champion should be set correctly after the intialization of the ThirdTask.",
						task3, champ.getListener());

			} else {
				assertEquals(
						"The instance variable listener of the fourth champion should be set correctly after the intialization of the ThirdTask.",
						task3, champ.getListener());

			}
		}
	}

	@Test(timeout = 1000)
	public void testCurrentChampionDieByMerPersonScenario() throws Exception {
		testMethodExistsInClass(Task.class, "moveRight", true, Void.TYPE);

		ArrayList<Champion> e = new ArrayList<>();
		SlytherinWizard r = new SlytherinWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		SlytherinWizard g = new SlytherinWizard("gryff");
		e.add(r);
		e.add(s);
		e.add(g);

		SecondTask task = null;
		int x, y;
		Point location = null;
		task = new SecondTask(e);
		location = new Point(5, 5);

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				if (!(task.getMap()[i][j] instanceof ChampionCell))
					task.getMap()[i][j] = new EmptyCell();

		x = location.x;
		y = location.y;

		task.getMap()[9][0] = new EmptyCell();
		task.setCurrentChamp(task.getChampions().get(0));
		Wizard currentChamp = (Wizard) task.getCurrentChamp();
		task.getMap()[x - 1][y] = new ObstacleCell(new Merperson(100, 300));
		task.getMap()[x - 1][y - 1] = new ObstacleCell(new Merperson(100, 300));
		task.getMap()[x + 1][y - 1] = new ObstacleCell(new Merperson(100, 300));
		task.getMap()[x][y - 2] = new ChampionCell((Champion) currentChamp);

		currentChamp.setLocation(new Point(x, y - 2));

		int hpOld = currentChamp.getHp();
		task.moveRight();
		assertEquals(
				"The damage inflicted to the current champion is incorrect",
				600, hpOld - currentChamp.getHp());
		task.setCurrentChamp((Champion) currentChamp);
		hpOld = currentChamp.getHp();
		task.moveRight();

		assertEquals(
				"The damage inflicted to the current champion is incorrect",
				250, hpOld - currentChamp.getHp());

		assertFalse(
				"The arraylist of champions should not contain the current champion after he dies",
				task.getChampions().contains(currentChamp));

		assertEquals(
				"The size of the arraylist of champions should be equal to the alive champions only",
				2, task.getChampions().size());

		assertTrue("The champion location should be an empty cell",
				task.getMap()[5][5] instanceof EmptyCell);

	}

	@Test(timeout = 1000)
	public void testDragonFireBothChampDies() throws Exception {

		ArrayList<Champion> e = new ArrayList<>();
		SlytherinWizard g = new SlytherinWizard("gryff");
		SlytherinWizard h = new SlytherinWizard("huff");
		SlytherinWizard r = new SlytherinWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);

		FirstTask task = new FirstTask(e);
		int expectedDmg = 150;

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				if (!(task.getMap()[i][j] instanceof ChampionCell))
					task.getMap()[i][j] = new EmptyCell();

		Wizard currentChamp = (Wizard) task.getCurrentChamp();
		Wizard otherChamp = (Wizard) task.getChampions().get(1);

		task.getMap()[currentChamp.getLocation().x][currentChamp.getLocation().y] = new EmptyCell();
		task.getMap()[2][2] = new ChampionCell((Champion) currentChamp);
		currentChamp.setLocation((Point) new Point(2, 2));

		int i, j = 0;
		i = currentChamp.getLocation().x;
		j = currentChamp.getLocation().y;
		Point currentLocation = currentChamp.getLocation();
		Point otherLocation = new Point(0, 0);

		for (int c = 0; c < 6; c++) {
			task.setCurrentChamp((Champion) currentChamp);
			task.getMarkedCells().clear();
			task.getMarkedCells().add(new Point(i - 1, j));
			task.getMarkedCells().add(new Point(i, j + 1));

			if (currentChamp.getHp() < 150)
				expectedDmg = currentChamp.getHp();

			int hpOld = currentChamp.getHp();
			otherLocation = new Point(i - 1, j);

			task.getMap()[otherChamp.getLocation().x][otherChamp.getLocation().y] = new EmptyCell();
			task.getMap()[otherLocation.x][otherLocation.y] = new ChampionCell(
					(Champion) otherChamp);
			otherChamp.setLocation((Point) otherLocation.clone());
			task.moveRight();

			currentLocation = currentChamp.getLocation();

			assertEquals(
					"The damage inflicted on the champion by the dragon fire is incorrect",
					expectedDmg, hpOld - currentChamp.getHp());

			assertEquals(
					"The damage inflicted on the champion by the dragon fire is incorrect",
					expectedDmg, hpOld - otherChamp.getHp());

			i = currentChamp.getLocation().x;
			j = currentChamp.getLocation().y;
		}

		assertEquals("Hp of the current champion should have reached zero", 0,
				currentChamp.getHp());

		assertFalse(
				"The champion arraylist should not contain the dead champions",
				task.getChampions().contains(currentChamp));

		assertTrue(
				"The champion location should be an empty cell",
				task.getMap()[currentLocation.x][currentLocation.y] instanceof EmptyCell);

		assertEquals("Hp of the other champion should have reached zero", 0,
				otherChamp.getHp());

		assertFalse(
				"The champion arraylist should not contain the dead champions",
				task.getChampions().contains(otherChamp));

		assertTrue(
				"The champion location should be an empty cell",
				task.getMap()[otherLocation.x][otherLocation.y] instanceof EmptyCell);

		assertEquals(
				"The size of the arraylist of champions should be equal to the alive champions only",
				2, task.getChampions().size());
	}

	@Test(timeout = 1000)
	public void testDragonFireCurrentChampDies() throws Exception {
		ArrayList<Champion> e = new ArrayList<>();
		SlytherinWizard g = new SlytherinWizard("gryff");
		SlytherinWizard h = new SlytherinWizard("huff");
		SlytherinWizard r = new SlytherinWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);
		FirstTask task = new FirstTask(e);
		int expectedDmg = 150;

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				if (!(task.getMap()[i][j] instanceof ChampionCell))
					task.getMap()[i][j] = new EmptyCell();

		Wizard currentChamp = (Wizard) task.getCurrentChamp();

		int i, j = 0;
		i = currentChamp.getLocation().x;
		j = currentChamp.getLocation().y;
		Point currentLocation = new Point(0, 0);
		for (int c = 0; c < 6; c++) {
			int[] pointExist = new int[5];
			task.setCurrentChamp((Champion) currentChamp);

			task.getMarkedCells().clear();
			task.markCells();

			assertEquals("Number of cells in marked cells is incorrect", 2,
					task.getMarkedCells().size());

			int[] Directions = new int[4];
			for (int k = 0; k < 2; k++) {
				int x, y = 0;
				x = (int) task.getMarkedCells().get(k).getX();
				y = (int) task.getMarkedCells().get(k).getY();
				if ((x == i && y == j)) {
					pointExist[0] = 1;
				} else if ((x == (i + 1) && y == j) && i != 9) {
					pointExist[1] = 1;
				} else if ((x == (i - 1) && y == j) && i != 0) {
					pointExist[2] = 1;
				} else if ((x == i && y == (j - 1)) && j != 0) {
					pointExist[3] = 1;
				} else if ((x == i && y == (j + 1)) && j != 9) {
					pointExist[4] = 1;
				}
			}
			for (int k = 0; k < 4; k++)
				Directions[k] = pointExist[k + 1];
			if (currentChamp.getHp() < 150)
				expectedDmg = currentChamp.getHp();
			assertEquals(
					"One or both of the marked cells is in an incorrect location",
					2, sumArray(pointExist));

			int hpOld = currentChamp.getHp();
			if (Directions[0] == 1) {
				task.moveBackward();
				currentLocation = new Point(i + 1, j);
			} else if (Directions[1] == 1) {
				task.moveForward();
				currentLocation = new Point(i - 1, j);

			} else if (Directions[2] == 1) {
				task.moveLeft();
				currentLocation = new Point(i, j - 1);

			} else {
				task.moveRight();
				currentLocation = new Point(i, j + 1);

			}
			assertEquals(
					"The damage inflicted on the champion by the dragon fire is incorrect",
					expectedDmg, hpOld - currentChamp.getHp());
			i = currentChamp.getLocation().x;
			j = currentChamp.getLocation().y;
		}
		assertEquals("Hp of the champion should have reached zero", 0,
				currentChamp.getHp());

		assertFalse(
				"The champion arraylist should not contain the dead champion",
				task.getChampions().contains(currentChamp));

		assertEquals(
				"The size of the arraylist of champions should be equal to the number of alive champions only",
				3, task.getChampions().size());

		assertTrue(
				"The champion location should be an empty cell",
				task.getMap()[currentLocation.x][currentLocation.y] instanceof EmptyCell);

	}

	@Test(timeout = 1000)
	public void testDragonFireDamageOtherChamp() throws Exception {
		testMethodExistsInClass(FirstTask.class, "fire", true, Void.TYPE);
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		SlytherinWizard r = new SlytherinWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");

		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);
		FirstTask task = new FirstTask(e);
		int expectedDmg = 150;

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				task.getMap()[i][j] = new EmptyCell();

		for (int c = 0; c < 6; c++) {
			int[] pointExist = new int[5];
			task.setCurrentChamp(s);
			int i, j = 0;
			i = (int) (3 + (Math.random() * 7));
			j = (int) (3 + (Math.random() * 7));
			switch (c) {
			case 0:
				i = 0;
				j = 0;
				break;
			case 1:
				i = 9;
				break;
			case 2:
				j = 0;
				break;
			case 3:
				j = 9;
				i = 9;
				break;
			}
			task.getMap()[i][j] = new ChampionCell(s);
			Wizard currentChamp = ((Wizard) task.getCurrentChamp());
			currentChamp.setLocation(new Point(i, j));
			task.getMarkedCells().clear();
			task.markCells();
			task.getMarkedCells().trimToSize();
			assertEquals("Number of cells in marked cells is incorrect", 2,
					task.getMarkedCells().size());
			for (int k = 0; k < 2; k++) {
				int x, y = 0;
				x = (int) task.getMarkedCells().get(k).getX();
				y = (int) task.getMarkedCells().get(k).getY();
				if ((x == i && y == j)) {
					pointExist[0] = 1;
				} else if ((x == (i + 1) && y == j) && i != 9) {
					pointExist[1] = 1;
				} else if ((x == (i - 1) && y == j) && i != 0) {
					pointExist[2] = 1;
				} else if ((x == i && y == (j - 1)) && j != 0) {
					pointExist[3] = 1;
				} else if ((x == i && y == (j + 1)) && j != 9) {
					pointExist[4] = 1;
				}
			}
			if (currentChamp.getHp() < 150)
				expectedDmg = currentChamp.getHp();
			assertTrue(
					"One or both of the marked cells is in an incorrect location",
					sumArray(pointExist) == 2);
			currentChamp.setLocation((Point) task.getMarkedCells().get(c / 3)
					.clone());
			r.setLocation((Point) task.getMarkedCells().get((5 - c) / 3)
					.clone());
			task.getMap()[i][j] = new EmptyCell();

			int champX = (int) task.getMarkedCells().get(c / 3).getX();
			int champY = (int) task.getMarkedCells().get(c / 3).getY();

			task.getMap()[champX][champY] = new ChampionCell(s);
			task.getMap()[(int) task.getMarkedCells().get((5 - c) / 3).getX()][(int) task
					.getMarkedCells().get((5 - c) / 3).getY()] = new ChampionCell(
					r);
			int hpOld = currentChamp.getHp();

			@SuppressWarnings("unchecked")
			ArrayList<Point> copyMarkedCells = (ArrayList<Point>) task
					.getMarkedCells().clone();
			task.fire();

			assertEquals(
					"The damage inflicted on the current champion by the dragon fire is incorrect",
					(hpOld - expectedDmg), currentChamp.getHp());
			assertEquals(
					"The damage inflicted on the other champion by the dragon fire is incorrect",
					(hpOld - expectedDmg), r.getHp());
			task.getMap()[champX][champY] = new EmptyCell();
			task.getMap()[(int) copyMarkedCells.get((5 - c) / 3).getX()][(int) copyMarkedCells
					.get((5 - c) / 3).getY()] = new EmptyCell();

		}
		assertEquals("Hp of the current champion should have reached zero", 0,
				((Wizard) task.getCurrentChamp()).getHp());

		assertEquals("Hp of the other champion should have reached zero", 0,
				r.getHp());

	}

	@Test(timeout = 1000)
	public void testDragonFireOnChampionInEggCell() throws Exception {
		testMethodExistsInClass(Task.class, "moveRight", true, Void.TYPE);

		while (true) {
			boolean done = false;
			ArrayList<Champion> e = new ArrayList<>();
			GryffindorWizard g = new GryffindorWizard("gryff");
			HufflepuffWizard h = new HufflepuffWizard("huff");
			SlytherinWizard r = new SlytherinWizard("raven");
			SlytherinWizard s = new SlytherinWizard("slyth");

			e.add(g);
			e.add(h);
			e.add(r);
			e.add(s);
			FirstTask task = new FirstTask(e);
			int expectedDmg = 150;

			task.getMap()[4][2] = new EmptyCell();
			task.getMap()[4][3] = new EmptyCell();

			task.setCurrentChamp(s);
			Wizard currentChamp = ((Wizard) task.getCurrentChamp());
			task.getMap()[(int) currentChamp.getLocation().getX()][(int) currentChamp
					.getLocation().getY()] = new EmptyCell();
			task.getMap()[4][2] = new ChampionCell(s);
			currentChamp.setLocation(new Point(4, 2));

			for (int c = 0; c < 2; c++) {
				task.setCurrentChamp(s);
				currentChamp = ((Wizard) task.getCurrentChamp());
				task.getMarkedCells().clear();
				task.markCells();
				assertEquals("Number of cells in marked cells is incorrect", 2,
						task.getMarkedCells().size());
				int[] pointExist = new int[5];
				int i = 4;
				int j = 2 + c;
				boolean marked = false;
				for (int k = 0; k < 2; k++) {
					int x, y = 0;
					x = (int) task.getMarkedCells().get(k).getX();
					y = (int) task.getMarkedCells().get(k).getY();
					if ((x == i && y == j)) {
						pointExist[0] = 1;
					} else if ((x == (i + 1) && y == j) && i != 9) {
						pointExist[1] = 1;
					} else if ((x == (i - 1) && y == j) && i != 0) {
						pointExist[2] = 1;
					} else if ((x == i && y == (j - 1)) && j != 0) {
						pointExist[3] = 1;
					} else if ((x == i && y == (j + 1)) && j != 9) {
						marked = true;
						pointExist[4] = 1;
					}
				}
				assertTrue(
						"One or both of the marked cells is in an incorrect location",
						sumArray(pointExist) == 2);
				if (marked) {
					int hpOld = currentChamp.getHp();
					task.moveRight();

					if (j == 2)
						assertEquals(
								"The damage inflicted on the current champion by the dragon fire is incorrect",
								expectedDmg, (hpOld - currentChamp.getHp()));
					else {
						assertEquals(
								"The damage inflicted on the current champion by the dragon fire is incorrect, the champion should win before receving any damage from the dragon",
								0, (hpOld - currentChamp.getHp()));
						assertTrue("Champion should be added to winners list",
								task.getWinners().contains(s));
						done = true;
					}

				} else
					c = 2;
			}
			if (done)
				break;
		}
	}

	@Test(timeout = 1000)
	public void testDragonFireOtherChampDies() throws Exception {

		ArrayList<Champion> e = new ArrayList<>();
		SlytherinWizard g = new SlytherinWizard("gryff");
		SlytherinWizard h = new SlytherinWizard("huff");
		SlytherinWizard r = new SlytherinWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);
		FirstTask task = new FirstTask(e);
		int expectedDmg = 150;
		int expectedDmgOther = 150;

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				if (!(task.getMap()[i][j] instanceof ChampionCell))
					task.getMap()[i][j] = new EmptyCell();

		Wizard currentChamp = (Wizard) task.getCurrentChamp();
		Wizard otherChamp = (Wizard) task.getChampions().get(2);

		otherChamp.setHp(250);
		task.getMap()[currentChamp.getLocation().x][currentChamp.getLocation().y] = new EmptyCell();
		task.getMap()[2][2] = new ChampionCell((Champion) currentChamp);
		currentChamp.setLocation((Point) new Point(2, 2));

		int i, j = 0;
		i = currentChamp.getLocation().x;
		j = currentChamp.getLocation().y;
		@SuppressWarnings("unused")
		Point currentLocation = currentChamp.getLocation();
		Point otherLocation = new Point(0, 0);

		for (int c = 0; c < 2; c++) {
			task.setCurrentChamp((Champion) currentChamp);
			task.getMarkedCells().clear();
			task.getMarkedCells().add(new Point(i - 1, j));
			task.getMarkedCells().add(new Point(i, j + 1));

			if (currentChamp.getHp() < 150)
				expectedDmg = currentChamp.getHp();
			if (otherChamp.getHp() < 150)
				expectedDmgOther = otherChamp.getHp();

			int hpOld = currentChamp.getHp();
			int hpOldOther = otherChamp.getHp();
			otherLocation = new Point(i - 1, j);

			task.getMap()[otherChamp.getLocation().x][otherChamp.getLocation().y] = new EmptyCell();
			task.getMap()[otherLocation.x][otherLocation.y] = new ChampionCell(
					(Champion) otherChamp);
			otherChamp.setLocation((Point) otherLocation.clone());
			task.moveRight();

			currentLocation = currentChamp.getLocation();

			assertEquals(
					"The damage inflicted on the champion by the dragon fire is incorrect",
					expectedDmg, hpOld - currentChamp.getHp());

			assertEquals(
					"The damage inflicted on the champion by the dragon fire is incorrect",
					expectedDmgOther, hpOldOther - otherChamp.getHp());
			i = currentChamp.getLocation().x;
			j = currentChamp.getLocation().y;
		}

		assertEquals("Hp of the other champion should have reached zero", 0,
				otherChamp.getHp());

		assertFalse(
				"The champion arraylist should not contain the dead champions",
				task.getChampions().contains(otherChamp));

		assertTrue(
				"The champion location should be an empty cell",
				task.getMap()[otherLocation.x][otherLocation.y] instanceof EmptyCell);

		assertEquals(
				"The size of the arraylist of champions should be equal to the alive champions only",
				3, task.getChampions().size());

	}

	@Test(timeout = 1000)
	public void testEncounterFourMerPeople() throws Exception {

		testMethodExistsInClass(SecondTask.class, "encounterMerPerson", true,
				Void.TYPE);
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		int merPersons = 4;

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				ArrayList<Champion> e = new ArrayList<>();
				SlytherinWizard s = new SlytherinWizard("slyth");
				e.add(g);
				e.add(h);
				e.add(r);
				e.add(s);
				SecondTask task = new SecondTask(e);

				for (int x = 0; x < 10; x++)
					for (int y = 0; y < 10; y++)
						task.getMap()[x][y] = new EmptyCell();

				ArrayList<Point> possibleLocations = new ArrayList<Point>();
				if (i + 1 <= 9)
					possibleLocations.add(new Point(i + 1, j));
				if (i - 1 >= 0)
					possibleLocations.add(new Point(i - 1, j));
				if (j - 1 >= 0)
					possibleLocations.add(new Point(i, j - 1));
				if (j + 1 <= 9)
					possibleLocations.add(new Point(i, j + 1));

				task.getMap()[i][j] = new ChampionCell(s);
				task.setCurrentChamp(s);
				s.setLocation(new Point(i, j));
				int accumelatedDamage = 0;
				int hpOld = s.getHp();
				for (int k = 0; k < possibleLocations.size() && k < merPersons; k++) {
					int x = (int) possibleLocations.get(k).getX();
					int y = (int) possibleLocations.get(k).getY();
					int damage = 100 + (int) (Math.random() * 51);
					task.getMap()[x][y] = new ObstacleCell(new Merperson(100,
							damage));
					accumelatedDamage += damage;
				}
				task.encounterMerPerson();
				assertEquals(
						"Inflicted merperson damage on the champion is incorrect",
						accumelatedDamage, (hpOld - s.getHp()));
			}
		}
	}

	@Test(timeout = 1000)
	public void testEncounterMerPeopleNotInRangeFar() throws Exception {

		testMethodExistsInClass(SecondTask.class, "encounterMerPerson", true,
				Void.TYPE);
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		ArrayList<Champion> e = new ArrayList<>();
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);
		SecondTask task = new SecondTask(e);

		for (int x = 0; x < 10; x++)
			for (int y = 0; y < 10; y++)
				task.getMap()[x][y] = new EmptyCell();

		int minDistance = 2;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {

				ArrayList<Point> possibleLocations = new ArrayList<Point>();
				for (int x = -2; x < 3; x += 2)
					for (int y = -2; y < 3; y += 2)
						if (!(x == 0 && y == 0))
							possibleLocations.add(new Point(i + x, j + y));

				possibleLocations.add(new Point(i + minDistance
						+ (int) (Math.random() * 3), j));

				possibleLocations.add(new Point(i + minDistance
						+ (int) (Math.random() * 3), j + minDistance
						+ (int) (Math.random() * 3)));

				possibleLocations.add(new Point(i, j + minDistance
						+ (int) (Math.random() * 3)));

				possibleLocations.add(new Point(i - minDistance
						- (int) (Math.random() * 3), j));

				possibleLocations.add(new Point(i - minDistance
						- (int) (Math.random() * 3), j - minDistance
						- (int) (Math.random() * 3)));

				possibleLocations.add(new Point(i, j - minDistance
						- (int) (Math.random() * 3)));

				task.getMap()[i][j] = new ChampionCell(s);
				task.setCurrentChamp(s);
				s.setLocation(new Point(i, j));
				int hpOld = s.getHp();

				for (int k = 0; k < possibleLocations.size(); k++) {
					int x = (int) possibleLocations.get(k).getX();
					int y = (int) possibleLocations.get(k).getY();
					if (x >= 0 && x <= 9 && y >= 0 && y <= 9) {
						int damage = 100 + (int) (Math.random() * 51);
						task.getMap()[x][y] = new ObstacleCell(new Merperson(
								100, damage));

						task.encounterMerPerson();

					}
				}

				assertEquals(
						"The champion should not receive any damage from MerPeople not in the correct damage range",
						0, (hpOld - s.getHp()));

				task.getMap()[i][j] = new EmptyCell();

				for (int k = 0; k < possibleLocations.size(); k++) {
					int x = (int) possibleLocations.get(k).getX();
					int y = (int) possibleLocations.get(k).getY();
					if (x >= 0 && x <= 9 && y >= 0 && y <= 9) {
						task.getMap()[x][y] = new EmptyCell();

					}
				}

			}
		}
	}

	@Test(timeout = 1000)
	public void testEncounterThreeMerPeople() throws Exception {
		testMethodExistsInClass(SecondTask.class, "encounterMerPerson", true,
				Void.TYPE);
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		int merPersons = 3;

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				ArrayList<Champion> e = new ArrayList<>();
				SlytherinWizard s = new SlytherinWizard("slyth");
				e.add(g);
				e.add(h);
				e.add(r);
				e.add(s);
				SecondTask task = new SecondTask(e);

				for (int x = 0; x < 10; x++)
					for (int y = 0; y < 10; y++)
						task.getMap()[x][y] = new EmptyCell();

				ArrayList<Point> possibleLocations = new ArrayList<Point>();
				if (i + 1 <= 9)
					possibleLocations.add(new Point(i + 1, j));
				if (i - 1 >= 0)
					possibleLocations.add(new Point(i - 1, j));
				if (j - 1 >= 0)
					possibleLocations.add(new Point(i, j - 1));
				if (j + 1 <= 9)
					possibleLocations.add(new Point(i, j + 1));

				task.getMap()[i][j] = new ChampionCell(s);
				task.setCurrentChamp(s);
				s.setLocation(new Point(i, j));
				int accumelatedDamage = 0;
				int hpOld = s.getHp();
				for (int k = 0; k < possibleLocations.size() && k < merPersons; k++) {
					int x = (int) possibleLocations.get(k).getX();
					int y = (int) possibleLocations.get(k).getY();
					int damage = 100 + (int) (Math.random() * 51);
					task.getMap()[x][y] = new ObstacleCell(new Merperson(100,
							damage));
					accumelatedDamage += damage;
				}
				task.encounterMerPerson();
				assertEquals(
						"Inflicted merperson damage on the champion is incorrect",
						accumelatedDamage, (hpOld - s.getHp()));
			}
		}
	}

	@Test(timeout = 1000)
	public void testFireEffectOnOtherCells() throws Exception {

		testMethodExistsInClass(FirstTask.class, "markCells", true, Void.TYPE);
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);
		FirstTask task = new FirstTask(e);

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				task.getMap()[i][j] = new EmptyCell();
		for (int c = 0; c < 20; c++) {
			for (int i = 1; i < 9; i++) {
				for (int j = 1; j < 9; j++) {
					int random = (int) (Math.random() * 10);
					r.setHp(r.getDefaultHp());
					task.getMap()[i - 1][j] = new ObstacleCell(
							new PhysicalObstacle(250 + random));
					task.getMap()[i + 1][j] = new CollectibleCell(new Potion(
							"Senzo", 250 + random));
					task.getMap()[i][j - 1] = new ObstacleCell(
							new PhysicalObstacle(250 + random));
					task.getMap()[i][j + 1] = new ChampionCell(r);

					r.setLocation(new Point(i, j + 1));

					int[] pointExist = new int[5];
					task.setCurrentChamp(h);
					task.getMap()[i][j] = new ChampionCell(h);
					((Wizard) task.getCurrentChamp()).setLocation(new Point(i,
							j));
					task.getMarkedCells().clear();

					task.markCells();

					assertEquals(
							"Number of cells in marked cells is incorrect", 2,
							task.getMarkedCells().size());

					for (int k = 0; k < 2; k++) {
						double x, y = 0;
						x = task.getMarkedCells().get(k).getX();
						y = task.getMarkedCells().get(k).getY();
						if ((x == i && y == j)) {
							pointExist[0] = 1;
						} else if ((x == (i + 1) && y == j) && i != 9) {
							pointExist[1] = 1;
						} else if ((x == (i - 1) && y == j) && i != 0) {
							pointExist[2] = 1;
						} else if ((x == i && y == (j - 1)) && j != 0) {
							pointExist[3] = 1;
						} else if ((x == i && y == (j + 1)) && j != 9) {
							pointExist[4] = 1;
						}
					}
					assertTrue(
							"One or both of the marked cells is in an incorrect location",
							sumArray(pointExist) == 2);
					task.fire();
					assertTrue(
							"The type of the obstacle cell should not be affected by the fire of the dragon",
							task.getMap()[i - 1][j] instanceof ObstacleCell);
					assertTrue(
							"The type of the collectible cell should not be affected by the fire of the dragon",
							task.getMap()[i + 1][j] instanceof CollectibleCell);
					assertTrue(
							"The type of the obstacle cell should not be affected by the fire of the dragon",
							task.getMap()[i][j - 1] instanceof ObstacleCell);
					assertTrue(
							"The type of the champion cell should not be affected by the fire of the dragon",
							task.getMap()[i][j + 1] instanceof ChampionCell);
					assertTrue(
							"The obstacle types should not be affected by the fire of the dragon",
							((ObstacleCell) task.getMap()[i - 1][j])
									.getObstacle() instanceof PhysicalObstacle
									&& ((ObstacleCell) task.getMap()[i][j - 1])
											.getObstacle() instanceof PhysicalObstacle);

					assertEquals(
							"The hp of the physical obstacles should not be affected by the dragon fire",
							250 + random,
							((PhysicalObstacle) ((ObstacleCell) task.getMap()[i - 1][j])
									.getObstacle()).getHp());

					assertEquals(
							"The hp of the physical obstacles should not be affected by the dragon fire",
							250 + random,
							((PhysicalObstacle) ((ObstacleCell) task.getMap()[i][j - 1])
									.getObstacle()).getHp());

					assertEquals(
							"The name of the potion should not be affected by the dragon fire",
							"Senzo",
							((Potion) ((CollectibleCell) task.getMap()[i + 1][j])
									.getCollectible()).getName());

					assertEquals(
							"The amount of the potion should not be affected by the dragon fire",
							250 + random, ((Potion) ((CollectibleCell) task
									.getMap()[i + 1][j]).getCollectible())
									.getAmount());

					task.getMap()[i][j] = new EmptyCell();
					task.getMap()[i - 1][j] = new EmptyCell();
					task.getMap()[i + 1][j] = new EmptyCell();
					task.getMap()[i][j - 1] = new EmptyCell();
					task.getMap()[i][j + 1] = new EmptyCell();
				}
			}
		}

	}

	@Test(timeout = 100)
	public void testFireMethodEffectOnUnMarkedCells1() throws Exception {
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);
		FirstTask task1 = new FirstTask(e);
		task1.getChampions().clear();
		task1.getChampions().add(g);
		task1.getChampions().add(h);
		task1.getChampions().add(r);
		task1.getChampions().add(s);
		Field map = Task.class.getDeclaredField("map");
		map.setAccessible(true);
		map.set(task1, new Cell[10][10]);
		Cell[][] taskMap = task1.getMap();
		for (int i = 0; i < taskMap.length; i++) {
			for (int j = 0; j < taskMap[i].length; j++) {
				taskMap[i][j] = new EmptyCell();
			}
		}

		int champPosX = (int) (8 * Math.random()) + 1;
		int champPosY = (int) (8 * Math.random()) + 1;
		task1.getMap()[champPosX][champPosY] = new ChampionCell(g);
		g.setLocation(new Point(champPosX, champPosY));
		task1.setCurrentChamp(g);

		task1.getMap()[champPosX][champPosY - 1] = new ChampionCell(h);
		h.setLocation(new Point(champPosX, champPosY - 1));

		task1.getMarkedCells().clear();
		ArrayList<Point> newMarkedCells = new ArrayList<Point>();
		newMarkedCells.add(new Point(champPosX + 1, champPosY));
		newMarkedCells.add(new Point(champPosX, champPosY + 1));
		Field markedCells = FirstTask.class.getDeclaredField("markedCells");
		markedCells.setAccessible(true);
		markedCells.set(task1, newMarkedCells);

		int currentChampHpBefore = ((Wizard) task1.getCurrentChamp()).getHp();
		int anotherChampHpBefore = ((Wizard) h).getHp();

		task1.moveForward();
		int currentChampHpAfter = ((Wizard) g).getHp();
		int anotherChampHpAfter = ((Wizard) h).getHp();

		assertEquals(
				"The player moved to one of the cells that was not fired by the dragon, however, his hp was affected",
				currentChampHpBefore, currentChampHpAfter);
		assertEquals(
				"The player is on one of the cells that was not fired by the dragon, however, his hp was affected",
				anotherChampHpBefore, anotherChampHpAfter);
	}

	@Test(timeout = 100)
	public void testFireMethodEffectOnUnMarkedCells2() throws Exception {
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);
		FirstTask task1 = new FirstTask(e);
		task1.getChampions().clear();
		task1.getChampions().add(g);
		task1.getChampions().add(h);
		task1.getChampions().add(r);
		task1.getChampions().add(s);
		Field map = Task.class.getDeclaredField("map");
		map.setAccessible(true);
		map.set(task1, new Cell[10][10]);
		Cell[][] taskMap = task1.getMap();
		for (int i = 0; i < taskMap.length; i++) {
			for (int j = 0; j < taskMap[i].length; j++) {
				taskMap[i][j] = new EmptyCell();
			}
		}

		int champPosX = (int) (8 * Math.random()) + 1;
		int champPosY = (int) (8 * Math.random()) + 1;
		task1.getMap()[champPosX][champPosY] = new ChampionCell(g);
		g.setLocation(new Point(champPosX, champPosY));
		task1.setCurrentChamp(g);

		task1.getMarkedCells().clear();
		ArrayList<Point> newMarkedCells = new ArrayList<Point>();
		newMarkedCells.add(new Point(champPosX + 1, champPosY));
		newMarkedCells.add(new Point(champPosX, champPosY + 1));
		Field markedCells = FirstTask.class.getDeclaredField("markedCells");
		markedCells.setAccessible(true);
		markedCells.set(task1, newMarkedCells);

		int currentChampHpBefore = ((Wizard) task1.getCurrentChamp()).getHp();
		task1.castHealingSpell(new HealingSpell("Cheering Charm", 50, 100, 2));
		int currentChampHpAfter = ((Wizard) g).getHp();
		assertEquals(
				"The player casted a spell on his location from one of the cells that was not fired by the dragon, however, his hp was affected",
				currentChampHpBefore, currentChampHpAfter);
	}

	@Test(timeout = 1000)
	public void testFirstTaskClassNotifiesListenerWhenThreeChampionsWin()
			throws Exception {
		testMethodExistsInClass(Tournament.class, "onFinishingFirstTask", true,
				Void.TYPE, ArrayList.class);
		hasCalled = false;
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(s);
		e.add(h);
		e.add(r);
		e.add(g);
		FirstTask task = new FirstTask(e);

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				task.getMap()[i][j] = new EmptyCell();
		Tournament t = new Tournament() {
			public void onFinishingFirstTask(ArrayList<Champion> winners)
					throws IOException {
				hasCalled = true;
				super.onFinishingFirstTask(winners);
				secondTask = getSecondTask();

				winnersList = winners;
			}
		};

		task.setCurrentChamp(s);
		s.setHp(150);
		task.getMap()[3][1] = new ChampionCell(s);

		((Wizard) task.getCurrentChamp()).setLocation(new Point(3, 1));
		task.getMarkedCells().clear();
		task.getMarkedCells().add(new Point(3, 2));
		task.getMarkedCells().add(new Point(7, 8));

		task.getMap()[3][4] = new ChampionCell(h);
		h.setLocation(new Point(3, 4));

		task.getMap()[4][5] = new ChampionCell(r);
		r.setLocation(new Point(4, 5));

		task.getMap()[4][3] = new ChampionCell(g);
		g.setLocation(new Point(4, 3));

		task.setListener(t);

		task.moveRight();
		assertFalse(
				"ONLY upon the completion of the first task, the FirstTask notifies the Tournament class",
				hasCalled);

		task.getMarkedCells().clear();
		task.getMarkedCells().add(new Point(3, 2));
		task.getMarkedCells().add(new Point(7, 8));
		task.setCurrentChamp(h);
		task.moveBackward();
		assertFalse(
				"ONLY upon the completion of the first task, the FirstTask notifies the Tournament class",
				hasCalled);

		task.getMarkedCells().clear();
		task.getMarkedCells().add(new Point(3, 2));
		task.getMarkedCells().add(new Point(7, 8));
		task.setCurrentChamp(r);
		task.moveLeft();
		assertFalse(
				"ONLY upon the completion of the first task, the FirstTask notifies the Tournament class",
				hasCalled);

		task.getMarkedCells().clear();
		task.getMarkedCells().add(new Point(3, 2));
		task.getMarkedCells().add(new Point(7, 8));
		task.setCurrentChamp(g);
		task.moveRight();

		assertTrue(
				"Upon completion of the first task, the FirstTask notifies the Tournament class",
				hasCalled);
		assertNotNull(
				"Upon completion of the first task, the tournament should intialize the second task as long as there are winners",
				secondTask);
		boolean condition = !winnersList.contains(s) && winnersList.contains(h)
				&& winnersList.contains(r) && winnersList.contains(g);
		assertTrue(
				"Upon completion of the first task, only the champions who survived should be sent as winners to the second task",
				condition);
		assertEquals(
				"Upon completion of the first task, the tournament should intialize the second task and set its listener to the tournament",
				t, secondTask.getListener());

	}

	@Test(timeout = 1000)
	public void testMarkCellsChangeBetweenTurns() throws Exception {

		testMethodExistsInClass(FirstTask.class, "markCells", true, Void.TYPE);
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);
		FirstTask task = new FirstTask(e);
		int identicalCombinations = 0;

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				task.getMap()[i][j] = new EmptyCell();
		int[] pointExistAll = new int[5];
		int[] pointExistOld = new int[5];
		task.getMap()[8][1] = new ChampionCell(task.getChampions().get(0));
		((Wizard) task.getChampions().get(0)).setLocation(new Point(8, 1));
		task.getMap()[8][8] = new ChampionCell(task.getChampions().get(1));
		((Wizard) task.getChampions().get(1)).setLocation(new Point(8, 8));
		task.getMap()[1][8] = new ChampionCell(task.getChampions().get(2));
		((Wizard) task.getChampions().get(2)).setLocation(new Point(1, 8));
		task.getMap()[1][1] = new ChampionCell(task.getChampions().get(3));
		((Wizard) task.getChampions().get(3)).setLocation(new Point(1, 1));
		task.moveRight();
		task.moveForward();
		task.moveLeft();
		task.moveBackward();

		for (int c = 0; c < 28; c++) {
			((Wizard) task.getCurrentChamp()).setHp(800);
			int i = ((Wizard) task.getCurrentChamp()).getLocation().x;
			int j = ((Wizard) task.getCurrentChamp()).getLocation().y;
			int[] pointExist = new int[5];

			assertEquals("Number of cells in marked cells is incorrect", 2,
					task.getMarkedCells().size());

			for (int k = 0; k < 2; k++) {
				int x, y = 0;
				x = task.getMarkedCells().get(k).x;
				y = task.getMarkedCells().get(k).y;
				if ((x == i && y == j)) {
					pointExist[0] = 1;
					pointExistAll[0] = 1;
				} else if ((x == (i + 1) && y == j) && i != 9) {
					pointExist[1] = 1;
					pointExistAll[1] = 1;
				} else if ((x == (i - 1) && y == j) && i != 0) {
					pointExist[2] = 1;
					pointExistAll[2] = 1;
				} else if ((x == i && y == (j - 1)) && j != 0) {
					pointExist[3] = 1;
					pointExistAll[3] = 1;
				} else if ((x == i && y == (j + 1)) && j != 9) {
					pointExist[4] = 1;
					pointExistAll[4] = 1;
				}
			}

			assertTrue(" Only " + sumArray(pointExist)
					+ " of the cells are marked correctly ",
					sumArray(pointExist) == 2);
			boolean identical = true;
			for (int k = 0; k < 5; k++) {
				if (pointExist[k] != pointExistOld[k])
					identical = false;
			}
			pointExistOld = pointExist.clone();
			if (identical)
				identicalCombinations++;

			if (c % 4 == 0)
				task.moveRight();
			else if (c % 4 == 1)
				task.moveForward();
			else if (c % 4 == 2)
				task.moveLeft();
			else
				task.moveBackward();

		}

		assertTrue(
				"The marked cells do not change between a turn and the other",
				identicalCombinations < 15);

	}

	@Test(timeout = 1000)
	public void testMarkCellsPotions() throws Exception {

		testMethodExistsInClass(FirstTask.class, "markCells", true, Void.TYPE);
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);
		FirstTask task = new FirstTask(e);

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				task.getMap()[i][j] = new EmptyCell();
		int[] pointExistAll = new int[5];
		for (int c = 0; c < 20; c++) {
			for (int i = 1; i < 9; i++) {
				for (int j = 1; j < 9; j++) {
					if (c / 4 < 4)
						task.getMap()[i - 1][j] = new CollectibleCell(
								new Potion("Senzu", 700));
					if (c / 4 < 3)
						task.getMap()[i + 1][j] = new CollectibleCell(
								new Potion("Senzu", 700));
					if (c / 4 < 2)
						task.getMap()[i][j - 1] = new CollectibleCell(
								new Potion("Senzu", 700));
					if (c / 4 < 1)
						task.getMap()[i][j + 1] = new CollectibleCell(
								new Potion("Senzu", 700));
					int[] pointExist = new int[5];
					task.setCurrentChamp(h);
					task.getMap()[i][j] = new ChampionCell(h);
					((Wizard) task.getCurrentChamp()).setLocation(new Point(i,
							j));
					task.getMarkedCells().clear();
					task.markCells();
					assertEquals(
							"Number of cells in marked cells is incorrect", 2,
							task.getMarkedCells().size());
					for (int k = 0; k < 2; k++) {
						double x, y = 0;
						x = task.getMarkedCells().get(k).getX();
						y = task.getMarkedCells().get(k).getY();
						if ((x == i && y == j)) {
							pointExist[0] = 1;
							pointExistAll[0] = 1;
						} else if ((x == (i + 1) && y == j) && i != 9) {
							pointExist[1] = 1;
							pointExistAll[1] = 1;
						} else if ((x == (i - 1) && y == j) && i != 0) {
							pointExist[2] = 1;
							pointExistAll[2] = 1;
						} else if ((x == i && y == (j - 1)) && j != 0) {
							pointExist[3] = 1;
							pointExistAll[3] = 1;
						} else if ((x == i && y == (j + 1)) && j != 9) {
							pointExist[4] = 1;
							pointExistAll[4] = 1;
						}
					}
					assertTrue(
							"One or both of the marked cells is in an incorrect location",
							sumArray(pointExist) == 2);
					task.getMap()[i][j] = new EmptyCell();
					if (c / 4 < 4)
						task.getMap()[i - 1][j] = new EmptyCell();
					if (c / 4 < 3)
						task.getMap()[i + 1][j] = new EmptyCell();
					if (c / 4 < 2)
						task.getMap()[i][j - 1] = new EmptyCell();
					if (c / 4 < 1)
						task.getMap()[i][j + 1] = new EmptyCell();
				}
			}
		}
		assertTrue(
				"One or both of the marked cells is in an incorrect location",
				sumArray(pointExistAll) == 5);

	}

	@Test(timeout = 1000)
	public void testMarkCellsRandom() throws Exception {

		testMethodExistsInClass(FirstTask.class, "markCells", true, Void.TYPE);
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);
		FirstTask task = new FirstTask(e);
		int identicalCombinations = 0;

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				task.getMap()[i][j] = new EmptyCell();
		int[] pointExistAll = new int[5];
		int[] pointExistOld = new int[5];
		for (int c = 0; c < 20; c++) {
			for (int i = 1; i < 9; i++) {
				for (int j = 1; j < 9; j++) {
					int[] pointExist = new int[5];
					task.setCurrentChamp(h);
					task.getMap()[i][j] = new ChampionCell(h);
					((Wizard) task.getCurrentChamp()).setLocation(new Point(i,
							j));
					task.getMarkedCells().clear();
					task.markCells();
					assertEquals(
							"Number of cells in marked cells is incorrect", 2,
							task.getMarkedCells().size());
					for (int k = 0; k < 2; k++) {
						double x, y = 0;
						x = task.getMarkedCells().get(k).getX();
						y = task.getMarkedCells().get(k).getY();
						if ((x == i && y == j)) {
							pointExist[0] = 1;
							pointExistAll[0] = 1;
						} else if ((x == (i + 1) && y == j) && i != 9) {
							pointExist[1] = 1;
							pointExistAll[1] = 1;
						} else if ((x == (i - 1) && y == j) && i != 0) {
							pointExist[2] = 1;
							pointExistAll[2] = 1;
						} else if ((x == i && y == (j - 1)) && j != 0) {
							pointExist[3] = 1;
							pointExistAll[3] = 1;
						} else if ((x == i && y == (j + 1)) && j != 9) {
							pointExist[4] = 1;
							pointExistAll[4] = 1;
						}
					}
					assertTrue(" Only " + sumArray(pointExist)
							+ " of the cells are marked correctly ",
							sumArray(pointExist) == 2);
					task.getMap()[i][j] = new EmptyCell();
					boolean identical = true;
					for (int k = 0; k < 5; k++) {
						if (pointExist[k] != pointExistOld[k])
							identical = false;
					}
					pointExistOld = pointExist.clone();
					if (identical)
						identicalCombinations++;

				}
			}
		}
		assertTrue("The range of the chosen cells to be marked is incorrect",
				sumArray(pointExistAll) == 5);
		assertTrue(
				"The randomization of marking the cells is done incorrectly",
				identicalCombinations < 500);

	}

	@Test(timeout = 1000)
	public void testMerPersonEffectOnOtherCells() throws Exception {

		testMethodExistsInClass(SecondTask.class, "encounterMerPerson", true,
				Void.TYPE);
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);
		SecondTask task = new SecondTask(e);

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				task.getMap()[i][j] = new EmptyCell();

		for (int c = 0; c < 20; c++) {
			for (int i = 1; i < 9; i++) {
				for (int j = 1; j < 9; j++) {
					int random = (int) (Math.random() * 10);
					h.setHp(h.getDefaultHp());
					task.getMap()[i][j] = new ObstacleCell(new Merperson(
							250 + random, 150 + random));
					task.getMap()[i][j - 1] = new ObstacleCell(new Merperson(
							250 + random, 150 + random));
					task.getMap()[i + 1][j] = new CollectibleCell(new Potion(
							"Senzo", 250 + random));
					task.getMap()[i][j + 1] = new ChampionCell(r);
					task.setCurrentChamp(h);
					task.getMap()[i - 1][j] = new ChampionCell(h);
					((Wizard) task.getCurrentChamp()).setLocation(new Point(i,
							j));

					task.encounterMerPerson();
					assertTrue(
							"The type of the obstacle cell should not be affected by the merperson attack",
							task.getMap()[i][j] instanceof ObstacleCell);
					assertTrue(
							"The type of the collectible cell should not be affected by the merperson attack",
							task.getMap()[i + 1][j] instanceof CollectibleCell);
					assertTrue(
							"The type of the obstacle cell should not be affected by the merperson attack",
							task.getMap()[i][j - 1] instanceof ObstacleCell);
					assertTrue(
							"The type of the champion cell should not be affected by the merperson attack",
							task.getMap()[i - 1][j] instanceof ChampionCell);
					assertTrue(
							"The obstacle types should not be affected by the merperson attack",
							((ObstacleCell) task.getMap()[i][j]).getObstacle() instanceof Merperson
									&& ((ObstacleCell) task.getMap()[i][j - 1])
											.getObstacle() instanceof Merperson);

					assertEquals(
							"The hp of the merpersons should not be affected by the merperson attack",
							250 + random, ((Merperson) ((ObstacleCell) task
									.getMap()[i][j]).getObstacle()).getHp());

					assertEquals(
							"The hp of the merpersons should not be affected by the merperson attack",
							250 + random, ((Merperson) ((ObstacleCell) task
									.getMap()[i][j - 1]).getObstacle()).getHp());

					assertEquals(
							"The damage of the merpersons should not be affected by the merperson attack",
							150 + random, ((Merperson) ((ObstacleCell) task
									.getMap()[i][j]).getObstacle()).getDamage());

					assertEquals(
							"The damage of the merpersons should not be affected by the merperson attack",
							150 + random, ((Merperson) ((ObstacleCell) task
									.getMap()[i][j - 1]).getObstacle())
									.getDamage());

					assertEquals(
							"The name of the potion should not be affected by the merperson attack",
							"Senzo",
							((Potion) ((CollectibleCell) task.getMap()[i + 1][j])
									.getCollectible()).getName());

					assertEquals(
							"The amount of the potion should not be affected by the merperson attack",
							250 + random, ((Potion) ((CollectibleCell) task
									.getMap()[i + 1][j]).getCollectible())
									.getAmount());

					task.getMap()[i][j] = new EmptyCell();
					task.getMap()[i - 1][j] = new EmptyCell();
					task.getMap()[i + 1][j] = new EmptyCell();
					task.getMap()[i][j - 1] = new EmptyCell();
					task.getMap()[i][j + 1] = new EmptyCell();
				}
			}
		}

	}

	@Test(timeout = 1000)
	public void testMerPersonEffectOnOtherCellTypes() throws Exception {

		testMethodExistsInClass(SecondTask.class, "encounterMerPerson", true,
				Void.TYPE);
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);
		SecondTask task = new SecondTask(e);

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				task.getMap()[i][j] = new EmptyCell();

		for (int c = 0; c < 20; c++) {
			for (int i = 1; i < 9; i++) {
				for (int j = 1; j < 9; j++) {
					int random = (int) (Math.random() * 10);
					h.setHp(h.getDefaultHp());
					task.getMap()[i][j] = new ObstacleCell(new Merperson(
							250 + random, 150 + random));
					task.getMap()[i][j - 1] = new ObstacleCell(new Merperson(
							250 + random, 150 + random));
					task.getMap()[i + 1][j] = new CollectibleCell(new Potion(
							"Senzo", 250 + random));
					task.getMap()[i][j + 1] = new ChampionCell(r);
					task.setCurrentChamp(h);
					task.getMap()[i - 1][j] = new ChampionCell(h);
					((Wizard) task.getCurrentChamp()).setLocation(new Point(i,
							j));

					task.encounterMerPerson();
					assertTrue(
							"The type of the obstacle cell should not be affected by the merperson attack",
							task.getMap()[i][j] instanceof ObstacleCell);
					assertTrue(
							"The type of the collectible cell should not be affected by the merperson attack",
							task.getMap()[i + 1][j] instanceof CollectibleCell);
					assertTrue(
							"The type of the obstacle cell should not be affected by the merperson attack",
							task.getMap()[i][j - 1] instanceof ObstacleCell);
					assertTrue(
							"The type of the champion cell should not be affected by the merperson attack",
							task.getMap()[i - 1][j] instanceof ChampionCell);
					assertTrue(
							"The obstacle types should not be affected by the merperson attack",
							((ObstacleCell) task.getMap()[i][j]).getObstacle() instanceof Merperson
									&& ((ObstacleCell) task.getMap()[i][j - 1])
											.getObstacle() instanceof Merperson);

					task.getMap()[i][j] = new EmptyCell();
					task.getMap()[i - 1][j] = new EmptyCell();
					task.getMap()[i + 1][j] = new EmptyCell();
					task.getMap()[i][j - 1] = new EmptyCell();
					task.getMap()[i][j + 1] = new EmptyCell();
				}
			}
		}

	}

	@Test(timeout = 1000)
	public void testMethodCastHealingSpell2() throws Exception {
		testMethodExistsInClass(Task.class, "castHealingSpell", true,
				Void.TYPE, HealingSpell.class);

		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);
		FirstTask task1 = new FirstTask(e) {
			@Override
			public void finalizeAction() {

			}

			@Override
			public void useSpell(Spell s) {

			}
		};
		task1.getChampions().clear();
		task1.getChampions().add(g);
		task1.getChampions().add(h);
		task1.getChampions().add(r);
		task1.getChampions().add(s);
		HealingSpell spell = new HealingSpell("HEL", 300, 4, 120);
		g.getSpells().add(spell);
		g.setHp(g.getHp() - 200);
		task1.setCurrentChamp(g);
		task1.castHealingSpell(spell);
		task1.setCurrentChamp(g);
		spell = new HealingSpell("HEL", 300, 4, 120);
		task1.castHealingSpell(spell);
		assertEquals(
				"In FirstTask, current champion's health points should not exceed the default health points after healing",
				g.getDefaultHp(), g.getHp());

		SecondTask task2 = new SecondTask(e) {
			@Override
			public void finalizeAction() {

			}

			@Override
			public void useSpell(Spell s) {

			}
		};
		task2.getChampions().clear();
		task2.getChampions().add(g);
		task2.getChampions().add(h);
		task2.getChampions().add(s);
		task2.getChampions().add(r);
		spell = new HealingSpell("HEL", 300, 4, 120);
		g.getSpells().add(spell);
		g.setHp(g.getHp() - 200);
		task2.setCurrentChamp(g);
		task2.castHealingSpell(spell);
		task2.setCurrentChamp(g);
		spell = new HealingSpell("HEL", 300, 4, 120);
		task2.castHealingSpell(spell);
		assertEquals(
				"In SecondTask, current champion's health points should not exceed the default health points after healing",
				g.getDefaultHp(), g.getHp());

		ThirdTask task3 = new ThirdTask(e) {
			@Override
			public void finalizeAction() {

			}

			@Override
			public void useSpell(Spell s) {

			}
		};
		task3.getChampions().add(g);
		task3.getChampions().add(h);
		task3.getChampions().add(r);
		task3.getChampions().add(s);
		spell = new HealingSpell("HEL", 300, 4, 120);
		g.getSpells().add(spell);
		g.setHp(g.getHp() - 200);
		task3.setCurrentChamp(g);
		task3.castHealingSpell(spell);
		task3.setCurrentChamp(g);
		spell = new HealingSpell("HEL", 300, 4, 120);
		task3.castHealingSpell(spell);
		assertEquals(
				"In ThirdTask, current champion's health points should not exceed the default health points after healing",
				g.getDefaultHp(), g.getHp());

	}

	@Test(timeout = 1000)
	public void testMethodEncounterMerpersonIsCalledPerTurn1() throws Exception {
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);
		hasCalled = false;
		SecondTask task1 = new SecondTask(e) {
			@Override
			public void encounterMerPerson() {
				hasCalled = true;
			}
		};
		task1.getChampions().clear();
		task1.getChampions().add(g);
		task1.getChampions().add(h);
		task1.getChampions().add(r);
		task1.getChampions().add(s);

		Field map = Task.class.getDeclaredField("map");
		map.setAccessible(true);
		map.set(task1, new Cell[10][10]);
		Cell[][] taskMap = task1.getMap();
		for (int i = 0; i < taskMap.length; i++) {
			for (int j = 0; j < taskMap[i].length; j++) {
				taskMap[i][j] = new EmptyCell();
			}
		}
		int champPosX = (int) (6 * Math.random()) + 2;
		int champPosY = (int) (6 * Math.random()) + 2;
		task1.getMap()[champPosX][champPosY] = new ChampionCell(g);
		g.setLocation(new Point(champPosX, champPosY));
		task1.setCurrentChamp(g);
		task1.moveLeft();
		assertTrue(
				"In SecondTask, the merperson should attack once at each turn",
				hasCalled);

		task1.getMap()[champPosX][champPosY] = new ChampionCell(h);
		h.setLocation(new Point(champPosX, champPosY));
		task1.setCurrentChamp(h);
		task1.moveRight();
		assertTrue(
				"In SecondTask, the merperson should attack once at each turn",
				hasCalled);

		task1.getMap()[champPosX][champPosY] = new ChampionCell(r);
		r.setLocation(new Point(champPosX, champPosY));
		task1.setCurrentChamp(r);
		task1.moveForward();
		assertTrue(
				"In SecondTask, the merperson should attack once at each turn",
				hasCalled);

		task1.getMap()[champPosX][champPosY] = new ChampionCell(s);
		s.setLocation(new Point(champPosX, champPosY));
		task1.setCurrentChamp(s);
		task1.moveBackward();
		assertTrue(
				"In SecondTask, the merperson should attack once at each turn",
				hasCalled);
	}

	@Test(timeout = 1000)
	public void testMethodFireIsCalledPerTurn2() throws Exception {
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);
		hasCalled = false;
		FirstTask task1 = new FirstTask(e) {
			@Override
			public void fire() {
				hasCalled = true;
			}
		};
		task1.getChampions().clear();
		task1.getChampions().add(g);
		task1.getChampions().add(h);
		task1.getChampions().add(r);
		task1.getChampions().add(s);

		Field map = Task.class.getDeclaredField("map");
		map.setAccessible(true);
		map.set(task1, new Cell[10][10]);
		Cell[][] taskMap = task1.getMap();
		for (int i = 0; i < taskMap.length; i++) {
			for (int j = 0; j < taskMap[i].length; j++) {
				taskMap[i][j] = new EmptyCell();
			}
		}
		int champPosX = (int) (6 * Math.random()) + 2;
		int champPosY = (int) (6 * Math.random()) + 2;
		task1.getMap()[champPosX][champPosY] = new ChampionCell(g);
		g.setLocation(new Point(champPosX, champPosY));
		g.getSpells().clear();
		HealingSpell spell = new HealingSpell("HEL", 20, 5, 20);
		g.getSpells().add(spell);
		task1.setCurrentChamp(g);
		task1.castHealingSpell(spell);
		assertTrue("In FirstTask, the dragon should fire once at each turn",
				hasCalled);

		task1.getMap()[champPosX][champPosY] = new ChampionCell(h);
		h.setLocation(new Point(champPosX, champPosY));
		h.getSpells().clear();
		DamagingSpell spell2 = new DamagingSpell("HEL", 20, 5, 20);
		h.getSpells().add(spell2);
		task1.setCurrentChamp(h);
		task1.castDamagingSpell(spell2, Direction.BACKWARD);
		assertTrue("In FirstTask, the dragon should fire once at each turn",
				hasCalled);

		task1.getMap()[champPosX][champPosY] = new ChampionCell(r);
		r.setLocation(new Point(champPosX, champPosY));
		r.getSpells().clear();
		spell2 = new DamagingSpell("HEL", 20, 5, 20);
		r.getSpells().add(spell2);
		task1.setCurrentChamp(r);
		task1.castDamagingSpell(spell2, Direction.BACKWARD);
		assertTrue("In FirstTask, the dragon should fire once at each turn",
				hasCalled);

		task1.getMap()[champPosX][champPosY] = new ChampionCell(s);
		s.setLocation(new Point(champPosX, champPosY));
		s.getSpells().clear();
		spell = new HealingSpell("HEL", 20, 5, 20);
		s.getSpells().add(spell2);
		task1.setCurrentChamp(s);
		task1.castHealingSpell(spell);
		assertTrue("In FirstTask, the dragon should fire once at each turn",
				hasCalled);
	}

	@Test(timeout = 1000)
	public void testMethodUsePotions2() throws Exception {

		testMethodExistsInClass(Task.class, "usePotion", true, Void.TYPE,
				Potion.class);

		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);
		FirstTask task1 = new FirstTask(e);
		task1.getChampions().clear();
		task1.getChampions().add(g);
		task1.getChampions().add(h);
		task1.getChampions().add(r);
		task1.getChampions().add(s);
		g = new GryffindorWizard("gryff");
		task1.setCurrentChamp(g);
		Potion p = task1.getPotions().get(0);
		g.getInventory().add(p);

		int newIP = g.getIp() + p.getAmount();
		task1.usePotion(p);

		assertEquals(
				"In FirstTask, whenever a champion uses a potion, his/her ip should be increased by the potion amount even if it will exceed the default IP",
				newIP, g.getIp());

		SecondTask task2 = new SecondTask(e);
		task2.getChampions().clear();
		task2.getChampions().add(g);
		task2.getChampions().add(h);
		task2.getChampions().add(s);
		task2.getChampions().add(r);
		g = new GryffindorWizard("gryff");
		task2.setCurrentChamp(g);
		p = task2.getPotions().get(0);
		g.getInventory().add(p);

		newIP = g.getIp() + p.getAmount();
		task2.usePotion(p);
		assertEquals(
				"In SecondTask, whenever a champion uses a potion, his/her ip should be increased by the potion amount even if it will exceed the default IP",
				newIP, g.getIp());

		ThirdTask task3 = new ThirdTask(e);
		task3.getChampions().add(g);
		task3.getChampions().add(h);
		task3.getChampions().add(r);
		task3.getChampions().add(s);
		g = new GryffindorWizard("gryff");
		task3.setCurrentChamp(g);
		p = task3.getPotions().get(0);

		g.getInventory().add(p);
		newIP = g.getIp() + p.getAmount();
		task3.usePotion(p);
		assertEquals(
				"In ThirdTask, whenever a champion uses a potion, his/her ip should be increased by the potion amount even if it will exceed the default IP",
				newIP, g.getIp());

	}

	@Test(timeout = 1000)
	public void testMethodUseSpell2() throws Exception {
		testMethodExistsInClass(Task.class, "useSpell", true, Void.TYPE,
				Spell.class);

		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		g.setLocation(new Point(0, 0));
		h.setLocation(new Point(0, 2));
		r.setLocation(new Point(0, 3));
		s.setLocation(new Point(0, 4));
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);

		FirstTask task1 = new FirstTask(e);
		task1.getChampions().clear();
		task1.getChampions().add(g);
		task1.getChampions().add(h);
		task1.getChampions().add(r);
		task1.getChampions().add(s);
		Spell spell = new DamagingSpell("DMG", 20, 5, 20);
		g.getSpells().add(spell);
		int newChampionIp = g.getIp() - spell.getCost();
		task1.setCurrentChamp(g);
		task1.useSpell(spell);
		assertEquals(
				"In the FirstTask, whenever a spell is used, Ip of the current champion should be reduced by the cost",
				newChampionIp, g.getIp());

		SecondTask task2 = new SecondTask(e);
		task2.getChampions().clear();
		task2.getChampions().add(g);
		task2.getChampions().add(h);
		task2.getChampions().add(s);
		task2.getChampions().add(r);
		g.getSpells().add(spell);
		g = new GryffindorWizard("gryff");
		g.setLocation(new Point(0, 0));
		spell = new DamagingSpell("DMG", 20, 5, 20);
		newChampionIp = g.getIp() - spell.getCost();
		task2.setCurrentChamp(g);
		task2.useSpell(spell);
		assertEquals(
				"In the SecondTask, whenever a spell is used, Ip of the current champion should be reduced by the cost",
				newChampionIp, g.getIp());

		ThirdTask task3 = new ThirdTask(e);
		task3.getChampions().add(g);
		task3.getChampions().add(h);
		task3.getChampions().add(r);
		task3.getChampions().add(s);
		g.getSpells().add(spell);
		g = new GryffindorWizard("gryff");
		g.setLocation(new Point(0, 0));
		spell = new DamagingSpell("DMG", 20, 5, 20);
		newChampionIp = g.getIp() - spell.getCost();
		task3.setCurrentChamp(g);
		task3.useSpell(spell);
		assertEquals(
				"In the ThirdTask, whenever a spell is used, Ip of the current champion should be reduced by the cost",
				newChampionIp, g.getIp());

	}

	@Test(timeout = 1000)
	public void testMoveBackwardToCollectibleCell() throws Exception {
		testMethodExistsInClass(Task.class, "moveBackward", true, Void.TYPE);
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);
		FirstTask task = new FirstTask(e);

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				task.getMap()[i][j] = new EmptyCell();

		task.setCurrentChamp(h);
		task.getMap()[4][3] = new ChampionCell(h);
		((Wizard) task.getCurrentChamp()).setLocation(new Point(4, 3));

		Potion p = new Potion("p", 12);
		task.getMap()[5][3] = new CollectibleCell(p);

		task.moveBackward();
		task.setCurrentChamp(h);

		assertTrue(
				"When a champion moves backward to a collectibe cell, the collectible should be added to his/her inventory",
				((Wizard) task.getCurrentChamp()).getInventory().contains(p));
		assertEquals(
				"When a champion moves backward from a cell, it should be converted to an EmptyCell ",
				EmptyCell.class, task.getMap()[4][3].getClass());
		assertEquals(
				"When a champion moves backward to a cell, it should be converted to a ChampionCell ",
				ChampionCell.class, task.getMap()[5][3].getClass());

	}

	@Test(timeout = 1000)
	public void testMoveBackwardToEggCell() throws Exception {

		testMethodExistsInClass(Task.class, "moveBackward", true, Void.TYPE);

		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);
		FirstTask task = new FirstTask(e);

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				task.getMap()[i][j] = new EmptyCell();

		task.setCurrentChamp(h);
		task.getMap()[3][4] = new ChampionCell(h);
		((Wizard) task.getCurrentChamp()).setLocation(new Point(3, 4));

		task.moveBackward();
		task.setCurrentChamp(h);

		assertTrue(
				"When a champion moves backward to cell (4,4), he/she is considered a winner. Thus the champion should be added to the winners list ",
				task.getWinners().contains(h));

		assertFalse(
				"When a champion moves backward to cell (4,4), he/she is considered a winner. Thus the champion should be removed from the champions list ",
				task.getChampions().contains(h));

		assertEquals(
				"When a champion moves backward from a cell, it should be converted to an EmptyCell ",
				EmptyCell.class, task.getMap()[3][4].getClass());

	}

	@Test(timeout = 1000)
	public void testMoveBackwardToEggCellSecondTask() throws Exception {

		testMethodExistsInClass(Task.class, "moveBackward", true, Void.TYPE);

		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);
		SecondTask task = new SecondTask(e);

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				task.getMap()[i][j] = new EmptyCell();

		task.setCurrentChamp(h);
		task.getMap()[3][4] = new ChampionCell(h);
		((Wizard) task.getCurrentChamp()).setLocation(new Point(3, 4));

		task.moveBackward();
		task.setCurrentChamp(h);

		assertFalse(
				"When a champion moves to a cell (4,4) in SecondTask, he/she is NOT considered a winner. Thus the champion should NOT be added to the winners list ",
				task.getWinners().contains(h));

		assertTrue(
				"When a champion moves to a cell (4,4) in SecondTask, he/she is NOT considered a winner. Thus the champion should NOT be removed from the champions list ",
				task.getChampions().contains(h));

		assertEquals(
				"When a champion moves forward from a cell, it should be converted to an EmptyCell ",
				EmptyCell.class, task.getMap()[3][4].getClass());

	}

	@Test(timeout = 1000)
	public void testMoveBackwardToEggCellThirdTask() throws Exception {

		testMethodExistsInClass(Task.class, "moveBackward", true, Void.TYPE);

		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);
		ThirdTask task = new ThirdTask(e);

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				task.getMap()[i][j] = new EmptyCell();

		task.setCurrentChamp(h);
		task.getMap()[3][4] = new ChampionCell(h);
		((Wizard) task.getCurrentChamp()).setLocation(new Point(3, 4));

		task.moveBackward();
		task.setCurrentChamp(h);

		assertTrue(
				"When a champion moves to a cell (4,4) in ThirdTask, he/she is NOT considered a winner. Thus the champion should NOT be removed from the champions list ",
				task.getChampions().contains(h));

		assertEquals(
				"When a champion moves forward from a cell, it should be converted to an EmptyCell ",
				EmptyCell.class, task.getMap()[3][4].getClass());

	}

	@Test(timeout = 1000)
	public void testMoveBackwardToEmptyCell() throws Exception {
		testMethodExistsInClass(Task.class, "moveBackward", true, Void.TYPE);
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);
		FirstTask task = new FirstTask(e);

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				task.getMap()[i][j] = new EmptyCell();

		task.setCurrentChamp(h);
		task.getMap()[4][3] = new ChampionCell(h);
		((Wizard) task.getCurrentChamp()).setLocation(new Point(4, 3));

		task.moveBackward();
		task.setCurrentChamp(h);

		Point newLocation = ((Wizard) task.getCurrentChamp()).getLocation();
		assertEquals(
				"MoveBackward method in class Task is not moving the champion correctly",
				new Point(5, 3), newLocation);

		assertEquals(
				"When a champion moves backward to a cell, it should be converted to a ChampionCell ",
				ChampionCell.class, task.getMap()[5][3].getClass());

		assertEquals(
				"When a champion moves backward from a cell, it should be converted to an EmptyCell ",
				EmptyCell.class, task.getMap()[4][3].getClass());

	}

	@Test(timeout = 1000)
	public void testMoveBackwardToTreasureCell() throws Exception {

		testMethodExistsInClass(Task.class, "moveBackward", true, Void.TYPE);
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);
		SecondTask task = new SecondTask(e);

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				task.getMap()[i][j] = new EmptyCell();

		task.setCurrentChamp(h);
		task.getMap()[0][3] = new ChampionCell(h);
		((Wizard) task.getCurrentChamp()).setLocation(new Point(0, 3));

		task.getMap()[1][3] = new TreasureCell(task.getCurrentChamp());

		task.moveBackward();
		task.setCurrentChamp(h);

		assertTrue(
				"When a champion moves backward to his/her treasure cell in the Second Task, he/she is considered a winner. Thus the champion should be added to the winners list ",
				task.getWinners().contains(h));

		assertFalse(
				"When a champion moves backward to his/her treasure cell in the Second Task, he/she is considered a winner. Thus the champion should be removed from the champions list ",
				task.getChampions().contains(h));

		assertEquals(
				"When a champion moves backward from a cell, it should be converted to an EmptyCell ",
				EmptyCell.class, task.getMap()[0][3].getClass());
	}

	@Test(timeout = 1000)
	public void testMoveLeftToCollectibleCell() throws Exception {

		testMethodExistsInClass(Task.class, "moveLeft", true, Void.TYPE);
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);
		FirstTask task = new FirstTask(e);

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				task.getMap()[i][j] = new EmptyCell();

		task.setCurrentChamp(h);
		task.getMap()[5][2] = new ChampionCell(h);
		((Wizard) task.getCurrentChamp()).setLocation(new Point(5, 2));

		Potion p = new Potion("p", 12);
		task.getMap()[5][1] = new CollectibleCell(p);

		task.moveLeft();
		task.setCurrentChamp(h);

		assertTrue(
				"When a champion moves left to a collectibe cell, the collectible should be added to his/her inventory",
				((Wizard) task.getCurrentChamp()).getInventory().contains(p));
		assertEquals(
				"When a champion moves left from a cell, it should be converted to an EmptyCell ",
				EmptyCell.class, task.getMap()[5][2].getClass());

		assertEquals(
				"When a champion moves left to a cell, it should be converted to a ChampionCell ",
				ChampionCell.class, task.getMap()[5][1].getClass());

	}

	@Test(timeout = 1000)
	public void testMoveLeftToEggCell() throws Exception {

		testMethodExistsInClass(Task.class, "moveLeft", true, Void.TYPE);
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);
		FirstTask task = new FirstTask(e);

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				task.getMap()[i][j] = new EmptyCell();

		task.setCurrentChamp(h);
		task.getMap()[4][5] = new ChampionCell(h);
		((Wizard) task.getCurrentChamp()).setLocation(new Point(4, 5));

		task.moveLeft();
		task.setCurrentChamp(h);

		assertTrue(
				"When a champion moves left to cell (4,4), he/she is considered a winner. Thus the champion should be added to the winners list ",
				task.getWinners().contains(h));

		assertFalse(
				"When a champion moves left to cell (4,4), he/she is considered a winner. Thus the champion should be removed from the champions list ",
				task.getChampions().contains(h));

		assertEquals(
				"When a champion moves left from a cell, it should be converted to an EmptyCell ",
				EmptyCell.class, task.getMap()[4][5].getClass());
	}

	@Test(timeout = 1000)
	public void testMoveLeftToEggCellSecondTask() throws Exception {

		testMethodExistsInClass(Task.class, "moveLeft", true, Void.TYPE);
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);
		SecondTask task = new SecondTask(e);

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				task.getMap()[i][j] = new EmptyCell();

		task.setCurrentChamp(h);
		task.getMap()[4][5] = new ChampionCell(h);
		((Wizard) task.getCurrentChamp()).setLocation(new Point(4, 5));

		task.moveLeft();
		task.setCurrentChamp(h);

		assertFalse(
				"When a champion moves to a cell (4,4) in SecondTask, he/she is NOT considered a winner. Thus the champion should NOT be added to the winners list ",
				task.getWinners().contains(h));

		assertTrue(
				"When a champion moves to a cell (4,4) in SecondTask, he/she is NOT considered a winner. Thus the champion should NOT be removed from the champions list ",
				task.getChampions().contains(h));

		assertEquals(
				"When a champion moves forward from a cell, it should be converted to an EmptyCell ",
				EmptyCell.class, task.getMap()[4][5].getClass());
	}

	@Test(timeout = 1000)
	public void testMoveLeftToEggCellThirdTask() throws Exception {

		testMethodExistsInClass(Task.class, "moveLeft", true, Void.TYPE);
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);
		ThirdTask task = new ThirdTask(e);

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				task.getMap()[i][j] = new EmptyCell();

		task.setCurrentChamp(h);
		task.getMap()[4][5] = new ChampionCell(h);
		((Wizard) task.getCurrentChamp()).setLocation(new Point(4, 5));

		task.moveLeft();
		task.setCurrentChamp(h);

		assertTrue(
				"When a champion moves to a cell (4,4) in ThirdTask, he/she is NOT considered a winner. Thus the champion should NOT be removed from the champions list ",
				task.getChampions().contains(h));

		assertEquals(
				"When a champion moves forward from a cell, it should be converted to an EmptyCell ",
				EmptyCell.class, task.getMap()[4][5].getClass());
	}

	@Test(timeout = 1000)
	public void testMoveLeftToEmptyCell() throws Exception {

		testMethodExistsInClass(Task.class, "moveLeft", true, Void.TYPE);
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);
		FirstTask task = new FirstTask(e);

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				task.getMap()[i][j] = new EmptyCell();

		task.setCurrentChamp(h);
		task.getMap()[5][3] = new ChampionCell(h);
		((Wizard) task.getCurrentChamp()).setLocation(new Point(5, 3));

		task.moveLeft();
		task.setCurrentChamp(h);

		Point newLocation = ((Wizard) task.getCurrentChamp()).getLocation();
		assertEquals(
				"MoveLeft method in class Task is not moving the champion correctly",
				new Point(5, 2), newLocation);

		assertEquals(
				"When a champion moves left to a cell, it should be converted to a ChampionCell ",
				ChampionCell.class, task.getMap()[5][2].getClass());
		assertEquals(
				"When a champion moves left from a cell, it should be converted to an EmptyCell ",
				EmptyCell.class, task.getMap()[5][3].getClass());

	}

	@Test(timeout = 1000)
	public void testMoveLeftToTreasureCell() throws Exception {

		testMethodExistsInClass(Task.class, "moveLeft", true, Void.TYPE);
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);
		SecondTask task = new SecondTask(e);

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				task.getMap()[i][j] = new EmptyCell();

		task.setCurrentChamp(h);
		task.getMap()[1][4] = new ChampionCell(h);
		((Wizard) task.getCurrentChamp()).setLocation(new Point(1, 4));

		task.getMap()[1][3] = new TreasureCell(task.getCurrentChamp());

		task.moveLeft();
		task.setCurrentChamp(h);

		assertTrue(
				"When a champion moves left to his/her treasure cell in the Second Task, he/she is considered a winner. Thus the champion should be added to the winners list ",
				task.getWinners().contains(h));

		assertFalse(
				"When a champion moves left to his/her treasure cell in the Second Task, he/she is considered a winner. Thus the champion should be removed from the champions list ",
				task.getChampions().contains(h));

		assertEquals(
				"When a champion moves left from a cell, it should be converted to an EmptyCell ",
				EmptyCell.class, task.getMap()[1][4].getClass());
	}

	@Test(timeout = 1000)
	public void testMoveRightToCollectibleCell() throws Exception {

		testMethodExistsInClass(Task.class, "moveRight", true, Void.TYPE);
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);
		FirstTask task = new FirstTask(e);

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				task.getMap()[i][j] = new EmptyCell();

		task.setCurrentChamp(h);
		task.getMap()[5][4] = new ChampionCell(h);
		((Wizard) task.getCurrentChamp()).setLocation(new Point(5, 4));

		Potion p = new Potion("p", 12);
		task.getMap()[5][5] = new CollectibleCell(p);

		task.moveRight();
		task.setCurrentChamp(h);

		assertTrue(
				"When a champion moves right to a collectibe cell, the collectible should be added to his/her inventory",
				((Wizard) task.getCurrentChamp()).getInventory().contains(p));
		assertEquals(
				"When a champion moves right from a cell, it should be converted to an EmptyCell ",
				EmptyCell.class, task.getMap()[5][4].getClass());

		assertEquals(
				"When a champion moves right to a cell, it should be converted to a ChampionCell ",
				ChampionCell.class, task.getMap()[5][5].getClass());

	}

	@Test(timeout = 1000)
	public void testMoveRightToEggCell() throws Exception {

		testMethodExistsInClass(Task.class, "moveRight", true, Void.TYPE);
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);
		FirstTask task = new FirstTask(e);

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				task.getMap()[i][j] = new EmptyCell();

		task.setCurrentChamp(h);
		task.getMap()[4][3] = new ChampionCell(h);
		((Wizard) task.getCurrentChamp()).setLocation(new Point(4, 3));

		task.moveRight();
		task.setCurrentChamp(h);

		assertTrue(
				"When a champion moves right to cell (4,4), he/she is considered a winner. Thus the champion should be added to the winners list ",
				task.getWinners().contains(h));
		assertFalse(
				"When a champion moves right to cell (4,4), he/she is considered a winner. Thus the champion should be removed from the champions list ",
				task.getChampions().contains(h));

		assertEquals(
				"When a champion moves right from a cell, it should be converted to an EmptyCell ",
				EmptyCell.class, task.getMap()[4][3].getClass());
	}

	@Test(timeout = 1000)
	public void testMoveRightToEggCellSecondTask() throws Exception {

		testMethodExistsInClass(Task.class, "moveRight", true, Void.TYPE);
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);
		SecondTask task = new SecondTask(e);

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				task.getMap()[i][j] = new EmptyCell();

		task.setCurrentChamp(h);
		task.getMap()[4][3] = new ChampionCell(h);
		((Wizard) task.getCurrentChamp()).setLocation(new Point(4, 3));

		task.moveRight();
		task.setCurrentChamp(h);

		assertFalse(
				"When a champion moves to a cell (4,4) in SecondTask, he/she is NOT considered a winner. Thus the champion should NOT be added to the winners list ",
				task.getWinners().contains(h));

		assertTrue(
				"When a champion moves to a cell (4,4) in SecondTask, he/she is NOT considered a winner. Thus the champion should NOT be removed from the champions list ",
				task.getChampions().contains(h));

		assertEquals(
				"When a champion moves forward from a cell, it should be converted to an EmptyCell ",
				EmptyCell.class, task.getMap()[4][3].getClass());
	}

	@Test(timeout = 1000)
	public void testMoveRightToEggCellThirdTask() throws Exception {

		testMethodExistsInClass(Task.class, "moveRight", true, Void.TYPE);
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);
		ThirdTask task = new ThirdTask(e);

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				task.getMap()[i][j] = new EmptyCell();

		task.setCurrentChamp(h);
		task.getMap()[4][3] = new ChampionCell(h);
		((Wizard) task.getCurrentChamp()).setLocation(new Point(4, 3));

		task.moveRight();
		task.setCurrentChamp(h);

		assertTrue(
				"When a champion moves to a cell (4,4) in ThirdTask, he/she is NOT considered a winner. Thus the champion should NOT be removed from the champions list ",
				task.getChampions().contains(h));

		assertEquals(
				"When a champion moves forward from a cell, it should be converted to an EmptyCell ",
				EmptyCell.class, task.getMap()[4][3].getClass());
	}

	@Test(timeout = 1000)
	public void testMoveRightToEmptyCell() throws Exception {

		testMethodExistsInClass(Task.class, "moveRight", true, Void.TYPE);
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);
		FirstTask task = new FirstTask(e);

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				task.getMap()[i][j] = new EmptyCell();

		task.setCurrentChamp(h);
		task.getMap()[5][3] = new ChampionCell(h);
		((Wizard) task.getCurrentChamp()).setLocation(new Point(5, 3));

		task.moveRight();
		task.setCurrentChamp(h);

		Point newLocation = ((Wizard) task.getCurrentChamp()).getLocation();
		assertEquals(
				"MoveRight method in class Task is not moving the champion correctly",
				new Point(5, 4), newLocation);

		assertEquals(
				"When a champion moves right to a cell, it should be converted to a ChampionCell ",
				ChampionCell.class, task.getMap()[5][4].getClass());
		assertEquals(
				"When a champion moves right from a cell, it should be converted to an EmptyCell ",
				EmptyCell.class, task.getMap()[5][3].getClass());

	}

	@Test(timeout = 1000)
	public void testMoveRightToTreasureCell() throws Exception {

		testMethodExistsInClass(Task.class, "moveRight", true, Void.TYPE);
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);
		SecondTask task = new SecondTask(e);

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				task.getMap()[i][j] = new EmptyCell();

		task.setCurrentChamp(h);
		task.getMap()[1][2] = new ChampionCell(h);
		((Wizard) task.getCurrentChamp()).setLocation(new Point(1, 2));

		task.getMap()[1][3] = new TreasureCell(task.getCurrentChamp());

		task.moveRight();
		task.setCurrentChamp(h);

		assertTrue(
				"When a champion moves right to his/her treasure cell in the Second Task, he/she is considered a winner. Thus the champion should be added to the winners list ",
				task.getWinners().contains(h));
		assertFalse(
				"When a champion moves right to his/her treasure cell in the Second Task, he/she is considered a winner. Thus the champion should be removed from the champions list ",
				task.getChampions().contains(h));

		assertEquals(
				"When a champion moves right from a cell, it should be converted to an EmptyCell ",
				EmptyCell.class, task.getMap()[1][2].getClass());
	}

	@Test(timeout = 1000)
	public void testOnGryffindorTraitTaskScenario2() throws Exception {
		testMethodExistsInClass(Task.class, "onGryffindorTrait", true,
				Void.TYPE);

		for (int hj = 0; hj < 1000; hj++) {
			ArrayList<Champion> e = new ArrayList<>();
			GryffindorWizard g = new GryffindorWizard("gryff");
			HufflepuffWizard h = new HufflepuffWizard("huff");
			RavenclawWizard r = new RavenclawWizard("raven");
			SlytherinWizard s = new SlytherinWizard("slyth");
			e.add(g);
			e.add(h);
			e.add(r);
			e.add(s);

			SecondTask task2 = new SecondTask(e);
			task2.getChampions().clear();
			task2.getChampions().add(g);
			task2.getChampions().add(h);
			task2.getChampions().add(r);
			task2.getChampions().add(s);

			Field map = Task.class.getDeclaredField("map");
			map.setAccessible(true);
			map.set(task2, new Cell[10][10]);
			Cell[][] taskMap = task2.getMap();
			for (int i = 0; i < taskMap.length; i++) {
				for (int j = 0; j < taskMap[i].length; j++) {
					taskMap[i][j] = new EmptyCell();
				}
			}
			int champPosX = (int) (6 * Math.random()) + 2;
			int champPosY = (int) (6 * Math.random()) + 2;
			task2.getMap()[champPosX][champPosY] = new ChampionCell(g);
			g.setLocation(new Point(champPosX, champPosY));
			task2.setCurrentChamp(g);
			g.useTrait();
			int newChampPosX = champPosX;
			int newChampPosY = champPosY;
			int randomMove = (int) (4 * Math.random());
			switch (randomMove) {
			case 0:
				task2.moveForward();
				newChampPosX -= 1;
				break;
			case 1:
				task2.moveBackward();
				newChampPosX += 1;
				break;
			case 2:
				task2.moveLeft();
				newChampPosY -= 1;
				break;
			case 3:
				task2.moveRight();
				newChampPosY += 1;
				break;
			}

			if (task2.getMap()[newChampPosX][newChampPosY] instanceof ChampionCell) {
				assertEquals(
						"In all Tasks, when GryffindorTrait is activated the champion location in the map should be updated correctly after using his/her two actions",
						g,
						((ChampionCell) task2.getMap()[newChampPosX][newChampPosY])
								.getChamp());

				assertTrue(
						"In all Tasks, after a champion moves his/her old cell should become EmptyCell",
						task2.getMap()[champPosX][champPosY] instanceof EmptyCell);
			} else {

				fail("In all Tasks, if GryffindorTrait is activated it should allow the champion to move in his/her turn");
			}

			assertEquals(
					"In all Tasks, when GryffindorTrait is activated the champion location should be updated correctly after moving",
					new Point(newChampPosX, newChampPosY), g.getLocation());

			assertEquals(
					"In all Tasks, when GryffindorTrait is activated the current champion should be updated only after finishing his/her two actions",
					g, task2.getCurrentChamp());

			g.getSpells().clear();
			g.getSpells().add(new DamagingSpell("DMG", 20, 5, 20));
			Spell spell = g.getSpells().get(0);
			int initSpellCooldown = spell.getDefaultCooldown();
			int newChampionIp = g.getIp() - spell.getCost();

			task2.getMap()[newChampPosX][newChampPosY + 1] = new ObstacleCell(
					new Merperson(100, 100));
			task2.castDamagingSpell((DamagingSpell) spell, Direction.RIGHT);

			g.setLocation(new Point(1, 1));
			h.setLocation(new Point(2, 2));
			r.setLocation(new Point(3, 3));
			s.setLocation(new Point(5, 5));
			taskMap[1][1] = new ChampionCell(g);
			taskMap[2][2] = new ChampionCell(h);
			taskMap[3][3] = new ChampionCell(r);
			taskMap[5][5] = new ChampionCell(s);

			task2.setCurrentChamp(h);
			task2.moveForward();
			task2.setCurrentChamp(r);
			task2.moveForward();
			task2.setCurrentChamp(s);
			task2.moveForward();

			assertEquals(
					"In all Tasks, if GryffindorTrait is activated it should allow the champion to move and cast a spell in his/her turn and update the IP accordingly ",
					newChampionIp, g.getIp());

			assertEquals(
					"In all Tasks, if GryffindorTrait is activated it should allow the champion to move and cast a spell in his/her turn and update the spell CoolDown accordingly ",
					(initSpellCooldown <= 0 ? 0 : initSpellCooldown - 1),
					spell.getCoolDown());
		}
	}

	@Test(timeout = 1000)
	public void testOnGryffindorTraitTaskScenario3() throws Exception {
		testMethodExistsInClass(Task.class, "onGryffindorTrait", true,
				Void.TYPE);

		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);

		SecondTask task2 = new SecondTask(e);
		task2.getChampions().clear();
		task2.getChampions().add(g);
		task2.getChampions().add(h);
		task2.getChampions().add(r);
		task2.getChampions().add(s);

		Field map = Task.class.getDeclaredField("map");
		map.setAccessible(true);
		map.set(task2, new Cell[10][10]);
		Cell[][] taskMap = task2.getMap();
		for (int i = 0; i < taskMap.length; i++) {
			for (int j = 0; j < taskMap[i].length; j++) {
				taskMap[i][j] = new EmptyCell();
			}
		}
		int champPosX = (int) (6 * Math.random()) + 2;
		int champPosY = (int) (6 * Math.random()) + 2;
		task2.getMap()[champPosX][champPosY] = new ChampionCell(g);
		g.setLocation(new Point(champPosX, champPosY));
		task2.setCurrentChamp(g);
		g.useTrait();

		g.getSpells().clear();
		g.getSpells().add(new HealingSpell("HealMe!", 16, 15, 20));
		Spell spell = g.getSpells().get(0);
		int initSpellCooldown = spell.getDefaultCooldown();
		int newChampionIp = g.getIp() - spell.getCost();
		task2.castHealingSpell((HealingSpell) spell);

		assertEquals(
				"In all Tasks, if GryffindorTrait is activated it should allow the champion to cast a spell and update the IP",
				newChampionIp, g.getIp());

		assertEquals(
				"In all Tasks, if GryffindorTrait is activated it should allow the champion to cast a spell and update the Cooldown",
				initSpellCooldown, spell.getCoolDown());

		assertEquals(
				"In all Tasks, when GryffindorTrait is activated the current champion should be updated only after finishing his/her two actions",
				g, task2.getCurrentChamp());

		int newChampPosX = champPosX;
		int newChampPosY = champPosY;
		int randomMove = (int) (4 * Math.random());
		switch (randomMove) {
		case 0:
			task2.moveForward();
			newChampPosX -= 1;
			break;
		case 1:
			task2.moveBackward();
			newChampPosX += 1;
			break;
		case 2:
			task2.moveLeft();
			newChampPosY -= 1;
			break;
		case 3:
			task2.moveRight();
			newChampPosY += 1;
			break;
		}

		if (task2.getMap()[newChampPosX][newChampPosY] instanceof ChampionCell) {
			assertEquals(
					"In all Tasks, when GryffindorTrait is activated the champion's location in the map should be updated correctly after using his/her two actions",
					g,
					((ChampionCell) task2.getMap()[newChampPosX][newChampPosY])
							.getChamp());
		} else {

			fail("In all Tasks, if GryffindorTrait is activated it should allow the champion to do two actions in his/her turn");
		}

		assertEquals(
				"In all Tasks, when GryffindorTrait is activated the champion location should be updated correctly after using his/her two actions",
				new Point(newChampPosX, newChampPosY), g.getLocation());

	}

	@Test(timeout = 1000)
	public void testOnGryffindorTraitTaskScenario5() throws Exception {

		for (int dh = 0; dh < 1000; dh++) {

			testMethodExistsInClass(Task.class, "onGryffindorTrait", true,
					Void.TYPE);

			ArrayList<Champion> e = new ArrayList<>();
			GryffindorWizard g = new GryffindorWizard("gryff");
			HufflepuffWizard h = new HufflepuffWizard("huff");
			RavenclawWizard r = new RavenclawWizard("raven");
			SlytherinWizard s = new SlytherinWizard("slyth");
			e.add(g);
			e.add(h);
			e.add(r);
			e.add(s);

			FirstTask task1 = new FirstTask(e);
			task1.getChampions().clear();
			task1.getChampions().add(g);
			task1.getChampions().add(h);
			task1.getChampions().add(r);
			task1.getChampions().add(s);

			Field map = Task.class.getDeclaredField("map");
			map.setAccessible(true);
			map.set(task1, new Cell[10][10]);
			Cell[][] taskMap = task1.getMap();
			for (int i = 0; i < taskMap.length; i++) {
				for (int j = 0; j < taskMap[i].length; j++) {
					taskMap[i][j] = new EmptyCell();
				}
			}
			int champPosX = 6;
			int champPosY = 6;

			task1.getMap()[champPosX][champPosY] = new ChampionCell(g);
			g.setLocation(new Point(champPosX, champPosY));
			task1.setCurrentChamp(g);

			g.useTrait();
			int newChampPosX = champPosX;
			int newChampPosY = champPosY;

			int randomMove = (int) (4 * Math.random());
			switch (randomMove) {
			case 0: {

				champPosX = 5;
				champPosY = 4;
				task1.getMap()[champPosX][champPosY] = new ChampionCell(g);
				g.setLocation(new Point(champPosX, champPosY));

				task1.moveForward();
				newChampPosX -= 1;

			}
				break;
			case 1: {

				champPosX = 3;
				champPosY = 4;
				task1.getMap()[champPosX][champPosY] = new ChampionCell(g);
				g.setLocation(new Point(champPosX, champPosY));

				task1.moveBackward();
				newChampPosX += 1;

			}
				break;

			case 2: {

				champPosX = 4;
				champPosY = 5;
				task1.getMap()[champPosX][champPosY] = new ChampionCell(g);
				g.setLocation(new Point(champPosX, champPosY));

				task1.moveLeft();
				newChampPosY -= 1;

			}
				break;
			case 3: {

				champPosX = 4;
				champPosY = 3;
				task1.getMap()[champPosX][champPosY] = new ChampionCell(g);
				g.setLocation(new Point(champPosX, champPosY));

				task1.moveRight();
				newChampPosY += 1;

			}
				break;

			}

			assertTrue(
					"In FirstTask, when GryffindorTrait is activated and the champion moves to cell (4,4), he/she is considered a winner and thus should be added to the winners list ",
					task1.getWinners().contains(g));

			assertFalse(
					"In FirstTask, when GryffindorTrait is activated and the champion moves to cell (4,4), he/she is considered a winner and thus should be removed from the champions list ",
					task1.getChampions().contains(g));

			assertNotEquals(
					"In FirstTask, when GryffindorTrait is activated and the champion moves to cell (4,4), he/she should no longer be the current champ",
					g, task1.getCurrentChamp());

			assertEquals(
					"In FirstTask, when GryffindorTrait is activated and the champion moves to cell (4,4) and wins, the egg cell should remain Empty",
					EmptyCell.class,
					task1.getMap()[newChampPosX][newChampPosY].getClass());

			assertEquals(
					"In all Tasks, when GryffindorTrait is activated and the champion moves from a cell, it should be converted to an EmptyCell ",
					EmptyCell.class,
					task1.getMap()[champPosX][champPosY].getClass());

		}
	}

	@Test(timeout = 100)
	public void testOnGryffTraitScenarioFirstTaskIsNotMove() throws Exception {

		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(s);
		e.add(h);
		e.add(r);
		e.add(g);
		FirstTask task1 = new FirstTask(e);
		task1.getChampions().clear();
		task1.getChampions().add(g);
		task1.getChampions().add(h);
		task1.getChampions().add(r);
		task1.getChampions().add(s);

		Field map = Task.class.getDeclaredField("map");
		map.setAccessible(true);
		map.set(task1, new Cell[10][10]);
		Cell[][] taskMap = task1.getMap();
		for (int i = 0; i < taskMap.length; i++) {
			for (int j = 0; j < taskMap[i].length; j++) {
				taskMap[i][j] = new EmptyCell();
			}
		}

		int champPosX = (int) (6 * Math.random()) + 2;
		int champPosY = (int) (6 * Math.random()) + 2;
		task1.getMap()[champPosX][champPosY] = new ChampionCell(g);
		g.setLocation(new Point(champPosX, champPosY));

		task1.setCurrentChamp(g);
		task1.onGryffindorTrait();
		assertEquals(
				"In FirstTask, if Gryffindor champ activated his trait it should not be considered as a move so his turn should not end and the current champion should remain the same",
				g, task1.getCurrentChamp());

	}

	@Test(timeout = 100)
	public void testOnGryffTraitScenarioSecondTaskIsNotMove() throws Exception {

		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(s);
		e.add(h);
		e.add(r);
		e.add(g);
		SecondTask task1 = new SecondTask(e);
		task1.getChampions().clear();
		task1.getChampions().add(g);
		task1.getChampions().add(h);
		task1.getChampions().add(r);
		task1.getChampions().add(s);

		Field map = Task.class.getDeclaredField("map");
		map.setAccessible(true);
		map.set(task1, new Cell[10][10]);
		Cell[][] taskMap = task1.getMap();
		for (int i = 0; i < taskMap.length; i++) {
			for (int j = 0; j < taskMap[i].length; j++) {
				taskMap[i][j] = new EmptyCell();
			}
		}

		int champPosX = (int) (6 * Math.random()) + 2;
		int champPosY = (int) (6 * Math.random()) + 2;
		task1.getMap()[champPosX][champPosY] = new ChampionCell(g);
		g.setLocation(new Point(champPosX, champPosY));

		task1.setCurrentChamp(g);
		task1.onGryffindorTrait();
		assertEquals(
				"In SecondTask, if Gryffindor champ activated his trait it should not be considered as a move so his turn should not end and the current champion should remain the same",
				g, task1.getCurrentChamp());

	}

	@Test(timeout = 100)
	public void testOnGryffTraitScenarioThirdTaskIsNotMove() throws Exception {

		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(s);
		e.add(h);
		e.add(r);
		e.add(g);
		ThirdTask task1 = new ThirdTask(e);
		task1.getChampions().clear();
		task1.getChampions().add(g);
		task1.getChampions().add(h);
		task1.getChampions().add(r);
		task1.getChampions().add(s);

		Field map = Task.class.getDeclaredField("map");
		map.setAccessible(true);
		map.set(task1, new Cell[10][10]);
		Cell[][] taskMap = task1.getMap();
		for (int i = 0; i < taskMap.length; i++) {
			for (int j = 0; j < taskMap[i].length; j++) {
				taskMap[i][j] = new EmptyCell();
			}
		}

		int champPosX = (int) (6 * Math.random()) + 2;
		int champPosY = (int) (6 * Math.random()) + 2;
		task1.getMap()[champPosX][champPosY] = new ChampionCell(g);
		g.setLocation(new Point(champPosX, champPosY));

		task1.setCurrentChamp(g);
		task1.onGryffindorTrait();
		assertEquals(
				"In ThirdTask, if Gryffindor champ activated his trait it should not be considered as a move so his turn should not end and the current champion should remain the same",
				g, task1.getCurrentChamp());

	}

	@Test(timeout = 1000)
	public void testOnHuffelpuffTraitSecondTaskScenario() throws Exception {
		testMethodExistsInClassOrSuperClass(SecondTask.class,
				"onHufflepuffTrait", true, Void.TYPE);

		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");

		e.add(h);
		e.add(g);
		e.add(r);
		e.add(s);
		SecondTask task2 = new SecondTask(e);
		task2.getChampions().clear();
		task2.getChampions().add(h);
		task2.getChampions().add(g);
		task2.getChampions().add(r);
		task2.getChampions().add(s);

		Field map = Task.class.getDeclaredField("map");
		map.setAccessible(true);
		map.set(task2, new Cell[10][10]);
		Cell[][] taskMap = task2.getMap();
		for (int i = 0; i < taskMap.length; i++) {
			for (int j = 0; j < taskMap[i].length; j++) {
				taskMap[i][j] = new EmptyCell();
			}
		}

		int champPosX = (int) (6 * Math.random()) + 2;
		int champPosY = (int) (6 * Math.random()) + 2;
		task2.getMap()[champPosX][champPosY] = new ChampionCell(h);
		h.setLocation(new Point(champPosX, champPosY));
		int hp = (int) ((Math.random() * 101) + 200);
		int dmg = (int) ((Math.random() * 201) + 100);
		task2.getMap()[champPosX - 1][champPosY + 1] = new ObstacleCell(
				new Merperson(hp, dmg));
		task2.getMap()[champPosX][champPosY + 2] = new ObstacleCell(
				new Merperson(hp, dmg));

		task2.setCurrentChamp(h);

		int hOldHp = h.getDefaultHp() - ((int) (4 * Math.random()) + 1) * 150;
		h.setHp(hOldHp);

		task2.onHufflepuffTrait();
		task2.moveRight();

		assertEquals(
				"In SecondTask, if a hufflepuff champion is activating his/her trait the merperson should not attack",
				hOldHp, h.getHp());

	}

	@Test(timeout = 1000)
	public void testOnHufflepuffTraitSecondTaskClass2() throws Exception {
		testMethodExistsInClassOrSuperClass(SecondTask.class,
				"onHufflepuffTrait", true, Void.TYPE);

		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);

		SecondTask task2 = new SecondTask(e);
		task2.getChampions().add(h);
		task2.getChampions().add(g);
		task2.getChampions().add(s);
		task2.getChampions().add(r);
		task2.setCurrentChamp(h);
		int newTraitCooldown = h.getTraitCooldown() + 6;
		task2.onHufflepuffTrait();
		assertEquals(
				"In SecondTask, onHufflepuffTrait method should set the champion's traitCooldown",
				newTraitCooldown, h.getTraitCooldown());

	}

	@Test(timeout = 1000)
	public void testOnHufflepuffTraitThirdTaskClass1() throws Exception {
		testMethodExistsInClassOrSuperClass(ThirdTask.class,
				"onHufflepuffTrait", true, Void.TYPE);

		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);

		ThirdTask task3 = new ThirdTask(e);
		task3.getChampions().add(h);
		task3.getChampions().add(g);
		task3.getChampions().add(r);
		task3.getChampions().add(s);
		task3.setTraitActivated(false);
		task3.setCurrentChamp(h);
		task3.onHufflepuffTrait();
		assertTrue(
				"In ThirdTask, onHufflepuffTrait method should set traitActivated",
				task3.isTraitActivated());

	}

	@Test(timeout = 100)
	public void testOnHuffTraitScenarioFirstTaskIsNotMove() throws Exception {

		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(s);
		e.add(h);
		e.add(r);
		e.add(g);
		FirstTask task1 = new FirstTask(e);
		task1.getChampions().clear();
		task1.getChampions().add(h);
		task1.getChampions().add(g);
		task1.getChampions().add(r);
		task1.getChampions().add(s);

		Field map = Task.class.getDeclaredField("map");
		map.setAccessible(true);
		map.set(task1, new Cell[10][10]);
		Cell[][] taskMap = task1.getMap();
		for (int i = 0; i < taskMap.length; i++) {
			for (int j = 0; j < taskMap[i].length; j++) {
				taskMap[i][j] = new EmptyCell();
			}
		}

		int champPosX = (int) (6 * Math.random()) + 2;
		int champPosY = (int) (6 * Math.random()) + 2;
		task1.getMap()[champPosX][champPosY] = new ChampionCell(h);
		h.setLocation(new Point(champPosX, champPosY));

		task1.setCurrentChamp(h);
		task1.onHufflepuffTrait();

		assertEquals(
				"In FirstTask, if Hufflepuff champ activated his trait it should not be considered as a move so his turn should not end and the current champion should remain the same",
				h, task1.getCurrentChamp());

	}

	@Test(timeout = 100)
	public void testOnHuffTraitScenarioFirstTaskIsNotMove2() throws Exception {
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(s);
		e.add(h);
		e.add(r);
		e.add(g);
		FirstTask task1 = new FirstTask(e);
		task1.getChampions().clear();
		task1.getChampions().add(h);
		task1.getChampions().add(g);
		task1.getChampions().add(r);
		task1.getChampions().add(s);

		Field map = Task.class.getDeclaredField("map");
		map.setAccessible(true);
		map.set(task1, new Cell[10][10]);
		Cell[][] taskMap = task1.getMap();
		for (int i = 0; i < taskMap.length; i++) {
			for (int j = 0; j < taskMap[i].length; j++) {
				taskMap[i][j] = new EmptyCell();
			}
		}

		int champPosX = 6;
		int champPosY = 6;
		task1.getMap()[champPosX][champPosY] = new ChampionCell(h);
		h.setLocation(new Point(champPosX, champPosY));

		task1.setCurrentChamp(h);
		task1.onHufflepuffTrait();
		task1.moveLeft();
		assertTrue(
				"In FirstTask, if Hufflepuff champ activated his trait it should not be considered as a move so can move afterwards",
				taskMap[champPosX][champPosY - 1] instanceof ChampionCell);
		assertEquals(
				"In FirstTask, if Hufflepuff champ activated his trait it should not be considered as a move so can move afterwards",
				h,
				((ChampionCell) taskMap[champPosX][champPosY - 1]).getChamp());

	}

	@Test(timeout = 100)
	public void testOnHuffTraitScenarioFirstTaskIsNotMove3() throws Exception {
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(s);
		e.add(h);
		e.add(r);
		e.add(g);
		FirstTask task1 = new FirstTask(e);
		task1.getChampions().clear();
		task1.getChampions().add(h);
		task1.getChampions().add(g);
		task1.getChampions().add(r);
		task1.getChampions().add(s);

		Field map = Task.class.getDeclaredField("map");
		map.setAccessible(true);
		map.set(task1, new Cell[10][10]);
		Cell[][] taskMap = task1.getMap();
		for (int i = 0; i < taskMap.length; i++) {
			for (int j = 0; j < taskMap[i].length; j++) {
				taskMap[i][j] = new EmptyCell();
			}
		}

		int champPosX = (int) (6 * Math.random()) + 2;
		int champPosY = (int) (6 * Math.random()) + 2;
		task1.getMap()[champPosX][champPosY] = new ChampionCell(h);
		h.setLocation(new Point(champPosX, champPosY));

		task1.setCurrentChamp(h);
		task1.onHufflepuffTrait();
		h.getSpells().clear();
		DamagingSpell spell = new DamagingSpell("DMG", 10, 5, 100);
		h.getSpells().add(spell);
		int champHpBeforeSpell = h.getIp();
		task1.castDamagingSpell(spell, Direction.LEFT);
		assertEquals(
				"In FirstTask, if Hufflepuff champ activated his trait it should not be considered as a move so can cast a spell afterwards",
				h.getIp(), champHpBeforeSpell - 10);

	}

	@Test(timeout = 100)
	public void testOnHuffTraitScenarioSecondTaskIsNotMove() throws Exception {

		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(s);
		e.add(h);
		e.add(r);
		e.add(g);
		SecondTask task1 = new SecondTask(e);
		task1.getChampions().clear();
		task1.getChampions().add(h);
		task1.getChampions().add(g);
		task1.getChampions().add(r);
		task1.getChampions().add(s);

		Field map = Task.class.getDeclaredField("map");
		map.setAccessible(true);
		map.set(task1, new Cell[10][10]);
		Cell[][] taskMap = task1.getMap();
		for (int i = 0; i < taskMap.length; i++) {
			for (int j = 0; j < taskMap[i].length; j++) {
				taskMap[i][j] = new EmptyCell();
			}
		}

		int champPosX = (int) (6 * Math.random()) + 2;
		int champPosY = (int) (6 * Math.random()) + 2;
		task1.getMap()[champPosX][champPosY] = new ChampionCell(h);
		h.setLocation(new Point(champPosX, champPosY));

		task1.setCurrentChamp(h);
		task1.onHufflepuffTrait();
		assertEquals(
				"In SecondTask, if Hufflepuff champ activated his trait it should not be considered as a move so his turn should not end and the current champion should remain the same",
				h, task1.getCurrentChamp());

	}

	@Test(timeout = 100)
	public void testOnHuffTraitScenarioSecondTaskIsNotMove2() throws Exception {
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(s);
		e.add(h);
		e.add(r);
		e.add(g);
		SecondTask task1 = new SecondTask(e);
		task1.getChampions().clear();
		task1.getChampions().add(h);
		task1.getChampions().add(g);
		task1.getChampions().add(r);
		task1.getChampions().add(s);

		Field map = Task.class.getDeclaredField("map");
		map.setAccessible(true);
		map.set(task1, new Cell[10][10]);
		Cell[][] taskMap = task1.getMap();
		for (int i = 0; i < taskMap.length; i++) {
			for (int j = 0; j < taskMap[i].length; j++) {
				taskMap[i][j] = new EmptyCell();
			}
		}

		int champPosX = (int) (6 * Math.random()) + 2;
		int champPosY = (int) (6 * Math.random()) + 2;
		task1.getMap()[champPosX][champPosY] = new ChampionCell(h);
		h.setLocation(new Point(champPosX, champPosY));

		task1.setCurrentChamp(h);
		task1.onHufflepuffTrait();
		task1.moveLeft();
		assertTrue(
				"In SecondTask, if Hufflepuff champ activated his trait it should not be considered as a move so can move afterwards",
				taskMap[champPosX][champPosY - 1] instanceof ChampionCell);
		assertEquals(
				"In SecondTask, if Hufflepuff champ activated his trait it should not be considered as a move so can move afterwards",
				h,
				((ChampionCell) taskMap[champPosX][champPosY - 1]).getChamp());

	}

	@Test(timeout = 100)
	public void testOnHuffTraitScenarioSecondTaskIsNotMove3() throws Exception {
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(s);
		e.add(h);
		e.add(r);
		e.add(g);
		SecondTask task1 = new SecondTask(e);
		task1.getChampions().clear();
		task1.getChampions().add(h);
		task1.getChampions().add(g);
		task1.getChampions().add(r);
		task1.getChampions().add(s);

		Field map = Task.class.getDeclaredField("map");
		map.setAccessible(true);
		map.set(task1, new Cell[10][10]);
		Cell[][] taskMap = task1.getMap();
		for (int i = 0; i < taskMap.length; i++) {
			for (int j = 0; j < taskMap[i].length; j++) {
				taskMap[i][j] = new EmptyCell();
			}
		}

		int champPosX = (int) (6 * Math.random()) + 2;
		int champPosY = (int) (6 * Math.random()) + 2;
		task1.getMap()[champPosX][champPosY] = new ChampionCell(h);
		h.setLocation(new Point(champPosX, champPosY));

		task1.setCurrentChamp(h);
		task1.onHufflepuffTrait();
		h.getSpells().clear();
		HealingSpell spell = new HealingSpell("DMG", 20, 5, 100);
		h.getSpells().add(spell);
		int champHpBeforeSpell = h.getIp();
		task1.castHealingSpell(spell);
		assertEquals(
				"In SecondTask, if Hufflepuff champ activated his trait it should not be considered as a move so can cast a spell afterwards",
				h.getIp(), champHpBeforeSpell - 20);

	}

	@Test(timeout = 100)
	public void testOnHuffTraitScenarioThirdTaskIsNotMove() throws Exception {

		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(s);
		e.add(h);
		e.add(r);
		e.add(g);
		ThirdTask task1 = new ThirdTask(e);
		task1.getChampions().clear();
		task1.getChampions().add(h);
		task1.getChampions().add(g);
		task1.getChampions().add(r);
		task1.getChampions().add(s);

		Field map = Task.class.getDeclaredField("map");
		map.setAccessible(true);
		map.set(task1, new Cell[10][10]);
		Cell[][] taskMap = task1.getMap();
		for (int i = 0; i < taskMap.length; i++) {
			for (int j = 0; j < taskMap[i].length; j++) {
				taskMap[i][j] = new EmptyCell();
			}
		}

		int champPosX = (int) (6 * Math.random()) + 2;
		int champPosY = (int) (6 * Math.random()) + 2;
		task1.getMap()[champPosX][champPosY] = new ChampionCell(h);
		h.setLocation(new Point(champPosX, champPosY));

		task1.setCurrentChamp(h);
		task1.onHufflepuffTrait();
		assertEquals(
				"In ThirdTask, if Hufflepuff champ activated his trait it should not be considered as a move so his turn should not end and the current champion should remain the same",
				h, task1.getCurrentChamp());
	}

	@Test(timeout = 100)
	public void testOnHuffTraitScenarioThirdTaskIsNotMove2() throws Exception {
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(s);
		e.add(h);
		e.add(r);
		e.add(g);
		ThirdTask task1 = new ThirdTask(e);
		task1.getChampions().clear();
		task1.getChampions().add(h);
		task1.getChampions().add(g);
		task1.getChampions().add(r);
		task1.getChampions().add(s);

		Field map = Task.class.getDeclaredField("map");
		map.setAccessible(true);
		map.set(task1, new Cell[10][10]);
		Cell[][] taskMap = task1.getMap();
		for (int i = 0; i < taskMap.length; i++) {
			for (int j = 0; j < taskMap[i].length; j++) {
				taskMap[i][j] = new EmptyCell();
			}
		}

		int champPosX = (int) (6 * Math.random()) + 2;
		int champPosY = (int) (6 * Math.random()) + 2;
		task1.getMap()[champPosX][champPosY] = new ChampionCell(h);
		h.setLocation(new Point(champPosX, champPosY));

		task1.setCurrentChamp(h);
		task1.onHufflepuffTrait();
		task1.moveRight();
		assertTrue(
				"In ThirdTask, if Hufflepuff champ activated his trait it should not be considered as a move so can move afterwards",
				taskMap[champPosX][champPosY + 1] instanceof ChampionCell);
		assertEquals(
				"In ThirdTask, if Hufflepuff champ activated his trait it should not be considered as a move so can move afterwards",
				h,
				((ChampionCell) taskMap[champPosX][champPosY + 1]).getChamp());

	}

	@Test(timeout = 100)
	public void testOnHuffTraitScenarioThirdTaskIsNotMove3() throws Exception {
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(s);
		e.add(h);
		e.add(r);
		e.add(g);
		ThirdTask task1 = new ThirdTask(e);
		task1.getChampions().clear();
		task1.getChampions().add(h);
		task1.getChampions().add(g);
		task1.getChampions().add(r);
		task1.getChampions().add(s);

		Field map = Task.class.getDeclaredField("map");
		map.setAccessible(true);
		map.set(task1, new Cell[10][10]);
		Cell[][] taskMap = task1.getMap();
		for (int i = 0; i < taskMap.length; i++) {
			for (int j = 0; j < taskMap[i].length; j++) {
				taskMap[i][j] = new EmptyCell();
			}
		}

		int champPosX = (int) (6 * Math.random()) + 2;
		int champPosY = (int) (6 * Math.random()) + 2;
		task1.getMap()[champPosX][champPosY] = new ChampionCell(h);
		h.setLocation(new Point(champPosX, champPosY));

		task1.setCurrentChamp(h);
		task1.onHufflepuffTrait();
		h.getSpells().clear();
		RelocatingSpell spell = new RelocatingSpell("DMG", 15, 5, 100);
		h.getSpells().add(spell);
		int champHpBeforeSpell = h.getIp();
		task1.castRelocatingSpell(spell, Direction.LEFT, Direction.BACKWARD, 2);
		assertEquals(
				"In ThirdTask, if Hufflepuff champ activated his trait it should not be considered as a move so can cast a spell afterwards",
				h.getIp(), champHpBeforeSpell - 15);

	}

	@Test(timeout = 1000)
	public void testOnRavenclawTraitInFirstTaskClass1() throws Exception {
		testMethodExistsInClassOrSuperClass(FirstTask.class,
				"onRavenclawTrait", true, Object.class);

		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);

		FirstTask task1 = new FirstTask(e);
		task1.getChampions().clear();
		task1.getChampions().add(g);
		task1.getChampions().add(h);
		task1.getChampions().add(r);
		task1.getChampions().add(s);

		task1.setCurrentChamp(r);
		task1.onRavenclawTrait();
		assertTrue(
				"In FirstTask, onRavenclawTrait method should set traitActivated",
				task1.isTraitActivated());

	}

	@Test(timeout = 1000)
	public void testOnRavenclawTraitInSecondTaskClass3() throws Exception {
		testMethodExistsInClassOrSuperClass(SecondTask.class,
				"onRavenclawTrait", true, Object.class);

		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);

		SecondTask task = new SecondTask(e);
		task.getChampions().add(r);
		task.getChampions().add(g);
		task.getChampions().add(s);
		task.getChampions().add(h);
		Field map = Task.class.getDeclaredField("map");
		map.setAccessible(true);
		map.set(task, new Cell[10][10]);
		Cell[][] taskMap = task.getMap();
		for (int i = 0; i < taskMap.length; i++) {
			for (int j = 0; j < taskMap[i].length; j++) {
				taskMap[i][j] = new EmptyCell();
			}
		}
		int champPosX = (int) (8 * Math.random()) + 1;
		int champPosY = (int) (8 * Math.random()) + 1;
		task.getMap()[champPosX][champPosY] = new ChampionCell(r);
		task.getMap()[champPosX - 1][champPosY - 1] = new TreasureCell(r);
		r.setLocation(new Point(champPosX, champPosY));
		task.setCurrentChamp(r);
		task.onRavenclawTrait();
		assertEquals(
				"In SecondTask, onRavenclawTrait method should set the champion's traitCooldown",
				7, r.getTraitCooldown());

	}

	@SuppressWarnings("unchecked")
	@Test(timeout = 1000)
	public void testOnRavenclawTraitInThirdTaskClass1() throws Exception {
		for (int gh = 0; gh < 1000; gh++) {
			testMethodExistsInClassOrSuperClass(ThirdTask.class,
					"onRavenclawTrait", true, Object.class);

			ArrayList<Champion> e = new ArrayList<>();
			GryffindorWizard g = new GryffindorWizard("gryff");
			HufflepuffWizard h = new HufflepuffWizard("huff");
			RavenclawWizard r = new RavenclawWizard("raven");
			SlytherinWizard s = new SlytherinWizard("slyth");
			e.add(g);
			e.add(h);
			e.add(r);
			e.add(s);

			boolean edge;

			Cell[][] taskMap;
			ThirdTask task;
			do {
				task = new ThirdTask(e);

				taskMap = task.getMap();

				edge = false;
				for (int i = 0; !edge && i < 10; i += 9)
					for (int j = 0; !edge && j < 10; j++)
						if (taskMap[i][j] instanceof CupCell)
							edge = true;
						else if (taskMap[j][i] instanceof CupCell)
							edge = true;
			} while (edge);

			Point cupLocation = new Point();

			for (int i = 1; i < 9; i++) {
				for (int j = 1; j < 9; j++) {
					if (taskMap[i][j] instanceof CupCell)
						cupLocation = new Point(i, j);
					else
						taskMap[i][j] = new EmptyCell();
				}
			}

			int champPosX = cupLocation.x + 1;
			int champPosY = cupLocation.y + 1;
			task.getMap()[champPosX][champPosY] = new ChampionCell(r);

			r.setLocation(new Point(champPosX, champPosY));
			task.setCurrentChamp(r);
			ArrayList<Direction> returnedDirection = (ArrayList<Direction>) task
					.onRavenclawTrait();

			assertEquals(
					"In ThirdTask, onRavenclawTrait should return the directions to the cup cell correctly",
					2, returnedDirection.size());
			assertTrue(
					"In ThirdTask, onRavenclawTrait should return the directions to the cup cell correctly",
					returnedDirection.contains(Direction.LEFT));
			assertTrue(
					"In ThirdTask, onRavenclawTrait should return the directions to the cup cell correctly",
					returnedDirection.contains(Direction.FORWARD));

			r.setTraitCooldown(0);
			task.setTraitActivated(false);
			champPosX = cupLocation.x + 1;
			champPosY = cupLocation.y;
			task.getMap()[champPosX][champPosY] = new ChampionCell(r);
			r.setLocation(new Point(champPosX, champPosY));
			task.setCurrentChamp(r);
			returnedDirection = (ArrayList<Direction>) task.onRavenclawTrait();

			assertEquals(
					"In ThirdTask, onRavenclawTrait should return the directions to the cup cell correctly",
					1, returnedDirection.size());
			assertTrue(
					"In ThirdTask, onRavenclawTrait should return the directions to the cup cell correctly",
					returnedDirection.contains(Direction.FORWARD));

			r.setTraitCooldown(0);
			task.setTraitActivated(false);
			champPosX = cupLocation.x + 1;
			champPosY = cupLocation.y - 1;
			task.getMap()[champPosX][champPosY] = new ChampionCell(r);
			r.setLocation(new Point(champPosX, champPosY));
			task.setCurrentChamp(r);
			returnedDirection = (ArrayList<Direction>) task.onRavenclawTrait();

			assertEquals(
					"In ThirdTask, onRavenclawTrait should return the directions to the cup cell correctly",
					2, returnedDirection.size());
			assertTrue(
					"In ThirdTask, onRavenclawTrait should return the directions to the cup cell correctly",
					returnedDirection.contains(Direction.RIGHT));
			assertTrue(
					"In ThirdTask, onRavenclawTrait should return the directions to the cup cell correctly",
					returnedDirection.contains(Direction.FORWARD));

			r.setTraitCooldown(0);
			task.setTraitActivated(false);
			champPosX = cupLocation.x;
			champPosY = cupLocation.y + 1;
			task.getMap()[champPosX][champPosY] = new ChampionCell(r);
			r.setLocation(new Point(champPosX, champPosY));
			task.setCurrentChamp(r);
			returnedDirection = (ArrayList<Direction>) task.onRavenclawTrait();

			assertEquals(
					"In ThirdTask, onRavenclawTrait should return the directions to the cup cell correctly",
					1, returnedDirection.size());
			assertTrue(
					"In ThirdTask, onRavenclawTrait should return the directions to the cup cell correctly",
					returnedDirection.contains(Direction.LEFT));

			r.setTraitCooldown(0);
			task.setTraitActivated(false);
			champPosX = cupLocation.x;
			champPosY = cupLocation.y - 1;
			task.getMap()[champPosX][champPosY] = new ChampionCell(r);
			r.setLocation(new Point(champPosX, champPosY));
			task.setCurrentChamp(r);
			returnedDirection = (ArrayList<Direction>) task.onRavenclawTrait();

			assertEquals(
					"In ThirdTask, onRavenclawTrait should return the directions to the cup cell correctly",
					1, returnedDirection.size());
			assertTrue(
					"In ThirdTask, onRavenclawTrait should return the directions to the cup cell correctly",
					returnedDirection.contains(Direction.RIGHT));

			r.setTraitCooldown(0);
			task.setTraitActivated(false);
			champPosX = cupLocation.x - 1;
			champPosY = cupLocation.y + 1;
			task.getMap()[champPosX][champPosY] = new ChampionCell(r);
			r.setLocation(new Point(champPosX, champPosY));
			task.setCurrentChamp(r);
			returnedDirection = (ArrayList<Direction>) task.onRavenclawTrait();

			assertEquals(
					"In ThirdTask, onRavenclawTrait should return the directions to the cup cell correctly",
					2, returnedDirection.size());
			assertTrue(
					"In ThirdTask, onRavenclawTrait should return the directions to the cup cell correctly",
					returnedDirection.contains(Direction.LEFT));
			assertTrue(
					"In ThirdTask, onRavenclawTrait should return the directions to the cup cell correctly",
					returnedDirection.contains(Direction.BACKWARD));

			r.setTraitCooldown(0);
			task.setTraitActivated(false);
			champPosX = cupLocation.x - 1;
			champPosY = cupLocation.y;
			task.getMap()[champPosX][champPosY] = new ChampionCell(r);
			r.setLocation(new Point(champPosX, champPosY));
			task.setCurrentChamp(r);
			returnedDirection = (ArrayList<Direction>) task.onRavenclawTrait();

			assertEquals(
					"In ThirdTask, onRavenclawTrait should return the directions to the cup cell correctly",
					1, returnedDirection.size());
			assertTrue(
					"In ThirdTask, onRavenclawTrait should return the directions to the cup cell correctly",
					returnedDirection.contains(Direction.BACKWARD));

			r.setTraitCooldown(0);
			task.setTraitActivated(false);
			champPosX = cupLocation.x - 1;
			champPosY = cupLocation.y - 1;
			task.getMap()[champPosX][champPosY] = new ChampionCell(r);
			r.setLocation(new Point(champPosX, champPosY));
			task.setCurrentChamp(r);
			returnedDirection = (ArrayList<Direction>) task.onRavenclawTrait();

			assertEquals(
					"In ThirdTask, onRavenclawTrait should return the directions to the cup cell correctly",
					2, returnedDirection.size());
			assertTrue(
					"In ThirdTask, onRavenclawTrait should return the directions to the cup cell correctly",
					returnedDirection.contains(Direction.RIGHT));
			assertTrue(
					"In ThirdTask, onRavenclawTrait should return the directions to the cup cell correctly",
					returnedDirection.contains(Direction.BACKWARD));

		}
	}

	@Test(timeout = 100)
	public void testOnRavenTraitScenarioFirstTaskIsNotMove() throws Exception {

		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(s);
		e.add(h);
		e.add(r);
		e.add(g);
		FirstTask task1 = new FirstTask(e);
		task1.getChampions().clear();
		task1.getChampions().add(r);
		task1.getChampions().add(g);
		task1.getChampions().add(h);
		task1.getChampions().add(s);

		Field map = Task.class.getDeclaredField("map");
		map.setAccessible(true);
		map.set(task1, new Cell[10][10]);
		Cell[][] taskMap = task1.getMap();
		for (int i = 0; i < taskMap.length; i++) {
			for (int j = 0; j < taskMap[i].length; j++) {
				taskMap[i][j] = new EmptyCell();
			}
		}

		int champPosX = (int) (6 * Math.random()) + 2;
		int champPosY = (int) (6 * Math.random()) + 2;
		task1.getMap()[champPosX][champPosY] = new ChampionCell(r);
		r.setLocation(new Point(champPosX, champPosY));

		task1.setCurrentChamp(r);
		task1.onRavenclawTrait();
		assertEquals(
				"In FirstTask, if Ravenclaw champ activated his trait it should not be considered as a move so his turn should not end and the current champion should remain the same",
				r, task1.getCurrentChamp());

	}

	@Test(timeout = 100)
	public void testOnRavenTraitScenarioFirstTaskIsNotMove2() throws Exception {
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		RavenclawWizard h = new RavenclawWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(s);
		e.add(r);
		e.add(r);
		e.add(g);
		FirstTask task1 = new FirstTask(e);
		task1.getChampions().clear();
		task1.getChampions().add(r);
		task1.getChampions().add(g);
		task1.getChampions().add(h);
		task1.getChampions().add(s);

		Field map = Task.class.getDeclaredField("map");
		map.setAccessible(true);
		map.set(task1, new Cell[10][10]);
		Cell[][] taskMap = task1.getMap();
		for (int i = 0; i < taskMap.length; i++) {
			for (int j = 0; j < taskMap[i].length; j++) {
				taskMap[i][j] = new EmptyCell();
			}
		}

		int champPosX = 6;
		int champPosY = 6;
		task1.getMap()[champPosX][champPosY] = new ChampionCell(r);
		r.setLocation(new Point(champPosX, champPosY));

		task1.setCurrentChamp(r);
		task1.onRavenclawTrait();
		task1.moveLeft();
		assertTrue(
				"In FirstTask, if Ravenclaw champ activated his trait it should not be considered as a move so can move afterwards",
				taskMap[champPosX][champPosY - 1] instanceof ChampionCell);
		assertEquals(
				"In FirstTask, if Ravenclaw champ activated his trait it should not be considered as a move so can move afterwards",
				r,
				((ChampionCell) taskMap[champPosX][champPosY - 1]).getChamp());

	}

	@Test(timeout = 100)
	public void testOnRavenTraitScenarioFirstTaskIsNotMove3() throws Exception {
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		RavenclawWizard h = new RavenclawWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(s);
		e.add(r);
		e.add(r);
		e.add(g);
		FirstTask task1 = new FirstTask(e);
		task1.getChampions().clear();
		task1.getChampions().add(r);
		task1.getChampions().add(g);
		task1.getChampions().add(h);
		task1.getChampions().add(s);

		Field map = Task.class.getDeclaredField("map");
		map.setAccessible(true);
		map.set(task1, new Cell[10][10]);
		Cell[][] taskMap = task1.getMap();
		for (int i = 0; i < taskMap.length; i++) {
			for (int j = 0; j < taskMap[i].length; j++) {
				taskMap[i][j] = new EmptyCell();
			}
		}

		int champPosX = (int) (6 * Math.random()) + 2;
		int champPosY = (int) (6 * Math.random()) + 2;
		task1.getMap()[champPosX][champPosY] = new ChampionCell(r);
		r.setLocation(new Point(champPosX, champPosY));

		task1.setCurrentChamp(r);
		task1.onRavenclawTrait();
		r.getSpells().clear();
		DamagingSpell spell = new DamagingSpell("DMG", 10, 5, 100);
		r.getSpells().add(spell);
		int champHpBeforeSpell = r.getIp();
		task1.castDamagingSpell(spell, Direction.LEFT);
		assertEquals(
				"In FirstTask, if Ravenclaw champ activated his trait it should not be considered as a move so can cast a spell afterwards",
				r.getIp(), champHpBeforeSpell - 10);

	}

	@Test(timeout = 100)
	public void testOnRavenTraitScenarioSecondTaskIsNotMove() throws Exception {

		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(s);
		e.add(h);
		e.add(r);
		e.add(g);
		SecondTask task1 = new SecondTask(e);
		task1.getChampions().clear();
		task1.getChampions().add(r);
		task1.getChampions().add(g);
		task1.getChampions().add(h);
		task1.getChampions().add(s);

		Field map = Task.class.getDeclaredField("map");
		map.setAccessible(true);
		map.set(task1, new Cell[10][10]);
		Cell[][] taskMap = task1.getMap();
		for (int i = 0; i < taskMap.length; i++) {
			for (int j = 0; j < taskMap[i].length; j++) {
				if (!(taskMap[i][j] instanceof TreasureCell))
					taskMap[i][j] = new EmptyCell();
			}
		}

		int champPosX = (int) (6 * Math.random()) + 2;
		int champPosY = (int) (6 * Math.random()) + 2;
		task1.getMap()[champPosX][champPosY] = new ChampionCell(r);
		r.setLocation(new Point(champPosX, champPosY));

		task1.setCurrentChamp(r);
		task1.onRavenclawTrait();
		assertEquals(
				"In SecondTask, if Ravenclaw champ activated his trait it should not be considered as a move so his turn should not end and the current champion should remain the same",
				r, task1.getCurrentChamp());
	}

	@Test(timeout = 100)
	public void testOnRavenTraitScenarioSecondTaskIsNotMove2() throws Exception {
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		RavenclawWizard h = new RavenclawWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(s);
		e.add(r);
		e.add(r);
		e.add(g);
		SecondTask task1 = new SecondTask(e);
		task1.getChampions().clear();
		task1.getChampions().add(r);
		task1.getChampions().add(g);
		task1.getChampions().add(h);
		task1.getChampions().add(s);

		Field map = Task.class.getDeclaredField("map");
		map.setAccessible(true);
		map.set(task1, new Cell[10][10]);
		Cell[][] taskMap = task1.getMap();
		for (int i = 0; i < taskMap.length; i++) {
			for (int j = 0; j < taskMap[i].length; j++) {
				if (!(taskMap[i][j] instanceof TreasureCell))
					taskMap[i][j] = new EmptyCell();
			}
		}

		int champPosX = (int) (6 * Math.random()) + 2;
		int champPosY = (int) (6 * Math.random()) + 2;
		task1.getMap()[champPosX][champPosY] = new ChampionCell(r);
		r.setLocation(new Point(champPosX, champPosY));

		task1.setCurrentChamp(r);
		task1.onRavenclawTrait();
		task1.moveLeft();
		assertTrue(
				"In SecondTask, if Ravenclaw champ activated his trait it should not be considered as a move so can move afterwards",
				taskMap[champPosX][champPosY - 1] instanceof ChampionCell);
		assertEquals(
				"In SecondTask, if Ravenclaw champ activated his trait it should not be considered as a move so can move afterwards",
				r,
				((ChampionCell) taskMap[champPosX][champPosY - 1]).getChamp());

	}

	@Test(timeout = 100)
	public void testOnRavenTraitScenarioSecondTaskIsNotMove3() throws Exception {
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		RavenclawWizard h = new RavenclawWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(s);
		e.add(r);
		e.add(r);
		e.add(g);
		SecondTask task1 = new SecondTask(e);

		task1.getChampions().clear();
		task1.getChampions().add(r);
		task1.getChampions().add(g);
		task1.getChampions().add(h);
		task1.getChampions().add(s);

		Field map = Task.class.getDeclaredField("map");
		map.setAccessible(true);
		map.set(task1, new Cell[10][10]);
		Cell[][] taskMap = task1.getMap();
		for (int i = 0; i < taskMap.length; i++) {
			for (int j = 0; j < taskMap[i].length; j++) {
				if (!(taskMap[i][j] instanceof TreasureCell))
					taskMap[i][j] = new EmptyCell();
			}
		}

		int champPosX = (int) (6 * Math.random()) + 2;
		int champPosY = (int) (6 * Math.random()) + 2;
		task1.getMap()[champPosX][champPosY] = new ChampionCell(r);
		r.setLocation(new Point(champPosX, champPosY));

		task1.setCurrentChamp(r);
		task1.onRavenclawTrait();
		r.getSpells().clear();
		HealingSpell spell = new HealingSpell("DMG", 20, 5, 100);
		r.getSpells().add(spell);
		int champHpBeforeSpell = r.getIp();
		task1.castHealingSpell(spell);
		assertEquals(
				"In SecondTask, if Ravenclaw champ activated his trait it should not be considered as a move so can cast a spell afterwards",
				r.getIp(), champHpBeforeSpell - 20);

	}

	@Test(timeout = 100)
	public void testOnRavenTraitScenarioThirdTaskIsNotMove() throws Exception {

		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(s);
		e.add(h);
		e.add(r);
		e.add(g);
		ThirdTask task1 = new ThirdTask(e);
		task1.getChampions().clear();
		task1.getChampions().add(r);
		task1.getChampions().add(g);
		task1.getChampions().add(h);
		task1.getChampions().add(s);

		Field map = Task.class.getDeclaredField("map");
		map.setAccessible(true);
		map.set(task1, new Cell[10][10]);
		Cell[][] taskMap = task1.getMap();
		for (int i = 0; i < taskMap.length; i++) {
			for (int j = 0; j < taskMap[i].length; j++) {
				if (!(taskMap[i][j] instanceof CupCell))
					taskMap[i][j] = new EmptyCell();
			}
		}

		int champPosX = (int) (6 * Math.random()) + 2;
		int champPosY = (int) (6 * Math.random()) + 2;
		task1.getMap()[champPosX][champPosY] = new ChampionCell(r);
		r.setLocation(new Point(champPosX, champPosY));

		task1.setCurrentChamp(r);
		task1.onRavenclawTrait();
		assertEquals(
				"In ThirdTask, if Ravenclaw champ activated his trait it should not be considered as a move so his turn should not end and the current champion should remain the same",
				r, task1.getCurrentChamp());
	}

	@Test(timeout = 100)
	public void testOnRavenTraitScenarioThirdTaskIsNotMove2() throws Exception {
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		RavenclawWizard h = new RavenclawWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(s);
		e.add(r);
		e.add(r);
		e.add(g);
		ThirdTask task1 = new ThirdTask(e);
		task1.getChampions().clear();
		task1.getChampions().add(r);
		task1.getChampions().add(g);
		task1.getChampions().add(h);
		task1.getChampions().add(s);

		Field map = Task.class.getDeclaredField("map");
		map.setAccessible(true);
		map.set(task1, new Cell[10][10]);
		Cell[][] taskMap = task1.getMap();
		for (int i = 0; i < taskMap.length; i++) {
			for (int j = 0; j < taskMap[i].length; j++) {
				if (!(taskMap[i][j] instanceof CupCell))
					taskMap[i][j] = new EmptyCell();
			}
		}

		int champPosX = (int) (6 * Math.random()) + 2;
		int champPosY = (int) (6 * Math.random()) + 2;
		task1.getMap()[champPosX][champPosY] = new ChampionCell(r);
		r.setLocation(new Point(champPosX, champPosY));

		task1.setCurrentChamp(r);
		task1.onRavenclawTrait();
		task1.moveRight();
		assertTrue(
				"In ThirdTask, if Ravenclaw champ activated his trait it should not be considered as a move so can move afterwards",
				taskMap[champPosX][champPosY + 1] instanceof ChampionCell);
		assertEquals(
				"In ThirdTask, if Ravenclaw champ activated his trait it should not be considered as a move so can move afterwards",
				r,
				((ChampionCell) taskMap[champPosX][champPosY + 1]).getChamp());

	}

	@Test(timeout = 100)
	public void testOnRavenTraitScenarioThirdTaskIsNotMove3() throws Exception {
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		RavenclawWizard h = new RavenclawWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(s);
		e.add(r);
		e.add(r);
		e.add(g);
		ThirdTask task1 = new ThirdTask(e);
		task1.getChampions().clear();
		task1.getChampions().add(r);
		task1.getChampions().add(g);
		task1.getChampions().add(h);
		task1.getChampions().add(s);

		Field map = Task.class.getDeclaredField("map");
		map.setAccessible(true);
		map.set(task1, new Cell[10][10]);
		Cell[][] taskMap = task1.getMap();
		for (int i = 0; i < taskMap.length; i++) {
			for (int j = 0; j < taskMap[i].length; j++) {
				if (!(taskMap[i][j] instanceof CupCell))
					taskMap[i][j] = new EmptyCell();
			}
		}

		int champPosX = (int) (6 * Math.random()) + 2;
		int champPosY = (int) (6 * Math.random()) + 2;
		task1.getMap()[champPosX][champPosY] = new ChampionCell(r);
		r.setLocation(new Point(champPosX, champPosY));

		task1.setCurrentChamp(r);
		task1.onRavenclawTrait();
		r.getSpells().clear();
		RelocatingSpell spell = new RelocatingSpell("DMG", 15, 5, 100);
		r.getSpells().add(spell);
		int champHpBeforeSpell = r.getIp();
		task1.castRelocatingSpell(spell, Direction.LEFT, Direction.BACKWARD, 2);
		assertEquals(
				"In ThirdTask, if Ravenclaw champ activated his trait it should not be considered as a move so can cast a spell afterwards",
				r.getIp(), champHpBeforeSpell - 15);

	}

	@Test(timeout = 1000)
	public void testOnSlytherinTraitInFirstTaskClass1() throws Exception {
		for (int hg = 0; hg < 1000; hg++) {
			testMethodExistsInClassOrSuperClass(FirstTask.class,
					"onSlytherinTrait", true, Void.TYPE, Direction.class);

			ArrayList<Champion> e = new ArrayList<>();
			GryffindorWizard g = new GryffindorWizard("gryff");
			HufflepuffWizard h = new HufflepuffWizard("huff");
			RavenclawWizard r = new RavenclawWizard("raven");
			SlytherinWizard s = new SlytherinWizard("slyth");
			e.add(g);
			e.add(h);
			e.add(r);
			e.add(s);

			FirstTask task = new FirstTask(e);
			task.getChampions().clear();
			task.getChampions().add(s);
			task.getChampions().add(h);
			task.getChampions().add(g);
			task.getChampions().add(r);

			Field map = Task.class.getDeclaredField("map");
			map.setAccessible(true);
			map.set(task, new Cell[10][10]);
			Cell[][] taskMap = task.getMap();
			for (int i = 0; i < taskMap.length; i++) {
				for (int j = 0; j < taskMap[i].length; j++) {
					taskMap[i][j] = new EmptyCell();
				}
			}
			int champPosX = (int) (6 * Math.random()) + 2;
			int champPosY = (int) (6 * Math.random()) + 2;
			while (champPosX - 2 == 4 && champPosY == 4)
				champPosY = (int) (6 * Math.random()) + 2;
			int newchampPosX = champPosX - 2;
			int newchampPosY = champPosY;
			task.getMap()[champPosX][champPosY] = new ChampionCell(s);
			s.setLocation(new Point(champPosX, champPosY));
			task.setCurrentChamp(s);
			s.setTraitDirection(Direction.FORWARD);
			task.onSlytherinTrait(Direction.FORWARD);

			if (task.getMap()[newchampPosX][newchampPosY] instanceof ChampionCell) {
				assertEquals(
						"In FirstTask, onSlytherinTrait should allow the champion to move two cells in the map at a time and place him correctly",
						s,
						((ChampionCell) task.getMap()[newchampPosX][newchampPosY])
								.getChamp());

			} else {
				fail("In FirstTask, onSlytherinTrait should allow the champion to move two cells in the map at a time");
			}
			assertTrue(
					"In FirstTask, after Slytherin champion uses his/her trait and move, his/her old cell should be empty",
					task.getMap()[champPosX][champPosY] instanceof EmptyCell);
			assertEquals(
					"In FirstTask, after Slytherin champion uses his/her trait and move, his/her location should be updated",
					new Point(newchampPosX, newchampPosY), s.getLocation());

			task = new FirstTask(e);
			task.getChampions().clear();
			task.getChampions().add(s);
			task.getChampions().add(h);
			task.getChampions().add(g);
			task.getChampions().add(r);

			map.set(task, new Cell[10][10]);
			taskMap = task.getMap();
			for (int i = 0; i < taskMap.length; i++) {
				for (int j = 0; j < taskMap[i].length; j++) {
					taskMap[i][j] = new EmptyCell();
				}
			}
			champPosX = (int) (6 * Math.random()) + 2;
			champPosY = (int) (6 * Math.random()) + 2;
			while (champPosX + 2 == 4 && champPosY == 4)
				champPosY = (int) (6 * Math.random()) + 2;
			newchampPosX = champPosX + 2;
			newchampPosY = champPosY;
			task.getMap()[champPosX][champPosY] = new ChampionCell(s);
			s.setLocation(new Point(champPosX, champPosY));
			task.setCurrentChamp(s);
			task.setTraitActivated(false);
			s.setTraitCooldown(0);
			s.setTraitDirection(Direction.BACKWARD);
			task.onSlytherinTrait(Direction.BACKWARD);

			if (task.getMap()[newchampPosX][newchampPosY] instanceof ChampionCell) {
				assertEquals(
						"In FirstTask, onSlytherinTrait should allow the champion to move two cells in the map at a time and place him correctly",
						s,
						((ChampionCell) task.getMap()[newchampPosX][newchampPosY])
								.getChamp());

			} else {
				fail("In FirstTask, onSlytherinTrait should allow the champion to move two cells in the map at a time");
			}

			assertTrue(
					"In FirstTask, after Slytherin champion uses his/her trait and move, his/her old cell should be empty",
					task.getMap()[champPosX][champPosY] instanceof EmptyCell);
			assertEquals(
					"In FirstTask, after Slytherin champion uses his/her trait and move, his/her location should be updated",
					new Point(newchampPosX, newchampPosY), s.getLocation());

			task = new FirstTask(e);
			task.getChampions().clear();
			task.getChampions().add(s);
			task.getChampions().add(h);
			task.getChampions().add(g);
			task.getChampions().add(r);

			map.set(task, new Cell[10][10]);
			taskMap = task.getMap();
			for (int i = 0; i < taskMap.length; i++) {
				for (int j = 0; j < taskMap[i].length; j++) {
					taskMap[i][j] = new EmptyCell();
				}
			}
			champPosX = (int) (6 * Math.random()) + 2;
			champPosY = (int) (6 * Math.random()) + 2;
			while (champPosX == 4 && champPosY - 2 == 4)
				champPosY = (int) (6 * Math.random()) + 2;
			newchampPosX = champPosX;
			newchampPosY = champPosY - 2;
			task.getMap()[champPosX][champPosY] = new ChampionCell(s);
			s.setLocation(new Point(champPosX, champPosY));
			task.setCurrentChamp(s);
			task.setTraitActivated(false);
			s.setTraitCooldown(0);
			s.setTraitDirection(Direction.LEFT);
			task.onSlytherinTrait(Direction.LEFT);

			if (task.getMap()[newchampPosX][newchampPosY] instanceof ChampionCell) {
				assertEquals(
						"In FirstTask, onSlytherinTrait should allow the champion to move two cells in the map at a time and place him correctly",
						s,
						((ChampionCell) task.getMap()[newchampPosX][newchampPosY])
								.getChamp());

			} else {
				fail("In FirstTask, onSlytherinTrait should allow the champion to move two cells in the map at a time");
			}
			assertTrue(
					"In FirstTask, after Slytherin champion uses his/her trait and move, his/her old cell should be empty",
					task.getMap()[champPosX][champPosY] instanceof EmptyCell);
			assertEquals(
					"In FirstTask, after Slytherin champion uses his/her trait and move, his/her location should be updated",
					new Point(newchampPosX, newchampPosY), s.getLocation());

			task = new FirstTask(e);
			task.getChampions().clear();
			task.getChampions().add(s);
			task.getChampions().add(h);
			task.getChampions().add(g);
			task.getChampions().add(r);

			map.set(task, new Cell[10][10]);
			taskMap = task.getMap();
			for (int i = 0; i < taskMap.length; i++) {
				for (int j = 0; j < taskMap[i].length; j++) {
					taskMap[i][j] = new EmptyCell();
				}
			}
			champPosX = (int) (6 * Math.random()) + 2;
			champPosY = (int) (6 * Math.random()) + 2;
			while (champPosX == 4 && champPosY + 2 == 4)
				champPosY = (int) (6 * Math.random()) + 2;
			newchampPosX = champPosX;
			newchampPosY = champPosY + 2;
			task.getMap()[champPosX][champPosY] = new ChampionCell(s);
			s.setLocation(new Point(champPosX, champPosY));
			task.setCurrentChamp(s);
			task.setTraitActivated(false);
			s.setTraitCooldown(0);
			s.setTraitDirection(Direction.RIGHT);
			task.onSlytherinTrait(Direction.RIGHT);

			if (task.getMap()[newchampPosX][newchampPosY] instanceof ChampionCell) {
				assertEquals(
						"In FirstTask, onSlytherinTrait should allow the champion to move two cells in the map at a time and place him correctly",
						s,
						((ChampionCell) task.getMap()[newchampPosX][newchampPosY])
								.getChamp());

			} else {
				fail("In FirstTask, onSlytherinTrait should allow the champion to move two cells in the map at a time");
			}
			assertTrue(
					"In FirstTask, after Slytherin champion uses his/her trait and move, his/her old cell should be empty",
					task.getMap()[champPosX][champPosY] instanceof EmptyCell);
			assertEquals(
					"In FirstTask, after Slytherin champion uses his/her trait and move, his/her location should be updated",
					new Point(newchampPosX, newchampPosY), s.getLocation());
		}
	}

	@Test(timeout = 1000)
	public void testOnSlytherinTraitInSecondTaskClass1() throws Exception {
		testMethodExistsInClassOrSuperClass(SecondTask.class,
				"onSlytherinTrait", true, Void.TYPE, Direction.class);

		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);

		SecondTask task = new SecondTask(e);
		task.getChampions().add(s);
		task.getChampions().add(g);
		task.getChampions().add(r);
		task.getChampions().add(h);
		Field map = Task.class.getDeclaredField("map");
		map.setAccessible(true);
		map.set(task, new Cell[10][10]);
		Cell[][] taskMap = task.getMap();
		for (int i = 0; i < taskMap.length; i++) {
			for (int j = 0; j < taskMap[i].length; j++) {
				taskMap[i][j] = new EmptyCell();
			}
		}
		int champPosX = (int) (6 * Math.random()) + 2;
		int champPosY = (int) (6 * Math.random()) + 2;
		int newchampPosX = champPosX - 2;
		int newchampPosY = champPosY;
		task.getMap()[champPosX][champPosY] = new ChampionCell(s);
		s.setLocation(new Point(champPosX, champPosY));
		task.setCurrentChamp(s);
		task.setTraitActivated(false);
		s.setTraitCooldown(0);
		s.setTraitDirection(Direction.FORWARD);
		task.onSlytherinTrait(Direction.FORWARD);

		if (task.getMap()[newchampPosX][newchampPosY] instanceof ChampionCell) {
			assertEquals(
					"In SecondTask, onSlytherinTrait should allow the champion to move two cells in the map at a time and place him correctly",
					s,
					((ChampionCell) task.getMap()[newchampPosX][newchampPosY])
							.getChamp());

		} else {
			fail("In SecondTask, onSlytherinTrait should allow the champion to move two cells in the map at a time");
		}
		assertTrue(
				"In SecondTask, after Slytherin champion uses his/her trait and move, his/her old cell should be empty",
				task.getMap()[champPosX][champPosY] instanceof EmptyCell);
		assertEquals(
				"In SecondTask, after Slytherin champion uses his/her trait and move, his/her location should be updated",
				new Point(newchampPosX, newchampPosY), s.getLocation());

		task = new SecondTask(e);
		task.getChampions().add(s);
		task.getChampions().add(g);
		task.getChampions().add(r);
		task.getChampions().add(h);
		map.set(task, new Cell[10][10]);
		taskMap = task.getMap();
		for (int i = 0; i < taskMap.length; i++) {
			for (int j = 0; j < taskMap[i].length; j++) {
				taskMap[i][j] = new EmptyCell();
			}
		}
		champPosX = (int) (6 * Math.random()) + 2;
		champPosY = (int) (6 * Math.random()) + 2;
		newchampPosX = champPosX + 2;
		newchampPosY = champPosY;
		task.getMap()[champPosX][champPosY] = new ChampionCell(s);
		s.setLocation(new Point(champPosX, champPosY));
		task.setCurrentChamp(s);
		task.setTraitActivated(false);
		s.setTraitCooldown(0);
		s.setTraitDirection(Direction.BACKWARD);
		task.onSlytherinTrait(Direction.BACKWARD);

		if (task.getMap()[newchampPosX][newchampPosY] instanceof ChampionCell) {
			assertEquals(
					"In SecondTask, onSlytherinTrait should allow the champion to move two cells in the map at a time and place him correctly",
					s,
					((ChampionCell) task.getMap()[newchampPosX][newchampPosY])
							.getChamp());

		} else {
			fail("In SecondTask, onSlytherinTrait should allow the champion to move two cells in the map at a time");
		}
		assertTrue(
				"In SecondTask, after Slytherin champion uses his/her trait and move, his/her old cell should be empty",
				task.getMap()[champPosX][champPosY] instanceof EmptyCell);
		assertEquals(
				"In SecondTask, after Slytherin champion uses his/her trait and move, his/her location should be updated",
				new Point(newchampPosX, newchampPosY), s.getLocation());

		task = new SecondTask(e);
		task.getChampions().add(s);
		task.getChampions().add(g);
		task.getChampions().add(r);
		task.getChampions().add(h);
		map.set(task, new Cell[10][10]);
		taskMap = task.getMap();
		for (int i = 0; i < taskMap.length; i++) {
			for (int j = 0; j < taskMap[i].length; j++) {
				taskMap[i][j] = new EmptyCell();
			}
		}
		champPosX = (int) (6 * Math.random()) + 2;
		champPosY = (int) (6 * Math.random()) + 2;
		newchampPosX = champPosX;
		newchampPosY = champPosY - 2;
		task.getMap()[champPosX][champPosY] = new ChampionCell(s);
		s.setLocation(new Point(champPosX, champPosY));
		task.setCurrentChamp(s);
		task.setTraitActivated(false);
		s.setTraitCooldown(0);
		s.setTraitDirection(Direction.LEFT);
		task.onSlytherinTrait(Direction.LEFT);

		if (task.getMap()[newchampPosX][newchampPosY] instanceof ChampionCell) {
			assertEquals(
					"In SecondTask, onSlytherinTrait should allow the champion to move two cells in the map at a time and place him correctly",
					s,
					((ChampionCell) task.getMap()[newchampPosX][newchampPosY])
							.getChamp());

		} else {
			fail("In SecondTask, onSlytherinTrait should allow the champion to move two cells in the map at a time");
		}
		assertTrue(
				"In SecondTask, after Slytherin champion uses his/her trait and move, his/her old cell should be empty",
				task.getMap()[champPosX][champPosY] instanceof EmptyCell);
		assertEquals(
				"In SecondTask, after Slytherin champion uses his/her trait and move, his/her location should be updated",
				new Point(newchampPosX, newchampPosY), s.getLocation());

		task = new SecondTask(e);
		task.getChampions().add(s);
		task.getChampions().add(g);
		task.getChampions().add(r);
		task.getChampions().add(h);
		map.set(task, new Cell[10][10]);
		taskMap = task.getMap();
		for (int i = 0; i < taskMap.length; i++) {
			for (int j = 0; j < taskMap[i].length; j++) {
				taskMap[i][j] = new EmptyCell();
			}
		}
		champPosX = (int) (6 * Math.random()) + 2;
		champPosY = (int) (6 * Math.random()) + 2;
		newchampPosX = champPosX;
		newchampPosY = champPosY + 2;
		task.getMap()[champPosX][champPosY] = new ChampionCell(s);
		s.setLocation(new Point(champPosX, champPosY));
		task.setCurrentChamp(s);
		task.setTraitActivated(false);
		s.setTraitCooldown(0);
		s.setTraitDirection(Direction.RIGHT);
		task.onSlytherinTrait(Direction.RIGHT);

		if (task.getMap()[newchampPosX][newchampPosY] instanceof ChampionCell) {
			assertEquals(
					"In SecondTask, onSlytherinTrait should allow the champion to move two cells in the map at a time and place him correctly",
					s,
					((ChampionCell) task.getMap()[newchampPosX][newchampPosY])
							.getChamp());

		} else {
			fail("In SecondTask, onSlytherinTrait should allow the champion to move two cells in the map at a time");
		}
		assertTrue(
				"In SecondTask, after Slytherin champion uses his/her trait and move, his/her old cell should be empty",
				task.getMap()[champPosX][champPosY] instanceof EmptyCell);
		assertEquals(
				"In SecondTask, after Slytherin champion uses his/her trait and move, his/her location should be updated",
				new Point(newchampPosX, newchampPosY), s.getLocation());

	}

	@Test(timeout = 1000)
	public void testOnSlytherinTraitInThirdTaskClass3() throws Exception {
		for (int ij = 0; ij < 1000; ij++) {

			testMethodExistsInClassOrSuperClass(ThirdTask.class,
					"onSlytherinTrait", true, Void.TYPE, Direction.class);

			ArrayList<Champion> e = new ArrayList<>();
			GryffindorWizard g = new GryffindorWizard("gryff");
			HufflepuffWizard h = new HufflepuffWizard("huff");
			RavenclawWizard r = new RavenclawWizard("raven");
			SlytherinWizard s = new SlytherinWizard("slyth");
			e.add(s);
			e.add(h);
			e.add(r);
			e.add(g);

			ThirdTask task = new ThirdTask(e);
			task.getChampions().clear();
			task.getChampions().add(s);
			task.getChampions().add(h);
			task.getChampions().add(r);
			task.getChampions().add(g);
			Field map = Task.class.getDeclaredField("map");
			map.setAccessible(true);
			map.set(task, new Cell[10][10]);
			Cell[][] taskMap = task.getMap();
			for (int i = 0; i < taskMap.length; i++) {
				for (int j = 0; j < taskMap[i].length; j++) {
					taskMap[i][j] = new EmptyCell();
				}
			}

			g.setLocation(new Point(1, 1));
			h.setLocation(new Point(2, 2));
			r.setLocation(new Point(3, 3));
			s.setLocation(new Point(5, 5));
			taskMap[1][1] = new ChampionCell(g);
			taskMap[2][2] = new ChampionCell(h);
			taskMap[3][3] = new ChampionCell(r);
			taskMap[5][5] = new ChampionCell(s);

			int champPosX = (int) (6 * Math.random()) + 2;
			int champPosY = (int) (6 * Math.random()) + 2;
			task.getMap()[champPosX][champPosY] = new ChampionCell(s);
			s.setLocation(new Point(champPosX, champPosY));
			task.setCurrentChamp(s);
			task.setTraitActivated(false);
			s.setTraitCooldown(0);
			s.setTraitDirection(Direction.FORWARD);
			task.onSlytherinTrait(Direction.FORWARD);

			task.setCurrentChamp(h);
			task.moveForward();
			task.setCurrentChamp(r);
			task.moveForward();
			task.setCurrentChamp(g);
			task.moveForward();

			assertEquals(
					"In ThirdTask, onSlytherinTrait method should set the champion's traitCooldown",
					9, s.getTraitCooldown());
		}
	}

	@Test(timeout = 100)
	public void testOnSlytherinTraitScenarioFirstTaskIsMove() throws Exception {

		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(s);
		e.add(h);
		e.add(r);
		e.add(g);
		FirstTask task1 = new FirstTask(e);
		task1.getChampions().clear();
		task1.getChampions().add(s);
		task1.getChampions().add(h);
		task1.getChampions().add(r);
		task1.getChampions().add(g);

		Field map = Task.class.getDeclaredField("map");
		map.setAccessible(true);
		map.set(task1, new Cell[10][10]);
		Cell[][] taskMap = task1.getMap();
		for (int i = 0; i < taskMap.length; i++) {
			for (int j = 0; j < taskMap[i].length; j++) {
				taskMap[i][j] = new EmptyCell();
			}
		}

		int champPosX = (int) (6 * Math.random()) + 2;
		int champPosY = (int) (6 * Math.random()) + 2;
		task1.getMap()[champPosX][champPosY] = new ChampionCell(s);
		s.setLocation(new Point(champPosX, champPosY));

		task1.setCurrentChamp(s);
		s.setTraitDirection(Direction.FORWARD);
		task1.onSlytherinTrait(Direction.FORWARD);
		assertNotEquals(
				"In FirstTask, if slytherin champ activated his trait it should be considered as a move so his turn should end and the current champion should be updated",
				s, task1.getCurrentChamp());

	}

	@Test(timeout = 100)
	public void testOnSlytherinTraitScenarioSecondTaskIsMove() throws Exception {

		ArrayList<Champion> e = new ArrayList<Champion>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(s);
		e.add(h);
		e.add(r);
		e.add(g);

		SecondTask task2 = new SecondTask(e);
		task2.getChampions().clear();
		task2.getChampions().add(s);
		task2.getChampions().add(h);
		task2.getChampions().add(r);
		task2.getChampions().add(g);

		Field map = Task.class.getDeclaredField("map");
		map.setAccessible(true);
		map.set(task2, new Cell[10][10]);
		Cell[][] taskMap = task2.getMap();
		for (int i = 0; i < taskMap.length; i++) {
			for (int j = 0; j < taskMap[i].length; j++) {
				taskMap[i][j] = new EmptyCell();
			}
		}

		int champPosX = (int) (6 * Math.random()) + 2;
		int champPosY = (int) (6 * Math.random()) + 2;
		task2.getMap()[champPosX][champPosY] = new ChampionCell(s);
		s.setLocation(new Point(champPosX, champPosY));

		task2.setCurrentChamp(s);
		s.setTraitDirection(Direction.FORWARD);
		task2.onSlytherinTrait(Direction.FORWARD);
		assertNotEquals(
				"In SecondTask, if slytherin champ activated his trait it should be considered as a move so his turn should end and the current champion should be updated",
				s, task2.getCurrentChamp());

	}

	@Test(timeout = 100)
	public void testOnSlytherinTraitScenarioThirdTaskIsMove() throws Exception {

		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(s);
		e.add(h);
		e.add(r);
		e.add(g);
		ThirdTask task3 = new ThirdTask(e);
		task3.getChampions().add(s);
		task3.getChampions().add(h);
		task3.getChampions().add(r);
		task3.getChampions().add(g);
		Field map = Task.class.getDeclaredField("map");
		map.setAccessible(true);
		map.set(task3, new Cell[10][10]);
		Cell[][] taskMap = task3.getMap();
		for (int i = 0; i < taskMap.length; i++) {
			for (int j = 0; j < taskMap[i].length; j++) {
				taskMap[i][j] = new EmptyCell();
			}
		}

		int champPosX = (int) (6 * Math.random()) + 2;
		int champPosY = (int) (6 * Math.random()) + 2;
		task3.getMap()[champPosX][champPosY] = new ChampionCell(s);
		s.setLocation(new Point(champPosX, champPosY));

		task3.setCurrentChamp(s);
		s.setTraitDirection(Direction.FORWARD);
		task3.onSlytherinTrait(Direction.FORWARD);
		assertNotEquals(
				"In ThirdTask, if slytherin champ activated his trait it should be considered as a move so his turn should end and the current champion should be updated",
				s, task3.getCurrentChamp());

	}

	@Test(timeout = 1000)
	public void testRavenclawWizardNotifiesListener() throws Exception {

		testMethodExistsInClassOrSuperClass(FirstTask.class,
				"onRavenclawTrait", true, Object.class);
		testMethodExistsInClassOrSuperClass(SecondTask.class,
				"onRavenclawTrait", true, Object.class);
		testMethodExistsInClassOrSuperClass(ThirdTask.class,
				"onRavenclawTrait", true, Object.class);

		testMethodExistsInClass(RavenclawWizard.class, "useTrait", true,
				Void.TYPE);

		hasCalled = false;
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(s);
		e.add(h);
		e.add(r);
		e.add(g);
		FirstTask task = new FirstTask(e) {
			public Object onRavenclawTrait() {
				hasCalled = true;
				return null;
			}
		};

		r.setListener(null);
		try {
			r.useTrait();
		} catch (NullPointerException ee) {
			fail("The method useTrait in RavenclawWizard class should handle when listener is null");
		}

		r.setListener(task);
		r.useTrait();
		assertTrue(
				"FirstTask, RavenclawWizard class should notify the Task class when the champion uses a special trait",
				hasCalled);

		hasCalled = false;
		e = new ArrayList<>();
		g = new GryffindorWizard("gryff");
		h = new HufflepuffWizard("huff");
		r = new RavenclawWizard("raven");
		s = new SlytherinWizard("slyth");
		e.add(s);
		e.add(h);
		e.add(r);
		e.add(g);
		SecondTask task2 = new SecondTask(e) {
			public Object onRavenclawTrait() {
				hasCalled = true;
				return null;
			}
		};

		r.setListener(null);
		try {
			r.useTrait();
		} catch (NullPointerException ee) {
			fail("The method useTrait in RavenclawWizard class should handle when listener is null");
		}

		r.setListener(task2);
		r.useTrait();
		assertTrue(
				"SecondTask, RavenclawWizard class should notify the Task class when the champion uses a special trait",
				hasCalled);

		hasCalled = false;
		e = new ArrayList<>();
		g = new GryffindorWizard("gryff");
		h = new HufflepuffWizard("huff");
		r = new RavenclawWizard("raven");
		s = new SlytherinWizard("slyth");
		e.add(s);
		e.add(h);
		e.add(r);
		e.add(g);
		ThirdTask task3 = new ThirdTask(e) {
			public Object onRavenclawTrait() {
				hasCalled = true;
				return null;
			}
		};

		r.setListener(null);
		try {
			r.useTrait();
		} catch (NullPointerException ee) {
			fail("The method useTrait in RavenclawWizard class should handle when listener is null");
		}

		r.setListener(task3);
		r.useTrait();
		assertTrue(
				"ThirdTask, RavenclawWizard class should notify the Task class when the champion uses a special trait",
				hasCalled);

	}

	@Test(timeout = 1000)
	public void testSecondTaskClassHandlesNullListenerWhenFourChampionsWin()
			throws Exception {
		testMethodExistsInClass(Tournament.class, "onFinishingSecondTask",
				true, Void.TYPE, ArrayList.class);
		hasCalled = false;
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(s);
		e.add(h);
		e.add(r);
		e.add(g);
		SecondTask task = new SecondTask(e);

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				task.getMap()[i][j] = new EmptyCell();

		task.setCurrentChamp(s);
		task.getMap()[3][1] = new ChampionCell(s);
		((Wizard) task.getCurrentChamp()).setLocation(new Point(3, 1));
		task.getMap()[3][2] = new TreasureCell(s);

		task.getMap()[1][2] = new ChampionCell(h);
		h.setLocation(new Point(1, 2));
		task.getMap()[2][2] = new TreasureCell(h);

		task.getMap()[5][5] = new ChampionCell(r);
		r.setLocation(new Point(5, 5));
		task.getMap()[5][4] = new TreasureCell(r);

		task.getMap()[5][7] = new ChampionCell(g);
		g.setLocation(new Point(5, 7));
		task.getMap()[6][7] = new TreasureCell(g);

		task.setListener(null);
		try {
			task.moveRight();

			task.setCurrentChamp(h);
			task.moveBackward();

			task.setCurrentChamp(r);
			task.moveLeft();

			task.setCurrentChamp(g);
			task.moveBackward();

		} catch (NullPointerException ee) {
			fail("The SecondTask class should handle when listener is null. It shouldn't notify the listener that the task ended.");
		}
	}

	@Test(timeout = 1000)
	public void testSecondTaskClassNotifiesListenerWhenAllChampionsDie()
			throws Exception {
		testMethodExistsInClass(Tournament.class, "onFinishingSecondTask",
				true, Void.TYPE, ArrayList.class);
		hasCalled = false;
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(s);
		e.add(h);
		e.add(r);
		e.add(g);
		SecondTask task = new SecondTask(e);

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				task.getMap()[i][j] = new EmptyCell();
		Tournament t = new Tournament() {
			public void onFinishingSecondTask(ArrayList<Champion> winners)
					throws IOException {
				hasCalled = true;
				super.onFinishingSecondTask(winners);
				thirdTask = getThirdTask();
				winnersList = winners;
			}
		};

		task.setCurrentChamp(s);
		task.getMap()[3][1] = new ChampionCell(s);
		((Wizard) task.getCurrentChamp()).setLocation(new Point(3, 1));
		task.getMap()[4][2] = new ObstacleCell(new Merperson(100, 1000));

		task.getMap()[1][2] = new ChampionCell(h);
		h.setLocation(new Point(1, 2));
		task.getMap()[2][1] = new ObstacleCell(new Merperson(100, 1000));

		task.getMap()[5][5] = new ChampionCell(r);
		r.setLocation(new Point(5, 5));
		task.getMap()[5][3] = new ObstacleCell(new Merperson(100, 1000));

		task.getMap()[5][7] = new ChampionCell(g);
		g.setLocation(new Point(5, 7));
		task.getMap()[6][6] = new ObstacleCell(new Merperson(100, 1000));

		task.setListener(t);
		task.moveRight();
		assertFalse(
				"ONLY upon the completion of the first task, the FirstTask notifies the Tournament class",
				hasCalled);

		task.setCurrentChamp(h);
		task.moveBackward();
		assertFalse(
				"ONLY upon the completion of the first task, the FirstTask notifies the Tournament class",
				hasCalled);

		task.setCurrentChamp(r);
		task.moveLeft();
		assertFalse(
				"ONLY upon the completion of the first task, the FirstTask notifies the Tournament class",
				hasCalled);

		task.setCurrentChamp(g);
		task.moveBackward();

		assertTrue(
				"Upon completion of the second task, the SecondTask notifies the Tournament class",
				hasCalled);
		assertNull(
				"Upon completion of the second task, the tournament SHOULD NOT intialize the third task as long as there are no winners",
				thirdTask);
		assertTrue(
				"Upon completion of the second task and no champions survived, the SecondTask should send to the Listener an empty ArrayList",
				winnersList.isEmpty());

	}

	@Test(timeout = 1000)
	public void testSecondTaskClassNotifiesListenerWhenFourChampionsWin()
			throws Exception {
		testMethodExistsInClass(Tournament.class, "onFinishingSecondTask",
				true, Void.TYPE, ArrayList.class);
		hasCalled = false;
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(s);
		e.add(h);
		e.add(r);
		e.add(g);
		SecondTask task = new SecondTask(e);

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				task.getMap()[i][j] = new EmptyCell();

		Tournament t = new Tournament() {
			public void onFinishingSecondTask(ArrayList<Champion> winners)
					throws IOException {
				hasCalled = true;
				super.onFinishingSecondTask(winners);
				thirdTask = getThirdTask();
				winnersList = winners;
			}
		};

		task.setCurrentChamp(s);
		task.getMap()[3][1] = new ChampionCell(s);
		((Wizard) task.getCurrentChamp()).setLocation(new Point(3, 1));
		task.getMap()[3][2] = new TreasureCell(s);

		task.getMap()[1][2] = new ChampionCell(h);
		h.setLocation(new Point(1, 2));
		task.getMap()[2][2] = new TreasureCell(h);

		task.getMap()[5][5] = new ChampionCell(r);
		r.setLocation(new Point(5, 5));
		task.getMap()[5][4] = new TreasureCell(r);

		task.getMap()[5][7] = new ChampionCell(g);
		g.setLocation(new Point(5, 7));
		task.getMap()[6][7] = new TreasureCell(g);

		task.setListener(t);
		task.moveRight();
		assertFalse(
				"ONLY upon the completion of the first task, the FirstTask notifies the Tournament class",
				hasCalled);

		task.setCurrentChamp(h);
		task.moveBackward();
		assertFalse(
				"ONLY upon the completion of the first task, the FirstTask notifies the Tournament class",
				hasCalled);

		task.setCurrentChamp(r);
		task.moveLeft();
		assertFalse(
				"ONLY upon the completion of the first task, the FirstTask notifies the Tournament class",
				hasCalled);

		task.setCurrentChamp(g);
		task.moveBackward();

		assertTrue(
				"Upon completion of the second task, the SecondTask notifies the Tournament class",
				hasCalled);
		assertNotNull(
				"Upon completion of the second task, the tournament should intialize the third task as long as there are winners",
				thirdTask);
		boolean condition = winnersList.contains(s) && winnersList.contains(h)
				&& winnersList.contains(r) && winnersList.contains(g);
		assertTrue(
				"Upon completion of the second task, only the champions who survived should be sent as winners to the third task",
				condition);
		assertEquals(
				"Upon completion of the second task, the tournament should intialize the third task and set its listener to the tournament",
				t, thirdTask.getListener());

	}

	@Test(timeout = 1000)
	public void testSlytherinWizardNotifiesListener() throws Exception {

		testMethodExistsInClassOrSuperClass(FirstTask.class,
				"onSlytherinTrait", true, Void.TYPE, Direction.class);
		testMethodExistsInClassOrSuperClass(SecondTask.class,
				"onSlytherinTrait", true, Void.TYPE, Direction.class);
		testMethodExistsInClassOrSuperClass(ThirdTask.class,
				"onSlytherinTrait", true, Void.TYPE, Direction.class);

		testMethodExistsInClass(SlytherinWizard.class, "useTrait", true,
				Void.TYPE);

		hasCalled = false;
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		s.setTraitDirection(Direction.LEFT);
		e.add(s);
		e.add(h);
		e.add(r);
		e.add(g);
		FirstTask task = new FirstTask(e) {
			public void onSlytherinTrait(Direction d) {
				hasCalled = true;
				direction = d;
			}
		};

		s.setListener(null);
		try {
			s.useTrait();
		} catch (NullPointerException ee) {
			fail("The method useTrait in SlytherinWizard class should handle when listener is null");
		}

		s.setListener(task);
		s.useTrait();
		assertTrue(
				"FirstTask, SlytherinWizard class should notify the Task class when the champion uses a special trait",
				hasCalled);

		assertEquals(
				"FirstTask, SlytherinWizard class should send the correct trait direction when notifying the Task class that the champion used a special trait",
				s.getTraitDirection(), direction);

		hasCalled = false;
		e = new ArrayList<>();
		g = new GryffindorWizard("gryff");
		h = new HufflepuffWizard("huff");
		r = new RavenclawWizard("raven");
		s = new SlytherinWizard("slyth");
		s.setTraitDirection(Direction.LEFT);
		e.add(s);
		e.add(h);
		e.add(r);
		e.add(g);
		SecondTask task2 = new SecondTask(e) {
			public void onSlytherinTrait(Direction d) {
				hasCalled = true;
				direction = d;
			}
		};

		s.setListener(null);
		try {
			s.useTrait();
		} catch (NullPointerException ee) {
			fail("The method useTrait in SlytherinWizard class should handle when listener is null");
		}

		s.setListener(task2);
		s.useTrait();
		assertTrue(
				"SecondTask, SlytherinWizard class should notify the Task class when the champion uses a special trait",
				hasCalled);

		assertEquals(
				"SecondTask, SlytherinWizard class should send the correct trait direction when notifying the Task class that the champion used a special trait",
				s.getTraitDirection(), direction);

		hasCalled = false;
		e = new ArrayList<>();
		g = new GryffindorWizard("gryff");
		h = new HufflepuffWizard("huff");
		r = new RavenclawWizard("raven");
		s = new SlytherinWizard("slyth");
		s.setTraitDirection(Direction.LEFT);
		e.add(s);
		e.add(h);
		e.add(r);
		e.add(g);
		ThirdTask task3 = new ThirdTask(e) {
			public void onSlytherinTrait(Direction d) {
				hasCalled = true;
				direction = d;
			}
		};

		s.setListener(null);
		try {
			s.useTrait();
		} catch (NullPointerException ee) {
			fail("The method useTrait in SlytherinWizard class should handle when listener is null");
		}

		s.setListener(task3);
		s.useTrait();
		assertTrue(
				"ThirdTask, SlytherinWizard class should notify the Task class when the champion uses a special trait",
				hasCalled);

		assertEquals(
				"ThirdTask, SlytherinWizard class should send the correct trait direction when notifying the Task class that the champion used a special trait",
				s.getTraitDirection(), direction);

	}

	@Test(timeout = 1000)
	public void testTurnAfterCurrentChampionDieByDragonFire() throws Exception {
		ArrayList<Champion> e = new ArrayList<>();
		SlytherinWizard g = new SlytherinWizard("gryff");
		SlytherinWizard h = new SlytherinWizard("huff");
		SlytherinWizard r = new SlytherinWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(g);
		e.add(h);
		e.add(r);
		e.add(s);
		FirstTask task = new FirstTask(e);
		int expectedDmg = 150;

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				if (!(task.getMap()[i][j] instanceof ChampionCell))
					task.getMap()[i][j] = new EmptyCell();

		Wizard currentChamp = (Wizard) task.getCurrentChamp();

		Champion secondChamp = task.getChampions().get(1);
		Champion thirdChamp = task.getChampions().get(2);
		Champion fourthChamp = task.getChampions().get(3);

		int i, j = 0;
		i = currentChamp.getLocation().x;
		j = currentChamp.getLocation().y;
		Point currentLocation = new Point(0, 0);
		currentChamp.setHp(150);

		int[] pointExist = new int[5];
		task.setCurrentChamp((Champion) currentChamp);

		task.getMarkedCells().clear();

		task.markCells();
		task.getMarkedCells().trimToSize();

		assertEquals("Number of cells in marked cells is incorrect", 2, task
				.getMarkedCells().size());

		int[] Directions = new int[4];
		for (int k = 0; k < 2; k++) {
			int x, y = 0;
			x = (int) task.getMarkedCells().get(k).getX();
			y = (int) task.getMarkedCells().get(k).getY();
			if ((x == i && y == j)) {
				pointExist[0] = 1;
			} else if ((x == (i + 1) && y == j) && i != 9) {
				pointExist[1] = 1;
			} else if ((x == (i - 1) && y == j) && i != 0) {
				pointExist[2] = 1;
			} else if ((x == i && y == (j - 1)) && j != 0) {
				pointExist[3] = 1;
			} else if ((x == i && y == (j + 1)) && j != 9) {
				pointExist[4] = 1;
			}
		}
		for (int k = 0; k < 4; k++)
			Directions[k] = pointExist[k + 1];
		if (currentChamp.getHp() < 150)
			expectedDmg = currentChamp.getHp();

		assertEquals(
				"One or both of the marked cells is in an incorrect location",
				2, sumArray(pointExist));

		int hpOld = currentChamp.getHp();

		if (Directions[0] == 1) {
			task.moveBackward();
			currentLocation = new Point(i + 1, j);
		} else if (Directions[1] == 1) {
			task.moveForward();
			currentLocation = new Point(i - 1, j);

		} else if (Directions[2] == 1) {
			task.moveLeft();
			currentLocation = new Point(i, j - 1);

		} else {
			task.moveRight();
			currentLocation = new Point(i, j + 1);

		}

		assertEquals(
				"The damage inflicted on the champion by the dragon fire is incorrect",
				expectedDmg, hpOld - currentChamp.getHp());

		assertEquals("Hp of the champion should have reached zero", 0,
				currentChamp.getHp());

		assertFalse(
				"The champion arraylist should not contain the dead champion",
				task.getChampions().contains(currentChamp));

		assertTrue(
				"The champion location should be an empty cell",
				task.getMap()[currentLocation.x][currentLocation.y] instanceof EmptyCell);

		assertEquals(
				"The size of the arraylist of champions should be equal to the alive champions only",
				3, task.getChampions().size());

		assertTrue(
				"The update in the order of the champions is incorrect after the champion's death",
				secondChamp == task.getCurrentChamp());

		task.moveForward();

		assertTrue(
				"The update in the order of the champions is incorrect after the champion's death",
				thirdChamp == task.getCurrentChamp());

		task.moveLeft();

		assertTrue(
				"The update in the order of the champions is incorrect after the champion's death",
				fourthChamp == task.getCurrentChamp());

		task.moveRight();

		assertTrue(
				"The update in the order of the champions is incorrect after the champion's death",
				secondChamp == task.getCurrentChamp());

	}

	@Test(timeout = 1000)
	public void testTurnsAlternatingCorrectly() throws Exception {
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(s);
		e.add(h);
		e.add(r);
		e.add(g);
		FirstTask task = new FirstTask(e);

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				if (!(task.getMap()[i][j] instanceof ChampionCell))
					task.getMap()[i][j] = new EmptyCell();

		e = task.getChampions();
		Wizard firstPlayer = (Wizard) e.get(0);
		Wizard secondPlayer = (Wizard) e.get(1);
		Wizard thirdPlayer = (Wizard) e.get(2);
		Wizard fourthPlayer = (Wizard) e.get(3);

		task.moveRight();

		for (int i = 0; i < e.size(); i++) {
			if (i == 0) {
				assertEquals(
						"MoveRight method in class FirstTask is not moving the champion correctly",
						new Point(9, 1), firstPlayer.getLocation());
			}

			else if (i == 1) {
				assertEquals(
						"When the current champion moves, the other players should remain in their places",
						new Point(9, 9), secondPlayer.getLocation());

			} else if (i == 2) {
				assertEquals(
						"When the current champion moves, the other players should remain in their places",
						new Point(0, 9), thirdPlayer.getLocation());

			} else {
				assertEquals(
						"When the current champion moves, the other players should remain in their places",
						new Point(0, 0), fourthPlayer.getLocation());

			}
		}

		assertEquals(
				"FirstTask is not alternating turns correctly between the players",
				secondPlayer, task.getCurrentChamp());

		SecondTask task2 = new SecondTask(e);

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				if (!(task2.getMap()[i][j] instanceof ChampionCell))
					task2.getMap()[i][j] = new EmptyCell();

		e = task2.getChampions();
		firstPlayer = (Wizard) e.get(0);
		secondPlayer = (Wizard) e.get(1);
		thirdPlayer = (Wizard) e.get(2);
		fourthPlayer = (Wizard) e.get(3);

		task2.setCurrentChamp(task2.getChampions().get(0));

		task2.moveRight();

		for (int i = 0; i < e.size(); i++) {
			if (i == 0) {
				assertEquals(
						"MoveRight method in class SecondTask is not moving the champion correctly",
						new Point(9, 1), firstPlayer.getLocation());
			}

			else if (i == 1) {
				assertEquals(
						"When the current champion moves, the other players should remain in their places",
						new Point(9, 9), secondPlayer.getLocation());

			} else if (i == 2) {
				assertEquals(
						"When the current champion moves, the other players should remain in their places",
						new Point(0, 9), thirdPlayer.getLocation());

			} else {
				assertEquals(
						"When the current champion moves, the other players should remain in their places",
						new Point(0, 0), fourthPlayer.getLocation());

			}
		}

		assertEquals(
				"SecondTask is not alternating turns correctly between the players",
				secondPlayer, task2.getCurrentChamp());

	}

	@Test(timeout = 1000)
	public void testWinnersAreSentInCorrectOrderFromSecondTaskToThirdTask()
			throws Exception {
		ArrayList<Champion> e = new ArrayList<>();
		GryffindorWizard g = new GryffindorWizard("gryff");
		HufflepuffWizard h = new HufflepuffWizard("huff");
		RavenclawWizard r = new RavenclawWizard("raven");
		SlytherinWizard s = new SlytherinWizard("slyth");
		e.add(s);
		e.add(h);
		e.add(r);
		e.add(g);
		SecondTask task = new SecondTask(e);
		task.getChampions().clear();

		task.getChampions().add(s);
		task.getChampions().add(h);
		task.getChampions().add(r);
		task.getChampions().add(g);

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				task.getMap()[i][j] = new EmptyCell();

		Tournament t = new Tournament() {
			public void onFinishingSecondTask(ArrayList<Champion> winners)
					throws IOException {
				super.onFinishingSecondTask(winners);
				thirdTask = getThirdTask();
				winnersList = winners;
			}
		};

		task.setCurrentChamp(s);
		task.getMap()[3][1] = new ChampionCell(s);
		((Wizard) task.getCurrentChamp()).setLocation(new Point(3, 1));
		task.getMap()[3][2] = new TreasureCell(s);

		task.getMap()[1][2] = new ChampionCell(h);
		h.setLocation(new Point(1, 2));
		task.getMap()[2][2] = new TreasureCell(h);

		task.getMap()[5][5] = new ChampionCell(r);
		r.setLocation(new Point(5, 5));
		task.getMap()[5][4] = new TreasureCell(r);

		task.getMap()[5][7] = new ChampionCell(g);
		g.setLocation(new Point(5, 7));
		task.getMap()[6][7] = new TreasureCell(g);

		task.setListener(t);
		task.moveRight();

		task.setCurrentChamp(h);
		task.moveBackward();

		task.setCurrentChamp(r);
		task.moveLeft();

		task.setCurrentChamp(g);
		task.moveBackward();

		boolean condition = thirdTask.getChampions().get(0).equals(s)
				&& thirdTask.getChampions().get(1).equals(h)
				&& thirdTask.getChampions().get(2).equals(r)
				&& thirdTask.getChampions().get(3).equals(g);
		assertTrue(
				"The champions playing order in the Third Task depends on the order in which they have finished the second task",
				condition);
	}

	/* Helper methods */
	public int sumArray(int[][] input) {
		int sum = 0;
		for (int i = 0; i < input.length; i++) {
			for (int j = 0; j < input[i].length; j++) {
				sum += input[i][j];
			}
		}
		return sum;
	}

	public int sumArray(int[] input) {
		int sum = 0;
		for (int i = 0; i < input.length; i++) {
			sum += input[i];
		}
		return sum;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void testMethodExistsInClassOrSuperClass(Class aClass,
			String methodName, boolean implementedMethod, Class returnType,
			Class... inputTypes) {

		Method[] methods = aClass.getDeclaredMethods();
		Method[] superMethods = aClass.getSuperclass().getDeclaredMethods();
		if (implementedMethod) {

			assertTrue("The method " + methodName
					+ " should be implemented in " + aClass.getName()
					+ " class or " + aClass.getSuperclass().getName()
					+ " class", containsMethodName(methods, methodName)
					|| containsMethodName(superMethods, methodName));
		} else {
			assertFalse(
					"The "
							+ methodName
							+ " method in class "
							+ aClass.getName()
							+ " should not be implemented, only inherited from super class.",
					containsMethodName(methods, methodName)
							|| containsMethodName(superMethods, methodName));
			return;
		}
		Method m = null;
		boolean found = true;
		try {
			m = aClass.getDeclaredMethod(methodName, inputTypes);
		} catch (Exception e) {
			try {
				m = aClass.getSuperclass().getDeclaredMethod(methodName,
						inputTypes);
			} catch (Exception e1) {
				found = false;
			}
		}

		String inputsList = "";
		for (Class inputType : inputTypes) {
			inputsList += inputType.getSimpleName() + ", ";
		}
		if (inputsList.equals(""))
			assertTrue("The method " + methodName
					+ " should be implemented in " + aClass.getName()
					+ " class or " + aClass.getSuperclass().getName()
					+ " class", found);
		else {
			if (inputsList.charAt(inputsList.length() - 1) == ' ')
				inputsList = inputsList.substring(0, inputsList.length() - 2);
			assertTrue(
					"The method " + methodName + " implemented in "
							+ aClass.getName() + " class or "
							+ aClass.getSuperclass().getName()
							+ " class, should take " + inputsList
							+ " parameter(s)", found);
		}

		assertTrue("incorrect return type for " + methodName + " method in "
				+ aClass.getName() + ".", m.getReturnType().equals(returnType));

	}

	private static boolean containsMethodName(Method[] methods, String name) {
		for (Method method : methods) {
			if (method.getName().equals(name))
				return true;
		}
		return false;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void testMethodExistsInClass(Class aClass, String methodName,
			boolean implementedMethod, Class returnType, Class... inputTypes) {

		Method[] methods = aClass.getDeclaredMethods();

		if (implementedMethod) {
			assertTrue(
					"The " + methodName + " method in class "
							+ aClass.getName() + " should be implemented.",
					containsMethodName(methods, methodName));
		} else {
			assertFalse(
					"The "
							+ methodName
							+ " method in class "
							+ aClass.getName()
							+ " should not be implemented, only inherited from super class.",
					containsMethodName(methods, methodName));
			return;
		}
		Method m = null;
		boolean found = true;
		try {
			m = aClass.getDeclaredMethod(methodName, inputTypes);
		} catch (Exception e) {
			found = false;
		}

		String inputsList = "";
		for (Class inputType : inputTypes) {
			inputsList += inputType.getSimpleName() + ", ";
		}
		if (inputsList.equals(""))
			assertTrue(aClass.getName() + " class should have " + methodName
					+ " method that takes no parameters", found);
		else {
			if (inputsList.charAt(inputsList.length() - 1) == ' ')
				inputsList = inputsList.substring(0, inputsList.length() - 2);
			assertTrue(aClass.getName() + " class should have " + methodName
					+ " method that takes " + inputsList + " parameter(s)",
					found);
		}

		assertTrue("incorrect return type for " + methodName + " method in "
				+ aClass.getName() + ".", m.getReturnType().equals(returnType));

	}

}
