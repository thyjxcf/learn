<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="">
<div class="dt">
	<span class="item-name">待办事项</span>
</div>
<#if memos?exists && (memos?size>0)>
<div class="srcoll-wrap" style="height:455px;">
	<div class="tit">时间<span>具体事项</span></div>
	<div class="list">
    	<#list memos as ent>
    	<#assign i = ent_index%2>
    		<p <#if i!=0>class="even"</#if>><span style="white-space: nowrap;">${ent.timeString!}</span>${ent.content!}</p>
    	</#list>
    </div>
</div>
<#else>
<div class="no_data" style="">暂无安排!</div>
</#if>
<script type="text/javascript">
$(function(){
	$('.desk-item-inner .srcoll-wrap').jscroll({ W:"5px"//设置滚动条宽度
		,Bar:{  Pos:""//设置滚动条初始化位置在底部
				,Bd:{Out:"#999fa5",Hover:"#5b5c5d"}//设置滚动滚轴边框颜色：鼠标离开(默认)，经过
				,Bg:{Out:"#999fa5",Hover:"#67686a",Focus:"#67686a"}}//设置滚动条滚轴背景：鼠标离开(默认)，经过，点击
		,Btn:{btn:false}//是否显示上下按钮 false为不显示
	});			
})
</script>
</@htmlmacro.moduleDiv>