package net.zdsoft.office.qrCode.service.impl;

import java.io.File;

import net.zdsoft.eis.base.common.entity.StorageDir;
import net.zdsoft.eis.base.common.service.StorageDirService;
import net.zdsoft.eis.base.common.service.SysOptionService;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.frame.util.RedisUtils;
import net.zdsoft.office.qrCode.service.QrCodeService;
import net.zdsoft.office.util.ZXingUtil;

import org.apache.commons.lang.StringUtils;

public class QrCodeServiceImpl implements QrCodeService {
	
	private SystemIniService systemIniService;
	private SysOptionService sysOptionService;
	private StorageDirService storageDirService;
	
	/**
	 * 初始化二维码
	 */
	public void init(){
		String needQrCode = systemIniService.getValue(BaseConstant.NEEDQRCODE);
		if("1".equals(needQrCode)){
			String domain = RedisUtils.get("EIS.BASE.PATH.V6");
			System.out.println("-----domain-----------------------"+domain);
			if(StringUtils.isNotBlank(domain)){
				try {
					StorageDir dir = storageDirService.getActiveStorageDir();
					//二维码地址
					String detailPath = dir.getDir() + File.separator + "qr_code" + File.separator;
					File saveDirFile = new File(detailPath);
					//如果不存在，那么创建一个
					if (!saveDirFile.exists()) {
						saveDirFile.mkdirs();
					}
					//生成二维码
					String androidFileName = "oaQrCode.jpg";
					String androidUrl = domain+"/common/open/officemobile/download.action";
//					ZXingUtil.encodeQRCodeImage(androidUrl, null, detailPath+androidFileName, 200, 200, domain+"/static/images/logo_android.png");
					ZXingUtil.encodeQRCodeImage(androidUrl, null, detailPath+androidFileName, 400, 400, null);
					//生成ios二维码
//					String fileUrl=sysOptionService.getValue("FILE.URL");
//					if(StringUtils.isNotBlank(fileUrl)){
//						if (!fileUrl.endsWith("/")) {
//							fileUrl += "/";
//						}
//						String iosFileName = "iosQrCode.jpg";
//						String iosUrl = fileUrl+"mobile/ios-install.html";
//						ZXingUtil.encodeQRCodeImage(iosUrl, null, detailPath+iosFileName, 200, 200, domain+"/static/images/logo_ios.png");
//					}else{
//						System.out.println("FILE.URL未配置，无法生成ios二维码");
//					}
					System.out.println("OfficeMsgReceivingServiceImpl类生成手机oa二维码图片成功");
				} catch (Exception e) {
					System.out.println("domain路径不对");
					e.printStackTrace();
				}
			}else{
				System.out.println("获取domain缓存数据失败");
			}
			
		}else{
			System.out.println("日常消息不需要显示手机oa apk二维码");
		}
	}

	public void setSystemIniService(SystemIniService systemIniService) {
		this.systemIniService = systemIniService;
	}

	public void setStorageDirService(StorageDirService storageDirService) {
		this.storageDirService = storageDirService;
	}

	public void setSysOptionService(SysOptionService sysOptionService) {
		this.sysOptionService = sysOptionService;
	}
	
}
