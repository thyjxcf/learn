/**
 * 
 */
package net.zdsoft.eis.base.remote.param.dto;

/** 
 * 返回参数
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author zhaosf
 * @since 1.0
 * @version $Id: OutParamDto.java,v 1.2 2006/12/26 13:33:34 jiangl Exp $
 */

public class OutParamDto {
	public static final int CODE_OK = 1; //成功
	public static final int CODE_ERROR = -1; //失败
	
	private int code;//代码	
	private String content;//内容
    
    public OutParamDto(){        
    }
    
    public OutParamDto(int code,String content){
        this.code=code;
        this.content=content;
    }

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}


