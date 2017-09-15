/**
 * 
 */
package net.zdsoft.eis.base.observe;

import java.io.Serializable;

/**
 * 主题
 * @author zhangkc
 * @date 2014年12月9日 上午11:40:06
 */
public interface Topic extends Serializable {
	
	/**
	 * 获取主题名称
	 * @return
	 * @author zhangkc
	 * @date 2014年12月19日 上午11:22:17
	 */
	String getName();
}
