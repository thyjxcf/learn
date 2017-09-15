<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<script>
function doAdd(){
	load("#showListDiv","${contextPath!}/office/teacherAttendance/teacherAttendance-placeEdit.action");
}

function doDelete(id){
	if(!showConfirm('您确认要删除吗？')){
		return;
	}
	$.ajax({
		type: "POST",
		url: "${contextPath!}/office/teacherAttendance/teacherAttendance-placeDelete.action",
		data: $.param( {"id":id},true),
		success: function(data){
			if(!data.operateSuccess){
					showMsgError(data.errorMessage);
					return;
				}else{
					showMsgSuccess("删除成功", "提示", function(){
						load("#showListDiv","${contextPath!}/office/teacherAttendance/teacherAttendance-place.action");
					});
					return;
				}
		},
		dataType: "json",
		error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
	});
}

</script>
<div class="pub-two-tab-inner">
	<ul class="checking-place fn-clear mt-30">
		<#if placeList?exists && (placeList?size>0)>
		<#list placeList as item>
		<li>
			<div class="checking-place-show">
				<p>考勤地点：${item.name!}</p>
				<p>地址：${item.address!}</p>
				<p>考勤范围：${item.range!}米</p>
				<p>经纬度：${item.longitude!},${item.latitude!}</p>
			</div>
			<p class="checking-place-opt">
				<a href="#" class="abtn-blue-big js-change" id="${item.id!}" onclick="doEdit('${item.id!}','${item.name!}','${item.mapName!}','${item.address!}','${item.range!}','${item.longitude!}','${item.latitude!}');">修改</a>
				<a href="#" class="abtn-blue-big js-del" onclick="doDelete('${item.id!}')">删除</a>
			</p>
		</li>
		</#list>
		</#if>
		<li>
			<a href="#" class="checking-place-add js-add">新增考勤地点</a>
		</li>
	</ul>
</div>
<input id="mainformIndex" type="hidden" value=""/>
<div class="popUp-layer" id="classLayer" style="display:none;"></div>	
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script type="text/javascript">
var index = 0;
$(function(){
	//增加
	$('.js-add').click(function(){
		var html = editPlace("","","","","","","");
		$(this).parent('li').before(html);
	});
});

function doEdit(id,name,mapName,address,range,longitude,latitude){
	var html = editPlace(id,name,mapName,address,range,longitude,latitude);
	$("#"+id).parents('li').hide().before(html);
}

function doCancel(){
	goBack();
}

function editPlace(id,name,mapName,address,range,longitude,latitude){
	index = index +1;
 	var lonlat = "";
 	if(!isBlank(longitude) && !isBlank(latitude)){
 		lonlat = longitude+","+latitude;
 	}
 	
	var htmlStr = '<form id="mainform_'+index+'">';
		htmlStr += '<input name="id" class="id" type="hidden" value="'+id+'"/>';
		htmlStr += '<input name="place.mapName" name="mapName" class="mapName" type="hidden" value="'+mapName+'"/>';
		htmlStr += '<input name="place.latitude" name="latitude" class="latitude" type="hidden" value="'+latitude+'"/>';
		htmlStr += '<input name="place.longitude" name="longitude" class="longitude" type="hidden" value="'+longitude+'"/>';
		 htmlStr += '<li>';
		 htmlStr += 		'<div class="checking-place-form">';
		 htmlStr += 				'<div class="map fn-clear">';
		 htmlStr += 						'<a href="#" class="abtn-blue" onclick="selMap('+index+')">定位</a>';
		 htmlStr += 						'<div class="txt-outer fn-clear">';
		 htmlStr += 							'<label for="t1">考勤地点：</label>';
		 htmlStr += 							'<p class="txt">';
		 htmlStr += 								'<input type="text" name="place.name" class="name" maxlength="50" value="'+name+'" id="t1" />';
		 htmlStr += 							'</p>';
		 htmlStr += 						'</div>';
		 htmlStr += 				'</div>';
		 htmlStr += 				'<div class="txt-outer fn-clear">';
		 htmlStr += 					'<label for="t1">地址：</label>';
		 htmlStr += 					'<p class="txt">';
		 htmlStr += 						'<input type="text" name="place.address" class="address"  maxlength="100" value="'+address+'" id="t1" />';
		 htmlStr += 					'</p>';
		 htmlStr += 				'</div>';
		 htmlStr += 				'<div class="txt-outer fn-clear">';
		 htmlStr += 					'<label for="t1">考勤范围：</label>';
		 htmlStr += 					'<p class="txt">';
		 htmlStr += 						'<input type="text" name="place.range" class="range" maxlength="4" value="'+range+'" id="t1" />';
		 htmlStr += 					'</p>';
		 htmlStr += 				'</div>';
	 	 htmlStr += 				'<div class="txt-outer fn-clear">';
		 htmlStr += 					'<label for="t1">经纬度：</label>';
		 htmlStr += 					'<p class="txt">';
		 htmlStr += 						'<input type="text" name="" class="lonlat" readonly value="'+lonlat+'" id="t1" />';
		 htmlStr += 					'</p>';
		 htmlStr += 				'</div>';
		 htmlStr += 		'</div>';
		 htmlStr += 		'<p class="checking-place-opt">';
		 htmlStr += 			'<a href="#" class="abtn-blue-big js-submit" onclick="doSave('+index+')">确定</a>';
		 htmlStr += 			'<a href="#" class="abtn-blue-big js-del ml-5" onclick="doCancel()">取消</a>';
		 htmlStr += 		'</p>';	
		 htmlStr +='</li>';
	htmlStr +='</form>';
		
	return htmlStr;
}


