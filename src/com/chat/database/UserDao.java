package com.chat.database;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.chat.bean.FriendList;
import com.chat.bean.LoginReturnType;
import com.chat.bean.Message;
import com.chat.bean.MessageContent;
import com.chat.bean.User;
import com.chat.bean.UserInformation;
import com.chat.util.GetPhotoBytes;
import com.chat.util.Utils;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.sun.xml.internal.ws.util.ByteArrayBuffer;

public class UserDao implements com.chat.util.UserDao {
	@Override
	public boolean addUser(UserInformation u,String imagePath) {
		// TODO Auto-generated method stub
		Connection conn = DataBaseHelper.getConnection();
		String sql = "insert into User (name,password) values(?,?)";
		PreparedStatement state = null;
		String sql2 = "insert into UserInf(name,password,nickname,birthday,sex,school,introduction,photo,isOnline) values(?,?,?,?,?,?,?,?,?)";
		try {
			state = (PreparedStatement) conn.prepareStatement(sql);
			state.setString(1, u.getName());
			state.setString(2, u.getPassword());
			state.executeUpdate();
			state = (PreparedStatement) conn.prepareStatement(sql2);
			state.setString(1, u.getName());
			state.setString(2, u.getPassword());
			state.setString(3, u.getNickname());
			state.setString(4, u.getBirthday());
			state.setInt(5, u.getSex());
			state.setString(6, u.getSchool());
			state.setString(7, u.getIntroduction());
			state.setString(8, imagePath);
			state.setInt(9, u.getIsOnline());
			state.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}

	@Override
	public void deleteUser(int name) {
		// TODO Auto-generated method stub

	}

	@Override
	public List searchList(int name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List userList(int name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoginReturnType loginUser(User u) {
		// TODO Auto-generated method stub
		Connection conn = DataBaseHelper.getConnection();
		String sql = "select * from User";
		PreparedStatement state;
		try {
			state = (PreparedStatement) conn.prepareStatement(sql);
			ResultSet rs = state.executeQuery(sql);
			while (rs.next()) {
				if (rs.getString(2).equals(u.getName())
						&& rs.getString(3).equals(u.getPassword())) {
					return LoginReturnType.SUCCESS;
				} else if (rs.getString(2).equals(u.getName())
						&& (!rs.getString(3).equals(u.getPassword()))) {
					return LoginReturnType.PASSWORD_ERROR;
				} else if ((!rs.getString(2).equals(u.getName()))
						&& rs.getString(3).equals(u.getPassword())) {
					return LoginReturnType.ACCOUNT_ERROR;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return LoginReturnType.ERROR;
	}

	@Override
	public boolean check(String name) {
		// TODO Auto-generated method stub
		Connection conn = DataBaseHelper.getConnection();
		String sql = "select * from User";
		PreparedStatement state;
		try {
			state = (PreparedStatement) conn.prepareStatement(sql);
			ResultSet rs = state.executeQuery(sql);
			while (rs.next()) {
				if(rs.getString(2).equals(name)){
					return true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String findPassword(String name) {
		// TODO Auto-generated method stub
		Connection conn = DataBaseHelper.getConnection();
		String sql = "select * from User";
		PreparedStatement state;
		try {
			state = (PreparedStatement) conn.prepareStatement(sql);
			ResultSet rs = state.executeQuery(sql);
			while (rs.next()) {
				if(rs.getString(2).equals(name)){
					return rs.getString(3);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public UserInformation findInf(String name) {
		// TODO Auto-generated method stub
		Connection conn = DataBaseHelper.getConnection();
		String sql = "select * from userinf";
		UserInformation u = new UserInformation();
		PreparedStatement state;
		try {
			state = (PreparedStatement) conn.prepareStatement(sql);
			ResultSet rs = state.executeQuery(sql);
			while (rs.next()) {
				if(rs.getString(2).equals(name)){
					u.setName(rs.getString(2));
					u.setPassword(rs.getString(3));
					u.setNickname(rs.getString(4));
					u.setBirthday(rs.getString(5));
					u.setSex(rs.getInt(6));
					u.setSchool(rs.getString(7));
					u.setIntroduction(rs.getString(8));
					u.setIsOnline(rs.getInt(10));
					File f = new File(rs.getString(9));
					FileInputStream fis = new FileInputStream(f);
					ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
					byte b[] = new byte [1000];
					int n;
					while((n = fis.read(b)) != -1){
						bos.write(b, 0, n);
					}
					fis.close();
					bos.close();
					byte buffer[] = bos.toByteArray();
					u.setPhoto(buffer);
					return u;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<FriendList> getFriends(String name) {
		// TODO Auto-generated method stub
		List<FriendList> friends = new ArrayList<FriendList>();
		String name2 = new Utils().removeSuffix(name);
		Connection conn = DataBaseHelper.getConnection();
		String sql1 = "select * from "+name2+"friendlist";
		String sql2 = "select * from userinf";
		PreparedStatement state1 = null;
		PreparedStatement state2 = null;
		try {
			state1 = (PreparedStatement) conn.prepareStatement(sql1);
			state2 = (PreparedStatement) conn.prepareStatement(sql2);
			ResultSet rs1 = state1.executeQuery(sql1);
			ResultSet rs2 = state2.executeQuery(sql2);
			List<FriendList> friendsName = new ArrayList<FriendList>();
			List<UserInformation> users = new ArrayList<UserInformation>();
			while(rs1.next()){
				FriendList friend = new FriendList();
				friend.setMyName(name);
				friend.setFriendName(rs1.getString(2));
				friend.setRemark(rs1.getString(3));
				friend.setU(null);
				friendsName.add(friend);
			}
			while(rs2.next()){
				// 初始化好友信息
				UserInformation u = new UserInformation();
				u.setName(rs2.getString(2));
				u.setPassword(null);
				u.setBirthday(rs2.getString(5));
				u.setIntroduction(rs2.getString(8));
				u.setSchool(rs2.getString(7));
				u.setSex(rs2.getInt(6));
				u.setNickname(rs2.getString(4));
				u.setType(-1);
				File f = new File(rs2.getString(9));
				FileInputStream fis = new FileInputStream(f);
				ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
				byte b[] = new byte [1000];
				int n;
				while((n = fis.read(b)) != -1){
					bos.write(b, 0, n);
				}
				fis.close();
				bos.close();
				byte buffer[] = bos.toByteArray();
				u.setPhoto(buffer);
				u.setIsOnline(rs2.getInt(10));
				users.add(u);
			}
			for(int i=0;i<friendsName.size();i++){
				for(int j=0;j<users.size();j++){
					if(friendsName.get(i).getFriendName().equals(users.get(j).getName())){
						friendsName.get(i).setU(users.get(j));
						friends.add(friendsName.get(i));
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			String sql3 = "create table "+name+"friendlist (myName varchar(50),friendName varchar(50),remark varchar(100))charset=utf8";
			try {
				PreparedStatement state3 = (PreparedStatement) conn.prepareStatement(sql3);
				state3.execute(sql3);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(name+"----->"+friends);
		return friends;
	}

	@Override
	public void UpdateUser(int isOnline,String name) {
		// TODO Auto-generated method stub
		Connection conn = DataBaseHelper.getConnection();
		String sql = "Update userinf set isOnline=? where name=?";
		try {
			PreparedStatement state = (PreparedStatement) conn.prepareStatement(sql);
			state.setInt(1, isOnline);
			state.setString(2, name);
			state.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void UpdateUser(UserInformation u) {
		// TODO Auto-generated method stub
		Connection conn = DataBaseHelper.getConnection();
		String sql = "update userinf set nickname=?,birthday=?,sex=?,school=?,introduction=? where name=?";
		try {
			PreparedStatement state = (PreparedStatement) conn.prepareStatement(sql);
			state.setString(1, u.getNickname());
			state.setString(2, u.getBirthday());
			state.setInt(3, u.getSex());
			state.setString(4, u.getSchool());
			state.setString(5, u.getIntroduction());
			state.setString(6, u.getName());
			state.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void UpdateFriend(FriendList friend) {
		// TODO Auto-generated method stub
		Connection conn = DataBaseHelper.getConnection();
		String name = new Utils().removeSuffix(friend.getMyName())+"friendlist";
		String sql = "Update "+name+" set remark=? where friendName=?";
		try {
			PreparedStatement state = (PreparedStatement) conn.prepareStatement(sql);
			state.setString(1, friend.getRemark());
			state.setString(2, friend.getFriendName());
			state.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void deleteFriend(FriendList friend) {
		// TODO Auto-generated method stub
		Connection conn = DataBaseHelper.getConnection();
		String name = new Utils().removeSuffix(friend.getMyName())+"friendlist";
		String sql = "delete from "+name+" where friendName = ?";
		try {
			PreparedStatement state = (PreparedStatement) conn.prepareStatement(sql);
			state.setString(1, friend.getFriendName());
			state.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void addSendMessage(Message message ,UserInformation u) {
		// TODO Auto-generated method stub
		Connection conn = DataBaseHelper.getConnection();
		String senderName = message.getSender().getName();
		String receiverName = message.getReceiver().getName();
		String messageContent = null;
		String messageName = null;
		if(message.getContent().getType() == MessageContent.FILE){
			
		}else if(message.getContent().getType() == MessageContent.OTHER){
			
		}else if(message.getContent().getType() == MessageContent.PICTURE){
			
		}else if(message.getContent().getType() == MessageContent.STRING){
			messageContent = (String) message.getContent().getContent();
			messageName = "";
		}
		String sql = "insert into "+senderName+"sendlist (senderName,receiverName,sendTime,receiveTime,isRead,messageType,messageContent,messageName) values(?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement state = (PreparedStatement) conn.prepareStatement(sql);
			state.setString(1, senderName);
			state.setString(2, receiverName);
			state.setDate(3, new java.sql.Date(message.getSendTime().getTime()));
			state.setDate(4, new java.sql.Date(message.getReceiveTime().getTime()));
			state.setInt(5, message.getIsRead());
			state.setInt(6, message.getContent().getType());
			state.setString(7, messageContent);
			state.setString(8, messageName);
			state.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			String sql3 = "create table "
					+ senderName
					+ "sendlist (id int(1),senderName varchar(20),receiverName varchar(20),sendTime datetime," 
					+"receiveTime datetime,isRead int(1),messageType int(1),messageContent varchar(400),messageName varchar(400))charset=utf8";
			try {
				PreparedStatement state3 = (PreparedStatement) conn.prepareStatement(sql3);
				state3.execute(sql3);
				addSendMessage(message, u);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				
			}
		}
	}

	@Override
	public void addReceiveMessage(Message message ,UserInformation u) {
		// TODO Auto-generated method stub
		Connection conn = DataBaseHelper.getConnection();
		String senderName = message.getSender().getName();
		String receiverName = message.getReceiver().getName();
		String messageContent = null;
		String messageName = null;
		if(message.getContent().getType() == MessageContent.FILE){
			
		}else if(message.getContent().getType() == MessageContent.OTHER){
			
		}else if(message.getContent().getType() == MessageContent.PICTURE){
			
		}else if(message.getContent().getType() == MessageContent.STRING){
			messageContent = (String) message.getContent().getContent();
			messageName = "";
		}
		String sql = "insert into "
				+ receiverName
				+ "receivelist (senderName,receiverName,sendTime,receiveTime," +
				"isRead,messageType,messageContent,messageName) values(?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement state = (PreparedStatement) conn.prepareStatement(sql);
			state.setString(1, senderName);
			state.setString(2, receiverName);
			state.setDate(3, new java.sql.Date(message.getSendTime().getTime()));
			state.setDate(4, new java.sql.Date(message.getReceiveTime().getTime()));
			state.setInt(5, message.getIsRead());
			state.setInt(6, message.getContent().getType());
			state.setString(7, messageContent);
			state.setString(8, messageName);
			state.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			String sql3 = "create table "
					+ receiverName
					+ "receivelist (id int (1),senderName varchar(20),receiverName varchar(20),sendTime datetime," 
					+"receiveTime datetime,isRead int(1),messageType int(1),messageContent varchar(400),messageName varchar(400))charset=utf8";
			try {
				PreparedStatement state3 = (PreparedStatement) conn.prepareStatement(sql3);
				state3.execute(sql3);
				addReceiveMessage(message, u);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				
			}
		}
	}

	@Override
	public void UpdateSendMessage(String senderName,String receiverName) {
		// TODO Auto-generated method stub
		String sender = new Utils().removeSuffix(senderName);
		String receiver = new Utils().removeSuffix(receiverName)+"sendlist";
		System.out.println(sender);
		String sql = "Update "+sender+" set isRead = 1 where receiverName="+receiverName;
		Connection conn = DataBaseHelper.getConnection();
		try {
			PreparedStatement state = (PreparedStatement) conn.prepareStatement(sql);
			state.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return;
		}
	}

	@Override
	public void UpdateReceiveMessage(String senderName,String receiverName) {
		// TODO Auto-generated method stub
		String sender = new Utils().removeSuffix(senderName);
		String receiver = new Utils().removeSuffix(receiverName)+"receivelist";
		String sql = "Update "+receiver+" set isRead = 1 where senderName="+senderName;
		Connection conn = DataBaseHelper.getConnection();
		try {
			PreparedStatement state = (PreparedStatement) conn.prepareStatement(sql);
			state.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return;
		}
	}

}
