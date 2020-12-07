package a9;

import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;

public class Dolphin extends Plant {

	public Dolphin(Double startingPosition, Double initHitbox, BufferedImage img, int health, int coolDown,
			int attackDamage) {
		super(startingPosition, initHitbox, img, health*5, coolDown, 3);
	}
	
}