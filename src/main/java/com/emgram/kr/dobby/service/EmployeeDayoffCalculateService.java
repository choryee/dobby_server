package com.emgram.kr.dobby.service;

import com.emgram.kr.dobby.dto.PageInfo;
import com.emgram.kr.dobby.dto.SearchCondition;
import com.emgram.kr.dobby.dto.dashboard.DashBoardDayoffDTO;
import com.emgram.kr.dobby.dto.employee.Employee;
import com.emgram.kr.dobby.dto.employee.EmployeeDayoffInfo;
import com.emgram.kr.dobby.dto.holiday.HolidayDto;
import com.emgram.kr.dobby.utils.EmployeeUtil;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeDayoffCalculateService {

    private final EmployeeService employeeService;

    private final HolidayService holidayService;

    private final DashBoardService dashBoardService;

    private static List<String> COUNT_PARAMETERS =
        Arrays.asList("totalDayoffCount", "usedDayoffCount", "remainingDayoffCount");

    /**
     * 사원별 정보 및 연차정보 조회
     *
     * @param searchCondition
     * @return
     */
    public PageInfo<EmployeeDayoffInfo> getEmployeeDayoffCalculateInfos(
        SearchCondition searchCondition) {
        //정렬 방식에 따라 다른 방식의 조회 및 페이징 사용
        if (searchCondition != null) {
            boolean isSortCountQuery = COUNT_PARAMETERS.contains(searchCondition.getSortQuery());
            if (isSortCountQuery) {
                return getSortDayoffCountEmployeeDayoffCalculateInfos(searchCondition);
            } else {
                return getSortCommonEmployeeDayoffCalculateInfos(searchCondition);
            }
        }
        return null;
    }

    /**
     * 정렬 및 페이징 정렬조건이 전체,소모,남은 연차 순일 경우 사용
     *
     * @param searchCondition
     * @return
     */
    private PageInfo<EmployeeDayoffInfo> getSortDayoffCountEmployeeDayoffCalculateInfos
    (SearchCondition searchCondition) {
        //모든 사원 호출
        List<Employee> employees = employeeService.getAllEmployeeList();

        List<EmployeeDayoffInfo> employeeDayoffInfos = getEmployeeDayoffInfos(searchCondition,
            employees);

        sortEmployeeDayoffInfos(searchCondition, employeeDayoffInfos);

        List<EmployeeDayoffInfo> result = employeeDayoffInfos.stream()
            .skip(searchCondition.getPageOffset())
            .limit(searchCondition.getPageSize())
            .collect(Collectors.toList());

        return new PageInfo<>(
            employeeDayoffInfos.size(),
            searchCondition,
            result
        );
    }

    /**
     * 리스트를 정렬 조건에 따라 정렬 정렬조건이 전체,소모,남은 연차 순일 경우 사용
     *
     * @param searchCondition
     * @param infos
     */
    private void sortEmployeeDayoffInfos(SearchCondition searchCondition,
        List<EmployeeDayoffInfo> infos) {

        Comparator<Double> directionComparator = Comparator.naturalOrder();

        //오름차순, 내림차순 정렬
        if (searchCondition.getDirection() != null &&
            searchCondition.getDirection().equalsIgnoreCase("DESC")) {
            directionComparator = Comparator.<Double>reverseOrder();
        }

        Function<EmployeeDayoffInfo, Double> function = null;

        //어떤것을 정렬할지 판별
        switch (searchCondition.getSortQuery()) {
            case "totalDayoffCount":
                function = EmployeeDayoffInfo::getTotalDayoffCount;
                break;
            case "usedDayoffCount":
                function = EmployeeDayoffInfo::getUsedDayoffCount;
                break;
            case "remainingDayoffCount":
                function = EmployeeDayoffInfo::getRemainingDayoffCount;
                break;
        }

        if (function != null) {
            infos.sort(Comparator.comparing(function, Comparator.nullsLast(directionComparator)));
        }
    }

    /**
     * 연차 수치 정렬외 사용
     *
     * @param searchCondition
     * @return
     */
    private PageInfo<EmployeeDayoffInfo> getSortCommonEmployeeDayoffCalculateInfos(
        SearchCondition searchCondition) {

        //사원목록 페이징 호출
        List<Employee> employees = employeeService.findAllEmployeesBySearchCondition(
            searchCondition);

        List<EmployeeDayoffInfo> employeeDayoffInfos = getEmployeeDayoffInfos(searchCondition,
            employees);

        if (employeeDayoffInfos.size() == searchCondition.getPageSize()) {
            Integer count = employeeService.countAllEmployees();
            if (count == null) {
                count = 0;
            }
            return new PageInfo<>(count, searchCondition, employeeDayoffInfos);
        }

        return new PageInfo<>(searchCondition, employeeDayoffInfos);
    }

    /**
     * 정렬 전 데이터 가공, 받은 사원들에 해당하는 전체,사용,남은 연차 정보 제공
     *
     * @param searchCondition
     * @param employees
     * @return
     */
    private List<EmployeeDayoffInfo> getEmployeeDayoffInfos(SearchCondition searchCondition,
        List<Employee> employees) {
        //사원-지급연차 맵 가공
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
        return employees
            .stream()
            .map((employee -> createEmployeeDayoffInfo(employee, employeePaidDayoffMap,
                employeeUsedDayoffMap)))
            .collect(Collectors.toList());
    }

    /**
     * 정렬전 데이터 가공시 사용하는 전체,사용,남은 연차 수치 판별
     *
     * @param employee
     * @param employeePaidDayoffMap
     * @param employeeUsedDayoffMap
     * @return
     */
    private EmployeeDayoffInfo createEmployeeDayoffInfo(Employee employee,
        Map<String, Double> employeePaidDayoffMap,
        Map<String, Double> employeeUsedDayoffMap
    ) {
        //기본값
        Double totalDayoffCount = null;
        Double usedDayoffCount = null;
        Double remaingDayoffCount = null;

        boolean isEmployee = EmployeeUtil.isEmployee(employee);

        //직원일 경우 계산
        if (isEmployee) {
            if (employeePaidDayoffMap.get(employee.getEmployeeNo()) != null) {
                totalDayoffCount = employeePaidDayoffMap.get(employee.getEmployeeNo());
            }

            if (totalDayoffCount != null
                && employeeUsedDayoffMap.get(employee.getEmployeeNo()) != null) {
                usedDayoffCount = employeeUsedDayoffMap.get(employee.getEmployeeNo());
            }

            if (totalDayoffCount != null && usedDayoffCount != null) {
                remaingDayoffCount = totalDayoffCount - usedDayoffCount;
            }
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
