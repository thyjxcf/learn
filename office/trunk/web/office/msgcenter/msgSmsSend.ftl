<#import "/common/htmlcomponent.ftl" as common>
<#import "/common/commonmacro.ftl" as commonmacro>
<@common.moduleDiv titleName="发消息">
<script>
	
	var isSubmit = false;
	function saveMsg(){
	  	if(isSubmit){
			return;
		}
		//初始化userIds
		setUsers();
	  	var userIds = $("#userIds").val();
	  	var phones=$("#phones").val();
		if (trim(userIds) == ""&&trim(phones)=="") {
		   showMsgWarn("收信人或手机号不能为空!");
	       return;
		}
	   	 var smsContent = $("#smsContent").val();
	    	if (smsContent == null || trim(smsContent) == "") {
			   showMsgWarn("短信内容不能为空!");
		    return;
			}
			if(getAbsoluteLength($('#smsContent').val())>500){
				showMsgWarn("短信字符长度不能超过500!");
				return;
			}
			if ($("#timing").attr("checked") == "checked") {
		 	var smsTime = $("#smsTime").val();
		 	if (smsTime == null || trim(smsTime) == "") {
				   showMsgWarn("定时发送时间为空!");
			       return;
				}
		 }
		isSubmit = true;
		var noteUrl = "${request.contextPath}/office/msgcenter/msgcenter-msgSmsSave.action";
		var options = {
          target : '#noteform',
          url : noteUrl,
          success : showSuccess,
          dataType : 'json',
          clearForm : false,
          resetForm : false,
          type : 'post'
        };
      	$("#noteform").ajaxSubmit(options);
		
	}
      
  //操作提示
  function showSuccess(data) {
    if (data!=null && data!=''){
      showMsgError(data);
      isSubmit = false;
      return;
    }else{
        showMsgSuccess("操作成功！", "提示", function(){
		  loadMsgDiv(8);
		});
		return;
    }
  }
 	var isSubmitt=false;
 	function txtSave(){
 		var txt=$("#uploadContentFileInput").val();
 		if(txt==null||txt==''){
 			showMsgWarn("请上传excel文件");
 			return;
 		}
 		isSubmitt = true;
 		var noteUrl = "${request.contextPath}/office/msgcenter/msgcenter-msgTxtSave.action";
		var options = {
          target : '#noteform',
          url : noteUrl,
          success : showSuccesss,
          dataType : 'json',
          clearForm : false,
          resetForm : false,
          type : 'post'
        };
      	$("#noteform").ajaxSubmit(options);
 	}
 	
 	//操作提示
  function showSuccesss(data) {
    if(!data.operateSuccess){
			   if(data.errorMessage!=null&&data.errorMessage!=""){
				   showMsgError(data.errorMessage);
				   isSubmit = false;
				   return;
			   }
			}else{
				var htmlStr=data.businessValue;
				var strs= new Array(); 
				strs=htmlStr.split(",");
				var strL=$('#userSpanDiv span').length+strs.length;
				var flag = false;
				if(htmlStr!=null&&htmlStr!=''){
					if(strL<=50){
						for(i=0;i<strs.length;i++){
								$('#userSpanDiv span').each(function(fn){
									var spanId=$(this).attr('id');
									if(strs[i] == spanId){
										flag = true;
										return false;
									}
							});
							if(!flag){
								$("<span id="+strs[i]+" type="+8+" detailName="+strs[i]+">"+strs[i]+"</span>").appendTo($("#userSpanDiv"));
							}
						}
					}
				}
				if(strL<=50){
					setUsers();
				}else{
					showMsgWarn("短信接收人不能超过50个");
				}
				return;
  		}
  }
  function getAbsoluteLength(str) {
	  var len = 0;
	  for ( var i = 0; i < str.length; i++) {
	    str.charCodeAt(i) > 255 ? len += 2 : len++;
	  }
	  return len;
 } 
