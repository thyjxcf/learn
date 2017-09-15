	var _office_winOpen = false; //判断窗口是否打开
	var _office_win ; //打开的窗口
	var _office_winTimer;//窗口定时器
	var _office_refreshJs;
	var _office_Index = 10100;
	
	function officeInit(initOpen,initRefreshJs){
		_office_winOpen = initOpen;
		_office_refreshJs = initRefreshJs;
	}
	
	//用来打开全新的窗口
	var wfBoxy;
	function openOfficeWin(formUrl,businessType,operation,instanceType,id,actionUrl,callBackJs,taskHandlerSaveJson,currentStepId,deploy,subsystemId,easyLevel,jsonResult,callBackJs1){
		var url=formUrl+"?businessType="+businessType+"&operation="+operation+"&instanceType="+instanceType+"&id="+id+"&callBackJs="+callBackJs+"&currentStepId="+currentStepId+"&deploy="+deploy+"&subsystemId="+subsystemId+"&easyLevel="+easyLevel+"&callBackJs1="+callBackJs1;
		var ifrmaeContent = "<iframe id='wfIframe' src='"+url+"' scrolling='auto' style='padding: 0px; margin: 0px; border-style: none;background-color: #FFFFFF;'></iframe>";
		$("#wf-actionUrl").val(actionUrl);
		$("#wf-taskHandlerSaveJson").val(taskHandlerSaveJson);
		$("#wf-jsonResult").val(jsonResult);
		wfBoxy=new Boxy(
	    	ifrmaeContent,
		        {
		            modal: true, //模态  本页面处于不活动状态，对话框处于活动状态。即con1中的页面内容处于活动状态(此处为iframe中嵌入的页面内容)
		            title:"流程设计器",  //对话框标题
		            afterHide:function(e){isSubmit=false;closeWfIframe();}, //当对话框隐藏的时候在Boxy对象的上下文执行回调函数
		            afterShow:function(e){}, //当对话框显示的时候在Boxy对象的上下文执行回调函数
		            closeText:"关闭",  //关闭功能按钮的标题文字
		            zIndex:12000	 //用来设置zindex 调整弹出层的遮盖问题
		        }
	     );
	     //高度和宽度需要根据显示器的分辨率去重新计算
	     //高度和宽度都定死
	     var winWidth=1300;//$(window).width()-20;
	     var winHeight=700;//$(window).height()-60;
		 wfBoxy.resize(winWidth,winHeight);  //设置对话框的大小
		 //让父页面自动滚动的顶端
		 window.scrollTo(0,0);
	}
	
	function openOfficeConfirmWin(formUrl,businessType,operation,instanceType,id,actionUrl,callBackJs,taskHandlerSaveJson,currentStepId,deploy,subsystemId,stepType,step,stepName,selTeacherId,selTeacherName,permissionStr,easyLevel,jsonResult,callBackJs1){
		var url=formUrl+"?businessType="+businessType+"&operation="+operation+"&instanceType="+instanceType+"&id="+id+"&callBackJs="+callBackJs+"&currentStepId="+currentStepId+"&deploy="+deploy+"&subsystemId="+subsystemId
		+"&stepType="+stepType+"&step="+step+"&stepName="+stepName+"&taskUserId="+selTeacherId+"&taskUserName="+selTeacherName+"&easyLevel="+easyLevel+"&callBackJs1="+callBackJs1+"&permission="+permissionStr;
		var ifrmaeContent = "<iframe id='wfIframe' src='"+url+"' scrolling='auto' style='padding: 0px; margin: 0px; border-style: none;background-color: #FFFFFF;'></iframe>";
		$("#wf-actionUrl").val(actionUrl);
		$("#wf-taskHandlerSaveJson").val(taskHandlerSaveJson);
		$("#wf-jsonResult").val(jsonResult);
		wfBoxy=new Boxy(
	    	ifrmaeContent,
		        {
		            modal: true, //模态  本页面处于不活动状态，对话框处于活动状态。即con1中的页面内容处于活动状态(此处为iframe中嵌入的页面内容)
		            title:"流程设计器",  //对话框标题
		            afterHide:function(e){isSubmit=false;closeWfIframe();}, //当对话框隐藏的时候在Boxy对象的上下文执行回调函数
		            afterShow:function(e){}, //当对话框显示的时候在Boxy对象的上下文执行回调函数
		            closeText:"关闭",  //关闭功能按钮的标题文字
		            zIndex:12000	 //用来设置zindex 调整弹出层的遮盖问题
		        }
	     );
	     //高度和宽度需要根据显示器的分辨率去重新计算
	     //高度和宽度都定死
	     var winWidth=1400;//$(window).width()-20;
	     var winHeight=700;//$(window).height()-60;
		 wfBoxy.resize(winWidth,winHeight);  //设置对话框的大小
		 //让父页面自动滚动的顶端
		 window.scrollTo(0,0);
	}
	
	function closeOfficeWin(callBackJs,id,easyLevel,jsonResult){
		wfBoxy.hide();
		//保存成功后的回调时间写在这里
		if (callBackJs && callBackJs != "") {
			var callBack=callBackJs+"('"+id+"',"+easyLevel+","+jsonResult+")";
			eval(callBack);
		}
	}
	
	function closeWfIframe(){
		if(typeof(closeWfIframeCallBack) === "function"){
			closeWfIframeCallBack();
			closeWfIframeCallBack="";
		}
//		try{
//			closeWfIframeCallBack();
//		}catch(e){
//			
//		}
	}
	
	function setWfTitle(title){
		wfBoxy.setTitle(title);
	}
	
	//用来打开全新的窗口
	function openOfficeWin1(formUrl,businessType,operation,instanceType,id,actionUrl,callBackJs,taskHandlerSaveJson,currentStepId,deploy,subsystemId){
		if(_office_winOpen){
			return;
		}
		var tempForm = document.createElement("form");    
		tempForm.id="newWindow";    
		tempForm.method="post";    
		tempForm.action=formUrl;    
		tempForm.target='flowEidt';    
		
		var hideInput1 = document.createElement("input");    
		hideInput1.type="hidden";    
		hideInput1.name= "businessType"  
		hideInput1.value= businessType;  
		tempForm.appendChild(hideInput1); 
		
		var hideInput2 = document.createElement("input");    
		hideInput2.type="hidden";    
		hideInput2.name= "operation"  
		hideInput2.value= operation;  
		tempForm.appendChild(hideInput2); 
		
		var hideInput3 = document.createElement("input");    
		hideInput3.type="hidden";    
		hideInput3.name= "instanceType"  
		hideInput3.value= instanceType;  
		tempForm.appendChild(hideInput3); 
		
		var hideInput4 = document.createElement("input");    
		hideInput4.type="hidden";    
		hideInput4.name= "id"  
		hideInput4.value= id;  
		tempForm.appendChild(hideInput4);
		
		var hideInput5 = document.createElement("input");    
		hideInput5.type="hidden";    
		hideInput5.name= "actionUrl"  
		hideInput5.value=actionUrl;  
		tempForm.appendChild(hideInput5); 
		
		var hideInput6 = document.createElement("input");    
		hideInput6.type="hidden";    
		hideInput6.name= "callBackJs"  
		hideInput6.value= callBackJs;  
		tempForm.appendChild(hideInput6); 
		
		var hideInput7 = document.createElement("input");    
		hideInput7.type="hidden";    
		hideInput7.name= "taskHandlerSaveJson"  
		hideInput7.value= taskHandlerSaveJson;  
		tempForm.appendChild(hideInput7); 
		
	 	var hideInput8 = document.createElement("input");
	    hideInput8.type = "hidden";
	    hideInput8.name = "currentStepId"
	    hideInput8.value = currentStepId;
	    tempForm.appendChild(hideInput8);
	    
	    var hideInput9 = document.createElement("input");    
	    hideInput9.type="hidden";    
	    hideInput9.name= "deploy"  
	    	hideInput9.value= deploy;  
	    tempForm.appendChild(hideInput9); 
	    
	    var hideInput10 = document.createElement("input");    
	    hideInput10.type="hidden";    
	    hideInput10.name= "subsystemId"  
	    hideInput10.value= subsystemId;  
	    tempForm.appendChild(hideInput10); 
		
	    
	    
		$('#foo').bind('onsubmit', openWindow('flowEidt'));
		document.body.appendChild(tempForm);    
		$('#foo').click();
		
		tempForm.submit();  
		document.body.removeChild(tempForm);  
		$("#_______overlayer").css({
			"zIndex" : (++_office_Index)
		});
		showTip("正在流程设计中,请到流程设计页处理");
		_office_winTimer = setInterval(checkWinOpen,5000);
	}
	
	function openWindow(name){
		_office_winOpen = true;
		_office_win =  window.open('about:blank',name);
	}
	
	function checkWinOpen(){
		try {
			if(_office_win&&_office_win.name=="flowEidt"){
				
			}else{
				closeTip();
				if (_office_refreshJs && _office_refreshJs != "") {
					if (_office_refreshJs instanceof Function) {
						eval(_office_refreshJs)();
					} else {
						eval(_office_refreshJs);
					}
				}
				clearInterval(_office_winTimer);
			}
		} catch (e) {
			// TODO: handle exception
			closeTip();
			if (_office_refreshJs && _office_refreshJs != "") {
				if (_office_refreshJs instanceof Function) {
					eval(_office_refreshJs)();
				} else {
					eval(_office_refreshJs);
				}
			}
			clearInterval(_office_winTimer);
		}
	}
	
	