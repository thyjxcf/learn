<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="重要消息">
<script>
contextPath = '${request.contextPath}';
$(document).ready(function() {
  load("#msgImportList",contextPath+"/office/msgcenter/msgcenter-listImportMessages.action");
});
function searchMsg(){
  var searchTitle = $("input[name='searchTitle']").val();
  if(searchTitle=='请输入关键字') searchTitle='';
  var url = contextPath + "/office/msgcenter/msgcenter-listImportMessages.action?";
  url +="&searchTitle="+encodeURIComponent(searchTitle);
  load("#msgImportList",url);
}
</script>
<div class="msg-content">
	<div class="msg-dataInfo">
    	<div class="msg-search fn-right fn-clear">
            <div class="pub-search fn-right">
                <input value="请输入关键字" id="searchTitle" name="searchTitle" class="txt" type="text" style="width:150px;"
                 onblur="if(this.value=='') this.value='请输入关键字';" onfocus="if(this.value=='请输入关键字') this.value='';">
                <a href="javascript:void(0);" class="btn" onclick="searchMsg();">查找</a>
            </div>
        </div>
        <#if switchName>
    	<span class="tt">重要邮件</span>
    	<#else>
    	<span class="tt">重要消息</span>
    	</#if>
    </div>
	<div id="msgImportList"></div>
</div>
</@common.moduleDiv>