<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="">
<script>
function showAccredit(userid){
	load("#unitListFrame","${request.contextPath}/basedata/user/userAdmin-accreditRole.action?id="+userid);
}
function doRetru(){
load("#container","${request.contextPath}/basedata/user/userAdmin.action");
}
</script>
<div class="view-permission fn-clear" >
    	<div class="permission-grid">
        	<div class="permission-grid-inner">
            	<p class="permission-tt">选择的用户列表：</p>
                <div class="permission-wrap" style="height:320px;overflow-y:auto">
		          <#list userList?if_exists as user>
		          	<span class="item" onclick="showAccredit('${user.id?default('')}')">
		          	  	<a href="javascript:void(0);" >${user.name?default('')}</a>
		          	 </span>
		          </#list>
                </div>
            </div>
        </div>
    	<div id="unitListFrame" name="unitListFrame"></div>
</div>
    	<p class="table-bt-gray t-center"><a href="javascript:void(0);" onclick="doRetru();" class="abtn-blue">返回</a></p>
<script>
<#list userList as user>
  <#if user_index==0>
  	showAccredit('${user.id?default('')}');
  <#else>
  	<#break>
  </#if>
</#list>
</script>
</@common.moduleDiv>
