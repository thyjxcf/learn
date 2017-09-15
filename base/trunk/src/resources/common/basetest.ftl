<#import "/common/commonmacro.ftl" as commonmacro>
<#import "/common/eisu/eisuCommonmacro.ftl" as eisuCommonmacro>
<#import "/common/flash.ftl" as flash>
<#import "/common/chartstructure.ftl" as chartstructure>
<script language="javascript">
function testPreset(){
	//alert("preset");
	//load("#classIdDiv","${request.contextPath}/common/popDiv.action?tree=true&idObjectId=classId&nameObjectId=className&url="+url+"&popup=false&useCheckbox=false&style=width:100px");


<div class="query-tt b ml-10 mt-5">班级：</div>
    <input type="hidden" name="classId" id="classId" value="" />
            <div id="classIdDiv" class="fn-left"></div>
    return true;
}

function getDynamicParam(){
	return "unitId=ff8080812e945a95012e99b72e9200c3"
}

function test(){
	//alert(document.getElementById("teacherName2").value);
	//alert("callback");
}  

function testPresetMoreLines(){
	return true;
}

var lineSign;
var totalLineSign = 13;
var isAdd = false;
function editMcode(event){
	var sign = event.data.s; 
	
	isAdd = false;
	lineSign = sign;
	
	$('#thisIds_20').val($('#thisIds_'+sign).val());
	$('#contents_20').val($('#contents_'+sign).val());
	
	$('#mcodeDiv').jWindowOpen({
		modal: false,
		center: true,
		close:"#mcodeDiv .close1"
	});
	return true;
}

function okMcode(){
	
	//表示新增
	if(isAdd){
		var tr = $('#cloneTr').clone(false);
		tr.show();
		tr.attr('id','');		
		tr.find('#rowNumTd').html('行'+lineSign+'：');
		tr.find('#thisIds_00').attr('id','thisIds_'+lineSign);
		tr.find('#contents_00').attr('id','contents_'+lineSign);
		
		tr.find('.my_mcode_edit').click({s:lineSign},editMcode);		
		tr.find('.my_mcode_edit').addClass('my_mcode_clear');
		
		$("#mcodeTable").append(tr);
	}
	
	$('#thisIds_'+lineSign).val($('#thisIds_20').val());
	$('#contents_'+lineSign).val($('#contents_20').val());
	$('#mcodeDiv').jWindowClose();
}

function addMcode(){	
	isAdd = true;
	lineSign = totalLineSign;
	totalLineSign = totalLineSign + 1;
	
	$('#thisIds_20').val('');
	$('#contents_20').val('');
	
	$('#mcodeDiv').jWindowOpen({
		modal: false,
		center: true,
		close:"#mcodeDiv .close1"
	});
	
	
}

$(document).ready(function(){
	$('.my_mcode_edit').each(function(){
		$(this).click({s:$(this).attr('val')},editMcode);
	});
});


<#-- ============================漂浮式============================-->
var lineSignFloat;
var totalLineSignFloat = 13;

function editMcodeFloat(event){
	var sign = event.data.s; 

	lineSignFloat = sign;
	
	$('#thisIds_float_20').val($('#thisIds_float_'+sign).val());
	$('#contents_float_20').val($('#contents_float_'+sign).val());

	//ajdustStyleFloat($(event.target));
	
	//return false;
}

//调整样式
function ajdustStyleFloat(event){	
	var obj = event.data.obj; 

	obj.siblings('.my_mcode_clear_float').click();
	
	var floatDiv = $('.my_mcode_float').find('.select_box02');
	
	floatDiv.prev().show();
	obj.after(floatDiv);
	obj.hide();

	floatDiv.trigger('click',false);

	return false;
}

function okMcodeFloat(){
	$('#thisIds_float_'+lineSignFloat).val($('#thisIds_float_20').val());
	$('#contents_float_'+lineSignFloat).val($('#contents_float_20').val());
}

