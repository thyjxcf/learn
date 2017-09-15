<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>

<@htmlmacro.moduleDiv titleName="审核信息">
<div id="carCancelAuditEditContainer">
<form name="carCancelAuditForm" id="carCancelAuditForm">
<input type="hidden" name="officeCarApply.id" value="${officeCarApply.id!}"/>
<input type="hidden" name="officeCarApply.state" id="state"/>
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
			<#if officeCarApply.isOvertime?exists && officeCarApply.isOvertime>
        		是
        	<#else>
        		否
        	</#if>
	        </td>
	    </tr>
		<#if officeCarApply.isOvertime?exists && officeCarApply.isOvertime>
		<tr>
	        <th>加班天数：</th>
	        <td colspan="3">
	        ${officeCarApply.overtimeNumber?string('0.#')}
	        </td>
	    </tr>
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
	    <#if applyType=="2">
	    <tr>
	        <th><span class="c-orange mr-5">*</span>审核意见：</th>
	        <td colspan="3">
	        	<textarea name="officeCarApply.remark" id="remark" cols="70" rows="4" class="text-area my-5" style="width:80%;padding:5px 1%;height:50px;" notNull="true" msgName="审核意见" maxLength="200">同意撤销</textarea>
	        </td>
	    </tr>
	    <#else>
	    <tr>
	        <th>审核意见：</th>
	        <td colspan="3" style="word-break:break-all; word-wrap:break-word;">
	        	${officeCarApply.remark!}
	        </td>
	    </tr>
	    </#if>
        <tr>
        	<td colspan="4" class="td-opt">
        		<#if applyType=="2">
	        	<a class="abtn-blue ml-5" href="javascript:void(0);" onclick="auditCancelInfo(6);">通过</a>
			    <a class="abtn-blue ml-5" href="javascript:void(0);" onclick="auditCancelInfo(3);">不通过</a>
			    </#if>
			    <a class="abtn-blue ml-5" href="javascript:void(0);" onclick="back();">返回</a>
            </td>
        </tr>
</@htmlmacro.tableDetail>
</form>
</div>
<script type="text/javascript">
function back(){
	doSearch();
}
var isSubmit = false;
function auditCancelInfo(state){
	if(isSubmit){
		return false;
	}
	if(!confirm("确定要审核吗？")){
		return false;
	}
	var remark = $("#remark").val();
	if(remark == null ||　remark == ''){
		showMsgWarn("审核意见不能为空！");
		return;
	}
	
	$("#state").val(state);
	isSubmit = true;
	var carAuditUrl = "${request.contextPath}/office/carmanage/carmanage-cancelAuditInfo.action";
	var options = {
      target : '#carCancelAuditForm',
      url : carAuditUrl,
      success : showSuccess,
      dataType : 'json',
      clearForm : false,
      resetForm : false,
      type : 'post'
    };
  	$("#carCancelAuditForm").ajaxSubmit(options);
}
function showSuccess(data) {
    if (data!=null && data!=''){
      showMsgError(data);
      isSubmit = false;
      return;
    }else{
        showMsgSuccess("审核成功！", "提示", function(){
		  doSearch();
		});
		return;
    }
}
</script>

</@htmlmacro.moduleDiv>