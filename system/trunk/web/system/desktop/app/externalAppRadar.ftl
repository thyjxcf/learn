<#import "/common/chartstructure.ftl" as chartstructure>
<div class="dt fn-clear"><span class="item-name">教师统计图</span></div>
<#if jsonStringCharts??>
	<div class="statistics-wrap">
		<@chartstructure.radar loadingDivId="loadingDivIdRad" divClass="" divStyle="width: 100%;height: 100%;" jsonStringData=jsonStringCharts[0][0] titleFontSize=10 isShowLegend=false isShowToolbox=false />
	</div>
<#else>
	<div class="no_data" style="">暂无统计数据</div>
</#if>
