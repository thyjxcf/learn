<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${webAppTitle}--序列号注册</title>
<#include "/common/public.ftl">
<script src="${request.contextPath}/static/js/buffalo.js"></script>
</head>
<script language="javascript">
function regionSelected(e){
	var thisCode=e.value;
	var thisName=e.name;
	var city = $("city");
	var county = $("county");
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
    var action = "/basedata/serial/serialAdmin-register-remoteRegion.action";
	bfl.remoteActionCall(action,"RemoteRegion",[thisCode],function(reply){
		if(reply.isFault()){
			Buffalo.showError(reply.getResult());
			return;
		}
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
	if(!checkAllValidate()){
		return false;
	}
	showSaveTip();
	updateForm1.submit();
}

</script>
<body>

<form name="updateForm1" id="updateForm1" method="POST" action="serialAdmin-register-update.action">
<input type="hidden" name="id" value="${unit.id?default('')}">
<input type="hidden" name="orderid" value="${unit.orderid?default(1)}">
<input type="hidden" name="authorized" value="${unit.authorized?default(0)}">
<#if newOrUpdate=="update" >
<input type="hidden" name="unitusetype" value="${unit.unitusetype?default(1)}">
</#if>
<input type="hidden" name="unittype" value="${unit.unittype?default(1)}">
<input type="hidden" name="usetype" value="${unit.usetype?default(1)}">
<input type="hidden" name="newOrUpdate" value="${newOrUpdate}">

<div class="table-content">
<table width="100%" cellspacing="0" cellpadding="0" border="0" class="table3 table-vline">
	<tr class="first"><th colspan="4" class="tt">序列号注册</th></tr>
        	<tr>
        		<th width="15%">
            		<span>*</span>单位名称：</span>
            	</th>
        		<td colspan="3">
                <input type="text" name="name" id="name" msgName="单位名称" notNull="true" maxLength="40" value="${unit.name?if_exists}" title="请填写申请序列号时给出的单位名称！" class="input-txt150" />
            	</td>
            </tr>
          <#if newOrUpdate="new">            
  	  	  	<tr>
  	  	  	  <th><span>*</span>账号：</th>
  	  	  	  <td colspan=3><input name="username" id="username" type="input" class="input-txt150" tabindex="1" 
  	  	  	  value="${unit.adminName?if_exists}" ></td>					  
			</tr>
  	  	  	<tr>
  	  	  	  <th><span>*</span>登录密码：</th>
  	  	  	  <td colspan=3><input name="pwd" id="pwd" type="password" class="input-txt150" tabindex="1" 
  	  	  	  value="${unit.password?if_exists}" ></td>					  
			</tr>            
			</#if>
        	<tr>
        		<th>注册日期：</th>
        		<td colspan="3">
                <input type="text" class="input-txt150 input-readonly" name="creationTime" id="creationTime" value="${unit.creationTime?if_exists?date}"/>
            	</td>
            </tr>
        	<tr>
        		<th>
            	<span>*</span>序列号：
            	</th>
        		<td colspan="3">
                <textarea cols="110" rows="12" msgName="序列号" notNull="true" id="licenseTxt" name="licenseTxt">${licenseTxt?default('')}</textarea>
            	</td>
            </tr>
        	<tr>
        		<th><span>*</span>单位使用类别：</th>
        		<td colspan="3">
		                <select class="input-sel150" name="unitusetype" id="unitusetype" <#if newOrUpdate=="update" >disabled="true"</#if>>
		                ${appsetting.getMcode("DM-UNITUSETYPE").getHtmlTag(unit.getUnitusetype()?default(''))}
		                </select>
		       	</td>
		     </tr>
		     <#if newOrUpdate="new">        
		     <tr>
        		<th>注册单位行政级别：</th>
        		<td colspan="3">
				                省级：<select class="input-sel150" id="province" name="province" onchange="regionSelected(this);" <#if newOrUpdate=="update" > disabled</#if>>
					      <option value="">--请选择--</option>
					      <#if provinceList?exists>
					      <#list provinceList as itm>
					      <option value='${(itm.regionCode)?default('')}' 
					      <#if (itm.regionCode)==province?default('')>selected</#if>>${itm.regionName?default('')}</option>
					      </#list>
					      </#if>        
				       </select>
				                地市：<select class="input-sel150" id="city" name="city" onchange="regionSelected(this);" <#if newOrUpdate=="update" > disabled</#if>>
					      <option value="">--请选择--</option>
					      <#if cityList?exists>
					      <#list cityList as itm>
					      <option value='${(itm.regionCode)?default('')}'  
					      <#if (itm.regionCode)==city?default('')>selected</#if>>${itm.regionName?default('')}</option>
					      </#list>
					      </#if>        
				       </select>
				                区县：<select class="input-sel150" id="county" name="county" onchange="regionSelected(this);" <#if newOrUpdate=="update" > disabled</#if>>
					      <option value="">--请选择--</option>
					      <#if countyList?exists>
					      <#list countyList as itm>
					      <option value='${(itm.regionCode)?default('')}' <#if (itm.regionCode)==county?default('')>selected</#if>>${itm.regionName?default('')}</option>
					      </#list>
					      </#if>
				         </select>
						         <span id="countryspan">
						         	<input type="checkbox" id="country" name="country" value="1" <#if (unit.regionlevel?default(-1)==5)>checked</#if> <#if hasSubUnits?default(false)>disabled</#if> disabled/>乡镇教育局
						         </span>	
            	</td>
            </tr>
            </#if> 
</table>
</div>

<div class="table1-bt t-center">
	<span class="input-btn2" onclick="saveInfo();"><button type="button">提交</button></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<span class="input-btn2"><button type="reset">重置</button></span>
</div>

</form>
</body>
</html>