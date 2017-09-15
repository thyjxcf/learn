<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.moduleDiv titleName="">
<script type="text/javascript" language="javascript" src="${request.contextPath}/static/js/strspell.js"></script>

<script language="javascript">

function changetype(elem){
	var regiondiv=document.getElementById('regiondiv');//所在行政区
	var classinput=document.getElementById('unitclass');//单位类别
	var schCode= document.getElementById('schCodetr');//学校代码  隐藏的 
	var schCodeType= document.getElementById('schCodeType');//学校性质
	if(elem.value=='${unittype_subedu}'){
		classinput.value='${unitclass_edu}';//unitclass设置为教育局
		regiondiv.style.display='';//显示下属教育局的信息
		jQuery("#schCodeType").hide(); //学校类别  学校办别这一行隐藏
		jQuery("#schCodetr").hide();	//学校代码这一行隐藏
		jQuery(".schcodecls").hide();
		$("#schCode").attr("notNull","false");
		
		$("#unitusetype_sel").addClass("ui-select-box-disable")
		document.getElementById('unitusetype_sel').value = '01';  //只有当单位类型为“教育局”时，才设置单位使用类别为“教育局”，其它情况下设置为“请选择”
		document.getElementById('unitusetype_sel').disabled = true;
		document.getElementById('unitusetype').value = '01'; 
	}else if(elem.value=='${unitclass_nonedu}'){
		classinput.value='${unitclass_edu}';//unitclass设置为非教育局单位
		regiondiv.style.display='none';	
		document.getElementById('unionid').value='';//对于学校单位，清除unionid	
		regiondiv.style.display='none';	   //单位地址隐藏 
		jQuery("#schCodeType").hide(); //学校类别  学校办别这一行显示
		jQuery("#schCodetr").hide();	//学校代码这一行显示 
		jQuery(".schcodecls").hide();
		$("#schCode").attr("notNull","false");
		
		document.getElementById('unitusetype_sel').value = '';
		document.getElementById('unitusetype_sel').disabled = false;
		$("#unitusetype_sel").removeClass().addClass("select-input");
		document.getElementById('unitusetype').value = ''; 
	}else{	
		regiondiv.style.display='none';
		classinput.value='${unitclass_school}';//unitclass设置为学校
		document.getElementById('unionid').value='';//对于学校单位，清除unionid	
		jQuery("#schCodeType").show(); //学校类别  学校办别这一行显示
		<#--if(elem.value=='${unittype_kg}'){
			jQuery("#schCodetr").show();	//学校代码这一行显示 
			jQuery(".schcodecls").hide();	//幼儿园不显示学校代码
			$("#colspanId").attr("colspan","3");
			$("#schCode").attr("notNull","false");
		}else{}-->
			$("#schCode").attr("notNull","true");
			jQuery("#schCodetr").show();	//学校代码这一行显示 
			jQuery(".schcodecls").show();
			$("#colspanId").attr("colspan","");
		
		document.getElementById('unitusetype_sel').value = '';
		document.getElementById('unitusetype_sel').disabled = false;
		$("#unitusetype_sel").removeClass().addClass("select-input");
		document.getElementById('unitusetype').value = ''; 
	}
	if(document.getElementById('parentid').value!=''){
		parentclick(document.getElementById('parentid'));
	}
}

