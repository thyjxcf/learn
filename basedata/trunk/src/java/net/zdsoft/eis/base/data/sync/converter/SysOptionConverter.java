/**
 * 
 */
package net.zdsoft.eis.base.data.sync.converter;

import com.winupon.syncdata.basedata.entity.son.MqSysOption;

import net.zdsoft.eis.base.common.entity.SysOption;
import net.zdsoft.eis.base.sync.SyncObjectConvertable;

/**
 * @author yanb
 * 
 */
public class SysOptionConverter implements SyncObjectConvertable<SysOption, MqSysOption> {

    @Override
    public void toEntity(MqSysOption m, SysOption e) {
        e.setOptionCode(m.getOptionCode());
        e.setName(m.getName());
        e.setDefaultValue(m.getDefaultValue());
        e.setDescription(m.getDescription());
        e.setNowValue(m.getNowValue());
        e.setValidateJS(m.getValidateJs());
        e.setViewable(m.isViewable() ? 1 : 0);
        e.setId(m.getId());
    }

    @Override
    public void toMq(SysOption e, MqSysOption m) {
        m.setOptionCode(e.getOptionCode());
        m.setName(e.getName());
        m.setDefaultValue(e.getDefaultValue());
        m.setDescription(e.getDescription());
        m.setNowValue(e.getNowValue());
        m.setValidateJs(e.getValidateJS());
        m.setViewable(0 == e.getViewable() ? false : Boolean.parseBoolean(String.valueOf(e
                .getViewable())));
        m.setId(e.getId());
    }

    public static void main(String[] args) {
        net.zdsoft.leadin.tmptool.DtoAssemblerTmp.printSetCode("e", "m", MqSysOption.class, true);
    }

}
