package com.xqs.service.base.intf;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xqs.entity.Feature;

public interface FeatureService {
	Feature create(Feature feature);

	Feature get(Long id);

	Feature update(Feature base, Feature changed);

	void delete(Long id);

	/**
	 * 批量得到功能
	 * 
	 * @param roleIds
	 * @return
	 */
	List<Feature> findFeatures(Long... featureIds);

	/**
	 * 对功能集合进行分层处理 数据接口：根据功能的层级深度，建立对应数量的存储空间Map，
	 * key为层级标识（从0开始），value为该层级的所有功能集合---以Map存储
	 * value存储的数据中，key为父级id，value为所有子级集合
	 * 
	 * @param features
	 * @return
	 */
	Map<Long, Map<Long, List<Feature>>> layerFilter(Set<Feature> features);

	/**
	 * 批量插入
	 * 
	 * @param features
	 */
	void batchCreate(List<Feature> features);
}
