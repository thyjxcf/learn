package net.zdsoft.leadin.dataimport.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import net.zdsoft.keel.util.UUIDUtils;

/**
 *  这个是zip解压通用类,使用的是ant.jar中的org.apache.tools.zip包,解
 *  决了使用java.util.zip包时中文乱码导致出错的问题
 *  org.apache.tools.zip包的api跟j2sdk中的基本一样
 * @author wsn
 *
 */
public class ZipUtil {
	private static final Logger log = LoggerFactory.getLogger(ZipUtil.class);	
	
	private ZipFile zipFile=null;
	public ZipUtil(String zipFileName) throws Exception {
		zipFile=new ZipFile(zipFileName);
	}
	public void closeFile() throws Exception {
		zipFile.close();
	}

    public static String extractFile(String zipFileName,String fileType) throws Exception  { 
        return extractFileIncludeOriFile(zipFileName, fileType)[0];
    }

    
    public static String[] extractFileIncludeOriFile(String zipFileName,String fileType) throws Exception  {
        String rtnFileName = "";   
        String oriName = ""; //原始文件名
        
        String _tmp = UUIDUtils.newId();
        String filePath = zipFileName.substring(0, zipFileName
                .lastIndexOf(File.separator))
                + File.separator + _tmp;
        try{
            
            ZipUtil util = new ZipUtil(zipFileName);
            String path = zipFileName.substring(0, zipFileName
                    .lastIndexOf(File.separator));
           
            util.unZip(filePath);
            File dir = new File(filePath);             
            FileFilter filter = FileFilterUtils.suffixFileFilter(fileType);
            File[] files = dir.listFiles(filter);
            if (null == files || files.length == 0) {
                throw new Exception("zip包里面没有" + fileType + "文件");
                
            }else if (files.length > 1) {
                throw new Exception("zip包里面包括多个" + fileType + "文件");
                
            }else{
                rtnFileName = path + File.separator + _tmp + "." + fileType;
                oriName = files[0].getName();
                files[0].renameTo(new File(rtnFileName));
            }            
            
        }finally{
            deleteFile(filePath);
        }
        
        return new String[]{rtnFileName,oriName};
    }    
  