function addMcodeFloat(){	
	lineSignFloat = totalLineSignFloat;
	totalLineSignFloat = totalLineSignFloat + 1;
	
	var tr = $('#cloneTrFloat').clone(false);
	tr.show();
	tr.attr('id','');		
	tr.find('#rowNumTdFloat').html('行'+lineSignFloat+'：');
	tr.find('#rowNumText').val(lineSignFloat);
	tr.find('#thisIds_float_00').attr('id','thisIds_float_'+lineSignFloat);
	tr.find('#contents_float_00').attr('id','contents_float_'+lineSignFloat);
	
	tr.find('.my_mcode_edit_float').click({obj:tr.find('.my_mcode_edit_float'),s:lineSignFloat},ajdustStyleFloat);		
	tr.find('.my_mcode_clear_float').click({s:lineSignFloat},editMcodeFloat);
	
	$("#mcodeTableFloat").append(tr);
	
	
}

$(document).ready(function(){
	$('.my_mcode_edit_float').each(function(){
		//ajdustStyleFloat($(this));
		$(this).click({obj:$(this),s:$(this).attr('val')},ajdustStyleFloat);
	});
	$('.my_mcode_clear_float').each(function(){
		$(this).click({s:$(this).attr('val')},editMcodeFloat);
	});
});
</script>
<#-- 
<div class="document-reader">
	<div class="fn-clear">
        <div class="doc-tit">
        	<p class="name"><img src="${request.contextPath}/static/images/icon/word.png" alt="word">5月实习小结</p>
        	<p class="info">高三（1）班<span class="mx-15">周小晨</span>上传于2013 02/01 17:20</p>
        </div>
        <p class="doc-up"><a href="#" class="abtn-blue-big">上传实习心得</a></p>
    </div>
    <div class="reader-wrap">
    	<div class="reader-container"><@flash.documentViewer url="${request.contextPath}/static/flash/test.swf" width="958" height="475" /> </div>
        <div class="reader-about">
        	<p class="tit">其他文章</p>
            <ul>
            	<li>
            		<img src="${request.contextPath}/static/images/icon/word.png" alt="word">
            		<p>实习小结20140212</p>
            		<p class="info">高三（1）班  周小晨</p>
            	</li>            	
            </ul>
        </div>
    </div>
</div>

<br><br>

<div class="app-info">
	<p class="tit"><span><a href="javascript:void(0);" onclick='addMcodeFloat();'>新增</a></span>多行编辑漂浮式</p>
	<div class="app-info-wrap" id="mcodeTableFloat">   
	<ul class="app-info-part fn-clear" id="cloneTrFloat" style="display:none" >
	   <li><span class="c-red mr-5">*</span><span id="rowNumTdFloat">行00：</span>代码：<input type="text" class="input-txt" id="thisIds_float_00"></li>
  	   <li class="my_mcode_float">
  	   	  <span class="c-red mr-5">*</span>内容：  
	  	   <input type="text" value="" id="contents_float_00" size="40" class="input-txt my_mcode_edit_float">
	  	   <a class="my_mcode_clear_float" val="00" href="javascript:void(0);" style="display:none">修改</a>
	   </li>
	   <li>
	   		<span class="c-red mr-5">*</span>行号： 
	  	   <input type="text" id="rowNumText" value="00" class="input-txt">
  	   </li>
  	</ul>
  		
  	<ul class="app-info-part fn-clear">
  		<li><span class="c-red mr-5">*</span><span>行11：</span>代码：<input type="text" class="input-txt" name="thisIds_float_11" id="thisIds_float_11" value="001,002,003"> </li>
  	   <li class="my_mcode_float">
  	   		<span class="c-red mr-5">*</span>内容：  
	  	   <input type="text" name="contents_float_11" id="contents_float_11"  val="11" class="input-txt my_mcode_edit_float" value="校长,党支部书记,副校长" size="40"> 
	  	   <a class="my_mcode_clear_float" val="11" href="javascript:void(0);" style="display:none">修改</a>
	   </li>
	   <li>
	  	   <span class="c-red mr-5">*</span>行号： 
	  	   <input type="text" value="11" class="input-txt">
  	   </li>
  	</ul>
  	<ul class="app-info-part fn-clear">
  		<li><span class="c-red mr-5">*</span><span>行12：</span>代码：<input type="text" class="input-txt" name="thisIds_float_12" id="thisIds_float_12" value="004,005"> </li>
  	   <li class="my_mcode_float">
  	   		<span class="c-red mr-5">*</span>内容：  
	  	   <input type="text" name="contents_float_12" id="contents_float_12"  val="12" class="input-txt my_mcode_edit_float" value="教务主任,教务员" size="40">
	  	   <a class="my_mcode_clear_float" val="12" href="javascript:void(0);" style="display:none">修改</a>
	   </li>
	   <li>
	   		<span class="c-red mr-5">*</span>行号： 
	  	   <input type="text" value="12" class="input-txt">
  	   </li>
  	</ul>
