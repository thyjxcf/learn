<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>首页</title>
<link href="css/Index.css" rel="stylesheet" type="text/css" />
</head>

<body>
<#--<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>                	
        <td width="580" valign="top">
            <table border="0" cellpadding="0" cellspacing="0" class="NewCustomer">
            	<tr>
                	<td height="35" background="images/NewCustomor.gif"></td>
                </tr>
                <tr>
               	  <td height="358">--> 
                   	  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="NewCustomer">
                       	  <tr>
                           	  <td width="6"></td>
                              <td><img src="images/Img.gif" border="0" usemap="#Map" /></td>
                            <td width="6"></td>
                            <td width="270">
                               	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                   	  <tr>
                                       	  <td height="76" background="images/MapLine.gif">
                                           	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                               	  <tr>
                                                   	  <td colspan="2" height="25" title="${region.fullName?default("")}">当前选择的区域：${regionNameDisplay?default('')}</td>
                                                  </tr>
                                                  <tr>
                                                   	  <td width="218" height="25">
                                                   	  	<select style="width:90px;" name="cityRegion" onchange="gotoCityRegion(this.value);">
                                                   	  	<#list listOfRegion as region>                                                   	  	
                                                       	  <option value="${region.fullCode}" <#if region.fullCode == cityRegionCode?default('')>selected</#if>>${region.cname}</option>
                                                       	</#list>
                                                       	  </select>
                                                          >>
                                                        <select style="width:90px;" name="countyRegion" onchange="gotoCountyRegion(this.value);">
                                                        <#list listOfSubRegion as subRegion>
                                                          <option value="${subRegion.fullCode}" <#if subRegion.fullCode == countyRegionCode?default('')>selected</#if>>${subRegion.cname}</option>
                                                        </#list>
                                                        </select>                                                                  </td>
                                                          
                                                      <td><input type="button" onclick="refreshRegion()" value="刷新" class="iButton" /></td>
                                                  </tr>
                                                  <tr>
                                                   	  <td height="26">按名称查找:<input type="text" name="unitName" class="SearchBox" value="${queryUnitName?default("")}" /></td>
                                                      <td><input type="button" value="查找" onclick="queryUnitByName()" class="iButton" /></td>
                                                  </tr>
                                              </table>                                                      </td>
                                      </tr>
                                      <tr>
                                       	  <td height="282">
                                           	  <div class="MapContent">
											   	  <table width="94%" border="0" cellpadding="0" cellspacing="0">
											       	  <tr>
											           	  <td colspan="2" height="8"></td>
											          </tr>
											          <#list listOfUnit as unitDto>
											       	  <tr>
											           	  <td width="150">											           	  
											           	  <#if unitDto.webContext?default('#') != "#" >
											           	  <p class="unitDisplay">
														  <a class="List" href="javascript:gotoUrl('${unitDto.webContext?default('#')}')"  title="${unitDto.name}"">${unitDto.name}</a>
														  </p>					           	  
											           	  <#else>
											           	  <p class="List" href="javascript:gotoUrl('${unitDto.webContext?default('#')}')"  title="${unitDto.name}"">${unitDto.name}</p>
											           	  </#if>
											           	  </td>
											              <td><p class="Date"><#if unitDto.creationTime?exists>${unitDto.creationTime?string('yyyy年MM月dd日')}</#if></p></td>
											          </tr>
											          </#list>
											          <tr>
											           	  <td colspan="2" height="8"></td>
											          </tr>
											      </table>
											  </div>                                                       </td>
                                      </tr>
                                  </table>                                        </td>
                              <td width="6"></td>
                          </tr>
                      </table>
                  <#--</td>
                </tr>
                <tr>
                	<td height="5" background="images/NewCustomorBottom.gif"></td>
                </tr>
            </table>
        </td>
    </tr>
</table>-->

