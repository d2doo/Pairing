package com.ssafy.i10a709be.domain.log.dto;


import lombok.Data;

import java.util.Date;

@Data
public class LogDto {
    public Long id;
    public String content;
    public Date date;
    public LogType type;
    public String ip;

    public LogDto( String content, Date date, String ip ) {
        this.content = content;
        this.date = date;
        this.ip = ip;
    }

}
