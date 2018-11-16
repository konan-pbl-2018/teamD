package Project;

import java.awt.GraphicsConfiguration;

import framework.RWT.RWTContainer;
import framework.RWT.RWTImage;
import framework.RWT.RWTVirtualController;
import framework.RWT.RWTVirtualKey;
import framework.game2D.Ground2D;

public class StartContainer extends RWTContainer {
	private TemplateAction2D game;
	private Ground2D haikei;


	public StartContainer(TemplateAction2D game) {
		this.game = game;
	}

	@Override
	public void build(GraphicsConfiguration gc) {
		RWTImage startImage = new RWTImage("data\\title\\title.jpg");
		startImage.setRelativePosition(0, 0);
		startImage.setImage("data\\title\\title.jpg");
		startImage.setSize(800, 800);
		addWidget(startImage);
		repaint();
	}



	@Override
	public void keyPressed(RWTVirtualKey key) {
		if (key.getVirtualKey() == RWTVirtualController.DOWN) {
			game.GoSinalio();
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
