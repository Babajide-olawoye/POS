package com.AAA.demo.controller;

import com.AAA.demo.dto.CreateRefundDto;
import com.AAA.demo.dto.RefundViewDto;
import com.AAA.demo.service.RefundService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/refunds")
public class RefundController {

    private final RefundService refundService;

    public RefundController(RefundService refundService) {
        this.refundService = refundService;
    }

    @PostMapping
    public ResponseEntity<RefundViewDto> create(@RequestBody CreateRefundDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(refundService.createRefund(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RefundViewDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(refundService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        refundService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
