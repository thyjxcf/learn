<#import "/common/commonmacro.ftl" as common>
<#import "/common/htmlcomponent.ftl" as htmlcomponent>
<@htmlcomponent.moduleDiv titleName="${webAppTitle}--年级设置">
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script language="javascript">
var isSubmitting = false;
//保存年级设置信息
function saveGrades() {
	if (isSubmitting) {
		return;
	}
	if(!checkAllValidate("#dataDivId")){
		isSubmitting=false;
		return;
	}
	var flag = false;
	<#if gradeList?exists&&(gradeList?size>0)>
	<#list gradeList as item>	
	var acadyear = $("#acadyear${item.id}").val().substring(0,4);
	var chaYear = ${currentSemYear!}-acadyear;
	var schoolinglen = $("#schoolinglen${item.id}").val();
	if(chaYear>=schoolinglen){
		addFieldError("schoolinglen${item.id}","学制至少为"+(chaYear+1)+"年，请修改");
		flag = true;
	}
	</#list>
	</#if>
	if(flag){
		return;
	}
	
  	isSubmitting = true;
  	var options = {
		url : "${request.contextPath}/basedata/sch/basicClassAdmin-saveGrades.action",
		success : function(data){
			if(data.operateSuccess){
				showMsgSuccess(data.promptMessage,"提示",function(){
					cancel();
				});
			}else{
				showMsgError(data.promptMessage);
			}
			isSubmitting=false;
		},
		async:false,//同步操作
		dataType : 'json',
		clearForm : false,
		resetForm : false,
		type : 'post',
		error:function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}//请求出错 
	};
	$("#dataDivId").ajaxSubmit(options);
}


