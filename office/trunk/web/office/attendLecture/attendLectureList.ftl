<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="">
<script src="${request.contextPath}/static/js/validate.js"></script>
<div class="pub-table-wrap">
	<div class="pub-table-inner">
	<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
	<div class="query-part">
		<div class="query-tt ml-10">
			<span class="fn-left">审核状态：</span>
		</div>
		<div class="select_box fn-left mr-10">
		<@htmlmacro.select style="width:150px;" valId="searchType" valName="searchType" myfunchange="doSearch" >
			<a val="" ><span>全部</span></a>
    		<a val="1" <#if searchType?default('')=='1'>class="selected"</#if>><span>待提交</span></a>
    		<a val="2" <#if searchType?default('')=='2'>class="selected"</#if>><span>审核中</span></a>
    		<a val="3" <#if searchType?default('')=='3'>class="selected"</#if>><span>审核通过</span></a>
    		<a val="4" <#if searchType?default('')=='4'>class="selected"</#if>><span>审核不通过</span></a>
		</@htmlmacro.select></div>
		<div class="query-tt ml-10">
			<span class="fn-left">听课时间：</span>
		</div>
		<div class="fn-left">
		<@htmlmacro.datepicker class="input-txt" style="width:100px;" id="startTime" value="${((startTime)?string('yyyy-MM-dd'))?if_exists}"/>
		&nbsp;&nbsp;至&nbsp;&nbsp;
		<@htmlmacro.datepicker class="input-txt" style="width:100px;" id="endTime" value="${((endTime)?string('yyyy-MM-dd'))?if_exists}"/>
		</div>
		<a href="javascript:void(0)" onclick="doSearch();" class="abtn-blue fn-left ml-20">查找</a>
		<a href="javascript:void(0);" onclick="doAdd();" class="abtn-orange-new fn-right mr-10">听课登记</a>
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
    	<th width="12%">授课老师</th>
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
		    	<td title="${item.teacherName?default('')}"><@htmlmacro.cutOff str="${item.teacherName?default('')}" length=8/></td>
		    	<td >
		    		<#if item.state=='1'>
		    			未提交	
		    		<#elseif item.state=='2'>
		    			审核中
		    		<#elseif item.state=='3'>
		    			审核结束-通过
		    		<#elseif item.state=='4'>
		    			审核结束-未通过
		    		</#if>
		    	</td>
		    	<td class="t-center">
		    		<#if item.state=='1'>
	    			<a href="javascript:void(0);" onclick="doEdit('${item.id!}');"><img src="${request.contextPath}/static/images/icon/edit.png" alt="编辑"></a>
		    		<a href="javascript:void(0);" onclick="doDelete('${item.id!}');"><img src="${request.contextPath}/static/images/icon/del2.png" alt="删除"></a>
		    		<#elseif item.state=='2'>
		    		<a href="javascript:void(0);" onclick="doInfo('${item.id!}');"><img src="${request.contextPath}/static/images/icon/view.png" alt="查看"></a>
		    		<#elseif item.state=='3'>
		    		<a href="javascript:void(0);" onclick="doInfo('${item.id!}');"><img src="${request.contextPath}/static/images/icon/view.png" alt="查看"></a>
		    		<#elseif item.state=='4'>
		    		<a href="javascript:void(0);" onclick="doInfo('${item.id!}');"><img src="${request.contextPath}/static/images/icon/view.png" alt="查看"></a>
		    		<a href="javascript:void(0);" onclick="doDelete('${item.id!}');"><img src="${request.contextPath}/static/images/icon/del2.png" alt="删除"></a>
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
	function doAdd(){
		load("#showListDiv","${request.contextPath}/office/attendLecture/attendLecture-edit.action?searchType="+$("#searchType").val()+"&startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val());
	}
	function doSearch(){
		if(compareDate($("#startTime").val(), $("#endTime").val())>0){
			showMsgError("听课时间有误，请修改");
			return;
		}
		load("#showListDiv","${request.contextPath}/office/attendLecture/attendLecture-list.action?searchType="+$("#searchType").val()+"&startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val());
	}
	function doEdit(id){
		load("#showListDiv","${request.contextPath}/office/attendLecture/attendLecture-edit.action?id="+id+"&searchType="+$("#searchType").val()+"&startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val());
	}
	function doInfo(id){
		load("#showListDiv","${request.contextPath}/office/attendLecture/attendLecture-edit.action?id="+id+"&viewOnly=true"+"&searchType="+$("#searchType").val()+"&startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val());
	}
	function doDelete(id){
		if(showConfirm("确定要删除？")){
			$.getJSON("${request.contextPath}/office/attendLecture/attendLecture-delete.action",{'id':id},function(data){
				if(data.operateSuccess){
					showMsgSuccess(data.promptMessage,"",function(){
						doSearch();
					});
				}else{
					showMsgError(data.errorMessage);
					return;
				}
		   }).error(function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);});
		}
	}
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>