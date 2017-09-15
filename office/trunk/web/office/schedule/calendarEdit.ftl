<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
<script type="text/javascript" charset="utf-8" src="${request.contextPath}/static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${request.contextPath}/static/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${request.contextPath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="${request.contextPath}/office/bulletinTl/js/bulletinTlSelect.js"></script>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/ueditor/themes/default/css/ueditor.css"/>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/ueditor/themes/iframe.css"/>
<script>
    var ue = UE.getEditor('content',{
        //focus时自动清空初始化时的内容
        autoClearinitialContent:false,
        //关闭字数统计
        wordCount:false,
        //关闭elementPath
        elementPathEnabled:false,
        //默认的编辑区域高度
        initialFrameHeight:300,
        //更多其他参数，请参考ueditor.config.js中的配置项
        toolbars:[[
	         'source', '|', 'undo', 'redo', '|',
	         'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
	         'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
	         'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
	         'directionalityltr', 'directionalityrtl', 'indent', '|',
	         'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
	         'link', 'unlink', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
	         'simpleupload',  'help', 'drafts'
	     	]]
    });
    $("#edui1").css("z-index","90");

var times = ['23:59:59','11:30:59','13:30:59','16:30:59','20:30:59'];
var isSubmit=false;
function saveCal(){
	if(isSubmit){
		return;
	}
	isSubmit=true;
	if(!checkAllValidate('#setAddForm')){
		isSubmit=false;
		return false;
	}
	if(!checkAfterDate(document.getElementById('calendarTime1'),document.getElementById('endTime1'))){
		isSubmit=false;
		return false;
	}
	var isSend=$("#isSmsAlarm").attr('checked');
	var str="";
	if(isSend && isSend=='checked'){
		if(!checkElement(document.getElementById('smsAlarmTime'),'短信发送时间')){
			isSubmit=false;
			return false;
		}
		str+="?isSendS=true";
	}else{
		str+="?isSendS=false";
	}
	var options = {
       url:'${request.contextPath}/office/schedule/schedule-saveCal.action'+str, 
       dataType : 'json',
       clearForm : false,
       resetForm : false,
       type : 'post',
       success : showReply
    };
	try{
		$('#setAddForm').ajaxSubmit(options);
	}catch(e){
		showMsgError('保存失败！');
	}
}

function showReply(data){
	if(data){
		var suc = data.operateSuccess;
		if(suc){
			showMsgSuccess(data.promptMessage,"",reloadData);
		} else {
			showMsgError(data.errorMessage);
		}
	} else {
		showMsgError('保存失败!');
	}
	isSubmit = false;
}

function clickAllDay(){
	var hasCheck=$('#isAllDayEvent').attr('checked');
	if(hasCheck && hasCheck=='checked'){
		$('#allPe').click();
		$('#setAddForm').find(".ui-select-box").addClass("ui-select-box-disable");
	} else {
		$('#setAddForm').find(".ui-select-box").removeClass("ui-select-box-disable");
	}
}

function clickSms(){
	var hasCheck=$('#isSmsAlarm').attr('checked');
	if(hasCheck && hasCheck=='checked'){
		$('#smsAlarmTime').show();
	} else {
		$('#smsAlarmTime').hide();
	}
}

