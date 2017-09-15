package net.zdsoft.eis.system.frame.web;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang3.StringEscapeUtils;

public final class NewXssHttpServletRequestWrapper extends
		HttpServletRequestWrapper {

	public NewXssHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	@Override
	public String getHeader(String name) {
		return StringEscapeUtils.escapeHtml4(super.getHeader(name));
	}

	@Override
	public String getQueryString() {
		return StringEscapeUtils.escapeHtml4(super.getQueryString());
	}

	@Override
	public String getParameter(String name) {
		return StringEscapeUtils.escapeHtml4(super.getParameter(name));
	}

	@Override
	public String[] getParameterValues(String name) {
		String[] values = super.getParameterValues(name);
		if (values != null) {
			int length = values.length;
			String[] escapseValues = new String[length];
			for (int i = 0; i < length; i++) {
				escapseValues[i] = StringEscapeUtils.escapeHtml4(values[i]);
			}

			return escapseValues;
		}

		return super.getParameterValues(name);
	}

	@Override
	public Map getParameterMap() {
		Map paramMap = super.getParameterMap();
		Set parameterNames = paramMap.keySet();
		Iterator it = parameterNames.iterator();
		while (it.hasNext()) {
			String paramName = (String) it.next();
			String[] values = (String[]) paramMap.get(paramName);
			if (values != null) {
				int length = values.length;
				for (int i = 0; i < length; i++) {
					//StringEscapeUtils.escapeHtml4  特殊字符会有问题
					values[i] = htmlEncode(values[i]);
				}
			}
		}
		return paramMap;
	}
	public static void main(String[] args) {
		String str= "http://192.168.22.13:8080/passport/login?server=1043302&url=http%3A%2F%2Flocalhost%3A80%2Ffpf%2Fhomepage%2FeisLogin.action&context=&root=1&input=http%3A%2F%2F192.168.22.13%3A8080%2Feisu%2Ffpf%2Flogin%2FloginForPassport.action&auth=941a856fd342416f4796a9d9467cbb3c";
				   //http://192.168.22.13:8080/passport/login?server=1043302&amp;url=http%3A%2F%2Flocalhost%3A80%2Ffpf%2Fhomepage%2FeisLogin.action&amp;context=&amp;root=1&amp;input=http%3A%2F%2F192.168.22.13%3A8080%2Feisu%2Ffpf%2Flogin%2FloginForPassport.action&amp;auth=941a856fd342416f4796a9d9467cbb3c
			 	   //http://192.168.22.13:8080/passport/login?server=1043302&url=http%3A%2F%2Flocalhost%3A80%2Ffpf%2Fhomepage%2FeisLogin.action&context=&root=1&input=http%3A%2F%2F192.168.22.13%3A8080%2Feisu%2Ffpf%2Flogin%2FloginForPassport.action&auth=941a856fd342416f4796a9d9467cbb3c
		System.out.println(StringEscapeUtils.escapeHtml4(str));
		System.out.println(htmlEncode(str));
	}
	
	public static String htmlEncode(String source) {
        if (source == null) {
            return "";
        }
        String html = "";
        if(source.indexOf("&")>0){
        	String[] strs = source.split("&");
        	for (int i = 0; i < strs.length; i++) {
        		html += StringEscapeUtils.escapeHtml4(strs[i]);
        		if(i != strs.length-1){
        			html+="&";
        		}
            }
        }else{
        	html = StringEscapeUtils.escapeHtml4(source);
        }
        
        html = html.replaceAll("eval\\((.*)\\)", "");
        html = html.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']",
                "\"\"");
        html = html.replace("script", "");
        html = html.replace("document", "");
        System.out.println("----"+html);
        return html;
    }
}
