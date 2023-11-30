//package com.emgram.kr.dobby.scheduler;
//
//import com.emgram.kr.dobby.dao.CaldavDao;
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
//public class CaldavSyncScheduler {
//    @Value("${caldav.url}")
//    private String hostname;
//
//    @Value("${caldav.port}")
//    private int port;
//
//    @Value("${caldav.id}")
//    private String username;
//
//    @Value("${caldav.pw}")
//    private String password;
//
//    @Value("${caldav.calendar.id}")
//    private String calendarId;
//
//    @Value("${caldav.start.date}")
//    private String calendarStartDate;
//
//    private CaldavService caldavService;
//
//    @Autowired
//    public CaldavSyncScheduler(CaldavService caldavService){
//        this.caldavService = caldavService;
//    }
//
//    @Scheduled(cron = "0/10 * * * * ?")
//    public void scheduleTaskSyncCaldav() throws ParseException {
//        log.info("[캘린더 동기화] ----- 시작");
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date startDate = sdf.parse(calendarStartDate);
//        CaldavClient client = new CaldavClient(hostname, port, username, password, calendarId);
//        CaldavResult result = caldavService.CaldavSyncRun(client, startDate);
//        log.info(String.format("[캘린더 동기화]\t%s", result.getCalendarStatus()));
//        if(result.getEventStatus() != null){
//            log.info(String.format("[캘린더 동기화]\t%s", result.getEventStatus().getInsert()));
//            log.info(String.format("[캘린더 동기화]\t%s", result.getEventStatus().getUpdate()));
//            log.info(String.format("[캘린더 동기화]\t%s", result.getEventStatus().getDelete()));
//        }
//        log.info("[캘린더 동기화] ----- 종료\n");
//    }
//}