function blurHalfDays(){
	var calendarTimeString = $("#calendarTime1").val();
	var endTimeString = $("#endTime1").val();
	var days = $("#halfDays").val();
	var days = days.replace(/^[0]*/,"");
	$("#halfDays").val(days);
	if(days=="") {
		if(calendarTimeString!="" && endTimeString != "") {
			$("#halfDays").val(getDifferDays(calendarTimeString, endTimeString));
		}
		return false;
	}
	if(calendarTimeString != "") {
		var cts = calendarTimeString.substring(0,10).split('-');
		var ctd = new Date(cts[0],parseInt(cts[1])-1,cts[2]);
		ctd.setDate(ctd.getDate()+parseInt(days)-1);
		var timeDT = ctd;
	    var timeStr = timeDT.getFullYear()+'-'+(timeDT.getMonth()+1)+'-'+getDateStr(timeDT.getDate())+calendarTimeString.substring(10);
	    $("#endTime1").val(timeStr);
	}else if(endTimeString != "") {
		var ets = endTimeString.substring(0,10).split('-');
		var etd = new Date(ets[0],parseInt(ets[1])-1,ets[2]);
		etd.setDate(etd.getDate()-parseInt(days)+1);
		var timeDT = etd;
	    var timeStr = timeDT.getFullYear()+'-'+(timeDT.getMonth()+1)+'-'+getDateStr(timeDT.getDate())+endTimeString.substring(10);
	    $("#calendarTime1").val(timeStr);
	}else {
		$("#halfDays").val("");
	}
}

function getDateStr(dd){
	var ddint = parseInt(dd);
	if(ddint < 10){
		return '0'+ddint;
	}
	return ddint;
}

function getDifferDays(ct,et){
	var cts = ct.substring(0,10).split('-');
	var ctd = new Date(cts[0],parseInt(cts[1])-1,cts[2]);
	var ets = et.substring(0,10).split('-');
	var etd = new Date(ets[0],parseInt(ets[1])-1,ets[2]);
	return etd.getDate()-ctd.getDate()+1;
}


