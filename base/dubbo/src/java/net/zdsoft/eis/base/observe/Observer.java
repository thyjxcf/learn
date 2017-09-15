/**
 * 
 */
package net.zdsoft.eis.base.observe;

/**
 * 
 * @author zhangkc
 * @date 2014年12月9日 上午11:38:23
 */
public interface Observer {

	/**
	 * 更新订阅
	 * @param t
	 * @param arg
	 * @author zhangkc
	 * @date 2014年12月11日 下午2:00:52
	 */
	void updateTopic(Topic t, Object arg);
}
