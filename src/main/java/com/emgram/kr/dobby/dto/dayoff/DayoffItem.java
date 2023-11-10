package com.emgram.kr.dobby.dto.dayoff;

import lombok.Getter;

import java.util.Date;

@Getter
public class DayoffItem {
    private int dayoff_idx;
    private String employee_no;
    private int event_idx;
    private String dayoff_type;
    private Date dayoff_dt;
    private String dayoff_reason;
    private String approver_no;
    private Date approval_dt;
    private String approval_msg;
    private String memo;
    private Date create_dt;
    private String creator_id;
    private Date update_dt;
    private String updator_id;

    public DayoffItem() {}

    public DayoffItem(int event_idx, String dayoff_type, Date dayoff_dt, Date create_dt, String creator_id, Date update_dt, String updator_id) {
        this.employee_no = creator_id;
        this.event_idx = event_idx;
        this.dayoff_type = dayoff_type;
        this.dayoff_dt = dayoff_dt;
        this.create_dt = create_dt;
        this.creator_id = creator_id;
        this.update_dt = update_dt;
        this.updator_id = updator_id;
    }


}
