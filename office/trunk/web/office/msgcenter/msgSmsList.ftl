<#import "/common/htmlcomponent.ftl" as common>
<script>
function doDelete(){
    var ids = [];
	if(isCheckBoxSelect($("[name='checkid']")) == false){
		showMsgWarn("请先选择想要进行操作的数据！");
		return;
	}
	if(!confirm("确定要删除吗？")){
		return;
	}
	var i = 0;
	$("input[name='checkid'][checked='checked']").each(function(){
		ids[i] = $(this).val();
		i++;
	});
	$.ajax({
		type: "POST",
		url: "${request.contextPath}/office/msgcenter/msgcenter-msgSmsDelete.action",
		data: $.param( {deleteIds:ids},true),
		success: function(data){
			if(data != null && data!=''){
				showMsgError(data);
				return;
			}else{
				showMsgSuccess("删除成功！", "提示", function(){
					searchMsgSmsList();
				});
			}
		},
		dataType: "json",
		error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
	});
}

function doView(infoId){
	openDiv("#msgSmsLayer", "#msgSmsLayer .close,#msgSmsLayer .submit,#msgSmsLayer .reset", "${request.contextPath}/office/msgcenter/msgcenter-msgSmsView.action?officeSmsInfo.id="+infoId, null, null, "900px");
}
   	
</script>
<form id="dataform" name="dataform">
<table class="mailing-list">
	<tr>
		<td style="width:10%;" class="b t-center">&nbsp;</td>
		<td style="width:75%;" class="b">短信内容</td>
		<td style="width:15%;" class="b">发送时间</td>
	</tr>
	<#if officeSmsInfoList?size gt 0>
	<#list officeSmsInfoList as x>
	<tr class="read" style="word-break:break-all; word-wrap:break-word;">
    	<td class="t-center"><span class="ui-checkbox"><input type="checkbox" class="chk" name="checkid" value="${x.id!}"></span></td>
    	<td style="cursor:pointer;" onclick="doView('${x.id!}');"><@common.cutOff str='${x.msg!}' length=100/></td>
    	<td style="cursor:pointer;" onclick="doView('${x.id!}');">${x.sendTimeStr!}</td>
    </tr>
    </#list>
    <tr class="tfoot">
        <td colspan="4">
			<@common.Toolbar container="#msgSmsList">
				<span class="ui-checkbox ui-checkbox-all" data-all="no"><input type="checkbox" class="chk">全选</input></span>
				<a href="javascript:void(0);" id="btnDelete" class="abtn-blue"  onclick="doDelete()"> 删除</a>
			</@common.Toolbar>
        </td>
    </tr>
    <#else>
    	<tr>
	    	<td colspan="4" style="border-bottom-width:0px;">
	    		<p class="no-data mt-50 mb-50">还没有数据哦！</p>
	    	</td>
	    </tr>
    </#if>
</table>
</form>