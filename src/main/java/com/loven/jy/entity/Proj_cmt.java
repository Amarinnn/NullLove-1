package com.loven.jy.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
public class Proj_cmt {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//pk이면서 자동증가칼럼
	private int cmt_seq;
	
	private int seq;
	private String content;
	private String id;
	
	@Column(columnDefinition ="datetime default now()")
	private Date reg_date;
}
