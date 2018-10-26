package Project;

import java.util.ArrayList;

import framework.RWT.RWTFrame3D;
import framework.RWT.RWTVirtualController;
import framework.game2D.Ground2D;
import framework.game2D.OvergroundActor2D;
import framework.game2D.Velocity2D;
import framework.gameMain.SimpleActionGame;
import framework.model3D.Universe;

public class TemplateAction2D extends SimpleActionGame {
	private Player player;
	private ArrayList<Enemy> enemies;
	private Enemy enemy;
	private Enemy_sky enemy2;

	private Ground2D stage;
	private Ground2D haikei;
	private int displayStatus=0;
	private int dispcnt=0;



	// ���ƂŐ݌v�ύX
	// Enemy�N���X�ł��̒l���g���������߁B
	public static final int RANGE = 30;
	public static final int ENEMYNUM = 5;

	// �v���C���[�̌��݂̑��x����������O���[�o���ϐ�
	private Velocity2D curV;

	@Override
	public void init(Universe universe) {
		player = new Player();
		player.setPosition(0.0, 0.0);
		player.setDirection(0.0, 0.0);


		enemies= new ArrayList<Enemy>();
		for(int i=0; i<ENEMYNUM; i++) {
			enemy = new Enemy();
			enemy.setPosition(i, 5.0);
			enemy.setDirection(1.0, 0.0);
			enemies.add(enemy);

		}
		enemy2 = new Enemy_sky();
		enemy2.setPosition(0.0, 5.0);
		enemy2.setDirection(1.0, 0.0);


		// �X�e�[�W��3D�f�[�^��ǂݍ��ݔz�u����
		stage = new Ground2D("data\\stage3\\stage3.wrl",
				"data\\images\\m101.jpg", windowSizeWidth, windowSizeHeight);
		//universe.place(stage);

		haikei = new Ground2D(null,
				"data\\images\\haikei.jpg", windowSizeWidth, windowSizeHeight);

		// �\���͈͂����߂�i���オ���_�Ƃ��Ă��̌��_���畝�A�������v�Z����j
		setViewRange(RANGE, RANGE);
	}

	@Override
	public RWTFrame3D createFrame3D() {
		// TODO Auto-generated method stub
		RWTFrame3D f = new RWTFrame3D();
		f.setSize(800, 800);
		// f.setExtendedState(Frame.MAXIMIZED_BOTH);
		f.setTitle("Template for Action 2DGame");
		return f;
	}

	@Override
	public void progress(RWTVirtualController virtualController, long interval) {
		if(dispcnt==0) {
			universe.place(haikei);
			dispcnt=1;
		}
		if(virtualController.isKeyDown(0, RWTVirtualController.DOWN)) {
			displayStatus=1;
		}
		if (displayStatus==0) {


		}else {
			if(dispcnt==1) {
				universe.displace(haikei);
				universe.place(stage);

				universe.place(player); // universe�ɒu���B��Ŏ�菜����悤�ɃI�u�W�F�N�g��z�u����B
				for(int i=0;i<ENEMYNUM;i++) {
					universe.place(enemies.get(i)); // universe�ɒu���B��Ŏ�菜����悤�ɃI�u�W�F�N�g��z�u����B
				}
				universe.place(enemy2);
				dispcnt=2;
			}



			curV = player.getVelocity();

			// �Î~��Ԃ̓v���C���[��x��0�ɂ���B�i��Ŋ����čs���Ă��܂����߁j
			curV.setX(0.0);
			player.setVelocity(curV);

			// �L�[����̏���
			// ��
			if (virtualController.isKeyDown(0, RWTVirtualController.LEFT)) {
				player.movePositionLeft(0.05);
			}
			// �E
			else if (virtualController.isKeyDown(0, RWTVirtualController.RIGHT)) {
				player.movePositionRight(0.05);
			}
			// ��
			if (virtualController.isKeyDown(0, RWTVirtualController.UP)) {
				// �W�����v
				if (player.isOnGround()) {
					curV.setY(10.0);
					player.setVelocity(curV);
				}
			}
			// ��
			else if (virtualController.isKeyDown(0, RWTVirtualController.DOWN)) {
				player.movePositionDown(0.01);
			}

			if (virtualController.isKeyDown(0, RWTVirtualController.BUTTON_B)) {
			}


			if (player.getPosition().getY() < -RANGE / 2.0) {
				player.setPosition(0.0, 0.0);
			}

			player.motion(interval, stage);
			enemy2.motion(interval, stage, player);
			for(int i=0;i<ENEMYNUM;i++) {
				enemies.get(i).motion(interval, stage, player);
			}


			// �Փ˔���i�v���C���[�ƓG�j
			if (player.checkCollision(enemy2)) {
				System.out.println("�G�ɐڐG�����I");
			}

		}
	}

	/**
	 * �Q�[���̃��C��
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		TemplateAction2D game = new TemplateAction2D();
		game.setFramePolicy(5, 33, false);
		game.start();
	}

	@Override
	public OvergroundActor2D getOvergroundActor() {
		return player;
	}

}
