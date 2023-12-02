package pt.uminho.di.chalktyk.services;

import pt.uminho.di.chalktyk.models.nonrelational.courses.Course;

public interface ICoursesService {
    void addCource(Course course) throws Exception;
    void removeCource(int id_user) throws Exception;

}
