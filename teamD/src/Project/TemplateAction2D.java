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



	// あとで設計変更
	// Enemyクラスでこの値を使いたいため。
	public static final int RANGE = 30;
	public static final int ENEMYNUM = 5;

	// プレイヤーの現在の速度が代入されるグローバル変数
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


		// ステージの3Dデータを読み込み配置する
		stage = new Ground2D("data\\stage3\\stage3.wrl",
				"data\\images\\m101.jpg", windowSizeWidth, windowSizeHeight);
		//universe.place(stage);

		haikei = new Ground2D(null,
				"data\\images\\haikei.jpg", windowSizeWidth, windowSizeHeight);

		// 表示範囲を決める（左上が原点としてその原点から幅、高さを計算する）
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

				universe.place(player); // universeに置く。後で取り除けるようにオブジェクトを配置する。
				for(int i=0;i<ENEMYNUM;i++) {
					universe.place(enemies.get(i)); // universeに置く。後で取り除けるようにオブジェクトを配置する。
				}
				universe.place(enemy2);
				dispcnt=2;
			}



			curV = player.getVelocity();

			// 静止状態はプレイヤーのxを0にする。（坂で滑って行ってしまうため）
			curV.setX(0.0);
			player.setVelocity(curV);

			// キー操作の処理
			// 左
			if (virtualController.isKeyDown(0, RWTVirtualController.LEFT)) {
				player.movePositionLeft(0.05);
			}
			// 右
			else if (virtualController.isKeyDown(0, RWTVirtualController.RIGHT)) {
				player.movePositionRight(0.05);
			}
			// 上
			if (virtualController.isKeyDown(0, RWTVirtualController.UP)) {
				// ジャンプ
				if (player.isOnGround()) {
					curV.setY(10.0);
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
				player.setPosition(0.0, 0.0);
			}

			player.motion(interval, stage);
			enemy2.motion(interval, stage, player);
			for(int i=0;i<ENEMYNUM;i++) {
				enemies.get(i).motion(interval, stage, player);
			}


			// 衝突判定（プレイヤーと敵）
			if (player.checkCollision(enemy2)) {
				System.out.println("敵に接触した！");
			}

		}
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
