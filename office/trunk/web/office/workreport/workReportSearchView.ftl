<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="查看信息">
<iframe id="downloadFrame" name="downloadFrame" allowTransparency="true" frameBorder="0" width="0%" height="0%" scrolling="auto" src="" style="display:none;"></iframe>
<div id="orderAuditEditContainer">
<@htmlmacro.tableDetail divClass="table-form" id="auditTable">
 		<tr>
            <th><span class="c-red">*</span> 汇报类型：</th>
            <td>
					${appsetting.getMcode("DM-HBLX").get(officeWorkReport.reportType!)}
	        </td>
        </tr>
        <tr>
        	<th><span class="c-red">*</span> 时间：</th>
        	<td>
    		${(officeWorkReport.beginTime?string('yyyy-MM-dd'))?if_exists}
            <span>至</span>
            ${(officeWorkReport.endTime?string('yyyy-MM-dd'))?if_exists}
        	</td>
        </tr>
        <tr>
        	<th><span class="c-red">*</span> 汇报人：</th>
        	<td>
				${officeWorkReport.createUserName!} 
        	</td>
        </tr>
        <tr>
        	<th><span class="c-red">*</span> 汇报人部门：</th>
        	<td>
				${officeWorkReport.deptId!} 
        	</td>
        </tr>
        <tr>
        <tr>
        	<th><span class="c-red">*</span> 汇报人单位：</th>
        	<td>
				${officeWorkReport.unitId!} 
        	</td>
        </tr>
        <tr>
        	<th><span class="c-red">*</span> 汇报内容：</th>
            <td>
            		<textarea id="content" name="officeWorkReport.content" type="text/plain" style="width:1024px;height:200px;" readonly="true">${officeWorkReport.content!}</textarea>
	        </td>
        </tr>
        <tr>
        	<td colspan="2" class="td-opt">
			    <a class="abtn-blue reset ml-5" href="javascript:void(0);" onclick="back();">返回</a>
            </td>
        </tr>
   </@htmlmacro.tableDetail>
</div>
<script type="text/javascript">
function back(){
  workReportSearch();
}
vselect();
</script>
</@htmlmacro.moduleDiv>