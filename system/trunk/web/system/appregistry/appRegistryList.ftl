<#assign ec=JspTaglibs["/WEB-INF/tld/extremecomponents.tld"]>
<#import "../../common/htmlcomponent.ftl" as htmlmacro>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${webAppTitle}--应用平台注册信息</title>
<link rel="stylesheet" href="${request.contextPath}/static/css/extremecomponents.css" type="text/css">
<link rel="stylesheet" href="${request.contextPath}/static/css/layout.css" type="text/css">
<script language="JavaScript" src="${request.contextPath}/static/js/prototype.js"></script>
<script language="JavaScript" src="${request.contextPath}/static/js/validate.js"></script>
<script language="javascript">    
<!-- 编辑 -->
function doEdit(id){
	var frm = document.getElementById("ec");
 	if (event.srcElement.name =="checkbox" || event.srcElement.name =="id"){ 
 		return false;
 	}
 	//这里不要用form表单提交，否则在选中单选框的时候会重复提交id
	window.location.href ="${request.contextPath}/system/appregistry/appRegistryAdmin-edit.action?id="+id;
} 

<!-- 增加 -->
function add(){
	var frm = document.getElementById("ec");
	frm.action="appRegistryAdmin-edit.action";
	frm.submit();
}

function formAction(frmAction,type){
	var frm = document.getElementById("ec");	
	var flag = false;
	var flag2 = false;
	var items = document.getElementsByName("id");
	var targetid = "";
	
	//选择连续行
	for(i=0;i<items.length;i++){
		if(items[i].checked){
			if(type == "up" && targetid == ""){
				if(i -1 == -1){
					alert("已经在置顶，不能再上移");
					return;
				}else{
					targetid = items[i -1].value;				
				}
			}
			if(flag2){
				alert("请选中连续的行");
				return;
			}
			flag = true;
		}else{
			if(flag) flag2 = true;
		}
	}

	if(type == "down"){	
		for(i=items.length -1;i > -1;i--){
			if(items[i].checked){
				if(i +1 == items.length){
					alert("已经在置底，不能再下移");
					return;
				}else{
					targetid = items[i +1].value;				
				}
		
				flag = true;
				break;
			}
		}
	}
	
	if(!flag){
		alert("没有选要操作的行，请先选择！");
		return;
	}
	frm.action = frmAction+"?operateType="+type+"&targetid="+targetid;
	frm.submit();
}      
</script>
</head>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" class="YecSpec">  
  <tr>
    <td height="30">
	  <table width="100%" border="0" cellpadding="0" cellspacing="0" height="100%">
		<tr>				  
		  <td>&nbsp;</td>
		  <td width="60"><div class="comm_button21" onMouseover="this.className='comm_button24';" onMousedown="this.className='comm_button22';" 
		  		onMouseout="this.className='comm_button21';" onclick="add();">新增</div>
		  </td>
		</tr>				 
	  </table>
    </td>
  </tr>
  <tr>
    <td height="100%" valign="top">
    <div class="content_div">
		<form id="ec" action="${request.contextPath}/fpf/appregistry/appRegistryAdmin.action"  method="post" >
    		<table  width="100%" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<th class="tdtitle" width="5%">选择</th>
					<th class="tdtitle" width="15%">注册名称</th>
					<th class="tdtitle" width="35%">应用主页</th>
					<th class="tdtitle" width="45%">注册码</th>
				</tr>
				
				<#list appRegistryList as x>
					<tr onclick="doEdit('${x.id!}')" height="20px;" valign="top" onMouseover="this.className='trmouseover';" 
						onMouseout="this.className='<#if x_index % 2 == 1>trdual<#else>trsingle</#if>';" 
						class="<#if x_index % 2 == 1>trdual<#else>trsingle</#if>">	
						<td class="tddata">
							<input type="checkbox" name="id" value="${x.id}" <#if idsMap[x.id]?exists>checked</#if>/>
						</td>
						<td class="tddata" >
							${x.appname?default("")}
						</td>
						<td class="tddata" >
							${x.url?default("")}
						</td>
						<td class="tddata" >
							${x.appcode?default("")}
						</td>
					</tr>
				</#list>		
			</table>
		</form>
    </div>
    </td>
  </tr>
  <tr><td bgcolor="#C2CDF7" height="1"></td></tr>
  <tr><td bgcolor="#ffffff" height="1"></td></tr>
  </tr>
	<td bgcolor="#C2CDF7" height="32" class="padding_left">
	<input type="checkbox" onClick="selectAllCheckbox(ec,$('selectAll'),'id');" id="selectAll">
	<label for="selectAll">全选</label>&nbsp;&nbsp;
	<input type='button' name='delete' onClick="javascript:userDelete();" value='删除' class='del_button1' 
		onmouseover = "this.className = 'del_button3';" onmousedown= "this.className = 'del_button2';" 
		onmouseout = "this.className = 'del_button1';">
	<input type='button' name='display_up'     value='上移' class='del_button1' onclick='formAction("appRegistryAdmin-updateOrder.action","up");'   onmouseover='this.className="del_button3";' onmousedown='this.className="del_button2";' onmouseout='this.className="del_button1";'>&nbsp;
	<input type='button' name='display_down'   value='下移' class='del_button1' onclick='formAction("appRegistryAdmin-updateOrder.action","down");' onmouseover='this.className="del_button3";' onmousedown='this.className="del_button2";' onmouseout='this.className="del_button1";'>&nbsp;
  	</td>
  </tr>
</table>
</body>
</html>