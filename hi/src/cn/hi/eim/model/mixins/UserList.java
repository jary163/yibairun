package cn.hi.eim.model.mixins;

import java.util.ArrayList;
import java.util.List;

import cn.hi.eim.model.User;

import com.google.gson.annotations.SerializedName;

public class UserList {
	@SerializedName("")
	private List<User> users = new ArrayList<User>();

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	
}
