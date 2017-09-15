<#import "/common/htmlcomponent.ftl" as htmlmacro>
<div style="position:absolute;z-index:-1;width:101%;height:101%;margin-left:-0.5%;left:0px;top:0px;">  
	<iframe style="width:100%;height:103%;border:0;filter:alpha(opacity=0);-moz-opacity:0"></iframe>  
</div>
<table>
    <#if firstModelList?exists && (firstModelList?size >0)>
	<#assign flsize = firstModelList?size />
	<#assign lineNum=(flsize/3)?int />
	<#if flsize%3 !=0>
	<#assign lineNum=lineNum+1 />
	</#if>
	<#assign indexNum=0 />
	<#list firstModelList as msg>
    <#if msg_index%3==0>
    <tr>
    <#assign indexNum=indexNum+1 />
    </#if>
        <td <#if flsize gt 1>class="<#if msg_index%3!=2 && (msg_has_next || lineNum gt 1)>line-right</#if> <#if msg_has_next && indexNum lt lineNum>line-bottom</#if>"</#if>>
            <div class="sub-nav-wrap">
                <div class="sub-nav fn-clear">
                    <#list secondCommonModelMap.get(msg.mid) as commonMsg2>
                    <a class="module${commonMsg2.id}" id="module${commonMsg2.id}" href="javascript:void(0);" onClick="go2Module('${commonMsg2.url!}','${commonMsg2.subsystem!}','${commonMsg2.id!}','${msg.id}','${commonMsg2.name}','desktop','${commonMsg2.limit!}'); return false;"><img src="${request.contextPath}${commonMsg2.picture}_s.png" alt="${commonMsg2.name}"><@htmlmacro.cutOff4List str=commonMsg2.name length=8/></a>
                    </#list>
                    <#list secondModelMap.get(msg.mid) as msg2>
                    <a class="module${msg2.id}" id="module${msg2.id}" href="javascript:void(0);" onClick="go2Module('${msg2.url!}','${msg2.subsystem!}','${msg2.id!}','${msg.id}','${msg2.name}','desktop','${msg2.limit!}'); return false;"><img src="${request.contextPath}${msg2.picture}_s.png" alt="${msg2.name}"><@htmlmacro.cutOff4List str=msg2.name length=8/></a>
                    </#list>
                </div>
                <p class="sub-nav-name" id="module${msg.id}">${msg.name}</p>
            </div>
        </td>
    <#if msg_index%3 ==2 || !msg_has_next>
		    <#if msg_index%3 != 2 && flsize gt 1 && lineNum gt 1>
		    	<td>&nbsp;</td>
		    </#if>
	    </tr>
    </#if>
    </#list>
	</#if>
</table>
<script>
$(document).ready(function(){
	<#--$('.small-model').each(function(){
		var modelLen=$(this).find('li').length;
		var modeCount=3;
		var modelGrid=Math.ceil(modelLen/modeCount);
		var modelWidth=modelGrid*128;
		$(this).width(modelWidth);
	});-->
	
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
<script src="${request.contextPath}/static/js/myscript.js"></script>