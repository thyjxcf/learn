<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/<#if loginUser?exists>${loginUser.skin?default('default')}.css<#else>default.css</#if>"/>
<!-- 通讯录脚本 -->
<script type="text/javascript" src="${request.contextPath}/static/js/userTab.js"></script>
<div class="pop-tip-dialog_title"><h2>通讯录</h2></div>
    <p class="dt-opt">
	    <#if loginInfo.user.ownerType != 1 && loginInfo.user.ownerType != 3>
			查找对象：
			<input type="text" id="searchName" class="input-txt" style="width:180px;" />
			<input type="hidden" id="roleTyle" name="roleTyle" value=""/> 
			<span class="input-btn2" onclick="searchAddressBookUsers();"><button type="button">查询</button></span>
			<span class="font">支持中文姓名查找</span>
	    </#if>
        <span class="input-btn2 f-right mt-5 mr-10" onclick="document.getElementById('address').style.display='none';jQuery('select').show();"><button type="button">取消</button></span>
        <span id="addressOK" name="addressOK" class="input-btn2 f-right mr-10 mt-5"  onclick="jQuery('select').show();"><button type="button">确定</button></span>
    </p>
    <div class="address-box" id="addressCut">
        <ul class="tab-list">
		    <li class="current" id="selfUnitRadio" name="linkgroupType" onclick="javascript:changeUserListBack();onSelfUnit('${loginInfo.user.unitid}','${loginInfo.user.deptid!}');"><span>本单位</span></li>
		     
		    <#if loginInfo.unitClass=1>  
		        <#if loginInfo.user.ownerType != 1 && loginInfo.user.ownerType != 3 && showDirectSubUnit>
		            <li  onclick="javascript:setUnchecked();changeUserListBack();onDirectSubUnit();jQuery('#searchName').val('');"><span>直属单位</span></li>
					<li onclick="javascript:setUnchecked();changeUserListBack();onAllDownUnit();jQuery('#searchName').val('');"><span>下属单位</span></li>
		        </#if>
		   </#if>
		    	<#if loginInfo.unitType != 1>
		        	<#if loginInfo.user.ownerType != 1 && loginInfo.user.ownerType != 3 && showParentUnit>
		             <li  onclick="javascript:setUnchecked();changeUserListBack();onParentUnit();jQuery('#searchName').val('');"><span>直接上级单位</span></li>
		    		</#if>
		        	<#if loginInfo.user.ownerType != 1 && loginInfo.user.ownerType != 3 && showSameLevelUnit>
		              <li onclick="javascript:setUnchecked();changeUserListBack();onSameLevelUnit();jQuery('#searchName').val('');"><span>同级单位</span></li>
		    		</#if>
		    	</#if>
		    
		     <#if loginInfo.user.ownerType != 1 && loginInfo.user.ownerType != 3 && showTeacherDuty>
		             <li onclick="javascript:setUnchecked();changeUserListBack();onDutys();jQuery('#searchName').val('');"><span>职务分类组</span></li>
			</#if>     
			 <#if loginInfo.user.ownerType != 1 && loginInfo.user.ownerType != 3 && showTeacherSubject>
			       <li onclick="javascript:setUnchecked();changeUserListBack();onSubjects();jQuery('#searchName').val('');"><span>学科分类组</span></li>
			</#if>             
		</ul>
        <span>
           <ul id="SelectLevel2"  style="">
           <div>	
	          <#-- 
	            <#if (loginInfo.user.ownerType == 3 || loginInfo.user.ownerType == 1) && loginInfo.isOrderEtoh()>
	               <li><input id="allTeacherMenu" name="linkmanType" type="radio" onclick="javascript:getGroup('${loginInfo.user.unitid}');"/><label for="allTeacherMenu">教师</label></li>  
			<#elseif loginInfo.user.ownerType != 3 && loginInfo.user.ownerType != 1>
			-->
			<#if loginInfo.user.ownerType != 3 && loginInfo.user.ownerType != 1>
	               <input id="allTeacherMenu" name="linkmanType" type="radio" onclick="javascript:changeUserListBack();getGroup('${loginInfo.user.unitid}');" /><label for="allTeacherMenu">教师</label>
	            </#if>
	            
		<#--		                  	
	            <#if (loginInfo.isOrderEtoh() && (loginInfo.user.ownerType == 2 || loginInfo.isGradeLogin() || loginInfo.isTeachingTeacherLogin() || loginInfo.isCommonTeacherLogin()))>
	                <li><input id="parentMenu" name="linkmanType" type="radio" onclick="javascript:changeUserListBack();getClass('getParent');"/><label for="parentMenu">家长</label></li>
	            </#if>
	 -->
		<#--学校端登录-->
		<#if loginInfo.unitClass=2> 
	                <input id="studentMenu" name="linkmanType" type="radio" onclick="javascript:changeUserListBack();getClass('getStudent');" /><label for="studentMenu">学生</label>
			<input id="parentMenu" name="linkmanType" type="radio" onclick="javascript:changeUserListBack();getClass('getParent');"/><label for="parentMenu">家长</label>
               <input id="adviserMenu" name="linkmanType" type="radio" onclick="javascript:changeUserListFull();getAdviserTeacher();" /><label for="adviserMenu">班主任</label>
               <input id="teacherMenu" name="linkmanType" type="radio" onclick="javascript:changeUserListFull();getTeachingTeacher();" /><label for="teacherMenu">任课老师</label>
                <input id="gradeMenu" name="linkmanType" type="radio" onclick="javascript:changeUserListFull();getGradeHeader();"/><label for="gradeMenu">年级组长</label>
              </#if>  
                </div>
     		</ul>
    		<p id="changeGroupSelection" class="t-center pl-10 pr-10" style="padding-top:1px;padding-bottom:1px;width:570px; float:right;" >部门名称：<select class="input-selectBox" onchange="onChangeGroupSelection(this.value);" style="width:200px;"><option>--请选择部门--</option></select></p>
        </span>
        <div class="addlist-box-zu" style="width: 180px">
            <p class="current"  onclick="">所有部门</p>
            <p>教导处</p>
            <p>123</p>
        </div>
        <div class="addlist-box-table" style="width:495px;">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table5">
                <tr>
                    <th width="7%"><input type="checkbox" id="selectAlladdressId" /></th>
                    <th width="10%">姓名</th>
                    <th width="20%">单位</th>
                    <th width="10%">性别</th>
                    <th width="20%">联系电话</th>
                </tr>
            </table>
            <div class="list" id="addlist-boxlist" style="overflow:auto;">
                <!--[if lte ie 8]> <div style="+zoom:1"><![endif]-->
                <table width="100%" border="0" cellspacing="0" cellpadding="0" id="dataTable">
                    <tr>
                        <td width="7%"><input type="checkbox" name='addressIds' /></td>
                        <td width="10%">张燕</td>
                        <td width="20%">演示学校</td>
                        <td width="10%">女</td>
                        <td width="20%">未填写</td>
                    </tr>
                    <tr>
                        <td width="7%"><input type="checkbox"  name='addressIds'/></td>
                        <td width="10%">张燕</td>
                        <td width="20%">演示学校</td>
                        <td width="10%">女</td>
                        <td width="20%">未填写</td>
                    </tr>
                </table>
                <!--[if lte ie 8]></div><![endif]-->
            </div>
        </div>
        <div class="clr"></div>
        <div class="user-tips"><!--<span class="ri">每页 <select class="input-sel"><option>15条</option></select></span>--><span style="color:blue;">已选择用户</span><span>（提示：双击姓名可删除）</span></div>
    </div>
    <div class="user-list" style="overflow:auto;height: 40px;word-break:break-all; word-wrap:break-word;" onselectstart='return false'></div>
    <p>
		 <#-- 
		 <#if showUserGroup && ((loginInfo.familyLogin=false && loginInfo.studentLogin=false) || loginInfo.isOrderEtoh())>
	        <span class="input-btn2" onclick="document.getElementById('aadUserContent').style.display='block';$('select').hide();"><i>创建组</i></span>
	     </#if> 
	     -->
         &nbsp;&nbsp;&nbsp;<span class="input-btn2" onclick="onClear()"><button type="button">清空</button></span>
    </p>
    
      <#--创建用户组内容
  <div class="popUp-layer" id="aadUserContent" >
   <h2>添加用户组</h2>
     <div id="editMsgContentDivBodyMain"  style="color:#00000;">
       请输入组名： <textarea  style="width:360px; margin-left:10px;" class="input-area" name="groupName" id="groupNameInput"></textarea>
    </div><span id="groupNameInputError" style="color: red;"></span>
    <span id="divActionError" style="color: red;"></span>
    <p class="p-int">
         <span class="input-btn1" onclick="addUserGroup2(); "><i >保存</i></span>
         <span class="input-btn1 closeAdd" onclick="document.getElementById('aadUserContent').style.display='none';$('select').show();"><i>取消</i></span>
    </p>
  </div>
 -->
