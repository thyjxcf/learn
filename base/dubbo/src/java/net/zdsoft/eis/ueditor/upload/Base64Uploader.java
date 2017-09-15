package net.zdsoft.eis.ueditor.upload;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import net.zdsoft.eis.base.common.entity.StorageDir;
import net.zdsoft.eis.base.common.service.StorageDirService;
import net.zdsoft.eis.ueditor.PathFormat;
import net.zdsoft.eis.ueditor.define.AppInfo;
import net.zdsoft.eis.ueditor.define.BaseState;
import net.zdsoft.eis.ueditor.define.FileType;
import net.zdsoft.eis.ueditor.define.State;
import net.zdsoft.keelcnet.config.ContainerManager;

import org.apache.commons.codec.binary.Base64;

public final class Base64Uploader {

	private static StorageDirService storageDirService = (StorageDirService) ContainerManager
			.getComponent("storageDirService");
	
	public static State save(HttpServletRequest request,String content, Map<String, Object> conf) {
		
		byte[] data = decode(content);

		long maxSize = ((Long) conf.get("maxSize")).longValue();

		if (!validSize(data, maxSize)) {
			return new BaseState(false, AppInfo.MAX_SIZE);
		}
		
		StorageDir dir = storageDirService.getActiveStorageDir();
		
		String savePath = (String) conf.get("savePath");
		SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMdd");
		String filePath = "photo" + File.separator + "attached" +File.separator + savePath + File.separator + df1.format(new Date());
		//检查扩展名
		String fileExt1 = "jpg";
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt1;
		String detailPath = dir.getDir() + File.separator + filePath + File.separator;
		File saveDirFile = new File(detailPath);
		//如果不存在，那么创建一个
		if (!saveDirFile.exists()) {
			saveDirFile.mkdirs();
		}
		File tempFile = new File(detailPath+newFileName);
		FileOutputStream outStream;
		try {
			outStream = new FileOutputStream(tempFile);
			//写入数据 
	        outStream.write(data);
	        outStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        String filename = newFileName;
        State storageState = new BaseState(true);
		if (storageState.isSuccess()) {
			storageState.putInfo("url", PathFormat.format(request.getContextPath()+"/common/downloadFile.action?filePath="+filePath+"&filename="+filename));
			storageState.putInfo("type", fileExt1);
			storageState.putInfo("original", filename);
		}

		return storageState;

	}

	private static byte[] decode(String content) {
		return Base64.decodeBase64(content);
	}

	private static boolean validSize(byte[] data, long length) {
		return data.length <= length;
	}
	
}