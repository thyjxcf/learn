<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="平台基础信息设置">
     <form id="form1" action="" name="form1">
		<div class="query-builder">
		    <div class="query-part">
		        <!--
		        1、下拉框因为是模拟的，用了div块元素，对齐需要左浮动或者是有浮动;
		        2、两个元素中间的间距用内补丁padding和外补丁margin来实现；对应的样式分别是pl-5、pr-10、pt-5、pb-20……ml-10、mr-20、mt-5、mb-30；具体多少像素在实际开发的时候调试；
		        3、模拟下拉框、input的宽度，在元素里直接用 style="width:100px;"来处理。
		        -->
		        <div class="query-tt">从：</div>
				<@htmlmacro.datepicker class="input-txt fn-left" name="beginTime" value="${(beginTime?string('yyyy-MM-dd'))?if_exists}"/>
		        
		        <div class="query-tt ml-10">到：</div>
		  	    <@htmlmacro.datepicker class="input-txt fn-left" name="endTime" value="${(endTime?string('yyyy-MM-dd'))?if_exists}"/>
		        
		        <div class="query-tt ml-10">子系统：</div>
		        <@htmlmacro.select style="width:120px;" valName="subSystem" valId="subSystem">
					<a val="-1"><span>所有子系统</span></a>
					<#list subSystemList as ss>
		  		  	    <a val="${ss.id?default('')}" <#if subSystemId?exists&&subSystemId==ss.id>selected</#if>><span>${ss.name?default('')}</span></a>
		  		  	</#list>
	            </@htmlmacro.select>
		        
		        <div class="query-tt ml-10">单位：</div>
		        <@htmlmacro.select style="width:120px;" valName="unitid" valId="unitid">
					<#list unitList as x>
			  		  	<a val="${x.id?default('')}" <#if unitId?exists&&unitId.equals(x.id)>selected</#if>><span>${x.name?default('')}</span></a>
			  		</#list>
	            </@htmlmacro.select>
		        
		        <a href="javascript:void(0)" class="abtn-blue ml-30 fn-left" id="search_btn" onclick="chooseList();">查找</a>
		        <#if loginInfo.unitType == 1>
  		  		   <div class="query-tt ml-10"><a href='javascript:logConfig();'>各子系统日志清理配置</a></div>  	  	
	  	  		</#if>
		        <div class="fn-clear"></div>
		    </div>
		</div>
		
		<div id="cDivLogList">
		     
		</div>
		
		<#--修改弹出层-->
		<div id="hiddenContext">
			
		</div>
	</form>

<script type="text/javascript">
     $(document).ready(function(){
         vselect();
         chooseList();         
     })
     
     function chooseList(){
		var beginTime=document.getElementById('beginTime').value;
		var endTime=document.getElementById('endTime').value;
		var subSystem=document.getElementById('subSystem').value;
		var unitId=document.getElementById('unitid').value;
		
		if(!checkElement(document.getElementById('beginTime'),'日志起始时间')){	
			return false;
		}
		else if(!checkElement(document.getElementById('endTime'),'日志截至时间')){	
			return false;
		}
		else if(!checkDate(document.getElementById('beginTime'),'日志起始时间')){
			return false;
		}
		else if(!checkDate(document.getElementById('endTime'),'日志截至时间')){
			return false;		
		}
		else if(!checkAfterDate(document.getElementById('beginTime'), document.getElementById('endTime'))){
			return false;
		}
		load("#cDivLogList", "${request.contextPath}/system/admin/platformInfoAdmin-logList.action?modID=${modID?default('')}&&beginTime="+beginTime+"&&endTime="+endTime+"&&subSystem="+subSystem+"&&unitid="+unitId);	
    }
    
    function logConfig(){
        var url = "${request.contextPath}/system/admin/platformInfoAdmin-logConfig.action";
        load("#hiddenContext",url,displayContext);
	}
	
	function displayContext(){
	    $('#editLayer').jWindowOpen({
			modal:true,
			center:true,
			close:'#editLayer .close,#editLayer .submit,#editLayer .reset'
		});
	}
</script>
</@htmlmacro.moduleDiv>