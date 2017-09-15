<#import "/common/htmlcomponent.ftl" as common>
<#import "/common/commonmacro.ftl" as commonmacro>
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
</script>
<@common.moduleDiv titleName="日程维护">
	<div id="workOutlineContainer">
	<form action="" method="post" id="worklineEdit" name="worklineEdit">
	<p class="tt"><a href="javascript:void(0);" class="close">关闭</a><span>周重点工作维护</span></p>	
	<div class="wrap pa-10" style="height:540px;overflow-y:auto;" id="contentDiv">
	<table class="table-edit mb-15" cellpadding="0" border="0" cellspacing="0">
		<input type="hidden" id="officeWorkOutline.id" name="officeWorkOutline.id" value="${officeWorkOutline.id!}"/>
		<input type="hidden" id="officeWorkOutline.version" name="officeWorkOutline.version" value="${officeWorkOutline.version!}"/>
		<input type="hidden" id="officeWorkOutline.createDept" name="officeWorkOutline.createDept" value="${officeWorkOutline.createDept!}"/>
		<input type="hidden" id="officeWorkOutline.unitId" name="officeWorkOutline.unitId" value="${officeWorkOutline.unitId!}"/>
		<tr>
			<th width="15%"><span class="c-orange mr-5">*</span>开始时间:</th>
			<td width="85%">
				<@common.datepicker id="calendarTime1" name="officeWorkOutline.calendarTime" style="width:190px;" notNull="true" msgName="开始时间" value="${((officeWorkOutline.calendarTime)?string('yyyy-MM-dd HH:mm:00'))?if_exists}"  notNull="true" dateFmt="yyyy-MM-dd HH:mm:00" maxlength="19" size="20" onpicked="blurHalfDays1" oncleared="blurHalfDays1" />				
				<span class="fn-left mt-5 ml-10">维持时间：</span>
				<input type="text" id="halfDays" name="officeWorkOutline.halfDays" class="input-txt fn-left" style="width:50px;" value="${officeWorkOutline.halfDays!}" onkeyup="this.value=this.value.replace(/\D/g,'')" onblur="blurHalfDays1();"/>
				<span class="fn-left mt-5 ml-5">天</span>
			</td>
		</tr>
		<tr>
			<th><span class="c-orange mr-5">*</span>结束时间:</th>
			<td>
				<@common.datepicker id="endTime1" name="officeWorkOutline.endTime" style="width:190px;" notNull="true" msgName="结束时间" value="${(officeWorkOutline.endTime?string('yyyy-MM-dd HH:mm:59'))?default('')}"  dateFmt="yyyy-MM-dd HH:mm:59" maxlength="19" />				
				<span class="fn-left mt-5 ml-10">时间段：</span>
				<#assign allcls="">
				<#if officeWorkOutline.isAllDayEvent><#assign allcls="ui-select-box-disable"/></#if>
				<@common.select style="width:72px;" className="${allcls!}" valName="officeWorkOutline.period" valId="period" myfunchange="changeEndTime">
					<a id="allday" <#if officeWorkOutline.period==0>class="selected"</#if>  val="0">请选择</a>
					<a <#if officeWorkOutline.period==1>class="selected"</#if> val="1">上午</a>
					<a <#if officeWorkOutline.period==2>class="selected"</#if> val="2">中午</a>
					<a <#if officeWorkOutline.period==3>class="selected"</#if> val="3">下午</a>
					<a <#if officeWorkOutline.period==4>class="selected"</#if> val="4">晚上</a>
				</@common.select>
				<span class="ui-checkbox fn-left mt-5 ml-10 <#if officeWorkOutline.isAllDayEvent> ui-checkbox-current</#if>" myfunclick="clickAll"><input type="checkbox" id="isAllDayChk" name="isAllDayChk" <#if officeWorkOutline.isAllDayEvent>checked="checked"</#if> value="true" class="chk"/><label for="isAllDayChk">全天</label></span>
			</td>
		</tr>
		<tr>
			<th>地点:</th>
			<td>
				<input type="text" class="input-txt" name="officeWorkOutline.place" value="${officeWorkOutline.place!}" class="input-txt" style="width:360px;" id="officeWorkOutline.place" maxlength="100"/>
			</td>
		</tr>
		<tr>
			<th>创建科室:</th>
			<td>
				<input type="text" id="officeWorkOutline.deptName" name="officeWorkOutline.deptName" class="input-txt" style="width:360px;" value="${officeWorkOutline.deptName!}" disabled="disabled" />
			</td>
		</tr>
		<tr>
			<th><span class="c-orange mr-5">*</span>内容:</th>
			<td>
				<!--<p><textarea  style="width:360px;" name="officeWorkOutline.content" id="officeWorkOutline.content" msgName="内容" notNull="true" class="text-area" maxlength="500" style="width:360px;">${officeWorkOutline.content!}</textarea></p>-->
				<p><textarea id="content" name="officeWorkOutline.content" msgName="内容" notNull="true" type="text/plain" maxlength="500" style="width:850px;height:200px;">${officeWorkOutline.content!}</textarea></p>
			</td>
		</tr>
		<tr>
			<th></th>
			<td>
				<p class="mt-3"><span class="ui-checkbox <#if isSendSms> ui-checkbox-current</#if>" onclick="clickSms1();"><input type="checkbox" name="officeWorkOutline.isSmsAlarm" id="isSmsAlarm1" value="" <#if isSendSms>checked="checked"</#if> class="chk">短信提醒</span>
				<#if isSendSms>
	      	  	<@common.datepicker id="smsAlarmTime1" name="officeWorkOutline.smsAlarmTime" class="input-txt input-date ml-5" notNull="true" msgName="短信发送时间" dateFmt="yyyy-MM-dd HH:mm:ss" value="${((officeWorkOutline.smsAlarmTime)?string('yyyy-MM-dd HH:mm:ss'))?if_exists}" maxlength="19" size="20" style="width:190px;" />
	      	  	<#else>
	      	  	<@common.datepicker id="smsAlarmTime1" name="officeWorkOutline.smsAlarmTime" class="input-txt input-date ml-5" notNull="false" msgName="短信发送时间" dateFmt="yyyy-MM-dd HH:mm:ss" value="${((officeWorkOutline.smsAlarmTime)?string('yyyy-MM-dd HH:mm:ss'))?if_exists}" maxlength="19" size="20" style="width:190px;display:none;" />
	      	  	</#if>
	      	  	</p>
      	  </td>
		</tr>
	</table>
	</div>
	<p class="dd">
	    <a class="abtn-blue" href="javascript:void(0);" onclick="doWorkoutlineSave();" id="worklinebtnSave">保存</a>
	    <a class="abtn-blue reset ml-5" href="javascript:void(0);">取消</a>
	</p>
	</form>
	</div>
