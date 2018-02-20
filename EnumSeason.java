package com.sunsea.testBaiduTaobao;

public enum EnumSeason {
	SPRING("春天"), SUMMER("夏天"), AUTUMN("秋天"), WINTER("冬天");
	
	private String now;
	
	private EnumSeason(String str) {
		this.now = str;
	}
	
	public String getNow() {
		return now;
	}
	
	public static void main() {
		
		EnumSeason sex = EnumSeason.SPRING;
		
		switch(sex) {
			case SPRING:
				System.out.println(sex.getNow());
				break;
			case SUMMER:
				System.out.println(sex.getNow());
				break;
			default:
				System.out.println(sex.getNow());
			
		}
		
	}
}
