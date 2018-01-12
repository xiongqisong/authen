package com.xqs.util;

import java.lang.reflect.Method;
import java.util.List;

public class CollectionUtil {
	public static List filter(List data, Class clz, String field) throws NoSuchMethodException, SecurityException {
		Method getField = clz.getMethod("get" + field, null);
		return data;
	}
}
