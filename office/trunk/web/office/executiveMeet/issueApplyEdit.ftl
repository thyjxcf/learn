<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.moduleDiv titleName="">
<form name="form1" id="form1" method="POST" action="" enctype="multipart/form-data">
<p class="table-dt fb18 mt-15">议题详细</p>
<input type="hidden" name="officeExecutiveIssue.id" value="${officeExecutiveIssue.id?default('')}">
<input type="hidden" name="officeExecutiveIssue.unitId" value="${officeExecutiveIssue.unitId?default('')}">
<input type="hidden" name="officeExecutiveIssue.createUserId" value="${officeExecutiveIssue.createUserId?default('')}">
<input type="hidden" name="officeExecutiveIssue.createTime" value="${officeExecutiveIssue.createTime?default('')}">
<input type="hidden" name="officeExecutiveIssue.serialNumber" value="${officeExecutiveIssue.serialNumber?default('')}">
<input type="hidden" name="officeExecutiveIssue.auditRemark" value="${officeExecutiveIssue.auditRemark?default('')}">
	<div class="meet-wrap" style="border:0px">
	<@htmlmacro.tableDetail divClass=" schedule-table schedule-table-meet">
	<tr>
		<th width="15%"><span class="c-orange mt-5 ml-10">*</span>&nbsp;议题名称：</th>
		<td colspan="2">
		<#if view>
			<span class="fn-left mt-3 ml-10">${officeExecutiveIssue.name!}</span>
		<#else>
			<input type="text" name="officeExecutiveIssue.name" id="name" class="input-txt" notNull="true" msgName="议题名称" maxlength="100" style="width:720px;" value="${officeExecutiveIssue.name!}"/>
		</#if>
		</td>
	</tr>
	<tr>
		<th width="15%"><span class="c-orange mt-5 ml-10">*</span>&nbsp;提报领导：</th>
		<td colspan="2">
		<#if view>
			<textarea name="officeExecutiveIssue.hostDeptNameStr" id="leaderNameStr" class="text-area my-5 input-readonly" style="width:75%;padding:5px 1%;height:50px;" disabled="disabled" rows="4" >${officeExecutiveIssue.leaderNameStr!}</textarea>
		<#else>
			<@commonmacro.selectMoreUser idObjectId="leaderId" nameObjectId="leaderNameStr" width=400 height=300>
			<input type="hidden" name="officeExecutiveIssue.leaderId" id="leaderId" notNull="true" msgName="提报领导" value="${officeExecutiveIssue.leaderId!}" > 
			<textarea name="officeExecutiveIssue.leaderNameStr" id="leaderNameStr" class="text-area my-5" style="width:75%;padding:5px 1%;height:50px;" disabled="disabled" rows="4" >${officeExecutiveIssue.leaderNameStr!}</textarea>
			</@commonmacro.selectMoreUser>
		</#if>
		</td>
	</tr>
	<tr>
		<th width="15%"><span class="c-orange mt-5 ml-10">*</span>&nbsp;主办科室：</th>
		<td colspan="2">
		<#if view>
			<textarea name="officeExecutiveIssue.hostDeptNameStr" id="hostDeptNameStr" class="text-area my-5 input-readonly" style="width:75%;padding:5px 1%;height:50px;" disabled="disabled" rows="4" >${officeExecutiveIssue.hostDeptNameStr!}</textarea>
		<#else>
			<@commonmacro.selectMoreTree idObjectId="hostDeptId" nameObjectId="hostDeptNameStr" preset="" treeUrl=request.contextPath+"/common/xtree/deptTree.action" >
			<input type="hidden" name="officeExecutiveIssue.hostDeptId" id="hostDeptId" notNull="true" msgName="主办科室" value="${officeExecutiveIssue.hostDeptId!}" > 
			<textarea name="officeExecutiveIssue.hostDeptNameStr" id="hostDeptNameStr" class="text-area my-5" style="width:75%;padding:5px 1%;height:50px;" disabled="disabled" rows="4" >${officeExecutiveIssue.hostDeptNameStr!}</textarea>
			</@commonmacro.selectMoreTree>
		</#if>
		</td>
	</tr>
	<tr>
		<th width="15%"><span class="c-orange mt-5 ml-10">*</span>&nbsp;列席科室：</th>
		<td colspan="2">
		<#if view>
			<textarea name="officeExecutiveIssue.attendDeptNameStr" id="attendDeptNameStr" class="text-area my-5 input-readonly" style="width:75%;padding:5px 1%;height:50px;" disabled="disabled" rows="4" >${officeExecutiveIssue.attendDeptNameStr!}</textarea>
		<#else>
			<@commonmacro.selectMoreTree idObjectId="attendDeptId" nameObjectId="attendDeptNameStr" preset="" treeUrl=request.contextPath+"/common/xtree/deptTree.action" >
			<input type="hidden" name="officeExecutiveIssue.attendDeptId" id="attendDeptId" notNull="true" msgName="列席科室" value="${officeExecutiveIssue.attendDeptId!}" > 
			<textarea name="officeExecutiveIssue.attendDeptNameStr" id="attendDeptNameStr" class="text-area my-5" style="width:75%;padding:5px 1%;height:50px;" disabled="disabled" rows="4" >${officeExecutiveIssue.attendDeptNameStr!}</textarea>
			</@commonmacro.selectMoreTree>
		</#if>
		</td>
	</tr>
	<tr>
		<th width="15%"><span class="c-orange mt-5 ml-10">*</span>&nbsp;意见征集科室：</th>
		<td colspan="2">
		<#if view>
			<textarea name="officeExecutiveIssue.opinionDeptNameStr" id="opinionDeptNameStr" class="text-area my-5 input-readonly" style="width:75%;padding:5px 1%;height:50px;" disabled="disabled" rows="4" >${officeExecutiveIssue.opinionDeptNameStr!}</textarea>
		<#else>
			<@commonmacro.selectMoreTree idObjectId="opinionDeptId" nameObjectId="opinionDeptNameStr" preset="" treeUrl=request.contextPath+"/common/xtree/deptTree.action" >
			<input type="hidden" name="officeExecutiveIssue.opinionDeptId" id="opinionDeptId" notNull="true" msgName="意见征集科室" value="${officeExecutiveIssue.opinionDeptId!}" > 
			<textarea name="officeExecutiveIssue.opinionDeptNameStr" id="opinionDeptNameStr" class="text-area my-5" style="width:75%;padding:5px 1%;height:50px;" disabled="disabled" rows="4" >${officeExecutiveIssue.opinionDeptNameStr!}</textarea>
			</@commonmacro.selectMoreTree>
		</#if>
		</td>
	</tr>
	<tr>
		<th width="15%">情况说明：</th>
		<td colspan="2">
			<textarea name="officeExecutiveIssue.remark" id="remark" style="width:75%;padding:5px 1%;height:50px;" <#if view>class="text-area my-5 input-readonly" disabled="disabled"<#else>class="text-area my-5"</#if> rows="4" maxlength="500">${officeExecutiveIssue.remark!}</textarea>
		</td>
	</tr>
	<tr>
	<th style="width:15%;">附件：</th>
	<#if officeExecutiveIssue.attachments?exists || !view>
	<td style="width:78%;">
		<div class="doc-wrap" style="width:880px;">
	        <ul class="doc-list fn-clear" id="upload-spanLi">
	            <#if officeExecutiveIssue.attachments?exists>
					<#list officeExecutiveIssue.attachments as att>
						<li class="view" id="attP${att_index}">
		                    <img src="${request.contextPath}/static/images/icon/file/
		                    <#if att.extName=='pdf'>
							pdf.png<#elseif att.extName=='doc'||att.extName=='docx'>
							word.png<#elseif att.extName=='ppt'||att.extName=='pptx'>
							ppt.png<#elseif att.extName=='xls'||att.extName=='xlsx'>
							xls.png<#elseif att.extName=='csv'>
							csv.png<#elseif att.extName=='rtf'>
							rtf.png<#elseif att.extName=='wav'||att.extName=='mp3'>
							music.png<#elseif att.extName=='txt'>
							txt.png<#elseif att.extName=='mp4'||att.extName=='avi'||att.extName=='mov'>
							move.png<#elseif att.extName=='png'||att.extName=='jpg'||att.extName=='jpeg'||att.extName=='gif'||att.extName=='bmp'>
							jpg.png<#else>other.png</#if>">
		                    <span class="name" style="width:230px;" title="${att.fileName!}"><@htmlmacro.cutOff str='${att.fileName!}' length=16/></span>
		                    <span class="fr" style="width:100px;">
		                        <a href="javascript:void(0);"  onclick="doDownload('${att.downloadPath!}');">下载</a>
		                        <#if !view>
	            				<a href="javascript:void(0);" onclick="doDeleteAtt('${att_index}','${att.id!}')">删除</a>
	            				</#if>
		                    </span>
		                </li>
					</#list>
				</#if>
	        </ul>
        </div>
    </td>
    <#if !view>
    <td class="last attTd" style="width:7%;">
    	<a href="javascript:void(0);" class="abtn-blue upfile-btn upload-span">选择</a>
    	<input style="display:none;" class="current" id="uploadAttFile0" name="uploadAttFile" hidefocus type="file" onchange="uploadAttachment(0,this);" value="">
	</td>
	</#if>
	<#else>
	<td colspan="2"></td>
	</#if>
	</tr>
	</@htmlmacro.tableDetail>
