package pt.uminho.di.chalktyk.models.tests;

import pt.uminho.di.chalktyk.models.institutions.Institution;
import pt.uminho.di.chalktyk.models.users.Specialist;
import pt.uminho.di.chalktyk.models.miscellaneous.Visibility;
import pt.uminho.di.chalktyk.models.courses.Course;

import java.util.ArrayList;

public class Test {
	private String id;
	private String title;
	private String globalInstructions;
	private Float globalPoints;
	private String conclusion;
	private DateTime creationDate;
	private DateTime publishDate;
	private Specialist specialist;
	private Visibility visibility;
	private Course course;
	private Institution institution;
	private ArrayList<TestTag> tags = new ArrayList<TestTag>();
	private ArrayList<TestGroup> groups = new ArrayList<TestGroup>();
}