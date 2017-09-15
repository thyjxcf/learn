<#import "/common/htmlcomponent.ftl" as common />
<#import "/common/commonmacro.ftl" as commonmacro>
<@common.moduleDiv titleName="">
<form name="reportForm" id="reportForm" action="" method="post">
	<p class="tt"><span>编辑报修单</span></p>
	<input type="hidden" name="officeRepaire.id" id="id" value="${officeRepaire.id!}"/>
	<input type="hidden" name="officeRepaire.unitId" id="unitId" value="${officeRepaire.unitId!}"/>
	<input type="hidden" name="officeRepaire.userId" id="userId" value="${officeRepaire.userId!}"/>
    <div class="wrap">
    	<table border="0" cellspacing="0" cellpadding="0" class="table-edit mt-5" style="table-layout:fixed">
        	<tr>
        		<th width="20%">报修人：</th>
        		<td width="30%">
					${officeRepaire.userName!}
                </td>
             	<#if loginInfo.unitClass == 2>
                <th width="15%"><span <#if teachAreaList?size lt 2>style="display:none;"</#if>><span class="c-red mr-5">*</span>校区：</span></th>
                <td width="35%">
                <#if teachAreaList?size == 0>
				<input type="hidden" name="officeRepaire.teachAreaId" id="teachAreaId" value="00000000000000000000000000000000">
				<#else>
				 <div class="select_box fn-left" <#if teachAreaList?size == 1>style="display:none;"</#if>>
			    		<@common.select style="width:150px;" valName="officeRepaire.teachAreaId" valId="teachAreaId" myfunchange="">
							<#list teachAreaList as area>
								<a val="${area.id}" <#if area.id == officeRepaire.teachAreaId?default('')>class="selected"</#if>>${area.areaName!}</a>
							</#list>
						</@common.select>
						
					</div>
				</#if>
		           
                </td>
                <#else>
                <th width="15%">&nbsp;</th>
                <td width="35%">
		           &nbsp;
                </td>
                </#if>
            </tr>
            <tr>
            	<th>${appsetting.getString("offce.repaire.sblx")!"设备类型"}：</th>
                <td>
		            <div class="select_box">
			    		<@common.select style="width:150px;" valName="officeRepaire.equipmentType" msgName="设备类型" valId="equipmentType" myfunchange="doEquipTypeChange">
							${appsetting.getMcode("DM-SBLX").getHtmlTag(officeRepaire.equipmentType?default(''))}
						</@common.select>
					</div>
                </td>
				<th><span class="c-red  mr-5">*</span>设备地点：</th>
	            	<td >
	            		<input type="text" style="width:140px;" id="goodsPlace"  name="officeRepaire.goodsPlace" notNull="true" msgName="设备地点" class="input-txt"  maxLength="100" value="${officeRepaire.goodsPlace!}"/>
	            	</td>
         	</tr>
         	<#if loginInfo.unitClass == 2 && "hzzc" != systemDeploySchool>
         	<tr style="display:<#if officeRepaire.equipmentType?default("") != "1" && officeRepaire.equipmentType?default("") != "2">none</#if>;" id="class_info">
            <th>班级信息：</th>
            <td colspan="3">
            	<#if binjiangDeploy>
	            	<@common.select style="width:437px;" valName="officeRepaire.classId" valId="classId">
						<a val="">---请选择---</a>
						<#list basicClassList as m>
							<a val="${m.id}" <#if m.id == officeRepaire.classId?default('')>class="selected"</#if> title="${m.classnamedynamic!}">${m.classnamedynamic!}</a>
						</#list>
					</@common.select>
            	<#else>
	            	<@common.select style="width:150px;" valName="officeRepaire.classId" valId="classId">
	            		<a val="">---请选择---</a>
						<#list basicClassList as m>
							<a val="${m.id}" <#if m.id == officeRepaire.classId?default('')>class="selected"</#if> title="${m.classnamedynamic!}">${m.classnamedynamic!}</a>
						</#list>
					</@common.select>
				</#if>
            </td>
            </tr>
            </#if>
            <tr>
        		<th><span class="c-red mr-5">*</span>联系电话：</th>
                <td>
            		<input type="text" style="width:140px;" id="phone" name="officeRepaire.phone"  notNull="true" msgName="电话" class="input-txt"  maxLength="20" value="${officeRepaire.phone!}" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
                </td>
           
            	<th><span class="c-red mr-5">*</span>设备名称：</th>
            	<td>
            		<input type="text" style="width:140px;" id="goodsName" name="officeRepaire.goodsName" notNull="true" msgName="设备名称" class="input-txt"  maxLength="60" value="${officeRepaire.goodsName!}"/>
            	</td>
    	 	</tr>
            <tr>
            	<th><span class="c-red mr-5">*</span>类别：</th>
                <td colspan="3">
            		<div class="select_box fn-left">
					<@common.select style="width:100px;" valName="officeRepaire.type" valId="type" myfunchange="doTypeChange">
						<#list mcodelist as m>
							<a val="${m.thisId}" <#if m.thisId == officeRepaire.type?default('${repaireDefaultType!}')>class="selected"</#if>>${m.content!}</a>
						</#list>
					</@common.select>
					</div>
					<div class="fn-left">
						<p class="ml-20">总务包括：水、电、灯、门、窗、桌、椅等。</p>
	            		<p class="ml-20">电教包括：电脑、打印机、网络、音响、多媒体等。</p>
		            </div>
                </td>
            </tr>
            <tr>
            	<th>二级类别：</th>
                <td>
                	<div id="repaireTypeIdDiv"></div>
                </td>
                <th><span class="c-red mr-5">*</span>报修时间：</th>
                <td>
        			<@common.datepicker class="input-txt" notNull="true" msgName="报修时间" style="width:140px;" id="detailTime" name="officeRepaire.detailTime" dateFmt="yyyy-MM-dd HH:mm:ss" maxlength="20"
					value="${(officeRepaire.detailTime?string('yyyy-MM-dd HH:mm:ss'))?if_exists}"/>
        		</td>
            </tr>
            <tr>
               <th>附件：</th>
		        <td colspan="3">
		        	<#if officeRepaire.attachments?exists && officeRepaire.attachments?size gt 0 >
		        		<input id="uploadContentFileInput" name="officeRepaire.uploadContentFileInput" type="text" class="input-txt input-readonly" readonly="readonly" style="width:200px;" value="<#if officeRepaire.attachments.get(0)?exists>${officeRepaire.attachments.get(0).fileName!}</#if>" maxLength="125"/>&nbsp;&nbsp;
		        	<#else>
		        		<input id="uploadContentFileInput" name="" type="text" class="input-txt input-readonly" readonly="readonly" style="width:200px;" value="" maxLength="125"/>&nbsp;&nbsp;
		        	</#if>
		        	<a href="javascript:void(0);" class="upload-span1 ">
					<input id="uploadContentFile" name="uploadContentFile" hidefocus type="file" onchange="uploadContent(this);" value="123" >
		        	<span  style="top:2px;">上传附件</span></a>      
	 				<span id="cleanFile"  <#if officeRepaire.attachments?exists && officeRepaire.attachments?size gt 0>style=" display:display"<#else>style="display:none"</#if> class="upload-span"><a href="javascript:deleteFile();"><span style="position:relative;bottom:4px;">清空</span></a></span>
	 				<#if officeRepaire.attachments?exists && officeRepaire.attachments?size gt 0>
	 					<#if extNames.indexOf(officeRepaire.attachments.get(0).extName)!=-1>
	 					<span id="viewFile" style="display:display" class="upload-span"><a href="javascript:viewAttachment('${officeRepaire.attachments.get(0).id!}','${officeRepaire.attachments.get(0).extName!}');"><span style="position:relative;bottom:4px;">预览</span></a></span>
	 					</#if>
	 				</#if>
		        </td>
		    </tr>
            <tr>
            	<th><span class="c-red">*</span>故障详情：</th>
            	<td colspan="3" style="word-break:break-all; word-wrap:break-word;">
            		<textarea id="remark" name="officeRepaire.remark"  msgName="故障详情" notNull="true" class="area200" maxLength="200" rows="4" cols="69" style="width:350px;">${officeRepaire.remark?default('')}</textarea>
            	</td>
            </tr>
        </table>
    </div>
    <div class=" popUp-layer contentDivPop keep-div" id="fwLayer" style="display:none;z-index:998;">
		<p id="divTt" class="tt"><span href="javascript:void(0);"  onclick="closePop()" class="fn-right">关闭</span><span>附件/预览</span></p>
	    <div class="docReader" id="showAttDiv" style="width:855px;height:700px;display:none;"></div>
		<div class="docReader" id="editContentDiv" style="width:855px;height:700px;display:none;"></div>
	</div>
    <p class="dd">
        <a href="javascript:void(0);" class="abtn-blue" id="saveBtn" onclick="saveRepaire();">保存</a>
        <a href="javascript:void(0);" class="abtn-blue ml-5" onclick="closeDiv('#classLayer3')">取消</a>
    </p>