function doSave(index){
	
	
	var name = trim($('#mainform_'+index+' .name').val());
	var mapName = trim($('#mainform_'+index+' .mapName').val());
	var address = trim($('#mainform_'+index+' .address').val());
	var range = trim($('#mainform_'+index+' .range').val());
	var latitude = trim($('#mainform_'+index+' .latitude').val());
	var longitude = trim($('#mainform_'+index+' .longitude').val());
	if(isBlank(latitude) || isBlank(longitude)){
		showMsgError("请先用定位选择地点。");
		return;
	}
	
	//地图点击选择mapName有可能为空
	if(isBlank(name)){
		showMsgError("考勤地点不能为空");
		return;
	}else if(getStringLen(name)>50){
		showMsgError("考勤地点长度不能超过50个字符（一个汉字占两个字符）");
		return;
	}
	
	if(isBlank(mapName)){
		$("#mapName").val(name);
	}
	
	if(isBlank(address)){
		showMsgError("地址不能为空");
		return;
	}else if(getStringLen(address)>100){
		showMsgError("地址长度不能超过100个字符（一个汉字占两个字符）");
		return;
	}
	
	if(isBlank(range)){
		showMsgError("考勤范围不能为空");
		return;
	}else if(!validateInteger(range) || getStringLen(range)>4){
		showMsgError("考勤范围只能输入小于10000的正整数");
		return;
	}
	
	
	var options = {
       url:'${request.contextPath}/office/teacherAttendance/teacherAttendance-placeSave.action', 
       success : showReply,//需事先showReply方法 
       dataType : 'json',
       clearForm : false,
       resetForm : false,
       type : 'post',
       timeout : 30000 
    };
	
    $('#mainform_'+index).ajaxSubmit(options);
}
function showReply(data){
	if(!data.operateSuccess){
		   if(data.errorMessage!=null&&data.errorMessage!=""){
			   showMsgError(data.errorMessage);
			   return;
		   }else if(data.fieldErrorMap!=null){
			  $.each(data.fieldErrorMap,function(key,value){
				   addFieldError(key,value+"");
			  });
		   }else{
		   	   showMsgError(data.promptMessage);
			   return;
		   }
	}else{
	   		showMsgSuccess("保存成功！","",function(){
			   goBack();
			});
			return;
	}
}

function selMap(index){
	//定位
	$("#mainformIndex").val(index);
	
	var name = trim($('#mainform_'+index+' .name').val())
	var mapName = trim($('#mainform_'+index+' .mapName').val());
	var address = trim($('#mainform_'+index+' .address').val());
	var latitude = trim($('#mainform_'+index+' .latitude').val());
	var longitude = trim($('#mainform_'+index+' .longitude').val());
	
	var parm = "?place.mapName="+mapName+"&place.address="+address+"&place.latitude="+latitude+"&place.longitude="+longitude;
	openDiv("#classLayer", "#classLayer .close,#classLayer .submit,#classLayer .reset", "${contextPath!}/office/teacherAttendance/teacherAttendance-placeEditMap.action"+parm, null, "700px", "500px");
}

function doSetMapData(name,address,lat,lng){
	if(isBlank(name) || 'undefined' == name){
		name = '';
	}
	var index = $("#mainformIndex").val();
	
	$('#mainform_'+index+' .name').val(name);
	$('#mainform_'+index+' .mapName').val(name);
	$('#mainform_'+index+' .address').val(address);
	$('#mainform_'+index+' .latitude').val(lat);
	$('#mainform_'+index+' .longitude').val(lng);
	$('#mainform_'+index+' .lonlat').val(lng+","+lat);
}

//正整数  校验通过返回true
function validateInteger(num){
	var pattern = /^([1-9]|[1-9][\d]+)$/;
	return pattern.test(num);
}

/**
 * 计算字符数(一个汉子占两个字符)sss
 * @param Str
 * @returns
 */
function  getStringLen(Str){  
	var i, len, code;
	if (Str == null || Str == "")
		return 0;
	len = Str.length;
	for (i = 0; i < Str.length; i++) {
		code = Str.charCodeAt(i);
		if (code > 255) {
			len++;
		}
	}
	return len;   
}

function goBack(){
	load("#showListDiv","${contextPath!}/office/teacherAttendance/teacherAttendance-place.action");
}
</script>
</@htmlmacro.moduleDiv>