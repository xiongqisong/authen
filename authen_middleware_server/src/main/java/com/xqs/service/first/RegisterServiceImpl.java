package com.xqs.service.first;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xqs.entity.App;
import com.xqs.entity.Feature;
import com.xqs.entity.Resource;
import com.xqs.entity.Feature.FeatureType;
import com.xqs.service.base.intf.AppService;
import com.xqs.service.base.intf.FeatureService;
import com.xqs.service.base.intf.ResourceService;
import com.xqs.service.base.intf.RoleService;
import com.xqs.service.first.intf.RegisterService;

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

	@Override
	public App registerApp(App app) {
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

		resources.add(new Resource(app, "资源管理", "/resource", "resource:*", true));
		resources.add(new Resource(app, "资源新增", "", "resource:create", true));
		resources.add(new Resource(app, "资源修改", "", "resource:update", true));
		resources.add(new Resource(app, "资源删除", "", "resource:delete", true));
		resources.add(new Resource(app, "资源查看", "", "resource:view", true));

		resources.add(new Resource(app, "角色管理", "/role", "role:*", true));
		resources.add(new Resource(app, "角色新增", "", "role:create", true));
		resources.add(new Resource(app, "角色修改", "", "role:update", true));
		resources.add(new Resource(app, "角色删除", "", "role:delete", true));
		resources.add(new Resource(app, "角色查看", "", "role:view", true));
		resourceService.batchCreate(resources);
		// 创建初始功能
		List<Feature> features = new ArrayList<Feature>();
		Date now = new Date();
		int menuIndex = 0;
		int flow = 0;
		for (int i = 0; i < resources.size(); i++) {
			Resource r = resources.get(i);
			Feature f = new Feature(app, r.getName(), FeatureType.button, r, true, now, now);
			if (i % 5 != 0) {
				f.setParent(features.get(menuIndex));
			}
			featureService.create(f);
			f.setParent(features.get(menuIndex));
			menuIndex++;
			if (flow / 5 == 1) {
				menuIndex++;
				flow = 0;
			}
		}
		featureService.batchCreate(features);
		// 创建管理员角色
		roleService.initRoles(app, resourceIds);
		// 创建初始管理员用户
		return result;
	}

}
