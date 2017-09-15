<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showMsg />
<@htmlmacro.moduleDiv titleName="学生返校查询">
<#import "/common/commonmacro.ftl" as commonmacro>
<script>
function doExport(){
    var holidayId = $('#holidayId').val();
	var classid=$('#classid').val();
	if(holidayId==""){
		showPrompt("holidayId", "请先选择节假日");
		return;
	}
	if(classid==""){
		showPrompt("classid", "请先选择班级");
		return;
	}
	var backState=$('#backState').val();
	var str="?holidayId="+holidayId+"&classid="+classid+"&backState="+backState;
	var url = "${request.contextPath}/office/check/backSchoolAdmin-Export.action"+str;
	window.location = url;
}

function doChange(){
	var holidayId = $('#holidayId').val();
	var classid=$('#classid').val();
	if(holidayId==""){
		showPrompt("holidayId", "请先选择节假日");
		return;
	}
	if(classid==""){
		showPrompt("classid", "请先选择班级");
		return;
	}
	var backState=$('#backState').val();
	var str="?holidayId="+holidayId+"&classid="+classid+"&backState="+backState;
	var url = "${request.contextPath}/office/check/backSchoolAdmin-list.action"+str;
	load('#containerHolidayDiv',url);
}
$(function(){
	vselect();
	var holidayId = $('#holidayId').val();
	var classid=$('#classid').val();
	var backState=$('#backState').val();
	var str="?holidayId="+holidayId+"&classid="+classid+"&backState="+backState;
	var url = "${request.contextPath}/office/check/backSchoolAdmin-list.action"+str;
	load('#containerHolidayDiv',url);
});
</script>

	
<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">
	<div class="query-part fn-rel fn-clear promt-div">
		<@htmlmacro.showPrompt/>
		<div class="query-tt b">节假日名称：</div>
		<div class="select_box fn-left mr-10">
			<@htmlmacro.select style="width:100px;" valId="holidayId" valName="holidayId" txtId="holidayTxt" myfunchange="doChange">
				<a val="">--请选择--</a>
				<#list holidayList as item>
					<a val="${item.id}" <#if item.id == holidayId?default('')>class="selected"</#if>><span>${item.name!}</span></a>
				</#list>
			</@htmlmacro.select>
		</div>
		
		<@commonmacro.fuzzySelectDiv idObjectId="classid" nameObjectId="className" url="/common/getFuzzyClassSelectDivData.action" divName='班级' onclick="doChange" selectedValue="${classid!}">
		</@commonmacro.fuzzySelectDiv>
		
		<div class="query-tt b">返校状态：</div>
		<div class="select_box fn-left mr-10">
			<@htmlmacro.select style="width:100px;" valId="backState" valName="backState" txtId="backStateTxt" myfunchange="doChange">
				<a val=""><span>全部</span></a>
				<a val="1" <#if backState?exists && backState=='1'>class="selected"</#if>><span>已返校</span></a>
				<a val="0" <#if backState?exists && backState=='0'>class="selected"</#if>><span>未返校</span></a>
			</@htmlmacro.select>
		</div>
		<a href="javascript:doChange();" class="abtn-blue ml-10">查找</a>
		<a href="javascript:doExport();" class="abtn-blue fn-right">导出</a>
	    <div class="fn-clear"></div>
	</div>
</div>

<br/>
<div id="containerHolidayDiv"></div>
</@htmlmacro.moduleDiv>