<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as common>
<script>
	$(function(){
		vselect();
	});
	
	function doSave(){
		if(!isActionable("#btnSave")){
			return false;
		}
		if(!checkAfterDate($("#startTime").get(0),$("#endTime").get(0))){
			return;
		};
		if(!checkAllValidate("#studentLeaveApply")){
			return;
		}
		$("#btnSave").attr("class","abtn-unable-big");
		
		var options={
			url:'${request.contextPath}/office/studentLeave/studentLeave-applySave.action',
			dataType:'json',
			clearForm:false,
			resetForm:false,
			type:'post',
			success:showReply
		};
		$("#studentLeaveApply").ajaxSubmit(options);
	}
	
	function showReply(data){
		if(!data.operateSuccess){
			showMsgError(data.promptMessage);
			$("#btnSave").attr("class","abtn-blue-big");
			return;
		}else{
			showMsgSuccess("保存成功","提示",function(){
			$("btnSave").attr("class","abtn-blue-big");
			stuLeaveApply();
			});
		}
	}
	
	function doSubmit(){
		if(!isActionable("#btnSubmit")){
			return false;
		}
		if(!checkAfterDate($("#startTime").get(0),$("#endTime").get(0))){
			return;
		};
		if(!checkAllValidate("#studentLeaveApply")){
			return;
		}
		$("#btnSubmit").attr("class","abtn-unable-big");
		var options={
			url:'${request.contextPath}/office/studentLeave/studentLeave-applySubmit.action',
			dataType:'json',
			clearForm:false,
			resetForm:false,
			type:'post',
			success:showReply1
		};
		$("#studentLeaveApply").ajaxSubmit(options);
	}
	
	function showReply1(data){
		if(!data.operateSuccess){
			showMsgError(data.promptMessage);
			$("#btnSubmit").attr("class","abtn-blue-big");
			return;
		}else{
			showMsgSuccess("提交成功","提示",function(){
			$("btnSubmit").attr("class","abtn-blue-big");
			stuLeaveApply();
			});
		}
	}
	
	function back(){
		stuLeaveApply();
	}
