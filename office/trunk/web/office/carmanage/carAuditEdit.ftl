<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.showPrompt/>
<script type="text/javascript">
function getCursorPos(e){
	var mousePos = mouseCoords(e); 
	var cursorX = mousePos.x+150;  //这个100是下拉框的长度，自行调整吧
	var cursorY = mousePos.y-50;
	$('#cursorLayer').show().css({'top':cursorY,'left':cursorX});
}
function mouseOver(event,carId){
	var url="${request.contextPath}/office/carmanage/carmanage-carUseInfo.action?carId="+carId;
	load("#cursorLayer",url);
	getCursorPos(event);
}
function mouseOut(){
	$('#cursorLayer').hide();
}
function mouseMove(ev) 
{ 
	ev= ev || window.event; 
	var mousePos = mouseCoords(ev); 
} 

function mouseCoords(ev) 
{ 
	if(ev.pageX || ev.pageY){ 
	return {x:ev.pageX, y:ev.pageY};
	} 
	return {
	x:ev.clientX + document.body.scrollLeft - document.body.clientLeft, 
	y:ev.clientY + document.body.scrollTop - document.body.clientTop 
	};
} 
</script>
<@htmlmacro.moduleDiv titleName="单位审核">
<div id="cursorLayer" style="display:none;position:absolute;top:100px;left:100px;width:500px;z-index:9999;background:#FFFFFF;border:1px solid #000;"></div>
<div id="carAuditEditContainer">
<form name="carAuditForm" id="carAuditForm">
<input type="hidden" name="officeCarApply.id" value="${officeCarApply.id!}"/>
<input type="hidden" name="officeCarApply.state" id="state"/>
<@htmlmacro.tableDetail divClass="table-form" id="auditTable">
    <tr>
        <th colspan="4" style="text-align:center;">申请信息</th>
    </tr>
    <tr>
        <th style="width:20%">申请人：</th>
        <td colspan="3" style="width:80%">
        	${officeCarApply.applyUserName!}
        </td>
    </tr>
     <tr>
        	<th style="width:20%">乘车联系人：</th>
            <td style="width:30%">
            	${officeCarApply.linkUserName!}
            </td>
            <th style="width:20%">手机号码：</th>
            <td style="width:30%">
            	${officeCarApply.mobilePhone!}
            </td>
        </tr>
    <tr>
        <th>乘车人数：</th>
        <td>
        	${officeCarApply.personNumber!}
        </td>
        <th>用车时间：</th>
        <td>
        	${(officeCarApply.useTime?string('yyyy-MM-dd HH:mm'))!} (${officeCarApply.xinqi!})
        </td>
    </tr>
    <tr>
        <th>目的地：</th>
        <td>
        	${officeCarApply.carLocation!}
        </td>
        <th>是否往返：</th>
        <td>
        	<#if officeCarApply.isGoback?exists && officeCarApply.isGoback>
        		是
        	<#else>
        		否
        	</#if>
        </td>
    </tr>
    <tr>
        <th>是否需要候车：</th>
        <td>
        	<#if officeCarApply.isNeedWaiting?exists && officeCarApply.isNeedWaiting>
        		是
        	<#else>
        		否
        	</#if>
        </td>
        <th>接返时间：</th>
        <td>
        	${(officeCarApply.waitingTime?string('yyyy-MM-dd HH:mm'))!} <#if officeCarApply.isNeedWaiting?exists && officeCarApply.isNeedWaiting>(${officeCarApply.xinqi2!})</#if>
        </td>
    </tr>
    <tr>
        <th>用车事由：</th>
        <td colspan="3" style="word-break:break-all; word-wrap:break-word;">
        	${officeCarApply.reason!}
        </td>
    </tr>
    <tr>
        <th colspan="4" style="text-align:center;">单位审核&nbsp;&nbsp;<a href="javascript:void(0);" id="addDriver">+ 追加车辆驾驶员</a></th>
    </tr>
    <tr>
        <th>车辆范围：</th>
        <td colspan="3">
        	<@htmlmacro.select style="width:150px;" valName="officeCarApply.area" valId="area" txtId="areaTxtId" myfunchange="doChangeCarScope">
				<a val="">请选择</a>
				<#if officeSubsidys?exists && officeSubsidys?size gt 0>
            		<#list officeSubsidys as x>
            			<a val="${x.id!}" <#if x.id == officeCarApply.area?default('')>class="selected"</#if>>${x.scope!}</a>
            		</#list>
            	</#if>
			</@htmlmacro.select>
        </td>
    </tr>
     <tr id="carSubsidyDiv" style="display:none;">
        <th>车辆补贴：</th>
        <td colspan="3">
				<#if officeSubsidys?exists && officeSubsidys?size gt 0>
            		<#list officeSubsidys as x>
            			<input id="${x.id}" type="text" class="input-txt" style="width:140px; <#if x.id != officeCarApply.area?default('')>display:none;</#if>" value="${x.subsidy?string('0.##')}" readonly />
            		</#list>
            		</#if>
        </td>
    </tr>
    
        <tr>
        <th><span class="c-orange mr-5">*</span>是否加班：</th>
        <td colspan="3">
        	<@htmlmacro.select style="width:150px;" valName="officeCarApply.isOvertime" valId="isOvertime" myfunchange="doChangeOvertime">
				<a val="true" <#if officeCarApply.isOvertime?default(false)> class="selected"</#if>><span>是</span></a>
                		<a val="false" <#if !officeCarApply.isOvertime?default(false)> class="selected"</#if>><span>否</span></a>
		</@htmlmacro.select>
        </td>
    </tr>
     <tr id="overtimeDiv" style="display:none;">
        <th>加班天数：</th>
        <td colspan="3">
            	<input name="officeCarApply.overtimeNumber" type="text" class="input-txt" style="width:150px;" maxLength="5" dataType="float" maxValue="999" minValue="0" msgName="加班天数" value="${officeCarApply.overtimeNumber?string('0.#')}" />
        </td>
    </tr>
    
    <tr>
        <th><span class="c-orange mr-5">*</span>车牌号码：</th>
        <td>
        	<@htmlmacro.select style="width:150px;" valName="carIds" valId="carIds" txtId="carTxtId" notNull="true">
				<a val="">请选择</a>
				<#if officeCarInfos?exists && officeCarInfos?size gt 0>
            		<#list officeCarInfos as x>
            			<a val="${x.id!}" <#if officeCarApply.carId?default('')==x.id> class="selected"</#if> onmouseover="mouseOver(event,'${x.id!}');" onmouseout="mouseOut();" title="${x.carNumber!}">${x.carNumber!}</a>
            		</#list>
            	</#if>
			</@htmlmacro.select>
        </td>
        <th>驾驶员：</th>
        <td>
        	<@htmlmacro.select style="width:150px;" valName="driverIds" valId="driverIds" txtId="driverTxtId">
				<a val="">请选择</a>
				<#if officeCarDrivers?exists && officeCarDrivers?size gt 0>
            		<#list officeCarDrivers as x>
            			<a val="${x.id!}" title="${x.driverName!}">${x.driverName!}</a>
            		</#list>
            	</#if>
			</@htmlmacro.select>
        </td>
    </tr>
    <tr id="needAddTr">
        <th><span class="c-orange mr-5">*</span>审核意见：</th>
        <td colspan="3">
        	<textarea name="officeCarApply.remark" id="remark" cols="70" rows="4" class="text-area my-5" style="width:80%;padding:5px 1%;height:50px;" notNull="true" msgName="审核意见" maxLength="200">同意</textarea>
        </td>
    </tr>
    <tr>
    	<td colspan="4" class="td-opt">
		    <a class="abtn-blue ml-5" href="javascript:void(0);" onclick="auditInfo(3);">通过</a>
		    <a class="abtn-blue ml-5" href="javascript:void(0);" onclick="auditInfo(4);">不通过</a>
		    <a class="abtn-blue ml-5" href="javascript:void(0);" onclick="back();">返回</a>
        </td>
    </tr>
