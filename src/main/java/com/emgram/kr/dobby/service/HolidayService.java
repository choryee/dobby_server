package com.emgram.kr.dobby.service;

import com.emgram.kr.dobby.dao.HolidayDao;
import com.emgram.kr.dobby.dto.holiday.HolidayAPIResponse;
import com.emgram.kr.dobby.dto.holiday.HolidayAPIResponse.HolidayAPIItem;
import com.emgram.kr.dobby.dto.holiday.HolidayDto;
import com.emgram.kr.dobby.dto.holiday.VerifyHolidayDto;
import com.emgram.kr.dobby.properties.HolidayProperties;
import com.emgram.kr.dobby.utils.DateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
@Slf4j
public class HolidayService {

    private final HolidayProperties properties;

    private final OkHttpClient okHttpClient;

    private final ObjectMapper holidayApiObjectMapper;

    private final HolidayDao holidayDao;

    @PostConstruct
    public void validateProperties() {
        Assert.notNull(properties, "HolidayService.properties is null");
        Assert.notNull(okHttpClient, "HolidayService.okHttpClient is null");
        Assert.notNull(holidayApiObjectMapper, "HolidayService.objectMapper is null");
    }

    public List<VerifyHolidayDto> getHolidays(LocalDate startDate, LocalDate endDate) {
        return holidayDao.findAllHolidayBetweenDate(startDate, endDate)
            .stream().map(VerifyHolidayDto::of).collect(Collectors.toList());
    }

    public boolean isDayHoliday(LocalDate date) {
        return holidayDao.findAllHolidayBetweenDate(date, date).size() > 0;
    }

    public void reRegisterHolidays(int year) throws Exception {
        holidayDao.deleteBetweenDate(DateUtil.getStartDayOfYear(year), DateUtil.getEndDayOfYear(year));
        saveHolidays(year);
    }

    public void registerHolidays(int year) {
        try {
            saveHolidays(year);
        } catch (Exception e){
            log.warn(String.format("%d 년도 휴일 수집이 이미 완료 되었습니다.", year));
        }
    }

    public List<HolidayDto> saveHolidays(int year) throws Exception {
        String responseBody = callHolidayAPI(year);

        if (responseBody == null) throw new RuntimeException("휴무일 API 조회에 실패하였습니다.");

        List<HolidayDto> holidayDtos = resolveHolidays(responseBody);

        if (holidayDtos.size() == 0) return null;

        holidayDao.insertHolidays(holidayDtos);

        return holidayDtos;
    }

    private String callHolidayAPI(int year) {
        HttpUrl httpUrl = getHolidayRequestURL(year);
        log.info(String.format("request URI : %s", httpUrl.url()) );
        Request request = new Request.Builder().url(httpUrl)
            .header("Content-Type", "application/json")
            .header("Accept", "*/*;q=0.9")
            .build();

        try (Response response = okHttpClient.newCall(request).execute()){
            ResponseBody responseBody = response.body();
            if (responseBody == null) return null;
            BufferedSource bufferedSource = responseBody.source();
            return bufferedSource.readString(StandardCharsets.UTF_8);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<HolidayDto> resolveHolidays(String body) {
        List<HolidayDto> holidayDtos = new ArrayList<>();

        try {
            HolidayAPIResponse holidayAPIResponse = holidayApiObjectMapper.readValue(body, HolidayAPIResponse.class);

            return holidayAPIResponse.getResponse().getBody().getItems().getItem().stream().map(
                HolidayAPIItem::toHolidayDto)
                .collect(Collectors.toList());

        } catch (JsonProcessingException e) {
            log.warn(String.format("holiday 추출 실패 : %s", body));
            return holidayDtos;
        }
    }

    private HttpUrl getHolidayRequestURL(int year) {
        String holidayAPIURL = properties.getHolidayAPIURL();
        String operation = properties.getRestDayOperation();
        String serviceKey = properties.getServiceKey();

        HttpUrl httpUrl = HttpUrl.parse(String.format("%s/%s",
            holidayAPIURL,
            operation));

        if (httpUrl == null) throw new RuntimeException("holiday URL Parse Fail");

        return httpUrl.newBuilder()
            .addQueryParameter("solYear", String.valueOf(year))
            .addQueryParameter("ServiceKey", serviceKey)
            .addQueryParameter("_type","json")
            .addQueryParameter("numOfRows","100")
            .build();
    }
}
