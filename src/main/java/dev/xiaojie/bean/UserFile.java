package dev.xiaojie.bean;

import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@ToString
public class UserFile {
	private int id;
	private String filename;
	private String path;
	private Timestamp createTime;
	private int isShared;
	private int ownerId;
	private String fileSize;
	private int count;
	private User owner;
}
