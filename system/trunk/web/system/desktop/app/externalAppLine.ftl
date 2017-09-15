<#import "/common/chartstructure.ftl" as chartstructure>
<div class="dt fn-clear"><span class="item-name">2006至2015年初中毕业生升学率变化情况</span></div>
<#if jsonStringCharts??>
	<div class="statistics-wrap">
		<@chartstructure.histogram isLine=true loadingDivId="loadingDivIdLin" divClass="" divStyle="width: 100%;height: 100%;" jsonStringData=jsonStringCharts[0][0] isShowLegend=false isShowToolbox=false/>
	</div>
<#else>
	<div class="no_data" style="">暂无统计数据</div>
</#if>
