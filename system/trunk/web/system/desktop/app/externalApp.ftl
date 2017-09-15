<script>
	function go2ExternalApp(url){
		if(url == "")
			return;
		window.open(url);
	}
	
	function addApp(){
		openDiv("#addSystem",null,"${request.contextPath}/system/desktop/app/externalApp-set.action");
	}
	
	function myApp(){
		var ulW=$('.myApp-wrap').width();
		var len=$('.myApp-wrap ul').length;
		var myW=ulW*len;
		$('.myApp-wrap ul').width(ulW);
		$('.myApp-inner').width(myW);
		$('.myApp-page a').click(function(e){
			e.preventDefault();
			var dataSort=parseInt($(this).parent('.myApp-page').attr('data-sort'));
			if($(this).hasClass('next')){
				if(dataSort<len-1){
					dataSort=dataSort+1;
					var sLeft='-'+dataSort*ulW+'px';
					$('.myApp-page').attr('data-sort',dataSort);
					$('.myApp-inner').animate({'left':sLeft},300);
				}else{
					$('.myApp-page').attr('data-sort',0);
					$('.myApp-inner').animate({'left':0},300);
				};
			}else{
				if(dataSort>0){
					dataSort=dataSort-1;
					var sLeft='-'+dataSort*ulW+'px';
					$('.myApp-page').attr('data-sort',dataSort);
					$('.myApp-inner').animate({'left':sLeft},300);
				}else{
					var sLeft='-'+(len-1)*ulW+'px';
					$('.myApp-page').attr('data-sort',len-1);
					$('.myApp-inner').animate({'left':sLeft},300);
				};
			};
		});
	};
	
	$(document).ready(function(){
		myApp();
	});
	
</script>
<div class="dt">
	<span class="fn-right page myApp-page" data-sort="0">
    	<a href="javascript:void(0);" class="prev"></a>
        <a href="javascript:void(0);" class="next"></a>
    </span>
    <#if loginInfo.user.type ==0 || loginInfo.user.type ==1>
    <a href="javascript:void(0);" onclick="addApp();" class="abtn-blue fn-right" style="margin-top:-5px;">设置</a>
    </#if>
	<span class="item-name"><#if systemDeploySchool! =='nbzx'>外部链接设置<#elseif systemDeploySchool! =='xinjiang'>管理服务<#elseif systemDeploySchool! =='hzzc'>信息化建设<#elseif systemDeploySchool! =='hdjy'>智慧教育服务应用<#else>数字化对接</#if></span>
</div>
<#if systemDeploySchool! =="hzzc" || systemDeploySchool! =="jian">
	<#assign pageNum=14>
<#else>
	<#assign pageNum=6>
</#if>
<div class="myApp-wrap">
	<div class="myApp-inner fn-clear">
		<#list externalAppList as app>
		<#if app_index%pageNum ==0>
        <ul class="fn-clear">
        </#if>
            <li><a href="javascript:void(0)" onclick="go2ExternalApp('${app.appUrl!}')"><img src="${app.downloadPath!}" alt="${app.appName!}"><span>${app.appName!}</span></a></li>
        <#if (app_index+1)%pageNum ==0>
        </ul>
        </#if>
        </#list>
    </div>
</div>