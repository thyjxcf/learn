<p class="tt"><a href="#" class="close">关闭</a><span>纪要查看</span></p>
<div class="wrap pa-10" id="minutesDetailDiv">
	<p class="dt"><span class="f16 b">${officeExecutiveMeet.name!}</span></p>
    <#list officeExecutiveMeetMinuteList as x>
    <div class="meet-item">
    	<p class="tit"><span class="sort">${x_index+1}</span></p>
    	<p><textarea class="text-area" readonly>${x.content!}</textarea></p>
        <div class="table-wrap">
            <table>
                <tr>
                    <th style="text-align:left;">${x.deptNames!}</th>
                </tr>
            </table>
        </div>
    </div>
    </#list>
</div>
<p class="dd">
	<a class="abtn-blue reset ml-5" href="#"><span>关闭</span></a>
</p>
<script>
$(function(){
	$('.schedule-layer-meet .wrap').scroll(function(){
		var scTop=$(this).scrollTop();
		$(this).children('.dt').css('top',scTop);
	});
});
</script>