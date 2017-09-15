<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<p class="tt"><a href="javascript:void(0);" id="closeBtn" class="close">关闭</a><span>办公地点</span></p>
<iframe width="700" height="500" scrolling="no"
	src="${request.contextPath}/office/teacherAttendance/teacherAttendance-placeEditSelMap.action?place.mapName=${place.mapName!}&place.address=${place.address!}&place.latitude=${place.latitude!}&place.longitude=${place.longitude!}"></iframe>
</@htmlmacro.moduleDiv>