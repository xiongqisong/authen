package com.xqs.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

public class CollectionUtil {
	/**
	 * TODO 未完成的工具方法
	 * 
	 * @param data
	 * @param clz
	 * @param field
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public static List filter(List data, Class clz, String field) throws NoSuchMethodException, SecurityException {
		Method getField = clz.getMethod("get" + field, null);
		return data;
	}

	/**
	 * 将集合中的元素按照指定的分隔符分割，以字符串格式返回
	 * 
	 * @param data		集合
	 * @param spliter	分隔符
	 * @return
	 */
	public static String toString(List data, String spliter) {
		Assert.notEmpty(data, "collections shouldn't be empty");
		StringBuilder sb = new StringBuilder();
		for (Object item : data) {
			sb.append(item.toString()).append(spliter);
		}
		return sb.substring(0, sb.length() - 1);
	}
}
