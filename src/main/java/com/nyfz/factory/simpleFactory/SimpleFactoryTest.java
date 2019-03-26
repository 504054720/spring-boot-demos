package com.nyfz.factory.simpleFactory;

public class SimpleFactoryTest {
	static abstract class Car{
		private String name;
		public abstract void drive();
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	static class Benchi extends Car{
		@Override
		public void drive() {
			System.out.println(this.getName()+"BEN");
			
		}
	}
	static class Baoma extends Car{

		@Override
		public void drive() {
			System.out.println(this.getName()+"BMW");
		}
	}
	static class Driverer{
		public static Car creatCar(String name){
			Car car = null;
			if("benchi".equals(name)){
				car =  new Benchi();
			}
			if("baoma".equals(name)){
				car = new Baoma(); 
			}
			return car;
		}
	}

	public static void main(String[] args) {
		Car car = Driverer.creatCar("benchi");
		car.setName("dabenchi");
		car.drive();
	}

}
