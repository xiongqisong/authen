package com.xqs.service.first;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.xqs.entity.App;
import com.xqs.entity.Feature;
import com.xqs.entity.Feature.FeatureType;
import com.xqs.entity.Resource;
import com.xqs.entity.Role;
import com.xqs.entity.User;
import com.xqs.service.base.intf.AppService;
import com.xqs.service.base.intf.FeatureService;
import com.xqs.service.base.intf.ResourceService;
import com.xqs.service.base.intf.RoleService;
import com.xqs.service.base.intf.UserService;
import com.xqs.service.first.intf.RegisterService;
import com.xqs.util.CollectionUtil;

@Service
public class RegisterServiceImpl implements RegisterService {
	@Autowired
	private AppService appService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private FeatureService featureService;
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private UserService userService;

	@Override
	@Transactional
	public App registerApp(App app) {
		// 检查App是否已经注册
		Assert.isNull(appService.findByName(app.getName()), app.getName() + "已被注册");

		App result = appService.create(app);
		// 创建初始资源
		List<Resource> resources = new ArrayList<Resource>();
		resources.add(new Resource(app, "组织机构管理", "/organization", "organization:*", true));
		resources.add(new Resource(app, "组织机构新增", "", "organization:create", true));
		resources.add(new Resource(app, "组织机构修改", "", "organization:update", true));
		resources.add(new Resource(app, "组织机构删除", "", "organization:delete", true));
		resources.add(new Resource(app, "组织机构查看", "", "organization:view", true));

		resources.add(new Resource(app, "用户管理", "/user", "user:*", true));
		resources.add(new Resource(app, "用户新增", "", "user:create", true));
		resources.add(new Resource(app, "用户修改", "", "user:update", true));
		resources.add(new Resource(app, "用户删除", "", "user:delete", true));
		resources.add(new Resource(app, "用户查看", "", "user:view", true));

		resources.add(new Resource(app, "角色管理", "/role", "role:*", true));
		resources.add(new Resource(app, "角色新增", "", "role:create", true));
		resources.add(new Resource(app, "角色修改", "", "role:update", true));
		resources.add(new Resource(app, "角色删除", "", "role:delete", true));
		resources.add(new Resource(app, "角色查看", "", "role:view", true));

		resources.add(new Resource(app, "功能管理", "/feature", "feature:*", true));
		resources.add(new Resource(app, "功能新增", "", "feature:create", true));
		resources.add(new Resource(app, "功能修改", "", "feature:update", true));
		resources.add(new Resource(app, "功能删除", "", "feature:delete", true));
		resources.add(new Resource(app, "功能查看", "", "feature:view", true));

		resources.add(new Resource(app, "资源管理", "/resource", "resource:*", true));
		resources.add(new Resource(app, "资源新增", "", "resource:create", true));
		resources.add(new Resource(app, "资源修改", "", "resource:update", true));
		resources.add(new Resource(app, "资源删除", "", "resource:delete", true));
		resources.add(new Resource(app, "资源查看", "", "resource:view", true));

		resourceService.batchCreate(resources);

		// 创建初始功能
		List<Feature> features = new CopyOnWriteArrayList<Feature>();
		Date now = new Date();
		int menuIndex = 0;
		boolean isMenu = false;
		for (int i = 0; i < resources.size(); i++) {
			Resource r = resources.get(i);
			Feature f = new Feature(app, r.getName(), r, true, now, now);
			isMenu = i % 5 == 0;
			f.setType(isMenu ? FeatureType.menu:FeatureType.button);
			f.setParent(isMenu ? null : features.get(menuIndex));
			featureService.create(f);
			if (i != 0 && (i + 1) % 5 == 0) {
				// 每创建完5个，目录索引加5
				menuIndex += 5;
			}
			features.add(f);
		}

		// 创建管理员角色
		Role adminRole = new Role(app, "admin", app.getName() + "管理员", new HashSet<Feature>(features), 0L, "0/", true,
				now, null);
		roleService.create(adminRole);
		Set<Role> adminRoleSet = new HashSet<Role>();
		adminRoleSet.add(adminRole);

		// 创建初始管理员用户
		User adminUser = new User(1L, app, "admin", "123456", adminRoleSet, false);
		userService.create(adminUser);
		return result;
	}
}
