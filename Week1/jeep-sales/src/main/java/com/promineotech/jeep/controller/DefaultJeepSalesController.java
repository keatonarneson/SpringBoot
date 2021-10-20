package com.promineotech.jeep.controller;

import java.util.List;

import com.promineotech.jeep.entity.Jeep;
import com.promineotech.jeep.entity.JeepModel;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultJeepSalesController implements JeepSalesController {

  @Override
  public List<Jeep> fetchJeeps(JeepModel model, String trim) {
    return null;
  }
}
