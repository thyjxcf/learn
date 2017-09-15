<#import "/common/chartstructure.ftl" as chartstructure>
<div class="dt fn-clear"><span class="item-name">教师统计图</span></div>
<#if jsonStringCharts??>
	<div class="statistics-wrap">
		<@chartstructure.histogram loadingDivId="loadingDivIdHis" divClass="" divStyle="width: 100%;height: 100%;" jsonStringData=jsonStringCharts[0][0] isDifferentColour=true barWidth=20 isShowLegend=false isShowToolbox=false color="['#b2d8fc','#bcdf69', '#deb968', '#e0796a', '#ee99c3','#a599ef',  '#ffa96e', '#cb92f3','#6791e7', '#89e7b2', '#efd87a']"/>
	</div>
<#else>
	<div class="no_data" style="">暂无统计数据</div>
</#if>
