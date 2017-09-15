<#assign loginInfo=session.getAttribute("${appsetting.loginSessionName}")>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<script language="JavaScript">
<#--assign basePath = request.scheme + "://" + request.serverName + ":" + request.serverPort + request.contextPath-->
<#assign basePath = request.contextPath>
function Module() {
  this.moduleId	= null;
  this.version	= null;
  this.fileList	= null;
  this.fileUrl	= null;
  this.fileType	= -1;
  this.mainFile	= null;
  this.winName	= null;
  this.width		= -1;
  this.height		= -1;
  this.isResize	= -1;
  this.pbd			= null;
  this.key			= null;
  this.actionParam	= null;
  this.project = null;
};

Module.prototype.run = function() {
  var basePath = "${request.scheme + "://" + request.serverName + ":" + request.serverPort + request.contextPath}";
  var commonUrl = basePath + "/app_x/pbd/common/";
  var dbparmUrl = basePath + "/app_x/dbparm";
  //alert(commonUrl);

  var commonProject = "EISS";
  var commonVersion = "20030926.1";
  var envUrl = basePath + "/downfolder/env.exe";
  var envVersion = "7.0";
  
  //新增的环境参数
  var envUrl2 = "";   //"/downfolder/msenv.exe";
  var envVersion2 = "";  //"2000";
  var isASA = false;

//main9.exe;main9.pbd;common_edu9.pbd;common_library9.pbd;common_object9.pbd

  if (pb.checkenvex(envUrl, envVersion, envUrl2, envVersion2) == 0) {
    //alert("执行成功：不需要下载该运行环境或已经下载成功");    
    if (pb.CheckFile(commonProject, this.mainFile + ";" + this.pbd, commonVersion, commonUrl, commonProject) == 0) {
      //alert("执行成功: 公共类检查成功");
      if (pb.CheckFile(this.moduleId, this.fileList, this.version, basePath + this.fileUrl, commonProject + "\\" + this.moduleId) == 0) {
        //alert("执行成功: 模块文件检查成功" + this.fileList + " " + this.fileUrl);
        var commandPara = dbparmUrl + " ${loginInfo.user.name} " + this.moduleId + " " + this.winName + " " + this.width + " " + this.height + " " +  this.isResize + " " + this.pbd + " " + this.key + " " + this.actionParam + " " + this.project;
        //alert(commandPara);
        //alert(this.fileType);
        //alert(commonProject + "\\" + this.mainFile);
        pb.Execute(this.fileType, commonProject + "\\" + this.mainFile, commandPara);
      }
    }    
  }
  else{
  	alert("请下载环境：导航条->工具->环境下载");
  }
};

Module.prototype.exe = function() {
  pb.Execute("2", this.mainFile, "");
};

function detectPlugin(){
    var pVersion;
    try
    {
        pVersion = pb.CheckEnv("","");  //空参数调用不会提示任何信息，只作控件是否正常的检查。
        return true;
    }
    catch (e)
    {
        return false;
    }
}

var seconds = 5;

function countDown() {
  timer.innerHTML = seconds--;
  if (seconds > 0) {
    setTimeout("countDown()", 1000);
  }
  else {
    self.close();
  }
}
</script>
</head>

<body>
<div id="info" style="display:none"></div>

<#if action.hasActionErrors()>
	<#list actionErrors as x>
	${x}
	</#list>
	<p align="center"><font id="timer" color="#990000">&nbsp;</font> 秒后会自动关闭</p>
	<script language="javascript">
		countDown();	
	</script>
<#else>
	<p align="center"><font id="timer" color="#990000">&nbsp;</font><span id="msg"> 正在加载中......</span></p>
	<OBJECT ID="pb" WIDTH="0" HEIGHT="0" 
	CLASSID="CLSID:15FA81D1-075F-4623-B0EF-218269354CC7" 
	CODEBASE="${request.contextPath}/downfolder/PbDownEx.ocx#version=1,3,3,0">
	  <PARAM NAME="_Version" VALUE="65536">
	  <PARAM NAME="_ExtentX" VALUE="0">
	  <PARAM NAME="_ExtentY" VALUE="0">
	  <PARAM NAME="_StockProps" VALUE="0">
	</OBJECT>
	
  <script language="JavaScript">
	<#if pbModule?exists>
		pbModule = new Module();
		pbModule.moduleId = "${pbModule.getModuleId()}";
		pbModule.version  = "${(pbModule.getVersion())?default('')}";
		pbModule.fileList = "${(pbModule.getFileList())?default('')}";
		pbModule.fileUrl  = "${(pbModule.getFileUrl())?default('')}";
		pbModule.fileType = 1;
		pbModule.mainFile = "${(pbModule.getMainFile())?default('')}";
		pbModule.winName  = "${(pbModule.getWinName())?default('')}";
		pbModule.width    = ${(pbModule.getWidth())?default(0)};
		pbModule.height   = ${(pbModule.getHeight())?default(0)};
		pbModule.isResize = ${(pbModule.getIsResize())?default(1)};
		pbModule.pbd      = "${(pbModule.getPbd())?default('')}";
	//	pbModule.key      = "";
		pbModule.actionParam  = "${(pbModule.getActionParam())?default('')}";
		pbModule.project      = "${(pbModule.getProject())?default('')}";
		if(detectPlugin()){
			pbModule.run();
			self.close();
		}else{
			//alert("控件没有安装！");
			msg.innerHTML="秒后关闭。<br>PbDownEx控件没有安装！"
			countDown();
		}
	<#else>
		promptMessage.innerTEXT = "您要执行的模块不存在";
	</#if>
  </script>	
</#if>
</body>
</html>