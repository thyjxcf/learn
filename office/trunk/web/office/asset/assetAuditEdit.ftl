<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<script>
	
function doAuditSave(){
	if(!isActionable("#btnSave")){
		return;
	}
	if(!checkAllValidate("#auditEditDiv")){
		return;
	}
	$("#btnSave").attr("class", "abtn-unable-big");
	var options = {
       url:'${request.contextPath}/office/asset/assetAdmin-saveAudit.action', 
       success : showReply,
       dataType : 'json',
       clearForm : false,
       resetForm : false,
       type : 'post'
    };
	
    $('#mainform').ajaxSubmit(options);
}

function showReply(data){
	if(!data.operateSuccess){
	   if(data.promptMessage!=null&&data.promptMessage!=""){
		   showMsgError(data.promptMessage);
		   $("#btnSave").attr("class", "abtn-blue-big");
		   return;
	   }
	}else{
		showMsgSuccess("操作成功！","",function(){
			$("#btnSave").attr("class", "abtn-blue-big");
		  	doCancel();
		});
		return;
	}
}

function doCancel(){
	var roleCode = '${roleCode!}';
	var stateQuery = '${stateQuery!}';
	load("#assetAuditListDiv", "${request.contextPath}/office/asset/assetAdmin-auditList.action?roleCode="+roleCode+"&stateQuery="+stateQuery);
}
	
