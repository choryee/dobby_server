package com.emgram.kr.dobby.controller;


import com.emgram.kr.dobby.dto.CommonResponse;
import com.emgram.kr.dobby.dto.employee.Employee;
import com.emgram.kr.dobby.dto.syscode.SystemCodeListItem;
import com.emgram.kr.dobby.service.EmployeeService;
import com.emgram.kr.dobby.service.SystemCodeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/syscode")
public class SystemCodeController {

    private final SystemCodeService systemCodeService;

    @GetMapping
    public CommonResponse<List<SystemCodeListItem>> getSysCodeList() {
        return new CommonResponse<>(systemCodeService.getSystemCodes());
    }
}
