<#import "/common/htmlcomponent.ftl" as common>
<#import "/common/commonmacro.ftl" as commonmacro>
<@common.moduleDiv titleName="周视图">
<#if memo.getId()?default('') == ''>
	<#assign isAdd = true>
<#else>
	<#assign isAdd = false>
</#if>	
<script>
  var isSubmit = false;
  $(function() {
    <#if !isAdd && memo.getTimeString()?default('') != ''>
	    $("#memoTimeInfo").val('${memo.getTimeString().substring(11,16)}');
	    <#if memo.getIsSmsAlarm()==1>
		    $("#mono_alarm_time").show();
		    $('#memo_alarm').attr("checked", true);
		    $("#mono_alarm_time").val('${memo.getAlarmTimeString()}');
	    </#if>
    </#if>
   
    $("#sava_meno_btn").click(function() {
		if(isSubmit){
			return;
		}
      var id = $("#memo_id").val();
      var memoTimeInfo = $("#memoTimeInfo").val();
      if(memoTimeInfo==null || memoTimeInfo==''){
        showMsgWarn("请选择时间");
        return ;
      }
      var v_today = $("#today").attr("value")+" "+memoTimeInfo+":00";
      $("#hidden_memo_time").attr("value",v_today);
      
      var isSmsAlarm = 0;
      if ($("#memo_alarm").attr("checked"))isSmsAlarm = 1;
      $("#hidden_memo_isSmsAlarm").attr("value",isSmsAlarm);
      
      var content = $("#memo_content").val();
      if (content == null || content == "") {
		   showMsgWarn("备忘录内容不能为空!");
	       return;
	  }
      var length = getAbsoluteLength(content);
      if(length>1000){
        showMsgWarn("内容长度不能大于1000个字符!");
        return ;
      }
      
      var time="";
      if($("#mono_alarm_time").val()!=""){
        time = $("#mono_alarm_time").val()+":00";
      }
      isSubmit = true;
      $("#hidden_date").attr("value",time);
      $("#saveBtn").attr("class", "abtn-unable");
      var memoUrl = _contextPath + "/office/desktop/memo/memo-saveOrUpdate.action";
      var options = {
          target : '#add_memo_form',
          url : memoUrl,
          success : showSuccess,
          dataType : 'json',
          clearForm : false,
          resetForm : false,
          type : 'post'
        };
      $("#add_memo_form").ajaxSubmit(options);
    });
    $('.ui-checkbox').unbind('click').click(function(){
		var inpt = $("#mono_alarm_time");
		if(!$(this).hasClass('ui-checkbox-current')){
			$(this).addClass('ui-checkbox-current').find('.chk').attr('checked','checked');
			inpt.val("");
        	inpt.show();
		}else{
			$(this).removeClass('ui-checkbox-current').find('.chk').removeAttr('checked');
			inpt.hide();
		};
	});
    $("#closeAddMon").click(function(e) {
      var parent = $("#parent_name").attr("value");
      if(parent=="weekmemo"){
        $("#modelLayer").show();
        $("#listWek").show();
      }else if(parent=="monthmemo"){
        $("#modelLayer").hide();
        $("#listMon").show();
        $("#modelLayer").show();
      }else{
        $("#modelLayer").hide();
      }
    });
    $('.close-top').click(function(){
		$(this).parents('.top-layer').hide();
		$('.table-kb td').removeClass('current')
	});
  });
  //操作提示
  function showSuccess(data) {
    if (data!=null && data!=''){
      showMsgError(data);
      $("#saveBtn").attr("class", "abtn-blue");
      isSubmit = false;
      return;
    }else{
      //showMsgSuccess("操作成功！", "提示", function(){
		  var parent = $("#parent_name").attr("value");
	      if(parent=="weekmemo"||parent=="monthmemo"){
	        frushMyMemo("showList",$("#today").attr("value"),10000);
	      }else{
	        frushMyMemo();
	      }
		//});
		return;
      
    }
  }
  
   function test(){
   		$("#persons").toggle();
   }
</script>
<div class="dt">
    <span class="close-top" id="closeAddMon">X</span>${date?string("M月d号（E）")}
</div>
<form action="" method="post" name="add_memo_form" id="add_memo_form">
<input type="hidden" id="memo_id" name="memo.id" value="${memo.getId()!}">
<input type="hidden" id="hidden_memo_isSmsAlarm" name="memo.isSmsAlarm" value="0">
<input type="hidden" id="hidden_date" name="date" value="">
<input type="hidden" id="hidden_memo_time" name="memo.time" value="${memo.send!}">