function changePeriod(){
	var pe = parseInt($('#period').val());
	var et = $('#endTime1').val();
	if(et != ''){
		return;
	}
	var ct = $('#calendarTime1').val();
	var ctstr = '';
	if(ct != ''){
		ctstr = ct.substring(0,10);
	}
	if(ctstr == ''){
		return;
	}
	var et = ctstr+' '+times[pe];
	$('#endTime1').val(et);
}
</script>
<form id="setAddForm" name="setAddForm">
    <p class="tt"><a href="javascript:void(0);" class="close" onclick="closeDiv('#scheduleLayer');">关闭</a><span>日志安排维护</span></p>
    <div class="wrap pa-10" style="height:510px;overflow-y:auto;">
        <input type="hidden" id="calId" name="cal.id" value="${cal.id!}"/>
        <input type="hidden" id="creator" name="cal.creator" value="${cal.creator!}"/>
        <input type="hidden" id="creatorType" name="cal.creatorType" value="${cal.creatorType}"/>
        <input type="hidden" id="unitId" name="cal.unitId" value="${cal.unitId!}"/>
        <input type="hidden" id="operator" name="cal.operator" value="${cal.operator!}"/>
        <table border="0" cellspacing="0" cellpadding="0" class="table-edit mb-15">
        	<tr>
                <th width="15%"><span class="c-orange ml-10">*</span>开始时间：</th>
                <td width="85%"><@common.datepicker id="calendarTime1" name="cal.calendarTime" notNull="true" msgName="开始时间" dateFmt="yyyy-MM-dd HH:mm:00" value="${((cal.calendarTime)?string('yyyy-MM-dd HH:mm:00'))?if_exists}" maxlength="19" size="20" style="width:190px;" onpicked="blurHalfDays" oncleared="blurHalfDays"/>
                	<span class="fn-left mt-5 ml-10">维持时间：</span>
                	<input type="text" class="input-txt fn-left" id="halfDays" name="cal.halfDays" style="width:50px;" value="<#if cal.id?default('') !=''>${cal.halfDays}</#if>" onkeyup="this.value=this.value.replace(/\D/g,'')" onblur="blurHalfDays();">
                    <span class="fn-left mt-5 ml-5">天</span>
                </td>
            </tr>
            <tr>
                <th><span class="c-orange ml-10">*</span>结束时间：</th>
                <td>
	                <@common.datepicker id="endTime1" name="cal.endTime" notNull="true" msgName="结束时间" dateFmt="yyyy-MM-dd HH:mm:59" value="${((cal.endTime)?string('yyyy-MM-dd HH:mm:59'))?if_exists}" maxlength="19" size="20" style="width:190px;" />
	               	<span class="fn-left mt-5 ml-10">时间段：</span>
	               	<#assign pecls = "" />
	               	<#if cal.allDayEvent><#assign pecls = "ui-select-box-disable" /></#if>
	               	<@common.select style="width:72px;" className="${pecls!}" valId="period" valName="cal.period" txtId="periodTxt" myfunchange="changePeriod">
						<a id="allPe" val="0" <#if cal.period==0>class="selected"</#if>>请选择</a>
						<a val="1" <#if cal.period==1>class="selected"</#if>>上午</a>
						<a val="2" <#if cal.period==2>class="selected"</#if>>中午</a>
						<a val="3" <#if cal.period==3>class="selected"</#if>>下午</a>
						<a val="4" <#if cal.period==4>class="selected"</#if>>晚上</a>
					</@common.select>
					<span class="ui-checkbox fn-left mt-5 ml-10<#if cal.allDayEvent> ui-checkbox-current</#if>" id="isAllDayEventChk" myfunclick="clickAllDay"><input type="checkbox" id="isAllDayEvent" name="itemEva.isAllDayEvent" value="true" <#if cal.allDayEvent>checked="checked"</#if> class="chk" /><label for='isAllDayEventChk'>全天</label></span>
                </td>
            </tr>
            <tr>
    			<th>地点：</th>
    			<td><input type="text" class="input-txt" style="width:360px;" name="cal.place" value="${cal.place!}" msgName="地点" maxLength="200"></td>
    		</tr>
            <tr>
                <th><span class="c-orange ml-10">*</span>内容：</th>
                <td>
	          	  <!--<p><textarea name="cal.content" id="content" msgName="内容" notNull="true" class="text-area" maxlength="1000" style="width:360px;">${cal.content!}</textarea></p>-->
	          	  <p><textarea id="content" name="cal.content" msgName="内容" notNull="true" type="text/plain" maxlength="1000" style="width:850px;height:200px;">${cal.content!}</textarea></p>
	          </td>
            </tr>
            <tr>
    			<th></th>
    			<td><p class="mt-3"><span class="ui-checkbox<#if cal.isSmsAlarm> ui-checkbox-current</#if>" id="isSmsAlarmSpan" myfunclick="clickSms"><input type="checkbox" id="isSmsAlarm" name="itemEva.isSmsAlarm" value="true" <#if cal.isSmsAlarm>checked="checked"</#if> class="chk">短信提醒</span>
	          	  <#if cal.isSmsAlarm>
	          	  <@common.datepicker id="smsAlarmTime" name="cal.smsAlarmTime" class="input-txt input-date ml-5" notNull="false" msgName="短信发送时间" dateFmt="yyyy-MM-dd HH:mm:ss" value="${((cal.smsAlarmTime)?string('yyyy-MM-dd HH:mm:ss'))?if_exists}" maxlength="19" size="20" style="width:190px;" />
	          	  <#else>
	          	  <@common.datepicker id="smsAlarmTime" name="cal.smsAlarmTime" class="input-txt input-date ml-5" notNull="false" msgName="短信发送时间" dateFmt="yyyy-MM-dd HH:mm:ss" value="${((cal.smsAlarmTime)?string('yyyy-MM-dd HH:mm:ss'))?if_exists}" maxlength="19" size="20" style="width:190px;display:none;" />
	          	  </#if>
	          	  </p>
	          	 </td>
	        </tr>  	  
		</table>
    </div>
	<p class="dd">
		<a class="abtn-blue submit" href="javascript:void(0);" onclick="saveCal();"><span>保存</span></a>
		<a class="abtn-blue reset ml-5" href="javascript:void(0);" onclick="closeDiv('#scheduleLayer');"><span>取消</span></a>
	</p>
</form>
</@common.moduleDiv>