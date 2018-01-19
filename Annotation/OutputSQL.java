package com.sunsea.anno;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class OutputSQL {

	public static void main(String[] args) {
		UserDAO user = new UserDAO();
		user.setUserName("����");
		user.setAge(18);
		user.setInfo("���꼶������");
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
		//��ע�����ȡ����
		Class c = filter.getClass();
		boolean b = c.isAnnotationPresent(DBMapping.class);
		if(!b) {
			return null;
		}
		//��filter�ﶨλ����ע��
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
		//ƴ��SQLǰ�벿
		sql.append("select * from ").append(tableName).append(" where 1=1 ");
		
		//ѭ�����жϣ������������ģ���ȡע�������������ȡ��Ӧ������ֵ
		String columnDBName = null;
		String value = null;
		Field fields[] = c.getDeclaredFields();
		DBMapping columnAnno = null;
		for(Field field : fields) {		
			//��ȡ�ֶζ�Ӧ��ע�����value��DB�������
			boolean fExists = field.isAnnotationPresent(DBMapping.class);
			if(!fExists) {
				continue;
			}
			columnAnno = field.getAnnotation(DBMapping.class);
			columnDBName = columnAnno.value();
			
			//ͨ��������ֶ����ҵ���Ӧ��get����
			try {
				Method method = c.getDeclaredMethod("get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1));
				
				//������õõ�ֵ
				Object obj = method.invoke(filter);
					//�������˵�Ϊ�յ��ֶ�
				if(obj == null) {
					continue;
				}
				value = method.invoke(filter).toString();
				
				//ƴ�������Ͷ�Ӧ����ֵ
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
