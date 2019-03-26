package com.nyfz.clone;

public class ShallowClone {
	
	public static void main(String[] args) {
		Student student1 =new Student();
		student1.setName("zhangsan");
		student1.setAge(10);
		ArtStudent art = new ArtStudent("yishu");
		student1.setArtStudent(art);
		Student student2 = (Student) student1.clone();
		// student1.setArtStudent(new ArtStudent("yishu111"));;
		// student2.setArtStudent(new ArtStudent("yishu222"));;
		art.setName("art");
		System.out.println(student2.getName() + "__"+student2.getAge()+"__"+student2.getArtStudent().getName());
		
		System.out.println(student1.getName() + "__"+student1.getAge()+"__"+student1.getArtStudent().getName());
		
	}
	
	
	 static class Student implements Cloneable{
		private String name;
		private int age;
		private ArtStudent artStudent;
		
		
		public ArtStudent getArtStudent() {
			return artStudent;
		}


		public void setArtStudent(ArtStudent artStudent) {
			this.artStudent = artStudent;
		}


		public  Object clone(){
			Student stu = null;
			try {
				stu = (Student) super.clone();
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return stu;
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
	 static class ArtStudent{
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
