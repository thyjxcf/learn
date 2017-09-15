package net.zdsoft.eis.base.event.impl;

import java.util.Iterator;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.zdsoft.eis.base.event.UserEvent;
import net.zdsoft.eis.base.event.UserListener;
import net.zdsoft.leadin.event.AbstractBaseEventDispatcher;
import net.zdsoft.leadin.event.BaseEvent;

public class UserEventDispatcher extends AbstractBaseEventDispatcher {
    protected transient final Logger log = LoggerFactory.getLogger(getClass());

    @SuppressWarnings("unchecked")
    public void dispatchEvent(BaseEvent event) {
        if (!(event instanceof UserEvent)) {
            return;
        }
        if (CollectionUtils.isEmpty(listenerList)) {
            log.info("The UserEventList is empty, the dispatch returned;");
            return;
        }

        Iterator<String> it = listenerList.iterator();
        String str;
        UserListener userListener = null;
        while (it.hasNext()) {
            str = (String) it.next();
            try {
                userListener = null;
                Class cl = getClass().getClassLoader().loadClass(str.trim());
                if (UserListener.class.isAssignableFrom(cl)) {
                    userListener = (UserListener) cl.newInstance();
                } else {
                    break;
                }
            } catch (Exception e) {
                log.error(e.toString());
            }
            if (userListener == null) {
                continue;
            }
            switch (event.getEventType()) {
            case UserEvent.USER_SAVE:
                userListener.insertUser((UserEvent) event);
                break;
            case UserEvent.USER_UPDATE:
                userListener.updateUser((UserEvent) event);
                break;
            case UserEvent.USER_DELETE:
                userListener.deleteUser((UserEvent) event);
                break;
            case UserEvent.USER_DIMISSION:
                userListener.updateUserDimission((UserEvent) event);
                break;
            default:
                break;
            }
        }

    }
}
