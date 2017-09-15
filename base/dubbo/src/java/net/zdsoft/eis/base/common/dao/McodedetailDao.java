package net.zdsoft.eis.base.common.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.constant.enumeration.McodeFieldType;
import net.zdsoft.keel.util.Pagination;

public interface McodedetailDao {
    /**
     * 根据微代码mcodeId获得微代码明细项列表（全部的,包括未启用的）
     * 
     * @param mcodeId 微代码类型ID
     * @return List
     */
    public List<Mcodedetail> getAllMcodeDetails(String mcodeId);
    
    /**
     * 根据微代码McodeId获得微代码明细项列表（在用的）
     * 
     * @param mcodeId 微代码类型ID
     * @return List
     */
    public List<Mcodedetail> getMcodeDetails(String mcodeId);
    
    /**
     * 根据微代码McodeId、区域类型和值 获得微代码明细项列表（在用的）
     * @param mcodeId
     * @param type
     * @param value
     * @return
     * @author zhangkc
     * @date 2015年4月1日 下午2:44:13
     */
    public List<Mcodedetail> getMcodeDetails(String mcodeId, McodeFieldType type, String value);
    
    /**
     * 根据微代码McodeId, name获得微代码明细项列表（在用的）
     * 
     * @param mcodeId 微代码类型ID
     * @param name 微代码名称
     * @return List
     */
    public List<Mcodedetail> getMcodeDetails(String mcodeId, String name);

    /**
     * 根据微代码McodeId和指定的thisId条件得到明细项列表（在用的）
     * 
     * @param mcodeId 微代码类型ID
     * @param thisId like '1%'
     * @return List
     */
    public List<Mcodedetail> getMcodeDetailFaintness(String mcodeId, String thisId);

    /**
     * 根据微代码mcodeId和微代码明细项的thisId获得微代码项
     * 
     * @param mcodeId
     * @param thisId
     * @return
     */
    public Mcodedetail getMcodeDetail(String mcodeId, String thisId);
    /**
     * 根据微代码mcodeId和微代码明细项的thisId 配置区域类型 区域值获得微代码项
     * @param mcodeId 
     * @param thisId
     * @param type 配置区域类型
     * @param value 区域值
     * @return
     * @author zhangkc
     * @date 2015年4月1日 下午2:45:16
     */
    public Mcodedetail getMcodeDetail(String mcodeId, String thisId, McodeFieldType type, String value);
    
    /**
     * map
     * @param mcodeId
     * @return key=thisId
     */
    public Map<String, Mcodedetail> getMcodeDetailMap(String mcodeId);
    /**
     * 根据微代码mcodeId获得微代码明细项列表（全部的,包括未启用的）
     * 
     * @param mcodeId 微代码类型ID
     * @param page
     * @return List
     */
    public List<Mcodedetail> getAllMcodeDetailsByPage(String mcodeId,Pagination page);

}

