<#import "/common/htmlcomponent.ftl" as common>
<#import "/common/commonmacro.ftl" as commonmacro>
<@common.moduleDiv titleName="周重点工作详情">
<form id="outlineForm" name="outlineForm">
    <p class="tt"><a href="javascript:void(0);" class="close" onclick="closeDiv('#scheduleLayer');">关闭</a><span>周重点工作详情</span></p>
    <div class="wrap pa-10 schedule-layer-des" style="height:540px;width=980px;overflow-y:auto;overflow-x:auto;">
        <table border="0" cellspacing="0" cellpadding="0" class="table-edit mb-15" style="table-layout:fixed;">
		<#if periodList?exists && periodList?size gt 0>
		<#list periodList as item>
        	<tr class="tit-line" >
    			<th><div class="sort-wrap"><span>${item_index+1}</span></div>时间：</th>
    			<td>
                	<span class="time">${(item.calendarTime?string("yyyy-MM-dd HH:mm"))!}-${(item.endTime?string("yyyy-MM-dd HH:mm"))!}</span>
                	<#if item.isHasAuthToOperate>
                    <span class="opt"><a href="javascript:void(0);" onclick="doEdit('${item.id!}')" class="edit"></a><a href="javascript:void(0);" onclick="doDel('${item.id!}')" class="del"></a></span>
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
                            <th>创建科室：</th>
                            <td>${item.deptName!}</td>
                        </tr>
                    	<tr>
                            <th width="20%">内容：</th>
                            <!--<td style="table-layout:fixed;word-break:break-all;overflow:hidden;"><p><textarea  style="width:360px;" name="officeWorkOutline.content" id="officeWorkOutline.content" msgName="内容" notNull="true" class="text-area" maxlength="500" style="width:360px;">${item.content!}</textarea></p></td>-->
                            <td width="80%" style="table-layout:fixed;word-break:break-all;">${item.content!}</td>
                        </tr>
                	</table>
                </td>
            </tr>
            </#list>
            <#else>
            <tr>
            	<td colspan="2">没有数据！</td>
            </tr>
			</#if>
		</table>
    </div>
</form>	
</form>
<script>
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

	function doEdit(id){
		var url="${request.contextPath}/office/schedule/schedule-workOutlineAdd.action?officeWorkOutline.id="+id;
		openDiv("#scheduleLayer","#scheduleLayer .close,#scheduleLayer .submit,#scheduleLayer .reset",url,null,null,"1000px",function(){
			
		});
	}
	
	function doDel(id){
		if(confirm("确认要删除吗？")){
			$.getJSON("${request.contextPath}/office/schedule/schedule-workOutlineDel.action?officeWorkOutline.id="+id,null,function(data){
				if(!data.operateSuccess){
					showMsgError(data.promptMessage);
				}else{
					showMsgSuccess("删除成功！","提示",function(){
						reloadOutlineData();
					});
				}
			});
		}
	}
</script>
</@common.moduleDiv>