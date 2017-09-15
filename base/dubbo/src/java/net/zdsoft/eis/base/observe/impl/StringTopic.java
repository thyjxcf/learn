/**
 * 
 */
package net.zdsoft.eis.base.observe.impl;

import net.zdsoft.eis.base.observe.Topic;

/**
 * 纯字符串主题
 * @author zhangkc
 * @date 2014年12月9日 上午11:40:37
 */
public class StringTopic implements Topic {
	private static final long serialVersionUID = -310935079962909712L;
	
	private String name;
	
	public StringTopic(String name){
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StringTopic other = (StringTopic) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
