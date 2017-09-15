<#import "treelib.ftl" as treelib>
 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学区选择窗口</title>
<link href="${request.contextPath}/static/css/layout.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${request.contextPath}/static/js/prototype.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/buffalo.js"></script>

<@treelib.treecite />
<script>
  var selectedId;
  var selectedName;
  var selectedType;

  function treeItemClick(id,type,name){  
  	  //为根目录时，返回空，相当于清空输入框
	  if(id=="0"){
	  	selectedId = "";
	  	selectedName = "";		
	  	return;
	  }
	  	selectedId = id;
		selectedName = name;
		selectedType = type;
  }
  
  function ok(){
	if (selectedType == "${stack.findValue("@net.zdsoft.eis.base.tree.TreeConstant@ITEMTYPE_EDUCATION")}"){
		alert("请选择各级教育局下的具体学区!");
		return;
	}
	var nameField=window.opener.document.getElementById("${valueField?default('schdistrictname')}");
	var idFiled=window.opener.document.getElementById("${codeField?default('schdistrictid')}");
	if (null == selectedId)
		selectedId = "";
	if (null == selectedName)
		selectedName = "";
	idFiled.value=selectedId;
	nameField.value=selectedName;
	 self.close();
  }
  
  function cancel(){
  	self.close();
  }
</script>
</head>

<body>
<table height="100%" width="100%" border="0">
<tr height="20"><td align="middle">
<input type="button" name="ok" value="确定" class="del_button1" onmouseover = "this.className = 'del_button3';" onmousedown= "this.className = 'del_button2';" onmouseout = "this.className = 'del_button1';"
       onclick="ok()"/>
<input type="button" name="cancel" value="取消" class="del_button1" onmouseover = "this.className = 'del_button3';" onmousedown= "this.className = 'del_button2';" onmouseout = "this.className = 'del_button1';"
       onclick="cancel()"/>
</td></tr>
<tr><td><div class="content_div">
<table border="0" width="100%"><tr><td id="treeID"><@treelib.tree /></td></tr></table>
<#--table width="100%"  border="0" cellspacing="0" cellpadding="0"  height="95%">
  <tr>
    <td  valign="top">
      <table  border="0" align="left" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top"><@treelib.tree /></td>
        </tr>
      </table>
    </td>
  </tr>
</table-->
</div></td></tr>
</table>
</body>
</html>
