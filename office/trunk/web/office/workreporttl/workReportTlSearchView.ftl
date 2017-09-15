<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="查看信息">
<iframe id="downloadFrame" name="downloadFrame" allowTransparency="true" frameBorder="0" width="0%" height="0%" scrolling="auto" src="" style="display:none;"></iframe>
<div id="orderAuditEditContainer">
<@htmlmacro.tableDetail divClass="table-form" id="auditTable">
 		<tr>
            <th><span class="c-red">*</span> 学年：</th>
            <td>
					第${officeWorkReportTl.year!}学年
	        </td>
        </tr>
        <tr>
        	<th><span class="c-red">*</span> 学期：</th>
        	<td>第${officeWorkReportTl.semester!}学期</td>
        </tr>
        <tr>
        <tr>
        	<th><span class="c-red">*</span> 周次：</th>
        	<td>第${officeWorkReportTl.week!}周</td>
        </tr>
        <tr>
        	<th><span class="c-red">*</span> 汇报人：</th>
        	<td>
				${officeWorkReportTl.createUserName!} 
        	</td>
        </tr>
        <tr>
        	<th><span class="c-red">*</span> 单位类型：</th>
        	<td>
				<#if officeWorkReportTl.unitClass?default(1) == 1>教育局
				<#elseif officeWorkReportTl.unitClass?default(2) == 2>学校
				</#if> 
        	</td>
        </tr>
        <tr>
        <tr>
        	<th><span class="c-red">*</span> 汇报人单位：</th>
        	<td>
				${officeWorkReportTl.unitName!} 
        	</td>
        </tr>
        <tr>
        	<th><span class="c-red">*</span> 汇报内容：</th>
            <td>
            		<textarea id="content" name="officeWorkReport.content" type="text/plain" style="width:850px;height:200px;" readonly="true">${officeWorkReportTl.content!}</textarea>
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
  doQueryChange();
}
vselect();
</script>
</@htmlmacro.moduleDiv>