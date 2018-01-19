package com.sunsea.anno;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ TYPE, FIELD })
public @interface DBMapping {
	String type(); //指定类型是表名还是列名
	String value(); 
}
