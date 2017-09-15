<#import "/common/htmlcomponent.ftl" as htmlmacro> 
<@htmlmacro.moduleDiv titleName="节假日编辑"> 

<form  name="editform" id="editform" enctype="multipart/form-data">
<input type="hidden" name="holidayInfo.id" value="${holidayInfo.id!}">
<input type="hidden" name="holidayInfo.unitId" value="${holidayInfo.unitId!}">
<input type="hidden" name="holidayInfo.creationTime" value="${(holiday.creationTime?string('yyyy-MM-dd'))!}">

<p class="tt"><a href="javascript:void(0);" class="close">关闭</a>
<span><#if holidayInfo.id?exists && holidayInfo.id?default("")!="">编辑<#else>新增</#if>
</span>
</p>
<div class="wrap pa-10">
	<div id="form">
	<table id="eidtTable" border="0" cellspacing="0" cellpadding="0" class="table-form">
		
		<tr>
		<th>节假日名称：</th>
		<td>
		<input type="text" msgName="节假日名称" class="input-txt fn-left" name="holidayInfo.name"  notNull="true" maxlength="30" value="${holidayInfo.name!}" style="width:280px;">
		<span class="c-orange fn-left mt-3 ml-10">*</span>
		</td>
		</tr>
		<tr>
		 <th>开始日期：</th>
		 <td>
		  <@htmlmacro.datepicker name="holidayInfo.startDate" notNull="true" msgName="开始日期" value="${((holidayInfo.startDate)?string('yyyy-MM-dd'))?if_exists}" size="20" dateFmt="yyyy-MM-dd" style="width:280px;"/>
		  <span class="c-orange fn-left mt-3 ml-10">*</span>
		   </td>  
		 </tr> 
		 <tr>
		 <th>结束日期：</th>
		 <td>
		  <@htmlmacro.datepicker name="holidayInfo.endDate"  notNull="true" msgName="结束日期" value="${((holidayInfo.endDate)?string('yyyy-MM-dd'))?if_exists}" size="20" dateFmt="yyyy-MM-dd" style="width:280px;"/>
		  <span class="c-orange fn-left mt-3 ml-10">*</span>
		   </td>  
		 </tr>                  
	</table>
	</div>
</div>
<p class="dd">
	<a class="abtn-blue submit" href="javascript:void(0);" onclick="doSave()">确定</a>
	<a class="abtn-blue reset ml-5" href="javascript:void(0);">取消</a>
</p>
</form>
<script>
vselect();
var isSubmit = false;
function doSave(){
	if(isSubmit){
		return false;
	}
	if(!checkAllValidate('#editform')){
		isSubmit=false;
		return false;
	}
	//起始日期不能大于结束日期
	var startTime=document.getElementsByName("holidayInfo.startDate")[0];
	var endTime=document.getElementsByName("holidayInfo.endDate")[0];
	if(compareDate(startTime.value,endTime.value)>0){
		isSubmit=false;
		addFieldError(startTime.name,'起始日期不可大于结束日期');
		return false;
	}
	isSubmit = true;
	var options = {
	       url:'${request.contextPath}/office/check/backSchoolAdmin-holidaySave.action', 
	       dataType : 'json',
	       clearForm : false,
	       resetForm : false,
	       type : 'post',
	       success : showReply
	    };
	try{
		$('#editform').ajaxSubmit(options);
	}catch(e){
		showMsgError('保存节假日失败！');
		isSubmit = false;	
	}
}
function showReply(data){
 	if(data.operateSuccess){
		showMsgSuccess(data.promptMessage,"",function(){
			 var url = "${request.contextPath}/office/check/backSchoolAdmin-holidayList.action?pageIndex="+'${page.pageIndex?default(1)}'+"&pageSize="+'${page.pageSize?default(20)}';
   			 load("#containerDiv", url);
		});
	}else{
		showMsgError(data.errorMessage);
	} 
	isSubmit = false;	
}

</script>
</@htmlmacro.moduleDiv>