</div>
</div>	
-->

<div id="EditContainer">

<#-- 
<table border="0" cellspacing="1" cellpadding="1" id="mcodeTableFloat">  
	<tr><td colspan="4"><font color='red'>多行编辑漂浮式 </font>   <a href="javascript:void(0);" onclick='addMcodeFloat();'>新增</a></td></tr>
	<tr id="cloneTrFloat" style="display:none" style="height:40px;">
  	   <td id="rowNumTdFloat">行00：</td>
  	   <td>
  	   		<input type="text" value="" id="thisIds_float_00">
  	   </td>
  	   <td class="my_mcode_float">  
	  	   <input type="text" value="" id="contents_float_00" size="40" class="my_mcode_edit_float">
	  	   <a class="my_mcode_clear_float" val="00" href="javascript:void(0);" style="display:none">修改</a>
	   </td>
	   <td> 
	  	   <input type="text" id="rowNumText" value="00">
  	   </td>
  	</tr>
  		
  	<tr style="height:40px;">
  	   <td >行11：</td>
  	   <td>
  	   		<input type="text" name="thisIds_float_11" id="thisIds_float_11" value="001,002,003">  
  	   </td>
  	   <td class="my_mcode_float">
	  	   <input type="text" name="contents_float_11" id="contents_float_11"  val="11" class="my_mcode_edit_float" value="校长,党支部书记,副校长" size="40"> 
	  	   <a class="my_mcode_clear_float" val="11" href="javascript:void(0);" style="display:none">修改</a>
	   </td>
	   <td>
	  	   <input type="text" value="11">
  	   </td>
  	</tr>
  	<tr style="height:40px;">
  	   <td >行12：</td>
  	   <td>
  	   		<input type="text" name="thisIds_float_12" id="thisIds_float_12" value="004,005">
  	   </td>
  	   <td class="my_mcode_float">
	  	   <input type="text" name="contents_float_12" id="contents_float_12"  val="12" class="my_mcode_edit_float" value="教务主任,教务员" size="40">
	  	   <a class="my_mcode_clear_float" val="12" href="javascript:void(0);" style="display:none">修改</a>
	   </td>
	   <td>
	  	   <input type="text" value="12">
  	   </td>
  	</tr>
</table>

<div class="my_mcode_float" style="display:none;">	
	<@commonmacro.selectMoreMcodeDetail idObjectId="thisIds_float_20" nameObjectId="contents_float_20" otherParam="groupId=DM-XXZW" callback="okMcodeFloat" switchSelector=".my_mcode_clear_float">
  	   <input type="hidden" name="thisIds_float_20" id="thisIds_float_20" value="">  
  	   <input type="text" name="contents_float_20" id="contents_float_20" value="" class="input-txt select_current02" size="40">
  	 </@commonmacro.selectMoreMcodeDetail>
</div>
<br>
-->

