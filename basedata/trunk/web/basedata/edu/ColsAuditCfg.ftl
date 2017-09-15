<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${request.contextPath}/static/css/layout.css" type="text/css" rel="stylesheet">
<script src="${request.contextPath}/static/js/validate.js"></script>
<script src="${request.contextPath}/basedata/js/colsDisplaySet.js"></script>
<script language="javascript">
</script>
<body>	<#--srcstuid 	adjust-Stuid	-->
<form action="" name="mainform" method="post" onSubmit="return doSave('stuAuditConfigSave');">
<table width="100%"  border="0" cellspacing="0" cellpadding="0" height="100%" >
  <tr>
    <td valign="top">
    <table width="100%"  border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="30" bgcolor="#AABBFF" class="padding_left">
		  	<table width="100%" border="0" cellspacing="0" cellpadding="0">
			  <tr>
				<td width="15%" align="left" valign="middle">可启用字段列表：
				<td width="3%" align="right" valign="middle"><input type="checkbox" name="selectAllSrc" value="checkbox" 
          		  onClick="selectAllCheckbox(this.form,this,'srcstuid');"></td>
          		<td width="17%" align="left" valign="middle">全选</td>
          		<td>允许学校维护学生信息<input name="allowed" type="checkbox" value="1" <#if "1"==allowed>checked</#if>/></td>
				<td width="15%" align="left" valign="middle">&nbsp;&nbsp;已经启用字段列表：
				<td width="3%" align="right" valign="middle"><input type="checkbox" name="selectAllDis" value="checkbox" 
          		  onClick="selectAllCheckbox(this.form,this,'disstuid');"></td>
          		<td align="left" valign="middle">全选</td>
			  </tr>
			</table>
		  </td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td height="100%" valign="top">
				
				<table width="100%" height="100%" border="0" class="YecSpec">
                    <tr>
                      <td width="45%" valign="top">
                      	<div class="content_div">
                      	<table id="srctable" width="100%" border="0" cellpadding="0" cellspacing="0" class="YecSpec7">
                          <tr>
						    <td class="YecSpec3" width="40">选择</td><td width="2" bgcolor="#ffffff"></td>
                            <td class="YecSpec3" >字段名</td>			 <td width="2" bgcolor="#ffffff"></td>
                          </tr>
                          <#if hideList?exists>
                          <#list hideList as item>	
						    <tr  id="srcrow" <#if (item_index+1)%2==1>bgcolor="#FFFFFF"<#else>bgcolor="#F3F5FE"</#if>>
							  	<td class="padding_left"><input name="srcstuid" type="checkbox" value="${item.id?if_exists}" /> </td>
							    <td width="2" bgcolor="#ffffff"></td>
								<td class="padding_left">${item.colsName?if_exists}</td>
							    <td width="2" bgcolor="#ffffff"></td>
                          	</tr>
                          </#list>
                          </#if>
                        </table>
                        </div>
                      </td>
                        
                      <td valign="middle" align="center" >
						  <table>
						  	<tr><td><input type="button" name="adjust" value="启用&gt;"class="del_button1" 
							  onmouseover = "this.className = 'del_button3';" onmousedown= "this.className = 'del_button2';" 
							  onmouseout= "this.className = 'del_button1';" onClick="doAdjust('${request.contextPath}');"/></td>
							</tr>
							<tr height="10"><td>&nbsp;</td></tr>
							<tr><td><input type="button" name="adjust" value="&lt;取消"class="del_button1" 
							  onmouseover = "this.className = 'del_button3';" onmousedown= "this.className = 'del_button2';" 
							  onmouseout= "this.className = 'del_button1';" onClick="doAdjustCancel('${request.contextPath}');"/></td>
							</tr>
						  </table>
					  </td>
					    
                      <td width="45%" valign="top">
                      	<div class="content_div">
                      	<table id="distable"width="100%" border="0" cellpadding="0" cellspacing="0" class="YecSpec7">
                          <tr>
							<td class="YecSpec3" width="40">选择</td><td width="2" bgcolor="#ffffff"></td>
							<td class="YecSpec3">字段名</td>			<td width="2" bgcolor="#ffffff"></td>
                          </tr>
                        
                          <#if DisplayList?exists>
                          <#list DisplayList as item>
						    <tr id="disrow" <#if (item_index+1)%2==1>bgcolor="#FFFFFF"<#else>bgcolor="#F3F5FE"</#if> >
								<td class="padding_left"><input name="disstuid" type="checkbox" value="${item.id?if_exists}" <#if item.colsConstraint == 1  || item.colsConstraint == 2 >disabled checked</#if> /> </td>
								<td width="2" bgcolor="#ffffff"></td>
								<td class="padding_left">${item.colsName?if_exists}</td>
								<td width="2" bgcolor="#ffffff"></td>
	                          </tr>
                          </#list>
                          </#if>
                        </table>
                        </div>
                      </td>
                    </tr>
                  </table>				
				</td>
              </tr>
	  <tr>
		<td bgcolor="#C2CDF7" height="1"></td>
	  </tr>
	  <tr>
		<td bgcolor="#ffffff" height="1"></td>
	  </tr>	  
      <tr>
        <td height="30" bgcolor="#AABBFF"><table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
		  <td align="center">
		      <input type="button" name="save" value="保存" class="del_button1" 
		  	  onmouseover = "this.className = 'del_button3';" onmousedown= "this.className = 'del_button2';" 
		      onmouseout= "this.className = 'del_button1';" onClick="doSave('stuAuditConfigAdmin-save');"/>  
		  </td>
		</tr>
	</table></td>
  </tr>
  
</table>
<input type="hidden" name="adjustIds" value=""/>
<input type="hidden" name="eduid" value="${eduid?default("")}"/>

</form>
</body>