<map name="Map" id="Map">
<area shape="poly" coords="40,56" href="1" />
<area shape="poly" coords="41,57" href="2" />
<area shape="poly" coords="48,51,35,70,30,97,29,109,35,129,32,150,34,159,48,152,56,142,68,147,79,132,74,119,87,92,76,78" href="newjoinunitcity.action?cityRegionCode=433100&regionCode=${regionCode}" title="单位数[${mapOfUnitCount['433100']?default('0')}]" />
<area shape="poly" coords="54,52,69,43,88,50,99,44,108,49,131,55,130,72,119,80,112,81,107,92,96,88,90,94" href="newjoinunitcity.action?cityRegionCode=430800&regionCode=${regionCode}" title="单位数[${mapOfUnitCount['430800']?default('0')}]" />
<area shape="poly" coords="99,42,94,35,94,27,105,28,112,23,126,26,138,35,153,36,169,41,173,50,183,51,182,58,181,65,180,75,182,85,183,98,173,105,160,107,151,107,146,110,133,112,116,119,122,105,120,95,117,85,127,78,134,72,133,65,134,56,126,51" href="newjoinunitcity.action?cityRegionCode=430700&regionCode=${regionCode}" title="单位数[${mapOfUnitCount['430700']?default('0')}]" />
<area shape="poly" coords="38,160,42,166,36,172,28,178,22,184,11,186,7,196,15,194,27,190,35,189,41,195,39,201,43,207,33,214,37,222,30,235,39,235,39,247,49,254,60,259,68,243,65,230,57,219,65,212,71,201,80,189,94,181,102,171,109,164,106,156,112,149,112,142,105,127,111,119,118,107,117,96,115,90,100,93,89,97,82,109,78,121,82,132,73,149,57,147,57,148,51,153" href="newjoinunitcity.action?cityRegionCode=431200&regionCode=${regionCode}" title="单位数[${mapOfUnitCount['431200']?default('0')}]" />
<area shape="poly" coords="196,69,185,59,196,51,206,48,219,45,217,59,228,58,234,48,246,40,253,49,255,60,251,72,266,84,277,97,274,109,257,113,234,104,220,112,209,109,200,108,205,101,209,94,217,86,216,77,207,73" href="newjoinunitcity.action?cityRegionCode=430600&regionCode=${regionCode}" title="单位数[${mapOfUnitCount['430600']?default('0')}]" />
<area shape="poly" coords="206,112,225,115,232,109,238,109,253,116,264,114,276,113,279,121,269,132,256,147,247,144,239,147,227,140,215,141,198,141,187,143,174,146,169,141,172,133,189,131,200,128" href="newjoinunitcity.action?cityRegionCode=430100&regionCode=${regionCode}" title="单位数[${mapOfUnitCount['430100']?default('0')}]" />
<area shape="poly" coords="184,62,197,71,206,77,214,81,205,91,197,100,201,112,197,121,183,129,171,129,161,142,148,143,143,134,134,131,125,138,116,141,112,133,111,125,127,120,142,116,155,113,168,110,178,107,185,100,184,87,183,75" href="newjoinunitcity.action?cityRegionCode=430900&regionCode=${regionCode}" title="单位数[${mapOfUnitCount['430900']?default('0')}]" />
<area shape="poly" coords="229,144,245,150,255,150,253,169,264,183,266,203,277,226,271,240,262,245,254,234,247,218,242,207,236,204,233,191,234,183,225,181,222,172,224,161,225,153" href="newjoinunitcity.action?cityRegionCode=430200&regionCode=${regionCode}" title="单位数[${mapOfUnitCount['430200']?default('0')}]" />
<area shape="poly" coords="116,144,130,139,136,136,144,141,151,146,162,144,167,150,171,157,175,165,184,167,189,174,190,179,178,182,163,176,149,166,138,171,128,168,118,162,111,156" href="newjoinunitcity.action?cityRegionCode=431300&regionCode=${regionCode}" title="单位数[${mapOfUnitCount['431300']?default('0')}]" />
<area shape="poly" coords="173,150,185,147,196,145,210,145,221,143,221,150,222,158,219,166,219,174,216,177,203,175,191,172,184,162,176,161" href="newjoinunitcity.action?cityRegionCode=430300&regionCode=${regionCode}" title="单位数[${mapOfUnitCount['430300']?default('0')}]" />
<area shape="poly" coords="114,166,105,175,100,182,88,189,78,200,70,211,65,220,66,230,71,239,73,249,85,250,97,238,114,238,120,226,125,215,132,211,141,203,149,198,158,193,167,193,172,185,152,173,134,174" href="newjoinunitcity.action?cityRegionCode=430500&regionCode=${regionCode}" title="单位数[${mapOfUnitCount['430500']?default('0')}]" />
<area shape="poly" coords="121,234,126,224,135,224,139,212,147,208,156,208,164,211,172,217,179,224,179,236,183,248,185,257,187,269,186,280,190,291,189,301,179,305,178,317,170,324,154,323,146,332,136,330,142,312,136,304,122,315,115,303,124,292,133,279,136,267,128,256,128,241" href="newjoinunitcity.action?cityRegionCode=431100&regionCode=${regionCode}" title="单位数[${mapOfUnitCount['431100']?default('0')}]" />
<area shape="poly" coords="186,248,201,243,222,242,230,229,230,208,242,217,248,228,255,240,260,251,274,244,286,244,275,260,272,275,268,287,261,292,243,285,228,284,219,291,222,304,219,317,195,303,192,284,190,265" href="newjoinunitcity.action?cityRegionCode=431000&regionCode=${regionCode}" title="单位数[${mapOfUnitCount['431000']?default('0')}]" />
<area shape="poly" coords="155,201,175,193,188,185,210,180,222,184,228,192,228,206,223,228,217,239,195,240,183,233,176,213,164,208" href="newjoinunitcity.action?cityRegionCode=430400&regionCode=${regionCode}" title="单位数[${mapOfUnitCount['430400']?default('0')}]" />
</map>
</body>
<script>
function gotoCityRegion(regionCode, unitName){
	if (unitName && unitName != ""){
		document.location.href = "newjoinunitcity.action?cityRegionCode=" + regionCode + "&regionCode=${regionCode}&queryUnitName=" + unitName;
	}
	else{
		document.location.href = "newjoinunitcity.action?cityRegionCode=" + regionCode + "&regionCode=${regionCode}";
	}
}

