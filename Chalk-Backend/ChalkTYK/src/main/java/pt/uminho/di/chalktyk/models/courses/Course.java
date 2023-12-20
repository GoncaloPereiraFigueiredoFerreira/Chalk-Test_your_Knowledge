package pt.uminho.di.chalktyk.models.courses;

import java.util.ArrayList;

import pt.uminho.di.chalktyk.models.institutions.Institution;
import pt.uminho.di.chalktyk.models.users.Specialist;

public class Course {
	private String id;
	private String name;
	private String description;
	private ArrayList<Specialist> specialists = new ArrayList<Specialist>();
	private Institution institution;
}