</form>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script>
function resetFilePos(){
	try{
		$(".upload-span1").css({"position":"relative","width":"60px","overflow":"hidden","display":"inline-block","cursor":"pointer"});
		$("#uploadContentFile").css({"position":"absolute","right":"0","top":"0","opacity":"0","filter":"alpha(opacity=0)","font-size":"100px","cursor":"pointer"});
	}catch(e){
	}
}
function uploadContent(target){
	$("#uploadContentFileInput").val($(target).val());
	$('#cleanFile').attr("style","display:display");
	$('#viewFile').attr("style","display:none");
}
vselect();
$(document).ready(function(){
	resetFilePos();
	var thisId = document.getElementById('type').value;
	doTypeChange(thisId);
}); 

function doEquipTypeChange(thisId){
	if(thisId == "1" || thisId == "2"){
		$("#class_info").show();
	}
	else{
		$("#class_info").hide();
	}
}
function deleteFile(){
	$('#uploadContentFileInput').val('');
	var file = $("#uploadContentFile")
	file.after(file.clone().val(""));
	file.remove();
	$('#cleanFile').attr("style","display:none");
	$('#viewFile').attr("style","display:none");
}
function doTypeChange(thisId){
	var url="${request.contextPath}/office/repaire/repaire-getRepaireType.action?thisId="+thisId+"&typeId=${officeRepaire.repaireTypeId!}";
	load("#repaireTypeIdDiv", url);
}
function validate(){
	if(!isActionable("#saveBtn")){
		return false;
	}
	if(!checkAllValidate("#classLayer3")){
		return;
	}
	var remark = document.getElementById('remark').value;
	if(remark == ''){
		showMsgError('故障详情不能为空！');
		return false;
	}
	var detailTime = document.getElementById('detailTime').value;
	if(detailTime == ''){
		showMsgError('报修时间不能为空！');
		return false;
	}
	return true;
}

