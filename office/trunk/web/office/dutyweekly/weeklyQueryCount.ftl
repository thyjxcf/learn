<#import "/common/htmlcomponent.ftl" as common>
<#import "/common/commonmacro.ftl" as commonmacro>
<div class="fn-clear">
<div class="pub-table-wrap">
	<div class="pub-table-inner">
		<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
    		<div class="query-part">
    			<div class="query-tt ml-10"><span class="fn-left">学年：</span></div>
			 	<div class="select_box fn-left">
					<@common.select style="width:100px;float:left;" valName="years" valId="years" notNull="true" myfunchange="searchOrder">
						<#if yearList?exists && yearList?size gt 0>
	                		<#list yearList as yearl>
	                			<a val="${yearl!}" <#if year?default('') == yearl>class="selected"</#if>>${yearl!}</a>
	                		</#list>
	                	</#if>
					</@common.select>
				</div>
				
				<div class="query-tt ml-10"><span class="fn-left">学期：</span></div>
				<div class="select_box fn-left">
					<@common.select style="width:80px;" valName="semesters" valId="semesters" myfunchange="searchOrder">
						<a val="1"  <#if semester?default("1")=="1">class="selected"</#if>><span>第一学期</span></a>
						<a val="2"  <#if semester?default("1")=="2">class="selected"</#if>><span>第二学期</span></a>
					</@common.select>
				</div>
    		
    			<!--<div class="query-tt ml-10"><span class="fn-left">日期：</span></div>
	    		<@common.datepicker name="dutyStartTime" id="dutyStartTime" style="width:120px;" value="${((dutyStartTime)?string('yyyy-MM-dd'))?if_exists}"/>
	   			<div class="query-tt">&nbsp;-&nbsp;</div>
	    		<@common.datepicker name="dutyEndTime" id="dutyEndTime" style="width:120px;" value="${((dutyEndTime)?string('yyyy-MM-dd'))?if_exists}"/>-->
				<div class="query-tt ml-10" style="margin-left:30px;"><span class="fn-left">周次：</span></div>
					<div class="select_box fn-left">
					<@common.select style="width:100px;" valName="week" valId="week" myfunchange="searchOrder">
						<a val=""><span>请选择</span></a>
						<#list weekTimeList as item>
						<a val="${item!}" <#if item==week?default('')>class="selected"</#if>><span>第${item!}周</span></a>
						</#list>
					</@common.select>
					</div>
				
				
				&nbsp;&nbsp;<a href="javascript:void(0);" onclick="searchOrder();" class="abtn-blue">查找</a>
				&nbsp;&nbsp;<a href="javascript:void(0);" onclick="doExport();" class="abtn-blue">导出</a>
			</div>
    	</div>
    </div>
</div>		       
</div>

<span>
   		<p style="text-align:center;font-size:25px;margin-top:12px;">${unitName!}值周检查统计表</p>
</span>

<div class="typical-table-wrap pt-15" style="overflow-x:auto;overflow-y:auto;">
    <table class="typical-table">
	        <tr>
        		<th class="tt" style="min-width:70px;">
        			班级名
        		</th>
	        	<#list coreList as tpl>
	        		<#if tpl?number==2>
	        			<th style="word-break:break-all; word-wrap:break-word;min-width:65px;">3分以下</th>
	        		<#else>
	        			<th style="word-break:break-all; word-wrap:break-word;min-width:65px;">${tpl!}分</th>
	        		</#if>
	        	</#list>
	        </tr>
        	<#--上下午-->
    			<#list eisuClasss as x>
		        	<tr>
			        	<td class="tt" style="text-align:center;">
		        			${x.classnamedynamic!}
		        		</td>
			        	<#list coreList as tpl><#--班级-->
				        		<#if countMap.get('${x.id!}_${tpl!}')?exists>
				        			<#assign applyInfo = countMap.get('${x.id!}_${tpl!}')/>
					        		<td>
						        		<span class="gray">${(applyInfo!='0')?string(applyInfo!,'0')}次</span>
					        		</td>
					        	<#else>
					        		<td>
					        		<span class="gray">0次</span>
					        		</td>
					        	</#if>
			        	</#list>
		        	</tr>
	        	</#list>
    </table>
</div>
<p class="typical-tips">
    <span>
    </span>
</p>
<script type="text/javascript">	

function searchOrder(){
	var week=$("#week").val();
	var years=$("#years").val();
	var semesters=$("#semesters").val();
	var str="?week="+week+"&years="+years+"&semesters="+semesters;
	load("#workReportDiv","${request.contextPath}/office/dutyweekly/dutyweekly-weeklyQueryCount.action"+str);
}

function doExport(){
	var week=$("#week").val();
	var years=$("#years").val();
	var semesters=$("#semesters").val();
	var str="?week="+week+"&years="+years+"&semesters="+semesters;
	location.href="${request.contextPath}/office/dutyweekly/dutyweekly-dutyDownCount.action"+str;
}

$(function(){
	//初始化
	var tpW=$('.typical-table-wrap').width();
	var tdLen=$('.typical-table th').length;
	var tdW=(tpW-75)/tdLen;
	$('.typical-table th:not(".tt")').width(tdW);
	vselect();
});
</script>