<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="开通用户">

<div id="userContainer">	
   <form id="form1" action="" method="post">
		<p class="pub-operation">
	    	<a href="javascript:void(0);" onclick="openUser();" class="abtn-blue">开通账号</a>
	    	<a href="javascript:void(0);" onclick="listStudent();" class="abtn-blue">刷新</a>
	    	<a href="javascript:void(0);" onclick="exportStuAndFamInfo();" class="abtn-blue">导出账号</a>
	    </p>
	
  <@htmlmacro.tableList id="tablelist">
  	<input type="hidden" id="classId" name="classId" value="${classId?default('')}" />
	<input type="hidden" id="ticket" name="ticket" value="${ticket?default('')}" />
	<tr>
		<th class="t-center" width="7%">选择</th>
		<th width="10%">
		   <#if studentUserList?exists><span class="ui-checkbox" id="studentAllSpan" onclick="onSelectAllStudentClick();" style="width:80px;"><input type="checkbox" id="studentAllCheck" class="chk">学生姓名</span></#if>
		</th>
		<th width="10%">学生账号</th>
		<th width="10%">
		   <#if studentUserList?exists><span class="ui-checkbox" id="familyAllSpan" onclick="onSelectAllFamilyClick();" data-all="no"><input type="checkbox" id="familyAllCheck"  class="chk">关系</span></#if>
		</th>
		<th width="10%">家长姓名</th>
		<th width="10%">家长账号</th>
		<th width="10%">手机号码</th>
	</tr>
	<#if studentUserList?exists && (studentUserList?size>0)>
		<#list studentUserList as studentUser>
		<tr> 
			<td class="t-center">${studentUser_index+1}</td>
			<td>
			<#if !studentUser.isOpen()><span class="ui-checkbox ui-checkbox-not" onclick="selectStudent('${studentUser.id}')" id="studentspan_check_${studentUser.id}"><input type="checkbox" name="studentCheck" id="student_check_${studentUser.id}" value="${studentUser.id}" class="chk" >${studentUser.studentName?default('')}</span>
			<#else>
			   ${studentUser.studentName?default('')}
			</#if>
			</td>
			<td >
				<#if studentUser.isOpen()>
					${studentUser.studentLoginName?default('')}
				<#else>
					<input type="text" maxlength="60" class="input-txt" style="width:300px;" name="studentLoginName" id="login_name_${studentUser.id}" value="" maxlength="20" regex="^[A-Za-z0-9_]+$" regexMsg="账号必须是4-20位的英文(A-Z，a-z)或数字(0-9)及下划线"/>
					<input type="hidden" id="real_name_${studentUser.id}" value="${studentUser.studentName?default('')}" />
					<input type="hidden" id="temp_name_${studentUser.id}" value="${studentUser.tempLoginName?default('')}" />
				</#if>
			</td>
			<#--如果该学生存在家长信息-->
			<#if familyUserMap.containsKey(studentUser.id?default(''))>
			<#assign familyList=familyUserMap.get(studentUser.id?default('')) />
			<td>
				<#--家长关系-->
				<#list familyList as family><br>
				<#if family.isOpen()>
				<div style="margin-left:27px;">
					  <#if EisuSchool>
				        ${appsetting.getMcode("DM-CGX").get(family.relation?default(''))}
				    <#else>
				    	    ${appsetting.getMcode("DM-GX").get(family.relation?default(''))}
				    </#if>
				</div>
				<#else>
				<div>
				    <span class="ui-checkbox" onclick="selectFamily('${family.id}','${studentUser.id}')" id="familyspan_check_${family.id}"><input type="checkbox" name="familyCheck"  id="family_check_${family.id}" value="${family.id}" stuId="${studentUser.id}"  class="chk" >
				    <#if EisuSchool>
				        ${appsetting.getMcode("DM-CGX").get(family.relation?default(''))}
				    <#else>
				    	    ${appsetting.getMcode("DM-GX").get(family.relation?default(''))}
				    </#if>
				
				    </span>
				</#if>
				</div><br>
				</#list>
			</td>
			<td>
				<#--家长的姓名-->
				<#list familyList as family><br>
				<div>${family.familyName?default('')}</div><br>
				</#list>
			</td>
			<td>
				<#--家长的账号-->
				<#list familyList as family><br>
				<div>
				<#if family.isOpen()>
				${family.familyLoginName?default('')}
				<#else>
				<input type="text" maxlength="60" class="input-txt" style="width:300px;" name="familyLoginName" id="login_name_${family.id?default('')}" maxlength="20" regex="^[A-Za-z0-9_]+$" regexMsg="账号必须是4-20位的英文(A-Z，a-z)或数字(0-9)及下划线"/>
				<input type="hidden" id="real_name_${family.id?default('')}" value="${family.familyName?default('')}" />
				<input type="hidden" id="temp_name_${family.id!}" value="${family.tempLoginName?default('')}" />
				</#if>
				</div><br>
				</#list>
			</td>
			<td>
				<#--家长的手机号码-->
				<#list familyList as family><br>
				<div>
				${family.mobile?default('')}
				</div><br>
				</#list>
			</td>
			<#else>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			</#if>
			
		</tr>
		</#list>
	<#else>   
	   	<tr><td colspan="7"> <p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td></tr>
	</#if>
    </form>
	</@htmlmacro.tableList>
