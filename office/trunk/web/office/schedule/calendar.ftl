<#import "/common/htmlcomponent.ftl" as common />
<#import "/common/commonmacro.ftl" as commonmacro>
	<div class="power-notes-dt mt-15">
	<#if hasSch>
		<div class="query-tt ml-10"><span class="fn-left mt-5">学年：</span></div>
		<@common.select style="width:100px;" valId="acadyear" valName="acadyear" txtId="acadyearTxt" myfunchange="doquery" >
			<#list acadyearList as item>
				<a val="${item}" <#if item == acadyear?default('')>class="selected"</#if>>${item!}</a>
			</#list>
		</@common.select>
		<div class="query-tt ml-10"><span class="fn-left mt-5 ml-10">学期：</span></div>
		<@common.select style="width:100px;" valName="semester" valId="semester" txtId="semesterTxt" myfunchange="doquery">
			<a val="1"  <#if semester?default("")=="1">class="selected"</#if>><span>第一学期</span></a>
			<a val="2"  <#if semester?default("")=="2">class="selected"</#if>><span>第二学期</span></a>
		</@common.select>
	<#else>
		<div class="query-tt ml-10"><span class="fn-left mt-5">年度：</span></div>
		<@common.select style="width:100px;" valId="calyear" valName="calyear" txtId="acadyearTxt" myfunchange="doquery" >
			<#list yearList as item>
				<a val="${item}" <#if item == calyear?default('')>class="selected"</#if>>${item!}</a>
			</#list>
		</@common.select>
	</#if>
		<@common.datepicker style="width:100px;" id="beginDate" name="beginDate"  class="input-date fn-left ml-30" notNull="true" value="${(beginDate?string('yyyy-MM-dd'))?if_exists}" msgName="开始日期" />
        <span class="fn-left mx-5">-</span>
        <@common.datepicker style="width:100px;" id="endDate" name="endDate"  class="input-date fn-left" notNull="true" value="${(endDate?string('yyyy-MM-dd'))?if_exists}" msgName="结束日期" />
        <span style="color:red;">
        <#if !officeCalendarSemester?exists || !officeCalendarSemester.id?exists>
        	<#if hasSch>
	    		未维护学年学期
	    	<#else>
	    		未维年度
	    	</#if>
	    </#if>
    	</span>
    	<#if canEdit>
    		<a href="javascript:void(0);" class="abtn-blue fn-right" onclick="saveSemester();">保存</a>
    	</#if>
    </div>
    <#if officeCalendarSemester?exists && officeCalendarSemester.id?exists>
    <table border="0" cellspacing="0" cellpadding="0" class="calendar">
        <tr>
            <td colspan="9" class="title">${unitName!}教育工作行事历</td>
        </tr>
        <tr>
            <th class="first"><img src="${request.contextPath!}/static/images/wekday.png" alt="周次 星期" /></th>
            <th>一</th>
            <th>二</th>
            <th>三</th>
            <th>四</th>
            <th>五</th>
            <th>六</th>
            <th>日</th>
            <th class="last">重  大  活  动</th>
        </tr>
        <#assign i =0>
	<#if calendarDayInfoDtoList?exists && calendarDayInfoDtoList.size() gt 0>
		<#list calendarDayInfoDtoList as dto>
			<#if dto_index == 0 || dto.day == 1>
				<#assign rowspanNum = dto.rowspan>
			</#if>
			<#if i == 0>
				<tr>
				<td class="first">${chineseNumber[dto.weekForYear-1]}</td>
				<#assign i = i+1>
			</#if>
			<#if dto_index == 0 && dto.week gt 1>
				<#list 1..dto.week-1 as k>
					<td>&nbsp;</td>
					<#assign i = i+1>
				</#list>
			</#if>
				<td>
		            	<div class="has-log <#if !dto.content?has_content>no-log</#if>">
		                	<div class="font"><span <#if dto.day == 1>style="color:blue;"</#if>>${dto.day}<#if dto.day == 1>/${dto.month}</#if></span>${dto.lunarStr.substring(dto.getLunarStr().indexOf("月")+1)!}</div>
		                	<div class="show-log" style="width:170px;display:none;">
		                    	<p>
		                    		<input type="hidden" id="dayInfoId${dto_index}" value="${dto.id!}" />
		                    		<input type="hidden" id="dayInfoDate${dto_index}" value="${dto.restDate?string("yyyy-MM-dd")}" />
		                    		<input type="text" class="input-txt" style="width:150px;" id="dayContent${dto_index}" value="${dto.content!}" <#if !canEdit>readonly</#if>/>
		                    	</p>
		                        <p class="btn"><a href="javascript:void(0);" style="width:85px;" <#if canEdit>onclick="saveDayInfo('${dto_index}');"</#if> class="save-log-model">确定</a><a href="javascript:void(0);" class="close-log-model" >取消</a></p>
		                    </div>
		                    <div class="layout-log"></div>
		                </div>
		            </td>
			<#assign i = i+1>
			<#if !dto_has_next >
			<#if dto.week lt 7>
				<#list 1..7-dto.week as k>
					<td>&nbsp;</td>
					<#assign i = i+1>
				</#list>
			</#if>
			</#if>
			<#if i == 8>
				<#if rowspanNum lt 100>
					<td rowspan="${rowspanNum}" class="last" style="word-break:break-all; word-wrap:break-word;">
			            	<div class="des-show"><font><#if calendarMonthInfoMap.get(dto.restDate?string('yyyy-MM'))?exists>${calendarMonthInfoMap.get(dto.restDate?string('yyyy-MM')).content!}</#if></font><#if canEdit><a href="javascript:void(0);" class="open-edit-model">[编辑]</a></#if></div>
			                <div class="des-edit" style="display:none;">
			                	<input type="hidden" id="monthId${dto_index}" value="<#if calendarMonthInfoMap.get(dto.restDate?string('yyyy-MM'))?exists>${calendarMonthInfoMap.get(dto.restDate?string('yyyy-MM')).id}</#if>" />
		                    	<input type="hidden" id="monthDate${dto_index}" value="${dto.restDate?string("yyyy-MM")}" />
			                	<textarea id="monthContent${dto_index}" value="<#if calendarMonthInfoMap.get(dto.restDate?string('yyyy-MM'))?exists>${calendarMonthInfoMap.get(dto.restDate?string('yyyy-MM')).content!}</#if>"><#if calendarMonthInfoMap.get(dto.restDate?string('yyyy-MM'))?exists>${calendarMonthInfoMap.get(dto.restDate?string('yyyy-MM')).content!}</#if></textarea>
			                    <div class="des-bt"><a href="javascript:void(0);" onclick="saveMonthInfo('${dto_index}');" class="blue save-edit-model">保存</a><a href="javascript:void(0);" class="close-edit-model">取消</a></div>
			                </div>
			            </td>
					<#assign rowspanNum = 101>
				</#if>
				</tr>
				<#assign i = 0>		
			</#if>
		</#list>
	</#if>
    </table>
    <div class="des-bz fn-clear">
    	<div class="tit">备注：</div>
        <div class="last" style="word-break:break-all; word-wrap:break-word;">
            <div class="des-show"><font>${officeCalendarSemester.content!}</font><#if canEdit><a href="javascript:void(0);" class="open-edit-model">[编辑]</a></#if></div>
            <div class="des-edit" style="display:none;">
                <textarea id="semesterContent" value="${officeCalendarSemester.content!}">${officeCalendarSemester.content!}</textarea>
                <div class="des-bt"><a href="javascript:void(0);" class="blue save-edit-model" onclick="saveSemesterContent()">保存</a><a href="javascript:void(0);" id="close" class="close-edit-model">取消</a></div>
            </div>
        </div>
    </div>
