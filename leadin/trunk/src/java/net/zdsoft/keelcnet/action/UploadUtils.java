package net.zdsoft.keelcnet.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Webwork工具类，用来获取request中的上传文件
 * @author taoy
 * @since 1.0
 * @version $Id: UploadUtils.java,v 1.3 2007/09/20 08:21:09 liangxiao Exp $
 */
public class UploadUtils {
    private static final Logger LOG = LoggerFactory.getLogger(UploadUtils.class);

    public static final UploadFile[] handleFileUpload(HttpServletRequest request,long sizeMax)
            throws FileUploadException {
        if (request instanceof MultiPartRequestWrapper) {
            MultiPartRequestWrapper requestWrapper = (MultiPartRequestWrapper) request;

            if (true == requestWrapper.hasErrors()) {
                FileUploadException fileUploadException = new FileUploadException();
                fileUploadException
                        .addError("忽略上传文件读取\n当前FORM表单请求request存在错误，请先检查确认没有错误！");

                Collection<String> errors = requestWrapper.getErrors();

                for (Iterator<String> i = errors.iterator(); i.hasNext();) {
                    fileUploadException.addError((String) i.next());
                }

                throw fileUploadException;
            }

            long requestSize = request.getContentLength();

            try {
                if ((sizeMax >= 0) && (requestSize > sizeMax)) {
                    throw new FileUploadException("错误：上传文件大小(size="
                            + (int) (requestSize / (1024 * 1024))
                            + "M)超出系统设置上传文件大小(size="
                            + (int) (sizeMax / (1024 * 1024)) + "M)！");
                }
            }
            catch (NumberFormatException e) {
                throw new FileUploadException(e.toString());
            }

            /**
             * requestWrapper.getFileNames() is used while upload field is
             * mutiple, if only one upload field, and also you know the field
             * name, please use requestWrapper.getFile(fieldName);
             */
            Enumeration<String> e = requestWrapper.getFileParameterNames();
            List<UploadFile> filelist = new ArrayList<UploadFile>();

            while (e.hasMoreElements()) {
                String fieldName = String.valueOf(e.nextElement());

                // uploaded file properties
                File[] uploadedFiles = requestWrapper.getFiles(fieldName);

                if ((uploadedFiles == null) || (uploadedFiles.length == 0)) {
                    FileUploadException fileUploadException = new FileUploadException();
                    fileUploadException.addError("文件上传表单域读取错误: " + fieldName);
                    throw fileUploadException;
                }

                String[] uploadNames = requestWrapper.getFileNames(fieldName);
                String[] contentTypes = requestWrapper
                        .getContentTypes(fieldName);

                if ((uploadNames.length == contentTypes.length)
                        && (uploadNames.length == uploadedFiles.length)) {
                    String fileName;

                    for (int i = 0; i < uploadedFiles.length; i++) {
                        fileName = uploadNames[i];

                        int slash = Math.max(fileName.lastIndexOf('/'),
                                fileName.lastIndexOf('\\'));

                        if (slash > -1) {
                            fileName = fileName.substring(slash + 1);
                        }

                        LOG.info("上传文件：" + fileName);

                        UploadFile uploadfile = new UploadFile(fileName,
                                uploadedFiles[i], contentTypes[i], fieldName);
                        filelist.add(uploadfile);
                        LOG.info("File [" + uploadedFiles[i] + "] uploaded.");
                    }
                }
            }

            return (UploadFile[]) filelist.toArray(new UploadFile[0]);
        }
        else {
            throw new FileUploadException(
                    "传入参数HttpServletRequest [request] 不是 MultiPartRequestWrapper 的实例, "
                            + "请检查你的form表单属性, 确认是否已加入\"enctype=\"multipart/form-data\"该属性.\n");
        }
    }
    
