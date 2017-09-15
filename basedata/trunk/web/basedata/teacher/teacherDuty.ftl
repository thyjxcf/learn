<html>
<head>
<link href="${request.contextPath}/static/css/layout.css" type="text/css" rel="stylesheet">

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${webAppTitle}--教职工职务维护</title>
</head>
<#import "../../common/commonmacro.ftl" as common>
 
<script src="${request.contextPath}/static/js/validate.js"></script>
<script src="${request.contextPath}/static/js/prototype.js"></script>
<script language="JavaScript" src="${request.contextPath}/static/js/buffalo.js"></script>
<script type="text/javascript" language="javascript" src="${request.contextPath}/static/js/wz_dragdrop.js"></script>
<script language="javascript">
var isSubmitting = false;
var beforeId;
var selectedId;
var isClickTd = false;
var divPosition = [0,0,250,380] //top, left, height, width;
var buffalo=new Buffalo(''); 
buffalo.async = false; //同步执行
replyInit("${request.contextPath}");
var dutyId;

String.prototype.trim = function()
{
    return this.replace(/(^\s*)|(\s*$)/g, "");
}
String.prototype.lTrim = function()
{
    return this.replace(/(^\s*)/g, "");
}
String.prototype.rTrim = function()
{
    return this.replace(/(\s*$)/g, "");
}

function returnMain(){
	location.href = "teacherAdmin.action";
}

function saveDuties() {
	if (isSubmitting) {
		return;
	}

  isSubmitting = true;
  addActionMessage("系统正在保存信息，请稍候...");

  var dutyIds = document.getElementsByName("dutyId");
  var contents = document.getElementsByName("content");
  var index = 0;   
  var duties = [];
  
  for (var i = 0, n = dutyIds.length; i < n; i++) {
  	if (contents[i].value.trim().length == 0){
	  	addActionError( "第" + (i + 1) + "条记录的职务名称为空，不能保存！","actionError");
	  	contents[i].focus();
  		isSubmitting = false;
  		return false;
  	}
  	for (var j = i + 1; j < contents.length; j ++){
  		if (contents[j].value.trim() == contents[i].value.trim()){
  			drawClientError("actionError", "第" + (i + 1) + "第" + (j + 1) + "条记录的职务名称相同，不能保存！");
  			addActionError("第" + (i + 1) + "第" + (j + 1) + "条记录的职务名称相同，不能保存！","actionError");
  			contents[j].focus();
  			isSubmitting = false;
  			return false;
  		}
  	}
  	
    var duty = Buffalo.Entity.createBean(["id","content"],
  	[],"net.zdsoft.eis.dataservice.middle.dto.DutyDto");
  	duty.id = dutyIds[i].value;
  	duty.content = contents[i].value;
    duties[index++] = duty;
  }
  
  buffalo.remoteActionCall("teacherAdmin-remoteDuty.action", "updateDuty", [duties], function(reply) {
    window.scrollTo(0,0);
    clearMessages();
    drawMessages(reply);
    if (reply.getResult().hasErrors) {
    }
    isSubmitting = false;
  });
}

function removeDuty(dutyId) {
  if (isSubmitting) {
    return;
  }

  clearMessages();
  if (!confirm("您确定要删除此职务吗？")) {
    return;
  }

  isSubmitting = true;
  var dutyIds = [];
  dutyIds[0] = dutyId;
  buffalo.remoteActionCall("remoteDuty.action", "deleteDuty", [dutyIds], function(reply) {
    if (reply.getResult().hasErrors) {
      drawMessages(reply);
      window.scroll(0, 9999);
    } 
    else {
      var dataTable = $("dataTable");
      var rowIndex = $("tr" + dutyId).rowIndex;
      dataTable.deleteRow(rowIndex);
      for (var i = rowIndex, n = dataTable.rows.length; i < n; i++) {
        var srcIndex = dataTable.rows[i].cells[0].innerText;
        dataTable.rows[i].cells[0].innerText = srcIndex - 1;
      }
    }
    isSubmitting = false;
  });
}

function newDuty(){
	buffalo.remoteActionCall("remoteDuty.action", "takeNewId", [], function(reply) {
	    window.scrollTo(0,0);
	    clearMessages();
	    drawMessages(reply);
	    if (!reply.getResult().hasErrors) {
			dutyId = reply.getResult();
	    }	      		
  	});

	var dataTable = $("dataTable");
	var tr = dataTable.insertRow();
	var trIndex = dataTable.rows.length;
	if ((trIndex + 1) % 2 == 1){
		tr.style.backgroundColor = "#FFF";
	}
	else{
		tr.style.backgroundColor = "#F3F5FE";
	}
	tr.id="tr" + dutyId;
	tr.insertCell().innerText = trIndex - 1;
	tr.insertCell().innerHTML = "<input name='content' id='content" + dutyId + "' class='blueText' value='' onKeyDown='if(event.keyCode==13) return false;'><input class='blueText' type='hidden' name='dutyId' id='dutyId" + dutyId + "' value='" + dutyId + "' size='10'>";
	tr.insertCell().innerHTML = "<a href='#' onclick=javascript:removeDuty('" + dutyId + "')>删除</a>";
}