</#if>
<script type="text/javascript">
function saveMonthInfo(index){
	var content = document.getElementById("monthContent"+index).value;
	var monthInfoDate = document.getElementById("monthDate"+index).value + "-01"; 
	var monthInfoId = document.getElementById("monthId"+index).value;
	var length = countCharacters(content);
	if(length>1000){
		showMsgWarn("内容不能超过1000字符");
		return;
	}
	$.ajax({
			type: "POST",
			url: "${request.contextPath}/office/schedule/schedule-saveMonthInfo.action",
			data: $.param({"officeCalendarMonthInfo.id":monthInfoId,"officeCalendarMonthInfo.content":content,"officeCalendarMonthInfo.monthDate":monthInfoDate},true),
			success: function(data){
				if(data.operateSuccess){
					showMsgSuccess(data.promptMessage,"", toCalendarInfo1());
	   			}else{
	   				showMsgError(data.errorMessage);
	   			}
			},
			dataType: "json",
			error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
		});
}

function saveDayInfo(index){
	var content = document.getElementById("dayContent"+index).value;
	var dayInfoDate = document.getElementById("dayInfoDate"+index).value; 
	var dayInfoId = document.getElementById("dayInfoId"+index).value;
	var length = countCharacters(content);
	if(length>30){
		showMsgWarn("内容不能超过30字符");
		return;
	}
	$.ajax({
			type: "POST",
			url: "${request.contextPath}/office/schedule/schedule-saveDayInfo.action",
			data: $.param( {"officeCalendarDayInfo.id":dayInfoId,"officeCalendarDayInfo.content":content,"officeCalendarDayInfo.restDate":dayInfoDate},true),
			success: function(data){
				if(data.operateSuccess){
					showMsgSuccess(data.promptMessage,"", toCalendarInfo1());
	   			}else{
	   				showMsgError(data.errorMessage);
	   			}
			},
			dataType: "json",
			error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
		});
}

