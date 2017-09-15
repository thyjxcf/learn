<#import "/common/htmlcomponent.ftl" as common>
<#import "/common/commonmacro.ftl" as commonmacro>
<style type="text/css">
.typical-ttt{width:25%;height:14px;line-height:14px;overflow:hidden;zoom:1;}
.typical-ttt span{display:inline-block;float:left;}
.typical-ttt span.block{width:14px;height:14px;margin-right:6px;}
.typical-ttt span.green{background:#91ce31;}
.typical-ttt span.gray{background:#ded9d9;}
.typical-ttt span.orange{background:#ee8c0c;}
.typical-ttt span.red{background:#e32a2a;}
</style> 
<#assign NEEDAUDIT = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_NEED_AUDIT") >
<#assign PASS = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_PASS") >
<#assign UNPASS = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_NOPASS") >
<#assign SECTION = stack.findValue("@net.zdsoft.office.util.Constants@OFFICE_ROOM_ORDER_USER_TYPE_SECTION") >
<div class="typical-table-wrap pt-15" <#if (periodList?size>10)>style="width:1200px;overflow-x:auto;"></#if>
<form action="" method="post" name="labApplyForm" id="labApplyForm" >
<input type="hidden" id="applyState" name="applyState"/>
<div id="labApplyDiv"></div>
    <table class="typical-table">
	        <tr>
        		<th class="tt" style="width:64px;">
        				间隔${officeBoardroomXj.timeInterval!}分钟
        		</th>
	        	<#list periodList as tpl>
	        		<th style="word-break:break-all; word-wrap:break-word;min-width:65px;">${tpl!}</th>
	        	</#list>
	        </tr>
    			<#list applyDateList as x>
		        	<tr>
			        	<td class="tt" style="text-align:center;">
		        			${timeMap.get(x)!}
		        		</td>
			        	<#list periodList as tpl>
			        		<#if applyMap.get('${x!}_${tpl!}')?exists>
			        			<#assign applyInfo = applyMap.get('${x!}_${tpl!}')/>
			        			<#-- 本人申请 -->
			        			<#if officeApplyDetailsXj.deptId== applyInfo.deptId>
				        			<#if applyInfo.state == NEEDAUDIT+''>
					        			<td onclick="changeCancelValue('myBookState${x!}${tpl!}',this);">
						        			<span class="orange"><i></i></span>
						        			<input type="hidden" id="myBookState${x!}${tpl!}" name="myBookState" value="${x!}_${tpl!}"/>
					        			</td>
				        			<#else>
				        				<td onclick="changeCancelValue('myBookState${x!}${tpl!}',this);">
						        			<span class="green"><span title="${applyInfo.userName!}">${applyInfo.userName!}</span><i></i></span>
						        			<input type="hidden" id="myBookState${x!}${tpl!}" name="myBookState" value="${x!}_${tpl!}"/>
					        			</td>
				        			</#if>
			        			<#else>
			        				<#-- 他人申请 -->
			        				<td>
					        			<span class="gray" title="${applyInfo.userName!}">${applyInfo.userName!}<i></i></span>
				        			</td>
			        			</#if>
			        		<#else>
			        			<#assign date1=x?number>
			        			<#assign date2='${compareDate!}'?number>
			        			<#if (date1>=date2)>
				        		<td onclick="changeApplyValue('myBookState${x!}${tpl!}',this);">
				        			<span class="empty"><i></i></span>
				        			<input type="hidden" id="myBookState${x!}${tpl!}" name="myBookState" value="${x!}_${tpl!}"/>
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
    </table>
    </div>
	</form>
    </div>
</div>
<#if periodList?exists && periodList?size gt 0>
<p class="typical-tips">
	提示：点击选中您需要使用的时间段(注：过去的日期不能申请)，再提交
    <span>
        <a href="javascript:void(0);" class="abtn-blue-big" onclick="applyRoomInfo();">提交</a>
        <!--<a href="javascript:void(0);" class="abtn-blue-big ml-15" onclick="cancelRoomInfo();">撤销</a>-->
        <a href="javascript:void(0);" class="abtn-blue-big ml-15" onclick="back();">返回</a>
    </span>
</p>
</#if>

<script type="text/javascript">	
	
function changeApplyValue(id,obj){
	
	var _input = $(obj).find("input");
		
	if($(_input).attr("needApply") == 1){
		$(_input).removeAttr("needApply");
	}else{
        $(_input).attr("needApply","1");
	}
}

function changeCancelValue(id,obj){
	var _input = $(obj).find("input");
	if($(_input).attr("needCancel") == 2){
		$(_input).removeAttr("needCancel","2");
	}else{
		$(_input).attr("needCancel","2");
	}
}
var isSubmit = false;
function applyRoomInfo(){
	if($("input[name='myBookState'][needApply='1']").length > 0){
		$("#applyState").val("1");
	}else{
		showMsgWarn("请先选择要申请的时段");
		return;
	}

	var applyState = $("#applyState").val();
	var promptInfo;
	var applyTimes = [];
	var i = 0;
	if(applyState == 1){
		promptInfo = "提交申请成功";
		$("input[name='myBookState'][needApply='1']").each(function(){
			applyTimes[i] = $(this).val();
			i++;
		});
		if(applyTimes.length == 0){
			showMsgWarn("请先选择要申请的时段");
			return;
		}
	}else{
		promptInfo = "撤销成功";
		$("input[name='myBookState'][needCancel='2']").each(function(){
			applyTimes[i] = $(this).val();
			i++;
		});
		if(applyTimes.length == 0){
			showMsgWarn("请先选择要撤销的时段");
			return;
		}
	}
	var roomId=$("#roomId").val();
	var deptId=$("#deptId").val();
	if(deptId==null||deptId==''){
		showMsgWarn("请选择部门");
		return;
	}
	isSubmit = true;
	$.ajax({
		type: "POST",
		url: "${request.contextPath}/office/boardroommanage/boardroommanage-boardRoomOrderSave.action",
		data: $.param( {"applyTimes":applyTimes,"officeBoardroomXjId":roomId,"applyState":applyState,"deptId":deptId},true),
		success: function(data){
			if(data != null && data!=''){
				showMsgError(data);
				isSubmit = false;
				return;
			}else{
				showMsgSuccess(promptInfo, "提示", function(){
					//var deptId=$("#deptId").val();
					//load("#orderApplyListDiv","${request.contextPath}/office/boardroommanage/boardroommanage-boardRoomOrderList.action?officeBoardroomXjId="+'${officeBoardroomXj.id!}'+"&deptId="+deptId);
					doOrder();
				});
				return;
			}
		},
		dataType: "json",
		error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
	});
}

function cancelRoomInfo(){
	if($("input[name='myBookState'][needCancel='2']").length > 0){
		$("#applyState").val("2");
	}else{
		showMsgWarn("请先选择要撤销的时段");
	}
	var applyState = $("#applyState").val();
	var promptInfo;
	var applyTimes = [];
	var i = 0;
	if(applyState == 1){
		promptInfo = "提交申请成功";
		$("input[name='myBookState'][needApply='1']").each(function(){
			applyTimes[i] = $(this).val();
			i++;
		});
		if(applyTimes.length == 0){
			showMsgWarn("请先选择要申请的时段");
			return;
		}
	}else{
		promptInfo = "撤销成功";
		$("input[name='myBookState'][needCancel='2']").each(function(){
			applyTimes[i] = $(this).val();
			i++;
		});
		if(applyTimes.length == 0){
			showMsgWarn("请先选择要撤销的时段");
			return;
		}
	}
	var roomId=$("#roomId").val();
	var deptId=$("#deptId").val();
	if(deptId==null||deptId==''){
		showMsgWarn("请选择部门");
		return;
	}
	isSubmit = true;
	$.ajax({
		type: "POST",
		url: "${request.contextPath}/office/boardroommanage/boardroommanage-boardRoomOrderSave.action",
		data: $.param( {"applyTimes":applyTimes,"officeBoardroomXjId":roomId,"applyState":applyState,"deptId":deptId},true),
		success: function(data){
			if(data != null && data!=''){
				showMsgError(data);
				isSubmit = false;
				return;
			}else{
				showMsgSuccess(promptInfo, "提示", function(){
					load("#orderApplyListDiv","${request.contextPath}/office/boardroommanage/boardroommanage-boardRoomOrderList.action?officeBoardroomXjId="+'${officeBoardroomXj.id!}'+"&deptId="+deptId);
				});
				return;
			}
		},
		dataType: "json",
		error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
	});
}

function back(){
	load("#boardRoomDiv","${request.contextPath}/office/boardroommanage/boardroommanage-boardRoomAdmin.action",function(){
		load("#boardRoomListDiv", "${request.contextPath}/office/boardroommanage/boardroommanage-boardRoomList.action");
	});
}
$(function(){
	//初始化
	var tpW=$('.typical-table-wrap').width();
	var tdLen=$('.typical-table th').length;
	var tdW=(tpW-75)/tdLen;
	$('.typical-table th:not(".tt")').width(tdW);
	
	
	//选中
	$('.typical-table td span:not(".gray")').click(function(){
		var $td=$(this).parent('td');
		if(!$td.hasClass('current')){
			$td.addClass('current');
		}else{
			$td.removeClass('current');
		}
	});
	
});



</script>