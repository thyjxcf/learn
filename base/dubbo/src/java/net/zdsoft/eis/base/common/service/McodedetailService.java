package net.zdsoft.eis.base.common.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.constant.enumeration.McodeFieldType;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.leadin.cache.CacheManager;

public interface McodedetailService extends CacheManager {

    /**
     * 根据微代码类型id和微代码项的thisId获得微代码项
     * 
     * @param mcodeId
     * @param thisId
     * @return
     */
    public Mcodedetail getMcodeDetail(String mcodeId, String thisId);
    /**
     * 根据微代码类型id和微代码项的thisId以及 微代码配置类型和值 获得微代码项
     * @param mcodeId
     * @param thisId
     * @param fieldType
     * @param fieldValue
     * @return
     * @author zhangkc
     * @date 2015年4月1日 下午3:04:50
     */
    public Mcodedetail getMcodeDetail(String mcodeId, String thisId, McodeFieldType fieldType, String fieldValue);
    
    /**
     * 根据微代码类型获得微代码明细项列表(包括未启用的)
     * 
     * @param mcodeId
     * @return
     */
    public List<Mcodedetail> getAllMcodeDetails(String mcodeId);

    /**
     * 根据微代码类型获得所有在用的微代码明细项列表
     * 
     * @param mcodeId
     * @return List
     */
    public List<Mcodedetail> getMcodeDetails(String mcodeId);
    /**
     * 根据微代码类型、配置区域类型、配置区域值 获得所有在用的微代码明细项列表
     * @param mcodeId
     * @param fieldType
     * @param fieldValue
     * @return
     * @author zhangkc
     * @date 2015年4月1日 下午3:05:51
     */
    public List<Mcodedetail> getMcodeDetails(String mcodeId, McodeFieldType fieldType, String fieldValue );
    /**
     * 根据微代码类型ID和指定的thisId得到明细项列表（在用的）
     * 
     * @param mcodeId 微代码类型ID
     * @param thisId like '1%'
     * @return List
     */
    public List<Mcodedetail> getMcodeDetailFaintness(String mcodeId, String thisId);

    /**
     * 根据微代码类型ID获得微代码明细项列表（在用的），其中不包括thisIds中的一些明细项
     * 
     * @param mcodeId
     * @param thisIds
     * @return
     */
    public List<Mcodedetail> getMcodeDetails(String mcodeId, String[] thisIds);
    
    /**
     * 根据微代码类型ID获得微代码明细项列表（在用的），其中不包括thisIds中的一些明细项
     * 
     * @param mcodeId
     * @param name 
     * @param thisIds
     * @return
     */
    public List<Mcodedetail> getMcodeDetails(String mcodeId, String name, String[] thisIds);

    /**
     * 根据微代码类型ID获得微代码明细项Map
     * 
     * @param mcodeIds
     * @return value为mcodeId下所有微代码的content相加
     */
    public Map<String, String> getTotalContents(String[] mcodeIds);

    /**
     * 取微代码明细map
     * 
     * @param mcodeId
     * @return
     */
    public Map<String, Mcodedetail> getMcodeDetailMap(String mcodeId);

    /**
     * 拼成Map
     * 
     * @param mcodeIds
     * @return key=mcodeId+thiIid,value=content
     */
    public Map<String, String> getContentMap(String[] mcodeIds);

    /**
     * 根据微代码类型ID获得微代码明细项Map
     * 
     * @param mcodeIds
     * @return
     */
    public Map<String, Map<String, String>> getContent2Map(String[] mcodeIds);

    /**
     * 根据微代码类型ID获得微代码明细项Map
     * 
     * @param mcodeIds
     * @return list的第一个元素以thisId为key;list的第二个元素以content为key
     */
    public Map<String, List<Map<String, String>>> getContentListMap(String[] mcodeIds);
    /**
     * 根据微代码mcodeId获得微代码明细项列表（全部的,包括未启用的）
     * 
     * @param mcodeId 微代码类型ID
     * @param page
     * @return List
     */
    public List<Mcodedetail> getAllMcodeDetailsByPage(String mcodeId,Pagination page);

}
