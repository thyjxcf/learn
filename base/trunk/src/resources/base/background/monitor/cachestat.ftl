<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>二级缓存情况</title>
<script language="javascript">
 jQuery(document).ready(function(){
	jQuery(".table-content").height(jQuery("#subIframe", window.parent.document).height()-jQuery(".table1-bt").height()-5);
})
function dosub(){
	var cachestat = document.getElementById("cachestat");
	cachestat.submit();
}
</script>

</head>

<body>
<form id="cachestat"  method="post">
<@common.tableDetail>
<tr>
<th width="150px;">总体情况：</th><td width="100%">${fullStatistics}</td>
</tr>

<tr>
<th rowspan="2" width="150px;">query情况：</th>
<td>query的RegionNames<input type="text" size="100" class="input-txt300" name="queryName" value="${queryName?if_exists}"></td>
</tr>
<tr>
<td ><textarea class="area150 " style="height:30px;width:95%;">${queryStatistics?default("　")}</textarea></td>
</tr>

<tr>
<th rowspan="2" width="150px;">entity情况：</th>
<td>entity的类全名<input type="text" size="100" class="input-txt300" name="entityName" value="${entityName?if_exists}"></td>
</tr>
<tr>
<td><textarea class="area150" style="height:20px;width:95%;">${entityStatistics?default("　")}</textarea ></td>
</tr>

<tr>
<th rowspan="2" width="150px;">entity情况：</th>
<td>collection的类全名<input type="text" size="100" class="input-txt300"name="collectionName" value="${collectionName?if_exists}"></td>
</tr>
<tr>
<td><textarea class="area150" style="height:20px;width:95%;">${collectionStatistics?default("　")}</textarea></td>
</tr>

<tr>
<th rowspan="2" width="150px;">secondLevelCache情况：</th>
<td>secondLevelCache的RegionName<input type="text" size="100" class="input-txt300" name="secondLevelCacheName" value="${secondLevelCacheName?if_exists}"></td>
</tr>
<tr>
<td><textarea class="area150" style="height:20px;width:95%;">${secondLevelCacheStatistics?default("　")}</textarea></td>
</tr>
</@common.tableDetail>
<@common.ToolbarBlank class="table1-bt t-center">
<span class="input-btn1" onclick="javascript:dosub();"><button type="button">确定</button></span>
</@common.ToolbarBlank>
</form>
</body>
</html>
