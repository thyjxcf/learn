/*
 * Created on 2004-7-23
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.zdsoft.leadin.file;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zdsoft.keelcnet.config.BootstrapManager;

/**
 * @author liangxiao
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FileOperationServlet extends HttpServlet {
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -5752104537392788058L;
    
    private HashMap<String, FileOperation> commands = new HashMap<String, FileOperation>();

    public void init(ServletConfig config) throws ServletException {
        String rootDir=BootstrapManager.getStoreHome()+File.separator+"eissdocuments"+File.separator;
        String rootPhotoDir = BootstrapManager.getStoreHome()+File.separator;
       
        new File(rootDir).mkdirs();
        UploadOperation uploadOperation = new UploadOperation();
        DownloadOperation downloadOperation = new DownloadOperation();
        DownloadOperation downloadOperationPhoto = new DownloadOperation(rootPhotoDir);
        DeleteOperation deleteOperation = new DeleteOperation();
        CopyOperation copyOperation = new CopyOperation();
        MoveOperation moveOperation = new MoveOperation();
        ExistOperation existOperation = new ExistOperation();
        FileOperation.setRootDir(rootDir);

        commands.put("upload", uploadOperation);
        commands.put("download", downloadOperation);
        commands.put("delete", deleteOperation);
        commands.put("copy", copyOperation);
        commands.put("move", moveOperation);
        commands.put("exist", existOperation);
        commands.put("photo", downloadOperationPhoto);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Command command = getCommand(request.getPathInfo());        
        if (command == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        command.execute(request, response);
    }

    public void destroy() {
        super.destroy();
    }

    private Command getCommand(String pathInfo) {
        String operation = null;

        int separatorIndex = pathInfo.indexOf("/", 1);
        if (separatorIndex == -1) {
            operation = pathInfo.substring(1);
        }
        else {
            operation = pathInfo.substring(1, separatorIndex);
        }

        return (Command) commands.get(operation);
    }
}