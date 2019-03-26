package com.nyfz.factory.methodFactory;

public class MethodFactoryTest {
	static abstract class Car{
		private String name;
		public abstract void drive();
	}
	static class Benchi extends Car{

		@Override
		public void drive() {
			System.out.println("this is Benchi");
			
		}
		}
	static class Baoma extends Car{

		@Override
		public void drive() {
			System.out.println("this is baoma");
		}
	}
	static abstract class Drivers{
		 abstract Car createCar();
	}
	
	static class BenchiDriver extends Drivers{

		@Override
		Car createCar() {
			return new Benchi(); 
		}
	}
	static class BaomaDriver extends Drivers{

		@Override
		Car createCar() {
			return new Baoma();
		}
	}
	

	public static void main(String[] args) {
		Drivers d = new BenchiDriver();
		Car car = d.createCar();
		car .drive();

	}

}