</div>  
	
		
<p class="t-center py-30">
<#if view>
    <a href="javascript:back();" class="abtn-blue-big">返回</a></p>
<#else>
    <a href="javascript:issueApplySave('1');"  class="abtn-blue-big">保存</a>
    <a href="javascript:issueApplySave('2');"  class="abtn-blue-big ml-10">提交</a>
    <a href="javascript:back();" class="abtn-blue-big ml-10">返回</a></p>                        
</#if>
<div id="remove_att" style="display:none;"></div>
</form> 
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script>
var isSubmit = false;
function issueApplySave(state){
	if(isSubmit){
    	return;
    }
	if(!checkAllValidate("#executiveMeetDiv")){
		return;
	}
    isSubmit = true;
	var options = {
       url:'${request.contextPath}/office/executiveMeet/executiveMeet-issueApplySave.action?submitState='+state, 
       success : showReply,
       dataType : 'json',
       clearForm : false,
       resetForm : false,
       type : 'post'
    };
    $('#form1').ajaxSubmit(options);
}

function showReply(data){
	var error = data;
	if(error && error != ''){
		showMsgError(data);
		isSubmit = false;
	} else {
		showMsgSuccess('操作成功!','提示',back);
	}
}
function back(){
	var url="${request.contextPath}/office/executiveMeet/executiveMeet-issueApply.action?queryName=${queryName!}";
	load("#executiveMeetDiv", url);
}

	
function uploadAttachment(index,target){
	if($('#uploadAttFile'+index).val()!=null&&$('#uploadAttFile'+index).val()!=""){
		if(!checkFileSize(target)){
			$('#uploadAttFile'+index).val('');
			return;
		}
		var fileName =$('#uploadAttFile'+index).val();
		var fileType = fileName.substr(fileName.lastIndexOf(".")+1).toLowerCase();
		var allName = fileName;
		if(fileName.length>26){
		    fileName = fileName.substr(0,26)+'...'; 
		}
		var innerHTML = '<li id="attachmentP'+index+'"><img src="'+_contextPath+'/static/images/icon/file/'+getFileType(fileType)+'">'+
        '<span class="name" style="width:280px;" title="'+allName+'">'+fileName+'</span><span class="fr"  style="width:50px;">'+
        '<a href="javascript:void(0);"  onclick="doDeleteFile('+index+')">删除</a></span></li>';
        
		$('#upload-spanLi').append(innerHTML);
		$('#uploadAttFile'+index).hide();
		$('#uploadAttFile'+index).removeClass("current");
		var num = parseInt(index)+1;
		$('#uploadAttFile'+index).after('<input style="display:none" class="current" id="uploadAttFile'+num+'" name="uploadAttFile" hidefocus type="file" onchange="uploadAttachment('+num+',this);"  value="" >');
		resetFilePosCur();
	}
}

