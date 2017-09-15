<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>二级缓存情况</title>
</head>

<body>
<form id="cachestat"  method="post">
<table width="100%" border="1" cellpadding="5">
<tr>
<td>总体情况：</td><td>${fullStatistics}</td>
</tr>

<tr>
<td rowspan="2">query情况：</td>
<td>query的RegionNames<input type="text" size="100" name="queryName" value="${queryName?if_exists}"></td>
</tr>
<tr>
<td>${queryStatistics?default("　")}</td>
</tr>

<tr>
<td rowspan="2">entity情况：</td>
<td>entity的类全名<input type="text" size="100" name="entityName" value="${entityName?if_exists}"></td>
</tr>
<tr>
<td>${entityStatistics?default("　")}</td>
</tr>

<tr>
<td rowspan="2">entity情况：</td>
<td>collection的类全名<input type="text" size="100" name="collectionName" value="${collectionName?if_exists}"></td>
</tr>
<tr>
<td>${collectionStatistics?default("　")}</td>
</tr>

<tr>
<td rowspan="2">secondLevelCache情况：</td>
<td>secondLevelCache的RegionName<input type="text" size="100" name="secondLevelCacheName" value="${secondLevelCacheName?if_exists}"></td>
</tr>
<tr>
<td>${secondLevelCacheStatistics?default("　")}</td>
</tr>

</table>
<input type="submit" value="确定">
</form>
</body>
</html>
