<#import "/common/htmlcomponent.ftl" as common>
<#import "/common/commonmacro.ftl" as commonmacro>
<@common.moduleDiv>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
<script type="text/javascript" charset="utf-8" src="${request.contextPath}/static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${request.contextPath}/static/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${request.contextPath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/ueditor/themes/default/css/ueditor.css"/>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/ueditor/themes/iframe.css"/>
<script>
	function saveOfficeWorkReport(state){
		if(!isActionable("#btnPass")){
			return;
		}
		if(!isActionable("#btnReject")){
			return;
		}
		$("#state").val(state);
	  	if(!checkAllValidate()){
	  		return;
	  	}
	  	var reportType=$("#reportType").val();
	  	if(reportType==null||reportType==''){
	  		showMsgWarn("请选择汇报类型！");
	  		return;
	  	}
	  	var beginTime= $("#beginTime").val();
   		var endTime=$("#endTime").val();
    	if(beginTime > endTime){
				showMsgWarn("开始时间不能大于结束时间，请重新选择！");
				return;
			}
		
		if($("#receiveUserId").val().length > 0){
			var receiveUserId = $("#receiveUserId").val().split(",");
			if(receiveUserId.length>30){
		    	showMsgWarn("接收人数量不能大于30个!");
		    	return;
		    }
		}
	  	//var content = $("textarea[name='officeWorkReport.content']") .val();
		//if (content == null || content == "") {
		//   showMsgWarn("汇报内容不能为空!");
	    //   return;
		//}
		$("#btnPass").attr("class", "abtn-unable");
		$("#btnReject").attr("class", "abtn-unable");
		$.ajax({
			type: "POST",
			url: "${request.contextPath}/office/workreport/workReport-myWorkReportSave.action",
			data: $('#officeWorkReportform').serialize(),
			success: function(data){
				$("#btnPass").attr("class", "abtn-blue");
				$("#btnReject").attr("class", "abtn-blue");
				if(data == null && data==''){
					showMsgError(data);
					return;
				}else{
					showMsgSuccess(data.promptMessage,"", function(){
			    		myWorkReport();
					});
					return;
				}
			},
			dataType: "json",
			error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
		});
	};
	function resetUserIds(){
	if($("#receiveUserId").val().length > 0){
	var receiveUserId = $("#receiveUserId").val().split(",");
		var divSpan = "";
		if(receiveUserId.length>30){
	    	showMsgWarn("接收人数量不能大于30个！");
	   	}
		//for(var i = 0; i < receiveUserId.length; i++ ){
		//	divSpan += "<span id="+userIds[i]+" name="+userNames[i]+" detailName="+detailNames[i]+">"+userNames[i]+"</span>";
		//}
		//$("#userSpanDiv").html(divSpan);
	}
};
function changeTime(){
	var reportType=$("#reportType").val();
	if(reportType==1){
		$("#beginTime").val($("#nowWeekFirstDay").val());
		$("#endTime").val($("#nowWeekLastDay").val());
		$("#receiveUserId").val($("#userIdsWeek").val());
		$("#detailNames").val($("#userNamesWeek").val());
	}else if(reportType==2){
		$("#beginTime").val($("#nowMonthFirstDay").val());
		$("#endTime").val($("#nowMonthLastDay").val());
		$("#receiveUserId").val($("#userIdsMonth").val());
		$("#userName").val($("#userNamesMonth").val());
		$("#detailNames").val($("#userNamesMonth").val());
	}else{
		$("#beginTime").val("");
		$("#endTime").val("");
	}
}
</script>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
<div class="pub-table-wrap">
<form action="" method="post" name="officeWorkReportform" id="officeWorkReportform">
<input type="hidden" id="nowWeekFirstDay"  value="${(nowWeekFirstDay?string('yyyy-MM-dd'))?if_exists}"/>
<input type="hidden" id="nowWeekLastDay" value="${(nowWeekLastDay?string('yyyy-MM-dd'))?if_exists}"/>
<input type="hidden" id="nowMonthFirstDay" value="${(nowMonthFirstDay?string('yyyy-MM-dd'))?if_exists}"/>
<input type="hidden" id="nowMonthLastDay"  value="${(nowMonthLastDay?string('yyyy-MM-dd'))?if_exists}"/>
<input type="hidden" id="userIdsMonth" value="${userIds?default('')}"/>
<input type="hidden" id="userNamesMonth"  value="${userNames?default('')}"/>
<input type="hidden" id="userIdsWeek" value="${officeWorkReport.receiveUserId?default('')}"/>
<input type="hidden" id="userNamesWeek"  value="${officeWorkReport.userName?default('')}"/>