</div>

	
<script type="text/javascript">

function listStudent(){	
    var classId="${classId?default('')}";
    load("#deptAdminContainer","${request.contextPath}/basedata/user/userOpenAdmin-listStudent.action?classId="+classId);
	return;
}

//选择某一个学生
function selectStudent(studentId,obj){
    var spanObj = $("#"+"studentspan_check_"+studentId); //当前触发span
	spanObj.unbind('click');//去除绑定
    var checkboxObj = $("#"+"student_check_"+studentId); //当前触发checkbox
	var chkLen = $("input[name='studentCheck']").length; //获取所有的checkbox长度
	var studentAllSpanObj =$("#studentAllSpan"); //学生账号全选按钮
	var inputObj = $("#"+"login_name_"+studentId); //学生账号input
	var tempName = $("#"+"temp_name_"+studentId).val(); 
	if(!spanObj.hasClass('ui-checkbox-current')){
		spanObj.addClass('ui-checkbox-current').find('.chk').attr('checked','checked');
		var chkedLen = $("input[name='studentCheck'][checked]").length;//获取选中的checkbox长度
		if(chkLen == chkedLen){
			studentAllSpanObj.addClass('ui-checkbox-current').find('.chk').attr('checked','checked');
			studentAllSpanObj.attr('data-all','yes');
		}
		//获取账号
		inputObj.val(tempName);
	}else{
		spanObj.removeClass('ui-checkbox-current').find('.chk').removeAttr('checked');
		studentAllSpanObj.removeClass('ui-checkbox-current').find('.chk').removeAttr('checked');
		studentAllSpanObj.attr('data-all','no');
		inputObj.val("");
	}
}


//选择某一个家长
function selectFamily(familyId,studentId){
    var spanObj = $("#"+"familyspan_check_"+familyId); //当前触发span
	spanObj.unbind('click');//去除绑定
    var checkboxObj = $("#"+"family_check_"+familyId); //当前触发checkbox
	var chkLen = $("input[name='familyCheck']").length; //获取所有的checkbox长度
	var familyAllSpanObj =$("#familyAllSpan"); //学生账号全选按钮
	var inputObj = $("#"+"login_name_"+familyId); //学生账号input
	var tempName = $("#"+"temp_name_"+familyId).val(); 
	if(!spanObj.hasClass('ui-checkbox-current')){
		spanObj.addClass('ui-checkbox-current').find('.chk').attr('checked','checked');
		var chkedLen = $("input[name='familyCheck'][checked]").length;//获取选中的checkbox长度
		if(chkLen == chkedLen){
			familyAllSpanObj.addClass('ui-checkbox-current').find('.chk').attr('checked','checked');
			familyAllSpanObj.attr('data-all','yes');
		}
		inputObj.val(tempName);
	}else{
		spanObj.removeClass('ui-checkbox-current').find('.chk').removeAttr('checked');
		familyAllSpanObj.removeClass('ui-checkbox-current').find('.chk').removeAttr('checked');
		familyAllSpanObj.attr('data-all','no');
		inputObj.val("");
	}
}


//选择所有学生的点击触发事件

function onSelectAllStudentClick(){
    var studentAllSpanObj =$("#studentAllSpan"); //学生账号全选按钮
    studentAllSpanObj.unbind('click');//去除绑定
    var flag=true;
    if(!studentAllSpanObj.hasClass('ui-checkbox-current')){
       flag=false;
       studentAllSpanObj.addClass('ui-checkbox-current').find('.chk').attr('checked','checked');
    }else{
       studentAllSpanObj.removeClass('ui-checkbox-current').find('.chk').removeAttr('checked');
    }
    $("input[name='studentCheck']").each(function(){
	       var studentId=$(this).val();
	       var spanObj=$(this).parent();
	       if(!flag){
	           spanObj.addClass('ui-checkbox-current').find('.chk').attr('checked','checked');
			   //获取账户
			   var tempName = $("#"+"temp_name_"+studentId).val();
			   $("#"+"login_name_"+studentId).val(tempName);
	       }else{
		       spanObj.removeClass('ui-checkbox-current').find('.chk').removeAttr('checked');
		       $("#"+"login_name_"+studentId).val("");
	       }
    });
	
}



