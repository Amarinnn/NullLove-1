package com.loven.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BlindVO {
	private int seq;
	private String title;
	private String content;
	private String file;
	private Date date;
	private int cnt;
	private int likes;
	private char order;
	private String id;
	private String nick;
	private String count;
}
