<#import "/common/chartstructure.ftl" as chartstructure>
<div class="dt fn-clear"><span class="item-name">学生年级分布统计图</span></div>
	<div class="statistics-wrap">
	<@chartstructure.histogram loadingDivId="studentGradeHistogram" divClass="wrap" divStyle="width: 100%;height: 230px;" jsonStringData=jsonStringCharts[0][0] isDifferentColour=true barWidth=20 isShowLegend=false isShowToolbox=false color="['#b2d8fc','#bcdf69', '#deb968', '#e0796a', '#ee99c3','#a599ef',  '#ffa96e', '#cb92f3','#6791e7', '#89e7b2', '#efd87a']"/>
</div>