    public static final UploadFile[] handleFileUpload2(HttpServletRequest request,long sizeMax) throws FileUploadException {
    	if (request instanceof MultiPartRequestWrapper) {
			MultiPartRequestWrapper requestWrapper = (MultiPartRequestWrapper) request;
			Collection<String> errors = requestWrapper.getErrors();
			if( errors != null){
				for(Iterator<String> it = errors.iterator(); it.hasNext();){
					String error = errors.iterator().next();
					if(StringUtils.contains(error, "its size (") && StringUtils.contains(error, "maximum (")){
						String s1 = StringUtils.substringBetween(error, "its size (", ")");
						String s2 = StringUtils.substringBetween(error, "maximum (", ")");
						error = "上传文件大小为" + s1 + "字节(" + NumberUtils.toLong(s1) / (1024*1024) + "兆)" +  
						"，已经超过了系统设定的最大限制" + s2 + "字节(" + NumberUtils.toLong(s2) / (1024*1024) + "兆)";
						throw new FileUploadException(error);
					}
				}
				long requestSize = request.getContentLength();

                if ((sizeMax >= 0) && (requestSize > sizeMax)) {
                    throw new FileUploadException("错误：上传文件大小(size="
                            + (int) (requestSize / (1024 * 1024))
                            + "M)超出系统设置上传文件大小(size="
                            + (int) (sizeMax / (1024 * 1024)) + "M)！");
                }
				if(CollectionUtils.isNotEmpty(errors)){
					throw new FileUploadException("保存超时");
				}
            }
			Enumeration e = requestWrapper.getFileParameterNames();
			List<UploadFile> filelist = new ArrayList<UploadFile>();
			while (e.hasMoreElements()) {
				String fieldName = String.valueOf(e.nextElement());

                // uploaded file properties
                File[] uploadedFiles = requestWrapper.getFiles(fieldName);

                if ((uploadedFiles == null) || (uploadedFiles.length == 0)) {
                    FileUploadException fileUploadException = new FileUploadException();
                    fileUploadException.addError("文件上传表单域读取错误: " + fieldName);
                    throw fileUploadException;
                }

                String[] uploadNames = requestWrapper.getFileNames(fieldName);
                String[] contentTypes = requestWrapper
                        .getContentTypes(fieldName);

                if ((uploadNames.length == contentTypes.length)
                        && (uploadNames.length == uploadedFiles.length)) {
                    String fileName;

                    for (int i = 0; i < uploadedFiles.length; i++) {
                        fileName = uploadNames[i];

                        int slash = Math.max(fileName.lastIndexOf('/'),
                                fileName.lastIndexOf('\\'));

                        if (slash > -1) {
                            fileName = fileName.substring(slash + 1);
                        }

                        LOG.info("上传文件：" + fileName);

                        UploadFile uploadfile = new UploadFile(fileName,
                                uploadedFiles[i], contentTypes[i], fieldName);
                        filelist.add(uploadfile);
                        LOG.info("File [" + uploadedFiles[i] + "] uploaded.");
                    }
                }
			}
			return (UploadFile[]) filelist.toArray(new UploadFile[0]);
		} else {
            throw new FileUploadException(
                    "传入参数HttpServletRequest [request] 不是 MultiPartRequestWrapper 的实例, "
                            + "请检查你的form表单属性, 确认是否已加入\"enctype=\"multipart/form-data\"该属性.\n");
        }
    }

    public static final class FileUploadException extends Exception {
        /**
         * Comment for <code>serialVersionUID</code>
         */
        private static final long serialVersionUID = 1578324388796015314L;
        
        private List<String> errors;

        public FileUploadException() {
            errors = new ArrayList<String>();
        }

        public FileUploadException(String error) {
            errors = new ArrayList<String>();
            errors.add(error);
        }

        public void addError(String error) {
            errors.add(error);
        }

        public String[] getErrors() {
            return (String[]) errors.toArray(new String[0]);
        }

        public String getMessage() {
            StringBuffer sb = new StringBuffer();
            sb.append("\n***文件上传错误***");

            for (Iterator<String> iter = errors.iterator(); iter.hasNext();) {
                String element = (String) iter.next();
                sb.append("\nERROR: " + element);
            }

            sb.append("\n****************");

            return sb.toString();
        }
    }
}
