<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.moduleDiv titleName="">
<div class="pub-table-wrap">
	<div class="pub-table-inner">
	<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
	<div class="query-part">
		<div class="query-tt ml-10">
			<span class="fn-left">审核状态：</span>
		</div>
		<div class="select_box fn-left mr-10">
		<@htmlmacro.select style="width:150px;" valId="searchType" valName="searchType" myfunchange="doSearch" >
			<a val="0" <#if searchType?default('')=='0'>class="selected"</#if>><span>待我审核</span></a>
			<a val="3" <#if searchType?default('')=='3'>class="selected"</#if>><span>我已审核</span></a>
    		<a val="1" <#if searchType?default('')=='1'>class="selected"</#if>><span>已审核</span></a>
		</@htmlmacro.select></div>
		<div class="query-tt ml-10">
			<span class="fn-left">听课时间：</span>
		</div>
		<div class="fn-left">
		<@htmlmacro.datepicker class="input-txt" style="width:100px;" id="startTime" value="${((startTime)?string('yyyy-MM-dd'))?if_exists}"/>
		&nbsp;&nbsp;至&nbsp;&nbsp;
		<@htmlmacro.datepicker class="input-txt" style="width:100px;" id="endTime" value="${((endTime)?string('yyyy-MM-dd'))?if_exists}"/>
		</div>
		<div class="query-tt ml-10">
			<span class="fn-left">提交人：</span>
		</div>
		<div class="fn-left">
			<input type="input" class="input-txt" style="width:100px;" id="applyUserName" value="${applyUserName!}">
		</div>
		
		<a href="javascript:void(0)" onclick="doSearch();" class="abtn-blue fn-left ml-20">查找</a>
		<div class="fn-clear"></div>
	</div>
	</div>
	</div>
</div>
<@htmlmacro.tableList class="public-table table-list table-list-edit">
  	<tr>
  		<th width="8%">序号</th>
    	<th width="8%">听课时间</th>
    	<th width="10%">课次</th>
    	<th width="10%">班级</th>
    	<th width="12%">学科</th>
    	<th width="">课题</th>
    	<th width="12%">提交人</th>
    	<th width="10%">状态</th>
    	<th width="10%" class="t-center">操作</th>
    </tr>
    <#if officeAttendLectureList?exists && officeAttendLectureList?size gt 0>
    	<#list officeAttendLectureList as item>
    		<tr>
    			<td>${item_index+1}</td>
    			<td >${(item.attendDate?string('yyyy-MM-dd'))?if_exists}</td>
		    	<td >${appsetting.getMcodeName("DM-TKSD",item.attendPeriod?default(''))}${appsetting.getMcodeName("DM-TKJC",item.attendPeriodNum?default(''))}</td>
		    	<td >${item.className?default('')}</td>
		    	<td title="${item.subjectName?default('')}"><@htmlmacro.cutOff str="${item.subjectName?default('')}" length=8/></td>
		    	<td title="${item.projectName?default('')}"><@htmlmacro.cutOff str="${item.projectName?default('')}" length=15/></td>
		    	<td title="${item.applyUserName?default('')}"><@htmlmacro.cutOff str="${item.applyUserName?default('')}" length=8/></td>
		    	<td >
		    		<#if item.state=='2'>
		    			审核中
		    		<#elseif item.state=='3'>
		    			审核结束-通过
		    		<#elseif item.state=='4'>
		    			审核结束-未通过
		    		</#if>
		    	</td>
		    	<td class="t-center">
		    		<#if item.state=='2' && item.taskId??>
		    		<a href="javascript:void(0);" onclick="doAudit('${item.id!}', '${item.taskId}');"><img src="${request.contextPath}/static/images/icon/edit.png" alt="审核"></a>
		    		<#else>
		    		<a href="javascript:void(0);" onclick="doInfo('${item.id!}');"><img src="${request.contextPath}/static/images/icon/view.png" alt="查看"></a>
		    		</#if>
		    		
		    	</td>
    		</tr>
    	</#list>
    <#else>
    	<tr>
    		<td colspan='88'><p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td>
	 	</tr>
    </#if>
</@htmlmacro.tableList>
<#if officeAttendLectureList?exists && officeAttendLectureList?size gt 0>
<@htmlmacro.Toolbar container="#showListDiv">
</@htmlmacro.Toolbar>
</#if>
<script>
	function doSearch(){
		if(compareDate($("#startTime").val(), $("#endTime").val())>0){
			showMsgError("听课时间有误，请修改");
			return;
		}
		load("#showListDiv","${request.contextPath}/office/attendLecture/attendLecture-auditList.action?searchType="+$("#searchType").val()+"&startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val()+"&applyUserName="+encodeURI($("#applyUserName").val().trim()));
	}
	function doAudit(id,taskId){
		load("#showListDiv","${request.contextPath}/office/attendLecture/attendLecture-auditEdit.action?officeAttendLecture.id="+id+"&taskId="+taskId+"&searchType="+$("#searchType").val()+"&startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val()+"&applyUserName="+$("#applyUserName").val());
	}
	function doInfo(id){
		load("#showListDiv","${request.contextPath}/office/attendLecture/attendLecture-auditDetail.action?id="+id+"&viewOnly=true"+"&searchType="+$("#searchType").val()+"&startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val()+"&applyUserName="+$("#applyUserName").val());
	}
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>