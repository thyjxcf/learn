<!--=S 备忘录 Start-->
<div class="grid">
	<div class="option">
		<span class="prev" id="preMonth" title="上个月"></span>
		<span id="yearLabel"></span>-<span id="monthLabel" value=""></span>
		<span class="next" id="nextMonth" title="下个月"></span>
	</div>
	<table border="0" cellspacing="0" cellpadding="0" class="table-hd">
		<tr>
			<td class="lbd">周日</td>
			<td class="lbd_1">周一</td>
			<td class="lbd_1">周二</td>
			<td class="lbd_1">周三</td>
			<td class="lbd_1">周四</td>
			<td class="lbd_1">周五</td>
			<td class="lbd_1">周六</td>
			<td class="rbd">&nbsp;</td>
		</tr>
	</table>
	<div class="table-scroll">
		<table width="100%" border="0" id="monthNote" cellspacing="0"
			cellpadding="0" class="table-kb table-kb-mon">
			<tr style="display: none">
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr style="display: none">
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr style="display: none">
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr style="display: none">
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr style="display: none">
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr style="display: none">
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
		</table>
		<div class="top-layer" id="addMon"></div>
		<div class="top-layer" id="listMon">
		</div>
	</div>

</div>
<script type="text/javascript">
memoUrlw = _contextPath+'/office/desktop/memo/addMemo.action';
$(document).ready(function() {
  // 下一个月
  $("#nextMonth").click(function(e) {
    /*参数：当前年 当前月*/
    var tmp = $('#monthLabel').attr("value");
    memo_month.getmonth($('#yearLabel').text(), tmp, '0');
  });
  // 上一个月
  $("#preMonth").click(function(e) {
    /*参数：当前年 当前月*/
    var tmp = $('#monthLabel').attr("value");
    memo_month.getmonth($('#yearLabel').text(), tmp, '2');
    e.stopPropagation();
  });
  $("#closeListMon").click(function(){
    $("#modelLayer").hide();
  });
});
</script>