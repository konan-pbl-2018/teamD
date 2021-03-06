package Project;

import java.awt.GraphicsConfiguration;

import framework.RWT.RWTContainer;
import framework.RWT.RWTImage;
import framework.RWT.RWTVirtualController;
import framework.RWT.RWTVirtualKey;
import framework.game2D.Ground2D;

public class GameOverContainer extends RWTContainer {
	private TemplateAction2D game;
	private Ground2D haikei;


	public GameOverContainer(TemplateAction2D game) {
		this.game = game;
	}

	@Override
	public void build(GraphicsConfiguration gc) {
		RWTImage startImage = new RWTImage("data\\gameover\\gameover.jpg");
		startImage.setRelativePosition(0, 0);
		startImage.setImage("data\\gameover\\gameover.jpg");
		startImage.setSize(800, 800);
		addWidget(startImage);
		repaint();
	}



	@Override
	public void keyPressed(RWTVirtualKey key) {
		if (key.getVirtualKey() == RWTVirtualController.BUTTON_A) {
			game.continued();
		}
	}

	@Override
	public void keyReleased(RWTVirtualKey key) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(RWTVirtualKey key) {
		// TODO Auto-generated method stub

	}

}
