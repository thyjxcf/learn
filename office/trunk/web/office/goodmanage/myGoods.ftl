<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
function doSearch(){
	var searchType = $("#searchType").val();
	var searchName = $("#searchName").val();
	load("#myGoodsListDiv", "${request.contextPath}/office/goodmanage/goodmanage-myGoods-list.action?searchType="+searchType+"&searchName="+searchName);
}

function doMyGoodsReturn(goodsReqId){
	openDiv("#myGoodsLayer", "#myGoodsLayer .close,#myGoodsLayer .submit,#myGoodsLayer .reset", "${request.contextPath}/office/goodmanage/goodmanage-myGoods-return.action?officeGoodsReq.id="+goodsReqId, null, null, "900px");
}

var isSubmit=false;
function doMyGoodsApply(goodsReqId){
	if(isSubmit){
		return;
	}
	if(!confirm("您确定要重新申请？")){
		return;
	}
	isSubmit=true;
	var options = {
       url:'${request.contextPath}/office/goodmanage/goodmanage-myGoods-apply.action?officeGoodsReq.id='+goodsReqId, 
       success : showApplyReply,
       dataType : 'json',
       clearForm : false,
       resetForm : false,
       type : 'post'
    };
	
    $('#myGoodsform').ajaxSubmit(options);
}

function showApplyReply(data){
	isSubmit=false;
	if(!data.operateSuccess){
			   if(data.errorMessage!=null&&data.errorMessage!=""){
				   showMsgError(data.errorMessage);
				   return;
			   }else{
			   	   showMsgError(data.promptMessage);
				   return;
			   }
	}else{
		   		showMsgSuccess("申请成功！","",function(){
		   			doSearch();
				});
	}
}
</script>
<div class="pub-table-wrap">
	<div class="pub-table-inner">
	<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
	<div class="query-part">
		<div class="query-tt ml-10">
			<span class="fn-left">物品类别：</span>
		</div>
		<div class="select_box fn-left mr-10">
		<@common.select style="width:150px;" valId="searchType" valName="searchType" myfunchange="doSearch" >
			<a val=""><span>全部</span></a>
        	<#if goodsTypeList?exists && (goodsTypeList?size>0)>
            	<#list goodsTypeList as type>
            		<a val="${type.typeId}" <#if searchType?default('') == type.typeId>class="selected"</#if>><span>${type.typeName}</span></a>
            	</#list>
        	</#if>
		</@common.select></div>
    	<div class="query-tt ml-10"><span class="fn-left">物品名称：</span></div>
        <input name="searchName" id="searchName" value="${searchName!}" maxLength="50" class="input-txt fn-left" style="width:170px;"/>
		<a href="javascript:void(0)" onclick="doSearch();" class="abtn-blue fn-left ml-20">查找</a>
		<div class="fn-clear"></div>
	</div>
	</div>
	</div>
</div>
<div id="myGoodsListDiv"></div>
<div class="popUp-layer" id="myGoodsLayer" style="display:none;width:700px;"></div>
<script>
	$(document).ready(function(){
		doSearch();
	});
</script>
</@common.moduleDiv>