</@htmlmacro.tableDetail>
</form>
</div>
<table id="copyTr" style="display:none;">
	<tr>
        <th><span class="c-orange mr-5">*</span>车牌号码：</th>
        <td>
        	<@htmlmacro.select style="width:150px;" valName="carIds" valId="carIds" txtId="carTxtId" notNull="true">
				<a val="">请选择</a>
				<#if officeCarInfos?exists && officeCarInfos?size gt 0>
            		<#list officeCarInfos as x>
            			<a val="${x.id!}" onmouseover="mouseOver(event,'${x.id!}');" onmouseout="mouseOut();"  title="${x.carNumber!}">${x.carNumber!}</a>
            		</#list>
            	</#if>
			</@htmlmacro.select>
        </td>
        <th>驾驶员：</th>
        <td>
        	<@htmlmacro.select style="width:150px;" valName="driverIds" valId="driverIds" txtId="driverTxtId">
				<a val="">请选择</a>
				<#if officeCarDrivers?exists && officeCarDrivers?size gt 0>
            		<#list officeCarDrivers as x>
            			<a val="${x.id!}" title="${x.driverName!}">${x.driverName!}</a>
            		</#list>
            	</#if>
			</@htmlmacro.select>
			&nbsp;&nbsp;&nbsp;
			<a href="javascript:void(0);" class="del delGroupTr"><img src="${request.contextPath}/static/images/icon/del2.png" alt="删除"></a>
        </td>
    </tr>
