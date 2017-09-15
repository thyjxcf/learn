var wapNetwork = {
		
	// 获取列表信息
	doGetList : function(userId, unitId, searchType, applyType, dataType,searchContent) {
		var listUrl="/common/open/officemobile/";
//		showLoading("正在加载数据……");
		if(WapConstants.DATA_TYPE_0 == dataType){
			listUrl+="repair-applyRepaireList.action";//我发起的
		}else if(WapConstants.DATA_TYPE_1 == dataType){
			listUrl+="repair-repaireManagerListH5.action";
			applyType=WapConstants.REPAIR_STATE_1+","+WapConstants.REPAIR_STATE_2;
		}else if(WapConstants.DATA_TYPE_2 == dataType){
			listUrl+="repair-repaireManagerListH5.action";
			applyType=WapConstants.REPAIR_STATE_3;
		}
		officeAjax.doAjax(listUrl, {'userId':userId,'unitId':unitId, 'searchType':searchType, 'applyType':applyType,'searchContent':searchContent,'pageSize':WapConstants.PATE_SIZE,'pageIndex':1}, function(data) {
//			closeMsgTip();
	    	wapNetworkService.doLoadList(dataType, data);
	    }, 'json').error(function(data) {
//	    	closeMsgTip();
	    	viewToast("连接出错");
		   console.log('[error] wapNetwork--doGetList error.');
	    });
	},
	
	//获取更多信息
	doMoreList : function(userId, unitId, searchType, applyType, dataType,searchContent, pageIndex) {
		var listUrl="/common/open/officemobile/";
		if(WapConstants.DATA_TYPE_0 == dataType){
			listUrl+="repair-applyRepaireList.action";//我发起的
		}else if(WapConstants.DATA_TYPE_1 == dataType){
			listUrl+="repair-repaireManagerListH5.action";
			applyType=WapConstants.REPAIR_STATE_1+","+WapConstants.REPAIR_STATE_2;
		}else if(WapConstants.DATA_TYPE_2 == dataType){
			listUrl+="repair-repaireManagerListH5.action";
			applyType=WapConstants.REPAIR_STATE_3;
		}
//		showLoading("正在加载数据……");
		officeAjax.doAjax(listUrl, {'userId':userId,'unitId':unitId, 'searchType':searchType, 'applyType':applyType,'searchContent':searchContent,'pageSize':WapConstants.PATE_SIZE,'pageIndex':pageIndex}, function(data) {
//			closeMsgTip();
	    	wapNetworkService.doLoadList(dataType, data);
	    }, 'json').error(function(data) {
//	    	closeMsgTip();
	    	viewToast("连接出错");
		   console.log('[error] wapNetwork--doMoreList error.');
	    });
	},
	
	// 获取待处理条数
	doGetWaitCount : function(userId, unitId) {
		var listUrl="/common/open/officemobile/";
		listUrl+="repair-repaireManagerListH5Count.action";
		var applyType=WapConstants.REPAIR_STATE_1+","+WapConstants.REPAIR_STATE_2;
		
		officeAjax.doAjax(listUrl, {'userId':userId,'unitId':unitId, 'applyType':applyType}, function(data) {
	    	wapNetworkService.doLoadCount(data);
	    }, 'json').error(function(data) {
	    	viewToast("连接出错");
		   console.log('[error] wapNetwork--doGetList error.');
	    });
	},
	
	
	//获取筛选信息
	doGetSelectList : function(userId, unitId, searchType, applyType) {
		var listUrl="/common/open/officemobile/repair-applyRepaireType.action";
		officeAjax.doAjax(listUrl, {}, function(data) {
	    	wapNetworkService.doLoadSelectList(searchType, applyType, data);
	    }, 'json').error(function(data) {
	    	viewToast("连接出错");
		   console.log('[error] wapNetwork--doGetSelectList error.');
	    });
	},
	
	doGetAdmin : function(userId, unitId) {
		var listUrl="/common/open/officemobile/repair-powerManager.action";
		officeAjax.doAjax(listUrl, {'userId':userId,'unitId':unitId}, function(data) {
	    	wapNetworkService.doLoadAdmin(data);
	    }, 'json').error(function(data) {
	    	viewToast("连接出错");
		   console.log('[error] wapNetwork--doGetAdmin error.');
	    });
	},
	
	doGetEdit : function(unitId, userId, id,dataType){
		var detailUrl="/common/open/officemobile/";
		detailUrl+="repair-applyRepaire.action";//我发起的
		officeAjax.doAjax(detailUrl, {'unitId':unitId,'userId':userId,'id':id}, function(data) {
			wapNetworkService.doLoadEdit(dataType, data);
		}, 'json').error(function(data) {
			viewToast("连接出错");
			console.log('[error] wapNetwork--doGetEdit error.');
		});
	},
	doGetAudit : function(unitId, userId, id,dataType){
		var detailUrl="/common/open/officemobile/";
		detailUrl+="repair-aduitRepaire.action";//报修管理
		officeAjax.doAjax(detailUrl, {'unitId':unitId,'userId':userId,'id':id}, function(data) {
			wapNetworkService.doLoadAudit(dataType, data);
		}, 'json').error(function(data) {
			viewToast("连接出错");
			console.log('[error] wapNetwork--doGetAudit error.');
		});
	},
	doGetDetail : function(unitId, userId, id,dataType){
		var detailUrl="/common/open/officemobile/";
		detailUrl+="repair-aduitRepaire.action";
//		showLoading("正在加载数据……");
		officeAjax.doAjax(detailUrl, {'unitId':unitId,'userId':userId,'id':id}, function(data) {
//			closeMsgTip();
			wapNetworkService.doLoadDetail(dataType, data);
	    }, 'json').error(function(data) {
//	    	closeMsgTip();
	    	viewToast("连接出错");
		   console.log('[error] wapNetwork--doGetDetail error.');
	    });
	},
	
	doSave : function(unitId,userId,dataType){
		showLoading("正在处理...");
		var detailUrl="/common/open/officemobile/";
		if(WapConstants.DATA_TYPE_0 == dataType || !isNotBlank(dataType)){
			detailUrl+="repair-saveRepaire.action";//我发起的
		}else{
			detailUrl+="repair-aduitRepaireSave.action";//报修管理
		}
		//ajax表单提交
		var options = {
			url : storage.get(Constants.MOBILE_CONTEXT_PATH)+detailUrl+"?unitId="+unitId+"&userId="+userId,
			success : function(data){
				wapNetworkService.doSave(data);
			},
			dataType : 'json',
			clearForm : false,
			resetForm : false,
			type : 'post',
			error:function(XMLHttpRequest, textStatus, errorThrown){
				closeMsgTip();
				viewToast("连接出错");
				console.log('[error] wapNetwork--doSave error.');
			}//请求出错 
		};
	  	$("#dataForm").ajaxSubmit(options);
	},
	
};