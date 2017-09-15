<#--修改弹出层-->
<form id="form3">
<div class="popUp-layer" id="editLayer" style="display:none;width:600px;">
	<p class="tt"><a href="#" class="close">关闭</a><span>各子系统日志清理配置</span></p>
    <div class="wrap pa-10" style="overflow:auto;height:310px">
     
        <table border="0" cellspacing="0" cellpadding="0" class="public-table table-list">
            <thead>
                <tr>
                    <th>子系统</th>
                    <th>日志保留天数<span class="fn f12">(0代表不进行日志自动清理)</span></th>
                </tr>
            </thead>
            <tbody>
	            <#list logConfigList as x>
	            <tr>
				  	 <th>${x.subSystemName?default("")}：</th>
	                 <td>
	                     <input type="text" name="logConfigDtos[${x_index}].days" id="logConfigDtos[${x_index}].days" msgName="${x.subSystemName?default('')}日志保留天数" notNull="true" class="input-txt" style="width:140px;" title="${x.subSystemName}日志保留天数只能是0~${logConfigMaxValue}范围内非负整数" dataType="integer" minValue="0" maxValue="${logConfigMaxValue?default('1')}" value="${x.days?default("")}">
	                 </td>
	                 <input name="logConfigDtos[${x_index}].id" id="logConfigDtos[${x_index}].id" type="hidden" value="${x.id?default('')}">
			  	     <input name="logConfigDtos[${x_index}].subSystem" type="hidden" value="${x.subSystem?default('')}">
	            </tr>
	            </#list>
            </tbody>
        </table>
    </div>
    <p class="dd">
        <a class="abtn-blue" href="javascript:void(0)" onclick="saveform();">保存</a>
        <a class="abtn-blue" href="javascript:void(0)" onclick="reset();">重置</a><button type="reset" id="resetButton" style="display:none"/>
        <a class="abtn-blue reset ml-5" href="javascript:void(0)" onclick="$('#editLayer').jWindowClose();">取消</a>
    </p>
</div>
</form>

<script type="text/javascript">
    
    function reset(){
       $("#resetButton")[0].click();
    }

    function saveform(){
		if(checkAllValidate()){
			showSaveTip();
			var options = {
		       url:"${request.contextPath}/system/admin/platformInfoAdmin-remoteLogConfig-saveLogConfig.action?modID=${modID?default('')}", 
		       dataType : 'json',
		       clearForm : false,
		       resetForm : false,
		       type : 'post',
		       timeout : 5000,
		       error : showError,
		       success : showReply
		    };
		    $("#form3").ajaxSubmit(options);
		}
	}
	
	function showError(){
	    closeTip();
	    $('#editLayer').jWindowClose();
	    showMsgError("保存日志清理配置失败！");
	}
	
	function showReply(data){
	  closeTip();
	  $('#editLayer').jWindowClose();
	  var result = data.promptMessageDto;
	  if(result.operateSuccess) {
	      showMsgSuccess(result.promptMessage);
	  }else{
	      showMsgError(result.promptMessage);
	  }
	}
	
</script>