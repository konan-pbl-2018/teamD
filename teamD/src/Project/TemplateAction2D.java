package Project;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import framework.RWT.RWTContainer;
import framework.RWT.RWTFrame3D;
import framework.RWT.RWTVirtualController;
import framework.audio.BGM3D;
import framework.audio.Sound3D;
import framework.game2D.Ground2D;
import framework.game2D.OvergroundActor2D;
import framework.game2D.Velocity2D;
import framework.gameMain.IGameState;
import framework.gameMain.SimpleActionGame;
import framework.model3D.Object3D;
import framework.model3D.Universe;

public class TemplateAction2D extends SimpleActionGame {
	private Player player;
	private ArrayList<Enemy> enemies;
	private ArrayList<Enemy_sky> enemies2;
	private Enemy enemy;
	private Enemy_sky enemy2;
	private Boss boss;
	private Sword sword;
	ActionCanvas3D canvas;
	double vy;

	private Ground2D stage;
	private int PlayerLife = 2;
	private int BGMstate;
	private long time;
	private long timeSecond;
	private int EnemyCount;
	private int Enemy2Count;
	private double enemyX[] = new double[100];
	private double enemyY[] = new double[100];
	private double enemy2X[] = new double[100];
	private double enemy2Y[] = new double[100];
	private Sound3D actionBGM = BGM3D
			.registerBGM("data\\music\\bgm_maoudamashii_fantasy13 (online-audio-converter.com).wav");
	private Sound3D startBGM = BGM3D
			.registerBGM("data\\music\\BGMstart.wav");
	private Sound3D gameoverBGM = BGM3D
			.registerBGM("data\\music\\bgm_maoudamashii_fantasy09 (online-audio-converter.com).wav");
	private Sound3D clearBGM = BGM3D
			.registerBGM("data\\music\\BGMkuria (1).wav");
	private Sound3D BossBGM = BGM3D
			.registerBGM("data\\music\\last Bos.wav");
	private RWTContainer container;


	// ���ƂŐ݌v�ύX
	// Enemy�N���X�ł��̒l���g���������߁B
	public static final int RANGE = 60;
//	public static final int ENEMYNUM = 5;
	public static final int PLAYERLIFE = 2;

	// �v���C���[�̌��݂̑��x����������O���[�o���ϐ�
	private Velocity2D curV;
	private Velocity2D EnemyCurv;

	private IGameState initialGameState = null;
	private IGameState SinalioState = null;
	private IGameState finalGameState = null;
	private IGameState GameOverGameState = null;
	private IGameState DeathState = null;

