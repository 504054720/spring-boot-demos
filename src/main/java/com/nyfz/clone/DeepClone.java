package com.nyfz.clone;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.apache.poi.ss.formula.functions.T;



public class DeepClone {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Student student = new Student();
		student.setAge(10);
		student.setName("zhangsan");
		
		ArtStudent  art = new ArtStudent("artOld");
		student.setArtStudent(art);
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
		objectOutputStream.writeObject(student);
		
		try {
			System.out.println("11111");
			art.setName("artNew");
			int a = 1/0;
		} catch (Exception e) {
			System.out.println("2222");
			return;
		}finally{
			System.out.println("finally!");
		}
		student.setName("LISI");
		System.out.println(student.getName()+"___"+student.getArtStudent().getName());
		
		ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
		
		ObjectInputStream objectIn = new ObjectInputStream(inputStream);
		Student stuNew = (Student)objectIn.readObject();
		System.out.println(stuNew.getName()+"___"+stuNew.getArtStudent().getName());
		
	}
	 static class Student implements Serializable{
			private String name;
			private int age;
			private ArtStudent artStudent;
			
			
			public ArtStudent getArtStudent() {
				return artStudent;
			}


			public void setArtStudent(ArtStudent artStudent) {
				this.artStudent = artStudent;
			}


			public String getName() {
				return name;
			}


			public void setName(String name) {
				this.name = name;
			}


			public int getAge() {
				return age;
			}


			public void setAge(int age) {
				this.age = age;
			}
			
			
		}
		 static class ArtStudent implements Serializable{
			 private String name;

			 public ArtStudent(String name){
				 this.name = name;
			 }
			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}
			 
		 }

}
