package Project;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsConfiguration;

import com.sun.j3d.utils.universe.SimpleUniverse;

import framework.RWT.RWTCanvas3D;
import framework.RWT.RWTLabel;

public class ActionCanvas3D extends RWTCanvas3D {


	RWTLabel startLabel = new RWTLabel();

	public ActionCanvas3D(TemplateAction2D game) {
		this(SimpleUniverse.getPreferredConfiguration(), game);
	}

	public ActionCanvas3D(GraphicsConfiguration gc, TemplateAction2D game) {
		super(gc, false);
		init(game);
	}

	public void update(TemplateAction2D g, long time) {


		startLabel.setString("残機"+g.getPlayerLife());

	}

	public void init(TemplateAction2D g) {

		startLabel.setString("残機"+g.getPlayerLife());
		startLabel.setRelativePosition(0.01f, 0.02f);
		Font f = new Font("", Font.PLAIN, 10);
		startLabel.setColor(Color.WHITE);
		startLabel.setFont(f);
		addWidget(startLabel);

	}

}
