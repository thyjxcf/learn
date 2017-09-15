<#import "/common/htmlcomponent.ftl" as common>
<#import "/common/commonmacro.ftl" as commonmacro>
<div class="fn-clear">

<div class="pub-table-wrap">
	<div class="pub-table-inner">
		<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
    		<div class="query-part">
    			<div class="query-tt ml-10">
					<span class="fn-left">登记日期：</span>
				</div>
				<div class="fn-left">
    			<@common.datepicker id="dutyDate" dateFmt="yyyy-MM-dd" value="${((dutyDate)?string('yyyy-MM-dd'))?if_exists}" onpicked="a1"/>
				</div>
				<div class="query-tt ml-10" style="margin-left:30px;">
					<span class="fn-left">周次：</span>
				</div>
				<div class="fn-left" id="importIdDiv">
					<@common.select style="width:230px;" className="ui-select-box-disable" valName="importId" valId="importId" myfunchange="searchOrder">
						<a val="">请选择</a>
						<#list weekTimeList as item>
						<a val="${item!}" <#if item==officeDutyWeekly.week?default('')+''>class="selected"</#if>>第${item!}周</a>
						</#list>
					</@common.select>
				</div>
			</div>
    	</div>
    </div>
</div>		
    	       
</div>


<div class="typical-table-wrap pt-15" style="overflow-x:auto;">
<input id="dutyWeeklyId" name="dutyWeeklyId" value="${officeDutyWeekly.id!}" type="hidden" />
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
			        	<#list officeDutyProjects as x><#--项目-->
			        		<#if officeDutyWeekly.canEdit>
			        			<#if applyMap.get('${x.id!}_${tpl.id!}')?exists>
			        				<#assign applyInfo = applyMap.get('${x.id!}_${tpl.id!}')/>
			        				<td>
					        			<input type="hidden" id="myBookStates${x.id!}_${tpl.id!}" name="myBookStates" value="${x.id!}_${tpl.id!}${(applyInfo.score!=0)?string('_'+applyInfo.score!,'')}"/>
					        			<input type="text" id="myBookState${x.id!}_${tpl.id!}" style="text-align: center;width:98%;height:98%" name="myBookState" regex="/^\d*$/" maxlength="2" onblur="tea('${x.id!}_${tpl.id!}');" value="${(applyInfo.score!=0)?string(applyInfo.score!,'')}"/>
				        			</td>
			        			<#else>
					        	<td>
					        		<input type="hidden" id="myBookStates${x.id!}_${tpl.id!}" name="myBookStates" value="${x.id!}_${tpl.id!}"/>
					        		<input type="text" id="myBookState${x.id!}_${tpl.id!}" style="text-align: center;width:98%; height:98%" name="myBookState" regex="/^\d*$/" maxlength="2" onblur="tea('${x.id!}_${tpl.id!}');" value=""/>
				        		</td>
				        		</#if>
				        	<#else>
				        		<#if applyMap.get('${x.id!}_${tpl.id!}')?exists>
				        			<#assign applyInfo = applyMap.get('${x.id!}_${tpl.id!}')/>
					        		<td>
						        		<span class="gray">${(applyInfo.score!=0)?string(applyInfo.score!,'')}</span>
					        		</td>
					        	<#else>
					        		<td>
					        		<span></span>
					        		</td>
					        	</#if>
				        	</#if>
			        	</#list>
		        	</tr>
	        	</#list>
	        	<tr style="display:none;">
	        		<#list officeDutyProjects as xx>
	        			<td>
	        				<input type="hidden" id="myBookStates${xx.id!}_${dutyClassId!}" name="myBookStates" value="${xx.id!}_${dutyClassId!}"/>
	        			</td>
	        		</#list>
	        	</tr>
	        <tr>
	        	<td class="tt" style="text-align:center;">
		        	备注:
		        </td>
		        <td colspan="${officeDutyProjects?size}">
				<textarea name="dutyRemark" id="dutyRemark" style="width:98%;padding:5px 1%;height:50px;" msgName="备注" maxLength="250">${officeDutyRemark.remark!}</textarea>
			</td>
	        </tr>
	    <#else>
	    	<tr>
		    	<td>
		    		<p class="no-data mt-50 mb-50">
	        				请先维护检查项目！
	    			</p>
		    	</td>
		    </tr>
	    </#if>
    </table>
</div>
<#if officeDutyWeekly.canEdit>
<p class="typical-tips">
	提示：填入的是扣分情况，不填入默认为满分（5分）
    <span>
        <#if officeDutyProjects?exists && officeDutyProjects?size gt 0><a href="javascript:void(0);" class="abtn-blue-big" onclick="applyRoomInfo();">保存</a></#if>
    </span>
</p>
</#if>
<script type="text/javascript">	

function a1(){
	var dutyDate=$("#dutyDate").val();
	load("#workReportDiv", "${request.contextPath}/office/dutyweekly/dutyweekly-weeklyapplyList.action?dutyDate="+dutyDate);
}


function tea(obj){
	var core=$("#myBookState"+obj).val();
	var regu = /^-[1-5]\d*$/;
	if (core!="") { 
	   if (!regu.test(core)) {  
	       $("#myBookStates"+obj).removeAttr("needApply","1");     
	       showMsgWarn("请输入值为1-5的负整数");
	       $("#myBookState"+obj).val("");
	   } else { 
	       $("#myBookStates"+obj).val(obj+"_"+core);
	       $("#myBookStates"+obj).attr("needApply","1");
	   }  
   }else{
   		$("#myBookStates"+obj).removeAttr("needApply","1");
   		$("#myBookStates"+obj).val(obj);
   } 
}	

var isSubmit = false;
function applyRoomInfo(){
	if(isSubmit){
		return;
	}
	var applyRooms = [];//申请扣分
	var i = 0;
	$("input[name='myBookStates'][needApply='1']").each(function(){
		applyRooms[i] = $(this).val();
		i++;
	});
	
	var fullSore=[];//满分
	var j = 0;
	$("input[name='myBookStates']").each(function(){
		fullSore[j] = $(this).val();
		j++;
	});
	
	var dutyWeeklyId = $("#dutyWeeklyId").val();
	var dutyRemark=$("#dutyRemark").val();
	var dutyDate=$("#dutyDate").val();
	
	isSubmit = true;
	$.ajax({
		type: "POST",
		url: "${request.contextPath}/office/dutyweekly/dutyweekly-saveApplyInfo.action",
		data: $.param( {"applyRooms":applyRooms,"fullSore":fullSore,"dutyWeeklyId":dutyWeeklyId,"dutyRemark":dutyRemark,"dutyDate":dutyDate},true),
		success: function(data){
			if(!data.operateSuccess){
			   if(data.errorMessage!=null&&data.errorMessage!=""){
				   showMsgError(data.errorMessage);
				   isSubmit = false;
				   return;
			   }
			}else{
				showMsgSuccess(data.promptMessage,"",function(){
				  	load("#workReportDiv", "${request.contextPath}/office/dutyweekly/dutyweekly-weeklyapplyList.action?dutyDate="+dutyDate);
				});
				return;
			}
		},
		dataType: "json",
		error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
	});
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