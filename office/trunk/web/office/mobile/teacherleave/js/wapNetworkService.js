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
	
	doGetAuditDetail : function(dataType, data){
		wapNetworkService._service.doGetAuditDetail(dataType, data);
	},
	
	
	//私有方法
	_service : {
		
		_loadList : function(dataType, searchStr, data) {
			if(data.result==Constants.SUCCESS){
				WapPage = data.page;
				//storage.set(WapConstants.WR_SEARCH_STR, searchStr);
				var array = data.result_array;
				var mcodes = '';
				//筛选条件
				if(WapConstants.DATA_TYPE_1 == dataType){
					mcodes = data.leaveTypeMcodes;
					if(mcodes.length > 0){
						storage.set(WapConstants.LEAVE_TYPE_MCODES, JSON.stringify(mcodes));
					}
				}
				wap._listService.initQuerys(dataType);
				if(array.length > 0){
					//列表
					var listHtmlStr = '';
					$.each(array, function(index, json) {
						var state = json.applyStatus;
						var stateClass = '';
						var stateStr = '';
						if(Constants.APPLY_STATUS_2 == state){//审核中
							if(WapConstants.DATA_TYPE_2 == dataType){
								stateClass = 'type-orange';
								stateStr = "待审核";
							}else{
								stateClass = 'type-gray';
								stateStr = "审核中";
							}
						}else if(Constants.APPLY_STATUS_4 == state){//未通过
							stateClass = 'type-red';
							stateStr = "未通过";
						}else if(Constants.APPLY_STATUS_3 == state){//通过
							stateClass = 'type-green';
							stateStr = "通过";
						}else if(Constants.APPLY_STATUS_1 == state){//未提交
							stateClass = 'type-blue';
							stateStr = "未提交";
						}
						
						var htmlInfo = '';
						if(WapConstants.DATA_TYPE_2 == dataType){//审核
							if(Constants.APPLY_STATUS_2 == state){//待审核
								htmlInfo = 'location.href=\'leaveAuditDetail.html?id='+json.id+'&dataType='+dataType+'&taskId='+json.taskId+'\'';
							}else{
								htmlInfo = 'location.href=\'leaveDetail.html?id='+json.id+'&dataType='+dataType+'\'';
							}
						}else{//申请
							if(Constants.APPLY_STATUS_1 == state){//未提交
								htmlInfo = 'location.href=\'leaveApplyEdit.html?id='+json.id+'&dataType='+dataType+'\'';
							}else{
								htmlInfo = 'location.href=\'leaveDetail.html?id='+json.id+'&dataType='+dataType+'\'';
							}
						}
						
                        listHtmlStr += '<li onclick="'+htmlInfo+'">';
                        listHtmlStr += '<span class="type '+stateClass+' f-14">'+stateStr+'</span>';
                        listHtmlStr += '<p class="tt f-18">'+json.content+'</p>';
                        listHtmlStr += '<p class="dd fn-clear f-14">';
                        listHtmlStr += '<span class="dd-1">'+json.leaveTypeName+'</span>';
                        if(WapConstants.DATA_TYPE_2 == dataType){
                        	listHtmlStr += '<span class="dd-3">'+json.applyUserName+'</span>';
                        }
                        listHtmlStr += '<span class="dd-2">'+json.beginTime+'</span>';
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
	    		//请假类型
	    		var leaveTypeMcodes = obj.leaveTypeMcodes;
	    		var leaveType = obj.leaveType;
	    		$("#leaveType").html('');
	    		if(leaveTypeMcodes.length > 0){
	    			storage.set(WapConstants.LEAVE_TYPES, JSON.stringify(leaveTypeMcodes));
	    			var htmlStr = '';
	    			$.each(leaveTypeMcodes, function(index, json) {
	    				if(isNotBlank(leaveType) && json.thisId == leaveType){
	    					htmlStr += '<option value="'+json.thisId+'" selected="selected">'+json.content+'</option>';
	    				}else{
	    					htmlStr += '<option value="'+json.thisId+'">'+json.content+'</option>';
	    				}
	    			});
	    			$("#leaveType").html(htmlStr);
	    		}
	    		
	    		//请假流程
	    		var flowArray = obj.flowArray;
	    		var flowId = obj.flowId;
	    		$("#leaveFlow").html('');
	    		if(flowArray.length > 0){
	    			storage.set(WapConstants.LEAVE_FLOWS, JSON.stringify(flowArray));
	    			var htmlStr = '';
	    			$.each(flowArray, function(index, json) {
	    				if(isNotBlank(flowId) && flowId == json.flowId){
	    					htmlStr += '<option value="'+json.flowId+'" selected="selected">'+json.flowName+'</option>';
	    				}else{
	    					htmlStr += '<option value="'+json.flowId+'">'+json.flowName+'</option>';
	    				}
	    				
	    			});
	    			$("#leaveFlow").html(htmlStr);
	    		}
	    		
	    		wap._editService.initBind();//绑定
	    		$("#id").val(obj.id);
	    		$("#unitId").val(obj.unitId);
	    		$("#deptId").val(obj.deptId);
	    		$("#applyUserId").val(obj.applyUserId);
	    		$("#applyUserName").val(obj.applyUserName);
	    		wap._editService.setUser(WapConstants.ADDRESS_TYPE_1, obj.applyUserId, obj.applyUserName);
		    	if(isNotBlank(id)){//编辑
					$("#startDate").val(obj.leaveBeginTime);
					$("#endDate").val(obj.leaveEndTime);
					$("#leaveDays").val(obj.days);
					$("#remark").val(obj.leaveReason);
					//通知人员
					wap._editService.setUser(WapConstants.ADDRESS_TYPE_2, obj.noticePersonIds, obj.noticePersonNames);
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
	    	}	
	    },
	    
	    
	    //申请详情
	    _doLoadDetail : function(dataType, data){
	    	if(data.result==Constants.SUCCESS){
	    		var obj = data.result_object;
	    		$("#title").html(obj.applyUserName+"的请假");
	    		$("#applyUserName").html(obj.applyUserName);
	    		$("#leaveTypeName").html(obj.leaveTypeName);
	    		$("#startTime").html(obj.leaveBeginTime);
	    		$("#endTime").html(obj.leaveEndTime);
	    		$("#days").html(obj.days);
	    		$("#remark").html(obj.leaveReason);
	    		$("#noticeUserNames").html(obj.noticePersonNames);
	    		
	    		//请假明细
	    		var darray = wap._editService.addLeaveDaysDetailDeatil(obj.leaveBeginTime,obj.leaveEndTime);
	    		var times = obj.times;
	    		var detailStr = "";
	    		if(times!="" && darray!= "" && times.length>0 && darray.length>0){
	    			for(var i = 0; i < times.length; i++){
	    				if(i < darray.length){
	    					if(i == 0){
	    						detailStr = darray[i]+"("+times[i]+"天)";
	    					}else{
	    						detailStr += "," + darray[i]+"("+times[i]+"天)";
	    					}
	    				}
	    			}
	    			$("#detailDays").html(detailStr);
	    			$("#leaveDaysDetail").show();
	    		}else{
	    			$("#leaveDaysDetail").hide();
	    		}
	    		
	    		//审核结果
	    		var applyStatus = obj.applyStatus;
	    		if(Constants.APPLY_STATUS_3 == obj.applyStatus){//通过
	    			$("#auditImg").html('<i class="icon-img icon-state icon-state-yse"></i>');
	    		}else if(Constants.APPLY_STATUS_4 == obj.applyStatus){//未通过
	    			$("#auditImg").html('<i class="icon-img icon-state icon-state-no"></i>');
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
//	    				htmlStr += '<a href="#" class="icon-img icon-download"></a>';
	    				htmlStr += '<p class="acc-tt f-16">'+json.fileName+'</p>';
	    				htmlStr += '<p class="acc-dd">'+fileSize+'</p>';
	    				htmlStr += '</li>';
	    			});
	    			$("#fileDiv").html(htmlStr);
	    		}
	    		
	    		//审核意见
	    		var hisTaskCommentArray= obj.hisTaskCommentArray;
	    		if(hisTaskCommentArray.length > 0){
	    			var htmlStr = ''; 
	    			$.each(hisTaskCommentArray, function(index, json){
	    				htmlStr += '<li>';
	    				htmlStr += '<span class="icon-img icon-right"></span>';
	    				htmlStr += '<div class="item"><i></i>';
	    				htmlStr += '<p class="tt"><span class="time">'+json.operateTime+'</span><span class="f-16">'+json.assigneeName+'</span></p>';
	    				htmlStr += '<p class="dd f-14"><span class="c-blue">'+json.textComment+'</span></p>';
	    				htmlStr += '</div>';
	    				htmlStr += '</li>';
	    			});
	    			$("#auditDiv").html(htmlStr);
	    		}
	    		
	    	}
	    },
	    
	  //审核详情
	    doGetAuditDetail : function(dataType, data){
	    	if(data.result==Constants.SUCCESS){
	    		var obj = data.result_object;
	    		$("#title").html(obj.applyUserName+"的请假");
	    		$("#applyUserName").html(obj.applyUserName);
	    		$("#leaveTypeName").html(obj.leaveTypeName);
	    		$("#startTime").html(obj.leaveBeginTime);
	    		$("#endTime").html(obj.leaveEndTime);
	    		$("#days").html(obj.days);
	    		$("#remark").html(obj.leaveReason);
	    		$("#noticeUserNames").html(obj.noticePersonNames);
	    		
	    		//请假明细
	    		var darray = wap._editService.addLeaveDaysDetailDeatil(obj.leaveBeginTime,obj.leaveEndTime);
	    		var times = obj.times;
	    		var detailStr = "";
	    		if(times!="" && darray!= "" && times.length>0 && darray.length>0){
	    			for(var i = 0; i < times.length; i++){
	    				if(i < darray.length){
	    					if(i == 0){
	    						detailStr = darray[i]+"("+times[i]+"天)";
	    					}else{
	    						detailStr += "," + darray[i]+"("+times[i]+"天)";
	    					}
	    				}
	    			}
	    			$("#detailDays").html(detailStr);
	    			$("#leaveDaysDetail").show();
	    		}else{
	    			$("#leaveDaysDetail").hide();
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
//	    				htmlStr += '<a href="#" class="icon-img icon-download"></a>';
	    				htmlStr += '<p class="acc-tt f-16">'+json.fileName+'</p>';
	    				htmlStr += '<p class="acc-dd">'+fileSize+'</p>';
	    				htmlStr += '</li>';
	    			});
	    			$("#fileDiv").html(htmlStr);
	    		}
	    		//流程信息
	    		$("#id").val(obj.id);
	    		$("#taskHandlerSaveJson").val(JSON.stringify(obj.taskHandlerSaveJson));
	    		
	    		//审核意见
	    		var hisTaskCommentArray= obj.hisTaskCommentArray;
	    		if(hisTaskCommentArray.length > 0){
	    			var htmlStr = ''; 
	    			$.each(hisTaskCommentArray, function(index, json){
	    				htmlStr += '<li>';
	    				htmlStr += '<span class="icon-img icon-right"></span>';
	    				htmlStr += '<div class="item"><i></i>';
	    				htmlStr += '<p class="tt"><span class="time">'+json.operateTime+'</span><span class="f-16">'+json.assigneeName+'</span></p>';
	    				htmlStr += '<p class="dd f-14"><span class="c-blue">'+json.textComment+'</span></p>';
	    				htmlStr += '</div>';
	    				htmlStr += '</li>';
	    			});
	    			$("#auditDiv").html(htmlStr);
	    		}
	    	}
	    },
	    
	}   
};