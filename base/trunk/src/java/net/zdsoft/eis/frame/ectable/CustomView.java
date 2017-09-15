/** 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author wangsn
 * @since 1.0
 * @version $Id: CustomView.java,v 1.1 2006/11/23 02:34:57 wangsn Exp $
 */
package net.zdsoft.eis.frame.ectable;

import org.extremecomponents.table.view.*;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.util.HtmlBuilder;


public class CustomView extends AbstractHtmlView {
    protected void beforeBodyInternal(TableModel model) {
        getTableBuilder().tableStart();

        getTableBuilder().theadStart();
        
        getTableBuilder().titleRowSpanColumns();

        
        getTableBuilder().filterRow();

        getTableBuilder().headerRow();

        getTableBuilder().theadEnd();
        

        getTableBuilder().tbodyStart();
    }

    protected void afterBodyInternal(TableModel model) {
        getCalcBuilder().defaultCalcLayout();

        getTableBuilder().tbodyEnd();

        //toolbar(getHtmlBuilder(), getTableModel());
        getTableBuilder().tableEnd();
    }
    
    protected void toolbar(HtmlBuilder html, TableModel model) {
        new CustomToolbar(html, model).layout();
    }
}
