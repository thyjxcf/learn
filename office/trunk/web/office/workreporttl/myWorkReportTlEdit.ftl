<#import "/common/htmlcomponent.ftl" as common>
<#import "/common/commonmacro.ftl" as commonmacro>
<@common.moduleDiv>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
<script type="text/javascript" charset="utf-8" src="${request.contextPath}/static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${request.contextPath}/static/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${request.contextPath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/ueditor/themes/default/css/ueditor.css"/>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/ueditor/themes/iframe.css"/>
<script>
	function saveOfficeWorkReport(state){
		if(!isActionable("#btnPass")){
			return;
		}
		if(!isActionable("#btnReject")){
			return;
		}
		$("#state").val(state);
	  	if(!checkAllValidate()){
	  		return;
	  	}
		
		$("#btnPass").attr("class", "abtn-unable");
		$("#btnReject").attr("class", "abtn-unable");
		$.ajax({
			type: "POST",
			url: "${request.contextPath}/office/workreporttl/workReportTl-myWorkReportSave.action",
			data: $('#officeWorkReportform').serialize(),
			success: function(data){
				$("#btnPass").attr("class", "abtn-blue");
				$("#btnReject").attr("class", "abtn-blue");
				if(!data.operateSuccess){
			  		 if(data.errorMessage!=null&&data.errorMessage!=""){
				  		 showMsgError(data.errorMessage);
				  		 isSubmit = false;
				  		 return;
			  		 }
				}else{
					showMsgSuccess(data.promptMessage,"", function(){
			    		doQueryChange();
					});
					return;
				}
			},
			dataType: "json",
			error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
		});
	};

</script>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
<div class="pub-table-wrap">
<form action="" method="post" name="officeWorkReportform" id="officeWorkReportform">
<input type="hidden" id="id" name="officeWorkReportTl.id" value="${officeWorkReportTl.id?default('')}"/>
<input type="hidden" id="unitId" name="officeWorkReportTl.unitId" value="${officeWorkReportTl.unitId?default('')}"/>
<input type="hidden" id="createUserId" name="officeWorkReportTl.createUserId" value="${officeWorkReportTl.createUserId?default('')}"/>
<input type="hidden" id="unitClass" name="officeWorkReportTl.unitClass" value="${officeWorkReportTl.unitClass?default('')}"/>
<input type="hidden" id="parentUnitId" name="officeWorkReportTl.parentUnitId" value="${officeWorkReportTl.parentUnitId?default('')}"/>
<input type="hidden" id="unitOrderId" name="officeWorkReportTl.unitOrderId" value="${officeWorkReportTl.unitOrderId?default('')}"/>
<input type="hidden" id="teacherOrderId" name="officeWorkReportTl.teacherOrderId" value="${officeWorkReportTl.teacherOrderId?default('')}"/>
<input type="hidden" id="state" name="officeWorkReportTl.state"/>
<table border="0" cellspacing="0" cellpadding="0" class="table-edit mt-5">
        <tr>
            <th><span class="c-red">*</span> 学年：</th>
            <td>
            	<input type="text" id="year" name="officeWorkReportTl.year" value="${year?default('')}" style="width:80px;height:20px;"/>
	        </td>
        </tr>
        <tr>
        	<th><span class="c-red">*</span> 学期：</th>
        	<td>
        	<#if officeWorkReportTl.id?exists>
    		<div class="select_box fn-left">
				<@common.select style="width:80px;" valName="officeWorkReportTl.semester" valId="semester" myfunchange="">
					<a val="1"  <#if officeWorkReportTl.semester?default(1)==1>class="selected"</#if>><span>第一学期</span></a>
					<a val="2"  <#if officeWorkReportTl.semester?default(1)==2>class="selected"</#if>><span>第二学期</span></a>
				</@common.select>
			</div>
			<#else>
    		<div class="select_box fn-left">
				<@common.select style="width:80px;" valName="officeWorkReportTl.semester" valId="semester" myfunchange="">
					<a val="1"  <#if semester?default("1")=="1">class="selected"</#if>><span>第一学期</span></a>
					<a val="2"  <#if semester?default("1")=="2">class="selected"</#if>><span>第二学期</span></a>
				</@common.select>
			</div>
			</#if>
        	</td>
        </tr>
        <tr>
        	<th><span class="c-red">*</span> 周次：</th>
        	<td>
        	<#if officeWorkReportTl.id?exists>
        	<div class="sendMsg-form">
				<@common.select style="width:80px;" valId="week" valName="officeWorkReportTl.week" txtId="searchWeekTxt" myfunchange="" >
					<#list weekTimeList as item>
						<a val="${item}" <#if item == officeWorkReportTl.week+''>class="selected"</#if>>第${item!}周</a>
					</#list>
				</@common.select>
			</div>
			<#else>
        	<div class="sendMsg-form">
				<@common.select style="width:80px;" valId="week" valName="officeWorkReportTl.week" txtId="searchWeekTxt" myfunchange="" >
					<#list weekTimeList as item>
						<a val="${item}" <#if item == week?default('')>class="selected"</#if>>第${item!}周</a>
					</#list>
				</@common.select>
			</div>
			</#if>
        	</td>
        </tr>
        <tr>
        	<th><span class="c-red">*</span> 汇报内容：</th>
            <td>
            	<textarea id="content" name="officeWorkReportTl.content" type="text/plain" style="width:850px;height:200px;" maxlength="1500" msgName="汇报内容" notNull="true">${officeWorkReportTl.content!}</textarea>
	        </td>
        </tr>
        <tr>
        	<th>&nbsp;</th>
        	<td>
        		<#--<#if officeWorkReport.state?exists && officeWorkReport.state == '1'>-->
        		<#--	<a href="javascript:void(0);" id="btnPass" class="abtn-blue" onclick="saveOfficeWorkReport(1);">保存</a>-->
        		<#--<#else>-->
            		<a href="javascript:void(0);" id="btnPass" class="abtn-blue" onclick="saveOfficeWorkReport(1);">保存</a>
	            	<a href="javascript:void(0);" id="btnReject" class="abtn-blue" onclick="saveOfficeWorkReport(2);">提交</a>
            	<#--</#if>-->
            	<a href="javascript:void(0);" class="abtn-blue" onclick="goBack();">取消</a>
            </td>
        </tr>
    </table>
</form>
<script>
	function goBack(){
		doQueryChange();
	}
	
</script>
</@common.moduleDiv>