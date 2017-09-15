/**
 * 
 */
package net.zdsoft.eis.frame.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;

/**
 * json的页面传值变量
 * @author zhangkc
 * @date 2015年5月15日 下午2:52:37
 */
public class JSONMessageDto implements Serializable, Map<Object, Object> {

	private static final long serialVersionUID = -9151075652542971759L;
	/**
	 * KEY_SUCCESS
	 */
	private static final String KEY_SUCCESS = "success";
	/**
	 * KEY_MESSAGE
	 */
	private static final String KEY_MESSAGE = "message";
	/**
	 * 默认使用全局的错误显示方式
	 */
	private static final String KEY_GLOBAL_FAIL = "globalFail";
	/**
	 * 保留关键字
	 */
	private static final String[] KEYS = new String[]{KEY_SUCCESS, KEY_MESSAGE, KEY_GLOBAL_FAIL}; 
	/**
	 * 实际的data
	 */
	private final Map<Object, Object> data = new HashMap<Object,Object>();
	/**
	 * 默认操作成功
	 */
	public JSONMessageDto() {
		this(true, "", true);
	}

	public JSONMessageDto(boolean success, String message) {
		this(success, message, true);
	}
	public JSONMessageDto(boolean success, String message, boolean globalFail) {
		super();
		data.put(KEY_SUCCESS, success);
		data.put(KEY_MESSAGE, message);
		data.put(KEY_GLOBAL_FAIL, globalFail);
	}
	/**
	 * Ajax操作成功与否标识
	 * @return
	 * @author zhangkc
	 * @date 2015年5月15日 下午5:04:46
	 */
	public boolean isSuccess() {
		return (Boolean)data.get(KEY_SUCCESS);
	}
	/**
	 * 
	 * @param success
	 * @author zhangkc
	 * @date 2015年5月15日 下午5:04:50
	 */
	public void setSuccess(boolean success) {
		data.put(KEY_SUCCESS, success);
	}
	/**
	 *  Ajax操作信息
	 * @return
	 * @author zhangkc
	 * @date 2015年5月15日 下午5:09:22
	 */
	public String getMessage() {
		return (String)data.get(KEY_MESSAGE);
	}
	/**
	 * 
	 * @param message
	 * @author zhangkc
	 * @date 2015年5月15日 下午5:09:25
	 */
	public void setMessage(String message) {
		data.put(KEY_MESSAGE, message);
	}
	/**
	 * 是否使用全局错误显示
	 * @return
	 * @author zhangkc
	 * @date 2015年5月16日 上午11:01:48
	 */
	public boolean isGlobalFail(){
		return (Boolean)data.get(KEY_GLOBAL_FAIL);
	}
	/**
	 * 设置是否使用全局的错误显示
	 * @param globalFail
	 * @return
	 * @author zhangkc
	 * @date 2015年5月16日 上午11:02:44
	 */
	public void setGlobalFail(boolean globalFail){
		data.put(KEY_GLOBAL_FAIL, globalFail);
	}
	
	@Override
	public int size() {
		return data.size();
	}

	@Override
	public boolean isEmpty() {
		return data.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return data.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return data.containsValue(value);
	}

	@Override
	public Object get(Object key) {
		return data.get(key);
	}

	@Override
	public Object put(Object key, Object value) {
		if(ArrayUtils.contains(KEYS, key)){
			throw new RuntimeException("无法设置key值"+key+"，因为key值["+Arrays.toString(KEYS)+"]为系统保留关键字。");
		}
		return data.put(key, value);
	}

	@Override
	public Object remove(Object key) {
		return data.remove(key);
	}

	@Override
	public void putAll(Map<? extends Object, ? extends Object> m) {
		if(m.keySet().containsAll(Arrays.asList(KEYS))){
			throw new RuntimeException("无法设置key值，因为key值["+Arrays.toString(KEYS)+"]为系统保留关键字。");
		}
		data.putAll(m);
	}

	@Override
	public void clear() {
		data.clear();
	}

	@Override
	public Set<Object> keySet() {
		return data.keySet();
	}

	@Override
	public Collection<Object> values() {
		return data.values();
	}

	@Override
	public Set<java.util.Map.Entry<Object, Object>> entrySet() {
		return data.entrySet();
	}
}
