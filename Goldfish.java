package a9;

import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;

public class Goldfish extends Plant {

	public Goldfish(Double startingPosition, Double initHitbox, BufferedImage img, int health, int coolDown,
			int attackDamage) {
		super(startingPosition, initHitbox, img, health, coolDown, 3);
	}
	
}
