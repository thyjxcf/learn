var wap = {
		
		//列表页
		initList : function(){
			wap._listService.init();
		},
		
		//详情页---收件
		initReceiveDetail : function(){
			wap._receiveDetailService.init();
		},
		//详情页---发件
		initDetail : function(){
			wap._detailService.init();
		},
		
		//编辑页
		initEdit : function(){
			wap._editService.init();
		},
		
		subStrName : function(name){
			if(name.length>4){
				name = name.substring(0,4);
			}
			return name;
		},
		
		//列表页面方法	
		_listService : {
			
			init : function(){
				var Request = new UrlSearch();
				var dataType = Request.dataType;
				var isBack = Request.isBack;
				if(dataType==null || typeof(dataType)=='undefined'){
					dataType=WapConstants.DATA_TYPE_3;
				}
				if(isBack==null || typeof(isBack)=='undefined'){
					isBack=WapConstants.BACK_LIST_GLAG_0;
				}
				
				var userId = storage.get(Constants.USER_ID);
				
				//tab事件
				$('.tab-wrap ul li').bind('touchstart', function(){
					var dataType = $(this).attr("dataValue");
					$(".ui-search-opt").show();
					
					wap._listService.cleanShowAll();
					
					if(dataType==WapConstants.DATA_TYPE_3){
						$('#searchMsg').attr('placeholder', '请输入标题或发件人');
					}else{
						$('#searchMsg').attr('placeholder', '请输入标题');
						$(".ui-search-opt").hide();
					}
					var userId = storage.get(Constants.USER_ID);
					if(isBack == WapConstants.BACK_LIST_GLAG_0){
						$('#searchMsg').val('');
						storage.remove(WapConstants.SEARCH_STR);
					}else{
						isBack = WapConstants.BACK_LIST_GLAG_0;
					}
					wap._listService.loadListData(userId, dataType,"");
				});
				//个人 部门 单位
				$('#showAllLayer ul li').bind('click', function(){
					var receiveType=$(this).val();
					var userId = storage.get(Constants.USER_ID);
					var dataType = $('.tab-wrap ul .current').attr("dataValue");
					wap._listService.loadListData(userId, dataType,receiveType);
					storage.set(WapConstants.SEARCH_STR,$('#searchMsg').val());
					$("#searchMsg").blur();
					return false;
				});
				//搜索事件监听
				$('.my-search-form').submit(function () {
					var receiveType=$(".sel current").val();
					var userId = storage.get(Constants.USER_ID);
					var dataType = $('.tab-wrap ul .current').attr("dataValue");
					wap._listService.loadListData(userId, dataType,receiveType);
					storage.set(WapConstants.SEARCH_STR,$('#searchMsg').val());
					$("#searchMsg").blur();
					return false;
				});
				$("#queryBtn").click(function(){
					var receiveType=$(".sel current").val();
					var userId = storage.get(Constants.USER_ID);
					var dataType = $('.tab-wrap ul .current').attr("dataValue");
					wap._listService.loadListData(userId, dataType,receiveType);
					storage.set(WapConstants.SEARCH_STR,$('#searchMsg').val());
					$("#searchMsg").blur();
					return false;
				});
				//更多
				$('.loading-more').bind('click', function(){
					var receiveType=$(".sel current").val();
					var userId = storage.get(Constants.USER_ID);
					var dataType = $('.tab-wrap ul .current').attr("dataValue");
					wap._listService.bindClickMore(userId, dataType,receiveType);
				});
				
				//新增
				$('#apply').click(function(){
					var dataType = $('.tab-wrap ul .current').attr("dataValue");
			    	location.href="messageEdit.html?editType="+WapConstants.EDIT_TYPE_1+"&dataType="+dataType;
				});
				
				//加载默认条件
				var searchStr = '';
				if(isBack == WapConstants.BACK_LIST_GLAG_1){
					//返回列表页默认搜索值
					searchStr = storage.get(WapConstants.SEARCH_STR);
					if(isNotBlank(searchStr)){
						$('#searchMsg').val(searchStr);
					}
				}
				//加载数据
				$('.tab-wrap ul li[dataValue="'+dataType+'"]').addClass('current').siblings('li').removeClass('current');
				$('.tab-wrap ul li[dataValue="'+dataType+'"]').trigger('touchstart');
				
				//wapNetwork.doGetList(userId, dataType, searchStr);
			},
			
			//返回时加载数据
			backList:function(dataType){
				location.href="messageList.html?dataType="+dataType+"&isBack="+WapConstants.BACK_LIST_GLAG_1;
			},
			//清空搜索类型
			cleanShowAll:function(){
				$('.show-all font').text('全部');
				$('.show-all').val("");
				$('.current.sel').removeClass('current');
				$('#showAllId').addClass('current');
			},
			//加载第一页数据
			loadListData : function(userId, dataType,receiveType){
				$('#list').hide();
				$('#empty').hide();
				$('.mail-list').html('');
				$('.loading-more').html('<a href="javascript:void(0)">加载更多</a>');
				$('.loading-more').bind('click', function(){
					wap._listService.bindClickMore(userId, dataType,receiveType);
				});
				
				var searchStr = $('#searchMsg').val();
				wapNetwork.doGetList(userId, dataType,searchStr,receiveType);
			},
			
			//加载更多
			bindClickMore : function(userId, dataType,receiveType){
				if (WapPage.pageIndex >= WapPage.maxPageIndex) {
			    	return;
			    }
				var searchStr = storage.get(WapConstants.SEARCH_STR);
			    wapNetwork.doMoreList(userId, dataType,searchStr,receiveType, ++WapPage.pageIndex);
			},
			
		},
		
		//详情页面方法
		_receiveDetailService : {
			back : function(){
				$('.abtn-back').click();
			},
			del : function(dataType,id){
				showMsg("确认删除？",function(){
					wapNetwork.doDelete(dataType,id);
				});
			},
			edit : function(dataType,id,replyMsgId,editType){
				location.href="messageEdit.html?id="+id+"&replyMsgId="+replyMsgId+"&dataType="+dataType+"&editType="+editType;
			},
			
			init : function(){
				var Request = new UrlSearch();
				var id = Request.id;
				var dataType = Request.dataType;
				var replyMsgId = Request.replyMsgId;
				var userId = storage.get(Constants.USER_ID);
				wapNetwork.doGetDetail(userId,dataType, id,replyMsgId);
				//返回按钮
			    $('.abtn-back').click(function() {
			    	wap._listService.backList(dataType);
			    });
			},
		},
		//详情页面方法
		_detailService : {
			
			init : function(){
				var Request = new UrlSearch();
				var id = Request.id;
				var dataType = Request.dataType;
				var replyMsgId = Request.replyMsgId;
				var userId = storage.get(Constants.USER_ID);
				wapNetwork.doGetDetail(userId,dataType, id,replyMsgId);
				//返回按钮
			    $('#cancelId').click(function() {
			    	wap._listService.backList(dataType);
			    });
			  //重新绑定
				$('.analog-chk').unbind('touchstart');
				$('.analog-chk').on('touchstart', function(){
					$(this).toggleClass('analog-chk-current');
				});
				//触发手机键盘done/go
				$('#dataForm').submit(function () { 
					$('#msgTitle').val('回复：'+$("#title").html());
					$('#msgCon').val($("#replyCon").html());
					wap._editService.save(dataType,WapConstants.OPRATE_TYPE_2);
				});
				//回复
				$("#saveReply").click(function(){
					$('#msgTitle').val('回复：'+$("#title").html());
					$('#msgCon').val($("#replyCon").html());
					wap._editService.save(dataType,WapConstants.OPRATE_TYPE_2);
				});
			},
			//评论时加载数据
			backDetail : function(id,dataType){
				location.href="messageDetail.html?dataType="+dataType+"&id="+id;
			},
		},
		
		//编辑页面方法
		_editService : {
			
			init : function(){
				
				var Request = new UrlSearch();
//				var type = Request.type;//1:其他、2：通讯录返回
				var dataType = Request.dataType;
				var id = Request.id;
				var editType = Request.editType;
				var replyMsgId = Request.replyMsgId;
				//初始化数据
				wap._editService.initData();
				
				//重新绑定
				$('.analog-chk').unbind('touchstart');
				$('.analog-chk').on('touchstart', function(){
					$(this).toggleClass('analog-chk-current');
				});
				//通讯录
				$("#address").click(function(){
					//已维护的数据缓存起来
					wap._editService.saveDataCache();
					
					//通讯录需求的几个参数
					var _contextPath = storage.get(Constants.MOBILE_CONTEXT_PATH);
					var _unitId = storage.get(Constants.UNIT_ID);
					var userId=storage.get(Constants.USER_ID);
					var returnurl = _contextPath + "/office/mobileh5/message/messageEdit.html?id="+id+"&replyMsgId="+replyMsgId+"&dataType="+dataType+"&editType="+editType+"&type=2";
					storage.set(Constants.ADDRESS_RETURN_URL, returnurl);//绝对路径
					
					storage.set(Constants.CONTEXTPATH, _contextPath);
					storage.set(Constants.ADDRESS_TYPE, Constants.TYPE_1);
					storage.set(Constants.ADDRESS_SELECT_TYPE, Constants.TYPE_1);
					//请求通讯录
					var requesturl = _contextPath + "/component/addressbookwap2/redirect.html?unitId="+_unitId+"&userId="+userId;
					location.href = requesturl;
				});
				
				//取消
				$("#cancelId").click(function(){
//					wap._listService.backList(dataType);
//					wap._editService.clearCache();
					var userIds = storage.get(Constants.ADDRESS_SELECTED_USERIDS, false);
					var usernames = storage.get(Constants.ADDRESS_SELECTED_USERNAMES, false);
					var title = $('#msgTitle').val();
					var content = $('#msgCon').val();
					var attNumber=$('#attNumber').html();
					if(isNotBlank(userIds)||isNotBlank(title)||isNotBlank(content)||isNotBlank(attNumber)){
						showMsg("是否保存草稿？",function(){
							wap._editService.save(dataType,WapConstants.OPRATE_TYPE_0);
						},function(){
							wap._listService.backList(dataType);
						});
					}else{
						wap._listService.backList(dataType);
						//清除缓存
						wap._editService.clearCache();
					}
				});
				
				//提交
				$('#submitBtn').click(function(){
					wap._editService.save(dataType,WapConstants.OPRATE_TYPE_1);
				});
			},
			
			//提交
			save : function(dataType,operateType){
				if(!isActionable()){
					return false;
				}
				var userId = storage.get(Constants.USER_ID);
				var sendUserName = storage.get(Constants.USER_REALNAME);
				var unitId = storage.get(Constants.UNIT_ID);
				var userIds ="";
				if(operateType==WapConstants.OPRATE_TYPE_2){
					userIds = $('#userIds').val();
				}else{
					userIds = storage.get(Constants.ADDRESS_SELECTED_USERIDS);
				}
//				var usernames = storage.get(Constants.ADDRESS_SELECTED_USERNAMES);
				var title = $('#msgTitle').val();
				var content = $('#msgCon').val();
				var isNeedsms = false;
				
				var fileName = $("#accFooter  li:first .acc-tt").html();
				if(isNotBlank(fileName)){
					var laIn=fileName.lastIndexOf('.');
					if(laIn!=-1){
						fileName = fileName.substring(0,laIn);
					}
					
				}
				if(!isNotBlank(userIds)){
					viewToast('收件人不能为空');
					return;
				}
				
				if(!isNotBlank(title)){
					if(!isNotBlank(fileName)){
						viewToast('主题不能为空');
						return;
					}else{
						$('#msgTitle').val(fileName);
					}
				}else{
					if(getStringLen(title) > 200){
						viewToast('主题不能超过200字符');
						return;
					}
					if(wap.isEmojiCharacter(title)){
						viewToast('主题不能带有表情符');
						return;
					}
				}
				
				if(!isNotBlank(content)&&!isNotBlank(fileName)){
					viewToast('内容不能为空');
					return;
				}else{
					if(wap.isEmojiCharacter(content)){
						viewToast('内容不能带有表情符');
						return;
					}
//					if(getStringLen(content) > 500){
//						alert('内容不能超过500字符');
//						return;
//					}
				}
				
				if($("#sendSms").hasClass('analog-chk-current')){
					isNeedsms = true;
				}
				wapNetwork.doSave(dataType,operateType,userIds,userId,sendUserName,unitId,isNeedsms);
			},
			//通讯录回调函数
			redirectBackFunction:function(){
				var userIds = storage.get(Constants.ADDRESS_SELECTED_USERIDS);
				var userNames = storage.get(Constants.ADDRESS_SELECTED_USERNAMES);
				wap._editService.setUser(userIds, userNames);
				$("#page").show();
				$("#redirectDivId").hide();
			},
			//初始化数据
			initData : function(){
				var Request = new UrlSearch();
				var type = Request.type;//1:其他、2：通讯录返回
				var dataType = Request.dataType;
				var editType = Request.editType;
				var fileIndex=1;
				var id = Request.id;
				var replyMsgId = Request.replyMsgId;
				if(isNotBlank(replyMsgId)){
					$("#replyMsgId").val(replyMsgId);
				}
				if(editType==WapConstants.EDIT_TYPE_2){
					$("title").html("回复");
				}else if(editType==WapConstants.EDIT_TYPE_3){
					$("title").html("回复全部");
				}else if(editType==WapConstants.EDIT_TYPE_4){
					$("title").html("转发");
				}else if(editType==WapConstants.EDIT_TYPE_5){
					$("title").html("编辑");
				}else{
					$("title").html("写消息");
				}
				if("2" == type){
					//获取缓存中已选中的人员
					var userIds = storage.get(Constants.ADDRESS_SELECTED_USERIDS);
					var userNames = storage.get(Constants.ADDRESS_SELECTED_USERNAMES);
					if(isNotBlank(userIds) && isNotBlank(userNames)){
						wap._editService.setUser(userIds, userNames);
					}
					//获取缓存中之前已维护的数据
					var title = storage.get(WapConstants.EDIT_TITLE);
					var content = storage.get(WapConstants.EDIT_CONTENT);
					var existAtt = storage.get(WapConstants.EDIT_EXIST_ATT);
					var delAtt = storage.get(WapConstants.EDIT_DEL_ATT);
					var isSendSms = storage.get(WapConstants.IS_NEED_SMS);
					var isHasSms = storage.get(WapConstants.IS_HAS_SMS);
					var msgId = storage.get(WapConstants.EDIT_MSG_ID);
					var msgReplyId = storage.get(WapConstants.EDIT_MSG_REPLY_ID);
					if(isSendSms=='true'){
						$("#sendSms").addClass('analog-chk-current');
					}
					if(isHasSms=='false'){
						$("#sendSms").hide();
					}
					
					if(isNotBlank(title)){
						$('#msgTitle').val(title);
					}
					if(isNotBlank(content)){
						$('#msgCon').val(content);
					}
					if(isNotBlank(msgId)){
						$("#msgId").val(msgId);
					}else{
						if(isNotBlank(id)){
							$("#msgId").val(id);
						}
					}
					if(isNotBlank(replyMsgId)){
						$("#replyMsgId").val(replyMsgId);
					}
					
					if(isNotBlank(delAtt)){
						$("#attDelIds").val(delAtt);
					}
					if(isNotBlank(existAtt)){
						$("#accFooter").html(existAtt);
						wap._editService.attNumber();
					}
				}else{
					//清除缓存
					wap._editService.clearCache();
					
//					if(editType==WapConstants.EDIT_TYPE_1){
//						//新增--不做处理
//					}else{
//						var Request = new UrlSearch();
//						var id = Request.id;
//						var dataType = Request.dataType;
						wapNetwork.doEdit(dataType,id,editType);
//					}
				}
				if(isWeiXin()){
					$('.ui-file').append('<input type="file" name="file" id="upFile'+fileIndex+'" accept="MIME_type">');
				}else{
					$('.ui-file').append('<input type="file" name="file" id="upFile'+fileIndex+'" accept="*/*">');
				}
				$("#upFile"+fileIndex).on('change',function(){
					wap._editService.fileChange("upFile"+fileIndex,fileIndex);
				});
				wap._editService.attNumber();
	    		wap._editService.auto();
				
			},
			
			//重新计算上部高度
			auto : function(){
				var all_h=parseInt($('.scroll-wrap').height());
				var list_h=parseInt($('.msgsnd-list').height());
				var text_h=all_h-list_h-28;
				$('#container').height(all_h);
				$('#msgCon').height(text_h);
				//layer('.icon-del2','.ui-layer','.close','标题1');
			},
			
			//通讯录赋值
			setUser : function(userIds, userNames){
				if(""!=userIds && userIds!=null && typeof(userIds)!='undefined'){
					var ids = userIds.split(',');
					if(""!=userNames && userNames!=null && typeof(userNames)!='undefined'){
						var names = userNames.split(',');
						var htmlStr = '';
						for(index in names){
							if(index < ids.length){
								htmlStr += '<span>'+names[index]+'<input type="hidden" class="my-userid-class" value="'+ids[index]+'"/></span>\n';
							}
						}
						$('.dd-scroll').html(htmlStr);
					}
				}
				$('.dd-scroll span').unbind();
				$('.dd-scroll span').click(function(e){
					e.stopPropagation();
					$(this).remove();
					wap._editService.deleteUser($(this).find(".my-userid-class").val());
				});
				
			},
			
			//删除用户
			deleteUser : function(userId){
				var userIds = storage.get(Constants.ADDRESS_SELECTED_USERIDS);
				var userNames = storage.get(Constants.ADDRESS_SELECTED_USERNAMES);
				var ids = userIds.split(',');
				var names = userNames.split(',');
				var index = ids.indexOf(userId);
				if(index > -1){
					ids.splice(index, 1);
					names.splice(index, 1);
				}
				
				storage.set(Constants.ADDRESS_SELECTED_USERIDS, ids.join(","));
				storage.set(Constants.ADDRESS_SELECTED_USERNAMES, names.join(","));
			},
			
			//数据缓存起来
			saveDataCache : function(){
				var msgId = $('#msgId').val();
				var replyMsgId = $('#replyMsgId').val();
				var title = $('#msgTitle').val();
				var content = $('#msgCon').val();
				var attDelIds = $('#attDelIds').val();
				if($("#sendSms").hasClass('analog-chk-current')){
					storage.set(WapConstants.IS_NEED_SMS, true);
				}else{
					storage.set(WapConstants.IS_NEED_SMS, 'false');
				}
				$("#accFooter .new-file").remove();
				
				storage.set(WapConstants.EDIT_MSG_ID, msgId);
				storage.set(WapConstants.EDIT_MSG_REPLY_ID, replyMsgId);
				storage.set(WapConstants.EDIT_TITLE, title);
				storage.set(WapConstants.EDIT_CONTENT, content);
				storage.set(WapConstants.EDIT_DEL_ATT, attDelIds);
				storage.set(WapConstants.EDIT_EXIST_ATT, $("#accFooter").html());
			},
			
			//清除缓存
			clearCache : function(){
				storage.remove(Constants.ADDRESS_RETURN_URL);
				storage.remove(Constants.CONTEXTPATH);
				
				storage.remove(Constants.ADDRESS_SELECTED_USERIDS);
				storage.remove(Constants.ADDRESS_SELECTED_USERNAMES);
				storage.remove(Constants.ADDRESS_RETURN_FUNCTION);
				
				storage.remove(WapConstants.EDIT_MSG_ID);
				storage.remove(WapConstants.EDIT_MSG_REPLY_ID);
				storage.remove(WapConstants.EDIT_TITLE);
				storage.remove(WapConstants.EDIT_CONTENT);
				storage.remove(WapConstants.EDIT_EXIST_ATT);
				storage.remove(WapConstants.EDIT_DEL_ATT);
				storage.remove(WapConstants.IS_NEED_SMS);
			},
			
			//计算附件数量
			attNumber : function(){
				var num=$("#accFooter li").length;
				if(num!=0){
					$("#attNumber").html(num);
				}else{
					$("#attNumber").html("");
				}
			},
			//删除附件
			attDel : function(obj,fileIndexId,attId){
				if(attId){
					var attDelIds=$("#attDelIds").val();
					if(attDelIds==''){
						$("#attDelIds").val(attId);
					}else{
						$("#attDelIds").val(attDelIds+=","+attId);
					}
					$(obj).parent('li').remove();
				}else{
					$(obj).parent('li').remove();
					$("#"+fileIndexId).remove();
				}
				wap._editService.attNumber();
					
			},
			//附件选择
			fileChange : function(objId,fileIndex){
				$("#"+objId).hide();
				var file = $("#"+objId).get(0).files[0];
				var fileSize = (Math.round(file.size * 100 / (1024 * 1024)) / 100).toString() + 'MB';
				var htmlStr='<li class="fn-clear new-file">';
				var laIn=file.name.lastIndexOf('.');
				if(laIn!=-1){
					htmlStr+=getFilePic(file.name.substring( laIn+1 ));
				}else{
					htmlStr+=getFilePic("");
				}
				htmlStr+='<a href="javascript:void(0)" class="icon-img icon-del2" onclick="wap._editService.attDel(this,\'upFile'+fileIndex+'\')"></a>';
				htmlStr+='<p class="acc-tt f-16">'+file.name+'</p>';
				htmlStr+='<p class="acc-dd">'+fileSize+'</p>';
				htmlStr+='</li>';
				$("#accFooter").append(htmlStr);
				wap._editService.attNumber();
				wap._editService.auto();
				fileIndex++;
				if(isWeiXin()){
					$('.ui-file').append('<input type="file" name="file" id="upFile'+fileIndex+'" accept="MIME_type">');
				}else{
					$('.ui-file').append('<input type="file" name="file" id="upFile'+fileIndex+'" accept="*/*">');
				}
				$("#upFile"+fileIndex).on('change',function(){
					wap._editService.fileChange("upFile"+fileIndex,fileIndex);
				});
			}
		},
		//判断是否有emoji表情符
		isEmojiCharacter:function(substring) { 
		    for ( var i = 0; i < substring.length; i++) {  
		        var hs = substring.charCodeAt(i);  
		        if (0xd800 <= hs && hs <= 0xdbff) {  
		            if (substring.length > 1) {  
		                var ls = substring.charCodeAt(i + 1);  
		                var uc = ((hs - 0xd800) * 0x400) + (ls - 0xdc00) + 0x10000;  
		                if (0x1d000 <= uc && uc <= 0x1f77f) {  
		                    return true;  
		                }  
		            }  
		        } else if (substring.length > 1) {  
		            var ls = substring.charCodeAt(i + 1);  
		            if (ls == 0x20e3) {  
		                return true;  
		            }  
		        } else {  
		            if (0x2100 <= hs && hs <= 0x27ff) {  
		                return true;  
		            } else if (0x2B05 <= hs && hs <= 0x2b07) {  
		                return true;  
		            } else if (0x2934 <= hs && hs <= 0x2935) {  
		                return true;  
		            } else if (0x3297 <= hs && hs <= 0x3299) {  
		                return true;  
		            } else if (hs == 0xa9 || hs == 0xae || hs == 0x303d || hs == 0x3030  
		                    || hs == 0x2b55 || hs == 0x2b1c || hs == 0x2b1b  
		                    || hs == 0x2b50) {  
		                return true;  
		            }  
		        }  
		    }  
		},
};

