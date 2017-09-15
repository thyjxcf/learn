<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${webAppTitle}--序列号注册</title>
<#include "/common/public.ftl">
<script src="${request.contextPath}/static/js/buffalo.js"></script>
<script language="javascript">
function regionSelected(e){
	var thisCode=e.value;
	var thisName=e.name;
	var city = document.getElementById("city");
	var county = document.getElementById("county");
	
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
	bfl.remoteActionCall("/basedata/serial/topUnit-remoteSerial.action","RemoteRegion",[thisCode],function(reply){
		//if(reply.isFault()){
		//	Buffalo.showError(reply.getResult());
		//	return;
		//}
		
		var retList = reply.getResult();
		
		for(i=1; i <= retList.length;i++){
			obj.options[i]=new Option(retList[i-1].regionName,retList[i-1].regionCode);
			if(retList[i-1].id=='-1'){
				obj.options.selectedIndex=i;
			}
		}
	});	
}

function saveInfo(){
	showSaveTip();
	updateForm1.submit();
}

function changeUnitClass(unitClass){
	/*
	var unitType = document.getElementById("unittype");
	if(unitClass = 1){
		unitType.value = "1";
	}else{
		
	}
	*/
}
</script>
</head>
<body>

<form name="updateForm1" id="updateForm1" method="POST" action="${request.contextPath}/basedata/serial/topUnit-saveTopUnit.action">
<input type="hidden" name="id" value="${unit.id?default('')}">
<input type="hidden" name="orderid" value="${unit.orderid?default(1)}">
<input type="hidden" name="authorized" value="${unit.authorized?default(0)}">
<input type="hidden" name="usetype" value="${unit.usetype?default(1)}">
<input type="hidden" name="newOrUpdate" value="update">

<div class="table-content">
<table width="100%" cellspacing="0" cellpadding="0" border="0" class="table3 table-vline">
	<tr class="first"><th colspan="4" class="tt">顶级单位注册</th></tr>
        	<tr>
        		<th width="15%">
            		<span>*</span>单位名称：</span>
            	</th>
        		<td colspan="3">
                <input type="text" name="name" id="name" value="${unit.name?if_exists}" title="请填写申请序列号时给出的单位名称！" class="input-txt150" />
            	</td>
            </tr>
            
			<tr>
			  <th><span>*</span>单位编号：</th>
			  <td colspan="3" >
              <input id="etohSchoolId" name="etohSchoolId" type="text" class="input-txt150" value="${unit.etohSchoolId?if_exists}" 
              size="20" maxlength="10" title="（例：1430100000 十位数字：1位运营商代码+2位省代码+2位运营平台流水号+5位单位流水号(从00000开始);运营商代码：移动：1，电信：2，网通：3，联通：4，独立学校：5，其它：9）"></td>
			</tr>
  	  	  	<tr>
  	  	  	  <th><span>*</span>账号：</th>
  	  	  	  <td colspan=3><input name="adminName" id="adminName" type="input" class="input-txt150" tabindex="1" 
  	  	  	  value="${unit.adminName?if_exists}" ></td>					  
			</tr>
  	  	  	<tr>
  	  	  	  <th><span>*</span>登录密码：</th>
  	  	  	  <td colspan=3><input name="password" id="password" type="password" class="input-txt150" tabindex="1" 
  	  	  	  value="${unit.password?if_exists}" ></td>					  
			</tr>
			<tr>
			<th width="100"><span>*</span>单位分类：</th>
			  <td>					 		
		 		<select name="unitclass" id="unitclass" class="input-sel150" tabindex="3" onchange="changeUnitClass(this.value);">
		 		  ${appsetting.getMcode("DM-DWFL").getHtmlTag(unit.getUnitclass()?default(1)?string)}					 		  	
	  	  		</select>
			  </td>	
			</tr>												
			<tr>
			  <th width="100"><span>*</span>单位类型：</th>
			  <td><select  name="unittype" id="unittype" class="input-sel150" tabindex="9">
			  		${appsetting.getMcode("DM-DWLX").getHtmlTag(unit.getUnittype()?default(1)?string)}
	  	  		</select>
			  </td>
			</tr>            

        	<tr>
        		<th><span>*</span>单位使用类别：</th>
        		<td colspan="3">
		                <select class="input-sel150" name="unitusetype" id="unitusetype">
		                ${appsetting.getMcode("DM-UNITUSETYPE").getHtmlTag(unit.getUnitusetype()?default(''))}
		                </select>
		       	</td>
		     </tr>
		     <tr>
        		<th>单位行政级别：</th>
        		<td colspan="3">
				                省：<select class="input-sel150" id="province" name="province" onchange="regionSelected(this);">
					      <option value="">--请选择--</option>
					      <#if provinceList?exists>
					      <#list provinceList as itm>
					      <option value='${(itm.regionCode)?default('')}' 
					      <#if (itm.regionCode)==province?default('')>selected</#if>>
					      ${itm.regionName?default('')}</option>
					      </#list>
					      </#if>        
				       </select>
				                市：<select class="input-sel150" id="city" name="city" onchange="regionSelected(this);">
					      <option value="">--请选择--</option>
					      <#if cityList?exists>
					      <#list cityList as itm>
					      <option value='${(itm.regionCode)?default('')}'  
					      <#if (itm.regionCode)==city?default('')>selected</#if>>
					      ${itm.regionName?default('')}</option>
					      </#list>
					      </#if>        
				       </select>
				                区/县：<select class="input-sel150" id="county" name="county">
					      <option value="">--请选择--</option>
					      <#if countyList?exists>
					      <#list countyList as itm>
					      <option value='${(itm.regionCode)?default('')}' <#if (itm.regionCode)==county?default('')>selected</#if>>
					      ${itm.regionName?default('')}
					      </option>
					      </#list>
					      </#if>
				         </select>
						         <span id="countryspan">
						         	<input type="checkbox" id="country" name="country" value="1" <#if (unit.regionlevel?default(-1)==5)>checked</#if> <#if hasSubUnits?default(false)>disabled</#if> disabled/>乡镇教育局
						         </span>	
            	</td>
            </tr>
</table>
</div>

<div class="table1-bt t-center">
	<span class="input-btn2" onclick="saveInfo();"><button type="button">提交</button></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<span class="input-btn2"><button type="reset">重置</button></span>
</div>

</form>

</body>
</html>