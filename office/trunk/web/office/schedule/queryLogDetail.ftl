<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<form id="setAddForm" name="setAddForm">
    <p class="tt"><a href="javascript:void(0);" class="close" onclick="closeDiv('#scheduleLayer');">关闭</a><span>工作日志</span></p>
    <div class="wrap pa-10" style="height:540px;width:980px;overflow-y:auto;overflow-x:auto">
        <input type="hidden" id="calId" name="cal.id" value="${cal.id!}"/>
        <table border="0" cellspacing="0" cellpadding="0" class="table-form" style="table-layout:fixed;">
        	<tr>
                <th width="20%">开始时间：</th>
                <td width="30%"><span class="fn-left">${((cal.calendarTime)?string('yyyy-MM-dd HH:mm:ss'))?if_exists}</span></td>
                <th width="20%">维持时间：</th>
                <td width="30%"><span class="fn-left">${cal.halfDays!}&nbsp;天</span></td>
            </tr>
            <tr>
                <th width="20%">结束时间：</th>
                <td width="30%"><span class="fn-left mt-5">${((cal.endTime)?string('yyyy-MM-dd HH:mm:ss'))?if_exists}</span></td>
                <th width="20%">时间段：</th>
                <td width="30%"><span class="fn-left mt-5">
	               		<#if cal.period==1>上午
						<#elseif cal.period==2>中午
						<#elseif cal.period==3>下午
						<#elseif cal.period==4>晚上
						<#else>全天</#if>
					</span>
				</td>
            </tr>
            <tr>
    			<th width="20%">地点：</th>
    			<td colspan="3" width="80%"><span class="fn-left mt-5">${cal.place!}</span></td>
    		</tr>
            <tr>
            	<th width="20%">内容：</th>
            	<td colspan="3" width="80%">${cal.content!}</td>
            </tr>
		</table>
    </div>
</form>
</@common.moduleDiv>