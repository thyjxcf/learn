package net.zdsoft.eis.support.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import net.zdsoft.eis.base.common.entity.Region;
import net.zdsoft.eis.base.common.service.McodeService;
import net.zdsoft.eis.base.common.service.RegionService;
import net.zdsoft.eis.support.entity.RecommendSchool;
import net.zdsoft.eis.support.page.PageAction;
import net.zdsoft.eis.support.service.RecommendSchoolService;
import net.zdsoft.keel.util.StringUtils;
import net.zdsoft.keelcnet.config.BootstrapManager;
import net.zdsoft.keelcnet.util.GUIdGenerator;

/* 
 * <p>ZDSoft数字校园（support）</p>
 * 
 * @author Cuibz 2007/08/18
 */

public class RecommendSchoolAction extends PageAction {

    private static final long serialVersionUID = 1L;

    private List<RecommendSchool> listOfRecommendSchool;

    private RecommendSchool recommendSchool;

    private RecommendSchoolService recommendSchoolService;

    private McodeService mcodeService;

    private String schoolId; // 学校ID

    private String schoolName; // 学校名称

    private String schoolHomepage; // 学校主页

    private String schoolBlog; // 学校博客

    private String schoolType; // 学校类型

    private String schoolAddress; // 学校地址

    private String schoolPostcode; // 学校邮政编码

    private String schoolRegionCode; // 学校区域编码

    private int schoolRecommend; // 学校推荐状态

    private String schoolPhone; // 学校电话

    private String schoolFax; // 学校传真

    private String schoolIntroduction; // 学校介绍

    private String schoolPictureUrl; // 学校图片链接

    private String schoolPictureTempUrl;

    private String schoolEmail;

    private String tempPic;

    private RegionService regionService;

    private String regionName;

    private String saveMessage;

    private static final int ROW_COUNT_ON_PAGE = 9;

    /*
     * 根据区域编码显示推荐学校名称,日期列表
     */
    @SuppressWarnings("unchecked")
    public String showRecommendSchoolList() {

        String regionCodeOri = schoolRegionCode;
        if (schoolRegionCode != null) {
            if (schoolRegionCode.length() > 6) {
                schoolRegionCode = schoolRegionCode.substring(0, 6);
            } else {
                for (int i = schoolRegionCode.length(); i < 6; i++) {
                    schoolRegionCode = schoolRegionCode + "0";
                }
            }
        }
        Region region = regionService.getRegion(regionCodeOri);
        listOfRecommendSchool = recommendSchoolService.getRecommendSchoolList(regionCodeOri + "%",
                getPage(19));
        if (region != null) {
            regionName = region.getRegionName();
        }

        Map<String, String> mapOfMCode = mcodeService.getMcode("DM-XXLB").getCodeMap();
        for (int i = 0; i < listOfRecommendSchool.size(); i++) {
            String typeText = mapOfMCode.get(listOfRecommendSchool.get(i).getSchoolType());
            Date recommendDate = listOfRecommendSchool.get(i).getSchoolRecommendDate();
            listOfRecommendSchool.get(i).setSchoolRecommendDateText(DoFormatDate(recommendDate)); // 日期格式转换
            listOfRecommendSchool.get(i).setSchoolTypeText(typeText);
            listOfRecommendSchool.get(i).setSchoolFullName(
                    listOfRecommendSchool.get(i).getSchoolName());
            listOfRecommendSchool.get(i).setSchoolName(
                    StringUtils.cutOut(listOfRecommendSchool.get(i).getSchoolName(), 14, "..."));
        }
        return SUCCESS;
    }

    /*
     * 显示全部的学校信息列表
     */
    public String showAllSchoolInfoList() {

        listOfRecommendSchool = recommendSchoolService.getAllSchool();
        if (listOfRecommendSchool != null) {
            for (int i = 0; i < listOfRecommendSchool.size(); i++) {
                String typeText = mcodeService.getMcodeContext("DM-XXLB", listOfRecommendSchool
                        .get(i).getSchoolType());
                listOfRecommendSchool.get(i).setSchoolTypeText(typeText);
            }
        }
        return SUCCESS;
    }

