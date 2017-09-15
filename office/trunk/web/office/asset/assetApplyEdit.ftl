<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.moduleDiv titleName="资产类别维护">
<script>
function doSave(){
	clearMessages();
	if(!isActionable("#btnSave")){
		return;
	}
	
	if(!checkAllValidate("#contentDiv")){
		return;
	}
	var unitPrice = $("#unitPrice").val();
	var assetNumber = $("#assetNumber").val();
	var totalPrice = unitPrice*assetNumber;
	if(totalPrice >= 50000){
		showMsgError("您所请购的物品总价(数量*申请单价)大于或等于50000，不能申请！");
		return;
	}
	
	$("#btnSave").attr("class", "abtn-unable");
	var options = {
       url:'${request.contextPath}/office/asset/assetAdmin-saveApply.action', 
       success : showReply,
       dataType : 'json',
       clearForm : false,
       resetForm : false,
       type : 'post'
    };
	
    $('#mainform').ajaxSubmit(options);
}

function showReply(data){
	var stateQuery = '${stateQuery!}';
	$("#btnSave").attr("class", "abtn-blue");
	if(!data.operateSuccess){
			   if(data.errorMessage!=null&&data.errorMessage!=""){
				   showMsgError(data.errorMessage);
				   return;
			   }else{
			   	   showMsgError(data.promptMessage);
				   return;
			   }
	}else{
		   		showMsgSuccess("提交成功！","",function(){
				   load("#adminDiv", "${request.contextPath}/office/asset/assetAdmin-apply.action?stateQuery="+stateQuery);
				});
				return;
	}
}

function dealPrice(dom){
	if("" == trim(dom.value) || isNaN(dom.value)){
		return;
	}
	var s = new Number(dom.value);
	dom.value =s.toFixed(2);
}
</script>
<p class="tt"><a href="javascript:void(0);" class="close">关闭</a><span>请购申请</span></p>
<div class="wrap pa-10" id="contentDiv">
<form id="mainform" action="" method="post" enctype="multipart/form-data">
    <table border="0" cellspacing="0" cellpadding="0" class="table-form">
    	<input type="hidden" id="id" name="id" value="${assetApply.id?default('')}">  
	    <input type="hidden" id="unitId" name="unitId" value="${assetApply.unitId?default('')}">
	    <input type="hidden" id="applyId" name="applyId" value="${assetApply.applyId?default('')}">  
	    <tr>
	      <th width="20%"><span class="c-orange mr-5">*</span>类别：</th>
	      <td width="30%">
	      	<#if assetApply.id?default("") != "">
	      	${assetApply.categoryName!}
	      	<#else>
	        <@htmlmacro.select style="width:150px;" valName="categoryId" valId="categoryId" myfunchange="" notNull="true" msgName="类别">
				<a val=""><span>---请选择---</span></a>
			  	<#list assetCategoryList as item>
			  	<a val="${item.id}" title="${item.assetName!}"><span>${item.assetName!}</span></a>
			  	</#list>
			</@htmlmacro.select>
			</#if>
	      </td>
	      <th width="20%"><span class="c-orange mr-5">*</span>物品名称：</th>
	      <td width="30%">
	        <input name="assetName" id="assetName" value="${assetApply.assetName?default('')}" 
	            type="text" <#if assetApply.id?default("") != "">class="input-txt input-readonly" readonly<#else>class="input-txt"</#if> style="width:140px;" msgName="物品名称"  notNull="true" maxlength="30">
	      </td>
	    </tr>
	    <tr>
	      	<th>规格：</th>
	    	<td>
	    		<input name="assetFormat" id="assetFormat" value="${assetApply.assetFormat?default('')}" 
	            type="text" <#if assetApply.id?default("") != "" && !assetApply.isOverMaxNum>class="input-txt input-readonly" readonly<#else>class="input-txt"</#if> style="width:140px;" msgName="规格"  notNull="" maxlength="60">
	    	</td>	
	    	<th><span class="c-orange mr-5">*</span>数量：</th>
	    	<td>
	    		<input name="assetNumber" id="assetNumber" value="${assetApply.assetNumber?default('')}" regex="/^[0-9]*[1-9][0-9]*$/" regexMsg="只能输入正整数"
	            type="text" <#if assetApply.id?default("") != "" && !assetApply.isOverMaxNum>class="input-txt input-readonly" readonly<#else>class="input-txt"</#if> style="width:140px;" msgName="数量"  notNull="true" maxlength="6">
	    	</td>
	    </tr>
	    <tr>
	    	<th><span class="c-orange mr-5">*</span>单位：</th>
	    	<td>
	    		<input name="assetUnit" id="assetUnit" value="${assetApply.assetUnit?default('')}" 
	            type="text" <#if assetApply.id?default("") != "" && !assetApply.isOverMaxNum>class="input-txt input-readonly" readonly<#else>class="input-txt"</#if> style="width:140px;" msgName="单位"  notNull="true" maxlength="20">
	    	</td>
	    	<th><span class="c-orange mr-5">*</span>申请单价：</th>
	    	<td>
	    		<input name="unitPrice" id="unitPrice" onblur="dealPrice(this);" notNull="true" msgName="申请单价" regex="/^(^[0-9]{0,5}$)|(^[0-9]{0,5}\.[0-9]{1,2}$)$/" regexMsg="最多5位整数2位小数" maxlength="12" value="${(assetApply.unitPrice?string('0.00'))?if_exists}" 
	    		<#if assetApply.id?default("") != "" && !assetApply.isOverMaxNum>class="input-txt input-readonly" readonly<#else>class="input-txt"</#if> style="width:140px;"/>
	    	</td>
	    </tr>
	    <tr>
            <th valign="top" class="pt-10"><span class="c-orange mt-5 mr-5">*</span>购买原因：</th>
            <td valign="top" class="pt-10" colspan="3">
            <textarea <#if assetApply.id?default("") != "">class="text-area my-5 input-readonly" readonly<#else>class="text-area my-5"</#if> id="reason" name="reason" maxlength="250" notNull="true" msgName="购买原因" style="width:80%;padding:5px 1%;height:50px;">${assetApply.reason?default("")}</textarea>
            </td>
        </tr>
	    <tr>
        	<th>图片：</th>
	        <td colspan="3">
	        <#if assetApply.id?default("") != ""&& !assetApply.isOverMaxNum>
	        	<#list assetApply.attachments as item>
				<a href="javascript:doDownload('${item.downloadPath!}');">${item.fileName!}</a>&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="javascript:viewAttachment('${assetApply.attachments.get(0).id!}');">预览</a>&nbsp;&nbsp;&nbsp;&nbsp;
	        	</#list>
	        <#else>
		        <#if assetApply.attachments?exists && assetApply.attachments?size gt 0 >
        		<input id="uploadContentFileInput" name="" type="text" class="input-txt input-readonly" readonly="readonly" style="width:200px;" value="<#if assetApply.attachments.get(0)?exists>${assetApply.attachments.get(0).fileName!}</#if>" maxLength="125"/>&nbsp;&nbsp;
		        <#else>
        		<input id="uploadContentFileInput" name="" type="text" class="input-txt input-readonly" readonly="readonly" style="width:200px;" value="" maxLength="125"/>&nbsp;&nbsp;
	        	</#if>       
	        	<a href="javascript:void(0);" class="upload-span1 ">
				<input id="uploadContentFile" name="uploadContentFile" accept="image/*" hidefocus type="file" onchange="uploadContent(this);" value="" >
	        	上传</a>
	        	<#if assetApply.attachments?exists && assetApply.attachments?size gt 0 >
	        		<a href="javascript:viewAttachment('${assetApply.attachments.get(0).id!}');">预览</a>&nbsp;&nbsp;&nbsp;&nbsp;
	        	</#if>
        	</#if>       
	        </td>
	    </tr>
        <#if assetApply.id?default("") != "" && assetApply.isOverMaxNum>
        <th colspan="4" class="t-center">采购反馈信息</th>
        <tr>
	    	<th><span class="c-orange mr-5">*</span>实际采购单价：</th>
	    	<td>${assetApply.purchasePrice?if_exists}
	    	</td>
	    	<th><span class="c-orange mr-5">*</span>实际采购总价：</th>
	    	<td>${assetApply.purchaseTotalPrice?if_exists}
	    	</td>
	    </tr>
        </#if>
    </table>
