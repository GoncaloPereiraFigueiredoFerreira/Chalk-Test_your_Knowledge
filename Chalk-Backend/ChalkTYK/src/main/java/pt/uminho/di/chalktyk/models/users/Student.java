package pt.uminho.di.chalktyk.models.users;

import java.util.ArrayList;

import pt.uminho.di.chalktyk.models.institutions.Institution;
import pt.uminho.di.chalktyk.models.courses.Course;

public class Student extends User {
	private String id;
	private ArrayList<Course> courses = new ArrayList<Course>();
	private Institution institution;
}