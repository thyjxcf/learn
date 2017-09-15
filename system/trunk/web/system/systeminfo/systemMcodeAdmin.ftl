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
        <div class="query-tt">子系统：</div>
        <@htmlmacro.select style="width:120px;" txtId="subSystemTxt" valName="subSystem" valId="subSystem" myfunchange="changeSubSystem">
			<a val="-2"><span>--请选择子系统--</span></a>
            <a val="-1"><span>所有子系统</span></a>
			<#list subSystemList as ss>
  		  	    <a val="${ss.id?default('')}" <#if subSystemId?exists&&subSystemId==ss.id>selected</#if>><span>${ss.name?default('')}</span></a>
  		  	</#list>
        </@htmlmacro.select>
        
        <div class="query-tt ml-10">维护类型：</div>
        <@htmlmacro.select style="width:120px;" valName="type" valId="type" myfunchange="findMcodelist">
			 <a val="-1" id="typeIndexFirst"><span>所有维护类型</span></a>
			 <a val="0" <#if type?exists&&type==0>selected</#if>><span>可维护</span></a>
  		  	 <a val="1"<#if type?exists&&type==1>selected</#if>><span>可启停</span></a>
  		  	 <a val="2"<#if type?exists&&type==2>selected</#if>><span>仅可查看</span></a>
        </@htmlmacro.select>
        
        <div class="query-tt ml-10">微代码类型：</div>
        <div class="ui-select-box fn-left" style="width:150px;" >
            <input type="text" class="ui-select-txt" value="" readonly/>
            <input type="hidden" id="mcodeId" name="mcodeId" class="ui-select-value" />
            <a class="ui-select-close"></a>
            <div class="ui-option" id="mcodeLi" myfunchange="showMcodedetail();">
            	<div class="a-wrap">
                <a val=""><span>--请选择微代码类型--</span></a>
				<#list mcodeList as mcl>
	  		  	    <a val="${mcl.mcodeId?default('')}" <#if mcodeId?exists&&mcodeId.equals(mcl.id)>selected</#if>><span>${mcl.mcodeName?default('')}</span></a>
	  		  	</#list>
	  		  	</div>
            </div>
        </div>
        <div class="fn-clear"></div>
    </div>
</div>

<div id="divContainer">
     <@htmlmacro.tableList addClass="ui-radio-box-table" id="tablelist">
        <tr>
	        <td colspan="6"><p class="no-data mt-50 mb-50">请先选择:子系统>维护类型>微代码类型！</p></td>
	    </tr> 
     </@htmlmacro.tableList>
</div>

</form>

<script type="text/javascript">
     var infostr; 
     $(document).ready(function(){
          vselect(); ////模拟下拉框
          //load("#divContainer", "${request.contextPath}/system/admin/platformInfoAdmin-systemMcodedetailList.action");
     })
     
     function changeSubSystem(){
		var subsystemselect=document.getElementById('subSystem');
		var typeselect=document.getElementById('type');
		var mcodeselect=document.getElementById('mcodeId');
		if(subsystemselect.value==-2){
		    $("#mcodeLi").empty();
		    $("#mcodeLi").append("<div class='a-wrap'><a val=''><span>--请选择微代码类型--</span></a></div>");
		    document.getElementById('mcodeId').value ="";
			showMcodedetail();
			return;
		}
		else{
			findMcodelist();		
		}
	  }
	  
	  function findMcodelist(){
			var subsystemId=document.getElementById('subSystem').value;
			if(subsystemId==-2){
				showMsgError('请先选择子系统');
				$("#typeIndexFirst")[0].click();
				return false;
			}
			var typeid=document.getElementById('type').value;
			
			$.ajax({
			     url:"${request.contextPath}/system/admin/platformInfoAdmin-remoteMcodedetailList-findMcodeList.action",
			     type:"post",
			     data:{"typeId":typeid,"subsystemId":subsystemId},
			     dataType:"json",
			     async: false,
			     timeout:5000,
			     error:function(){
			         showMsgError('获取微代码类型失败！');
			     },
			     success:function(data){
			            data = data.mcodeList;
			            $("#mcodeLi").empty();
			            infostr=new Array(data.length);
			            var html="<div class='a-wrap'><a val=''><span>--请选择微代码类型--</span></a>";
						for(var i=0; i<data.length;i++){
						    var mcodeId = data[i].mcodeId;
						    var mcodeName = data[i].mcodeName;
						    html+="<a val='"+mcodeId+"'><span>"+mcodeName+"</span></a>";
						    if(data[i].describe=='NULL'){
								infostr[i]='无';				
							}
							else{
								infostr[i]=data[i].describe;
							}
						}
						html+="</div>";
						$("#mcodeLi").html(html);
						vselect(); ////模拟下拉框
						//showMcodedetail();
			     }
			})
			
		}
		
		function showMcodedetail(){
			var mcodeid= $("#mcodeId").val();
		    load("#divContainer", "${request.contextPath}/system/admin/platformInfoAdmin-systemMcodedetailList.action?modID=${modID?default('')}&mcodeId="+mcodeid);	
		}
     
     
</script>
</@htmlmacro.moduleDiv>
