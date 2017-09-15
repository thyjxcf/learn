package net.zdsoft.eis.sms.dao;

import net.zdsoft.eis.sms.entity.SmsBatch;
import net.zdsoft.eis.sms.entity.SmsSend;

public class SmsDaoTest extends OfficeDaoTestCase {

    private SmsSendDao smsSendDao;

    private SmsBatchDao smsBatchDao;

    public void setSmsSendDao(SmsSendDao smsSendDao) {
        this.smsSendDao = smsSendDao;
    }

    public void setSmsBatchDao(SmsBatchDao smsBatchDao) {
        this.smsBatchDao = smsBatchDao;
    }

    public void testInsertSms() {
        SmsBatch batch = new SmsBatch();
        batch.setUserId("402881fa2138ba46012139392cbb0400");
        batch.setTaskId(null);
        batch.setDepId("402881fa2138ba46012139392cbb0400");
        batch.setUnitId("402881fa2138ba46012139392cbb0400");
        batch.setSendDate("20090423");
        batch.setContent("测试短信1");
        batch.setSmsType("6");
        batch.setSendHour(null);
        batch.setSendMinutes(null);
        batch.setFeastDayname(null);
        batch.setDep("部门名称");
        batch.setUserName("用户名");
        batch.setStatus("0");
        batch.setStatusdesc("等待发送");

        for (int i = 0; i < 2; i++) {
            SmsSend send = new SmsSend();
            send.setId("0000000000000000000000000000000" + i);
            send.setUnitId("402881fa2138ba46012139392cbb0400");
            send.setReceiverId("402881fa2138ba46012139392cbb0400");
            send.setAccountId("402881fa2138ba46012139392cbb0400");
            send.setStatus("22");
            send.setMobile("13967112926");
            send.setReceiverName("好人" + i);
            send.setStatusdesc("成功");
            send.setReceiverType(2);
            send.setItemCount(1);
            send.setBusinessType(1);
            batch.addSmsDetailEntity(send);
        }

        for (SmsSend send : batch.getSendSet()) {
            send.setId(null);
        }

        smsBatchDao.save(batch);
        // this.setComplete();

    }

    public void testGetSmsSend() {
        SmsSend send = smsSendDao.getSmsSendById("4028819021C3FFCD0121C3FFCE0D0001");
        assertEquals("13967112926", send.getMobile());
    }

    public void testUpdateSmsSend() {
        SmsSend send = smsSendDao.getSmsSendById("4028819021C3FFCD0121C3FFCE0D0001");
        send.setMobile("13967112927");
        smsSendDao.update(send);
        assertEquals("13967112927", send.getMobile());
    }

    public void testDeleteSmsSend() {
        SmsSend send = smsSendDao.getSmsSendById("4028819021C3FFCD0121C3FFCE0D0001");
        smsSendDao.remove(send);
        send = smsSendDao.getSmsSendById("4028819021C3FFCD0121C3FFCE0D0001");
        assertEquals(null, send);
    }

    public void testGetSmsBatch() {
        SmsBatch batch = smsBatchDao.getSmsBatchById("4028819021C3FFCD0121C3FFCDFE0000");
        assertEquals("测试短信1", batch.getContent());
    }

    public void testUpdateSmsBatch() {
        SmsBatch batch = smsBatchDao.getSmsBatchById("4028819021C3FFCD0121C3FFCDFE0000");
        batch.setContent("测试短信2");
        smsBatchDao.update(batch);
        assertEquals("测试短信2", batch.getContent());
    }

    public void testDeleteSmsBatch() {
        SmsBatch batch = smsBatchDao.getSmsBatchById("4028819021C3FFCD0121C3FFCDFE0000");
        smsBatchDao.remove(batch);
        batch = smsBatchDao.getSmsBatchById("4028819021C3FFCD0121C3FFCDFE0000");
        assertEquals(null, batch);
    }

}
