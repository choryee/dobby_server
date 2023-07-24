package com.emgram.kr.dobby.dto.caldav;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
@Getter
public class CaldavEventData {
    public String calscale;
    public Vtimezone vtimezone;
    public Vevent vevent;
    public String prodid;
    public String version;
    @Getter
    public static class Vtimezone{
        public Standard standard;
        public String tzurl;
        public String tzid;
        public String xLicLocation;
        public String lastModified;
    }
    @Getter
    public static class Vevent{
        public String summary;
        public String created;
        public String description;
        public String priority;
        public String uid;
        public String sequence;
        public XNaverLastModifier xNaverLastModifier;
        public String location;
        public XNaverRegisterer xNaverRegisterer;
        public String lastModified;
        public Dtend dtend;
        @JsonProperty("class")
        public String myclass;
        public Dtstart dtstart;
        public String dtstamp;
        public String transp;
        public String xNaverCategoryId;
    }
    @Getter
    public static class XNaverLastModifier{
        public String val;
        public String xWorksmobileWid;
        public String cutype;
        public String cn;
    }
    @Getter
    public static class XNaverRegisterer{
        public String val;
        public String xWorksmobileWid;
        public String cutype;
        public String cn;
    }
    @Getter
    public static class Dtstart{
        public String val;
        public String tzid;
        public String value;
    }
    @Getter
    public static class Dtend{
        public String val;
        public String tzid;
        public String value;
    }
    @Getter
    public static class Standard{
        public String tzoffsetto;
        public String tzoffsetfrom;
        public String tzname;
        public String dtstart;
    }
}
