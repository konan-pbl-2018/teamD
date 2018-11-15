package Project;

import java.awt.GraphicsConfiguration;

import framework.RWT.RWTContainer;
import framework.RWT.RWTVirtualKey;
import framework.game2D.Ground2D;

public class ActionContainer extends RWTContainer {
	private TemplateAction2D game;
	private Ground2D haikei;
	ActionCanvas3D canvas;


	public ActionContainer(TemplateAction2D game) {
		this.game = game;
	}

	@Override
	public void build(GraphicsConfiguration gc) {
		if (gc != null) {
			canvas = new ActionCanvas3D(gc, game);
		} else {
			canvas = new ActionCanvas3D(game);
		}
		canvas.setRelativePosition(0.0f, 0.0f);
		canvas.setRelativeSize(1.0f, 1.0f);
		addCanvas(canvas);
		repaint();
		game.setCanvas(canvas);
	}



	@Override
	public void keyPressed(RWTVirtualKey key) {
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
