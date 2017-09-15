<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="废件箱">
<script>
contextPath = '${request.contextPath}';
$(document).ready(function() {
  load("#msgAbandonList",contextPath+"/office/msgcenter/msgcenter-listAbandonMessages.action");
});
function searchMsg(){
  var searchTitle = $("input[name='searchTitle']").val();
  if(searchTitle=='请输入关键字') searchTitle='';
  var url = contextPath + "/office/msgcenter/msgcenter-listAbandonMessages.action?";
  url+="searchTitle="+encodeURIComponent(searchTitle);
  load("#msgAbandonList",url);
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
    	<span class="tt">废件箱</span>
    </div>
	<div id="msgAbandonList"></div>
</div>
</@common.moduleDiv>