<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.moduleDiv titleName="教师请假申请">
	<form name="teacherLeaveNhForm" id="teacherLeaveNhForm" method="post">
		<input id="id" name="officeTeacherLeaveNh.id" value="${officeTeacherLeaveNh.id!}" type="hidden" />
		<input id="flowType" name="officeTeacherLeaveNh.flowType" value="${officeTeacherLeaveNh.flowType!}" type="hidden" />
		<@htmlmacro.tableDetail divClass="table-form">
			<tr>
		        <th colspan="4" style="text-align:center;">教师请假申请</th>
		    </tr>
		    <tr>
		        <th style="width:20%"><span class="c-orange mr-5">*</span>请假时间：</th>
		        <td colspan="3" >
		        	<@htmlmacro.datepicker name="officeTeacherLeaveNh.beginTime" id="beginTime" class="input-txt" maxlength="19" style="width:280px;" msgName="开始时间" notNull="true" value="${((officeTeacherLeaveNh.beginTime)?string('yyyy-MM-dd HH:mm:ss'))?if_exists}" dateFmt="yyyy-MM-dd HH:mm:00"/>
		        	至
		        	<@htmlmacro.datepicker name="officeTeacherLeaveNh.endTime" id="endTime" class="input-txt" maxlength="19" style="width:280px;" msgName="结束时间" notNull="true" value="${((officeTeacherLeaveNh.endTime)?string('yyyy-MM-dd HH:mm:ss'))?if_exists}" dateFmt="yyyy-MM-dd HH:mm:00"/>
		        </td>
		    </tr>
		    <tr>
		       <th style="width:20%"><span class="c-orange mr-5">*</span>共计天数：</th>
		       <td style="width:30%">
		        	<input name="officeTeacherLeaveNh.days" id="days" type="text" class="input-txt" style="width:140px;" maxlength="5" dataType="float" maxValue="999" minValue="0.1" decimalLength="1" value="${(officeTeacherLeaveNh.days?string('0.#'))?if_exists}" msgName="共计天数" notNull="true" style="width:140px;"/>
		       </td>
		        <th style="width:20%"><span class="c-orange mr-5">*</span>申请人：</th>
		        <td style="width:30%">
		        	<@commonmacro.selectOneUser idObjectId="applyUserId" nameObjectId="userName" width=400 height=300 callback="doChangeFlow">
						<input type="hidden" id="applyUserId" name="officeTeacherLeaveNh.applyUserId" value="${officeTeacherLeaveNh.applyUserId!}"/> 
						<input type="text" id="userName" name="officeTeacherLeaveNh.userName" notNull="true" msgName="申请人" value="${officeTeacherLeaveNh.userName!}" class="input-txt fn-left" style="width:200px;" readonly="readonly"/>
			  		</@commonmacro.selectOneUser>
		        </td>
		    </tr>
		     <tr>
		        <th style="width:20%"><span class="c-orange mr-5">*</span>请假类型：</th>
		        <td colspan="3" style="width:80%">
	        	 	<@htmlmacro.select style="width:280px;" valName="officeTeacherLeaveNh.leaveTypeId" valId="leaveTypeId" msgName="请假类型" notNull="true" myfunchange="">
						<a val=""><span>---请选择---</span></a>
						<#list leaveTypeList as leaveType>
							<a val="${leaveType.id}" <#if officeTeacherLeaveNh.leaveTypeId?default('') == leaveType.id>class="selected"</#if> title="${leaveType.name!}"><span>${leaveType.name!}</span></a>
						</#list>
					</@htmlmacro.select>
		        </td>
		    </tr>
		    <tr>
		        <th style="width:20%">早自修调换人：</th>
		        <td style="width:30%">
		        	<input type="text" name="officeTeacherLeaveNh.morningChange" id="morningChange" value="${officeTeacherLeaveNh.morningChange!}" class="input-txt" msgName="早自修调换人" maxLength="100" style="width:140px;"/>
		        </td>
		        <th style="width:20%">晚自修调换人：</th>
		        <td style="width:30%">
		        	<input type="text" name="officeTeacherLeaveNh.nightChange" id="nightChange" value="${officeTeacherLeaveNh.nightChange!}" class="input-txt" msgName="晚自修调换人" maxLength="100" style="width:140px;"/>
		        </td>
		    </tr>
		    <tr>
		        <th style="width:20%">值周调换人：</th>
		        <td style="width:30%">
		        	<input type="text" name="officeTeacherLeaveNh.weekChange" id="weekChange" value="${officeTeacherLeaveNh.weekChange!}" class="input-txt" msgName="值周调换人" maxLength="100" style="width:140px;"/>
		        </td>
		        <th style="width:20%">代理班主任：</th>
		        <td style="width:30%">
		        	<input type="text" name="officeTeacherLeaveNh.actChargeTeacher" id="actChargeTeacher" value="${officeTeacherLeaveNh.actChargeTeacher!}" class="input-txt" msgName="代理班主任" maxLength="100" style="width:140px;"/>
		        </td>
		    </tr>
		    <tr>
		        <th>备注：</th>
		        <td colspan="3">
		        	<textarea name="officeTeacherLeaveNh.remark" id="remark" cols="70" rows="4" class="text-area my-5" style="width:80%;padding:5px 1%;height:50px;" msgName="备注" maxLength="200">${officeTeacherLeaveNh.remark!}</textarea>
		        </td>
		    </tr>
		    <tr>
	    	   <th colspan="1" >
	    	   		请假流程选择：
	    	   </th>
	    	   <td colspan="3">
	    	   	<div id="flowDiv">
		    	   	<@htmlmacro.select style="width:280px;" valName="officeTeacherLeaveNh.flowId" valId="flowId" myfunchange="flowChange" msgName="请假流程">
						<#if flowList?exists && flowList?size gt 0>
							<#list flowList as flow>
								<a val="${flow.flowId!}"<#if officeTeacherLeaveNh.flowId?exists && officeTeacherLeaveNh.flowId==flow.flowId>class="selected"</#if>  ><span>${flow.flowName!}</span></a>
							</#list>
						</#if>
					</@htmlmacro.select>
				<div>
	    	   </td>
		    </tr>   
		    <tr>
		    	<td colspan="4" class="td-opt">
		    	    <a class="abtn-blue-big" href="javascript:void(0);" onclick="doSave() ">保存</a>
		    	    <a class="abtn-blue-big" href="javascript:void(0);" onclick="doSubmit()">提交审核</a>
				    <a class="abtn-blue-big ml-5" href="javascript:void(0);" onclick="doSearch();">返回</a>
		        </td>
		    </tr>
		</@htmlmacro.tableDetail>
	</form>
	<div id="flowShow"  class="docReader my-20" style="height:660px;">
		<p >请选择流程</p>
	</div>
	<script>
	
		function doChangeFlow(){
			var applyUserId = $("#applyUserId").val();
			if(applyUserId!=""){
				var url='${request.contextPath}/office/teacherLeaveNh/teacherLeaveNh-flowDiv.action?applyUserId='+applyUserId;
				load("#flowDiv",url);
			}
		}
		
		//流程选择
		function flowChange(){
			var flowId= $("#flowId").val();
			if(flowId!=""){
				load("#flowShow","${request.contextPath}/jbmp/editor/wfPreview.action?subsystemId=70&id="+flowId);
			}
		}
		
		$(document).ready(function(){
			vselect();
			var flowId= $("#flowId").val();
			load("#flowShow","${request.contextPath}/jbmp/editor/wfPreview.action?subsystemId=70&id="+flowId);
		});
		
		var isSubmit =false;
		function doSave(){
			if(isSubmit){
				return;
			}
			if(!checkAllValidate("#teacherLeaveNhForm")){
				return;
			}
		    isSubmit = true;
			var options = {
		       target : '#teacherLeaveNhForm',
		       url:'${request.contextPath}/office/teacherLeaveNh/teacherLeaveNh-saveTeacherLeaveNh.action', 
		       success : showReply,
		       dataType : 'json',
		       clearForm : false,
		       resetForm : false,
		       type : 'post'
		    };
		    $('#teacherLeaveNhForm').ajaxSubmit(options);
		}
		
		function doSubmit(){
			var flowId= $("#flowId").val();
			if(!flowId||flowId==""){
				 showMsgError("要提交审核,必须选择一个流程");
				 return;
			}
			if(isSubmit){
				return;
			}
			if(!checkAllValidate("#teacherLeaveNhForm")){
				return;
			}
			var options = {
		       url:'${request.contextPath}/office/teacherLeaveNh/teacherLeaveNh-submitTeacherLeaveNh.action', 
		       success : showReply,
		       dataType : 'json',
		       clearForm : false,
		       resetForm : false,
		       type : 'post'
		    };
		    isSubmit = true;
		    $('#teacherLeaveNhForm').ajaxSubmit(options);
		}
		
		function showReply(data){
			if(!data.operateSuccess){
			   if(data.errorMessage!=null&&data.errorMessage!=""){
				   showMsgError(data.errorMessage);
				   isSubmit = false;
				   return;
			   }
			}else{
				showMsgSuccess(data.promptMessage,"",doSearch);
				return;
			}
		}
	</script>
</@htmlmacro.moduleDiv >