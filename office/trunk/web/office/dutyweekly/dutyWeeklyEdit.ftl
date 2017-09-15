<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<#import "../jtgooutmanage/archiveWebuploader.ftl" as archiveWebuploader>
<@htmlmacro.moduleDiv titleName="值周安排">
	<p class="tt"><a href="javascript:void(0);" class="close">关闭</a><span style="text-align:center;">值周安排</span></p>
	<div class="wrap pa-10">
	<form name="officeDutyWeeklyForm" id="officeDutyWeeklyForm" method="post">
		<input id="id" name="officeDutyWeekly.id" value="${officeDutyWeekly.id!}" type="hidden" />
		<input id="unitId" name="officeDutyWeekly.unitId" value="${officeDutyWeekly.unitId!}" type="hidden" />
		<input id="createUserId" name="officeDutyWeekly.createUserId" value="${officeDutyWeekly.createUserId!}" type="hidden" />
		<input id="createTime" name="officeDutyWeekly.createTime" value="${officeDutyWeekly.createTime!}" type="hidden" />
		<input id="year" name="officeDutyWeekly.year" value="${officeDutyWeekly.year!}" type="hidden" />
		<input id="semester" name="officeDutyWeekly.semester" value="${officeDutyWeekly.semester!}" type="hidden" />
		<@htmlmacro.tableDetail divClass="table-form">
		    <tr>
		       <th style="width:10%"><span class="c-orange mr-5">*</span>周次：</th>
		       <td style="width:40%"  colspan="3">
		        	<#if officeDutyWeekly.id?exists>
		        	<div class="sendMsg-form">
						<@htmlmacro.select style="width:80px;" valId="week" valName="officeDutyWeekly.week" txtId="searchWeekTxt" myfunchange="" >
							<#list weekTimeList as item>
								<a val="${item}" <#if item == officeDutyWeekly.week+''>class="selected"</#if>>第${item!}周</a>
							</#list>
						</@htmlmacro.select>
					</div>
					<#else>
		        	<div class="sendMsg-form">
						<@htmlmacro.select style="width:80px;" valId="week" valName="officeDutyWeekly.week" txtId="searchWeekTxt" myfunchange="" >
							<#list weekTimeList as item>
								<a val="${item}" <#if item ==weeks>class="selected"</#if>>第${item!}周</a>
							</#list>
						</@htmlmacro.select>
					</div>
					</#if>
		       </td>
		    </tr>
		    <tr>
		        <th><span class="c-orange mr-5">*</span>周日期：</th>
		        <td colspan="3" >
		        	<@htmlmacro.datepicker name="officeDutyWeekly.weekStartTime" id="weekStartTime" class="input-txt" style="width:25%;" msgName="值周开始日期" notNull="true" size="20" maxlength="19" dateFmt="yyyy-MM-dd" value="${((officeDutyWeekly.weekStartTime)?string('yyyy-MM-dd'))?if_exists}" />
		        	至
		        	<@htmlmacro.datepicker name="officeDutyWeekly.weekEndTime" id="weekEndTime" class="input-txt" style="width:25%;" msgName="值周结束日期" notNull="true" size="20" maxlength="19" dateFmt="yyyy-MM-dd" value="${((officeDutyWeekly.weekEndTime)?string('yyyy-MM-dd'))?if_exists}" />
		        </td>
		    </tr>
		    
		    <tr>
		       <th style="width:20%"><span class="c-orange mr-5">*</span>值周年级：</th>
		       <td style="width:30%">
		       		<@htmlmacro.select style="width:83%;" valName="officeDutyWeekly.dutyGrade" valId="dutyGrade" notNull="true" myfunchange="changeGrade" msgName="值周年级">
					<a val="" ><span>请选择</span></a>
						<#if gradesList?exists && gradesList?size gt 0>
							<#list gradesList as item>
								<a val="${item.id!}"<#if officeDutyWeekly.dutyGrade?default('') == item.id>class="selected"</#if>  ><span>${item.gradename!}</span></a>
							</#list>
						</#if>
					</@htmlmacro.select>
		        	<!--<input name="officeDutyWeekly.dutyClass" id="dutyClass" type="text" class="input-txt" style="width:35%;" maxlength="50"  value="${officeDutyWeekly.dutyClass!}" msgName="值周班级" notNull="true" />-->
		       </td>
		       <th style="width:20%"><span class="c-orange mr-5">*</span>值周班级：</th>
		       <td style="width:30%">
		       		<@htmlmacro.select style="width:83%;" valName="officeDutyWeekly.dutyClass" valId="dutyClass" txtId="classTxt" notNull="true" optionDivName="itemClassIdOptionDiv" msgName="值周班级">
						<a val="" ><span>请选择</span></a>
					</@htmlmacro.select>
		       </td>
		    </tr>
		    <tr>
		       <th><span class="c-orange mr-5">*</span>值周登记教师：</th>
		        <td colspan="3">
		        	<@commonmacro.selectAddressBookAllLayer userObjectId="dutyTeacher" deptObjectId="" unitObjectId="" detailObjectId="dutyTeacherNames" sendToOtherUnit="false" otherParam="" callback="resetUserIds" preset="" height="430">
			        	<input type="hidden" id="dutyTeacher" name="officeDutyWeekly.dutyTeacher" value="${officeDutyWeekly.dutyTeacher!}"/>
			        	<textarea id="dutyTeacherNames" name="officeDutyWeekly.dutyTeacherNames" cols="70" rows="4" class="text-area my-5" style="width:97%;padding:5px 1%;height:100px;line-height:20px;" msgName="值周登记教师">${officeDutyWeekly.dutyTeacherNames!}</textarea>
            		</@commonmacro.selectAddressBookAllLayer>
		        <span id="noneUser" class="field_tip input-txt-warn-tip"></span>
		        </td>
		    </tr>
		    <tr>
		    	<td colspan="4" class="td-opt">
		    	    <a class="abtn-blue" href="javascript:void(0);" onclick="doSave()">保存</a>
		    	    <a class="abtn-blue reset ml-5" href="javascript:void(0);">取消</a>
		        </td>
		    </tr>
		</@htmlmacro.tableDetail>
	</form>
	</div>
	<script>
