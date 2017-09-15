<#import "treelib.ftl" as treelib>
 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>行政区选择窗口</title>
<script type="text/javascript" src="${request.contextPath}/static/js/prototype.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/buffalo.js"></script>

<@treelib.treecite />
<script>
  var regionCode;
  var regionName;

  function treeItemClick(code,node){
  	  //为根目录时，返回空，相当于清空行政区框
	  if(code=="0"){
	  	regionCode = "";
	  	regionName = "";		
	  	return;
	  }
	  	regionCode =(code + "000000").substring(0,6);
	  	regionName = node.text;
	  	var parentNode = node.parentNode;
	  	while(parentNode.parentNode){
	  		regionName = parentNode.text+regionName;
	  		parentNode = parentNode.parentNode;
	  	}
	  	
  }
  
  function ok(){
     var regionname=window.opener.document.getElementById("${valueField?default('regionname')}");
	 var region=window.opener.document.getElementById("${codeField?default('region')}");
	if (null == regionCode)
		regionCode = "";
	if (null == regionName)
		regionName = "未知";
	 region.value=regionCode;
	 regionname.value=regionName;
	 self.close();
  }
  
  function cancel(){
  	self.close();
  }
</script>
</head>

<body>
<table height="100%" width="100%">
<tr height="20"><td align="middle">
<span class="input-btn2" onclick="ok()"><button type="button">确定</button></span>&nbsp;
<span class="input-btn2" onclick="cancel()"><button type="button">取消</button></span>
</td></tr>
<tr><td><div class="content_div">
<table border="0" width="100%"><tr><td id="treeID"><@treelib.regionTree /></td></tr></table>
</div></td></tr>
</table>
</body>
</html>
<script language="javascript">
buildTree();
</script>