<#import "/common/htmlcomponent.ftl" as htmlmacro>
<script>
	function backToHistory () {
		var actionParam =  "?objectName=${objectName?default('')}&importType=${importType!}&url="+encodeURIComponent('${url}')+"&userDefinedUrl=${userDefinedUrl!}";
		load("#importDiv","${request.contextPath}/${importHistoryUrl!}.action"+actionParam);
	}
	
	function deleteByIds(){
		var	str = "objectName=${objectName?default('')}&importType=${importType!}&url="+encodeURIComponent('${url}')+"&covered=${covered!}";
		var ids =[];
		var i = 0;
		$("[name='checkid'][checked='checked']:input").each(function(){
			ids[i] = $(this).val();
			i++;
		});
		if(ids.length == 0){
			showMsgError("没有选要删除的行，请先选择！");
			return;
		}
		if(showConfirm('您确认要删除信息吗？')){
			$.ajax({
				type: "POST",
				url: "${request.contextPath}/${namespace!}/topicAdmin-import-viewRecordDelete.action",
				data: $.param( {ids:ids},true),
				success: function(data){
						if(data.operateSuccess){
	   					showMsgSuccess(data.promptMessage,"",function(){
	   						load("#topicListList","${request.contextPath}/${namespace!}/topicAdmin-import-viewRecordList.action?"+str);
	   					});
		   			}else{
		   				showMsgError(data.errorMessage);
		   			}
				},
				dataType: "json",
				error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
			});
		}
	}
	function deleteById(id){
		var	str = "?objectName=${objectName?default('')}&importType=${importType!}&url="+encodeURIComponent('${url}')+"&covered=${covered!}";
		var ids =[];
		ids[0]=id;
		if(showConfirm('您确认要删除已保存的信息吗？')){
			$.getJSON("${request.contextPath}/${namespace!}/topicAdmin-import-viewRecordDelete.action?ids="+ids, {
				}, function(data){
					if(data.operateSuccess){
	   					showMsgSuccess(data.promptMessage,"",function(){
	   						load("#topicListList","${request.contextPath}/${namespace!}/topicAdmin-import-viewRecordList.action?"+str);
	   					});
		   			}else{
		   				showMsgError(data.errorMessage);
		   			}
			}).error(function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);
			});
		}
	}
	
	$(document).ready(function(){
		var	str = "?objectName=${objectName?default('')}&importType=${importType!}&url="+encodeURIComponent('${url}')+"&covered=${covered!}";
		load("#topicListList","${request.contextPath}/${namespace!}/topicAdmin-import-viewRecordList.action?"+str);
	})
	
	
</script>
<div id="contectListDiv">
<div class="fn-clear pb-15">
   <p class="fn-right t-right pt-15">
        <a href="javascript:void(0);" onclick="backToHistory();" class="abtn-blue-big">返回</a>
    </p>
</div>
<div id="topicListList"></div>
</div>
