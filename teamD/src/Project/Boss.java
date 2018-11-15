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

public class Boss extends OvergroundActor2D {
	//private static final double ENEMY_SPEED = 5.0;
	private int time = 0;
	private int flag;

	private boolean motionFlag = false;

	@Override
	public String getAnimationFileName() {
		return null;

	}

	@Override
	public String getModelFileName() {
		return "data\\character\\enemy1.obj";
	}

	public void motion(long interval, Ground2D ground) {
		time += interval;
		Velocity2D curv;
		curv = this.getVelocity();
		curv.setX(0.0);
		this.setVelocity(curv);
		if (time > 500) {
			flag = (int) (6 * Math.random());
			time = 0;
		}
		if (flag == 0) {
			curv.setX(2.0);
		}else if(flag==1) {
			curv.setX(-2.0);
		}else if(flag==2&&this.isOnGround()) {
			curv.setY(15.0);
		}else if(flag==3&&this.isOnGround()) {
			curv.setY(15.0);
			curv.setX(3.0);
		}else if(flag==4&&this.isOnGround()){
			curv.setY(15.0);
			curv.setX(-3.0);
		}else if(flag==5&&!this.isOnGround()){
			curv.setY(-30.0);
		}

		this.setVelocity(curv);



		super.motion(interval, ground);
	}

	public void setmotionFlag(boolean flag) {
		motionFlag = flag;
	}

	public boolean getmotionFlag() {
		return motionFlag;
	}

}
