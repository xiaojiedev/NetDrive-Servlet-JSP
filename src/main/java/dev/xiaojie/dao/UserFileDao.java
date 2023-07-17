package dev.xiaojie.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dev.xiaojie.bean.UserFile;
import dev.xiaojie.util.DBInfo;

//数据库 用户文件操作相关方法
public class UserFileDao {

	private DBInfo db = DBInfo.getInstance();
	private UserDao userDao = new UserDao();

	//根据用户ID查找上传的文件
	public UserFile findUserFileById(int id){
		Connection conn = db.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		UserFile userFile = null;
		try {
			ps = conn.prepareStatement("select * from file where id=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while(rs.next()){
				userFile = new UserFile();
				userFile.setId(rs.getInt(1));
				userFile.setFilename(rs.getString(2));
				userFile.setPath(rs.getString(3));
				userFile.setCreateTime(rs.getTimestamp(4));
				userFile.setIsShared(rs.getInt(5));
				userFile.setOwnerId(rs.getInt(6));
				userFile.setFileSize(rs.getString(7));
				userFile.setCount(rs.getInt(8));
				userFile.setOwner(userDao.findUserById(userFile.getOwnerId()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(conn!=null)
					conn.close();
				if(ps!=null)
					ps.close();
				if(rs!=null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return userFile;
	}

	//修改文件状态
	public void update(UserFile userFile){
		Connection conn = db.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("update file set filename=?,count=?,isShared=? where id=?");
			ps.setString(1, userFile.getFilename());
			ps.setInt(2, userFile.getCount());
			ps.setInt(3, userFile.getIsShared());
			ps.setInt(4, userFile.getId());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(conn!=null)
					conn.close();
				if(ps!=null)
					ps.close();
				if(rs!=null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	//根据用户ID查找文件列表
	public List<UserFile> findFileListByOwnerId(int ownerId){
		List<UserFile> fileList = new ArrayList<UserFile>();
		Connection conn = db.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		UserFile userFile = null;
		try {
			ps = conn.prepareStatement("select * from file where ownerid=?");
			ps.setInt(1, ownerId);
			rs = ps.executeQuery();
			while(rs.next()){
				userFile = new UserFile();
				userFile.setId(rs.getInt(1));
				userFile.setFilename(rs.getString(2));
				userFile.setPath(rs.getString(3));
				userFile.setCreateTime(rs.getTimestamp(4));
				userFile.setIsShared(rs.getInt(5));
				userFile.setOwnerId(rs.getInt(6));
				userFile.setFileSize(rs.getString(7));
				userFile.setCount(rs.getInt(8));
				fileList.add(userFile);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(conn!=null)
					conn.close();
				if(ps!=null)
					ps.close();
				if(rs!=null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return fileList;
	}

	//查找所有被共享的文件
	public List<UserFile> findSharedFile(){
		List<UserFile> fileList = new ArrayList<UserFile>();
		Connection conn = db.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		UserFile userFile = null;
		try {
			ps = conn.prepareStatement("select * from file where isshared=1 order by count desc");
			rs = ps.executeQuery();
			while(rs.next()){
				userFile = new UserFile();
				userFile.setId(rs.getInt(1));
				userFile.setFilename(rs.getString(2));
				userFile.setPath(rs.getString(3));
				userFile.setCreateTime(rs.getTimestamp(4));
				userFile.setIsShared(rs.getInt(5));
				userFile.setOwnerId(rs.getInt(6));
				userFile.setFileSize(rs.getString(7));
				userFile.setCount(rs.getInt(8));
				userFile.setOwner(userDao.findUserById(userFile.getOwnerId()));
				fileList.add(userFile);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(conn!=null)
					conn.close();
				if(ps!=null)
					ps.close();
				if(rs!=null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return fileList;
	}

	//根据用户ID查找共享的文件
	public List<UserFile> findMySharedFile(int id){
		List<UserFile> fileList = new ArrayList<UserFile>();
		Connection conn = db.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		UserFile userFile = null;
		try {
			ps = conn.prepareStatement("select * from file where isshared=1 and ownerid = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while(rs.next()){
				userFile = new UserFile();
				userFile.setId(rs.getInt(1));
				userFile.setFilename(rs.getString(2));
				userFile.setPath(rs.getString(3));
				userFile.setCreateTime(rs.getTimestamp(4));
				userFile.setIsShared(rs.getInt(5));
				userFile.setOwnerId(rs.getInt(6));
				userFile.setFileSize(rs.getString(7));
				userFile.setCount(rs.getInt(8));
				userFile.setOwner(userDao.findUserById(userFile.getOwnerId()));
				fileList.add(userFile);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(conn!=null)
					conn.close();
				if(ps!=null)
					ps.close();
				if(rs!=null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return fileList;
	}

	//新增上传文件
	public void save(UserFile userFile) {
		Connection conn = db.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("insert into file values(NULL,?,?,?,?,?,?,?)");
			ps.setString(1, userFile.getFilename());
			ps.setString(2, userFile.getPath());
			ps.setTimestamp(3, userFile.getCreateTime());
			ps.setInt(4, userFile.getIsShared());
			ps.setInt(5, userFile.getOwnerId());
			ps.setString(6, userFile.getFileSize());
			ps.setInt(7, userFile.getCount());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(conn!=null)
					conn.close();
				if(ps!=null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	//根据文件ID列表获取文件对象
	public List<UserFile> findByIds(int[] ids){
		if(ids == null || ids.length <=0)
			return null;
		List<UserFile> fileList = new ArrayList<UserFile>();
		Connection conn = db.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		UserFile userFile = null;
		try {
			StringBuffer sb = new StringBuffer();
			for(int i = 0 ; i < ids.length; i++){
				if(i == ids.length - 1){
					sb.append("?");
				}else{
					sb.append("?,");
				}
			}
			String in = sb.toString();
			ps = conn.prepareStatement("select * from file where id in ("+in+")");
			for(int i = 0 ; i < ids.length; i++){
				ps.setInt(i+1, ids[i]);
			}
			rs = ps.executeQuery();
			while(rs.next()){
				userFile = new UserFile();
				userFile.setId(rs.getInt(1));
				userFile.setFilename(rs.getString(2));
				userFile.setPath(rs.getString(3));
				userFile.setCreateTime(rs.getTimestamp(4));
				userFile.setIsShared(rs.getInt(5));
				userFile.setOwnerId(rs.getInt(6));
				userFile.setFileSize(rs.getString(7));
				userFile.setCount(rs.getInt(8));
				userFile.setOwner(userDao.findUserById(userFile.getOwnerId()));
				fileList.add(userFile);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(conn!=null)
					conn.close();
				if(ps!=null)
					ps.close();
				if(rs!=null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return fileList;
	}

	//根据文件ID删除一个或多个文件
	public void deleteByIds(int[] ids){
		if(ids == null || ids.length <=0)
			return;
		List<UserFile> fileLists = findByIds(ids);
		//先删除磁盘文件，再删除数据库记录
		deleteFileOnDisk(fileLists);
		Connection conn = db.getConnection();
		PreparedStatement ps = null;
		try {
			StringBuffer sb = new StringBuffer();
			for(int i = 0 ; i < ids.length; i++){
				if(i == ids.length - 1){
					sb.append("?");
				}else{
					sb.append("?,");
				}
			}
			String in = sb.toString();
			ps = conn.prepareStatement("delete from file where id in ("+in+")");
			for(int i = 0 ; i < ids.length; i++){
				ps.setInt(i+1, ids[i]);
			}
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(conn!=null)
					conn.close();
				if(ps!=null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	//删除物理磁盘的文件
	private void deleteFileOnDisk(List<UserFile> fileLists) {
		File file;
		for(UserFile userFile:fileLists){
			file = new File(userFile.getPath());
			if(file.exists()){
				file.delete();
			}
		}
	}

	//根据文件名模糊查询分享的文件
	public List<UserFile> findSharedFileWithName(String filename) {
		List<UserFile> fileList = new ArrayList<UserFile>();
		Connection conn = db.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		UserFile userFile = null;
		try {
			ps = conn.prepareStatement("select * from file where isshared=1 and filename like ? order by count desc");
			ps.setString(1, "%"+filename+"%");
			rs = ps.executeQuery();
			while(rs.next()){
				userFile = new UserFile();
				userFile.setId(rs.getInt(1));
				userFile.setFilename(rs.getString(2));
				userFile.setPath(rs.getString(3));
				userFile.setCreateTime(rs.getTimestamp(4));
				userFile.setIsShared(rs.getInt(5));
				userFile.setOwnerId(rs.getInt(6));
				userFile.setFileSize(rs.getString(7));
				userFile.setCount(rs.getInt(8));
				userFile.setOwner(userDao.findUserById(userFile.getOwnerId()));
				fileList.add(userFile);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(conn!=null)
					conn.close();
				if(ps!=null)
					ps.close();
				if(rs!=null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return fileList;
	}

	//查询所有文件
	public List<UserFile> findAll() {
		List<UserFile> fileList = new ArrayList<UserFile>();
		Connection conn = db.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		UserFile userFile = null;
		try {
			ps = conn.prepareStatement("select * from file order by count desc");
			rs = ps.executeQuery();
			while(rs.next()){
				userFile = new UserFile();
				userFile.setId(rs.getInt(1));
				userFile.setFilename(rs.getString(2));
				userFile.setPath(rs.getString(3));
				userFile.setCreateTime(rs.getTimestamp(4));
				userFile.setIsShared(rs.getInt(5));
				userFile.setOwnerId(rs.getInt(6));
				userFile.setFileSize(rs.getString(7));
				userFile.setCount(rs.getInt(8));
				userFile.setOwner(userDao.findUserById(userFile.getOwnerId()));
				fileList.add(userFile);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(conn!=null)
					conn.close();
				if(ps!=null)
					ps.close();
				if(rs!=null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return fileList;
	}

	//根据文件名模糊查询所有文件
	public List<UserFile> findAllWithName(String filename) {
		List<UserFile> fileList = new ArrayList<UserFile>();
		Connection conn = db.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		UserFile userFile = null;
		try {
			ps = conn.prepareStatement("select * from file where filename like ? order by count desc");
			ps.setString(1, "%"+filename+"%");
			rs = ps.executeQuery();
			while(rs.next()){
				userFile = new UserFile();
				userFile.setId(rs.getInt(1));
				userFile.setFilename(rs.getString(2));
				userFile.setPath(rs.getString(3));
				userFile.setCreateTime(rs.getTimestamp(4));
				userFile.setIsShared(rs.getInt(5));
				userFile.setOwnerId(rs.getInt(6));
				userFile.setFileSize(rs.getString(7));
				userFile.setCount(rs.getInt(8));
				userFile.setOwner(userDao.findUserById(userFile.getOwnerId()));
				fileList.add(userFile);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(conn!=null)
					conn.close();
				if(ps!=null)
					ps.close();
				if(rs!=null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return fileList;
	}
}
