package com.chat.util;

import java.util.List;

import com.chat.bean.FriendList;
import com.chat.bean.LoginReturnType;
import com.chat.bean.Message;
import com.chat.bean.User;
import com.chat.bean.UserInformation;

public interface UserDao {
	public boolean addUser(UserInformation u,String imagePath);
	public void deleteUser(int name);
	public List userList(int name);
	public List searchList(int name);
	public LoginReturnType loginUser(User u);
	public boolean check(String name);
	public String findPassword(String name);
	public UserInformation findInf(String name);
	public List<FriendList> getFriends(String name);
	public void UpdateUser(int isOnline,String name);
	public void UpdateUser(UserInformation u);
	public void UpdateFriend(FriendList friend);
	public void deleteFriend(FriendList friend);
	public void addSendMessage(Message message ,UserInformation u);
	public void addReceiveMessage(Message message ,UserInformation u);
	public void UpdateSendMessage(String senderName,String receiverName);
	public void UpdateReceiveMessage(String senderName, String receiverName);
}
