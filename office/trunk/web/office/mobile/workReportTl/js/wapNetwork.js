var wapNetwork = {
		
	// 获取列表信息
	doGetList : function(userId, unitId, searchName, dataType) {
		var listUrl="/common/open/officemobile/";
		if(WapConstants.DATA_TYPE_0 == dataType){
			listUrl+="workReportTl-workReportSearchList.action";//我收到的
		}else if(WapConstants.DATA_TYPE_1 == dataType){
			listUrl+="workReportTl-applyList.action";//我发出的
		}
		officeAjax.doAjax(listUrl, {'userId':userId,'unitId':unitId, 'searchName':searchName, 'pageSize':WapConstants.PATE_SIZE,'pageIndex':1}, function(data) {
	    	wapNetworkService.doLoadList(dataType, data);
	    }, 'json').error(function(data) {
	    	viewToast("连接出错");
		   console.log('[error] wapNetwork--doGetList error.');
	    });
	},
	
	//获取更多信息
	doMoreList : function(userId, unitId, searchName, dataType, pageIndex) {
		var listUrl="/common/open/officemobile/";
		if(WapConstants.DATA_TYPE_0 == dataType){
			listUrl+="workReportTl-workReportSearchList.action";//我收到的
		}else if(WapConstants.DATA_TYPE_1 == dataType){
			listUrl+="workReportTl-applyList.action";//我发出的
		}
		officeAjax.doAjax(listUrl, {'userId':userId,'unitId':unitId, 'searchName':searchName, 'pageSize':WapConstants.PATE_SIZE,'pageIndex':pageIndex}, function(data) {
	    	wapNetworkService.doLoadList(dataType, data);
	    }, 'json').error(function(data) {
	    	viewToast("连接出错");
		   console.log('[error] wapNetwork--doMoreList error.');
	    });
	},
	
	//详情
	doGetDetail : function(unitId,userId,dataType, id,isEdit){
		var detailUrl="/common/open/officemobile/workReportTl-applyWorkReport.action";
		officeAjax.doAjax(detailUrl, {'unitId':unitId,'userId':userId,'id':id}, function(data) {
			wapNetworkService.doLoadDetail(dataType, data,isEdit);
	    }, 'json').error(function(data) {
	    	viewToast("连接出错");
		   console.log('[error] wapNetwork--doGetDetail error.');
	    });
	},
	
	doSave : function(unitId,userId,dataType){
		showLoading("正在处理...");
		var detailUrl="/common/open/officemobile/";
		if(WapConstants.DATA_TYPE_0 == dataType){
			detailUrl+="workReportTl-aduitWorkReportSave.action";//我收到的
		}else{
			detailUrl+="workReportTl-submitWorkReport.action";//我发出的
		}
		//ajax表单提交
		var options = {
			url : storage.get(Constants.MOBILE_CONTEXT_PATH)+detailUrl+"?userId="+userId,
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