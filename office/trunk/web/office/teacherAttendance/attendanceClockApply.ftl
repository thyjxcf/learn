<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<script type="text/javascript" src="${request.contextPath}/static/jbmp/editor/js/flow.js"></script>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/boxy/boxy.css"/>
<script type="text/javascript" src="${request.contextPath}/static/boxy/jquery.boxy.js"></script>
<@htmlmacro.moduleDiv titleName="补卡申请">
<p class="tt"><a href="javascript:void(0);" class="close">关闭</a><span>补卡申请</span></p>
<div class="wrap pa-10" id="contentAuditDiv">
<form name="attendanceClockApplyEditForm" id="attendanceClockApplyEditForm" method="post">
	<input id="attenceDate" name="attenceDate" value="${attenceDate!}" type="hidden" />
	<input id="type" name="type" value="${type!}" type="hidden" />
	<input id="typeWeekTime" name="typeWeekTime" value="${typeWeekTime!}" type="hidden">
	<@htmlmacro.tableDetail divClass="table-form">
	    <tr>
	        <th style="width:30%"><span class="c-orange mr-5">*</span>补卡日期：</th>
	        <td colspan="3">
	        	${attenceDate!}&nbsp&nbsp${typeWeekTime!}
	        </td>
	    </tr>
	    <tr>
	    	<th  style="width:30%"><span class="c-orange mr-5">*</span>补卡原因：</th>
	        <td class="pt-10" style="width:80%">
	        	<textarea class="text-area my-5" id="reason" name="officeAttendanceColckApply.reason" maxlength="1000" notNull="true" msgName="补卡原因" style="width:90%;padding:5px 1%;height:50px;"></textarea>
	        </td>
		 </tr>
	    <tr>
	    	<td colspan="4" class="td-opt">
	    	    <a class="abtn-blue ml-5" href="javascript:void(0);" onclick="doSave();">提交</a>
			    <a href="javascript:void(0);" class="abtn-blue ml-5" onclick="closeDiv('#classLayer3')">取消</a>
	        </td>
	    </tr>
	  </@htmlmacro.tableDetail>
</form>
</div>
<script>
	var isSubmit =false;
	function doSave(){
		if(isSubmit){
			return;
		}
		if(!checkAllValidate("#attendanceClockApplyEditForm")){
			return;
		}
		var options = {
	       url:'${request.contextPath!}/office/teacherAttendance/teacherAttendance-submitAttendanceColck.action', 
	       success : showReply,
	       dataType : 'json',
	       clearForm : false,
	       resetForm : false,
	       type : 'post'
	    };
	    isSubmit = true;
	    $('#attendanceClockApplyEditForm').ajaxSubmit(options);
	}
	function showReply(data){
		if(!data.operateSuccess){
		   if(data.errorMessage!=null&&data.errorMessage!=""){
			   showMsgError(data.errorMessage);
			   isSubmit = false;
			   return;
		   }
		}else{
			showMsgSuccess(data.promptMessage,"",function(){
			  	doSearch();
			});
			return;
		}
	}
</script>
</@htmlmacro.moduleDiv >