<#import "/common/htmlcomponent.ftl" as common>
<#import "/common/commonmacro.ftl" as htmlcom>
<#assign SXRRT=stack.findValue("@net.zdsoft.eis.base.constant.BaseConstant@SYS_DEPLOY_SCHOOL_SXRRT")>
<@common.moduleDiv titleName="${webAppTitle}--<#if id?exists>修改班级<#else>新增班级</#if>">
<script language="javascript">
var isSubmitting = false;
function formSubmit(theform,operation){
	if(isSubmitting){
		return;
	}
	if(!checkAllValidate()){
		return;
	}
	//数据类型和格式判断
	//if(!checkPlusInt(theform.schoolinglen,"学制")) return;
	//if(!checkTermyear(theform.acadyear)) return;
	isSubmitting = true;
	jQuery.ajax({
		url:"${request.contextPath}/basedata/sch/basicClassAdmin-Save.action",
		type:"POST",
		dataType:"JSON",
		data:jQuery("#form1").serialize(),
		async:false,
		success:function(data){
			if(data.operateSuccess){
   				showMsgSuccess(data.promptMessage,"",cancel());
   			}else{
   				showMsgError(data.promptMessage);
   			}
		isSubmitting = false;
		}
	});
}

// 改变学段时 
function onChangeSection(){
	var frm = document.form1;
	var schid = $("#schid").val();
	var section = parseInt($("#section").val());
	var acadyear = $("#acadyear").val();
	//判断：若是幼儿园则不能选择
	//if(!checkSection()) return;
	jQuery.ajax({
		url:"${request.contextPath}/basedata/sch/basicClassAdmin-changeClass.action",
		type:"POST",
		dataType:"JSON",
		data:$.param({acadyearchange:acadyear,schidchange:schid,sectionchange:section},true),
		async:false,
		success:function(data){
			if(data != null){
				//入学学年
				var acadyearSelect = document.getElementById("acadyearId");
				acadyearSelect.innerHTML = '<select name="acadyear" id="acadyear" onChange="onChangeAcadyear();" style="width:150px;">' + data[0] + '</select>';
				//班级代码
				frm.classcode.value = data[1];
				//班级名称
				frm.classname.value = data[2];
				//班级名称前缀
				var preClassnameLabel = document.getElementById("preClassnameId");
				preClassnameLabel.innerHTML = data[3];
				//班级类型
				$("#classtypeOptionDivName .a-wrap").html(data[4]);
				//var classtypeSelect = document.getElementById("classtypeId");
				//classtypeSelect.innerHTML = '<select name="classtype" id="classtypeId" style="width:150px;">' + data[4] + '</select>';
				
				//学制
				frm.schoolinglen.value = data[5];
			}
		}
	});
}

//改变年级（即入学学年）时 
function onChangeAcadyear(){
	var frm = document.form1;
	var schid = frm.schid.value;
	var section = parseInt(frm.section.value);
	var acadyear = frm.acadyear.value;
	var gradeid = frm.gradeid.value;
	jQuery.ajax({
		url:"${request.contextPath}/basedata/sch/basicClassAdmin-changeAcadyear.action",
		type:"POST",
		dataType:"JSON",
		data:$.param({acadyearchange:acadyear,schidchange:schid,sectionchange:section,gradeidchange:gradeid},true),
		async:false,
		success:function(data){
			if(data != null){
				//班级代码
				frm.classcode.value = data[0];
				//班级名称
				frm.classname.value = data[1];
				//班级名称前缀
				var preClassnameLabel = document.getElementById("preClassnameId");
				preClassnameLabel.innerHTML = data[2];
				//学制
				frm.schoolinglen.value = data[4];
			}
		}
	});
}

//改变年制时（只是新增班时有效） 
function onChangeSchoolinglen(){
	var frm = document.form1;
	var id = frm.id.value;
	if(id!=null && id!="") return;
	
	var schid = frm.schid.value;
	var section = frm.section.value;
	var acadyear = frm.acadyear.value;
	var schoolinglen = frm.schoolinglen.value;
	
	if(schoolinglen == null || schoolinglen == "") return;
	
	//变化的各字段值
	buffalo.remoteActionCall("basicClassAdmin-remoteClass.action","doChangeSchoolinglen",[schid,section,acadyear,schoolinglen], function(reply) {
		drawMessages(reply);
		var retStr = reply.getResult();
		if(retStr != null){
			//入学学年
			var acadyearSelect = document.getElementById("acadyearId");
			acadyearSelect.innerHTML = '<select name="acadyear" id="acadyear" onChange="onChangeAcadyear();" style="width:160">' + retStr[0] + '</select>';
			
			//班级代码
			frm.classcode.value = retStr[1];
			
			//班级名称
			frm.classname.value = retStr[2];
			
			//班级名称前缀
			var preClassnameLabel = document.getElementById("preClassnameId");
			preClassnameLabel.innerHTML = retStr[3];
			
		}
	});	
}

