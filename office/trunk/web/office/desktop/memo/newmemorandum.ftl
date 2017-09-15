<#import "/common/htmlcomponent.ftl" as common>
<#import "/common/commonmacro.ftl" as commonmacro>
<@common.moduleDiv titleName="编辑备忘录">
<p class="tt"><a href="javascript:void(0);" class="close">关闭</a><span>编辑备忘录</span></p>
<div class="wrap" id="memoEditDiv">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <th>时间：</th>
            <td>
                <@common.datepicker class="input-txt" style="width:150px;" name="memoDate" id="memoDate"  notNull="false"
				   msgName="开始日期"  value="${(memo.getTime()?string('yyyy-MM-dd HH:mm'))!}" dateFmt="yyyy-MM-dd HH:mm"/>
                 <input type="hidden" id="timeString" name="timeString" value="${memo.getTimeString()!}">
                 <input type="hidden" id="alarmTimeString" name="alarmTimeString" value="${memo.getAlarmTimeString()!}">
                 <input type="hidden" id="isSmsAlarm" name="isSmsAlarm" value="${memo.getIsSmsAlarm()!}">
                 <input type="hidden" id="id" name="id" value="${memo.getId()!}">
            </td>
        </tr>
        <tr>
            <th valign="top" class="pt-10">内容：</th>
            <td class="pt-10"><textarea id="contents" name="contents" class="text-area"">${memo.getContent()!}</textarea></td>
        </tr>
        <tr style="display:none;">
            <th>&nbsp;</th>
            <td class="pt-10 pb-10">
	            <span class="ui-checkbox <#if memo.isSmsAlarm == 1>ui-checkbox-current</#if>">
	            	<input type="checkbox" class="chk" <#if memo.isSmsAlarm == 1>checked</#if> id="memoSmsAlerm" name="memoSmsAlerm"/>短信提醒
	        	</span>
        	</td>
        </tr>
        <tr>
            <th>&nbsp;</th>
            <td class="pb-10">
                <#if memo.isSmsAlarm == 1>
	            	<@common.datepicker class="input-txt" style="width:150px;" name="memoAlarmDate" id="memoAlarmDate"  notNull="false"
				   msgName="开始日期"  value="${(memo.getSmsAlarmTime()?string('yyyy-MM-dd HH:mm'))!}" dateFmt="yyyy-MM-dd HH:mm"/>
	        	<#else>
	        		<@common.datepicker class="input-txt" style="width:150px;display:none;" name="memoAlarmDate" id="memoAlarmDate"  notNull="false"
				   msgName="开始日期"  value="${(memo.getSmsAlarmTime()?string('yyyy-MM-dd HH:mm'))!}" dateFmt="yyyy-MM-dd HH:mm"/>
	        	</#if>
            </td>
        </tr>
        <#if sendMemo>
        <tr>
        	<th style="width:49px;">
        		<#if memo.userIds! !="">
	        		<span class="ui-checkbox send-sms-btn ui-checkbox-current" onclick="test(this);">
		            		<input type="checkbox" id="isSend" name="isSend" class="chk" value="true" checked="checked">推送
		            </span>
        		<#else>
	            	<span class="ui-checkbox send-sms-btn" onclick="test(this);">
	            		<input type="checkbox" id="isSend" name="isSend" class="chk" value="true">推送
	            	</span>
            	</#if>
            </th>
            <td colspan="3" <#if memo.userIds! =="">style="display:none;"</#if> id="persons">
		        	<@commonmacro.selectMoreUser idObjectId="userIdss" nameObjectId="userNamess" width=400 height=300>
			        	<input type="hidden" id="userIdss" name="userIdss" value="${memo.userIdss!}"/>
			        	<textarea id="userNamess" name="userNamess" cols="70" rows="4" class="text-area my-5" style="width:91%;padding:5px 1%;height:50px;" msgName="" maxLength="100">${memo.userNamess!}</textarea>
		        	</@commonmacro.selectMoreUser>
		        </td>
        </tr>
        </#if>
        <tr>
        	<th>&nbsp;</th>
            <td class="pt-10">
            	<a href="javascript:void(0);" class="abtn-blue submit" id="saveBtn" name="saveBtn"><#if memo.getId()?default('') == ''>创建<#else>保存</#if></a>
                <a href="javascript:void(0);" class="abtn-blue reset" onclick="closeDiv('#memoLayer');return false;">取消</a>
            </td>
        </tr>
    </table>
</div>
<script type="text/javascript">
var isSubmit = false;
$(document).ready(function() {
	$("#saveBtn").unbind("click").bind("click",function(e){
	   if(isSubmit){
	   	return false;
	   }
	   var timeString = $("#memoDate").val() + ":00";
	   var content = $("#contents").val();
	   var isSmsAlarm = 0;
	   if ($("#memoDate").val() == null || $("#memoDate").val() == "") {
		   showMsgWarn("请选择时间");
	   	   return false;
	   }
	   if (content == null || content == "") {
		   showMsgWarn("备忘录内容不能为空!");
	       return false;
	   }
	   var length = getAbsoluteLength(content);
	   if(length>1000){
		   showMsgWarn("内容长度不能大于1000个字符!");
	       return false;
	   }
	   
	   if($("#isSend").attr("checked")!="checked"){
	   		$("#userIds").val("");
	   }
	   
	   isSubmit = true;
	   $("#saveBtn").attr("class", "abtn-unable");
		var s = getJsonStr("#memoEditDiv"); //调用这个函数会自动获取这个div中的所有输入框等内容，组装成json格式。
		$.post("${request.contextPath}/office/desktop/memo/memo-remoteSave.action", {
		"jsonString":s
		}, function(data){
			if(data!= null && data!= ""){
				showMsgError(data);
				isSubmit = false;
				$("#saveBtn").attr("class", "abtn-blue");
				return false;
			}
			else{
				//showMsgSuccess("保存成功！", "提示", function(){
					frushMyMemo();
				    $("#memorandum").show();
				    $("#memoLayer").hide();
				//});
			}
		}).error(
		function(XMLHttpRequest, textStatus, errorThrown){
			alert(XMLHttpRequest.status);
		});
	 });
});
$('.ui-checkbox').unbind('click').click(function(){
	var inpt = $("#memoAlarmDate");
	if(!$(this).hasClass('ui-checkbox-current')){
		$(this).addClass('ui-checkbox-current').find('.chk').attr('checked','checked');
		inpt.val("");
    	inpt.show();
	}else{
		$(this).removeClass('ui-checkbox-current').find('.chk').removeAttr('checked');
		inpt.hide();
	};
});
function getAbsoluteLength(str) {
  var len = 0;
  for ( var i = 0; i < str.length; i++) {
    str.charCodeAt(i) > 255 ? len += 2 : len++;
  }
  return len;
}
 function test(obj){
   		$(obj).parent().parent().find("#persons").toggle();
   }
</script>
</@common.moduleDiv>