package com.xqs.dao;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xqs.entity.Feature;

@Repository
public class FeatureDao extends IdEntityDao<Feature> {

	public List<Feature> findFeatures(Long[] featureIds) {
		String hql = "from Feature where id in :featureIds";
		return createQuery(hql).setParameterList("featureIds", featureIds).list();
	}

	public void batchCreate(List<Feature> features) {
		for (Feature f : features) {
			Date now = new Date();
			f.setCreateTime(now);
			f.setUpdateTime(now);
			create(f);
		}
	}

}
