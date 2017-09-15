package net.zdsoft.eis.base.affair;

import java.util.List;

/**
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jan 21, 2011 10:29:49 AM $
 */
public interface AffairDao {
    /**
     * 增加事项
     * 
     * @param affair
     */
    public void addAffair(Affair affair);

    /**
     * 删除事项
     * 
     * @param affairIds
     */
    public void deleteAffairs(String[] affairIds);

    /**
     * 删除事项
     * 
     * @param affairIds
     */
    public void deleteAffair(String identifier, String receiverId);
    
    /**
     * 更新事项
     * 
     * @param affair
     */
    public void updateAffair(Affair affair);

    /**
     * 更新事项是否完成
     * 
     * @param affair(identifier/receiverId,isComplete 不能为空)
     */
    public void updateAffairCompleteSign(Affair affair);

    /**
     * 更新系统内置的待办事项的receiverId
     * 
     * @param receiverId
     */
    public void updateAffairSystemReceiverId(String receiverId);

    /**
     * 获取事项
     * 
     * @param affairId
     * @return
     */
    public Affair getAffair(String affairId);
    
    /**
     * 获取事项
     * @param identifier
     * @param receiverId
     * @param senderId
     * @return
     */
    public Affair getAffair(String identifier, String receiverId,String senderId);

    /**
     * 获取事项
     * 
     * @param affairSource
     * @return
     */
    public List<Affair> getAffairs(int affairSource);
    
	/**
	 * 根据接收者id获取未完成的待办事项
	 * @param receiverId
	 * @return
	 */
	public List<Affair> getAffairByReceiverId(String receiverId);
	
	/**
	 * 获取指定数量的代办事项
	 * @param receiverId
	 * @param rowNum
	 * @return
	 */
	public List<Affair> getTopAffairByReceiverId(String receiverId,int rowNum);

}
