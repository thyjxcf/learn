<#import "/common/commonmacro.ftl" as commonmacro>
<#import "/common/chartstructure.ftl" as chartstructure>

<ul class="board-total fn-clear">
	<li class="li-1">
		<div class="item">
			<p class="tt">资源总数</p>
			<p>1043246个</p>
		</div>
	</li>
	<li class="li-2">
		<div class="item">
			<p class="tt">学校总数</p>
			<p>6241所</p>
		</div>
	</li>
	<li class="li-3">
		<div class="item">
			<p class="tt">教师总数</p>
			<p>208071人</p>
		</div>
	</li>
	<li class="li-4">
		<div class="item">
			<p class="tt">学生总数</p>
			<p>2307421人</p>
		</div>
	</li>
</ul>

<ul class="board-wrap fn-clear">
	<li class="board-item">
		<div class="item">
        	<p class="dt"><span class="item-name">教师学历统计</span></p>
        	<@chartstructure.histogram loadingDivId="loadingDivId0" divClass="wrap" divStyle="width: 100%;height: 280px;" jsonStringData=jsonStringCharts[0][0] isDifferentColour=true barWidth=20 isShowLegend=false isShowToolbox=false/>
        </div>
	</li>
	<li class="board-item">
		<div class="item">
        	<p class="dt"><span class="item-name">教师学历统计</span></p>
        	<@chartstructure.pieChart loadingDivId="loadingDivId1" divClass="wrap" divStyle="width: 100%;height: 280px;" jsonStringData=jsonStringCharts[1][0] titleFontSize=10 isShowLegend=false isShowToolbox=false isShowDataLabel=true/>
        </div>
	</li>
	
	
	<li class="board-item">
		<div class="item">
        	<p class="dt"><span class="item-name">教师年龄统计</span></p>
        	<@chartstructure.histogram loadingDivId="loadingDivId2" divClass="wrap" divStyle="width: 100%;height: 280px;" jsonStringData=jsonStringCharts[2][0] isDifferentColour=true barWidth=20 isShowLegend=false isShowToolbox=false/>
        </div>
	</li>
	<li class="board-item">
		<div class="item">
        	<p class="dt"><span class="item-name">教师年龄统计</span></p>
        	<@chartstructure.pieChart loadingDivId="loadingDivId3" divClass="wrap" divStyle="width: 100%;height: 280px;" jsonStringData=jsonStringCharts[3][0] titleFontSize=10 isShowLegend=false isShowToolbox=false isShowDataLabel=true/>
        </div>
	</li>
	
	
	<li class="board-item full">
		<div class="item">
        	<p class="dt"><span class="item-name">学段学生性别统计</span></p>
        	<@chartstructure.histogram loadingDivId="loadingDivId5" divClass="wrap" divStyle="width: 100%;height: 280px;" jsonStringData=jsonStringCharts[5][0] units="k" isShowLegend=true isShowToolbox=false/>
        </div>
	</li>
	
	
	<li class="board-item">
		<div class="item">
        	<p class="dt"><span class="item-name">学生户口类别统计</span></p>
        	<@chartstructure.histogram loadingDivId="loadingDivId6" divClass="wrap" divStyle="width: 100%;height: 280px;" jsonStringData=jsonStringCharts[6][0] units="k" isDifferentColour=true barWidth=20 isShowLegend=false isShowToolbox=false/>
        </div>
	</li>
	<li class="board-item">
		<div class="item">
        	<p class="dt"><span class="item-name">学生户口类别统计</span></p>
        	<@chartstructure.pieChart loadingDivId="loadingDivId7" divClass="wrap" divStyle="width: 100%;height: 280px;" jsonStringData=jsonStringCharts[7][0] units="k" titleFontSize=10 isShowLegend=false isShowToolbox=false isShowDataLabel=true/>
        </div>
	</li>
	
	
	<li class="board-item">
		<div class="item">
        	<p class="dt"><span class="item-name">学生类别统计</span></p>
        	<@chartstructure.histogram loadingDivId="loadingDivId8" divClass="wrap" divStyle="width: 100%;height: 280px;" jsonStringData=jsonStringCharts[8][0] units="k" isDifferentColour=true barWidth=20 isShowLegend=false isShowToolbox=false/>
        </div>
	</li>
	<li class="board-item">
		<div class="item">
        	<p class="dt"><span class="item-name">学生类别统计</span></p>
        	<@chartstructure.pieChart loadingDivId="loadingDivId9" divClass="wrap" divStyle="width: 100%;height: 280px;" jsonStringData=jsonStringCharts[9][0] units="k" titleFontSize=10 isShowLegend=false isShowToolbox=false isShowDataLabel=true/>
        </div>
	</li>
	
	
	<li class="board-item">
		<div class="item">
        	<p class="dt"><span class="item-name">资源日上传量统计</span></p>
        	<@chartstructure.histogram loadingDivId="loadingDivId10" divClass="wrap" divStyle="width: 100%;height: 280px;" jsonStringData=jsonStringCharts[10][0] isLine=true isShowLegend=false isShowToolbox=false/>
        </div>
	</li>
	<li class="board-item">
		<div class="item">
        	<p class="dt"><span class="item-name">资源来源统计</span></p>
        	<@chartstructure.pieChart loadingDivId="loadingDivId11" divClass="wrap" divStyle="width: 100%;height: 280px;" jsonStringData=jsonStringCharts[11][0] titleFontSize=10 isShowLegend=false isShowToolbox=false isShowDataLabel=true/>
        </div>
	</li>
	
	
	<li class="board-item">
		<div class="item">
        	<p class="dt"><span class="item-name">资源种类统计</span></p>
        	<@chartstructure.histogram loadingDivId="loadingDivId12" divClass="wrap" divStyle="width: 100%;height:280px;" jsonStringData=jsonStringCharts[12][0] isXYexchange=true isDifferentColour=true isShowLegend=false isShowToolbox=false/>
        </div>
	</li>
	<li class="board-item">
		<div class="item">
        	<p class="dt"><span class="item-name">学科资源统计</span></p>
        	<@chartstructure.histogram loadingDivId="loadingDivId13" divClass="wrap" divStyle="width: 100%;height: 280px;" jsonStringData=jsonStringCharts[13][0] isLine=true isAreaStyle=true isShowLegend=false isShowToolbox=false/>
        </div>
	</li>
	
	
	<li class="board-item">
		<div class="item">
        	<p class="dt"><span class="item-name">资源上传每分钟的数量<span class="fn-right" id="resourceNumber" class="ml-20" style="font-size: 30px;font-weight: bold;color:red;">1043246</span><span class="fn-right" style="font-size: 30px;font-weight: bold;color:red;">总数：</span></span>
        	</p>
        	<@chartstructure.gauge loadingDivId="loadingDivId14" divClass="wrap" divStyle="width: 100%;height: 280px;" jsonStringData=jsonStringCharts[14][0] isShowToolbox=false/>
        </div>
	</li>
	
	
	<li class="board-item full">
		<div class="item">
        	<p class="dt"><span class="item-name">考勤数据</span></p>
        	<@chartstructure.histogram loadingDivId="loadingDivId15" divClass="wrap" divStyle="width: 100%;height: 280px;" jsonStringData=jsonStringCharts[15][0] isShowLegend=true isShowToolbox=false/>
        </div>
	</li>
	
	
	<li class="board-item full">
		<div class="item">
        	<p class="dt"><span class="item-name">教师异动数据</span></p>
        	<@chartstructure.histogram loadingDivId="loadingDivId16" divClass="wrap" divStyle="width: 100%;height: 280px;" jsonStringData=jsonStringCharts[16][0] isShowLegend=true isShowToolbox=false/>
        </div>
	</li>
	<li class="board-item full">
		<div class="item">
        	<p class="dt"><span class="item-name">学生异动数据</span></p>
        	<@chartstructure.histogram loadingDivId="loadingDivId17" divClass="wrap" divStyle="width: 100%;height: 280px;" jsonStringData=jsonStringCharts[17][0] isShowLegend=true isShowToolbox=false/>
        </div>
	</li>
	<script>
		$(document).ready(function(){
			var resourceNumber=$("#resourceNumber");
			resourceNumber.numberAnimate();
			var intNumber=1043246;
			if(objTimer!=0){
				clearInterval(objTimer);
			}
			var overallNum=-1;
			objTimer = setInterval(function(){
				intNumber+=+25;
				resourceNumber.numberAnimate('set', intNumber);
				var ind=parseInt(Math.random()*10);
				while(overallNum==ind){
					ind=parseInt(Math.random()*10);
				}
				overallNum=ind;
				switch(ind){
					case 0 : 
						loadingDivId14changeData('${jsonStringCharts[14][0]}');
						break; 
					case 1 : 
						loadingDivId14changeData('${jsonStringCharts[14][1]}');
						break; 
					case 2 : 
						loadingDivId14changeData('${jsonStringCharts[14][2]}');
						break; 
					case 3 : 
						loadingDivId14changeData('${jsonStringCharts[14][3]}');
						break; 
					case 4 : 
						loadingDivId14changeData('${jsonStringCharts[14][4]}');
						break; 
					case 5 : 
						loadingDivId14changeData('${jsonStringCharts[14][5]}');
						break; 
					case 6 : 
						loadingDivId14changeData('${jsonStringCharts[14][6]}');
						break; 
					case 7 : 
						loadingDivId14changeData('${jsonStringCharts[14][7]}');
						break; 
					case 8 : 
						loadingDivId14changeData('${jsonStringCharts[14][8]}');
						break; 
					case 9 : 
						loadingDivId14changeData('${jsonStringCharts[14][9]}');
						break; 
					default : 
						break; 
				}
			},3000);
		});
	</script>
	
</ul>
