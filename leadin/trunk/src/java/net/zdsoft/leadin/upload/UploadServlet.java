package net.zdsoft.leadin.upload;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import net.zdsoft.keel.util.ServletUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;


public class UploadServlet extends HttpServlet
{
  /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -4278279696148744526L;

protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    doPost(request,response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
      ServletUtils.setCharacterEncoding(request);
    HttpSession session = request.getSession();

    if("status".equals(request.getParameter("c")))
    {
      doStatus(session, response);
    }
    else
    {
      doFileUpload(session, request, response);
    }
  }

  private void doFileUpload(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException
  {
    try
    {
      FileUploadListener listener = new FileUploadListener(request.getContentLength());
      
      // 同一个session同时上传多个不同的文件，会被覆盖掉 TODO
      session.setAttribute("FILE_UPLOAD_STATS", listener.getFileUploadStats());
      
      FileUploadListener.FileUploadStats fileUploadStats = (FileUploadListener.FileUploadStats) session.getAttribute("FILE_UPLOAD_STATS");
      System.out.println("====================111=="+fileUploadStats);
      System.out.println("====================111=="+fileUploadStats.getBytesRead());
      FileItemFactory factory = new MonitoredDiskFileItemFactory(listener);
      ServletFileUpload upload = new ServletFileUpload(factory);
     
      List<FileItem> fileItems = new ArrayList<FileItem>();
      List items = upload.parseRequest(request);      
      boolean hasError = false;
      for (Iterator i = items.iterator(); i.hasNext();)
      {
        FileItem fileItem = (FileItem) i.next();
        if (!fileItem.isFormField())
        {

          // *************************************************
          // This is where you would process the uploaded file
          // *************************************************

            fileItems.add(fileItem);
        }
      }

      // 同一个session同时上传多个不同的文件，会被覆盖掉 TODO
      request.getSession().setAttribute("uploadFiles", fileItems);
      if(!hasError)
      {
        sendCompleteResponse(response, null);
      }
      else
      {
        sendCompleteResponse(response, "Could not process uploaded file. Please see log for details.");
      }
    }
    catch (Exception e)
    {
      sendCompleteResponse(response, e.getMessage());
    }
  }

  private void doStatus(HttpSession session, HttpServletResponse response) throws IOException
  {
    // Make sure the status response is not cached by the browser
    response.addHeader("Expires", "0");
    response.addHeader("Cache-Control", "no-store, no-cache, must-revalidate");
    response.addHeader("Cache-Control", "post-check=0, pre-check=0");
    response.addHeader("Pragma", "no-cache");
    response.setContentType("text/html;charset=UTF-8"); 
    response.setCharacterEncoding("UTF-8");

    FileUploadListener.FileUploadStats fileUploadStats = (FileUploadListener.FileUploadStats) session.getAttribute("FILE_UPLOAD_STATS");
    if(fileUploadStats != null)
    {   System.out.println("====================222=="+fileUploadStats);
        System.out.println("====================222=="+fileUploadStats.getBytesRead());
      long bytesProcessed = fileUploadStats.getBytesRead();
      long sizeTotal = fileUploadStats.getTotalSize();
      long percentComplete = (long)Math.floor(((double)bytesProcessed / (double)sizeTotal) * 100.0);
      long timeInSeconds = fileUploadStats.getElapsedTimeInSeconds();
      double uploadRate = bytesProcessed / (timeInSeconds + 0.00001);
      double estimatedRuntime = sizeTotal / (uploadRate + 0.00001);

      response.getWriter().println("<b>文件上传信息：</b><br/>");

      if(fileUploadStats.getBytesRead() != fileUploadStats.getTotalSize())
      {
        response.getWriter().println("<div class=\"prog-border\"><div class=\"prog-bar\" style=\"width: " + percentComplete + "%;\"></div></div>");
        response.getWriter().println("正在上传：已上传 " + bytesProcessed + " 字节； 文件大小 " + sizeTotal + " 字节 (" + percentComplete + "%)； 上传速率 " + (long)Math.round(uploadRate / 1024) + " KB <br/>");
        response.getWriter().println("运行时间：已上传 " + formatTime(timeInSeconds) + " ； 总时间 " + formatTime(estimatedRuntime) + " ； 剩余 " + formatTime(estimatedRuntime - timeInSeconds) + " <br/>");
      }
      else
      {
          if(!(fileUploadStats.isNone())){ 
              response.getWriter().println("已上传：" + bytesProcessed + " ； 文件大小 " + sizeTotal + " 字节<br/>");
          }
      }
    }

    if(fileUploadStats != null && fileUploadStats.getBytesRead() == fileUploadStats.getTotalSize())
    {
        if(fileUploadStats.isDone()){
            response.getWriter().println("文件上传完成。<br/>");
            //为下次上传清空数据
            fileUploadStats.clear();
        }
    }
  }

  private void sendCompleteResponse(HttpServletResponse response, String message) throws IOException
  {
    if(message == null)
    {
      response.getOutputStream().print("<html><head><script type='text/javascript'>function killUpdate() { window.parent.killUpdate(''); }</script></head><body onload='killUpdate()'></body></html>");
    }
    else
    {
      response.getOutputStream().print("<html><head><script type='text/javascript'>function killUpdate() { window.parent.killUpdate('" + message + "'); }</script></head><body onload='killUpdate()'></body></html>");
    }
  }

  private String formatTime(double timeInSeconds)
  {
    long seconds = (long)Math.floor(timeInSeconds);
    long minutes = (long)Math.floor(timeInSeconds / 60.0);
    long hours = (long)Math.floor(minutes / 60.0);

    if(hours != 0)
    {
      return hours + "时 " + (minutes % 60) + "分 " + (seconds % 60) + "秒";
    }
    else if(minutes % 60 != 0)
    {
      return (minutes % 60) + "分 " + (seconds % 60) + "秒";
    }
    else
    {
      return (seconds % 60) + " 秒";
    }
  }
}
