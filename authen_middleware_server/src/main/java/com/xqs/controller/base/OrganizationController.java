package com.xqs.controller.base;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.xqs.entity.Organization;
import com.xqs.entity.User;
import com.xqs.service.base.intf.OrganizationService;
import com.xqs.web.bind.annotation.CurrentUser;

@Controller
@RequestMapping(value = "/organization")
public class OrganizationController {
	@Autowired
	private OrganizationService sysOrganizationService;

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		return "organization/index";
	}

	@RequiresPermissions("organization:view")
	@RequestMapping(value = "/tree", method = RequestMethod.GET)
	public String showTree(@CurrentUser User loginUser, Model model) {
		model.addAttribute("organizationList", sysOrganizationService.findAll(loginUser.getApp().getId()));
		return "organization/tree";
	}

	@RequiresPermissions("organization:create")
	@GetMapping("/{parentId}/appendChild")
	public String showAddForm(@PathVariable("parentId") Long parentId, Model model) {
		Organization parent = sysOrganizationService.get(parentId);
		Organization child = new Organization();
		child.setApp(parent.getApp());
		child.setParent(parent);
		model.addAttribute("parent", parent);
		model.addAttribute("child", child);
		model.addAttribute("op", "新增");
		return "organization/appendChild";
	}

	@RequiresPermissions("organization:create")
	@PostMapping("/{parentId}/appendChild")
	public String create(Organization organization, RedirectAttributes redirectAttributes) {
		sysOrganizationService.create(organization);
		redirectAttributes.addFlashAttribute("msg", "新增成功");
		return "redirect:/organization/success";
	}

	@RequiresPermissions("organization:update")
	@GetMapping("/{id}/update")
	public String showUpdateForm(@PathVariable("id") Long organizationId, Model model) {
		model.addAttribute("organization", sysOrganizationService.get(organizationId));
		model.addAttribute("op", "修改");
		return "organization/maintain";
	}

	@RequiresPermissions("organization:update")
	@PostMapping("/{id}/update")
	public String edit(@PathVariable("id") Long organizationId, Organization organization,
			RedirectAttributes redirectAttributes) {
		sysOrganizationService.update(sysOrganizationService.get(organizationId), organization);
		redirectAttributes.addFlashAttribute("msg", "修改成功");
		return "redirect:/organization/success";
	}

	@RequiresPermissions("organization:delete")
	@PostMapping("/{id}/delete")
	public String delete(@PathVariable("id") Long organizationId, RedirectAttributes redirectAttributes) {
		sysOrganizationService.delete(organizationId);
		redirectAttributes.addFlashAttribute("msg", "删除成功");
		return "redirect:/organization/success";
	}
}
