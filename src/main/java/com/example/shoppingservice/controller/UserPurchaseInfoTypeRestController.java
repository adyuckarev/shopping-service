package com.example.shoppingservice.controller;

import com.example.shoppingservice.dto.xml.UserPurchaseInfoType;
import com.example.shoppingservice.service.UserPurchaseInfoService;
import com.example.shoppingservice.util.ConvertUtil;
import com.example.shoppingservice.util.ValidationUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user-purchases-info")
@RequiredArgsConstructor
public class UserPurchaseInfoTypeRestController {
    private static final Logger logger = LoggerFactory.getLogger(UserPurchaseInfoTypeRestController.class);
    private final UserPurchaseInfoService service;

    @Operation(summary = "Endpoint to add a new user purchase information.")
    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> addPurchase(
            @Valid @RequestBody UserPurchaseInfoType userPurchaseInfoType,
            BindingResult bindingResult) {
        return processPurchase(userPurchaseInfoType, bindingResult, () -> service.create(ConvertUtil.convertToUserPurchaseInfo(userPurchaseInfoType)),
                "Purchase added successfully");
    }

    @Operation(summary = "Endpoint to update user purchase information.")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_XML_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> update(@Valid @RequestBody UserPurchaseInfoType userPurchaseInfoType, @PathVariable int id, BindingResult bindingResult) {
        logger.info("update {} with id={}", userPurchaseInfoType, id);
        return processPurchase(userPurchaseInfoType, bindingResult, () -> service.update(ConvertUtil.convertToUserPurchaseInfo(userPurchaseInfoType), id),
                "Purchase updated successfully");
    }

    @Operation(summary = "Endpoint to get user purchase information by ID.")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public UserPurchaseInfoType get(@PathVariable int id) {
        return ConvertUtil.convertToUserPurchaseInfoType(service.get(id));
    }

    @Operation(summary = "Endpoint to delete user purchase information by ID.")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }

    @Operation(summary = "Endpoint to get all user purchase information.")
    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public List<UserPurchaseInfoType> getAll() {
        return ConvertUtil.convertToListUserPurchaseInfoType(service.getAll());
    }

    private ResponseEntity<String> processPurchase(UserPurchaseInfoType userPurchaseInfoType,
                                                   BindingResult bindingResult,
                                                   Runnable action,
                                                   String successMessage) {
        if (!ValidationUtil.isValid(userPurchaseInfoType)) {
            return new ResponseEntity<>("Invalid XML format", HttpStatus.BAD_REQUEST);
        }
        String errorMessage = ValidationUtil.handleBindingErrors(bindingResult);
        if (!errorMessage.isEmpty()) {
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        action.run();
        return new ResponseEntity<>(successMessage, HttpStatus.CREATED);
    }
}