<style>
  .input-selectBox{
    border:1px solid #b2c7cd;
    width:auto;
    height:22px;
    padding:0 0 0 5px;
    vertical-align:middle;
  }
</style>
<script>
contextPath="${request.contextPath}";
jQuery(function(){
	jQuery("#selfUnitRadio").click();
	jQuery(".addlist-box-zu p").live('click', function() {
	    jQuery(this).addClass("current").siblings("p").removeClass("current");
	   jQuery("#selectAlladdressId").attr("checked",false);
	  });
	
	//全选返选
	jQuery("#selectAlladdressId").click(function() {
		//$('[name=addressIds]:checkbox').click();
		//showMsgWarn(this.checked);
		jQuery('[name=addressIds]:checkbox').attr("checked", this.checked);
		jQuery('[name=addressIds]:checkbox').click();
		jQuery('[name=addressIds]:checkbox').attr("checked", this.checked);
	});
	
	jQuery('[name=addressIds]:checkbox').click(function() {
		// 定义一个临时变量，避免重复使用同一个选择器选择页面中的元素，提升程序效率。
		var $tmp = jQuery('[name=addressIds]:checkbox');
		// 用filter方法筛选出选中的复选框。并直接给CheckedAll赋值。
		jQuery('#selectAlladdressId').attr('checked', $tmp.length == $tmp.filter(':checked').length);
	});
});
</script>