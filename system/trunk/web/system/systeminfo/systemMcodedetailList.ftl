<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="平台基础信息设置">
	 <#if mcodelist?exists && mcodelist.mcodeId?default('') =='DM-WJFL'>
	 	<input type="hidden" id="mcodeId" name="mcodeId" class="ui-select-value" value="DM-WJFL"/>
     </#if>
     <form id="form2" action="" name="form2">
      <@htmlmacro.tableList addClass="ui-radio-box-table" id="tablelist">
		    <tr>
			    <th width="30">选择</th>
			    <th>排序编号</th>
			    <th>代码</th>
			    <th>代码名称</th>
			    <th>状态</th>
			    <th>维护</th>
		    </tr>
	    <#if mcodedetailList?exists && (mcodedetailList.size() > 0)>
	      <#list mcodedetailList as x>
		    <tr >
			    <td class="t-center"><p><span class="<#if mcodelist.maintain==1&&x.type==1&&isTopUnit>ui-checkbox<#else>ui-checkbox ui-checkbox-disabled</#if>" data-name="a"><input type="checkbox" class="chk" <#if mcodelist.maintain==1&&x.type==1&&isTopUnit>name="arrayIds"<#else>name="arrayId" disabled</#if> value="${x.id?default('')}" /></span></p></td>
			    <td>${x.orderId?default("")}</td>
			    <td>${x.thisId?default("")}</td>
			    <td>${x.content?default("")}</td>
			    <td>
			        <#if isTopUnit>
					  	<div class="ui-select-box <#if (mcodelist.type==1&&mcodelist.maintain==1)||(mcodelist.type==1&&mcodelist.maintain==0)> <#if x.type==2>ui-select-box-disable</#if> <#else>ui-select-box-disable </#if> fn-left" style="width:60px;">
				            <input type="text" id="mcodedetail_txt_${x.id?default('')}" class="ui-select-txt" readonly value="" />
				            <input type="hidden" id="mcodedetail_${x.id?default('')}" name="mcodedetail_${x.id?default('')}" class="ui-select-value" />
				            <a class="ui-select-close"></a>
				            <div class="ui-option" id="yt" myfunchange="mcodedetailUsingChange('${x.id}');">
				                <div class="a-wrap">
				                <a val="${x.id?default('')}" id="mcodedetail_Y_${x.id?default('')}" <#if x.isUsing?default(0)==1>class="selected"</#if>><span>启用</span></a>
				                <a val="${x.id?default('')}" id="mcodedetail_N_${x.id?default('')}" <#if x.isUsing?default(0)==0>class="selected"</#if>><span>停用</span></a>
				                </div>
				            </div>
				        </div>
					 <#else>
					 	<#if x.isUsing?default(0)==1>启用
					 	<#elseif x.isUsing?default(0)==0>停用
					 	</#if>
					 </#if>
				 	<span id="mcodedetail_${x.id?default('')}_span" style="display:none;">
				 		<#if x.isUsing?default(0)==1>启用</#if>
				  		<#if x.isUsing?default(0)==0>停用</#if>
				 	</span>		  	
			    
			    </td>
			    <td>
			       <#if mcodelist.maintain==1&&x.type==1>
					  	<#if isTopUnit>
					  	  <a href="javascript:void(0)" onclick="editMcode('${x.id?default('')}');">可维护(点击维护)</a>
					  	<#else>
					  	  非顶级单位不可维护
					  	</#if>
			  		<#else>
				  		系统默认不可维护
			  		</#if>
			    </td>
		    </tr>
	      </#list>
	    <#else>
		    <tr>
	            <td colspan="6"><p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td>
	        </tr>  	
        </#if>
      	</@htmlmacro.tableList>
      	<@htmlmacro.Toolbar  container="#divContainer" >
      	    <#if mcodelist?exists>
	           <#if isTopUnit>
	                <span class="ui-checkbox ui-checkbox-all" data-all="no"><input type="checkbox" class="chk" onclick='javascript:<@htmlmacro.doCheckbox checkboxname="arrayIds" />'>全选</span>
                    <#if mcodelist.mcodeId?default('') =='DM-WJFL'>
                    <#else>
                    <a href="javascript:void(0)" class="abtn-blue" onclick="mcodeDelete();">删除</a>	
		      	    </#if>
		      	    <#if mcodelist.maintain?exists&&mcodelist.maintain==1>
		      	        <a href="javascript:void(0)" class="abtn-blue" onclick="newMcode();">新增</a>
		      	    </#if>
		      	<#else>
		      	    <font size='2px;'>只有顶级单位才能维护微代码</font>    
		        </#if>
		     </#if> 	    
      	</@htmlmacro.Toolbar>
      </form>
      	
      	<#--新增弹出层-->
      	<div class="popUp-layer" id="newLayer" style="display:none;width:600px;">
			<p class="tt"><a href="#" class="close">关闭</a><span>新增微代码明细项</span></p>
		    <div class="wrap">
		        <table border="0" cellspacing="0" cellpadding="0" class="table-edit mt-20">
		            <tr>
		                <th>排序编号：</th>
		                <td><input type="text" name="new_orderid" id="new_orderid" class="input-txt" style="width:200px;" msgName="排序编号" notNull="true" maxLength="6" datatype="integer" title="请输入长度不超过6位的非负整数"><span class="c-orange ml-10">*</span></td>
		            </tr>
		            <tr>
					  	 <th>代码：</th>
		                 <td>
		                     <input name="new_id" id="new_id" type="hidden" value="">
		                     <input type="text" name="new_thisid" id="new_thisid" class="input-txt" style="width:200px;" msgName="代码" notNull="true"  ><span class="c-orange ml-10">*</span>
		                 </td>
		            </tr>
		           
		            <tr>
					  	 <th>代码名称：</th>
		                 <td>
		                     <input type="text" name="new_content" id="new_content" class="input-txt" style="width:200px;" msgName="代码名称" notNull="true" maxLength="30" ><span class="c-orange ml-10">*</span>
		                 </td>
		            </tr>
		        </table>
		    </div>
		    <p class="dd">
		        <a class="abtn-blue" href="javascript:void(0)" onclick="saveMcode();">确定</a>
		        <a class="abtn-blue reset ml-5" href="javascript:void(0)" onclick="$('#newLayer').jWindowClose();">取消</a>
		    </p>
		</div>
		<#--修改弹出层-->
		<div class="popUp-layer" id="editLayer" style="display:none;width:600px;">
			<p class="tt"><a href="#" class="close">关闭</a><span>修改微代码明细项</span></p>
		    <div class="wrap">
		        <table border="0" cellspacing="0" cellpadding="0" class="table-edit mt-20">
		            <tr>
		                <th>排序编号：</th>
		                <td><input type="text" name="edit_orderid" id="edit_orderid" class="input-txt" style="width:200px;" msgName="排序编号" notNull="true" maxLength="6" datatype="integer" title="请输入长度不超过6位的非负整数"><span class="c-orange ml-10">*</span></td>
		            </tr>
		            <tr>
					  	 <th>代码：</th>
		                 <td>
		                     <input name="edit_id" id="edit_id" type="hidden" value="">
		                     <input type="text" name="edit_thisid" id="edit_thisid" class="input-txt" style="width:200px;" msgName="代码" notNull="true"  value="${mcodedetail.thisId?default('')}"><span class="c-orange ml-10">*</span>
		                 </td>
		            </tr>
		           
		            <tr>
					  	 <th>代码名称：</th>
		                 <td>
		                     <input type="text" name="edit_content" id="edit_content" class="input-txt" style="width:200px;" msgName="代码名称" notNull="true" maxLength="30" value="${mcodedetail.content?default('')}"><span class="c-orange ml-10">*</span>
		                 </td>
		            </tr>
		        </table>
		    </div>
		    <p class="dd">
		        <a class="abtn-blue" href="javascript:void(0)" onclick="updateMcode();">确定</a>
		        <a class="abtn-blue reset ml-5" href="javascript:void(0)" onclick="$('#editLayer').jWindowClose();">取消</a>
		    </p>
		</div>
		
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>      	
<script type="text/javascript">
      var avaOrderId="${avaOrderId?default('0')}";
      $(document).ready(function(){
          vselect();
          
          $('#newBtn').click(function(e){
				e.preventDefault();
				$('#newLayer').jWindowOpen({
					modal:true,
					center:true,
					close:'#newLayer .close,#newLayer .submit,#newLayer .reset'
				});
		  });
      })
      
      function mcodedetailUsingChange(id){
            var elem = $("#mcodedetail_"+id)[0];
			var mcodeid='';
			<#if mcodelist.mcodeId?exists>mcodeid='${mcodelist.mcodeId?default('')}'</#if>
			var mcodedetailId=elem.value;
			var selectindex=elem.selectedIndex;
			var selectindexTxt = $("#mcodedetail_txt_"+id)[0].value;
			
			var elemspan=document.getElementById(elem.name+'_span');
			$.ajax({
			     url:"${request.contextPath}/system/admin/platformInfoAdmin-remoteMcodedetailList-setMcodedetailUsing.action",
			     type:"post",
			     data:{"mcodelistId":mcodeid,"mcodedetailId":mcodedetailId},
			     dataType:"json",
			     timeout:500000000,
			     error:function(){
			         showMsgError('修改微代码类型失败！');
			     },
			     success:function(data){
			        var result = data.result;
		            if(result==${MCODE_ERROR}){	
						if(selectindexTxt=='停用'){
							$("#mcodedetail_Y_"+id)[0].click();
						}
						if(selectindexTxt=='启用'){
							$("#mcodedetail_N_"+id)[0].click();
						}
						
						addFieldError($("#mcodedetail_txt_"+id)[0],'该微代码类型不能修改启停状态，或者有相同代码名称的已启用');
					}
					else if(result==${MCODE_SUCCESS}){
						if(selectindexTxt=='停用'){
							addActionMessage('停用该微代码类型成功');
							//addFieldSuccess(elem,'停用该微代码类型成功');
							//elem.blur();
							//elem.focus();
							if(elemspan){
								elemspan.innerHTML='停用';
							}
						}
						else if(selectindexTxt=='启用'){
							addActionMessage('启用该微代码类型成功');
							//addFieldSuccess(elem,'启用该微代码类型成功');
							//elem.blur();
							//elem.focus();
							
							if(elemspan){
								elemspan.innerHTML='启用';
							}
						}
					}
					else if(result==${MCODE_NO_ACCREDIT}){
						if(selectindexTxt=='停用'){
							$("#mcodedetail_Y_"+id)[0].click();
						}
						if(selectindexTxt=='启用'){
							$("#mcodedetail_N_"+id)[0].click();
						}
						addFieldError($("#mcodedetail_txt_"+id)[0],'对不起，只有顶级教育局才有权限操作微代码启停');
					}
				 }
			})
			return ;
	  }
      
      function editMcode(val){
			$.ajax({
			     url:"${request.contextPath}/system/admin/platformInfoAdmin-remoteMcodedetailList-getMcodedetailById.action",
			     type:"post",
			     data:{"mcodedetailId":val},
			     dataType:"json",
			     timeout:5000,
			     error:function(){
			         showMsgError('获取微代码详情失败！');
			     },
			     success:function(data){
			            var result = data.mcodeDetail;
			            document.getElementById('edit_id').value=result.id;
						document.getElementById('edit_orderid').value=result.orderId;
						document.getElementById('edit_thisid').value=result.thisId;
						document.getElementById('edit_content').value=result.content;
			          
				        $('#editLayer').jWindowOpen({
							modal:true,
							center:true,
							close:'#editLayer .close,#editLayer .submit,#editLayer .reset'
					    });
			     }
			})
	 }
	 
	 function updateMcode(){
		if(!checkAllValidate("#editLayer")){
			return false;
		}
		
		var id=document.getElementById('edit_id').value;
		var orderId=document.getElementById('edit_orderid').value;
		var thisId=document.getElementById('edit_thisid').value;
		var content=document.getElementById('edit_content').value;
		if(thisId.match(/^\w{${mcodelist.length?default(0)},${mcodelist.length?default(0)}}?$/)==null){
			addFieldError('edit_thisid','保存代码必须是数字或者字母且长度为${mcodelist.length?default(0)}！');
			return false;
		}
		
		$.ajax({
			     url:"${request.contextPath}/system/admin/platformInfoAdmin-remoteMcodedetailList-updateMcodedetail.action",
			     type:"post",
			     data:{"id":id,"orderId":orderId,"thisId":thisId,"content":content},
			     dataType:"json",
			     timeout:5000,
			     error:function(){
			         showMsgError('更新微代码失败！');
			     },
			     success:function(data){
			            $('#editLayer').jWindowClose(); 
			            var result=data.result;
						if(result[0] == 0){  //0:代表成功，其它:代表失败
							var mcodeid= $("#mcodeId").val();
							<#if mcodelist.mcodeId?default('') =='DM-WJFL'>
                    			load("#divContainer", "${request.contextPath}/system/admin/platformInfoAdmin-systemMcodedetailList.action?modID=${modID?default('')}&&mcodeId=DM-WJFL");	
                    		<#else>
                    			load("#divContainer", "${request.contextPath}/system/admin/platformInfoAdmin-systemMcodedetailList.action?modID=${modID?default('')}&&mcodeId="+mcodeid);	
                    		</#if>
							//parent.mcodeListFrame.document.location.href="platformInfoAdmin-systemMcodedetailList.action?modID=${modID?default('')}&&mcodeId="+mcodeid+"&pageIndex=${pageIndex}";
						}else{
							showMsgError(result[1]);
						}
			     }
			})
		
		
    }
    
    <#if mcodelist.mcodeId?exists>
		function newMcode(){
			document.getElementById('new_thisid').value='';
			document.getElementById('new_content').value='';
			document.getElementById('new_orderid').value=avaOrderId;
			
			$('#newLayer').jWindowOpen({
				modal:true,
				center:true,
				close:'#newLayer .close,#newLayer .submit,#newLayer .reset'
			});
		}
	</#if>
	
	function saveMcode(){
			
		if(!checkAllValidate("#newLayer")){
			return false;
		}
		var mcodeId=document.getElementById('mcodeId').value;
		var orderId=document.getElementById('new_orderid').value;
		var thisId=document.getElementById('new_thisid').value;
		if(thisId.match(/^\w{${mcodelist.length?default(0)},${mcodelist.length?default(0)}}?$/)==null){
			addFieldError('new_thisid','保存代码必须是数字或者字母且长度为${mcodelist.length?default(0)}！');
			return false;
		}
		
		var content=document.getElementById('new_content').value;
		
		$.ajax({
			     url:"${request.contextPath}/system/admin/platformInfoAdmin-remoteMcodedetailList-saveMcode.action",
			     type:"post",
			     data:{"mcodeId":mcodeId,"orderId":orderId,"thisId":thisId,"content":content},
			     dataType:"json",
			     timeout:5000,
			     error:function(){
			         showMsgError('保存微代码失败！');
			     },
			     success:function(data){
			            $('#newLayer').jWindowClose(); 
			            var result=data.result;
						if(result[0] == 0){  //0:代表成功，其它:代表失败
							var mcodeid= $("#mcodeId").val();
							load("#divContainer", "${request.contextPath}/system/admin/platformInfoAdmin-systemMcodedetailList.action?modID=${modID?default('')}&&mcodeId="+mcodeid);	
							//parent.mcodeListFrame.document.location.href="platformInfoAdmin-systemMcodedetailList.action?modID=${modID?default('')}&&mcodeId="+mcodeid+"&pageIndex=${pageIndex}";
						}else{
							showMsgError(result[1]);
						}
			     }
			})
	}
	
	var reload = 0;
	function mcodeDelete(){
		var selects = $("input[name='arrayIds']:checked");
		if(selects.length < 1) {
		    showMsgError('请选择要删除的记录！');
		    return;
		}
		if(!showConfirm("您确认要删除信息吗？")) return;
		
		var options = {
	       url:"${request.contextPath}/system/admin/platformInfoAdmin-remoteMcodedetailList-deleteMcode.action?modID=${modID?default('')}&&mcodeId=${mcodeId?default('')}", 
	       dataType : 'json',
	       clearForm : false,
	       resetForm : false,
	       type : 'post',
	       timeout : 5000,
	       error : showError,
	       success : showReply
	    };
		$("#form2").ajaxSubmit(options);	
		reload = 1;
	}
	
	function showError() {
	    showMsgError("删除失败！");
	}
	
	function showReply(data) {
	    var result = data.promptMessageDto;
	    if(result.operateSuccess) {
	        showMsgSuccess(result.promptMessage,'',cancelOperate);
	    }else{
	        showMsgError(result.promptMessage);
	    }
	}
	
	function cancelOperate(){
	   var mcodeid= $("#mcodeId").val();
	   load("#divContainer", "${request.contextPath}/system/admin/platformInfoAdmin-systemMcodedetailList.action?modID=${modID?default('')}&&mcodeId="+mcodeid);	
	}
</script>      	
</@htmlmacro.moduleDiv>