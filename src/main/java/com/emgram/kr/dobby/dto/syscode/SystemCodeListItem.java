package com.emgram.kr.dobby.dto.syscode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SystemCodeListItem {

    private String codeGroup;

    private String codeId;

    private String codeName;

    private String codeValue;
}
