<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="草稿箱">
<script>
contextPath = '${request.contextPath}';
$(document).ready(function() {
  load("#msgDraftboxList",contextPath+"/office/msgcenter/msgcenter-listDraftMessages.action?1=1&pageIndex=1&canDeFile=${canDeFile!}");
});
function searchMsg(){
  var searchTitle = $("input[name='searchTitle']").val();
  if(searchTitle=='请输入关键字') searchTitle='';
  var url = contextPath + "/office/msgcenter/msgcenter-listDraftMessages.action?";
  url+="&searchTitle="+encodeURIComponent(searchTitle);
  load("#msgDraftboxList",url);
}
</script>
<div class="msg-content">
	<div class="msg-dataInfo">
    	<div class="msg-search fn-right fn-clear" style="width:700px;">
            <div class="pub-search fn-right">
                <input value="请输入关键字" id="searchTitle" name="searchTitle" class="txt" type="text" style="width:150px;"
                 onblur="if(this.value=='') this.value='请输入关键字';" onfocus="if(this.value=='请输入关键字') this.value='';">
                <a href="javascript:void(0);" class="btn" onclick="searchMsg();">查找</a>
            </div>
        </div>
    	<span class="tt">草稿箱</span>
    </div>
	<div id="msgDraftboxList"></div>
</div>
</@common.moduleDiv>