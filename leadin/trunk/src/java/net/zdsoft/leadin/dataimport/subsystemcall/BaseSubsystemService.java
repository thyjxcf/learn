package net.zdsoft.leadin.dataimport.subsystemcall;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 28, 2010 2:19:59 PM $
 */
public interface BaseSubsystemService {
    /**
     * 得到全部行政区域list
     * 
     * @return [0]=fullCode;[1]=fullName
     */
    public List<String[]> getRegions();

    /**
     * 微代码
     * @param mcodeId
     * @return key=content;value=thisId
     */
    public Map<String, String> getCodeMap(String mcodeId);

    /**
     * 数据导入并发数
     * 
     * @return
     */
    public int getDataImportConcurrentcyNum();

    /**
     * 取用户名称，先取realname，如果realname为空，则取name
     * 
     * @param userIds
     * @return key=userId,value=realname|name
     */
    public Map<String, String> getUsersMap(String[] userIds);
    
    /**
     * 当前单位的所有上级单位，单位等级越值，排序号越小
     * 
     * @param unitId
     * @return <unitId,排序号>
     */
    public Map<String, Integer> getParentUnitMap(String unitId);

}