function saveSemesterContent(){
	var content = document.getElementById("semesterContent").value;
	var length = countCharacters(content);
	if(length>1000){
		showMsgWarn("内容不能超过1000字符");
		return;
	}
	$.ajax({
			type: "POST",
			url: "${request.contextPath}/office/schedule/schedule-saveSemesterContent.action",
			data: $.param( {"officeCalendarSemester.id":"${officeCalendarSemester.id!}","officeCalendarSemester.content":content},true),
			success: function(data){
				if(data.operateSuccess){
					showMsgSuccess(data.promptMessage,"", toCalendarInfo1());
	   			}else{
	   				showMsgError(data.errorMessage);
	   			}
			},
			dataType: "json",
			error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
		});
}
function saveSemester(){
	var beginDate=document.getElementById("beginDate").value;  //开始时间
	var endDate=document.getElementById("endDate").value;  //结束时间
	if(beginDate == ''){
		showMsgWarn("开始时间不能为空");
		return;	
	}
	if(endDate == ''){
		showMsgWarn("结束时间不能为空");
		return;
	}
	new Date(beginDate.replace(/\-/g, "\/"))
	var date3=new Date(endDate.replace(/\-/g, "\/")).getTime()-new Date(beginDate.replace(/\-/g, "\/")).getTime();  //时间差的毫秒数
	if(date3<0){
		showMsgWarn("开始时间不能大于结束时间");
		return;
	}
	<#if hasSch>
	//计算出相差天数
	var days=Math.floor(date3/(24*3600*1000));
	if(days>365){
		showMsgWarn("学期时间跨度不能超过365天");
		return;
	}
	var acadyear = document.getElementById("acadyear").value;  
	var semester = document.getElementById("semester").value;
	$.ajax({
			type: "POST",
			url: "${request.contextPath}/office/schedule/schedule-saveSemesterInfo.action",
			data: $.param( {"officeCalendarSemester.id":"${officeCalendarSemester.id!}","beginDate":beginDate,"endDate":endDate,"acadyear":acadyear,"semester":semester},true),
			success: function(data){
				if(data.operateSuccess){
					showMsgSuccess(data.promptMessage,"", toCalendarInfo1());
	   			}else{
	   				showMsgError(data.errorMessage);
	   			}
			},
			dataType: "json",
			error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
		});
<#else>
	var calyear = document.getElementById("calyear").value;  
	if(beginDate.substr(0,4) != calyear){
		showMsgWarn("选择的时间和年度要保持一致");
		return;
	}
	new Date(beginDate.replace(/\-/g, "\/"))
	var date3=new Date(endDate.replace(/\-/g, "\/")).getTime()-new Date(beginDate.replace(/\-/g, "\/")).getTime();  //时间差的毫秒数
	if(new Date(endDate.replace(/\-/g, "\/")).getFullYear() != new Date(beginDate.replace(/\-/g, "\/")).getFullYear()){
		showMsgWarn("时间不能跨年");
		return; 
	}
	$.ajax({
			type: "POST",
			url: "${request.contextPath}/office/schedule/schedule-saveSemesterInfo.action",
			data: $.param( {"officeCalendarSemester.id":"${officeCalendarSemester.id!}","beginDate":beginDate,"endDate":endDate,"calyear":calyear},true),
			success: function(data){
				if(data.operateSuccess){
					showMsgSuccess(data.promptMessage,"", toCalendarInfo1());
	   			}else{
	   				showMsgError(data.errorMessage);
	   			}
			},
			dataType: "json",
			error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
		});
</#if>
}

