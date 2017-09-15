var wapNetwork = {
		
	// 获取列表信息
	doGetList : function(userId, dataType, searchStr,receiveType) {
		var listUrl="/common/open/officeh5/";
		if(dataType == WapConstants.DATA_TYPE_1){
			//草稿箱
			listUrl+="draftMessagesList.action";
		}else if(dataType == WapConstants.DATA_TYPE_2){
			//发件箱
			listUrl+="sendedMessagesList.action";
		}else{
			//收件箱
			listUrl+="receivedMessagesList.action";
		}
//		showLoading("正在加载数据……");
		officeAjax.doAjax(listUrl, {'userId':userId, 'searchStr':searchStr,'receiveType':receiveType,'pageSize':WapConstants.PATE_SIZE,'pageIndex':1}, function(data) {
//			closeMsgTip();
	    	wapNetworkService.doLoadList(dataType, searchStr, data);
	    }, 'json').error(function(data) {
//	    	closeMsgTip();
		   console.log('[error] wapNetwork--msgList error.');
	    });
	},
	
	//获取更多信息
	doMoreList : function(userId, dataType, searchStr,receiveType, pageIndex) {
		var listUrl="/common/open/officeh5/";
		if(dataType == WapConstants.DATA_TYPE_1){
			//草稿箱
			listUrl+="draftMessagesList.action";
		}else if(dataType == WapConstants.DATA_TYPE_2){
			//发件箱
			listUrl+="sendedMessagesList.action";
		}else{
			//收件箱
			listUrl+="receivedMessagesList.action";
		}
//		showLoading("正在加载数据……");
		officeAjax.doAjax(listUrl, {'userId':userId, 'searchStr':searchStr,'receiveType':receiveType,'pageSize':WapConstants.PATE_SIZE,'pageIndex':pageIndex}, function(data) {
//			closeMsgTip();
			wapNetworkService.doLoadList(dataType, searchStr, data);
	    }, 'json').error(function(data) {
//	    	closeMsgTip();
		   console.log('[error] wapNetwork--msgList error.');
	    });
	},
	
	//详情
	doGetDetail : function(userId,dataType, id,replyMsgId){
		var unitId = storage.get(Constants.UNIT_ID);
		var detailUrl="/common/open/officeh5/";
		if(dataType == WapConstants.DATA_TYPE_2){
			//发件箱
			detailUrl+="messageDetail.action";
		}else{
			//收件箱
			detailUrl+="messageReceiveDetail.action";
		}
//		showLoading("正在加载数据……");
		officeAjax.doAjax(detailUrl, {'userId':userId,'unitId':unitId,'msgId':id,'dataType':dataType,"replyMsgId":replyMsgId}, function(data) {
//			closeMsgTip();
			if(dataType == WapConstants.DATA_TYPE_3){
				//收件箱--会话明细
				wapNetworkService.doLoadReceivedDetailList(userId,dataType, data);
			}else{
				wapNetworkService.doLoadDetail(dataType, data);
			}
	    }, 'json').error(function(data) {
//	    	closeMsgTip();
		   console.log('[error] wapNetwork--msgDetail error.');
	    });
	},
	
	//收件箱详情展开
	doGetReceivedDetail : function(userId,dataType, id, obj){
//		showLoading("正在加载数据……");
		var detailUrl="/common/open/officeh5/messageDetailContent.action";
		officeAjax.doAjax(detailUrl, {'userId':userId,'msgId':id}, function(data) {
			//收件箱--会话中点击明细
//			closeMsgTip();
			wapNetworkService.doLoadReceivedDetail(dataType, data, obj);
		}, 'json',false).error(function(data) {
//			closeMsgTip();
			console.log('[error] wapNetwork--getReceivedDetail error.');
		});
	},
	
	doEdit : function(dataType,id,editType){
		var unitId = storage.get(Constants.UNIT_ID);
		officeAjax.doAjax("/common/open/officeh5/messageEdit.action", {'msgId':id,'unitId':unitId,'editType':editType}, function(data) {
			wapNetworkService.doEditMsg(data,id,dataType);
		}, 'json',false).error(function(data) {
			console.log('[error] wapNetwork--msgEdit error.');
		});
	},
	
	doDelete : function(dataType, id){
		officeAjax.doAjax("/common/open/officeh5/messageRemove.action", {'dataType':dataType, 'msgId':id}, function(data) {
			if(data.result==Constants.SUCCESS){
				viewToast('操作成功', function(){
	    			wap._listService.backList(dataType);
	    		});
	    	}else{
	    		viewToast('操作失败');
	    	}
		}, 'json').error(function(data) {
			console.log('[error] wapNetwork--workReportList error.');
		});
	},
	
	doCollect : function(dataType,id,hasStar,obj){
		officeAjax.doAjax("/common/open/officeh5/changeStar.action", {'msgId':id, 'hasStar':hasStar}, function(data) {
			wapNetworkService.doCollectMsg(data,obj);
		}, 'json').error(function(data) {
			console.log('[error] wapNetwork--changeStar error.');
		});
	},
	
	doSave : function(dataType,operateType,userIds,userId,sendUserName,unitId,isNeedsms){
		showLoading("正在处理……");
		$("#userIds").val(userIds);
		$("#userId").val(userId);
		$("#userName").val(sendUserName);
		$("#unitId").val(unitId);
		$("#isNeedsms").val(isNeedsms);
		$("#operateType").val(operateType);
		//ajax表单提交
		var options = {
			url : storage.get(Constants.MOBILE_CONTEXT_PATH)+"/common/open/officeh5/messageSave.action",
			success : function(data){
				closeMsgTip();
				if(data.result==Constants.SUCCESS){
					viewToast('操作成功', function(){
						wap._listService.backList(dataType);
						//清除缓存
						wap._editService.clearCache();
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
			error:function(XMLHttpRequest, textStatus, errorThrown){showMsgTip(XMLHttpRequest.status);}//请求出错 
		};
	  	$("#dataForm").ajaxSubmit(options);
	},
	
};