package com.xqs.controller.base;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.xqs.entity.Role;
import com.xqs.entity.User;
import com.xqs.service.base.intf.ResourceService;
import com.xqs.service.base.intf.RoleService;
import com.xqs.web.bind.annotation.CurrentUser;

@Controller
@RequestMapping("/role")
public class RoleController {
	private RoleService sysRoleService;
	private ResourceService sysResourceService;

	@Autowired
	public RoleController(RoleService sysRoleService, ResourceService sysResourceService) {
		this.sysRoleService = sysRoleService;
		this.sysResourceService = sysResourceService;
	}

	@RequiresPermissions("role:view")
	@GetMapping
	public String list(@CurrentUser User loginUser, Model model) {
		model.addAttribute("roleList", sysRoleService.findAll(loginUser.getApp().getId()));
		return "role/list";
	}

	@RequiresPermissions("role:create")
	@GetMapping("/create")
	public String showAddForm(@CurrentUser User loginUser, Model model) {
		setCommonData(model, loginUser.getApp().getId());
		model.addAttribute("role", new Role());
		model.addAttribute("op", "新增");
		return "role/edit";
	}

	@RequiresPermissions("role:create")
	@PostMapping("/create")
	public String create(@CurrentUser User loginUser, Role role, RedirectAttributes redirectAttributes) {
		role.setApp(loginUser.getApp());
		sysRoleService.create(role);
		redirectAttributes.addFlashAttribute("msg", "新增成功");
		return "redirect:/role";
	}

	@RequiresPermissions("role:update")
	@GetMapping("/{id}/update")
	public String showUpdateForm(@PathVariable("id") Long roleId, Model model) {
		Role role = sysRoleService.get(roleId);
		setCommonData(model, role.getApp().getId());
		model.addAttribute("role", role);
		model.addAttribute("op", "修改");
		return "role/edit";
	}

	@RequiresPermissions("role:update")
	@PostMapping("/{id}/update")
	public String edit(@PathVariable("id") Long roleId, Role role, RedirectAttributes redirectAttributes) {
		sysRoleService.update(sysRoleService.get(roleId), role);
		redirectAttributes.addFlashAttribute("msg", "修改成功");
		return "redirect:/role";
	}

	@RequiresPermissions("role:delete")
	@GetMapping("/{id}/delete")
	public String showDeleteForm(@PathVariable("id") Long roleId, Model model) {
		Role role = sysRoleService.get(roleId);
		setCommonData(model, role.getApp().getId());
		model.addAttribute("role", role);
		model.addAttribute("op", "删除");
		return "role/edit";
	}

	@RequiresPermissions("role:delete")
	@PostMapping("/{id}/delete")
	public String delete(@PathVariable("id") Long roleId, RedirectAttributes redirectAttributes) {
		sysRoleService.delete(roleId);
		redirectAttributes.addFlashAttribute("msg", "删除成功");
		return "redirect:/role";
	}

	private void setCommonData(Model model, Long appId) {
		model.addAttribute("resourceList", sysResourceService.findAll(appId));
	}
}
