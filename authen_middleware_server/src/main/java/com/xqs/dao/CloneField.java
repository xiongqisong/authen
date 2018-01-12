package com.xqs.dao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 将B对象的特定字段复制到A对象
 * 
 * @author ycr
 * @created 2017年9月22日 下午2:31:25
 */
public interface CloneField<T> {
	public final String GET = "get";
	public final String SET = "set";

	/**
	 * 使用changeData的数据更新base中对应的属性（fields）
	 * 
	 * @param base
	 *            被更新的对象
	 * @param changeData
	 *            用于更新base的对象
	 * @param fields
	 *            更新的字段
	 * @return
	 */
	default T cloneFields(T base, T changeData, String... fields) {
		try {
			if (base.getClass().equals(changeData.getClass())) {
				Method[] methods = base.getClass().getMethods();
				Map<String, Method> methodsKV = new HashMap<String, Method>();
				for (Method method : methods) {
					methodsKV.put(method.getName(), method);
				}

				for (String field : fields) {
					String fieldFirstChar2Upcase = field.substring(0, 1).toUpperCase() + field.substring(1);
					String getMethod = GET + fieldFirstChar2Upcase;
					String setMethod = SET + fieldFirstChar2Upcase;
					methodsKV.get(setMethod).invoke(base, methodsKV.get(getMethod).invoke(changeData, null));
				}
				return base;
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new RuntimeException("clone field failed", e);
		}
	}
}
