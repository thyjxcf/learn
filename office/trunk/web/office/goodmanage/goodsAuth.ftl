<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
function doSearch(){
	var searchName = $("#searchName").val();
	load("#goodsTypeAuthListDiv", "${request.contextPath}/office/goodmanage/goodmanage-goodsAuth-list.action?searchName="+searchName);
}

function doGoodsTypeAuthEdit(goodsAuthId){
	openDiv("#goodsTypeAuthEditLayer", "#goodsTypeAuthEditLayer .close,#goodsTypeAuthEditLayer .submit,#goodsTypeAuthEditLayer .reset", "${request.contextPath}/office/goodmanage/goodmanage-goodsAuth-edit.action?officeGoodsTypeAuth.id="+goodsAuthId, null, null, "900px");
}
</script>
<div class="pub-table-wrap">
	<div class="pub-table-inner">
	<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
	<div class="query-part">
		<a href="javascript:void(0);" onclick="doGoodsTypeAuthEdit('');" class="abtn-orange-new fn-right">添加权限</a>
		<div class="pub-search fn-right mr-10">
            <input type="text" value="" class="txt" id="searchName" placeholder="输入用户名或姓名">
            <a href="javascript:void(0);" class="btn" onclick="doSearch();">查找</a>
        </div>
		<div class="fn-clear"></div>
	</div>
	</div>
	</div>
</div>
<div id="goodsTypeAuthListDiv"></div>
<div class="popUp-layer" id="goodsTypeAuthEditLayer" style="display:none;width:600px;"></div>
<script>
	$(document).ready(function(){
		doSearch();
	});
</script>
</@common.moduleDiv>