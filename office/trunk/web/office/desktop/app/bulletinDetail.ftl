<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta charset="utf-8">
<title>${bulletin.title!}</title>
<style>
    table{margin-bottom:10px;border-collapse:collapse;display:table;border:1px dashed #ddd}
    #test td{padding:5px;border: 1px solid #DDD;}
</style>
<body class="widescreen">
<div style="margin-left:auto;margin-right:auto;width:1200px;">
	<h3 style="text-align:center;word-break:break-all; word-wrap:break-word;">${bulletin.title!}</h3>
    <p style="text-align:right;">
    	创建人（部门）：${bulletin.createUserName!}
				<#if bulletin.deptName?exists && bulletin.deptName != ''>
					(${bulletin.deptName!})
				</#if>
    	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;创建时间：${(bulletin.createTime?string('yy-MM-dd'))?if_exists}(${bulletin.weekDay!})
    	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;截止时间：&nbsp;&nbsp;${appsetting.getMcodeName("DM-JZSJ",bulletin.endType?default("2"))?default("一年")}
    	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;点击量：${bulletin.clickNum!}
	</p>
	<table id="test" style="width:100%">
	    <tr>
	        <td class="content" id="content2" style="word-break:break-all; word-wrap:break-word;">${bulletin.content!}</td>
	    </tr>
	</table>
</div>
</body>
</html>