function showReply(data){
	var error = data;
	if(error && error != ''){
		showMsgError(data);
		$("#saveBtn").attr("class", "abtn-blue");
	} else {
		showMsgSuccess('保存成功!','提示',sear);
	}
}

function saveRepaire(){
	if(!validate()){
		return;
	}
	//点击按钮，且验证通过后，按钮样式变为灰色
	$("#saveBtn").attr("class", "abtn-unable");
	
	var options = {
	       url:'${request.contextPath}/office/repaire/repaire-saveMy.action', 
	       dataType : 'json',
	       clearForm : false,
	       resetForm : false,
	       type : 'post',
	       success : showReply
	    };
	try{
		$('#reportForm').ajaxSubmit(options);
	}catch(e){
		showMsgError('保存失败！');
		$("#saveBtn").attr("class", "abtn-blue");
	}
}

//以下方法用来判断浏览器类型 控件使用 针对不同浏览器要使用不同的方法
		var browser;	//浏览器对象
		var version;	//浏览器版本
		
		// 请勿修改，否则可能出错 初始化方法用来判断浏览器类型
		var userAgent = navigator.userAgent, rMsie = /(msie\s|trident.*rv:)([\w.]+)/, rFirefox = /(firefox)\/([\w.]+)/, rOpera = /(opera).+version\/([\w.]+)/, rChrome = /(chrome)\/([\w.]+)/, rSafari = /version\/([\w.]+).*(safari)/;
		
		var ua = userAgent.toLowerCase();
		function uaMatch(ua) {
			var match = rMsie.exec(ua);
			if (match != null) {
				return {
					browser : "IE",
					version : match[2] || "0"
				};
			}
			var match = rFirefox.exec(ua);
			if (match != null) {
				return {
					browser : match[1] || "",
					version : match[2] || "0"
				};
			}
			var match = rOpera.exec(ua);
			if (match != null) {
				return {
					browser : match[1] || "",
					version : match[2] || "0"
				};
			}
			var match = rChrome.exec(ua);
			if (match != null) {
				return {
					browser : match[1] || "",
					version : match[2] || "0"
				};
			}
			var match = rSafari.exec(ua);
			if (match != null) {
				return {
					browser : match[2] || "",
					version : match[1] || "0"
				};
			}
			if (match != null) {
				return {
					browser : "",
					version : "0"
				};
			}
		}
		var browserMatch = uaMatch(userAgent.toLowerCase());
		if (browserMatch.browser) {
			browser = browserMatch.browser;
			version = browserMatch.version;
		}



