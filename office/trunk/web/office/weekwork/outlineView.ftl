<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="大纲编辑">
<div id="outlineEditContainer">
<p class="table-dt fb18">周工作大纲</p>
<@htmlmacro.tableDetail divClass="table-form">
        <tr>
            <th style="width:20%;"><span class="c-orange ml-10">*</span>&nbsp;开始日期：</th>
            <td style="width:80%;">
            	${officeWorkArrangeOutline.startTime?string('yyyy-MM-dd')!}
            </td>
        </tr>
        <tr>
            <th><span class="c-orange ml-10">*</span>&nbsp;结束日期：</th>
            <td>
                ${officeWorkArrangeOutline.endTime?string('yyyy-MM-dd')!}
            </td>
        </tr>
        <tr>
            <th><span class="c-orange ml-10">*</span>&nbsp;工作大纲名称：</th>
            <td style="word-break:break-all; word-wrap:break-word;">
            	${officeWorkArrangeOutline.name?default("")}
            </td>
        </tr>
                     
        <tr>
            <th>工作重点：</th>
            <td colspan="3" style="word-break:break-all; word-wrap:break-word;">
				${officeWorkArrangeOutline.workContent?default("")}
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
  load("#weekworkContainer","${request.contextPath}/office/weekwork/weekwork-outlineList.action");
}
vselect();
</script>
</@htmlmacro.moduleDiv>