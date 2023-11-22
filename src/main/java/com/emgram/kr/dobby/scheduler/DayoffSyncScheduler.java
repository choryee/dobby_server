//package com.emgram.kr.dobby.scheduler;
//
//import com.emgram.kr.dobby.dto.caldav.CaldavClient;
//import com.emgram.kr.dobby.dto.caldav.CaldavResult;
//import com.emgram.kr.dobby.service.CaldavService;
//import com.emgram.kr.dobby.service.DayoffService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//@Slf4j
//@Component
//@EnableAsync
//public class DayoffSyncScheduler {
//    private DayoffService dayoffService;
//
//    @Autowired
//    public DayoffSyncScheduler(DayoffService dayoffService){
//        this.dayoffService = dayoffService;
//    }
//
//    @Scheduled(cron = "0/10 * * * * ?")
//    public void scheduleTaskSyncDayoff(){
//        /**
//         * 연차 사용 내역 테이블 업데이트 로직
//         *  [추가]
//         *  1. caldav_event_info에 있고 dayoff_hisotry 에 없는거 가져오기
//         *  2. start_dt, end_dt 기준으로 데이터 생성
//         *  3. dayoff_history 에 저장
//         *
//         *  [삭제]
//         *  1. caldav_event_info.delete_dt is not null 데이터 전부 삭제
//         *  2. caldav_event_info.update_dt != dayoff_history.update_dt 데이터 전부 삭제
//         *
//         *  [수정]
//         *  1. [삭제.1] 후 [추가]
//         */
//
//        log.info("[연차 사용 내역 업데이트] ----- 시작");
//
//        log.info(String.format(
//                "[연차 사용 내역 업데이트]\t삭제된 연차 [%s]"
//                , dayoffService.deleteDayoff()
//        ));
//        log.info(String.format(
//                "[연차 사용 내역 업데이트]\t추가된 연차 [%s]"
//                , dayoffService.insertDayoff()
//        ));
//
//        log.info("[연차 사용 내역 업데이트] ----- 종료\n");
//    }
//}
