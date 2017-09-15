<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="${webAppTitle}--用户管理">
<script>
//锁定用户
function lockUser(){
	if(check()){
		tongYong("${request.contextPath}/basedata/user/userAdmin-lock.action?modID=${modID?default('')}");
	}
}
//用户解锁
function unlockUser(){
	if(check()){
		tongYong("${request.contextPath}/basedata/user/userAdmin-unlock.action?modID=${modID?default('')}");
	}
}
//审核
function auditUser(){
	if(check()){
		tongYong("${request.contextPath}/basedata/user/userAdmin-audit.action?modID=${modID?default('')}");
	}
}
//角色委派
function roleUser(){
	var arrayIds = new Array()
	var f=document.getElementById('ec');
	var ischenkval=check();
	if(ischenkval){
		var ids=document.getElementsByName('arrayIds');
		var count=0;
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked){
				arrayIds[count]=ids[i].value;
				count++;
			}
		}
		if(count>1){
		  if(window.confirm('您选择了多个用户进行角色委派，将会显示各用户的所有角色，确定吗？')){
		  	load("#container","${request.contextPath}/basedata/user/userAdmin-role.action?modID=${modID?default('')}&arrIdsStr="+arrayIds);
		  }
		}else{
			load("#container","${request.contextPath}/basedata/user/userAdmin-role.action?modID=${modID?default('')}&arrIdsStr="+arrayIds);
		}
	}
}


function check(){
	var flag=false;
	var f=document.getElementById('ec');	
	var length = f.elements.length;
	for(i=0;i<length;i++){
		if(f.elements[i].name == "arrayIds" && f.elements[i].checked){
			flag = true;
		}
	}
	if(!flag){
		addActionError("请先选择想要进行操作的用户！");
	}
	return flag;
}

function tongYong(url){
  		 jQuery.ajax({
	   		url:url,
	   		type:"POST",
	   		data:jQuery('#ec').serialize(),
	   		dataType:"JSON",
	   		async:false,
	   		success:function(data){
	   			if(data.operateSuccess){
	   					showMsgSuccess(data.promptMessage,"",function(){
	   					load("#container","${request.contextPath}/basedata/user/userAdmin.action");
	   				});
	   			}else{
	   				showMsgError(data.errorMessage);
	   			}
	   		}
 		});
}
//编辑
function doEdit(userid){
	load("#container","${request.contextPath}/basedata/user/userAdmin-edit.action?modID=${modID?default('')}&&id="+userid);
}
//密码规则
function genericPassword(){
	openDiv("#classLayer","#classLayer .close,#classLayer .reset",
	 "${request.contextPath}/basedata/user/userAdmin-genericPassword.action?modID=${modID?default('')}", null, null ,null,function(){vselect();},null);
}
//权限查看
function userAccredit(){
	var arrayIds = new Array();
	var elem=document.getElementsByName('arrayIds');
	var num=0;
	for(var i=0;i<elem.length;i++){
		if(elem[i].checked){
			arrayIds[num]=elem[i].value;
			num++;		
		}
	}
	if(num==0){
		addActionError('请选择一个用户！');
		return false;
	}
	load("#container","${request.contextPath}/basedata/user/userAdmin-accreditAdmin.action?arrIdsStr="+arrayIds);
}
//密码重置
function passwordReset(){
	if(!need2choose1()){
		return;
	}
	tongYong("${request.contextPath}/basedata/user/userAdmin-passwordReset.action");
}
function need2choose1(){
	var elem=document.getElementsByName('arrayIds');
	var num=0;
	var val='';
	for(var i=0;i<elem.length;i++){
		if(elem[i].checked){
			num++;
			val=elem[i].value;			
		}
	}
	if(num==0){
		addActionError('请选择一个用户！');
		return false;
	}
	return true;
}

//导出用户信息
function exportUser(){
	location.href = "${request.contextPath}/basedata/user/userAdmin-export.action";
}

function inputFocus(obj, str){
	if (obj.value == str){
		obj.value = "";		
	}
}

function inputBlur(obj, str){
	if (obj.value == ""){
		obj.value = str;
	}
}
//查找用户
function queryTeacher(){
	var queryUserName = encodeURI($.trim($("#queryUserName").val()));
	var queryUserRlName = encodeURI($.trim($("#queryUserRlName").val()));
	load("#container","${request.contextPath}/basedata/user/userAdmin.action?modID=${modID?default('')}&&deptidnow=${deptidnow?default('')}&queryUserName="+queryUserName+"&queryUserRlName="+queryUserRlName);
}

