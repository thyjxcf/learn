<#import "/common/chartstructure.ftl" as chartstructure>
<div class="dt fn-clear">
	<div  style="width:29%; float:left;">
	<span class="item-name">下属学校学生类别分布图</span>
	</div>
	<div  style="width:24%; float:left;">
	<span class="item-name">小学年级学生人数统计</span>
	</div>
	<div  style="width:23%; float:left;">
	<span class="item-name">初中年级学生人数统计</span>
	</div>
	<div  style="width:24%; float:left;">
	<span class="item-name">高中年级学生人数统计</span>
	</div>
</div>
	<div class="statistics-wrap">
	<#--<@chartstructure.pieChart loadingDivId="subSchoolStudentCategoryPie" divClass="fn-left" divStyle="width: 100%;height: 100%;" jsonStringData=jsonStringCharts[0][0] titleFontSize=10 isShowLegend=false isShowToolbox=false isShowDataLabel=false/>-->
	<@chartstructure.pieChart loadingDivId="subSchoolStudentCategoryPie1" divClass="fn-left" divStyle="width: 29%;height: 100%;" jsonStringData=jsonStringCharts[0][0] titleFontSize=10 isShowLegend=true isShowToolbox=false isShowDataLabel=false color="['#b2d8fc','#bcdf69', '#deb968', '#e0796a', '#ee99c3','#a599ef',  '#ffa96e', '#cb92f3','#6791e7', '#89e7b2', '#efd87a']"/>
	<@chartstructure.pieChart loadingDivId="subSchoolStudentCategoryPie2" divClass="fn-left" divStyle="width: 24%;height: 100%;" jsonStringData=jsonStringCharts[1][0] titleFontSize=10 isShowLegend=true isShowToolbox=false isShowDataLabel=false color="['#b2d8fc','#bcdf69', '#deb968', '#e0796a', '#ee99c3','#a599ef',  '#ffa96e', '#cb92f3','#6791e7', '#89e7b2', '#efd87a']"/>
	<@chartstructure.pieChart loadingDivId="subSchoolStudentCategoryPie3" divClass="fn-left" divStyle="width: 23%;height: 100%;" jsonStringData=jsonStringCharts[2][0] titleFontSize=10 isShowLegend=true isShowToolbox=false isShowDataLabel=false color="['#b2d8fc','#bcdf69', '#deb968', '#e0796a', '#ee99c3','#a599ef',  '#ffa96e', '#cb92f3','#6791e7', '#89e7b2', '#efd87a']"/>
	<@chartstructure.pieChart loadingDivId="subSchoolStudentCategoryPie4" divClass="fn-left" divStyle="width: 24%;height: 100%;" jsonStringData=jsonStringCharts[3][0] titleFontSize=10 isShowLegend=true isShowToolbox=false isShowDataLabel=false color="['#b2d8fc','#bcdf69', '#deb968', '#e0796a', '#ee99c3','#a599ef',  '#ffa96e', '#cb92f3','#6791e7', '#89e7b2', '#efd87a']"/>
</div>