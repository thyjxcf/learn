var wapNetworkService = {
		
	// 客户管理列表信息
	doLoadList : function(dataType,searchStr,customerType,customerRegion,customerStatus,data){
		wapNetworkService._service._loadList(dataType,searchStr,customerType,customerRegion,customerStatus,data);
	},
	
	// 客户管理跟踪详情信息
	doFollowDetail : function(dataType,data){
		wapNetworkService._service._doFollowDetail(dataType,data);
	},
	// 客户管理申请信息
	doLoadApplyDetail : function(id,data,isBack){
		wapNetworkService._service._doLoadApplyDetail(id,data,isBack);
	},
	
	// 客户管理查看信息
	doCanReadDetail : function(dataType,data,isBack){
		wapNetworkService._service._doLoadCanReadDetail(dataType,data,isBack);
	},
	
	//审核查看信息
	doAuditDetail : function(dataType,data){
		wapNetworkService._service._doAuditDetail(dataType,data);
	},
	//资源库查看信息
	doZYKDetail : function(dataType,data,isBack){
		wapNetworkService._service._doZYKDetail(dataType,data,isBack);
	},
	//跟踪记录编辑
	doFollowEdit : function(dataType,data,isBack){
		wapNetworkService._service._doFollowEdit(dataType,data,isBack);
	},
	
	//私有方法
	_service : {
		_loadList : function(dataType,searchStr,customerType,customerRegion,customerStatus,data) {
			if(data.result==Constants.SUCCESS){
				WapPage = data.page;
				storage.set(WapConstants.WR_SEARCH_STR, searchStr);
				
				var stateChoiseStr = '';
				if(dataType==WapConstants.DATA_TYPE_1||dataType==WapConstants.DATA_TYPE_6){
					stateChoiseStr+='<li dataval="01"><span>初步接洽<i class="icon-img icon-current"></i></span></li>';
					stateChoiseStr+='<li dataval="02"><span>达成意向<i class="icon-img icon-current"></i></span></li>';
					stateChoiseStr+='<li dataval="03"><span>机构开设<i class="icon-img icon-current"></i></span></li>';
					stateChoiseStr+='<li dataval="04"><span>开试听课<i class="icon-img icon-current"></i></span></li>';
					stateChoiseStr+='<li dataval="05"><span>跟踪结束<i class="icon-img icon-current"></i></span></li>';
					stateChoiseStr+='<li dataval="06"><span>签订合同<i class="icon-img icon-current"></i></span></li>';
					stateChoiseStr+='<li dataval="07"><span>开正式课<i class="icon-img icon-current"></i></span></li>';
					stateChoiseStr+='<li dataval="08"><span>持续开课<i class="icon-img icon-current"></i></span></li>';
					stateChoiseStr+='<li dataval="09"><span>终止合作<i class="icon-img icon-current"></i></span></li>';
				}else if(dataType==WapConstants.DATA_TYPE_7||dataType==WapConstants.DATA_TYPE_8||dataType==WapConstants.DATA_TYPE_9){
					/*stateChoiseStr+='<li dataval="1"><span>待审核<i class="icon-img icon-current"></i></span></li>';
					stateChoiseStr+='<li dataval="2"><span>已通过<i class="icon-img icon-current"></i></span></li>';
					stateChoiseStr+='<li dataval="3"><span>未通过<i class="icon-img icon-current"></i></span></li>';*/
					stateChoiseStr+='<li dataval="0"><span>未提交<i class="icon-img icon-current"></i></span></li>';
					stateChoiseStr+='<li dataval="5"><span>待初审<i class="icon-img icon-current"></i></span></li>';
					stateChoiseStr+='<li dataval="7"><span>初审未通过<i class="icon-img icon-current"></i></span></li>';
					stateChoiseStr+='<li dataval="6"><span>待复审<i class="icon-img icon-current"></i></span></li>';
					stateChoiseStr+='<li dataval="9"><span>复审通过<i class="icon-img icon-current"></i></span></li>';
					stateChoiseStr+='<li dataval="8"><span>复审未通过<i class="icon-img icon-current"></i></span></li>';
				}else{
					stateChoiseStr+='<li dataval="0"><span>未提交<i class="icon-img icon-current"></i></span></li>';
					stateChoiseStr+='<li dataval="5"><span>待初审<i class="icon-img icon-current"></i></span></li>';
					stateChoiseStr+='<li dataval="7"><span>初审未通过<i class="icon-img icon-current"></i></span></li>';
					stateChoiseStr+='<li dataval="6"><span>待复审<i class="icon-img icon-current"></i></span></li>';
					stateChoiseStr+='<li dataval="9"><span>复审通过<i class="icon-img icon-current"></i></span></li>';
					stateChoiseStr+='<li dataval="8"><span>复审未通过<i class="icon-img icon-current"></i></span></li>';
				}
				if(dataType!=WapConstants.DATA_TYPE_5){
					$("#stateChoise").html(stateChoiseStr);
				}
				//筛选-----选中
				if(!isNotBlank(customerType)){
					customerType='';
					$("#customerTypeUl").find("li[dataval='']").addClass("current");
				}else{
					$("#customerTypeUl").find("li[dataval='"+customerType+"']").addClass("current");
				}
				if(!isNotBlank(customerStatus)){
					customerType='';
					$("#stateChoise").find("li[dataval='']").addClass("current");
				}else{
					$("#stateChoise").find("li[dataval='"+customerStatus+"']").addClass("current");
				}
				$('.filter-layer li').click(function(e){
					e.preventDefault();
					if(!$('.filter-layer').hasClass('filter-layer-full')){
						$(this).addClass('current').siblings('li').removeClass('current');
					}else{
						$('.filter-layer li').removeClass('current');
						$(this).addClass('current');
					};
				})
				var array = data.result_array;
				if(array.length > 0){
					var listHtmlStr = '';
					$.each(array, function(index, json) {
						var	htmlInfo = '';
						var progressStateVal = json.progressStateVal;
						var expiryDate = '';
						if(progressStateVal=='01'||progressStateVal=='02'||progressStateVal=='03'||progressStateVal=='04'){
							expiryDate = json.expiryDate;
						}else{
							expiryDate=1;
						}
						if(dataType==WapConstants.DATA_TYPE_1||dataType==WapConstants.DATA_TYPE_6){
							if(json.isMine==0||dataType==WapConstants.DATA_TYPE_6||progressStateVal=='05'||progressStateVal=='09'){
								htmlInfo += 'location.href=\'customerIcanReadDetail.html?dataType='+dataType+"&id="+json.id;
								if(progressStateVal=='05'||progressStateVal=='09'){
									htmlInfo += "&isOver=1";
								}
							}else{
								htmlInfo += 'location.href=\'myCustomerFollow.html?dataType='+dataType+"&id="+json.id+'&expiryDate='+expiryDate;
							}
						}else if(dataType==WapConstants.DATA_TYPE_5){
							htmlInfo += 'location.href=\'customerLibrary.html?dataType='+dataType+"&id="+json.id;
						}else if(dataType==WapConstants.DATA_TYPE_3&&!isNotBlank(json.auditState)){
							htmlInfo += 'location.href=\'customerApplyEdit.html?dataType='+dataType+"&id="+json.id;
						}else{//dataType in {2,3,4,7,8,9}
							htmlInfo += 'location.href=\'customerAudit.html?dataType='+dataType+"&id="+json.id+'&auditState='+json.auditState
							+'&applyId='+json.applyId+'&auditId='+json.auditId;
						}
						htmlInfo += '\''
						listHtmlStr += '<li onclick="'+htmlInfo+'">';
						listHtmlStr += '<i class="radio"></i>';
						if(dataType==WapConstants.DATA_TYPE_1||dataType==WapConstants.DATA_TYPE_6){
							listHtmlStr += '<span class="type type-blue">'+json.progressState+'</span>';
						}else if(dataType==WapConstants.DATA_TYPE_5){
							listHtmlStr += '<span class="type type-blue">'+json.type+'</span>';
						}else if(dataType==WapConstants.DATA_TYPE_7||dataType==WapConstants.DATA_TYPE_8||dataType==WapConstants.DATA_TYPE_9){
							/*if(json.auditState==1){
								listHtmlStr += '<span class="type type-gray">待审核</span>';
							}else if(json.auditState==2){
								listHtmlStr += '<span class="type type-green">已通过</span>';
							}else if(json.auditState==3){
								listHtmlStr += '<span class="type type-red">未通过</span>';
							}*/
							if(json.auditState==5){
								listHtmlStr += '<span class="type type-gray">待初审</span>';
							}else if(json.auditState==6){
								listHtmlStr += '<span class="type type-gray">待复审</span>';
							}else if(json.auditState==7){
								listHtmlStr += '<span class="type type-red">初审未通过</span>';
							}else if(json.auditState==8){
								listHtmlStr += '<span class="type type-red">复审未通过</span>';
							}else if(json.auditState==9){
								listHtmlStr += '<span class="type type-green">复审通过</span>';
							}else{
								listHtmlStr += '<span class="type type-blue">未提交</span>';
							}
						}else{
							if(json.auditState==5){
								listHtmlStr += '<span class="type type-gray">待初审</span>';
							}else if(json.auditState==6){
								listHtmlStr += '<span class="type type-gray">待复审</span>';
							}else if(json.auditState==7){
								listHtmlStr += '<span class="type type-red">初审未通过</span>';
							}else if(json.auditState==8){
								listHtmlStr += '<span class="type type-red">复审未通过</span>';
							}else if(json.auditState==9){
								listHtmlStr += '<span class="type type-green">复审通过</span>';
							}else{
								listHtmlStr += '<span class="type type-blue">未提交</span>';
							}
						}
						listHtmlStr +=  '<p class="tt f-15">';
						if(dataType==WapConstants.DATA_TYPE_1&&json.isMine==0){
							listHtmlStr +=  '<span class="icon-img icon-plane"></span>';
						}
						listHtmlStr += json.name+'</p>';
						listHtmlStr +=  '<p class="dd f-14">';
						if(dataType==WapConstants.DATA_TYPE_1){
							var dateVal = json.expiryDate;
							
							if(progressStateVal=='06'||progressStateVal=='07'||progressStateVal=='08'){
								dateVal='不限';
							}else{
								if(Number(dateVal)<0){
									dateVal = '<font class="c-red">'+dateVal+'天</font>';
								}else{
									dateVal += '天';
								}
							}
							listHtmlStr +=  '<span>有效期：'+dateVal+'</span>';
						}else{
							listHtmlStr +=  '<span>'+json.region+'</span>';
						}
						if(dataType!=WapConstants.DATA_TYPE_5){
							if(dataType==WapConstants.DATA_TYPE_6||(dataType==WapConstants.DATA_TYPE_1&&json.isMine==0)){
								listHtmlStr +=  ' <span class="c-blue">'+json.applyUserName+'</span>';
							}else{
								listHtmlStr +=  '<span>'+json.applyDate+'</span>';
							}
						}
						listHtmlStr +=  '</p>';
						listHtmlStr += '</li>';
					});
					$("#list").append(listHtmlStr);
					
					if (WapPage.pageIndex >= WapPage.maxPageIndex) {
//				    	$('.loading-more').html('<a href="javascript:void(0)">暂时没有更多的记录哦</a>');
				    	$('.loading-more').html('');
				    	$('.loading-more').unbind();
				    }
					
					$("#listDiv").show();
					$('#empty').hide();
				}else{
					//内容为空
					$('#listDiv').hide();
					$('#empty').show();
				}
			}
	    },
	    
	    //查看详情
	    _doLoadCanReadDetail : function(dataType,data,isBack){
	    	if(data.result==Constants.SUCCESS){
	    		var obj = data.result_object;
	    		if(isBack==WapConstants.BACK_LIST_GLAG_1){
	    			$("#followUserName").val(storage.get(WapConstants.CUSTOMER_FOLLOW_USER_NAME));
	    			$("#followUser").val(storage.get(WapConstants.CUSTOMER_FOLLOW_USER_ID));
	    		}else{
	    			wap._editService.clearCache();//清除缓存
	    			$("#followUserName").val(obj.followUserName);
    				$("#followUser").val(obj.followUser);
	    		}
	    		$("#name").html(obj.officeCustomerInfo.name);
	    		$("#nameStr").val(obj.officeCustomerInfo.name);
    			$("#nickName").html(obj.officeCustomerInfo.nickName);
    			$("#region").html(obj.officeCustomerInfo.regionName);
    			$("#type").html(obj.officeCustomerInfo.type);
    			$("#infoSource").html(obj.officeCustomerInfo.infoSource);
    			$("#backgroundInfo").html(obj.officeCustomerInfo.backgroundInfo);
    			$("#progressState").html(obj.officeCustomerInfo.progressState);
    			$("#product").html(obj.officeCustomerInfo.product);
    			$("#contact").html(obj.officeCustomerInfo.contact);
    			$("#phone").html(obj.officeCustomerInfo.phone);
    			$("#contactInfo").html(obj.officeCustomerInfo.contactInfo);
    			$("#applyDate").html(obj.applyDate);
    			$("#firstAuditTime").html(obj.firstAuditTime);
    			$("#finallyAuditTime").html(obj.finallyAuditTime);
    			
    			//跟踪记录
    			var followArray=obj.followArray;
    			$("#followInfo .ui-axis").html('');
				if(followArray.length > 0){
					var	htmlInfo  = '';
					$.each(followArray, function(index, json) {
						htmlInfo += '<li>';
						htmlInfo += '<span class="icon-img icon-round1"></span>';
						htmlInfo += '<div class="item f-13">';
						htmlInfo += '<p class="li"><span class="time f-10">'+json.createTime+'</span>';
						if(isNotBlank(json.copyUserName)){
							htmlInfo += '<span class="dt">抄送：</span><span class="dd">'+json.copyUserName+'</span>';
						}
						htmlInfo += '</p>';
						htmlInfo += '<p class="li"><span class="dt">跟进状态：</span><span class="dd">'+json.progressState+'</span></p>';
						var remark="";
						if(isNotBlank(json.remark)){
							remark=json.remark;
						}
						htmlInfo += '<p class="li"><span class="dt">跟进信息：</span><span class="dd">'+remark+'</span></p>';
						htmlInfo += '</div>';
						htmlInfo += '</li>';
					});
					$("#followInfo .ui-axis").html(htmlInfo);
				}
				if(WapConstants.DATA_TYPE_1==storage.get(Constants.ROLE_TYPE)){
//					var	str = '<input type="text" disabled class="txt" value="隔壁老王" placeholder="">'
//					$("#followUser").hide();
//					$("#saveFollowMan").hide();
//					$("#followUserTxt").html(str);
					$("#followUserLi").unbind('click');
					$("#followUserTxt").next('.icon-arrow').hide();
					$("#saveFollowMan").hide();
				}
	    	}
	    },
	    
	    //审核详情
	    _doAuditDetail : function(dataType,data){
	    	if(data.result==Constants.SUCCESS){
	    		var obj = data.result_object;
	    		$("#name").html(obj.officeCustomerInfo.name);
	    		$("#nickName").html(obj.officeCustomerInfo.nickName);
	    		$("#type").html(obj.officeCustomerInfo.type);
	    		$("#region").html(obj.officeCustomerInfo.regionName);
	    		$("#infoSource").html(obj.officeCustomerInfo.infoSource);
	    		$("#backgroundInfo").html(obj.officeCustomerInfo.backgroundInfo);
	    		$("#progressState").html(obj.officeCustomerInfo.progressState);
	    		$("#product").html(obj.officeCustomerInfo.product);
	    		$("#contact").html(obj.officeCustomerInfo.contact);
	    		$("#phone").html(obj.officeCustomerInfo.phone);
	    		$("#contactInfo").html(obj.officeCustomerInfo.contactInfo);
	    		if(obj.auditState==7){
	    			$("#noPassReason .dd").html(obj.deptOpinion);
	    		}else{
	    			$("#noPassReason .dd").html(obj.operateOpinion);
	    		}
	    		$("#applyUser .dd").html(obj.applyUserName);
	    		$("#forwardUser .dd").html(obj.followUserName);
	    		$("#userArea .dd").html(obj.deptName);
	    		
	    		
	    		$("#auditId").val(obj.auditId);
	    		$("#customerId").val(obj.customerId);
	    		$("#applyType").val(obj.applyType);
	    		$("#auditStatus").val(obj.auditStatus);
	    		var auditState = Number(obj.auditState);
	    		switch (auditState)
	    		{
	    		case 5:
	    			$("#iconState").addClass("icon-state2");
	    			break;
	    		case 6:
	    			$("#iconState").addClass("icon-state5");
	    			break;
	    		case 7:
	    			$("#iconState").addClass("icon-state1");
	    			break;
	    		case 8:
	    			$("#iconState").addClass("icon-state4");
	    			break;
	    		case 9:
	    			$("#iconState").addClass("icon-state6");
	    			break;
	    		}
	    		if(Number(dataType)<5){
		    		if(obj.applyType==0){//新
		    			$(".ui-footer .submit").html("编辑申请");
//		    			$(".ui-footer .hideSpan").hide();
		    			$(".ui-footer .no-pass").html("删除申请");
		    		}else if(obj.applyType==1){//老
		    			$(".ui-footer").hide();
//		    			$(".ui-footer .no-pass").html("放弃申请");
		    		}else{
//		    			$(".ui-footer .submit").html("再次申请");
//		    			$(".ui-footer .no-pass").html("放弃申请");
		    		}
		    		if(dataType==2||dataType==4||(obj.auditState!=7&&obj.auditState!=8)){//初审未通过---显示再次申请
		    			$(".ui-footer ").hide();
		    		}
		    		if(obj.auditState!=8&&obj.auditState!=7){//未通过显示原因
		    			$("#noPassReason").hide();
		    		}
		    		if(dataType==4){
		    			$("#applyInfoUl").show();
		    		}
	    		}else{
	    			if(obj.auditState!=8&&obj.auditState!=7){//未通过显示原因
		    			$("#noPassReason").hide();
		    		}
	    			$("#applyInfoUl").show();
	    			if(dataType!=9){
	    				$("#forwardUser").hide();
	    			}
	    			$(".ui-footer .submit").html("通过");
	    			$(".ui-footer .no-pass").html("不通过");
//	    			layer('.no-pass','.ui-layer','.close','不通过原因');
	    			if(obj.waitAudit==1){//待审核
	    				$("#iconState").hide();
		    		}else{
		    			$(".ui-footer ").hide();
		    		}
	    		}
	    	}
	    },
	    //资源库详情
	    _doZYKDetail : function(dataType,data,isBack){
	    	if(data.result==Constants.SUCCESS){
	    		var obj = data.result_object;
	    		$("#name").html(obj.officeCustomerInfo.name);
	    		$("#nickName").html(obj.officeCustomerInfo.nickName);
	    		$("#type").html(obj.officeCustomerInfo.type);
	    		$("#region").html(obj.officeCustomerInfo.regionName);
	    		$("#infoSource").html(obj.officeCustomerInfo.infoSource);
	    		$("#backgroundInfo").html(obj.officeCustomerInfo.backgroundInfo);
	    		$("#product").html(obj.officeCustomerInfo.product);
	    		$("#contact").html(obj.officeCustomerInfo.contact);
	    		$("#phone").html(obj.officeCustomerInfo.phone);
	    		$("#contactInfo").html(obj.officeCustomerInfo.contactInfo);
	    		var progressStateMcodes = obj.progressStateMcodes;
	    		var progressState = obj.officeCustomerInfo.progressState;
	    		$("#progressState").html('');
	    		var htmlStr = '<option value="" disabled></option>';
	    		if(progressStateMcodes.length > 0){
	    			$.each(progressStateMcodes, function(index, json) {
	    				if(isNotBlank(progressState) && json.thisId == progressState){
	    					htmlStr += '<option value="'+json.thisId+'" selected="selected">'+json.content+'</option>';
	    				}else{
	    					htmlStr += '<option value="'+json.thisId+'">'+json.content+'</option>';
	    				}
	    			});
	    		}
	    		$("#progressState").html(htmlStr);
	    		if(isBack==WapConstants.BACK_LIST_GLAG_1){
	    			$("#followUserName").val(storage.get(WapConstants.CUSTOMER_FOLLOW_USER_NAME));
	    			$("#followUser").val(storage.get(WapConstants.CUSTOMER_FOLLOW_USER_ID));
	    		}else{
	    			wap._editService.clearCache();//清除缓存
	    			$("#followUserName").val(obj.followUserName);
    				$("#followUser").val(obj.followUser);
	    		}
	    		wap._zykDetailService.initBind();
	    	}
	    },
	    
	    //跟踪详情
	    _doFollowDetail : function(dataType,data){
	    	if(data.result==Constants.SUCCESS){
	    		//跟踪记录
	    		var obj = data.result_object;
	    		var followArray=obj.followArray;
	    		if(followArray.length > 0){
	    			var	htmlInfo  = '';
	    			$.each(followArray, function(index, json) {
	    				htmlInfo += '<li>';
	    				htmlInfo += '<span class="icon-img icon-round1"></span>';
	    				htmlInfo += '<div class="item f-13">';
	    				htmlInfo += '<p class="li"><span class="time f-10">'+json.createTime+'</span>';
						if(isNotBlank(json.copyUserName)){
							htmlInfo += '<span class="dt">抄送：</span><span class="dd">'+json.copyUserName+'</span>';
						}
						htmlInfo += '</p>';
						htmlInfo += '<p class="li"><span class="dt">跟进状态：</span><span class="dd">'+json.progressState+'</span></p>';
						var remark="";
						if(isNotBlank(json.remark)){
							remark=json.remark;
						}
						htmlInfo += '<p class="li"><span class="dt">跟进信息：</span><span class="dd">'+remark+'</span></p>';
						htmlInfo += '</div>';
						htmlInfo += '</li>';
	    			});
	    			$("#followInfo .ui-axis").append(htmlInfo);
	    		}
	    		var delayTime = obj.delayTime;
	    		var delayCurrentTime = obj.delayCurrentTime;
	    		var delayEndTime = obj.delayEndTime;
	    		$("#delayTime").html(delayTime);
	    		$("#delayCurrentTime").html(delayCurrentTime);
	    		$("#delayEndTime").html(delayEndTime);
	    		if(obj.applyType==2){
					$("#delayLi").hide();
				}
	    	}
	    },
	    
	    //申请信息
	    _doLoadApplyDetail : function(id,data,isBack){
	    	if(data.result==Constants.SUCCESS){
	    		var obj = data.result_object;
	    		var type='';
	    		var product='';
	    		var progressState='';
	    		if(isBack==WapConstants.BACK_LIST_GLAG_1){
	    			$("#name").val(storage.get(WapConstants.CUSTOMER_EDIT_NAME));
	    			$("#nickName").val(storage.get(WapConstants.CUSTOMER_EDIT_NICKNAME));
	    			$("#showRegionName").val(storage.get(WapConstants.CUSTOMER_EDIT_REGIONNAME));
    				$("#showRegionVal").val(storage.get(WapConstants.CUSTOMER_EDIT_REGION));
	    			$("#infoSource").val(storage.get(WapConstants.CUSTOMER_EDIT_INFOSOURCE));
	    			$("#backgroundInfo").val(storage.get(WapConstants.CUSTOMER_EDIT_BACKGROUND_INFO));
	    			$("#contact").val(storage.get(WapConstants.CUSTOMER_EDIT_CONTACT));
	    			$("#phone").val(storage.get(WapConstants.CUSTOMER_EDIT_PHONE));
	    			$("#contactInfo").val(storage.get(WapConstants.CUSTOMER_EDIT_CONTACT_INFO));
	    			$("#typeTxt").val(storage.get(WapConstants.CUSTOMER_EDIT_TYPETXT));
	    			$("#followUserName").val(storage.get(WapConstants.CUSTOMER_FOLLOW_USER_NAME));
	    			$("#followUser").val(storage.get(WapConstants.CUSTOMER_FOLLOW_USER_ID));
	    			type =storage.get(WapConstants.CUSTOMER_EDIT_TYPE);
	    			product = storage.get(WapConstants.CUSTOMER_EDIT_PRODUCT);
	    			progressState = storage.get(WapConstants.CUSTOMER_EDIT_PROGRESS_STATE);
	    		}else{
	    			wap._editService.clearCache();//清除缓存
	    			if(isNotBlank(id)){//编辑
	    				$("#name").val(obj.name);
	    				$("#nickName").val(obj.nickName);
	    				$("#showRegionName").val(obj.regionName);
	    				$("#showRegionVal").val(obj.region);
	    				$("#infoSource").val(obj.infoSource);
	    				$("#backgroundInfo").val(obj.backgroundInfo);
	    				$("#contact").val(obj.contact);
	    				$("#phone").val(obj.phone);
	    				$("#contactInfo").val(obj.contactInfo);
	    				$("#typeTxt").val(obj.typeTxt);
	    				$("#followUserName").val(obj.followUserName);
	    				$("#followUser").val(obj.followUser);
	    			}
	    			type = obj.type;
	    			product = obj.product;
	    			progressState = obj.progressState;
	    		}
	    		$("#typeVal").val(obj.type);
	    		var typeMcodes = obj.typeMcodes;
	    		$("#type").html('');
	    		var typeStr = '<option value="" disabled></option>';
	    		if(typeMcodes.length > 0){
	    			$.each(typeMcodes, function(index, json) {
	    				if(isNotBlank(type) && json.thisId == type){
	    					typeStr += '<option value="'+json.thisId+'" selected="selected">'+json.content+'</option>';
	    				}else{
	    					typeStr += '<option value="'+json.thisId+'">'+json.content+'</option>';
	    				}
	    			});
	    		}
	    		$("#type").html(typeStr);
	    		
		    	var progressStateMcodes = obj.progressStateMcodes;
		    	$("#progressState").html('');
		    	var htmlStr = '<option value="" disabled></option>';
	    		if(progressStateMcodes.length > 0){
	    			$.each(progressStateMcodes, function(index, json) {
	    				if(isNotBlank(progressState) && json.thisId == progressState){
	    					htmlStr += '<option value="'+json.thisId+'" selected="selected">'+json.content+'</option>';
	    				}else{
	    					htmlStr += '<option value="'+json.thisId+'">'+json.content+'</option>';
	    				}
	    			});
	    		}
	    		$("#progressState").html(htmlStr);
	    		
	    		var productMcodes = obj.productMcodes;
	    		
	    		$("#product").html('');
	    		var productStr = '<option value="" disabled></option>';
	    		var arr = new Array();
	    		if(isNotBlank(product)){
	    			var pArr = product.split(',');
	    			for(pro in pArr){
	    				arr.push(pArr[pro].trim());
	    			}
	    		}
	    		if(productMcodes.length > 0){
	    			$.each(productMcodes, function(index, json) {
	    				if(isNotBlank(product) &&($.inArray(json.thisId,arr)>-1)){
	    					productStr += '<option value="'+json.thisId+'" selected="selected">'+json.content+'</option>';
	    				}else{
	    					productStr += '<option value="'+json.thisId+'">'+json.content+'</option>';
	    				}
	    			});
	    		}
	    		$("#product").html(productStr);
	    		wap._editService.initBind();
	    		if(!isNotBlank(type)||type=='undefined'){
	    			$('#type_dummy').attr('placeholder','请选择客户类别（必填）').val('');
	    			$('#type').val('');
	    		}
	    		if(!isNotBlank(progressState)||progressState=='undefined'){
	    			$('#progressState_dummy').attr('placeholder','请选择客户状态（必填）').val('');
	    			$('#progressState').val('');
	    		}
	    		if(!isNotBlank(product)||product=='undefined'){
	    			$('#product_dummy').attr('placeholder','请选择意向合作产品（必填）').val('');
	    			$('#product').val('');
	    		}
	    	}
	    },
	    
	    //编辑
	    _doFollowEdit : function(dataType,data,isBack){
	    	if(data.result==Constants.SUCCESS){
	    		var obj = data.result_object;
	    		var progressStateMcodes = obj.progressStateMcodes;
	    		var copyTo = obj.copyTo;
	    		var copyToName = obj.copyToName;
	    		var progressState = '';
	    		if(isBack==WapConstants.BACK_LIST_GLAG_1){
	    			$("#copyToName").val(storage.get(WapConstants.CUSTOMER_FOLLOW_USER_NAME));
	    			$("#copyTo").val(storage.get(WapConstants.CUSTOMER_FOLLOW_USER_ID));
	    			$("#followInfo").val(storage.get(WapConstants.CUSTOMER_FOLLOW_INFO));
	    			progressState = storage.get(WapConstants.CUSTOMER_FOLLOW_UP);
	    		}else{
	    			wap._followEditService.clearCache();//清除缓存
	    			$("#copyTo").val(copyTo);
	    			$("#copyToName").val(copyToName);
	    			progressState = obj.progressState;
	    		}
	    		
		    	$("#followUp").html('');
		    	var htmlStr = '';
	    		if(progressStateMcodes.length > 0){
	    			$.each(progressStateMcodes, function(index, json) {
	    				if(isNotBlank(progressState) && json.thisId == progressState){
	    					htmlStr += '<option value="'+json.thisId+'" selected="selected">'+json.content+'</option>';
	    				}else{
	    					htmlStr += '<option value="'+json.thisId+'">'+json.content+'</option>';
	    				}
	    			});
	    		}
	    		$("#followUp").html(htmlStr);
	    		wap._followEditService.initBind();
	    		if(!isNotBlank(progressState)||progressState=='undefined')
			    	$('#followUp_dummy').attr('placeholder','请选择跟进状态（必填）').val('');
	    		$('#followUp').val('');
	    		
	    	}
	    },
	    
	}   
};