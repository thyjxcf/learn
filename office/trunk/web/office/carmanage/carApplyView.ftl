<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>

<#assign PASS = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_PASS") >
<#assign UNPASS = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_NOPASS") >

<@htmlmacro.moduleDiv titleName="申请信息">
<div id="carApplyEditContainer">
<@htmlmacro.tableDetail divClass="table-form">

        <tr>
            <th colspan="4" style="text-align:center;">申请信息</th>
        </tr>
        <tr>
        	<th style="width:20%">乘车联系人：</th>
            <td style="width:30%">
            	${officeCarApply.linkUserName!}
            </td>
            <th style="width:20%">手机号码：</th>
            <td style="width:30%">
            	${officeCarApply.mobilePhone!}
            </td>
        </tr>
        <tr>
            <th>乘车人数：</th>
            <td>
            	${officeCarApply.personNumber!}
            </td>
            <th>用车时间：</th>
            <td>
            	${(officeCarApply.useTime?string('yyyy-MM-dd HH:mm'))!}  (${officeCarApply.xinqi!})
            </td>
        </tr>
        <tr>
            <th>目的地：</th>
            <td>
            	${officeCarApply.carLocation!}
            </td>
            <th>是否往返：</th>
            <td>
            	<#if officeCarApply.isGoback?exists && officeCarApply.isGoback>
            		是
            	<#else>
            		否
            	</#if>
            </td>
        </tr>
        <tr>
            <th>是否需要候车：</th>
            <td>
            	<#if officeCarApply.isNeedWaiting?exists && officeCarApply.isNeedWaiting>
            		是
            	<#else>
            		否
            	</#if>
            </td>
            <th>接返时间：</th>
            <td>
            	${(officeCarApply.waitingTime?string('yyyy-MM-dd HH:mm'))!} <#if officeCarApply.isNeedWaiting?exists && officeCarApply.isNeedWaiting>(${officeCarApply.xinqi2!})</#if>
            </td>
        </tr>
        <tr>
            <th>用车事由：</th>
            <td colspan="3" style="word-break:break-all; word-wrap:break-word;">
            	${officeCarApply.reason!}
            </td>
        </tr>
        <#if officeCarApply.state == PASS>
        	<tr>
		        <th colspan="4" style="text-align:center;">单位审核</th>
		    </tr>
		    <tr>
		        <th>审核人：</th>
		        <td colspan="3">
		        	${officeCarApply.auditUserName!}
		        </td>
		    </tr>
		     <tr>
		       <th>车辆范围：</th>
		        <td>
		        	${officeCarApply.areaStr?default("")}
		        </td>
		        <th>出车补贴：</th>
		        <td>
				${officeCarApply.areaSubsidy?string('0.##')}
		        </td>
		    </tr>
		    <tr>
		        <th>是否加班：</th>
		        <td colspan="3">
		        <#if applyType == '2'>
		        	<@htmlmacro.select style="width:150px;" valName="officeCarApply.isOvertime" valId="isOvertime" myfunchange="doChangeOvertime">
					<a val="true" <#if officeCarApply.isOvertime?default(false)> class="selected"</#if>><span>是</span></a>
		          		<a val="false" <#if !officeCarApply.isOvertime?default(false)> class="selected"</#if>><span>否</span></a>
				</@htmlmacro.select>
			<#else>
				<#if officeCarApply.isOvertime?exists && officeCarApply.isOvertime>
	            		是
	            	<#else>
	            		否
	            	</#if>
			</#if>
		        </td>
		    </tr>
		    <#if applyType == '2'>
		    <tr id="overtimeDiv" <#if !officeCarApply.isOvertime?default(false)>style="display:none;"</#if>>
		        <th>加班天数：</th>
		        <td colspan="3">
		        	<input id="overtimeNumber" type="text" class="input-txt" style="width:150px;" maxLength="5" dataType="float" maxValue="999" minValue="0" msgName="加班天数" value="${officeCarApply.overtimeNumber?string('0.#')}" />
		        </td>
		    </tr>
		    <#else>
		    		<#if officeCarApply.isOvertime?exists && officeCarApply.isOvertime>
		    		<tr>
		        <th>加班天数：</th>
		        <td colspan="3">
		        ${officeCarApply.overtimeNumber?string('0.#')}
		        </td>
		    </tr>
	            	</#if>
		    </#if>
		    <tr>
		        <th>车牌号码：</th>
		        <td style="word-break:break-all; word-wrap:break-word;">
		        	${officeCarApply.carNumber!}
		        </td>
		        <th>驾驶员：</th>
		        <td style="word-break:break-all; word-wrap:break-word;">
		        	${officeCarApply.driverName!}
		        </td>
		    </tr>
		    <tr>
		        <th>是否通过：</th>
		        <td colspan="3">
		        	通过
		        </td>
		    </tr>
		    <tr>
		        <th>审核意见：</th>
		        <td colspan="3" style="word-break:break-all; word-wrap:break-word;">
		        	${officeCarApply.remark!}
		        </td>
		    </tr>
        <#elseif officeCarApply.state == UNPASS>
        	<tr>
		        <th colspan="4" style="text-align:center;">单位审核</th>
		    </tr>
		    <tr>
		        <th>审核人：</th>
		        <td>
		        	${officeCarApply.auditUserName!}
		        </td>
		        <th>是否通过：</th>
		        <td>
		        	不通过
		        </td>
		    </tr>
		    <tr>
		        <th>审核意见：</th>
		        <td colspan="3" style="word-break:break-all; word-wrap:break-word;">
		        	${officeCarApply.remark!}
		        </td>
		    </tr>
        </#if>
        <tr>
        	<td colspan="4" class="td-opt">
        		<#if officeCarApply.state == PASS && applyType == '2'>
        		    <a class="abtn-blue-big ml-5" href="javascript:void(0);" onclick="save();">保存</a>
        		</#if>
			    <a class="abtn-blue-big ml-5" href="javascript:void(0);" onclick="back();">返回</a>
            </td>
        </tr>
</@htmlmacro.tableDetail>
</div>
<script type="text/javascript">
function back(){
	doSearch();
}
var flag = false;
function save(){
	if(flag){
		return false;
	}
	var isOvertime = $("#isOvertime").val();
	var overtimeNumber = $("#overtimeNumber").val();
	if(!checkAllValidate("#carApplyEditContainer")){
		return;
	}
	$.ajax({
			type: "POST",
			url: "${request.contextPath}/office/carmanage/carmanage-saveOvertime.action",
			data: $.param({"officeCarApply.id" : "${officeCarApply.id}","officeCarApply.isOvertime":isOvertime,"officeCarApply.overtimeNumber":overtimeNumber}),
			success: function(data){
				if(data && data!=''){
					showMsgError(data);
				}else{
					showMsgSuccess("保存成功！");
					flag = true;
					doSearch();
				}
			},
			dataType: "json",
			error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
		});
}
function doChangeOvertime(){
	var isOvertime = $("#isOvertime").val();
  	if('true'==isOvertime){
  		$("#overtimeDiv").show();
  	}else{
  		$("#overtimeDiv").hide();
  	}
}
</script>

</@htmlmacro.moduleDiv>