package Project;

import javax.vecmath.Vector2d;

import framework.game2D.Ground2D;
import framework.game2D.OvergroundActor2D;
import framework.game2D.Velocity2D;

/**
 * 敵のクラス
 *
 * @author H.Kotobuki
 *
 */
public class Enemy_sky extends OvergroundActor2D {
	private static final double ENEMY_SPEED = 1.0;
	private boolean motionFlag=false;


	@Override
	public String getAnimationFileName() {
		return null;
	}

	@Override
	//ここに画像読み込み
	public String getModelFileName() {
		return "data\\character\\enemy2.obj";
	}

	public void motion(long interval, Ground2D ground, Player player) {
		Vector2d v = player.getPosition().getVector2d();
		v.sub(getPosition().getVector2d());
		v.normalize();
		v.scale(ENEMY_SPEED);
		setVelocity(new Velocity2D(v));
		super.motion(interval, ground);
	}

	public void setmotionFlag(boolean flag) {
		motionFlag=flag;
	}

	public boolean getmotionFlag() {
		return motionFlag;
	}

}
