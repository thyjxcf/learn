<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#include "/common/handlefielderror.ftl">
<@htmlmacro.moduleDiv titleName="">
<div class="query-builder-no pt-20">
	<div class="query-part fn-rel fn-clear promt-div">
		<span id="resourceofficedocTypeList" class="user-sList user-sList-radio">
	    	<span <#if applyStatus?default(0)==0> class="current"</#if> key="0">全部</span>
	    	<span <#if applyStatus?default(0)==1> class="current"</#if> key="1">待提交</span>
	    	<span <#if applyStatus?default(0)==2> class="current"</#if> key="2">审核中</span>
	    	<span <#if applyStatus?default(0)==3> class="current"</#if> key="3">审核结束-通过</span>
	    	<span <#if applyStatus?default(0)==4> class="current"</#if> key="4">审核结束-不通过</span>
	    </span>
	    <div class="fn-right ml-10">
	    	<a href="javascript:doAdd()" class="abtn-orange-new fn-right" id="addNew">新建请假申请</a>
	    </div>
    </div>
</div>
<@htmlmacro.tableList class="public-table table-list table-list-edit">
  	<tr>
  		<th >序号</th>
    	<th >请假起止时间</th>
    	<th >共计天数</th>
    	<th >请假类型</th>
    	<th >请假状态</th>
    	<th class="t-center">操作</th>
    </tr>
    <#if officeTeacherLeaveList?exists && officeTeacherLeaveList?size gt 0>
    	<#list officeTeacherLeaveList as leave>
    		<tr>
    			<td>${leave_index+1}</td>
    			<td >${(leave.leaveBeignTime?string('yyyy-MM-dd'))?if_exists}至${(leave.leaveEndTime?string('yyyy-MM-dd'))?if_exists}</td>
		    	<td >${leave.days?string('0.#')!}天</td>
		    	<td >${appsetting.getMcodeName("DM-QJLX",leave.leaveType?default("9"))}</td>
		    	<td ><#if leave.applyStatus==1>
		    			未提交	
		    		<#elseif leave.applyStatus==2>
		    			审核中
		    		<#elseif leave.applyStatus==3>
		    			审核结束-通过
		    		<#elseif leave.applyStatus==4>
		    			审核结束-未通过
		    		</#if>
		    	</td>
		    	<td class="t-center">
		    		<#if leave.applyStatus==1>
	    			<a href="javascript:void(0);" onclick="doEdit('${leave.id!}');">编辑</a>
		    		<a href="javascript:void(0);" onclick="doDelete('${leave.id!}');">删除</a>
		    		<#elseif leave.applyStatus==2>
		    		<a href="javascript:void(0);" onclick="doInfo('${leave.id!}');">查看</a>
	    			<a href="javascript:void(0);" onclick="doRevoke('${leave.id!}');">撤销</a>
		    		<#elseif leave.applyStatus==3>
		    		<a href="javascript:void(0);" onclick="doInfo('${leave.id!}');">查看</a>
		    		<a href="javascript:void(0);" onclick="printDiv('${leave.id!}');">打印</a>
		    		<#elseif leave.applyStatus==4>
		    		<a href="javascript:void(0);" onclick="doInfo('${leave.id!}');">查看</a>
		    		<a href="javascript:void(0);" onclick="doDelete('${leave.id!}');">删除</a>
		    		</#if>
		    		
		    	</td>
    		</tr>
    	</#list>
    <#else>
    	<tr>
    		<td colspan='10'><p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td>
	 	</tr>
    </#if>
</@htmlmacro.tableList>
<#if officeTeacherLeaveList?exists && officeTeacherLeaveList?size gt 0>
	<@htmlmacro.Toolbar container="#showListDiv">
	</@htmlmacro.Toolbar>
</#if> 

<div id="printDiv" style="display:none">
	 <@htmlmacro.tableDetail divClass="table-form">
		<tr>
	        <th colspan="4" style="text-align:center;">教师请假</th>
	    </tr>
	    <tr>
	        <th  style="width:20%;"><span class="c-orange mr-5"></span>请假时间：</th>
	        <td style="width:30%;" id="leaveTime">
	        </td>
	        <th  style="width:20%;"><span class="c-orange mr-5"></span>提交时间：</th>
	        <td style="width:30%;" id="createTime">
	        </td>
	    </tr>
	    <tr>
	       <th style="width:20%"><span class="c-orange mr-5"></span>共计天数：</th>
	       <td style="width:30%" id="days">
	       </td>
	        <th style="width:20%">申请人：</th>
	        <td style="width:30%" id="userName">
	        </td>
	    </tr>
	     <tr>
	        <th style="width:20%">请假类型：</th>
	        <td style="width:30%" id="leaveType">
	        </td>
	        <th style="width:20%">提交人：</th>
	        <td style="width:30%" id="createUserName">
	        </td>
	    </tr>
	     <tr>
	        <th>请假原因：</th>
	        <td colspan="3" id="leaveReason">
	        </td>
	    </tr>
	    <tr>
	        <th>通知人员：</th>
	        <td colspan="3" id="noticePersonNames">
	        </td>
	    </tr>
	    <tr>
	        <th>附件：</th>
	        <td colspan="3" id="fileName">
	        </td>
	    </tr>
	</@htmlmacro.tableDetail>
    <br>
	<div class="fw-edit mt-10">
    	<p class="tt">流程意见</p>
	        <div id="printFlow" class="fw-item-wrap">
        </div>
    </div>
