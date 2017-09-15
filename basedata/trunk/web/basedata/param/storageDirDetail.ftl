<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="存放目录详细信息">
<p class="tt"><a href="javascript:void(0);" class="close">关闭</a><span>存储目录<#if storageDir.preset?default(false)>（系统内置）</#if></span></p>
<div id="cd" class="wrap pa-10">
<form action="" method="post" name="mainform" id="mainform">
	<input name="id" id="id" type="hidden" value="${storageDir.id?default('')}">
	<input name="preset" id="preset" type="hidden" value="${storageDir.preset?default(false)?string}">
	<table border="0" cellspacing="0" cellpadding="0" class="table-edit mt-20">
  	<tr>
  	  <th width="50px">类型：</th>
  	  <td style="width:350px;">  
  	  	<div class="ui-select-box fn-left" style="width:260px;" <#if storageDir.id?default('') != ''>disabled</#if>>
            <input type="text" class="ui-select-txt" value="" readonly />
            <input type="hidden" value="" name="type" id="type" class="ui-select-value" />
            <a class="ui-select-close"></a>
            <div class="ui-option">
            	<div class="a-wrap">
            	<#list dirTypes as x>
            		<a val="${x.value}" <#if x.value==storageDir.type>class="selected"</#if>><span>${x.description}</span></a>
		  	  	</#list>
		  	  	</div>
            </div>
        </div>
        <span class="fn-left c-orange mt-5 ml-10">*</span>
  	  </td>
  	</tr>
  	<tr>
  	  <th>目录：</th>
  	  <td><input name="dir" id="dir" type="text" class="input-txt fn-left" style="width:250px;" maxlength="50" value="${storageDir.dir?default('')}" 
  	  	<#if storageDir.preset?default(false) && storageDir.publicDir?default(false)>disabled</#if>><span class="fn-left c-orange mt-5 ml-10">*</span>
  	  </td>	  	  	  	  	  			  	  	  	  	   	  	  	  	  		  	  	  	  	  
	</tr>
	<tr><th colspan="2">（说明：目录为绝对路径，系统内置公共目录不允许修改）</th></tr>
	<tr>
  	  <th>是否激活：</th>
  	  <td>
  	  <span class="ui-checkbox <#if storageDir.active?default(false)>ui-checkbox-current</#if>" onclick="">
  	  <input class="chk" name="active" id="active" type="checkbox" <#if storageDir.active?default(false)>checked</#if>>
  	  </span>
  	  </td>	  	  	  	  	  			  	  	  	  	   	  	  	  	  		  	  	  	  	  
	</tr>							
</table>
</form>
</div>
<p class="dd">
	<a class="abtn-blue" id="btnSave" onclick="doSave();" href="javascript:void(0);">确定</a>
	<a class="abtn-blue reset ml-5" href="javascript:void(0);">取消</a>
</p>

<script>
function doSave(){
	if(!isActionable("#btnSave")){
		return;
	}
	
	if($("#active").is(':checked')){
		$("#active").attr("value","true");	
	}else{
		$("#active").attr("value","false");	
	}
	$("#btnSave").attr("class", "abtn-unable");
	var options = {
	       url:'${request.contextPath}/system/admin/platformInfoAdmin-remoteStorageDir.action', 
	       success : showReply,
	       dataType : 'json',
	       clearForm : false,
	       resetForm : false,
	       type : 'post',
	       timeout : 3000 
	    };
		
	$('#mainform').ajaxSubmit(options);
}

function showReply(data){
	if(!data.operateSuccess){
		$("#btnSave").attr("class", "abtn-blue");
		   if(data.errorMessage!=null&&data.errorMessage!=""){
			   showMsgError(data.errorMessage);
			   return;
		   }else if(data.fieldErrorMap!=null){
			  $.each(data.fieldErrorMap,function(key,value){
				   addFieldError(key,value+"");
			  });
		   }
		return;
	}
	else{
		showMsgSuccess("保存成功！", "提示", function(){
			$("#btnSave").attr("class", "abtn-blue");
			load("#divContent", "${request.contextPath}/system/admin/platformInfoAdmin-storageDir.action");
		}); 
		return;
	}
}
</script>
</@htmlmacro.moduleDiv>
