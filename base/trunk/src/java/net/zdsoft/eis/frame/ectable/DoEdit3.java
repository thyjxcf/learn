package net.zdsoft.eis.frame.ectable;

import org.extremecomponents.table.bean.Row;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.interceptor.RowInterceptor;

import net.zdsoft.eis.frame.dto.BaseDto3;
import net.zdsoft.keelcnet.entity.EntityObject;

public class DoEdit3 implements RowInterceptor {

    public void addRowAttributes(TableModel tableModel, Row row) {
    }

    public void modifyRowAttributes(TableModel model, Row row) {
        Long id = 0L;
        
        Object object = model.getCurrentRowBean();
        if (object instanceof BaseDto3) {
            id = ((BaseDto3) object).getId();
        } else if (object instanceof EntityObject) {
            id = ((EntityObject) object).getId();
        }
        row.setOnclick("doEdit('" +id + "',event)");

    }

}