</script>
<body>
<form action="" method="post" name="mainform">
<table width="100%"  border="0" cellspacing="0" cellpadding="0" class="YecSpec" height="100%">
	<tr>
    	<td height="35" class="padding_left">
	    	<table width="99%"  border="0" cellspacing="0" cellpadding="0">
		        <tr>
			        <td width="5"><img src="${request.contextPath}/static/images/frame/tl_tips.jpg" width="5" height="5"></td>
			        <td height="5" background="${request.contextPath}/static/images/frame/t_bg.jpg"></td>
			        <td width="5"><img src="${request.contextPath}/static/images/frame/rt_tips.jpg" width="5" height="5"></td>
		        </tr>
		        <tr>
		        	<td width="5" background="${request.contextPath}/static/images/frame/l_bg.jpg"></td>
		        	<td class="right_titlebg">
		        		<table width="99%" border="0">
							<tr>								
					            <td>&nbsp;</td>
					            <td width="10" align="right"><div class="comm_button21" onMouseover = "this.className = 'comm_button21';" 
							            onMousedown= "this.className = 'comm_button22';"onMouseout= "this.className = 'comm_button21';" 
							            onClick="newDuty();">新增职务</div>
							    </td>							    
							    <td width="10" align="right"><div class="comm_button21" onMouseover = "this.className = 'comm_button21';" 
							            onMousedown= "this.className = 'comm_button22';"onMouseout= "this.className = 'comm_button21';" 
							            onClick="returnMain();">返回</div>
							    </td>
							</tr>
						</table>
					</td>
			        <td width="5" background="${request.contextPath}/static/images/frame/r_bg.jpg"></td>
				</tr>
				<tr>
				    <td width="5"><img src="${request.contextPath}/static/images/frame/bl_tips.jpg" width="5" height="5"></td>
				    <td height="5" background="${request.contextPath}/static/images/frame/b_bg.jpg"></td>
				    <td width="5"><img src="${request.contextPath}/static/images/frame/rb_tips.jpg" width="5" height="5"></td>
				</tr>
			</table>
		</td>
	</tr>	
	<tr>
    	<td height="100%" valign="top">
        	<div class="content_div">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
          			<tr>
            			<td>
            				<table id="dataTable" cellspacing="1" width="100%"  border="0" cellpadding="0" cellspacing="0">
				            	<tr>
				            		<td class="trTitle">序号</td>
					              	<td class="trTitle">职务名称</td>
					                <td class="trTitle">操作</td>
				              	</tr>			  
			  <#if listOfDutyDto?exists>
			  <#list listOfDutyDto as item>	
				          		<tr <#if (item_index+1)%2==1>bgcolor="#FFFFFF"<#else>bgcolor="#F3F5FE"</#if> id="tr${item.id}">
				                	<td>${item_index + 1}</td>
				                	<td><input name="content" id="conatent${item.id}" class="blueText" value="${item.content?default("")}"></td>
				                	<td><a href="#" onclick="javascript:removeDuty('${item.id}')">删除</a></td>
				                	<input type="hidden" name="dutyId" id="dutyId${item.id}" value="${item.id}">
				              	</tr>
			  </#list>
			  </#if>
							</table>
            			</td>
          			</tr>
          		</table>
          	</div>
		</td>
	</tr>
	<tr><td><span id="actionError"></span></td></tr>
    <tr><td bgcolor="#C2CDF7" height="1"></td></tr>
	<tr><td bgcolor="#ffffff" height="2"></td></tr>	  
	<tr>
	  	<td bgcolor="#C2CDF7" height="32" class="padding_left">
	  		<table width="99%"  border="0" cellpadding="0" cellspacing="0">
    			<tr>
    				<td width="100">&nbsp;</td>
      				<td>
      					<label>
        					<input type="button" name="delete" value="保存" class="del_button1" onMouseover = "this.className = 'del_button3';" 
        						onMousedown= "this.className = 'del_button2';" onMouseout = "this.className = 'del_button1';" 
        						onClick="saveDuties();"/>
      					</label>
      				</td>
      				<td align="right">
	  				</td>
	  			</tr>
      		</table>
      	</td>
	</tr>
</table>
</form>
</body>
</html>

