<#--跟进人-->
<#macro selectFollowUser idObjectId nameObjectId url="${request.contextPath}/common/open/customer/getFollowUserDataPopup.action" otherParam="" callback="" preset="" dynamicParam=""  width=300 height=380 switchSelector="" showCancelButton="true">
	<@selectObject useCheckbox=false idObjectId=idObjectId nameObjectId=nameObjectId url=url otherParam=otherParam callback=callback preset=preset dynamicParam=dynamicParam width=width height=height switchSelector=switchSelector showCancelButton=showCancelButton>
		<#nested />
	</@selectObject>	
</#macro>

<#--抄送人-->
<#macro selectCopyUser idObjectId nameObjectId url="${request.contextPath}/common/open/customer/getCopyUserDataPopup.action" otherParam="" callback="" preset="" dynamicParam=""  width=300 height=380 switchSelector="" showCancelButton="true">
	<@selectObject useCheckbox=false idObjectId=idObjectId nameObjectId=nameObjectId url=url otherParam=otherParam callback=callback preset=preset dynamicParam=dynamicParam width=width height=height switchSelector=switchSelector showCancelButton=showCancelButton>
		<#nested />
	</@selectObject>	
</#macro>

<#macro selectObject useCheckbox idObjectId nameObjectId url otherParam="" callback="" preset="" dynamicParam="" width=300 height=380 switchSelector="" loadCallback="" showCancelButton="true">
	<#assign showObjectsFunction = "showPopupObjects('"+idObjectId+"','"+nameObjectId+"','"+url+"','"+useCheckbox?string+"','"+otherParam+"','"+callback+"','"+preset+"','"+dynamicParam+"',"+width+","+height+");">	

	<div id="${idObjectId}_nestedDiv"><#nested /></div>
	<div id="${idObjectId}_div" class="select_box02" style="z-index:999;" val_height="${height}" val_switchSelector="${switchSelector}" val_loadCallback="${loadCallback}" val_showCancelButton="${showCancelButton}">
		<div class="select_list02_container" style="width:${width}px;">
		</div>
	</div>
	<script language="JavaScript" src="${request.contextPath}/static/common/popupObjectSelect.js"></script>	
	<script>
		$(document).ready(function(){
			${showObjectsFunction}
		});
	</script>
</#macro>