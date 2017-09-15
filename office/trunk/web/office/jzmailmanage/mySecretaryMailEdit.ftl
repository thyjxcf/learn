<#import "/common/htmlcomponent.ftl" as common>
<#import "/common/commonmacro.ftl" as commonmacro>
<#import "../jzmailmanage/archiveWebuploader.ftl" as archiveWebuploader>
<@common.moduleDiv>
<script type="text/javascript" charset="utf-8" src="${request.contextPath}/static/webuploader/webuploader.js"></script>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/webuploader/webuploader.css"/>
<script>

	var isSubmit =false;
	function doSave(){
		if(isSubmit){
			return;
		}
		if(!checkAllValidate("#officeMailform")){
			return;
		}
		
		var mail=$("#mail").val();
		var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		if((mail!=null&&mail!='')){
			if(!myreg.test(mail)){
				showMsgWarn("请输入有效的email!");
				$("#mail").focus();
				return;
			}
		}
		
		var phone=$("#phone").val();
		var phonereg=/^1\d{10}$/;
		if(!phonereg.test(phone)){
			showMsgWarn("请输入有效的手机号码!");
			return;
		}
		
	isSubmit = true;
		var options = {
	       url:'${request.contextPath}/office/jzmailmanage/jzmailmanage-myMailSave.action', 
	       success : showReply,
	       dataType : 'json',
	       clearForm : false,
	       resetForm : false,
	       type : 'post'
	    };
	    isSubmit = true;
	    $('#officeMailform').ajaxSubmit(options);
	}
	
	function showReply(data){
		if(!data.operateSuccess){
		   if(data.errorMessage!=null&&data.errorMessage!=""){
			   showMsgError(data.errorMessage);
			   isSubmit = false;
			   return;
		   }
		}else{
			showMsgSuccess(data.promptMessage,"",function(){
			  	mySearch();
			});
			return;
		}
	}
</script>
<div class="pub-table-wrap">
<form action="" method="post" name="officeMailform" id="officeMailform">
<input type="hidden" id="id" name="officeJzmail.id" value="${officeJzmail.id?default('')}"/>
<@common.tableDetail divClass="table-form">
		<tr>
		    <th colspan="4" style="text-align:center;">留言申请</th>
		</tr>
        <tr>
            <th style="width:20%"><span class="c-red">*</span> 标题：</th>
            <td colspan="3" style="width:70%">
            	<input type="text" class="input-txt fn-left" id="title" style="width:36%;" msgName="标题" notNull="true" maxlength="100" name="officeJzmail.title" value="${officeJzmail.title!}">
	        </td>
        </tr>
        <tr>
        	<th style="width:20%"><span class="c-red">*</span> 单位：</th>
        	<td style="width:30%">
    			${officeJzmail.unitName!}
        	</td>
        	<th style="width:20%"> 姓名:</th>
        	<td style="width:30%">
        		${officeJzmail.createUserName!}
        	</td>
        </tr>
        <tr>
        	<th style="width:20%"> 邮箱：</th>
        	<td style="width:30%">
				<input type="text" class="input-txt fn-left" id="mail" style="width:45%;" maxlength="100" name="officeJzmail.mail" value="${officeJzmail.mail!}">
        	</td>
        	<th style="width:20%"><span class="c-red">*</span> 手机：</th>
        	<td style="width:30%">
				<input type="text" class="input-txt fn-left" id="phone" style="width:35%;" notNull="true" maxlength="100" name="officeJzmail.phone" value="${officeJzmail.phone!}">
        	</td>
        </tr>
        
        <tr>
        	<th style="width:20%"> 是否匿名：</th>
        	<td style="width:30%">
				<span class="ui-radio ui-radio-noTxt <#if officeJzmail.anonymous?default(true)==false>ui-radio-current</#if>" data-name="a">
                <input type="radio" class="radio" <#if officeJzmail.anonymous?default(true)==false>checked="checked"</#if> name="officeJzmail.anonymous" value="false">&nbsp否</span>&nbsp;&nbsp;
                <span class="ui-radio ui-radio-noTxt <#if officeJzmail.anonymous?default(true)==true>ui-radio-current</#if>" data-name="a">
                <input type="radio" class="radio" <#if officeJzmail.anonymous?default(true)==true>checked="checked"</#if> name="officeJzmail.anonymous" value="true">&nbsp是</span></td>
        	</td>
        	<th style="width:20%"></th>
        	<td></td>
        </tr>
        
        <tr>
        	<th style="width:20%"><span class="c-red">*</span> 内容：</th>
            <td style="width:80%" colspan="3">
            	<textarea id="content" name="officeJzmail.content" type="text/plain" rows="4" style="width:100%;height:200px;" maxlength="2000" msgName="内容" notNull="true">${officeJzmail.content!}</textarea>
	        </td>
        </tr>
        <@archiveWebuploader.archiveWebuploaderEditViewer canEdit=true showAttachmentDivId='showAttDiv' editContentDivId='editContentDiv' isSend=true loadDiv=false />
        <p class="pt-15 t-center">
        	<td colspan="4" class="td-opt">
	            <a href="javascript:void(0);" id="btnReject" class="abtn-blue" onclick="doSave();">提交</a>
            	<a href="javascript:void(0);" class="abtn-blue" onclick="goBack();">取消</a>
            </td>
        </p>
  </@common.tableDetail>
