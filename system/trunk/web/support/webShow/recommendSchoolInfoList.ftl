<html>
	<head>
		<title>推荐学校详细信息列表</title>
		<link rel="stylesheet" type="text/css" href="css/style.css">
		<style type="text/css">
			image {border:0;}
		</style>
	</head>
	<body>	
		<table width="221" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="25" background="images/school_pic3.gif">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td align="center" class="white_green">${regionName?default("")}</td>
                    </tr>
                </table>
                </td>
              </tr>
              <tr>
                <td height="230" valign="top">
                <table width="100%" height="100%" border="0" align="center" cellpadding="6" cellspacing="0">
                    <tr bgcolor="#F6FCF2">
                      <td height="450" valign="top">
                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
		        		 <#list listOfRecommendSchool as tList>
		                 <tr>
						<#if tList.schoolHomepage?default("") == "">
		                 	<td height="23">·<span title="${tList.schoolFullName?default("")}(未维护首页)">${tList.schoolName?default("")}</span></td>
		                 <#else>
		                   <td height="23">·<a href="javascript:gotoHomepage('${tList.schoolHomepage?default('')}');" title="${tList.schoolFullName?default("")}(${tList.schoolHomepage?default('')})">${tList.schoolName?default("")}</a></td>
		                  </#if>		                   

		                   <td>${tList.schoolRecommendDateText?default("")}</td>
		                 </tr>
		                 </#list>
		               </table>
                      </td>
                    </tr>
		          <tr>
		             <td height="1" colspan="2" align="center"><img src="images/line_pic5.gif" width="201" height="1"></td>
		          </tr>
		          <tr height="30" bgcolor="#F6FCF2">
		          	<td colspan="2" align="center" valign="middle" height="30">${htmlOfPagination2}</td>
		          </tr>
		      </table>
                </td>
              </tr>
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
</thml>