    /*
     * 根据学校名称进行查询，获得学校列表
     */
    public String getSchoolsBySchName() {
        listOfRecommendSchool = recommendSchoolService.getSchools(schoolName, getPage(23));
        if (listOfRecommendSchool != null && listOfRecommendSchool.size() > 0) {
            for (int i = 0; i < listOfRecommendSchool.size(); i++) {
                listOfRecommendSchool.get(i)
                        .setSchoolName(
                                StringUtils.cutOut(listOfRecommendSchool.get(i).getSchoolName(),
                                        30, "..."));
            }
        }
        return SUCCESS;
    }

    /*
     * 显示全部的推荐学校列表,首页显示
     */
    public String showAllRecommendSchoolList() {
        listOfRecommendSchool = recommendSchoolService.getAllRecommendSchoolList(ROW_COUNT_ON_PAGE);

        for (int i = 0; i < listOfRecommendSchool.size(); i++) {
            String typeText = mcodeService.getMcodeContext("DM-XXLB", listOfRecommendSchool.get(i)
                    .getSchoolType());
            Date recommendDate = listOfRecommendSchool.get(i).getSchoolRecommendDate();
            listOfRecommendSchool.get(i).setSchoolRecommendDateText(DoFormatDate(recommendDate)); // 日期格式转换
            listOfRecommendSchool.get(i).setSchoolTypeText(typeText);
            listOfRecommendSchool.get(i).setSchoolName(
                    StringUtils.cutOut(listOfRecommendSchool.get(i).getSchoolName(), 22, "..."));
        }
        return SUCCESS;
    }

    /*
     * 显示推荐学校的详细信息
     */
    public String showRecommendSchoolInfo() {

        if (schoolId != null && !"".equals(schoolId)) {
            recommendSchool = recommendSchoolService.getRecommendSchoolInfo(schoolId);
        } else if (schoolName != null && !"".equals(schoolName)) {
            listOfRecommendSchool = recommendSchoolService.getSchools(schoolName, getPage(5));
        }

        if (recommendSchool == null) {
            recommendSchool = new RecommendSchool();
        }

        String typeText = mcodeService.getMcodeContext("DM-XXLB", recommendSchool.getSchoolType());
        if (recommendSchool.getSchoolRecommendDate() != null)
            recommendSchool.setSchoolRecommendDateText(DoFormatDate(recommendSchool
                    .getSchoolRecommendDate())); // 日期格式转换
        recommendSchool.setSchoolTypeText(typeText);
        recommendSchool.setSchoolName(StringUtils
                .cutOut(recommendSchool.getSchoolName(), 28, "..."));
        // 如没有图片，添加默认图片
        if (recommendSchool.getSchoolPictureUrl() == null
                || recommendSchool.getSchoolPictureUrl().length() < 8) {
            recommendSchool.setSchoolPictureUrl("images/noUploadPic.jpg");
        } else {
            recommendSchool.setSchoolPictureUrl("\\store\\recommend_school\\"
                    + recommendSchool.getSchoolPictureUrl());
        }

        return SUCCESS;
    }

