<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="">
<script>
	
	$(function(){
		vselect();
		reloadOutlineData();
	});
	
	//radio click
	function changeFrame(type){
		$(obj).addClass('ui-radio-current').siblings('span').removeClass('ui-radio-current');
		switch(type){
			case "1":
				load("#scheduleDiv","${request.contextPath}/office/schedule/schedule-workOutline.action?isWeek=true&isMonth=false&isDay=false");
				break;
			case "2":
				load("#scheduleDiv","${request.contextPath}/office/schedule/schedule-workOutline.action?isWeek=false&isMonth=true&isDay=false");
				break;
			case "3":
				load("#scheduleDiv","${request.contextPath}/office/schedule/schedule-workOutline.action?isWeek=false&isMonth=false&isDay=true");
				break;
			default:
				document.write("There id no type!");
				break;
		}
	}
	
	//导出周视图
	function exportWeek(){
		var str="";
		var unitordept=$("#unitordept").val();
		str+="?unitordept="+unitordept+"&officeCalendarDto.calendarTime=${(officeCalendarDto.calendarTime?string('yyyy-MM-dd'))?if_exists}";
		var url="${request.contextPath}/office/schedule/schedule-workOutlineExport.action"+str;
		window.open(url);
		/*
		$("#exportDiv").load(url,null,function(data){
			if(!data.operateSuccess){
				showMsgError(data.promptMessage);
				return;
			}else{
				showMsgSuccess("导出成功！");
				return;
			}
		});*/
	}
	
	function export2(){//zdexcel
		var str="";
		var unitordept=$("#unitordept").val();
		str+="?unitordept="+unitordept+"&officeCalendarDto.calendarTime=${(officeCalendarDto.calendarTime?string('yyyy-MM-dd'))?if_exists}";
		var url="${request.contextPath}/office/schedule/schedule-workOutlineExport.action"+str;
		location.href=url;
	}
	
	function doSearch(){
		var str="";
		var unitordept=$("#unitordept").val();
		str+="?unitordept="+unitordept+"&officeCalendarDto.calendarTime=${(officeCalendarDto.calendarTime?string('yyyy-MM-dd'))?if_exists}";
		var item = $('.ui-radio-current .radio').attr('value');
		if(item==1){
			url="${request.contextPath}/office/schedule/schedule-workOutlineWeekList.action"+str;
		}else if(item==2){
			url="${request.contextPath}/office/schedule/schedule-workOutlineMonthList.action"+str;
		}else if(item==3){
			var time=$("#calendarTime").val();
			url="${request.contextPath}/office/schedule/schedule-workOutlineDayList.action"+"?officeCalendarDto.calendarTime="+time;
		}
		load("#scheduleWeekListDiv", url);
	}
	//上一个 下一个
	function changeDate(ope){
		var str="";
		str+="&officeCalendarDto.calendarTime=${(officeCalendarDto.calendarTime?string('yyyy-MM-dd'))?if_exists}&officeCalendarDto.operate="+ope;
		var item = $('.ui-radio-current .radio').attr('value');
		if(item==1){
			url="${request.contextPath}/office/schedule/schedule-workOutline.action?isWeek=true&isMonth=false&isDay=false"+str;
		}else if(item==2){
			url="${request.contextPath}/office/schedule/schedule-workOutline.action?isWeek=false&isMonth=true&isDay=false"+str;
		}else if(item==3){
			//var time=$("#calendarTime").val();
			url="${request.contextPath}/office/schedule/schedule-workOutline.action?isWeek=false&isMonth=false&isDay=true"+str;
		}
		load("#scheduleDiv", url);
	}
	
	
	//加载视图部分
	function reloadOutlineData(){
		var item = $('.ui-radio-current .radio').attr('value');
		var url="";
		if(item==1){
			var unitordept=$("#unitordept").val();
			var str="?unitordept="+unitordept+"&officeCalendarDto.calendarTime=${(officeCalendarDto.calendarTime?string('yyyy-MM-dd'))?if_exists}";
			url="${request.contextPath}/office/schedule/schedule-workOutlineWeekList.action"+str+"&isWeek=true&isMonth=false&isDay=false";
		}else if(item==2){
			var unitordept=$("#unitordept").val();
			var str="?unitordept="+unitordept+"&officeCalendarDto.calendarTime=${(officeCalendarDto.calendarTime?string('yyyy-MM-dd'))?if_exists}";
			url="${request.contextPath}/office/schedule/schedule-workOutlineMonthList.action"+str+"&isWeek=false&isMonth=true&isDay=false";
		}else if(item==3){
			var str="?officeCalendarDto.calendarTime=${(officeCalendarDto.calendarTime?string('yyyy-MM-dd'))?if_exists}";
			url="${request.contextPath}/office/schedule/schedule-workOutlineDayList.action"+str+"&isWeek=false&isMonth=false&isDay=true";
		}
		load("#scheduleWeekListDiv", url);
	}
	
	function dayOutlineAdd(){
		var url="${request.contextPath}/office/schedule/schedule-workOutlineAdd.action?"
		+"officeCalendarDto.calendarTime=${(officeCalendarDto.calendarTime?string('yyyy-MM-dd'))?if_exists}"+"&isWeek=false&isMonth=false&isDay=ture";
		openDiv("#scheduleLayer","#scheduleLayer .close,#scheduleLayer .submit,#scheduleLayer .reset",url,null,null,"600px",function(){
			
		});
	}
	
	function showtips(){
		showMsgError("您没有操作的权限");
	}
	
	function selectDate(){
		var dt=$('#calendarTime').val(); 
		//$("#timeImgSpan3").text();
		var item = $('.ui-radio-current .radio').attr('value');
		if(item==2){
			dt+=('-01');
		}
		var str="";
		var unitordept=$("#unitordept").val();
		str+="&unitordept="+unitordept+"&officeCalendarDto.calendarTime="+dt;
		var item = $('.ui-radio-current .radio').attr('value');
		if(item==1){
			url="${request.contextPath}/office/schedule/schedule-workOutline.action?isWeek=true&isMonth=false&isDay=false"+str;
		}else if(item==2){
			url="${request.contextPath}/office/schedule/schedule-workOutline.action?isWeek=false&isMonth=true&isDay=false"+str;
		}else if(item==3){
			url="${request.contextPath}/office/schedule/schedule-workOutline.action?isWeek=false&isMonth=false&isDay=true"+"&officeCalendarDto.calendarTime="+dt;
		}
		load("#scheduleDiv", url);
	}
