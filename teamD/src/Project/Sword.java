package Project;

import framework.game2D.OvergroundActor2D;

/**
 * �G�̃N���X
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
		//���̉摜�ǂݍ���
		return "data\\character\\sword.obj";
	}

}
