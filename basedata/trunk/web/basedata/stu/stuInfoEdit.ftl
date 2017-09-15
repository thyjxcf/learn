<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.moduleDiv titleName="">
<script language="javascript">
function saveInfo(){
	if(!checkAllValidate("#studentSaveForm")){
		return;
	}
	
	var options = {
		url : "${request.contextPath}/basedata/stu/studentadmin-save.action",
	    dataType : 'json',
		success : showSuccessOnly,
		clearForm : false,
		resetForm : false,
		type : 'post',
		error:function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
	};
  	$("#studentSaveForm").ajaxSubmit(options);
}
function showSuccessOnly(data){
	if(data.operateSuccess){
		showMsgSuccess("保存成功","",doBackList);
	}else{
		showMsgError(data.errorMessage);
		isSubmitting=false;
		return;
	}
}
function doBackList(){
	var str = "?unitId=${unitId?default("")}&searchName=${searchName!}&pageIndex=${pageIndex!}";
    load("#container1","${request.contextPath}/basedata/stu/studentadmin-list.action"+str);
};

function checkUserName(){
	var name=document.getElementById('userName').value;
	if(trim(name)==''){
		showMsgError('请输入用户名');
		return ;
	}
	$.ajax({
		type: "POST",
		url: "${request.contextPath}/basedata/stu/studentadmin-serverUserName.action",
		data: $.param( {userName:name},true),
		dataType: "json",
		success: function(data){
			if(data.operateSuccess){
				addFieldSuccess(document.getElementById('userName'),'恭喜您,'+name+'用户名尚可使用');
			}else{
				addFieldError('userName',data.errorMessage);
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
	});
}

function doChangeGrade(){
	var gradeId = $("#gradeId").val();
	if(trim(gradeId) == ""){
		$("#classDiv").find(".ui-option").html("<div class='a-wrap'><a val=''><span>--请选择--</span></a></div>");
		vselect();
		return;
	}
	$.getJSON("${request.contextPath}/basedata/stu/studentadmin-getStuClassList.action", {"gradeId":gradeId }, function(data){
			var htmlStr= "<div class='a-wrap'><a val=''><span>--请选择--</span></a>";
	   		$.each(data, function(index, cls){
	       		 htmlStr += "<a val='"+cls.id+"'><span>"+cls.classname+"</span></a>";  
	    	});
	    	htmlStr+="</div>";
			$("#classDiv").find(".ui-option").html(htmlStr);
			vselect();
			return;
	}).error(function(XMLHttpRequest, textStatus, errorThrown){alert(errorThrown);});
}
</script>
<div id="unitContent">
<form name="studentSaveForm" id="studentSaveForm" method="POST" action="" >
<input type="hidden" name="unitId" value="${unitId?default('')}">
<input type="hidden" name="id" value="${studentDto.id?default('')}">
<p class="tt"><a href="javascript:void(0)" class="close">关闭</a><span>学生信息维护</span></p>
<div class="wrap pa-10">
<table border="0" cellspacing="0" cellpadding="0" class="table-list-edit table-form mt-5">
	<tr>
  	  <th width="30%"><span class="c-red">*</span>年级：</th>
  	  <td width="70%">
		<@htmlmacro.select style="width:150px;" valId="gradeId" valName="gradeId" txtId="gradeIdTxt" msgName="年级" myfunchange="doChangeGrade" notNull="true">
			<a val=""><span>--请选择--</span></a>
			<#if gradeList?exists&&(gradeList?size>0)>
			<#list gradeList as item>
				<a val="${item.id!}" <#if item.id?default('')==gradeId!>class="selected"</#if>><span>${item.gradename!}</span></a>
			</#list>
			</#if>
		</@htmlmacro.select>
  	  </td>	
  	</tr>
  	<tr>	  	  	  	  	
  	  <th width="30%"><span class="c-red">*</span>班级：</th>
	  <td width="70%">
		  <div class="ui-select-box fn-left" style="width:150px;" id="classDiv">
	        <input type="text" class="ui-select-txt" value="" notNull="true" msgName="班级"  readonly/>
	        <input name="classid" id="classid" type="hidden" value="" class="ui-select-value" />
	        <a class="ui-select-close"></a>
	        <div class="ui-option">
	    		<div class="a-wrap">
	        	<a val=""><span>--请选择--</span></a>
	        	<#if classList?exists&&(classList?size>0)>
				<#list classList as item>
	        		<a val="${item.id!}" <#if item.id?default('')==classid!>class="selected"</#if>><span>${item.classname!}</span></a>
	        	</#list>
				</#if>
	        	</div>
	        </div>
		  </div>
	  </td>
  	</tr>		  	  	  	  	
  	<tr>
  	  <th width="30%"><span class="c-red">*</span>姓名：</th>
  	  <td width="70%"><input name="stuname" id="stuname" type="input" style="width:140px;" class="input-txt" notNull="true" msgName="姓名" value="${studentDto.stuname?default('')}" maxlength="10"></td>
	</tr>
	<tr>
  	  <th width="30%"><span class="c-red">*</span>性别：</th>
  	  <td width="70%">
  	  <@htmlmacro.select valName="sex" valId="sex" notNull="true"  style="width:150px;">
			${appsetting.getMcode("DM-XB").getHtmlTag(studentDto.sex?default(0)?string!)}
	  </@htmlmacro.select>
	  </td>
	</tr>
  	<#if studentDto.id?exists>
	<tr>
	  <th width="30%"><span class="c-red">*</span>用户名：</th>
	  <td width="70%">
		  ${studentDto.userName!}
	  </td>
	</tr>
	<#else>
	<tr>
	  <th width="30%"><span class="c-red">*</span>用户名：</th>
	  <td width="70%">
		  <input name="userName" id="userName" type="text" style="width:140px;" class="input-txt" notNull="true" msgName="用户名" value="${studentDto.userName?default('')}">
		  <img src="${request.contextPath}/static/images/toolmenu_tips3.gif" border="0" style="cursor:pointer;" onclick="checkUserName();" alt="验证该用户名是否可用">
	  </td>
	</tr>
	<tr>
	  <th width="30%"><span class="c-red">*</span>密码：</th>
	  <td width="70%">
		  <input name="password" id="password" type="password" style="width:140px;" class="input-txt" notNull="true" msgName="密码" value="">
	  </td>
	</tr>
	</#if>
</table>
</div>
<p class="dd">
	<a href="javascript:saveInfo(); " class="abtn-blue submit">保存</a>
	<a href="javascript:void(0);" class="abtn-blue reset ml-5">取消</a>
</p>
</form>
</div>
</@htmlmacro.moduleDiv>