<table border="0" cellspacing="1" cellpadding="1" id="mcodeTable">  
	<tr><td colspan="2"><font color='red'>多行编辑弹出式</font>    <a href="javascript:void(0);" class="my_mcode_clear" onclick='addMcode();'>新增</a></td></tr>
	<tr id="cloneTr" style="display:none">
  	   <td id="rowNumTd">行00：</td>
  	   <td>
	  	   <input type="text" value="" id="thisIds_00">  
	  	   <input type="text" value="" id="contents_00" size="40"> 
	  	   <a class="my_mcode_edit" val="00" href="javascript:void(0);">修改</a>
  	   </td>
  	</tr>
  		
  	<tr>
  	   <td >行11：</td>
  	   <td>
	  	   <input type="text" name="thisIds_11" id="thisIds_11" value="001,002,003">  
	  	   <input type="text" name="contents_11" id="contents_11" value="校长,党支部书记,副校长" size="40"> 
	  	   <a class="my_mcode_edit my_mcode_clear" val="11" href="javascript:void(0);">修改</a>
  	   </td>
  	</tr>
  	<tr>
  	   <td >行12：</td>
  	   <td>
	  	   <input type="text" name="thisIds_12" id="thisIds_12" value="004,005">  
	  	   <input type="text" name="contents_12" id="contents_12" value="教务主任,教务员" size="40"> 
	  	   <a class="my_mcode_edit my_mcode_clear" val="12" href="javascript:void(0);">修改</a>
  	   </td>
  	</tr>
</table>
<div id="mcodeDiv" class="popUp-layer" style="display:none;">
	<p class="tt"><a href="javascript:void(0);" class="close close1">关闭</a><span>修改</span></p>
    <div class="wrap">
        <p class="content">
        	<@commonmacro.selectMoreMcodeDetail idObjectId="thisIds_20" nameObjectId="contents_20" otherParam="groupId=DM-XXZW" preset="testPresetMoreLines" switchSelector=".my_mcode_clear">
		  	   <input type="hidden" name="thisIds_20" id="thisIds_20" value="">  
		  	   <input type="text" name="contents_20" id="contents_20" value="" class="select_current02" size="40">
		  	   <br><br>
		  	 </@commonmacro.selectMoreMcodeDetail>	
        </p>
        <p class="t-center pb-20">
            <a class="abtn-blue submit" href="javascript:void(0);" onclick="okMcode();">确定</a>
        </p>
    </div>	
</div>
<br>
<#---->
<#--
<table border="0" cellspacing="1" cellpadding="1">  	
<tr>  	 
  	   <td >行政区划：</td>
  	   <td>
  	   <@commonmacro.selectTree idObjectId="regionCode" nameObjectId="regionName" treeUrl=request.contextPath+"/common/xtree/regionTree.action">
  	   <input type="hidden" name="regionCode" id="regionCode" value="" class="select_current02">
  	   <input type="text" name="regionName" id="regionName" value="" class="select_current02">
  	   </@commonmacro.selectTree>
  		</td>	
  		
  	</tr>  	
</table>
<br>

<table border="0" cellspacing="1" cellpadding="1">  	
  	<tr>  	 
  	   <td >单选专业：</td>
  	   <td>
  	   <@commonmacro.selectTree idObjectId="specialtyId" nameObjectId="specialtyName" treeUrl=request.contextPath+"/common/xtree/studentTree.action?allLinkOpen=false" callback="test" preset="testPreset">
  	   <input type="hidden" name="specialtyId" id="specialtyId" value="" class="select_current02">
  	   <input type="text" name="specialtyName" id="specialtyName" value="" class="select_current02">
  	   </@commonmacro.selectTree>
  		</td>	
  		
  	</tr>

</table>
 
<table border="0" cellspacing="1" cellpadding="1">  	
  	<tr>  	 
  	   <td >班级树多选：</td>
  	   <td>
  	   <@commonmacro.selectMoreTree idObjectId="classId" nameObjectId="className" treeUrl=request.contextPath+"/common/xtree/studentTree.action?allLinkOpen=false">
  	   <input type="hidden" name="classId" id="classId" value="" class="select_current02">
  	   <input type="text" name="className" id="className" value="" class="select_current02">
  	   </@commonmacro.selectMoreTree>
  		</td>	
  	</tr>
</table>

