package Project;

import framework.game2D.OvergroundActor2D;

/**
 * 敵のクラス
 *
 * @author H.Kotobuki
 *
 */
public class Sword extends OvergroundActor2D {

	@Override
	public String getAnimationFileName() {
		return null;

	}

	@Override
	public String getModelFileName() {
		//剣の画像読み込み
		return "data\\character\\sword.obj";
	}

}