</script>
<#--周视图 日视图  选择栏-->
<div id="container">
<div class="power-notes-dt mt-10">
    	<span class="fn-left mt-5">
        	<span class="ui-radio <#if isWeek>ui-radio-current"</#if>" data-name="a"><input name="ser" id="weekBt" type="radio" class="radio" value="1" onclick="changeFrame('1')">周视图</span>
        	<span class="ui-radio <#if isMonth>ui-radio-current"</#if>" data-name="a"><input name="ser" type="radio" class="radio"  value="2" onclick="changeFrame('2')">月视图</span>
        	<span class="ui-radio <#if isDay>ui-radio-current"</#if>" data-name="a"><input name="ser" type="radio" class="radio" value="3" onclick="changeFrame('3')">日视图</span>
        </span>
        <#if isWeek>
        <a href="javascript:void(0);" id="exportWeek" onclick="exportWeek();" class="abtn-blue fn-right ml-10">导出周视图</a>
        </#if>
        <#if isWeek||isMonth>
        <div class="fn-right">
        	<@common.select style="width:110px;" valName="unitordept" valId="unitordept" myfunchange="doSearch" >
				<a id="dpt1" val=1>科室</a>
        	<#if isHasAuth>
				<a val=2>单位</a>
        	</#if>
			</@common.select>
		</div>
		</#if>
		<#if isDay && isDeptAuth>
			<div class="fn-right">
            	<a class="abtn-orange fn-right" href="javascript:void(0);" onclick="dayOutlineAdd()">新建日志</a>
			</div>
		</#if>
        <div class="opt-wrap">
            <span class="page page-prev" onclick=changeDate('1')></span>
            <span class="time">
            <#if isWeek>
            	<input type="hidden" id="calendarTime" name=value="${officeCalendarDto.calendarTime?string('yyyy-MM-dd')?if_exists}">
            	${officeCalendarDto.calendarTime?string('yyyy-MM-dd')?if_exists}至${(officeCalendarDto.endTime?string('yyyy-MM-dd'))?if_exists}
            <#elseif isMonth>
            	<input type="hidden" id="calendarTime" name=value="${(officeCalendarDto.calendarTime?string('yyyy-MM-dd'))?if_exists}">
            	${officeCalendarDto.calendarTime?string('yyyy-MM')?if_exists}
            <#elseif isDay>
            	<input type="hidden" id="calendarTime" name=value="${(officeCalendarDto.calendarTime?string('yyyy-MM-dd'))?if_exists}">
            	${officeCalendarDto.calendarTime?string('yyyy-MM-dd')?if_exists}
            </#if>
            </span>
            <#if isWeek>
            <#--
            <span class="time-img" id="timeImgSpan3" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:selectDate});"></span>-->
            <#elseif isMonth>
            <span class="time-img" id="timeImgSpan3" onclick="WdatePicker({el:'calendarTime',dateFmt:'yyyy-MM',onpicked:selectDate});"></span>
            <#elseif isDay>
            <span class="time-img" id="timeImgSpan3" onclick="WdatePicker({el:'calendarTime',dateFmt:'yyyy-MM-dd',onpicked:selectDate});"></span>
            </#if>
            <span class="page page-next" onclick="changeDate('2')"></span>
        </div>
</div>
</div>
<div id="exportDiv" style="display:none;"/>
<div id="scheduleWeekListDiv"/>
</@common.moduleDiv>