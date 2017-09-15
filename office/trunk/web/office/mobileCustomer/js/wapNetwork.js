var wapNetwork = {
		
	// 获取列表信息
	doGetList : function(unitId, userId, dataType,searchStr,customerType,customerRegion,customerStatus) {
		var url = "/common/open/customer/customer-list.action";
		officeAjax.doAjax(url, {'unitId':unitId, 'userId':userId, "dataType":dataType,"searchStr":searchStr,"customerType":customerType,"customerRegion":customerRegion,"customerStatus":customerStatus, 'pageSize':Constants.PATE_SIZE,'pageIndex':1}, function(data) {
	    	wapNetworkService.doLoadList(dataType,searchStr,customerType,customerRegion,customerStatus,data);
	    }, 'json').error(function(data) {
		   console.log('[error] wapNetwork--customerList error.');
	    });
	},
	
	//获取更多信息
	doMoreList : function(unitId, userId, dataType,searchStr,customerType,customerRegion,customerStatus,pageIndex) {
		var url = "/common/open/customer/customer-list.action";
		officeAjax.doAjax(url, {'unitId':unitId, 'userId':userId, "dataType":dataType, "searchStr":searchStr,"customerType":customerType,"customerRegion":customerRegion,"customerStatus":customerStatus,'pageSize':Constants.PATE_SIZE,'pageIndex':pageIndex}, function(data) {
			wapNetworkService.doLoadList(dataType,searchStr,customerType,customerRegion,customerStatus,data);
	    }, 'json').error(function(data) {
		   console.log('[error] wapNetwork--customerList error.');
	    });
	},
	
	//获取申请信息
	doGetApplyDetail : function(id,type,isBack){
		url = "/common/open/customer/customer-apply.action";
		officeAjax.doAjax(url, {"id":id}, function(data) {
			wapNetworkService.doLoadApplyDetail(id,data,isBack);
		}, 'json').error(function(data) {
			console.log('[error] wapNetwork--customerApply error.');
		});
	},
	
	//查看详情
	doGetCanReadDetail : function(dataType,id,isBack){
		url = "/common/open/customer/customer-canReadDetail.action";
		officeAjax.doAjax(url, {"id":id}, function(data) {
			wapNetworkService.doCanReadDetail(dataType,data,isBack);
		}, 'json').error(function(data) {
			console.log('[error] wapNetwork--canReadDetail error.');
		});
	},
	//跟踪详情
	doGetFollowDetail : function(dataType,id){
		url = "/common/open/customer/customer-followDetail.action";
		officeAjax.doAjax(url, {"id":id}, function(data) {
			wapNetworkService.doFollowDetail(dataType,data);
		}, 'json').error(function(data) {
			console.log('[error] wapNetwork--followDetail error.');
		});
	},
	
	//审核详情
	doAuditDetail : function(dataType,id,applyId,auditId){
		url = "/common/open/customer/customer-auditDetail.action";
		officeAjax.doAjax(url, {"id":id,"dataType":dataType,"applyId":applyId,"auditId":auditId}, function(data) {
			wapNetworkService.doAuditDetail(dataType,data);
		}, 'json').error(function(data) {
			console.log('[error] wapNetwork--auditDetail error.');
		});
	},
	//资源库详情
	doZYKDetail : function(dataType,id,isBack){
		url = "/common/open/customer/customer-zykDetail.action";
		officeAjax.doAjax(url, {"id":id,"dataType":dataType}, function(data) {
			wapNetworkService.doZYKDetail(dataType,data,isBack);
		}, 'json').error(function(data) {
			console.log('[error] wapNetwork--zykDetail error.');
		});
	},
	
	//跟踪记录编辑
	doFollowEdit : function(id,dataType,isBack){
		url = "/common/open/customer/customer-followInfo.action";
		officeAjax.doAjax(url, {"id":id,"dataType":dataType}, function(data) {
			wapNetworkService.doFollowEdit(dataType,data,isBack);
		}, 'json').error(function(data) {
			console.log('[error] wapNetwork--followInfo error.');
		});
	},
	
	//提交申请
	doSave : function(dataType){
		showLoading("正在处理……");
		//ajax表单提交
		var options = {
			url : storage.get(Constants.MOBILE_CONTEXT_PATH)+"/common/open/customer/customer-save.action",
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
	  	$("#customerInfoForm").ajaxSubmit(options);
	},
	
	//保存跟进人
	doSaveFollowMan : function(id,roleType,dataType){
		//ajax表单提交
		showLoading("正在处理……");
		var options = {
				url : storage.get(Constants.MOBILE_CONTEXT_PATH)+"/common/open/customer/customer-doSaveFollowMan.action",
			    data: {
			    		'id': id,
		                'dataType': dataType,
		                'roleType': roleType
		            },
				success : function(data){
					closeMsgTip();
					
					if(data.result==Constants.SUCCESS){
						wap._listService.backList(dataType);
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
	
	//保存跟进信息
	doSaveFollowInfo : function(id,dataType,expiryDate,followUp){
		//ajax表单提交
		showLoading("正在处理……");
		var options = {
				url : storage.get(Constants.MOBILE_CONTEXT_PATH)+"/common/open/customer/customer-doSaveFollowInfo.action",
				success : function(data){
					closeMsgTip();
					
					if(data.result==Constants.SUCCESS){
						if(followUp=='05'||followUp=='09'){
							wap._listService.backList(dataType);
						}else{
							location.href='myCustomerFollow.html?dataType='+dataType+'&id='+id+'&expiryDate='+expiryDate;
						}
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
		$("#followInfoForm").ajaxSubmit(options);
	},
	//保存延期信息
	doSaveDelayInfo : function(id,dataType){
		//ajax表单提交
		showLoading("正在处理……");
		var options = {
				url : storage.get(Constants.MOBILE_CONTEXT_PATH)+"/common/open/customer/customer-doSaveDelayInfo.action",
				data: {
		    		'roleType': storage.get(Constants.ROLE_TYPE),
	            },
				success : function(data){
					closeMsgTip();
					
					if(data.result==Constants.SUCCESS){
						wap._listService.backList('2');
						//location.href='myCustomerFollow.html?dataType='+dataType+'&id='+id;
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
		$("#delayInfoForm").ajaxSubmit(options);
	},
	
	//放弃申请
	doGiveUpApply : function(dataType,id){
		//ajax表单提交
		showLoading("正在处理……");
		var options = {
				url : storage.get(Constants.MOBILE_CONTEXT_PATH)+"/common/open/customer/customer-doGiveUpApply.action",
				success : function(data){
					closeMsgTip();
					
					if(data.result==Constants.SUCCESS){
						wap._listService.backList(dataType);
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
	//删除申请
	doDelete : function(dataType,id){
		//ajax表单提交
		showLoading("正在处理……");
		var options = {
				url : storage.get(Constants.MOBILE_CONTEXT_PATH)+"/common/open/customer/customer-doDelete.action",
				data: {
			    		'id': id,
		            },
				success : function(data){
					closeMsgTip();
					
					if(data.result==Constants.SUCCESS){
						wap._listService.backList(dataType);
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
	
	//审核
	doAudit : function(dataType,id){
		//ajax表单提交
		showLoading("正在处理……");
		var options = {
				url : storage.get(Constants.MOBILE_CONTEXT_PATH)+"/common/open/customer/customer-doAudit.action",
				success : function(data){
					closeMsgTip();
					
					if(data.result==Constants.SUCCESS){
						wap._listService.backList(dataType);
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