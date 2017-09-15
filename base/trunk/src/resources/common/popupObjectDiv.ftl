<#import "/common/popupObjectDivDynamic.ftl" as popupObject>
<div style="position:absolute;z-index:-1;width:101%;height:101%;margin-left:-0.5%;">  
	<iframe style="width:100%;height:100%;border:0;filter:alpha(opacity=0);-moz-opacity:0"></iframe>  
</div>
<a id="close" class="close" hidefocus="true">关闭</a>
<!-- tab切换 开始 -->
<div class="px-20">
    <div class="tabMenu tab_menu">
        <ul class="clearfix" id="title">
            <@popupObject.title />            
        </ul>
    </div>
    <div class="tab_box" id="content">
       <@popupObject.content />
    </div>
</div>
<!-- tab切换 结束 -->

<#if useCheckbox=="true">
<div class="address-selected">
    <p class="dt"><span>已选择：</span>（小提示：点击“×”可删除）</p>
    <div class="level-box">
        <span class="show-part">收起</span>
        <div class="level-list" style="height: auto;" id="nameList">
        </div>
    </div>
</div>
</#if>


<div class="dd">
	<#if useCheckbox=="true">
		<a class="abtn-blue ml-5" id="comfirmCheckbox">确定</a>
	<#else>
		<#-- 单选确定：如果必须选择到叶子节点，则不需要确定。如果要半路中停，如选择部门（多级）、行政区划等，则需要确定按钮-->
		<a class="abtn-blue ml-5" id="comfirmTreeStop" style="disply:none">确定</a> 	
	</#if>
	<a class="abtn-blue ml-5" id="cancel">清空</a>
</div>

<script>

//(同一个页面中出现多个div时的相互影响，可以考虑用${idObjectId}来圈定范围)
popupObject($(document.getElementById("${idObjectId}_div")),'${idObjectId}','${nameObjectId}',<#if callback?exists>'${callback!}'<#else>null</#if>,<#if useCheckbox=="true">true<#else>false</#if>).initPopup();

</script>