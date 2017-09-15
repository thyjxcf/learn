<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showMsg />
<@htmlmacro.moduleDiv titleName="节假日设置">
<#import "/common/commonmacro.ftl" as commonmacro>

<p class="pub-operation">
    <a href="javascript:void(0);" id="btnAdd" onClick="doAdd();" class="abtn-orange-new">新增</a>
 </p>		
<@htmlmacro.tableList id="tablelist mt-15">
    <tr>
    	<th  width="16%">节假日名称</th>
        <th width="16%">开始时间</th>
    	<th width="16%">结束时间</th>
    	<th  class="t-center" width="16%">操作</th>
    </tr>
    <#if holidayList?exists && (holidayList?size>0)>
    <#list holidayList as holiday>
    <tr>
    	<td>${holiday.name?default('')}</td>
        <td>${(holiday.startDate?string('yyyy-MM-dd'))!}</td>
    	<td>${(holiday.endDate?string('yyyy-MM-dd'))!}</td>
    	<td class="t-center">
    	<a name="deletebutton" href="javascript:void(0);" onClick="doDelete('${holiday.id?default("")}')" class="ml-15"><img name="deleteimg" src="${request.contextPath}/static/images/icon/del2.png" alt="删除"></a>
	         
            <a  href="javascript:void(0);" onClick="doUpdate('${holiday.id?default("")}');" class="ml-15"><img name="deleteimg" src="${request.contextPath}/static/images/icon/edit.png" alt="修改"></a>
	              
    	</td>
    	
    </tr>
    </#list>
    <#else>
	   <tr><td colspan="10"> <p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td></tr>
	</#if>
</@htmlmacro.tableList>
<div class="popUp-layer" id="addDiv" style="display:none;width:450px;"></div>
<@htmlmacro.Toolbar container="#containerDiv" />
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>

<script type="text/javascript">
	function doAdd(){
		var url="${request.contextPath}/office/check/backSchoolAdmin-holidayEdit.action";
		openDiv("#addDiv", "#addDiv .close,#addDiv .reset", url, null, null, 400, vselect);
	}
	function doUpdate(id){
		var url="${request.contextPath}/office/check/backSchoolAdmin-holidayEdit.action?pageIndex="+'${page.pageIndex?default(1)}'+"&pageSize="+'${page.pageSize?default(20)}'+"&holidayInfo.id="+id;
		openDiv("#addDiv", "#addDiv .close,#addDiv .reset", url, null, null, 400, vselect);
	
	}
	function doDelete(id){
		
		var url="${request.contextPath}/office/check/backSchoolAdmin-holidayDelete.action";
		$.getJSON(url, {"holidayInfo.id":id}, function(data){
		if(!data.operateSuccess){
		   if(data.promptMessage!=null&&data.promptMessage!=""){
			   showMsgError(data.promptMessage);
			   return;
		   }
		}else{
			showMsgSuccess("删除成功！","",function(){
			  var url = "${request.contextPath}/office/check/backSchoolAdmin-holidayList.action?pageIndex="+'${page.pageIndex?default(1)}'+"&pageSize="+'${page.pageSize?default(20)}';
   			 load("#containerDiv", url);
			});
			return;
		}
	});
	}
</script>

</@htmlmacro.moduleDiv>