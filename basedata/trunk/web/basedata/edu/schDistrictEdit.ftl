<#import "../../common/htmlcomponent.ftl" as common>
<html>
<head>
<title>${webAppTitle}--学区编辑</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" >
<link href="${request.contextPath}/static/css/layout.css" type="text/css" rel="stylesheet">

<script src="${request.contextPath}/static/js/validate.js"></script>
<SCRIPT language=javascript src="${request.contextPath}/static/js/click.js" type=text/javascript></SCRIPT>
<script src="${request.contextPath}/stusys/sch/student/student.js"></script>
<script>

var isSubmit = false;
function onSave(){
	if (isSubmit){
		return false;
	}
	isSubmit = true;	
	var eduid = document.getElementById("eduid").value;
	var f=document.getElementById('form1');
	if(!formvalidate()){
		isSubmit = false;
		return false;
	}
	f.action="schDistrict-save.action";
	f.submit();	
}

function formvalidate(){	
	if(trim(document.getElementById('name').value)==''){
		addFieldError('name','学区名称不能为空');
		return false;
	}
		
	var obj = document.getElementById('remark');
	//alert(obj.value.length);
	if(obj.value.length > 250){

		addFieldError('remark','备注只允许输入250字');
		return false;
	}	
	
	return true; 
}

/*function checkOverLen(elem,maxlen){
	var len;
	var i;
	len = 0;
	var val = trim(elem.value);

	var maxlength = parseInt(maxlen);
	var length = val.length;
	alert(length);	
	for (i=0;i<length;i++){
		if (val.charCodeAt(i)>255) 
			len+=2;
		else 
			len++;
	}
	if(len>maxlength){
		//alert(field+"长度超过范围,允许范围为0-"+maxlength);
		addFieldError('remark','备注只允许输入250字');
		return false;
	}
	return true;
}*/



function onCancel(){
	var eduid = document.getElementById("eduid").value;
	document.location.href="schDistrict-Admin.action?eduid="+eduid;
}

</script>
</head>
<body>
<form name="form1" id="form1" method="POST" action="">
<input type="hidden" name="id" value="${id?default('')}" >
<input type="hidden" name="eduid" value="${schoolDistrict.eduid?default('')}" >
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" class="YecSpec" >
 <tr><td height="100%" valign="top">
    <table width="100%"  border="0" cellspacing="0" cellpadding="0" >
                  <tr><td><table width="100%"  border="0" cellspacing="0" cellpadding="0" >  	  	  	  

                      <tr height="30">
                      	<td class="send_font_title" colspan="4" align="center">学区基本信息<#if id?exists>编辑<#else>新增</#if></td>
                      <tr>
                      
				      <tr>
				       	<td class="send_font_no_width"><font color="red">*</font>学区编号：</td>
				       	<td class="send_padding_no_width" colspan="3"><input name="code" type="text" class="input_readonly" readonly value="${schoolDistrict.code?default('')}" maxlength="15"></td>
				      </tr>
				      <tr>
				       	<td class="send_font_no_width" width="100px"><font color="red">*</font>学区名称：</td>
				      	<td class="send_padding_no_width" colspan="3"><input name="name" type="text" class="input" value="${schoolDistrict.name?default('')}" maxlength="50" size="50" style="width:510px" ></td>
				      </tr>

				     
				      <tr>
				       	<td class="send_font_no_width">区域范围：</td>
				       	<td class="send_padding_no_width" colspan="3"><input name="region" type="text" class="input" size="50" value="${schoolDistrict.region?default('')}"  style="width:510px" maxlength="250"></td>
				      </tr>
				      <tr>
				       	<td class="send_font_no_width" valign="top">备　　注：</td>
				       	<td class="send_padding_no_width" colspan="3"><textarea name="remark" class="input" cols="49" rows="3" style="width:510px"  >${schoolDistrict.remark?default('')}</textarea></td>
				        
				      </tr>	
				      <tr><td >&nbsp;</td>
				          <td  colspan="3" align="left" >(限250字)</td>
				      </tr>			      
				    </table></td></tr>
				   	  	  	
	</table></td></tr>  
		  	  
  <tr style="height:0px;"><td id="actionTip"></td></tr>  
  <tr><td bgcolor="#C2CDF7" height="1"></td></tr>
  <tr><td bgcolor="#ffffff" height="1"></td></tr>
  <tr>
  	<td bgcolor="#C2CDF7" height="32" class="padding_left4">
	  	<table width="253" border="0" cellspacing="0" cellpadding="0">
	      <tr>        
			<td width="100" align="center"><label>
			  <input type="button"  value="保存"class="del_button1" onmouseover = "this.className = 'del_button3';" onmousedown= "this.className = 'del_button2';" onmouseout = "this.className = 'del_button1';" onClick="onSave();"/>
	        </label></td>
			<td width="53" ><label>
			  <input type="button"  value="取消"class="del_button1" onmouseover = "this.className = 'del_button3';" onmousedown= "this.className = 'del_button2';" onmouseout = "this.className = 'del_button1';" onClick="onCancel();"/>
			</label></td>
		  </tr>
		</table>
	</td>
  </tr>
  
  
</table>
</form>

</body>
</html>