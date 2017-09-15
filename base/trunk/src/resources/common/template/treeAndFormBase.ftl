<#import "baseMacro.ftl" as base >

<@base.head title/>

<body>
	
    <!-- main content -->
	<div id="mainContent">
		<#--当前所在位置 -->
		<@base.breadcrumbs/>
		
        <div class="treeTableOut">
	        <div class="treeOut">
				<div class="treeInner"> 
		      	<#--左边那棵树 -->
	            <#if treeInner?exists>
					<@base.tree treeInner/>
				</#if>
				</div>
                <div id="drag"></div><!--drag end-->
			</div>
        	
            <div id="drag"></div><!--drag end-->
			
			
            <#--右边部分 -->
            <!-- table out on the right side -->
            <div class="tableOut" id="treeShow">
            	<#-- 头 -->
    	        <div class="gridBox">
	            	<#--上部的tab框 -->
	            	<#if tab?exists> 
	            		<@base.tab tab />
	            	</#if> 
	            	
	            	<#--tab框下的状态栏，主要是异动用到 -->
	            	<#if tabOption?exists> 
	            		<@base.tabOption tabOption />
	            	</#if> 
	            	
	            	<#--右边列表上部的条件框 -->
	            	<#if condition?exists> 
	            		<@base.condition  condition />
	            	</#if> 
	            	<#--列表的表头 -->
	            	<#if tabletitle?exists> 
	            		<@base.tabletitle  tabletitle />
	            	</#if>
            	</div><!--gridBox end-->
            	
            	<#-- 中间 -->
            	<!-- tree % table out -->
    			<div id="tbody">
	            	<#-- 右边列表 --> 
	            	<#if dateTable?exists> 
	            		<#--列表页面 -->
	            		<@base.dateTable  dateTable />
	            	<#elseif dataform?exists>
	            		<#--form页面 -->
	            		<#--因为有特殊的东西要处理，这里引进js文件 -->
	            		<script type="text/javascript" src="${request.contextPath}/static/js/newstyle/jquery.bgiframe.js"></script>
	            		<@base.dataform  dataform />
	            	</#if> 
        	 	</div>
    			<!-- End #tbody -->
            	<#--尾巴 右边列表底部 -->
            	<#if dateTableBottom?exists> 
            		<@base.dateTableBottom  dateTableBottom />
            	</#if> 
            </div>
        	<!-- End .tableOut -->
        </div>
        <!-- End .treeTableOut -->
    </div>
    <!-- End #mainContent -->

</body>
</html>
