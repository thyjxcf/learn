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
	if(document.getElementById("signaturePic").value.length>0){
		if(!checkFileType(document.getElementById("signaturePic")))
			return false;
	}
</@teacherCommonVialidate>
function isBlank(s) {
    var re = /^\s*$/g;
    return re.test(s);
}
var qNameOrId ="&queryTchName=${queryTchName?default('')}&queryTchId=${queryTchId?default('')}";
function formsubmit(){
	if(!checkAllValidate("#teacherEditDiv")){
		return false;
	}	
	if(formvalidate()){
      var options = {
          target : '#updateForm',
          url : "${request.contextPath}/basedata/teacher/teacherAdmin-update.action?modID=${modID?default('')}",
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
		load("#teacherList","${request.contextPath}/basedata/teacher/teacherAdmin-list.action?modID=${modID?default('')}&deptidnow=${deptidnow?default('')}"+qNameOrId);
		});
	}else{
		showMsgError(data.errorMessage);
	}
}

function cancelBack(){
	load("#teacherList","${request.contextPath}/basedata/teacher/teacherAdmin-list.action?modID=${modID?default('')}&&deptidnow=${deptidnow?default('')}"+qNameOrId);
}
</script>
<div id="teacherEditDiv">
<form name="updateForm" id="updateForm" method="POST" action=""  enctype="multipart/form-data">
<input name="deptidnow" type="hidden" value="${deptidnow?default('')}">
<input name="id" type="hidden" value="${teacher.getId()?default('')}">
<input name="regionCode" type="hidden" value="${teacher.regionCode?default('')}">
<p class="table-dt">职工编辑</p>
<@common.tableDetail >
	<#include "/basedata/teacher/teacherDetailCommon.ftl">	
      <tr>
      	<th>关联用户：</th>
      	<td colspan="2">
		  <#list userList?if_exists as u>
  	  	  	[<#-- connectPassport>账号：<strong>${u.sequence!}</strong>--> 账号：<strong>${u.name?default('')}</strong>]
  	  	  	<#if u_has_next>、</#if>
  	  	  </#list>
      	</td>
      	<td>
      		<input type="checkbox" name="teacher.hidePhone" <#if teacher.hidePhone == 1>checked="checked"</#if> value="1" />&nbsp隐藏手机号
      	</td>
      </tr>
      <tr>
      	<th>手机短号：</th>
      	<td colspan="3">
      		<input type="text" class="input-txt fn-left" style="width:140px;" name="teacher.mobileCornet" id="teacher.mobileCornet"  msgName="手机短号" maxLength="10" value="${teacher.mobileCornet?default('')}">
      	</td>
      </tr>
      <tr>
		<th>手写签名图片：</th>
		<td colspan="3">
			<input name="signature" id="signaturePic" type="file" class="input-txt" style="width:340px;">
		</td>						
	 </tr>
	 <#if signaturePath?default('')!=''>
     <tr>
		<th>原签名图片：</th>
		<td colspan="3">
			<img src="${signaturePath}"/>
		</td>						
	 </tr>
	 </#if>
	 <tr>
		<td colspan="4" class="td-opt" >
	    	<a href="javascript:void(0);" class="abtn-blue" onclick="formsubmit();">保存</a>
	        <a href="javascript:void(0);" class="abtn-blue ml-10" onclick="cancelBack();">取消</a>
	    </td>
  </tr>
<#include "/basedata/teacher/teacherColumnCommon.ftl">
</@common.tableDetail>
</form>
</div>
<script>
vselect();
</script>
</@common.moduleDiv>
