package com.sunsea.testBaiduTaobao;

import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

public class JavaCollectionMapReminder {
	
	public static void main(String[] args) {
/*
		|Collection 
		|　　├List 普通的集合
		|　　│-├LinkedList 擅长增删改，线程不同步
		|　　│-├ArrayList 最普通的一个，线程不同步
		|　　│-└Vector ArrayList的线程同步版
		|　　│ └Stack 用的少，先进后出集合
		|　　├Set 特点是元素不能重复，null只能出现一个
		|　　│├HashSet 普通的Set，线程不同步
		|　　│├TreeSet 自带排序，线程不同步
		|　　│└LinkedHashSet 擅长增删改，线程不同步
		| 
		|Map 特点是键值对
		　　├Hashtable 线程同步,key不能为null
		　　│-└Properties 继承了Hashtable，有简单的读写键值对到文件的方法
		　　├HashMap 线程不同步,但功能比Hashtable强大，key可以有一个为null
		　　├ConcurrentHashMap HashMap的线程同步版
		　　└WeakHashMap 用的少
	*/	

		//Vector类似ArrayList.但是线程同步的。效率低些
		//ArrayList要线程同步，一般可以：
		//List list = Collections.synchronizedList(new LinkedList(…));
		Vector<String> v = new Vector<String>();
		v.addElement("this");
		v.addElement("is");
		v.addElement("an");
		v.addElement("example");
		
		for(int i=0; i<v.size(); i++) {
			System.out.println(v.get(i));
		}
		
		for(String s : v) {
			System.out.println(s);
		}
		
		for(Iterator<String> i = v.iterator(); i.hasNext();) {
			System.out.println(i.next());
			
		}
		
		//这玩意用得少了，一般用Iterator
		Enumeration<String> e = v.elements();
		while(e.hasMoreElements()) {
			System.out.println(e.nextElement());
		}
		
		
		
		//与List并列的有Set。有三种，HashSet, TreeSet, LinkedHashSet
		//特点是元素不能重复
		//TreeSet自带排序
		//都不线程同步。需要时可以：
		//HashSet hs = (HashSet) Collections.synchronizedSet(new HashSet());
		
		

		
		
		//Collection的排序：
		//元素类要继承Comparable接口，实现compareTo方法
		Collections.sort(v);//正序，按compareTo方法指定的逻辑排序。
		
		Comparator cpar = Collections.reverseOrder();
		Collections.sort(v, cpar);//逆序，按compareTo方法指定的逻辑反排序。
		

		//一次性搞定的做法：
		Collections.sort(v, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				// TODO Auto-generated method stub
				return o1.compareTo(o2); //String类自带了compareTo方法进行比较。自己可以随意
			}
		});
		
		System.out.println(v);
		
		
		
		
		//和Collection并列的是Map系列。有四个。特点是键值对。
		//HashTable, HashMap, ConcurrentHashMap，WeakHashMap(用得少)
		//HashTable线程同步，HashMap线程不同步，但功能比hashtable强大
		//HashMap要想同步，用ConcurrentHashMap
		//或者用：
		//HashMap hm = (HashMap)Collections.synchronizedMap(new HashMap());
		//它们的key,value中的key若是自定义的类，则要重写equals和hashCode方法
		
		
		//Map的排序：
		Map<Integer,String> tm = new TreeMap<Integer,String>();//TreeMap默认是正序的
		tm.put(3, "three");
		tm.put(2, "two");
		tm.put(5, "five");
		tm.put(8, "eight");
		tm.put(4, "four");
		
		//针对map里的key的排序。
		Map<Integer,String> rtm = new TreeMap<Integer,String>(new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				if(o1 < o2)
					return 1;
				else if(o1 > o2)
					return -1;
				else
					return 0;
			}
		});
		
		//将逆序的结果给rtm
		rtm.putAll(tm);
		
		System.out.println(tm);
		System.out.println(rtm);
		
		
		
		
	}
	
}