    /**
     * 解压带密码的压缩包，采用7zip的命令行方式解压（特别用于重庆平台的导入），支持windows和linux平台.
     * @param zipExecutePath 7zip的可执行文件绝对路径
     * @param zipFileName 被解压的压缩包绝对路径
     * @param fileType 被解压的压缩包内的要被解压的文件扩展名
     * @param pwd 被解压的压缩包解压密码
     * @return 返回[加压后的压缩文件名（新名）, 加压后的压缩文件名（原始名）]
     * @throws Exception
     */
    public static String[] extractZip(String zipExecutePath,String zipFileName,String fileType,String pwd) throws Exception  {
        String rtnFileName = "";   
        String oriName = ""; //原始文件名
        
		//zipFileName = zipFileName.substring(1);
		
        String basePath = zipFileName.substring(0, zipFileName.lastIndexOf(File.separator));
        String _tmp = UUIDUtils.newId();
        String filePath = basePath + File.separator + _tmp;
       	
        try{
            
             //7zip命令行解压一个带密码的压缩包
            StringBuilder cmd = new StringBuilder();
            cmd.append(zipExecutePath);
            cmd.append(" x ");
            cmd.append(zipFileName);
            cmd.append(" -p");
            cmd.append(pwd); 
            cmd.append(" -aoa -o");
            cmd.append(filePath); 
            log.debug("7zip unzip cmd:"+cmd.toString());
            
            Process proc = Runtime.getRuntime().exec(cmd.toString());
            File[] files = null;
            if (proc.waitFor() == 0) {
                File dir = new File(filePath);             
                FileFilter filter = FileFilterUtils.suffixFileFilter(fileType);
                files = dir.listFiles(filter);	
            }

            if (null == files || files.length == 0) {
                throw new Exception("zip包里面没有" + fileType + "文件");
                
            }else if (files.length > 1) {
                throw new Exception("zip包里面包括多个" + fileType + "文件");
                
            }else{
                rtnFileName = basePath + File.separator + _tmp + "." + fileType;
                oriName = files[0].getName();
                files[0].renameTo(new File(rtnFileName));
            }            
            
        }finally{
            deleteFile(filePath);
        }
        
        return new String[]{rtnFileName,oriName};
    }     
    
    
    public static void main(String[] args) {
        try {
            System.out.println(extractFile("g:\\aa\\c.zip","xls"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

	private void createDirectory(String directory, String subDirectory) {
		String dir[];
		File fl = new File(directory);
		try {
			if (subDirectory == "" && fl.exists() != true)
				fl.mkdir();
			else if (subDirectory != "") {
				dir = subDirectory.replace('\\', '/').split("/");
				for (int i = 0; i < dir.length; i++) {
					File subFile = new File(directory + File.separator + dir[i]);
					if (subFile.exists() == false)
						subFile.mkdir();
					directory += File.separator + dir[i];
				}
			}
		} catch (Exception ex) {
			log.debug(ex.getMessage());
		}
	}
	
    public void unZip(String outputDirectory)
			throws Exception {
		try {
			 
		    @SuppressWarnings("unchecked")
			java.util.Enumeration e = zipFile.getEntries();
			org.apache.tools.zip.ZipEntry zipEntry = null;
			createDirectory(outputDirectory, "");
			while (e.hasMoreElements()) {
				zipEntry = (org.apache.tools.zip.ZipEntry) e.nextElement();
				log.debug("unziping " + zipEntry.getName());
				if (zipEntry.isDirectory()) {
					String name = zipEntry.getName();
					name = name.substring(0, name.length() - 1);
					File f = new File(outputDirectory + File.separator + name);
					f.mkdir();
					log.debug("创建目录：" + outputDirectory
							+ File.separator + name);
				} else {
					String fileName = zipEntry.getName();
					fileName = fileName.replace('\\', '/');
					// log.debug("测试文件1：" +fileName);
					if (fileName.indexOf("/") != -1) {
						createDirectory(outputDirectory, fileName.substring(0,
								fileName.lastIndexOf("/")));
						fileName = fileName.substring(
								fileName.lastIndexOf("/") + 1, fileName
										.length());
					}

					File f = new File(outputDirectory + File.separator
							+ zipEntry.getName());

					f.createNewFile();
					InputStream in = zipFile.getInputStream(zipEntry);
					FileOutputStream out = new FileOutputStream(f);

					byte[] by = new byte[1024];
					int c;
					while ((c = in.read(by)) != -1) {
						out.write(by, 0, c);
					}
					out.close();
					in.close();
				}
			zipFile.close();
			}
		} catch (Exception ex) {
			log.debug(ex.getMessage());
		}

	}
	
	/**
	 * 删除文件或文件夹
	 * @param path
	 */
	public static void deleteFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}

		if (file.isDirectory()) {
			String[] fileNames = file.list();
			if (fileNames != null) {
				for (int i = 0; i < fileNames.length; i++) {
					deleteFile(path + File.separator +fileNames[i]);
				}
			}
			file.delete();
		} else {
			file.delete();
		}
	}
	
	/**
	 * Creates a Zip archive. If the name of the file passed in is a directory,
	 * the directory's contents will be made into a Zip file.
	 */
	public static void makeZip(String fileName, ZipOutputStream zos)
			throws IOException, FileNotFoundException {
		File file = new File(fileName);
		try {
			// Call recursion.
			recurseFiles(file, zos,"");

		} finally {
			if (null != zos) {
				zos.close();
			}
		}
	}

	public static void recurseFiles(File file, ZipOutputStream zos,String base)
			throws IOException, FileNotFoundException {
		if (file.isDirectory()) {
            ZipEntry zipEntry = new ZipEntry(base+"/");
            zos.putNextEntry(zipEntry);            
            base = base.length() == 0 ? "" : base + "/";
            
			// Create an array with all of the files and subdirectories
			// of the current directory.
			String[] fileNames = file.list();
			if (fileNames != null) {
				// Recursively add each array entry to make sure that we get
				// subdirectories as well as normal files in the directory.
				for (int i = 0; i < fileNames.length; i++) {
					recurseFiles(new File(file, fileNames[i]), zos,base+fileNames[i]);// 给子目录里的文件打包!!
				}
			}
		}
		// Otherwise, a file so add it as an entry to the Zip file.
		else {
			byte[] buf = new byte[1024];
			int len;
			// Create a new Zip entry with the file's name.
			ZipEntry zipEntry = new ZipEntry(base);
			// Create a buffered input stream out of the file
			// we're trying to add into the Zip archive.
			FileInputStream fin = new FileInputStream(file);
			BufferedInputStream in = new BufferedInputStream(fin);
			zos.putNextEntry(zipEntry);
			// Read bytes from the file and write into the Zip archive.
			while ((len = in.read(buf)) >= 0) {
				zos.write(buf, 0, len);
			}
			// Close the input stream.
			in.close();
			// Close this entry in the Zip stream.
			zos.setEncoding("utf-8");   
			zos.closeEntry();
		}
	}	
	
}
