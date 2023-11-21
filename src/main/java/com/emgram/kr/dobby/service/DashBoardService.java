package com.emgram.kr.dobby.service;

import com.emgram.kr.dobby.dao.DayoffDao;
import com.emgram.kr.dobby.dto.dashboard.DashBoardDayoffDTO;
import com.emgram.kr.dobby.dto.dashboard.HolidayDashBoardDTO;
import com.emgram.kr.dobby.dto.employee.Employee;
import com.emgram.kr.dobby.dto.holiday.HolidayDto;
import com.emgram.kr.dobby.dto.holiday.VerifyHolidayDto;
import com.emgram.kr.dobby.utils.DateUtil;
import com.emgram.kr.dobby.utils.EmployeeUtil;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DashBoardService {

    private final DayoffDao dayoffDao;

    private final HolidayService holidayService;

    private final EmployeeService employeeService;

    private final HolidayWorkService holidayWorkService;

    @Transactional
    public Map<String, Object> getDashBoardInfo(int year) {
        //정보를 구성할 Map 생성
        Map<String, Object> info = new HashMap<>();
        //기반 날짜 추출
        LocalDate startDate = DateUtil.getStartDayOfYear(year);
        LocalDate endDate = DateUtil.getEndDayOfYear(year);

        //날짜를 기반으로 공휴일 목록 호출
        List<VerifyHolidayDto> holidayDtoList = holidayService.getHolidays(startDate, endDate);

        //날짜를 기반으로 모든 연차 목록 호출
        List<DashBoardDayoffDTO> dayoffItems = getDashboardDayoffItem(startDate, endDate)
            .stream().filter(EmployeeUtil::isEmployee).collect(Collectors.toList());

        //전 사원 목록 추출
        List<Employee> employees = employeeService.getAllEmployeeList()
            .stream().filter(EmployeeUtil::isEmployee).collect(Collectors.toList());

        //전 사원 지급 연차 맵 추출
        Map<String, Double> employeePaidDayoffMap = getAllEmployeePaidDayoffMap(employees, year);

        //전 사원 사용 연차 맵 추출
        Map<String, Double> employeeUsedDayoffMap = getAllEmployeeUsedDayoffMap(dayoffItems, holidayDtoList);

        //전 사원 남은연차, 사용연차 정보 등록
        registerAllEmployeeDayoffCount2Map(info, employeePaidDayoffMap, employeeUsedDayoffMap);

        //최근 연차자 정보 등록
        registerRecentUseDayoff(info, dayoffItems);

        //가장 많은 연차를 가진 사람 정보 등록
        registerMostRemainingDayoff(info, employees, employeePaidDayoffMap, employeeUsedDayoffMap);

        //가장 많은 휴일 출근자 등록
        registerMuchHolidayWorker(info, year);

        //해당 년도 총 휴일 출근 일 수 등록
        registerTotalHolidayWorkCount(info, year);
        return info;
    }

    public List<DashBoardDayoffDTO> getDashboardDayoffItem(LocalDate startDate, LocalDate endDate) {
        return dayoffDao.findAllDayOffByYear(startDate, endDate);
    }

    private void registerMuchHolidayWorker(Map<String, Object> info, int year) {
        HolidayDashBoardDTO dashBoardDTO = holidayWorkService.getMuchHolidayWorker(year);
        if (dashBoardDTO == null) {
            info.put("muchHolidayWorkerName", "");
            info.put("muchHolidayWorkerWorkDateCount", 0);
            return;
        }
        info.put("muchHolidayWorkerName", dashBoardDTO.getName());
        info.put("muchHolidayWorkerWorkDateCount", dashBoardDTO.getHolidayWorkDateCount());
    }

    private void registerTotalHolidayWorkCount(Map<String, Object> info, int year) {
        int result = holidayWorkService.countAllHolidayWorkByYear(year);
        info.put("totalHolidayWorkCount", result);
    }

    private void registerMostRemainingDayoff (Map<String, Object> info, List<Employee> employees,
        Map<String, Double> employeePaidVacationMap, Map<String, Double> employeeUsedVacationMap) {

        double max = 0;
        Employee maxDayoffRemainingEmployee = null;
        for (Employee employee : employees){
            if (employeePaidVacationMap.containsKey(employee.getEmployeeNo())) {
                double remainDayOffCount = employeePaidVacationMap.get(employee.getEmployeeNo());
                if (employeeUsedVacationMap.containsKey(employee.getEmployeeNo())) {
                    remainDayOffCount -= employeeUsedVacationMap.get(employee.getEmployeeNo());
                }

                if (max < remainDayOffCount) {
                    maxDayoffRemainingEmployee = employee;
                    max = remainDayOffCount;
                }
            }
        }

        info.put("mostRemainingDayoffCount", max);
        info.put("mostRemainingDayoffName", maxDayoffRemainingEmployee == null || maxDayoffRemainingEmployee.getName() == null ? "" : maxDayoffRemainingEmployee.getName());
    }

    public Map<String, Double> getAllEmployeePaidDayoffMap(List<Employee> employees, int year) {
        Map<String, Double> map = new HashMap<>();

        for (Employee employee : employees) {
            map.put(employee.getEmployeeNo(), dayoffCalculationService.calculateTotalVacation(employee, year));
        }

        return map;
    }

    public Map<String, Double> getAllEmployeeUsedDayoffMap(List<DashBoardDayoffDTO> dayoffItems, List<? extends HolidayDto> holidayDtoList) {
        //주말, 공휴일, 연차, 사원인지 필터
        Stream<DashBoardDayoffDTO> filteredDayoffItemStream = dayoffItems
            .stream()
            .filter(this::dayOffCheck)
            .filter((item) -> !DateUtil.isHoliday(item.getDayoffDt(), holidayDtoList))
            .filter((item) -> !DateUtil.isWeekend(item.getDayoffDt()));

        Map<String, Double> map = new HashMap<>();

        filteredDayoffItemStream.forEach(
            (item) -> {
                if (map.containsKey(item.getEmployeeNo())) {
                    map.put(item.getEmployeeNo(), map.get(item.getEmployeeNo()) + item.getCodeVal());
                }
                else {
                    map.put(item.getEmployeeNo(), item.getCodeVal());
                }
            }
        );

        return map;
    }


    private void registerRecentUseDayoff(
        Map<String, Object> info,
        List<DashBoardDayoffDTO> dayoffItems) {

        DashBoardDayoffDTO dashBoardDayoffDTO = dayoffItems.stream()
            .min((item1, item2) -> DateUtil.dateToInt(item2.getCreateDt()) - DateUtil.dateToInt(item1.getCreateDt()))
            .orElseGet(DashBoardDayoffDTO::new);

        info.put("recentUseDayoffCreateDt", dashBoardDayoffDTO.getCreateDt());
        info.put("recentUseDayoffDate", dashBoardDayoffDTO.getDayoffDt());
        info.put("recentUseDayoffName", dashBoardDayoffDTO.getName());
    }

    private void registerAllEmployeeDayoffCount2Map(Map<String, Object> info,
        Map<String, Double> employeePaidVacation,
        Map<String, Double> employeeUseVacationMap
        ) {

        //사용한 총 연차 값
        double totalUseDayOffCount = employeeUseVacationMap.values().stream()
            .reduce((double) 0, Double::sum);

        double totalDayOffCount = 0;

        //전 사원 지급 연차 합계
        for (Entry<String, Double> item : employeePaidVacation.entrySet()){
            totalDayOffCount += item.getValue();
        }

        info.put("totalRemainingDayoffCount", totalDayOffCount - totalUseDayOffCount);
        info.put("totalUseDayoffCount", totalUseDayOffCount);
    }

    private boolean dayOffCheck(DashBoardDayoffDTO item) {
        int dayoffType = Integer.parseInt(item.getDayoffType());
        return dayoffType >= 1000 && dayoffType < 2000;
    }

}