</script>
<form action="" method="post" name="noteform" id="noteform" enctype="multipart/form-data">
	<input type="hidden" id="id" name="officeMsgSending.id" value="">
	<input type="hidden" id="phones" name="phones" value="">
    <div class="msg-content" style="margin-top:10px;">
    	<div class="sendMsg-form">
            <div class="tt fn-clear">
            	<span class="fn-left" style="margin-top:10px;"><span class="c-red">*</span>收信人(或号码)：</span>
                <div class="pub-search fn-left" style="margin-top:5px;">
                    <input type="text" value="" class="txt" id="searchTxt" onkeyup="searchUsers();">
                    <a href="javascript:void(0);" class="btn" onclick="searchUsers();">添加</a>
                    <div id="userNamesDiv" class="pub-search-list" style="display:none;">
                        <a href="javascript:void(0);"></a>
                    </div>
                </div>
                <p class="pt-5 attDiv">
                <!--position: absolute; opacity: 0; width: 83px; height: 25px; cursor: pointer; left: 806px; top: 268px;filter:alpha(opacity=0)-->
                <input id="uploadContentFileInput" name="" type="text" class="input-txt input-readonly" readonly="readonly" style="width:100px;margin-left:20px;" value="" maxLength="125"/>&nbsp;&nbsp;
            	<span class="upload-span1 "><a href="javascript:void(0);" class="abtn-blue upfile-btn upload-span" style="margin-top:-5px;">文件选择</a></span>
            	<input id="uploadAttFile0" style="" id="uploadAttFile0" name="uploadAttFile" hidefocus type="file" onchange="uploadContent1(this);" value="">
            	<a href="javascript:void(0);" class="abtn-blue upfile-btn upload-span" style="margin-top:-5px;" onclick="txtSave();">导入</a>
            	(excel文件内容为号码)
        		</p>
            </div>
            <div class="msg-user-wrap mt-10" style="">
				<@commonmacro.selectAddressBookAllLayerTel userObjectId="userIds" deptObjectId="deptIds" unitObjectId="unitIds" detailObjectId="detailNames" callback="resetUserSpanDiv" preset="setUsers" sendToOtherUnit="${sendToOtherUnit?string('true','false')}">
					<a href="javascript:void(0);" class="msg-user-add" title="添加"></a>
					<input type="hidden" name="officeMsgSending.userIds" id="userIds" value="${officeMsgSending.userIds!}">
					<input type="hidden" name="officeMsgSending.deptIds" id="deptIds" value="${officeMsgSending.deptIds!}">
					<input type="hidden" name="officeMsgSending.unitIds" id="unitIds" value="${officeMsgSending.unitIds!}"> 
					<input type="hidden" name="detailNames" id="detailNames" value="${officeMsgSending.detailNames!}" readonly="readonly" class="select_current02">
				</@commonmacro.selectAddressBookAllLayerTel>
                <!--<div style="min-height:18;max-height:70px;line-height:18px;border:0px;padding:5px 50px 5px 0;overflow-x:hidden;overflow-y:auto;" id="userSpanDiv">-->
                <div class="msg-user" id="userSpanDiv" style="min-height:45px;max-height:120px;line-height:18px;overflow-y:auto;padding:5px 50px 5px 0;">
                </div>
            </div>
            <p class="tt pt-10 pl-10">
            	<span class="num">长度<span id="smsContentStat">${smsContentLength!}</span>/500</span>
            	<span>
            		发送短信
            	</span>
            </p>
            <div class="send-sms-wrap mt-10" style="">
                <i></i>
                <textarea id="smsContent" name="officeMsgSending.smsContent" onkeyup="statWordCount(this);" onpropertychange="statWordCount(this);"></textarea>
            </div>
            <div class="tt send-timing pt-10 pl-10 fn-clear" style="">
            	<span class="ui-checkbox send-timing-btn fn-left" id="timingSpan" onclick="checkTime(this);">
            		<input type="checkbox" id="timing" name="officeMsgSending.timing" value="true" class="chk">定时发送
        		</span>
                <div class="send-timing-inner fn-left" style="display:none;">
                    <span class="ml-15 fn-left">短信时间：</span>
                    <@common.datepicker class="input-txt" style="width:150px;" name="officeMsgSending.smsTime" id="smsTime" notNull="true"
				   msgName="短信时间" dateFmt="yyyy-MM-dd HH:mm"/>
                </div>
            </div>
            <div class="form-bt mt-10">
            	<a href="javascript:void(0);" class="abtn-blue ml-15" onclick="saveMsg();">发送</a>
        	</div>
    	</div>
    </div>
</form>
</@common.moduleDiv>
<script type="text/javascript">
//初始化是调用方法设置按钮位置
$(function(){
	$('.msg-user').on('mouseover','span',function(){
		$(this).addClass('hover').siblings('span').removeClass('hover');
	});
	$('.msg-user').on('mouseout','span',function(){
		$(this).removeClass('hover');
	});
	$('.msg-user').on('click','span',function(){
		$(this).remove();
	});
	resetUserSpanDiv();
	resetFilePos();
});

//重置上传位置的计算
function resetFilePos(){
	try{
		$("#uploadAttFile0").css({"position":"absolute","-moz-opacity":"0","opacity":"0","filter":"alpha(opacity=0)","width":$(".upload-span1 a").width() + 27,"height":$(".upload-span1").height(),"cursor":"pointer"});
		$("#uploadAttFile0").offset({"left":$(".upload-span1").offset().left});		
		$("#uploadAttFile0").css({"display":""});
		$("#uploadAttFile0").offset({"top":$(".upload-span1").offset().top });
	}catch(e){
	}
}

function searchUsers(){
	//中文转码
	var searchTxt = $("#searchTxt").val();
	var reg=/^1\d{10}$/;
	if(reg.test(searchTxt)){
		addToSelected(searchTxt, '8', searchTxt);
		setUsers();
	}else{
			if(searchTxt!='' && trim(searchTxt)!=''){
				load("#userNamesDiv","${request.contextPath}/office/msgcenter/msgcenter-searchUser.action?searchTxt="+encodeURIComponent(searchTxt));
				$("#userNamesDiv").show();
			}else{
				$("#userNamesDiv").hide();
			}
	}
}

