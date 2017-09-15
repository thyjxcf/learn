/**
 * 
 */
package net.zdsoft.eis.base.observe.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.observe.Observer;
import net.zdsoft.eis.base.observe.Subject;
import net.zdsoft.eis.base.observe.Topic;

/**
 * 
 * @author zhangkc
 * @date 2014年12月9日 上午11:43:52
 */
public abstract class AbstractSubject implements Subject {
	
	private Map<Topic, List<Observer>> observers = new HashMap<Topic, List<Observer>>();
	
	@Override
	public void registerObserver(Topic t, Observer o) {
		List<Observer> tObservers = observers.get(t);
		if(tObservers != null){
			tObservers.add(o);
		}else{
			tObservers = new ArrayList<Observer>();
			tObservers.add(o);
		}
		observers.put(t, tObservers);
	}

	@Override
	public void removeObserver(Topic t, Observer o) {
		List<Observer> tObservers = observers.get(t);
		if(tObservers != null){
			tObservers.remove(o);
			observers.put(t, tObservers);
		}
	}

	@Override
	public void notifyObservers(Topic t, Object arg) {
		List<Observer> tObservers = observers.get(t);
		if(tObservers != null){
			for(Observer tObserver : tObservers){
				tObserver.updateTopic(t, arg);
			}
		}
	}
	
	public Map<Topic, List<Observer>> getObservers() {
		return observers;
	}
	public void setObservers(Map<Topic, List<Observer>> observers) {
		this.observers = observers;
	}
}
