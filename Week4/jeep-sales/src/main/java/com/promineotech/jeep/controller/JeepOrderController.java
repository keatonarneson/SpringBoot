package com.promineotech.jeep.controller;

import com.promineotech.jeep.entity.Order;
import com.promineotech.jeep.entity.OrderRequest;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Validated
@RequestMapping("/orders")
public interface JeepOrderController {

  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  Order createOrder(@RequestBody OrderRequest orderRequest);
  
}
