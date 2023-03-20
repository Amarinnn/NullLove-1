package com.loven.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
	private String id;
	private String pw;
	private String name;
	private String nick;
	private String email;
	private Date reg_date;
	private String login_type;
	private String company_name;
	private List<BlindVO> blindVOList;

}
