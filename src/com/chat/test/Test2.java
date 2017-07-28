package com.chat.test;

public class Test2 {
	private int a = 2;
	private static Test2 t= null;
	static{
		t = new Test2();
	}
	private Test2(){
		
	}
	public static int getA(){
		return t.a;
	}
	public static void setA(int a){
		a = a;
	}
}