</script>
<@htmlmacro.moduleDiv titleName="学生请假登记">
	<form id="studentLeaveApply" name="studentLeaveApply" method="post" action="">
	<input type="hidden" name="officeStudentLeave.classId" value="${officeStudentLeave.classId!}">
	<input type="hidden" name="officeStudentLeave.acadyear" value="${officeStudentLeave.acadyear!}">
	<input type="hidden" name="officeStudentLeave.semester" value="${officeStudentLeave.semester!}">
	<input type="hidden" name="officeStudentLeave.id" value="${officeStudentLeave.id!}">
	<input type="hidden" name="officeStudentLeave.isDeleted" value="${officeStudentLeave.isDeleted!}">
	<input type="hidden" name="officeStudentLeave.state" value="${officeStudentLeave.state!}">
	<@htmlmacro.tableDetail divClass="table-form">
		<tr>
			<th colspan="4"  style="text-align:center;">学生请假申请</th>
		</tr>
		<#if view>
		<tr>
			<th><span class="c-orange mr-5">*</span>请假时间：</th>
			<td colspan="3">
    			${((officeStudentLeave.startTime)?string('yyyy-MM-dd HH:mm'))?if_exists}				
				至
				${((officeStudentLeave.endTime)?string('yyyy-MM-dd HH:mm'))?if_exists}				
			</td>
		</tr>
		<tr>
			<th><span class="c-orange mr-5">*</span>共计天数：</th>
			<td>${officeStudentLeave.days?string('0.#')!}</td>
			<th><span class="c-orange mr-5">*</span>申请人：</th>
			<td>
				${officeStudentLeave.stuName!}
			</td>
		</tr>
		<tr>
			<th><span class="c-orange mr-5">*</span>请假类型：</th>
			<td>
				${officeStudentLeave.leaveTypeName!}
			</td>
			<th><span class="c-orange mr-5"></span>提交人：</th>
			<td>
				${officeStudentLeave.createUserName!}
			</td>
		</tr>
		<tr>
			<th><span class="c-orange mr-5">*</span>请假原因：</th>
			<td colspan="3">
				<textarea name="officeStudentLeave.remark" notNull="true" class="text-area my-5" style="width:80%;padding:5px 1%;height:50px;" disabled="disabled" id="remark" rows="4" cols="70" maxlength="200">${officeStudentLeave.remark!}</textarea>
			</td>
		</tr>
			<#if (officeStudentLeave.state>=3)>
					<tr>
						<th colspan="4" style="text-align:center;">审核信息</th>
					</tr>
					<tr>
						<th width="25%">审核人：</th>
						<td colspan="3">${officeStudentLeave.auditUserName!}</td>
					</tr>
					<tr>
						<th>审核意见：</th>
						<td colspan="3">
							${officeStudentLeave.auditRemark!}
						</td>
					</tr>		
			</#if>
		<#else>
		<tr>
			<th><span class="c-orange mr-5">*</span>请假时间：</th>
			<td colspan="3">
    			<@htmlmacro.datepicker id="startTime" name="officeStudentLeave.startTime" style="width:280px;" msgName="开始时间" value="${((officeStudentLeave.startTime)?string('yyyy-MM-dd HH:mm:ss'))?if_exists}" class="input-txt" notNull="true" dateFmt="yyyy-MM-dd HH:mm:00" maxlength="19"/>				
				至
				<@htmlmacro.datepicker id="endTime" name="officeStudentLeave.endTime" style="width:280px;" msgName="结束时间" value="${((officeStudentLeave.endTime)?string('yyyy-MM-dd HH:mm:ss'))?if_exists}" class="input-txt" notNull="true" dateFmt="yyyy-MM-dd HH:mm:00" maxlength="19"/>				
			</td>
		</tr>
		<tr>
			<th style="width:20%"><span class="c-orange mr-5">*</span>共计天数：</th>
			<td style="width:30%"><input id="officeStudentLeave.days" name="officeStudentLeave.days"style="width:140px;" maxlength="5" dataType="float" maxValue="999" minValue="0.1" decimalLength="1" value="${(officeStudentLeave.days?string('0.#'))?if_exists}" class="input-txt" notNull="true" msgName="共计天数" style="width:140px;"></td>
			<th style="width:20%"><span class="c-orange mr-5">*</span>申请人：</th>
			<td style="width:30%">
				<@common.selectTree idObjectId="studentId" nameObjectId="stuName" treeUrl=request.contextPath+"/common/xtree/studentTree.action?allLinkOpen=false">
					<input type="hidden" id="studentId" name="officeStudentLeave.studentId" value="${officeStudentLeave.studentId!}">
					<input type="text" id="stuName" name="officeStudentLeave.stuName" class="input-txt" value="${officeStudentLeave.stuName!}" notNull="true">
				</@common.selectTree>
			</td>
		</tr>
		<tr>
			<th><span class="c-orange mr-5">*</span>请假类型：</th>
			<td>
				<@htmlmacro.select valId="leaveTypeId" valName="officeStudentLeave.leaveTypeId" style="width:280px;" className="" notNull="true">
					<#list leaveTypeList as lev>
						<a val="${lev.id!}" <#if lev.id?default('')==(officeStudentLeave.leaveTypeId)!>class="selected"</#if>><span>${lev.name!}</span></a>
					</#list>
				</@htmlmacro.select>
			</td>
			<th><span class="c-orange mr-5"></span>提交人：</th>
			<td>${officeStudentLeave.createUserName!}</td>
		</tr>
		<tr>
			<th><span class="c-orange mr-5">*</span>请假原因：</th>
			<td colspan="3">
				<textarea name="officeStudentLeave.remark" notNull="true" id="remark" rows="4" cols="70" maxlength="200">${officeStudentLeave.remark!}</textarea>
			</td>
		</tr>
		</#if>
		<tr>
			<td colspan="4" class="td-opt">
				<#if !view>
				 <a class="abtn-blue-big" href="javascript:void(0);" onclick="doSave();" id="btnSave">保存</a>
		    	 <a class="abtn-blue-big" href="javascript:void(0);" onclick="doSubmit();" id="btnSubmit">提交审核</a>
		    	 </#if>
				 <a class="abtn-blue-big ml-5" href="javascript:void(0);" onclick="back();">返回</a>
			</td>
		</tr>
	</@htmlmacro.tableDetail>
	</form>

</@htmlmacro.moduleDiv>