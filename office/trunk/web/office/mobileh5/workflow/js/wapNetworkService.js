var wapNetworkService = {
		
		addNextAuditUser : function(data){
			this._service._addNextAuditUser(data);
		},
		//首页模块
		mainTabSet : function(data){
			var array = new Array();
			if(isNotBlank(storage.get(WapConstants.OFFICE_MAIN_MODEL))){
				array = JSON.parse(storage.get(WapConstants.OFFICE_MAIN_MODEL));
			}else{
				if (this._service._doError(data)) return;
				if(data.result==Constants.SUCCESS){
					array = data.result_array;
					storage.set(WapConstants.OFFICE_MAIN_MODEL, JSON.stringify(array));
				}
			}
			this._service._mainTabSet(array);
		},
		// 列表信息
		doLoadAuditList : function(dataType, searchStr, data, businessType){
			if (this._service._doError(data)) return;
			wapNetworkService._service.doLoadAuditList(dataType, searchStr, data, businessType);
		},
		
		// 列表信息
		doLoadApplyList : function(searchStr, data, businessType){
			if (this._service._doError(data)) return;
			wapNetworkService._service.doLoadApplyList(searchStr, data, businessType);
		},
		
		doLoadDetail : function(dataType, data, businessType){
			if (this._service._doError(data)) return;
			if(WapConstants.OFFICE_GOODS == businessType){//物品管理
				wapNetworkService._service._doLoadGoodsDetail(dataType, data);
			}else{
				wapNetworkService._service._doLoadDetail(dataType, data, businessType);
			}
		},
		
		doLoadApplyDetail : function(id, data, businessType){
			if (this._service._doError(data)) return;
			if(WapConstants.OFFICE_GO_OUT == businessType){//外出管理
				wapNetworkService._service._loadGooutApplyDetail(id, data);
			}else if(WapConstants.OFFICE_GO_OUT_JT == businessType){//集体外出管理
				wapNetworkService._service._loadGooutJtApplyDetail(id, data);
			}else if(WapConstants.OFFICE_EVECTION == businessType){//出差管理
				wapNetworkService._service._loadEvectionApplyDetail(id, data);
			}else if(WapConstants.OFFICE_EXPENSE == businessType){//报销管理
				wapNetworkService._service._loadExpenseApplyDetail(id, data);
			}else if(WapConstants.OFFICE_GOODS == businessType){//物品管理
				
			}else if(WapConstants.OFFICE_ATTEND_LECTURE == businessType){//听课管理
				wapNetworkService._service._loadAttendLecturetApplyDetail(id, data);
			}else{//请假管理
				wapNetworkService._service._loadLeaveApplyDetail(id, data);
			}
		},
		
		doGetAuditDetail : function(dataType, data, businessType){
			wapNetworkService._service.doGetAuditDetail(dataType, data, businessType);
		},
		
		// 筛选信息
		doLoadGoodsSelectList : function(searchType, applyType, data){
			if (this._service._doError(data)) return;
			wapNetworkService._service._loadGoodsSelectList(searchType, applyType, data);
		},
		
		// 列表信息
		doLoadGoodsApplyList : function(data){
			if (this._service._doError(data)) return;
			wapNetworkService._service._LoadGoodsApplyList(data);
		},
		
		doLoadGoodsApplyDetail : function(unitId,userId,data){
			if (this._service._doError(data)) return;
			wapNetworkService._service._loadGoodsApplyDetail(unitId,userId,data);
		},
		
	_service : {
		
		_addNextAuditUser : function(data){
//			[{"taskName":"1","userArray":[{"assigneeId":"402896C6544B054301544B0AB5380002","assigneeName":"杜拉拉"},{"assigneeId":"402896C654318CE80154319093230002","assigneeName":"钱多多"},{"assigneeId":"67F2098476534F408DEEDECD09C020BE","assigneeName":"杜美美"}]}]
			
			if(data.result == Constants.SUCCESS){
				var array = data.result_array;
				
				if(array.length>0){
					
					var htmlStr = '<li class="mt-20">';
					htmlStr += '<p class="tit">审批人</p>';
					htmlStr += '<ul class="ui-approval ui-form">';
					htmlStr += '<span class="cover-top"></span>';
					htmlStr += '<span class="cover-bottom"></span>';
					$.each(array, function(index, json) {
						var taskName = json.taskName;
						var userArray = json.userArray;
						
						htmlStr += '<li class="ui-approval-item">';
						htmlStr += '<span class="item-name f-10">';
						htmlStr += '<i class="round"></i>'+getLastStr(taskName, 4)+'</span>';//步骤名称
						htmlStr += '<p class="user-list fn-clear f-14 c-333">';
						
						$.each(userArray, function(index, userinfo) {
							var assigneeId = userinfo.assigneeId;
							var assigneeName = userinfo.assigneeName;
							var photoUrl = userinfo.photoUrl;
							
							if(isNotBlank(photoUrl)){
								htmlStr += '<span><img src="'+photoUrl+'" class="place-avatar">'+assigneeName+'</span>';
					    	}else{
					    		htmlStr += '<span><span class="place-avatar" style="background:'+getBackgroundColor(assigneeName)+';">'+getLast2Str(assigneeName)+'</span>'+getLastStr(assigneeName, 4)+'</span>';
					    	}
						});
						
						htmlStr += '</p>';
						htmlStr += '</li>';
					});
					htmlStr += '</ul>';
					htmlStr += '</li>';
					
					$("#flowDetail").html(htmlStr);
					$("#flowDetail").show();
				}else{
					$("#flowDetail").html('');
					$("#flowDetail").hide();
				}
			}else{
				$("#flowDetail").html('');
				$("#flowDetail").hide();
			}
		},
		
		doLoadAuditList : function(dataType, searchStr, data, businessType){
			if(data.result==Constants.SUCCESS){
				WapPage = data.page;
				var array = data.result_array;
				var count = data.count;
				if(WapConstants.DATA_TYPE_1 == dataType){//待审批搜索更新count
					if(count > 99){
						count = '99+';
					}
					$("#num").text("("+count+")");
				}
				if(array.length > 0){
					var listHtmlStr = '';
					$.each(array, function(index, json) {
						var businessType = json.type;
						var title = json.title;
						var subtitle = json.subtitle;
						var dataStr = json.dataStr;
						var businessId = json.businessId;
						var taskId = json.taskId;
						
						var srcStr = '';
						if(WapConstants.OFFICE_GO_OUT == businessType){//外出管理
							srcStr = '../../../static/html/images/icon/app_goout.png';
						}else if(WapConstants.OFFICE_GO_OUT_JT == businessType){//集体外出管理
							srcStr = '../../../static/html/images/icon/app_jtgoout.png';
						}else if(WapConstants.OFFICE_EVECTION == businessType){//出差管理
							srcStr = '../../../static/html/images/icon/app_evection.png';
						}else if(WapConstants.OFFICE_EXPENSE == businessType){//报销管理
							srcStr = '../../../static/html/images/icon/app_apply.png'; 
						}else if(WapConstants.OFFICE_GOODS == businessType){//物品管理
							srcStr = '../../../static/html/images/icon/app_goods.png';
						}else if(WapConstants.OFFICE_ATTEND_LECTURE == businessType){//听课管理
							srcStr = '../../../static/html/images/icon/app_course.png';
						}else if(WapConstants.OFFICE_ATTENDANCE == businessType){//教师考勤-补卡申请
							srcStr = '../../../static/html/images/icon/attendance_audit.png';
						}else{//请假管理
							srcStr = '../../../static/html/images/icon/app_leave.png';
						}
						
						var clickHtmlStr = '';
						if(WapConstants.DATA_TYPE_1 == dataType){//待审核
							if(WapConstants.OFFICE_GO_OUT == businessType){//外出管理
								clickHtmlStr = 'location.href=\'goOutAuditDetail.html?id='+businessId+'&businessType='+businessType+'&dataType='+dataType+'&taskId='+taskId+'\'';
							}else if(WapConstants.OFFICE_GO_OUT_JT == businessType){//集体外出管理
								clickHtmlStr = 'location.href=\'goOutJtAuditDetail.html?id='+businessId+'&businessType='+businessType+'&dataType='+dataType+'&taskId='+taskId+'\'';
							}else if(WapConstants.OFFICE_EVECTION == businessType){//出差管理
								clickHtmlStr = 'location.href=\'evectionAuditDetail.html?id='+businessId+'&businessType='+businessType+'&dataType='+dataType+'&taskId='+taskId+'\'';
							}else if(WapConstants.OFFICE_EXPENSE == businessType){//报销管理
								clickHtmlStr = 'location.href=\'expenseAuditDetail.html?id='+businessId+'&businessType='+businessType+'&dataType='+dataType+'&taskId='+taskId+'\'';
							}else if(WapConstants.OFFICE_GOODS == businessType){//物品管理
								clickHtmlStr = 'location.href=\'goodsManagerDetail.html?id='+businessId+'&dataType='+dataType+'&businessType='+businessType+'\'';
							}else if(WapConstants.OFFICE_ATTEND_LECTURE == businessType){//听课管理
								clickHtmlStr = 'location.href=\'attendLectureAuditDetail.html?id='+businessId+'&businessType='+businessType+'&dataType='+dataType+'&taskId='+taskId+'\'';
							}else if(WapConstants.OFFICE_ATTENDANCE == businessType){//教师考勤-补卡申请
								clickHtmlStr = 'location.href=\'tchAttendanceAuditDetail.html?id='+businessId+'&businessType='+businessType+'&dataType='+dataType+'&taskId='+taskId+'\'';
							}else{//请假管理
								clickHtmlStr = 'location.href=\'leaveAuditDetail.html?id='+businessId+'&businessType='+businessType+'&dataType='+dataType+'&taskId='+taskId+'\'';
							}
						}else{//已审核
							if(WapConstants.OFFICE_GO_OUT == businessType){//外出管理
								clickHtmlStr = 'location.href=\'goOutDetail.html?id='+businessId+'&businessType='+businessType+'&dataType='+dataType+'\'';
							}else if(WapConstants.OFFICE_GO_OUT_JT == businessType){//集体外出管理
								clickHtmlStr = 'location.href=\'goOutjtDetail.html?id='+businessId+'&businessType='+businessType+'&dataType='+dataType+'\'';
							}else if(WapConstants.OFFICE_EVECTION == businessType){//出差管理
								clickHtmlStr = 'location.href=\'evectionDetail.html?id='+businessId+'&businessType='+businessType+'&dataType='+dataType+'\'';
							}else if(WapConstants.OFFICE_EXPENSE == businessType){//报销管理
								clickHtmlStr = 'location.href=\'expenseDetail.html?id='+businessId+'&businessType='+businessType+'&dataType='+dataType+'\'';
							}else if(WapConstants.OFFICE_GOODS == businessType){//物品管理
								clickHtmlStr = 'location.href=\'goodsManagerDetail.html?id='+businessId+'&dataType='+dataType+'&businessType='+businessType+'\'';
							}else if(WapConstants.OFFICE_ATTEND_LECTURE == businessType){//听课管理
								clickHtmlStr = 'location.href=\'attendLectureDetail.html?id='+businessId+'&dataType='+dataType+'&businessType='+businessType+'\'';
							}else if(WapConstants.OFFICE_ATTENDANCE == businessType){//教师考勤-补卡申请
								clickHtmlStr = 'location.href=\'tchAttendanceDetail.html?id='+businessId+'&dataType='+dataType+'&businessType='+businessType+'\'';
							}else{//请假管理
								clickHtmlStr = 'location.href=\'leaveDetail.html?id='+businessId+'&businessType='+businessType+'&dataType='+dataType+'\'';
							}
						}
						
						listHtmlStr += '<a href="javascript:void(0);" onclick="'+clickHtmlStr+'">';
						listHtmlStr += '<img src="'+srcStr+'" alt="">';
						listHtmlStr += '<span class="tt f-15"><span class="time f-11 c-999">'+dataStr+'</span>'+title+'</span>';
						listHtmlStr += '<span class="dd f-13 c-666">'+subtitle+'</span>';
						listHtmlStr += '</a>';
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
			
		doLoadApplyList : function(searchStr, data, businessType){
			if(data.result==Constants.SUCCESS){
				WapPage = data.page;
				var array = data.result_array;
				if(array.length > 0){
					var listHtmlStr = '';
					$.each(array, function(index, json) {
						var businessType = json.type;
						var title = json.title;
						var subtitle = json.subtitle;
						var dataStr = json.dataStr;
						var businessId = json.businessId;
						
						var srcStr = '';
						if(WapConstants.OFFICE_GO_OUT == businessType){//外出管理
							srcStr = '../../../static/html/images/icon/app_goout.png';
						}else if(WapConstants.OFFICE_GO_OUT_JT == businessType){//集体外出管理
							srcStr = '../../../static/html/images/icon/app_jtgoout.png';
						}else if(WapConstants.OFFICE_EVECTION == businessType){//出差管理
							srcStr = '../../../static/html/images/icon/app_evection.png';
						}else if(WapConstants.OFFICE_EXPENSE == businessType){//报销管理
							srcStr = '../../../static/html/images/icon/app_apply.png'; 
						}else if(WapConstants.OFFICE_GOODS == businessType){//物品管理
							srcStr = '../../../static/html/images/icon/app_goods.png';
						}else if(WapConstants.OFFICE_ATTENDANCE == businessType){//教师考勤-补卡申请
							srcStr = '../../../static/html/images/icon/attendance_audit.png';
						}else if(WapConstants.OFFICE_ATTEND_LECTURE == businessType){//听课管理
							srcStr = '../../../static/html/images/icon/app_course.png';
						}else{//请假管理
							srcStr = '../../../static/html/images/icon/app_leave.png';
						}
						
						var clickHtmlStr = '';
						var dataType = WapConstants.DATA_TYPE_0;
						if(WapConstants.OFFICE_GO_OUT == businessType){//外出管理
							clickHtmlStr = 'location.href=\'goOutDetail.html?id='+businessId+'&businessType='+businessType+'&dataType='+dataType+'\'';
						}else if(WapConstants.OFFICE_GO_OUT_JT == businessType){//集体外出管理
							clickHtmlStr = 'location.href=\'goOutjtDetail.html?id='+businessId+'&businessType='+businessType+'&dataType='+dataType+'\'';
						}else if(WapConstants.OFFICE_EVECTION == businessType){//出差管理
							clickHtmlStr = 'location.href=\'evectionDetail.html?id='+businessId+'&businessType='+businessType+'&dataType='+dataType+'\'';
						}else if(WapConstants.OFFICE_EXPENSE == businessType){//报销管理
							clickHtmlStr = 'location.href=\'expenseDetail.html?id='+businessId+'&businessType='+businessType+'&dataType='+dataType+'\'';
						}else if(WapConstants.OFFICE_GOODS == businessType){//物品管理
							clickHtmlStr = 'location.href=\'goodsManagerDetail.html?id='+businessId+'&businessType='+businessType+'&dataType='+dataType+'\'';
						}else if(WapConstants.OFFICE_ATTENDANCE == businessType){//教师考勤-补卡申请
							clickHtmlStr = 'location.href=\'tchAttendanceDetail.html?id='+businessId+'&businessType='+businessType+'&dataType='+dataType+'\'';
						}else if(WapConstants.OFFICE_ATTEND_LECTURE == businessType){//听课管理
							clickHtmlStr = 'location.href=\'attendLectureDetail.html?id='+businessId+'&businessType='+businessType+'&dataType='+dataType+'\'';
						}else{//请假管理
							clickHtmlStr = 'location.href=\'leaveDetail.html?id='+businessId+'&businessType='+businessType+'&dataType='+dataType+'\'';
						}
						
						listHtmlStr += '<a href="javascript:void(0);" onclick="'+clickHtmlStr+'">';
						listHtmlStr += '<img src="'+srcStr+'" alt="">';
						listHtmlStr += '<span class="tt f-15"><span class="time f-11 c-999">'+dataStr+'</span>'+title+'</span>';
						listHtmlStr += '<span class="dd f-13 c-666">'+subtitle+'</span>';
						listHtmlStr += '</a>';
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
		
		//详情
	    _doLoadDetail : function(dataType, data, businessType){
	    	if(data.result==Constants.SUCCESS){
	    		var obj = data.result_object;
	    		var titleStr = '';
	    		var _contextPath = storage.get(Constants.MOBILE_CONTEXT_PATH);
	    		var userPhotoStr = this._getDefaultPhotoUrl(obj.photoUrl,obj.applyUserName)+'<span class="f-18">'+obj.applyUserName+'</span>';
	    		$("#userPhotoImg").html(userPhotoStr);
//	    		$("#photoImg").attr("src", this._getDefaultPhotoUrl(obj.photoUrl));
//	    		$("#applyUserName").html(obj.applyUserName);
	    		$("#deptName").html(obj.deptName);
	    		if(WapConstants.OFFICE_GO_OUT == businessType){//外出管理
	    			titleStr = obj.applyUserName+"的外出";
	    			$("#title").html(obj.applyUserName+"的外出");
		    		$("#startTime").html(obj.beginTime);
		    		$("#endTime").html(obj.endTime);
		    		if(validateInteger(obj.hours)){
		    			$("#days").html(obj.hours+'.0');
		    		}else{
		    			$("#days").html(obj.hours);
		    		}
		    		$("#type").html(obj.typeStr);
		    		$("#remark").html(obj.tripReason);
				}else if(WapConstants.OFFICE_GO_OUT_JT == businessType){//集体外出管理
					titleStr = obj.applyUserName+"的集体外出";
					$("#title").html(obj.applyUserName+"的集体外出");
					$("#startTime").html(obj.beginTime);
					$("#endTime").html(obj.endTime);
					var goOutJtType=obj.goOutJtType;
					if(goOutJtType=="1"){
						$("#teacherEx").hide();
						
						$("#goOutJtType").html("学生集体活动");
						$("#organize").html(obj.organize);
						$("#activityNumber").html(obj.activityNumber);
						$("#place").html(obj.place);
						$("#scontent").html(obj.scontent);
						$("#vehicle").html(obj.vehicle);
						$("#isDrivinglicence").html(obj.isDrivinglicenceStr);
						var isOrganizationStr=obj.isOrganizationStr;
						$("#isOrganization").html(isOrganizationStr);
						if(isOrganizationStr=="是"){
							$("#traveUnit").html(obj.traveUnit);
							$("#travelinkPerson").html(obj.traveLinkPerson);
							$("#travelinkPhone").html(obj.traveLinkPhone);
						}else{
							$("#organizationDiv").hide();
						}
						$("#isInsurance").html(obj.isInsuranceStr);
						$("#activityleaderName").html(obj.activityLeaderName);
						$("#activityleaderPhone").html(obj.activityLeaderPhone);
						$("#leadGroupName").html(obj.leadGroupName);
						$("#leadGroupPhone").html(obj.leadGroupPhone);
						$("#otherTeacherNames").html(obj.otherTeacherNames);
						$("#activityIdeal").html(obj.activityIdealStr);
						$("#saftIdeal").html(obj.saftIdealStr);
					}else{
						$("#studentEx").hide();
						$("#goOutJtType").html("教师集体培训");
						$("#tcontent").html(obj.tcontent);
						$("#partakePersonNames").html(obj.partakePersonNames);
					}	
				}else if(WapConstants.OFFICE_EVECTION == businessType){//出差管理
					titleStr = obj.applyUserName+"的出差";
					$("#title").html(obj.applyUserName+"的出差");
		    		$("#place").html(obj.place);
		    		$("#startTime").html(obj.beginTime);
		    		$("#endTime").html(obj.endTime);
		    		$("#days").html(obj.days);
		    		$("#remark").html(obj.tripReason);
				}else if(WapConstants.OFFICE_EXPENSE == businessType){//报销管理
					titleStr = obj.applyUserName+"的报销";
					$("#title").html(obj.applyUserName+"的报销");
					/*var expenseMoney=obj.expenseMoney.toString();
		    		if(isNotBlank(expenseMoney)){
		    			var moneys=expenseMoney.split(".");
		    			if(moneys.length==1){
		    				expenseMoney=expenseMoney+'.00';
		    			}else if(moneys.length>1){
		    				if(moneys[1].length<2){
		    					expenseMoney=expenseMoney+'.0';
		    				}
		    			}
		    		}*/
					$("#expenseMoney").html(obj.expenseMoney);
		    		$("#expenseType").html(obj.expenseType);
		    		$("#detail").html(obj.detail);
				}else if(WapConstants.OFFICE_ATTENDANCE == businessType){//教师考勤-补卡申请
					titleStr = obj.applyUserName+"的补卡申请";
					$("#title").html(obj.applyUserName+"的补卡申请");
					$("#attenceDateType").html(obj.attenceDateType);
					$("#clockReason").html(obj.reason);
				}else if(WapConstants.OFFICE_ATTEND_LECTURE == businessType){//听课管理
					titleStr = obj.applyUserName+"的听课";
					$("#title").html(obj.applyUserName+"的听课");
					$("#subjectName").html(obj.subjectName);
					$("#projectName").html(obj.projectName);
					$("#attendDate").html(obj.attendDate);
					$("#attendPeriod").html(obj.attendPeriod);
					$("#gradeName").html(obj.gradeName);
					$("#className").html(obj.className);
					$("#teacherName").html(obj.teacherName);
					$("#projectContent").html(obj.projectContent);
					$("#projectOpinion").html(obj.projectOpinion);
				}else if(WapConstants.OFFICE_GOODS == businessType){//物品管理
					
				}else{//请假管理
					titleStr = obj.applyUserName+"的请假";
					$("#title").html(obj.applyUserName+"的请假");
		    		$("#leaveTypeName").html(obj.leaveTypeName);
		    		$("#startTime").html(obj.leaveBeginTime);
		    		$("#endTime").html(obj.leaveEndTime);
		    		$("#days").html(obj.days);
		    		$("#remark").html(obj.leaveReason);
		    		$("#noticeUserNames").html(obj.noticePersonNames);
		    		
		    		//请假明细
		    		var darray = wap._applyService._LeaveService.addLeaveDaysDetailDeatil(obj.leaveBeginTime,obj.leaveEndTime);
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
				}
	    		$("title").text(titleStr);
	    		
	    		//审核结果
	    		var applyStatus = obj.applyStatus;
	    		if(Constants.APPLY_STATUS_3 == obj.applyStatus){//通过
	    			$("#auditImg").html('<i class="icon-img icon-state icon-state-abs icon-state-yse"></i>');
	    		}else if(Constants.APPLY_STATUS_4 == obj.applyStatus){//未通过
	    			$("#auditImg").html('<i class="icon-img icon-state icon-state-abs icon-state-no"></i>');
	    		}else{
	    			if(dataType!=2)//我已审批的不显示
	    			$("#delete").show();
	    		}
	    		
	    		//附件
	    		var attachmentArray = obj.attachmentArray;
	    		if(attachmentArray!=null && attachmentArray.length > 0){
	    			var htmlStr = ''; 
	    			$.each(attachmentArray, function(index, json){
	    				var fileSize = getFileSize(json.fileSize);
	    				htmlStr += '<li class="fn-clear">';
	    				htmlStr += getFilePic(json.extName);
	    				
	    				if(json.fileExist){
	    					if(isWeiXin()&&isAndroid()){
	    						var downloadPath = _contextPath+"/common/open/officeh5/downloadAtt.action?path="+json.downloadPath;
	    						htmlStr+='<a href="'+downloadPath+'" class="icon-img icon-download"></a>';
	    					}else{
	    						htmlStr+='<a href="'+json.downloadPath+'" class="icon-img icon-download"></a>';
	    					}
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
	    				htmlStr += wapNetworkService._service._getDefaultPhotoUrl(json.photoUrl,json.assigneeName);
	    				htmlStr += '<p class="tt"><span class="time">'+json.operateTime+'</span><span class="f-16">'+json.assigneeName+'</span></p>';
	    				htmlStr += '<p class="dd f-14"><span class="c-blue">'+json.textComment+'</span></p>';
	    				htmlStr += '</div>';
	    				htmlStr += '</li>';
	    			});
	    			$("#auditDiv").html(htmlStr);
	    		}
	    		
	    	}
	    },
	    //集体外出申请
	    _loadGooutJtApplyDetail:function(id,data){
	    	if(data.result==Constants.SUCCESS){
	    		var obj = data.result_object;
	    		
	    		var flowArray = obj.flowArray;
	    		var flowId = obj.flowId;
	    		$("#gooutJtFlow").html('');
	    		if(flowArray.length > 0){
	    			var htmlStr = '';
	    			storage.set(WapConstants.JT_FLOWS, JSON.stringify(flowArray));
	    			if(isNotBlank(flowId)){
	    				$.each(flowArray, function(index, json) {
		    				if(flowId == json.flowId){
		    					htmlStr += '<option value="'+json.flowId+'" selected="selected">'+json.flowName+'</option>';
		    				}else{
		    					htmlStr += '<option value="'+json.flowId+'">'+json.flowName+'</option>';
		    				}
		    			});
	    			}else{
	    				$.each(flowArray, function(index, json) {
		    				if(json.isDefaultFlow){
		    					htmlStr += '<option value="'+json.flowId+'" selected="selected">'+json.flowName+'</option>';
		    				}else{
		    					htmlStr += '<option value="'+json.flowId+'">'+json.flowName+'</option>';
		    				}
		    				
		    			});
	    			}
	    			$("#gooutJtFlow").html(htmlStr);
	    		}
	    		//类型
	    		var typeArray = obj.typeArray;
	    		var type = obj.type;
	    		$("#goOutJtType").html('');
	    		if(typeArray.length > 0){
	    			storage.set(WapConstants.JT_TYPES, JSON.stringify(typeArray));
	    			var htmlStr = '';
	    			$.each(typeArray, function(index, json) {
	    				if(isNotBlank(type) && type == json.type){
	    					htmlStr += '<option value="'+json.type+'" selected="selected">'+json.typeName+'</option>';
	    				}else{
	    					htmlStr += '<option value="'+json.type+'">'+json.typeName+'</option>';
	    				}
	    				
	    			});
	    			$("#goOutJtType").html(htmlStr);
	    		}
	    		//营业证
	    		var isDrivinglicence=obj.isDrivinglicence;
	    		$("#isDrivinglicence").html('');
	    		var htmlLicence = ' <option value="false" ';
    			if(isNotBlank(isDrivinglicence) && isDrivinglicence==0){
    				htmlLicence += ' selected="selected" ';
    			}
    			htmlLicence += ' >否</option> <option value="true" ';
    			if(isNotBlank(isDrivinglicence) && isDrivinglicence==1){
    				htmlLicence += ' selected="selected" ';
    			}
    			htmlLicence +='>是</option>';
    			$("#isDrivinglicence").html(htmlLicence);
    			
	    		//是否由旅行社组织
	    		var isOrganization=obj.isOrganization;
	    		$("#isOrganization").html('');
	    		var htmlOr = ' <option value="false" ';
    			if(isNotBlank(isOrganization) && isOrganization=="0"){
    				htmlOr += ' selected="selected" ';
    			}
    			htmlOr += ' >否</option> <option value="true" ';
    			if(isNotBlank(isOrganization) && isOrganization=="1"){
    				htmlOr += ' selected="selected" ';
    			}
    			htmlOr +='>是</option>';
    			$("#isOrganization").html(htmlOr);
    			
	    		//是否购买人身保险和意外伤害保险：
	    		var isInsurance=obj.isInsurance;
	    		$("#isInsurance").html('');
	    		var htmlIn = ' <option value="false" ';
    			if(isNotBlank(isInsurance) && isInsurance=="0"){
    				htmlIn += ' selected="selected" ';
    			}
    			htmlIn += ' >否</option> <option value="true" ';
    			if(isNotBlank(isInsurance) && isInsurance=="1"){
    				htmlIn += ' selected="selected" ';
    			}
    			htmlIn +='>是</option>';
    			$("#isInsurance").html(htmlIn);
    			
	    		//活动方案
	    		var activityIdeal=obj.activityIdeal;
	    		$("#activityIdeal").html('');
	    		var htmlAc = ' <option value="false" ';
    			if(isNotBlank(activityIdeal) && activityIdeal=="0"){
    				htmlAc += ' selected="selected" ';
    			}
    			htmlAc += ' >否</option> <option value="true" ';
    			if(isNotBlank(activityIdeal) && activityIdeal=="1"){
    				htmlAc += ' selected="selected" ';
    			}
    			htmlAc +='>是</option>';
    			$("#activityIdeal").html(htmlAc);
    			
	    		//安全方案
	    		var saftIdeal=obj.saftIdeal;
	    		$("#saftIdeal").html('');
	    		var htmlSa = ' <option value="false" ';
    			if(isNotBlank(saftIdeal) && saftIdeal=="0"){
    				htmlSa += ' selected="selected" ';
    			}
    			htmlSa += ' >否</option> <option value="true" ';
    			if(isNotBlank(saftIdeal) && saftIdeal=="1"){
    				htmlSa += ' selected="selected" ';
    			}
    			htmlSa +='>是</option>';
    			$("#saftIdeal").html(htmlSa);
    			
    			
    			wap._applyService._gooutJtService.initBind();//绑定//绑定
	    		$("#id").val(obj.id);
	    		$("#unitId").val(obj.unitId);
	    		$("#applyUserId").val(obj.applyUserId);
	    		$("#createTime").val(obj.createTime);
    			if(isNotBlank(id)){//编辑
					$("#startDate").val(obj.beginTime);
					$("#endDate").val(obj.endTime);
					//anyone to do
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
	   //外出申请
	    _loadGooutApplyDetail : function(id, data){
	    	if(data.result==Constants.SUCCESS){
	    		var obj = data.result_object;
	    		//流程
	    		var flowArray = obj.flowArray;
	    		var flowId = obj.flowId;
	    		$("#leaveFlow").html('');
	    		if(flowArray.length > 0){
	    			var htmlStr = '';
	    			if(isNotBlank(flowId)){
	    				$.each(flowArray, function(index, json) {
		    				if(flowId == json.flowId){
		    					htmlStr += '<option value="'+json.flowId+'" selected="selected">'+json.flowName+'</option>';
		    				}else{
		    					htmlStr += '<option value="'+json.flowId+'">'+json.flowName+'</option>';
		    				}
		    			});
	    			}else{
	    				$.each(flowArray, function(index, json) {
		    				if(json.isDefaultFlow){
		    					htmlStr += '<option value="'+json.flowId+'" selected="selected">'+json.flowName+'</option>';
		    				}else{
		    					htmlStr += '<option value="'+json.flowId+'">'+json.flowName+'</option>';
		    				}
		    				
		    			});
	    			}
	    			
	    			$("#leaveFlow").html(htmlStr);
	    		}
	    		//类型
	    		var typeArray = obj.typeArray;
	    		var type = obj.type;
	    		$("#goOutType").html('');
	    		if(typeArray.length > 0){
	    			var htmlStr = '';
	    			$.each(typeArray, function(index, json) {
	    				if(isNotBlank(type) && type == json.type){
	    					htmlStr += '<option value="'+json.type+'" selected="selected">'+json.typeName+'</option>';
	    				}else{
	    					htmlStr += '<option value="'+json.type+'">'+json.typeName+'</option>';
	    				}
	    				
	    			});
	    			$("#goOutType").html(htmlStr);
	    		}
	    		wap._applyService._gooutService.initBind();//绑定//绑定
	    		$("#id").val(obj.id);
	    		$("#unitId").val(obj.unitId);
	    		$("#applyUserId").val(obj.applyUserId);
//	    		$("#applyUserName").val(obj.applyUserName);
		    	if(isNotBlank(id)){//编辑
					$("#startDate").val(obj.beginTime);
					$("#endDate").val(obj.endTime);
					$("#goOutDays").val(obj.hours);
					$("#remark").val(obj.tripReason);
					
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
	    
	    //听课申请
	    _loadAttendLecturetApplyDetail : function(id, data){
	    	if(data.result==Constants.SUCCESS){
	    		var obj = data.result_object;
	    		//流程
	    		var flowArray = obj.flowArray;
	    		var flowId = obj.flowId;
	    		$("#attendLectureFlow").html('');
	    		if(flowArray.length > 0){
	    			var htmlStr = '';
//	    			$.each(flowArray, function(index, json) {
//	    				if(isNotBlank(flowId) && flowId == json.flowId){
//	    					htmlStr += '<option value="'+json.flowId+'" selected="selected">'+json.flowName+'</option>';
//	    				}else{
//	    					htmlStr += '<option value="'+json.flowId+'">'+json.flowName+'</option>';
//	    				}
//	    				
//	    			});
	    			if(isNotBlank(flowId)){
	    				$.each(flowArray, function(index, json) {
		    				if(flowId == json.flowId){
		    					htmlStr += '<option value="'+json.flowId+'" selected="selected">'+json.flowName+'</option>';
		    				}else{
		    					htmlStr += '<option value="'+json.flowId+'">'+json.flowName+'</option>';
		    				}
		    			});
	    			}else{
	    				$.each(flowArray, function(index, json) {
		    				if(json.isDefaultFlow){
		    					htmlStr += '<option value="'+json.flowId+'" selected="selected">'+json.flowName+'</option>';
		    				}else{
		    					htmlStr += '<option value="'+json.flowId+'">'+json.flowName+'</option>';
		    				}
		    				
		    			});
	    			}
	    			$("#attendLectureFlow").html(htmlStr);
	    		}
	    		
	    		//上、下午
	    		var attendPeriodMcodes = obj.attendPeriodMcodes;
	    		var attendPeriod = obj.attendPeriod;
	    		$("#attendPeriod").html('');
	    		if(attendPeriodMcodes.length > 0){
	    			var htmlStr = '';
	    			$.each(attendPeriodMcodes, function(index, json) {
	    				if(isNotBlank(attendPeriod) && json.thisId == attendPeriod){
	    					htmlStr += '<option value="'+json.thisId+'" selected="selected">'+json.content+'</option>';
	    				}else{
	    					htmlStr += '<option value="'+json.thisId+'">'+json.content+'</option>';
	    				}
	    			});
	    			$("#attendPeriod").html(htmlStr);
	    		}
	    		//节次
	    		var attendPeriodNumMcodes = obj.attendPeriodNumMcodes;
	    		var attendPeriodNum = obj.attendPeriodNum;
	    		$("#attendPeriodNum").html('');
	    		if(attendPeriodMcodes.length > 0){
	    			var htmlStr = '';
	    			$.each(attendPeriodNumMcodes, function(index, json) {
	    				if(isNotBlank(attendPeriodNum) && json.thisId == attendPeriodNum){
	    					htmlStr += '<option value="'+json.thisId+'" selected="selected">'+json.content+'</option>';
	    				}else{
	    					htmlStr += '<option value="'+json.thisId+'">'+json.content+'</option>';
	    				}
	    			});
	    			$("#attendPeriodNum").html(htmlStr);
	    		}
	    		//年级
	    		var gradeArray = obj.gradeArray;
	    		var gradeId = obj.gradeId;
	    		$("#gradeId").html('');
	    		if(gradeArray.length > 0){
	    			var htmlStr = '';
	    			$.each(gradeArray, function(index, json) {
	    				if(isNotBlank(gradeId) && gradeId == json.gradeId){
	    					htmlStr += '<option value="'+json.gradeId+'" selected="selected">'+json.gradeName+'</option>';
	    				}else{
	    					htmlStr += '<option value="'+json.gradeId+'">'+json.gradeName+'</option>';
	    				}
	    				
	    			});
	    			$("#gradeId").html(htmlStr);
	    		}
	    		//班级
	    		var classArray = obj.classArray;
	    		var classId = obj.classId;
	    		storage.set(WapConstants.OFFICE_CLASS_ARRAY, JSON.stringify(classArray));
	    		$("#classId").html('');
	    		if(classArray.length > 0){
	    			var gradeId = $("#gradeId").val();
	    			var classes = new Array();;
	    			$.each(classArray, function(index, json) {
	    				if(isNotBlank(gradeId) && gradeId == json.gradeId){
	    					classes.push(json);
	    				}
	    				
	    			});
	    			var htmlStr = '';
	    			$.each(classes, function(index, json) {
	    				if(isNotBlank(classId) && classId == json.classId){
	    					htmlStr += '<option value="'+json.classId+'" selected="selected">'+json.className+'</option>';
	    				}else{
	    					htmlStr += '<option value="'+json.classId+'">'+json.className+'</option>';
	    				}
	    				
	    			});
	    			$("#classId").html(htmlStr);
	    		}
	    		wap._applyService._attendLectureService.initBind();//绑定
	    		$("#id").val(obj.id);
	    		$("#unitId").val(obj.unitId);
	    		$("#applyUserId").val(obj.applyUserId);
	    		
	    		if(isNotBlank(id)){//编辑
	    			$("#attendDate").val(obj.attendDate);
	    			$("#subjectName").val(obj.subjectName);
	    			$("#projectName").val(obj.projectName);
	    			$("#teacherName").val(obj.teacherName);
	    			$("#projectContent").val(obj.projectContent);
	    			$("#projectOpinion").val(obj.projectOpinion);
	    		}
	    	}	
	    },
	    
	    //出差申请
	    _loadEvectionApplyDetail : function(id, data){
	    	if(data.result==Constants.SUCCESS){
	    		var obj = data.result_object;
	    		//流程
	    		var flowArray = obj.flowArray;
	    		var flowId = obj.flowId;
	    		$("#leaveFlow").html('');
	    		if(flowArray.length > 0){
	    			var htmlStr = '';
//	    			$.each(flowArray, function(index, json) {
//	    				if(isNotBlank(flowId) && flowId == json.flowId){
//	    					htmlStr += '<option value="'+json.flowId+'" selected="selected">'+json.flowName+'</option>';
//	    				}else{
//	    					htmlStr += '<option value="'+json.flowId+'">'+json.flowName+'</option>';
//	    				}
//	    				
//	    			});
	    			if(isNotBlank(flowId)){
	    				$.each(flowArray, function(index, json) {
		    				if(flowId == json.flowId){
		    					htmlStr += '<option value="'+json.flowId+'" selected="selected">'+json.flowName+'</option>';
		    				}else{
		    					htmlStr += '<option value="'+json.flowId+'">'+json.flowName+'</option>';
		    				}
		    			});
	    			}else{
	    				$.each(flowArray, function(index, json) {
		    				if(json.isDefaultFlow){
		    					htmlStr += '<option value="'+json.flowId+'" selected="selected">'+json.flowName+'</option>';
		    				}else{
		    					htmlStr += '<option value="'+json.flowId+'">'+json.flowName+'</option>';
		    				}
		    				
		    			});
	    			}
	    			$("#leaveFlow").html(htmlStr);
	    		}
	    		
	    		wap._applyService._evectionService.initBind();//绑定
	    		$("#id").val(obj.id);
	    		$("#unitId").val(obj.unitId);
	    		$("#applyUserId").val(obj.applyUserId);
//	    		$("#applyUserName").val(obj.applyUserName);
		    	if(isNotBlank(id)){//编辑
		    		$("#createTime").val(obj.createTime);
		    		$("#place").val(obj.place);
					$("#startDate").val(obj.beginTime);
					$("#endDate").val(obj.endTime);
					$("#goOutDays").val(obj.days);
					$("#remark").val(obj.tripReason);
					
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
	    
	  //报销申请
	    _loadExpenseApplyDetail : function(id, data){
	    	if(data.result==Constants.SUCCESS){
	    		var obj = data.result_object;
	    		
	    		//流程
	    		var flowArray = obj.flowArray;
	    		var flowId = obj.flowId;
	    		$("#expenseFlow").html('');
	    		if(flowArray.length > 0){
	    			var htmlStr = '';
//	    			$.each(flowArray, function(index, json) {
//	    				if(isNotBlank(flowId) && flowId == json.flowId){
//	    					htmlStr += '<option value="'+json.flowId+'" selected="selected">'+json.flowName+'</option>';
//	    				}else{
//	    					htmlStr += '<option value="'+json.flowId+'">'+json.flowName+'</option>';
//	    				}
//	    				
//	    			});
	    			if(isNotBlank(flowId)){
	    				$.each(flowArray, function(index, json) {
		    				if(flowId == json.flowId){
		    					htmlStr += '<option value="'+json.flowId+'" selected="selected">'+json.flowName+'</option>';
		    				}else{
		    					htmlStr += '<option value="'+json.flowId+'">'+json.flowName+'</option>';
		    				}
		    			});
	    			}else{
	    				$.each(flowArray, function(index, json) {
		    				if(json.isDefaultFlow){
		    					htmlStr += '<option value="'+json.flowId+'" selected="selected">'+json.flowName+'</option>';
		    				}else{
		    					htmlStr += '<option value="'+json.flowId+'">'+json.flowName+'</option>';
		    				}
		    				
		    			});
	    			}
	    			$("#expenseFlow").html(htmlStr);
	    		}
	    		
	    		
	    		wap._applyService._expenseService.initBind();//绑定
	    		$("#id").val(obj.id);
	    		$("#unitId").val(obj.unitId);
	    		$("#applyUserId").val(obj.applyUserId);
		    	if(isNotBlank(id)){//编辑
					$("#expenseMoney").val(obj.expenseMoney);
					$("#expenseType").val(obj.expenseType);
					$("#detail").val(obj.detail);
				}
		    	var attachmentArray = obj.attachmentArray;
	    		if(attachmentArray.length > 0){
	    			var htmlStr = ''; 
	    			$.each(attachmentArray, function(index, json){
	    				$("#fileName").val(json.fileName);
	    			});
	    		}
	    	}	
	    },
	    
	    //请假申请
	    _loadLeaveApplyDetail : function(id, data){
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
//	    			$.each(flowArray, function(index, json) {
//	    				if(isNotBlank(flowId) && flowId == json.flowId){
//	    					htmlStr += '<option value="'+json.flowId+'" selected="selected">'+json.flowName+'</option>';
//	    				}else{
//	    					htmlStr += '<option value="'+json.flowId+'">'+json.flowName+'</option>';
//	    				}
//	    				
//	    			});
	    			if(isNotBlank(flowId)){
	    				$.each(flowArray, function(index, json) {
		    				if(flowId == json.flowId){
		    					htmlStr += '<option value="'+json.flowId+'" selected="selected">'+json.flowName+'</option>';
		    				}else{
		    					htmlStr += '<option value="'+json.flowId+'">'+json.flowName+'</option>';
		    				}
		    			});
	    			}else{
	    				$.each(flowArray, function(index, json) {
		    				if(json.isDefaultFlow){
		    					htmlStr += '<option value="'+json.flowId+'" selected="selected">'+json.flowName+'</option>';
		    				}else{
		    					htmlStr += '<option value="'+json.flowId+'">'+json.flowName+'</option>';
		    				}
		    			});
	    			}
	    			$("#leaveFlow").html(htmlStr);
	    		}
	    		
	    		wap._applyService._LeaveService.initBind();//绑定
	    		$("#id").val(obj.id);
	    		$("#unitId").val(obj.unitId);
	    		$("#deptId").val(obj.deptId);
	    		$("#applyUserId").val(obj.applyUserId);
	    		$("#applyUserName").val(obj.applyUserName);
	    		wap._applyService._LeaveService.setUser(WapConstants.ADDRESS_TYPE_1, obj.applyUserId, obj.applyUserName);
		    	if(isNotBlank(id)){//编辑
					$("#startDate").val(obj.leaveBeginTime);
					$("#endDate").val(obj.leaveEndTime);
					$("#leaveDays").val(obj.days);
					$("#remark").val(obj.leaveReason);
					//通知人员
					wap._applyService._LeaveService.setUser(WapConstants.ADDRESS_TYPE_2, obj.noticePersonIds, obj.noticePersonNames);
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
	    
	    //物品审核页面
	    _doLoadGoodsDetail : function(dataType, data){
	    	var result=data.result_object;
	    	
	    	$("title").text(result.reqUserName+"的物品领用");
	    	
	    	var userPhotoStr = this._getDefaultPhotoUrl(result.photoUrl,result.reqUserName)+'<span class="f-18">'+result.reqUserName+'</span>';
	    	$("#userPhotoImg").html(userPhotoStr);
//	    	$("#photoImg").attr("src", this._getDefaultPhotoUrl(result.photoUrl));
//	    	$('#applyUserName').text(result.reqUserName);
	    	$('#deptName').html(result.deptName);
	    	$('#name').html(result.name);
	    	$('#typeName').html(result.typeName);
	    	$('#price').html(result.price);
	    	$('#model').html(result.model);
	    	$('#amount').html(result.amount);
	    	$('#reqRemark').html(result.reqRemark);
	    	$('#remark').html(result.remark);
	    	if(result.state == WapConstants.GOODS_AUDIT_PASS){
	    		$('.icon-state-yse').show();
	    	}else if(result.state == WapConstants.GOODS_AUDIT_NOT_PASS){
	    		$('.icon-state-no').show();
	    		var htmlStr='<ul class="ui-detail mt-20 f-14"><li class="fn-clear"><span class="tt">审核意见：</span>';
	    		htmlStr+='<span class="dd">'+result.advice+'</span>';
	    		htmlStr+='</li></ul>';
	    		$("#container").append(htmlStr);
	    	}
	    	
	    	if(WapConstants.DATA_TYPE_1 == dataType){//待审核
	    		$("#auditDivId").show();
	    		$("#passAudit").on('touchstart',function(){
	    			if(!isActionable()){
						return false;
					}
	    			var passContent=$("#passReason").val();
	    			wapNetwork.doGoodsAudit(storage.get(Constants.USER_ID),result.id,WapConstants.GOODS_AUDIT_PASS, passContent, dataType);
	    		});
	    		
	    		$("#failAudit").on('touchstart',function(){
	    			if(!isActionable()){
						return false;
					}
	    			var nPassContent=$("#failReason").val();
	    			if(nPassContent==''){
	    				viewToast('审核意见不能为空');
						return;
					}
					if(getStringLen(nPassContent) > 1000){
						viewToast('审核意见不能超过1000字符');
						return;
					}
					wapNetwork.doGoodsAudit(storage.get(Constants.USER_ID),result.id,WapConstants.GOODS_AUDIT_NOT_PASS,nPassContent,dataType);
	    		});
	    	}else{
	    		
	    	}
	    	
	    	if(dataType == WapConstants.DATA_TYPE_1 && result.state == WapConstants.GOODS_AUDIT_PASS && !result.isReturned){
//	    		$("#giveBackDivId").show();
//	    		$("#giveBack").click(function(){
//	    			if(!isActionable()){
//						return false;
//					}
//	    			showMsg("确定归还？",function(){
//						wapNetwork.doGiveBack(storage.get(Constants.USER_ID),result.id);
//					});
//	    		});
	    	}else if(dataType == WapConstants.DATA_TYPE_0  && result.state == WapConstants.GOODS_NOT_AUDIT){
	    		
	    	}
	    },
	    
	    
	  //审核详情
	    doGetAuditDetail : function(dataType, data, businessType){
	    	if(data.result==Constants.SUCCESS){
	    		var obj = data.result_object;
	    		var titleStr = '';
	    		var _contextPath = storage.get(Constants.MOBILE_CONTEXT_PATH);
	    		var userPhotoStr = this._getDefaultPhotoUrl(obj.photoUrl,obj.applyUserName)+'<span class="f-18">'+obj.applyUserName+'</span>';
	    		$("#userPhotoImg").html(userPhotoStr);
//	    		$("#photoImg").attr("src", this._getDefaultPhotoUrl(obj.photoUrl));
//	    		$("#applyUserName").html(obj.applyUserName);
	    		$("#deptName").html(obj.deptName);
	    		if(WapConstants.OFFICE_GO_OUT == businessType){//外出管理
	    			titleStr = obj.applyUserName+"的外出";
	    			$("#title").html(obj.applyUserName+"的外出");
		    		$("#startTime").html(obj.beginTime);
		    		$("#endTime").html(obj.endTime);
		    		if(validateInteger(obj.hours)){
		    			$("#days").html(obj.hours+'.0');
		    		}else{
		    			$("#days").html(obj.hours);
		    		}
		    		$("#type").html(obj.typeStr);
		    		$("#remark").html(obj.tripReason);
		    		$("#currentStepId").val(obj.currentStepId);
		    		
	    		}else if(WapConstants.OFFICE_GO_OUT_JT == businessType){//集体外出管理
	    			titleStr = obj.applyUserName+"的集体外出";
					$("#title").html(obj.applyUserName+"的集体外出");
					$("#startTime").html(obj.beginTime);
					$("#endTime").html(obj.endTime);
					$("#currentStepId").val(obj.currentStepId);
					
					var goOutJtType=obj.goOutJtType;
					if(goOutJtType=="1"){
						$("#teacherEx").hide();
						
						$("#goOutJtType").html("学生集体活动");
						$("#organize").html(obj.organize);
						$("#activityNumber").html(obj.activityNumber);
						$("#place").html(obj.place);
						$("#scontent").html(obj.scontent);
						$("#vehicle").html(obj.vehicle);
						$("#isDrivinglicence").html(obj.isDrivinglicenceStr);
						var isOrganizationStr=obj.isOrganizationStr;
						$("#isOrganization").html(isOrganizationStr);
						if(isOrganizationStr=="是"){
							$("#traveUnit").html(obj.traveUnit);
							$("#travelinkPerson").html(obj.traveLinkPerson);
							$("#travelinkPhone").html(obj.traveLinkPhone);
						}else{
							$("#organizationDiv").hide();
						}
						$("#isInsurance").html(obj.isInsuranceStr);
						$("#activityleaderName").html(obj.activityLeaderName);
						$("#activityleaderPhone").html(obj.activityLeaderPhone);
						$("#leadGroupName").html(obj.leadGroupName);
						$("#leadGroupPhone").html(obj.leadGroupPhone);
						$("#otherTeacherNames").html(obj.otherTeacherNames);
						$("#activityIdeal").html(obj.activityIdealStr);
						$("#saftIdeal").html(obj.saftIdealStr);
					}else{
						$("#studentEx").hide();
						$("#goOutJtType").html("教师集体培训");
						$("#tcontent").html(obj.tcontent);
						$("#partakePersonNames").html(obj.partakePersonNames);
					}
	    			
				}else if(WapConstants.OFFICE_EVECTION == businessType){//出差管理
					titleStr = obj.applyUserName+"的出差";
					$("#title").html(obj.applyUserName+"的出差");
		    		$("#place").html(obj.place);
		    		$("#startTime").html(obj.beginTime);
		    		$("#endTime").html(obj.endTime);
		    		$("#days").html(obj.days);
		    		$("#remark").html(obj.tripReason);
		    		$("#currentStepId").val(obj.currentStepId);
				}else if(WapConstants.OFFICE_EXPENSE == businessType){//报销管理
					titleStr = obj.applyUserName+"的报销";
					$("#title").html(obj.applyUserName+"的报销");
		    		$("#expenseMoney").html(obj.expenseMoney);
		    		$("#expenseType").html(obj.expenseType);
		    		$("#detail").html(obj.detail);
		    		$("#currentStepId").val(obj.currentStepId);
				}else if(WapConstants.OFFICE_ATTEND_LECTURE == businessType){//听课管理
					titleStr = obj.applyUserName+"的听课";
					$("#title").html(obj.applyUserName+"的听课");
					$("#subjectName").html(obj.subjectName);
					$("#projectName").html(obj.projectName);
					$("#attendDate").html(obj.attendDate);
					$("#attendPeriod").html(obj.attendPeriod);
					$("#gradeName").html(obj.gradeName);
					$("#className").html(obj.className);
					$("#teacherName").html(obj.teacherName);
					$("#projectContent").html(obj.projectContent);
					$("#projectOpinion").html(obj.projectOpinion);
					$("#currentStepId").val(obj.currentStepId);
				}else if(WapConstants.OFFICE_ATTENDANCE == businessType){//教师考勤-补卡申请
					titleStr = obj.applyUserName+"的补卡申请";
					$("#title").html(obj.applyUserName+"的补卡申请");
					$("#attenceDateType").html(obj.attenceDateType);
					$("#clockReason").html(obj.reason);
				}else if(WapConstants.OFFICE_GOODS == businessType){//物品管理
					
				}else{//请假管理
					titleStr = obj.applyUserName+"的请假";
					$("#title").html(obj.applyUserName+"的请假");
		    		$("#leaveTypeName").html(obj.leaveTypeName);
		    		$("#startTime").html(obj.leaveBeginTime);
		    		$("#endTime").html(obj.leaveEndTime);
		    		$("#days").html(obj.days);
		    		$("#remark").html(obj.leaveReason);
		    		$("#noticeUserNames").html(obj.noticePersonNames);
		    		$("#currentStepId").val(obj.currentStepId);
		    		//请假明细
		    		var darray = wap._applyService._LeaveService.addLeaveDaysDetailDeatil(obj.leaveBeginTime,obj.leaveEndTime);
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
				}
	    		
	    		$('title').text(titleStr);
	    		
	    		//附件
	    		var attachmentArray = obj.attachmentArray;
	    		if(attachmentArray!=null && attachmentArray.length > 0){
	    			var htmlStr = ''; 
	    			$.each(attachmentArray, function(index, json){
	    				var fileSize = getFileSize(json.fileSize);
	    				htmlStr += '<li class="fn-clear">';
	    				htmlStr += getFilePic(json.extName);
	    				
	    				if(json.fileExist){
	    					if(isWeiXin()&&isAndroid()){
	    						var downloadPath = _contextPath+"/common/open/officeh5/downloadAtt.action?path="+json.downloadPath;
	    						htmlStr+='<a href="'+downloadPath+'" class="icon-img icon-download"></a>';
	    					}else{
	    						htmlStr+='<a href="'+json.downloadPath+'" class="icon-img icon-download"></a>';
	    					}
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
	    				htmlStr += wapNetworkService._service._getDefaultPhotoUrl(json.photoUrl,json.assigneeName);
//	    				htmlStr += '<img src="'+wapNetworkService._service._getDefaultPhotoUrl(json.photoUrl)+'">';
	    				htmlStr += '<p class="tt"><span class="time">'+json.operateTime+'</span><span class="f-16">'+json.assigneeName+'</span></p>';
	    				htmlStr += '<p class="dd f-14"><span class="c-blue">'+json.textComment+'</span></p>';
	    				htmlStr += '</div>';
	    				htmlStr += '</li>';
	    			});
	    			$("#auditDiv").html(htmlStr);
	    		}
	    	}
	    },
	    
	    //物品领用类型
	    _loadGoodsSelectList : function(searchType, applyType, data) {
    		var result=data.result_array;
    		var containerHtml="";
    		if(!isNotBlank(searchType)){
    			containerHtml+='<li class="condition-all current" id=""><span>全部<i class="icon-img icon-current"></i></span></li>';
    		}else{
    			containerHtml+='<li class="condition-all" id=""><span>全部<i class="icon-img icon-current"></i></span></li>';
    		}
    		
    		if(result.length>0){
    			for(var i=0;i<result.length;i++){
    				var obj = result[i];
    				containerHtml += '<li';
    				if(searchType==obj.typeId){
    					containerHtml += ' class="current"';
    				}
    				containerHtml += ' id="'+obj.typeId+'">';
    				containerHtml += '<span>'+obj.typeName+'<i class="icon-img icon-current"></i></span></li>';
    			}
    		}
    		$(".goodsTypes").html(containerHtml);
	    },
		
	    //物品领用申请list
	    _LoadGoodsApplyList : function(data) {
			WapPage = data.page;
			var array = data.result_array;
			var cou=array.length;
			if(cou>0){
				var conHtmlStr="";
				for(var i=0;i<cou;i++){
					var obj = array[i];
					var htmlStr = '<li';
					htmlStr+=' onclick="location.href=\'goodsManagerDetailApply.html?id='+obj.id+'\';"';
					htmlStr+='>';
					htmlStr+= '<p class="tt f-18">' + obj.name ;
					htmlStr+= '</p>';
					htmlStr+= '<p class="dd f-14">';
					htmlStr+= '<span>' + obj.typeName +'</span>';
					htmlStr+='</p>';
					htmlStr+='</li>';
					conHtmlStr+=htmlStr;
				}
				$('.ui-list').append(conHtmlStr);
				if (WapPage.pageIndex >= WapPage.maxPageIndex) {
			    	$('.loading-more').html('<a href="javascript:void(0)">暂时没有更多的记录哦</a>');
			    	$('.loading-more').unbind();
			    }
				$('#list').show();
				$('#empty').hide();
			}else{
				$('#list').hide();
				$('#empty').show();
			}	
	    },
	    
	    //物品领用申请
	    _loadGoodsApplyDetail : function(unitId,userId,data){
	    	var result=data.result_object;
	    	$('#name').val(result.name);
	    	$('#typeName').val(result.typeName);
	    	$('#price').val(result.price);
	    	$('#model').val(result.model);
	    	$('#goodsUnit').val(result.goodsUnit);
	    	$('#amount').html(result.amount);
	    	$('#remark').val(result.remark);
	    },
	    
	    _mainTabSet : function(array){
	    	var flag = false;
			if(array.length > 0){
				$.each(array, function(index, json) {
					if(6==json.type){
						flag=true;
					}
				});
				if(flag){
					$("#li_6").show();
					$("#li_no").hide();
				}else{
					$("#li_6").hide();
					$("#li_no").show();
				}
			}
	    },
	    
	    _getDefaultPhotoUrl : function(photoUrl,userName){
	    	if(isNotBlank(photoUrl)){
	    		return '<img src="'+photoUrl+'">';
	    		
	    	}else{
//	    		return "../../../static/html/images/icon/img_default_photo.png";
	    		return '<span class="place-avatar" style="background:'+getBackgroundColor(userName)+';">'+getLast2Str(userName)+'</span>';
	    	}
	    },
	    
	    
		_doError : function(data) {
			if(typeof(data) == 'undefined' && data == 'undefined' && data == null && data == ''){
				closeMsgTip();
				viewToast("未获取到数据");
	        	return true;
	        }else if (data.result == Constants.FAILURE) {
	        	closeMsgTip();
	        	viewToast(data.msg);
	        	return true;
	        }
	        return false;
	      },
	},
};