<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<script type="text/javascript" src="${request.contextPath}/static/jbmp/editor/js/flow.js"></script>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/boxy/boxy.css"/>
<script type="text/javascript" src="${request.contextPath}/static/boxy/jquery.boxy.js"></script>
<@htmlmacro.moduleDiv titleName="考勤时段添加">
	<form name="attendanceSetEditForm" id="attendanceSetEditForm" method="post">
		<input id="id" name="officeAttendanceSet.id" value="${officeAttendanceSet.id!}" type="hidden" />
		<input id="unitId" name="officeAttendanceSet.unitId" value="${officeAttendanceSet.unitId!}" type="hidden" />
		<input type="hidden" value="${((officeAttendanceSet.creationTime)?string('yyyy-MM-dd HH:mm:ss'))?default('')}" name="officeAttendanceSet.creationTime">
		
		<div class="mt-30"></div>
		<@htmlmacro.tableDetail divClass="table-form ">
			<tr>
		        <th colspan="4" style="text-align:center;">考勤时段添加</th>
		    </tr>
		    <tr>
		        <th style="width:20%"><span class="c-orange mr-5">*</span>考勤时段：</th>
		        <td style="width:30%">
		        	<input name="officeAttendanceSet.name" id="name" value="${officeAttendanceSet.name!}" msgName="考勤时段" type="text" class="input-txt" style="width:138px;" maxlength="50"  notNull="true" />
		        </td>
		        <th  style="width:20%"><span class="c-orange mr-5">*</span>上班时间：</th>
		        <td  style="width:30%">
		        	<@htmlmacro.datepicker name="officeAttendanceSet.startTime" id="startTime" onpicked="clickChangeStart" class="input-txt" style="width:138px;" msgName="上班时间" notNull="true" dateFmt="HH:mm"  value="${officeAttendanceSet.startTime!}" />
		        </td>
		    </tr>
		    <tr>
		        <th  ><span class="c-orange mr-5">*</span>下午上班时间：</th>
		        <td  >
		        	<@htmlmacro.datepicker name="officeAttendanceSet.pmTime" id="pmTime" class="input-txt" onpicked="clickChangePm" style="width:138px;" msgName="下午上班时间" notNull="true"  dateFmt="HH:mm" value="${officeAttendanceSet.pmTime!}" />
		        </td>
		        <th  ><span class="c-orange mr-5">*</span>下班时间：</th>
		        <td  >
		        	<@htmlmacro.datepicker name="officeAttendanceSet.endTime" id="endTime" class="input-txt" onpicked="clickChangeEnd" style="width:138px;" msgName="下班时间" notNull="true" dateFmt="HH:mm" value="${officeAttendanceSet.endTime!}" />
		        </td>
		    </tr>
		    <tr>
		     	<th ><span class="c-orange mr-5">*</span>上班提前打卡时间：</th>
		    	<td >
			    	<@htmlmacro.select style="width:145px;" valName="officeAttendanceSet.startRange" valId="startRange"   msgName="上班提前打卡时间" notNull='true'>
						<a val="" ><span>--请选择--</span></a>
						<a val="1" <#if officeAttendanceSet.startRange?default("")=="1">class="selected"</#if> ><span>1小时</span></a>
						<a val="2" <#if officeAttendanceSet.startRange?default("")=="2">class="selected"</#if> ><span>2小时</span></a>
						<a val="3" <#if officeAttendanceSet.startRange?default("")=="3">class="selected"</#if> ><span>3小时</span></a>
						<a val="4" <#if officeAttendanceSet.startRange?default("")=="4">class="selected"</#if> ><span>4小时</span></a>
						<a val="5" <#if officeAttendanceSet.startRange?default("")=="5">class="selected"</#if> ><span>5小时</span></a>
						<a val="6" <#if officeAttendanceSet.startRange?default("")=="6">class="selected"</#if> ><span>6小时</span></a>
					</@htmlmacro.select>
			    </td>
			   
			    <th><span class="mr-5 c-red">*</span><span>是否弹性工作制：</span></th>
				<td>
					<span class="ui-radio <#if (officeAttendanceSet.isElastic?default(false))?c=="false">ui-radio-current</#if>" data-name="a"><input <#if (officeAttendanceSet.isElastic?default(false))?c=="false">checked="checked"</#if> onclick="changeElastic(0)" type="radio" class="radio" name="officeAttendanceSet.isElastic" value="false">否</span>
					<span class="ui-radio <#if (officeAttendanceSet.isElastic?default(false))?c=='true'>ui-radio-current</#if>" data-name="a"><input <#if (officeAttendanceSet.isElastic?default(false))?c=="true">checked="checked"</#if> onclick="changeElastic(1)" type="radio" class="radio" name="officeAttendanceSet.isElastic" value="true">是</span>
					<span id="errorType" class="field_tip input-txt-warn-tip"></span>
				</td>
		    </tr>
		     <tr id="elasticStyle" <#if (officeAttendanceSet.isElastic?default(false))?c=="false">style="display:none"</#if> >
		     	<th ><span class="c-orange mr-5">*</span>上班弹性范围：</th>
		    	<td >
			    	<@htmlmacro.select style="width:145px;" valName="officeAttendanceSet.elasticRange" valId="elasticRange" txtId="elasticRangeTxt"  msgName="上班弹性范围" notNull='true'>
						<a val="" ><span>--请选择--</span></a>
						<a val="0"  <#if officeAttendanceSet.elasticRange?default("")=="0">class="selected"</#if> ><span>0分钟</span></a>
						<a val="5"  <#if officeAttendanceSet.elasticRange?default("")=="5">class="selected"</#if> ><span>5分钟</span></a>
						<a val="10" <#if officeAttendanceSet.elasticRange?default("")=="10">class="selected"</#if> ><span>10分钟</span></a>
						<a val="20" <#if officeAttendanceSet.elasticRange?default("")=="20">class="selected"</#if> ><span>20分钟</span></a>
						<a val="30" <#if officeAttendanceSet.elasticRange?default("")=="30">class="selected"</#if> ><span>30分钟</span></a>
						<a val="40" <#if officeAttendanceSet.elasticRange?default("")=="40">class="selected"</#if> ><span>40分钟</span></a>
						<a val="50" <#if officeAttendanceSet.elasticRange?default("")=="50">class="selected"</#if> ><span>50分钟</span></a>
						<a val="60" <#if officeAttendanceSet.elasticRange?default("")=="60">class="selected"</#if> ><span>60分钟</span></a>
					</@htmlmacro.select>
			    </td>
			    
		     	<th ><span class="c-orange mr-5">*</span>下班弹性范围：</th>
		    	<td >
	    		<@htmlmacro.select style="width:145px;" valName="officeAttendanceSet.endElasticRange" valId="endElasticRange" txtId="endElasticRangeTxt"  msgName="下班弹性范围" notNull='true'>
						<a val="" ><span>--请选择--</span></a>
						<a val="0"  <#if officeAttendanceSet.endElasticRange?default("")=="0">class="selected"</#if> ><span>0分钟</span></a>
						<a val="5"  <#if officeAttendanceSet.endElasticRange?default("")=="5">class="selected"</#if> ><span>5分钟</span></a>
						<a val="10" <#if officeAttendanceSet.endElasticRange?default("")=="10">class="selected"</#if> ><span>10分钟</span></a>
						<a val="20" <#if officeAttendanceSet.endElasticRange?default("")=="20">class="selected"</#if> ><span>20分钟</span></a>
						<a val="30" <#if officeAttendanceSet.endElasticRange?default("")=="30">class="selected"</#if> ><span>30分钟</span></a>
						<a val="40" <#if officeAttendanceSet.endElasticRange?default("")=="40">class="selected"</#if> ><span>40分钟</span></a>
						<a val="50" <#if officeAttendanceSet.endElasticRange?default("")=="50">class="selected"</#if> ><span>50分钟</span></a>
						<a val="60" <#if officeAttendanceSet.endElasticRange?default("")=="60">class="selected"</#if> ><span>60分钟</span></a>
				</@htmlmacro.select>
			    </td>
		    </tr>
		    <tr>
		    	<td colspan="4" class="td-opt">
		    	    <a class="abtn-blue-big" href="javascript:void(0);" onclick="doSave() ">保存</a>
				    <a class="abtn-blue-big ml-5" href="javascript:void(0);" onclick="getAttendanceSetTab();">返回</a>
		        </td>
		    </tr>
		  </@htmlmacro.tableDetail>
	</form>
	<script>
	$(document).ready(function(){
		<#if (officeAttendanceSet.isElastic?default(false))?c=="false">
			$("#elasticRange").removeAttr("notNull");
			$("#elasticRangeTxt").removeAttr("notNull");
			$("#endElasticRange").removeAttr("notNull");
			$("#endElasticRangeTxt").removeAttr("notNull");
		</#if>
	});
	function clickChangeStart(){
		var startTime=$("#startTime").val();
		var endTime=$("#endTime").val();
		if(startTime!=null&&startTime!="" && endTime!=null&&endTime!=""){
			if(startTime>endTime){
				showMsgError('上班时间不能大于下班时间');
				return false;
			}
		}
		return true;
	}
	function clickChangeEnd(){
		var startTime=$("#startTime").val();
		var endTime=$("#endTime").val();
		if(startTime!=null&&startTime!="" && endTime!=null&&endTime!=""){
			if(startTime>endTime){
				showMsgError('下班时间不能小于上班时间');
				return false;
			}
		} 
		return true;
	}
	function clickChangePm(){
		var startTime=$("#startTime").val();
		var endTime=$("#endTime").val();
		var pmTime=$("#pmTime").val();
		if(startTime!=null&&startTime!="" && pmTime!=null&&pmTime!=""){
			if(startTime>pmTime){
				showMsgError('下午上班时间不能小于上班时间');
				return false;
			}
		} 
		if(endTime!=null&&endTime!="" && pmTime!=null&&pmTime!=""){
			if(endTime<pmTime){
				showMsgError('下午上班时间不能大于下班时间');
				return false;
			}
		} 
		return true;
	}
	var isSubmit =false;
	function doSave(){
		if(isSubmit){
			return;
		}
		if(!checkAllValidate("#attendanceSetEditForm")){
			return;
		}
		if(!clickChangeStart() || !clickChangeEnd() || !clickChangePm()){
			return;
		}
		var options = {
	       url:'${request.contextPath!}/office/teacherAttendance/teacherAttendance-setSave.action', 
	       success : showReply,
	       dataType : 'json',
	       clearForm : false,
	       resetForm : false,
	       type : 'post'
	    };
	    isSubmit = true;
	    $('#attendanceSetEditForm').ajaxSubmit(options);
	}
	
	function showReply(data){
		if(!data.operateSuccess){
		   if(data.errorMessage!=null&&data.errorMessage!=""){
			   showMsgError(data.errorMessage);
			   isSubmit = false;
			   return;
		   }
		}else{
			showMsgSuccess(data.promptMessage,"",function(){
			  	getAttendanceSetTab();
			});
			return;
		}
	}
	function getAttendanceSetTab(){
		load("#showListDiv","${request.contextPath}/office/teacherAttendance/teacherAttendance-setList.action");
	}
	function changeElastic(type){
		if(type==0){
			document.getElementById("elasticStyle").style.display="none";
			$("#elasticRange").removeAttr("notNull");
			$("#elasticRangeTxt").removeAttr("notNull");
			$("#endElasticRange").removeAttr("notNull");
			$("#endElasticRangeTxt").removeAttr("notNull");
		}else if(type==1){
			document.getElementById("elasticStyle").style.display="";
			$("#elasticRange").attr("notNull", "true");
			$("#elasticRangeTxt").attr("notNull", "true");
			$("#endElasticRange").attr("notNull", "true");
			$("#endElasticRangeTxt").attr("notNull", "true");
		}
	}
	</script>
</@htmlmacro.moduleDiv >