/**
 * 
 */
package net.zdsoft.eis.base.observe;

/**
 * 
 * @author zhangkc
 * @date 2014年12月9日 上午11:38:10
 */
public interface Subject {
	
	/**
	 * 订阅
	 * @param t
	 * @param o
	 * @author zhangkc
	 * @date 2014年12月11日 下午2:00:13
	 */
	void registerObserver(Topic t, Observer o);
	
	/**
	 * 取消订阅
	 * @param t
	 * @param o
	 * @author zhangkc
	 * @date 2014年12月11日 下午2:00:26
	 */
	void removeObserver(Topic t, Observer o);
	
	/**
	 * 发布
	 * @param t
	 * @param arg
	 * @author zhangkc
	 * @date 2014年12月11日 下午2:00:32
	 */
	void notifyObservers(Topic t, Object arg);
}
