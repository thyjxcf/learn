/** 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author zhengyy
 * @since 1.0
 * @version $Id: DoEdit.java,v 1.1 2006/11/23 02:34:58 wangsn Exp $
 */
package net.zdsoft.eis.frame.ectable;

import org.extremecomponents.table.bean.Row;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.interceptor.RowInterceptor;

import net.zdsoft.eis.frame.client.BaseEntity;
import net.zdsoft.eis.frame.dto.BaseDto;
import net.zdsoft.eis.frame.dto.BaseDto3;




public class DoEdit implements RowInterceptor{
	
	public void addRowAttributes(TableModel tableModel, Row row) {
	}    

	public void modifyRowAttributes(TableModel model, Row row) {
	    String id = null;
	    Object object = model.getCurrentRowBean();
        if (object instanceof BaseDto3) {
            id = ((BaseDto) object).getId();
        } else if (object instanceof BaseEntity) {
            id = ((BaseEntity) object).getId();
        } else if(object instanceof BaseDto){
            id = ((BaseDto) object).getId();
        }
		row.setOnclick("doEdit('"+id+"',event)");	

	}
}