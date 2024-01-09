package pt.uminho.di.chalktyk;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.transaction.Transactional;
import pt.uminho.di.chalktyk.models.courses.Course;
import pt.uminho.di.chalktyk.models.exercises.Exercise;
import pt.uminho.di.chalktyk.models.exercises.ExerciseRubric;
import pt.uminho.di.chalktyk.models.exercises.ExerciseSolution;
import pt.uminho.di.chalktyk.models.exercises.ExerciseStatement;
import pt.uminho.di.chalktyk.models.exercises.fill_the_blanks.FillTheBlanksData;
import pt.uminho.di.chalktyk.models.exercises.fill_the_blanks.FillTheBlanksExercise;
import pt.uminho.di.chalktyk.models.exercises.fill_the_blanks.FillTheBlanksRubric;
import pt.uminho.di.chalktyk.models.exercises.items.Item;
import pt.uminho.di.chalktyk.models.exercises.items.StringItem;
import pt.uminho.di.chalktyk.models.exercises.multiple_choice.Mctype;
import pt.uminho.di.chalktyk.models.exercises.multiple_choice.MultipleChoiceData;
import pt.uminho.di.chalktyk.models.exercises.multiple_choice.MultipleChoiceExercise;
import pt.uminho.di.chalktyk.models.exercises.multiple_choice.MultipleChoiceResolutionItem;
import pt.uminho.di.chalktyk.models.exercises.multiple_choice.MultipleChoiceRubric;
import pt.uminho.di.chalktyk.models.exercises.open_answer.*;
import pt.uminho.di.chalktyk.models.institutions.Institution;
import pt.uminho.di.chalktyk.models.miscellaneous.Tag;
import pt.uminho.di.chalktyk.models.miscellaneous.Visibility;
import pt.uminho.di.chalktyk.models.tests.DeliverDateTest;
import pt.uminho.di.chalktyk.models.tests.LiveTest;
import pt.uminho.di.chalktyk.models.tests.TestExercise.ReferenceExercise;
import pt.uminho.di.chalktyk.models.tests.TestGroup;
import pt.uminho.di.chalktyk.models.tests.TestResolution;
import pt.uminho.di.chalktyk.models.tests.TestResolutionStatus;
import pt.uminho.di.chalktyk.models.tests.TestExercise.ConcreteExercise;
import pt.uminho.di.chalktyk.models.tests.TestExercise.TestExercise;
import pt.uminho.di.chalktyk.models.users.InstitutionManager;
import pt.uminho.di.chalktyk.models.users.Specialist;
import pt.uminho.di.chalktyk.models.users.Student;
import pt.uminho.di.chalktyk.services.*;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;

import javax.swing.*;

@SpringBootTest
public class Seed {
    private final ISeedService seedService;

    @Autowired
    public Seed(ISeedService seedService){
        this.seedService = seedService;
    }

    @Test
    public void seed() throws BadInputException, NotFoundException, InterruptedException {
        seedService.seed();
        
    }
}