function getFileType(fileType){
    var typeName='other.png';
    if(fileType=='pdf'){typeName='pdf.png';} 
    if(fileType=='doc'||fileType=='docx'){typeName='word.png';}
    if(fileType=='ppt'||fileType=='pptx'){typeName='ppt.png';}
	if(fileType=='mp4'||fileType=='avi'||fileType=='mov'){typeName='move.png';}
	if(fileType=='txt'){typeName='txt.png';}
	if(fileType=='png'||fileType=='jpg'||fileType=='jpeg'||fileType=='bmp'||fileType=='gif'){typeName='jpg.png';}
	if(fileType=='csv'){typeName='csv.png';}
	if(fileType=='mp3'||fileType=='mav'){typeName='music.png';}
	if(fileType=='rtf'){typeName='rtf.png';}
	if(fileType=='xls'||fileType=='xlsx'){typeName='xls.png';}
	return typeName;
}

//TODO
function doDeleteAtt(num,attId){
	if(confirm("确认要删除该附件?")){
		$('#attP'+num).remove();
		var innerHtml = $('#remove_att').html() + '<input style="display:none" name="removeAttachment" type="text" value="'+attId+'">';
		$('#remove_att').html(innerHtml);
	}
}

function doDeleteFile(num){
	$('#attachmentP'+num).remove();
	$('#uploadAttFile'+num).remove();
}

