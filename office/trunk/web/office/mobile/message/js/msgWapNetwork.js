var wapNetwork = {
		
	// 获取列表信息
	doGetList : function(userId, dataType, searchStr) {
		var listUrl="/common/open/officehfive/";
		if(dataType == WapConstants.DATA_TYPE_1){
			//草稿箱
			listUrl+="remoteDraftMessagesList.action";
		}else if(dataType == WapConstants.DATA_TYPE_2){
			//发件箱
			listUrl+="remoteSendedMessagesList.action";
		}else{
			//收件箱
			listUrl+="remoteReceivedMessagesList.action";
		}
		officeAjax.doAjax(listUrl, {'userId':userId, 'searchTitle':searchStr,'pageSize':WapConstants.PATE_SIZE,'pageIndex':1}, function(data) {
	    	wapNetworkService.doLoadList(dataType, searchStr, data);
	    }, 'json').error(function(data) {
		   console.log('[error] wapNetwork--workReportList error.');
	    });
	},
	
	//获取更多信息
	doMoreList : function(userId, dataType, searchStr, pageIndex) {
		var listUrl="/common/open/officehfive/";
		if(dataType == WapConstants.DATA_TYPE_1){
			//草稿箱
			listUrl+="remoteDraftMessagesList.action";
		}else if(dataType == WapConstants.DATA_TYPE_2){
			//发件箱
			listUrl+="remoteSendedMessagesList.action";
		}else{
			//收件箱
			listUrl+="remoteReceivedMessagesList.action";
		}
		officeAjax.doAjax(listUrl, {'userId':userId, 'searchTitle':searchStr,'pageSize':WapConstants.PATE_SIZE,'pageIndex':pageIndex}, function(data) {
			wapNetworkService.doLoadList(dataType, searchStr, data);
	    }, 'json').error(function(data) {
		   console.log('[error] wapNetwork--workReportList error.');
	    });
	},
	
	//详情
	doGetDetail : function(userId,dataType, id,replyMsgId){
		var detailUrl="/common/open/officehfive/";
		if(dataType == WapConstants.DATA_TYPE_1){
			//草稿箱
			$("#reply-all").hide();
			detailUrl+="viewMsgSingle.action";
		}else if(dataType == WapConstants.DATA_TYPE_2){
			//发件箱明细
			$("#reply-all").show();
			detailUrl+="viewMsgSingle.action";
		}else{
			//收件箱--会话明细
			detailUrl+="msgDetail.action";
		}
		
		officeAjax.doAjax(detailUrl, {'userId':userId,'msgId':id,'msgState':dataType,"replyMsgId":replyMsgId}, function(data) {
			if(dataType == WapConstants.DATA_TYPE_1 || dataType == WapConstants.DATA_TYPE_2){
				//草稿箱、发件箱明细
				wapNetworkService.doLoadDetail(dataType, data);
			}else{
				//收件箱--会话明细
				wapNetworkService.doLoadReceivedDetailList(userId,dataType, data);
			}
	    }, 'json').error(function(data) {
		   console.log('[error] wapNetwork--workReportList error.');
	    });
	},
	
	//详情
	doGetReceivedDetail : function(userId,dataType, id, obj){
		var detailUrl="/common/open/officehfive/msgDetailContent.action";
		officeAjax.doAjax(detailUrl, {'userId':userId,'msgId':id}, function(data) {
			//收件箱--会话中点击明细
			wapNetworkService.doLoadReceivedDetail(dataType, data, obj);
		}, 'json',false).error(function(data) {
			console.log('[error] wapNetwork--workReportList error.');
		});
	},
	
	doEdit : function(dataType,id,editType){
		officeAjax.doAjax("/common/open/officehfive/sendNote.action", {'operateType':editType, 'msgId':id}, function(data) {
			wapNetworkService.doEditMsg(data,dataType,editType);
		}, 'json').error(function(data) {
			console.log('[error] wapNetwork--workReportList error.');
		});
	},
	
	doDelete : function(dataType, id){
		officeAjax.doAjax("/common/open/officehfive/removeMsg.action", {'msgState':dataType, 'deleteId':id}, function(data) {
			wapNetworkService.doDeleteMsg(data);
		}, 'json').error(function(data) {
			console.log('[error] wapNetwork--workReportList error.');
		});
	},
	
	doCollect : function(dataType,id,hasStar,obj){
		officeAjax.doAjax("/common/open/officehfive/changeStar.action", {'msgId':id, 'hasStar':hasStar}, function(data) {
			wapNetworkService.doCollectMsg(data,obj);
		}, 'json').error(function(data) {
			console.log('[error] wapNetwork--workReportList error.');
		});
	},
	doSave : function(dataType,editType,userIds,userId,sendUserName,unitId,state){
		showLoading("正在处理……");
		$("#userIds").val(userIds);
		$("#userId").val(userId);
		$("#sendUserName").val(sendUserName);
		$("#unitId").val(unitId);
		$("#state").val(state);
		//ajax表单提交
		var options = {
			url : storage.get(Constants.MOBILE_CONTEXT_PATH)+"/common/open/officehfive/saveNote.action",
			success : function(data){
				if(data.result==Constants.SUCCESS){
					showMsgTip('操作成功',function(){
						wap._editService.backList(dataType);
		    		});
		    	}else{
		    		showMsgTip('操作失败');
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