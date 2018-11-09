package Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import framework.RWT.RWTContainer;
import framework.RWT.RWTFrame3D;
import framework.RWT.RWTVirtualController;
import framework.game2D.Ground2D;
import framework.game2D.OvergroundActor2D;
import framework.game2D.Velocity2D;
import framework.gameMain.IGameState;
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
	private int enemycnt=0;
	private int enemyX[]=new int[ENEMYNUM];
	private int enemyY[]=new int[ENEMYNUM];


	// ���ƂŐ݌v�ύX
	// Enemy�N���X�ł��̒l���g���������߁B
	public static final int RANGE = 30;
	public static final int ENEMYNUM = 5;

	// �v���C���[�̌��݂̑��x����������O���[�o���ϐ�
	private Velocity2D curV;

	private IGameState initialGameState = null;
	private IGameState finalGameState = null;
	private IGameState GameOverGameState = null;

	public TemplateAction2D() {
		super();
		initialGameState = new IGameState() {
			@Override
			public void init(RWTFrame3D frame) {
				TemplateAction2D.this.frame = frame;
				RWTContainer container = new StartContainer(TemplateAction2D.this);
				changeContainer(container);
			}
			@Override
			public boolean useTimer() {
				return false;
			}
			@Override
			public void update(RWTVirtualController virtualController, long interval) {
			}
		};
		finalGameState = new IGameState() {
			@Override
			public void init(RWTFrame3D frame) {
				TemplateAction2D.this.frame = frame;
				RWTContainer container = new EndingContainer(TemplateAction2D.this);
				changeContainer(container);
			}
			@Override
			public boolean useTimer() {
				return false;
			}
			@Override
			public void update(RWTVirtualController virtualController, long interval) {
			}
		};
		GameOverGameState = new IGameState() {
			@Override
			public void init(RWTFrame3D frame) {
				TemplateAction2D.this.frame = frame;
				RWTContainer container = new GameOverContainer(TemplateAction2D.this);
				changeContainer(container);
			}
			@Override
			public boolean useTimer() {
				return false;
			}
			@Override
			public void update(RWTVirtualController virtualController, long interval) {
			}
		};
		setCurrentGameState(initialGameState);

	}




	@Override
	public void init(Universe universe) {
		player = new Player();
		player.setPosition(0.0, 0.0);
		player.setDirection(0.0, 0.0);


		File f = new File("data\\\\EnemyPosition\\\\EnemyPosition.txt");

		//�G�̍��W
		try(Scanner sc = new Scanner(f)){

			sc.useDelimiter(",");

			//hasNextLine�Ŏ��̍s�����݂��邩�𔻒肵�܂��B
			for(int i=0; sc.hasNextLine(); i++){

				enemyX[i] = sc.nextInt();
				enemyY[i] = sc.nextInt();

				System.out.println("X:"+enemyX[i]);
				System.out.println("Y:"+enemyY[i]);
				System.out.println();

				sc.nextLine();//���̍s��
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		enemies= new ArrayList<Enemy>();
		for(int i=0; i<ENEMYNUM; i++) {
			enemy = new Enemy();
			enemy.setPosition(enemyX[i], enemyY[i]);
			enemy.setDirection(1.0, 0.0);
			enemies.add(enemy);

		}
		enemy2 = new Enemy_sky();
		enemy2.setPosition(0.0, 5.0);
		enemy2.setDirection(1.0, 0.0);


		// �X�e�[�W��3D�f�[�^��ǂݍ��ݔz�u����
		stage = new Ground2D("data\\stage3\\stage3.wrl",
				"data\\stage\\testhaikei.jpg", windowSizeWidth, windowSizeHeight);

		universe.place(stage);
		universe.place(player);
		for(int i=0;i<ENEMYNUM;i++) {
			universe.place(enemies.get(i)); // universe�ɒu���B��Ŏ�菜����悤�ɃI�u�W�F�N�g��z�u����B
		}
		universe.place(enemy2);



//		haikei = new Ground2D(null,
//				"data\\title\\title.jpg", windowSizeWidth, windowSizeHeight);

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

//		Title();
//		if (virtualController.isKeyDown(0, RWTVirtualController.DOWN)&&displayStatus==0) {
//			displayStatus=1;//�Q�[���X�^�[�g
//
//		}
//
//		if (displayStatus==1) {
			GameSetUp();




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
				GameOver();//(�Q�[���I�[�o�[�j
			}

			player.motion(interval, stage);
			enemy2.motion(interval, stage, player);
			for(int i=0;i<ENEMYNUM;i++) {
				enemies.get(i).motion(interval, stage, player);
			}

//			System.out.println(player.getPosition().getVector2d());

			// �Փ˔���i�v���C���[�ƓG�j

			if (player.checkCollision(enemy2)) {
				System.out.println("�G�ɐڐG�����I");
				GameOver();
			}
			for(int i=0;i<enemies.size(); i++) {
				if (player.checkCollision(enemies.get(i))) {
					GameOver();
				}
			}

//		}
	}

	public  void Title() {
		if(dispcnt==0) {
			universe.place(haikei);
			dispcnt=1;
		}
	}

	public void play() {
		stop();
		setCurrentGameState(this);
		start();
	}

	public  void GameSetUp() {
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
	}

	public void GameOver() {
		stop();
		setCurrentGameState(GameOverGameState);
		start();
	}
	public void restart() {
//		dispcnt=0;
//		displayStatus=0;

		stop();
		universe.displace(stage);
		universe.displace(player);
		universe.displace(enemy2);
		for(int i=0; i<ENEMYNUM;i++) {
			universe.displace(enemies.get(i));
		}


		player.setPosition(0.0, 0.0);
		player.setDirection(0.0, 0.0);


		for(int i=0; i<ENEMYNUM; i++) {
			enemies.get(i).setPosition(enemyX[i], enemyY[i]);
			enemies.get(i).setDirection(1.0, 0.0);

		}
		enemy2.setPosition(0.0, 5.0);
		enemy2.setDirection(1.0, 0.0);

		setCurrentGameState(initialGameState);

		start();

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
