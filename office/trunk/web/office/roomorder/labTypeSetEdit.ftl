<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="">
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
       url:'${request.contextPath}/office/roomorder/roomorder-labTypeSet-save.action', 
       success : showReply,
       dataType : 'json',
       clearForm : false,
       resetForm : false,
       type : 'post'
    };
	
    $('#addform').ajaxSubmit(options);
}

function showReply(data){
	$("#btnSave").attr("class", "abtn-blue");
	if(data!=null && data != ''){
		showMsgError(data);
	}else{
   		showMsgSuccess("保存成功！","",function(){
   			closeDiv("#labTypeSetLayer");
   			searchLabTypeSet();
		});
	}
}
</script>
<p class="tt"><a href="javascript:void(0);" class="close">关闭</a><span>实验种类设置</span></p>
<div class="wrap pa-10" id="contentDiv">
<form id="addform" action="" method="post">
    <table border="0" cellspacing="0" cellpadding="0" class="table-form">
    	<input type="hidden" id="id" name="officeLabSet.id" value="${officeLabSet.id?default('')}">
	    <tr>
	      	<th style="width:15%">实验名称：</th>
	    	<td style="width:35%">
    			<input type="text" msgName="实验名称" class="input-txt fn-left" id="name" name="officeLabSet.name" maxlength="100" notNull="true" value="${officeLabSet.name!}" style="width:180px;">
	    		<span class="c-orange mt-5 ml-10">*</span>
	    	</td>
	    	<th style="width:15%">教材页面：</th>
	    	<td style="width:35%">
    			<input type="text" msgName="教材页面" class="input-txt fn-left" id="courseBook" name="officeLabSet.courseBook" maxlength="100" notNull="true" value="${officeLabSet.courseBook!}" style="width:180px;">
	    		<span class="c-orange mt-5 ml-10">*</span>
	    	</td>
	    </tr>
	    <tr>
	    	<th>学科：</th>
	    	<td <#if !hasGrade>colspan="3"</#if>>
	    		<@htmlmacro.select style="width:190px;" valId="subject" valName="officeLabSet.subject" notNull="true" msgName="学科">
					<a val=""><span>----请选择----</span></a>
		        	<#list appsetting.getMcode('DM-SYSLX').getMcodeDetailList() as item>
	            		<a val="${item.thisId}" <#if officeLabSet.subject?default('') == item.thisId>class="selected"</#if>><span>${item.content}</span></a>
	            	</#list>
				</@htmlmacro.select>
	    		<span class="c-orange mt-5 ml-10">*</span>
	    	</td>
	    	<#if hasGrade>
	    	<th>年级：</th>
	    	<td>
	    		<@htmlmacro.select style="width:190px;" valId="grade" valName="officeLabSet.grade" notNull="true" msgName="年级">
					<a val=""><span>----请选择----</span></a>
					<#if gradeList?exists && gradeList?size gt 0>
		        	<#list gradeList as item>
	            		<a val="${item}" <#if officeLabSet.grade?default('') == item>class="selected"</#if>><span>${item}</span></a>
	            	</#list>
	            	</#if>
				</@htmlmacro.select>
	    		<span class="c-orange mt-5 ml-10">*</span>
	    	</td>
	    	<#else>
	    		<input type="hidden" id="grade" name="officeLabSet.grade" value="" />
	    	</#if>
	    </tr>
	    <tr>
	      	<@htmlmacro.tdt msgName="所需仪器" id="apparatus" name="officeLabSet.apparatus" maxLength="1000" colspan="3" style="width:470px;" value="${officeLabSet.apparatus!}" />
	    </tr>
	    <tr>
	      	<@htmlmacro.tdt msgName="所需药品" id="reagent" name="officeLabSet.reagent" maxLength="1000" colspan="3" style="width:470px;" value="${officeLabSet.reagent!}" />
	    </tr>
    </table>
</form>    
</div>
<p class="dd">
    <a class="abtn-blue" href="javascript:void(0);" onclick="doSave();" id="btnSave">保存</a>
    <a class="abtn-blue reset ml-5" href="javascript:void(0);">取消</a>
</p>
<script>
vselect();
</script>
</@htmlmacro.moduleDiv>