<table border="0" cellspacing="1" cellpadding="1">
  	<tr>  	 
  	   <td >不带树多选某班内学生：</td>
  	   <td>
  	   <@commonmacro.selectMoreStudent idObjectId="studentIds2" nameObjectId="studentNames2" otherParam="groupId=FF808081422B8C7901422BE326C0035D">
  	   <input type="hidden" name="studentIds2" id="studentIds2" value="">  
  	   <textarea name="studentNames2" id="studentNames2" cols="60" rows="4" class="select_current02" readonly style="height:80px"></textarea>
  	   </@commonmacro.selectMoreStudent>
  	   </td>
  	</tr>
  	
  	<tr>  	 
  	   <td >不带树单选某班内学生：</td>
  	   <td>
  	   <@commonmacro.selectOneStudent idObjectId="studentId2" nameObjectId="studentName2" otherParam="groupId=FF808081422B8C7901422BE326C0035D" callback="test">
  	   <input type="hidden" name="studentId2" id="studentId2" value=""> 
  	   <input type="text" name="studentName2" id="studentName2" value="" class="select_current02" size="20">
  	   </@commonmacro.selectOneStudent>
  	   </td>
  	</tr>
  	<tr>  	 
  	   <td >场地：</td>
  	   <td>
  	   <@commonmacro.selectOneTeachPlace idObjectId="teachPlaceId" nameObjectId="teachPlaceIdName" preset="testPreset" callback="test">
  	   <input type="hidden" name="teachPlaceId" id="teachPlaceId" value=""> 
  	   <input type="text" name="teachPlaceName" id="teachPlaceIdName" value="" class="select_current02" size="20">
  	   </@commonmacro.selectOneTeachPlace>
  	   </td>
  	</tr>
  	
  	<tr>  	 
  	   <td >场地单选树：</td>
  	   <td>
  	    <@commonmacro.selectTree idObjectId="teachPlaceId2" nameObjectId="teachPlaceIdName2" treeUrl=request.contextPath+"/common/xtree/teachPlaceTree.action?allLinkOpen=false">
  	   <input type="hidden" name="teachPlaceId2" id="teachPlaceId2" value=""> 
  	   <input type="text" name="teachPlaceName2" id="teachPlaceIdName2" value="" class="select_current02" size="20">
  	   </@commonmacro.selectTree>
  	   </td>
  	</tr>
  	<tr>  	 
  	   <td >场地多选树：</td>
  	   <td>
  	    <@commonmacro.selectMoreTree idObjectId="teachPlaceId3" nameObjectId="teachPlaceIdName3" treeUrl=request.contextPath+"/common/xtree/teachPlaceTree.action?allLinkOpen=false">
  	   <input type="hidden" name="teachPlaceId3" id="teachPlaceId3" value=""> 
  	   <input type="text" name="teachPlaceName3" id="teachPlaceIdName3" value="" class="select_current02" size="20">
  	   </@commonmacro.selectMoreTree>
  	   </td>
  	</tr>
</table>

<br>
<table border="0" cellspacing="1" cellpadding="1"> 
  	<tr>  	 
  	   <td >单选部门：</td>
  	   <td>
  	   <@commonmacro.selectTree idObjectId="deptId2" nameObjectId="deptName2"  preset="testPreset"
  	   	treeUrl=request.contextPath+"/common/xtree/deptTree.action" width="1000" >
  	   <input type="hidden" name="deptId2" id="deptId2" value="" class="select_current02"> 
  	   <input type="text" name="deptName2" id="deptName2" value="" class="select_current02" size="20">
  	   </@commonmacro.selectTree>
  	   </td>
  	</tr>  	
  	
  	<tr>  	 
  	   <td >带部门树单选教师：</td>
  	   <td>
  	   <@commonmacro.selectTree idObjectId="teacherId4" nameObjectId="teacherName4"  preset="testPreset"
  	   	treeUrl=request.contextPath+"/common/xtree/teacherTree.action?allLinkOpen=false" >
  	   <input type="hidden" name="teacherId4" id="teacherId4" class="select_current02" value=""> 
  	   <input type="text" name="teacherName4" id="teacherName4" value="" class="select_current02" size="20">
  	   </@commonmacro.selectTree>
  	   </td>
  	</tr>
  
   	<tr>  	 
  	   <td >带部门树多选教师：</td>
  	   <td>
  	   <@commonmacro.selectMoreTree idObjectId="teacherId3" nameObjectId="teacherName3"  preset="testPreset"
  	   	treeUrl=request.contextPath+"/common/xtree/teacherTree.action?allLinkOpen=false" >
  	   <input type="hidden" name="teacherId3" id="teacherId3" class="select_current02" value=""> 
  	   <input type="text" name="teacherName3" id="teacherName3" value="" class="select_current02" size="20">
  	   </@commonmacro.selectMoreTree>
  	   </td>
  	</tr>
   	
  	<tr>  	 
  	   <td >不带树多选某单位内教师：</td>
  	   <td>
  	   <@commonmacro.selectMoreTeacher idObjectId="teacherIds2" nameObjectId="teacherNames2" otherParam="">
  	   <input type="hidden" name="teacherIds2" id="teacherIds2" value="">  
  	   <input type="text" name="teacherNames2" id="teacherNames2" value="" class="select_current02" size="20">
  	   </@commonmacro.selectMoreTeacher>
  	   </td>
  	</tr>
  	<tr>  	 
  	   <td >不带树单选某单位内教师：</td>
  	   <td>
  	   <@commonmacro.selectOneTeacher idObjectId="teacherId2" nameObjectId="teacherName2" preset="testPreset" dynamicParam="getDynamicParam" callback="test">
  	   <input type="hidden" name="teacherId2" id="teacherId2" value=""> 
  	   <input type="text" name="teacherName2" id="teacherName2" value="" class="select_current02" size="20">
  	   </@commonmacro.selectOneTeacher>
  	   </td>
  	</tr>
  	
