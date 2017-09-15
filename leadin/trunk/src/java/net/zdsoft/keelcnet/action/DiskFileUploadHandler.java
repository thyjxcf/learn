package net.zdsoft.keelcnet.action;

import org.apache.commons.fileupload.DiskFileUpload;
//import org.apache.commons.fileupload.FileItem;
//import org.apache.commons.fileupload.FileUploadException;
//import org.apache.commons.fileupload.MultipartStream;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;

/*
 * @author Brave Tao
 * @since 2004-11-22
 * @version $Id: DiskFileUploadHandler.java,v 1.1 2007/01/08 11:48:56 liangxiao Exp $
 * @since
 */

@SuppressWarnings({ "unchecked", "deprecation" })
public class DiskFileUploadHandler extends DiskFileUpload {
//    @SuppressWarnings("unused")
//    private static final Logger LOG = LoggerFactory.getLogger(DiskFileUploadHandler.class);
//        
//    public List /* FileItem */parseRequest(HttpServletRequest req)
//            throws FileUploadException {
//        List result = super.parseRequest(req);
//        if (result != null)
//            return result;
//        if (null == req) {
//            throw new NullPointerException("req parameter");
//        }
//
//        ArrayList items = new ArrayList();
//        String contentType = req.getHeader(CONTENT_TYPE);
//
//        if ((null == contentType) || (!contentType.startsWith(MULTIPART))) {
//            throw new InvalidContentTypeException(
//                    "the request doesn't contain a " + MULTIPART_FORM_DATA
//                            + " or " + MULTIPART_MIXED
//                            + " stream, content type header is " + contentType);
//        }
//
//        int requestSize = req.getContentLength();
//
//        if (requestSize == -1) {
//            throw new UnknownSizeException(
//                    "the request was rejected because it's size is unknown");
//        }
//
//        long sizeMax = getSizeMax();
//
//        if ((sizeMax >= 0) && (requestSize > sizeMax)) {
//            throw new SizeLimitExceededException(
//                    "the request was rejected because "
//                            + "it's size exceeds allowed range");
//        }
//
//        try {
//            int boundaryIndex = contentType.indexOf("boundary=");
//
//            if (boundaryIndex < 0) {
//                throw new FileUploadException(
//                        "the request was rejected because "
//                                + "no multipart boundary was found");
//            }
//
//            byte[] boundary = contentType.substring(boundaryIndex + 9)
//                    .getBytes();
//            InputStream input = req.getInputStream();
//            MultipartStream multi = new MultipartStream(input, boundary);
//            multi.setHeaderEncoding(getHeaderEncoding());
//
//            boolean nextPart = multi.skipPreamble();
//
//            while (nextPart) {
//                Map headers = parseHeaders(multi.readHeaders());
//                String fieldName = getFieldName(headers);
//
//                if (fieldName != null) {
//                    String subContentType = getHeader(headers, CONTENT_TYPE);
//
//                    if ((subContentType != null)
//                            && subContentType.startsWith(MULTIPART_MIXED)) {
//                        // Multiple files.
//                        byte[] subBoundary = subContentType.substring(
//                                subContentType.indexOf("boundary=") + 9)
//                                .getBytes();
//                        multi.setBoundary(subBoundary);
//
//                        boolean nextSubPart = multi.skipPreamble();
//
//                        while (nextSubPart) {
//                            headers = parseHeaders(multi.readHeaders());
//
//                            if (getFileName(headers) != null) {
//                                FileItem item = createItem(headers, false);
//                                OutputStream os = item.getOutputStream();
//
//                                try {
//                                    multi.readBodyData(os);
//                                } finally {
//                                    os.close();
//                                }
//
//                                items.add(item);
//                            } else {
//                                // Ignore anything but files inside
//                                // multipart/mixed.
//                                multi.discardBodyData();
//                            }
//
//                            nextSubPart = multi.readBoundary();
//                        }
//
//                        multi.setBoundary(boundary);
//                    } else {
//                        if (getFileName(headers) != null) {
//                            // A single file.
//                            FileItem item = createItem(headers, false);
//                            OutputStream os = item.getOutputStream();
//
//                            try {
//                                multi.readBodyData(os);
//                            } finally {
//                                os.close();
//                            }
//
//                            items.add(item);
//                        } else {
//                            // A form field.
//                            FileItem item = createItem(headers, true);
//                            OutputStream os = item.getOutputStream();
//
//                            try {
//                                multi.readBodyData(os);
//                            } finally {
//                                os.close();
//                            }
//
//                            items.add(item);
//                        }
//                    }
//                } else {
//                    // Skip this part.
//                    multi.discardBodyData();
//                }
//
//                nextPart = multi.readBoundary();
//            }
//        } catch (IOException e) {
//            throw new FileUploadException("Processing of "
//                    + MULTIPART_FORM_DATA + " request failed. "
//                    + e.getMessage());
//        }
//
//        return items;
//    }
}
