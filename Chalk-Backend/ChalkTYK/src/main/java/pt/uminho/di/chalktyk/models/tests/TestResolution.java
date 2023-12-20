package pt.uminho.di.chalktyk.models.tests;

import pt.uminho.di.chalktyk.models.users.Student;

import java.util.ArrayList;

public class TestResolution {
	private String id;
	private DateTime startDate;
	private DateTime submissionDate;
	private int submissionNr;
	private Float totalPoints;
	private Student student;
	private Test test;
	private TestResolutionStatus status;
	private ArrayList<TestResolutionGroup> groups = new ArrayList<TestResolutionGroup>();
}