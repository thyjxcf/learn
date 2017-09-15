package net.zdsoft.eis.base.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class SerializationUtils {

	public static <T> String serialize(T t) {
		if (t == null)
			return null;
		return JSON.toJSONString(t, SerializerFeature.EMPTY);
	}

	public static <T> T deserialize(String data, Class<T> clazz) {
		if (data == null)
			return null;
		return JSON.parseObject(data, clazz, Feature.IgnoreNotMatch);
	}

	public static <T> T deserialize(String data, TypeReference<T> type) {
		if (data == null)
			return null;
		return JSON.parseObject(data, type, Feature.IgnoreNotMatch);
	}

	public static void main(String[] args) {
	}

}