//如果已经包含，则不再增加
function addToSelected(id, type, detailName){
	var flag = false;
	if($('#userSpanDiv span').length<50){
		$('#userSpanDiv span').each(function(fn){
			var spanId=$(this).attr('id');
			if(id == spanId){
				flag = true;
				return false;
			}
		});
		if(!flag){
			$("<span id="+id+" type="+type+" detailName="+detailName+">"+detailName+"</span>").appendTo($("#userSpanDiv"));
		}
		$("#userNamesDiv").hide();
		document.getElementById("userSpanDiv").style.height="";
	}else{
		showMsgWarn("短信接收人不能超过50个");
	}
}

//根据显示的内容，初始化input隐藏域的值
function setUsers(){
	var userIds = "";
	var deptIds = "";
	var unitIds = "";
	var detailNames = "";
	var phones="";
	
	var userNamesTemp = "";
	var deptNamesTemp = "";
	var unitNamesTemp = "";
	
	var i = 0;
	$('#userSpanDiv span').each(function(fn){
		var spanId=$(this).attr('id');
		var type=$(this).attr('type');
		var spanDetailName=$(this).attr('detailName');
		if(type==2){
			if(userIds == ""){
				userIds += spanId;
				userNamesTemp += spanDetailName;
			}else{
				userIds += "," + spanId;
				userNamesTemp += "," + spanDetailName;
			}
		}else if(type==4){
			if(deptIds == ""){
				deptIds += spanId;
				deptNamesTemp += spanDetailName;
			}else{
				deptIds += "," + spanId;
				deptNamesTemp += "," + spanDetailName;
			}
		}else if(type==5){
			if(unitIds == ""){
				unitIds += spanId;
				unitNamesTemp += spanDetailName;
			}else{
				unitIds += "," + spanId;
				unitNamesTemp += "," + spanDetailName;
			}
		}else if(type=8){
			if(phones == ""){
				phones += spanId;
			}else{
				phones += "," + spanId;
			}
		}
	});
	$("#userIds").val(userIds);
	$("#deptIds").val(deptIds);
	$("#unitIds").val(unitIds);
	$("#phones").val(phones);
	
	//将用户名称，部门名称，单位名称拼接一起
	detailNames += unitNamesTemp;
	if(deptNamesTemp != ""){
		if(detailNames != ""){
			detailNames += "," + deptNamesTemp;
		}else{
			detailNames += deptNamesTemp;
		}
		
	}
	if(userNamesTemp != ""){
		if(detailNames != ""){
			detailNames += "," + userNamesTemp;
		}else{
			detailNames += userNamesTemp;
		}
	}
	$("#detailNames").val(detailNames);
}

function resetUserSpanDiv(){
	if($("#detailNames").val().length > 0){
		var userIds = $("#userIds").val().split(",");
		var deptIds = $("#deptIds").val().split(",");
		var unitIds = $("#unitIds").val().split(",");
		var phones = $("#phones").val().split(",");
		var detailNames = $("#detailNames").val().split(",");
		var divSpan = "";
		var ss = 0;
		
		if($("#unitIds").val()!=""){
			for(var i = 0; i < unitIds.length; i++ ){
				divSpan += "<span id="+unitIds[i]+" type='5' detailName="+detailNames[ss]+">"+detailNames[ss]+"</span>";
				ss++;
			}
		}
		if($("#deptIds").val()!=""){
			for(var i = 0; i < deptIds.length; i++ ){
				divSpan += "<span id="+deptIds[i]+" type='4' detailName="+detailNames[ss]+">"+detailNames[ss]+"</span>";
				ss++;
			}
		}
		if($("#userIds").val()!=""){
			for(var i = 0; i < userIds.length; i++ ){
				divSpan += "<span id="+userIds[i]+" type='2' detailName="+detailNames[ss]+">"+detailNames[ss]+"</span>";
				ss++;
			}
			if($("#phones").val()!=""){
				for(var i = 0; i < phones.length; i++ ){
					divSpan += "<span id="+phones[i]+" type='8' detailName="+phones[i]+">"+phones[i]+"</span>";
				}
			}
		}
		$("#userSpanDiv").html(divSpan);
		document.getElementById("userSpanDiv").style.height="";
	}else{
		//置空
		$("#userSpanDiv").html("");
		document.getElementById("userSpanDiv").style.height="18";
	}
}

function checkTime(timeObj) {
	if($(timeObj).hasClass('ui-checkbox-current')){
		$('.send-timing-inner').hide();
	}else{
		$('.send-timing-inner').show();
	}
}

  function statWordCount(field) {
	  var stat = document.getElementById(field.id + "Stat");
	  if (stat) {
	    stat.innerHTML = getAbsoluteLength(field.value);
	  }
  }
  
  	function uploadContent1(target){
  		var filename=$(target).val();
			var name=filename.substring(filename.lastIndexOf(".")+1,filename.length);
			if(name!=""&&name.toUpperCase()=='XLS'||name.toUpperCase()=='XLSX'){
				$("#uploadContentFileInput").val($(target).val());
			}else{
				showMsgWarn("请上传excel文件");
			}
	}
	
</script>