<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<#include "/common/chart.ftl">


<script type="text/javascript" src="${request.contextPath}/static/js/chart/jquery.jqplot.min.js"></script>
<link rel="stylesheet" type="text/css" hrf="${request.contextPath}/static/js/chart/jquery.jqplot.min.css" />

<script type="text/javascript" src="${request.contextPath}/static/js/chart/plugins/jqplot.json2.min.js"></script>


<script type="text/javascript" src="${request.contextPath}/static/js/chart/plugins/jqplot.barRenderer.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/chart/plugins/jqplot.BezierCurveRenderer.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/chart/plugins/jqplot.blockRenderer.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/chart/plugins/jqplot.bubbleRenderer.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/chart/plugins/jqplot.canvasAxisLabelRenderer.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/chart/plugins/jqplot.canvasAxisTickRenderer.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/chart/plugins/jqplot.canvasOverlay.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/chart/plugins/jqplot.canvasTextRenderer.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/chart/plugins/jqplot.categoryAxisRenderer.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/chart/plugins/jqplot.ciParser.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/chart/plugins/jqplot.cursor.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/chart/plugins/jqplot.dateAxisRenderer.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/chart/plugins/jqplot.donutRenderer.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/chart/plugins/jqplot.dragable.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/chart/plugins/jqplot.enhancedLegendRenderer.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/chart/plugins/jqplot.funnelRenderer.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/chart/plugins/jqplot.highlighter.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/chart/plugins/jqplot.json2.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/chart/plugins/jqplot.logAxisRenderer.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/chart/plugins/jqplot.mekkoAxisRenderer.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/chart/plugins/jqplot.mekkoRenderer.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/chart/plugins/jqplot.meterGaugeRenderer.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/chart/plugins/jqplot.mobile.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/chart/plugins/jqplot.ohlcRenderer.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/chart/plugins/jqplot.pieRenderer.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/chart/plugins/jqplot.pointLabels.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/chart/plugins/jqplot.pyramidAxisRenderer.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/chart/plugins/jqplot.pyramidGridRenderer.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/chart/plugins/jqplot.pyramidRenderer.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/chart/plugins/jqplot.trendline.min.js"></script>

<script type="text/javascript">
function query(){
	var url = '${request.contextPath}/common/chart!showChart1.action';
	chart.loadLineChart('chart1',url,{'singleSeries':false},{title:'测试1',axes : {
			xaxis : {				
				tickOptions : {
					angle : -50
				}
			}
		}});
	chart.loadBarChart('chart2',url,{'singleSeries':false},{title:'测试2'});
	chart.loadPieChart('chart3',url,{'singleSeries':true},{title:'测试3'});
	chart.loadDonutChart('chart4',url,{'singleSeries':false},{title:'测试4'});
	
}
$(document).ready(function(){ 
	query();
});

</script>
</head>
<body>
图表：<button onclick='query();'>加载数据</button>
	<div id="chart1" style="width:500px; height: 400px"></div>
	<div id="chart2" style="width:500px; height:400px;"></div><br>
	<div id="chart3" style="width:500px; height:250px;"></div><br>
	<div id="chart4" style="width:500px; height:250px;"></div><br>
</body>	
<script type="text/javascript">
	
</script>

</html> 
