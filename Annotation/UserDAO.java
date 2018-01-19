package com.sunsea.anno;

@DBMapping(type="Table",value="Users")
public class UserDAO implements DAOInterface{
	
	@DBMapping(type="Column",value="username")
	private String userName;
	
	@DBMapping(type="Column",value="age")
	private Integer age = null;
	
	@DBMapping(type="Column",value="classname")
	private String className;
	
	@DBMapping(type="Column",value="info")
	private String info;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
	
}
