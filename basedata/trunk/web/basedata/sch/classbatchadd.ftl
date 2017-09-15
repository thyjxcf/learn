<#import "/common/htmlcomponent.ftl" as common>
<#import "/common/commonmacro.ftl" as htmlcom>
<#assign SXRRT=stack.findValue("@net.zdsoft.eis.base.constant.BaseConstant@SYS_DEPLOY_SCHOOL_SXRRT")>
<@common.moduleDiv titleName="${webAppTitle}----批量新增班级">
<script language="javascript">
var isSubmitting = false;
function formSubmit(){
	if(isSubmitting){
    	return;
	}
	if(!checkAllValidate()){
		return;
	}
	isSubmitting = true;
	jQuery.ajax({
		url:"${request.contextPath}/basedata/sch/basicClassAdmin-BatchAddConfirm.action",
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

///改变学段时 
function onChangeSection(){
	var frm = document.form1;
	var schid = frm.schid.value;
	var section = parseInt(frm.section.value);
	var acadyear = frm.acadyear.value;
	
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
			    var retStr = data;
				//入学学年
				var acadyearSelect = document.getElementById("acadyearId");
				acadyearSelect.innerHTML = '<select name="acadyear" onChange="onChangeAcadyear();" style="width:150px;">' + retStr[0] + '</select>';
				//班级代码
				frm.classcode.value = retStr[1];
				//班级类型
				$("#classtypeOptionDivName .a-wrap").html(data[4]);
				//var classtypeSelect = document.getElementById("classtypeId");
				//classtypeSelect.innerHTML = '<select name="classtype" id="classtypeId" style="width:150px;">' + retStr[4] + '</select>';
				//学制
				frm.schoolinglen.value = retStr[5];
				$("#gradeid").val(retStr[6]);
			}
		}
	});	
}

//改变年级（即入学学年）时
function onChangeAcadyear(){
	var frm = document.form1;
	var schid = frm.schid.value;
	if (schid == ""){
		schid = "<#if basicClassModelDto?exists>${basicClassModelDto.schid}</#if>";
	}
	var section = parseInt(frm.section.value);
	var acadyear = frm.acadyear.value;
	jQuery.ajax({
		url:"${request.contextPath}/basedata/sch/basicClassAdmin-changeAcadyear.action",
		type:"POST",
		dataType:"JSON",
		data:$.param({acadyearchange:acadyear,schidchange:schid,sectionchange:section},true),
		async:false,
		success:function(data){
			if(data != null){
			    var retStr = data;
				//班级代码
				frm.classcode.value = retStr[0];
				$("#gradeid").val(retStr[3]);
				//学制
				frm.schoolinglen.value = retStr[4];
			}
		}
	});
}

