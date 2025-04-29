package model;

import java.util.Date;

public class UserInfo {
	private int userID;
    private String userName;
    private String password;
    private int roleID;
    private String gender;
    private String email;
    private String phone;
    private String address;
    private Date birthday;

    public UserInfo(int userID, String userName, String password, int roleID, String gender, String email, String phone, String address, Date birthday) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.roleID = roleID;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.birthday = birthday;
    }

	public UserInfo(String userName, String password, int roleID, String gender, String email, String phone,
			String address, Date birthday) {
		super();
		this.userName = userName;
		this.password = password;
		this.roleID = roleID;
		this.gender = gender;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.birthday = birthday;
	}

	public UserInfo() {
		super();
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRoleID() {
		return roleID;
	}

	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
    
    
}
