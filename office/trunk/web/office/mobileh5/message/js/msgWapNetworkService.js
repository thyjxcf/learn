var wapNetworkService = {
		
	// 邮件消息列表信息
	doLoadList : function(dataType, searchStr, data){
		wapNetworkService._service._loadList(dataType, searchStr, data);
	},
	//消息详情查看
	doLoadDetail : function(dataType, data){
		wapNetworkService._service._loadDetail(dataType, data);
	},
	//收件箱详情--会话列表
	doLoadReceivedDetailList : function(userId,dataType, data){
		wapNetworkService._service._loadReceivedDetailList(userId,dataType, data);
	},
	doLoadReceivedDetail : function(dataType, data, obj){
		wapNetworkService._service._loadReceivedDetail(dataType, data, obj);
	},
	doCollectMsg : function(data,obj){
		wapNetworkService._service._loadCollect(data,obj);
	},
	
	doEditMsg : function(data,id,dataType){
		wapNetworkService._service._loadEdit(data,id,dataType);
	},
	
	//私有方法
	_service : {
		
		_loadList : function(dataType, searchStr, data) {
			if(data.result==Constants.SUCCESS){
				WapPage = data.page;
				var msgCount = data.msgCount;
				var array = data.result_array;
				var cou=array.length;
				if(msgCount>0){
					if(msgCount>99){
						$("#receiveBox").html("收件箱(99+)");
					}else{
						$("#receiveBox").html("收件箱("+msgCount+")");
					}
				}else{
					$("#receiveBox").html("收件箱("+0+")");
				}
				
				if(cou>0){
					var conHtmlStr="";
					for(var i=0;i<cou;i++){
						var obj = array[i];
						var htmlStr = '<li';
						if(dataType == WapConstants.DATA_TYPE_1){
							htmlStr+=' onclick="location.href=\'messageEdit.html?replyMsgId='+obj.replyMsgId+'&id='+obj.msgId+'&editType='+WapConstants.EDIT_TYPE_5+'&dataType='+dataType+'\';"';
						}else if(dataType == WapConstants.DATA_TYPE_2){
							htmlStr+=' onclick="location.href=\'messageDetail.html?replyMsgId='+obj.replyMsgId+'&id='+obj.msgId+'&dataType='+dataType+'\';"';
						}else{
							htmlStr+=' onclick="location.href=\'messageReceivedDetail.html?replyMsgId='+obj.replyMsgId+'&id='+obj.msgId+'&dataType='+dataType+'\';"';
						}
						htmlStr+='>';
						if(dataType == WapConstants.DATA_TYPE_3){
						}
						
						if(isNotBlank(obj.photoUrl)){
							htmlStr+='<img src="'+obj.photoUrl+'" class="media"/>';
						}else{
							htmlStr += '<span class="place-avatar" style="background:'+getBackgroundColor(obj.photoUsername)+';">'+getLast2Str(obj.photoUsername)+'</span>';
						}
						htmlStr+='<p class="tt f-16">';
						if(!obj.isRead && dataType==WapConstants.DATA_TYPE_3){
							htmlStr+='<span class="new"></span>  ';
						}
						if(obj.isEmergency){
							htmlStr+='<span class="flag"></span>';//旗子
						}
						htmlStr+='<span class="time f-12">';
						htmlStr+=obj.dateStr+'</span>      ';
						htmlStr+=obj.photoUsername;
						if(obj.hasAttached){
							htmlStr+='<span class="icon-img icon-acc"></span>';
						}
						if(obj.hasStar){
							htmlStr+='<span class="icon-img icon-start"></span>';
						}
						htmlStr+='</p><p class="dd f-14">';
						if(obj.isWithdraw){
							htmlStr+='已被撤回';
						}else{
							htmlStr+=obj.title;
						}
						htmlStr+='</p></li>';
						conHtmlStr+=htmlStr;
					}
					$('.mail-list').append(conHtmlStr);
					if (WapPage.pageIndex >= WapPage.maxPageIndex) {
				    	$('.loading-more').html('<a href="javascript:void(0)">暂时没有更多的记录哦</a>');
				    	$('.loading-more').unbind();
				    }
					$('#list').show();
					$('#empty').hide();
				}else{
					$('#list').hide();
					$('#empty').show();
				}	
			}else{
				viewToast('操作失败');
			}
	    },
	    
	    _bandLoadDetail:function(userId,dataType,obj){
	    	//点击加载详细
			var msgId=$(obj).find('.hidMsgId').val();
			var replyMsgId=$(obj).find('.hidReplyMsgId').val();
			if(!$(obj).hasClass('already-open')){
				$(obj).addClass('already-open');
				wapNetwork.doGetReceivedDetail(userId,dataType, msgId,obj);
			}
			if($(obj).hasClass('isWithdraw')){
				$(".abtn-reply").hide();
				$(".abtn-reply-all").hide();
			}else{
				$(".abtn-reply").show();
				$(".abtn-reply-all").show();
			}
			var sendMsgId=$(obj).find('.hidSendMsgId').val();
			if($(obj).hasClass('detail-item-close')){
				$(obj).addClass('active');
				$(obj).addClass('detail-item-open').removeClass('detail-item-close').siblings('.detail-item').addClass('detail-item-close').removeClass('detail-item-open');
				if($(obj).find(".isSendInfo").val()=='false'){
					$('#msgFooter').show();
				}else{
					$('#msgFooter').hide();
				}
				if($(obj).find(".hasStar").val()=='true'){
					$('.abtn-collect').addClass('active');
				}else{
					$('.abtn-collect').removeClass('active');
				}
				var th=obj;
				$('.abtn-collect').unbind();
				$('.abtn-collect').on('click',function(){
					if($(th).find(".hasStar").val()=='true'){
						wapNetwork.doCollect(dataType, msgId,'false',th);
					}else{
						wapNetwork.doCollect(dataType, msgId,'true',th);
					}
				});
				$('.abtn-del').unbind();
				$('.abtn-del').on('click',function(){
					wap._receiveDetailService.del(dataType,msgId);
				});
				$('.abtn-reply').unbind();
				$('.abtn-reply').on('click',function(){
					wap._receiveDetailService.edit(dataType,sendMsgId,replyMsgId,WapConstants.EDIT_TYPE_2);
				});
				$('.abtn-reply-all').unbind();
				$('.abtn-reply-all').on('click',function(){
					wap._receiveDetailService.edit(dataType,sendMsgId,replyMsgId,WapConstants.EDIT_TYPE_3);
				});
			};
	    },
	    _loadReceivedDetailList : function(userId,dataType, data){
	    	$('#container').html("");
	    	if(data.result==Constants.SUCCESS){
	    		var array = data.result_array;
	    		var cou=array.length;
				if(cou>0){
					var conHtmlStr="";
					conHtmlStr+='<p class="detail-tt f-18">'+array[cou-1].title+'</p>'
					+'<div class="detail-wrap"><div class="detail-inner">';
					for(var i=0;i<cou;i++){
						var obj = array[i];
						var htmlStr = '';
						if(i==0){
							conHtmlStr+='<div class="detail-item detail-item-first detail-item-close ';
						}else{
							conHtmlStr+='<div class="detail-item detail-item-close ';
						}
						if(obj.isWithdraw){
							conHtmlStr+='isWithdraw';
						}
						conHtmlStr+='"><input type="hidden" class="hasStar" value="'+obj.hasStar+'">';
						conHtmlStr+='<input type="hidden" class="isEmergency" value="'+obj.isEmergency+'">';
						conHtmlStr+='<input type="hidden" class="isSendInfo" value="'+obj.isSendInfo+'">';
						conHtmlStr+='<input type="hidden" class="hidMsgId" value="'+obj.msgId+'">';
						conHtmlStr+='<input type="hidden" class="hidReplyMsgId" value="'+obj.replyMsgId+'">';
						conHtmlStr+='<input type="hidden" class="hidSendMsgId" value="">';
						conHtmlStr+='<span class="num f-18">'+(cou-i)+'</span>';
						conHtmlStr+='<span class="time">'+obj.dateStr+'</span>';
						conHtmlStr+='<span class="arrow"></span>'
							+'<div class="tt-wrap">';
						conHtmlStr+='<p class="tt-user">';
						conHtmlStr+='<a href="javascript:void(0)" class="show">显示详情</a>';
						conHtmlStr+='<span class="name-tt f-18">发件人：</span>';
						conHtmlStr+='<span class="name f-18">'+obj.sendUsername+'</span>';
						if(obj.hasAttached){
							conHtmlStr+='<span class="icon-img icon-acc"></span>';
						}
						if(obj.hasStar){
							conHtmlStr+='<span class="icon-img icon-start" style="display:display"></span>';
						}else{
							conHtmlStr+='<span class="icon-img icon-start" style="display:none"></span>';
						}
						conHtmlStr+='</p><div class="tt-des" style="display:none;">';
						conHtmlStr+='<p><span class="tt">收件人：</span>';
						conHtmlStr+='<span class="dd dd-name"></span></p>';
						conHtmlStr+='<p><span class="tt">时间：</span>';
						conHtmlStr+='<span class="dd">'+obj.dateStr+'</span></p></div>';
						conHtmlStr+='</div>';
						conHtmlStr+='<p class="des f-14">';
						if(obj.isWithdraw){
							conHtmlStr+='已被撤回';
						}else{
							conHtmlStr+=obj.simpleContent
						}
						conHtmlStr+='</p>';
						conHtmlStr+='<ul class="acc-wrap"></ul>';
						conHtmlStr+='</div>';
						
						conHtmlStr+=htmlStr;
					}
					conHtmlStr+='</div></div>';
					$('#container').append(conHtmlStr);
					if($(".detail-item").size()>0){
						wapNetworkService._service._bandLoadDetail(userId,dataType,$(".detail-item").get(0));
					}
					
					$('.detail-item').on('click', function(){
						wapNetworkService._service._bandLoadDetail(userId,dataType,this);
					});
					
					$('.detail-item').on('click', function(){
						$(this).removeClass('active');
					});
					
					$('.tt-user').on('click', function(e){
						e.stopPropagation();
						var $item=$(this).parents('.detail-item');
						var replyMsgId=$item.find('.hidReplyMsgId').val();
						if($item.hasClass('detail-item-open')){
							$item.addClass('detail-item-close').removeClass('detail-item-open');
							$('#msgFooter').hide();
						}else{
							//点击加载详细
							if(!$item.hasClass('already-open')){
								$item.addClass('already-open');
								var msgId=$item.find('.hidMsgId').val();
								wapNetwork.doGetReceivedDetail(userId,dataType, msgId,$item);
							}
							if($(this).hasClass('isWithdraw')){
								$(".abtn-reply").hide();
								$(".abtn-reply-all").hide();
							}else{
								$(".abtn-reply").show();
								$(".abtn-reply-all").show();
							}
							var sendMsgId=$item.find('.hidSendMsgId').val();
							$item.addClass('detail-item-open').removeClass('detail-item-close').siblings('.detail-item').addClass('detail-item-close').removeClass('detail-item-open');
							if($item.find(".isSendInfo").val()=='false'){
								$('#msgFooter').show();
							}else{
								$('#msgFooter').hide();
							}
							if($item.find(".hasStar").val()=='true'){
								$('.abtn-collect').addClass('active');
							}else{
								$('.abtn-collect').removeClass('active');
							}
							$('.abtn-collect').unbind();
							$('.abtn-collect').on('click',function(){
								if($item.find(".hasStar").val()=='true'){
									wapNetwork.doCollect(dataType, obj.msgId,'false',$item);
								}else{
									wapNetwork.doCollect(dataType, obj.msgId,'true',$item);
								}
							});
							$('.abtn-del').unbind();
							$('.abtn-del').on('click',function(){
								wap._receiveDetailService.del(dataType,msgId);
							});
							$('.abtn-reply').unbind();
							$('.abtn-reply').on('click',function(){
								wap._receiveDetailService.edit(dataType,sendMsgId,replyMsgId,WapConstants.EDIT_TYPE_2);
							});
							$('.abtn-reply-all').unbind();
							$('.abtn-reply-all').on('click',function(){
								wap._receiveDetailService.edit(dataType,sendMsgId,replyMsgId,WapConstants.EDIT_TYPE_3);
							});
							
						};
					});
					
					$('.tt-user .show').on('click', function(e){
						e.preventDefault();
						e.stopPropagation();
						var $des=$(this).parent('.tt-user').siblings('.tt-des');
						if(!$des.is(':visible')){
							$(this).text('隐藏详情');
							$des.show();
						}else{
							$(this).text('显示详情');
							$des.hide();
						};
					});
					
					$('.detail-tt').on('click', function(){
						$('.detail-item').removeClass('detail-item-open').addClass('detail-item-close');
						$('#msgFooter').hide();
					});
					
//					$('.detail-item-first').trigger("touchstart");
					
//					$('.detail-item-first').trigger("touchend");
					
				}
				
	    	}else{
	    		viewToast('操作失败');
			}
	    },
	    
	    _loadReceivedDetail : function(dataType, data, obj){
	    	if(data.result==Constants.SUCCESS){
		    	var oneObje=data.result_array;
		    	$(obj).find(".hidSendMsgId").val(oneObje.messageId);
		    	$(obj).find('.dd-name').html(oneObje.userNames);
		    	var cou=oneObje.attachmentArray.length;
		    	var _contextPath = storage.get(Constants.MOBILE_CONTEXT_PATH);
	    		if(cou>0 && !oneObje.isWithdraw){
	    			var conHtmlStr=""
		    		for(var i=0;i<cou;i++){
		    			var attObj=oneObje.attachmentArray[i];
		    			var htmlStr = '<li class="fn-clear">';
		    			htmlStr+=getFilePic(attObj.extName);
		    			
		    			if(attObj.fileExist){
	    					if(isAndroid()&&isWeiXin()){
	    						var downloadPath = _contextPath+"/common/open/officeh5/downloadAtt.action?path="+attObj.downloadPath;
	    						htmlStr+='<a href="'+downloadPath+'" class="icon-img icon-download"></a>';
	    					}else{
	    						htmlStr+='<a href="'+attObj.downloadPath+'" class="icon-img icon-download"></a>';
	    					}
		    			}else{
		    				htmlStr+='<a href="javascript:viewToast('+"'附件不存在'"+');" class="icon-img icon-download"></a>';
		    			}
		    			
//		    			if(attObj.fileExist){
//		    				htmlStr+='<a href="'+attObj.downloadPath+'" class="icon-img icon-download"></a>';
//		    			}else{
//		    				htmlStr+='<a href="javascript:showMsgTip('+"'附件不存在'"+');" class="icon-img icon-download"></a>';
//		    			}
		    			htmlStr+='<p class="acc-tt f-16">'+attObj.fileName+'</p>';
		    			var fileSize = getFileSize(attObj.fileSize);
		    			htmlStr+='<p class="acc-dd">'+fileSize+'</p>';
		    			htmlStr+='</li>';
		    			conHtmlStr+=htmlStr;
		    		}
		    		$(obj).find('.acc-wrap').append(conHtmlStr);
	    		}
		    }else{
		    	viewToast('操作失败');
			}
	    },
	    
	    _loadCollect : function(data,obj){
	    	if(data.result==Constants.SUCCESS){
	    		if($(obj).find(".hasStar").val()=='true'){
	    			$(obj).find(".hasStar").val('false');
	    			$(".abtn-collect").removeClass('active');
	    			$(obj).find(".icon-start").hide();
				}else{
					$(obj).find(".hasStar").val('true');
					$(".abtn-collect").addClass('active');
					$(obj).find(".icon-start").show();
				}
	    		viewToast('操作成功');
	    	}else{
	    		viewToast('操作失败');
	    	}
	    },	
	    
	    
	    _loadDetail : function(dataType, data){
	    	if(data.result==Constants.SUCCESS){ 
	    		var obj = data.result_object;
	    		var replyArray = obj.reply;
	    		var readUserArray = obj.readUserArray;
	    		var unReadUserArray = obj.unReadUserArray;
	    		var attachmentArray = obj.attachmentArray;
	    		var hasSms = obj.hasSms;
	    		var _contextPath = storage.get(Constants.MOBILE_CONTEXT_PATH);
				
				$("#title").html(obj.title);
				$("#userIds").val(obj.userIds);
				$("#replyMsgId").val(obj.replyMsgId);
				$("#sendUserName").html(obj.sendUserName+'<span class="ml-30">'+obj.sendTime+'</span>');
				$("#content").html('<p class="pt-20 t-indent">'+obj.content+'</p>');
				
				//附件
	    		if(attachmentArray.length > 0){
	    			var htmlStr = ''; 
	    			$.each(attachmentArray, function(index, json){
	    				var fileSize = getFileSize(json.fileSize);
	    				htmlStr += '<li class="fn-clear">';
	    				htmlStr += getFilePic(json.extName);
	    				if(json.fileExist){
	    					if(isAndroid()&&isWeiXin()){
	    						var downloadPath = _contextPath+"/common/open/officeh5/downloadAtt.action?path="+json.downloadPath;
	    						htmlStr+='<a href="'+downloadPath+'" class="icon-img icon-download"></a>';
	    					}else{
	    						htmlStr+='<a href="'+json.downloadPath+'" class="icon-img icon-download"></a>';
	    					}
		    			}else{
		    				htmlStr+='<a href="javascript:viewToast('+"'附件不存在'"+');" class="icon-img icon-download"></a>';
		    			}
	    				htmlStr += '<p class="acc-tt f-16">'+json.fileName+'</p>';
	    				htmlStr += '<p class="acc-dd">'+fileSize+'</p>';
	    				htmlStr += '</li>';
	    			});
	    			$("#fileDiv").html(htmlStr);
	    		}
				//已读
				if(readUserArray.length > 0){
					$("#readNum").html(readUserArray.length+"人");
					var	htmlInfo  = '<p class="list fn-clear f-14 c-333">';
					$.each(readUserArray, function(index, json) {
						var	nameAndPath = json.readInfo.split('==');
						var htmlStr = ''; 
						if(nameAndPath[1]==''||nameAndPath[1]==null){
							htmlStr += '<span><span class="place-avatar" style="background:'+getBackgroundColor(nameAndPath[0])+';">'+getLast2Str(nameAndPath[0])+'</span>'
//							nameAndPath[1]=_contextPath+'/static/html/images/icon/img_default_photo.png';
						}else{
							htmlStr += '<span><img src="'+nameAndPath[1]+'">';
						}
						htmlInfo += htmlStr+wap.subStrName(nameAndPath[0])+'</span>';
					});
					htmlInfo += '</p>';
					$("#readUserLi").append(htmlInfo);
				}else{
					$("#readUserLi").hide();
				}
				//未读
				if(unReadUserArray.length > 0){
					$("#unReadNum").html(unReadUserArray.length+"人");
					var	htmlInfo = '<p class="list fn-clear f-14 c-333">';
					$.each(unReadUserArray, function(index, json) {
						var	nameAndPath = json.readInfo.split('==');
						var htmlStr = ''; 
						if(nameAndPath[1]==''||nameAndPath[1]==null){
							htmlStr += '<span><span class="place-avatar" style="background:'+getBackgroundColor(nameAndPath[0])+';">'+getLast2Str(nameAndPath[0])+'</span>'
//							nameAndPath[1]=_contextPath+'/static/html/images/icon/img_default_photo.png';
						}else{
							htmlStr += '<span><img src="'+nameAndPath[1]+'">';
						}
						htmlInfo += htmlStr+wap.subStrName(nameAndPath[0])+'</span>';
					});
					htmlInfo += '</p>';
					$("#unReadUserLi").append(htmlInfo);
				}else{
					$("#unReadUserLi").hide();
				}
				if(replyArray.length > 0){
					var	htmlInfo ='<p class="pt-30 f-15 c-666">共有'+replyArray.length+'条回复</p>';
					htmlInfo += '<div class="reply-list">';
					$.each(replyArray, function(index, json) {
						var	nameAndPath = ["",""];
						if(isNotBlank(json.userName)){
							nameAndPath = json.userName.split('==');
						}
						
						if(replyArray.length-1==index){
							htmlInfo += '<div class="reply-item fn-clear last">';
						}else{
							htmlInfo += '<div class="reply-item fn-clear">';
						}
						if(nameAndPath[1]==''||nameAndPath[1]==null){
							htmlInfo += '<span class="place-avatar" style="background:'+getBackgroundColor(nameAndPath[0])+';">'+getLast2Str(nameAndPath[0])+'</span>'
						}else{
							htmlInfo += '<img src="'+nameAndPath[1]+'">';
						}
						htmlInfo += '<p class="f-14">';
						htmlInfo += '<span class="c-999"><span class="fn-right f-10">'+json.createTime+'</span>'+nameAndPath[0]+'</span>';
						htmlInfo += '<span class="pt-10 c-333">'+json.content+'</span>';
						htmlInfo += '</p>';
						htmlInfo += '</div>';
					});
					htmlInfo += '</div>';
					$("#replyLi").html(htmlInfo);
				}else{
					$("#replyLi").hide();
				}
				if(hasSms){
//    				$("#sendSms").addClass('analog-chk-current');
    			}else{
    				$("#sendSms").hide();
    			}
	    	}
	    },
	    
	    _loadEdit : function(data,id,dataType){
	    	if(data.result==Constants.SUCCESS){
	    		var obj=data.result_object;
	    		var isNeedsms = obj.isNeedsms;
	    		var hasSms = obj.hasSms;
	    		if(hasSms){
//	    			$("#sendSms").addClass('analog-chk-current');
	    			storage.set(WapConstants.IS_HAS_SMS, true);
	    		}else{
	    			$("#sendSms").hide();
	    			storage.set(WapConstants.IS_HAS_SMS, false);
	    		}
	    		if(isNotBlank(id)){
	    			$("#msgTitle").val(obj.title);
	    			$("#msgCon").val(obj.simpleContent);
	    			$("#editType").val(obj.editType);
	    			//if(dataType==WapConstants.DATA_TYPE_1){
	    				if(hasSms&&isNeedsms){
	    					$("#sendSms").addClass('analog-chk-current');
	    				}
	    				$("#msgId").val(obj.msgId);
	    				$("#replyMsgId").val(obj.replyMsgId);
	    			//}
	    			storage.set(Constants.ADDRESS_SELECTED_USERIDS, obj.userIds);
	    			storage.set(Constants.ADDRESS_SELECTED_USERNAMES, obj.userNames);
	    			wap._editService.setUser(obj.userIds,obj.userNames);
	    			var cou=obj.attachmentArray.length;
	    			if(cou>0){
	    				for(var i=0;i<cou;i++){
	    					var attObj=obj.attachmentArray[i];
	    					var htmlStr = '<li class="fn-clear">';
	    					htmlStr+=getFilePic(attObj.extName);
	    					htmlStr+='<a href="javascript:void(0)" class="icon-img icon-del2"><input type="hidden" class="attId" value="'+attObj.id+'"></a>';
	    					htmlStr+='<p class="acc-tt f-16">'+attObj.fileName+'</p>';
	    					var fileSize = getFileSize(attObj.fileSize);
	    					htmlStr+='<p class="acc-dd">'+fileSize+'</p>';
	    					htmlStr+='</li>';
	    					$('#accFooter').append(htmlStr);
	    				}
	    				$(".icon-del2").on('click',function(){
	    					wap._editService.attDel(this,null,$(this).find(".attId").val());
	    				});
	    			}
	    		}
	    		
	    	}else{
	    		viewToast('操作失败');
	    	}
	    },	
  }
};