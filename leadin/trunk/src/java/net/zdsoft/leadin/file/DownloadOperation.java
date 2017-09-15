/*
 * Created on 2004-8-20
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.zdsoft.leadin.file;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author liangxiao
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DownloadOperation extends FileOperation {
    
    private String photoPath = null;

	public DownloadOperation() {
	}
	
	public DownloadOperation(String path){
	    photoPath = path;
	}
	
    public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String pathInfo = request.getPathInfo();
		
		//url路径的编码取决于tomcat下conf/server.xml中的设置和浏览器
		//pathInfo = new String(pathInfo.getBytes("UTF-8"), "UTF-8");
		//pathInfo= new String(pathInfo.getBytes("ISO-8859-1"),"UTF-8");
		@SuppressWarnings("deprecation")
		String _pathInfo=URLDecoder.decode(URLEncoder.encode(pathInfo,"ISO-8859-1"));
		pathInfo = _pathInfo;
        
//		pathInfo = new String(pathInfo.getBytes(),"UTF-8");

		String noticeName = request.getParameter("notice");
		//System.out.println(pathInfo);
		String virtualPath = pathInfo.substring(pathInfo.indexOf("/", 1));	    
		
		if (noticeName != null) {
		    int pos = virtualPath.lastIndexOf("/");
			virtualPath = virtualPath.substring(0, pos + 1) + noticeName ;
		}

		String fileName;
		if (photoPath == null){
		    fileName = getRootDir() + virtualPath;
		}
		else{
		    fileName = photoPath + virtualPath;
		}
		//System.out.println(fileName);
		//System.out.println(URLEncoder.encode(name, "UTF-8"));
		//response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
		
		File file = new File(fileName);

		if (!file.exists()) {
	//	    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		download(file, request, response);
	}

	private void download(File file, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/data");
	    response.setHeader("Cache-Control",""); 

		String range = request.getHeader("Range");

		if (range == null) {
			doDownload(file, request, response);
			return;
		}

		long fileSize = file.length();
		long startPos = getStartPosition(range);
		long endPos = getEndPosition(range);

		if (startPos == NONE_FLAG) {
			return;
		}

		if (endPos == NONE_FLAG || endPos >= fileSize) {
			endPos = fileSize - 1;
		}

		doRangeDownload(file, startPos, endPos, request, response);
	}

	private static int NONE_FLAG = -1;

	private long getStartPosition(String range) {
		return string2Long(range.substring(range.indexOf("=") + 1, range
				.lastIndexOf("-")));
	}

	private long getEndPosition(String range) {
		return string2Long(range.substring(range.indexOf("-") + 1));
	}

	private long string2Long(String stringValue) {
		long numeric = 0;

		if (stringValue.length() == 0) {
			numeric = NONE_FLAG;
		} else {
			try {
				numeric = Long.parseLong(stringValue);
			} catch (NumberFormatException ex) {
				numeric = 0;
			}
		}

		return numeric;
	}

	private void doDownload(File file, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		BufferedInputStream in = null;
		BufferedOutputStream out = null;

		try {
			in = new BufferedInputStream(new FileInputStream(file));
			out = new BufferedOutputStream(response.getOutputStream());

			response.setContentLength(new Long(file.length()).intValue());

			int length;
			byte[] data = new byte[1024];
			while ((length = in.read(data)) != -1) {
				out.write(data, 0, length);
			}
		} finally {
			in.close();
			out.flush();
			out.close();
		}
	}

	private void doRangeDownload(File file, long startPos, long endPos,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BufferedInputStream in = null;
		BufferedOutputStream out = null;

		try {
			in = new BufferedInputStream(new FileInputStream(file));
			out = new BufferedOutputStream(response.getOutputStream());

			if (startPos > 0) {
				in.skip(startPos);
			}

			response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
			response.setContentLength(new Long(endPos - startPos + 1)
					.intValue());
			response.setHeader("Content-Range", "bytes " + startPos + '-'
					+ endPos + '/' + file.length());

			int data;
			while ((data = in.read()) != -1) {
				out.write(data);

				if (++startPos > endPos) {
					break;
				}
			}
		} finally {
			in.close();

			out.flush();
			out.close();
		}
	}

	/*
	private String toUTF8String(String s) {
		StringBuffer sb = new StringBuffer();

		for (int i = 0, n = s.length(); i < n; i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b;

				try {
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception e) {
					//log.error("Get utf-8 bytes failed!", e);
					b = new byte[0];
				}

				for (int j = 0, len = b.length; j < len; j++) {
					int k = b[j];
					if (k < 0) {
						k += 256;
					}

					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}

		return sb.toString();
	}
    */
}