    /*
     * 显示修改推荐学校的详细信息
     */
    public String showUpdateRecSchInfo() {

        if (schoolId != null && !"".equals(schoolId)) {
            recommendSchool = recommendSchoolService.getRecommendSchoolInfo(schoolId);
        } else if (schoolName != null && !"".equals(schoolName)) {
            listOfRecommendSchool = recommendSchoolService.getSchools(schoolName, getPage(5));
        }

        if (recommendSchool == null) {
            recommendSchool = new RecommendSchool();
        }

        String typeText = mcodeService.getMcodeContext("DM-XXLB", recommendSchool.getSchoolType());
        if (recommendSchool.getSchoolRecommendDate() != null)
            recommendSchool.setSchoolRecommendDateText(DoFormatDate(recommendSchool
                    .getSchoolRecommendDate())); // 日期格式转换
        recommendSchool.setSchoolTypeText(typeText);
        recommendSchool.setSchoolName(StringUtils
                .cutOut(recommendSchool.getSchoolName(), 28, "..."));
        // 如没有图片，添加默认图片
        if (recommendSchool.getSchoolPictureUrl() == null
                || recommendSchool.getSchoolPictureUrl().length() < 8) {
            recommendSchool.setSchoolPictureUrl("images/noUploadPic.jpg");
        } else {
            recommendSchool.setSchoolPictureUrl("\\store\\recommend_school\\"
                    + recommendSchool.getSchoolPictureUrl());
        }

        return SUCCESS;
    }

