package net.zdsoft.eis.base.common.action;

import java.util.Random;

import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.keel.page.TextImage;
import net.zdsoft.keel.util.ServletUtils;

import org.apache.struts2.ServletActionContext;

/**
 * 验证码Action
 * 
 * @author jiangf
 * @version 创建时间：2011-11-24 下午01:56:02
 */

public class VerifyImageAction extends BaseAction {

	private static final long serialVersionUID = -35021120145642654L;

	private static final String code = "123456789abcdefhijkmnprstuvwxyzABCDEFGHJKLMNPRSTUVWXYZ";

	public String execute() throws Exception {
		ServletUtils.clearCache(getResponse());
		String verifyCode = getRandomNum(4);
		ServletActionContext.getContext().getSession().put(
				BaseConstant.VERIFY_CODE, verifyCode);
		TextImage textImage = new TextImage(62, 25);
		textImage.setBgColor(0xf8, 0xf8, 0xf6);
		textImage.setFontColor(0x53, 0x66, 0x39);
		textImage.setFont(18);
		textImage.setRandomPointNum(10);
		textImage.export(verifyCode, 6, 17, getResponse().getOutputStream());
		return null;
	}

	public boolean isPrintEnabled() {
		return true;
	}

	public String getPrintContent() {
		return null;
	}

	// 生成随机字母和数字
	private String getRandomNum(int length) {
		String ramdonMum = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			ramdonMum += code.charAt(random.nextInt(54));
		}
		return ramdonMum;
	}
}
