<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="">
<script>
function changeRole(roleId){
	load("#roleListFrame","${request.contextPath}/basedata/user/userAdmin-accredit.action?uid=${user.id?default('')}&roleId="+roleId);
}
</script>
	<div class="permission-grid" >
        	<div class="permission-grid-inner">
            	<p class="permission-tt">用户对应角色：</p>
                <div class="permission-wrap" style="height:320px;overflow-y:auto">
			  	  	<#list roleList?if_exists as role>
			  	  	  <span class="item" <#if role.isactive>onclick="changeRole('${role.id?default('-1')}')"</#if>>
			  	  	  	  <a href="javascript:void(0);">${role.name?default('')}<#if !role.isactive><font color="red">(已锁定)</font></#if></a>
			  	  		</span>
			  	  	</#list>
                </div>
            </div>
        </div>
        <div class="permission-grid permission-grid-big" >
        	<div class="permission-grid-inner" id="roleListFrame" style="height:450px;overflow-y:auto">
            	
            </div>
        </div>
<script>
load("#roleListFrame","${request.contextPath}/basedata/user/userAdmin-accredit.action?uid=${id?default('')}");
</script>
</@common.moduleDiv>