</script>

<form name="searchForm" action="${request.contextPath}/basedata/user/userAdmin.action" method="post">
	<div class="query-builder">
    	<div class="query-part">
        	<!--input、a、span是内链元素，同行对齐不需要左浮动和有浮动-->
            <span>账号：</span>
            <input type="text" name="queryUserName" id="queryUserName" class="input-txt" value="${queryUserName?default("")}">
            <span class="ml-15">姓名：</span>
            <input type="text" class="input-txt" name="queryUserRlName" id="queryUserRlName" value="${queryUserRlName?default("")}">
            <a href="javascript:void(0);" class="abtn-blue ml-50" onclick="queryTeacher();">查找</a>
        </div>
    </div>
	<p class="pub-operation">
    	<a href="javascript:void(0);" onclick="roleUser();" class="abtn-blue">角色委派</a>
        <a href="javascript:void(0);" onclick="userAccredit();" class="abtn-blue">权限查看</a>
        <a href="javascript:void(0);" onclick="genericPassword();" class="abtn-blue">密码规则</a>
        <#if pwdStart=='1'>
       	<a href="javascript:void(0);" onclick="passwordReset();" class="abtn-blue"> 密码初始化</a>
       	</#if>
        <a href="javascript:void(0);" onclick="exportUser();" class="abtn-blue">导出excel</a>
    </p>
</form>

<form name="ec" id="ec" action="" method="post"> 
<@common.tableList id="tablelist">
<tr>
	<th width="5%" class="t-center">选择</th>
	<th width="10%" class="t-center">排序号</th>
	<#--if connectPassport
	<th width="10%" >账号</th>
	-->
	<th width="20%">账号</th>
	<th width="20%">姓名</th>
	<th width="20%">用户状态</th>
	<th width="15%">创建时间</th>
	<th width="15%" class="t-center">操作</th>
</tr>
<#if userList?exists &&  (userList?size>0)>
<#list userList as x>
	<tr>	
		<td class="t-center"><p><span <#if x.type?exists&&(x.type==1||x.type==0)>class="ui-checkbox ui-checkbox-disabled"<#else>class="ui-checkbox"</#if> ><input type="checkbox" class="chk" value="${x.id?default('')}"  <#if x.type?exists&&(x.type==1||x.type==0)>name="notarrayIds" <#else> name="arrayIds"</#if>></span></p>
		</td>
	
		<td class="t-center">
			${x.orderid?default('')}
		</td>
		<#-- connectPassport
			<td >
				${x.sequence?default('')}
			</td>
		-->
		<td >
			${x.name?default('')}<#if x.type?exists&&(x.type==1||x.type==0)><font color="red">（单位管理员）</font></#if>
		</td>
		<td title="${x.realname?default('')}">
			<@common.cutOff str=x.realname length=14 />
		</td>
		<td >
 			<#if userstatus_noaudit==x.mark?default(-1)?number><font color="red"></#if>
	  	  	<#if x.mark != 1><font color='red'><strong></#if>
	  	  		${appsetting.getMcode("DM-YHZT").get(x.mark?default(-1)?string)}
	  	  	<#if x.mark != 1></font></strong></#if>
	  	  	<#if userstatus_noaudit==x.mark?default(-1)?number></font></#if>
		</td>
		<td >
			<#if x.creationTime?exists>${x.creationTime?string('yyyy-MM-dd')}</#if>
		</td>
		<td class="t-center"><a href="javascript:doEdit('${x.id!}');"><img src="${request.contextPath}/static/images/icon/edit.png" alt="编辑"></a></td>
	</tr>
</#list>
<#else>
	<tr>
		<td colspan="<#if connectPassport>8<#else>7</#if>"> <p class="no-data mt-20">还没有任何记录哦！</p></td>
	</tr>
</#if>		
</@common.tableList>
<@common.Toolbar container="#container">
<#if userList?exists && (userList?size>0)>
	<span class="ui-checkbox ui-checkbox-all" data-all="no" style="margin-left:-10px;">
	<input type="checkbox" class="chk"  id="selectAll">全选</span>
	<a href="javascript:void(0);" onclick="auditUser();" class="abtn-blue"> 审核</a>
	<a href="javascript:void(0);" onclick="lockUser();" class="abtn-blue">锁定</a>
	<a href="javascript:void(0);" onclick="unlockUser();" class="abtn-blue">解锁</a>
</#if>
</@common.Toolbar>
</form>
<div  id="classLayer" class="popUp-layer" style="display:none;width:720px"></div>
</@common.moduleDiv>