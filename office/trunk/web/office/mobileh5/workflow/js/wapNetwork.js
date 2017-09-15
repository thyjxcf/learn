var wapNetwork = {
	
		//获取下一步审核人
		getAuditUser : function(unitId, userId, state, flowId, taskId){
			var url = "/common/open/officemobile/getAuditUser.action";
			officeAjax.doAjax(url, {'unitId':unitId,"userId":userId, "state":state, "flowId":flowId, "taskId":taskId}, function(data) {
				wapNetworkService.addNextAuditUser(data);
			}, 'json').error(function(data) {
				
			});
		},
		
		//首页模块权限
		initMainTab : function(unitId) {
			if(isNotBlank(storage.get(WapConstants.OFFICE_MAIN_MODEL))){
				wapNetworkService.mainTabSet();
			}else{
				var url = "/common/open/officeh5/applist.action";
				officeAjax.doAjax(url, {'unitId':unitId}, function(data) {
					wapNetworkService.mainTabSet(data);
				}, 'json').error(function(data) {
					viewToast("连接出错");
				});
			}
		},
		
		// 获取审核列表信息
		doGetAuditList : function(unitId, userId, dataType, businessType,searchStr,pageIndex) {
			var url = "/common/open/officeh5/getAuditList.action";
//			//showLoading("正在加载数据……");
			officeAjax.doAjax(url, {'unitId':unitId, 'userId':userId, "dataType":dataType, "type":businessType, "searchStr":searchStr, 'pageSize':Constants.PATE_SIZE,'pageIndex':pageIndex}, function(data) {
//				//closeMsgTip();
		    	wapNetworkService.doLoadAuditList(dataType, searchStr, data, businessType);
		    }, 'json').error(function(data) {
//		    	//closeMsgTip();
		    	viewToast("连接出错");
		    });
		},
		
		// 获取申请列表信息
		doGetApplyList : function(userId, businessType,searchStr, pageIndex) {
			var url = "/common/open/officeh5/getApplyList.action";
			//showLoading("正在加载数据……");
			officeAjax.doAjax(url, {'userId':userId, "type":businessType,"searchStr":searchStr, 'pageSize':Constants.PATE_SIZE,'pageIndex':pageIndex}, function(data) {
				//closeMsgTip();
		    	wapNetworkService.doLoadApplyList(searchStr, data, businessType);
		    }, 'json').error(function(data) {
		    	//closeMsgTip();
		    	viewToast("连接出错");
		    });
		},
		
		//详情
		doGetDetail : function(dataType, id, businessType, unitId){
			var url = "";
			//showLoading("正在加载数据……");
			if(WapConstants.OFFICE_GO_OUT == businessType){//外出管理
				url = "/common/open/officemobile/goOut-applyDetail.action";
			}else if(WapConstants.OFFICE_GO_OUT_JT == businessType){//集体外出管理
				url = "/common/open/officemobile/goOutJt-applyDetailJt.action";
			}else if(WapConstants.OFFICE_EVECTION == businessType){//出差管理
				url = "/common/open/officemobile/evection-applyDetail.action";
			}else if(WapConstants.OFFICE_EXPENSE == businessType){//报销管理
				url = "/common/open/officemobile/expense-expenseApplyDetail.action";
			}else if(WapConstants.OFFICE_ATTEND_LECTURE == businessType){//听课管理
				url = "/common/open/officemobile/attendLecture-applyDetail.action";
			}else if(WapConstants.OFFICE_ATTENDANCE == businessType){//教师考勤-补卡申请
				url = "/common/open/officemobile/attendance-applyDetail.action";
			}else if(WapConstants.OFFICE_GOODS == businessType){//物品管理
				url = "/common/open/officemobile/goods-goodsAuditDetail.action";
			}else{//请假管理
				url = "/common/open/officemobile/teacherLeave-applyDetail.action";
			}
			officeAjax.doAjax(url, {"unitId":unitId,"id":id}, function(data) {
				//closeMsgTip();
		    	wapNetworkService.doLoadDetail(dataType, data, businessType);
		    }, 'json').error(function(data) {
		    	//closeMsgTip();
			   console.log('[error] wapNetwork--workReportList error.');
		    });
		},
		
		//审核
		doGetAuditDetail : function(dataType, id, unitId, userId, taskId, businessType){
			var url = "";
			//showLoading("正在加载数据……");
			if(WapConstants.OFFICE_GO_OUT == businessType){//外出管理
				url = "/common/open/officemobile/goOut-auditGoOut.action";
			}else if(WapConstants.OFFICE_GO_OUT_JT == businessType){//集体外出管理
				url = "/common/open/officemobile/goOutJt-auditGoOutJt.action";
			}else if(WapConstants.OFFICE_EVECTION == businessType){//出差管理
				url = "/common/open/officemobile/evection-aduitBusinessTrip.action";
			}else if(WapConstants.OFFICE_EXPENSE == businessType){//报销管理
				url = "/common/open/officemobile/expense-auditExpense.action";
			}else if(WapConstants.OFFICE_ATTEND_LECTURE == businessType){//听课管理
				url = "/common/open/officemobile/attendLecture-auditAttendLecture.action";
			}else if(WapConstants.OFFICE_ATTENDANCE == businessType){//教师考勤-补卡申请
				url = "/common/open/officemobile/attendance-auditAttendanceClock.action";
			}
			else if(WapConstants.OFFICE_GOODS == businessType){//物品管理
				
			}else{//请假管理
				url = "/common/open/officemobile/teacherLeave-auditTeacherLeave.action";
			}
			
			officeAjax.doAjax(url, {"id":id, "unitId":unitId, "userId":userId, "taskId":taskId}, function(data) {
				//closeMsgTip();
		    	wapNetworkService.doGetAuditDetail(dataType, data, businessType);
		    }, 'json').error(function(data) {
		    	//closeMsgTip();
			   console.log('[error] wapNetwork--workReportList error.');
		    });
		},
		
		doGetApplyDetail : function(unitId, userId, id, businessType){
			var url = "";
			if(WapConstants.OFFICE_GO_OUT == businessType){//外出管理
				url = "/common/open/officemobile/goOut-applyGoOut.action";
			}else if(WapConstants.OFFICE_GO_OUT_JT == businessType){//集体外出管理
				url = "/common/open/officemobile/goOutJt-applyGoOutJt.action";
			}else if(WapConstants.OFFICE_EVECTION == businessType){//出差管理
				url = "/common/open/officemobile/evection-applyBusinessTrip.action";
			}else if(WapConstants.OFFICE_EXPENSE == businessType){//报销管理
				url = "/common/open/officemobile/expense-applyExpense.action";
			}else if(WapConstants.OFFICE_GOODS == businessType){//物品管理
				url="/common/open/officemobile/goods-goodsApplyListGoodsInfo.action"; 
			}else if(WapConstants.OFFICE_ATTEND_LECTURE == businessType){//听课管理
				url="/common/open/officemobile/attendLecture-applyAttendLecture.action"; 
			}else{//请假管理
				url = "/common/open/officemobile/teacherLeave-applyTeacherLeave.action";
			}
			officeAjax.doAjax(url, {'unitId':unitId, 'userId':userId, "id":id}, function(data) {
				if(WapConstants.OFFICE_GOODS == businessType){//物品
					wapNetworkService.doLoadGoodsApplyDetail(unitId,userId,data);
				}else{
					wapNetworkService.doLoadApplyDetail(id, data, businessType);
				}
		    }, 'json').error(function(data) {
			   console.log('[error] wapNetwork--workReportList error.');
		    });
		},
		
		
		//提交申请
		doSave : function(businessType){
			showLoading("正在处理……");
			var url = "";
			if(WapConstants.OFFICE_GO_OUT == businessType){//外出管理
				url = storage.get(Constants.MOBILE_CONTEXT_PATH)+"/common/open/officemobile/goOut-submitGoOut.action";
			}else if(WapConstants.OFFICE_GO_OUT_JT == businessType){//集体外出管理
				url = storage.get(Constants.MOBILE_CONTEXT_PATH)+"/common/open/officemobile/goOutJt-submitGoOutJt.action";
			}else if(WapConstants.OFFICE_EVECTION == businessType){//出差管理
				url = storage.get(Constants.MOBILE_CONTEXT_PATH)+"/common/open/officemobile/evection-submitBusinessTrip.action";
			}else if(WapConstants.OFFICE_EXPENSE == businessType){//报销管理
				url = storage.get(Constants.MOBILE_CONTEXT_PATH)+"/common/open/officemobile/expense-submitExpenseApply.action";
			}else if(WapConstants.OFFICE_ATTEND_LECTURE == businessType){//听课管理
				url = storage.get(Constants.MOBILE_CONTEXT_PATH)+"/common/open/officemobile/attendLecture-submitAttendLecture.action";
			}else if(WapConstants.OFFICE_GOODS == businessType){//物品管理
				
			}else{//请假管理
				url = storage.get(Constants.MOBILE_CONTEXT_PATH)+"/common/open/officemobile/teacherLeave-submitTeacherLeave.action";
			}
			
			//ajax表单提交
			var options = {
				url : url,
				success : function(data){
					closeMsgTip();
					if(data.result==Constants.SUCCESS){
						viewToast('操作成功', function(){
							wap._applyService._jumpDetail(data.id,businessType);
						});
			    	}else{
			    		if(data.msg!=''){
			    			viewToast(data.msg);
			    		}else
			    		viewToast('操作失败');
			    	}
				},
				dataType : 'json',
				clearForm : false,
				resetForm : false,
				type : 'post',
				error:function(XMLHttpRequest, textStatus, errorThrown){
					closeMsgTip();
					viewToast(XMLHttpRequest.status);
				}//请求出错 
			};
			
		  	$("#dataForm").ajaxSubmit(options);
		},
		//提交审核
		doAuditSave : function(dataType, businessType){
			showLoading("正在处理……");
			var url = "";
			if(WapConstants.OFFICE_GO_OUT == businessType){//外出管理
				url = storage.get(Constants.MOBILE_CONTEXT_PATH)+"/common/open/officemobile/goOut-auditPassGoOut.action";
			}else if(WapConstants.OFFICE_GO_OUT_JT == businessType){//集体外出管理
				url = storage.get(Constants.MOBILE_CONTEXT_PATH)+"/common/open/officemobile/goOutJt-auditPassGoOutJt.action";
			}else if(WapConstants.OFFICE_EVECTION == businessType){//出差管理
				url = storage.get(Constants.MOBILE_CONTEXT_PATH)+"/common/open/officemobile/evection-auditPassBusinessTrip.action";
			}else if(WapConstants.OFFICE_EXPENSE == businessType){//报销管理
				url = storage.get(Constants.MOBILE_CONTEXT_PATH)+"/common/open/officemobile/expense-saveExpenseAudit.action";
			}else if(WapConstants.OFFICE_ATTEND_LECTURE == businessType){//听课管理
				url = storage.get(Constants.MOBILE_CONTEXT_PATH)+"/common/open/officemobile/attendLecture-auditPassAttendLecture.action";
			}else if(WapConstants.OFFICE_ATTENDANCE == businessType){//教师考勤-补卡申请
				url = storage.get(Constants.MOBILE_CONTEXT_PATH)+"/common/open/officemobile/attendance-saveAudit.action";
			}else if(WapConstants.OFFICE_GOODS == businessType){//物品管理
				
			}else{//请假管理
				url = storage.get(Constants.MOBILE_CONTEXT_PATH)+"/common/open/officemobile/teacherLeave-auditPassLeave.action";
			}
			//ajax表单提交
			var options = {
				url : url,
				success : function(data){
					closeMsgTip();
					if(data.result==Constants.SUCCESS){
						viewToast('操作成功', function(){
							wap._auditListService.backList(dataType);
						});
			    	}else{
			    		if(data.msg!=''){
			    			viewToast(data.msg);
			    		}else{
			    			viewToast('操作失败');
			    		}
			    	}
				},
				dataType : 'json',
				clearForm : false,
				resetForm : false,
				type : 'post',
				error:function(XMLHttpRequest, textStatus, errorThrown){
					closeMsgTip();
					viewToast(XMLHttpRequest.status);
				}//请求出错 
			};
			
		  	$("#dataForm").ajaxSubmit(options);
		},
		
		doGoodsAudit : function(userId,id,applyType,auditContent, dataType){
			if(!auditContent){
				auditContent="";
			}
			showLoading("正在处理...");
			var detailUrl="/common/open/officemobile/goods-goodsAuditSave.action";
			officeAjax.doAjax(detailUrl, {'userId':userId,'id':id,'applyType':applyType,'auditContent':auditContent}, function(data) {
				closeMsgTip();
				if(data.result==Constants.SUCCESS){
					viewToast("操作成功",function(){
			    		wap._auditListService.backList(dataType);
			    	});
		    	}else{
		    		if(data.msg!=''){
		    			viewToast(data.msg);
		    		}else{
		    			viewToast('操作失败');
		    		}
		    	}
			}, 'json').error(function(data) {
				closeMsgTip();
				viewToast("连接出错");
				console.log('[error] wapNetwork--doAudit error.');
			});
		},
		
		//物品获取筛选信息
		doGetGoodsSelectList : function(userId, unitId, searchType, applyType) {
			var listUrl="/common/open/officemobile/goods-goodsApplyType.action";
			officeAjax.doAjax(listUrl, {'userId':userId,'unitId':unitId, 'searchType':searchType, 'applyType':applyType}, function(data) {
		    	wapNetworkService.doLoadGoodsSelectList(searchType, applyType, data);
		    }, 'json').error(function(data) {
		    	viewToast("连接出错");
			   console.log('[error] wapNetwork--doGetSelectList error.');
		    });
		},
		
		// 获取列表信息
		doGetGoodsApplyList : function(userId, unitId, searchType, searchName, pageIndex) {
			var listUrl="/common/open/officemobile/goods-goodsApplyList.action";
			//showLoading("正在加载数据……");
			officeAjax.doAjax(listUrl, {'userId':userId,'unitId':unitId, 'searchType':searchType, 'searchName':searchName, 'pageSize':WapConstants.PATE_SIZE,'pageIndex':pageIndex}, function(data) {
				//closeMsgTip();
		    	wapNetworkService.doLoadGoodsApplyList(data);
		    }, 'json').error(function(data) {
		    	//closeMsgTip();
		    	viewToast("连接出错");
			   console.log('[error] wapNetwork--doGetList error.');
		    });
		},
		
		doGoodsSave : function(unitId,userId,id,applyAmount,applyRemark){
			showLoading("正在处理...");
			var detailUrl="/common/open/officemobile/goods-goodsApplySave.action";
			officeAjax.doAjax(detailUrl, {'unitId':unitId,'userId':userId,'id':id,'applyAmount':applyAmount,'applyRemark':applyRemark}, function(data) {
				closeMsgTip();
				if(data.result==Constants.SUCCESS){
					viewToast('操作成功', function(){
						wap.backList();
					});
		    	}else{
		    		if(data.msg!=''){
		    			viewToast(data.msg);
		    		}else{
		    			viewToast('操作失败');
		    		}
		    	}
			}, 'json').error(function(data) {
				closeMsgTip();
				viewToast("连接出错");
				console.log('[error] wapNetwork--doSave error.');
			});
		},
		
		//删除申请
		doDeleteApply : function(dataType, id, businessType){
			showLoading("正在处理……");
			var url = "";
			if(WapConstants.OFFICE_GO_OUT == businessType){//外出
				url = "/common/open/officemobile/goOut-doDeleteApply.action";
			}else if(WapConstants.OFFICE_TEACHER_LEAVE == businessType){//请假
				url = "/common/open/officemobile/teacherLeave-doDeleteApply.action";
			}else if(WapConstants.OFFICE_GO_OUT_JT==businessType){//集体外出
				url="/common/open/officemobile/goOutJt-deDeleteApply.action";
			}
			
			officeAjax.doAjax(url, {'id':id}, function(data) {
				closeMsgTip();
				if(data.result==Constants.SUCCESS){
					viewToast('操作成功', function(){
						wap._applyListService.backList(dataType);
					});
		    	}else{
		    		if(data.msg!=''){
		    			viewToast(data.msg);
		    		}else{
		    			viewToast('操作失败');
		    		}
		    	}
			}, 'json').error(function(data) {
				closeMsgTip();
				viewToast("连接出错");
				console.log('[error] wapNetwork--doDeleteApply error.');
			});
		},
};