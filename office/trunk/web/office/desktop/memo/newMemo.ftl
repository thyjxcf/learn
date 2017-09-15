<#import "/common/htmlcomponent.ftl" as common>
<#import "/common/commonmacro.ftl" as commonmacro>
<@common.moduleDiv titleName="新建备忘录">
<p class="tt"><a href="javascript:void(0);" onclick="closeDiv('#memoLayer');return false;" class="close">关闭</a><span>新增备忘录</span></p>
<div class="wrap" id="memoEditDiv">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <th>时间：</th>
            <td>
                <@common.datepicker class="input-txt" style="width:150px;" name="memoDate" id="memoDate"  notNull="false"
						   msgName="开始日期"  value="" dateFmt="yyyy-MM-dd HH:mm"/>
            </td>
        </tr>
        <tr>
            <th valign="top" class="pt-10">内容：</th>
            <td class="pt-10"><textarea id="contents" name="contents" class="text-area"></textarea></td>
        </tr>
        <tr style="display:none;">
        	<th>&nbsp;</th>
            <td class="pt-10 pb-10">
	            <span class="ui-checkbox">
	            	<input type="checkbox" class="chk" id="memoSmsAlerm" name="memoSmsAlerm">短信提醒
	        	</span>
        	</td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td id="memoAlarmDateTd" style="display: none" class="pb-10">
                <@common.datepicker class="input-txt" style="width:150px;" name="memoAlarmDate" id="memoAlarmDate"  notNull="false"
				   msgName="开始日期"  value="" dateFmt="yyyy-MM-dd HH:mm"/>
            </td>
        </tr>
        	<#if sendMemo>
        	<tr>
        	<th style="width:49px;">
            	<span class="ui-checkbox send-sms-btn" onclick="test(this);">
            		<input type="checkbox" id="isSend" name="isSend" class="chk" value="true">推送
            	</span>
            </th>
            <td colspan="3" style="display:none;" id="persons">
		        	<@commonmacro.selectMoreUser idObjectId="userIdsss" nameObjectId="userNamesss" width=400 height=300>
			        	<input type="hidden" id="userIdsss" name="userIdsss" value=""/>
			        	<textarea id="userNamesss" name="userNamesss" cols="70" rows="4" class="text-area my-5" style="width:91%;padding:5px 1%;height:50px;" msgName="请假原因" maxLength="100"></textarea>
		        	</@commonmacro.selectMoreUser>
		        </td>
            </tr>
            </#if>
        <tr>
        	<th>&nbsp;</th>
            <td class="pt-10">
            	<a href="javascript:void(0);" class="abtn-blue submit" id="saveBtn" name="saveBtn">创建</a>
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
	   		return;
	   }
	   var timeString = $("#memoDate").val() + ":00";
	   var content = $("#contents").val();
	   var isSmsAlarm = 0;
	   if ($("#memoDate").val() == null || $("#memoDate").val() == "") {
		   showMsgWarn("请选择时间");
	   	   return;
	   }
	   if (content == null || content == "") {
		   showMsgWarn("备忘录内容不能为空!");
	       return;
	   }
	   var length = getAbsoluteLength(content);
	   if(length>1000){
		   showMsgWarn("内容长度不能大于1000个字符!");
	       return ;
	   }
	   
	   if ($("#memoSmsAlerm").attr("checked") == "checked") {
	     isSmsAlarm = 1;
	   }
	   
	   if($("#isSend").attr("checked")!="checked"){
	   		$("#userIds").val("");
	   }
	   
	   $("#saveBtn").attr("class", "abtn-unable");
	   isSubmit = true;
		var s = getJsonStr("#memoEditDiv"); //调用这个函数会自动获取这个div中的所有输入框等内容，组装成json格式。
		$.post("${request.contextPath}/office/desktop/memo/memo-remoteSave.action", {
		"jsonString":s
		}, function(data){
			if(data != null && data != ""){
				showMsgError(data);
				$("#saveBtn").attr("class", "abtn-blue");
				isSubmit = false;
				return;
			}
			else{
				showMsgSuccess("保存成功！", "提示", function(){
					load("#memoDiv","${request.contextPath}/office/desktop/memo/memo.action");
				});
				return;
			}
		}).error(
			function(XMLHttpRequest, textStatus, errorThrown){
				alert(XMLHttpRequest.status);
			}
		);
	 });
});
$('.ui-checkbox').unbind('click').click(function(){
	var inpt = $("#memoAlarmDateTd");
	if(!$(this).hasClass('ui-checkbox-current')){
		$(this).addClass('ui-checkbox-current').find('.chk').attr('checked','checked');
		inpt.val("");
    	inpt.show();
	}else{
		$(this).removeClass('ui-checkbox-current').find('.chk').removeAttr('checked');
		inpt.hide();
	};
});

   function test(obj){
   		$(obj).parent().parent().find("#persons").toggle();
   }
</script>
</@common.moduleDiv>