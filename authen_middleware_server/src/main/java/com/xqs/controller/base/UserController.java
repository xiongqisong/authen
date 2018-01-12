package com.xqs.controller.base;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.xqs.entity.User;
import com.xqs.service.base.intf.OrganizationService;
import com.xqs.service.base.intf.RoleService;
import com.xqs.service.base.intf.UserService;
import com.xqs.web.bind.annotation.CurrentUser;

@Controller
@RequestMapping("/user")
public class UserController {
	private UserService sysUserService;
	private OrganizationService sysOrganizationService;
	private RoleService sysRoleService;

	@Autowired
	public UserController(UserService sysUserService, OrganizationService sysOrganizationService,
			RoleService sysRoleService) {
		this.sysUserService = sysUserService;
		this.sysOrganizationService = sysOrganizationService;
		this.sysRoleService = sysRoleService;
	}

	@RequiresPermissions("user:view")
	@GetMapping
	public String list(@CurrentUser User loginUser, Model model) {
		model.addAttribute("userList", sysUserService.findAll(loginUser.getApp().getId()));
		return "user/list";
	}

	@RequiresPermissions("user:create")
	@GetMapping("/create")
	public String showAddForm(@CurrentUser User loginUser, Model model) {
		setCommonData(model, loginUser.getApp().getId());
		User newUser = new User();
		newUser.setApp(loginUser.getApp());
		model.addAttribute("user", newUser);
		model.addAttribute("op", "新增");
		return "user/edit";
	}

	@RequiresPermissions("user:create")
	@PostMapping("/create")
	public String create(@CurrentUser User loginUser,User user, RedirectAttributes redirectAttributes) {
		user.setApp(loginUser.getApp());
		sysUserService.create(user);
		redirectAttributes.addFlashAttribute("msg", "新增成功");
		return "redirect:/user";
	}

	@RequiresPermissions("user:update")
	@GetMapping("/{id}/update")
	public String showUpdateForm(@PathVariable("id") Long userId, Model model) {
		User user = sysUserService.get(userId);
		setCommonData(model, user.getApp().getId());
		model.addAttribute("user", user);
		model.addAttribute("op", "修改");
		return "user/edit";
	}

	@RequiresPermissions("user:update")
	@PostMapping("/{id}/update")
	public String edit(@PathVariable("id") Long userId, User user, RedirectAttributes redirectAttributes) {
		sysUserService.update(sysUserService.get(userId), user);
		redirectAttributes.addFlashAttribute("msg", "修改成功");
		return "redirect:/user";
	}

	@RequiresPermissions("user:delete")
	@GetMapping("/{id}/delete")
	public String delete(@PathVariable("id") Long userId, RedirectAttributes redirectAttributes) {
		sysUserService.delete(userId);
		redirectAttributes.addFlashAttribute("msg", "删除成功");
		return "redirect:/user";
	}

	@RequiresPermissions("user:update")
	@GetMapping("/{id}/changePassword")
	public String showChangePasswordForm(@PathVariable("id") Long userId, Model model) {
		model.addAttribute("user", sysUserService.get(userId));
		model.addAttribute("op", "修改密码");
		return "user/changePassword";
	}

	@RequiresPermissions("user:update")
	@PostMapping("/{id}/changePassword")
	public String changePassword(@PathVariable("id") Long userId, String newPassword,
			RedirectAttributes redirectAttributes) {
		sysUserService.changePassword(userId, newPassword);
		redirectAttributes.addFlashAttribute("msg", "修改成功");
		return "redirect:/user";
	}

	private void setCommonData(Model model, Long appId) {
		model.addAttribute("organizationList", sysOrganizationService.findAll(appId));
		model.addAttribute("roleList", sysRoleService.findAll(appId));
	}
}
