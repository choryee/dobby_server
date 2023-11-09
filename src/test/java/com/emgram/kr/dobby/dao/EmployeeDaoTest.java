package com.emgram.kr.dobby.dao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

import com.emgram.kr.dobby.dto.employee.Employee.SimpleEmployeeDTO;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmployeeDaoTest {

    @Autowired
    private EmployeeDao employeeDao;

    @Test
    @DisplayName("저장되어 있는 단순 사원 목록을 정상적으로 조회할수 있어야한다.")
    public void getEmployeeTest () {
        //given//when
        List<SimpleEmployeeDTO> employeeDTOList = employeeDao.findAllSimpleEmployeeList();

        //then
        assertThat(employeeDTOList.size(), greaterThan(10));
    }
}