function parentclick(elem){
	var unittype=document.getElementById('unittype');
	var unitid=elem.value;
	var provinceSelect=document.getElementById('province');
		provinceSelect.options.length=0;
		provinceSelect.options[0]=new Option('--请选择--');
		//provinceSelect.disabled='true';
	var citySelect=document.getElementById('city');
		citySelect.options.length=0;
		citySelect.options[0]=new Option('--请选择--');
		//citySelect.disabled='true';
	var sectionSelect=document.getElementById('section');
		sectionSelect.options.length=0;
		sectionSelect.options[0]=new Option('--请选择--');
		//sectionSelect.disabled='true';
	var countryspan=document.getElementById('countryspan');
	var countrycheck=document.getElementById('country');		
	if(unittype.value=='${unittype_subedu}'){
	  if(unitid!=''){	
	  	$.ajax({
			type: "POST",
			url: "${request.contextPath}/basedata/unit/unitAdmin-unitGetRegion.action",
			data: $.param({unitId:unitid},true),
			dataType: "json",
			success: function(data){
				var result=data;
				if(result!=null){
					var obj;
					for(var i=0;i<result.length-3;i++){
						if(i==0){
							obj=provinceSelect;
						}
						else if(i==1){
							obj=citySelect;
						}
						else if(i==2){
							obj=sectionSelect;						
						}
						obj.options.length=0;					
					  	if(result[i]!=null){	
							if(result[i].length>1){
								obj.options[0]=new Option('--请选择--','');
								//obj.setAttribute("disabled","disabled")	
								//obj.removeAttr("disabled")
								for(var j=0;j<result[i].length;j++){
									obj.options[j]=new Option(result[i][j].regionName,result[i][j].regionCode);
									if(result[i][j].id=='${unionid?default('-1')}'){
										obj.options.selectedIndex=j;
										break;
									}
								}
							}else{
								for(var j=0;j<result[i].length;j++){						
									obj.options[j]=new Option(result[i][j].regionName,result[i][j].regionCode);
								}
								if(i==2){
									//只有一个区县教育局时，同时选中乡镇教育局
									countryspan.style.display='';
									$("#countryspan").addClass("ui-checkbox-disabled-checked");
								}else{
									countryspan.style.display='none';
									$("#countryspan").removeClass("ui-checkbox-disabled-checked");
								}
							}
					  	  }										
					}
					document.getElementById('orderid').value=result[3][0];	
					document.getElementById('regionlevel').value=result[4][0];
					
					if(result[5][0]==true){
						villiageEdu();
					}	
				}else{
					
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
		});
	  }
	}else {
		$.ajax({
			type: "POST",
			url: "${request.contextPath}/basedata/unit/unitAdmin-unitGetOrder.action",
			data: $.param({unitId:unitid},true),
			dataType: "json",
			success: function(data){
				var result=data[0];
				if(result!=null){
					document.getElementById('orderid').value=result[0];
					document.getElementById('regionlevel').value=result[1];
					if(result[2]==true){
						villiageEdu();
					}else{
					    notVilliageEdu();
					}
				}	
			},
			error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
		});
	}
}

//乡镇级教育局过滤单位类型中的下属教育局
function villiageEdu(){
	var unittype=document.getElementById('unittype');
	var selectedIndex=unittype.selectedIndex;
	
	var unitTypeArray=new Array();
	<#if unitClassList?exists>
		<#list unitClassList as uc>
			unitTypeArray[${uc_index}]=new Array(2);
			unitTypeArray[${uc_index}][0]='${uc.thisId?default('')}';
			unitTypeArray[${uc_index}][1]='${uc.content?default('')}';
		</#list>
	</#if>
	unittype.options.length=0;
	unittype.options[0]=new Option('--请选择--','');
	var j=1;
	for(var i=0;i<unitTypeArray.length;i++){
		if(unitTypeArray[i][1]!='下属教育局'){
			unittype.options[j]=new Option(unitTypeArray[i][1],unitTypeArray[i][0]);
			j++;
		}
	}
	unittype.selectedIndex=selectedIndex;
	document.getElementById('regiondiv').style.display='none';
	document.getElementById('unitclass').value='${unitclass_school}';//unitclass设置为学校
	document.getElementById('unionid').value='';//对于学校单位，清除unionid	
	jQuery("#schCodeType").show(); //学校类别  学校办别这一行显示
	<#--if(unittype.value=='${unittype_kg}'){
		jQuery("#schCodetr").hide();//幼儿园不显示学校代码
		$("#schCode").attr("notNull","false");
	}else{-->
		$("#schCode").attr("notNull","true");
		jQuery("#schCodetr").show();	//学校代码这一行显示 
	document.getElementById('unitusetype_sel').value = '';
	document.getElementById('unitusetype_sel').disabled = false;
	$("#unitusetype_sel").removeClass().addClass("select-input");
	document.getElementById('unitusetype').value = ''; 
}

//不过滤单位类型中的下属教育局
function notVilliageEdu(){
	var unittype=document.getElementById('unittype');
	var selectedIndex=unittype.selectedIndex;
	
	var unitTypeArray=new Array();
	<#if unitClassList?exists>
		<#list unitClassList as uc>
			unitTypeArray[${uc_index}]=new Array(2);
			unitTypeArray[${uc_index}][0]='${uc.thisId?default('')}';
			unitTypeArray[${uc_index}][1]='${uc.content?default('')}';
		</#list>
	</#if>
	unittype.options.length=0;
	unittype.options[0]=new Option('--请选择--','');
	var j=1;
	for(var i=0;i<unitTypeArray.length;i++){
		unittype.options[j]=new Option(unitTypeArray[i][1],unitTypeArray[i][0]);
		j++;
	}
	unittype.selectedIndex=selectedIndex;
	
	document.getElementById('regiondiv').style.display='none';
}
function sysUserLoginName(){
	if($("#name").val()!=''){
		document.getElementById('userDtoname').value=document.getElementById('name').value.toInitial()+'_admin';
	}
}

function checkUserName(){
	var name=document.getElementById('userDtoname').value;
	if(trim(name)==''){
		//addFieldError('userDtoname','请输入用户名');
		showMsgError('请输入用户名');
		return ;
	}
	$.ajax({
		type: "POST",
		url: "${request.contextPath}/basedata/unit/unitAdmin-serverUserName.action",
		data: $.param( {userName:name},true),
		dataType: "json",
		success: function(data){
			if(data.operateSuccess){
				addFieldSuccess(document.getElementById('userDtoname'),'恭喜您,'+name+'用户名尚可使用');
			}else{
				addFieldError('userDtoname',data.errorMessage);
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
	});
}

</script>
<div id="unitContent">
<form name="unitSaveForm" id="unitSaveForm" method="POST" action="" >
<input name="mark" id="mark" type="hidden" value="${mark?default('1')}"/>
<input name="regcode" id="regcode" type="hidden" value="${regcode?default('')}"/>
<input name="regionlevel" id="regionlevel" type="hidden" value="${regionlevel?default('')}"/>
<input name="usetype" id="usetype" type="hidden" value="${usetype?default('')}"/>
<input name="authorized" id="authorized" type="hidden" value="${authorized?default('')}"/>
<input name="unionid" id="unionid" type="hidden" value="${unionid?default('')}"/>

<p class="tt"><a href="javascript:void(0)" class="close">关闭</a><span>编辑单位</span></p>
<div class="wrap pa-10">
	<table border="0" cellspacing="0" cellpadding="0" class="table-form mt-5">
		<tr>
  	  	  <th width="110"><span class="c-red">*</span>单位名称：</th>
  	  	  <td><input name="name" id="name" type="input" style="width:150px;" class="input-txt" notNull="true" msgName="单位名称" tabindex="1" value="${unitDto.name?default('')}" onkeyup="sysUserLoginName();" title="请输入单位名称，字符数不超过64个(一个中文算2个字符)" maxlength="64"></td>
  	  	  <th width="110"><span class="c-red">*</span>单位类型：</th>
		  <td>
	 		<select name="unittype" id="unittype" style="width:150px;" class="input-txt" notNull="true" msgName="单位类型" tabindex="3" onchange="changetype(this);">
	 		  <option value=''>--请选择--</option>		
	 		  <#list unitClassList! as uc>
	 		  	<option value='${uc.thisId?default('')}' <#if unittype?exists&&uc.thisId==unittype?string>selected</#if>>${uc.content?default('')}</option>
	 		  </#list>
  	  		</select>				  	  		
  	  		<input name="unitclass" id="unitclass" type="hidden" value="${unitclass?default('')}">
		  </td>		  	  	  	  	  
  	  	</tr>
  	  	<tr>		  	  	  	 
  	  	  <input type="hidden" name="etohSchoolId" value="${unitDto.etohSchoolId?default("")}"> 	  
		  <th width="110"><span class="c-red">*</span>上级单位：</th>
		  <td><select name="parentid" id="parentid" style="width:162px;" class="input-txt" notNull="true"  tabindex="9" onchange="parentclick(this);">
		  	  <#list listOfUnitDto! as unit>
		  	  	<option value='${unit.id?default('')}' 
		  	  	<#if !parentid?exists>
		  	  	  <#if unit.id?default('') == unitId?default("")>selected</#if>
		  	  	<#else>
		  	  	  <#if parentid.equals(unit.id)>selected</#if>
		  	  	</#if>>${unit.name?default('')}</option>
		  	  </#list>
  	  		</select>
		  </td>
		  <th width="110">单位使用类别：</th>
		  <td>
		  	<select style="height:25px;width:150px" value="" class="select-input" id="unitusetype_sel" name="unitusetype_sel">
				${appsetting.getMcode("DM-UNITUSETYPE").getHtmlTag(unitDto.unitusetype?default(''),true, 1)}
			</select>
  	  		<input type="hidden" name="unitusetype" id="unitusetype" value="">
		  </td>								  					  					  
		</tr>						
  	  	<tr style="display:none">
  	  	  <th>排序编号：</th>
		  <td><input name="orderid" id="orderid" type="text" style="width:150px;" class="input-txt" tabindex="8" value="${unitDto.orderid?default('')}" title="请输入排序编号，长度不超过4位"></td>	  	  	  	  	  
  	  	  <td colspan="2">&nbsp;</td>		  
		</tr>
		<tr style="display:" id="schCodetr">
			<th><span class="c-red">*</span>区域属性码：</th>
			
			<td id="colspanId" colspan="">
			<@htmlmacro.select style="width:162px"  valName="regionPropertyCode" txtId="regionPropertyCodeId" valId="regionPropertyCode" myfunchange="" notNull="false" msgName="区域属性码" >
				${appsetting.getMcode("DM-TZ-QYSX").getHtmlTag(unitDto.regionPropertyCode?default(''))}
			</@htmlmacro.select>									
			</td>
			<th style="display:;" class="schcodecls"><span class="c-red">*</span>学校代码：</th>
			<td style="display:;" class="schcodecls" id="schCodetrSenc"><input name="schCode" id="schCode" type="text" style="width:150px;" class="input-txt"  notNull="false" msgName="学校代码"
					maxlength="46" value="${unitDto.schCode?default('')?trim}" title="学校代码必须为1-46位">
			</td>
		</tr>
		<tr style="display:" id="schCodeType">
			<th><span class="c-red">*</span>学校类别：</th>
			<td>
			<@htmlmacro.select style="width:162px"  valName="schtype" txtId="schtypeId" valId="schtype" myfunchange="" notNull="false" msgName="学校类别" >
				${appsetting.getMcode("DM-XXLB").getHtmlTag(unitDto.schtype?default(''))}
			</@htmlmacro.select>									
			</td>
			<th><span class="c-red">*</span>办学性质：</th>
			<td>
			<@htmlmacro.select style="width:162px"  valName="runschtype" txtId="runschtypeId" valId="runschtype" myfunchange="" notNull="false" msgName="办学性质" >
				${appsetting.getMcode("DM-XXBB").getHtmlTag(unitDto.runschtype?default(''))}
			</@htmlmacro.select>	
			</td>
		</tr>
		<!--<tr>
		  <th>所在地行政区：</th>					
		  <td colspan="3">
		  <div id="regiondiv" style="display:none;width:160px;">
		  <@commonmacro.selectTree idObjectId="region" nameObjectId="regionName" treeUrl=request.contextPath+"/common/xtree/regionTree.action">
			<input name="regionName" id="regionName" value="" readonly type="text" class="input-txt f-left input-readonly" style="width:140px;" />
			<input name="region" id="region" type="hidden" class="input" value=""> 
		  </@commonmacro.selectTree>
		  </div>
		  </td>	
		</tr>-->
		<tr style="display:none;" id="regiondiv">
		  <th class="send_font_no_width">所在地行政区：</th>					
		  <td colspan="3" class="send_padding_no_width">
		  	<table id="regiondiv1" style="display:;" width="100%" height="100%" cellspacing="0" cellpadding="0" border="0">
		  	  <tr>
		  	  	<td>
			    &nbsp;省：
			    <select name="province" id="province" value="" class="select-input" style="height:25px;width:100px;padding-right:10px;">
			      <option value="">--请选择--</option>
			    </select>
			    &nbsp;市：<select name="city" id="city" class="select-input" style="height:25px;width:100px;padding-right:10px;">
			      <option value="">--请选择--</option>
			    </select>
			    &nbsp;区/县：<select name="section" id="section" class="select-input" style="height:25px;width:100px;padding-right:10px;">
			      <option value="">--请选择--</option>
			    </select>
		    	<span id="countryspan" class="ui-checkbox" style="display:none">
		    	<input name="country" id="country" type="checkbox"  class="chk" value="${unitCountry}" >
		    	<label for="country">乡镇教育局</label></span>
		    	</td>
		      </tr>
		    </table>
		  </td>						  					  
		</tr>
	</table>
</div>

<input name="userDto.orderid" type="hidden" value="${userDto.orderid?default('')}"/>
<input name="userDto.mark" type="hidden" value="${userDto.mark?default('')}"/>
<input name="userDto.type" type="hidden" value="${userDto.type?default('')}"/>
<input name="userDto.deptid" type="hidden" value="${userDto.deptid?default('')}"/>

<p class="tt"><span>同步新增该单位管理员(创建后不能删除)</span></p>
<div class="wrap pa-10 table-content user"  id="user">
<table border="0" cellspacing="0" cellpadding="0" class="table-form mt-5">
	  <tr>
	  	<th width="100"><span class="c-red">*</span>用户名：</th>
	  	<td>
	  	  <input name="userDto.name" id="userDtoname" type="text" style="width:150px;" class="input-txt" notNull="true" msgName="用户名" tabindex="12" value="${userDto.name?default('')}" title="${userNameFieldTip}">
	  	  <img src="${request.contextPath}/static/images/toolmenu_tips3.gif" border="0" style="cursor:pointer;" onclick="checkUserName();" alt="验证该用户名是否可用">
	  	</td>
	  </tr>
	  <tr>
	  	<th><span class="c-red">*</span>登录密码：</th>
	  	<td>
	  	  <input name="userDto.password" id="userDtopassword" type="password" tabindex="13" style="width:150px;" class="input-txt" notNull="true" msgName="登录密码" title="${userPasswordFieldTip}">
	  	</td>
	  </tr>
	  <tr>
	  	<th><span class="c-red">*</span>密码确认：</th>
	  	<td><input name="userDto.confirmPassword" id="confirmPassword" type="password" tabindex="14" style="width:150px;" class="input-txt" notNull="true" msgName="密码确认"></td>
	  </tr>
  	  	<!--start smscenter-->
  	  	<#if useSmsCenter>
  	  	<tr>
	  		<th>创建短信账号：</th>
  	  	  <td colspan="2">
  	  	  	<input type="hidden" name="syncSmsCenter" value="0">
		   &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" id="isSmsCenter1" name="isSmsCenter" value="1" onclick="syncSmsCenter.value=this.value;" ><label for="isSmsCenter1">是，同步创建账号</label>
			&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" id="isSmsCenter2" name="isSmsCenter" value="0" onclick="syncSmsCenter.value=this.value;" checked><label for="isSmsCenter2">否，不同步创建账号</label>  	  	  
  	  	  </td>
  	  	</tr>
  	  	</#if>
</table>
</div>
<input type="hidden" name="currUnitId" id="currUnitId" value="${unitId?default('')}"/>
<p class="dd">
	<a href="javascript:doOnlySave(); " class="abtn-blue submit">保存</a>
	<a href="javascript:void(0);" class="abtn-blue reset ml-5">取消</a>
</p>
</form>
</div>
<script type="text/javascript">
var isSubmitting = false;
function doOnlySave(){
	//单位使用类别
	var unitusertype = $("#unitusetype_sel").val();
	$("#unitusetype").val(unitusertype);
	var schCode= document.getElementById('schCodetr');
	var schCodeType= document.getElementById('schCodeType');
	if(schCodeType.style.display !='none'){
		$("#regionPropertyCodeId").attr("notNull","true");
		$("#schtypeId").attr("notNull","true");
		$("#runschtypeId").attr("notNull","true");
	}else{
		$("#regionPropertyCodeId").attr("notNull","false");
		$("#schtypeId").attr("notNull","false");
		$("#runschtypeId").attr("notNull","false");
		document.getElementById('schCode').value=='';
		document.getElementById('runschtype').value=='';
		document.getElementById('schtype').value=='';
		document.getElementById('regionPropertyCode').value=='';
	}
	var orderid = $("#orderid").val();
	$("#orderid").val(orderid.replace(/[a-zA-Z]+/, ""));
	if(!checkAllValidate("#unitSaveForm")){
		return;
	}
	if(!validateform()){
		return;
	}
	if(isSubmitting){
		return;
	}
	isSubmitting = true;
	
	//教育局单位需要设置unionid	
	if(document.getElementById('unitclass').value=='${unitclass_edu}'){
		getUnionid();
	}
	var modId = '${modID?default("")}';
	var unitId = '${unitDto.parentid?default("")}';
	var options = {
		url : "${request.contextPath}/basedata/unit/unitAdmin-save.action",
	    dataType : 'json',
		success : showSuccessOnly,
		clearForm : false,
		resetForm : false,
		type : 'post',
		error:function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
	};
  	$("#unitSaveForm").ajaxSubmit(options);
}
	
function showSuccessOnly(data){
	if(data.operateSuccess){
		showMsgSuccess("保存成功","",doBackList);
	}else{
		showMsgError(data.errorMessage);
		isSubmitting=false;
		return;
	}
}

function doBackList(){
	var currUnitId = $('#currUnitId').val();
    //load("#container1","${request.contextPath}/basedata/unit/unitAdmin-list.action?"+str);
    load("#container1","${request.contextPath}/basedata/unit/unitAdmin-list.action?unitId="+currUnitId);
	load("#ztreeDiv","${request.contextPath}/common/xtree/unitztree.action?useCheckbox=false"+"&unitId=${loginInfo.getUnitID()?default('')}");
};
function validateform(){
	if(document.getElementById('userDtopassword').value!=document.getElementById('confirmPassword').value){
		//addFieldError('confirmPassword','请确认单位管理员用户的登录密码填写一致');	
		showMsgError('请确认单位管理员用户的登录密码填写一致');
		return false;
	}
	var schCode= document.getElementById('schCodetrSenc');
	var schCodeType= document.getElementById('schCodeType');
	if(schCode.style.display !='none'){
		$("#schCode").attr("notNull","true");
		var pattern = /^[0-9]{1,46}$/;
	   	if(!pattern.test(document.getElementById('schCode').value)) {
	   		showMsgError('学校代码必须为全数字组合(1-46位)！');
			return false;
	   	}
	}else{
		$("#schCode").attr("notNull","false");	
	}
	var unitType = document.getElementById("unittype").value;
	if(unitType != '${unittype_subedu}' && unitType !='${unitclass_nonedu}'){
		if(document.getElementById('unitusetype').value == "01"){
			//addFieldError('unitusetype_sel','单位使用类别不能为教育局');
			showMsgError("单位使用类别不能为教育局");
			return false;
		}
	}
	var schtype = document.getElementById('schtype').value
	if(unitType == '${unittype_kg!}'){
		if(schtype != "" && schtype != ${unitTypeKg111!} && schtype != ${unitTypeKg112!} && schtype != ${unitTypeKg119!}){
			//addFieldError('schtype','学校类别不属于幼儿园类型，请修改');
			showMsgError("学校类别不属于幼儿园类型，请修改");
			return false;
		}
	}
	//这个涉及到学籍管理的学年学期设置，没有学制，导致界面报错
	if(unitType == 3 && (schtype == ${unitTypeKg111!} || schtype == ${unitTypeKg112!} || schtype == ${unitTypeKg119!})){
		//addFieldError('schtype','单位类型为托管中小学，学校类别不能为幼儿园');
		showMsgError("单位类型为托管中小学，学校类别不能为幼儿园");
		return false;
	}
	return true;
}

function getUnionid(){
	var provinceSelect=document.getElementById('province');
	var citySelect=document.getElementById('city');
	var sectionSelect=document.getElementById('section');
	var countrycheck=document.getElementById('country');	
	var countryspan=document.getElementById('countryspan');
	var unionidinput=document.getElementById('unionid');	
	if(countryspan.style.display==''){//如果是新增乡镇级教育局则也需要生成unionid
		unionidinput.value='';
	}
	else if(sectionSelect.value!=''){
		unionidinput.value=sectionSelect.value;
	}
	else if(citySelect.value!=''){
		unionidinput.value=citySelect.value;
	}
	else if(provinceSelect.value!=''){
		unionidinput.value=provinceSelect.value;
	}
	else if(document.getElementById('unitclass')=='${unitclass_edu}'){
		showMsgError('请选择所在地行政区');
		return ;
	}
	return ;
}

</script>    
</@htmlmacro.moduleDiv>