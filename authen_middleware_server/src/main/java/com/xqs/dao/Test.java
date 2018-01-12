package com.xqs.dao;

public class Test {
	public static void change(User user){
		user.setName("123");
	}
	
	public static void main(String[] args) {
		User a = new User("456");
		
		Test.change(a);
		System.out.println(a.getName());
	}
}

class User {
	private String name;

	public User(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}