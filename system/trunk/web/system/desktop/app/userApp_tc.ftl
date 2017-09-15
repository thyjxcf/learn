<#import "/common/htmlcomponent.ftl" as htmlmacro>
<script>
function appSet(){
	openDiv('#commonApp',null,'${request.contextPath}/system/desktop/app/userApp-set-tc.action',false,'#commonApp .wrap',null,'commonAppScroll()');
}

function go2ExternalApp(url){
	if(url == "")
		return;
	window.open(url);
}

//app初始化
	$('.app-wrap .app-item').each(function(){
		var ulWidth=870;
		var ulLen=$(this).find('.app-item-inner ul').length;
		var itemWidth=ulWidth*ulLen;
		$(this).children('.app-item-inner').width(itemWidth);
	});

//分页
$('.app-item-page a').click(function(e){
	e.preventDefault();
	var myIndex=$(this).index();
	var ulWidth=870;
	var itemLeft='-'+ulWidth*myIndex+'px';
	$(this).addClass('current').siblings('a').removeClass();
	$(this).parent('.app-item-page').prev('.app-item-inner').stop(true).animate({'left':itemLeft},500);
});
</script>
<!--=S 平台首页 Start-->
<div class="app-item-inner">
	<ul class="fn-clear">
	<#if userAppList?exists && 0 < userAppList?size>
		<#list userAppList! as app>
	        <li>
		        <a href="javascript:void(0);" onclick="<#if app.picture! =="" || app.picture?index_of(".") != -1>go2ExternalApp('${app.url!}');<#else>go2Module('${app.url}','${app.subsystem}','${app.moduleId}','${app.parentId}','${app.name}','desktop','${app.limit!}');return false;</#if>"><img src="<#if app.picture! =="" || app.picture?index_of(".") != -1>${request.contextPath}/static/images/ad/3.png<#else>${request.contextPath}${app.picture}_m.png</#if>" alt="${app.name}" />
		        	<span><@htmlmacro.cutOff str=app.name length=6 /></span>
		        </a>
	        </li>	
        	<#if (app_index + 1) % 35 == 0>
        	</ul><ul class="fn-clear">
        	</#if>
        </#list>
	</#if>
	<li class="add"><a href="javascript:void(0);" onclick="appSet();return false;"><img src="${request.contextPath}/static/images/app/add.png" alt="添加" /><span>添加</span></a></li>
    </ul>
</div>
<div class="app-item-page">
	<#assign num = ((userAppList?size+1) / 35)?int >
    	<#assign num1 = (userAppList?size+1) % 35 >
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
