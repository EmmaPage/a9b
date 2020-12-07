package a9;

import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;

public class Chicken extends Zombie{

	public Chicken(Double startingPosition, Double initHitbox, BufferedImage img, int health, int coolDown,
			double speed, int attackDamage) {
		super(startingPosition, initHitbox, img, health, coolDown, -0.5, 14);
	}

}