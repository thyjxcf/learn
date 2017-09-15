<#import "/common/htmlcomponent.ftl" as common>
<#import "/common/commonmacro.ftl" as commonmacro>
<#assign NEEDAUDIT = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_NEED_AUDIT") >
<#assign PASS = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_PASS") >
<#assign UNPASS = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_NOPASS") >
<#assign SECTION = stack.findValue("@net.zdsoft.office.util.Constants@OFFICE_ROOM_ORDER_USER_TYPE_SECTION") >

<div class="fn-clear">
		
    	    <!--<span>&nbsp;&nbsp;&nbsp;</span>
    		<span class="block green"></span>
    		<span>本人已申请</span>
    		<span class="block gray ml-20"></span>
    		<span>他人已抢占</span>
    		<span class="block orange ml-20"></span>
    		<span>审核中</span>
    		<span class="block red ml-20"></span>
    		<span>未通过</span>-->
    		<p class="c-red" style="margin:8px auto;float:left;"><#if officeDutyInformationSet.instruction?default('')!=''>值班说明:</#if>${officeDutyInformationSet.instruction!}</p>
        
    </div>

<div class="typical-table-wrap pt-15" style="overflow-x:auto;">
<input id="userName" name="userName" value="${userName!}" type="hidden" />
<input id="userId" name="userId" value="${loginInfo.user.id!}" type="hidden" />
<input id="applyState" name="applyState" value="${applyState!}" type="hidden" />
    <table class="typical-table">
	        <tr>
        		<th class="tt" style="min-width:70px;">
        			日期
        		</th>
	        	<#list periods as tpl>
	        		<#assign timeName=periodMap.get('${tpl!}')>
	        		<th style="word-break:break-all; word-wrap:break-word;min-width:65px;">${timeName!}</th>
	        	</#list>
	        </tr>
        	<#--上下午-->
    			<#list dateList as x>
    				<#assign riq=dateMap.get('${x!}')>
		        	<tr>
			        	<td class="tt" style="text-align:center;">
		        			${riq!}
		        		</td>
			        	<#list periods as tpl>
			        		<#if applyMap.get('${x!}_${tpl!}')?exists>
			        			<#assign applyInfo = applyMap.get('${x!}_${tpl!}')/>
			        			<#-- 本人申请 -->
			        			<#if loginInfo.user.id == applyInfo.userId>
			        				<#if admin>
			        					<td onclick="changeCancelValue('myBookState${x!}${tpl!}');">
						        			<input type="hidden" id="myBookState${x!}${tpl!}" name="myBookState" value="${x!}_${tpl!}_${loginInfo.user.id!}" needapply="1"/>
			        						<@commonmacro.selectOneUser idObjectId="userIdMyBookState${x!}${tpl!}" nameObjectId="userNameMyBookState${x!}${tpl!}" width="600" callback="setPhoneNumber">
						        			<span class="green" id="userNameMyBookState${x!}${tpl!}">${applyInfo.userName!}</span>
            								<input id="userIdMyBookState${x!}${tpl!}" name="userId"   value="${applyInfo.userId?default('')}" type="hidden" >
            								</@commonmacro.selectOneUser>
					        			</td>
			        				<#else>
					        			<td onclick="changeCancelValue('myBookState${x!}${tpl!}');">
						        			<span class="green" id="usermyBookState${x!}${tpl!}">${applyInfo.userName!}</span>
						        			<input type="hidden" id="myBookState${x!}${tpl!}" name="myBookState" value="${x!}_${tpl!}_${loginInfo.user.id!}" needapply="1"/>
					        			</td>
					        		</#if>
			        			<#else>
			        				<#-- 他人申请 -->
			        				<#if admin>
			        					<td onclick="changeCancelValue('myBookState${x!}${tpl!}');">
						        			<input type="hidden" id="myBookState${x!}${tpl!}" name="myBookState" value="${x!}_${tpl!}_${applyInfo.userId!}" needapply="1"/>
			        						<@commonmacro.selectOneUser idObjectId="userIdMyBookState${x!}${tpl!}" nameObjectId="userNameMyBookState${x!}${tpl!}" width="600" callback="setPhoneNumber">
						        			<span class="gray" id="userNameMyBookState${x!}${tpl!}">${applyInfo.userName!}</span>
            								<input id="userIdMyBookState${x!}${tpl!}" name="userId"   value="${applyInfo.userId?default('')}" type="hidden" >
            								</@commonmacro.selectOneUser>
					        			</td>
			        				<#else>
			        					<td>
					        				<span class="gray">${applyInfo.userName!}</span>
				        				</td>
			        				</#if>
			        			</#if>
			        		<#else>
			        			<#if officeDutyInformationSet.canEdit>
			        				<#assign date1=x?number>
			        				<#assign date2='${compareDate!}'?number>
			        				<#if (date1>=date2)>
					        		<td onclick="changeApplyValue('myBookState${x!}${tpl!}');">
					        			<span id="usermyBookState${x!}${tpl!}"></span>
					        			<input type="hidden" id="myBookState${x!}${tpl!}" name="myBookState" value="${x!}_${tpl!}_${loginInfo.user.id!}"/>
				        			</td>
				        			<#else>
				        				<td>
					        			<span></span>
					        			<input type="hidden" name="myBookState"/>
				        			</td>
				        			</#if>
				        		<#else>
				        			<td>
					        			<span></span>
					        			<input type="hidden" name="myBookState"/>
				        			</td>
				        		</#if>
			        		</#if>
			        	</#list>
		        	</tr>
	        	</#list>
    </table>
