<#import "/common/commonmacro.ftl" as htmlcom>
<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="新增或修改教研组">
<script language="javascript">
	
	$(document).ready(function(){
		var sec = $('input[name="section"]:checked').val();
		if (sec == '0') {
			 $('#subject1').attr("style","display:block");
			 $('#subject2').attr("style","display:none");
			 $('#subject3').attr("style","display:none");
			 $('#subject4').attr("style","display:none");
		}
		if (sec == '1') {
			 $('#subject1').attr("style","display:none");
			 $('#subject2').attr("style","display:block");
			 $('#subject3').attr("style","display:none");
			 $('#subject4').attr("style","display:none");
		}
		if (sec == '2') {
			 $('#subject1').attr("style","display:none");
			 $('#subject2').attr("style","display:none");
			 $('#subject3').attr("style","display:block");
			 $('#subject4').attr("style","display:none");
		}
		if (sec == '3') {
			 $('#subject1').attr("style","display:none");
			 $('#subject2').attr("style","display:none");
			 $('#subject3').attr("style","display:none");
			 $('#subject4').attr("style","display:block");
		}
	}); 
	
	function changeSection(data) {
		if (data == '0') {
			 $('#subject1').attr("style","display:block");
			 $('#subject2').attr("style","display:none");
			 $('#subject3').attr("style","display:none");
			 $('#subject4').attr("style","display:none");
		}
		if (data == '1') {
			 $('#subject1').attr("style","display:none");
			 $('#subject2').attr("style","display:block");
			 $('#subject3').attr("style","display:none");
			 $('#subject4').attr("style","display:none");
		}
		if (data == '2') {
			 $('#subject1').attr("style","display:none");
			 $('#subject2').attr("style","display:none");
			 $('#subject3').attr("style","display:block");
			 $('#subject4').attr("style","display:none");
		}
		if (data == '3') {
			 $('#subject1').attr("style","display:none");
			 $('#subject2').attr("style","display:none");
			 $('#subject3').attr("style","display:none");
			 $('#subject4').attr("style","display:block");
		}
	}
	
	var isSubmitting = false;
	function save(){
		if(isSubmitting){
			return;
		}
		if(!checkAllValidate()){
			return;
		}
		
		isSubmitting = true;
		jQuery.ajax({
			url:"${request.contextPath}/basedata/researchgroup/researchGroupAdmin-Save.action",
			type:"POST",
			dataType:"JSON",
			data:jQuery("#editform").serialize(),
			async:false,
			success:function(data){
				if(data.operateSuccess){
   					showMsgSuccess(data.promptMessage,"",goBack());
   				}else{
   					showMsgError(data.promptMessage);
   				}
			isSubmitting = false;
			}
		});
	}
	
	
	function update(){
		if(isSubmitting){
			return;
		}
		if(!checkAllValidate()){
			return;
		}
		
		isSubmitting = true;
		jQuery.ajax({
			url:"${request.contextPath}/basedata/researchgroup/researchGroupAdmin-update.action",
			type:"POST",
			dataType:"JSON",
			data:jQuery("#editform").serialize(),
			async:false,
			success:function(data){
				if(data.operateSuccess){
   					showMsgSuccess(data.promptMessage,"",goBack());
   				}else{
   					showMsgError(data.promptMessage);
   				}
			isSubmitting = false;
			}
		});
	}
	
	function goBack(){
		load("#container","${request.contextPath}/basedata/researchgroup/researchGroupAdmin.action");
	}
	
	function show() {
		var ids = $("#subjectIds").val().split(',');
		if (ids !== null || ids !== undefined || ids !== '') {
			for (var i = 0; i < ids.length; i++) {
				$("#"+ ids[i]).attr("checked","checked");
				$("#"+ ids[i]).parent("span").attr("class","ui-checkbox mt-5 mr-10 mb-5 ui-checkbox-current");
			}
		}
		$("#subjectDiv").attr("style","display:block;");
	}
	
	function hid() {
		$("#subjectDiv").attr("style","display:none;");
	}
	
	function determine() {
		var ids = "";
		var names = "";
		$('input[name="subject"]:checked').each(function(){
			var idName = $(this).val().split(',');
			ids = ids + idName[0] + ",";
			names = names + idName[1] + ",";
		}); 
		ids = ids.substring(0,ids.length - 1);
		names = names.substring(0,names.length - 1);
		$("#subjectIds").val(ids);
		$("#subjectNames").val(names);
		$("#subjectDiv").attr("style","display:none;");
	}
