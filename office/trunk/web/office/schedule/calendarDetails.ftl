<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
function toEdit(id){
	var url="${request.contextPath}/office/schedule/schedule-toEdit.action?cal.id="+id;
	openDiv("#scheduleLayer", "",url);
}

function toDel(id){
	if(!confirm('确定要删除该记录吗？')){
		return;
	}
	$.ajax({
		type: "POST",
		url: "${request.contextPath}/office/schedule/schedule-delCal.action",
		data: $.param( {"cal.id":id},true),
		success: function(data){
			if(data){
				var suc = data.operateSuccess;
				if(suc){
					showMsgSuccess(data.promptMessage,"",reloadData);
				} else {
					showMsgError(data.errorMessage);
				}
			}else{
				showMsgError("删除失败！");
				return;
			}
		},
		dataType: "json",
		error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
	});
}
</script>
<form id="setAddForm" name="setAddForm">
    <p class="tt"><a href="javascript:void(0);" class="close" onclick="closeDiv('#scheduleListLayer');">关闭</a><span>日志安排详情</span></p>
    <div class="wrap pa-10" style="height:540px;width=980px;overflow-y:auto;overflow-x:auto;">
        <table border="0" cellspacing="0" cellpadding="0" class="table-edit mb-15" style="table-layout:fixed;">
        	<#if cals?exists && cals?size gt 0>
        	<#list cals as item>
        	<tr class="tit-line">
    			<th><div class="sort-wrap"><span>${item_index+1}</span></div>时间：</th>
    			<td>
                	<span class="time">${(item.calendarTime?string('yyyy-MM-dd HH:mm'))?if_exists}至${(item.endTime?string('yyyy-MM-dd HH:mm'))?if_exists}</span>
                    <#if item.creator?default('') == loginInfo.user.id!>
                    <span class="opt"><a href="javascript:void(0);" onclick="toEdit('${item.id!}');" class="edit"></a><a href="javascript:void(0);" onclick="toDel('${item.id!}');" class="del"></a></span>
                    </#if>
                </td>
    		</tr>
            <tr class="open-inner" style="display:none;">
            	<td colspan="2">
                	<table style="table-layout:fixed;">
                    	<tr>
                            <th width="20%">地址：</th>
                            <td width="80%">${item.place!}</td>
                        </tr>
                    	<tr>
                            <th>创建人：</th>
                            <td>${item.creatorName!}</td>
                        </tr>
                    	<tr>
                            <th width="20%">内容：</th>
                            <td  width="80%" style="table-layout:fixed;word-break:break-all;">${item.content!}</td>
                        </tr>
                	</table>
                </td>
            </tr>
            </#list>
            <#else>
            <tr>
				<td colspan="1"><p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
			<tr>
            </#if>
		</table>
    </div>
</form>	
<script type="text/javascript">
$(document).ready(function(){
	$('tr.tit-line').click(function(){
		if($(this).next('tr.open-inner').css("display")=='table-row'){
			$(this).next('tr.open-inner').hide();
		}else{
			$(this).next('tr.open-inner').show();
		}
		$(this).next('tr.open-inner').siblings('tr.open-inner').hide();
		$(this).find('.opt').show();
	});
});	
</script>	
</@common.moduleDiv>