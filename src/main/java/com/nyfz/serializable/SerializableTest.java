package com.nyfz.serializable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerializableTest {

	public static void main(String[] args) throws FileNotFoundException, IOException, Exception {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("D:\\aaa.txt"));
		oos.writeObject("你好！");
		oos.writeObject(System.currentTimeMillis());
		Employee employee = new Employee("zhangsan", 10);
		oos.writeObject(employee);
		oos.close();
		
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("D:\\aaa.txt"));
		System.out.println(ois.readObject());
		System.out.println(ois.readObject());
		System.out.println(ois.readObject());
		
		ois.close();

	}
	
     static class Employee implements Serializable{
		private transient String name;
		private int age;
		public Employee(String name,int age){
			this.name = name;
			this.age = age;
		}
		public String toString(){
			return this.name + this.age;
		}
	}

}
