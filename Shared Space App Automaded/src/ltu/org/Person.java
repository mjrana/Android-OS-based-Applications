package ltu.org;

import android.graphics.Bitmap;

/*
 * This class represents a Person object. For simplicity, a person only has
 * - a name
 * - an age
 */
public class Person {

	public String name;
	public String age;
	public Bitmap photo;
	
	public Person(){
		name = ""; age = ""; 
	}
	
	public Person(String pName, String pAge, Bitmap pThumb){
		
		name = pName;
		age = pAge;
		photo = pThumb;
	}
	
	@Override
	public String toString(){
		return "\nName:" + name + "\nAge:" + age;
	}
}
