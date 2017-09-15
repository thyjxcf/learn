<#import "/common/htmlcomponent.ftl" as common />
<#import "/common/commonmacro.ftl" as commonmacro>
<@common.moduleDiv titleName="">
<script>
var isSubmit = false;
function saveDuty(){
if (isSubmit){
	return;
}
isSubmit = true;
if(!checkAllValidate("#dutyForm")){
	isSubmit=false;
	return;
}

	var options = {
		url : "${request.contextPath}/office/dutymanage/dutymanage-dutyRegisterSave.action",
		success : function(data){
			if(!data.operateSuccess){
			   if(data.errorMessage!=null&&data.errorMessage!=""){
				   showMsgError(data.errorMessage);
				   isSubmit = false;
				   return;
			   }
			}else{
				showMsgSuccess(data.promptMessage,"",function(){
				  	dutyRegister();
				});
				return;
			}
		},
		dataType : 'json',
		clearForm : false,
		resetForm : false,
		type : 'post',
		error:function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}//请求出错 
	};
  	$("#dutyForm").ajaxSubmit(options);
}
</script>

<form id="dutyForm" name="dutyForm">
<div class="pub-table-wrap">
	<div class="pub-table-inner mt-30">
        <div class="table-fen1-wrap" id="driverManageDiv">
            <table class="public-table table-list table-list-edit table-fen1" id="groupTable">
            <input type="hidden" id="applyDate" name="applyDate" value="${applyDate?string('yyyy-MM-dd')!}"/>
                <thead>
            	<tr>
		        	<th colspan="4" style="text-align:center;">${officeDutyInformationSet.dutyName!}登记表</th>
		    	</tr>
		    	<tr>
		    		<th colspan="4" style="text-align:center;">值班时间:${applyDate?string('yyyy-MM-dd')}<span style="margin-right:50px;"></span>值班人:${userName!}</th>
		    	</tr>
                <tr>
                    <th width="20%">巡查地点</th>
                    <th width="60%">巡查情况</th>
                    <th width="20%">巡查时间</th>
                </tr>
                </thead>
				<tbody>
				<#assign index = 0>
                <#if officeDutyPlaces?exists && officeDutyPlaces?size gt 0>
                <#list officeDutyPlaces as officeDutyPlace>
                <tr>
                    <td>
                    	<input type="hidden" id="unitId${index}" name="officeDutyPlaces[${index}].unitId" value="${officeDutyPlace.unitId!}"/>
                    	<input type="hidden" id="id${index}" name="officeDutyPlaces[${index}].id" value="${officeDutyPlace.id!}"/>
                    	<input type="hidden" id="dutyApplyId${index}" name="officeDutyPlaces[${index}].dutyApplyId" value="${officeDutyInformationSet.id!}"/>
                    	<input type="hidden" id="patrolPlaceId${index}" name="officeDutyPlaces[${index}].patrolPlaceId" value="${officeDutyPlace.patrolPlaceId!}"/>
                    	<input type="hidden" id="dutyTime${index}" name="officeDutyPlaces[${index}].dutyTime" value="${officeDutyPlace.dutyTime!}"/>
                    	<input type="hidden" id="dutyUserId${index}" name="officeDutyPlaces[${index}].dutyUserId" value="${officeDutyPlace.dutyUserId!}"/>
                    	<input type="hidden" id="createTime${index}" name="officeDutyPlaces[${index}].createTime" value="${officeDutyPlace.createTime!}"/>
                    	${officeDutyPlace.placeName!}
                    </td>
                    <td>
						<input class="edit-txt input-txt" style="width:98%;" id="patrolContent${index}" name="officeDutyPlaces[${index}].patrolContent" maxLength="500" msgName="巡查情况"  value="${officeDutyPlace.idExit?string(officeDutyPlace.patrolContent!,'正常')}"/>
					</td>
                    <td>
						<input class="edit-txt input-txt" style="width:50%;" id="patrolTime${index}" name="officeDutyPlaces[${index}].patrolTime" maxLength="100" msgName="巡查时间" value="${officeDutyPlace.patrolTime?default('')}"/>
					</td>
                </tr>
                <#assign index=index+1>
                </#list>
                </#if>
            </tbody>
            </table>
        </div>
        <p class="t-center pt-30">
            <a href="javascript:void(0);" class="abtn-blue-big" onclick="saveDuty();">保存</a>
        </p>
    </div>
</div>
</form>
<script>
vselect();
$(document).ready(function(){
<#if officeDutyInformationSet?exists&&officeDutyInformationSet.id! ==''>
	<#if admin>
	load('#mySalaryListDiv','${request.contextPath}/common/tipMsg.action?msg=你没有安排值班活动，请与管理员联系！');
	<#else>
	load('#dutyDiv','${request.contextPath}/common/tipMsg.action?msg=你没有安排值班活动，请与管理员联系！');
	</#if>
</#if>
});
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>