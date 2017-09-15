<#import "/common/htmlcomponent.ftl" as common />
<@common.showPrompt/>
<@common.moduleDiv titleName="">
<script type="text/javascript">
function add(id){
   	var url="${request.contextPath}/office/meetingmanage/receptionmanage-receptionManageAdd.action?id="+id;
   	openDiv("#classLayer","#classLayer .close,#classLayer .submit,#classLayer .reset",url,null,null,"200px",function(){
   		var ss= jQuery("#valId").val();
		if(ss=="true"){
		 	jQuery("#ycy").show();
	 	}else{
		 	jQuery("#ycy").hide();
		 	jQuery("#personNumber").attr("notNull","false");
 	        jQuery("#standard").attr("notNull","false");
		}
   	});
   	
   }
 function singleDelete(id){
  if(!showConfirm('您确认要删除吗？')){
		return;
	}
	$.ajax({
		type: "POST",
		url: "${request.contextPath}/office/meetingmanage/receptionmanage-receptionManageDelete.action?id="+id,
		success: function(data){
			if(!data.operateSuccess){
					showMsgError(data.errorMessage);
					return;
				}else{
					showMsgSuccess("删除成功", "提示", function(){
						load("#contectDiv", "${request.contextPath}/office/meetingmanage/receptionmanage-receptionManageList.action");
					});
					return;
				}
		},
		dataType: "json",
		error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
	});
  
  }
 function doTimeChange(){
   var startTime = jQuery("#startTime").val();
   var endTime =jQuery("#endTime").val();
   if(startTime!=''&&endTime!=''){
		var re = compareDate(startTime,endTime);
		if(re==1){
			showMsgError("结束时间不能小于开始时间！");
			return;
		}
	}
	
	var url="${request.contextPath}/office/meetingmanage/receptionmanage-receptionManageList.action?startTime="+startTime+"&endTime="+endTime;
	load("#contectDiv", url);
}
</script>

<div class="query-builder" >
<div class="query-part">
    	<div class="query-tt">开始日期：</div>
         <@common.datepicker name="startTime" id="startTime" style="width:120px;" value="${(startTime?string('yyyy-MM-dd'))?if_exists}" dateFmt='yyyy-MM-dd'/>
	    <div class="query-tt ml-10">结束日期：</div>
         <@common.datepicker name="endTime" id="endTime" style="width:120px;" value="${(endTime?string('yyyy-MM-dd'))?if_exists}" dateFmt='yyyy-MM-dd'/>

	      <a href="javascript:void(0);" onclick="doTimeChange();" class="abtn-blue fn-left ml-20">查找</a>
        	<a href="javascript:void(0);" class="abtn-orange-new fn-right" onclick="add('');">新建接待</a>
		<div class="fn-clear"></div>
    </div>
	
</div>
  
<@common.tableList id="listTable" name="listTable" class="public-table table-list table-dragSort mt-15" style="table-layout:fixed">
	<tr>
		<th  width="12%">接待时间</th>
		<th  width="10%">接待人</th>
		<th  width="8%">是否用餐</th>
		<th  width="8%">是否住宿</th>
		<th  width="22%">接待地点</th>
		<th  width="22%">内容</th>
		<th class="t-center" width="10%">操作</th>
	</tr>
<#if officeReceptionList?exists &&  (officeReceptionList?size>0)>
	<#list officeReceptionList as x>
	<tr>
	  <td  >${(x.receptionDate?string('yyyy-MM-dd'))?if_exists}</td>
	  <td>${x.receptionUserName?default('')}</td>
	  <td  >
		<#if x.isDining>是<#else>否</#if>	</td>
	  	<td><#if x.isAcommodation>是<#else>否</#if>	</td>
		<td><@common.cutOff4List str="${x.place?default('')}" length=17 /></td>
	  	<td><@common.cutOff4List str="${x.content?default('')}" length=17 /></td>
	  	<td class="t-center"><a href="javascript:add('${x.id}');"><img src="${request.contextPath}/static/images/icon/edit.png" alt="编辑"></a><a href="javascript:singleDelete('${x.id}');" class="ml-15"><img src="${request.contextPath}/static/images/icon/del2.png" alt="删除"></a></td>
	</tr>
	</#list>
<#else>
	<tr>
		<td colspan="7"> <p class="no-data mt-20">还没有任何记录哦！</p></td>
	</tr>
</#if>
</@common.tableList>			
    

<@common.Toolbar container="#contectDiv"/>
<div class="popUp-layer" id="classLayer" style="display:none;width:800px;"></div>	
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>