</div> 
<script>
	$(document).ready(function(){
		vselect();
		$('.user-sList-radio span').click(function(){
			$(this).addClass('current').siblings('span').removeClass('current');
			var status=$('#resourceofficedocTypeList.user-sList-radio span.current').attr("key");
			var str = "?applyStatus="+status;
			load("#showListDiv","${request.contextPath}/office/teacherLeave/teacherLeave-applyList.action"+str);
		});
	});
	
	function doAdd(){
		load("#showListDiv","${request.contextPath}/office/teacherLeave/teacherLeave-applyTeacherLeave.action");
	}
	
	function doEdit(id){
		load("#showListDiv","${request.contextPath}/office/teacherLeave/teacherLeave-applyTeacherLeave.action?id="+id);
	}
	function printDiv(id){
		jQuery.ajax({
			url:"${request.contextPath}/office/teacherLeave/teacherLeave-printTeacherLeave.action",
		   	type:"POST",
		   	dataType:"json",
		   	data:{"id":id},
		   	async:false,
		   	error:function(){
		   		showMsgError("打印出错！");
		   	},
		   	success:function(data){
		   		printValueDiv(data);
		   	}
		});
	}
	function printValueDiv(data){
		var teacherLeave=data["teacherLeave"];
		if(teacherLeave!=null){
			document.getElementById("leaveTime").innerHTML=teacherLeave.leaveBeginTimeStr+"至"+teacherLeave.leaveEndTimeStr;
			document.getElementById("days").innerHTML=teacherLeave.days;
			document.getElementById("userName").innerHTML=teacherLeave.applyUserName;
			document.getElementById("leaveType").innerHTML=teacherLeave.leaveType;
			document.getElementById("createTime").innerHTML=teacherLeave.createTimeStr;
			document.getElementById("createUserName").innerHTML=teacherLeave.createUserName;
			document.getElementById("leaveReason").innerHTML=teacherLeave.leaveReason;
			document.getElementById("noticePersonNames").innerHTML=teacherLeave.noticePersonNames;
			if(teacherLeave.fileNames!=null){
				var fileName="";
				var lengthLeave=teacherLeave.fileNames.length;
				for(var j=0;j<lengthLeave;j++){
					if(j==lengthLeave-1){
						fileName+=teacherLeave.fileNames[j];
					}else{
						fileName+=teacherLeave.fileNames[j]+"    ";
					}
				}
				document.getElementById("fileName").innerHTML=fileName;
			}
			if(teacherLeave.flowId!=null && teacherLeave.flowId!='1'){
				var flowHtml="";
				for(var i=0;i<teacherLeave.hisTaskList.length;i++){
					var task=teacherLeave.hisTaskList[i];
					flowHtml+='<div class="fw-item fn-clear"> <p class="tit fn-clear"><span class="num">';
					flowHtml+=(i+1);
					flowHtml+='</span><span class="pl-5">';
					flowHtml+=task.taskName;
					flowHtml+='</span></p><p class="name">负责人：';
					flowHtml+=task.assigneeName;
					flowHtml+='</p> <div class="fn-clear"></div><div class="des" >';
					if(task.comment.commentType==1){
						flowHtml+=task.comment.textComment;
					}
					flowHtml+='</div><p class="date">';
					flowHtml+='';
					flowHtml+='</p></div>';
				}
				$("#printFlow").html(flowHtml);
			}
		}
		LODOP=getLodop();
		LODOP.ADD_PRINT_HTM("10mm","5mm","RightMargin:5mm","BottomMargin:10mm",getPrintContent(jQuery('#printDiv')));
	  	LODOP.PREVIEW();
	}
	
	function doInfo(id){
		load("#showListDiv","${request.contextPath}/office/teacherLeave/teacherLeave-applyDetail.action?fromTab=1&&id="+id);
	}
	
	function doDelete(id){
		if(showConfirm("确定要删除该请假申请")){
			$.getJSON("${request.contextPath}/office/teacherLeave/teacherLeave-deleteLeave.action",{id:id},function(data){
				if(data.operateSuccess){
					showMsgSuccess(data.promptMessage,"",onApplyLeave);
				}else{
					showMsgError(data.errorMessage);
					return;
				}
		   }).error(function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);});
		}
	}
	//撤销功能暂时没用到
	function doRevoke(id){
		if(showConfirm("确定要撤销该请假申请")){
			$.getJSON("${request.contextPath}/office/teacherLeave/teacherLeave-revokeLeave.action",{id:id},function(data){
				if(data.operateSuccess){
					showMsgSuccess(data.promptMessage,"",onApplyLeave);
				}else{
					showMsgError(data.errorMessage);
					return;
				}
		   }).error(function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);});
		}
	}
	
</script>
</@htmlmacro.moduleDiv>