///改变年制时（只是新增班时有效）
function onChangeSchoolinglen(){
	var frm = document.form1;
	var schid = frm.schid.value;
	var section = frm.section.value;
	var acadyear = frm.acadyear.value;
	var schoolinglen = frm.schoolinglen.value;
	if(schoolinglen == null || schoolinglen == "") return;
	//变化的各字段值
	buffalo.remoteActionCall("basicClassAdmin-remoteClass.action", "doChangeSchoolinglen",[schid,section,acadyear,schoolinglen], function(reply) {
		var rtnStr = reply.getResult();
		if(rtnStr != null){
			//入学学年
			var acadyearSelect = document.getElementById("acadyearId");
			acadyearSelect.innerHTML = '<select name="acadyear" onChange="onChangeAcadyear();" style="width:140">' + rtnStr[0] + '</select>';
			
			//班级代码
			frm.classcode.value = rtnStr[1];
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
<p class="table-dt">批量新增班级信息</p>
<table width="100%" cellspacing="0" cellpadding="0" border="0" class="table-form">
	  <tr>
	  	<th width="10%"><span class="c-orange">*</span>所属学段：</th>
        <td width="40%">
            <select name="section" onChange="onChangeSection();" msgName="所属学段" notNull="true" class="input-txt fn-left" style="width:162px;">${sectionHtml?default("")}</select></td>
        <th width="10%"><span class="c-orange">*</span>年&nbsp;&nbsp;&nbsp;&nbsp;级：</th>
        <td  width="40%" id="acadyearId">
        <select name="acadyear" onChange="onChangeAcadyear();" msgName="年级" notNull="true" class="input-txt fn-left" style="width:162px;">${acadyearHtml?default("")}</select></td>
	  </tr>
	  <tr>
        <th><span class="c-orange">*</span>学&nbsp;&nbsp;&nbsp;&nbsp;制：</th>
        <td>
        <!-- 允许学制修改时，使用下面语句
        <select name="schoolinglen" style="width:140" onChange="onChangeSchoolinglen();">${schoolinglenHtml?if_exists}</select></td>
        -->
        <!-- 不允许学制修改时，使用下面语句-->
        <input name="schoolinglen" class="input-txt fn-left input-readonly" readonly="readonly" type="text" value="${schoolinglen?if_exists}" style="width:150px;" /></td>
        <th><span class="c-orange">*</span>新增班级数量：</th>
        <td>
        	<input name="batchaddamount" type="text" value="" notNull="true" msgName="新增班级数量" class="input-txt fn-left" size="20" dataType="integer" notNull="true" minValue="1" maxValue="99" maxlength="2" style="width:150px;"/>
        	<#if systemDeploySchool?exists && systemDeploySchool == SXRRT>
        	<input name="classcode" id="classcode" type="hidden" value="${classcode?if_exists}"/>
        	<input name="datecreated" id="datecreated" type="hidden" value="${(datecreated?string('yyyy-MM-dd'))?if_exists}"/>
        	<input name="artsciencetype" id="artsciencetype" type="hidden" value="${artsciencetype?if_exists}"/>
        	</#if>
        </td>
      </tr>
      <#if !systemDeploySchool?exists || systemDeploySchool != SXRRT>
	  <tr>
        <th><span class="c-orange">*</span>建班年月：</th>
        <td><@common.datepicker msgName="建班年月" class="input-txt input-readonly" style="width:150px" notNull="true" name="datecreated" id="datecreated" value="${(datecreated?string('yyyy-MM-dd'))?if_exists}"/>
        </td>
       <#-- <td width="291" height="20" align="left">
        <input name="datecreated" type="text" value="<#if datecreated?exists>${datecreated?string("yyyy-MM-dd")}</#if>" class="input" size="20" style="width:160"> 
		<img src="${request.contextPath}/static/images/date_icon.gif" width="18" height="16" 
			onClick="fPopUpCalendarDlg('${request.contextPath}/static/js/CalendarDlg.htm',datecreated);return false" style="cursor:pointer;"></td>-->
		<th><span class="c-orange">*</span>文理类型：</th>
        <td>
        <@common.select style="width:162px;" msgName="文理类型" notNull="true" txtId="artsciencetypeId" valName="artsciencetype" valId="artsciencetype">
			${artsciencetypeHtml?if_exists}
		</@common.select>
		</td>	
      </tr>
	  <tr>
        <th>班级类型：</th>
        <td>
        <@common.select style="width:162px;" txtId="classtypeId" valName="classtype" valId="classtype">
			${classtypeHtml?if_exists}
		</@common.select>
		</td>
        <th><span class="c-orange">*</span>班级开始代码：</th>
        <td><input name="classcode" type="text" value="${classcode?if_exists}" msgName="班级开始代码" notNull="true" dataType="integer" maxlength="10" style="width:150px;"
        	class="input-txt fn-left input-readonly" readonly="readonly"/><#--是只读的-->
        </td>
      </tr>
      </#if>
      <#if subschoolidHtml?exists && subschoolidHtml!="">
	  <tr>        
	      	<th><span class="c-orange">*</span>所属分校区：</th>
	        <td colspan="3">
	        <select name="subschoolid" class="input-txt fn-left" msgName="所属分校区" notNull="true" style="width:150px;">${subschoolidHtml?if_exists}</select></td>
      </tr>
      <#else>
      		<input name="subschoolid" type="hidden" class="input-txt fn-left" style="width:150px;" value="${subschoolid?if_exists}"/>
      </#if> 
	  <tr><td colspan=4 align="left">
	  		<br><br>
			 备注：<br>
			 <#if systemDeploySchool?exists && systemDeploySchool == SXRRT>
			1、班级名称会根据填写的所属学段、入学学年、根据班级名称命名规则动态生成完整的班级名称，例如：所填学段是“初中”，
			入学学年是“2005-2006”，若当前日期是2006年10月10日，则班级名称依次为：初二（1）班，初二（2）班 ...　或者是：2005级初（1）班， 
			2005级初（2）班...<br>
			<font color="#FF0000">
			2、请确认批量新增的班级不存在。
			</font><br>
			 <#else>
			1、班级初始代码不能修改。<br>
			2、班级代码为班级开始代码+1；例：班级开始代码为20040101，则班级代码依次为：20040102,20040103 ...<br>
			3、班级名称会根据填写的所属学段、入学学年、生成班级代码最后两位和根据班级名称命名规则动态生成完整的班级名称，例如：所填学段是“初中”，
			入学学年是“2005-2006”，若当前日期是2006年10月10日，则班级名称依次为：初二（1）班，初二（2）班 ...　或者是：2005级初（1）班， 
			2005级初（2）班...<br>
			<font color="#FF0000">
			4、请确认批量新增的班级不存在。
			</font><br>
			</#if>
			<br>
	     </td>
     </tr>
  	 <tr>
		<td colspan="4" class="td-opt">
	    	<a href="javascript:formSubmit();" class="abtn-blue-big">确定</a>
	        <a href="javascript:cancel();" class="abtn-blue-big">取消</a>
	    </td>
     </tr>
</table>
</div> 
<input name="schid" type="hidden" value="${schid?if_exists}"/>
<input name="gradeid" id="gradeid" type="hidden" value="${gradeid?if_exists}"/>
</form>
</@common.moduleDiv>