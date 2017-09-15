<#import "/common/htmlcomponent.ftl" as common>
<#import "/common/commonmacro.ftl" as commonmacro>
<div class="fn-clear">
<div class="pub-table-wrap">
	<div class="pub-table-inner">
		<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
    		<div class="query-part">
    			<div class="query-tt ml-10"><span class="fn-left">日期：</span></div>
	    		<@common.datepicker name="dutyStartTime" id="dutyStartTime" style="width:120px;" value="${((dutyStartTime)?string('yyyy-MM-dd'))?if_exists}"/>
	   			<div class="query-tt">&nbsp;-&nbsp;</div>
	    		<@common.datepicker name="dutyEndTime" id="dutyEndTime" style="width:120px;" value="${((dutyEndTime)?string('yyyy-MM-dd'))?if_exists}"/>
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
				&nbsp;&nbsp;<a href="javascript:void(0);" onclick="doPrint();" class="abtn-blue">打印</a>
				&nbsp;&nbsp;<a href="javascript:void(0);" onclick="doExport();" class="abtn-blue">导出</a>
			</div>
    	</div>
    </div>
</div>			       
</div>

<div id="printDiv">
<div class="typical-table-wrap pt-15" style="overflow-x:auto;">
    <table class="typical-table">
    	<#if officeDutyProjects?exists && officeDutyProjects?size gt 0>
	        <tr>
        		<th class="tt" style="min-width:70px;">
        			
        		</th>
	        	<#list officeDutyProjects as x>
	        		<th style="word-break:break-all; word-wrap:break-word;min-width:65px;">${x.projectName!}</th>
	        	</#list>
	        </tr>
        	<#--上下午-->
    			<#list eisuClasss as tpl>
		        	<tr>
			        	<td class="tt" style="text-align:center;">
		        			${tpl.classnamedynamic!}
		        		</td>
			        	<#list officeDutyProjects as x><#--班级-->
				        		<#if totalMap.get('${x.id!}_${tpl.id!}')?exists>
				        			<#assign applyInfo = totalMap.get('${x.id!}_${tpl.id!}')/>
					        		<td>
						        		<span class="gray">${(applyInfo.score!=0)?string(applyInfo.score!,'')}</span>
					        		</td>
					        	<#else>
					        		<td>
					        		<span></span>
					        		</td>
					        	</#if>
			        	</#list>
		        	</tr>
	        	</#list>
	        <tr>
	        	<td class="tt" style="text-align:center;">
		        	备注:
		        </td>
		        <td colspan="${eisuClasss?size}">
				<#if officeDutyRemarks?exists&&(officeDutyRemarks?size>0)>
				<#list officeDutyRemarks as x>
					<p class="remarkTD mt-5">&nbsp;${x.remark!}(${((x.createTime)?string('yyyy-MM-dd'))?if_exists});</p>
				</#list>
				</#if>
			</td>
	        </tr>
	    <#else>
	    	<tr>
		    	<td>
		    		<p class="no-data mt-50 mb-50">
	        				暂无记录！
	    			</p>
		    	</td>
		    </tr>
	    </#if>    
    </table>
</div>
</div>
<p class="typical-tips">
	提示：查询的是扣分情况，按周次查询默认查询的是当前学年学期的扣分记录;
    <span>
    </span>
</p>
<script type="text/javascript">	

function searchOrder(){
	var dutyStartTime=$("#dutyStartTime").val();
	var dutyEndTime=$("#dutyEndTime").val();
	if(dutyStartTime!=''&&dutyEndTime!=''){
		var re = compareDate(dutyStartTime,dutyEndTime);
		if(re==1){
			showMsgError("结束时间不能早于开始时间，请重新选择！");
			return;
		}
	}
	var week=$("#week").val();
	var str="?dutyStartTime="+dutyStartTime+"&dutyEndTime="+dutyEndTime+"&week="+week;
	load("#workReportDiv","${request.contextPath}/office/dutyweekly/dutyweekly-weeklyQueryList.action"+str);
}

function doExport(){
	var dutyStartTime=$("#dutyStartTime").val();
	var dutyEndTime=$("#dutyEndTime").val();
	if(dutyStartTime!=''&&dutyEndTime!=''){
		var re = compareDate(dutyStartTime,dutyEndTime);
		if(re==1){
			showMsgError("结束时间不能早于开始时间，请重新选择！");
			return;
		}
	}
	var week=$("#week").val();
	var str="?dutyStartTime="+dutyStartTime+"&dutyEndTime="+dutyEndTime+"&week="+week;
	location.href="${request.contextPath}/office/dutyweekly/dutyweekly-dutyDown.action"+str;
}
function doPrint(){
	LODOP=getLodop();
	LODOP.ADD_PRINT_HTM("10mm","5mm","RightMargin:5mm","BottomMargin:15mm",getPrintContent(jQuery('#printDiv')));
  	LODOP.PREVIEW();
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