<input type="hidden" id="parent_name" value="${parent!}">
<input type="hidden" id="today" value="${date?string("yyyy-MM-dd")}">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<#if memo.send?default("0")=="0">
    <tr>
        <td class="tit" valign="top">内容：</td>
        <td><textarea class="area" id="memo_content" name="memo.content">${memo.getContent()!}</textarea></td>
    </tr>
    <tr>
        <td class="tit">时间：</td>
        <td><@common.datepicker class="input-txt" style="width:150px;" name="memoTimeInfo" id="memoTimeInfo"  notNull="false"
				   msgName="开始日期"  value="${(memo.getTime()?string('HH:mm'))!}" dateFmt="HH:mm"/>
        </td>
    </tr>
    <tr style="display:none;">
    	<th>&nbsp;</th>
        <td class="pt-10 pb-10">
        	<span class="ui-checkbox <#if memo.isSmsAlarm == 1>ui-checkbox-current</#if>">
        		<input type="checkbox" <#if memo.isSmsAlarm == 1>checked</#if> class="chk" id="memo_alarm" name="memo_alarm">短信提醒
        	</span>
        	</td>
    </tr>
    <tr>
        <td>&nbsp;</td>
        <td class="pb-10">
            <#if memo.isSmsAlarm == 1>
            	<@common.datepicker class="input-txt" style="width:150px;" name="mono_alarm_time" id="mono_alarm_time"  notNull="false"
			   msgName="开始日期"  value="${(memo.getSmsAlarmTime()?string('yyyy-MM-dd HH:mm'))!}" dateFmt="yyyy-MM-dd HH:mm"/>
        	<#else>
        		<@common.datepicker class="input-txt" style="width:150px;display:none;" name="mono_alarm_time" id="mono_alarm_time"  notNull="false"
			   msgName="开始日期"  value="${(memo.getSmsAlarmTime()?string('yyyy-MM-dd HH:mm'))!}" dateFmt="yyyy-MM-dd HH:mm"/>
        	</#if>
        </td>
    </tr>
    <#if sendMemo>
    <tr style="margin-top:4px;">
        	<th style="width:49px;">
            	<#if memo.userIds! !="">
	        		<span class="ui-checkbox send-sms-btn ui-checkbox-current" onclick="test();">
		            		<input type="checkbox" id="isSend" name="isSend" class="chk" value="true" checked="checked">推送
		            </span>
        		<#else>
	            	<span class="ui-checkbox send-sms-btn" onclick="test();">
	            		<input type="checkbox" id="isSend" name="isSend" class="chk" value="true">推送
	            	</span>
            	</#if>
            </th>
            <td colspan="3" <#if memo.userIds! =="">style="display:none;"</#if> id="persons">
		        	<@commonmacro.selectMoreUser idObjectId="userIds" nameObjectId="userNames" width=400 height=300>
			        	<input type="hidden" id="userIds" name="memo.userIds" value="${memo.userIds!}"/>
			        	<textarea id="userNames" name="userNames" cols="70" rows="4" class="text-area my-5" style="width:91%;padding:5px 1%;height:50px;" msgName="请假原因" maxLength="100">${memo.userNames!}</textarea>
		        	</@commonmacro.selectMoreUser>
		        </td>
            </tr>
            </#if>
    <tr>
        <td>&nbsp;</td>
        <td><a href="javascript:void(0);" class="abtn-blue" id="sava_meno_btn"><#if isAdd>创建<#else>保存</#if></a></td>
    </tr>
    
    <#else>
    <tr>
        <td class="tit" valign="top">内容：</td>
        <td><textarea class="area" id="memo_content" readOnly="ture" name="memo.content">${memo.getContent()!}</textarea></td>
    </tr>
    <tr>
        <td class="tit">时间：</td>
        <td><@common.datepicker class="input-txt" style="width:150px;" name="memoTimeInfo" id="memoTimeInfo" readonly="true"  notNull="false"
				   msgName="开始日期"  value="${(memo.getTime()?string('HH:mm'))!}" dateFmt="HH:mm"/>
        </td>
    </tr>
    <tr>
        <td>&nbsp;</td>
    </tr>
    </#if>
</table>
</form>
</@common.moduleDiv>