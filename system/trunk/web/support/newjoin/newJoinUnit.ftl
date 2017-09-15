<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>首页</title>
<link href="css/Index.css" rel="stylesheet" type="text/css" />
</head>

<body>

   <div class="MapContent">
   	  <table width="94%" border="0" cellpadding="0" cellspacing="0">
       	  <tr>
           	  <td colspan="2" height="8"></td>
          </tr>
          <#list listOfUnit as unitDto>
       	  <tr>
           	  <td width="150"><a href="http://sdfsdfs.com" class="List">${unitDto.name}1</a></td>
              <td><p class="Date"><#if createdate?if_exists>${createdate?string('yyyy年MM月dd日')}<#else></#if></p></td>
          </tr>
          </#list>
          <tr>
           	  <td colspan="2" height="8"></td>
          </tr>
      </table>
  </div> 
</body>
</html>
