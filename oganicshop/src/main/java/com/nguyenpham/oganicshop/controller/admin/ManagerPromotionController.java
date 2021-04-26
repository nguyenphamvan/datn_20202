package com.nguyenpham.oganicshop.controller.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin/manager_promotions")
public class ManagerPromotionController {

    @GetMapping
    public String viewPromotionPage() {
        return "admin/manager-promotion";
    }

    @GetMapping("/add")
    public String addPromotion(Model model) {
        return "admin/manager-addPromotion";
    }

    @GetMapping("/edit/{promotionId}")
    public String editPromotion(@PathVariable("promotionId") long promotionId, Model model) {
        model.addAttribute("promotionId", promotionId);
        return "admin/manager-editPromotion";
    }

}
