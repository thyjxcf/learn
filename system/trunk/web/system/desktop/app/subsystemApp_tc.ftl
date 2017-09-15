<#import "/common/htmlcomponent.ftl" as htmlmacro>
<!--=S 平台首页 Start-->
<div class="app-item-inner">
	<ul class="fn-clear">
		<#list userSystemList! as app>
			<#if app.type! =='1'>
				<#if app.source == "99">
			        <li>
				        <a href="javascript:void(0);" onClick="go2subsystem('${app.xurl}','${app.id}','dir','${app.source!}');return false;">
				        <img src="${app.image!}" alt="${app.appname}" />
				        <span><@htmlmacro.cutOff str=app.appname length=6 /></span>
				        </a>
			        </li>
			   <#else>
			   		 <li>
				        <a href="javascript:void(0);" onClick="go2subsystem('${app.xurl}','${app.id}','dir','${app.source!}','${app.parentId}');return false;">
				        <img src="${fileUrl}/store/subsystempic/${app.image!}" alt="${app.appname}" />
				        <span><@htmlmacro.cutOff str=app.appname length=6 /></span>
				        </a>
			        </li>
			   </#if> 
		   </#if>   	
        	<#if (app_index + 1) % 36 == 0>
        	</ul><ul class="fn-clear">
        	</#if>
        </#list>
    </ul>
</div>
<div class="app-item-page">
	<#assign num = ((userSystemList?size+1) / 36)?int >
    	<#assign num1 = (userSystemList?size+1) % 36 >
        <#if num == 0 >
    		<!--<li class="on">&nbsp;&nbsp;&nbsp;</li>-->
    	<#else>
    		<#if num1 != 0>
    		<#assign num=num+1>
    		</#if>
    		<#if 1 < num >
	        	<#list 1 .. num as t >
	        		<#if t ==1>
	        		<a href="javascript:void(0);" class="current"></a>
	            	<#else>
	            	<a href="javascript:void(0);"></a>
	            	</#if>
		        </#list>
	        </#if>
	    </#if>
</div>
