var wapNetworkService = {
		
	// 工作汇报列表信息
	doLoadList : function(dataType, searchStr, data){
		wapNetworkService._service._loadList(dataType, searchStr, data);
	},
	
	doLoadDetail : function(dataType, data){
		wapNetworkService._service._loadDetail(dataType, data);
	},
	
	doLoadReceivedDetailList : function(userId,dataType, data){
		wapNetworkService._service._loadReceivedDetailList(userId,dataType, data);
	},
	doLoadReceivedDetail : function(dataType, data, obj){
		wapNetworkService._service._loadReceivedDetail(dataType, data, obj);
	},
	
	doEditMsg : function(data,dataType,editType){
		wapNetworkService._service._loadEdit(data,dataType,editType);
	},
	
	doDeleteMsg : function(data){
		wapNetworkService._service._loadDelete(data);
	},
	doCollectMsg : function(data,obj){
		wapNetworkService._service._loadCollect(data,obj);
	},
	
	doSaveMsg : function(data, abUserIds, abUserNames){
		wapNetworkService._service._loadSave(data, abUserIds, abUserNames);
	},
	
	
	//私有方法
	_service : {
		
		_loadList : function(dataType, searchStr, data) {
			if(data.result==Constants.SUCCESS){
				WapPage = data.page;
				storage.set(WapConstants.WR_SEARCH_STR, searchStr);
				var array = data.result_array;
				var cou=array.length;
				if(cou>0){
					var conHtmlStr="";
					for(var i=0;i<cou;i++){
						var obj = array[i];
//						<li class="new">
//	                        <i></i>
//	                        <p class="tt f-18"><span class="time f-12">11:53</span>明天下午开会<span class="icon-img icon-acc"></span><span class="icon-img icon-start"></span></p>
//	                        <p class="dd f-14">明天下午1点钟在公司3号楼第15楼开会，讨论关于本次办公OA的新版本问题你觉得怎么样...</p>
//	                    </li>
//	                	<li>
//	                        <i></i>
//	                        <p class="tt f-18"><span class="time f-12">昨天</span>明天下午开会</p>
//	                        <p class="dd f-14">明天下午1点钟在公司3号楼第15楼开会，讨论关于本次办公OA的新版本问题你觉得怎么样...</p>
//	                    </li>
						var htmlStr = '<li';
						if(!obj.isRead && dataType==WapConstants.DATA_TYPE_3){
							htmlStr+=' class="new"';
						}
						if(dataType == WapConstants.DATA_TYPE_1 || dataType == WapConstants.DATA_TYPE_2){
							htmlStr+=' onclick="location.href=\'messageGeneralDetail.html?replyMsgId='+obj.replyMsgId+'&id='+obj.msgId+'&dataType='+dataType+'\';"';
						}else{
							htmlStr+=' onclick="location.href=\'messageReceivedDetail.html?replyMsgId='+obj.replyMsgId+'&id='+obj.msgId+'&dataType='+dataType+'\';"';
						}
						htmlStr+='>';
						htmlStr+='<i></i>';
						htmlStr+='<p class="tt f-18"><span class="time f-12">';
						if(obj.hasAttached){
							htmlStr+='<span class="icon-img icon-acc mr-10"></span>';
						}
						if(obj.hasStar && dataType==WapConstants.DATA_TYPE_3){
							htmlStr+='<span class="icon-img icon-start mr-10"></span>';
						}
						htmlStr+=obj.dateStr+'</span>';
						if(obj.isWithdraw){
							htmlStr+='已被撤回';
						}else{
							htmlStr+=obj.title;
						}
						htmlStr+='</p><p class="dd f-14">';
						if(obj.isWithdraw){
							
						}else{
							htmlStr+=obj.simpleContent;
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
				showMsgTip('操作失败');
			}
	    },
	    
	    _loadDetail : function(dataType, data){
	    	$('#container').html("");
	    	$('#accFooter').html("");
	    	if(data.result==Constants.SUCCESS){ 
	    		var obj=data.result_array;
	    		var containerHtml="";
//		    	<p class="detail-tt f-18">山东足球队移动应用app需求会议</p>
//	            <div class="detail-wrap detail-wrap-in">
//	            	<div class="detail-inner">
//	                
//	                	<!--=S item Start-->
//	                	<div class="detail-item detail-item-first detail-item-open">
//	                		<div class="tt-wrap">
//	                        	<p class="tt-user">
//	                                <a href="#" class="show">显示详情</a>
//	                                <span class="name-tt f-18">发件人：</span>
//	                                <span class="name f-18">莱奥纳多</span>
//	                                <span class="icon-img icon-acc"></span>
//	                                <span class="icon-img icon-start"></span>
//	                            </p>
//	                            <div class="tt-des" style="display:none;">
//	                            	<p>
//	                                	<span class="tt">收件人：</span>
//	                                    <span class="dd dd-name">克里斯蒂亚诺；安德烈；保罗；米开朗琪罗；马尔基西奥；拉稀莫维奇；德尔皮耶罗...</span>
//	                                </p>
//	                                <p>
//	                                    <span class="tt">时间：</span>
//	                                    <span class="dd">2014年12月11日	11:53:52</span>
//	                                </p>
//	                            </div>
//	                        </div>
//	                        <p class="des f-14">今天你完成了帽子戏法，进了三个球，下面我会回敬一个帽子戏法。恩里克如今在巴萨就像玩数独游戏一般，对巴黎圣日耳曼，恩里克的排兵布阵再次令人惊讶。今天你完成了帽子戏法，进了三个球，下面我会回敬一个帽子戏法。恩里克如今在巴萨就像玩数独游戏一般，对巴黎圣日耳曼，恩里克的排兵布阵再次令人惊讶。恩里克如今在巴萨就像玩数独游戏一般，对巴黎圣日耳曼，恩里克的排兵布阵再次令人惊讶。恩里克如今在巴萨就像玩数独游戏一。</p>
//	                	</div>
//	                    <!--=E item End-->
//	                    
//	                </div>
//	            </div>
	    		containerHtml+='<p class="detail-tt f-18">'+obj.title;
	    		containerHtml+='</p><div class="detail-wrap detail-wrap-in">'
	    			+'<div class="detail-inner">'+'<div class="detail-item detail-item-first detail-item-open">'
	    			+'<div class="tt-wrap">'+'<p class="tt-user">';
	    		containerHtml+='<a href="javascript:void(0)" class="show">显示详情</a>';
	    		containerHtml+='<span class="name-tt f-18">发件人：</span>';
	    		containerHtml+='<span class="name f-18">'+obj.sendUserName+'</span>';
	    		if(obj.attachmentArray.length>0){
	    			containerHtml+='<span class="icon-img icon-acc"></span>';
				}
	    		containerHtml+='<div class="tt-des" style="display:none;"><p><span class="tt">收件人：</span>'
	    			+'<span class="dd dd-name">'+obj.userNames+'</span></p><p>'
    			if(dataType == WapConstants.DATA_TYPE_2){
    				containerHtml+='<span class="tt">时间：</span><span class="dd">'+obj.sendTime+'</span>';
    			}
	    		containerHtml+='</p></div></div>';
	    		containerHtml+='<p class="des f-14">'+obj.simpleContent+'</p>';
	    		containerHtml+='</div></div></div>'
	    		$('#container').html(containerHtml);
	    		var cou=obj.attachmentArray.length;
	    		if(cou>0){
		    		for(var i=0;i<cou;i++){
		    			var attObj=obj.attachmentArray[i];
	//			    	<li class="fn-clear">
	//			            <span class="icon-img icon-class icon-word"></span>
	//			            <a href="#" class="icon-img icon-download"></a>
	//			            <p class="acc-tt f-16">关于帽子戏法的...百度解释.docx</p>
	//			            <p class="acc-dd">805.2KB</p>
	//			        </li>
		    			var htmlStr = '<li class="fn-clear">';
		    			htmlStr+=getFilePic(attObj.extName);
		    			if(attObj.fileExist){
		    				htmlStr+='<a href="'+attObj.downloadPath+'" class="icon-img icon-download"></a>';
		    			}else{
		    				htmlStr+='<a href="javascript:showMsgTip('+"'附件不存在'"+');" class="icon-img icon-download"></a>';
		    			}
	    				
		    			htmlStr+='<p class="acc-tt f-16">'+attObj.fileName+'</p>';
		    			var fileSize = getFileSize(attObj.fileSize);
		    			htmlStr+='<p class="acc-dd">'+fileSize+'</p>';
		    			htmlStr+='</li>';
		    			$('#accFooter').append(htmlStr);
		    		}
	    		}
	    		$('#delId').click(function(){
					wap._detailService.del(dataType,obj.msgId);
				});
	    		if(dataType == WapConstants.DATA_TYPE_1){
	    			$("#compileId").show();
	    			$('#compileId').click(function(){
						wap._detailService.edit(dataType,obj.msgId,obj.replyMsgId,WapConstants.EDIT_TYPE_5);
					});
	    		}else{
	    			$("#replyAllId").show();
	    			$('#replyAllId').click(function(){
    					wap._detailService.edit(dataType,obj.msgId,obj.replyMsgId,WapConstants.EDIT_TYPE_3);
					});
	    		}
	    		$('.tt-user .show').on('touchstart', function(e){
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
	    		
	    	}else{
	    		showMsgTip('操作失败');
			}
	    },
	    
	    _loadReceivedDetailList : function(userId,dataType, data){
	    	$('#container').html("");
	    	if(data.result==Constants.SUCCESS){
	    		var array = data.result_array;
	    		var cou=array.length;
				if(cou>0){
//					<p class="detail-tt f-18">山东足球队移动应用app需求会议</p>
//	                <div class="detail-wrap">
//	                	<div class="detail-inner">
//	                    
//	                    	<!--=S item Start-->
//	                    	<div class="detail-item detail-item-first detail-item-open">//<div class="detail-item detail-item-close">
//	                        	<span class="num f-18">5</span>
//	                            <span class="time">11:53</span>
//	                            <span class="arrow"></span>
//	                    		<div class="tt-wrap">
//	                            	<p class="tt-user">
//	                                    <a href="#" class="show">显示详情</a>
//	                                    <span class="name-tt f-18">发件人：</span>
//	                                    <span class="name f-18">莱奥纳多</span>
//	                                    <span class="icon-img icon-acc"></span>
//	                                    <span class="icon-img icon-start"></span>
//	                                </p>
//	                                <div class="tt-des" style="display:none;">
//	                                	<p>
//	                                    	<span class="tt">收件人：</span>
//	                                        <span class="dd dd-name">克里斯蒂亚诺；安德烈；保罗；米开朗琪罗；马尔基西奥；拉稀莫维奇；德尔皮耶罗...</span>
//	                                    </p>
//	                                    <p>
//	                                        <span class="tt">时间：</span>
//	                                        <span class="dd">2014年12月11日	11:53:52</span>
//	                                    </p>
//	                                </div>
//	                            </div>
//	                            <p class="des f-14">今天你完成了帽子戏法，进了三个球，下面我会回敬一个帽子戏法。恩里克如今在巴萨就像玩数独游戏一般，对巴黎圣日耳曼，恩里克的排兵布阵再次令人惊讶。今天你完成了帽子戏法，进了三个球，下面我会回敬一个帽子戏法。恩里克如今在巴萨就像玩数独游戏一般，对巴黎圣日耳曼，恩里克的排兵布阵再次令人惊讶。恩里克如今在巴萨就像玩数独游戏一般，对巴黎圣日耳曼，恩里克的排兵布阵再次令人惊讶。恩里克如今在巴萨就像玩数独游戏一。</p>
//	                            <ul class="acc-wrap">
//	                            	<li class="fn-clear">
//	                                	<span class="icon-img icon-class icon-word"></span>
//	                                    <a href="#" class="icon-img icon-download"></a>
//	                                    <p class="acc-tt f-16">关于帽子戏法的...百度解释.docx</p>
//	                                    <p class="acc-dd">805.2KB</p>
//	                                </li>
//	                            </ul>
//	                    	</div>
//	                        <!--=E item End-->
//	                        
//	                    </div>
//	                </div>
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
					
					$('.detail-item').on('touchstart', function(){
						//点击加载详细
						var msgId=$(this).find('.hidMsgId').val();
						var replyMsgId=$(this).find('.hidReplyMsgId').val();
						if(!$(this).hasClass('already-open')){
							$(this).addClass('already-open');
							wapNetwork.doGetReceivedDetail(userId,dataType, msgId,this);
						}
						if($(this).hasClass('isWithdraw')){
							$(".abtn-reply").hide();
							$(".abtn-reply-all").hide();
						}else{
							$(".abtn-reply").show();
							$(".abtn-reply-all").show();
						}
						var sendMsgId=$(this).find('.hidSendMsgId').val();
						if($(this).hasClass('detail-item-close')){
							$(this).addClass('active');
							$(this).addClass('detail-item-open').removeClass('detail-item-close').siblings('.detail-item').addClass('detail-item-close').removeClass('detail-item-open');
							if($(this).find(".isSendInfo").val()=='false'){
								$('#msgFooter').show();
							}else{
								$('#msgFooter').hide();
							}
							if($(this).find(".hasStar").val()=='true'){
								$('.abtn-collect').addClass('active');
							}else{
								$('.abtn-collect').removeClass('active');
							}
							var th=this;
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
								wap._detailService.del(dataType,msgId);
							});
							$('.abtn-reply').unbind();
							$('.abtn-reply').on('click',function(){
								wap._detailService.edit(dataType,sendMsgId,replyMsgId,WapConstants.EDIT_TYPE_2);
							});
							$('.abtn-reply-all').unbind();
							$('.abtn-reply-all').on('click',function(){
								wap._detailService.edit(dataType,sendMsgId,replyMsgId,WapConstants.EDIT_TYPE_3);
							});
						};
					});
					
					$('.detail-item').on('touchend', function(){
						$(this).removeClass('active');
					});
					
					$('.tt-user').on('touchstart', function(e){
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
								wap._detailService.del(dataType,msgId);
							});
							$('.abtn-reply').unbind();
							$('.abtn-reply').on('click',function(){
								wap._detailService.edit(dataType,sendMsgId,replyMsgId,WapConstants.EDIT_TYPE_2);
							});
							$('.abtn-reply-all').unbind();
							$('.abtn-reply-all').on('click',function(){
								wap._detailService.edit(dataType,sendMsgId,replyMsgId,WapConstants.EDIT_TYPE_3);
							});
							
						};
					});
					
					$('.tt-user .show').on('touchstart', function(e){
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
					
					$('.detail-tt').on('touchstart', function(){
						$('.detail-item').removeClass('detail-item-open').addClass('detail-item-close');
						$('#msgFooter').hide();
					});
					
					$('.detail-item-first').trigger("touchstart");
					
					$('.detail-item-first').trigger("touchend");
					
				}
				
	    	}else{
	    		showMsgTip('操作失败');
			}
	    },
	    
	    _loadReceivedDetail : function(dataType, data, obj){
	    	if(data.result==Constants.SUCCESS){
		    	var oneObje=data.result_array;
		    	$(obj).find(".hidSendMsgId").val(oneObje.messageId);
		    	$(obj).find('.dd-name').html(oneObje.userNames);
		    	var cou=oneObje.attachmentArray.length;
	    		if(cou>0 && !oneObje.isWithdraw){
	    			var conHtmlStr=""
		    		for(var i=0;i<cou;i++){
		    			var attObj=oneObje.attachmentArray[i];
	//			    	<li class="fn-clear">
	//			            <span class="icon-img icon-class icon-word"></span>
	//			            <a href="#" class="icon-img icon-download"></a>
	//			            <p class="acc-tt f-16">关于帽子戏法的...百度解释.docx</p>
	//			            <p class="acc-dd">805.2KB</p>
	//			        </li>
		    			var htmlStr = '<li class="fn-clear">';
		    			htmlStr+=getFilePic(attObj.extName);
		    			if(attObj.fileExist){
		    				htmlStr+='<a href="'+attObj.downloadPath+'" class="icon-img icon-download"></a>';
		    			}else{
		    				htmlStr+='<a href="javascript:showMsgTip('+"'附件不存在'"+');" class="icon-img icon-download"></a>';
		    			}
		    			htmlStr+='<p class="acc-tt f-16">'+attObj.fileName+'</p>';
		    			var fileSize = getFileSize(attObj.fileSize);
		    			htmlStr+='<p class="acc-dd">'+fileSize+'</p>';
		    			htmlStr+='</li>';
		    			conHtmlStr+=htmlStr;
		    		}
		    		$(obj).find('.acc-wrap').append(conHtmlStr);
	    		}
		    }else{
		    	showMsgTip('操作失败');
			}
	    },
	    
	    _loadEdit : function(data,dataType,editType){
	    	if(data.result==Constants.SUCCESS){
	    		var obj=data.result_array;
	    		$("#msgTitle").val(obj.title);
	    		$("#msgCon").val(obj.simpleContent);
	    		if(editType==WapConstants.EDIT_TYPE_5){
					$("#msgId").val(obj.msgId);
				}
	    		$("#replyMsgId").val(obj.replyMsgId);
	    		storage.set(Constants.ADDRESS_SELECTED_USERIDS, obj.userIds);
				storage.set(Constants.ADDRESS_SELECTED_USERNAMES, obj.userNames);
				wap._editService.setUser(obj.userIds,obj.userNames);
				var cou=obj.attachmentArray.length;
	    		if(cou>0){
		    		for(var i=0;i<cou;i++){
		    			var attObj=obj.attachmentArray[i];
	//			    	<li class="fn-clear">
	//			            <span class="icon-img icon-class icon-word"></span>
	//			            <a href="#" class="icon-img icon-del2"></a>
	//			            <p class="acc-tt f-16">关于帽子戏法的...百度解释.docx</p>
	//			            <p class="acc-dd">805.2KB</p>
	//			        </li>
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
	    	}else{
	    		showMsgTip('操作失败');
	    	}
	    },	
	    
	    _loadDelete : function(data){
	    	if(data.result==Constants.SUCCESS){
	    		showMsgTip('操作成功',function(){
	    			wap._detailService.back();
	    		});
	    	}else{
	    		showMsgTip('操作失败');
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
	    		showMsgTip('操作成功');
	    	}else{
	    		showMsgTip('操作失败');
	    	}
	    },	
	    
  }
};