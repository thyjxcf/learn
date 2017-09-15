<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.moduleDiv titleName="反馈信息维护">
<script>
var isSubmit = false;
function doSave(){
	if(isSubmit){
		return;
	}
	
	if(!checkAllValidate("#contentDiv")){
		return;
	}
	isSubmit = true;	
	var options = {
       url:'${request.contextPath}/office/roomorder/roomorder-saveFeedback.action', 
       success : showReply,
       dataType : 'json',
       clearForm : false,
       resetForm : false,
       type : 'post'
    };
	
    $('#mainform').ajaxSubmit(options);
}

function showReply(data){
	if(data!=null&&data!=""){
	   isSubmit = false;
	   showMsgError(data);
	   return;
	}else{
		isSubmit = false;
   		showMsgSuccess("操作成功！","",function(){
		   	searchOrder();
		});
		return;
	}
}
</script>
<p class="tt"><a href="javascript:void(0);" class="close">关闭</a><span>反馈信息维护</span></p>
<div class="wrap pa-10" id="contentDiv">
<form id="mainform" action="" method="post">
    <table border="0" cellspacing="0" cellpadding="0" class="table-form">
    	<input type="hidden" id="id" name="officeApplyNumber.id" value="${officeApplyNumber.id?default('')}">  
	    <tr>
	      	<th style="width:20%;">反馈：</th>
	    	<td style="width:80%;">
	    		<textarea name="officeApplyNumber.feedback" id="feedback" cols="70" rows="5" class="text-area my-5" notNull="true" msgName="反馈" maxLength="500">${officeApplyNumber.feedback!}</textarea>
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