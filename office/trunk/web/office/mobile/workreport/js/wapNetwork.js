var wapNetwork = {
		
	// 获取列表信息
	doGetList : function(unitId, userId, dataType, searchStr) {
		var url = "/common/open/officemobile/workReport-list.action";
		officeAjax.doAjax(url, {'unitId':unitId, 'userId':userId, "dataType":dataType, "searchStr":searchStr, 'pageSize':Constants.PATE_SIZE,'pageIndex':1}, function(data) {
	    	wapNetworkService.doLoadList(dataType, searchStr, data);
	    }, 'json').error(function(data) {
		   console.log('[error] wapNetwork--workReportList error.');
	    });
	},
	
	//获取更多信息
	doMoreList : function(unitId, userId, dataType, searchStr, pageIndex) {
		var url = "/common/open/officemobile/workReport-list.action";
		officeAjax.doAjax(url, {'unitId':unitId, 'userId':userId, "dataType":dataType, "searchStr":searchStr, 'pageSize':Constants.PATE_SIZE,'pageIndex':pageIndex}, function(data) {
	    	wapNetworkService.doLoadList(dataType, searchStr, data);
	    }, 'json').error(function(data) {
		   console.log('[error] wapNetwork--workReportList error.');
	    });
	},
	
	//编辑
	doGetApplyDetail : function(id){
		url = "/common/open/officemobile/workReport-detail.action";
		officeAjax.doAjax(url, {"id":id}, function(data) {
	    	wapNetworkService.doLoadApplyDetail(id, data);
	    }, 'json').error(function(data) {
		   console.log('[error] wapNetwork--workReportList error.');
	    });
	},
	
	//详情
	doGetDetail : function(dataType, id){
		url = "/common/open/officemobile/workReport-detail.action";
		officeAjax.doAjax(url, {"id":id}, function(data) {
	    	wapNetworkService.doLoadDetail(dataType, data);
	    }, 'json').error(function(data) {
		   console.log('[error] wapNetwork--workReportList error.');
	    });
	},
	
	//提交申请
	doSave : function(dataType){
		showLoading("正在处理……");
		//ajax表单提交
		var options = {
			url : storage.get(Constants.MOBILE_CONTEXT_PATH)+"/common/open/officemobile/workReport-save.action",
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
	
};