</table>
<script type="text/javascript">
$z_Index = 999;
var oldArea = "";
$(document).ready(function(){
		<#if officeSubsidys?exists && officeSubsidys?size gt 0>
		<#list officeSubsidys as x>
			<#if x.id == officeCarApply.area?default('')>
				oldArea = '${x.id!}';
			</#if>
		</#list>
		</#if>
	$('#addDriver').click(function(e){
		e.preventDefault();
		$("#needAddTr").before($($('#copyTr tr').clone(false)));
		doFalsh();
		$(".ui-select-box").each(function(){
			i=--$z_Index;
			$(this).css("z-index",i);
		});
	});
	function doFalsh(){
		$(".delGroupTr").unbind("click").click(function(e){
			e.preventDefault();
			var index = $(this).parents("tr").index();
			$("#auditTable tr:eq("+index+")").remove();
		});
	}
	doFalsh();
});

var isSubmit = false;
function auditInfo(state){
	if(isSubmit){
		return false;
	}
	if(!confirm("确定要审核吗？")){
		return false;
	}
	if(state == 3){
		if(!checkAllValidate("#carAuditEditContainer")){
			return false;
		}
		var carIds = document.getElementsByName("carIds");
		//排除隐藏块里面的
		if(carIds.length > 2){
			var flag = false;
			for(var i=0;i<carIds.length-1;i++){
				for(var j=i+1;j<carIds.length-1;j++){
					if(carIds[i].value==carIds[j].value){
						flag = true;
						break;
					}
				}
				if(flag){
					break;
				}
			}
			if(flag){
				showMsgWarn("车牌号码不能相同");
				return false;
			}
			var driverIds = document.getElementsByName("driverIds");
			for(var i=0;i<driverIds.length-1;i++){
				for(var j=i+1;j<driverIds.length-1;j++){
					if(driverIds[i].value==null||driverIds[i].value==''){
						continue;
					}
					if(driverIds[i].value==driverIds[j].value){
						flag = true;
						break;
					}
				}
				if(flag){
					break;
				}
			}
			if(flag){
				showMsgWarn("驾驶员不能相同");
				return false;
			}
		}
	}else{
		var remark = $("#remark").val();
		if(remark == null ||　remark == ''){
			showMsgWarn("审核意见不能为空！");
			return;
		}
	}
	$("#state").val(state);
	isSubmit = true;
	var carAuditUrl = "${request.contextPath}/office/carmanage/carmanage-auditInfo.action";
	var options = {
      target : '#carAuditForm',
      url : carAuditUrl,
      success : showSuccess,
      dataType : 'json',
      clearForm : false,
      resetForm : false,
      type : 'post'
    };
  	$("#carAuditForm").ajaxSubmit(options);
}

function showSuccess(data) {
    if (data!=null && data!=''){
      showMsgError(data);
      isSubmit = false;
      return;
    }else{
        showMsgSuccess("审核成功！", "提示", function(){
		  doSearch();
		});
		return;
    }
}

function doChangeCarScope(){
	var area = $("#area").val();
	if(oldArea && oldArea !=""){
		$("#" + oldArea).hide();
	}
  	if(area && area != ''){
  		$("#carSubsidyDiv").show();
  		oldArea = area;
  		$("#" + area).show();
  	}else{
  		$("#carSubsidyDiv").hide();
  	}
}
function doChangeOvertime(){
	var isOvertime = $("#isOvertime").val();
  	if('true'==isOvertime){
  		$("#overtimeDiv").show();
  	}else{
  		$("#overtimeDiv").hide();
  	}
}
function back(){
	doSearch();
}
</script>

</@htmlmacro.moduleDiv>