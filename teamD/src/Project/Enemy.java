package Project;

import framework.game2D.Ground2D;
import framework.game2D.OvergroundActor2D;
import framework.game2D.Velocity2D;

/**
 * “G‚ÌƒNƒ‰ƒX
 *
 * @author H.Kotobuki
 *
 */
public class Enemy extends OvergroundActor2D {
	//private static final double ENEMY_SPEED = 5.0;

	@Override
	public String getAnimationFileName() {
		return null;

	}

	@Override
	public String getModelFileName() {
		return "data\\Head4.wrl";
	}

	public void motion(long interval, Ground2D ground, Player player) {
		Velocity2D curv;
		curv = this.getVelocity();
		curv.setX(0.0);
		this.setVelocity(curv);

		if (this.isOnGround()) {
			curv.setY(10.0);
			this.setVelocity(curv);

		}

		this.movePositionLeft(0.02);
		super.motion(interval, ground);
	}
}


