package net.zdsoft.eis.system.frame.web.ucenter;

import java.io.IOException;
import java.io.InputStream;

import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.constant.UCenterConstant;
import net.zdsoft.keelcnet.config.ContainerManager;
import net.zdsoft.leadin.cache.CacheCall.CacheObjectParam;
import net.zdsoft.leadin.cache.SimpleCacheManager;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 鼎永uc
 * 
 * @author weixh
 * @since 2017-8-15 下午3:57:25
 */
public class UcWebServiceClient {
	private static String ucToken;
	private static String ucHost;
	private SimpleCacheManager simpleCacheManager;
	
	private Log log = LogFactory.getLog(UcWebServiceClient.class);
	
	public void init(){
		if(StringUtils.isEmpty(ucToken)){
			SystemIniService systemIniService = (SystemIniService) ContainerManager.getComponent("systemIniService");
			ucToken = systemIniService.getValue(UCenterConstant.SYSTEM_UCENTER_UCTOKEN);
			ucHost = systemIniService.getValue(UCenterConstant.SYSTEM_UCENTER_URL);
			simpleCacheManager = (SimpleCacheManager) ContainerManager.getComponent("simpleCacheManager");
		}
	}
	
	public static UcWebServiceClient getInstance(){
		UcWebServiceClient client = new UcWebServiceClient();
		client.init();
		return client;
	}
	
	/**
	 * 获取accessToken
	 * @param username
	 * @return
	 */
	public String getAccessToken(final String username) {
		String acToken = simpleCacheManager
				.getObjectFromCache(new CacheObjectParam<String>() {
					public String fetchObject() {
						return null;
					}

					public String fetchKey() {
						return username
								+ UCenterConstant.ACCESSTOKEN_CACHE_NAME;
					}
				});
		if(acToken == null){
			InputStream is = null;
			HttpClient client = new HttpClient();
			PostMethod pmethod = new PostMethod(ucHost
					+ UCenterConstant.GET_ACCESSTOKEN_PATH);
			pmethod.setRequestHeader("Host", ucHost);
			pmethod.setRequestHeader("Content-Type",
					"application/x-www-form-urlencoded");
			pmethod.setParameter("Token", ucToken);
			pmethod.setParameter("Username", username);
			try {
				client.executeMethod(pmethod);
				is = pmethod.getResponseBodyAsStream();
				SAXReader saxreader = new SAXReader();
				Document doc = saxreader.read(is);
				Element re = doc.getRootElement();
				if ("string".equals(re.getName())) {
					acToken = re.getText();
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			} finally {
				pmethod.releaseConnection();
				try {
					if (is != null) {
						is.close();
					}
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
			simpleCacheManager.put(username
									+ UCenterConstant.ACCESSTOKEN_CACHE_NAME, acToken, 3000);
		}
		return acToken;
	}
	
	/**
	 * 修改用户手机号
	 * @param username
	 * @param tel
	 * @return
	 */
	public boolean updateTel(String username, String tel){
		String acctoken = getAccessToken(username);
		if(StringUtils.isEmpty(acctoken)){
			log.error("用户["+username+"]获取AccessToken失败！");
			return false;
		}
		String result = "";
		InputStream is = null;
		HttpClient client = new HttpClient();
		PostMethod pmethod = new PostMethod(
				ucHost + UCenterConstant.UPDATE_TELNUM);
		pmethod.setRequestHeader("Host", ucHost);
		pmethod.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
		pmethod.setParameter("Token", ucToken);
		pmethod.setParameter("AccessToken", acctoken);
		pmethod.setParameter("telNum", tel);
		try {
			client.executeMethod(pmethod);
			is = pmethod.getResponseBodyAsStream();
			SAXReader saxreader = new SAXReader();  
			Document doc = saxreader.read(is);
			Element re = doc.getRootElement();
			if("string".equals(re.getName())){
				result = re.getText();
				if(UCenterConstant.UPDATETEL_SUCCESS_RESULT_CODE.equals(result)){
					return true;
				} else {
					log.error("修改手机号失败："+UCenterConstant.getErrorResultMsg(result));
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			pmethod.releaseConnection();
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
		return false;
	}
	
	public static void main(String[] args) throws Exception {  
        // sjpt.bjqjyj.cn:8081 
        String url = "http://zhjy.bjqjyj.cn:8081/webapi/UCenter.asmx";
        String method = "GetAccessToken";
        String pam1 = "Token";
        String pam2 = "Username";
//        call.setTargetEndpointAddress(new URL(url));  //设置要调用的接口地址以上一篇的为例子  
//        call.setOperationName(new QName(method));  //设置要调用的接口方法  
//        call.addParameter("Token", org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN);//设置参数名 id  第二个参数表示String类型,第三个参数表示入参  
//        call.addParameter("Username", org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN);//设置参数名 id  第二个参数表示String类型,第三个参数表示入参  
//        call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);//返回参数类型  
        //开始调用方法,假设我传入的参数id的内容是1001   调用之后会根据id返回users信息，以xml格式的字符串返回，也可以json格式主要看对方用什么方式返回  
        String token = "70AF0CE93F8ADA82F097775837920B580B83EB8B7CA988739074805B1FDBB37EA28A1047DA53B5C46FBAA5BE640D697E4774477D60DCF55A9A30FA6AE292B160";
        String username="bj_qjc134522";
//        String tel = "13412345678";
        InputStream is = null;
		HttpClient client = new HttpClient();
		PostMethod pmethod = new PostMethod(
				url +"/"+method);
		pmethod.setRequestHeader("Host", "http://zhjy.bjqjyj.cn:8081");
		pmethod.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
		pmethod.setParameter(pam1, token);
		pmethod.setParameter(pam2, username);
		try {
			client.executeMethod(pmethod);
			is = pmethod.getResponseBodyAsStream();
			SAXReader saxreader = new SAXReader();  
			Document doc = saxreader.read(is);
			Element re = doc.getRootElement();
			if("string".equals(re.getName())){
				System.out.println(re.getText());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pmethod.releaseConnection();
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }

}