function doDownload(url){
	document.getElementById('downloadFrame').src=url;
}
</script>
<iframe name="downloadFrame" id="downloadFrame" style="display:none;"></iframe>
<div id="auditEditDiv">
<form action="" method="post" name="mainform" id="mainform">
<input type="hidden" id="id" name="assetApply.id" value="${assetApply.id?default('')}"/>
<input type="hidden" id="auditId" name="assetApply.auditId" value="${assetApply.auditId?default('')}"/>
<input type="hidden" id="auditId" name="assetApply.applyId" value="${applyid?default('')}"/>
<p class="table-dt">请购审核</p>
	<table border="0" cellspacing="0" cellpadding="0" class="table-form">
		<tr>
	      	<th><span class="c-orange mr-5">*</span>请购单编号：</th>
	    	<td colspan="3">${assetApply.applyCode?default('')}
	    	</td>	
	    </tr>	
	    	<th><span class="c-orange mr-5">*</span>请购申请人：</th>
	    	<td>${assetApply.applyUserName?default('')}
	    	</td>
	    	<th><span class="c-orange mr-5">*</span>所在部门：</th>
	    	<td>${assetApply.deptName?default('')}
	    	</td>
	    </tr>
		<tr>
	      <th width="15%"><span class="c-orange mr-5">*</span>类别：</th>
	      <td width="35%">${assetApply.categoryName?default('')}</td>
	      <th width="15%"><span class="c-orange mr-5">*</span>物品名称：</th>
	      <td width="35%">${assetApply.assetName?default('')}</td>
	    </tr>
	    <tr>
	      	<th>规格：</th>
	    	<td colspan="3">${assetApply.assetFormat?default('')}
	    	</td>
	    </tr>
	    <tr>		
	    	<th><span class="c-orange mr-5">*</span>数量：</th>
	    	<td>${assetApply.assetNumber?default('')}
	    	</td>
	    	<th><span class="c-orange mr-5">*</span>单位：</th>
	    	<td>${assetApply.assetUnit?default('')}
	    	</td>
	    </tr>
	    <tr>
	    	<th><span class="c-orange mr-5">*</span>申请单价：</th>
	    	<td>${(assetApply.unitPrice?string("0.00"))!}
	    	</td>
	    	<th><span class="c-orange mr-5">*</span>申请总价：</th>
	    	<td>${(assetApply.totalUnitPrice?string("0.00"))!}
	    	</td>
	    </tr>
	    <tr>
            <th valign="top" class="pt-10"><span class="c-orange mt-5 mr-5">*</span>购买原因：</th>
            <td valign="top" class="pt-10" colspan="3">
            <textarea class="text-area my-5 input-readonly" readonly  style="width:850px;" >${assetApply.reason?default("")}</textarea>
            </td>
        </tr>
        <#if assetApply.attachments?exists && assetApply.attachments?size gt 0 >
        	<th>图片：</th>
	        <td colspan="3">
        	<#list assetApply.attachments as item>
				<a href="javascript:doDownload('${item.downloadPath!}');">${item.fileName!}</a>&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="javascript:viewAttachment('${assetApply.attachments.get(0).id!}');">预览</a>&nbsp;&nbsp;&nbsp;&nbsp;
        	</#list>
        	</td>
    	</#if>
    	
    	<#if auditModel>
        <tr>
            <th rowspan="2" style="width:20%;"><span class="c-orange mt-5 mr-5">*</span>处室负责人意见：</th>
            <#if "dept_leader"==roleCode?default("dept_leader") && assetApply.auditStatus == "1">
            <td colspan="3">
            	<#if assetApply.totalUnitPrice?default(0) lte 200>
                <span class="ui-radio <#if assetApply.auditStatus != "3">ui-radio-current</#if>" data-name="a"><input name="assetApply.auditStatus" type="radio" class="radio" value="4" <#if assetApply.auditStatus != "3">checked</#if>>通过</span>
                <#else>
                <span class="ui-radio <#if assetApply.auditStatus != "3">ui-radio-current</#if>" data-name="a"><input name="assetApply.auditStatus" type="radio" class="radio" value="2" <#if assetApply.auditStatus != "3">checked</#if>>通过</span>
               </#if>
                <span class="ui-radio <#if assetApply.auditStatus = "3">ui-radio-current</#if>" data-name="a"><input name="assetApply.auditStatus" type="radio" class="radio" value="3" <#if assetApply.auditStatus=="3">checked</#if>>不通过</span>
            </td>
            </#if>
        </tr>
        <tr>
            <td colspan="3"><textarea <#if "dept_leader"==roleCode?default("dept_leader") && assetApply.auditStatus == "1">name="assetApply.opinion" notNull="true" class="text-area my-10"<#else>class=" input-readonly text-area my-10" readonly </#if> id=""   msgName="审核意见"  maxlength="250" style="width:850px;">${assetApply.deptOpinion?default('')}</textarea>
            </td>
        </tr>
        <#if assetApply.totalUnitPrice?default(0) gt 200>
        <tr>
            <th rowspan="2" style="width:20%;"><span class="c-orange mt-5 mr-5">*</span>分管校领导意见：</th>
            <#if "asset_leader"==roleCode?default("dept_leader") && assetApply.auditStatus == "1">
            <td colspan="3">
            	<#if assetApply.totalUnitPrice?default(0) lte 2000>
                <span class="ui-radio <#if assetApply.auditStatus != "3">ui-radio-current</#if>" data-name="a"><input name="assetApply.auditStatus" type="radio" class="radio" value="4" <#if assetApply.auditStatus != "3">checked</#if>>通过</span>
                <#else>
                <span class="ui-radio <#if assetApply.auditStatus != "3">ui-radio-current</#if>" data-name="a"><input name="assetApply.auditStatus" type="radio" class="radio" value="2" <#if assetApply.auditStatus != "3">checked</#if>>通过</span>
                </#if>
                <span class="ui-radio <#if assetApply.auditStatus = "3">ui-radio-current</#if>" data-name="a"><input name="assetApply.auditStatus" type="radio" class="radio" value="3" <#if assetApply.auditStatus=="3">checked</#if>>不通过</span>
            </td>
            </#if>
        </tr>
        <tr>
            <td colspan="3"><textarea <#if "asset_leader"==roleCode?default("dept_leader") && assetApply.auditStatus == "1">name="assetApply.opinion" notNull="true" class="text-area my-10"<#else>class=" input-readonly text-area my-10" readonly </#if>  msgName="审核意见"  maxlength="250" style="width:850px;">${assetApply.assetLeaderOpinion?default('')}</textarea>
            </td>
        </tr>
       </#if>
        <#if assetApply.totalUnitPrice?default(0) gt 2000>
        <tr>
            <th rowspan="2"><span class="c-orange mt-5 mr-5">*</span>校长意见：</th>
            <#if "office_schoolmaster"==roleCode?default("dept_leader") && assetApply.auditStatus == "1">
            <td colspan="3">
            	<#if assetApply.totalUnitPrice?default(0) lt 10000>
                <span class="ui-radio <#if assetApply.auditStatus != "3">ui-radio-current</#if>" data-name="a"><input name="assetApply.auditStatus" type="radio" class="radio" value="4" <#if assetApply.auditStatus != "3">checked</#if>>通过</span>
                <#else>
                <span class="ui-radio <#if assetApply.auditStatus != "3">ui-radio-current</#if>" data-name="a"><input name="assetApply.auditStatus" type="radio" class="radio" value="2" <#if assetApply.auditStatus != "3">checked</#if>>通过</span>
               </#if>
                <span class="ui-radio <#if assetApply.auditStatus = "3">ui-radio-current</#if>" data-name="a"><input name="assetApply.auditStatus" type="radio" class="radio" value="3" <#if assetApply.auditStatus=="3">checked</#if>>不通过</span>
            </td>
            </#if>
        </tr>
        <tr>
            <td colspan="3"><textarea <#if "office_schoolmaster"==roleCode?default("dept_leader") && assetApply.auditStatus == "1">name="assetApply.opinion" notNull="true" class="text-area my-10"<#else>class=" input-readonly text-area my-10" readonly </#if>  msgName="审核意见" maxlength="250" style="width:850px;">${assetApply.schoolmasterOpinion?default('')}</textarea>
            </td>
        </tr>
        </#if>
        <#if assetApply.totalUnitPrice?default(0) gte 10000>
        <tr>
            <th rowspan="2"><span class="c-orange mt-5 mr-5">*</span>会议讨论意见：</th>
            <#if "office_meetingleader"==roleCode?default("dept_leader") && assetApply.auditStatus == "1">
            <td colspan="3">
                <span class="ui-radio <#if assetApply.auditStatus != "3">ui-radio-current</#if>" data-name="a"><input name="assetApply.auditStatus" type="radio" class="radio" value="4" <#if assetApply.auditStatus != "3">checked</#if>>通过</span>
                <span class="ui-radio <#if assetApply.auditStatus = "3">ui-radio-current</#if>" data-name="a"><input name="assetApply.auditStatus" type="radio" class="radio" value="3" <#if assetApply.auditStatus=="3">checked</#if>>不通过</span>
            </td>
            </#if>
        </tr>
        <tr>
            <td colspan="3"><textarea <#if "office_meetingleader"==roleCode?default("dept_leader") && assetApply.auditStatus == "1">name="assetApply.opinion" notNull="true" class="text-area my-10"<#else>class=" input-readonly text-area my-10" readonly </#if>  msgName="审核意见" maxlength="250" style="width:850px;">${assetApply.meetingleaderOpinion?default('')}</textarea>
            </td>
        </tr>
        </#if>
        
        <#else>
        
        <#if !officeAssetCategory.is_DeptLeader>
        <tr>
            <th rowspan="2" style="width:20%;">处室负责人意见：</th>
            <#if "dept_leader"==roleCode?default("dept_leader") && assetApply.auditStatus == "1">
            <td colspan="3">
            	<#--<#if assetApply.totalUnitPrice?default(0) lte 200>-->
                <!--<span class="ui-radio <#if assetApply.auditStatus != "3">ui-radio-current</#if>" data-name="a"><input name="assetApply.auditStatus" type="radio" class="radio" value="4" <#if assetApply.auditStatus != "3">checked</#if>>通过</span>-->
                <#--<#else>-->
                <span class="ui-radio <#if assetApply.auditStatus != "3">ui-radio-current</#if>" data-name="a"><input name="assetApply.auditStatus" type="radio" class="radio" value="2" <#if assetApply.auditStatus != "3">checked</#if>>通过</span>
               <#--</#if>-->
                <span class="ui-radio <#if assetApply.auditStatus = "3">ui-radio-current</#if>" data-name="a"><input name="assetApply.auditStatus" type="radio" class="radio" value="3" <#if assetApply.auditStatus=="3">checked</#if>>不通过</span>
            </td>
            </#if>
        </tr>
        <tr>
            <td colspan="3"><textarea <#if "dept_leader"==roleCode?default("dept_leader") && assetApply.auditStatus == "1">name="assetApply.opinion" class="text-area my-10"<#else>class=" input-readonly text-area my-10" readonly </#if> id=""   msgName="审核意见"  maxlength="250" style="width:850px;">${assetApply.deptOpinion?default('')}</textarea>
            </td>
        </tr>
        </#if>
        <#--<#if assetApply.totalUnitPrice?default(0) gt 200>-->
        <#if !officeAssetCategory.is_Leader>
        <tr>
            <th rowspan="2" style="width:20%;">分管校领导意见：</th>
            <#if "asset_leader"==roleCode?default("dept_leader") && assetApply.auditStatus == "1">
            <td colspan="3">
            	<#--<#if assetApply.totalUnitPrice?default(0) lte 2000>
                <span class="ui-radio <#if assetApply.auditStatus != "3">ui-radio-current</#if>" data-name="a"><input name="assetApply.auditStatus" type="radio" class="radio" value="4" <#if assetApply.auditStatus != "3">checked</#if>>通过</span>
                <#else>-->
                <span class="ui-radio <#if assetApply.auditStatus != "3">ui-radio-current</#if>" data-name="a"><input name="assetApply.auditStatus" type="radio" class="radio" value="2" <#if assetApply.auditStatus != "3">checked</#if>>通过</span>
                <#--</#if>-->
                <span class="ui-radio <#if assetApply.auditStatus = "3">ui-radio-current</#if>" data-name="a"><input name="assetApply.auditStatus" type="radio" class="radio" value="3" <#if assetApply.auditStatus=="3">checked</#if>>不通过</span>
            </td>
            </#if>
        </tr>
        <tr>
            <td colspan="3"><textarea <#if "asset_leader"==roleCode?default("dept_leader") && assetApply.auditStatus == "1">name="assetApply.opinion" class="text-area my-10"<#else>class=" input-readonly text-area my-10" readonly </#if>  msgName="审核意见"  maxlength="250" style="width:850px;">${assetApply.assetLeaderOpinion?default('')}</textarea>
            </td>
        </tr>
        </#if>
       <#--</#if>-->
        <#--<#if assetApply.totalUnitPrice?default(0) gt 2000>-->
        <#if !officeAssetCategory.is_master>
        <tr>
            <th rowspan="2">校长意见：</th>
            <#if "office_schoolmaster"==roleCode?default("dept_leader") && assetApply.auditStatus == "1">
            <td colspan="3">
            	<#--<#if assetApply.totalUnitPrice?default(0) lt 10000>
                <span class="ui-radio <#if assetApply.auditStatus != "3">ui-radio-current</#if>" data-name="a"><input name="assetApply.auditStatus" type="radio" class="radio" value="4" <#if assetApply.auditStatus != "3">checked</#if>>通过</span>
                <#else>-->
                <span class="ui-radio <#if assetApply.auditStatus != "3">ui-radio-current</#if>" data-name="a"><input name="assetApply.auditStatus" type="radio" class="radio" value="2" <#if assetApply.auditStatus != "3">checked</#if>>通过</span>
               <#--</#if>-->
                <span class="ui-radio <#if assetApply.auditStatus = "3">ui-radio-current</#if>" data-name="a"><input name="assetApply.auditStatus" type="radio" class="radio" value="3" <#if assetApply.auditStatus=="3">checked</#if>>不通过</span>
            </td>
            </#if>
        </tr>
        <tr>
            <td colspan="3"><textarea <#if "office_schoolmaster"==roleCode?default("dept_leader") && assetApply.auditStatus == "1">name="assetApply.opinion" class="text-area my-10"<#else>class=" input-readonly text-area my-10" readonly </#if>  msgName="审核意见" maxlength="250" style="width:850px;">${assetApply.schoolmasterOpinion?default('')}</textarea>
            </td>
        </tr>
        </#if>
        <#--</#if>-->
        <#--<#if assetApply.totalUnitPrice?default(0) gte 10000>-->
        <#if !officeAssetCategory.is_meeting>
        <tr>
            <th rowspan="2">会议讨论意见：</th>
            <#if "office_meetingleader"==roleCode?default("dept_leader") && assetApply.auditStatus == "1">
            <td colspan="3">
                <span class="ui-radio <#if assetApply.auditStatus != "3">ui-radio-current</#if>" data-name="a"><input name="assetApply.auditStatus" type="radio" class="radio" value="4" <#if assetApply.auditStatus != "3">checked</#if>>通过</span>
                <span class="ui-radio <#if assetApply.auditStatus = "3">ui-radio-current</#if>" data-name="a"><input name="assetApply.auditStatus" type="radio" class="radio" value="3" <#if assetApply.auditStatus=="3">checked</#if>>不通过</span>
            </td>
            </#if>
        </tr>
        <tr>
            <td colspan="3"><textarea <#if "office_meetingleader"==roleCode?default("dept_leader") && assetApply.auditStatus == "1">name="assetApply.opinion" class="text-area my-10"<#else>class=" input-readonly text-area my-10" readonly </#if>  msgName="审核意见" maxlength="250" style="width:850px;">${assetApply.meetingleaderOpinion?default('')}</textarea>
            </td>
        </tr>
        </#if>
        <#--</#if>-->
        </#if>
        
    </table>
    <div class=" popUp-layer contentDivPop keep-div" id="fwLayer" style="display:none;z-index:998;">
		<p id="divTt" class="tt"><span href="javascript:void(0);"  onclick="closePop()" class="fn-right">关闭</span><span>附件/预览</span></p>
	    <div class="docReader" id="showAttDiv" style="width:855px;height:700px;display:none;overflow-x:auto;"></div>
		<div class="docReader" id="editContentDiv" style="width:855px;height:700px;display:none;"></div>
	</div>
     <p class="t-center pt-20">
     	<#if assetApply.auditStatus == "1">
        <a href="javascript:void(0);" id="btnSave" onclick="doAuditSave();" class="abtn-blue-big">保存</a>
        </#if>
        <a href="javascript:doCancel();" class="abtn-blue-big ml-10">返回</a>
    </p>
</form>
</div>

<script>
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
			editDivHide();
			doRemoveBackground();
			$("#fwLayer").show();
			$("#divTt").show();
			$("#fwLayer").css("width","855px").css("height","764px");
			$("#fwLayer").css("top","190px").css("left","20%" );
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
			$("#fwLayer").css("top","190px").css("left","20%" );
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
</@htmlmacro.moduleDiv>