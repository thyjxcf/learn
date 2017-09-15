<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#include "/common/handlefielderror.ftl">
<#import "/common/commonmacro.ftl" as commonmacro>

<@htmlmacro.moduleDiv titleName="">
<script type="text/javascript" charset="utf-8" src="${request.contextPath}/static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${request.contextPath}/static/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${request.contextPath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/ueditor/themes/default/css/ueditor.css"/>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/ueditor/themes/iframe.css"/>
<style type="text/css">
	//#edui1{z-index:90}
	#edui_fixedlayer{z-index:9999 !important;}
</style>
<script>
	//实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('htmlContent',{
        //focus时自动清空初始化时的内容
        autoClearinitialContent:false,
        //关闭字数统计
        wordCount:false,
        //关闭elementPath
        elementPathEnabled:false,
        //默认的编辑区域高度
        initialFrameHeight:285,
        //更多其他参数，请参考ueditor.config.js中的配置项
        toolbars:[[
	         'fullscreen', 'source', '|', 'undo', 'redo', '|',
	         'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
	         'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
	         'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
	         'directionalityltr', 'directionalityrtl', 'indent', '|',
	         'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
	         'link', 'unlink', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
	         'simpleupload', 'insertimage', 'emotion', 'scrawl', 'music', 'map', 'insertframe', 'template', '|',
	         'horizontal', 'date', 'time', 'spechars', 'wordimage',
	         'print', 'preview', 'searchreplace', 'help', 'drafts'
	     	]]
    });
    $("#edui1").css("z-index","90");
    
    //调整全屏功能
    $("#edui3_state").click(function(){
		var fontSize = $(this).attr("class").indexOf("edui-state-checked"); 
		if(fontSize != (-1)){
			$("#edui1").css("z-index","9999");
		}else{
			$("#edui1").css("z-index","90");
		}
	});
	
	var isSubmit = false;
	function doSave(){
		if(!checkAllValidate("#officeTempCommentForm")){
			return;
		}
	  	var content = $("textarea[name='officeTempComment.htmlContent']").val();
		if (content == null || trim(content) == '') {
		   showMsgWarn("内容不能为空!");
	       return;
		}
		isSubmit = true;
		var url = "${request.contextPath}/office/tempcomment/tempComment-save.action";
		var options = {
          target : '#officeTempCommentForm',
          url : url,
          success : showSuccess,
          dataType : 'json',
          clearForm : false,
          resetForm : false,
          type : 'post'
        };
      	$("#officeTempCommentForm").ajaxSubmit(options);
		
	}
      
  //操作提示
  function showSuccess(data) {
  if (!data.operateSuccess) {  
      showMsgError(data);
      isSubmit = false;
      return;
    }else{
        showMsgSuccess("操作成功！", "提示", function(){
		 doBack();
		});
		return;
    }
  }
  
</script>
<form id="officeTempCommentForm">
	<input type="hidden" name="officeTempComment.id" value="${(officeTempComment.id)!}"/>
	<input type="hidden" name="officeTempComment.objectId" value="${(officeTempComment.objectId)!}"/>
	<input type="hidden" name="type" value="${type!}"/>
	<table class="table-edit table-edit2">
		<tr>
			<th><span class="c-red">*</span>模板名称：</th>
			<td><input type="text" maxlength="100" class="input-txt" style="width:350px;" id="title" name="officeTempComment.title" value="${(officeTempComment.title)!}" notNull="true"/></td>
		</tr>
	    <tr>
	    	<th>所属分类：</th>
	    	<#if type?exists&&type=='1'><td>系统模板</td></#if>
			<#if type?exists&&type=='2'><td>单位模板</td></#if>
			<#if type?exists&&type=='3'><td>部门模板</td></#if>
			<#if type?exists&&type=='4'><td>个人模板</td></#if>
	    </tr>
	    <tr>
		    <td colspan="2">
				<div class="ml-5">
				<div class="msg-des mt-10 mb-10 ml-50">
					<textarea id="htmlContent" name="officeTempComment.htmlContent" type="text/plain" style="width:900px;height:360px;">${((officeTempComment.htmlContent)!'')?html}</textarea>
					<textarea style="display:none;" id="simpleContent" name="officeMsgSending.simpleContent"></textarea>
				</div>
				</div>
		    </td>
	    </tr>
	</table>
	<div class=" t-center">
    	<a href="javascript:void(0)" onclick="doSave()" class="abtn-blue-big mb-10">保存</a>
        <a href="javascript:void(0)" onclick="doBack()" class="abtn-blue-big mb-10 ml-20">取消</a>
    </div>
</form>
<script>
	function doBack(){
		load("#container","${request.contextPath}/office/tempcomment/tempComment.action?type=${type!}");
	}
	
</script>
</@htmlmacro.moduleDiv>