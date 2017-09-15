<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.moduleDiv titleName="">
<script type="text/javascript"> 
vselect();
function edit(baseTeachPlaceId) {
   	var url = "${request.contextPath}/office/dutymanage/dutymanage-patrolPlaceEdit.action?officePatrolPlace.id="+baseTeachPlaceId;
   	openDiv("#positionLayerAdd", "",url, false,"","500px");
}

function newApply() {
   	var url = "${request.contextPath}/office/dutymanage/dutymanage-patrolPlaceEdit.action";
   	openDiv("#positionLayerAdd", "",url, false,"","500px");
}

function doDelete(id) {
		if(showConfirm("确定要删除该巡查地点")){
			$.getJSON("${request.contextPath}/office/dutymanage/dutymanage-patrolPlaceDelete.action",{officePatrolPlaceId:id},function(data){
				if(data.operateSuccess){
					showMsgSuccess(data.promptMessage,"",patrolPlace);
				}else{
					showMsgError(data.errorMessage);
					return;
				}
		   }).error(function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);});
		}
  	}

</script>  
    
<div class="query-builder-nobg mt-5" style="padding:0 0 5px 0;">
    <div class="query-part">
	    <a href="javascript:newApply();" class="abtn-orange-new fn-right">新增</a>
        <div class="fn-clear"></div>
    </div>
</div>
<div class="popUp-layer" id="positionLayerAdd" style="display:none;width:500px;"></div>
<form name="form1" id="form1" action="" method="post"> 
<input type="hidden" id="unitId" name="unitId" value="">  
<@htmlmacro.tableList id="tablelist mt-15">
	  <tr> 
	    <th>序号</th>
	    <th>巡查地点</th>
	    <th class="t-center">操作</th>
	  </tr> 
	  <#if officePatrolPlaces?exists && (officePatrolPlaces?size>0)>
	  <#list officePatrolPlaces as officePatrolPlace>   
	  <tr> 
	    <td>${officePatrolPlace_index+1}</td>
	    <td>${officePatrolPlace.placeName?default('')}</td>
	    <td class="t-center"><a href="javascript:edit('${officePatrolPlace.id}');"><img src="${request.contextPath}/static/images/icon/edit.png" alt="编辑"></a>
	    <a href="javascript:doDelete('${officePatrolPlace.id}')" class="ml-15"><img src="${request.contextPath}/static/images/icon/del2.png" alt="删除"></a></td>
	  </tr>  
	  </#list> 
	  <#else>
	  	<tr>
           <td colspan=3> <p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td>
    	</tr>
	  </#if>
   		
  </@htmlmacro.tableList>
<#if officePatrolPlaces?exists && (officePatrolPlaces?size>0)>
<@htmlmacro.Toolbar container="#dutyDiv">
</@htmlmacro.Toolbar>	
</#if>
</form>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>