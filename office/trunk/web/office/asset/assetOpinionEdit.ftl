<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.moduleDiv titleName="审核意见维护">
<script>
function doSave(){
	if(!isActionable("#btnSave")){
		return;
	}
	
	if(!checkAllValidate("#contentDiv")){
		return;
	}
	
	$("#btnSave").attr("class", "abtn-unable");
	
	var options = {
       url:'${request.contextPath}/office/asset/assetAdmin-saveOpinion.action', 
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
		   		showMsgSuccess("保存成功！","",function(){
				   load("#adminDiv", "${request.contextPath}/office/asset/assetAdmin-opinion.action");
				});
				return;
	}
}
</script>
<p class="tt"><a href="javascript:void(0);" class="close">关闭</a><span>审核意见维护</span></p>
<div class="wrap pa-10" id="contentDiv">
<form id="mainform" action="" method="post">
    <table border="0" cellspacing="0" cellpadding="0" class="table-form">
    	<input type="hidden" id="id" name="id" value="${opinion.id?default('')}">  
	    <input type="hidden" id="unitId" name="unitId" value="${opinion.unitId?default('')}">  
	    <tr>
	      <th width="30%"><span class="c-orange mr-10">*</span>意见类别：</th>
	      <td width="70%">
			<@htmlmacro.select style="width:150px;" valName="type" valId="type" myfunchange="" notNull="true" msgName="意见类别">
			<a val=""><span>---请选择---</span></a>
		  	<a val="1" title="通过" <#if opinion.type?exists && opinion.type=="1">class="selected"</#if>><span>通过</span></a>
		  	<a val="2" title="不通过" <#if opinion.type?exists && opinion.type=="2">class="selected"</#if>><span>不通过</span></a>
			</@htmlmacro.select>
	    </tr>
	    <tr>
	    	<th width="30%"><span class="c-orange mr-5">*</span>意见内容：</th>
            <td width="70%">
            	<textarea id="content" name="content" notNull="true" class="text-area my-10" msgName="意见内容"  maxlength="250" style="width:200px;">${opinion.content?default('')}</textarea>
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