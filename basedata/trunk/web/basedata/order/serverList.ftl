<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="">
<script>   
//授权
function doAuthorize(){
	if(isCheckBoxSelect($("[id='serverIds'][type='checkbox']"))  == false){
		showMsgWarn("请先选择想要进行操作的服务！");
		return;
	}
	var ids = [];
	var i=0;
	$("input[name='serverIds'][checked='checked']").each(function(){
		ids[i] = $(this).val();
		i++;
	});
	var roleType = $("#roleType").val()
	load("#listDiv", "${request.contextPath}/basedata/order/serverAuthorize-authorizeByServer.action?"+$.param({ serverIds:ids, roleType:roleType }, true));
}

function changeRoleType(){
	var roleType = $("#roleType").val();
	load("#listDiv", "${request.contextPath}/basedata/order/serverAuthorize-serverList.action?roleType="+roleType);
}
</script>
</head>
<body>
<div id="containerServer">
<form id="serverForm" method="post">
	<div class="query-builder">
		<div class="query-part">
			<div class="query-tt">服务适用类型：</div>
			<div class="ui-select-box fn-left" style="width:100px;">
                <input type="text" class="ui-select-txt" value="" readonly/>
                <input name="roleType" id="roleType" type="hidden" value="" class="ui-select-value" />
                <a class="ui-select-close"></a>
                <div class="ui-option"  myfunchange="changeRoleType();">
                	<div class="a-wrap">
                    <#list roleTypes as x>
                    	<a val="${x[0]}" <#if roleType?string==x[0]>class="selected"</#if>><span>${x[1]}</span></a>
	  		  	  	</#list>
	  		  	  	</div>
                </div>
            </div>
            <div class="fn-clear"></div>
		</div>
	</div>
<@htmlmacro.tableList>
		<tr>
			<th width="30">选择</th>
			<th width="30%">服务代码</th>
			<th width="30%">服务名称</th>
			<th width="30%">首页地址</th>
		</tr>
		<#if servers?exists &&(servers?size>0)>	
		<#list servers as server>
			<tr>	
				<td><p><span class="ui-checkbox">
					<input type="checkbox" class="chk" name="serverIds" id="serverIds" value="${server.id}" />	         	
				</span></p></td>
				<td >
					${server.code?default('')}
				</td>
				<td >
					${server.name?default('')}
				</td>
				<td >
					${server.url?default('')}
				</td>
			</tr>
		</#list>	
		<#else>
				<tr>
		           <td colspan=4> <p class="no-data mt-50 mb-50">没有可授权的服务！</p></td>
		    	</tr>
		</#if>
</@htmlmacro.tableList>
	<div class="base-operation">
    	<p class="opt">
        	<span class="ui-checkbox ui-checkbox-all" data-all="no"><input type="checkbox" class="chk">全选</span>
            <a href="javascript:doAuthorize();" class="abtn-blue">授权</a>
        </p>
	</div>		
</div>
</form>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script>vselect();</script>
</@htmlmacro.moduleDiv>