<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.moduleDiv titleName="">
<script language="javascript">
function saveInfo(){
	if(!checkAllValidate("#studentSaveForm")){
		return;
	}
	var acadyear = $("#acadyear").val().substring(0,4);
	var chaYear = ${currentSemYear!}-acadyear;
	var schoolinglen = $("#schoolinglen").val();
	if(chaYear>=schoolinglen){
		addFieldError("schoolinglen","学制至少为"+(chaYear+1)+"年，请修改");
		return;
	}
	var options = {
		url : "${request.contextPath}/basedata/sch/basicClassAdmin-saveGrade.action",
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
    load("#container","${request.contextPath}/basedata/sch/basicClassAdmin-GradeList.action?section=${section!}");
};

function doChangeOpenYear(){
	var acadyear = $("#acadyear").val();
	$.getJSON("${request.contextPath}/basedata/sch/basicClassAdmin-doChangeOpenYear.action", {"acadyear":acadyear,"section":${section?default("")} }, function(data){
			$("#gradeName").val(data.promptMessage);
			vselect();
			return;
	}).error(function(XMLHttpRequest, textStatus, errorThrown){alert(errorThrown);});
}
</script>
<div id="unitContent">
<form name="studentSaveForm" id="studentSaveForm" method="POST" action="" >
<p class="tt"><a href="javascript:void(0)" class="close">关闭</a><span>新增年级信息</span></p>
<div class="wrap pa-10">
<table border="0" cellspacing="0" cellpadding="0" class="table-list-edit table-form mt-5">
	<tr>
  	  <th width="30%">学段：</th>
  	  <td width="70%">
		<#list sectionList as sec>
			<#if section == sec[1]>${sec[2]}</#if>
		</#list>
  	  </td>	
  	</tr>
  	<tr>	  	  	  	  	  
  	  <th><span class="c-red">*</span>年级名称：</th>
	  <td>
		  <input name="grade.gradename" id="gradeName" maxlength="32" notNull="true" class="input-txt fn-left" style="width:150px;" value="${grade.gradename?default("")}">
	  </td>
  	</tr>		  	  	  	  	
  	<tr>	  	  	  	  	  
  	  <th>年级组长：</th>
	  <td>
	  	<input name="grade.section" type="hidden" value="${section?default("")}">
		<input name="grade.id" type="hidden" value="${grade.id?default("")}">
 		<input name="grade.schid" id="schId" type="hidden" value="${grade.schid?default("")}">
		<@commonmacro.selectOneTeacher idObjectId="teacherId" nameObjectId="lab">
     		<input id="teacherId" name="grade.teacherId" type="hidden" value="${grade.teacherId?default("")}"/>
     		<input id="lab" name="lab" type="text" style="width:150px;" class="input-txt fn-left input_readonly" value="${grade.teacherName?default("")}"/>
		</@commonmacro.selectOneTeacher>
	  </td>
  	</tr>
  	<tr>	  	  	  	  	  
  	  <th><span class="c-red">*</span>入学学年：</th>
	  <td>
	  	 <@htmlmacro.select style="width:161px;" notNull="true" txtId="acadyearId" valName="grade.acadyear" valId="acadyear" myfunchange="doChangeOpenYear();">
			<#list openYearList as sec>
			<a val="${sec!}"><span>${sec!}</span></a>
			</#list>
		</@htmlmacro.select>
	  </td>
  	</tr>
  	<tr>	  	  	  	  	  
  	  <th><span class="c-red">*</span>学制(年)：</th>
	  <td>
		  <input class="input-txt fn-left" style="width:150px;" name="grade.schoolinglen" id="schoolinglen" value="${schoolinglen?default('')}" notNull="true" datatype="integer" maxlength="1" minvalue="1" size="3">
	  </td>
  	</tr>
  	<tr>	  	  	  	  	  
  	  <th>上午课时：</th>
	  <td>
		  <input name="grade.amLessonCount" id="am" value="4" class="input-txt fn-left" style="width:150px;" datatype="integer" size="3" maxlength="1" minvalue="0" maxValue="5">
	  </td>
  	</tr>
  	<tr>	  	  	  	  	  
  	  <th>下午课时：</th>
	  <td>
	  	  <input name="grade.pmLessonCount" id="pm" value="4" class="input-txt fn-left" style="width:150px;" datatype="integer" size="3" maxlength="1" minvalue="0" maxValue="5">
	  </td>
  	</tr>
  	<tr>	  	  	  	  	  
  	  <th width="30%">晚上课时：</th>
	  <td width="70%">
          <input name="grade.nightLessonCount" id="ni" value="0" class="input-txt fn-left" style="width:150px;" datatype="integer" size="3" maxlength="1" minvalue="0" maxValue="5">
	  </td>
  	</tr>
</table>
</div>
<p class="dd">
	<a href="javascript:saveInfo(); " class="abtn-blue submit">保存</a>
	<a href="javascript:void(0);" class="abtn-blue reset ml-5">取消</a>
</p>
</form>
</div>
</@htmlmacro.moduleDiv>