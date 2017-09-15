<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="车辆使用情况">
<@htmlmacro.tableDetail divClass="table-form">
    <tr>
        <th style="width:20%" class="t-center">可容纳人数</th>
        <th style="width:80%" class="t-center">用车情况</th>
    </tr>
 	<tr>
        	
        <td class="t-center">${useCarInfoDto.seating!}</td>
        <td class="t-left">
        	<#if useCarInfoDto.officeCarApplyList?exists && useCarInfoDto.officeCarApplyList?size gt 0>
        		<#list useCarInfoDto.officeCarApplyList as x>
        			用车时间：${x.useTime?string('yyyy-MM-dd HH:mm')}&nbsp;&nbsp;
        			<#if x.waitingTime?exists>
        			返回时间：${x.waitingTime?string('yyyy-MM-dd HH:mm')}
        			<#else>
        			不需接返
        			</#if>
        			<br/>
        		</#list>
        	</#if>
        </td>
    </tr>
</@htmlmacro.tableDetail>
</@htmlmacro.moduleDiv>