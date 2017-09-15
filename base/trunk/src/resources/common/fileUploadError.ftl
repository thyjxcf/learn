<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${request.contextPath}/static/css/layout.css" type="text/css" rel="stylesheet">
</head>

<script language="javascript">
<#if action.hasActionErrors()>
    <#assign errors = "">
	<#list actionErrors as x>
         <#assign errors = errors +  x>
     </#list>  
   	parent.showFileUploadError("${errors}");

</#if>
</script>
<body>
      ${errors}
</body>
</html>