function initShowAtt(){
	if($(".att_show ").size()>0){
		var attId = $(".att_show ").get(0).getAttribute('dataId');
		initViewAttachment(attId);
	}
}

function checkWb(){
  if(navigator.userAgent.indexOf("MSIE")>0) { 
      return  true;
  } 
  if(navigator.userAgent.indexOf("Trident")>0) { 
      return true;
  } 
}

var fileCountSize = 0;
function checkFileSize(target){
	var fileSize = 0;  
	if (checkWb()) {   
		try{
			var filePath = target.value;      
			var fileSystem = new ActiveXObject("Scripting.FileSystemObject");         
			var file = fileSystem.GetFile (filePath);
			fileSize = file.Size;     
		}catch(e){
			return true;
		}   
	} else {     
		fileSize = target.files[0].size;      
	}  
	fileCountSize =fileCountSize+fileSize;
	var size = fileSize/1024/1024;     
	if(size>5){ 
		showMsgError("上传失败: 单个文件大小不能大于5M");  
		return false;
	} 
	var oldCountSize = fileCountSize/1024/1024;
	var size2 = fileCountSize/1024/1024;
	if(size2>50){ 
		showMsgError("上传失败: 当前总个文件大小"+oldCountSize+"M 上传后大于50M");
		fileCountSize=fileCountSize- size;  
		return false;
	}
	return true;
}

function resetFilePosCur(){
	if($(".attTd .current") != null && $(".attTd .current").length > 0){
		$(".attTd .current").css({"position":"absolute","-moz-opacity":"0","opacity":"0","filter":"alpha(opacity=0)","width":$("a.upload-span").width()+27,"height":$(".upload-span").height(),"cursor":"pointer"});
		$(".attTd .current").offset({"left":$(".upload-span").offset().left});		
		$(".attTd .current").css({"display":""});
		$(".attTd .current").offset({"top":$(".upload-span").offset().top});
	}
}

resetFilePosCur();
</script>
</@htmlmacro.moduleDiv>