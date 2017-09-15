<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">

<div id="attendanceGroupdiv">
<form id="mainform" action="" method="post" >
	<input type="hidden" name="officeAttendanceGroup.unitId" value="${officeAttendanceGroup.unitId!}" >
	<input type="hidden" id="groupid"  name="officeAttendanceGroup.id" value="${officeAttendanceGroup.id!}" >
	<input type="hidden" id="groupid"  name="officeAttendanceGroup.creationTime" value="${officeAttendanceGroup.creationTime!}" >

	<p class="table-dt">考勤组</p>
    <table border="0" cellspacing="0" cellpadding="0" class="table-form">
    <tr>
        <th width="15%"><span class="c-orange mr-5">*</span>考勤组名称：</th>
        <td width="35%">
        	<input type="text" id="groupName" name="groupName" class="input-txt fn-left" style="width:200px;" notNull="true" onblur="checkGroupName();"; msgName="考勤助名称" maxlength="40" value="${officeAttendanceGroup.name!}">
        </td>
    	<th width="15%"><span class="c-orange mr-5">*</span>考勤时段：</th>
        <td width="35%">
	            <@htmlmacro.select valName="officeAttendanceGroup.attSetId" notNull="true" msgName="考勤时间段"  curVal="${officeAttendanceGroup.attSetId!}" valId="attendanceSet" txtId="attendanceSet_name"  style="width:200px;" >
	            		<a value="" <#if officeAttendanceGroup.attSetId?default('') == ''> class="selected" </#if> >--请选择--</a>
					    <#list attendanceSetList as set>
					    	<a val="${set.id!''}" <#if set.id == officeAttendanceGroup.attSetId?default('') > class="selected"</#if> >${set.name!''}</a>
					    </#list>
	            </@htmlmacro.select>
	    </td>
    </tr> 
     <tr>
        <th width="15%"><span class="c-orange mr-5">*</span>考勤地点：</th>
		<td width="85%" colspan="3">
		<#list attendancePlaceList as place >
		<#assign flag = false />
		<#list placeMap?keys as key>
			<#if  key == place.id > 
				<#assign flag = true />
			</#if>
		</#list>
			<#if flag >
				<span class="ui-checkbox  ml-10 ui-checkbox-current "><input name="placeNames"  value="${place.id!}" type="checkbox" class="chk"  checked="checked" >${place.name!}</span>
			<#else>
				<span class="ui-checkbox ml-10"><input  name="placeNames" value="${place.id!}" type="checkbox" class="chk" >${place.name!}</span>
			</#if>
		</#list>
			<span id="errorPlace"  class="field_tip input-txt-warn-tip" style="display: none;"  >考勤地点  不能为空！</span>
		</td>
    </tr> 
   	<tr>
   		<th width="15%"><span class="c-orange mr-5">*</span>考勤组人员：</th>
   		<td colspan="4">
	       	<@commonmacro.selectMoreTree idObjectId="teacherIdStr" nameObjectId="teachersshow" width=600 height=600  treeUrl=request.contextPath+"/common/xtree/teacherTree.action?allLinkOpen=false" >
		  	   <input type="hidden" name="teacherIdStr" id="teacherIdStr" class="select_current02" value="${officeAttendanceGroup.teacherIds!}"> 
		  	   <textarea name="teachersshow" id="teachersshow" notNull="true" msgName="考勤组人员" class="text-area my-10 input-readonly" rows="4" cols="69"  style="width:80%;padding:5px 1%;height:50px;" readonly="readonly">${officeAttendanceGroup.userNames!}</textarea>
  	         </@commonmacro.selectMoreTree>
		</td>
   	</tr>
    </table>

</form>
</div>
<p class="t-center pt-15">
	<a href="javascript:saveAttendanceGroup();" id="btnSave" class="abtn-blue-big">保存</a>
    <a href="javascript:;" class="abtn-blue-big" onclick="doBack();" >返回</a>
</p>
<script>
	var flag=false;

	function saveAttendanceGroup(){
		if(!chackAllElement()){
			return;
		}
		if(flag){
			return;
		}
		flag = true;
		var places = "";
		var placeNames = $("input[name='placeNames']:checked");
		var len = placeNames.length;
		for(var j=0;j<len;j++){
			if(j==0){
				places = $(placeNames[j]).val();
			}else{
				places += "," + $(placeNames[j]).val()
			}
		}

		var options = {
	       url:'${request.contextPath}/office/teacherAttendance/teacherAttendance-editGroup.action', 
	       success : function(data){
					if(data.operateSuccess){
						showMsgSuccess(data.promptMessage,"",function(){
							load("#showListDiv","${request.contextPath}/office/teacherAttendance/teacherAttendance-groupMain.action");
						});
					}else{
						showMsgError(data.promptMessage);
					}
					flag=false;
				},
		   data:{"places":places},
	       dataType : 'json',
	       clearForm : false,
	       resetForm : false,
	       type : 'post',
	       timeout : 30000 
	    };
		$('#mainform').ajaxSubmit(options);
		
	}
	
	function checkGroupName(){
	
		var groupName = $("#groupName").val();
		var groupId = $("#groupid").val();
		if(groupName.trim() == ""){
			$("#error").remove();
		}
		jQuery.ajax({
			url:"${request.contextPath}/office/teacherAttendance/teacherAttendance-checkGroupName.action",
			type:"post",
			async:false,
			data:{"groupName":groupName,"groupId":groupId},
			success:function(data){
				if(data.operateSuccess){
					if(document.getElementById("error")){
						$("#error").remove();
					}
				}else{
					if(!document.getElementById("error")){
					  $("#groupName").after("<span id=\"error\" class=\"input-txt-warn-tip\">" + data.errorMessage + "</span>");
					}
				}
			}
		});
	}
	function chackAllElement(){
		var flag1 = false;
		var flag2 = false;
		if(checkAllValidate("#mainform")){
			flag1=true;
		}
		var names = document.getElementsByName("placeNames");
		for(var i=0;i<names.length;i++){
			if(names[i].checked){
				flag2=true;
			}
		}
		if(!flag2){
			$("#errorPlace").show();
		}else{
			$("#errorPlace").hide();
		}
		return flag1 && flag2;
	}
	function doBack(){
		load("#showListDiv","${request.contextPath}/office/teacherAttendance/teacherAttendance-groupMain.action");
	}
</script>
</@htmlmacro.moduleDiv>