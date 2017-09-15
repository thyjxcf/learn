package net.zdsoft.eis.frame.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.zdsoft.eis.base.common.entity.CurrentSemester;
import net.zdsoft.eis.base.common.entity.Student;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.SemesterService;
import net.zdsoft.eis.base.common.service.StudentService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.frame.client.LoginInfo;
import net.zdsoft.eis.frame.client.StuLoginInfo;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keel.util.URLUtils;

import org.apache.commons.lang.StringUtils;

public class StuPlatformBaseAction extends BaseActionSupport {

    private static final long serialVersionUID = 2255650783478871112L;

    private String modID;// 模块id

    protected String id;

    private StuLoginInfo stuLoginInfo;// 学生信息
    protected StudentService studentService;// 学生信息

    public StuLoginInfo getStuLoginInfo() {
        // if (stuLoginInfo == null) {
        // stuLoginInfo = (StuLoginInfo) this
        // .getSession(StuPlatformConstant.SESSION_STUINFO);
        // }
        if (stuLoginInfo == null) {
            LoginInfo loginInfo = (LoginInfo) this.getSession(BaseConstant.SESSION_LOGININFO);
            User user = loginInfo.getUser();
            // 如果为学生登录
            if (user.getOwnerType() == User.STUDENT_LOGIN) {
                String ownerId = user.getTeacherid();
                Student stuDto = studentService.getStudent(ownerId);
                stuLoginInfo = new StuLoginInfo(stuDto, loginInfo.getUnitName(), loginInfo
                        .getUnitPartitionNum());
                setSession(BaseConstant.SESSION_STUINFO, stuLoginInfo);
            }

        }
        return stuLoginInfo;
    }

    public String getModID() {
        if (modID == null)
            modID = "";
        return modID;
    }

    public void setModID(String modID) {
        this.modID = modID;
    }

