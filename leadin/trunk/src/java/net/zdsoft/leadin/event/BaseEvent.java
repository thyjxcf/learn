package net.zdsoft.leadin.event;

import java.util.Date;
import java.util.Map;

public interface BaseEvent {
    /**
     * Returns the event type corresponding to the event.
     * 
     * @return the event type corresponding to the event.
     */
    public int getEventType();

    /**
     * Returns a map of parameters which can be used to pass data to a listener.
     * 
     * @return map of parameters which can be used to pass data to a listener.
     */
    @SuppressWarnings("unchecked")
    public Map getParams();

    /**
     * Returns the date (as a Date object) that the event was created.
     * 
     * @return the date (as a Date object) that the event was created.
     */
    public Date getDate();
}
