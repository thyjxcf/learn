var wapNetwork = {
		
	// 获取列表信息
	doGetList : function(userId, unitId, searchType, searchName, applyType, dataType) {
		var listUrl="/common/open/officemobile/";
		if(WapConstants.DATA_TYPE_0 == dataType){
			listUrl+="goods-goodsAuditList.action";//物品审核
		}else if(WapConstants.DATA_TYPE_1 == dataType){
			listUrl+="goods-myGoodsList.action";//我的物品
		}else if(WapConstants.DATA_TYPE_2 == dataType){
			listUrl+="goods-goodsApplyList.action";//领用物品
		}
		officeAjax.doAjax(listUrl, {'userId':userId,'unitId':unitId, 'searchType':searchType, 'searchName':searchName, 'applyType':applyType,'pageSize':WapConstants.PATE_SIZE,'pageIndex':1}, function(data) {
	    	wapNetworkService.doLoadList(dataType, data);
	    }, 'json').error(function(data) {
	    	viewToast("连接出错");
		   console.log('[error] wapNetwork--doGetList error.');
	    });
	},
	
	//获取更多信息
	doMoreList : function(userId, unitId, searchType, searchName, applyType, dataType, pageIndex) {
		var listUrl="/common/open/officemobile/";
		if(dataType == WapConstants.DATA_TYPE_0){
			listUrl+="goods-goodsAuditList.action";//物品审核
		}else if(WapConstants.DATA_TYPE_1==dataType){
			listUrl+="goods-myGoodsList.action";//我的物品
		}else if(WapConstants.DATA_TYPE_2 == dataType){
			listUrl+="goods-goodsApplyList.action";//领用物品
		}
		officeAjax.doAjax(listUrl, {'userId':userId,'unitId':unitId, 'searchType':searchType, 'searchName':searchName, 'applyType':applyType,'pageSize':WapConstants.PATE_SIZE,'pageIndex':pageIndex}, function(data) {
	    	wapNetworkService.doLoadList(dataType, data);
	    }, 'json').error(function(data) {
	    	viewToast("连接出错");
		   console.log('[error] wapNetwork--doMoreList error.');
	    });
	},
	
	//获取筛选信息
	doGetSelectList : function(userId, unitId, searchType, applyType) {
		var listUrl="/common/open/officemobile/goods-goodsApplyType.action";
		officeAjax.doAjax(listUrl, {'userId':userId,'unitId':unitId, 'searchType':searchType, 'applyType':applyType}, function(data) {
	    	wapNetworkService.doLoadSelectList(searchType, applyType, data);
	    }, 'json').error(function(data) {
	    	viewToast("连接出错");
		   console.log('[error] wapNetwork--doGetSelectList error.');
	    });
	},
	
	//我的物品详情
	doGetDetail : function(unitId,dataType, id){
		var detailUrl="/common/open/officemobile/goods-goodsAuditDetail.action";
		officeAjax.doAjax(detailUrl, {'unitId':unitId,'id':id}, function(data) {
			wapNetworkService.doLoadDetail(dataType, data);
	    }, 'json').error(function(data) {
	    	viewToast("连接出错");
		   console.log('[error] wapNetwork--doGetDetail error.');
	    });
	},
	
	//领用申请详情
	doGetApplyDetail : function(unitId,userId,id){
		var detailUrl="/common/open/officemobile/goods-goodsApplyListGoodsInfo.action";
		officeAjax.doAjax(detailUrl, {'unitId':unitId,'id':id}, function(data) {
			wapNetworkService.doLoadApplyDetail(unitId,userId,data);
		}, 'json',false).error(function(data) {
			viewToast("连接出错");
			console.log('[error] wapNetwork--doGetApplyDetail error.');
		});
	},
	
	doSave : function(unitId,userId,id,applyAmount,applyRemark){
		showLoading("正在处理...");
		var detailUrl="/common/open/officemobile/goods-goodsApplySave.action";
		officeAjax.doAjax(detailUrl, {'unitId':unitId,'userId':userId,'id':id,'applyAmount':applyAmount,'applyRemark':applyRemark}, function(data) {
			wapNetworkService.doSave(data);
		}, 'json').error(function(data) {
			closeMsgTip();
			viewToast("连接出错");
			console.log('[error] wapNetwork--doSave error.');
		});
	},
	
	doAudit : function(userId,id,applyType,auditContent){
		if(!auditContent){
			auditContent="";
		}
		showLoading("正在处理...");
		var detailUrl="/common/open/officemobile/goods-goodsAuditSave.action";
		officeAjax.doAjax(detailUrl, {'userId':userId,'id':id,'applyType':applyType,'auditContent':auditContent}, function(data) {
			wapNetworkService.doAudit(data);
		}, 'json').error(function(data) {
			closeMsgTip();
			viewToast("连接出错");
			console.log('[error] wapNetwork--doAudit error.');
		});
	},
	
	doGiveBack : function(userId,id){
		showLoading("正在处理...");
		var detailUrl="/common/open/officemobile/goods-goodsReturnSave.action";
		officeAjax.doAjax(detailUrl, {'userId':userId,'id':id}, function(data) {
			wapNetworkService.doGiveBack(data);
		}, 'json').error(function(data) {
			closeMsgTip();
			viewToast("连接出错");
			console.log('[error] wapNetwork--doGiveBack error.');
		});
	},
	
};