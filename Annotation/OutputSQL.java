package com.sunsea.anno;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class OutputSQL {

	public static void main(String[] args) {
		UserDAO user = new UserDAO();
		user.setUserName("张三");
		user.setAge(18);
		user.setInfo("五年级留级生");
		String sql = query(user);
		System.out.println(sql);
		
		ScoreDAO score = new ScoreDAO();
		score.setChineseScore(80);
		score.setMathScore(0);
		score.setEngScore(75);
		String sql2 = query(score);
		System.out.println(sql2);
		
	}
	
	private static String query(DAOInterface filter) {
		StringBuffer sql = new StringBuffer();
		//从注解里获取表名
		Class c = filter.getClass();
		boolean b = c.isAnnotationPresent(DBMapping.class);
		if(!b) {
			return null;
		}
		//在filter里定位到此注解
		//DBMapping tableAnno = (DBMapping)c.getAnnotations()[0];
		Annotation annos[] = c.getAnnotations();
		DBMapping tableAnno = null;
		for(Annotation anno : annos) {
			if(anno instanceof DBMapping && (((DBMapping) anno).type().equals("Table"))) {
				tableAnno = (DBMapping)anno;
				break;
			}
		}
		String tableName = tableAnno.value();
		//拼接SQL前半部
		sql.append("select * from ").append(tableName).append(" where 1=1 ");
		
		//循环，判断，把满足条件的：获取注解里的列名，获取对应参数的值
		String columnDBName = null;
		String value = null;
		Field fields[] = c.getDeclaredFields();
		DBMapping columnAnno = null;
		for(Field field : fields) {		
			//获取字段对应的注释里的value即DB里的列名
			boolean fExists = field.isAnnotationPresent(DBMapping.class);
			if(!fExists) {
				continue;
			}
			columnAnno = field.getAnnotation(DBMapping.class);
			columnDBName = columnAnno.value();
			
			//通过类里的字段名找到对应的get方法
			try {
				Method method = c.getDeclaredMethod("get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1));
				
				//反射调用得到值
				Object obj = method.invoke(filter);
					//条件过滤掉为空的字段
				if(obj == null) {
					continue;
				}
				value = method.invoke(filter).toString();
				
				//拼接列名和对应参数值
				if(method.invoke(filter) instanceof Integer) {
					sql.append("and ").append(columnDBName + "=").append(value).append(" ");
				}
				else {
					sql.append("and ").append(columnDBName + "=").append("'").append(value).append("' ");
				}

			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sql.toString();	
	}

}
