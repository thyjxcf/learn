package net.zdsoft.leadin.dataimport.event;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.zdsoft.keelcnet.config.ContainerManager;
import net.zdsoft.leadin.dataimport.core.DataImportListener;

public class DataImportDispatcher {

    private static Map<String, DataImportListener> listenerCache = new ConcurrentHashMap<String, DataImportListener>();

    public static void dispatchEvent(List<String> listeners, DataImportEvent event)
            throws Exception {
        if (listeners == null || event == null) {
            throw new Exception("-->导入失败，未指定监听器或事件！");
        }

        DataImportListener listener = null;
        int eventType = event.getEventType();

        for (int i = 0; i < listeners.size(); i++) {
            String listenerName = (String) listeners.get(i);
            listener = listenerCache.get(listenerName);
            if (listener == null) {
                try {
                    listener = (DataImportListener) ContainerManager.getComponent(listenerName);
                } catch (Exception e) {
                    throw new Exception("-->导入失败，无法实例化监听器！");
                }
                listenerCache.put(listenerName, listener);
            }

            if (eventType == DataImportEvent.DO_BEFORE) {
                listener.doBefore(event);
            } else if (eventType == DataImportEvent.DO_AFTER) {
                listener.doAfter(event);
            }
        }
    }
}
