<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>推荐学校详细信息</title>
		<link rel="stylesheet" type="text/css" href="css/style.css">
		<link href="${request.contextPath}/static/css/layout.css" type="text/css" rel="stylesheet">
	<style type="text/css">
	.tinput{
		border-width:1px;
		border-style:solid;
		border-color:0;
	}
	</style>
	<link href="/eis/css/layout.css" type="text/css" rel="stylesheet">
	<link href="/eis/css/layout.css" type="text/css" rel="stylesheet">
	<script  language="javascript" src="js/manager1.js" ></script>
	<script type="text/javascript" language="javascript">
		function changePhoto(obj, src, width, height){
		    var srcImage = new Image();
		    srcImage.src = src;
		    var srcWidth = srcImage.width;
		    var srcHeight = srcImage.height;
		
		    if (srcWidth <= width && srcHeight <= height) {
		        widtht = srcWidth;
		        heightt = srcHeight;
		    }
		    else {
		    	var scale = 1;
		    	if ((width / srcWidth) > (height / srcHeight)){
		    		scale = height / srcHeight;
		    	}
		    	else{
		    		scale = width / srcWidth;
		    	}
		        widtht = (srcWidth * scale);
		        heightt = (srcHeight * scale);        
		    }
		    if (widtht != 0 && heightt != 0){
				width = widtht;
				height = heightt;
		    }
		    obj.width = width;
		    obj.height = height;
		}
		function chksubmit(){
			var schNameInput = document.getElementById("schoolName");
			var schRegionCodeInput = document.getElementById("schoolRegionCode");
			var message = "";
			if(schNameInput.value == ""){
				message = message + "学校名称不能为空，请填写";
			}
			if(schRegionCodeInput.value == ""){
				message = message + "\n区域编码不能为空，请填写";
			}
			if(message != ""){
				alert(message);
				return false;
			} 		 
				return true;	
		}
		function showPic(){
			var picurl = document.getElementById("schoolPictureUrl");
			var pic = document.getElementById("showpic");
			var temppic = document.getElementById("tempPic");
			var picTempUrl = document.getElementById("schoolPictureTempUrl");
			
			picTempUrl.value = picurl.value;
			changePhoto(pic,picurl.value,350,230);
			pic.src = picurl.value;
			
			if(picurl.value == ""){
				pic.src = tempPic.value;
				picurl.value = tempPic.value;
				picTempUrl.value = tempPic.value;
			}
		}
	</script>
	</head>
	<body class="YecSpec">
		<table width="776" border="0" align="center" cellpadding="0" cellspacing="0" class="YecSpec8">
  <tr>
    <td align="center" valign="top" class="YecSpec"><br>
      <table width="500" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td align="center">
	<table width="1%" border="0" cellpadding="3" cellspacing="1">
          <tr>
            <td height="232" width="350" colspan="3" algin="center"><img id="showpic" src="${recommendSchool.schoolPictureUrl?default("")}"></td>
            
          </tr>
    </table>
	</td>
      </tr>
      <form name="form1" action="updateRecommendSchoolInfo.action" method="post" enctype="multipart/form-data">
      <tr>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="26"><strong>学校名称</strong>：<input id="schoolName" class="input" name="schoolName" size="45" type="text" maxlength="20" value="${recommendSchool.schoolName?default("")}"> <font color="red">*</font></td>
          </tr>
          <tr>
            <td height="26"><strong>学校主页</strong>：<input class="input" name="schoolHomepage" size="45" type="text" maxlength="20" value="${recommendSchool.schoolHomepage?default("")}"></td>
          </tr>
          <tr>
            <td height="26"><strong>学校博客</strong>：<input class="input" name="schoolBlog" size="45" type="text" maxlength="20" value="${recommendSchool.schoolBlog?default("")}"></td>
          </tr>
          <tr>
            <td height="26"><strong>学校类别</strong>：<select name="schoolType" class="input" style="width:200px;">
            											<option value='01'>幼儿园</option>
														<option value='11'>小学</option>
														<option value='21'>初级中学</option>
														<option value='31'>高级中学</option>
														<option value='41'>职业中学</option>
														<option value='51'>小初一贯制学校</option>
														<option value='61'>完全中学</option>
														<option value='71'>小初高一贯制学校</option>
														<option value='73'>特殊教育学校（小初高一贯制）</option>
            										 </select>
            </td>										 
          </tr>
          <tr>
            <td height="26"><strong>学校地址</strong>：<input class="input" name="schoolAddress" size="45" type="text" maxlength="30" value="${recommendSchool.schoolAddress?default("")}"></td>
          </tr>
          <tr>
            <td height="26"><strong>邮政编码</strong>：<input class="input" name="schoolPostcode" size="45" maxlength="6" onkeyup=" value=value.replace(/[^\d]/g,'') " 
            	onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" value="${recommendSchool.schoolPostcode?default("")}">
          </tr>
          <tr>
            <td height="26"><strong>区域编码</strong>：<input class="input" name="schoolRegionCode" size="45" type="text" maxlength="6" value="${recommendSchool.schoolRegionCode?default("")}"></td>
          </tr>
          <tr>
            <td height="26"><strong>联系电话</strong>：<input class="input" name="schoolPhone" size="45" type="text" maxlength="20" value="${recommendSchool.schoolPhone?default("")}"></td>
          </tr>
          <tr>
            <td height="26"><strong>传　　真</strong>：<input class="input" name="schoolFax" size="45" type="text" maxlength="20" value="${recommendSchool.schoolFax?default("")}"></td>
          </tr>
          <tr>
            <td height="26"><strong>电子邮件</strong>：<input class="input" name="schoolEmail" size="45" type="text" maxlength="20" value="${recommendSchool.schoolEmail?default("")}"></td>
          </tr>
          <tr>
            <td height="26"><strong>学校图片</strong>：<input class="input" id="schoolPictureUrl" name="schoolPictureUrl" size="35" type="file" maxlength="20" value="${recommendSchool.schoolPictureUrl?default("")}" onchange="showPic()"></td>
          </tr>
          <input type="hidden" name="tempPic" id="temppic" value="${recommendSchool.schoolPictureUrl?default("无图片")}">
          	<input id="schoolPictureTempUrl" name="schoolPictureTempUrl" size="35" type="hidden" >
          <tr>
            <td height="26"><strong>学校介绍</strong>：</td>
          </tr>
          <tr>
            <td height="30">
            <textarea name="schoolIntroduction" class="input" rows="7" cols="58">${recommendSchool.schoolIntroduction?default("")}
            </textarea></td>
          </tr>
          	<input name="schoolId" type="hidden" value="${recommendSchool.schoolId?default("")}">
          <tr>
	          <td height="35"><input type="submit" name="Submit" value="保存" class="del_button1" onmouseover = "this.className = 'del_button3';" onmousedown= "this.className = 'del_button2';" 
	          		onClick="return chksubmit()"	onmouseout = "this.className = 'del_button1';"/>
	          </td>
          </tr>
          </form>
          <script type="text/javascript">
				var schoolTypeValue = ${recommendSchool.schoolType?default("")};
				var schTypes = document.getElementById("schoolType");
				for(var i = 0 ; i < schTypes.options.length; i++){
					var schTypeValues = schTypes.options[i].value;
					if(schTypeValues == schoolTypeValue){
						schTypes.options[i].selected = true;
					}
				}
		  </script>
        </table>
	</td>
      </tr>
    </table>
	</body>
</html>