package com.xqs.dao;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.xqs.entity.Resource;

@Repository
public class ResourceDao extends IdEntityDao<Resource> {
	public List<Resource> findAll(Long appId) {
		String hql = "from Resource where app.id = :appId";
		return createQuery(hql).setParameter("appId", appId).list();
	}


	public List<Resource> findResources(Set<Long> resourceIds) {
		String hql = "from Resource where id in :resourceIds";
		return createQuery(hql).setParameterList("resourceIds", resourceIds).list();
	}

	// 批量插入这个方法有性能问题，可以考虑优化
	public void batchCreate(List<Resource> list){
		for(Resource r : list){
			create(r);
		}
		/*SessionFactory sf = this.getSession().getSessionFactory();
		DataSource ds = SessionFactoryUtils.getDataSource(sf);
		Connection conn = ds.getConnection();
		Statement stmt = conn.createStatement();
		String sql = "";
		stmt.executeQuery(sql);
		conn.commit();*/
	}
}