</script>
<div id="subjectDiv" name="subjectDiv" class="" style="display:none;" >
<@common.moduleDiv titleName="选择学科">
	<p class="table-dt">选择学科</p>
	<@common.tableDetail  class="table-form">
        <tr>
	  		  <th width="25%">选择学段：</th>
	  		  <td width="75%" style="text-align:center;">
	  		  <div class="ui-radio-box">
				  <#list researchGroupDto.mcodeList as x>
				  	<span class="ui-radio <#if (researchGroupDto.section!) == (x.thisId!)>ui-radio-current</#if>" data-name="c"><input class="radio" name="section" type="radio" value="${x.thisId!}" <#if (researchGroupDto.section!) == (x.thisId!)>checked="checked"</#if> onClick="changeSection('${x.thisId!}');" />${x.content!}</span>
				  </#list>
			  </div>
	  		  </td>
	  	</tr> 
	  	<tr>
	  		  <th width="25%">选择学科：</th>
	  		  <td width="75%" style="text-align:center;">
				<div style="height:100px;width:100%;overflow:auto;;overflow-x:hidden;overflow-y:scroll;margin-top:15px;">
	  		  		<div id="subject1" style="display:none">
	  		  			<#list researchGroupDto.subjectsList as sub>
	  		  				<#if (sub.section!) == "0">
	  		  					<span class="ui-checkbox mt-5 mr-10 mb-5"><input id="${sub.id!}" name="subject" type="checkbox" class="chk" value="${sub.id!},${sub.subjectName!}">${sub.subjectName!}</span>
	  		  				</#if>
	  		  			</#list>
	  		  		</div>
	  		  		<div id="subject2" style="display:none">
	  		  			<#list researchGroupDto.subjectsList as sub>
	  		  				<#if (sub.section!) == "1">
	  		  					<span class="ui-checkbox mt-5 mr-10 mb-5"><input id="${sub.id!}" name="subject" type="checkbox" class="chk" value="${sub.id!},${sub.subjectName!}">${sub.subjectName!}</span>
	  		  				</#if>
	  		  			</#list>
	  		  		</div>
	  		  		<div id="subject3" style="display:none">
	  		  			<#list researchGroupDto.subjectsList as sub>
	  		  				<#if (sub.section!) == "2">
	  		  					<span class="ui-checkbox mt-5 mr-10 mb-5"><input id="${sub.id!}" name="subject" type="checkbox" class="chk" value="${sub.id!},${sub.subjectName!}">${sub.subjectName!}</span>
	  		  				</#if>
	  		  			</#list>
	  		  		</div>
	  		  		<div id="subject4" style="display:none">
	  		  			<#list researchGroupDto.subjectsList as sub>
	  		  				<#if (sub.section!) == "3">
	  		  					<span class="ui-checkbox mt-5 mr-10 mb-5"><input id="${sub.id!}" name="subject" type="checkbox" class="chk" value="${sub.id!},${sub.subjectName!}">${sub.subjectName!}</span>
	  		  				</#if>
	  		  			</#list>
	  		  		</div>
				</div>
	  		  </td>
	  	</tr>
	  	<tr>
	  		<td colspan=4 align="center">
            <a href="javascript:void(0);" onclick="determine();" class="abtn-blue">确定</a>
            <a href="javascript:void(0);" onclick="hid();" class="abtn-blue">取消</a>
	  		</td>
	  	</tr>  	
</@common.tableDetail>
</@common.moduleDiv>
</div>


<form action="" method="post" name="editform" id="editform">
<div class="table-content">
<p class="table-dt"><#if (researchGroupId!) == "">新增教研组信息<#else>修改教研组信息</#if></p>
<input type="hidden" id="researchGroupId" name="researchGroupId" value="${researchGroupId!}"/>
<input type="hidden" id="beforeName" name="researchGroupDto.beforeName" value="${researchGroupDto.beforeName!}"/>
<table width="100%" cellspacing="0" cellpadding="0" border="0" class="table-form">
	<tr>
		<th width="20%"><span class="c-orange">*</span>教研组名称：</th>
        <td width="30%">
			<input msgName="教研组名称" name="researchGroupDto.teachGroupName" id="teachGroupName" value="${researchGroupDto.teachGroupName!}" type="text" notNull="true" class="input-txt fn-left" style="width:150px;" maxlength="100" />
        </td>
        <th width="20%"><span class="c-orange">*</span>科目：</th>
        <td width="30%">
        	<input name="researchGroupDto.subjectIds" id="subjectIds" value="${researchGroupDto.subjectIds!}" type="hidden"/>
        	<input msgName="科目" readonly="readonly" name="researchGroupDto.subjectNames" id="subjectNames" value="${researchGroupDto.subjectNames!}" type="text" notNull="true" class="input-txt fn-left" style="width:150px;" onClick="show()"/>
        </td>
    </tr>
	<tr>
        <th><span class="c-orange">*</span>负责人：</th>
        <td>
        	<@htmlcom.selectMoreTeacher idObjectId="principalTeacherID" nameObjectId="principalTeacherName">
			<input id="principalTeacherID" name="researchGroupDto.principalTeacherID" type="hidden" value="${researchGroupDto.principalTeacherID!}"/>
			<input id="principalTeacherName" name="researchGroupDto.principalTeacherName" type="text" style="width:150px;" notNull="true" msgName="负责人" class="input-txt fn-left input-readonly" value="${researchGroupDto.principalTeacherName!}"/>
	        </@htmlcom.selectMoreTeacher>
	    </td>
        <th><span class="c-orange">*</span>成员：</th>
        <td>
        	<@htmlcom.selectMoreTeacher idObjectId="memberTeacherID" nameObjectId="memberTeacherName" >
			<input id="memberTeacherID" name="researchGroupDto.memberTeacherID" type="hidden" value="${researchGroupDto.memberTeacherID!}"/>
			<input id="memberTeacherName" name="researchGroupDto.memberTeacherName" type="text" style="width:150px;" notNull="true" msgName="成员" class="input-txt fn-left input-readonly" value="${researchGroupDto.memberTeacherName!}"/>
	        </@htmlcom.selectMoreTeacher>
        </td>
    </tr>
    <tr>
    	<td colspan="5" class="td-opt">
    		<#if (researchGroupId!) == "">
        	<a href="javascript:save();" class="abtn-blue-big">保存</a>
        	<#else>
        	<a href="javascript:update();" class="abtn-blue-big">保存</a>
        	</#if>
            <a href="javascript:goBack();" class="abtn-blue-big">返回</a>
        </td>
    </tr>
</table>
</div>  
</form>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>

