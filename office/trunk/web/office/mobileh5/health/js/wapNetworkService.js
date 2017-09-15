var wapNetworkService={
		
		doGetClassRank:function(data,studentId){
			if(data.result==Constants.SUCCESS){
				var obj=data.result_object;
				var allList= obj.allStuHealthList;
				var myList=obj.myStuHealthList;
				if(allList!=null && allList.length>0){
					var centerHtml=' <p class="lh60 ml20 f12 grey">班级总人数: '+allList.length+'</p>';
					centerHtml+='<input type="hidden" id="studentId" value="'+studentId+'" />';
					centerHtml+='<ul class="healthList">';
					if(myList!=null && myList.length>0){
						for(var i=0;i<myList.length;i++){
							var myHea=myList[i];
							var photoUrl=myHea.photoUrl;
							var stuRank=myHea.stuRank!=null?myHea.stuRank:0;
							var numClass="num";
							if(stuRank==1){
								numClass="num first";
							}else if(stuRank==2){
								numClass="num second";
							}else if(stuRank==3){
								numClass="num third";
							}
							if(photoUrl==null || photoUrl==""){
								photoUrl="images/head2.jpg";
							}
							centerHtml+='<li class="fn-flex mb20"><div class="'+numClass+'">';
							centerHtml+=stuRank;
							centerHtml+='</div> <div><img class="head" src="'+photoUrl+'"></div>';
							centerHtml+='<div class="fn-flex-auto nowrap">';
							centerHtml+=myHea.studentName!=null?myHea.studentName:"";
							centerHtml+='</div><div class="orange ml15">';
							centerHtml+=myHea.step!=null?myHea.step:0;
							centerHtml+='步</div><div class="km">';
							centerHtml+=myHea.distance!=null?myHea.distance:0;
							centerHtml+='公里</div></li>';
						}
					}
					for(var i=0;i<allList.length;i++){
						var allHea=allList[i];
						var photoUrl=allHea.photoUrl;
						var stuRank=allHea.stuRank!=null?allHea.stuRank:0;
						var numClass="num";
						if(stuRank==1){
							numClass="num first";
						}else if(stuRank==2){
							numClass="num second";
						}else if(stuRank==3){
							numClass="num third";
						}
						if(photoUrl==null || photoUrl==""){
							photoUrl="images/head2.jpg";
						}
						centerHtml+='<li class="fn-flex"><div class="'+numClass+'">';
						centerHtml+=stuRank;
						centerHtml+='</div> <div><img class="head" src="'+photoUrl+'"></div>';
						centerHtml+='<div class="fn-flex-auto nowrap">';
						centerHtml+=allHea.studentName!=null?allHea.studentName:"";
						centerHtml+='</div><div class="orange ml15">';
						centerHtml+=allHea.step!=null?allHea.step:0;
						centerHtml+='步</div><div class="km">';
						centerHtml+=allHea.distance!=null?allHea.distance:0;
						centerHtml+='公里</div></li>';
					}
					centerHtml+='</ul>';
					$("#centerDiv").html(centerHtml);
				}
			}
		},
		
		doGetDetail : function(data,ownerId,studentId,dateType){
			if(data.result==Constants.SUCCESS){
				var obj=data.result_object;
				var health=obj.health;
				var healthList=obj.healthList;
				if(studentId==null || studentId==""){
					studentId=obj.studentId;
				}
				//日期
				if(dateType==2 || dateType==3){
					var beforeDate=obj.beforeDate!=null?obj.beforeDate:"";
					var nowDate=obj.nowDate!=null?obj.nowDate:"";
					$("#showDate").text(beforeDate+"-"+nowDate);
				}else{
					var queryDateStr=obj.queryDateStr;
					var showDate=obj.showDate;
					if(queryDateStr!=null){
						$("#queryDateStr").val(queryDateStr);
					}
					if(showDate!=null){
						$("#showDate").text(showDate);
					}
				}
				//当天总的数据
				if(health!=null){
					$("#step").text(health.step);
					$("#distance").text(health.distance);
					$("#calorie").text(parseInt(health.calorie));
				}else{
					$("#step").text(0);
					$("#distance").text(0);
					$("#calorie").text(0);
				}
				if(dateType==1){
					$("#classRankDiv").show();
					//<span class="fn-flex-auto green" id="stuRank"></span>
                    //<a class="grey no_unl" href="javascript:void(0);">查看榜单</a>
					var stuRank=obj.stuRank;
					if(stuRank==null) stuRank="";
					var classRankHtml='<span class="fn-flex-auto green" id="stuRank">当前班级排名: '+stuRank+'</span>';
					var	clickHtmlStr= 'location.href=\'healthClassRank.html?studentId='+studentId+'&queryDateStr='+$("#queryDateStr").val()+'\'';
					classRankHtml+='<a class="grey no_unl" href="javascript:void(0);"  onclick="'+clickHtmlStr+'">查看榜单</a>';
					$("#classRankDiv").html(classRankHtml);
				}else{
					$("#classRankDiv").hide();
				}
				
				//学生list
				var studentList=obj.studentList;
				if(studentList!=null && studentList.length>0){
					var studentHtml='<ul class="tabMenu1_ul">';
					for(var i=0;i<studentList.length;i++){
						var student=studentList[i];
						studentHtml+='<li id="'+student.studentId+'" class="studentId';
						if(student.studentId==studentId){
							studentHtml+=' active  ';
						}
						studentHtml+='"><a href="javascript:void(0);">'+student.studentName;
						studentHtml+='</a></li>';
					}
					studentHtml+='</ul>';
					$("#studentDiv").html(studentHtml);
				}
				//柱状图数据
				var xData="";
				var yData="";
				if(dateType==2 || dateType==3){
					if(healthList!=null && healthList.length>0){
						xData=new Array();
						yData=new Array();
						for(var i=0;i<healthList.length;i++){
							var hea=healthList[i];
							xData[i]=hea.dateStr;
							yData[i]=hea.step;
						}
					}
				}else{
					xData=new Array();
					yData=new Array();
					var xDataInt=new Array();
					for(var i=0;i<=24;i++){
						xData[i]=i+":00";
						xDataInt[i]=i;
						yData[i]=0;
					}
					if(healthList!=null && healthList.length>0){
						for(var i=0;i<healthList.length;i++){
							var hea=healthList[i];
							for(var j=0;j<xDataInt.length;j++){
								if(hea.hour==xDataInt[j]){
									yData[j]=hea.step;
								}
							}
						}
					}
				}
				
				wapNetworkService.getShow(xData,yData);
				
				wap._detailService.band();
			}
		},
		getShow:function(xData,yData){
			var myChartNum = echarts.init(document.getElementById("echarts5"));
			window.addEventListener('resize',function(){myChartNum.resize();},false);
			var option5 = {
					backgroundColor: '#0ec587',
					tooltip : {
						trigger: 'axis',
						axisPointer: {
							lineStyle: {
								color: '#42f0b5',
							}
						}
					},
					grid: {
						left: '6%',
					right: '16%',
					bottom: '14%'
				},
				xAxis : [
					{
						type : 'category',
						boundaryGap : false,
						data : xData,
						splitLine: {show: false},
						axisLine: {
							lineStyle: {
								color: '#42f0b5'
							}
						}
					}
				],
				yAxis : [
					{
						type : 'value',
						name: '步数',
						position: 'right',
						splitLine: {
							lineStyle: {
								color: '#15d795'
							}
						},
						axisLine: {
							lineStyle: {
								color: '#42f0b5'
							}
						}
					}
				],
				series : [
					{
						type:'line',
						name: '步数',
						itemStyle: {normal: {color: '#78ffce'}},
						areaStyle: {normal: {color: '#3ed19f'}},
						data:yData,
						markPoint: {
							data: [
								{
									label:{
									    normal:{
										    textStyle:{
												color:'#78ffce',
											}
										}
									}
								}
							]
						}
					}
				]
			};
			// 使用刚指定的配置项和数据显示图表
			myChartNum.setOption(option5);
		},
		
};