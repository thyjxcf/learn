<#import "/common/htmlcomponent.ftl" as common />
<#import "/common/commonmacro.ftl" as commonmacro>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<@common.tableList id="listTable" name="listTable" class="public-table table-list table-dragSort" style="table-layout:fixed">
					                    <tr>
					                        <th class="t-center">日期</th>
					                        <th class="t-center">考勤人数</th>
					                        <th class="t-center">正常考勤人数</th>
					                        <th class="t-center">外勤考勤人数</th>
					                        <th class="t-center">迟到人数</th>
					                        <th class="t-center">早退人数</th>
					                        <th class="t-center">请假人数</th>
					                        <th class="t-center">缺卡人数</th>
					                        <th class="t-center">旷工人数</th>
					                        <th class="t-center">出差人数</th>
					                        <th class="t-center">外出人数</th>
					                        <th class="t-center">集体外出人数</th>
					                    </tr>
					<#if viewDtoList?exists &&  (viewDtoList?size>0)>
						<#list viewDtoList as view>
							<tr>
								            <td class="t-center">${(view.day)?string('yyyy-MM-dd')?if_exists}</td>
					                        <td class="t-center">${view.attendanceNum?default('0')}</td>
					                        <td class="t-center">${view.customAttendanceNum?default('0')}</td>
					                        <td class="t-center">${view.outWorkNum?default('0')}</td>
					                        <td class="t-center">${view.laterNum?default(0)}</td>
					                        <td class="t-center">${view.leaveEarlyNum?default(0)}</td>
					                        <td class="t-center">${view.leaveNum?default(0)}</td>
					                        <td class="t-center">${view.missCardNum?default(0)}</td>
					                         <td class="t-center">${view.notWorkNum?default('0')}</td>
					                        <td class="t-center">${view.businessNum?default(0)}</td>
					                        <td class="t-center">${view.goOutNum?default(0)}</td>
					                        <td class="t-center">${view.jtGoOutNum?default(0)}</td>
								</td>
							</tr>
						</#list>
					<#else>
						<tr>
							<td colspan="12"><p class="no-data mt-20 mb-20">还没有任何记录哦！</p></td>
						</tr>
					</#if>
</@common.tableList>
<@common.Toolbar container="#showList">
	<p class="opt">
	
    </p>
</@common.Toolbar>