<#assign SUBSYSTEM_DATACENTER=stack.findValue('@net.zdsoft.eis.base.common.entity.SubSystem@SUBSYSTEM_DATACENTER')>
<#assign FLOW_DIAGRAM_ID_DATACENTER_HAVEDATA=stack.findValue('@net.zdsoft.eis.system.frame.entity.FlowDiagram@FLOW_DIAGRAM_ID_DATACENTER_HAVEDATA')>
<#assign FLOW_DIAGRAM_ID_DATACENTER_NODATA=stack.findValue('@net.zdsoft.eis.system.frame.entity.FlowDiagram@FLOW_DIAGRAM_ID_DATACENTER_NODATA')>
<#assign UNIT_CLASS_SCHOOL=stack.findValue('@net.zdsoft.eis.base.common.entity.Unit@UNIT_CLASS_SCHOOL')>

<#if flowDiagramList?exists && 0<flowDiagramList?size> 	
	<#list flowDiagramList! as diagram>
	    <div class="flow-wrap" >
	    	<p class="flowchart-dt">${diagram.tfdName!}</p>
	        <div class="flowchart">
	         	${diagram.html!}
	        </div>
	    </div>
    </#list>
<#else>
	<#if appId?string == "${SUBSYSTEM_DATACENTER}" && (loginInfo.unitClass?string)! == "${UNIT_CLASS_SCHOOL}">
	<div class="welcome-flow">
		<#if isHaveLastData>
			<#if isPreEdu>
				<p class="t-center">
	        	<img src="${request.contextPath}/static/images/welcome_flow5.jpg" alt="" usemap="#Map">
	            <map name="Map">
	              <area shape="rect" coords="201,100,398,199" href="javascript:void(0);"  onclick="clickModule(9607);return false;">
	              <area shape="rect" coords="486,99,678,201" href="javascript:void(0);"  onclick="clickModule(9607);return false;">
	              <area shape="rect" coords="766,98,963,138" href="javascript:void(0);" class="cannot-click">
	            </map>
	        	<img src="${request.contextPath}/static/images/welcome_flow6.jpg" alt="" usemap="#Map2">
	            <map name="Map2">
	              <area shape="rect" coords="201,100,397,164" href="javascript:void(0);" onclick="clickModule(9631);return false;">
	              <area shape="rect" coords="483,100,679,162" href="javascript:void(0);" onclick="clickModule(9631);return false;">
	              <area shape="rect" coords="766,99,962,136" href="javascript:void(0);" onclick="clickModule(9606);return false;">
	              <area shape="rect" coords="767,219,961,257" href="javascript:void(0);" onclick="clickModule(9651);return false;">
	              <area shape="rect" coords="768,280,957,315" href="javascript:void(0);" onclick="clickModule(9652);return false;">
	              <area shape="rect" coords="485,218,677,303" href="javascript:void(0);" onclick="clickModule(9607);return false;">
	              <area shape="rect" coords="202,217,394,285" href="javascript:void(0);" onclick="clickModule(9607);return false;">
	              <area shape="rect" coords="204,351,395,391" href="javascript:void(0);" onclick="clickModule(9608);return false;">
	              <area shape="rect" coords="484,354,678,393" href="javascript:void(0);" onclick="clickModule(9609);return false;">
	              <area shape="rect" coords="767,355,960,437" href="javascript:void(0);" onclick="clickModule(9609);return false;">
	            </map>
	        	</p>
			<#else>
			<p class="t-center">
				<img src="${request.contextPath}/static/images/welcome_flow1.jpg" alt="" usemap="#Map1">
	            <map name="Map1">
	              <area shape="rect" coords="149,162,346,227" href="javascript:void(0)"  onclick="clickModule(9609);return false;">
	              <area shape="rect" coords="379,162,571,276" href="javascript:void(0)" class="cannot-click">
	              <area shape="rect" coords="765,101,962,142" href="javascript:void(0)" onclick="clickModule(9672);return false;">
	              <area shape="rect" coords="766,161,960,203" href="javascript:void(0)" onclick="clickModule(9653);return false;">
	              <area shape="rect" coords="765,220,963,262" href="javascript:void(0)" onclick="clickModule(9641);return false;">
	              <area shape="rect" coords="764,280,963,397" href="javascript:void(0)" onclick="clickModule(9609);return false;">
	            </map>
	        	<img src="${request.contextPath}/static/images/welcome_flow2.jpg" alt="" usemap="#Map2">
	            <map name="Map2">
	              <area shape="rect" coords="202,100,395,199" href="javascript:void(0)" onclick="clickModule(9607);return false;">
	              <area shape="rect" coords="485,98,679,200" href="javascript:void(0)" onclick="clickModule(9607);return false;">
	              <area shape="rect" coords="768,101,961,140" href="javascript:void(0)" class="cannot-click">
	            </map>
	        	<img src="${request.contextPath}/static/images/welcome_flow3.jpg" alt="" usemap="#Map3">
	            <map name="Map3">
	              <area shape="rect" coords="202,98,397,163" href="javascript:void(0)" onclick="clickModule(9631);return false;">
	              <area shape="rect" coords="484,100,679,181" href="javascript:void(0)" onclick="clickModule(9631);return false;">
	              <area shape="rect" coords="769,99,961,138" href="javascript:void(0)" onclick="clickModule(9606);return false;">
	              <area shape="rect" coords="766,219,962,258" href="javascript:void(0)" onclick="clickModule(9651);return false;">
	              <area shape="rect" coords="768,278,960,317" href="javascript:void(0)" onclick="clickModule(9652);return false;">
	              <area shape="rect" coords="485,219,681,305" href="javascript:void(0)" onclick="clickModule(9607);return false;">
	              <area shape="rect" coords="203,218,399,282" href="javascript:void(0)" onclick="clickModule(9607);return false;">
	              <area shape="rect" coords="201,352,398,391" href="javascript:void(0)" onclick="clickModule(9608);return false;">
	              <area shape="rect" coords="483,351,681,393" href="javascript:void(0)" onclick="clickModule(9609);return false;">
	              <area shape="rect" coords="765,353,959,436" href="javascript:void(0)" onclick="clickModule(9609);return false;">
	            </map>
			</p>
			</#if>
		<#else>
			<p class="t-center"><img src="${request.contextPath}/static/images/welcome_flow4.jpg" alt="" usemap="#Map4">
	          <map name="Map4">
	            <area shape="rect" coords="202,102,399,168" href="javascript:void(0)" onclick="clickModule(9631);return false;">
	            <area shape="rect" coords="484,101,679,188" href="javascript:void(0)" onclick="clickModule(9631);return false;">
	            <area shape="rect" coords="768,101,964,143" href="javascript:void(0)" onclick="clickModule(9606);return false;">
	            <area shape="rect" coords="768,221,961,262" href="javascript:void(0)" onclick="clickModule(9673);return false;">
	            <area shape="rect" coords="765,283,961,324" href="javascript:void(0)" onclick="clickModule(9651);return false;">
	            <area shape="rect" coords="765,339,964,384" href="javascript:void(0)" onclick="clickModule(9652);return false;">
	            <area shape="rect" coords="487,219,680,266" href="javascript:void(0)" onclick="clickModule(9641);return false;">
	            <area shape="rect" coords="481,282,681,321" href="javascript:void(0)" onclick="clickModule(9653);return false;">
	            <area shape="rect" coords="203,223,399,262" href="javascript:void(0)" onclick="clickModule(9672);return false;">
	            <area shape="rect" coords="200,417,397,455" href="javascript:void(0)" onclick="clickModule(9608);return false;">
	            <area shape="rect" coords="482,414,680,458" href="javascript:void(0)" onclick="clickModule(9609);return false;">
	            <area shape="rect" coords="764,414,963,506" href="javascript:void(0)" onclick="clickModule(9609);return false;">
	          </map>
			</p>
		</div>
		</#if>
	<#else>
		<div class="welcome">欢迎使用${subsystemName!}</div>
	</#if>
</#if>
<script>
function clickModule(moduleId){
	$("#module"+moduleId).click();
}

$(document).ready(function(){
	//流程图
	$('.flow-wrap .flowchart-dt').click(function(){
		$(this).toggleClass('flowchart-dt-open');
		$(this).siblings('.flowchart').toggle();
	});
	$('.flow-wrap .flowchart-dt:first').toggleClass('flowchart-dt-open');
	$('.flow-wrap .flowchart-dt:first').siblings('.flowchart').toggle();
	$('.cannot-click').click(function(){
		return false;
	});
})
</script>