package com.xqs.service.base.intf;

import java.util.List;
import java.util.Set;

import com.xqs.entity.App;
import com.xqs.entity.Resource;
import com.xqs.entity.User;

public interface ResourceService {
	/**
	 * 新建资源，且资源会绑定到创建资源的用户所属的应用上
	 * 
	 * @param sysResource
	 * @return
	 */
	Resource create(Resource sysResource);

	Resource get(Long id);

	Resource update(Resource base, Resource changed);

	void delete(Long id);

	/**
	 * 根据应用id得到应用的所有资源
	 * 
	 * @param appId
	 * @return
	 */
	List<Resource> findAll(Long appId);

	/**
	 * 得到资源对应的权限字符串
	 * 
	 * @param resourceIds
	 * @return
	 */
	Set<String> findPermissions(Set<Long> resourceIds);

	/**
	 * 根据用户权限得到资源（资源的类型为menu）
	 * PS：因为用户可以具备menu下面的任一资源（menu或button），但是不具备menu资源。这样在用户登录之后，初始化用户的菜单时，
	 * 用户虽然具备menu下面的某一资源，但是由于看不到menu，而看不到资源的情况。
	 * 因此做法是，根据用户具备的权限加载菜单，加载用户具备的权限的所有上级menu资源（上级资源即“权限包含用户具备的权限”的资源），当然如果
	 * 用户本身具备的某一项权限所代表的资源就是一个menu资源，那么也会被加载。
	 * 例子：用户具备“新增用户”权限(button,user:add)，但不具备“管理用户”权限(menu,user:*)，则加载菜单时会因为不加载“
	 * 管理用户”菜单导致“新增用户”按钮不可见
	 * 核心：加载资源的所有父级menu资源，确保在具备儿子资源时，不会因为不具备父级menu资源的而导致儿子资源不可见。
	 * 
	 * 
	 * 
	 * @param permissions
	 * @return
	 */
	// List<Resource> findMenus(User sysUser, Set<String> permissions);

	/**
	 * 得到资源
	 * 
	 * @param sysUser
	 * @return
	 */
	List<Resource> findResources(Set<Long> resourceIds);

	/**
	 * 批量插入
	 * 
	 * @param app
	 */
	void batchCreate(List<Resource> resources);
}
