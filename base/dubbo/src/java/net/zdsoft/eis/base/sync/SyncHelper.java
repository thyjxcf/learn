/**
 * 
 */
package net.zdsoft.eis.base.sync;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import net.zdsoft.eis.base.deploy.SystemDeployService;
import net.zdsoft.eis.frame.client.BaseEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winupon.amqp.rabbit.basedata.util.MqClientSupport;
import com.winupon.syncdata.basedata.entity.MqBaseData;

/**
 * @author Administrator
 * 
 */
public class SyncHelper {
	private static final Logger log = LoggerFactory.getLogger(SyncHelper.class);

	private static SystemDeployService systemDeployService;

	public void setSystemDeployService(SystemDeployService systemDeployService) {
		SyncHelper.systemDeployService = systemDeployService;
	}

	/**
	 * 缓存类對象
	 */
	@SuppressWarnings("unchecked")
	private static Queue<AbstractHandlerTemplate> cacheObjects = new ConcurrentLinkedQueue<AbstractHandlerTemplate>();

	/**
	 * 注册缓存类
	 * 
	 * @param service
	 */
	public static <E extends BaseEntity, M extends MqBaseData> void register(
			AbstractHandlerTemplate<E, M> service) {
		cacheObjects.add(service);

	}

	/**
	 * 开始接收数据
	 */
	@SuppressWarnings("unchecked")
	public static void start() {
		boolean opened = systemDeployService.isOpenMq();
		if (opened == false) {
			return;
		}

		for (AbstractHandlerTemplate handler : cacheObjects) {
			MqClientSupport.receiveMessage(handler);
			log.debug("注册 {} 接收消息服务", handler.getClass().getSimpleName());
		}
	}

}
