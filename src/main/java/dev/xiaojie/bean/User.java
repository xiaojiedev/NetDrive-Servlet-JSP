package dev.xiaojie.bean;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {
	private int id;
	private String username;
	private String password;
	private int isAdmin;

	public User() {
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
}
