<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
$(document).ready(function(){
	reloadData('${calDto.rangeType?default('1')}');
});

// 切换视图
function toData(stype){
	$(obj).addClass('ui-radio-current').siblings('span').removeClass('ui-radio-current');
	var url="${request.contextPath}/office/schedule/schedule-toPersonal.action?calDto.creatorType=${calDto.creatorType?default('1')}&calDto.searchType="+stype;
	load("#scheduleDiv", url);
}

// 切换查询数据范围
function getData(){
	var rtype = $('#rangeType').val();
	var url="${request.contextPath}/office/schedule/schedule-toData.action?calDto.creatorType=${calDto.creatorType?default('1')}"
	+"&calDto.searchType=${calDto.searchType?default('1')}&calDto.rangeType="+rtype
	+"&calDto.calendarTime=${(calDto.calendarTime?string('yyyy-MM-dd'))?if_exists}";
	load("#calendarDiv", url);
}

// 日期查询
function changeDate(ope){
	var rtype = $('#rangeType').val();
	var url="${request.contextPath}/office/schedule/schedule-toPersonal.action?calDto.creatorType=${calDto.creatorType?default('1')}"
	+"&calDto.searchType=${calDto.searchType?default('1')}&calDto.rangeType="+rtype
	+"&calDto.calendarTime=${(calDto.calendarTime?string('yyyy-MM-dd'))?if_exists}&calDto.operate="+ope;
	load("#scheduleDiv", url);
}

function pickDate(){
	var ct =$('#calendarTime').val(); 
	<#if "2"==calDto.searchType?default('1')>
	ct += ('-01');
	</#if>
	var url="${request.contextPath}/office/schedule/schedule-toPersonal.action?calDto.creatorType=${calDto.creatorType?default('1')}"
	+"&calDto.searchType=${calDto.searchType?default('1')}&calDto.rangeType=1"
	+"&calDto.calendarTime="+ct;
	load("#scheduleDiv", url);
}

// 重新加载数据
function reloadData(rty){
	if(!rty){
		rty = $('#rangeType').val();
	}
	var url="${request.contextPath}/office/schedule/schedule-toData.action?calDto.creatorType=${calDto.creatorType?default('1')}"
	+"&calDto.searchType=${calDto.searchType?default('1')}&calDto.rangeType="+rty
	+"&calDto.calendarTime=${(calDto.calendarTime?string('yyyy-MM-dd'))?if_exists}&calDto.operate=${calDto.operate!}";
	load("#calendarDiv", url);
}

function toAdd(dastr,pe){
	if(!dastr){
		dastr = $('#calendarTime').val();
	}
	if(!pe){
		pe="";
	}
	var url="${request.contextPath}/office/schedule/schedule-toEdit.action?"
		+"calDto.creatorType=${calDto.creatorType!'1'}"
		+"&calDto.calendarTime="+dastr+"&calDto.period="+pe;
	openDiv("#scheduleLayer", "",url, false,"","600px");
}

function toDetails(dastr,pe){
	var rt = $('#rangeType').val();
	var url="${request.contextPath}/office/schedule/schedule-toDetails.action?calDto.creatorType=${calDto.creatorType?default('1')}"
		+"&calDto.rangeType="+rt+"&calDto.calendarTime="+dastr+"&calDto.period="+pe;
	openDiv("#scheduleListLayer", "",url, false,"","600px");//
}

function toDown(){
	var rt = $('#rangeType').val();
	var url="${request.contextPath}/office/schedule/schedule-toDown.action?calDto.creatorType=${calDto.creatorType?default('1')}"
		+"&calDto.rangeType="+rt+"&calDto.calendarTime=${(calDto.calendarTime?string('yyyy-MM-dd'))?if_exists}";
	location.href=url;
}
</script>
	<div class="power-notes-dt mt-10">
		<span class="fn-left mt-5">
			<span class="ui-radio<#if calDto.searchType?default('1')=='1'> ui-radio-current</#if>" data-name="a" onclick="toData('1');"><input type="radio" class="radio">周视图</span>
			<span class="ui-radio<#if calDto.searchType?default('1')=='2'> ui-radio-current</#if>" data-name="a" onclick="toData('2');"><input type="radio" class="radio">月视图</span>
        	<span class="ui-radio<#if calDto.searchType?default('1')=='3'> ui-radio-current</#if>" data-name="a" onclick="toData('3');"><input type="radio" class="radio">日视图</span>
		</span>
		<#if "1"==calDto.searchType?default('1')>
			<a href="javascript:void(0);" onclick="toDown();" class="abtn-blue fn-right ml-10">导出周视图</a>
		</#if>
		<#if "3"!=calDto.searchType?default('1')>
			<@common.select className="fn-right ml-10" style="width:100px;" valId="rangeType" valName="calDto.rangeType" txtId="rangeTypeTxt" myfunchange="getData">
				<a val="1" <#if calDto.rangeType?default('1')=='1'>class="selected"</#if>>个人</a>
				<#if hasDeptAuth && calDto.creatorType?default('1')!='3'><a val="2" <#if calDto.rangeType?default('1')=='2'>class="selected"</#if>>科室</a></#if>
				<#if hasUnitAuth><a val="3" <#if calDto.rangeType?default('1')=='3'>class="selected"</#if>>单位</a></#if>
			</@common.select>
		<#elseif calDto.creatorType?default('1')=='3'>
			<input type="hidden" id="rangeType" value="${calDto.rangeType!'1'}" name="calDto.rangeType">
			<#if hasUnitAuth>
				<a href="javascript:void(0);" onclick="toAdd();" class="abtn-orange fn-right">新建日志</a>
			</#if>
		<#else>
			<input type="hidden" id="rangeType" value="1" name="calDto.rangeType">
			<a href="javascript:void(0);" onclick="toAdd();" class="abtn-orange fn-right">新建日志</a>
		</#if>
		<div class="opt-wrap">
			<span class="page page-prev" onclick="changeDate(1);"></span>
			<input type="hidden" id="calendarTime" name="calDto.calendarTime" value="${(calDto.calendarTime?string('yyyy-MM-dd'))?if_exists}">
			<span class="time" id="timeSpan">
				<#if "1"==calDto.searchType?default('1')>
					${(calDto.calendarTime?string('yyyy-MM-dd'))?if_exists}至${(calDto.endTime?string('yyyy-MM-dd'))?if_exists}
				<#elseif "2"==calDto.searchType?default('1')>
					${(calDto.calendarTime?string('yyyy-MM'))?if_exists}
				<#elseif "3"==calDto.searchType?default('1')>
					<#--<@common.datepicker value="${(calDto.calendarTime?string('yyyy-MM-dd'))?if_exists}" id="calendarTime" style="width:100px;" onpicked="pickDate" />-->
					${(calDto.calendarTime?string('yyyy-MM-dd'))?if_exists}
				</#if>
			</span>
			<#if "2"==calDto.searchType?default('1')>
			<span class="time-img" id="timeImgSpan" onclick="WdatePicker({el:'calendarTime',dateFmt:'yyyy-MM',onpicked:pickDate});">
			</span>
			<#elseif "3"==calDto.searchType?default('1')>
			<span class="time-img" id="timeImgSpan" onclick="WdatePicker({el:'calendarTime',dateFmt:'yyyy-MM-dd',onpicked:pickDate});"></span>
			</#if>
			<span class="page page-next" onclick="changeDate(2);"></span>
		</div>
	</div>
	<div id="calendarDiv" />
</@common.moduleDiv>