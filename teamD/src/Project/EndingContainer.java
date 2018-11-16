package Project;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsConfiguration;

import framework.RWT.RWTContainer;
import framework.RWT.RWTImage;
import framework.RWT.RWTLabel;
import framework.RWT.RWTVirtualController;
import framework.RWT.RWTVirtualKey;

public class EndingContainer extends RWTContainer {
	private TemplateAction2D game;

	public EndingContainer(TemplateAction2D game) {
		this.game = game;
	}

	@Override
	public void build(GraphicsConfiguration gc) {
		RWTImage startImage = new RWTImage("data\\title\\gameclear.jpg");
		startImage.setRelativePosition(0, 0);
		startImage.setImage("data\\title\\gameclear.jpg");
		startImage.setSize(800, 800);
		addWidget(startImage);
		RWTLabel timeLabel = new RWTLabel();
		timeLabel.setString("クリアタイム:"+game.getTime()+"秒！");
		timeLabel.setRelativePosition(0.5f, 0.5f);
		timeLabel.setColor(Color.BLACK);
		Font f = new Font("", Font.PLAIN, 20);
		timeLabel.setFont(f);
		addWidget(timeLabel);
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
