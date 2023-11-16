package com.emgram.kr.dobby.service;

import com.emgram.kr.dobby.dao.HolidayWorkDao;
import com.emgram.kr.dobby.dto.PageInfo;
import com.emgram.kr.dobby.dto.SearchCondition;
import com.emgram.kr.dobby.dto.dashboard.HolidayDashBoardDTO;
import com.emgram.kr.dobby.dto.holiday.work.HolidayWork;
import com.emgram.kr.dobby.dto.holiday.work.HolidayWorkDto;
import com.emgram.kr.dobby.utils.DateUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HolidayWorkService {

    private final HolidayWorkDao holidayWorkDao;

    public long countWorkDayByYearAndEmployeeNo(String employeeNo, int year) {
        if (employeeNo == null) {
            throw new RuntimeException("검색조건이 들어오지 않았습니다.");
        }
        return holidayWorkDao.countWorkDayByYearAndEmployeeNo(employeeNo,
            DateUtil.getStartDayOfYear(year),
            DateUtil.getEndDayOfYear(year));
    }

    public PageInfo<HolidayWorkDto> getWorkDays(SearchCondition searchCondition) {
        if (searchCondition == null) {
            throw new RuntimeException("검색조건이 없습니다.");
        }

        List<HolidayWorkDto> list = holidayWorkDao.findAllHolidayWorkBySearchCondition(
            searchCondition);
        if (list.size() == searchCondition.getPageSize()) {
            Integer count = holidayWorkDao.countFindAllHolidayWorkBySearchCondition(
                searchCondition);
            return new PageInfo<>(count, searchCondition, list);
        }
        return new PageInfo<>(searchCondition, list);
    }

    public void saveWorkDays(HolidayWork holidayWork) {
        if (holidayWork == null) {
            throw new RuntimeException("휴일 출근 저장정보가 들어오지 않앗습니다.");
        }

        int result = holidayWorkDao.saveHolidayWork(holidayWork);

        if (result == 0) {
            throw new RuntimeException("휴일 출근이 입력되지 않았습니다.");
        }
    }

    public void updateWorkDay(Long holidayWorkId, HolidayWorkDto holidayWorkDto) {
        if (holidayWorkId == null) {
            throw new RuntimeException("휴일 출근 수정 id가 들어오지 않앗습니다.");
        }
        if (holidayWorkDto == null) {
            throw new RuntimeException("휴일 출근 수정정보가 들어오지 않앗습니다.");
        }

        int result = holidayWorkDao.updateHolidayWork(holidayWorkId, holidayWorkDto);

        if (result == 0) {
            throw new RuntimeException("휴일 출근이 수정되지 않았습니다. 정확한 id가 맞는지 확인해 주세요");
        }
    }

    public void deleteWorkDay(Long holidayWorkId) {
        if (holidayWorkId == null) {
            throw new RuntimeException("휴일 출근 삭제 id가 들어오지 않앗습니다.");
        }

        int result = holidayWorkDao.deleteHolidayWork(holidayWorkId);

        if (result == 0) {
            throw new RuntimeException("휴일 출근이 삭제되지 않았습니다. id가 맞는지 확인해 주세요");
        }
    }

    public HolidayDashBoardDTO getMuchHolidayWorker(int year) {
        return holidayWorkDao.findMuchHolidayWorker(year);
    }

    public int countAllHolidayWorkByYear(int year) {
        Integer result = holidayWorkDao.countAllHolidayWorkByYear(year);
        return result == null ? 0 : result;
    }
}