//选择所有家长的点击触发事件
function onSelectAllFamilyClick(obj){

    var familyAllSpanObj =$("#familyAllSpan"); //学生账号全选按钮
    familyAllSpanObj.unbind('click');//去除绑定
    var flag=true;
    if(!familyAllSpanObj.hasClass('ui-checkbox-current')){
        flag=false;
        familyAllSpanObj.addClass('ui-checkbox-current').find('.chk').attr('checked','checked');
    }else{
        familyAllSpanObj.removeClass('ui-checkbox-current').find('.chk').removeAttr('checked');
    }
    $("input[name='familyCheck']").each(function(){
	       var familyId=$(this).val();
	       var spanObj=$(this).parent();
	       if(!flag){
	       		var tempName = $("#"+"temp_name_"+familyId).val(); 
	       		$("#"+"login_name_"+familyId).val(tempName);
				spanObj.addClass('ui-checkbox-current').find('.chk').attr('checked','checked');
	       }else{
	            $("#"+"login_name_"+familyId).val("");
	            spanObj.removeClass('ui-checkbox-current').find('.chk').removeAttr('checked');
	       }
    });
    
}

//远程开通用户
function openUser(){
	if($("input[name='studentCheck']:checked").size()==0&&$("input[name='familyCheck']:checked").size()==0){
		showMsgWarn("请选择要开通的用户!");
		return;
	}
	var studentIds="";
	var studentLogin="";
	var studentRealName="";
	var familyIds="";
	var familyLogin="";
	var familyRealName="";
	var flag=false;
	$("input[name='studentCheck']:checked").each(function(){
	    var studentId=$(this).val();
	    var myStuLogin=$("#"+"login_name_"+studentId).val();
	    var myStuRealName=$("#"+"real_name_"+studentId).val();
	    if(myStuLogin==""){
	      addFieldError("login_name_"+studentId,"学生账号不能为空!");
	      flag=true;
	      return false;
	    }else if(myStuLogin.length<4||myStuLogin.length>20){
	      addFieldError("login_name_"+studentId,"账号必须是4-20位的英文(A-Z，a-z)或数字(0-9)及下划线");
	      flag=true;
	      return false;
	    }
	    studentIds+=studentId+",";
	    studentLogin+=myStuLogin+",";
	    studentRealName+=myStuRealName+",";
	});
	if(flag){
	   return;
	}
	$("input[name='familyCheck']:checked").each(function(){
	   var familyId=$(this).val();
	   var myFamLogin=$("#"+"login_name_"+familyId).val();
	   var myFamRealName=$("#"+"real_name_"+familyId).val();
	   if(myFamLogin==""){
	      addFieldError("login_name_"+familyId,"家长账号不能为空!");
	      flag=true;
	      return false;
	   }else if(myFamLogin.length<4||myFamLogin.length>20){
	      addFieldError("login_name_"+familyId,"账号必须是4-20位的英文(A-Z，a-z)或数字(0-9)及下划线");
	      flag=true;
	      return false;
	   }
	   familyLogin+=myFamLogin+",";
	   familyIds+=familyId+",";
	   familyRealName+=myFamRealName+",";
	});
	if(flag){
	  return;
	}
	if(studentIds.length>0){
	  studentIds=studentIds.substring(0,studentIds.length-1);   
	}
	if(studentLogin.length>0){
	  studentLogin=studentLogin.substring(0,studentLogin.length-1);   
	}
	if(studentRealName.length>0){
	  studentRealName=studentRealName.substring(0,studentRealName.length-1);   
	}	
	if(familyIds.length>0){
	  familyIds=familyIds.substring(0,familyIds.length-1);   
	}	
	if(familyLogin.length>0){
	  familyLogin=familyLogin.substring(0,familyLogin.length-1);   
	}
	if(familyRealName.length>0){
	  familyRealName=familyRealName.substring(0,familyRealName.length-1);
	}
	if(!isActionable("#btnSaveAll")){
		return false;
	}
	if(!checkAllValidate("#userContainer")){
		return false;
	}
	$("#btnSaveAll").attr("class", "abtn-unable-big");	
	$.ajax({
		url:"${request.contextPath}/basedata/user/userOpenAdmin-remoteUserOpen.action",
		type:"POST",
		data:{"studentIds":studentIds,"studentLogin":studentLogin,"studentRealName":studentRealName,"familyIds":familyIds,"familyLogin":familyLogin,"familyRealName":familyRealName,"classId":"${classId!}"},
		dataType:"json",
		async:false,
		success:function(data){
			if(!data.operateSuccess){
			   if(data.errorMessage!=null&&data.errorMessage!=""){
				   showMsgError(data.errorMessage);
				   return;
			   }else if(data.fieldErrorMap!=null){
				  $.each(data.fieldErrorMap,function(key,value){
					   addFieldError(key,value+"");
				  });
			   }
			}else{
		   		showMsgSuccess("账号开通成功！","",function(){
				   load("#userContainer","${request.contextPath}/basedata/user/userOpenAdmin-listStudent.action?classId="+"${classId!}");
				});
				return;
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
	   			alert(XMLHttpRequest.status);
	    }
	});

}


//导出学生和家长的登录信息
function exportStuAndFamInfo(){
	if("${classId?default("")}" == ""){
		showMsgWarn("请选择要导出的班级!");
		return;
	}
	location.href="${request.contextPath}/basedata/user/userOpenAdmin-stuAndFamInfoExport.action?classId=${classId!}";
}
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>