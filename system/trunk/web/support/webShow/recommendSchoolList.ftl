<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>推荐学校列表</title>
		<link rel="stylesheet" type="text/css" href="css/style.css">
	</head>
	<body>
		<table width="192" border="0" cellspacing="0" cellpadding="0">
		<#list listOfRecommendSchool as tList>
		<#if tList_index < 9 >
		  <#if tList_index % 2 == 0>
			  <tr>
			  
			  <#if tList.schoolHomepage?default("") == "">
             	<td height="23">·<span title="${tList.schoolFullName?default("")}(未维护首页)">${tList.schoolName?default("")}</span></td>
             <#else>
               <td height="23">·<a href="javascript:gotoHomepage('${tList.schoolHomepage?default('')}');" title="${tList.schoolFullName?default("")}(${tList.schoolHomepage?default('')})">${tList.schoolName?default("")}</a></td>
              </#if>	
		                  
			    <#--<td height="23">·<a target="_blank" href="${request.contextPath}\support\getRecommendSchoolDetail.action?schoolId=${tList.schoolId?default("")}">${tList.schoolName?default("")}</a></td>-->
			  </tr>
		  <#else>
			  <tr>
			  
			  <#if tList.schoolHomepage?default("") == "">
             	<td height="23" class="bg_gray">·<span title="${tList.schoolFullName?default("")}(未维护首页)">${tList.schoolName?default("")}</span></td>
             <#else>
               <td height="23" class="bg_gray">·<a href="javascript:gotoHomepage('${tList.schoolHomepage?default('')}');" title="${tList.schoolFullName?default("")}(${tList.schoolHomepage?default('')})">${tList.schoolName?default("")}</a></td>
              </#if>
              
	    	  	<#--<td height="23" class="bg_gray">·<a target="_blank" href="${request.contextPath}\support\getRecommendSchoolDetail.action?schoolId=${tList.schoolId?default("")}">${tList.schoolName?default("")}</a></td>-->
	  		  </tr>
		  </#if>
		  </#if>
		 </#list>
		</table>
		          	<script>
	function gotoHomepage(url){
		if(url.indexOf("http://") < 0){
			url = "http://" + url;
		}
		window.open(url);
	}
	</script>
	</body>
</html>