<input type="hidden" id="id" name="officeWorkReport.id" value="${officeWorkReport.id?default('')}"/>
<input type="hidden" id="unitId" name="officeWorkReport.unitId" value="${officeWorkReport.unitId?default('')}"/>
<input type="hidden" id="createUserId" name="officeWorkReport.createUserId" value="${officeWorkReport.createUserId?default('')}"/>
<input type="hidden" id="deptId" name="officeWorkReport.deptId" value="${officeWorkReport.deptId?default('')}"/>
<input type="hidden" id="deptId" name="officeWorkReport.createUserName" value="${officeWorkReport.createUserName?default('')}"/>
<input type="hidden" id="state" name="officeWorkReport.state"/>
<table border="0" cellspacing="0" cellpadding="0" class="table-edit mt-5">
        <tr>
            <th><span class="c-red">*</span> 汇报类型：</th>
            <td>
            	<@common.select style="width:120px;" myfunchange="changeTime" valName="officeWorkReport.reportType" valId="reportType" notNull="true" msgName="汇报类型">
					${appsetting.getMcode("DM-HBLX").getHtmlTag(officeWorkReport.reportType?default('1'))}
				</@common.select>
	        </td>
        </tr>
        <tr>
        	<th><span class="c-red">*</span> 时间：</th>
        	<td>
    		<@common.datepicker name="officeWorkReport.beginTime" id="beginTime" class="input-date"  notNull="true" msgName="开始日期" maxlength="30" readonly="${readonlyStyle?default('false')}" value="${(officeWorkReport.beginTime?string('yyyy-MM-dd'))?if_exists}" dateFmt='yyyy-MM-dd'/>
            <span>-</span>
            <@common.datepicker name="officeWorkReport.endTime" id="endTime" class="input-date"  notNull="true" msgName="结束日期" maxlength="30" readonly="${readonlyStyle?default('false')}" value="${(officeWorkReport.endTime?string('yyyy-MM-dd'))?if_exists}" dateFmt='yyyy-MM-dd'/>
        	</td>
        </tr>
        <tr>
        	<th><span class="c-red">*</span> 接收人：</th>
        	<td>
        	<div class="sendMsg-form">
				<@commonmacro.selectAddressBookLayer idObjectId="receiveUserId" nameObjectId="userNames" detailObjectId="detailNames" callback="resetUserIds">
					<input type="hidden" name="officeWorkReport.receiveUserId" id="receiveUserId" value="${officeWorkReport.receiveUserId!}">
					<textarea name="detailNames" id="detailNames" notNull="true" msgName="接收人"  class="text-area my-10 ${classStyle?default('')}" rows="4" cols="69" style="width:80%;padding:5px 1%;height:50px;" readonly="readonly">${officeWorkReport.userName?default("")}</textarea>
					<input type="hidden" name="officeWorkReport.userName" id="userName" value="${officeWorkReport.userName!}"> 
				</@commonmacro.selectAddressBookLayer>
        	</td>
        </tr>
        <tr>
        	<th><span class="c-red">*</span> 汇报内容：</th>
            <td>
            	<textarea id="content" name="officeWorkReport.content" type="text/plain" style="width:1024px;height:200px;" maxlength="500" msgName="汇报内容" notNull="true">${officeWorkReport.content!}</textarea>
	        </td>
        </tr>
        <tr>
        	<th>&nbsp;</th>
        	<td>
        		<#--<#if officeWorkReport.state?exists && officeWorkReport.state == '1'>-->
        		<#--	<a href="javascript:void(0);" id="btnPass" class="abtn-blue" onclick="saveOfficeWorkReport(1);">保存</a>-->
        		<#--<#else>-->
            		<a href="javascript:void(0);" id="btnPass" class="abtn-blue" onclick="saveOfficeWorkReport(1);">保存</a>
	            	<a href="javascript:void(0);" id="btnReject" class="abtn-blue" onclick="saveOfficeWorkReport(2);">提交</a>
            	<#--</#if>-->
            	<a href="javascript:void(0);" class="abtn-blue" onclick="goBack();">取消</a>
            </td>
        </tr>
    </table>
</form>
<script>
	function goBack(){
		myWorkReport();
	}
	
</script>
</@common.moduleDiv>