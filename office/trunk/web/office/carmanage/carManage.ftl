<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>

function addCar(){
	var url="${request.contextPath}/office/carmanage/carmanage-carManageEdit.action";
	load("#carManageListDiv", url);
}

function doSearch(){
	var carNumber = $("#carNumber").val();
	var url="${request.contextPath}/office/carmanage/carmanage-carManageList.action?carNumber="+carNumber;
	load("#carManageListDiv", url);
}

$(document).ready(function(){
	doSearch();
});
</script>

<div class="popUp-layer" id="classLayer3" style="display:none;width:500px;"></div>
<div class="popUp-layer" id="bulletinLayer" style="display:none;top:100px;left:300px;width:700px;height:580px;"></div>
<div class="pub-table-wrap">
	<div class="pub-table-inner">
		<div class="query-builder-nobg mt-5" style="padding:0 0 5px 0;">  
    		<div class="query-part">
				<div class="fn-rel">
					<div class="query-tt ml-10"><span class="fn-left">车牌号码：</span></div>
        			<input name="carNumber" id="carNumber" value="${carNumber!}" maxLength="30" class="input-txt fn-left" style="width:120px;"/>
					<a href="javascript:void(0);" onclick="doSearch();" class="abtn-blue ml-20">查找</a>
		            <a href="javascript:void(0);" class="abtn-orange-new fn-right applyForBtn" onclick="addCar()">新增车辆</a>
		        </div>
		        <div class="fn-clear"/>
			</div>
    	</div>
    </div>
</div>
<div id="carManageListDiv"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>