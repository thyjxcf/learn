<#import "/common/htmlcomponent.ftl" as common>
<#import "/common/commonmacro.ftl" as commonmacro>
<@common.moduleDiv>
<div class="pub-table-wrap">
<form action="" method="post" name="officeMailform" id="officeMailform" enctype="multipart/form-data">
<input type="hidden" id="id" name="officeJzmail.id" value="${officeJzmail.id?default('')}"/>
<@common.tableDetail divClass="table-form">
		<tr>
		    <th colspan="4" style="text-align:center;">留言申请</th>
		</tr>
        <tr>
            <th style="width:20%"><span class="c-red">*</span> 标题：</th>
            <td colspan="3" style="width:70%">
            	${officeJzmail.title!}
	        </td>
        </tr>
        <tr>
        	<th style="width:20%"> 邮箱：</th>
        	<td style="width:30%">
				${officeJzmail.mail!}
        	</td>
        	<th style="width:20%"><span class="c-red">*</span> 手机：</th>
        	<td style="width:30%">
				${officeJzmail.phone!}
        	</td>
        </tr> 
        <tr>
        	<th style="width:20%"> 状态：</th>
        	<td style="width:30%">
				<#if officeJzmail.state?exists&&officeJzmail.state==1>
				已提交
				<#else>
				已回复
				</#if>
        	</td>
        	<th style="width:20%"> 处理人：</th>
        	<td style="width:30%">
        		${officeJzmail.dealUserName!}
        	</td>
        </tr> 
        <tr>
        	<th style="width:20%"><span class="c-red">*</span> 内容：</th>
            <td style="width:80%" colspan="3">
            	${officeJzmail.content!}
	        </td>
        </tr>
        <tr id="picture" <#if officeJzmail.display?exists&&officeJzmail.display> style="display:none" <#else> style="display:none"</#if>>
        	<th style="width:20%"><span class="c-red">*</span> 图片：</th>
            <td style="width:80%" colspan="3">
            	<ul class="accessory-list fn-clear pt-10" id="upload-spanLi">
                <#if officeJzmail.attachments?exists>
					<#list officeJzmail.attachments as att>
						<li id="attP${att_index}" style="height:50%;width:50%;">
		                    <#if att.extName=='png'||att.extName=='jpg'||att.extName=='jpeg'||att.extName=='gif'||att.extName=='bmp'>
		                    <img src="${att.downloadPath!}" style="height:100%;width:100%;">
		                    </#if>
		                </li>
					</#list>
				</#if>
            </ul>
	        </td>
        </tr>
        <tr>
        <div class=" popUp-layer contentDivPop keep-div" id="fwLayer" style="display:none;z-index:998;">
			<p id="divTt" class="tt"><a href="javascript:void(0);"  onclick="closePop()" class="fn-right">关闭</a><span>图片/预览</span></p>
	    	<div class="docReader" id="showAttDiv" style="width:855px;height:700px;display:none;overflow-x:auto;"></div>
		</div>
		</tr>
        <tr>
        	<th>附件：</th>
            <td colspan="3">
            	<ul class="accessory-list fn-clear pt-10" id="upload-spanLi">
                <#if officeJzmail.attachments?exists>
					<#list officeJzmail.attachments as att>
						<li id="attP${att_index}">
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
		                    <span class="name" style="width:300px;" title="${att.fileName!}"><@common.cutOff str='${att.fileName!}' length=22/></span>
		                    <span class="fr">
		                        <a href="javascript:void(0);"  onclick="doDownload('${att.downloadPath!}');">下载</a>
		                        <#if att.extName=='png'||att.extName=='jpg'||att.extName=='jpeg'||att.extName=='gif'||att.extName=='bmp'>
		                        <a href="javascript:void(0);"  onclick="doView('${att.id!}');">预览</a>
		                        </#if>
		                    </span>
		                </li>
					</#list>
				</#if>
            </ul>
	        </td>
        </tr>
        <tr>
        	<td colspan="4" class="td-opt">
            	<a href="javascript:void(0);" class="abtn-blue" onclick="goBack();">返回</a>
            </td>
        </tr>
  <iframe name="downloadFrame" id="downloadFrame" style="display:none;"></iframe>
  </@common.tableDetail>
</form>
<script>
	function goBack(){
		doQueryChange();
	}
	
	//显示预览的附件
		function doView(attachmentId) {
			$("#showAttDiv").attr('attId',attachmentId).show();
			$("#fwLayer").show();
			$("#divTt").show();
			$("#fwLayer").css("width","855px").css("height","764px");
			$("#fwLayer").css("top","190px").css("left","18%" );
			load("#showAttDiv","${request.contextPath}/office/jzmailmanage/jzmailmanage-myPictureView.action?attachmentId="+attachmentId);
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
		
	function editDivHide(){
				$("#showAttDiv").hide();
				$("#fwLayer").hide();
				//$("#divTt").hide();
	}
</script>
</@common.moduleDiv>