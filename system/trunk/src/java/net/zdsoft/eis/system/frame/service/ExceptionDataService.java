package net.zdsoft.eis.system.frame.service;

import net.zdsoft.eis.system.frame.dto.ResultPack;

public interface ExceptionDataService {
    /**
     * 执行sql的接口
     * 
     * @param taskContent
     * @param pageNum
     * @return
     * @throws Exception
     */
    public ResultPack[] saveExecuteQuery(String taskContent, int pageNum) throws Exception;

    /**
     * 执行更新及删除语句的接口
     * 
     * @param taskContent
     * @return
     */
    public Integer[] updateExecute(String taskContent) throws Exception;

}
