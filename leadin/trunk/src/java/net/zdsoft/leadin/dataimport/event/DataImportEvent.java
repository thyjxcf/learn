package net.zdsoft.leadin.dataimport.event;

import net.zdsoft.keel.action.Reply;
import net.zdsoft.leadin.dataimport.param.DataImportParam;

/**
 * @author linqzh 2007-3-25
 * @version $Revision: 1.1 $, $Date: 2007/03/26 13:05:00 $
 */
public class DataImportEvent {
    public static final int DO_BEFORE = 1;
    public static final int DO_AFTER = 2;

    private int eventType;
    private DataImportParam param;
    private Reply reply;

    public DataImportEvent(DataImportParam param, Reply reply, int eventType) {
        this.param = param;
        this.reply = reply;
        this.eventType = eventType;
    }

    /**
     * @return the eventType
     */
    public int getEventType() {
        return eventType;
    }

    /**
     * @return Returns the param.
     */
    public DataImportParam getParam() {
        return param;
    }

    /**
     * @return the reply
     */
    public Reply getReply() {
        return reply;
    }

}