function gotoCountyRegion(regionCode, unitName){
	if(unitName && unitName != ""){
		document.location.href = "newjoinunitcounty.action?countyRegionCode=" + regionCode + "&regionCode=${regionCode}&cityRegionCode=${cityRegionCode?default('')}&queryUnitName=" + unitName;
	}
	else{
		document.location.href = "newjoinunitcounty.action?countyRegionCode=" + regionCode + "&regionCode=${regionCode}&cityRegionCode=${cityRegionCode?default('')}";
	}
}

function refreshRegion(unitName){
	var cityRegionCode = document.getElementById("cityRegion").value;
	var countyRegionCode = document.getElementById("countyRegion").value;
	if (countyRegionCode == ""){		
		gotoCityRegion(cityRegionCode, unitName);
	}
	else{
		gotoCountyRegion(countyRegionCode, unitName);
	}
}

function queryUnitByName(){
	var unitName = document.getElementById("unitName").value;
	if (unitName == ""){
		alert("请输入要查找的单位名称");
		document.getElementById("unitName").focus();
		return;
	}
	refreshRegion(unitName);
}

function gotoUrl(url){
	var tempUrl = url.toLowerCase();		
	if (tempUrl.indexOf("http://") == 0 || tempUrl.indexOf("showappindex.action") >= 0){
		window.open(url);
	}else{
		window.open("http://" + url);
	}
}
</script>
</html>
