package com.hcms.modules.finance.controller;

import com.hcms.common.api.ApiResponse;
import com.hcms.modules.finance.dto.BillingResponse;
import com.hcms.modules.finance.service.FinanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/finance")
@RequiredArgsConstructor
@Tag(name = "Finance", description = "Billing and Payment API")
public class FinanceController {

    private final FinanceService financeService;

    @Operation(summary = "View Billing Invoice (UC-08)")
    @GetMapping("/billings/visit/{visitId}")
    public ResponseEntity<ApiResponse<BillingResponse>> getInvoice(@PathVariable UUID visitId) {
        BillingResponse response = financeService.getInvoiceByVisit(visitId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @Operation(summary = "Update Payment Status (UC-09)")
    @PatchMapping("/billings/{billingId}/pay")
    public ResponseEntity<ApiResponse<BillingResponse>> markAsPaid(@PathVariable UUID billingId) {
        BillingResponse response = financeService.markAsPaid(billingId);
        return ResponseEntity.ok(ApiResponse.success("Payment confirmed", response));
    }
}
