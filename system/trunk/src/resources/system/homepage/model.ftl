<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#if subSystem.multiScreen?default(0) ==1 && loginInfo.user.ownerType ==2>
<a href="javascript:void(0);" class="prev first"  style="display:none;" hidefocus="true">prev</a>
<a href="javascript:void(0);" class="next" style="display:none;" hidefocus="true">next</a>
</#if>
<div class="sub-menu">
	<div class="sub-menu-wrap">
		<#if firstModelList?exists && (firstModelList?size >0)>
		<#list firstModelList as msg>
		<div class="sub-menu-grid">
	    	<div class="model-grid">
	    		 <ul class="big-model">
	    		 	<#list secondCommonModelMap.get(msg.mid) as commonMsg2>
	                	<li id="module${commonMsg2.id}" class="module" limit="${commonMsg2.limit!}" onClick="go2Module('${commonMsg2.url!}','${commonMsg2.subsystem!}','${commonMsg2.id!}','${msg.id}','${commonMsg2.name}','subsystem','${commonMsg2.limit!}'); return false;"><a href="javascript:void(0);" title="${commonMsg2.name}"><img src="<#if commonMsg2.picture! =="">${request.contextPath}/static/images/ad/3.png<#else>${request.contextPath}${commonMsg2.picture}_m.png</#if>" alt="${commonMsg2.name}"><@htmlmacro.cutOff str=commonMsg2.name length=8/></a></li>
	           		</#list>
	            </ul>
	            <ul class="small-model">
	    		 	<#list secondModelMap.get(msg.mid) as msg2>
	                	 <li id="module${msg2.id}" class="module" limit="${msg2.limit!}" onClick="go2Module('${msg2.url!}','${msg2.subsystem!}','${msg2.id!}','${msg.id}','${msg2.name}','subsystem','${msg2.limit!}'); return false;"><a href="javascript:void(0);" title="${msg2.name}"><img src="<#if msg2.picture! =="">${request.contextPath}/static/images/ad/4.png<#else>${request.contextPath}${msg2.picture}_s.png</#if>" alt="${msg2.name}"><@htmlmacro.cutOff str=msg2.name length=8/></a></li>
	           		</#list>
	            </ul>
	        </div>
	        <p class="grid-name" id="module${msg.id}">${msg.name}</p>
	    </div>
	    </#list>
	    </#if>
		<div class="sub-menu-grid sub-menu-grid-last"></div>
	</div>
</div>	
<div class="sub-menu-mini" style="display:none;">
<#if smallModelList?exists && (smallModelList?size >0)>
	<#list smallModelList as smallMsg>
	 	<a href="javascript:void(0);" onClick="go2Module('${smallMsg.url!}','${smallMsg.subsystem!}','${smallMsg.id!}','${smallMsg.parentid}','${smallMsg.name}','subsystem','${smallMsg.limit!}'); return false;" title="${smallMsg.name}"><img src="<#if smallMsg.picture! =="">${request.contextPath}/static/images/ad/3.png<#else>${request.contextPath}${smallMsg.picture}_s.png</#if>" alt="${smallMsg.name}"><@htmlmacro.cutOff str=smallMsg.name length=8/></a>
    </#list>
</#if>
</div>
<script>
$(document).ready(function(){
	$('.small-model').each(function(){
		var modelLen=$(this).find('li').length;
		var modeCount=3;
		var modelGrid=Math.ceil(modelLen/modeCount);
		var modelWidth=modelGrid*128;
		$(this).width(modelWidth);
	});
	
	<#if moduleID ! !=0>
		$('#module${moduleID}').addClass('current');
	</#if>
	<#if appId ! !=0>
		<#if subSystem.parentId ==-1>
			$('#subsystem${subSystem.id}').addClass('current');
		<#else>
			$('#subsystem${subSystem.parentId}').addClass('current');
		</#if>
	</#if>
	<#if subSystem.multiScreen?default(0) ==1 && loginInfo.user.ownerType ==2>
		$('#modelList').addClass('sub-menu-cut');
		var reLen=$('.sub-menu-wrap .sub-menu-grid').length;
		var wrapWidth=$('.sub-menu').width();
		var myWidth=reWidth=0;
		for(var i=0;i<reLen;i++){
			myWidth=myWidth+$('.sub-menu-wrap .sub-menu-grid:eq('+i+')').width()+12;
			if(myWidth>wrapWidth){
				$('.sub-menu-cut .next,.sub-menu-cut .prev').show();
				for(var j=0;j<i;j++){
					reWidth=reWidth+$('.sub-menu-wrap .sub-menu-grid:eq('+j+')').width()+12;
				};
				var marginLeft=(wrapWidth-reWidth)/2;
				var j=j-1;
				$('.sub-menu-wrap .sub-menu-grid:first').addClass('sub-menu-grid-first');
				$('.sub-menu-wrap .sub-menu-grid:eq('+j+')').addClass('sub-menu-grid-right').next('.sub-menu-grid').addClass('sub-menu-grid-first');
				$('.sub-menu-grid-first').css('margin-left',marginLeft).siblings('.sub-menu-grid-right').css('margin-right',marginLeft);
				return false;
			}
		};
	<#else>
		$('#modelList').removeClass('sub-menu-cut');
	</#if>
});
</script>
<script src="${request.contextPath}/static/js/myscript.js"></script>