var selectedValue="";	
$(document).ready(function(){
	vselect();
	selectedValue = '${officeDutyWeekly.dutyClass!}';
 	changeGrade();
});
function changeGrade(){
	var dutyGrade = $("#dutyGrade").val();
	getClassItems(dutyGrade);
}
function getClassItems(gradeId,curVal){
	if(curVal){
		selectedValue=curVal;
	}
	$.ajax({
		type: "POST",
		url: "${request.contextPath}/office/dutyweekly/dutyweekly-findClassList.action",
		async:false,
		dataType:"json",
		data: $.param({"id":gradeId},true),
		success: assembelClassItemList,
		error: function(XMLHttpRequest, textStatus, errorThrown){alert(errorThrown);}
	});
}
function assembelClassItemList(data){
	if (data.length>0) {
		var optionHtml="<div style='position:absolute;z-index:-1;width:100%;height:102%;'><iframe style='width:100%;height:100%;border:0;filter:alpha(opacity=0);-moz-opacity:0'></iframe></div>"
		optionHtml+="<div class='a-wrap'>";
		optionHtml+="<a val=''><span>请选择</span></a>";
		$.each(data,function(j,item){
			if(item.id == selectedValue){
				optionHtml+="<a val='"+item.id+"' class='selected'><span>"+item.classnamedynamic+"</span></a>";
			}else{
				optionHtml+="<a val='"+item.id+"'><span>"+item.classnamedynamic+"</span></a>";
			}
		});	
		optionHtml+="</div>";
		$("#classTxt").val('请选择');
		$("#classId").val('');
		$("#itemClassIdOptionDiv").html(optionHtml);

	} else {
		var optionHtml="<div style='position:absolute;z-index:-1;width:100%;height:102%;'><iframe style='width:100%;height:100%;border:0;filter:alpha(opacity=0);-moz-opacity:0'></iframe></div>"
		optionHtml+="<div class='a-wrap'>";
		optionHtml+="<a val=''><span>请选择</span></a>";
		optionHtml+="</div>";

		$("#itemClassIdOptionDiv").html(optionHtml);
		$("#classTxt").val('请选择');
		$("#classId").val('');
	}
	vselect();
	selectedValue="";
}


	function resetUserIds(){
		if($("#dutyTeacher").val().length > 0){
			var receiveUserId = $("#dutyTeacher").val().split(",");
			if(receiveUserId.length<2){
	    		showMsgWarn("值周登记教师数量最少为2个！");
	    		return;
	   		}
		}
	};
		
		function checkNoneUser(){
			$("#noneUser").text("");
			var dutyTeacher=$("#dutyTeacher").val();
			if(dutyTeacher==null||dutyTeacher==''){
				$("#noneUser").text("值周登记教师 不能为空！");
				return false;
			}
			$("#noneUser").text("");
			return true;
		}
		
		var isSubmit =false;
		function doSave(){
			if(isSubmit){
				return;
			}
			if(!checkAllValidate("#officeDutyWeeklyForm")){
				if(!checkNoneUser()){
					return;
				}
				return;
			}
			if(!checkNoneUser()){
					return;
			}
			
			if($("#dutyTeacher").val().length > 0){
			var receiveUserId = $("#dutyTeacher").val().split(",");
			if(receiveUserId.length<2){
	    		showMsgWarn("值周登记教师数量最少为2个！");
	    		return;
	   		}
		}
			
			if(!checkAfterDate($("#weekStartTime").get(0),$("#weekEndTime").get(0))){
				return;
			}
			
			var options = {
		       url:'${request.contextPath}/office/dutyweekly/dutyweekly-dutyweeklySave.action', 
		       success : showReply,
		       dataType : 'json',
		       clearForm : false,
		       resetForm : false,
		       type : 'post'
		    };
		    isSubmit = true;
		    $('#officeDutyWeeklyForm').ajaxSubmit(options);
		}
		
		function showReply(data){
			if(!data.operateSuccess){
			   if(data.errorMessage!=null&&data.errorMessage!=""){
				   showMsgError(data.errorMessage);
				   isSubmit = false;
				   return;
			   }
			}else{
				showMsgSuccess(data.promptMessage,"",function(){
				  	dutyweekly();
				});
				return;
			}
		}
		
		
	</script>
</@htmlmacro.moduleDiv >