var wapNetwork = {
		
	// 获取列表信息
	doGetList : function(unitId, userId, dataType, searchStr, leaveType, applyStatus) {
		var url = '';
		if(WapConstants.DATA_TYPE_2 == dataType){
			url = "/common/open/officemobile/goOut-auditList.action";
		}else{
			url = "/common/open/officemobile/goOut-applyGoOutList.action";
		}
		officeAjax.doAjax(url, {'unitId':unitId, 'userId':userId, "leaveType":leaveType, "applyStatus":applyStatus, "auditStatus":applyStatus, 'pageSize':Constants.PATE_SIZE,'pageIndex':1}, function(data) {
	    	wapNetworkService.doLoadList(dataType, searchStr, data);
	    }, 'json').error(function(data) {
		   console.log('[error] wapNetwork--workReportList error.');
	    });
	},
	
	//获取更多信息
	doMoreList : function(unitId, userId, dataType, searchStr,applyStatus, pageIndex) {
		var url = '';
		if(WapConstants.DATA_TYPE_2 == dataType){
			url = "/common/open/officemobile/goOut-auditList.action";
		}else{
			url = "/common/open/officemobile/goOut-applyGoOutList.action";
		}
		officeAjax.doAjax(url, {'unitId':unitId, 'userId':userId, "applyStatus":applyStatus, "auditStatus":applyStatus, 'pageSize':Constants.PATE_SIZE,'pageIndex':pageIndex}, function(data) {
	    	wapNetworkService.doLoadList(dataType, searchStr, data);
	    }, 'json').error(function(data) {
		   console.log('[error] wapNetwork--workReportList error.');
	    });
	},
	
	//编辑
	doGetApplyDetail : function(unitId, userId, id){
		var url = '';
		url = "/common/open/officemobile/goOut-applyGoOut.action";
		officeAjax.doAjax(url, {'unitId':unitId, 'userId':userId, "id":id}, function(data) {
	    	wapNetworkService.doLoadApplyDetail(id, data);
	    }, 'json').error(function(data) {
		   console.log('[error] wapNetwork--workReportList error.');
	    });
	},
	
	//详情
	doGetDetail : function(dataType, id){
		var url = '';
		url = "/common/open/officemobile/goOut-applyDetail.action";
		officeAjax.doAjax(url, {"id":id}, function(data) {
	    	wapNetworkService.doLoadDetail(dataType, data);
	    }, 'json').error(function(data) {
		   console.log('[error] wapNetwork--workReportList error.');
	    });
	},
	
	//审核
	doGetAuditDetail : function(dataType, id, unitId, userId, taskId){
		var url = '';
		url = "/common/open/officemobile/goOut-auditGoOut.action";
		officeAjax.doAjax(url, {"id":id, "unitId":unitId, "userId":userId, "taskId":taskId}, function(data) {
	    	wapNetworkService.doGetAuditDetail(dataType, data);
	    }, 'json').error(function(data) {
		   console.log('[error] wapNetwork--workReportList error.');
	    });
	},
	
	
	//提交申请
	doSave : function(dataType){
		showLoading("正在处理……");
		//ajax表单提交
		var options = {
			url : storage.get(Constants.MOBILE_CONTEXT_PATH)+"/common/open/officemobile/goOut-submitGoOut.action",
			success : function(data){
				closeMsgTip();
				if(data.result==Constants.SUCCESS){
					viewToast('操作成功', function(){
						wap._listService.backList(dataType);
					});
		    	}else{
		    		var msg = data.msg;
		    		if(isNotBlank(msg)){
		    			viewToast(msg);
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
	
	//提交审核
	doAuditSave : function(dataType){
		showLoading("正在处理……");
		//ajax表单提交
		var options = {
			url : storage.get(Constants.MOBILE_CONTEXT_PATH)+"/common/open/officemobile/goOut-auditPassGoOut.action",
			success : function(data){
				closeMsgTip();
				if(data.result==Constants.SUCCESS){
					viewToast('操作成功', function(){
						wap._listService.backList(dataType);
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
	
};