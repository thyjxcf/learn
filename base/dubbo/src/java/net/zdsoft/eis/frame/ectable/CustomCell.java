/** 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author wangsn
 * @since 1.0
 * @version $Id: CustomCell.java,v 1.1 2006/11/23 02:34:57 wangsn Exp $
 */
package net.zdsoft.eis.frame.ectable;

import java.util.Collection;

import org.apache.commons.beanutils.BeanUtils;
import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.cell.Cell;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.util.HtmlBuilder;



public class CustomCell implements Cell {
	  public String getExportDisplay(TableModel model, Column column) {
	      return null;
	  }

	  @SuppressWarnings({ "deprecation", "unchecked" })
    public String getHtmlDisplay(TableModel model, Column column) {
	      HtmlBuilder html = new HtmlBuilder();
	      
	      org.extremecomponents.table.view.html.CellBuilder.tdStart(html, column);
	      
	      try {
	          Object bean = model.getCurrentRowBean();
	          String presidentId = BeanUtils.getProperty(bean, "id");
	          
	          Collection selectids = (Collection)model.getContext().getRequestAttribute("SELECTED");
	          if ( selectids ==null ) {
	              html.input("checkbox").name("chkbx"+presidentId).value(presidentId);
	              html.xclose();
	          } else {
		          if (selectids.contains(presidentId)) {
		              html.input("checkbox").name("chkbx"+presidentId).value(presidentId);
		              html.checked();
		              html.xclose();
		          } else {
		              html.input("checkbox").name("chkbx"+presidentId).value(presidentId);
		              html.xclose();
		          }
	          }
	      } catch (Exception e) {}
	      
	      org.extremecomponents.table.view.html.CellBuilder.tdEnd(html);
	      
	      return html.toString();
	  }
	}