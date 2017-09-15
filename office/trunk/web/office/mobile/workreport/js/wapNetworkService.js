var wapNetworkService = {
		
	// 工作汇报列表信息
	doLoadList : function(dataType, searchStr, data){
		wapNetworkService._service._loadList(dataType, searchStr, data);
	},
	
	doLoadApplyDetail : function(id, data){
		wapNetworkService._service._loadApplyDetail(id, data);
	},
	
	doLoadDetail : function(dataType, data){
		wapNetworkService._service._doLoadDetail(dataType, data);
	},
	
	
	//私有方法
	_service : {
		
		_loadList : function(dataType, searchStr, data) {
			if(data.result==Constants.SUCCESS){
				WapPage = data.page;
				storage.set(WapConstants.WR_SEARCH_STR, searchStr);
				var array = data.result_array;
				if(array.length > 0){
					//改变列表样式
					if(dataType == WapConstants.DATA_TYPE_2){
						$("#list").attr('class', 'ui-list ui-list-full');
					}else{
						$("#list").attr('class', 'ui-list');
					}
					
					//列表
					var listHtmlStr = '';
					$.each(array, function(index, json) {
						var typeStr = '';
						if(WapConstants.REPORT_TYPE_1 == json.reportType){//周报
							typeStr = '周报';
                        }else if(WapConstants.REPORT_TYPE_2 == json.reportType){//月报
                        	typeStr = '月报';
                        }
						
						var username = '';
						if(dataType == WapConstants.DATA_TYPE_2){
							username = json.sendUserName;
						}else{
							username = json.receiveUserName;
						}
						
						var htmlInfo = '';
						if(WapConstants.DATA_TYPE_2 == dataType){//我收到的
							htmlInfo = 'location.href=\'workreportDetail.html?id='+json.id+'&dataType='+dataType+'\'';
						}else{//我发出的
							if(Constants.APPLY_STATUS_1 == json.state){//未提交
								htmlInfo = 'location.href=\'workreportEdit.html?id='+json.id+'&dataType='+dataType+'\'';
							}else{
								htmlInfo = 'location.href=\'workreportDetail.html?id='+json.id+'&dataType='+dataType+'\'';
							}
						}
						
						listHtmlStr += '<li onclick="'+htmlInfo+'">';
                        if(WapConstants.DATA_TYPE_1 == dataType){
                        	var stateClass = '';
    						var stateStr = '';
    						if(Constants.APPLY_STATUS_2 == json.state){//已提交
    							stateClass = 'type-green';
    							stateStr = "已提交";
    						}else if(Constants.APPLY_STATUS_1 == json.state){//未提交
    							stateClass = 'type-blue';
    							stateStr = "未提交";
    						}	
                        	listHtmlStr += '<span class="type '+stateClass+' f-14">'+stateStr+'</span>';
                        }
                        
                        listHtmlStr += '<p class="tt f-18">'+json.content+'</p>';
                        listHtmlStr += '<p class="dd fn-clear f-14">';
                        listHtmlStr += '<span class="dd-1">'+typeStr+'</span>';
                        if(dataType == WapConstants.DATA_TYPE_2){
                        	listHtmlStr += '<span class="dd-2">'+username+'</span>';
                        }
                        listHtmlStr += '<span class="dd-3">'+json.sendTime+'</span>';
                        listHtmlStr += '</p>';
                        listHtmlStr += '</li>';
					});
					$("#list").append(listHtmlStr);
					
					if (WapPage.pageIndex >= WapPage.maxPageIndex) {
				    	$('.loading-more').html('<a href="javascript:void(0)">暂时没有更多的记录哦</a>');
				    	$('.loading-more').unbind();
				    }
					
					$("#listDiv").show();
					$('#empty').hide();
				}else{
					//内容为空
					$('#listDiv').hide();
					$('#empty').show();
				}
			}
	    },
	    
	    
	    //申请
	    _loadApplyDetail : function(id, data){
	    	if(data.result==Constants.SUCCESS){
	    		var obj = data.result_object;
				//接收人
				wap._editService.setUser(obj.receiveUserId, obj.receiveUserName);
				$("#id").val(obj.id);
				$("#reportType").val(obj.reportType);
	    		$("#startDate").val(obj.startTime);
	    		$("#endDate").val(obj.endTime);
	    		$("#content").html(obj.content);
	    		
	    		wap._editService.initBind();
	    	}	
	    },
	    
	    
	    //申请详情
	    _doLoadDetail : function(dataType, data){
	    	if(data.result==Constants.SUCCESS){
	    		var obj = data.result_object;
	    		var typeStr = '';
				if(WapConstants.REPORT_TYPE_1 == obj.reportType){//周报
					typeStr = '周报';
                }else if(WapConstants.REPORT_TYPE_2 == obj.reportType){//月报
                	typeStr = '月报';
                }
				
				if(dataType == WapConstants.DATA_TYPE_2){
					$("#sendDiv").hide();
					$("#receiveDiv").show();
				}else{
					$("#sendDiv").show();
					$("#receiveDiv").hide();
				}
	    		
	    		$("#title").html(obj.sendUserName+"的工作汇报");
	    		$("#reportType").html(typeStr);
	    		$("#time").html(obj.startTime + "到" + obj.endTime);
	    		$("#unitName").html(obj.unitName);
	    		$("#deptName").html(obj.deptName);
	    		$("#receiveNames").html(obj.receiveUserName);
	    		$("#sendTime").html(obj.sendTime);
	    		$("#content").html(obj.content);
	    	}
	    },
	}   
};