<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.moduleDiv titleName="用车时间维护">
<script>
function doSave(){
	clearMessages();
	if(!isActionable("#btnSave")){
		return;
	}
	
	if(!checkAllValidate("#contentDiv")){
		return;
	}
	$("#btnSave").attr("class", "abtn-unable");
	var options = {
       url:'${request.contextPath}/office/carmanage/carmanage-carOverTimeSave.action', 
       success : showReply,
       dataType : 'json',
       clearForm : false,
       resetForm : false,
       type : 'post'
    };
	
    $('#mainform').ajaxSubmit(options);
}

function showReply(data){
	$("#btnSave").attr("class", "abtn-blue");
	if(!data.operateSuccess){
			   if(data.errorMessage!=null&&data.errorMessage!=""){
				   showMsgError(data.errorMessage);
				   return;
			   }else{
			   	   showMsgError(data.promptMessage);
				   return;
			   }
	}else{
		   		showMsgSuccess("提交成功！","",function(){
				   	var url="${request.contextPath}/office/carmanage/carmanage-carOverTimeList.action";
					load("#carOverTimeListDiv", url);
				});
				return;
	}
}
</script>
<p class="tt"><a href="javascript:void(0);" class="close">关闭</a><span>用车加班时间维护</span></p>
<div class="wrap pa-10" id="contentDiv">
<form id="mainform" action="" method="post">
    <table border="0" cellspacing="0" cellpadding="0" class="table-form">
    	<input type="hidden" id="id" name="officeCarApply.id" value="${officeCarApply.id?default('')}">  
	    <tr>
	      	<th>加班时间（天）：</th>
	    	<td>
	    		<input name="officeCarApply.overtimeNumber" id="overtimeNumber" value="<#if officeCarApply.overtimeNumber?exists>${officeCarApply.overtimeNumber?string("0.#")}</#if>" 
	           regex="/^(^[0-9]{0,3}$)|(^[0-9]{0,3}\.[0-9]{1}$)$/" regexMsg="最多3位整数1位小数" type="text" class="input-txt" style="width:150px;"  notNull="true" maxlength="30">
	    	</td>	
	    </tr>
    </table>
</form>    
</div>
<p class="dd">
    <a class="abtn-blue" href="javascript:void(0);" onclick="doSave();" id="btnSave">保存</a>
    <a class="abtn-blue reset ml-5" href="javascript:void(0);">取消</a>
</p>
<script>vselect();</script>
</@htmlmacro.moduleDiv>