var wapNetwork = {
		
	// 获取列表信息
	doGetList : function(unitId, userId, dataType, searchStr,reportType,sendTime,collect) {
		var url = "/common/open/officeh5/workReport-list.action";
//		showLoading("正在加载数据……");
		officeAjax.doAjax(url, {'unitId':unitId, 'userId':userId, "dataType":dataType, "searchStr":searchStr,"reportType":reportType,"sendTime":sendTime, 'pageSize':Constants.PATE_SIZE,'pageIndex':1}, function(data) {
//			closeMsgTip();
	    	wapNetworkService.doLoadList(dataType, searchStr,reportType,sendTime,collect, data);
	    }, 'json').error(function(data) {
//	    	closeMsgTip();
		   console.log('[error] wapNetwork--workReportList error.');
	    });
	},
	
	//获取更多信息
	doMoreList : function(unitId, userId, dataType, searchStr,reportType,sendTime,collect,pageIndex) {
		var url = "/common/open/officeh5/workReport-list.action";
//		showLoading("正在加载数据……");
		officeAjax.doAjax(url, {'unitId':unitId, 'userId':userId, "dataType":dataType, "searchStr":searchStr,"reportType":reportType,"sendTime":sendTime, 'pageSize':Constants.PATE_SIZE,'pageIndex':pageIndex}, function(data) {
//			closeMsgTip();
			wapNetworkService.doLoadList(dataType, searchStr,reportType,sendTime,collect, data);
	    }, 'json').error(function(data) {
//	    	closeMsgTip();
		   console.log('[error] wapNetwork--workReportList error.');
	    });
	},
	
	//详情
	doGetDetail : function(dataType,id,userId){
		url = "/common/open/officeh5/workReport-detail.action";
//		showLoading("正在加载数据……");
		officeAjax.doAjax(url, {"id":id,"userId":userId}, function(data) {
//			closeMsgTip();
	    	wapNetworkService.doLoadDetail(dataType,data);
	    }, 'json').error(function(data) {
//	    	closeMsgTip();
		   console.log('[error] wapNetwork--workReportList error.');
	    });
	},
	getReportById:function(id,dataType){
		url = "/common/open/officeh5/workReport-getReportById.action";
//		showLoading("正在加载数据……");
		officeAjax.doAjax(url, {"id":id}, function(data) {
//			closeMsgTip();
	    	wapNetworkService.doLoadEdit(dataType,id,data);
	    }, 'json').error(function(data) {
//	    	closeMsgTip();
		   console.log('[error] wapNetwork--getReportById error.');
	    });
	},
	//撤销
	cancelReport:function(id,userId,dataType,sendTime,reportType,collect){
		url = "/common/open/officeh5/workReport-cancelReport.action";
		showLoading("正在处理……");
		officeAjax.doAjax(url, {"id":id,"userId":userId}, function(data) {
			closeMsgTip();
			wap._listService.backList(dataType,sendTime,reportType,collect);
	    }, 'json').error(function(data) {
	    	closeMsgTip();
		   console.log('[error] wapNetwork--cancelReport error.');
	    });
	},
	//提交申请
	doSave : function(dataType){
		showLoading("正在处理……");
		//ajax表单提交
		var options = {
			url : storage.get(Constants.MOBILE_CONTEXT_PATH)+"/common/open/officeh5/workReport-saveReport.action",
			success : function(data){
				closeMsgTip();

				var userIds = storage.get(WapConstants.ADDRESS_USERIDS_1);
				var userNames = storage.get(WapConstants.ADDRESS_USERNAMES_1);
				
				storageLocal.set(WapConstants.LAST_RECEIVE_IDS, userIds);
	    		storageLocal.set(WapConstants.LAST_RECEIVE_NAMES, userNames);
				//清楚缓存
				wap._editService.clearCache();
				
				if(data.result==Constants.SUCCESS){
					viewToast('操作成功', function(){
						wap._listService.backList(dataType,'','','');
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
	
	doSaveReply : function(id,dataType,sendTime,reportType,collect){
		//ajax表单提交
		showLoading("正在处理……");
		var options = {
				url : storage.get(Constants.MOBILE_CONTEXT_PATH)+"/common/open/officeh5/workReport-saveReply.action",
				success : function(data){
					closeMsgTip();
					if(data.result==Constants.SUCCESS){
							wap._detailService.backDetail(id,dataType,sendTime,reportType,collect);
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
	
};