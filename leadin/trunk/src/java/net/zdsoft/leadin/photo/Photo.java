package net.zdsoft.leadin.photo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.zdsoft.keel.util.ImageUtils;
import net.zdsoft.keelcnet.config.BootstrapManager;

public abstract class Photo {
    //界面显示的图片像素
    public final static int SMALL_IMAGE_WIDTH = 152 ; // 小图片的默认宽度
    public final static int SMALL_IMAGE_HEIGHT = 114 ; // 小图片的默认高度  
    
    //实际上传后保存的像素
    public final static int IMAGE_WIDTH = SMALL_IMAGE_WIDTH * 6 ; // 服务器上保存的图片的宽度
    public final static int IMAGE_HEIGHT = SMALL_IMAGE_HEIGHT * 6 ; // 服务器上保存的图片的高度
    
    private static final Logger log = LoggerFactory.getLogger(Photo.class);

    /**
     * 返回照片的标识符
     * 
     * @return String
     */
    public abstract String getPhotoIdentifier();

    /**
     * 返回Photo name
     * 
     * @return String
     */
    public String getPhotoName() {
        return null;
    }

    /**
     * 返回此类Photo所在的物理存放的根路径 默认的实现方法
     * 
     * @return String
     */
    public String getPhotoPath(String secondDir, String unitid) {
        StringBuffer pathBuf = new StringBuffer();
        pathBuf.append(File.separator).append(getPhotoIdentifier()).append("_photo");
        pathBuf.append(File.separator).append(unitid).append(File.separator);
        pathBuf.append(secondDir).append(File.separator);
        File photopath = new File(getPhotoRootPath(), pathBuf.toString());
        if (!photopath.exists()) {
            photopath.mkdirs();
        }
        return photopath.getAbsolutePath();
    }

    /**
     * 保存照片
     * 
     * @param unitunionid 学校代码，通常用做一级目录
     * @param secondir 二级目录，比如存储学生照片，此目录名为班级学年（如2006-2007）
     * @param photoid 照片名，比如存储学生照片，此照片名称已学生guid为名称
     * @param photoFile 照片对应文件，一般是临时文件(上传的文件通过框架处理临时先存在\bin\temp目录下
     * @param fileRealname
     *            照片真实名称，就是实际上传的照片名称（比如D:\somefolder\somephoto.gif中的somephoto.gif）
     */
    public void savePhoto(String unitunionid, String secondir, String photoid, File photoFile,
            ServletContext context) throws IOException {
        savePhoto(unitunionid, secondir, photoid, photoFile, null, context);
    }

    public void savePhoto(String unitunionid, String secondir, String photoid, File photoFile,
            String fileRealname, ServletContext context) throws IOException {
        // 首先删除已存在的照片
        photoid = photoid.toUpperCase();
        this.deletePhoto(secondir, unitunionid, photoid, context);
        String photopath = this.getPhotoPath(secondir, unitunionid) + File.separator + photoid;

        // 取临时文件的文件名，通常是类似"upload__666fe528_118de5cb4f4__7fc8_00000086.tmp"这样的名称，需转化
        String _name = photoFile.getName();
        if (fileRealname != null)
            _name = fileRealname;
        if (_name.lastIndexOf(".") > -1)
            photopath += _name.substring(_name.lastIndexOf("."));
        // 有照片上传

        if (photoFile.length() > 0) {
            // 把原始的保存在临时文件夹下的临时文件，保存为指定目标地址、指定压缩比例的图片
            ImageUtils.changeOppositeSize(photoFile.getAbsolutePath(), photopath, IMAGE_WIDTH, IMAGE_HEIGHT);
            
            //删除临时文件
            photoFile.delete();
            
            // FileUtils.copyFile(photoFile, new File(photopath));
        }

    }

    /**
     * 得到Photo root path
     * 
     * @return String
     */
    public String getPhotoRootPath() {
        return BootstrapManager.getStoreHome();
    }

    /**
     * 得到指定photo的文件路径
     * 
     * @param secondir 入学学年
     * @param unitunionid 单位统一编号
     * @param photoid 学生ID
     * @param context ServletContext
     * @return
     */
    public String getSinglePhotoPath(String secondir, String unitunionid, final String photoid,
            ServletContext context) {
        File photoFile = null;
        photoFile = new File(getPhotoPath(secondir, unitunionid));

        String photoPath = "";
        // 根据PhotoID来找到对应的照片
        if (photoFile != null) {

            File[] files = photoFile.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    if (name.startsWith(photoid)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            });

            if (files != null && files.length > 0) {
                photoPath = files[0].getAbsolutePath();
            } else {
                photoPath = PhotoConstant.getNoPictrueURI(context);
            }
        } else {
            photoPath = PhotoConstant.getNoPictrueURI(context);
        }
        // try {
        // Image srcImage = ImageIO.read(photoFile);
        // if (srcImage.getWidth(null) > 110 || srcImage.getHeight(null) > 110){
        // ImageUtils.changeOppositeSize(photoPath, photoPath, 110, 110);
        // }
        // }
        // catch (IOException e) {
        // e.printStackTrace();
        // } // 构造Image对象
        return photoPath;
    }

    /**
     * 获取指定文件名前缀的所有照片文件名列表，用于校产，有多张照片的问题
     * 
     * @param secondir
     * @param unitunionid 单位统一编号
     * @param photoid 文件名前缀
     * @return List {String=文件名}
     */
    public List<String> getPhotosByPhotoid(String secondir, String unitunionid, final String photoid) {
        List<String> photos = new ArrayList<String>();
        File photoFile = null;
        photoFile = new File(getPhotoPath(secondir, unitunionid));

        // 根据PhotoID来找到对应的照片
        if (photoFile != null) {
            File[] files = photoFile.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    if (name.startsWith(photoid)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            });

            if (files != null && files.length > 0) {
                for (int i = 0; i < files.length; i++) {
                    File _tempFile = files[i];
                    photos.add(_tempFile.getName());
                }
            }
        }
        return photos;
    }

    /**
     * 删除照片
     * 
     * @param secondir 二级目录
     * @param unitunionid 单位统一编号
     * @param photoid PhotoID
     * @param context
     */
    public void deletePhoto(String secondir, String unitunionid, final String photoid,
            ServletContext context) {
        String photofile = getSinglePhotoPath(secondir, unitunionid, photoid, context);
        // 如果存在照片，则删除
        if (!photofile.endsWith(PhotoConstant.NOPICTURE_URI)) {
            File file = new File(photofile);
            file.delete();
        }
    }

    /**
     * 转移Photo
     * 
     * @param src 源文件路径
     * @param des 目的路径
     * @param isDelete 是否要删除源文件
     */
    public void transferPhoto(String src, String des, ServletContext context, boolean isDelete) {
        if (!src.endsWith(PhotoConstant.getNoPictrueURI(context))) {
            File file = new File(src);
            try {
                BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
                String desFile = des + File.separator + file.getName();
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(desFile));
                byte[] bytes = new byte[PhotoConstant.BUFFER_SIZE];
                int count = -1;
                while ((count = in.read(bytes)) != -1) {
                    out.write(bytes, 0, count);
                }
                out.flush();
                out.close();
                in.close();
            } catch (Exception ex) {
                log.error("转移Photo时出错：", ex);
            }
            if (isDelete)
                file.delete();
        }
    }

}
