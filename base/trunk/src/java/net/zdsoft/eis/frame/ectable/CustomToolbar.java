/** 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author wangsn
 * @since 1.0
 * @version $Id: CustomToolbar.java,v 1.1 2006/11/23 02:34:57 wangsn Exp $
 */
package net.zdsoft.eis.frame.ectable;



import java.util.Iterator;

import org.extremecomponents.table.bean.Export;
import org.extremecomponents.table.core.TableConstants;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.view.html.BuilderConstants;
import org.extremecomponents.table.view.html.BuilderUtils;
import org.extremecomponents.table.view.html.TableActions;
import org.extremecomponents.table.view.html.ToolbarBuilder;
import org.extremecomponents.table.view.html.TwoColumnRowLayout;
import org.extremecomponents.util.HtmlBuilder;

/**
 * @author Jeff Johnston
 */
public class CustomToolbar extends TwoColumnRowLayout {
    public CustomToolbar(HtmlBuilder html, TableModel model) {
        super(html, model);
    }

    protected boolean showLayout(TableModel model) {
        boolean showStatusBar = BuilderUtils.showStatusBar(model);
        boolean filterable = BuilderUtils.filterable(model);
        boolean showExports = BuilderUtils.showExports(model);
        boolean showPagination = BuilderUtils.showPagination(model);
        boolean showTitle = BuilderUtils.showTitle(model);
        if (!showStatusBar && !filterable && !showExports && !showPagination && !showTitle) {
            return false;
        }

        return true;
    }

    protected void columnLeft(HtmlBuilder html, TableModel model) {
        html.td(4).styleClass(BuilderConstants.COMPACT_TOOLBAR_CSS).close();
        html.append("<input type='checkbox' >全选&nbsp;&nbsp;");
        html.append("<input type='submit' name='Submit' value='删除' class='del_button1'>");
        html.tdEnd();
    }

    @SuppressWarnings("unchecked")
    protected void columnRight(HtmlBuilder html, TableModel model) {
        boolean filterable = BuilderUtils.filterable(model);
        boolean showPagination = BuilderUtils.showPagination(model);
        boolean showExports = BuilderUtils.showExports(model);

        ToolbarBuilder toolbarBuilder = new ToolbarBuilder(html, model);

        html.td(4).styleClass(BuilderConstants.COMPACT_TOOLBAR_CSS).align("right").close();

        html.table(4).border("0").cellPadding("1").cellSpacing("2").close();
        html.tr(5).close();

        if (showPagination) {
        	html.td(5).close();
        	html.append("共"+String.valueOf(model.getLimit().getTotalRows())+"条");
        	html.tdEnd();
        	
            html.td(5).close();
            toolbarBuilder.firstPageItemAsImage();
            html.tdEnd();

            html.td(5).close();
            toolbarBuilder.prevPageItemAsImage();
            html.tdEnd();

            html.td(5).close();
            toolbarBuilder.nextPageItemAsImage();
            html.tdEnd();

            html.td(5).close();
            toolbarBuilder.lastPageItemAsImage();
            html.tdEnd();
            

            /*
            html.td(5).close();
            toolbarBuilder.separator();
            html.tdEnd();
            */
            
            //转到第几页
            html.td(5).close();
            html.append("转");
            switchToPage(html,model);
            html.append("页");
            html.tdEnd();
            
            
            //每页显示几条
            html.td(5).close();
            html.append("&nbsp;&nbsp;");
            rowsDisplayedInput(html,model);
            html.append("条/页");
            html.tdEnd();

            
            /*
            html.td(5).close();
            toolbarBuilder.rowsDisplayedDroplist();
            html.tdEnd();
            */
            
            

            if (showExports) {
              //  html.td(5).close();
              //  toolbarBuilder.separator();
              //  html.tdEnd();
            }
        }

        if (showExports) {
            Iterator iterator = model.getExportHandler().getExports().iterator();
            for (Iterator iter = iterator; iter.hasNext();) {
                html.td(5).close();
                Export export = (Export) iter.next();
                toolbarBuilder.exportItemAsImage(export);
                html.tdEnd();
            }
        }

        if (filterable) {
            if (showExports || showPagination) {
               // html.td(5).close();
               // toolbarBuilder.separator();
               // html.tdEnd();
            }

            //html.td(5).close();
            //toolbarBuilder.filterItemAsImage();
            //html.tdEnd();

            //html.td(5).close();
            //toolbarBuilder.clearItemAsImage();
            //html.tdEnd();
        }

        html.trEnd(5);

        html.tableEnd(4);

        html.tdEnd();
    }
    
    private void switchToPage(HtmlBuilder html, TableModel model) {
    	
    	 int totalPages = BuilderUtils.getTotalPages(model);
         

        html.select().name(model.getTableHandler().prefixWithTableId() + TableConstants.ROWS_DISPLAYED);

        StringBuffer onchange = new StringBuffer();
        onchange.append(getSwitchToPageAction(model));
        html.onchange(onchange.toString());

        html.close();

        html.newline();
        html.tabs(4);
        for (int i=1; i<=totalPages; i++) {

	        html.option().value(String.valueOf(i));
	        html.close();
	        html.append(String.valueOf(i));
	        html.optionEnd();
        }

        html.newline();
        html.tabs(4);
        html.selectEnd();
    }
    private String getSwitchToPageAction(TableModel model ) {
    	
        StringBuffer result = new StringBuffer();
        
        String form = BuilderUtils.getForm(model);
        String selectedOption = "this.options[this.selectedIndex].value";
        
        result.append("document.forms.").append(form).append(".");
        result.append(model.getTableHandler().prefixWithTableId()).append(TableConstants.PAGE);
        result.append(".value=").append(selectedOption).append(";");
        result.append(new TableActions(model).getOnInvokeAction());
        
        return result.toString();
    }
    
    private void rowsDisplayedInput( HtmlBuilder html,TableModel model ) {
    	String form = BuilderUtils.getForm(model);
    	html.append("<input type='text' size='3' OnkeyPress=\"");
        html.append("  var  keycode  =  event.keyCode;   ");
        html.append(" if (keycode == 13 || keycode == 3) {");
        html.append("document.forms.").append(form).append(".");
        html.append(model.getTableHandler().prefixWithTableId()).append(TableConstants.CURRENT_ROWS_DISPLAYED);
        html.append(".value=").append("event.srcElement.value").append(";");
        
        html.append("document.forms.").append(form).append(".");
        html.append(model.getTableHandler().prefixWithTableId()).append(TableConstants.PAGE);
        html.append(".value='").append("1").append("';");
        
        String action = model.getTableHandler().getTable().getAction();
        html.append("document.forms.").append(form).append(".setAttribute('action','").append(action).append("');");
        
        String method = model.getTableHandler().getTable().getMethod();
        html.append("document.forms.").append(form).append(".setAttribute('method','").append(method).append("');");

        html.append("document.forms.").append(form).append(".submit()");
        
        html.append("}\" >"); 

        
    }
}
