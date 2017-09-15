<#import "/common/htmlcomponent.ftl" as htmlmacro>
<div style="position:absolute;z-index:-1;width:101%;height:101%;margin-left:-0.5%;">  
	<iframe style="width:103%;height:100%;border:0;filter:alpha(opacity=0);-moz-opacity:0"></iframe>  
</div>
<a href="javascript:void(0);" class="inside-nav-cut inside-nav-cut-hide" title="收起"></a>
<a href="javascript:void(0);" class="back-home"><span>返回</span></a>
<div class="inside-nav-wrap mCustomScrollbar" id="navScroll">
	<div class="inside-nav-inner">
    	<div class="inside-nav-list">
        	<#if firstModelList?exists && (firstModelList?size >0)>
			<#list firstModelList as msg>
        	<p class="tt">${msg.name}</p>
            <ul>
            	<#list secondCommonModelMap.get(msg.mid) as commonMsg2>
            		<li class="module module${commonMsg2.id}" id="limodule${commonMsg2.id}" alt="${commonMsg2.name}" onClick="go2Module('${commonMsg2.url!}','${commonMsg2.subsystem!}','${commonMsg2.id!}','${msg.id}','${commonMsg2.name}','side','${commonMsg2.limit!}'); return false;">
            			<a href="javascript:void(0);" title="${commonMsg2.name!}"><img src="${request.contextPath}${commonMsg2.picture}_s.png"><span><@htmlmacro.cutOff str=commonMsg2.name length=7/></span></a>
            		</li>
            	</#list>
            	<#list secondModelMap.get(msg.mid) as msg2>
            		<li class="module module${msg2.id}" id="limodule${msg2.id}" alt="${msg2.name}" onClick="go2Module('${msg2.url!}','${msg2.subsystem!}','${msg2.id!}','${msg.id}','${msg2.name}','side','${msg2.limit!}'); return false;">
            			<a href="javascript:void(0);" title="${msg2.name!}"><img src="${request.contextPath}${msg2.picture}_s.png"><span><@htmlmacro.cutOff str=msg2.name length=7/></span></a>
            		</li>
            	</#list>
            </ul>
            </#list>
            </#if>
        </div>
    </div>
</div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript-nav-inside.js"></script>
<script>
$(document).ready(function(){
	<#--
	$('.small-model').each(function(){
		var modelLen=$(this).find('li').length;
		var modeCount=3;
		var modelGrid=Math.ceil(modelLen/modeCount);
		var modelWidth=modelGrid*128;
		$(this).width(modelWidth);
	});-->
	// 默认缩小导航
	$('.inside-nav-cut-hide').click();
	// 返回桌面
	$('.back-home').click(function(){
		$('#subsystemListDiv').show();
		$('#modelList').hide();
	});
	
	<#if moduleID ! !=0>
		$('.module${moduleID}').addClass('current');
	</#if>
	<#if appId ! !=0>
		<#if subSystem.parentId ==-1>
			$('#subsystem${subSystem.id}').addClass('current');
		<#else>
			$('#subsystem${subSystem.parentId}').addClass('current');
		</#if>
	</#if>
	
});
</script>