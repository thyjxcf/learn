package net.zdsoft.eis.base.event;

public interface UserListener {
    /**
     * 用户新增
     * 
     * @param userEvent
     */
    void insertUser(UserEvent userEvent);

    /**
     * 用户修改
     * 
     * @param userEvent
     */
    void updateUser(UserEvent userEvent);

    /**
     * 用户删除
     * 
     * @param userEvent
     */
    void deleteUser(UserEvent userEvent);

    /**
     * 用户离职锁定
     * 
     * @param userEvent
     */
    void updateUserDimission(UserEvent userEvent);
}
