<#assign ec=JspTaglibs["/WEB-INF/tld/extremecomponents.tld"]>
<#import "../../common/commonmacro.ftl" as common>
<html>
<head>
<title>${webAppTitle}--序列号注册</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${request.contextPath}/static/css/extremecomponents.css" type="text/css">
<link rel="stylesheet" href="${request.contextPath}/static/css/layout.css" type="text/css">
<script type="text/javascript" language="javascript" src="${request.contextPath}/static/js/prototype.js"></script>
<script type="text/javascript" language="javascript" src="${request.contextPath}/static/js/buffalo.js"></script>
<script type="text/javascript" language="javascript" src="${request.contextPath}/static/js/validate.js"></script>
<script language="javascript">
function regionSelected(e){
	var thisCode=e.value;
	var thisName=e.name;
	var city = Buffalo.getElementById("city");
	var county = Buffalo.getElementById("county");
	
	var obj;

	if(thisName=="province"){
		city.options.length=1;
		county.options.length=1;
		obj=city;
	}else if(thisName=="city"){
		county.options.length=1;
		obj=county;
	}else{
		return ;
	}	

	if(thisCode=="") return;
	
	var bfl = new Buffalo("${request.contextPath}");
	bfl.remoteActionCall("/basedata/serial/remoteRegion.action","RemoteRegion",[thisCode],function(reply){
		if(reply.isFault()){
			Buffalo.showError(reply.getResult());
			return;
		}
		
		var retList = reply.getResult();
		
		for(i=1; i <= retList.length;i++){
			obj.options[i]=new Option(retList[i-1].regionName,retList[i-1].id);
			if(retList[i-1].id=='-1'){
				obj.options.selectedIndex=i;
			}
		}
	});	
}

function saveInfo(){
    if(confirm("序列号注册后单位行政级别不能修改，请确定现在的单位行政级别是正确的。")){
       var e_save=document.getElementById("save");
       e_save.disabled=true;
       updateForm.submit();
	}
}

</script>
</head>
<body>

<form name="updateForm" id="updateForm" method="POST" action="serialAdmin-register-update.action">
<input type="hidden" name="id" value="${unit.id?default('')}">

<table align="left" width="100%" height="100%" cellspacing="0" cellpadding="0" border="0" class="YecSpec16">
  <tr>		
	<td valign="top">
    <table width="100%" cellspacing="0" cellpadding="0" border="0">
      <tr>
        <td height="100%" valign="top">
          <table width="100%"  border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td>		  	  	  
                <table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#FCFFFF">
                  <tr><td  class="send_titlefont" style="background:#E8EBFE;" colspan="4">序列号注册</td></tr>
                  <tr>
                    <td class="send_font_no_width"><font color="red">*</font>单位名称：</td>
                    <td class="send_padding"><input name="name" id="name" type="input" class="input" tabindex="1" 
                      value="${unit.name?if_exists}" fieldtip="请填写申请序列号时给出的单位名称！"></td>

                  </tr>
                  <tr>
                  <td class="send_font_no_width"><font color="red">*</font>序列串：</td>
                  <td colspan="3" class="send_padding" >
                    <textarea cols="60" rows="6" name="licenseTxt">${licenseTxt?default('')}</textarea>
                  </td>
                </tr>
                  <tr>
                    <td class="send_font_no_width">注册日期：</td>
                    <td class="send_padding">
                      <input name="createdate" id="createdate" type="input" class="input" tabindex="2" 
                      value="" readonly></td>					  						  						  						  
                  </tr>
                  <tr>
                    <td class="send_font_no_width" width="100"><font color="red">*</font>单位使用类别：</td>
                    <td class="send_padding_no_width"><select  name="unitusetype" id="unitusetype" style="width:150px;" tabindex="9" onchange="">
                        ${appsetting.getMcode("DM-UNITUSETYPE").getHtmlTag(unit.getUnitusetype()?default(''))}
                      </select>

                    </td>
                  </tr>
                  <tr>
                    <td class="send_font_no_width">单位行政级别：</td>					
                    <td colspan="3" class="send_padding_no_width" >
                      <div id="regiondiv" style="display:">
                        <table cellspacing="0" cellpadding="0" border="0">
                          <tr>
                            <td style="color:#29248A">
                              省：<select name="province" style="width:150px;padding-right:10px;" onchange="regionSelected(this);">
                                <option value="">--请选择--</option>
                                <#if provinceList?exists>
                                <#list provinceList as itm>
                                <option value='${(itm.regionCode)?default('')}' <#if (itm.id)==province?default('')>selected</#if>>${itm.regionName?default('')}</option>
                                </#list>
                                </#if>
                              </select>
                              &nbsp;市：
                              <select name="city" style="width:150px;padding-right:10px;"  onchange="regionSelected(this);">
                                <option value="">--请选择--</option>
                                <#if cityList?exists>
                                <#list cityList as itm>
                                <option value='${(itm.regionCode)?default('')}'  <#if (itm.id)==city?default('')>selected</#if>>${itm.regionName?default('')}</option>
                                </#list>
                                </#if>
                              </select>
                              &nbsp;区/县：<select name="county" style="width:150px;padding-right:10px;" >
                                <option value="">--请选择--</option>
                                <#if countyList?exists>
                                <#list countyList as itm>
                                <option value='${(itm.regionCode)?default('')}' <#if (itm.id)==county?default('')>selected</#if>>
                                ${itm.regionName?default('')}
                                </option>
                                </#list>
                                </#if>

                              </select>
                              <span id="countryspan" style="display:"><input id="country" name="country" type="checkbox" value="1" <#if (unit.regionlevel?default(-1)==5)>checked</#if>><label for="country">乡镇教育局</label></span>
                            </td>
                          </tr>
                        </table>
                      </div>
                    </td>						  					  
                  </tr>	
                  <#if addAdmin>
                  <tr>
                    <td class="send_font_no_width">管理员帐号：</td>
                    <td class="send_padding">${unit.adminName?default('')}&nbsp;&nbsp;密码与帐号相同</td>					  						  						  						  				  									
                  </tr>
                  </#if>											
                </table>
            </td></tr>
          </table>
        </td>
      </tr>		  	  
    </table>
		  </td>
		</tr>
  <tr style="height:0px;"><td id="actionTip"></td></tr>
  <tr><td bgcolor="#C2CDF7" height="1"></td></tr>
  <tr><td bgcolor="#ffffff" height="1"></td></tr>
  <tr>
  	<td bgcolor="#C2CDF7" height="32" class="padding_left4"><table width="153" border="0" cellspacing="0" cellpadding="0">
      <tr>
		<td width="100" align="center"><label>
		  <input type="button" name="save" id="save" value="保存"class="del_button1" onmouseover = "this.className = 'del_button3';" onmousedown= "this.className = 'del_button2';" onmouseout = "this.className = 'del_button1';" onClick="saveInfo();" <#if action.hasActionMessages()>disabled</#if>/>
        </label></td>
		<td width="53"><label>
		  <input type="button" name="cancel" value="取消"class="del_button1" onmouseover = "this.className = 'del_button3';" onmousedown= "this.className = 'del_button2';" onmouseout = "this.className = 'del_button1';" onClick="window.close()"/>
		</label></td>
	  </tr>
	</table></td>
  </tr>
</table>
</form>

  </body>
</html>
