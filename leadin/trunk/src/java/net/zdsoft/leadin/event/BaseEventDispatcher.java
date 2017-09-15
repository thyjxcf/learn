package net.zdsoft.leadin.event;

public interface BaseEventDispatcher {
    /**
     * 发布事件
     * 
     * @param event
     *            the event to possibly dispatch.
     */
    public void dispatchEvent(BaseEvent event);
}
