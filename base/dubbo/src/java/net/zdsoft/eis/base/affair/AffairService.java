/* 
 * @(#)AffairService.java    Created on Dec 29, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.affair;

import java.util.List;
import java.util.Set;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 29, 2010 3:14:57 PM $
 */
public interface AffairService {
    /**
     * 增加事项
     * 
     * @param affair
     */
    public void addAffair(Affair affair);

    /**
     * 更新事项
     * 
     * @param affair
     */
    public void updateAffair(Affair affair);

    /**
     * 如果事项不存在，则增加事项，否则更新事项(根据identifier/receiverId/senderId更新)
     * 
     * @param affair
     */
    public void saveAffair(Affair affair);

    /**
     * 更新事项是否完成
     * 
     * @param affair(identifier/receiverId,isComplete 不能为空)
     */
    public void updateAffairCompleteSign(Affair affair);

    /**
     * 更新事项是否完成
     * 
     * @param transactable
     */
    public void updateAffairCompleteSign(Transactable transactable);

    /**
     * 更新系统内置的待办事项的receiverId为顶级单位id
     */
    public void updateAffairSystemReceiverId();

    /**
     * 删除事项
     * 
     * @param affairIds
     */
    public void deleteAffairs(String[] affairIds);

    /**
     * 删除事项
     * 
     * @param transactable
     */
    public void deleteAffair(Transactable transactable);

    /**
     * 运行事项任务
     */
    public void runAffairJob();

    /**
     * 获取事项
     * 
     * @param affairId
     * @return
     */
    public Affair getAffair(String affairId);
    
    /**
	 * 根据接收者id获取未完成的待办事项
	 * 
	 * @param receiverId
	 * @param modSet
	 *            当前用户拥有的模块集合
	 * @param operSet
	 *            当前用户拥有的操作集合
	 * @param rowNum
	 *            显示的行数 0 表示取出全部记录数
	 * @return
	 */
	public List<Affair> getAffairByReceiverId(String receiverId,
			Set<Integer> modSet, Set<String> operSet, int rowNum);

	/**
	 * 根据接收者id获取未完成的待办事项数量
	 * 
	 * @param receiverId
	 * @param modSet
	 *            当前用户拥有的模块集合
	 * @param operSet
	 *            当前用户拥有的操作集合
	 * @return
	 */
	public int getAffairNumByReceiverId(String receiverId, Set<Integer> modSet,
			Set<String> operSet);

}
