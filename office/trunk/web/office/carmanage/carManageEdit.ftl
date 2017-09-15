<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="维护车辆">
<div id="carEditContainer">
<form name="carInfoForm" id="carInfoForm">
<@htmlmacro.tableDetail divClass="table-form">
        <input type="hidden" name="officeCarInfo.id" id="id" value="${officeCarInfo.id!}"/>
        <tr>
            <th colspan="4" style="text-align:center;">维护车辆</th>
        </tr>
        <tr>
            <th>车牌号码：</th>
            <td>
            	<input name="officeCarInfo.carNumber" id="carNumber" type="text" class="input-txt" style="width:150px;" maxlength="50" value="${officeCarInfo.carNumber!}" msgName="车牌号码" notNull="true" /><span class="c-orange ml-10">*</span>
            </td>
            <th>车辆型号：</th>
            <td>
            	<input name="officeCarInfo.carType" id="carType" type="text" class="input-txt" style="width:150px;" maxlength="50" value="${officeCarInfo.carType!}" msgName="车辆型号" />
            </td>
        </tr>
        <tr>
            <th>购买时间：</th>
            <td>
               <@htmlmacro.datepicker class="input-txt" name="officeCarInfo.buyDate" id="buyDate" value="${(officeCarInfo.buyDate?string('yyyy-MM-dd'))!}" size="20" msgName="购买时间"/>
            </td>
            <th>购买价格：</th>
            <td>
            	<input name="officeCarInfo.buyPrice" id="buyPrice" type="text" class="input-txt" style="width:150px;" dataType="integer" maxlength="9" value="${officeCarInfo.buyPrice!}" msgName="购买价格"/>
            </td>
        </tr>
        <tr>
            <th>车辆容量：</th>
            <td colspan="3">
            	<input name="officeCarInfo.seating" id="seating" type="text" class="input-txt" style="width:150px;" dataType="integer" maxlength="5" value="${officeCarInfo.seating!}" msgName="车辆容量" notNull="true" /><span class="c-orange ml-10">*</span>
            </td>
        </tr>
        <tr>
            <th>备注：</th>
            <td colspan="3">
				<textarea name="officeCarInfo.remark" id="remark" cols="70" rows="4" class="text-area my-5" style="width:80%;padding:5px 1%;height:50px;" msgName="备注" maxLength="200">${officeCarInfo.remark!}</textarea>
			</td>
        </tr>
        <tr>
        	<td colspan="4" class="td-opt">
        	    <a class="abtn-blue-big" href="javascript:void(0);" onclick="save()">保存</a>
			    <a class="abtn-blue-big ml-5" href="javascript:void(0);" onclick="back();">返回</a>
            </td>
        </tr> 
</@htmlmacro.tableDetail>
</form>
</div>

<script type="text/javascript">
var isSubmit = false;
function save(){
	if(isSubmit){
		return;
	}
	if(!checkAllValidate("#carEditContainer")){
		return;
	}
	isSubmit = true;
	var carInfoUrl = "${request.contextPath}/office/carmanage/carmanage-saveCarInfo.action";
		var options = {
          target : '#carInfoForm',
          url : carInfoUrl,
          success : showSuccess,
          dataType : 'json',
          clearForm : false,
          resetForm : false,
          type : 'post'
        };
      	$("#carInfoForm").ajaxSubmit(options);
		
	}
      
  //操作提示
  function showSuccess(data) {
    if (data!=null && data!=''){
      showMsgError(data);
      isSubmit = false;
      return;
    }else{
        showMsgSuccess("操作成功！", "提示", function(){
		  back();
		});
    }
  }

function back(){
	doSearch();
}
</script>

</@htmlmacro.moduleDiv>