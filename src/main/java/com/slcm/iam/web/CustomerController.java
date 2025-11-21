package com.slcm.iam.web;

import com.slcm.iam.domain.CustomerStatus;
import com.slcm.iam.dto.CreateCustomerRequest;
import com.slcm.iam.dto.CustomerDto;
import com.slcm.iam.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CreateCustomerRequest req) {
        return ResponseEntity.ok(customerService.createCustomer(req));
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> listCustomers() {
        return ResponseEntity.ok(customerService.listCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable("id") Long id) {
        return ResponseEntity.ok(customerService.getCustomer(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable("id") Long id,
                                                      @RequestBody CreateCustomerRequest req) {
        return ResponseEntity.ok(customerService.updateCustomer(id, req));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> changeStatus(@PathVariable("id") Long id,
                                             @RequestParam("status") CustomerStatus status) {
        customerService.changeStatus(id, status);
        return ResponseEntity.noContent().build();
    }
}
