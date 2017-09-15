<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="院系管理">
<form>
<div id="loadData">
<p class="tt"><span>请选择</span></p>
<div class="wrap pa-10">
	<#if showLetterIndex>
	<p class="letter-filter letter-filter-select">
    	<a href="javascript:refreshObjectDiv('')" class="current">全部</a>
    	<#list  firstNameLetterSet as letter><#--显示字母列表 -->
        <a href="javascript:refreshObjectDiv('${letter}')">${letter}</a>
        </#list>
    </p>
    </#if>
    <table border="0" cellspacing="0" cellpadding="0" class="public-table table-list mt-10">
	    <#list objects?chunk(2) as row>
        <tr>
        	<#list row as x>
            <td>
            	<#if useCheckbox=="true">
            	<p><span class="ui-checkbox <#if -1<objectIds!?index_of(x.id)>ui-checkbox-current</#if>">
            		<input class="chk" type="checkbox" <#if -1<objectIds!?index_of(x.id)> checked="checked"</#if> 
            		id="ids${x.id}" name="idss" value="${x.id?default('')}"/>
            		<input type="hidden" id="${x.id}"  name="names" value="${x.objectName}"/> 
            		${x.objectName}<#if x.objectCode?default('')!="">(${x.objectCode?default('')})</#if>
            	</span></p>
            	<#else>
            	<a class="lnk" onclick="javascript:<#if iframe>parent.</#if>submitObject('${idObjectId}','${nameObjectId}','${x.id}','${x.objectName}',<#if callback?exists>callbackObjects('${callback!}')<#else>null</#if>);return false;" href="#" >
			        ${x.objectName?default('')}<#if x.objectCode?default('')!="">(${x.objectCode?default('')})</#if>
			    </#if>
            </td>
            </#list>
        </tr>
        </#list>
    </table>
</div>
<p class="dd">
	<#if useCheckbox=="true">
    <span class="ui-checkbox ui-checkbox-all" data-all="no"><input type="checkbox" class="chk" onclick="<#if iframe>parent.onSelectAllClick(this.checked,'idss')<#else>onSelectAllDiv(document.getElementById('selectAllDiv'),'idss')</#if>;" id="selectAllDiv">全选</span>
    <a class="abtn-blue submit" href="javascript:<#if iframe>parent.</#if>submitObjects('${idObjectId}','${nameObjectId}',document.getElementsByName('idss'),document.getElementsByName('names'),<#if callback?exists>callbackObjects('${callback!}')<#else>null</#if>)">确定</a>
	</#if>
    <a class="abtn-blue submit" href="javascript:<#if iframe>parent.</#if>cancelObjects('${idObjectId}','${nameObjectId}',<#if callback?exists>callbackObjects('${callback!}')<#else>null</#if>)">清除</a>
    <a class="abtn-gray reset ml-5" href="javascript:<#if iframe>parent.</#if>closeObjectDivs(callbackObjects('${callback!}'))">关闭</a>
</p>
</form>
<div style="position:absolute;z-index:-1;width:100%;height:100%;">  
    <iframe style="width:100%;height:100%;border:0;filter:alpha(opacity=0);-moz-opacity:0"></iframe>  
</div> 
</div>
<script>
function refreshObjectDiv(letter) {
	<#if iframe>
		parent.document.getElementById("letter").value = letter;
		parent.doSearchObject();
	<#else>
		var url = "${request.contextPath}${url?default('')}?nameObjectId=${nameObjectId}&idObjectId=${idObjectId}&useCheckbox=${useCheckbox}&callback=${callback}";
		<#if params?default('') !="">
 			url+="&${params}";
 		</#if>
		var pos = url.indexOf("letter");
		if( pos == -1){
			url += "&letter="+letter;
		}else{
			url = url.substring(0,pos+7) + letter + url.substring(pos+8);
		}
		load("#loadData",url,haveOverlayer);
	</#if>
}

</script>
</@htmlmacro.moduleDiv>