    /*
     * 修改推荐学校的详细信息
     */
    public String updateRecommendSchoolInfo() {

        String picType = schoolPictureTempUrl.substring(schoolPictureTempUrl.lastIndexOf("."));
        if (schoolPictureUrl == null || "".equals(schoolPictureUrl)) {
            schoolPictureUrl = tempPic;
        }
        String SreverPicUrl = BootstrapManager.getStoreHome() + "\\recommend_school\\";
        // 上传图片
        try {
            recommendSchoolService.picUpload(getRequest(), schoolId.toUpperCase(), SreverPicUrl);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        schoolPictureUrl = schoolId.trim().toUpperCase() + picType.trim().toLowerCase(); // 设置数据库保存的图片连接
        recommendSchoolService.updateRecommendSchoolInfo(setUpdateRecommendParamtes());

        recommendSchool = new RecommendSchool();
        recommendSchool.setSchoolId(schoolId);

        return SUCCESS;
    }

    /*
     * 修改学校的推荐状态
     */
    public String updateRecommendState() {
        if (!"".equals(schoolId) && schoolId != null) {
            String[] schoolIdparametes = schoolId.split(", ");
            recommendSchoolService.updateSchoolRecommendState(schoolIdparametes);
            return SUCCESS;
        } else {
            recommendSchoolService.updateSchoolRecommendState(new String[] {});
            return SUCCESS;
        }
    }

    /*
     * 添加新推荐学校
     */
    public String addRecommendSchool() {

        List<RecommendSchool> tempList = recommendSchoolService.getSchool(schoolName.trim());// 检验学校是否已经添加
        if (tempList.size() == 0) {
            schoolId = GUIdGenerator.getUUID().toUpperCase(); // 图片名称大写
            String picType = schoolPictureTempUrl.substring(schoolPictureTempUrl.lastIndexOf("."));
            String SreverPicUrl = BootstrapManager.getStoreHome() + "\\recommend_school\\";
            // 上传图片
            try {
                recommendSchoolService.picUpload(getRequest(), schoolId, SreverPicUrl);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            schoolPictureUrl = schoolId.trim().toUpperCase() + picType.trim().toLowerCase(); // 设置数据库保存的图片连接
            recommendSchoolService.addRecommendSchool(setAddRecommendParamtes());
            saveMessage = schoolName + " 保存成功";
            return SUCCESS;
        } else {
            saveMessage = "保存失败： " + schoolName + " 此学校已经被添加";
            return SUCCESS;
        }

    }

    public void setSchId(String schoolId) {
        this.schoolId = schoolId;
    }

    public void setSchoolRegionCode(String schoolRegionCode) {
        this.schoolRegionCode = schoolRegionCode;
    }

    public void setRecommendSchoolService(RecommendSchoolService recommendSchoolService) {
        this.recommendSchoolService = recommendSchoolService;
    }

    /*
     * 日期格式转换
     */
    public String DoFormatDate(java.util.Date dt_in) {
        if (dt_in == null) {
            dt_in = new Date();
        }
        return (new SimpleDateFormat("yyyy/MM/dd")).format(dt_in);
    }

    /*
     * 设置修改推荐学校详细信息的参数数组 updateRecommendSchoolInfo()方法调用此方法
     */
    public Object[] setUpdateRecommendParamtes() {
        byte[] schInfo = schoolIntroduction.getBytes();
        if (schInfo.length > 1000) {
            schoolIntroduction = new String(schInfo, 0, 999);
        }
        return new Object[] { schoolName.trim(), schoolHomepage, schoolBlog, schoolType,
                schoolAddress, schoolPostcode, schoolPhone, schoolFax, schoolIntroduction,
                schoolRegionCode, schoolPictureUrl, schoolEmail, schoolId };
    }

    public Object[] setAddRecommendParamtes() {
        byte[] schInfo = schoolIntroduction.getBytes();
        if (schInfo.length > 1000) {
            schoolIntroduction = new String(schInfo, 0, 999);
        }
        return new Object[] { schoolId, schoolName.trim(), schoolHomepage, schoolBlog, schoolType,
                schoolAddress, schoolPostcode, schoolPhone, schoolFax, schoolIntroduction,
                schoolRegionCode, schoolPictureUrl, schoolEmail };
    }

    public List<RecommendSchool> getListOfRecommendSchool() {
        if (null == listOfRecommendSchool)
            listOfRecommendSchool = new ArrayList<RecommendSchool>();
        return listOfRecommendSchool;
    }

    public void setMcodeService(McodeService mcodeService) {
        this.mcodeService = mcodeService;
    }

    public RecommendSchool getRecommendSchool() {
        return recommendSchool;
    }

    public void setRecommendSchool(RecommendSchool recommendSchool) {
        this.recommendSchool = recommendSchool;
    }

    public void setSchoolAddress(String schoolAddress) {
        this.schoolAddress = schoolAddress;
    }

    public void setSchoolBlog(String schoolBlog) {
        this.schoolBlog = schoolBlog;
    }

    public void setSchoolFax(String schoolFax) {
        this.schoolFax = schoolFax;
    }

    public void setSchoolHomepage(String schoolHomepage) {
        this.schoolHomepage = schoolHomepage;
    }

    public void setSchoolIntroduction(String schoolIntroduction) {
        // 对获得的字符串长度进行调整
        if (schoolIntroduction.length() > 500) {
            schoolIntroduction = schoolIntroduction.substring(0, 500);
        }
        this.schoolIntroduction = schoolIntroduction;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public void setSchoolPhone(String schoolPhone) {
        this.schoolPhone = schoolPhone;
    }

    public void setSchoolPostcode(String schoolPostcode) {
        this.schoolPostcode = schoolPostcode;
    }

    public void setSchoolType(String schoolType) {
        this.schoolType = schoolType;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public void setSchoolRecommend(int schoolRecommend) {
        this.schoolRecommend = schoolRecommend;
    }

    public int getSchoolRecommend() {
        return schoolRecommend;
    }

    public void setRegionService(RegionService regionService) {
        this.regionService = regionService;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getSchoolPictureUrl() {
        return schoolPictureUrl;
    }

    public void setSchoolPictureUrl(String schoolPictureUrl) {
        this.schoolPictureUrl = schoolPictureUrl;
    }

    public String getTempPic() {
        return tempPic;
    }

    public void setTempPic(String tempPic) {
        this.tempPic = tempPic;
    }

    public String getSchoolEmail() {
        return schoolEmail;
    }

    public void setSchoolEmail(String schoolEmail) {
        this.schoolEmail = schoolEmail;
    }

    public String getSchoolPictureTempUrl() {
        return schoolPictureTempUrl;
    }

    public void setSchoolPictureTempUrl(String schoolPictureTempUrl) {
        this.schoolPictureTempUrl = schoolPictureTempUrl;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public String getSchoolRegionCode() {
        return schoolRegionCode;
    }

    public String getSaveMessage() {
        return saveMessage;
    }

    public void setSaveMessage(String saveMessage) {
        this.saveMessage = saveMessage;
    }

}
