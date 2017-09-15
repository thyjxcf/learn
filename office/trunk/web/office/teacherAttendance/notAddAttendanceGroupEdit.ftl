<#import "/common/commonmacro.ftl" as commonmacro>
<div id="attendanceGroupdiv" class="wrap pa-10">
	<form id="mainform" action="" method="post" >

	<p class="table-dt">不参加考勤统计人员</p>
    <table border="0" cellspacing="0" cellpadding="0" class="table-form">
    <tr>
	<tr>
   		<th width="15%"><span class="c-orange mr-5">*</span>不参加考勤统计人员：</th>
   		<td colspan="4">
	       	<@commonmacro.selectMoreTree idObjectId="teacherIdStr" nameObjectId="teachersshow"  width=600 height=600   treeUrl=request.contextPath+"/common/xtree/teacherTree.action?allLinkOpen=false" >
		  	   <input type="hidden" name="teacherIdStr" id="teacherIdStr" class="select_current02" value="${officeAttendanceNotStaticstisPeopleDto.teacherIds!}"> 
		  	   <textarea name="teachersshow" id="teachersshow" notNull="true" msgName="考勤组人员" class="text-area my-10 input-readonly" rows="4" cols="69" maxLength="2000" style="width:80%;padding:5px 1%;height:50px;" readonly="readonly">${officeAttendanceNotStaticstisPeopleDto.teacherNames!}</textarea>
  	         </@commonmacro.selectMoreTree>
		</td>
   	</tr>
    </table>
    </form>
<p class="t-center pt-15">
	<a href="javascript:saveNotAttendanceGroup();" id="btnSave" class="abtn-blue-big">保存</a>
	<a href="javascript:doClose();" class="abtn-blue-big">关闭</a>
</p>
    
</div>
<script>
	var flag=false;
	function saveNotAttendanceGroup(){
		if(flag){
			return;
		}
		flag = true;
		jQuery.ajax({
			url:"${request.contextPath}/office/teacherAttendance/teacherAttendance-notAddStatisticPeopleEdit.action?"+jQuery("#mainform").serialize(),
			type:"post",
			async:false,
			dataType:"json",
			success:function(data){
				if(data.operateSuccess){
					showMsgSuccess(data.promptMessage);
				}else{
					showMsgError(data.errorMessage);
				}
				flag=false;
			}
		});
		
	}
	function doClose(){
		closeDiv("#addDiv");
	}
</script>