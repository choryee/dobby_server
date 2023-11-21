package com.emgram.kr.dobby.controller;

import com.emgram.kr.dobby.dto.CommonResponse;
import com.emgram.kr.dobby.dto.PageInfo;
import com.emgram.kr.dobby.dto.SearchCondition;
import com.emgram.kr.dobby.dto.employee.Employee.SimpleEmployeeDTO;
import com.emgram.kr.dobby.dto.employee.EmployeeDayoffInfo;
import com.emgram.kr.dobby.dto.employee.EmployeeInfo;
import com.emgram.kr.dobby.service.EmployeeDayoffCalculateService;
import com.emgram.kr.dobby.service.EmployeeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    private final EmployeeDayoffCalculateService employeeDayoffCalculateService;

    @GetMapping("/simple-info")
    public CommonResponse<List<SimpleEmployeeDTO>> getSimpleEmployeeList() {
        return new CommonResponse<>(employeeService.getSimpleEmployeeList());
    }

    @GetMapping("/list")
    public ResponseEntity<List<EmployeeInfo>> getAllEmployee() {

        List<EmployeeInfo> list = employeeService.getListEmployee();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/list/{employeeNo}")
    public ResponseEntity getEmployee(@PathVariable String employeeNo) {
        try {
            EmployeeInfo employeeInfo = employeeService.getList(employeeNo);
            if (employeeInfo != null) {
                return new ResponseEntity<>(employeeInfo, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Employee not found for employeeNo: " + employeeNo,
                    HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(
                "Failed to get employee information. Error: " + e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/insert")
    public ResponseEntity postEmployee(@RequestBody EmployeeInfo employeeInfo) {
        try {
            employeeService.insertEmployee(employeeInfo);
            return new ResponseEntity<>("Employee information inserted successfully.",
                HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                "Failed to insert employee information. Error: " + e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/modify/{employeeNo}")
    public ResponseEntity updateEmployee(@PathVariable String employeeNo,
        @RequestBody EmployeeInfo employeeInfo) {
        try {
            employeeInfo.setEmployeeNo(employeeNo);
            employeeService.updateEmployee(employeeInfo);
            return new ResponseEntity<>("Employee information updated successfully.",
                HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                "Failed to insert employee information. Error: " + e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/delete/{employeeNo}")
    public ResponseEntity deleteEmployee(@PathVariable String employeeNo) {
        try {
            employeeService.deleteEmployee(employeeNo);
            return new ResponseEntity<>("Employee information deleted successfully.",
                HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                "Failed to insert employee information. Error: " + e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/dayoff/list")
    public CommonResponse<PageInfo<EmployeeDayoffInfo>> getEmployeeDayoffInfos(
        @ModelAttribute SearchCondition searchCondition
    ) {
        return new CommonResponse<>(
            employeeDayoffCalculateService.getEmployeeDayoffCalculateInfos(searchCondition));
    }

}