	//�R���X�g���N�^
	public TemplateAction2D() {
		super();
		//�Q�[�����
		initialGameState = new IGameState() {
			@Override
			public void init(RWTFrame3D frame) {
				TemplateAction2D.this.frame = frame;
				RWTContainer container = new StartContainer(TemplateAction2D.this);
				time=0;
				BGM3D.playBGM(startBGM);
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

		SinalioState = new IGameState() {
			@Override
			public void init(RWTFrame3D frame) {
				TemplateAction2D.this.frame = frame;
				RWTContainer container = new SinalioContainer(TemplateAction2D.this);
				time=0;
				BGM3D.playBGM(startBGM);
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

		DeathState = new IGameState() {
			@Override
			public void init(RWTFrame3D frame) {
				TemplateAction2D.this.frame = frame;
				RWTContainer container = new DeathContainer(TemplateAction2D.this);
				changeContainer(container);
			}

			@Override
			public boolean useTimer() {
				return true;
			}

			@Override
			public void update(RWTVirtualController virtualController, long interval) {
				try {
					Thread.sleep(2000);
					reset();
					play();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		finalGameState = new IGameState() {
			@Override
			public void init(RWTFrame3D frame) {
				TemplateAction2D.this.frame = frame;
				RWTContainer container = new EndingContainer(TemplateAction2D.this);
				BGM3D.playBGM(clearBGM);
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
				BGM3D.playBGM(gameoverBGM);
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

		//�����̃Q�[����ʂ��X�^�[�g��ʂ�
		setCurrentGameState(initialGameState);

	}

	//���C���Q�[����ʂ̃R���e�i�����B
	protected RWTContainer createRWTContainer() {
		container = new ActionContainer(TemplateAction2D.this);
		return container;
	}

	//�Q�[���̏�����
	@Override
	public void init(Universe universe) {
		//�v���C���[�̏�����
		player = new Player();
		player.setPosition(0.0, 10.0);
		player.setDirection(0.0, 0.0);
		((Object3D) player.getBody()).scale(0.035);

		//�G�̍��W�t�@�C��
		File f = new File("data\\EnemyPosition\\EnemyPosition.txt");
		File f2 = new File("data\\EnemyPosition\\Enemy2Position.txt");

		try (Scanner sc = new Scanner(f)) {

			sc.useDelimiter(",");

			//hasNextLine�Ŏ��̍s�����݂��邩�𔻒肵�܂��B
			for (int i = 0; sc.hasNextLine(); i++) {

				enemyX[i] = sc.nextDouble();
				enemyY[i] = sc.nextDouble();

				System.out.println("X:" + enemyX[i]);
				System.out.println("Y:" + enemyY[i]);
				System.out.println();
				EnemyCount++;

				sc.nextLine();//���̍s��
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try (Scanner sc = new Scanner(f2)) {

			sc.useDelimiter(",");

			//hasNextLine�Ŏ��̍s�����݂��邩�𔻒肵�܂��B
			for (int i = 0; sc.hasNextLine(); i++) {

				enemy2X[i] = sc.nextDouble();
				enemy2Y[i] = sc.nextDouble();

				System.out.println("X:" + enemy2X[i]);
				System.out.println("Y:" + enemy2Y[i]);
				System.out.println();
				Enemy2Count++;

				sc.nextLine();//���̍s��
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		//Enemy�̃C���X�^���X�𐶐���ArrayList�ɓ����
		enemies = new ArrayList<Enemy>();
		for (int i = 0; i < EnemyCount; i++) {
			enemy = new Enemy();
			enemy.setPosition(enemyX[i], enemyY[i]);
			enemy.setDirection(1.0, 0.0);
			((Object3D) enemy.getBody()).scale(0.1);
			enemies.add(enemy);

		}
		enemies2=new ArrayList<Enemy_sky>();
		for (int i = 0; i < Enemy2Count; i++) {
			enemy2 = new Enemy_sky();
			enemy2.setPosition(enemy2X[i], enemy2Y[i]);
			enemy2.setDirection(1.0, 0.0);
			((Object3D) enemy2.getBody()).scale(0.1);
			enemies2.add(enemy2);
		}

		boss = new Boss();
		boss.setPosition(-250, 8);
		boss.setDirection(1.0, 0.0);
		boss.setmotionFlag(true);

		//���̃I�u�W�F�N�g������
		sword = new Sword();
		sword.setPosition(-265.82, 12.054);
		sword.setDirection(0.0, 0.0);
		((Object3D) sword.getBody()).scale(0.035);

		// �X�e�[�W��3D�f�[�^��ǂݍ��ݔz�u����
		stage = new Ground2D("data\\stage\\stage.obj",
				"data\\stage\\testhaikei.jpg", windowSizeWidth, windowSizeHeight, 0.03);

		//����
		universe.place(stage);
		universe.place(player);
		universe.place(sword);
		for (int i = 0; i < enemies.size(); i++) {
			universe.place(enemies.get(i)); // universe�ɒu���B
		}
		for (int i = 0; i < enemies2.size(); i++) {
			universe.place(enemies2.get(i)); // universe�ɒu���B
		}
		universe.place(boss);

		// �\���͈͂����߂�i���オ���_�Ƃ��Ă��̌��_���畝�A�������v�Z����j
		setViewRange(RANGE, RANGE);
		//BGM
		BGMstate=0;
		BGM3D.playBGM(actionBGM);

	}

	@Override
	public RWTFrame3D createFrame3D() {
		// TODO Auto-generated method stub
		RWTFrame3D f = new RWTFrame3D();
		f.setSize(800, 800);
		// f.setExtendedState(Frame.MAXIMIZED_BOTH);
		f.setTitle("Template for Action 2DGame");
		f.setBackground(Color.BLACK);
		return f;
	}

	@Override
	public void progress(RWTVirtualController virtualController, long interval) {

		curV = player.getVelocity();
		time += interval;
		timeSecond=time/1000;
		canvas.update(this, timeSecond);

		// �Î~��Ԃ̓v���C���[��x��0�ɂ���B�i��Ŋ����čs���Ă��܂����߁j
		curV.setX(0.0);
		player.setVelocity(curV);

		// �L�[����̏���
		// ��
		if (virtualController.isKeyDown(0, RWTVirtualController.LEFT)) {
			player.movePositionLeft(0.2);
		}
		// �E
		else if (virtualController.isKeyDown(0, RWTVirtualController.RIGHT)) {
			player.movePositionRight(0.2);
		}
		// ��
		if (virtualController.isKeyDown(0, RWTVirtualController.UP)) {
			// �W�����v
			if (player.isOnGround()) {
				curV.setY(28);
				player.setVelocity(curV);
			}
		}
		// ��
		else if (virtualController.isKeyDown(0, RWTVirtualController.DOWN)) {
			player.movePositionDown(0.01);
		}

		if (virtualController.isKeyDown(0, RWTVirtualController.BUTTON_B)) {
		}

		//���������玀�S
		if (player.getPosition().getY() < -RANGE / 2.0) {
			//GameOver();//(�Q�[���I�[�o�[�j
			PlayerLife--;
			Death();
		}
		//���������G�l�~�[����
		for(int i=0; i<enemies.size(); i++) {
			if (enemies.get(i).getPosition().getY() < -RANGE / 2.0) {
				universe.displace(enemies.get(i));
				enemies.remove(i);
			}
		}


		//���삳����B
		vy=0;
		vy=player.getVelocity().getY()-0.2;
		player.setVelocity(player.getVelocity().getX(), vy);
		player.getActor().setPosition(player.getActor().getPosition().setZ(0.0));
		player.motion(interval, stage);

		for (int i = 0; i < enemies.size(); i++) {
			//��ʂɕ\������Ă��瓮����
			if (Math.abs(player.getPosition().getX() - enemies.get(i).getPosition().getX()) < RANGE / 2) {
				enemies.get(i).setmotionFlag(true);
			}
			if (enemies.get(i).getmotionFlag() == true) {
				enemies.get(i).motion(interval, stage, player);
			}
		}
		for (int i = 0; i < enemies2.size(); i++) {
			//��ʂɕ\������Ă��瓮����
			if (Math.abs(player.getPosition().getX() - enemies2.get(i).getPosition().getX()) < RANGE / 2) {
				enemies2.get(i).setmotionFlag(true);
			}
			if (enemies2.get(i).getmotionFlag() == true) {
				enemies2.get(i).motion(interval, stage, player);
			}
		}

//		if (Math.abs(player.getPosition().getX() - boss.getPosition().getX()) < RANGE / 2) {
//			boss.setmotionFlag(true);
//		}

		if (boss.getmotionFlag() == true) {
			if ((int) (100 * Math.random()) > 98) {
				enemy = new Enemy();
				enemy.setPosition(boss.getPosition().getX(), boss.getPosition().getY());
				enemy.setDirection(1.0, 0.0);
				EnemyCurv = enemy.getVelocity();
				EnemyCurv.setX(40-20*Math.random());
				EnemyCurv.setY(25.0-20*Math.random());
				enemy.setmotionFlag(true);
				enemy.setVelocity(EnemyCurv);
				((Object3D) enemy.getBody()).scale(0.1);
				universe.place(enemy);
				enemies.add(enemy);
			}
			boss.motion(interval, stage);
		}
		//�f�o�b�O�p�v���C���[���W
		    System.out.println(player.getPosition().getVector2d());
//			System.out.println(enemies.size());

//		 �Փ˔���i�v���C���[�ƓG�j
		for (int i = 0; i < enemies.size(); i++) {
			if (player.checkCollision(enemies.get(i))) {
				PlayerLife--;
				Death();
				break;
			}
		}
		for (int i = 0; i < enemies2.size(); i++) {
			if (player.checkCollision(enemies2.get(i))) {
				PlayerLife--;
				Death();
				break;
			}
		}
		if (player.checkCollision(boss)) {
			PlayerLife--;
			Death();
		}

		//���Ƃ̐ڐG����
		if (player.checkCollision(sword)) {
			System.out.println("���ɐڐG�����I");
			ending();
		}

		if(player.getPosition().getX()<-187&&BGMstate!=1) {
			BGM3D.playBGM(BossBGM);
			BGMstate=1;
		}
		if(player.getPosition().getX()>-187&&BGMstate!=0) {
			BGM3D.playBGM(actionBGM);
			BGMstate=0;
		}




	}

	public void setCanvas(ActionCanvas3D canvas) {
		this.canvas = canvas;
	}

	public void ending() {
		reset();
		stop();
		setCurrentGameState(finalGameState);
		start();
	}

	public void play() {
		stop();
		setCurrentGameState(this);
		start();
	}

	public void GoSinalio() {
		stop();
		setCurrentGameState(SinalioState);
		start();
	}


	public void GameOver() {
		stop();
		reset();
		setCurrentGameState(GameOverGameState);
		start();
	}

	public void Death() {
		if (getPlayerLife() >= 0) {
			stop();
			setCurrentGameState(DeathState);
			start();
		} else {
			GameOver();
		}
	}

	public void reset() {
		EnemyCount=0;
		Enemy2Count=0;
		for (int i = 0; i < enemies.size(); i++) {
			enemies.remove(i);
		}
		for (int i = 0; i < enemies2.size(); i++) {
			enemies2.remove(i);
		}

	}

	public void continued() {
		stop();

		PlayerLife = 2;

		setCurrentGameState(initialGameState);

		start();

	}

	public int getPlayerLife() {
		return PlayerLife;
	}

	public long getTime() {
		return timeSecond;
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
