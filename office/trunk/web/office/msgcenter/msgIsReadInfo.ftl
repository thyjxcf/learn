<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="查看信息">
<p class="tt"><a href="javascript:void(0);" onclick="closeDiv('#msgLayer');return false;" class="close">关闭</a><span><#if isRead == 0>未读<#else>已读</#if>人员</span></p>
<div class="wrap userList-layer" id="memoEditDiv">
        <#list readInfoDtos as x>
	  <h3>${x.unitName!}<span>   ${x.userDeptNameList.size()!}人</span></h3>
	  <ul class="fn-clear">
		<#list x.userDeptNameList as str>
	        	<li>${str!}</li>
		</#list>
        </ul>
    	</#list>
</div>
            <p class="dd">
            	<a class="abtn-blue reset" onclick="closeDiv('#msgLayer');return false;" href="javascript:void(0);"><span>关闭</span></a>
            </p>
<script type="text/javascript">
</script>
</@common.moduleDiv>