package Project;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsConfiguration;

import com.sun.j3d.utils.universe.SimpleUniverse;

import framework.RWT.RWTCanvas3D;
import framework.RWT.RWTLabel;

public class ActionCanvas3D extends RWTCanvas3D {


	RWTLabel zankiLabel = new RWTLabel();
	RWTLabel timeLabel = new RWTLabel();
	private long time;
	private long pretime;

	public ActionCanvas3D(TemplateAction2D game) {
		this(SimpleUniverse.getPreferredConfiguration(), game);
	}

	public ActionCanvas3D(GraphicsConfiguration gc, TemplateAction2D game) {
		super(gc, false);
		init(game);
	}

	public void update(TemplateAction2D g, long time) {
		this.time=time;

		zankiLabel.setString("残機"+g.getPlayerLife());
		if(pretime!=time) {
			timeLabel.setString("タイマー"+this.time+"秒");
			repaint();
			pretime=time;
		}



	}

	public void init(TemplateAction2D g) {

		zankiLabel.setString("残機"+g.getPlayerLife());
		zankiLabel.setRelativePosition(0.01f, 0.02f);
		Font f = new Font("", Font.PLAIN, 10);
		zankiLabel.setColor(Color.WHITE);
		zankiLabel.setFont(f);
		addWidget(zankiLabel);
		timeLabel.setString("タイマー"+time/1000);
		timeLabel.setRelativePosition(0.1f, 0.02f);
		timeLabel.setColor(Color.WHITE);
		timeLabel.setFont(f);
		addWidget(timeLabel);
		pretime=time;


	}

}