function toCalendarInfo1(){
	var beginDate=document.getElementById("beginDate").value;  //开始时间
	var endDate=document.getElementById("endDate").value;  //结束时间
<#if hasSch>
	var acadyear = document.getElementById("acadyear").value;  
	var semester = document.getElementById("semester").value;
	var url="${request.contextPath}/office/schedule/schedule-toCalendarInfo.action?beginDate="+beginDate+"&endDate="+endDate+"&acadyear="+acadyear+"&semester="+semester;
<#else>
	var calyear = document.getElementById("calyear").value;
	var url="${request.contextPath}/office/schedule/schedule-toCalendarInfo.action?beginDate="+beginDate+"&endDate="+endDate+"&calyear="+calyear;
</#if>
	load("#scheduleDiv", url);
}

function countCharacters(str){
	    var totalCount = 0;
	    for (var i=0; i<str.length; i++)
	    {
	        var c = str.charCodeAt(i);
	        if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f))
	        {
	             totalCount++;
	        }
	        else
	        {     
	             totalCount+=2;
	        }
	     }
	    return totalCount;
} 

function doquery(){
<#if hasSch>
	var acadyear = document.getElementById("acadyear").value;  
	var semester = document.getElementById("semester").value;
	var url="${request.contextPath}/office/schedule/schedule-toCalendarInfo.action?acadyear="+acadyear+"&semester="+semester;
<#else>
	var calyear = document.getElementById("calyear").value;  
	var url="${request.contextPath}/office/schedule/schedule-toCalendarInfo.action?calyear="+calyear;
</#if>
	load("#scheduleDiv", url);
}

$(function(){
	$('.open-edit-model').click(function(e){
		e.preventDefault();
		var $txt1=$(this).siblings('font').text();
		$(this).parents('.last').find('textarea').val($txt1);
		$('.des-edit').hide().siblings('.des-show').show();;
		$(this).parents('.des-show').hide().siblings('.des-edit').show();
	});
	$('.save-edit-model').click(function(e){
		e.preventDefault();
		var $txt2=$(this).parents('.last').find('textarea').val();
		$(this).parents('.last').find('font').text($txt2);
		$(this).parents('.des-edit').hide().siblings('.des-show').show();
	});
	$('.close-edit-model').click(function(e){
		e.preventDefault();
		$(this).parents('.des-edit').hide().siblings('.des-show').show();
	})
	$('.layout-log').click(function(){
		var $txt=$(this).siblings('.show-log').find('input:text').val();
		$('.show-log').hide();
		$(this).siblings('.show-log').show();
		$('.save-log-model').click(function(e){
			e.preventDefault();
			$(this).parents('.show-log').hide();
		});
		$('.close-log-model').click(function(e){
			e.preventDefault();
			$(this).siblings('input:text').val($txt);
			$(this).parents('.show-log').hide();
		});
	});
});
</script>