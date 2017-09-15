<html>
<head>
<title>${webAppTitle}--职工管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${request.contextPath}/static/css/extremecomponents.css" type="text/css">
<link rel="stylesheet" href="${request.contextPath}/static/css/layout.css" type="text/css">
<script language="JavaScript" src="${request.contextPath}/static/js/prototype.js"></script>
<script type="text/javascript" language="javascript" src="${request.contextPath}/static/js/validate.js"></script>
<script type="text/javascript" language="javascript" src="${request.contextPath}/static/js/util.js"></script>
<script>
function doEdit(deptid){
	var f=document.getElementById('ec');
	if(event.srcElement.name=='arrayIds'||event.srcElement.name=='checkbox'){
		return false;
	}
	f.action="deptAdmin-edit.action?modID=${modID?default('')}&&id="+deptid+"&&unitId=${unitId?default('00000000000000000000000000000000')}";
	f.submit();
}
function deptNew(){
	var f=document.getElementById('ec');
	f.action="deptAdmin-new.action?modID=${modID?default('')}&&unitId=${unitId?default('00000000000000000000000000000000')}&&deptId=${deptId?default('00000000000000000000000000000000')}";
	f.submit();
}

function deptDelete(){
	if(!checkSelectCheckbox(ec,"arrayIds")) return;
	if(!confirm("您确认要删除信息吗？")) return;
	
	ec.action = "${request.contextPath}/basedata/unit/deptAdmin-delete.action?deptId=${deptId?default('')}&&unitId=${unitId?default('')}";
	ec.submit();
}
</script>
</head>
<body bgcolor="transparent">
<#assign htmlaction=request.contextPath+"/basedata/unit/deptAdmin-list.action?deptId=${deptId?default('')}&&unitId=${unitId?default('')}" />
<table height="100%" width="100%" cellspacing="0" cellpadding="0" border="0" align="center">
  <tr><td height="30" class="padding_left">
  	<table width="99%" border="0" cellpadding="0" cellspacing="0">
  	  <tr><td align="right">
  	  	<#if (((loginInfo.unitClass==2 <#--&& !(dept.deptname?default('').equals("学校管理员组"))-->) || loginInfo.unitClass==1))>
  		<div class="comm_button21" onMouseover = "this.className = 'comm_button21';" onMousedown= "this.className = 'comm_button22';"onMouseout= "this.className = 'comm_button21';" onClick="deptNew();">新增</div>
  		</#if>
  	  </td></tr>
  	</table>
  </td></tr>
  <tr>
	<td height="100%" valign="top">
	<div class="content_div">
	<form id="ec" action="${htmlaction}"  method="post" >
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="YecSpec" height="100%">
			<tr height="100%" valign="top">
				<td>
					<div class="content_div">
						<table  width="100%" cellspacing="0" cellpadding="0" border="0">
							<colgroup>
								<col width="6%" />
								<col width="10%" />
								<col width="15%" />
								<col width="15%" />
								<col width="15%" />
								<col width="12%" />
								<col />
							</colgroup>
							<tr>
								<td class="tdtitle">选择</th>
								<td class="tdtitle">部门编号</th>
								<td class="tdtitle">部门名称</th>
								<td class="tdtitle">电话号码</th>
								<td class="tdtitle">负责人</th>
								<td class="tdtitle">教研组标识</th>
								<td class="tdtitle">描述信息</th>
							</tr>
							<#list deptList as x>
							<tr onclick="doEdit('${x.id!}')" height="20px;" valign="top" onMouseover="this.className='trmouseover';" 
								onMouseout="this.className='<#if x_index % 2 == 1>trdual<#else>trsingle</#if>';" 
								class="<#if x_index % 2 == 1>trdual<#else>trsingle</#if>">	
								<td class="tddata">
									<#if !(x.deptname?default('').equals("学校管理员组")) || (loginInfo.unitClass==1)> 
						       			<input type="checkbox" name="arrayIds" value="${x.id?default('')}" /> 
						       		</#if>      				         	
								</td>
								<td class="tddata" >
									${x.deptCode?default("")}
								</td>
								<td class="tddata" >
									<#if x.deptname?exists&&x.deptname?length gt 10>${x.deptname.substring(0,10)}…<#else>${x.deptname?default('')}</#if>
								</td>
								<td class="tddata" >
									${x.depttel?default("")}
								</td>
								<td class="tddata" >
									${x.principanname?default("")}
								</td>
								<td class="tddata" >
									${appsetting.getMcode("DM-JYBZ").get(x.jymark?default("")?string)}
								</td>
								<td class="tddata" >
									<#if x.about?exists&&x.about?length gt 10>${x.about.substring(0,10)}…<#else>${x.about?default('')}</#if>
								</td>
							
							</tr>
							</#list>
						</table>
					</div>
				</td>
			
			</tr>
		</table>
	</form>
	</div>
  	</td>
  </tr>
  <tr style="height:0px;"><td id="actionTip"></td></tr>
  <tr><td bgcolor="#C2CDF7" height="1"></td></tr>
  <tr><td bgcolor="#ffffff" height="1"></td></tr>
  <tr valign="bottom">
  	<td bgcolor="#C2CDF7" height="32" >
		<table width="100%" >
			<tr>
				<td align="left" width="30%">	
					<input type="checkbox" onClick="selectAllCheckbox(ec,$('selectAll'),'arrayIds');" id="selectAll">
					<label for="selectAll">全选</label>&nbsp;&nbsp;
					<input type='button' name='delete' onClick="javascript:deptDelete();" 
						value='删除' class='del_button1' onmouseover = "this.className = 'del_button3';" onmousedown= "this.className = 'del_button2';" onmouseout = "this.className = 'del_button1';">
				</td>
			</tr>
		</table>
	</td>
  </tr>
</table>
</body>
</html>