package com.xqs.controller.base;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.xqs.entity.Resource;
import com.xqs.entity.Role;
import com.xqs.entity.User;
import com.xqs.service.base.intf.AppService;
import com.xqs.service.base.intf.ResourceService;
import com.xqs.service.base.intf.RoleService;
import com.xqs.service.base.intf.UserService;
import com.xqs.web.bind.annotation.CurrentUser;

@Controller
@RequestMapping("/resource")
public class ResourceController {
	private ResourceService resourceService;

	@Autowired
	public ResourceController(ResourceService sysResourceService, UserService sysUserService,
			RoleService sysRoleService, AppService sysAppService) {
		this.resourceService = sysResourceService;
	}

	/*@ModelAttribute("types")
	public Resource.ResourceType[] resourceTypes() {
		return Resource.ResourceType.values();
	}*/

	/**
	 * 获取指定用户的资源树(超级管理员可以看到全部资源)
	 * 
	 * @param loginUser
	 * @param model
	 * @return
	 */
	@RequiresPermissions("resource:view")
	@GetMapping
	public String list(@CurrentUser User loginUser, Model model) {
		Subject subject = SecurityUtils.getSubject();
		if (subject.hasRole("admin")) {
			// “超级管理员”可以查看应用的所有资源
			model.addAttribute("resourceList", resourceService.findAll(loginUser.getApp().getId()));
		} else {
			throw new RuntimeException("非超级管理员角色的用户不允许查看系统资源列表");
		}
		return "/resource/list";
	}

	// TODO 展示添加资源界面也可以去掉了
	/*@RequiresPermissions("resource:create")
	@GetMapping(value = "/{parentId}/appendChild")
	public String showAddForm(@PathVariable("parentId") Long parentId, Model model) {
		Resource parent = resourceService.get(parentId);
		Resource child = new Resource();
		child.setParent(parent);
		child.setApp(parent.getApp());
		model.addAttribute("parent", parent);
		model.addAttribute("resource", child);
		model.addAttribute("op", "新增子节点");
		return "resource/edit";
	}*/

	@RequiresPermissions("resource:create")
	@PostMapping(value = "/{parentId}/appendChild")
	public String create(@CurrentUser User loginUser, Resource sysResource,
			RedirectAttributes redirectAttributes) {
		sysResource.setApp(loginUser.getApp());
		sysResource.setAvailable(true);
		resourceService.create(sysResource);
		redirectAttributes.addFlashAttribute("msg", "新增子节点成功");
		return "redirect:/resource";
	}

	@RequiresPermissions("resource:update")
	@GetMapping(value = "/{id}/update")
	public String showUpdateForm(@PathVariable("id") Long resourceId, Model model) {
		model.addAttribute("resource", resourceService.get(resourceId));
		model.addAttribute("op", "修改");
		return "resource/edit";
	}

	@RequiresPermissions("resource:update")
	@PostMapping(value = "/{id}/update")
	public String edit(@PathVariable("id") Long resourceId, Resource sysResource,
			RedirectAttributes redirectAttributes) {
		resourceService.update(resourceService.get(resourceId), sysResource);
		redirectAttributes.addFlashAttribute("msg", "修改成功");
		return "redirect:/resource";
	}

	@RequiresPermissions("resource:delete")
	@GetMapping(value = "/{id}/delete")
	public String delete(@PathVariable("id") Long resourceId, RedirectAttributes redirectAttributes) {
		resourceService.delete(resourceId);
		redirectAttributes.addFlashAttribute("msg", "删除成功");
		return "redirect:/resource";
	}

	// @RequestMapping(method = RequestMethod.GET)
	// public void check(@CurrentUser SysUser loginUser ,HttpServletRequest
	// request){
	// Subject subject = SecurityUtils.getSubject();
	// subject.checkPermission("");
	// }
}
