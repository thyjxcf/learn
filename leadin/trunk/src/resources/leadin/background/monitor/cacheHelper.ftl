<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<title>缓存管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script language="JavaScript" src="${request.contextPath}/static/js/buffalo.js"></script>
<#import "/common/htmlcomponent.ftl" as common />
<script language="javascript">
var buffalo=new Buffalo('');
buffalo.async = false; //同步执行 

function initCache(indexKey){ 
	buffalo.remoteActionCall("cacheHelper-remote.action","initCache",[indexKey],function(reply){
		var result=reply.getResult();
		if(result){
			showMsgSuccess("初始化成功");
		}else{
			showMsgError("初始化失败");
		}
		window.setTimeout("document.location.href = document.location.href",1000);
	}); 	
} 

function clearCache(indexKey){ 
	buffalo.remoteActionCall("cacheHelper-remote.action","clearCache",[indexKey],function(reply){
		var result=reply.getResult();
		if(result){
			showMsgSuccess("清除成功");
		}else{
			showMsgErrort("清除失败");
		}
		window.setTimeout("document.location.href = document.location.href",1000);
	}); 	
} 

function testCache(){
	buffalo.remoteActionCall("cacheHelper-remote.action","testCache",[],function(reply){		
		showMsgSuccess("测试完成");
	});
}
 jQuery(document).ready(function(){
	jQuery("#table1").height((jQuery("#subIframe", window.parent.document).height()-jQuery('.head-tt').height() - jQuery("#p1").height()-jQuery("#p2").height()-5)/3*2);
	jQuery("#table2").height((jQuery("#subIframe", window.parent.document).height()-jQuery('.head-tt').height() - jQuery("#p1").height()-jQuery("#p2").height()-5)/3*1);
})
</script>
<body >
<form name="form1" method="post">
<p id="p1">缓存数据对象信息：</p>
			<@common.tableList divId="table1">
				<tr>
					<th>序号</td>
					<th>数据类别</td>
					<th>是否启用缓存</td>
					<th>缓存状态</td>
					<th>初始化缓存</td>
					<th>清除缓存</td>	
				</tr>
				<#list cacheObjects as x>				
				<tr height="10px;" onMouseover="this.className='trmouseover';" onMouseout="this.className='<#if x_index % 2 == 1>trdual<#else>trsingle</#if>';" class="<#if x_index % 2 == 1>trdual<#else>trsingle</#if>">
					<td align="center">${x_index + 1}</td>
					<td align="center">${x.dataName}</td>
					<td align="center">${x.useCache?string}</td>
					<td align="center">${x.status}</td>					
					<td><a onclick="initCache('${x.indexKey}');" href="#">初始化</a></td>
					<td><a onclick="clearCache('${x.indexKey}');" href="#"">清除</a></td>
				</tr>		
				</#list>
				<tr>
				<td height="35" colspan="5">
					<span class="input-btn2" ><button type="button" onclick="initCache('eis');" >初始化eis缓存</button></span>
					<span class="input-btn2" ><button type="button" onclick="clearCache('eis');" >清除eis缓存</button></span>
					<span class="input-btn2" ><button type="button" onclick="clearCache('');" >清除所有缓存</button></span>
					<span class="input-btn2" ><button type="button" onclick="testCache();" >测试</button></span>
				</td>
			</tr>	
			</@common.tableList>
		</td></tr>	
<p id="p2">缓存信息：</p>
				<@common.tableList divId="table2">
					<tr><td>
					响应时间信息：
					名称：${cacheStat.response.cacheName} &nbsp;&nbsp;
					开始时间：${cacheStat.response.startTime?string('yyyy-mm-dd hh:mm:ss')}&nbsp;&nbsp;
					结束时间：${cacheStat.response.endTime?string('yyyy-mm-dd hh:mm:ss')}
					</td></tr>
				<tr><td>
					缓存状态：					
					</td></tr>
					<tr><td>
					缓存items：</td></tr>
				</@common.tableList>
</form>
</body>
</html>
