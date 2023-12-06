package pt.uminho.di.chalktyk.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import pt.uminho.di.chalktyk.models.nonrelational.tests.Test;
import pt.uminho.di.chalktyk.models.nonrelational.tests.TestResolution;

@Service
public class TestsService implements ITestsService {

    @Autowired
    public TestsService(){
        
    }

    @Override
    public Page<Test> getTests(Integer page, Integer itemsPerPage, List<Integer> tags, Boolean matchAllTags,
            String visibilityType, String visibilityTarget, String specialistId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTests'");
    }

    @Override
    public String createTest(String visibility, Test body) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createTest'");
    }

    @Override
    public TestResolution getTestResolutionById(String resolutionId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTestResolutionById'");
    }

    @Override
    public void deleteTestById(String testId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteTestById'");
    }

    @Override
    public String duplicateTestById(String testId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'duplicateTestById'");
    }

    @Override
    public void updateTest(String testId, Test body) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateTest'");
    }

    @Override
    public void automaticCorrection(String testId, String correctionType) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'automaticCorrection'");
    }

    @Override
    public Integer countStudentsTestResolutions(String testId, Boolean total) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'countStudentsTestResolutions'");
    }

    @Override
    public Page<TestResolution> getTestResolutions(String testId, Integer page, Integer itemsPerPage) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTestResolutions'");
    }

    @Override
    public void createTestResolution(Integer testId, TestResolution body) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createTestResolution'");
    }

    @Override
    public Boolean canStudentSubmitResolution(String testId, String studentId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'canStudentSubmitResolution'");
    }

    @Override
    public Integer countStudentSubmissionsForTest(String testId, String studentId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'countStudentSubmissionsForTest'");
    }

    @Override
    public List<String> getStudentTestResolutionsIds(String testId, String studentId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getStudentTestResolutionsIds'");
    }

    @Override
    public TestResolution getStudentLastResolution(String testId, String studentId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getStudentLastResolution'");
    }    
}