</table>

<br>
<table border="0" cellspacing="1" cellpadding="1">  
  	<tr>  	 
  	   <td >多选微代码：</td>
  	   <td>
  	   <@commonmacro.selectMoreMcodeDetail idObjectId="thisIds" nameObjectId="contents" otherParam="groupId=DM-XXZW" callback="test" preset="testPreset">
  	   <input type="hidden" name="thisIds" id="thisIds" value="">  
  	   <input type="text" name="contents" id="contents" value="" class="select_current02" size="20">
  	   </@commonmacro.selectMoreMcodeDetail>
  	   </td>
  	</tr>
  	<tr>  	 
  	   <td >单选微代码：</td>
  	   <td>
  	   <@commonmacro.selectOneMcodeDetail idObjectId="thisId1" nameObjectId="content1" otherParam="groupId=DM-XXZW" callback="test" preset="testPreset">
  	   <input type="hidden" name="thisId1" id="thisId1" value="">  
  	   <input type="text" name="content1" id="content1" value="" class="select_current02" size="20">
  	   </@commonmacro.selectOneMcodeDetail>
  	   </td>
  	</tr>
</table> 
<#---->

<#--  ===========================old======================
<br>
<table border="0" cellspacing="1" cellpadding="1">  
	 <tr>  	 
  	   <td >带班级树有权限多选学生：</td>
  	   <td>
  	   <@eisuCommonmacro.selectMoreStudentTreeByPopedom idObjectId="studentIds" nameObjectId="studentNames" otherParam="needAllPopedom=true">
  	   <input type="text" name="studentIds" id="studentIds" value="">  
  	   <input type="text" name="studentNames" id="studentNames" value="" class="select_current02" size="20">
  	   </@eisuCommonmacro.selectMoreStudentTreeByPopedom>
  	   </td>
  	</tr>
  	<tr>  	 
  	   <td >带班级树有权限单选学生：</td>
  	   <td>
  	   <@eisuCommonmacro.selectOneStudentTreeByPopedom idObjectId="studentId" nameObjectId="studentName" otherParam="needAllPopedom=true">
  	   <input type="text" name="studentId" id="studentId" value=""> 
  	   <input type="text" name="studentName" id="studentName" value="" class="select_current02" size="20">
  	   </@eisuCommonmacro.selectOneStudentTreeByPopedom>
  	   </td>
  	</tr>
  	
  	
  	<tr>  	 
  	   <td >带班级树多选学生：</td>
  	   <td>
  	   <@commonmacro.selectMoreStudentTree idObjectId="studentIds1" nameObjectId="studentNames1">
  	   <input type="text" name="studentIds1" id="studentIds1" value="">  
  	   <input type="text" name="studentNames1" id="studentNames1" value="" class="select_current02" size="20">
  	   </@commonmacro.selectMoreStudentTree>
  	   </td>
  	</tr>
  	
  	<tr>  	 
  	   <td >带班级树单选学生：</td>
  	   <td>
  	   <@commonmacro.selectOneStudentTree idObjectId="studentId1" nameObjectId="studentName1" callback="test">
  	   <input type="text" name="studentId1" id="studentId1" value=""> 
  	   <input type="text" name="studentName1" id="studentName1" value="" class="select_current02" size="20">
  	   </@commonmacro.selectOneStudentTree>
  	   </td>
  	</tr>
  	
  	<tr>  	 
  	   <td >带班级树单选学生(学籍号)：</td>
  	   <td>
  	   <@commonmacro.selectOneStudentTreeUnitiveCode idObjectId="studentId3" nameObjectId="studentName3" callback="test">
  	   <input type="text" name="studentId3" id="studentId3" value=""> 
  	   <input type="text" name="studentName3" id="studentName3" value="" class="select_current02" size="20">
  	   </@commonmacro.selectOneStudentTreeUnitiveCode>
  	   </td>
  	</tr>
  	
  	<tr>  	 
  	   <td >带部门树多选教师：</td>
  	   <td>
  	   <@commonmacro.selectMoreTeacherTree idObjectId="teacherIds1" nameObjectId="teacherNames1">
  	   <input type="text" name="teacherIds1" id="teacherIds1" value="">  
  	   <input type="text" name="teacherNames1" id="teacherNames1" value="" class="select_current02" size="20">
  	   </@commonmacro.selectMoreTeacherTree>
  	   </td>
  	</tr>
  	
  	<tr>  	 
  	   <td >带部门树单选教师：</td>
  	   <td>
  	   <@commonmacro.selectOneTeacherTree idObjectId="teacherId1" nameObjectId="teacherName1"  preset="testPreset">
  	   <input type="text" name="teacherId1" id="teacherId1" value=""> 
  	   <input type="text" name="teacherName1" id="teacherName1" value="" class="select_current02" size="20">
  	   </@commonmacro.selectOneTeacherTree>
  	   </td>
  	</tr>
  	</table> 
  	-->

