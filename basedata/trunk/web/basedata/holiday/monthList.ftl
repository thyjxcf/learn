<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#include "/common/handlefielderror.ftl">
<@htmlmacro.moduleDiv titleName="时间设置">
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script language="javascript">
	function doquery(){
	    var acadyear = document.getElementById("acadyear").value;
	    var semester = document.getElementById("semester").value;
	    var url="${request.contextPath}/basedata/holiday/monthSet.action?acadyear="+acadyear+"&semester="+semester;
	    load("#container",url);
	}
	var submit = false;
	function saveListInfo(){
		if(!checkAllValidate("#listForm")){
			return;
		}
		if(submit){
			return;
		}
		var options = {
		     url:'${request.contextPath}/basedata/holiday/monthSet-save.action', 
		     success : showReply,
		     dataType : 'json',
	           clearForm : false,
	           resetForm : false,
	           type : 'post'
	    	};
	    	submit = true;
		$('#listForm').ajaxSubmit(options);
	}
	function showReply(data){
		if(data && data != ''){
			submit = false;
			showMsgError(data);
		} else {
			showMsgSuccess('信息保存成功','',goBack2);
		}
	}
	function goBack2() {
	    load("#container","${request.contextPath}/basedata/holiday/monthSet.action?acadyear=${acadyear!}&semester=${semester!}&moon=${moon!}");
	}
	vselect();
</script>
<div class="popUp-layer" id="positionLayerAdd" style="display:none;width:480px;"></div>
<div class="query-builder">
	<div class="query-part fn-rel fn-clear">
	    <div class="query-tt b mt-5">学年：</div>
	    <div class="select_box fn-left">
		<@htmlmacro.select style="width:100px;" valId="acadyear" valName="acadyear" txtId="searchAcadyearTxt" myfunchange="doquery" >
			<#list acadyearList as item>
				<a val="${item}" <#if item == acadyear?default('')>class="selected"</#if>>${item!}</a>
			</#list>
		</@htmlmacro.select></div>
		
		<div class="query-tt b ml-10 mt-5">学期：</div><div class="select_box fn-left">
		<@htmlmacro.select style="width:100px;" valName="semester" valId="semester" myfunchange="doquery">
			<a val="1"  <#if semester?default("")=="1">class="selected"</#if>><span>第一学期</span></a>
			<a val="2"  <#if semester?default("")=="2">class="selected"</#if>><span>第二学期</span></a>
		</@htmlmacro.select></div>
</div>
</div>
<form id="listForm">
<input type="hidden" name="acadyear" value="${acadyear!}"/>
<input type="hidden" name="semester" value="${semester!}"/>
<@htmlmacro.tableList id="listTable" name="listTable" class="public-table table-list table-dragSort mt-15">
	<tr>
	    <th class="t-center">月份</th>
		<th class="t-center">开始周次</th>
		<th class="t-center">结束周次</th>
	</tr>
<#if dtolist?exists &&  (dtolist?size>0)>
	<#list dtolist as x>
	<tr>
	  	<td class="t-center">${x.month?default('')}
	  	<input type="hidden" name="dtolist[${x_index}].month" value="${x.month!}"/>
	  	</td>
	  	
	  	<td class="t-center">
	  		<div class="ui-select-box" style="width:100px;">
	                  <input type="text" class="ui-select-txt" value=""  readonly="readonly"  />
	                  <input type="hidden" name="dtolist[${x_index}].beginWeek" id="beginWeek${x_index}" value="${x.beginWeek!}" class="ui-select-value" notNull="true" />
	                  <a class="ui-select-close"></a>
	                  <div class="ui-option" myfunchange="" id="${x_index}">
	                  	<div class="a-wrap">
	                  	<#list 0..week as i>
	                      <a val="${i}" <#if x.beginWeek == i>class="selected"</#if>><span><#if i==0>--请选择--<#else>第${i}周</#if></span></a>
	                      </#list>
	                 		</div>
	                  </div>
	              </div>
	  	</td>
	  	<td class="t-center">
			<div class="ui-select-box" style="width:100px;">
	                  <input type="text" class="ui-select-txt" value=""  readonly="readonly"  />
	                  <input type="hidden" name="dtolist[${x_index}].endWeek" id="beginWeek${x_index}" value="${x.endWeek!}" class="ui-select-value" notNull="true" />
	                  <a class="ui-select-close"></a>
	                  <div class="ui-option" myfunchange="" id="${x_index}">
	                  	<div class="a-wrap">
	                  	<#list 0..week as i>
	                      <a val="${i}" <#if x.endWeek == i>class="selected"</#if>><span><#if i==0>--请选择--<#else>第${i}周</#if></span></a>
	                      </#list>
	                 		</div>
	                  </div>
	              </div>
		</td>
	</tr>
	</#list>
<#else>
	<tr>
		<td colspan="5"> <p class="no-data mt-20">还没有任何记录哦！</p></td>
	</tr>
</#if>
</@htmlmacro.tableList>
</form>
<#if dtolist?exists && dtolist?size gt 0>
<p class="t-center pt-30">
	<a href="javascript:void(0);" style="margin-top:10px;" id="saveBtn" onclick="saveListInfo();" class="abtn-blue-big">提交</a>
</p>
</#if>
</@htmlmacro.moduleDiv>