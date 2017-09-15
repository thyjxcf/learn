<#import "/common/htmlcomponent.ftl" as common>
<#import "/common/commonmacro.ftl" as commonmacro>
<@common.moduleDiv titleName="请假类型维护">
	<div id="attendPeopleContainer">
	<p class="tt"><a href="javascript:void(0);" class="close" onclick="closeDiv('#classLayer3')">关闭</a><span>会议</span></p>	
	<div class="wrap pa-10" id="contentDiv">
	<table class="table-form" cellpadding="0" border="0" cellspacing="0">
		<input type="hidden" name="officeLeaveType.id" value="">
		<tr>
			<th style="width:20%;">确定参会人员</th>
			<td style="text-align:left;">
				${meeting.attendNum!}个&nbsp;${meeting.attendNames!}
			</td>
		</tr>
		<tr>
			<th>无法参会人员</th>
			<td style="text-align:left;">
				${meeting.notAttendNum!}个&nbsp;${meeting.notAttendNames!}
			</td>
		</tr>
		<tr>
			<th>未响应人员</th>
			<td style="text-align:left;">
				${meeting.notSureNum!}个&nbsp;${meeting.notSureNames!}
			</td>
		</tr>
	</table>
	</div>
	<p class="dd">
	    <a class="abtn-blue reset ml-5" href="javascript:void(0);" onclick="closeDiv('#classLayer3');">返回</a>
	</p>
	</div>
</@common.moduleDiv>