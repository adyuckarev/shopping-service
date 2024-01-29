package com.example.shoppingservice.controller;

import com.example.shoppingservice.dto.UserPurchaseInfoDTO;
import com.example.shoppingservice.mapper.UserPurchaseInfoMapper;
import com.example.shoppingservice.model.UserPurchaseInfo;
import com.example.shoppingservice.service.UserPurchaseInfoService;
import com.example.shoppingservice.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user-purchases")
@RequiredArgsConstructor
public class UserPurchaseInfoController {
    private static final Logger logger = LoggerFactory.getLogger(UserPurchaseInfoController.class);
    private final UserPurchaseInfoService service;
    private final UserPurchaseInfoMapper userPurchaseInfoMapper;

    @GetMapping
    public String list(Model model) {
        logger.info("Getting list of user purchases");
        model.addAttribute("userPurchases", service.getAll());
        return "user-purchase/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        logger.info("Displaying create form for user purchase");
        model.addAttribute("userPurchase", new UserPurchaseInfo());
        return "user-purchase/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute UserPurchaseInfoDTO userPurchaseInfoDTO, BindingResult bindingResult, Model model, HttpServletResponse response) {
        logger.info("Creating user purchase");
        UserPurchaseInfo userPurchase = userPurchaseInfoMapper.toEntity(userPurchaseInfoDTO);
        String errors = ValidationUtil.handleValidationErrors(bindingResult, response, model, "user-purchase/create", userPurchase);
        if (errors.isEmpty()) {
            service.create(userPurchase);
            return "redirect:/user-purchases";
        } else {
            return errors;
        }
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable int id, Model model) {
        logger.info("Displaying edit form for user purchase with ID: {}", id);
        model.addAttribute("userPurchase", service.get(id));
        return "user-purchase/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable int id, @Valid @ModelAttribute UserPurchaseInfo userPurchase, BindingResult bindingResult, Model model, HttpServletResponse response) {
        logger.info("Editing user purchase with ID: {}", id);
        String errors = ValidationUtil.handleValidationErrors(bindingResult, response, model, "user-purchase/edit", userPurchase);
        if (errors.isEmpty()) {
            service.checkNew(id, userPurchase);
            return "redirect:/user-purchases";
        } else {
            return errors;
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        logger.info("Deleting user purchase with ID: {}", id);
        service.delete(id);
        return "redirect:/user-purchases";
    }

    @GetMapping("/report/last-week-purchases")
    public String lastWeekPurchases(Model model) {
        logger.info("Generating report for last week purchases");
        model.addAttribute("lastWeekPurchases", service.findPurchaseNamesInLastWeek());
        return "user-purchase/last-week-purchases";
    }

    @GetMapping("/report/most-purchased-item")
    public String mostPurchasedItem(Model model) {
        logger.info("Generating report for most purchased item last month");
        model.addAttribute("mostPurchasedItems", service.findMostPurchasedItemLastMonth());
        return "user-purchase/most-purchased-item";
    }

    @GetMapping("/report/most-active-customer")
    public String mostActiveCustomer(Model model) {
        logger.info("Generating report for most active customer in last six months");
        List<Map<String, Long>> user = service.findTopBuyerInLastSixMonths();
        model.addAttribute("mostActiveCustomer", service.findTopBuyerInLastSixMonths());
        return "user-purchase/most-active-customer";
    }

    @GetMapping("/report/most-purchased-by-age")
    public String mostPurchasedByAge(Model model) {
        logger.info("Generating report for most purchased items for age 18");
        model.addAttribute("mostPurchasedByAge", service.findMostPurchasedItemsForAge18());
        return "user-purchase/most-purchased-by-age";
    }
}
