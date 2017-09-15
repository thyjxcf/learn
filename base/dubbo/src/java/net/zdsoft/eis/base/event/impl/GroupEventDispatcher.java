package net.zdsoft.eis.base.event.impl;

import java.util.Iterator;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.zdsoft.eis.base.event.GroupEvent;
import net.zdsoft.eis.base.event.GroupListener;
import net.zdsoft.leadin.event.AbstractBaseEventDispatcher;
import net.zdsoft.leadin.event.BaseEvent;

public class GroupEventDispatcher extends AbstractBaseEventDispatcher {
    protected transient final Logger log = LoggerFactory.getLogger(getClass());

    @SuppressWarnings("unchecked")
    public void dispatchEvent(BaseEvent event) {
        if (!(event instanceof GroupEvent)) {
            return;
        }

        if (CollectionUtils.isEmpty(listenerList)) {
            log.info("The UserEventList is empty, the dispatch returned;");
            return;
        }

        Iterator<String> it = listenerList.iterator();
        String str;
        GroupListener groupListener = null;
        while (it.hasNext()) {
            str = (String) it.next();
            try {
                groupListener = null;
                Class cl = getClass().getClassLoader().loadClass(str);
                if (GroupListener.class.isAssignableFrom(cl)) {
                    groupListener = (GroupListener) cl.newInstance();
                } else {
                    break;
                }
            } catch (Exception e) {
                log.error(e.toString());
            }

            if (groupListener == null) {
                continue;
            }
            switch (event.getEventType()) {          
            case GroupEvent.GROUP_DELETE:
                groupListener.deleteGroup((GroupEvent) event);
                break;
            default:
                break;
            }
        }
    }

}
