<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script> 
<script type="text/javascript">

function setMessage(id){
		if(!showConfirm('将发短信给班主任和学生家长（需要正确维护班主任和家长手机号才能发送成功），确定要发送吗?')){
			return;
		}
		
		var holidayId = $('#holidayId').val();
		var url="${request.contextPath}/office/check/backSchoolAdmin-setMessage.action";
		$.getJSON(url, {"studentid":id, "holidayId":holidayId}, function(data){
		if(!data.operateSuccess){
		   if(data.errorMessage!=null&&data.errorMessage!=""){
				   showMsgError(data.errorMessage);
				   return;
			}	   
		}else{
			showMsgSuccess(data.promptMessage,"",function(){
			  var url = "${request.contextPath}/office/check/backSchoolAdmin-list.action?holidayId="+'${holidayId!}'+"&classid="+'${classid!}'+"&backState="+'${backState!}';
   			 load("#containerHolidayDiv", url);
			});
			return;
		}
	});
	}
</script>

<@htmlmacro.tableList id="tablelist mt-15">
    <tr>
        <th>班级</th>
    	<th>姓名</th>
    	<th>返校状态</th>
    	<th class="t-center">短信提醒</th>
    </tr>
    <#if stuBackList?exists && (stuBackList?size>0)>
    <#list stuBackList as item>
    <tr>
       <td>${item.className?default('')}</td>
    	<td>${item.stuname?default('')}</td>
    	<td>${item.backstateText?default('')}</td>
   		<td class="t-center"><#if item.backstate?exists>
   				<#if item.backstate=="1">
   				不用提醒
   				<#elseif item.backstate=="0">
   				<a class ="edit-class" href="javascript:void(0);" onclick="setMessage('${item.studentId?default("")}')" >发送</a>
					
   				<#else>
   				&nbsp;
   				</#if>
   			<#else>
   			&nbsp;
   			</#if></td>
    </tr>
    </#list>
    <#else>
	   <tr><td colspan="10"> <p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td></tr>
	</#if>
</@htmlmacro.tableList>
