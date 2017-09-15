<!--天长桌面定制(消息)-->
<div class="desk-notice">
<p class="tt" >通知</p>
<div id="listmarquee">
	<table>
		<#if tcs?exists && tcs?size gt 0>
		<#list tcs as tcNotice>
		<tr>
			<td class="lei" nowrap>[${tcNotice.subTitle!}]</td>
			<td >
				<a href="javascript:;" onclick="${tcNotice.javaScript!}">
					${tcNotice.title!}
				</a>
			</td>
			<td class="time" nowrap>${tcNotice.dateStr!}</td>
		</tr>
		</#list>
		</#if>
	</table>
</div>
</div>
<script>
$(document).ready(function(){
	
	var num = ${dataSize?default('0')};
	var repeat = false;
	if(num>14){
		repeat = true;
	}

    var marquee = new ScrollText("listmarquee",false,false,false,repeat);
    marquee.LineHeight = 60;
    marquee.Amount = 1;
    marquee.Timeout = 30;
    marquee.Delay = 30;
    marquee.Start();
});
</script>
