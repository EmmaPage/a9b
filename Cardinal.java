package a9;

import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;

public class Cardinal extends Zombie{

	public Cardinal(Double startingPosition, Double initHitbox, BufferedImage img, int health, int coolDown,
			double speed, int attackDamage) {
		super(startingPosition, initHitbox, img, health, coolDown, -2, attackDamage);
	}

}
