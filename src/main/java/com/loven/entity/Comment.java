package com.loven.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Comment {

    private int cmt_seq;
    private String content;
    private Date reg_date;
    private int seq;
    private String id;
    private String nick;
}
