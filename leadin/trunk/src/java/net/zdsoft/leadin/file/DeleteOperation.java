/*
 * Created on 2004-8-20
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.zdsoft.leadin.file;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author liangxiao
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DeleteOperation extends FileOperation {

    public DeleteOperation() {
    }

    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        Map<String, String> filePaths = getParameters(request, "filePath");
        Map<String, String> realNames = getParameters(request, "realName");

        String[] keys = getKeys(realNames);
        for (int i = 0; i < keys.length; i++) {
            String realName = (String) realNames.get(keys[i]);
            String filePath = (String) filePaths.get(keys[i]);

            if (!isValidName(realName)) {
                continue;
            }

            String fileName = getRootDir() + (filePath == null ? "" : filePath)
                    + File.separator + realName;

            File file = new File(fileName);

            if (file.exists()) {
                file.delete();
                println(out, keys[i], true);
            }
            else {
                println(out, keys[i], false);
            }
        }

        out.flush();
        out.close();
    }
}