function removeGrade(gradeId) {
  if (isSubmitting) {
    return;
  }
  if (!confirm("您确定要删除此年级吗？")) {
    return;
  }
  isSubmitting = true;
  var options = {
		url : "${request.contextPath}/basedata/sch/basicClassAdmin-deleteGrade.action?gradeId="+gradeId,
		async:false,//同步操作
		dataType : 'json',
		clearForm : false,
		resetForm : false,
		type : 'post',
		success : function(data){
			if(data.operateSuccess){
				showMsgSuccess(data.promptMessage,"提示",function(){
					load("#container","${request.contextPath}/basedata/sch/basicClassAdmin-GradeList.action?section=${section!}");
				});
			}else{
				showMsgError(data.errorMessage);
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}//请求出错 
	};
  $("#dataDivId").ajaxSubmit(options);
  isSubmitting = false;
}

function newGrade(){
	buffalo.remoteActionCall("remoteBasicGradeAction.action", "getNewGradeId", [], function(reply) {
	    window.scrollTo(0,0);
	    clearMessages();
	    drawMessages(reply);
	    if (!reply.getResult().hasErrors) {
			gradeId = reply.getResult();
	    }	      		
  	});

	var dataTable = $("dataTable");
	var tr = dataTable.insertRow();
	var trIndex = dataTable.rows.length;
	if ((trIndex + 1) % 2 == 1){
		tr.style.backgroundColor = "#FFF";
	}
	else{
		tr.style.backgroundColor = "#F3F5FE";
	}
	tr.id="tr" + gradeId;
	tr.insertCell().innerText = trIndex - 1;
	tr.insertCell().innerHTML = "<input name='gradeName' id='gradeName" + gradeId + "' class='blueText' value=''>";
	var td = tr.insertCell();
	td.id="td" + gradeId;	
	td.innerHTML="<input name='gradeId' type='hidden' value='" + gradeId + "'><input name='gradeSchId' id='schId" + gradeId + "' type='hidden' value='${loginInfo.getUnitID()}'><input id='t" + gradeId + "' name='teacherId' type='hidden' value=''><input id='lab"+ gradeId +"' name='lab"+ gradeId +"' type='text' style='width:140' class='input_readonly' value=''/> ";
	tr.insertCell().innerHTML = "<input class='blueText' name='acadyear' id='acadyear" + gradeId + "' onblur= javascript:takeGradeName('" + gradeId + "'); size='10'>";
	tr.insertCell().innerHTML = "<input name='schoolinglen' id='schoolinglen" + gradeId + "' class='blueText' onblur=javascript:takeGradeName('" + gradeId + "'); size='3'>";
	tr.insertCell().innerHTML = "<input name='amLessonCount' class='blueText' value='4' size='3'>";
	tr.insertCell().innerHTML = "<input name='pmLessonCount' class='blueText' value='4' size='3'>";
	tr.insertCell().innerHTML = "<input name='nightLessonCount' class='blueText' value='0' size='3'>";
	tr.insertCell().innerHTML = "<a href='#' onclick=javascript:removeGrade('" + gradeId + "')>删除</a>";
}

function takeGradeName(id){
	var gradeName = $("gradeName" + id).value;
	if (gradeName != ""){
		return;
	}	
	var schId = $("schid" + id).value;
	if (schId || schId == ""){
		schId = "${loginInfo.getUnitID()}";
	}
	var acadyear = $("acadyear" + id).value;
	var section = $("selectSectionCode").value;
	var schoolingLength = parseInt($("schoolinglen" + id).value);
	if (section == "" || acadyear == "" || schoolingLength == "" || isNaN(schoolingLength)){
		return;
	}
	buffalo.remoteActionCall("remoteBasicGradeAction.action", "takeGradeName", [schId, acadyear, section, schoolingLength], function(reply) {
	    window.scrollTo(0,0);
	    clearMessages();
	    drawMessages(reply);
	    if (!reply.getResult().hasErrors) {
			$("gradeName" + id).value = reply.getResult();
	    }	      		
  	});
}

function changeSection(){
	var section = $("#selectSectionCode").val();
	load("#container","${request.contextPath}/basedata/sch/basicClassAdmin-GradeList.action?section=" + section);
}

function cancel(){
	load("#container","${request.contextPath}/basedata/sch/basicClassAdmin.action");
}

function addNewGreade(){
	openDiv("#addDiv", "#addDiv .close,#addDiv .reset", "${request.contextPath}/basedata/sch/basicClassAdmin-addNewGrade.action?section=${section?default('')}&&schoolinglen=${schoolinglen!}", null, null, 400, vselect);
}
</script>
<div class="popUp-layer" id="addDiv" style="display:none;width:400px;"></div>
<div>
<form action="" method="post" id="dataDivId" name="mainform">
<p class="pub-operation">
	<div class="query-tt fn-left b mt-5">请选择学段：</div>
    <div class="query-tt fn-left">
    	<@htmlcomponent.select style="width:150px;" txtId="selectSectionCodeId" valName="selectSectionCode" valId="selectSectionCode" myfunchange="changeSection();" >
			<#list sectionList as sec>
			<a val="${sec[1]}" <#if section == sec[1]> class="selected"</#if> ><span>${sec[2]}</span></a>
		</#list>
		</@htmlcomponent.select>
    </div>
    <div class="query-tt fn-right">
    <#if (schoolinglen>0) && (schoolinglen>gradeList?size)>
    <a href="javascript:addNewGreade();" class="abtn-orange-new">新增年级</a>
    </#if>
	</div>
	<div class="fn-clear"></div>
</p>
<#--
<td align="right">
	<span>课时数请到<strong>学年学期设置</strong> 模块进行设置</span>
</td>-->
<@htmlcomponent.tableList id="tablelist" class="public-table table-list mt-15">
	<tr>
		<th width="5%">序号</th>
	  	<th width="15%">年级名称</th>
	  	<th width="15%">年级组长</th>
	    <th width="15%">入学学年</th>                
		<th width="10%">学制(年)</th>
	    <th width="12%">上午课时</th>
	    <th width="12%">下午课时</th>
	    <th width="11%">晚上课时</th>
	    <th width="5%">操作</th>
	</tr>			  
	<#if gradeList?exists&&(gradeList?size>0)>
	<#list gradeList as item>	
  		<tr <#if (item_index+1)%2==1>bgcolor="#FFFFFF"<#else>bgcolor="#F3F5FE"</#if> id="tr${item.id}">
        	<td align="center">${item_index + 1}</td>
        	<td><input name="gradeList[${item_index}].gradename" id="gradeName${item.id}" maxlength="32" notnull="true" class="input-txt fn-left <#if switchOn?exists&&switchOn=='no'>input-readonly</#if>" <#if switchOn?exists&&switchOn=="no">readonly="readonly"</#if> style="width:150px;" value="${item.gradename?default("")}"></td>
         	<td id="td${item.id?default("")}">
         		<input name="gradeList[${item_index}].section" type="hidden" value="${item.section?default("")}">
         		<input name="gradeList[${item_index}].id" type="hidden" value="${item.id?default("")}">
         		<input name="gradeList[${item_index}].schid" id="schId${item.id}" type="hidden" value="${item.schid?default("")}">
				<@common.selectOneTeacher idObjectId="t"+item.id?default("") nameObjectId="lab"+item.id?default("")>
	         		<input id="t${item.id?default("")}" name="gradeList[${item_index}].teacherId" type="hidden" value="${item.teacherId?default("")}"/>
	         		<input id="lab${item.id?default("")}" name="lab${item.id?default("")}" type="text" style="width:150px;" class="input-txt fn-left input_readonly" value="${item.teacherName?default("")}"/>
				</@common.selectOneTeacher>
		    </td>
        	<td><input class="input-txt fn-left input-readonly" style="width:100px;" name="gradeList[${item_index}].acadyear" id="acadyear${item.id}" onblur="javascript:takeGradeName('${item.id}');" value="${item.acadyear?default("")}" readonly="readonly" size="10"></td>
        	<td><input class="input-txt fn-left" style="width:100px;" name="gradeList[${item_index}].schoolinglen" id="schoolinglen${item.id}" value="${item.schoolinglen?default(1)}" datatype="integer" maxlength="1" size="3"></td>
        	<td><input name="gradeList[${item_index}].amLessonCount" id="${item_index}am" value="${item.amLessonCount?default(4)}" class="input-txt fn-left" style="width:50px;" datatype="integer" size="3" maxlength="1" minvalue="0" maxValue="5"></td>				
        	<td><input name="gradeList[${item_index}].pmLessonCount" id="${item_index}pm" value="${item.pmLessonCount?default(4)}" class="input-txt fn-left" style="width:50px;" datatype="integer" size="3" maxlength="1" minvalue="0" maxValue="5"></td>
        	<td><input name="gradeList[${item_index}].nightLessonCount" id="${item_index}ni" value="${item.nightLessonCount?default(0)}" class="input-txt fn-left" style="width:50px;" datatype="integer" size="3" maxlength="1" minvalue="0" maxValue="5"></td>
        	<td><a href="#" onclick="javascript:removeGrade('${item.id}')">删除</a></td>
      	</tr>
	 </#list>
	 <#else>
	  	<tr>
           <td colspan=9> <p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td>
    	</tr>
	 </#if>
	<!--<tr><td><span id="actionError"></span></td></tr>-->
	<tr height="50px">
		<td colspan="9" class="t-center td-opt">
			<#if gradeList?exists&&(gradeList?size>0)>
	    	<a href="javascript:saveGrades();" class="abtn-blue-big"">保存</a>
		    </#if>
	    	<a href="javascript:cancel();" class="abtn-blue-big">返回</a>
	    </td>
    </tr>
</@htmlcomponent.tableList>
<input name="schid" type="hidden" value="${schid?if_exists}"/>
</form>
</div>
</@htmlcomponent.moduleDiv>