//预览附件 用来获取附件状态
	function viewAttachment(attachmentId,extName){
		$.ajax({
	        url:"${request.contextPath}/office/common/checkAttachment.action",
	        dataType:"json",
	        data:{"attachmentId":attachmentId},
	        type:"post",
	        success:function(data){
		        if(data.operateSuccess){
	   				showAttachment(data.businessValue,extName);
	   			}else{
	   				showAttachmentError(data.errorMessage,attachmentId,extName);
	   				return;
	   			}  
	        }
	    });
	}
	
	//显示预览的附件
		function showAttachment(attachmentId,extName) {
			$("#showAttDiv").attr('attId',attachmentId).show();
			if(extName=='png'||extName=='jpg'||extName=='jpeg'||extName=='bmp'||extName=='gif'){
				$("#${showAttachmentDivId!}").css("overflow-x","auto");
			}else{
				$("#${showAttachmentDivId!}").css("overflow-x","");
			}
			editDivHide();
			doRemoveBackground();
			$("#fwLayer").show();
			$("#divTt").show();
			$("#fwLayer").css("width","855px").css("height","764px");
			$("#fwLayer").css("top","190px").css("left","-24%" );
			load("#showAttDiv","${request.contextPath}/office/common/showAttachment.action?attachmentId="+attachmentId);
		}
	
//显示附件预览的错误
		function showAttachmentError(msg,attachmentId,extName){
			editDivHide();
			var strHtml = '<p class="noData">'+msg+'</p>';
			$("#showAttDiv").html(strHtml).show();
			$("#fwLayer").show();
			$("#divTt").show();
			$("#fwLayer").css("width","855px").css("height","764px");
			$("#fwLayer").css("top","190px").css("left","-24%" );
		}
function editDivHide(){
		if (browser == "IE") {
			$("#editContentDiv").hide();
		}else{
				$("#editContentDiv").css("width","0px").css("height","0px");
				$("#editOcx").css("height","0px");
				$("#redSpan").hide();
				$("#editButtonDiv").hide();
				$("#divTt").hide();
		}
	}

function closePop(){
			editDivHide();
			$("#showAttDiv").hide();
			if (browser == "IE") {
				$("#fwLayer").hide();
			}else{
				$("#fwLayer").css("width","0px").css("height","0px");
			}
		}

function doRemoveBackground(){
			$(".docReader .noData").hide();
		}


</script>
</@common.moduleDiv>