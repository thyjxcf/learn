package net.zdsoft.office.dataAnalysis.action;

import net.zdsoft.eis.base.common.action.ObjectDivAction;

import net.zdsoft.eis.base.simple.entity.SimpleObject;
import net.zdsoft.office.dataAnalysis.entity.OfficeDataReportStreet;
import net.zdsoft.office.dataAnalysis.service.OfficeDataReportStreetService;

import java.util.List;

public class OfficeDataAnalysisStreetDivAction  extends ObjectDivAction<OfficeDataReportStreet>{
    private OfficeDataReportStreetService officeDataReportStreetService;
    @Override
    protected void toObject(OfficeDataReportStreet officeDataReportStreet, SimpleObject object) {
        if(officeDataReportStreet == null){
            return;
        }
        if(object == null){
            return;
        }

        object.setId(officeDataReportStreet.getId());
        object.setObjectName(officeDataReportStreet.getStreetName());
        object.setUnitId(officeDataReportStreet.getUnitId());

    }


    public  List<OfficeDataReportStreet> getDatasByUnitId() {

        String unitId = this.getLoginInfo().getUnitID();

        List<OfficeDataReportStreet> list = officeDataReportStreetService.getOfficeDataReportStreetByUnitIdPage(unitId,null);

        return list;
    }

    public void setOfficeDataReportStreetService(OfficeDataReportStreetService officeDataReportStreetService) {
        this.officeDataReportStreetService = officeDataReportStreetService;
    }

}
