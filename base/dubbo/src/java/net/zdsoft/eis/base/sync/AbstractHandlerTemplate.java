/* 
 * @(#)AbstractHandlerTemplate.java    Created on Dec 9, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.sync;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.frame.client.BaseEntity;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winupon.amqp.rabbit.basedata.handler.template.MqClientHandlerTemplate;
import com.winupon.syncdata.basedata.entity.MqBaseData;
import com.winupon.syncdata.basedata.property.MqEventType;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 9, 2010 1:47:21 PM $
 */
public abstract class AbstractHandlerTemplate<E extends BaseEntity, M extends MqBaseData>
		extends MqClientHandlerTemplate<M> implements SingleHandlable<E> {
	private static final Logger log = LoggerFactory
			.getLogger(AbstractHandlerTemplate.class);

	protected SyncObjectConvertable<E, M> converter;// 转换
	private final Class<E> eisEntityClass;

	public void setConverter(SyncObjectConvertable<E, M> converter) {
		this.converter = converter;
	}

	@SuppressWarnings("unchecked")
	public AbstractHandlerTemplate() {
		super();

		Type[] types = ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments();

		Class<E> _eisEntityClass = (Class<E>) types[0];
		this.eisEntityClass = _eisEntityClass;

		setBaseDataClass((Class<M>) types[1]);
	}

	public void init() {
	    String v = System.getProperty("mqEnable");
	    if(StringUtils.equals(v, "1")) {
	        SyncHelper.register(this);
	    }
	}

	@Override
	public final void realHandle(M basedata) {
		log.debug("调用单个处理方法: {}", basedata.getClass().getSimpleName());

		MqEventType eventType = basedata.getEventType();

		if (MqEventType.HD == eventType || MqEventType.D == eventType) {
			deleteData(basedata.getId(), EventSourceType.OTHER);
			return;
		}

		if (MqEventType.I == eventType) {
			E e = newEntity();

			// 转化并设置扩展字段的数据
			SyncObjectConverter.toEntity(basedata, e, converter);
			addData(e);

		} else if (MqEventType.U == eventType) {

			// 转化并设置扩展字段的数据
			E e = fetchOldEntity(basedata.getId());
			if (null == e) {
				e = newEntity();
			}
			SyncObjectConverter.toEntity(basedata, e, converter);
			updateData(e);
		}

	}

	/**
	 * 实化例entity
	 * 
	 * @return
	 */
	private E newEntity() {
		E e = null;
		try {
			e = eisEntityClass.newInstance();
		} catch (InstantiationException e1) {
			log.error("实例化entity失败", e1);
		} catch (IllegalAccessException e1) {
			log.error("实例化entity失败", e1);
		}
		return e;
	}

	/**
	 * 默认调用单个处理消息的方法
	 */
	@Override
	public final void realHandle(List<M> entityList, MqEventType eventType) {
		String className = "";
		if (entityList.size() > 0) {
			entityList.get(0).getClass().getSimpleName();
		}
		log.debug("调用批量处理方法: {}", className);

		// 如果实现了BatchHandlable接口，则调用此接口的批量处理方法
		if (this instanceof BatchHandlable) {
			@SuppressWarnings("unchecked")
			BatchHandlable<E> batchHandlable = (BatchHandlable<E>) this;

			if (MqEventType.D == eventType) {
				Set<String> idSet = new HashSet<String>();
				for (M basedata : entityList) {
					idSet.add(basedata.getId());
				}
				if (idSet.size() > 0) {
					batchHandlable.deleteDatas(idSet.toArray(new String[0]),
							EventSourceType.OTHER);
				}
				return;
			}

			List<E> entities = new ArrayList<E>();
			if (MqEventType.I == eventType) {
				for (M basedata : entityList) {
					E e = newEntity();
					SyncObjectConverter.toEntity(basedata, e, converter);
					entities.add(e);
				}

				batchHandlable.addDatas(entities);

			} else if (MqEventType.U == eventType) {
				Set<String> idSet = new HashSet<String>();
				for (M basedata : entityList) {
					idSet.add(basedata.getId());
				}
				Map<String, E> oldEntities = null;
				if (idSet.size() > 0) {
					oldEntities = batchHandlable.fetchOldEntities(idSet
							.toArray(new String[0]));
				}
				if (null == oldEntities) {
					oldEntities = new HashMap<String, E>();
				}

				for (M basedata : entityList) {
					E e = oldEntities.get(basedata.getId());
					if (null == e) {
						e = newEntity();
					}
					SyncObjectConverter.toEntity(basedata, e, converter);
					entities.add(e);
				}

				batchHandlable.updateDatas(entities);
			}

		} else {
			// 循环调用单个处理的方法
			for (M basedata : entityList) {
				basedata.setEventType(eventType);
				realHandle(basedata);
			}

		}
	}

}
