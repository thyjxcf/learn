package net.zdsoft.eis.frame.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.alibaba.com.caucho.hessian.io.Hessian2Input;
import com.alibaba.com.caucho.hessian.io.Hessian2Output;

public class SerializationUtils {

	public static byte[] serialize(Object t) {
		if (t == null)
			return null;
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			Hessian2Output out = new Hessian2Output(os);
			out.writeObject(t);
			out.flushBuffer();
			os.close();
			return os.toByteArray();
		} catch (IOException e) {
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static Object deserialize(byte[] data) {
		if (data == null)
			return null;
		try {
			ByteArrayInputStream is = new ByteArrayInputStream(data);
			Hessian2Input in = new Hessian2Input(is);
			is.close();
			return in.readObject();
		} catch (IOException e) {
		}
		return null;
	}

}