    public String getUnitId() {
        return getStuLoginInfo().getStudentInfo().getSchid();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    // ========================学年学期=======================
    protected SemesterService semesterService;

    protected String acadyear;
    protected String semester;

    /**
     * 学年列表
     */
    public List<String> getAcadyearList() {
        return semesterService.getAcadyears();
    }

    public CurrentSemester getCurrentSemester() {
        return semesterService.getCurrentSemester();
    }

    /**
     * @return the curAcadyear
     */
    public String getAcadyear() {
        if (StringUtils.isEmpty(acadyear)) {
            CurrentSemester sem = getCurrentSemester();
            if (sem != null)
                acadyear = getCurrentSemester().getAcadyear();
            else
                acadyear = "";
        }
        return acadyear;
    }

    /**
     * @return the curSemester
     */
    public String getSemester() {
        if (StringUtils.isEmpty(semester)) {
            CurrentSemester sem = getCurrentSemester();
            if (sem != null)
                semester = getCurrentSemester().getSemester();
            else
                semester = "";
        }
        return semester;
    }

    public void setAcadyear(String acadyear) {
        this.acadyear = acadyear;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setSemesterService(SemesterService semesterService) {
        this.semesterService = semesterService;
    }
       
    // ========================分页=======================
    // 分页的html内容
//    private String htmlOfPagination;
	private String htmlOfPaginationLoad;
	private String htmlOfPaginationDispatch;

    private static final int DEFAULT_PAGE_SIZE = 20;

    private Pagination page = null;

    public StuPlatformBaseAction() {
        // 默认不使用游标
        page = new Pagination(DEFAULT_PAGE_SIZE, false);
    }

    public Pagination getPage() {
        boolean m = false;
        if (page.getPageIndex() == 0)
            page.setPageIndex(1);
        if(page.getMaxRowCount() == 0){
            page.setMaxRowCount(1);
            m = true;
        }
        //page.initialize(); //注释by chenzy 在basicDAO调用该方法比较合适.
        if (m){
            page.setMaxRowCount(0);
        }
        return page;
    }

    @SuppressWarnings("unchecked")
    public List getPageList(List rs) {
        page = getPage();
        page.setMaxRowCount(rs.size());
        page.initialize();
        List results = new ArrayList();
        if (page.getMaxRowCount() > 0) {
            int fromIndex = (page.getPageIndex() - 1) * page.getPageSize();
            int toIndex = page.getPageIndex() * page.getPageSize();
            if(toIndex+1>=page.getMaxRowCount())
                toIndex=page.getMaxRowCount();
            results.addAll(rs.subList(fromIndex, toIndex));
        }

        return results;
    }
    
    public void setPageIndex(int pageIndex) {
        page.setPageIndex(pageIndex);
    }

    public int getPageIndex() {
        return page.getPageIndex();
    }

    public void setPageSize(int pageSize){
    	page.setPageSize(pageSize);
    }
    
    public void setUseCursor(boolean isUseCursor) {
        page.setUseCursor(isUseCursor);
    }

    /**
     * @return the htmlOfPagination
     */
    @SuppressWarnings("unchecked")
//    public String getHtmlOfPagination() {
//        HttpServletRequest request = getRequest();
//        String uri = request.getRequestURI();
//        String actionName = "";
//        if (uri != null && !uri.equals("")) {
//            int pos = uri.lastIndexOf("/");
//            if (pos < 0) {
//                pos = uri.lastIndexOf("\\");
//            }
//            if (pos >= 0 && pos != uri.length() - 1) {
//                actionName = uri.substring(pos + 1, uri.length());
//            }
//        }
//        // 因为从request中取得的这个map是被锁定的,不能操作,所以另定义一个
//        Map<String, String> mapOfParameter = request.getParameterMap();
//        Map<String, String> mapOfParameterOpe = new HashMap<String, String>();
//        mapOfParameterOpe.putAll(mapOfParameter);
//        if (mapOfParameterOpe.containsKey("pageIndex"))
//            mapOfParameterOpe.remove("pageIndex");
//        String[] keys = mapOfParameterOpe.keySet().toArray(new String[0]);
//        Object[] values = new Object[0];
//        if (keys != null) {
//            values = new Object[keys.length];
//            for (int i = 0; i < keys.length; i++) {
//                values[i] = mapOfParameterOpe.get(keys[i]);
//            }
//        }
//        String url = URLUtils.addQueryString(actionName, keys, values);
//        try{
//        htmlOfPagination = PageUtils.pagination(url, getPage(), true, request
//                .getContextPath() 
//                + "/static/images/table");
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (null == htmlOfPagination)
//            htmlOfPagination = "";
//        return htmlOfPagination;
//    }
    public String getHtmlOfPaginationDispatch() {
		if(htmlOfPaginationDispatch != null)
			return htmlOfPaginationDispatch;
		
		HttpServletRequest request = getRequest();
		String uri = request.getRequestURI();
		String actionName = "";
		if (uri != null && !uri.equals("")) {
			int pos = uri.lastIndexOf("/");
			if (pos < 0) {
				pos = uri.lastIndexOf("\\");
			}
			if (pos >= 0 && pos != uri.length() - 1) {
				actionName = uri.substring(pos + 1, uri.length());
			}
		}
		// 因为从request中取得的这个map是被锁定的,不能操作,所以另定义一个
		Map mapOfParameter = request.getParameterMap();
		Map<String, String> mapOfParameterOpe = new HashMap<String, String>();
		mapOfParameterOpe.putAll(mapOfParameter);
		if (mapOfParameterOpe.containsKey("pageIndex")) {
			mapOfParameterOpe.remove("pageIndex");
		}
		if (mapOfParameterOpe.containsKey("pageSize")) {
			mapOfParameterOpe.remove("pageSize");
		}
		
		String[] keys = mapOfParameterOpe.keySet().toArray(new String[0]);
		Object[] values = new Object[0];
		if (keys != null) {
			values = new Object[keys.length];
			for (int i = 0; i < keys.length; i++) {
				values[i] = mapOfParameterOpe.get(keys[i]);
			}
		}
		String url = URLUtils.addQueryString(actionName, keys, values);
		htmlOfPaginationDispatch = PageUtils.paginationDispatch(url, page);
		if (null == htmlOfPaginationDispatch)
			htmlOfPaginationDispatch = "";
		return htmlOfPaginationDispatch;
	}

	public String getHtmlOfPaginationLoad() {
		if (htmlOfPaginationLoad != null)
			return htmlOfPaginationLoad;
		
		HttpServletRequest request = getRequest();
		String uri = request.getRequestURI();
		String actionName = "";
		if (uri != null && !uri.equals("")) {
			int pos = uri.lastIndexOf("/");
			if (pos < 0) {
				pos = uri.lastIndexOf("\\");
			}
			if (pos >= 0 && pos != uri.length() - 1) {
				actionName = uri.substring(pos + 1, uri.length());
			}
		}
		// 因为从request中取得的这个map是被锁定的,不能操作,所以另定义一个
		Map mapOfParameter = request.getParameterMap();
		Map<String, String> mapOfParameterOpe = new HashMap<String, String>();
		mapOfParameterOpe.putAll(mapOfParameter);
		if (mapOfParameterOpe.containsKey("pageIndex")) {
			mapOfParameterOpe.remove("pageIndex");
		}
		if (mapOfParameterOpe.containsKey("pageSize")) {
			mapOfParameterOpe.remove("pageSize");
		}
		
		String[] keys = mapOfParameterOpe.keySet().toArray(new String[0]);
		Object[] values = new Object[0];
		if (keys != null) {
			values = new Object[keys.length];
			for (int i = 0; i < keys.length; i++) {
				values[i] = mapOfParameterOpe.get(keys[i]);
			}
		}
		String url = URLUtils.addQueryString(actionName, keys, values);
		htmlOfPaginationLoad = PageUtils.paginationLoad(url, page);
		if (null == htmlOfPaginationLoad)
			htmlOfPaginationLoad = "";
		return htmlOfPaginationLoad;
	}

}
