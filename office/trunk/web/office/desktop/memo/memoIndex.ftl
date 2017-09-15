<#import "/common/htmlcomponent.ftl" as common>
<script type="text/javascript" src="${request.contextPath}/office/desktop/js/memo.js"></script>
<script type="text/javascript" src="${request.contextPath}/office/desktop/js/memoIndex.js"></script>
<script type="text/javascript" src="${request.contextPath}/office/desktop/js/Utils.js"></script>

<@common.moduleDiv titleName="备忘录">
<!--=S 备忘录 Start-->
<div class="dt fn-clear">
	<span class="model-name">备忘录<i>${totalCount!}</i></span>
    <span class="add add-memo">添加备忘</span>
</div>
<div class="memo-list">
    <ul>
    	<li>
        	<div class="memo-wrap">
                <div class="tt">
                	周${Dayary[0]!}
                	${weekDate.get(0)?string("MM月/dd日")}
                </div>
                <div class="list-wrap">
            	<#if currentMemoDto?exists && currentMemoDto?size gt 0>
					<#if currentMemoDto?size gt 8>
						<#list 0..7 as i>
							<#assign memoDto = currentMemoDto.get(i)/>
							<#if "memo" == memoDto.getType()>
								<#assign memo = memoDto.getMemo()/>
								<p title="${appsetting.htmlFilter(memo.getContent())}"><span>${memo.getTime()?string("HH:mm")}</span>
								<#if memo.send! =="1"><font color="#026db7"><@common.cutOff str='${appsetting.htmlFilter(memo.getContent())}' length=10/></font><#else>
									<@common.cutOff str='${appsetting.htmlFilter(memo.getContent())}' length=10/>
								</#if>
								</p>
					        </#if>
					    </#list>
					 <#else>
					 	<#list currentMemoDto as memoDto>
							<#if "memo" == memoDto.getType()>
								<#assign memo = memoDto.getMemo()/>
								<p title="${appsetting.htmlFilter(memo.getContent())}"><span>${memo.getTime()?string("HH:mm")}</span>
								<#if memo.send! =="1"><font color="#026db7"><@common.cutOff str='${appsetting.htmlFilter(memo.getContent())}' length=10/></font><#else>
									<@common.cutOff str='${appsetting.htmlFilter(memo.getContent())}' length=10/>
								</#if>
								</p>
					        </#if>
					    </#list>
					 </#if>
			 	</#if>
                </div>
            </div>
        </li>
    	<li class="add-new add-memo">新增备忘</li>
    	<li class="more more-memo">查看更多 >></li>
        <#if weekMemoDto?exists && weekMemoDto?size gt 0>
			<#assign index = 1/>
			<#list weekMemoDto as memoDtoList>
        	<li>
        		<div class="memo-wrap">
                    <div class="tt">
                    	周${Dayary[index]!}
                    	${weekDate.get(index)?string("MM月/dd日")}
                    </div>
                	<div class="list-wrap">
					<#if memoDtoList?exists && memoDtoList?size gt 0>
						<#if memoDtoList?size gt 8>
							<#list 0..7 as i>
								<#assign memoDto = memoDtoList.get(i)/>
								<#if "memo" == memoDto.getType()>
									<#assign memo = memoDto.getMemo()/>
									<p title="${appsetting.htmlFilter(memo.getContent())}"><span>${memo.getTime()?string("HH:mm")}</span>
									<#if memo.send! =="1"><font color="#026db7"><@common.cutOff str='${appsetting.htmlFilter(memo.getContent())}' length=10/></font>
									<#else><@common.cutOff str='${appsetting.htmlFilter(memo.getContent())}' length=10/></#if>
									</p>
						        </#if>
						    </#list>
						 <#else>
						 	<#list memoDtoList as memoDto>
								<#if "memo" == memoDto.getType()>
									<#assign memo = memoDto.getMemo()/>
									<p title="${appsetting.htmlFilter(memo.getContent())}"><span>${memo.getTime()?string("HH:mm")}</span>
									<#if memo.send! =="1"><font color="#026db7"><@common.cutOff str='${appsetting.htmlFilter(memo.getContent())}' length=10/></font>
									<#else><@common.cutOff str='${appsetting.htmlFilter(memo.getContent())}' length=10/></#if>
									</p>
						        </#if>
						    </#list>
						 </#if>
				 	</#if>
					</div>
	            </div>
	        </li>
			<#assign index= index+1/>
		 </#list>
	 </#if>
    </ul>
	<a href="javascript:void(0);" class="prev"></a>
    <a href="javascript:void(0);" class="next"></a>
</div>
<script type="text/javascript">
	$(function(){
		$('.add-memo').click(function(){
			openDiv('#memoLayer',null,"${request.contextPath}/office/desktop/memo/memo-add.action",true,'#memoLayer .wrap','410');
		});
	});
	function getAbsoluteLength(str) {
	  var len = 0;
	  for ( var i = 0; i < str.length; i++) {
	    str.charCodeAt(i) > 255 ? len += 2 : len++;
	  }
	  return len;
	}
	function closeDiv1(divId){
		//jWindow.close($(divId));
		$(divId).jWindowClose();
		load("#memoDiv","${request.contextPath}/office/desktop/memo/memo.action");
	}
</script>
</@common.moduleDiv>