<script>
	$(function(){
		vselect();
		//$("#isAllDayChk").click();//默认全选
		//$("#isAllDayChk").attr('checked',"checked");
	});
	function doWorkoutlineSave(){
		if(!isActionable("#worklinebtnSave")){
			return;
		}
		if(!checkAfterDate($("#calendarTime1").get(0),$("#endTime1").get(0))){
			return;
		}
		if(!checkAllValidate("#workOutlineContainer")){
			return;
		}
		$("#worklinebtnSave").attr("class","abtn-unable");
		var isSend=$("#isSmsAlarm1").attr('checked');
		var str="";
		if(isSend && isSend=='checked'){
			str+="?isSendSms=true";
		}else{
			str+="?isSendSms=false";
		}
		var options={
			url:'${request.contextPath}/office/schedule/schedule-workOutlineSave.action'+str,
			target : '#worklineEdit',
			dataType:'json',
			clearForm:false,
			resetForm:false,
			type:'post',
			success:showReply1
		};
		$("#worklineEdit").ajaxSubmit(options);
	}
	function showReply1(data){
		if(!data.operateSuccess){
			showMsgError(data.promptMessage);
			$("#worklinebtnSave").attr("class","abtn-blue");
			return;
		}else{
			showMsgSuccess("保存成功!","提示",function(){
				$("#worklinebtnSave").attr("class","abtn-blue");
				reloadOutlineData();
				return;
			});
		}
	}
	function clickAll(){
		var isChk=$("#isAllDayChk").attr('checked');
		if(isChk && isChk=='checked'){
			$("#worklineEdit").find('.ui-select-box').addClass('ui-select-box-disable');
			$("#allday").click();
		}else{
			$("#worklineEdit").find('.ui-select-box').removeClass('ui-select-box-disable');
		}
	}
	
	
function blurHalfDays1(){
	var calendarTimeString = $("#calendarTime1").val();
	var endTimeString = $("#endTime1").val();
	var days = $("#halfDays").val();
	var days = days.replace(/^[0]*/,"");
	$("#halfDays").val(days);
	if(days=="") {
		if(calendarTimeString!="" && endTimeString != "") {
			$("#halfDays").val(getDifferDays1(calendarTimeString, endTimeString));
		}
		return false;
	}
	if(calendarTimeString != "") {
		var cts = calendarTimeString.substring(0,10).split('-');
		var ctd = new Date(cts[0],parseInt(cts[1])-1,cts[2]);
		ctd.setDate(ctd.getDate()+parseInt(days)-1);
		var timeDT = ctd;
	    var timeStr = timeDT.getFullYear()+'-'+(timeDT.getMonth()+1)+'-'+getDateStr1(timeDT.getDate())+calendarTimeString.substring(10);
	    $("#endTime1").val(timeStr);
	}else if(endTimeString != "") {
		var ets = endTimeString.substring(0,10).split('-');
		var etd = new Date(ets[0],parseInt(ets[1])-1,ets[2]);
		etd.setDate(etd.getDate()-parseInt(days)+1);
		var timeDT = etd;
	    var timeStr = timeDT.getFullYear()+'-'+(timeDT.getMonth()+1)+'-'+getDateStr1(timeDT.getDate())+endTimeString.substring(10);
	    $("#calendarTime1").val(timeStr);
	}else {
		$("#halfDays").val("");
	}
}

function getDateStr1(dd){
	var ddint = parseInt(dd);
	if(ddint < 10){
		return '0'+ddint;
	}
	return ddint;
}

function getDifferDays1(ct,et){
	var cts = ct.substring(0,10).split('-');
	var ctd = new Date(cts[0],parseInt(cts[1])-1,cts[2]);
	var ets = et.substring(0,10).split('-');
	var etd = new Date(ets[0],parseInt(ets[1])-1,ets[2]);
	return etd.getDate()-ctd.getDate()+1;
}
	
	function clickSms1(){
		var hasCheck1=$('#isSmsAlarm1').attr('checked');
		if(hasCheck1 && hasCheck1=='checked'){
			$('#smsAlarmTime1').hide();
			$('#smsAlarmTime1').attr('notNull','false');
		} else {
			$('#smsAlarmTime1').show();
			$('#smsAlarmTime1').attr('notNull','true');
		}
	}
	
	var times = ['23:59:59','11:30:59','13:30:59','16:30:59','20:30:59'];
	function changeEndTime(){
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
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>