//判断：若是幼儿园则不能继续
function checkSection(){
	var frm = document.form1;
	var section = frm.section.value;
	return true;
	//if(section == "0"){
	//	alert("提示：本系统暂不支持幼儿园学段，请更改学段！");
	//	frm.section.select();
	//	return false;
	//}else{
	//	return true;
	//}
}
//返回
function cancel(){
    var gradeid = $("#gradeid").val();
	load("#container","${request.contextPath}/basedata/sch/basicClassAdmin.action?gradeid="+gradeid);
}
</script>
<iframe width="174" height="172" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="${request.contextPath}/static/js/calendar/ipopeng.html?begin=2000-01-01" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0px;"></iframe>
<form action="" method="post" name="form1" id="form1">
<div class="table-content">
<p class="table-dt"><#if id?exists && id!="">编辑<#else>新增</#if>班级信息</p>
<table width="100%" cellspacing="0" cellpadding="0" border="0" class="table-form">
	  <tr>
	  <#if id?exists && id!=""><#-- 编辑-->
	  	<th width="10%"><span class="c-orange">*</span>所属学段：</th>
	  	<td width="40%">
	  	<input name="sectionName" type="text" class="input-txt fn-left input-readonly" readonly="readonly" style="width:150px;" value="${sectionMap[section?string?default('')]?default('')}"/>
	  	</td>
	  	<th width="10%"><span class="c-orange">*</span>入学学年：</th>
	  	<td width="40%">
	  	<input name="acadyearName" type="text" class="input-txt fn-left input-readonly" readonly="readonly" style="width:150px;" value="${acadyear?if_exists}"/>
	  	</td>
	  	<input name="section" id="section" type="hidden" value="${section?if_exists}"/>
		<input name="acadyear" id="acadyear" type="hidden" value="${acadyear?if_exists}"/>
	  <#else><#-- 新增-->
	  	<th width="10%"><span class="c-orange">*</span>所属学段：</th>
        <td width="40%">
        <select name="section" id="section" onChange="onChangeSection();" msgName="所属学段" notNull="true" class="input-txt fn-left" style="width:162px;">${sectionHtml?default("")}</select></td>
        <th width="10%"><span class="c-orange">*</span>年&nbsp;&nbsp;&nbsp;&nbsp;级：</th>
        <td  width="40%" id="acadyearId">
        <select name="acadyear" id="acadyear" onChange="onChangeAcadyear();" msgName="年级" notNull="true" class="input-txt fn-left" style="width:162px;">${acadyearHtml?default("")}</select></td>
	  </#if>
	  </tr>
	  <#if !systemDeploySchool?exists || systemDeploySchool != SXRRT>
      <tr>
        <th><span class="c-orange">*</span>班级代码：</th>
        <td>
        <input name="classcode" type="text" value="${classcode?if_exists}" msgName="班级代码" notNull="true" dataType="integer" maxlength="8" fieldtip="不能重复" style="width:150px;"
        class="input-txt fn-left"/>
        </td>
        <th><span class="c-orange">*</span>建班年月：</th>
        <td>
        <@common.datepicker msgName="建班年月" class="input-txt input-readonly" style="width:150px" notNull="true" name="datecreated" id="datecreated" value="${(datecreated?string('yyyy-MM-dd'))?if_exists}"/>
        <!--<input name="datecreated" type="text" value="<#if datecreated?exists>${datecreated?string("yyyy-MM-dd")?default('0000-00-00')}</#if>" class="input-txt fn-left" style="width:135"> 
		<a href="javascript:void(0)" onClick="gfPop.fPopCalendar(document.getElementById('datecreated'));return false;" hidefocus><img name="popcal" align="absmiddle" src="${request.contextPath}/static/js/calendar/calbtn.gif"  border="0" alt=""></a></td>
		-->
		</td>
		</#if>
      </tr>
	  <tr>
        <th>班级荣誉称号：</th>
        <td>
        <input name="honor" type="text" value="${honor?if_exists?trim}" class="input-txt fn-left" style="width:150px;" maxlength="40"/></td>
        <#if systemDeploySchool?exists && systemDeploySchool == SXRRT>
        	<input name="classcode" id="classcode" type="hidden" value="${classcode?if_exists}"/>
        	<input name="artsciencetype" id="artsciencetype" type="hidden" value="${artsciencetype?if_exists}"/>
	        <#if datecreated?exists><#-- 编辑-->
	        	<input name="datecreated" id="datecreated" type="hidden" value="${(datecreated?string('yyyy-MM-dd'))?if_exists}"/>
	        <#else>
	        	<input name="datecreated" id="datecreated" type="hidden" value="${(.now?string('yyyy-MM-dd'))?if_exists}"/>
	        </#if>
        </#if>
        <th><span class="c-orange">*</span>班级名称：</th>
        <td>
        <label id="preClassnameId"><#if preClassname?exists>${preClassname?trim}</#if></label>
        <input name="classname" type="text" value="${classname?if_exists}" msgName="班级名称" notNull="true" class="input-txt" style="width:120px;" maxlength="30" fieldtip="只填写几班即可，年级会自动生成"/>
        </td>
      </tr>
      <#if !systemDeploySchool?exists || systemDeploySchool != SXRRT>
	  <tr>
        <th>班级类型：</th>
        <td>
        <@common.select style="width:162px;" txtId="classtypeId" valName="classtype" valId="classtype" optionDivName="classtypeOptionDivName">
			${classtypeHtml?if_exists}
		</@common.select>
		</td>
        <th><span class="c-orange">*</span>文理类型：</th>
        <td>
        <@common.select style="width:162px;" msgName="文理类型" notNull="true" txtId="artsciencetypeId" valName="artsciencetype" valId="artsciencetype">
			${artsciencetypeHtml?if_exists}
		</@common.select>
		</td>
      </tr>
		</#if>
	  <tr>        
	  	<th>班 主 任：</th>
       	<td><#-- 直接放在td下，超链接的有效范围会较长，所以套一层table-->
	 		<@htmlcom.selectOneTeacher idObjectId="classTeacherID" nameObjectId="classTeacherName">
			<input id="classTeacherID" name="teacherid" type="hidden" value="${teacherid?default("")}"/>
			<input id="classTeacherName" name="classTeacherName" type="text" style="width:150px;" class="input-txt fn-left input-readonly" value="${teachername?default("")}"/>
	        </@htmlcom.selectOneTeacher>
	    </td>
        <th>副班主任：</th>
       	<td>
     		<@htmlcom.selectOneTeacher idObjectId="assistantTeacherID" nameObjectId="assistantTeacherName">
   			<input id="assistantTeacherID" name="viceTeacherId" type="hidden" value="${viceTeacherId?default("")}"/>
   			<input id="assistantTeacherName" name="assistantTeacherName" type="text" style="width:150px;" class="input-txt fn-left input-readonly" value="${viceTeacherName?default("")}"/>
     		</@htmlcom.selectOneTeacher>
	    </td>
      </tr>
      <tr>
      	<th><span class="c-orange">*</span>学&nbsp;&nbsp;&nbsp;&nbsp;制：</th>
        <td>
        <input name="schoolinglen" class="input-txt fn-left input-readonly" readonly="readonly" type="text" value="${schoolinglen?if_exists}" style="width:150px;" /></td>
        
        <th>班&nbsp;&nbsp;&nbsp;&nbsp;长：</th>
       	<td>
     		<@htmlcom.selectOneStudent idObjectId="classStudentID" nameObjectId="classStudentName" otherParam="groupId="+id?default("")>
     		<input id="classStudentID" name="stuid" type="hidden" value="${stuid?default("")}"/>
     		<input id="classStudentName" name="classStudentName" type="text" style="width:150px;" class="input-txt fn-left input-readonly" value="${monitor?default("")}"/>
     		</@htmlcom.selectOneStudent>
	    </td>
	    <#--
        <td width="180" height="20" align="left">
        <select name="stuid" style="width:160">${stuidHtml?if_exists}</select></td>-->
      </tr>
      <#if subschoolidHtml?exists && subschoolidHtml!="">
	  <tr>        
	      	<th><span class="c-orange">*</span>所属分校区：</th>
	        <td colspan="3">
	        <select name="subschoolid" class="input-txt fn-left" msgName="所属分校区" notNull="true" style="width:150px;">${subschoolidHtml?if_exists}</select></td>
      </tr>
      <#else>
      		<input name="subschoolid" type="hidden" class="input-txt fn-left" style="width:150px;" value="${subschoolid?if_exists}"/>
      </#if>
      <tr>
        <th>教室：</th>
        <td colspan="3">
    	 <@htmlcom.selectTree idObjectId="teachPlaceId" nameObjectId="teachPlaceName" treeUrl=request.contextPath+"/common/xtree/teachPlaceTree.action?allLinkOpen=false">
	  	   <input type="hidden" name="teachPlaceId" id="teachPlaceId" value="${teachPlaceId?default('')}"> 
	  	   <input type="text" name="teachPlaceName" class="input-txt fn-left" style="width:150px;" id="teachPlaceName" value="${teachPlaceName?default('')}" class="select_current02" style="width:140px;" readonly="readonly">
  	   </@htmlcom.selectTree>
        </td>
      </tr>
   	<tr>
    	<td colspan="4" class="td-opt">
        	<a href="javascript:formSubmit();" class="abtn-blue-big">保存</a>
            <a href="javascript:cancel();" class="abtn-blue-big">返回</a>
        </td>
    </tr>
</table>
</div> 
<input name="id" type="hidden" value="${id?if_exists}"/>
<input name="schid" id="schid" type="hidden" value="${schid?if_exists}"/>
<input name="preClassname" type="hidden" value="${preClassname?if_exists}"/>
<input name="classnamedynamic" type="hidden" value="${classnamedynamic?if_exists}"/>
<input name="gradeid" id="gradeid" type="hidden" value="${gradeid?if_exists}"/>
</form>
</@common.moduleDiv>

