<#import "/common/htmlcomponent.ftl" as common />
<#import "/common/commonmacro.ftl" as commonmacro>
<@common.moduleDiv titleName="">
	<p class="tt"><span>报修单详情</span></p>
	<input type="hidden" name="officeRepaire.id" id="id" value="${officeRepaire.id!}"/>
	<div class="wrap">
		<table border="0" cellspacing="0" cellpadding="0" class="table-edit mt-5" style="table-layout:fixed">
	    	<tr>
	    		<th width="20%">报修人：</th>
	    		<td width="30%">
					${officeRepaire.userName!}
	            </td>
	            <#if loginInfo.unitClass == 2 && teachAreaList?size gt 1>
	            <th width="15%"><span class="c-red mr-5">*</span>校区：</th>
	            <td width="35%">
					 ${officeRepaire.teachAreaName!}
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
	        	<td >
	    			 ${appsetting.getMcode("DM-SBLX").get(officeRepaire.equipmentType!)}
	    		</td>
	        <th><span class="c-red mr-5">*</span>故障设备地点：</th>
	        	<td >
	    			 ${officeRepaire.goodsPlace!}
	        	</td>
	        </tr>
	        <#if loginInfo.unitClass == 2  && "hzzc" != systemDeploySchool && (officeRepaire.equipmentType?default("0") == "1" || officeRepaire.equipmentType?default("0") == "2")>
	        <tr>
	        	<th>班级信息：</th>
	            <td>
				 	${officeRepaire.className!}
	            </td>
	        	<th>&nbsp;</th>
	        	<td>
	    			 &nbsp;
	        	</td>
		 	</tr>
	        </#if>
	        <tr>
	        	<th><span class="c-red mr-5">*</span>联系电话：</th>
	            <td>
				 	${officeRepaire.phone!}
	            </td>
	       
	        	<th><span class="c-red mr-5">*</span>设备名称：</th>
	        	<td>
	    			 ${officeRepaire.goodsName!}
	        	</td>
		 	</tr>
	        <tr>
	        	<th><span class="c-red mr-5">*</span>类别：</th>
	            <td colspan="3">
	                <div class="fn-left">
	                ${appsetting.getMcode("DM-BXLB").get(officeRepaire.type!)}
	                </div>
					<div class="fn-left">
						<p>&nbsp;&nbsp;&nbsp;总务包括：水、电、灯、门、窗、桌、椅等。</p>
	            		<p>&nbsp;&nbsp;&nbsp;电教包括：电脑、打印机、网络、音响、多媒体等。</p>
		            </div>
	            </td>
	        </tr>
	        <tr>
	        	<th>二级类别：</th>
	            <td>
	        		${officeRepaire.repaireTypeName!}
	            </td>
	            <th><span class="c-red mr-5">*</span>报修时间：</th>
	            <td>
	        		${(officeRepaire.detailTime?string('yyyy-MM-dd HH:mm'))?if_exists}
	            </td>
	        </tr>
	        <tr>
	        	<th>附件：</th>
	       		 <td colspan="3">
	        	<#list officeRepaire.attachments as item>
				<a href="javascript:doDownload('${item.downloadPath!}');">${item.fileName!}</a>&nbsp;&nbsp;&nbsp;&nbsp;
				<#if extNames.indexOf(item.extName)!=-1>
					<a href="javascript:viewAttachment('${item.id!}','${item.extName!}');">预览</a>&nbsp;&nbsp;&nbsp;&nbsp;
				</#if>
        		</#list>
	        </td>
	    </tr>
	        <tr>
	        	<th><span class="c-red">*</span>故障详情：</th>
	        	<td colspan="3" style="word-break:break-all; word-wrap:break-word;">
	        		${officeRepaire.remark?default('')}
	        	</td>
	        </tr>
	    	<tr>
	        	<th>维修状态：</th>
	        	<td>
	        		<#if officeRepaire.state == '1'>
	            		未维修
	            	<#elseif officeRepaire.state == '2'>
	            		维修中
	            	<#elseif officeRepaire.state == '3'>
	            		已维修
	            	</#if>
	        	</td>
	        	<#if officeRepaire.state != '1'>
	        	<th><span class="c-red mr-5">*</span>维修时间：</th>
	        	<td>
	        		${(officeRepaire.repaireTime?string('yyyy-MM-dd HH:mm'))?if_exists}
	        	</td>
	        	</#if>
	        </tr>
	        <#if officeRepaire.state != '1'>
	        <tr>
	    		<th width="20%">维修人：</th>
	    		<td width="30%">
					${officeRepaire.repaireUserName!}
	            </td>
	            <th width="15%">&nbsp;</th>
	            <td width="35%">
					 &nbsp;
	            </td>
	        </tr>
	        <tr>
	        	<th>维修备注：</th>
	        	<td colspan="3" style="word-break:break-all; word-wrap:break-word;">
	        		${officeRepaire.repaireRemark?default('')}
	        	</td>
	        </tr>
	        </#if>
	        <#if officeRepaire.isFeedback>
	      	<tr>
	        	<th><span class="c-red">*</span>反馈评分：</th>
	        	<td colspan="3" style="word-break:break-all; word-wrap:break-word;">
					<div class="ui-rating">
						<p>
					        <span class="rating-star">
					        	<#if officeRepaire.mark?default('')=="1">
					            <a class="star1 current" mark="1"></a>
					            <#elseif officeRepaire.mark?default('')=="2">
					            <a class="star2 current" mark="2"></a>
					            <#elseif officeRepaire.mark?default('')=="3">
					            <a class="star3 current" mark="3"></a>
					            <#elseif officeRepaire.mark?default('')=="4">
					            <a class="star4 current" mark="4"></a>
					            <#elseif officeRepaire.mark?default('')=="5">
					            <a class="star5 current" mark="5"></a>
					            </#if>
					        </span>
						</p>
					</div>
				</td>
	        </tr>
	        <tr>
	        	<th>反馈说明：</th>
	        	<td colspan="3" style="word-break:break-all; word-wrap:break-word;">
	        		${officeRepaire.feedback?default('')}
	        	</td>
        	</tr>
	        </#if>
	    </table>
	</div>
	<div class=" popUp-layer contentDivPop keep-div" id="fwLayer" style="display:none;z-index:998;">
		<p id="divTt" class="tt"><span href="javascript:void(0);"  onclick="closePop()" class="fn-right">关闭</span><span>附件/预览</span></p>
	    <div class="docReader" id="showAttDiv" style="width:855px;height:700px;display:none;"></div>
		<div class="docReader" id="editContentDiv" style="width:855px;height:700px;display:none;"></div>
	</div>
	<p class="dd">
	    <a href="javascript:void(0);" class="abtn-blue ml-5" onclick="closeDiv('#classLayer3')">取消</a>
	</p>
<script>
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