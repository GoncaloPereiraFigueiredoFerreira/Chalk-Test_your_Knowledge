package pt.uminho.di.chalktyk.models.exercises;

import java.util.ArrayList;

import pt.uminho.di.chalktyk.models.institutions.Institution;
import pt.uminho.di.chalktyk.models.users.Specialist;
import pt.uminho.di.chalktyk.models.miscellaneous.Tag;
import pt.uminho.di.chalktyk.models.miscellaneous.Visibility;
import pt.uminho.di.chalktyk.models.courses.Course;

public class Exercise {
	private String id;
	private String title;
	private String exerciseType;
	private Float points;
	private int nrCopies;
	private Visibility visibility;
	private Course course;
	private Specialist specialist;
	private Institution institution;
	private ArrayList<Tag> tags = new ArrayList<Tag>();
}