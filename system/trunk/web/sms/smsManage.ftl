<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>发送留言</title>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<link href="${request.contextPath}/static/css/layout.css" type="text/css" rel="stylesheet">
<SCRIPT language=javascript src="${request.contextPath}/static/js/click.js" type=text/javascript></SCRIPT>
<script language=javascript src="${request.contextPath}/static/js/calendarDlg.js"></script>
<script language="JavaScript" src="${request.contextPath}/static/js/util.js"></script>
<#--buffalo-->
<script language="JavaScript" src="${request.contextPath}/static/js/prototype.js"></script>
<script language="JavaScript" src="${request.contextPath}/static/js/buffalo.js"></script>
<#--buffalo-->
<script type="text/javascript">

function onStat(){
	document.form1.action = "smsManage.action";
	document.form1.target="_self";
	document.form1.submit();
}

//删除
function onRemove(){
	var args = "";	
	var ids = document.getElementsByName("ids");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked)
			args += ids[i].value + "&";	
	}
	if(args==""){
		alert("请选择您要删除的信息！");
		return;	
	}
	
	if(window.confirm("您确认要删除这些信息吗？")){
		var buffalo=new Buffalo(''); 	
	 	buffalo.async = false; //同步执行
	 	buffalo.remoteActionCall("smsManage-remote.action","remoteRemoveSms",[args],function(reply){		
			drawMessages(reply,function(){
				if(!reply.getResult().actionErrors && !reply.getResult().fieldErrors){			
					onStat();			
				}
			});
		});	
			
	} else {
		return;	
	}
}

//全选
function onSelectAll(){	
	if( document.getElementById("selectAll").checked){
		checkAllByStatus('ids','checked');
	}
	else{
		checkAllByStatus('ids','');
	}
}
</script>
</head>
<body>
<iframe width="174" height="172" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="${request.contextPath}/static/js/calendar/ipopeng.html?begin=2000-01-01&end=2020-12-31" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0px;"></iframe>
<table width="100%"  border="0" cellspacing="0" cellpadding="0" class="YecSpec" height="100%">
      <tr>
        <td class="padding_top2"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td bgcolor="#AABBFF" class="padding_left"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                <form name="form1" method="post">
                <table border="0" cellspacing="1" cellpadding="1">
                  <tr>
                    <td width="10" class="fontblue_bold">&nbsp;</td>
                    <td width="105" height="30" class="fontblue_bold"> 设置查询时间：</td>
                    <td class="padding_left2">从
                      <input name="startDate" type="text" value="${startDate!}" style="width:80px;" class="input_readonly" />
                      <img src="${request.contextPath}/static/images/date_icon.gif" width="18" height="16" align="absmiddle" onClick="gfPop.fPopCalendar(document.getElementById('startDate'));return false" style="cursor:pointer;"/> 到
                      <input name="endDate" type="text" value="${endDate!}" style="width:80px;" class="input_readonly"/>
                      <img src="${request.contextPath}/static/images/date_icon.gif" width="18" height="16" align="absmiddle"  onClick="gfPop.fPopCalendar(document.getElementById('endDate'));return false" style="cursor:pointer;"/></td>
                    <td width="110" align="right" class="padding_left2">
                      <div class="comm_button1" onclick="onStat();" onmouseover="this.className='comm_button11';" onmousedown="this.className='comm_button111';" onmouseout="this.className='comm_button1';">查询</div>
                   	</td>
                   	<td width="200" class="padding_left2"> 
                   	  <span id="messageError"></span>
                    </td>
                  </tr>
                </table>
                </form>
                </td>
                <td align="right">&nbsp;</td>
              </tr>
            </table></td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td height="100%" valign="top"><div class="content_div">
          <table width="100%"  border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
            <tr>
              <td width="2" bgcolor="#FFFFFF"></td>
              <td class="tdtitle" width="40">选择</td>
              <td width="2" bgcolor="#FFFFFF"></td>
              <td class="tdtitle" >短信类型</td>
              <td width="2" bgcolor="#FFFFFF"></td>
              <td class="tdtitle">接收人</td>
              <td width="2" bgcolor="#FFFFFF"></td>
              <td class="tdtitle">内容</td>
              <td width="2" bgcolor="#FFFFFF"></td>
              <td class="tdtitle">批次状态</td>
              <td width="2" bgcolor="#FFFFFF"></td>
              <td class="tdtitle">发送时间</td>
            </tr>
            <#list queryList as item>     		    		
       		<tr <#if item_index % 2 == 1>bgcolor="#F3F5FE"</#if>  onmouseover="this.className='tr_onmouse';" onmouseout="this.className='tr_onmouse2';" >
     	      <td bgcolor="#FFFFFF"></td>
              <td class="tddata" name =="idsTr"><input type="checkbox" name="ids" value="${item.id?default('')}"></td>
     	      <td bgcolor="#FFFFFF"></td>
              <td class="tddata">${item.smsType!}</td>
              <td bgcolor="#FFFFFF"></td>
              <td class="tddata">${item.receiveName!}</td>
              <td bgcolor="#FFFFFF"></td>
              <td class="tddata"><#if item.content?length lt 21>${item.content?default("")}<#else>${item.content[0..20]}...</#if></td>
              <td bgcolor="#FFFFFF"></td>
              <td class="tddata"><#if item.status =='1'>成功<#else>失败</#if></td>    
              <td bgcolor="#FFFFFF"></td>
              <td class="tddata">${item.sendDate!}(${item.sendHour!}:<#if item.sendMinutes gt 9>${item.sendMinutes!}<#else>0${item.sendMinutes!}</#if>)</td>     
            </tr>
            </#list>
          </table>
        </div></td>
      </tr>
	  <tr><td bgcolor="#C2CDF7" height="1"></td></tr>
	  <tr><td bgcolor="#ffffff" height="1"></td></tr>
	   <tr>
		<td bgcolor="#C2CDF7" height="32" class="padding_left">
		  <table width="99%"  border="0" cellpadding="0" cellspacing="0">
        	<tr class="turnPage">
	          <td width="60"><input type="checkbox" onclick="onSelectAll();" id="selectAll"><label for="selectAll">全选</label></td>
			  <td width="100"><input type='button' name='delete' onclick="onRemove();" value='删除' class='del_button1' onmouseover = "this.className = 'del_button3';" onmousedown= "this.className = 'del_button2';" onmouseout = "this.className = 'del_button1';"></td>
			  <td align="right" valign="bottom">${htmlOfPagination?default('')}</td>
			</tr>
		  </table>
		</td>
	  </tr>
</table>
</body>
</html>
