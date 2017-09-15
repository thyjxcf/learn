<#import "/common/chartstructure.ftl" as chartstructure>
<div class="dt fn-clear"><span class="item-name">学生综合统计</span></div>
<#if jsonStringCharts??>
	<div class="statistics-wrap">
		<@chartstructure.pieChart loadingDivId="loadingDivIdPie111" divClass="fn-left" divStyle="width: 33%;height: 100%;" jsonStringData=jsonStringCharts[0][0] titleFontSize=10 isShowLegend=false isShowToolbox=false isShowDataLabel=false color="['#b2d8fc','#bcdf69', '#deb968', '#e0796a', '#ee99c3','#a599ef',  '#ffa96e', '#cb92f3','#6791e7', '#89e7b2', '#efd87a']"/>
		<@chartstructure.pieChart loadingDivId="loadingDivIdPie222" divClass="fn-left" divStyle="width: 33%;height: 100%;" jsonStringData=jsonStringCharts[1][0] titleFontSize=10 isShowLegend=false isShowToolbox=false isShowDataLabel=false color="['#b2d8fc','#bcdf69', '#deb968', '#e0796a', '#ee99c3','#a599ef',  '#ffa96e', '#cb92f3','#6791e7', '#89e7b2', '#efd87a']"/>
		<@chartstructure.pieChart loadingDivId="loadingDivIdPie333" divClass="fn-left" divStyle="width: 33%;height: 100%;" jsonStringData=jsonStringCharts[2][0] titleFontSize=10 isShowLegend=false isShowToolbox=false isShowDataLabel=false color="['#b2d8fc','#bcdf69', '#deb968', '#e0796a', '#ee99c3','#a599ef',  '#ffa96e', '#cb92f3','#6791e7', '#89e7b2', '#efd87a']"/>
	</div>
<#else>
	<div class="no_data" style="display:none;">暂无统计数据</div>
</#if>
