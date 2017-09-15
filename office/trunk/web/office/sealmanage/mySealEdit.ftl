<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<#import "../sealmanage/archiveWebuploader.ftl" as archiveWebuploader>
<@htmlmacro.moduleDiv titleName="">
<script>
var isSubmit =false;
function doSave(obj){
	if(isSubmit){
			return;
		}
	if(!checkAllValidate("#addSealform")){
		return;
	}
	$("#state").val(obj);
	isSubmit = true;
	var options = {
       url:'${request.contextPath}/office/sealmanage/sealmanage-saveSeal.action', 
       success : showReply,
       dataType : 'json',
       clearForm : false,
       resetForm : false,
       type : 'post'
    };
	
    $('#addSealform').ajaxSubmit(options);
}

function doSave2(obj){
	if(isSubmit){
			return;
		}
	if(!checkAllValidate("#addSealform")){
		return;
	}
	$("#state").val(obj);
	isSubmit = true;
	var options = {
       url:'${request.contextPath}/office/sealmanage/sealmanage-saveSeal.action', 
       success : showReply,
       dataType : 'json',
       clearForm : false,
       resetForm : false,
       type : 'post'
    };
	
    $('#addSealform').ajaxSubmit(options);
}

function showReply(data){
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
	   	//closeDiv("#sealAddLayer");
	   	back();
		});
	}
}

function uploadContent(target){
	$("#uploadContentFileInput").val($(target).val());
	$('#cleanFile').attr("style","display:display");
	$('#cleanFile').attr("style","height:0px");
}
		
function deleteFile(){
	$('#uploadContentFileInput').val('');
	var file = $("#uploadContentFile")
	file.after(file.clone().val(""));
	file.remove();
	$('#cleanFile').attr("style","display:none");
	}
	
function back(){
	myApply();
}
</script>
<!--<p class="tt"><a href="javascript:void(0);" class="close">关闭</a><#if officeSeal?exists&&officeSeal.id?exists><span>用印编辑</span><#else><span>用印申请</span></#if></p>-->
<div class="wrap pa-10" id="contentDiv">
<form id="addSealform" action="" method="post">
    	<input type="hidden" id="id" name="officeSeal.id" value="${officeSeal.id?default('')}">  
    	<input type="hidden" id="state" name="officeSeal.state" value="">
    	<@htmlmacro.tableDetail divClass="table-form">
    	<tr>
		        <th colspan="4" style="text-align:center;"><#if officeSeal?exists&&officeSeal.id?exists>用印编辑<#else>用印申请</#if></th>
		</tr> 
	    <tr>
	    	<th style="width:15%"><span class="c-orange mt-5 ml-10">*</span>印章类型：</th>
	    	<td style="width:35%">
	    		<@htmlmacro.select style="width:185px;" valId="sealType" valName="officeSeal.sealType" notNull="true" msgName="印章类型">
					<a val=""><span>--请选择--</span></a>
		        	<#if officeSealTypeList?exists && (officeSealTypeList?size>0)>
		            	<#list officeSealTypeList as item>
		            		<a val="${item.typeId}" <#if officeSeal.sealType?default('') == item.typeId>class="selected"</#if>><span>${item.typeName}</span></a>
		            	</#list>
		        	</#if>
				</@htmlmacro.select>
	    	</td>	
	    </tr>
	    <tr>
	      	<th><span class="c-red">*</span>用印事由：</th>
            	<td colspan="3" style="word-break:break-all; word-wrap:break-word;">
            		<textarea id="applyOpinion" name="officeSeal.applyOpinion"  msgName="用印事由" notNull="true" class="text-area  mt-5 mb-5" maxLength="200" rows="4" cols="69" style="width:470px;">${officeSeal.applyOpinion?default('')}</textarea>
            	</td>
	    </tr>
	    <@archiveWebuploader.archiveWebuploaderEditViewer canEdit=true showAttachmentDivId='showAttDiv' editContentDivId='editContentDiv' isSend=true loadDiv=false />
	    </@htmlmacro.tableDetail>
		<@htmlmacro.tableDetail>
			<tr>
		    	<td colspan="4" class="td-opt">
		    		<a class="abtn-blue-big" href="javascript:void(0);" onclick="doSave(1);" id="btnSave">保存</a>
    				<a class="abtn-blue-big" href="javascript:void(0);" onclick="doSave2(2);" id="btnSave">提交</a>
    				<a class="abtn-blue-big" href="javascript:void(0);" onclick="back();">返回</a>
		        </td>
		    </tr>
    </@htmlmacro.tableDetail>
</form>    
</div>
</@htmlmacro.moduleDiv>