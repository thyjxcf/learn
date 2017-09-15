<#import "/common/chartstructure.ftl" as chartstructure>
<div class="dt fn-clear"><span class="item-name">下属单位统计图</span></div>
<div class="statistics-wrap">
	<@chartstructure.histogram loadingDivId="loadingSubSchoolSetTypeDiv" divClass="wrap" divStyle="width: 100%;height: 100%;" jsonStringData=jsonStringCharts[0][0] isDifferentColour=true barWidth=20 isShowLegend=false isShowToolbox=false color="['#b2d8fc','#bcdf69', '#deb968', '#e0796a', '#ee99c3','#a599ef',  '#ffa96e', '#cb92f3','#6791e7', '#89e7b2', '#efd87a']"/>
</div>