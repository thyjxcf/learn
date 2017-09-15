<#import "/common/htmlcomponent.ftl" as common>
<script>
	//移动到
	$('.move-wrap .move-btn').click(function(e){
		e.preventDefault();
		$('.move-wrap .move-inner').hide();
		$(this).siblings('.move-inner').show();
	});
	$('.move-wrap .move-inner a').click(function(e){
		e.preventDefault();
		$(this).parent('.move-inner').hide();
	});
	
	/*自定义信息夹中内容还原*/
  function revertMessageFromFolder(folderDetailId) {
    $.getJSON(contextPath+"/office/msgcenter/msgcenter-revertMessageFromFolder.action", {
      "folderDetailId" : folderDetailId
    }, function(data) {
    	if(data != null && data!=''){
			showMsgError(data);
			return;
		}else{
			searchMsg();
		}
    });
  }
	
	function removeSelected(){
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
			url: "${request.contextPath}/office/msgcenter/msgcenter-removeMsgs.action",
			data: $.param( {deleteIds:ids,msgState:5},true),
			success: function(data){
				if(data != null && data!=''){
					showMsgError(data);
					return;
				}else{
					showMsgSuccess("删除成功！", "提示", function(){
						searchMsg();
					});
				}
			},
			dataType: "json",
			error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
		});
	}
	frameAuto();
</script>
<form id="dataform" name="dataform">
<table class="mailing-list">
	<#if officeMsgFolderDetails?size gt 0>
	<#list officeMsgFolderDetails as x>
	<tr class="read">
    	<td style="width:5%;"><span class="ui-checkbox"><input type="checkbox" class="chk" name="checkid" value="${x.id!}"></span></td>
    	<td style="width:60%;cursor:pointer;" onclick="viewMsg('${x.id!}',5);" title="${x.title?html}"><@common.cutOff str='${x.title?html}' length=40/></td>
    	<td style="width:15%;cursor:pointer;" onclick="viewMsg('${x.id!}',5);">${x.dateStr!}</td>
        <td style="width:10%;cursor:pointer;" onclick="viewMsg('${x.id!}',5);">
			<#if x.referenceState == 1>
				草稿箱
			<#elseif x.referenceState == 2>
				发件箱
			<#elseif x.referenceState == 3>
				收件箱
			</#if>
		</td>
        <td style="width:10%;">
        <#if x.isCopy == 0>
			<a href="javascript:void(0);" onclick="revertMessageFromFolder('${x.id}');">还原</a>
		<#else>
			&nbsp;
		</#if>
		</td>
    </tr>
    </#list>
    <tr class="tfoot">
        <td colspan="5">
        	<@common.Toolbar container="#msgFolderList">
				<span class="ui-checkbox ui-checkbox-all" data-all="no"><input type="checkbox" class="chk">全选</span>
		        <a class="abtn-blue" href="javascript:void(0);" onclick="removeSelected();">删除</a>
		        <span class="move-wrap">
		        	<a href="javascript:void(0);" class="move-btn">移动到</a>
		            <#if officeMsgFolders?size gt 0>
		            <span class="move-inner" style="bottom:38px;">
						<#list officeMsgFolders as x>
  		             		<a href="javascript:void(0);" onclick="turnToFolder('${x.id!}',5);" title="${x.folderName!}"><@common.cutOff str='${x.folderName!}' length=7/></a>
    					</#list>
                    </span>
                    </#if>
		        </span>
	        </@common.Toolbar>
        </td>
    </tr>
    <#else>
    	<tr>
	    	<td colspan="5" style="border-bottom-width:0px;">
	    		<p class="no-data mt-50 mb-50">还没有数据哦！</p>
	    	</td>
	    </tr>
    </#if>
</table>
</form>