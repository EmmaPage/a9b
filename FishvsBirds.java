package a9;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class FishvsBirds extends JPanel implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private Timer timer;
	private ArrayList<Actor> actors; // Plants and zombies all go in here
	BufferedImage plantImage;
	BufferedImage zombieImage;
	BufferedImage goldfishImage;
	BufferedImage dolphinImage;
	BufferedImage cardinalImage;
	BufferedImage chickenImage;
	BufferedImage horseImage;
	int numRows;
	int numCols;
	int cellSize;
	private JButton goldfish;
	private JButton fish;
	private JButton dolphin;
	private int FISH;
	private JLabel horsePower;
	private JLabel horsePower2;
	private JLabel fishCost;
	private JLabel goldfishCost;
	private JLabel dolphinCost;
	private int horseP;
	private int fishCount;

	/**
	 * Setup the basic info for the example
	 */
	public FishvsBirds() {
		super();
		FISH = 0;

		// Define some quantities of the scene
		numRows = 5;
		numCols = 7;
		cellSize = 75;
		setPreferredSize(new Dimension(50 + numCols * cellSize, 175 + numRows * cellSize));

		// Store all the plants and zombies in here.
		actors = new ArrayList<>();

		// Load images
		try {
			plantImage = ImageIO.read(new File("src/a9/fish-icon.png"));
			goldfishImage = ImageIO.read(new File("src/a9/gold-fish-icon.png"));
			dolphinImage = ImageIO.read(new File("src/a9/dolphin-icon.png"));
			zombieImage = ImageIO.read(new File("src/a9/bird-icon.png"));
			cardinalImage = ImageIO.read(new File("src/a9/cardinal-icon.png"));
			chickenImage = ImageIO.read(new File("src/a9/chicken-icon.png"));
			horseImage = ImageIO.read(new File("src/a9/horse-icon.png"));
		} catch (IOException e) {
			System.out.println("A file was not found");
			System.exit(0);
		}

		timer = new Timer(28, this);
		timer.start();

		fish = new JButton(new ImageIcon(plantImage));
		goldfish = new JButton(new ImageIcon(goldfishImage));
		dolphin = new JButton(new ImageIcon(dolphinImage));

		JPanel fishChoice = new JPanel();
		JPanel goldfishChoice = new JPanel();
		JPanel dolphinChoice = new JPanel();
		JPanel horsePowerPanel = new JPanel();

		BoxLayout boxa = new BoxLayout(fishChoice, BoxLayout.PAGE_AXIS);
		BoxLayout boxb = new BoxLayout(goldfishChoice, BoxLayout.PAGE_AXIS);
		BoxLayout boxc = new BoxLayout(dolphinChoice, BoxLayout.PAGE_AXIS);

		fishCost = new JLabel("5 HP");
		goldfishCost = new JLabel("10 HP");
		dolphinCost = new JLabel("25 HP");
		horsePower = new JLabel(new ImageIcon(horseImage));
		horseP = 60; // Starts at 60 to allow some defense (fish) to be placed.
		fishCount = 0;
		horsePower2 = new JLabel();

		addMouseListener(this);

		fishChoice.setLayout(boxa);
		goldfishChoice.setLayout(boxb);
		dolphinChoice.setLayout(boxc);

		fishChoice.add(fish);
		fishChoice.add(fishCost);

		goldfishChoice.add(goldfish);
		goldfishChoice.add(goldfishCost);

		dolphinChoice.add(dolphin);
		dolphinChoice.add(dolphinCost);

		horsePowerPanel.add(horsePower);
		horsePowerPanel.add(horsePower2);

		this.add(fishChoice);
		this.add(goldfishChoice);
		this.add(dolphinChoice);
		this.add(horsePowerPanel);

		fish.addActionListener(this);
		goldfish.addActionListener(this);
		dolphin.addActionListener(this);
	}

	/***
	 * Implement the paint method to draw the plants
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Actor actor : actors) {
			actor.draw(g, 0);
			actor.drawHealthBar(g);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		double rowPlant = -100;
		double colPlant = -100;
		// To place the fish in the center of the mouse clicked area, round to the
		// nearest upper left corner of the grid.
		if (e.getX() < 100 && e.getY() < 100) {
			// If in the first column, only need to round two digits.
			rowPlant = Math.round(0.1 * e.getX()) * 10;
			colPlant = Math.round(0.1 * e.getY()) * 10;
		} else {
			// If in the second column, round to column*100.
			rowPlant = Math.round(0.01 * e.getX()) * 100;
			colPlant = Math.round(0.01 * e.getY()) * 100;
		}

		// According to which fish was chosen by the user, place in appropriate box if
		// horse power is available. Playing field moved down one row to account for the
		// user panel.
		if (FISH == 1 && colPlant >= 100 & horseP > 5) {

			horseP = horseP - 5; // Subtract Horse Power.
			Plant plant = new Plant(new Point2D.Double(rowPlant, colPlant),
					new Point2D.Double(plantImage.getWidth(), plantImage.getHeight()), plantImage, 100, 5, 1);
			actors.add(plant);
			fishCount++; // Count number of fish placed.
		} else if (FISH == 2 && colPlant >= 100 && horseP > 10) {

			horseP = horseP - 10;
			Goldfish goldfish = new Goldfish(new Point2D.Double(rowPlant, colPlant),
					new Point2D.Double(goldfishImage.getWidth(), goldfishImage.getHeight()), goldfishImage, 100, 5, 1);
			actors.add(goldfish);
			fishCount++;
		} else if (FISH == 3 && colPlant >= 100 && horseP > 25) {
			horseP = horseP - 25;
			Dolphin dolphin = new Dolphin(new Point2D.Double(rowPlant, colPlant),
					new Point2D.Double(dolphinImage.getWidth(), dolphinImage.getHeight()), dolphinImage, 100, 5, 1);
			actors.add(dolphin);
			fishCount++;
		}

	}

	/**
	 * 
	 * This is triggered by the timer. It is the game loop of this test.
	 * 
	 * @param e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		// For use in placing fish, generate a value respective of the button pressed
		// (or fish chosen by the user).
		if (e.getSource() == fish) {
			FISH = 1;
		} else if (e.getSource() == goldfish) {
			FISH = 2;
		} else if (e.getSource() == dolphin) {
			FISH = 3;
		}

		int randNum = new Random().nextInt(100);

		// Show horse power value on the frame.
		horsePower2.setText("Horse Power: " + horseP);
		// Generate horse power at random.
		if (randNum < 5) {
			horseP++;
		}

		// Basic blue birds enter the screen at random.
		if (randNum > 95 && randNum < 97) {
			double rowZombie = new Random().nextInt(5) + 1;
			rowZombie = rowZombie * 100;
			Zombie zombie = new Zombie(new Point2D.Double(500, rowZombie),
					new Point2D.Double(plantImage.getWidth(), plantImage.getHeight()), zombieImage, 100, 50, -1, 10);

			actors.add(zombie);
		}

		// Faster birds enter the screen at random.
		if (randNum > 20 && randNum < 22) {
			double rowZombie = new Random().nextInt(3) + 1;
			rowZombie = rowZombie * 100;
			Cardinal cardinal = new Cardinal(new Point2D.Double(500, rowZombie),
					new Point2D.Double(cardinalImage.getWidth(), cardinalImage.getHeight()), cardinalImage, 100, 50, -1,
					10);

			actors.add(cardinal);
		}
		// Game gets harder after ten plants have been placed. Chickens, with much
		// larger health, are able to enter the screen at random.
		if (fishCount >= 10 && randNum > 3.1 && randNum < 4.1) {
			double rowZombie = new Random().nextInt(3) + 1;
			rowZombie = rowZombie * 100;
			Chicken chicken = new Chicken(new Point2D.Double(500, rowZombie),
					new Point2D.Double(chickenImage.getWidth(), chickenImage.getHeight()), chickenImage, 100, 50, -1,
					10);

			actors.add(chicken);
		}

		// This method is getting a little long, but it is mostly loop code
		// Increment their cooldowns and reset collision status

		for (Actor actor : actors) {
			actor.update();
		}

		// Try to attack
		for (Actor actor : actors) {
			for (Actor other : actors) {
				actor.attack(other);
			}
		}

		// Remove plants and zombies with low health
		ArrayList<Actor> nextTurnActors = new ArrayList<>();
		for (Actor actor : actors) {
			if (actor.isAlive())
				nextTurnActors.add(actor);
			else
				actor.removeAction(actors); // any special effect or whatever on removal
		}
		actors = nextTurnActors;

		// Check for collisions between zombies and plants and set collision status
		for (Actor actor : actors) {
			for (Actor other : actors) {
				actor.setCollisionStatus(other);
			}
		}

		// Move the actors.
		for (Actor actor : actors) {
			actor.move(); // for Zombie, only moves if not colliding.
		}

		// Game Over when a bird reaches the far left side of the screen.
		for (Actor actor : actors) {
			if (actor.position.getX() < 0.0) {
				horseP = 0;
				horsePower2.setText("GAME OVER.");
				horsePower2.setForeground(Color.RED);
				horsePower2.setFont(horsePower2.getFont().deriveFont(20.0f));
				timer.stop();
			}
		}

		// Redraw the new scene
		repaint();
	}

	/**
	 * Make the game and run it
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame app = new JFrame("Plant and Zombie Test");
				app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				FishvsBirds panel = new FishvsBirds();

				app.setContentPane(panel);
				app.pack();
				app.setVisible(true);
			}
		});
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}