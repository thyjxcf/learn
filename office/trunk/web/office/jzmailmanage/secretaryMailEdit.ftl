<#import "/common/htmlcomponent.ftl" as common>
<#import "/common/commonmacro.ftl" as commonmacro>
<@common.moduleDiv>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
<script type="text/javascript" charset="utf-8" src="${request.contextPath}/static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${request.contextPath}/static/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${request.contextPath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/ueditor/themes/default/css/ueditor.css"/>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/ueditor/themes/iframe.css"/>
<script>
	var ue = UE.getEditor('content',{
        //focus时自动清空初始化时的内容
        autoClearinitialContent:false,
        //关闭字数统计
        wordCount:false,
        //关闭elementPath
        elementPathEnabled:false,
        //默认的编辑区域高度
        initialFrameHeight:300,
        //更多其他参数，请参考ueditor.config.js中的配置项
        toolbars:[[
	         'source', '|', 'undo', 'redo', '|',
	         'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
	         'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
	         'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
	         'directionalityltr', 'directionalityrtl', 'indent', '|',
	         'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
	         'link', 'unlink', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
	         'help', 'drafts'
	     	]]
    });
    
    
	var isSubmit =false;
	function doSave(){
		if(isSubmit){
			return;
		}
		if(!checkAllValidate("#officeMailform")){
			return;
		}
		
		isSubmit = true;
		var options = {
	       url:'${request.contextPath}/office/jzmailmanage/jzmailmanage-mailInfoSave.action', 
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
			  	//mySearch();
			  	load("#container","${request.contextPath}/"+'office/jzmailmanage/jzmailmanage.action');
			});
			return;
		}
	}
</script>
<div class="pub-table-wrap">
<form action="" method="post" name="officeMailform" id="officeMailform">
<input type="hidden" id="id" name="officeJzmail.id" value="${officeJzmailInfo.id?default('')}"/>
<@common.tableDetail divClass="table-form">
		<tr>
		    <th colspan="4" style="text-align:center;">${topUnitName}信箱使用说明</th>
		</tr>
        <tr>
        	<th style="width:20%"><span class="c-red">*</span> 内容：</th>
            <td style="width:80%" colspan="3">
            	<p><textarea id="content" name="officeJzmailInfo.content" msgName="内容" notNull="true" type="text/plain" style="width:850px;height:200px;" notNull="true">${officeJzmailInfo.content!}</textarea></p>
	        </td>
        </tr>
        <tr>
        	<td colspan="4" class="td-opt">
	            <a href="javascript:void(0);" id="btnReject" class="abtn-blue" onclick="doSave();">保存</a>
            </td>
        </tr>
  </@common.tableDetail>
</form>
</@common.moduleDiv>