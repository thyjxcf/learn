<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<script>

</script>
<@htmlmacro.tableDetail class="public-table table-list table-list-edit">
 <input type="hidden" id="classId" name="classId" value="${classId?default('')}"/>
    <tr>
    	<th width="50%">模块名称</th>
    	<th width="50%">手机端是否启用</th>
    </tr>
    <#if applist?exists && (applist?size>0)>
		<#list applist as ent>
    	<tr>
			<td>
		    	${ent.moduleName!}
		    </td>
		    <td>
			    <span onclick="doClick(1, '${ent.unitId!}', '${ent.type!}');" class="ui-radio <#if ent.isUsing?default(false)>ui-radio-current</#if>" data-name="isUsing_${ent.type!}" style="margin-right:5px;">
			    	<input type="radio" name="isUsing_${ent.type!}"  class="radio" <#if ent.isUsing?default(false)>checked="checked"</#if> value="1">启用
		    	</span>
		         <span onclick="doClick(0, '${ent.unitId!}', '${ent.type!}');" class="ui-radio <#if !ent.isUsing?default(false)>ui-radio-current</#if>" data-name="isUsing_${ent.type!}" style="margin-right:5px;">
		         	<input type="radio" name="isUsing_${ent.type!}" class="radio" <#if !ent.isUsing?default(false)>checked="checked"</#if> value="0">不启用
		         </span>
	  	  	</td>
    	</tr>
  	  	</#list>
	<#else>
	   <tr><td colspan="6"> <p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td></tr>
	</#if>
</@htmlmacro.tableDetail>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script>
function doClick(isUsing, unitId, type){
	var curVal = $("input[type='radio']:checked").val();
	if(isUsing != curVal){
		var _isUsing = false;
		if(1 == isUsing){
			_isUsing = true;
		}
		showSaveTip();
		$.ajax({
		type: "POST",
		url: "${request.contextPath}/office/basic/appAuth-save.action",
		data: $.param( {"app.unitId":unitId,"app.type":type,"app.isUsing":_isUsing},true),  
		success: function(data){
			closeTip();
			if(!data.operateSuccess){
				if(data.errorMessage!=null&&data.errorMessage!=""){
				   showMsgError(data.errorMessage);
				   return;
			   }else{
			   	   showMsgError(data.promptMessage);
				   return;
			   }
			}else{
				showMsgSuccess("保存成功");
			}
		},
		dataType: "json",
		error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
	   }); 
	}
}
</script>
</@htmlmacro.moduleDiv>