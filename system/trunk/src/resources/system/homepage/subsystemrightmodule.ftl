<SCRIPT language=javascript src="${request.contextPath}/static/system/js/preparedUtil.js" type=text/javascript></SCRIPT>
<link href="${request.contextPath}/static/css/layout.css" type="text/css" rel="stylesheet">
<link href="${request.contextPath}/static/system/homepage/css/subsystem.css" type="text/css" rel="stylesheet">
<script language="javascript">
function selectModule(current){
	var tbs=tbsContainer.getElementsByTagName("table");
	for(i=0;i<tbs.length;i++){
		if(tbs[i].id.indexOf("table")>=0){
			tbs[i].className ="downstyle2";
		}
	}
	tbs[current].className ="downstyle";
}

function openRunWindow(moduleId, contextPath) {
  var width = 230;
  var height = 60;
  var left = (screen.width - width) / 2;
  var top = (screen.height - height) / 2;
	
	if (contextPath != null) {
		window.open(contextPath + "run.action?moduleID=" + moduleId, "", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,dependent=yes,alwaysRaised=yes,width=" + width + ",height=" + height + ",left=" + left + ",top=" + top);
	}
	else {
  		window.open("run.action?moduleID=" + moduleId, "", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,dependent=yes,alwaysRaised=yes,width=" + width + ",height=" + height + ",left=" + left + ",top=" + top);
	}
}
</script>
<table id="tbsContainer" width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" class="YecSpec">
<#assign cols=4>
<#list modules?chunk(cols) as row>
 <tr height="100">
 <#list row as module>
 <#if module.name?exists>
  <#assign tUrl="javascript:openRunWindow('${module.id}');">
  <td valign="middle" align="middle" class="padding_top">
    <table width="116" height="61" border="0" cellpadding="0" cellspacing="0" id="table${row_index*cols+module_index}" class="downstyle2" onClick="javascript:selectModule(${row_index*cols+module_index})">
     <a href="${tUrl}">
      <tr>
       <td class="icon">
       <a href="${tUrl}"><img src="../${module.picture}" width="28" height="28" border="0"></a>
       </td>
      </tr>
      <tr>
       <td align="center" class="fontblue">${module.name}</td>
      </tr>
     </a>
    </table>
  </td>
  <#else>
  <td valign="middle" align="middle" >
    <table width="116" height="61" border="0" cellpadding="0" cellspacing="0">
      <tr>
       <td >       
       </td>
      </tr>
      <tr>
       <td align="center" ></td>
      </tr>
     </a>
    </table>
  </td>
  </#if>
 </#list>
 </tr>
</#list>
<tr></tr>
</table>



      