<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="${webAppTitle}--编辑用户">
<#import "/common/commonmacro.ftl" as commonmacro>
<#include "/basedata/teacher/teacherDetailValidate.ftl">
<script type="text/javascript" language="javascript" src="${request.contextPath}/static/js/strspell.js"></script>
<script type="text/javascript" language="JavaScript" src="${request.contextPath}/static/js/pwdintensity.js"></script>
<script>
<#--检查图片格式-->
<@checkFileType/>
<#--鼠标控制事件-->
<@mouseEvent/>
<#--教职工信息验证JS-->
<@teacherCommonVialidate>
</@teacherCommonVialidate>
function isBlank(s) {
    var re = /^\s*$/g;
    return re.test(s);
}
var qNameOrId ="&queryTchName=${queryTchName?default('')}&queryTchId=${queryTchId?default('')}";
function formsubmit(){
	if(formvalidate()){
      var options = {
          target : '#updateForm',
          url : "${request.contextPath}/basedata/teacher/common/teacherAdmin-update.action?modID=${modID?default('')}",
          success : showSuccess,
          dataType : 'json',
          clearForm : false,
          resetForm : false,
          type : 'post',
          timeout : 3000
        };
      $("#updateForm").ajaxSubmit(options);
	}
}

function showSuccess(data) {
   if(data.operateSuccess){
		showMsgSuccess(data.promptMessage,"",function(){
			closeDiv('#setLayer');return false;
		});
	}else{
		showMsgError(data.errorMessage);
	}
}

function cancelBack(){
	closeDiv('#setLayer');return false;
}
</script>
<div style="height:480px;">
<div style="overflow-y:auto;height:406px;">
<div id="teacherEditDiv" class="mt-10 mr-5 ml-5 mb-10">
<form name="updateForm" id="updateForm" method="POST" action=""  enctype="multipart/form-data">
<input name="deptidnow" type="hidden" value="${deptidnow?default('')}">
<input name="id" type="hidden" value="${teacher.getId()?default('')}">
<input name="regionCode" type="hidden" value="${teacher.regionCode?default('')}">
<input name="teacher.oldMobile" type="hidden" value="${teacher.oldMobile?default('')}">
<input name="isDesktop" type="hidden" value="${isDesktop?string('true','false')}">
<@common.tableDetail >
<#if eisuSchool>
	<#include "/basedata/teacher/teacherDetailCommon.ftl">	
<#else>
	<#include "/basedata/teacher/teacherDetailCommonEis.ftl">	
</#if>	
      <tr>
      	<th>关联用户：</th>
      	<td colspan="3">
		  <#list userList?if_exists as u>
  	  	  	[<#-- connectPassport>账号：<strong>${u.sequence!}</strong>--> 账号：<strong>${u.name?default('')}</strong>]
  	  	  	<#if u_has_next>、</#if>
  	  	  </#list>
      	</td>
      </tr>
<#include "/basedata/teacher/teacherColumnCommon.ftl">
</@common.tableDetail>
</form>
</div>
</div>
<p class="t-center pt-15">
	<a href="javascript:void(0);" class="abtn-blue" onclick="formsubmit();">保存</a>
    <a href="javascript:void(0);" class="abtn-blue reset ml-10" href="javascript:void(0);" onclick="cancelBack();">取消</a>
</p>
</div>
<script>
vselect();
</script>
</@common.moduleDiv>
