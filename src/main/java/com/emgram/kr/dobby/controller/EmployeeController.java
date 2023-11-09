package com.emgram.kr.dobby.controller;

import com.emgram.kr.dobby.dto.employee.Employee.SimpleEmployeeDTO;
import com.emgram.kr.dobby.service.EmployeeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/simple-info")
    @CrossOrigin
    public List<SimpleEmployeeDTO> getSimpleEmployeeList() {
        return employeeService.getSimpleEmployeeList();
    }
}
