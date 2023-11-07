package com.emgram.kr.dobby.properties;

import javax.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@Getter
public class HolidayProperties {
    @Value("${dobby.api.pd.holiday.url}")
    private String holidayAPIURL;

    @Value("${dobby.api.pd.holiday.operation.rest}")
    private String restDayOperation;

    @Value("${dobby.api.pd.key}")
    private String serviceKey;

    @PostConstruct
    private void validateProperties() {
        Assert.notNull(holidayAPIURL, "HolidayService.holidayAPIURL is null");
        Assert.notNull(restDayOperation, "HolidayService.restDayOperation is null");
        Assert.notNull(serviceKey, "HolidayService.serviceKey is null");
    }
}
