package net.zdsoft.leadin.photo;

import javax.servlet.ServletContext;

public class PhotoConstant {
    // 缓冲区的默认大小,为byte
    public static final int BUFFER_SIZE = 1024 * 10;

    public static final String NOPICTURE_URI = "/static/images/nopicture.jpg";

    private static String nopicture = null;

    /**
     * 返回NOPicture的URI
     * 
     * @param context
     * @return String
     */
    public static String getNoPictrueURI(ServletContext context) {
        if (nopicture == null) {
            nopicture = context.getContextPath() + NOPICTURE_URI;
        }
        return nopicture;
    }

}
