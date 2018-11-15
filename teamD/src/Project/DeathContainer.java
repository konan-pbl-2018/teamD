package Project;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsConfiguration;

import framework.RWT.RWTContainer;
import framework.RWT.RWTLabel;
import framework.RWT.RWTVirtualKey;
import framework.game2D.Ground2D;

public class DeathContainer extends RWTContainer {
	private TemplateAction2D game;
	private Ground2D haikei;
	RWTLabel deathLabel = new RWTLabel();
	RWTLabel zankiLabel = new RWTLabel();

	public DeathContainer(TemplateAction2D game) {
		this.game = game;
	}

	@Override
	public void build(GraphicsConfiguration gc) {

		deathLabel.setString("あなたは死んだのです。");
		deathLabel.setRelativePosition(0.3f, 0.5f);
		deathLabel.setColor(Color.WHITE);

		zankiLabel.setString("残機："+game.getPlayerLife());
		zankiLabel.setRelativePosition(0.3f, 0.7f);
		zankiLabel.setColor(Color.WHITE);

		Font f = new Font("", Font.PLAIN, 20);
		deathLabel.setFont(f);
		zankiLabel.setFont(f);
		addWidget(deathLabel);
		addWidget(zankiLabel);
		repaint();


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