</form>    
</div>
<div class=" popUp-layer contentDivPop keep-div" id="fwLayer" style="display:none;z-index:998;">
		<p id="divTt" class="tt"><span href="javascript:void(0);"  onclick="closePop()" class="fn-right">关闭</span><span>附件/预览</span></p>
	    <div class="docReader" id="showAttDiv" style="width:855px;height:700px;display:none;overflow-x:auto;"></div>
		<div class="docReader" id="editContentDiv" style="width:855px;height:700px;display:none;"></div>
	</div>
<p class="dd">
<#if assetApply.id?default("") == "" || assetApply.isOverMaxNum>
    <a class="abtn-blue" href="javascript:void(0);" onclick="doSave();" id="btnSave">提交</a>
</#if>    
    <a class="abtn-blue reset ml-5" href="javascript:void(0);">取消</a>
</p>
<iframe name="downloadFrame" id="downloadFrame" style="display:none;"></iframe>
<script>
$(document).ready(function(){
		vselect();
		resetFilePos();
	});
function resetFilePos(){
	try{
		$(".upload-span1").css({"position":"relative","width":"40px","top":"3px","overflow":"hidden","display":"inline-block","cursor":"pointer"});
		$("#uploadContentFile").css({"position":"absolute","right":"0","top":"0","opacity":"0","filter":"alpha(opacity=0)","font-size":"100px","cursor":"pointer"});
	}catch(e){
	}
}
function uploadContent(target){
	$("#uploadContentFileInput").val($(target).val());
}
function doDownload(url){
	document.getElementById('downloadFrame').src=url;
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
			editDivHide();
			doRemoveBackground();
			$("#fwLayer").show();
			$("#divTt").show();
			$("#fwLayer").css("width","855px").css("height","764px");
			$("#fwLayer").css("top","190px").css("left","-18%" );
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
			$("#fwLayer").css("top","190px").css("left","-18%" );
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