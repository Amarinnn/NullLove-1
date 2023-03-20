package com.loven.jy.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//pk이면서 자동증가칼럼
	private int seq;
	private String title;
	private String content;
	@Column(columnDefinition ="datetime default now()")
	private Date date;
	@Column(columnDefinition = "int default 0")
	private int cnt;
	private Long likes;
	private char notice;
	private String id;
	private int count;
	private String file;

}
