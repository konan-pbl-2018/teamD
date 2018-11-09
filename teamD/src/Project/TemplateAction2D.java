package Project;

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
	private Enemy enemy;
	private Enemy_sky enemy2;
	private Sword sword;

	private Ground2D stage;
	private Ground2D haikei;
	private int displayStatus = 0;
	private int dispcnt = 0;
	private int enemycnt = 0;
	private double enemyX[] = new double[ENEMYNUM];
	private double enemyY[] = new double[ENEMYNUM];
	private Sound3D actionBGM = BGM3D
			.registerBGM("data\\music\\bgm_maoudamashii_fantasy13 (online-audio-converter.com).wav");
	private Sound3D startBGM = BGM3D
			.registerBGM("data\\music\\BGMstart.wav");
	private Sound3D gameoverBGM = BGM3D
			.registerBGM("data\\music\\bgm_maoudamashii_fantasy09 (online-audio-converter.com).wav");
	private Sound3D clearBGM = BGM3D
			.registerBGM("data\\music\\BGMkuria (1).wav");

	// あとで設計変更
	// Enemyクラスでこの値を使いたいため。
	public static final int RANGE = 60;
	public static final int ENEMYNUM = 5;

	// プレイヤーの現在の速度が代入されるグローバル変数
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
		//GameContainer=new RWTContainer();
		setCurrentGameState(initialGameState);

	}

	@Override
	public void init(Universe universe) {
		player = new Player();
		player.setPosition(0.0, 10.0);
		player.setDirection(0.0, 0.0);
		((Object3D) player.getBody()).scale(0.035);

		File f = new File("data\\\\EnemyPosition\\\\EnemyPosition.txt");

		//敵の座標
		try (Scanner sc = new Scanner(f)) {

			sc.useDelimiter(",");

			//hasNextLineで次の行が存在するかを判定します。
			for (int i = 0; sc.hasNextLine(); i++) {

				enemyX[i] = sc.nextDouble();
				enemyY[i] = sc.nextDouble();

				System.out.println("X:" + enemyX[i]);
				System.out.println("Y:" + enemyY[i]);
				System.out.println();

				sc.nextLine();//次の行へ
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		enemies = new ArrayList<Enemy>();
		for (int i = 0; i < ENEMYNUM; i++) {
			enemy = new Enemy();
			enemy.setPosition(enemyX[i], enemyY[i]);
			enemy.setDirection(1.0, 0.0);
			((Object3D) enemy.getBody()).scale(0.1);
			enemies.add(enemy);

		}
		enemy2 = new Enemy_sky();
		enemy2.setPosition(0.0, 5.0);
		enemy2.setDirection(1.0, 0.0);


		sword=new Sword();
		sword.setPosition(-265.82,12.054);
		sword.setDirection(0.0, 0.0);

		// ステージの3Dデータを読み込み配置する
		stage = new Ground2D("data\\stage\\stage.obj",
				"data\\stage\\testhaikei.jpg", windowSizeWidth, windowSizeHeight, 0.03);

		universe.place(stage);
		universe.place(player);
		universe.place(sword);
		for (int i = 0; i < ENEMYNUM; i++) {
			universe.place(enemies.get(i)); // universeに置く。後で取り除けるようにオブジェクトを配置する。
		}
		universe.place(enemy2);

		//		haikei = new Ground2D(null,
		//				"data\\title\\title.jpg", windowSizeWidth, windowSizeHeight);

		// 表示範囲を決める（左上が原点としてその原点から幅、高さを計算する）
		setViewRange(RANGE, RANGE);
		BGM3D.playBGM(actionBGM);
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
		//			displayStatus=1;//ゲームスタート
		//
		//		}
		//
		//		if (displayStatus==1) {
		//			GameSetUp();
		//		RWTLabel startLabel = new RWTLabel();
		//		startLabel.setString("Start");
		//		startLabel.setRelativePosition(0.3f, 0.5f);
		//		Font f = new Font("", Font.PLAIN, 20);
		//		startLabel.setFont(f);
		//		startLabel.repaint();
		//
		curV = player.getVelocity();

		// 静止状態はプレイヤーのxを0にする。（坂で滑って行ってしまうため）
		curV.setX(0.0);
		player.setVelocity(curV);

		// キー操作の処理
		// 左
		if (virtualController.isKeyDown(0, RWTVirtualController.LEFT)) {
			player.movePositionLeft(0.1);
		}
		// 右
		else if (virtualController.isKeyDown(0, RWTVirtualController.RIGHT)) {
			player.movePositionRight(0.1);
		}
		// 上
		if (virtualController.isKeyDown(0, RWTVirtualController.UP)) {
			// ジャンプ
			if (player.isOnGround()) {
				curV.setY(15.0);
				player.setVelocity(curV);
			}
		}
		// 下
		else if (virtualController.isKeyDown(0, RWTVirtualController.DOWN)) {
			player.movePositionDown(0.01);
		}

		if (virtualController.isKeyDown(0, RWTVirtualController.BUTTON_B)) {
		}

		if (player.getPosition().getY() < -RANGE / 2.0) {
			GameOver();//(ゲームオーバー）
		}

		player.motion(interval, stage);
		enemy2.motion(interval, stage, player);
		for (int i = 0; i < ENEMYNUM; i++) {
			if (Math.abs(player.getPosition().getX() - enemies.get(i).getPosition().getX()) < RANGE / 2) {
				enemies.get(i).setmotionFlag(true);
			}
			if (enemies.get(i).getmotionFlag() == true) {
				enemies.get(i).motion(interval, stage, player);
			}
		}

		System.out.println(player.getPosition().getVector2d());

		// 衝突判定（プレイヤーと敵）

		//			if (player.checkCollision(enemy2)) {
		//				System.out.println("敵に接触した！");
		//				GameOver();
		//			}
		//			for(int i=0;i<enemies.size(); i++) {
		//				if (player.checkCollision(enemies.get(i))) {
		//					GameOver();
		//				}
		//			}
		//		}
		if (player.checkCollision(sword)) {
							System.out.println("剣に接触した！");
							ending();
						}
	}

	public void Title() {
		if (dispcnt == 0) {
			universe.place(haikei);
			dispcnt = 1;
		}
	}

	public void ending() {
		stop();
		setCurrentGameState(finalGameState);
		start();
	}

	public void play() {
		stop();
		setCurrentGameState(this);
		System.out.println(this);
		start();
	}

	//	public void GameSetUp() {
	//		if (dispcnt == 1) {
	//			universe.displace(haikei);
	//			universe.place(stage);
	//
	//			universe.place(player); // universeに置く。後で取り除けるようにオブジェクトを配置する。
	//			for (int i = 0; i < ENEMYNUM; i++) {
	//				universe.place(enemies.get(i)); // universeに置く。後で取り除けるようにオブジェクトを配置する。
	//			}
	//			universe.place(enemy2);
	//			dispcnt = 2;
	//		}
	//	}

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
		for (int i = 0; i < ENEMYNUM; i++) {
			universe.displace(enemies.get(i));
		}

		player.setPosition(0.0, 0.0);
		player.setDirection(0.0, 0.0);

		for (int i = 0; i < ENEMYNUM; i++) {
			enemies.get(i).setPosition(enemyX[i], enemyY[i]);
			enemies.get(i).setDirection(1.0, 0.0);

		}
		enemy2.setPosition(0.0, 5.0);
		enemy2.setDirection(1.0, 0.0);

		setCurrentGameState(initialGameState);

		start();

	}

	/**
	 * ゲームのメイン
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
