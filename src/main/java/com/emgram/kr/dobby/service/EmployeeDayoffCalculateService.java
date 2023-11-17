package com.emgram.kr.dobby.service;

import com.emgram.kr.dobby.dto.PageInfo;
import com.emgram.kr.dobby.dto.SearchCondition;
import com.emgram.kr.dobby.dto.dashboard.DashBoardDayoffDTO;
import com.emgram.kr.dobby.dto.employee.Employee;
import com.emgram.kr.dobby.dto.employee.EmployeeDayoffInfo;
import com.emgram.kr.dobby.dto.holiday.HolidayDto;
import com.emgram.kr.dobby.utils.EmployeeUtil;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeDayoffCalculateService {

    private final EmployeeService employeeService;

    private final DashBoardService dashBoardService;

    private final HolidayService holidayService;

    public PageInfo<EmployeeDayoffInfo> getEmployeeDayoffCalculateInfos(
        SearchCondition searchCondition) {

        //사원목록 페이징 호출
        List<Employee> employees = employeeService.findAllEmployeesBySearchCondition(
            searchCondition);

        //사원-지급연차맵 가공
        List<? extends HolidayDto> holidayDtos = holidayService.getHolidays(
            searchCondition.getStartDate(), searchCondition.getEndDate());
        Map<String, Double> employeePaidDayoffMap = dashBoardService.getAllEmployeePaidDayoffMap(
            employees, searchCondition.getYear());

        //사원-사용한 연차맵 가공
        List<DashBoardDayoffDTO> dashBoardDayoffDTOS = dashBoardService.getDashboardDayoffItem(
            searchCondition.getStartDate(), searchCondition.getEndDate());
        Map<String, Double> employeeUsedDayoffMap = dashBoardService.getAllEmployeeUsedDayoffMap(
            dashBoardDayoffDTOS, holidayDtos);

        //데이터 가공
        List<EmployeeDayoffInfo> employeeDayoffInfos = employees
            .stream()
            .map((employee -> createEmployeeDayoffInfo(employee, employeePaidDayoffMap, employeeUsedDayoffMap)))
            .collect(Collectors.toList());


        if (employeeDayoffInfos.size() == searchCondition.getPageSize()) {
            Integer count = employeeService.countAllEmployees();
            if (count == null) count = 0;
            return new PageInfo<>(count, searchCondition, employeeDayoffInfos);
        }
        return new PageInfo<>(searchCondition, employeeDayoffInfos);
    }

    private EmployeeDayoffInfo createEmployeeDayoffInfo(Employee employee,
        Map<String, Double> employeePaidDayoffMap,
        Map<String, Double> employeeUsedDayoffMap
    ) {

        Double totalDayoffCount = 0.0;
        Double usedDayoffCount = 0.0;
        Double remaingDayoffCount = 0.0;

        boolean isEmployee = EmployeeUtil.isEmployee(employee);

        if (isEmployee) {
            if (employeePaidDayoffMap.get(employee.getEmployeeNo()) != null) {
                totalDayoffCount = employeePaidDayoffMap.get(employee.getEmployeeNo());
            }

            if (totalDayoffCount != -1.0
                && employeeUsedDayoffMap.get(employee.getEmployeeNo()) != null) {
                usedDayoffCount = employeeUsedDayoffMap.get(employee.getEmployeeNo());
            }
            ;
            if (totalDayoffCount != -1.0 && usedDayoffCount != -1.0) {
                remaingDayoffCount = totalDayoffCount - usedDayoffCount;
            }

        } else {
            totalDayoffCount = -1.0;
            usedDayoffCount = -1.0;
            remaingDayoffCount = -1.0;
        }

        return EmployeeDayoffInfo.builder()
            .employeeNo(employee.getEmployeeNo())
            .name(employee.getName())
            .joiningDt(employee.getJoiningDt())
            .rankName(employee.getRankName())
            .totalDayoffCount(totalDayoffCount)
            .usedDayoffCount(usedDayoffCount)
            .remainingDayoffCount(remaingDayoffCount)
            .build();
    }
}
