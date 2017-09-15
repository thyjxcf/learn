package net.zdsoft.eis.base.common.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.common.service.Mcode;
import net.zdsoft.eis.base.common.service.McodeService;
import net.zdsoft.eis.base.common.service.McodedetailService;


/* 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author Dongzk
 * @since 1.0
 * @version $Id: McodeServiceImpl.java,v 1.4 2006/10/09 06:16:32 dongzk Exp $
 */
public class McodeServiceImpl implements McodeService {
	private Logger log = LoggerFactory.getLogger(this.getClass().getName());
	
	private McodedetailService mcodedetailService;
	
	public void setMcodedetailService(McodedetailService mcodedetailService) {
		this.mcodedetailService = mcodedetailService;
	}

	public Mcode getMcode(String mcodeId) {
	    if (mcodeId == null){
            throw new IllegalArgumentException("微代码类型名称不能为空!");
        }
        
        Map<String, Mcodedetail> mcodedetailMap = new LinkedHashMap<String, Mcodedetail>();
        List<Mcodedetail> list = mcodedetailService.getMcodeDetails(mcodeId);       
        
        if(list != null){
            for (Iterator<Mcodedetail> it = list.iterator(); it.hasNext(); ) {
                Mcodedetail mcodedetail = it.next();
                mcodedetailMap.put(mcodedetail.getThisId(), mcodedetail);
            }
        }
        
        Mcode mcode = new McodeImpl(mcodeId,mcodedetailMap);        
        return mcode;
	}
	
	public Mcode getMcode(String mcodeId, boolean includeNoIsusing) {
		if (StringUtils.isEmpty(mcodeId)){
            throw new IllegalArgumentException("微代码类型名称不能为空!");
        }
		
		if(!includeNoIsusing){
			return this.getMcode(mcodeId);
		}else{
			Map<String, Mcodedetail> mcodedetailMap = new LinkedHashMap<String, Mcodedetail>();
	        List<Mcodedetail> list = mcodedetailService.getAllMcodeDetails(mcodeId);       
	        
	        if(list != null){
	            for (Iterator<Mcodedetail> it = list.iterator(); it.hasNext(); ) {
	                Mcodedetail mcodedetail = it.next();
	                mcodedetailMap.put(mcodedetail.getThisId(), mcodedetail);
	            }
	        }
	        
	        Mcode mcode = new McodeImpl(mcodeId,mcodedetailMap);        
	        return mcode;
		}
	}
	
	public Mcode getMcodeFaintness(String mcodeId, String thisId) {	
		log.info("getMcode: get mcode list of mcodeId and mcode detail");
		
		if (mcodeId == null){
			throw new IllegalArgumentException("微代码类型名称不能为空!");
		}
		
		Map<String, Mcodedetail> mcodedetailMap = new LinkedHashMap<String, Mcodedetail>();
		List<Mcodedetail> list = mcodedetailService.getMcodeDetailFaintness(mcodeId, thisId);
		
		if(list != null){
			for (Iterator<Mcodedetail> it = list.iterator(); it.hasNext(); ) {
				Mcodedetail mcodedetail = it.next();
				mcodedetailMap.put(mcodedetail.getThisId(), mcodedetail);
			}
		}
		
		Mcode mcode = new McodeImpl(mcodeId,mcodedetailMap);
		
		return mcode;
	}

	public List<String[]> getMcodeAarray(String mcodeId) {
	    log.info("getMcodeAarray: get mcode detial of thisId and context under condition");
        
        ArrayList<String[]> array = new ArrayList<String[]>();
        List<Mcodedetail> list = mcodedetailService.getMcodeDetails(mcodeId); 
        
        for (Iterator<Mcodedetail> it = list.iterator(); it.hasNext(); ) {
            Mcodedetail mcodedetail = it.next();
            array.add(new String[]{mcodedetail.getThisId(),mcodedetail.getContent()});
        }
        
        return array;
	}

	public String getMcodeContext(String mcodeId, String mDetailId) {
		Mcodedetail mDetailDto= mcodedetailService.getMcodeDetail(mcodeId, mDetailId);
		if(mDetailDto==null){
			return null;
		}else{
			return mDetailDto.getContent();
		}
		
	}
	
}



