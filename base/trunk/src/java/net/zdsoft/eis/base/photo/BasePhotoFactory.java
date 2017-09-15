package net.zdsoft.eis.base.photo;

import net.zdsoft.leadin.photo.Photo;

/**
 * Photo构造Factory
 */
public class BasePhotoFactory {
    private static final Photo studentPhoto = new StudentPhoto();

    public static Photo getStudentPhoto() {
        return studentPhoto;
    }

    /**
     * 学生Photo实现类
     */
    public static class StudentPhoto extends Photo {

        public final static long MAX_IMAGE_SIZE = 1024;// 限制图片上传大小,单位k
        public final static String STUDENT_PHOTO_TEMP = "student_photo_temp";// 学生临时照片文件夹
        public final static String[] IMAGE_PATTEN = { "gif", "jpg", "jpeg" };// 限制图片上传格式

        private StudentPhoto() {

        }

        public String getPhotoName() {
            return "学生";
        }

        /**
         * 返回照片的标识符
         * 
         * @return String
         */
        public String getPhotoIdentifier() {
            return "student";
        }
    }
}
