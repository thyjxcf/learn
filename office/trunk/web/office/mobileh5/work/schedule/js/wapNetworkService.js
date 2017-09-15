var wapNetworkService = {
		
	// 工作日志申请列表信息
	doLoadList : function(dataType,data){
		wapNetworkService._service._loadList(dataType,data);
	},
	// 日历
	doLoadCalendar : function(data){
		wapNetworkService._service._loadCalendar(data);
	},
	//工作日志申请
	doLoadApplyDetail : function(id,data){
		wapNetworkService._service._loadApplyDetail(id,data);
	},
	//工作日志详情
	doLoadDetail : function(data){
		wapNetworkService._service._doLoadDetail(data);
	},
	
	//私有方法
	_service : {
		_loadList : function(dataType,data) {
			if(data.result==Constants.SUCCESS){
				WapPage = data.page;
				var array = data.result_array;
				var isGroupHead = data.isGroupHead;
				var isLeader = data.isLeader;
//				var hasUnitAuth = storage.get(WapConstants.HAS_UNIT_AUTH);
				if(isGroupHead=='1'){
					$("#myTab2").show();
				}else{
					$("#myTab2").hide();
				}
				if(isLeader=='1'){
					$("#myTab3").show();
				}else{
					$("#myTab3").hide();
				}
				if(dataType=='2'||dataType=='3'){
					$("#apply").hide();
				}else{
					$("#apply").show();
				}
				if(array.length > 0){
					//列表
					$("#list").html('');
					var listHtmlStr = '';
					
					$.each(array, function(index, json) {
						var htmlInfo = '';
							htmlInfo += 'location.href=\'scheduleDetail.html?id='+json.id+'&dataType='+dataType+'\'';
						if(index%4==0){
							listHtmlStr+='<li onclick="'+htmlInfo+'" class="blue fn-clear">';
						}else if(index%4==1){
							listHtmlStr+='<li onclick="'+htmlInfo+'" class="green fn-clear">';
						}else if(index%4==2){
							listHtmlStr+='<li onclick="'+htmlInfo+'" class="yellow fn-clear">';
						}else if(index%4==3){
							listHtmlStr+='<li onclick="'+htmlInfo+'" class="orange fn-clear">';
						}
						listHtmlStr+='<p class="tt f-14">'+json.periodName+'</p>';
						listHtmlStr+='<div class="dd">';
						listHtmlStr+='<p class="dt f-15">'+json.content+'</p>';
						listHtmlStr+='<p class="user f-12">'+json.userName+'&nbsp;&nbsp;<span>'+json.place+'</span></p>';
						listHtmlStr+='<p class="time f-12">'+json.startDate+'-'+json.endDate+'</p>';
						listHtmlStr+='</div>';
						listHtmlStr+='</li>';
					});
					$("#list").html(listHtmlStr);
					
					if (WapPage.pageIndex >= WapPage.maxPageIndex) {
				    	$('.loading-more').hide();
				    	$('.loading-more').unbind();
				    }
					
					$("#listDiv").show();
					$('#empty').hide();
				}else{
					//内容为空
					$('#listDiv').hide();
					$('.loading-more').hide();
					$('#empty').show();
				}
			}
	    },
	    
	    _loadCalendar : function(data) {
	    	if(data.result==Constants.SUCCESS){
	    		var obj = data.result_object;
	    		var weekNum = obj.weekNum;
	    		var firstWeek = obj.firstWeek;
	    		var prevDayNum = obj.prevDayNum;
	    		var dayNum = obj.dayNum;
	    		var choiseDay = obj.choiseDay;
	    		var redArray = obj.redArray;
	    		var weekHtmlStr = '';
	    		var dayHtmlStr = '';
	    		var monHtmlStr = '<tr class="tit"><th>日</th><th>一</th><th>二</th><th>三</th><th>四</th><th>五</th><th>六</th></tr>';
    			
	    		 $("#dateVal").html(obj.dateVal);
	    		 $("#date").val(obj.date);
	    		if(weekNum>0&&firstWeek>0&&prevDayNum>0&&dayNum>0){
	    			for (var i=0;i<weekNum;i++){
	    				weekHtmlStr += '<th>日</th><th>一</th><th>二</th><th>三</th><th>四</th><th>五</th><th>六</th>';
	    			}
	    			for (var i=1;i<firstWeek;i++){
	    				dayHtmlStr += '<td class="prev"><span>'+(prevDayNum-firstWeek+i+1)+'</span><i></i></td>';
	    			}
	    			for (var i=0;i<dayNum;i++){
	    				var clsStr = '';
	    				if(redArray.length > 0){
	    					for(var j=0;j<redArray.length;j++){
	    						if((i+1)==redArray[j]){
	    							clsStr = 'has';
	    						}
	    					}
	    				}
	    				if(choiseDay==(i+1)){
	    					dayHtmlStr+='<td class="today '+clsStr+'"><span>'+(i+1)+'</span><i></i></td>';
	    				}else{
	    					dayHtmlStr+='<td class="'+clsStr+'"><span>'+(i+1)+'</span><i></i></td>';
	    				}
	    			}
	    			for (var i=0;i<(weekNum*7-firstWeek-dayNum+1);i++){
	    				dayHtmlStr+='<td class="next"><span>'+(i+1)+'</span><i></i></td>';
	    			}
	    			if(firstWeek!=1){
	    				if((choiseDay+firstWeek)<7){
	    					monHtmlStr+='<tr class="cur-line">';
	    				}else{
	    					monHtmlStr+='<tr class="no-cur-line">';
	    				}
	    				for (var i=1;i<firstWeek;i++){
	    					monHtmlStr += '<td class="prev"><span>'+(prevDayNum-firstWeek+i+1)+'</span><i></i></td>';
	    				}
	    			}
	    			for (var i=0;i<dayNum;i++){
	    				if((firstWeek+i-1)%7==0){
	    					if(firstWeek!=1){
	    						monHtmlStr+='</tr>';
	    					}
	    					if((choiseDay)>i&&i>=(choiseDay-6)){
	    						monHtmlStr+='<tr class="cur-line">';
	    					}else{
	    						monHtmlStr+='<tr class="no-cur-line">';
	    					}
	    				}
	    				var clsStr = '';
	    				if(redArray.length > 0){
	    					for(var j=0;j<redArray.length;j++){
	    						if((i+1)==redArray[j]){
	    							clsStr = 'has';
	    						}
	    					}
	    				}
	    				if(choiseDay==(i+1)){
	    					monHtmlStr+='<td class="today '+clsStr+'"><span>'+(i+1)+'</span><i></i></td>';
	    				}else{
	    					monHtmlStr+='<td class="'+clsStr+'"><span>'+(i+1)+'</span><i></i></td>';
	    				}
//	    				if(choiseDay==(i+1)){
//	    					monHtmlStr+='<td class="today"><span>'+(i+1)+'</span><i></i></td>';
//	    				}else{
//	    					monHtmlStr+='<td><span>'+(i+1)+'</span><i></i></td>';
//	    				}
	    			}
	    			for (var i=0;i<(weekNum*7-firstWeek-dayNum+1);i++){
	    				monHtmlStr+='<td class="next"><span>'+(i+1)+'</span><i></i></td>';
	    			}
	    			monHtmlStr+='</tr>';
	    			$("#dayList").html(dayHtmlStr);
	    			$("#weekList").html(weekHtmlStr);
	    			$("#monList").html(monHtmlStr);
	    			scrollCalendar();
	    			changeToday();
	    		}
	    	}
	    },
	    
	    //申请
	    _loadApplyDetail : function(id, data){
	    	if(data.result==Constants.SUCCESS){
	    		var obj = data.result_object;
	    		
	    		//时间段
	    		var periodArray = obj.periodArray;
	    		var period = obj.period;
	    		$("#period").html('');
	    		if(periodArray.length > 0){
	    			var htmlStr = '';
	    			$.each(periodArray, function(index, json) {
	    				if(isNotBlank(period) && type == json.period){
	    					htmlStr += '<option value="'+json.period+'" selected="selected">'+json.periodName+'</option>';
	    				}else{
	    					htmlStr += '<option value="'+json.period+'">'+json.periodName+'</option>';
	    				}
	    			});
	    			$("#period").html(htmlStr);
	    		}
	    		wap._editService.initBind();//绑定
	    		$("#id").val(obj.id);
		    	if(isNotBlank(id)){//编辑
					$("#startDate").val(obj.beginTime);
					$("#endDate").val(obj.endTime);
					$("#place").val(obj.place);
					$("#content").val(obj.content);
					
					//附件
		    		var attachmentArray = obj.attachmentArray;
		    		if(attachmentArray.length > 0){
		    			var fileName = '';
		    			$.each(attachmentArray, function(index, json){
		    				var fileSize = getFileSize(json.fileSize);
		    				if(json.fileExist){
		    					fileName = json.fileName;
		    					$("#fileName").val(fileName);
		    					return;
			    			}
		    			});
		    		}
				}
//		    	$('#period_dummy').attr('placeholder','请选择（必填）').val('');
	    	}	
	    },
	    
	    //详情
	    _doLoadDetail : function(data){
	    	if(data.result==Constants.SUCCESS){
	    		var obj = data.result_object;
	    		var typeStr = '';
	    		var _contextPath = storage.get(Constants.MOBILE_CONTEXT_PATH);
	    		$("#startDate").html(obj.startDate);
	    		$("#endDate").html(obj.endDate);
	    		$("#place").html(obj.place);
	    		$("#period").html(obj.periodName);
	    		$("#content").html(obj.content);
	    		var path = obj.photoUrl;
	    		var htmlStr='';
                if(path==''||path==null){
                	htmlStr += '<span class="place-avatar" style="background:'+getBackgroundColor(obj.userName)+';">'+getLast2Str(obj.userName)+'</span>';
				}else{
					htmlStr += '<img src="'+path+'">';
				}
	    		var str = htmlStr+'<span class="f-17">'+obj.userName+'</span>'
    			$("#opreator").html(str);
	    		//富文本中图片、附件等格式优化
	    		$("img").each(function(){
	    			if($(this).width()==0)
	    			$(this).attr("style","max-width:85%;height:auto;"); 
    		    });
                if (isIOS()){
		    		$("p[style='line-height: 16px;']").each(function(){
		    			$(this).find("img").attr("style","height:30px;padding-top:1px;");
		    			$(this).find("a").attr("style","font-size:32px;");
		    			if(!$(this).has("img").length){
		    				//$(this).removeAttr("style","line-height: 16px;");
		    				$(this).find("span").attr("style","");
		    			}
		    			$(this).attr("style","padding-bottom:5px;");
		    		});
                }
	    		if (isAndroid()){
	    			$("p[style='line-height: 16px;']").each(function(){
	    				$(this).find("img").attr("style","height:16px;padding-top:1px;");
	    				$(this).find("a").attr("style","font-size:16px;");
	    				$(this).attr("style","line-height: 16px;padding-bottom:5px;");
	    			});
	    		}
	    		//附件
	    		var attachmentArray = obj.attachmentArray;
	    		if(attachmentArray.length > 0){
	    			var htmlStr = ''; 
	    			$.each(attachmentArray, function(index, json){
	    				var fileSize = getFileSize(json.fileSize);
	    				htmlStr += '<li class="fn-clear">';
	    				htmlStr += getFilePic(json.extName);
	    				if(json.fileExist){
    						htmlStr+='<a href="'+json.downloadPath+'" class="icon-img icon-download"></a>';
		    			}else{
		    				htmlStr+='<a href="javascript:viewToast('+"'附件不存在'"+');" class="icon-img icon-download"></a>';
		    			}
	    				htmlStr += '<p class="acc-tt f-16">'+json.fileName+'</p>';
	    				htmlStr += '<p class="acc-dd">'+fileSize+'</p>';
	    				htmlStr += '</li>';
	    			});
	    			$("#fileDiv").html(htmlStr);
	    		}
	    	}
	    },
	}   
};