package net.zdsoft.base.data.service;

import java.util.UUID;

import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.passport.PassportClient;
import net.zdsoft.passport.PassportClientParam;
import net.zdsoft.passport.entity.Account;
import net.zdsoft.passport.exception.PassportException;

public class PassportTest {

    private static final String url = "http://192.168.1.183";

    // 开发：125的库hunan
    private static final int serverId = 54301;
    private static final String verifyKey = "74JXR17HYRSPF3NYLL3VNZ6ZVHZ9PNN4";

    // 测试：141的库hebei
    // private static final int serverId= 51301;
    // private static final String verifyKey = "4028809F1FA7A327011FA7A3278B0000";

    public static void main(String[] args) throws PassportException {

        System.out.println("begin...");
        initPassport();

        // queryAccount();
        passportTest();
        // modifyAccount();
        // addAccount();
        System.out.println("end...");
    }

    private static void initPassport() {
        PassportClientParam p0 = new PassportClientParam(url, serverId, verifyKey);

        PassportClient.getInstance().init(p0);
    }

    public static void queryAccount() {
        try {

            Account account = PassportClient.getInstance().queryAccount(
                    "4028819020EA72A30120EAAE36F50008");
            System.out.println("username:" + account.getUsername() + ";regionid:"
                    + account.getRegionId() + ";register time:" + account.getRegisterTime()
                    + ";user state:" + account.getState() + ";real name:" + account.getRealName()
                    + ";accountId:" + account.getId());

        } catch (PassportException e) {

            e.printStackTrace();
        }

    }

    public static void passportTest() {

        try {
            Account account = PassportClient.getInstance().queryAccountByUsername("yysch2");
            System.out.println("username:" + account.getUsername() + ";regionid:"
                    + account.getRegionId() + ";register time:" + account.getRegisterTime()
                    + ";user state:" + account.getState() + ";real name:" + account.getRealName()
                    + ";accountId:" + account.getId() + "sequence:" + account.getSequence());

        } catch (PassportException e) {

            e.printStackTrace();
        } finally {
            PassportClient.getInstance().shutdown();

        }
    }

    public static void modifyAccount() throws PassportException {

        Account account = PassportClient.getInstance().queryAccountByUsername("hnedutop");
        account.setRealName("湖南省教育厅");
        account.setRegionId("430000");
        account.setSchoolId("1430100001");
        PassportClient.getInstance().modifyAccount(account,
                new String[] { "realName", "regionId", "schoolId" });
    }

    public static void addAccount() {

        Account account = new Account();
        account.setRegionId("430000");
        account.setSchoolId("1430100001");
        account.setType(12);
        account.setFixedType(2);
        account.setId("40288099200D42BE01200D42BED22222");
        account.setUsername("hnedutop");
        account.setPassword("12345678");
        account.setRealName("湖南省教育厅");
        account.setRegisterTime(DateUtils.string2Date("2009-04-17"));
        account.setState(1);
        account.setSex(1);

        Account retAccount = null;
        try {
            retAccount = PassportClient.getInstance().addAccount(account);
        } catch (PassportException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (retAccount != null) {
            System.out.println(retAccount.getSequence());
        }
        PassportClient.getInstance().shutdown();
    }

    public static String getGUID() {

        return UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
    }

    // public static void addAccount() {
    //		
    // Account account = new Account();
    // account.setRegionId("130000");
    // account.setSchoolId("1130100001");
    // account.setType(12);
    // account.setFixedType(2);
    // account.setId("40288099200D42BE01200D42BED11111");
    // account.setUsername("hebeitop");
    // account.setPassword("12345678");
    // account.setRealName("河北教育局");
    // account.setRegisterTime(DateUtils.string2Date("2009-04-15"));
    // account.setState(1);
    // account.setSex(1);
    //
    // Account retAccount = null;
    // try {
    // retAccount = PassportClient.getInstance().addAccount(account);
    // } catch (PassportException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // if (retAccount != null) {
    // System.out.println(retAccount.getSequence());
    // }
    // PassportClient.getInstance().shutdown();
    // }

}
