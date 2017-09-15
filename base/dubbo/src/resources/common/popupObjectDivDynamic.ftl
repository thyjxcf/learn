<#-- 标题 -->
<#macro title>
 	<li class="mainlevel active"><a class="mainlevel_a" val="" txt="">请选择</a></li>
</#macro>
 
 <#--- 内容 -->
<#macro content>
<div> 
	<#--- 节点 -->
	<#if filterObjects?exists && filterObjects.size() != 0>
		<div id="filterNodeDiv" class="level-list level-list-line">
			 <#list filterObjects as x>
			    <a href="javascript:void(0);" class="my_node_filter" val="${x.id}" txt="${x.objectName}" xmlSrc="${x.xmlSrc!}">${x.objectName?default('')}</a> 
			  </#list>
    	</div>
    	<#if treeRoot?exists || objectMap?size != 0 ><p class="level-line"></p></#if> <#-- 横线-->
	</#if>
	
	<#--- 可选中根节点 -->
	<#if treeRoot?exists>
		<#assign x = treeRoot>
		<div id="rootNodeDiv" class="level-list level-list-line user-sList user-sList-radio">
			<span id="span_${x.id}" class="my_node_root" val="${x.id}" txt="${x.objectName}" xmlSrc="" >
    			${x.objectName?default('')}<#if x.objectCode?default('')!="">(${x.objectCode?default('')})</#if>
    		</span>
    	</div>
    	<#if objectMap?size != 0 ><p class="level-line"></p></#if> <#-- 横线-->
	</#if>
	
	<#if objectMap?size != 0 >
	    <div class="level-wrap">
		<#if showLetterIndex?default(false)>
			<p class="letter-filter">
			    <#list firstNameLetterSet as letter><#--显示字母列表 -->
			    <a href="#${letter}" val="${letter}">${letter}</a>
			    </#list>
			</p>
		</#if>
		<#if useCheckbox=="true">
			<p class="pa-10"><a href="javascript:void(0);" id="selectAllDiv">反选</a></p>
		</#if>
	      <div id="contentNodeDiv" class="tree-menu-select select_list02" style="overflow:auto">
	    	<#list objectMap?keys as key >
	        <div class="first-level <#if !(showLetterIndex?default(false))>first-level-noLetter</#if>">
	        	<#if showLetterIndex?default(false)>
	            <span id="${key}" class="level-tt">${key}</span>
	            </#if>
	            <div class="level-list">				       
	                <span class="user-sList <#if useCheckbox=="true">user-sList-checkbox<#else>user-sList-radio</#if>">
	                	<#assign objects = objectMap.get(key)>
	                	<#list objects as x>
		            		<span id="span_${x.id}" class="<#if x.xmlSrc?default('') != ''>my_node_stop<#else>my_node</#if>" val="${x.id}" txt="${x.fullName}" xmlSrc="${x.xmlSrc!}" >
		            			${x.objectName?default('')}<#if x.objectCode?default('')!="">(${x.objectCode?default('')})</#if>
		            		</span>
	                	</#list>
	                </span>
	            </div>
	        </div>
	        </#list>
	      </div>
	    </div>
    </#if>
</div>
</#macro>

<#-- <div id="tabContent"></div> -->
<@content />

 
 