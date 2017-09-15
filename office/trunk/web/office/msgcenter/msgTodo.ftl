<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="待办消息">
<script>
contextPath = '${request.contextPath}';
$(document).ready(function() {
  load("#msgTodoList",contextPath+"/office/msgcenter/msgcenter-listTodoMessages.action");
});
function searchMsg(){
  var searchTitle = $("input[name='searchTitle']").val();
  if(searchTitle=='请输入关键字') searchTitle='';
  var url = contextPath + "/office/msgcenter/msgcenter-listTodoMessages.action?";
  url +="&searchTitle="+encodeURIComponent(searchTitle);
  load("#msgTodoList",url);
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
    	<span class="tt">待办邮件</span>
    	<#else>
    	<span class="tt">待办消息</span>
    	</#if>
    </div>
	<div id="msgTodoList"></div>
</div>
</@common.moduleDiv>