<#-- 
配置说明如下： 
1、修改本文件中的文字
2、图片路径：
	1)system\images\abount\system_top.jpg
	2)system\images\index\logo.jpg
	3)help\sys_images\top_tel.gif
3、数据库配置：cnet2_sys_version
-->


<#-- 产品图片 -->
<#macro logPic>
  <tr id="log_pic" valign="top" height="100">
    <td>
	  <table width="100%" height="" border="0" cellpadding="0" cellspacing="0" background="${request.contextPath}/static/common/images/top_bg.jpg">
       <tr>
      <td><img id="top_left_img" src="${request.contextPath}/static/common/images/top_left.jpg" width="334" height="100"></td>
      <td><img id="top_center_img" src="${request.contextPath}/static/common/images/top_center.jpg" width="355" height="100"></td>
      <td align="right"><img id="top_right_img" src="${request.contextPath}/static/common/images/top_right.jpg" width="101" height="100"></td>
       </tr>
      </table>
    </td>
   </tr>
</#macro>

<#-- 版权 -->
<#macro copyright>
  <tr>
    <td height="30">
	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  		<tr>
    	  <td align="center"></td>
  		</tr>
	  </table>
	</td>
  </tr>
</#macro>


