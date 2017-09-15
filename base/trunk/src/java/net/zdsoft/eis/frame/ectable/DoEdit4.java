/** 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author songzc
 * @since 1.0
 * @version $Id: DoEdit4.java,v 1.2 2006/12/21 02:42:10 songzc Exp $
 */
package net.zdsoft.eis.frame.ectable;

import net.zdsoft.eis.frame.entity.HibernateEntity;

import org.extremecomponents.table.bean.Row;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.interceptor.RowInterceptor;




public class DoEdit4 implements RowInterceptor{
    
    public void addRowAttributes(TableModel tableModel, Row row) {
    }    

    public void modifyRowAttributes(TableModel model, Row row) {
        HibernateEntity hibernateEntity = (HibernateEntity) model.getCurrentRowBean();

        String id = hibernateEntity.getId();
        row.setOnclick("doEdit('"+id+"',event)"); 

    }
}