</div>
<#if officeDutyInformationSet.canEdit||admin>
<p class="typical-tips">
	提示：在空白位置点击则报名，再次点击则取消，报名后保存生效
    <span>
        <a href="javascript:void(0);" class="abtn-blue-big" onclick="applyRoomInfo();">保存</a>
    </span>
</p>
</#if>
<script type="text/javascript">	
	
function changeApplyValue(id){
	if($("#"+id).attr("needApply") == 1){
		$("#"+id).removeAttr("needApply","1");
		$("#user"+id).text("");
		$("#user"+id).removeClass("green");
	}else{
        $("#"+id).attr("needApply","1");
        $("#user"+id).text($("#userName").val());
        $("#user"+id).addClass("green");
	}
}

function changeCancelValue(id){
	if($("#"+id).attr("needApply") == 1){
		$("#"+id).removeAttr("needApply","1");
		$("#user"+id).text("");
		$("#user"+id).removeClass("green");
	}else{
        $("#"+id).attr("needApply","1");
        $("#user"+id).text($("#userName").val());
		$("#user"+id).addClass("green");
	}
}

function setPhoneNumber(idstr){
	var userId=$("#"+idstr).val();
	var desId=$("#"+idstr).parent().prev().val();
	var arrA=desId.split("_");
	if(userId==''){
		$("#"+idstr).parent().prev().val(arrA[0]+"_"+arrA[1]);
		$("#"+idstr).parent().prev().removeAttr("needApply","1");
		$("#"+idstr).prev().removeClass("green");
		return;
	}
	var desA=arrA[0]+"_"+arrA[1]+"_"+userId;
	$("#"+idstr).parent().prev().val(desA);
	$("#"+idstr).parent().prev().attr("needApply","1");
	var desUser=$("#userId").val();
	if(desUser==userId){
		$("#"+idstr).prev().removeClass();
		$("#"+idstr).prev().addClass("green");
	}else{
		$("#"+idstr).prev().removeClass();
		$("#"+idstr).prev().addClass("gray");
	}
	$.getJSON("${request.contextPath}/office/dutymanage/dutymanage-applyGetPhoneNumber.action", {
		"backUserId":userId
		}, function(data){
			if (!data.operateSuccess){
				if(data.errorMessage!=null&&data.errorMessage!=""){
				   showMsgError(data.errorMessage);
				   return;
			    }
			}else{
				if(data.promptMessage != null && data.promptMessage != ""){
					var userName=$("#"+idstr).prev().text();
					var phone=data.promptMessage;
					if(phone==null||phone==''){
						$("#"+idstr).prev().text(userName);
					}else{
						$("#"+idstr).prev().text(userName+"("+phone+")");
					}
				}
				return;
			}
	}).error(function(XMLHttpRequest, textStatus, errorThrown){alert(textStatus);alert(XMLHttpRequest.status);
	});
}

var isSubmit = false;
function applyRoomInfo(){
	if(isSubmit){
		return;
	}
	var promptInfo;
	var applyRooms = [];
	var i = 0;
	$("input[name='myBookState'][needApply='1']").each(function(){
		applyRooms[i] = $(this).val();
		i++;
	});
	//if(applyRooms.length == 0){
	//	showMsgWarn("值班报名人不能为空");
	//	return;
	//}
	var dutyId = $("#dutyId").val();
	if(dutyId == ''){
		showMsgWarn("请选择你要报名的名称");
		return;
	}
	isSubmit = true;
	$.ajax({
		type: "POST",
		url: "${request.contextPath}/office/dutymanage/dutymanage-saveApplyInfo.action",
		data: $.param( {"applyRooms":applyRooms,"dutyId":dutyId},true),
		success: function(data){
			if(!data.operateSuccess){
			   if(data.errorMessage!=null&&data.errorMessage!=""){
				   showMsgError(data.errorMessage);
				   isSubmit = false;
				   return;
			   }
			}else{
				showMsgSuccess(data.promptMessage,"",function(){
				  	searchOrder();
				});
				return;
			}
		},
		dataType: "json",
		error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
	});
}

function showReply(data){
	if(data != null && data!=''){
		showMsgError(data);
		isSubmit = false;
		return;
	}else{
		showMsgSuccess("提交申请成功", "提示", function(){
			searchOrder();
		});
		return;
	}
}

$(function(){
	//初始化
	var tpW=$('.typical-table-wrap').width();
	var tdLen=$('.typical-table th').length;
	var tdW=(tpW-75)/tdLen;
	$('.typical-table th:not(".tt")').width(tdW);
	<#--<#if canEdit>-->
	//选中
	$('.typical-table td span:not(".gray")').click(function(){
		var $td=$(this).parent('td');
		if(!$td.hasClass('current')){
			$td.addClass('current');
		}else{
			$td.removeClass('current');
		}
	});
	<#--</#if>-->
	vselect();
});
</script>