</form>
<script>
	function goBack(){
		mySearch();
	}
    
    // 当有文件添加进来之前处理
    function atta() {
    	var innerHTML = '<li  id="'+file.id+'"><img src="'+_contextPath+'/static/images/icon/file/'+getFileType(file.ext)+'">';
    		innerHTML+='<span class="name" title="'+file.name+'">'+file.name+'</span>';            
			innerHTML+='<span class="fr"></span></li>';
       	 	$('#fileList').html($('#fileList').html()+innerHTML);
       	 	
       	 	var innerHtml =  '<li  id="'+file.id+'"><img src="'+_contextPath+'/static/images/icon/file/'+getFileType(file.ext)+'">';
       	 			innerHtml+='<span class="name" title="'+file.name+'">'+file.name+'</span>'; 
			        innerHtml += '<a href="javascript:void(0);" class="att_delete"  onclick="doDeleteContentAtt(\'\',,\''+file.id+'\')">删除</a> ';      		
				$("#upload-spanLi").html(innerHtml);
		attachmentUploader.makeThumb( file, function( error, src ) {
       		 if ( error ) {
           	 	$("#"+file.id).find('img').replaceWith('<span>不能预览</span>');
            	return;
       	 	}
        $("#"+file.id).find('img').attr( 'src', src );
   		 }, 100, 100 );
    };
    
     // 文件上传过程中创建进度条实时显示。
    function attac( ) {
        closeTip();
        showTip("文件上传中....当前进度"+Math.floor(percentage*100) + "%");
        if(Math.floor(percentage*100)==100){
        	closeTip();
        	showTip("文件保存中...");
        }
    };
    
    //上传成功
    function attachment() {
    	closeTip();
    	if(data.operateSuccess){
    		showMsgSuccess("文件保存完成");
			if(data.businessValue){
				var returnValue = data.businessValue.split("*");
			    var innerHtml =  '<a href="javascript:void(0);" class="att_download" dataIdValue="'+returnValue[0]+'" dataValue="'+returnValue[1]+'"';
			    	innerHtml += 'onclick="doDownload(\''+returnValue[1]+'\');" >下载</a>';
			        innerHtml += '<a href="javascript:void(0);" class="att_show" dataId="'+returnValue[0]+'">预览</a>';
			        innerHtml += '<a href="javascript:void(0);" class="att_delete" >删除</a> ';       		
				$("#"+file.id+" .fr").html(innerHtml);
				//$("#contentP li").first().attr("dataValue",returnValue[0]);
				//	if(file.ext!="doc" && file.ext!="docx"){
				//		$(".edit-content").hide();
				//	}else{
				//		$(".edit-content").show();
				//	}
			}
    	}else{
    		showMsgError("文件保存出错");
    	}
    };
    
    //上传出错
    function attachment() {
    	closeTip();
    	showMsgError("文件上传出错");
    };
	
	//无论上传是否成功 都会在上传完成时执行该事件
    function attachment() {
    };
	//所有的事件执行都会触发这个事件
    function attachment() {
    	if ( type === 'startUpload' ) {
            state = 'uploading';
        } else if ( type === 'stopUpload' ) {
            state = 'paused';
        } else if ( type === 'uploadFinished' ) {
            state = 'done';
        }

        if ( state === 'uploading' ) {
            ;
        } else {
            ;
        }
    };
    
	function attachment() {
    	data.phone =$("#phone").val();
    	data.title =$("#title").val();
    	data.mail =$("#mail").val();
    	data.content =$("#content").val();
   	 	//data.ItemCode = $("#txtItemCode").val();
	};  
    
</script>
</@common.moduleDiv>