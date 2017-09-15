package net.zdsoft.eis.base.common.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.common.service.McodedetailService;
import net.zdsoft.eis.base.simple.entity.SimpleObject;

public class McodeDetailDivAction extends ObjectDivAction<Mcodedetail> {

    private static final long serialVersionUID = 4832380912850622177L;

    private McodedetailService mcodedetailService;

    //过滤掉的微代码的thisId 通过‘,’拼接
    private String codes;
    
    public McodeDetailDivAction() {
    	setShowLetterIndex(true);
	}

	@Override
    protected List<Mcodedetail> getDatasByGroupId() {
        if (!(StringUtils.isBlank(groupId))) {
        	
        	if(StringUtils.isNotEmpty(codes)){
        		return mcodedetailService.getMcodeDetails(groupId, codes.split(","));
        	}
            return mcodedetailService.getMcodeDetails(groupId);
        } else {
            return null;
        }
    }

    @Override
    protected void toObject(Mcodedetail e, SimpleObject object) {
        if (e == null) {
            return;
        }
        if (object == null) {
            return;
        }

        object.setId(e.getThisId());
        object.setObjectCode(e.getThisId());
        object.setObjectName(e.getContent());
        object.setUnitiveCode("");
    }

    public void setMcodedetailService(McodedetailService mcodedetailService) {
        this.mcodedetailService = mcodedetailService;
    }

	public void setCodes(String codes) {
		this.codes = codes;
	}
}