</div> 
<div style="width:100%;height: 1300px;"> 

<#--柱状图普通-->
<@chartstructure.histogram loadingDivId="loadingDivId1" divClass="fn-left" divStyle="width: 50%;height: 25%;" jsonStringData=jsonStringChart[0] isNeedAverage=true isNeedMax=true isNeedMin=true />
<#--柱状图堆叠-->
<@chartstructure.histogram loadingDivId="loadingDivId2" divClass="fn-left" divStyle="width: 50%;height: 25%;" jsonStringData=jsonStringChart[5] isShowDataLabel=true/>

<#--折线图-->
<@chartstructure.histogram isLine=true loadingDivId="loadingDivId5" divClass="fn-left" divStyle="width: 50%;height: 25%;" jsonStringData=jsonStringChart[0] isNeedAverage=true isNeedMax=true isNeedMin=true />
<#--饼图-->
<@chartstructure.pieChart loadingDivId="loadingDivId3" divClass="fn-left" divStyle="width: 50%;height: 25%;" jsonStringData=jsonStringChart[1] units="k" radius="67%" center="['50%', '50%']" isShowLegend=false isShowDataLabel=true/>

<#--雷达图-->
<@chartstructure.radar loadingDivId="loadingDivId6" divClass="fn-left" divStyle="width: 50%;height: 25%;" jsonStringData=jsonStringChart[3] isShowLegend=false/>
<#--仪表盘-单一-->
<@chartstructure.gauge loadingDivId="loadingDivId7" divClass="fn-left" divStyle="width: 50%;height: 25%;" jsonStringData=jsonStringChart[4]/>

<#--散点图-->
<@chartstructure.scatter loadingDivId="loadingDivId4" divClass="fn-left" divStyle="width: 50%;height: 25%;" jsonStringData=jsonStringChart[2] isShowDataLabel=true dataDisplay="{c}" isShowLegend=true />

</div>
<script>
$(document).ready(function(){
	vselect();
})
</script>