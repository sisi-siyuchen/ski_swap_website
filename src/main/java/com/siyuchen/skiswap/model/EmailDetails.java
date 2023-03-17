package com.siyuchen.skiswap.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailDetails {
    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;
}
