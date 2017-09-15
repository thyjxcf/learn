<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>发送留言</title>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<link href="${request.contextPath}/static/css/layout.css" type="text/css" rel="stylesheet">
<SCRIPT language=javascript src="${request.contextPath}/static/js/click.js" type=text/javascript></SCRIPT>
<script language=javascript src="${request.contextPath}/static/js/calendarDlg.js"></script>
<script type="text/javascript">
function init(){
	selectfish2Tab(${tabIndex});
	<#if message! != "" >
		addActionError("${message!}");
	</#if>
}
	
function changeTab(index){
	location.href = "smsStat.action?tabIndex=" + index;
}

function onStat(){
	document.form1.action = "smsStat.action?tabIndex=${tabIndex}";
	document.form1.target="_self";
	document.form1.submit();
}
</script>
</head>
<body onload="init();">
<iframe width="174" height="172" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="${request.contextPath}/static/js/calendar/ipopeng.html?begin=2000-01-01&end=2020-12-31" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0px;"></iframe>
<table width="100%"  border="0" cellspacing="0" cellpadding="0" class="YecSpec" height="100%">
      <tr>
        <td></td>
      </tr>
      <tr>
        <td height="10" class="padding_left"></td>
      </tr>
      <tr>
        <td class="padding_top2"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="27" background="${request.contextPath}/static/images/stylemenu_zbg.gif" valign="bottom"><table width="100%" height="27"  border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="9">&nbsp;</td>                
                <td class="style_menu2" id="menu1" onclick="javascript:changeTab(1)" <#if !isStatisticUnitSms>style="display:none"</#if>>部门短信统计</td>
                <td width="3" <#if !isStatisticUnitSms >style="display:none"</#if>>&nbsp;</td>                
                <td class="style_menu1" id="menu2" onclick="javascript:changeTab(2)" <#if !isStatisticUnitSms>style="display:none"</#if>>帐户短信统计</td>                
                <td width="3">&nbsp;</td>
                <td class="style_menu1" id="menu3" onclick="javascript:changeTab(3)">分类统计</td>
                <td>&nbsp;</td>
                <td width="22"></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td bgcolor="#AABBFF" class="padding_left"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                <form name="form1" method="post">
                <table border="0" cellspacing="1" cellpadding="1">
                  <tr>
                    <td width="10" class="fontblue_bold">&nbsp;</td>
                    <td width="105" height="30" class="fontblue_bold"> 设置统计时间：</td>
                    <td class="padding_left2">从
                      <input name="startDate" type="text" value="${startDate!}" style="width:80px;" class="input_readonly" />
                      <img src="${request.contextPath}/static/images/date_icon.gif" width="18" height="16" align="absmiddle" onClick="gfPop.fPopCalendar(document.getElementById('startDate'));return false" style="cursor:pointer;"/> 到
                      <input name="endDate" type="text" value="${endDate!}" style="width:80px;" class="input_readonly"/>
                      <img src="${request.contextPath}/static/images/date_icon.gif" width="18" height="16" align="absmiddle"  onClick="gfPop.fPopCalendar(document.getElementById('endDate'));return false" style="cursor:pointer;"/></td>
                    <td width="110" align="right" class="padding_left2">
                      <div class="comm_button1" onclick="onStat();" onmouseover="this.className='comm_button11';" onmousedown="this.className='comm_button111';" onmouseout="this.className='comm_button1';">查看统计表</div>
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
              <td class="tdtitle" ><#if tabIndex==1>部门</#if><#if tabIndex==2>帐户</#if><#if tabIndex==3>类型</#if></td>
              <td width="2" bgcolor="#FFFFFF"></td>
              <td class="tdtitle">发送成功</td>
              <td width="2" bgcolor="#FFFFFF"></td>
              <td class="tdtitle">发送失败</td>
            </tr>
            <#list statList as item>     		    		
       		<tr <#if item_index % 2 == 1>bgcolor="#F3F5FE"</#if>  onmouseover="this.className='tr_onmouse';" onmouseout="this.className='tr_onmouse2';" >
     	      <td bgcolor="#FFFFFF"></td>
              <td class="tddata">${item.deptName!}</td>
              <td bgcolor="#FFFFFF"></td>
              <td class="tddata">${item.sucessItemsCount!}</td>
              <td bgcolor="#FFFFFF"></td>
              <td class="tddata">${item.failItemsCount!}</td>      
            </tr>
            </#list>
          </table>
        </div></td>
      </tr>
	  <tr><td bgcolor="#C2CDF7" height="1"></td></tr>
	  <tr><td bgcolor="#ffffff" height="1"></td></tr>
	  <tr>
	    <td bgcolor="#C2CDF7" height="32" class="padding_left">&nbsp;</td>
	  </tr>
</table>
</body>
</html>