<script>
	var isSubmit = false;
	function saveExternalApp(){
		if (isSubmit){
			return ;
		}
		<#if systemDeploySchool =='nbzx'>
		//判断当前已经有的个数
		if($('#appListId .externalAppList').length >=3){
			showMsgError('最多支持添加3个应用！');
			return false;
		}
		</#if>
		
		if(!checkAllValidate("#appForm1")){
			return false;
		}
		
		var elem = document.getElementById("appPic");	
	   	if(elem.value !="" ){
		   	if(!checkPicFile(elem,1024)){
	   			return false;
	   		}
	   	}else{
	   		showMsgError('请上传图标！');
	   		return false;
	   	}
		
		isSubmit = true;
		var options = {
			   target :'#appForm1',
		       url:'${request.contextPath}/system/desktop/app/externalApp-save.action', 
		       dataType : 'json',
		       clearForm : false,
		       resetForm : false,
		       type : 'post',
		       success : showSuccess
		    };
		try{
			$('#appForm1').ajaxSubmit(options);
		}catch(e){
			showMsgError('添加失败！');
			isSubmit = false;	
		}
	}
	
	function showSuccess(data) {
	   if(data.operateSuccess){
			//closeExternalApp();
			//closeDiv('#deskAppListLayer');
			//addApp();
			load("#deskAppListLayer","${request.contextPath}/system/desktop/app/externalApp-set.action?appAll=true");
		}else{
			showMsgError(data.errorMessage);
		}
		isSubmit = false;
	}
	
	function saveAppShow(){	
		var array_id = new Array(); 
	    var a = 0;  
		$('#appListNo li').each(function(fn){
			var load=$(this).attr('data-load');
			if(load == 'yes'){
				array_id[a++] = $(this).attr('module-id');	
			}
		});
		a=0;
		var array_id2 = new Array(); 
		$('#appListOut li').each(function(fn){
			var load=$(this).attr('data-load');
			if(load == 'yes'){
				array_id2[a++] = $(this).attr('app-id');	
			}
		});
		getJSON("${request.contextPath}/system/desktop/app/externalApp-show.action",{"moduleIds":array_id.join(","),"appIds":array_id2.join(",")},function(data){
			if(data && data !=''){
				showMsgError(data,'提示', function(){
					closeExternalApp();
				});
			} else {
				closeExternalApp();
			}
		});
	}
	
	function closeExternalApp(){
		closeDiv("#deskAppListLayer",refreshList);
	}
	
	function refreshList(){
		load('#externalApp','${request.contextPath}/system/desktop/app/externalApp.action?appAll=true');
	}
	
	function changePic(){
	  var picVal = $('#appPic').val();
	  var ind = picVal.lastIndexOf('\\');
	  $('#picName').html(picVal.substring(ind+1,picVal.length)+'  ');
	}
	
	$(document).ready(function(){
	})
</script>
<form name="form1" id="appForm1" method="post" enctype="multipart/form-data">
<p class="tt"><a href="javascript:void(0);" class="close" onclick="closeExternalApp();">关闭</a><span>设置常用应用</span></p>
<div class="wrap">
    <div class="app-grid-tt"><span>提示：点击添加应用</span>未添加应用</div>
	<div class="app-grid-scroll">
        <div class="app-grid" id="appListNo">
            <ul class="fn-clear">
                <#list moduleList as module>
                <li data-id="${module_index}" module-id="${module.id}" data-add="no" data-out="no" data-load="no">
                    <a href="javascript:void(0);">
                        <span class="app-img"><img src="${request.contextPath}${module.picture}_b.png"></span>
                        <span class="app-name">${module.name}</span>
                        <i></i>
                    </a>
                </li>
                </#list>
            </ul>
        </div>
    </div>
    <#if loginInfo.user.type ==0 || loginInfo.user.type ==1>
    <div class="app-grid-tt"><span>提示：先上传应用图片，再点击添加</span>添加第三方应用</div>
    <div class="app-grid-out">
        <div class="app-grid-scroll">
            <div class="app-grid" id="appListOut">
                <ul class="fn-clear">
                    <#list externalAppList as app>
                    <li data-id="${app_index}" app-id="${app.id!}" data-add="no" data-out="yes" data-load="no">
                        <a href="javascript:void(0);">
                            <span class="app-img"><img src="${app.downloadPath!}"></span>
                            <span class="app-name">${app.appName!}</span>
                            <i></i>
                        </a>
                    </li>
                    </#list>
                    <li class="add" id="addAppOut">
                        <a href="javascript:void(0);">
                            <span class="app-img"><img src="${request.contextPath}/static/images/app/desk_icon/add.png"></span>
                            <span class="app-name">添加</span>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    </#if>
</div>
	<p class="dd">
		<a class="abtn-blue submit" id="saveAppList" href="javascript:void(0);" onclick="saveAppShow();return false;"><span>保存</span></a>
		<a class="abtn-blue reset ml-5" id="cancelAppList" href="javascript:void(0);" onclick="closeExternalApp();"><span>取消</span></a>
	</p>
    <div class="add-out-layer form" style="display:none;">
    	<div class="form-wrap">
            <table>
                <input type="hidden" id="temp" name="temp" value="true">
                <tr>
                    <th>应用名称：</th>
                    <td><input type="text" class="input-txt" id="appName" name="appName" notNull="true" maxLength="16" style="width:200px;"></td>
                    <td>最多支持8个汉字（16个字符）</td>
                </tr>
                <tr>
                    <th>应用地址：</th>
                    <td><input type="text" class="input-txt" id="appUrl" name="appUrl" notNull="true" maxLength="1000" style="width:200px;"></td>
                    <td>（请以http://开始）</td>
                </tr>
                <tr>
                    <th>应用排序：</th>
                    <td><input type="text" class="input-txt" id="orderNo" name="orderNo" notNull="true" maxLength="2" dataType="integer" nonnegative="true" style="width:200px;"></td>
                    <td></td>
                </tr>
                <tr>
                    <th valign="top" class="pt-10">应用图标：</th>
                    <td valign="top" class="pt-10">
                        <p><span id="picName"></span><a href="javascript:void(0);" class="up-file">上传图片<input type="file" name="appPic" id="appPic" onchange="changePic();return false;"></a></p>
                        <p class="c-orange">建议上传60*60图片</p>
                    </td>
                    <td valign="top" class="pt-10">&nbsp;</td>
                </tr>
            </table>
        </div>
        <p class="form-bt"><a href="javascript:void(0);" class="cancel">取消</a><a href="javascript:void(0);" onclick="saveExternalApp();return false;" class="submit">确定</a></p>
    </div>
</form>
<script id="deskAppJs" type="text/javascript" src="${request.contextPath}/static/js/myscript-deskApp.js"></script>