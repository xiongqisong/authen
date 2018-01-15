package com.xqs.service.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.xqs.dao.FeatureDao;
import com.xqs.entity.Feature;
import com.xqs.entity.Feature.FeatureType;
import com.xqs.service.base.intf.FeatureService;

@Service
public class FeatureServiceImpl implements FeatureService {
	public static final Long DEEPEST_LAYER = 3L;
	@Autowired
	public FeatureDao dao;

	@Override
	@Transactional
	public Feature create(Feature feature) {
		Date now = new Date();
		feature.setCreateTime(now);
		feature.setUpdateTime(now);
		return dao.create(feature);
	}

	@Override
	public Feature get(Long id) {
		return dao.get(id);
	}

	@Override
	@Transactional
	public Feature update(Feature base, Feature changed) {
		return dao.safeUpdate(base, changed);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		dao.delete(id);
	}

	@Override
	public List<Feature> findFeatures(Long... featureIds) {
		Assert.notEmpty(featureIds, "功能id集合不应为空");
		return dao.findFeatures(featureIds);
	}

	@Override
	public Map<Long, Map<Long, List<Feature>>> layerFilter(Set<Feature> features) {
		long rootLayer = 0L;
		long deepestLayer = 0L;
		Map<Long, Map<Long, List<Feature>>> result = new LinkedHashMap<Long, Map<Long, List<Feature>>>();// 采用LinkedHashMap保证层级的顺序

		// 获取功能的最深层级
		for (Feature feature : features) {
			if (feature.getType() == FeatureType.menu) {
				long currentLayer = currentLayer(feature);
				if (currentLayer >= deepestLayer) {
					deepestLayer = currentLayer;
				}
			}
		}

		// 根据层数建立功能的分层存储空间(层级编号从0开始)
		for (long i = rootLayer; i < deepestLayer; i++) {
			result.put(i, new HashMap<Long, List<Feature>>());
		}

		// 填充每一层的数据
		for (Feature feature : features) {
			if (feature.getType() == FeatureType.menu) {
				long currentLayer = currentLayer(feature);
				// 填充前，查看层数据存储value的值的集合是否创建
				if (result.get(currentLayer - 1).get(feature.getParentId()) == null) {// 因为层级编号从0开始，所以要减1
					result.get(currentLayer - 1).put(feature.getParentId(), new ArrayList<Feature>());
					result.get(currentLayer - 1).get(feature.getParentId()).add(feature);
				} else {
					result.get(currentLayer - 1).get(feature.getParentId()).add(feature);
				}
			}
		}
		return result;
	}

	private static long currentLayer(Feature feature) {
		return feature.getParentIds().split("/").length;// 根功能的parentIds的表示形式为0/
	}

	@Override
	public void batchCreate(List<Feature> features) {
		dao.batchCreate(features);
	}
}
