<#import "/common/imagemacro.ftl" as imageMacro>
<#assign RANDOM=random />

<p class="tt"><a href="javascript:void(0);" onclick="closeDiv('#userImageLayer');return false;" class="close">关闭</a><span>编辑头像</span></p>
<!--头像编辑  开始 -->
<div class="block">
    <div class="head_edit clearfix">
        <img id="nowImage" class="png head_tip" src="<#if filePath?default('') != ''>${request.contextPath}/system/desktop/app/userInfo-imageModify-display.action?userSetId=${userSetId!}&fileId=${fileId!}&${RANDOM}&albumId=${albumId!}<#else>${request.contextPath}/static/images/ad/default.png</#if>" />
        <div class="head_edit_upload fl">
            <@imageMacro.singleUpload action="${request.contextPath}/system/desktop/app/userInfo-imageModify-upload.action;jsessionid=${request.session.id}" fileTypes="${fileTypes!}" description="图片文件" callBack="reloadImage()" filesize="${fileSizeLimit!}" display=false/>
            <div class="head_edit_upload_container">
		        <#if filePath?default('') != ''>
			        <#assign swfPath="${request.contextPath}/static/flash/screenShot.swf">
			    	<div id="flashcontent">
						<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
						id="test" width="556" height="520" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab">
					         <param name="movie" value="${swfPath}"/>
					         <param name="quality" value="high"/>
					         <param name="bgcolor" value="#865ca7"/>
			        		 <param name="wmode" value="Opaque"/>
					         <param name="allowScriptAccess" value="sameDomain"/>
					         <embed src="${swfPath}" quality="high" bgcolor="#865ca7"
					             width="556" height="522" name="test" align="left" wmode="Opaque"
					             play="true" loop="false" quality="high" allowScriptAccess="sameDomain"
					             type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer">
					         </embed>
					     </object>
					</div>
					<script language="JavaScript">
					     var jsReady = false;
					     function isReady() {
					         return jsReady;
					     }
					     function pageInit() {
					         jsReady = true;
					     }
					     function largeImgAction(){<#--保存大头像的action-->
					   	 	return "/userInfo-imageModify-save.action";
					     }
					     function getDomain(){<#--域名-->
					   	 	return "${request.contextPath}/system/desktop/app";
					     }
					     function getImgUrl(){<#--头像原图地址-->
					   	 	return "${request.contextPath}/system/desktop/app/userInfo-imageModify-display.action?displaySource=true&userSetId=${userSetId!}&fileId=${fileId!}&${RANDOM}&albumId=${albumId!}";
					     }
					     function getImgId(){<#--头像id-->
					   	 	return "${fileId!}";
					     }
					     function cancelFunJS(){<#--flash中点击取消操作-->
							load("#mainContentContainer","${request.contextPath}/system/desktop/app/userInfo-imageModify-index.action","","",true);
					     }
					     function refreshHead(){<#--头像编辑保存完成，刷新-->
							showMsgSuccess("头像修改成功", "提示", function(){
								location.href="${request.contextPath}/fpf/homepage/loginForEisOnly.action";				
							});		 		
						 }
						 
						 $(document).ready(function(){
							 pageInit();
						 })
					 </script>
 					</#if>               
            </div>
        </div>
    </div>
</div>