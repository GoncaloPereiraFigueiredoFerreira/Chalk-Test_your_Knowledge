package pt.uminho.di.chalktyk.users;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pt.uminho.di.chalktyk.models.users.Specialist;
import pt.uminho.di.chalktyk.models.users.User;
import pt.uminho.di.chalktyk.repositories.SpecialistDAO;
import pt.uminho.di.chalktyk.repositories.UserDAO;
import pt.uminho.di.chalktyk.services.ISpecialistsService;
import pt.uminho.di.chalktyk.services.IUsersService;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;

@SpringBootTest
public class SpecialistsServiceTest {
    private final ISpecialistsService specialistsService;
    private final UserDAO userDAO;
    private final SpecialistDAO specialistDAO;
    private final IUsersService usersService;

    @Autowired
    public SpecialistsServiceTest(ISpecialistsService specialistsService, UserDAO userDAO, SpecialistDAO specialistDAO, IUsersService usersService) {
        this.specialistsService = specialistsService;
        this.userDAO = userDAO;
        this.specialistDAO = specialistDAO;
        this.usersService = usersService;
    }

    String createSpecialistAlex() throws BadInputException {
        String name = "Alex",
                photoPath = "google.com/some/path/to/image.png",
                email = "alex@gmail.com",
                description = "Descricao.";
        Specialist alex = new Specialist(null, name, photoPath, email, description);
        return specialistsService.createSpecialist(alex);
    }
    
    @Test
    @Transactional
    void createSpecialistSuccess() throws BadInputException {
        String alex_id = createSpecialistAlex();

        // assert user exists and is instance of specialist
        User alexUser = userDAO.findById(alex_id).orElse(null);
        assert alexUser instanceof Specialist;

        // asserts that the information was stored correctly
        Specialist alexSpecialist = (Specialist) alexUser;
        assert alexSpecialist.getName().equals("Alex")
                && alexSpecialist.getDescription().equals("Descricao.")
                && alexSpecialist.getPhotoPath().equals("google.com/some/path/to/image.png")
                && alexSpecialist.getEmail().equals("alex@gmail.com");
    }

    @Test
    @Transactional
    void createSpecialistBadEmail(){
        // bad email format
        Specialist luis = new Specialist(null, "Luis", "google.com/some/path/to/image.png", "l-gmail.com", "description.");
        try{
            specialistsService.createSpecialist(luis);
            assert false;
        }catch (BadInputException ignored){}
        assert !userDAO.existsByEmail("l-gmail.com");

        // email already used
        luis.setEmail("l@gmail.com");
        try{ specialistsService.createSpecialist(luis);
        }catch (BadInputException bie){assert false;}
        Specialist luis2 = new Specialist(null, "Luis2", "google.com/some/path/to/image2.png", "l@gmail.com", "description2.");
        try{
            specialistsService.createSpecialist(luis2);
            assert false;
        }catch (BadInputException ignored){
            assert true;
        }
    }

    @Test
    @Transactional
    void getSpecialist() throws BadInputException, NotFoundException {
        String id = createSpecialistAlex();
        Specialist s = specialistsService.getSpecialistById(id);
        assert s != null;
    }

    @Test
    @Transactional
    void existsSpecialist() throws BadInputException {
        String id = createSpecialistAlex();
        assert specialistsService.existsSpecialistById(id);
    }
    
    @Test
    @Transactional
    void updateSpecialist() throws BadInputException, NotFoundException {
        String id = createSpecialistAlex();
        Specialist s = specialistsService.getSpecialistById(id);
        String name = "Alex Mart",
                photoPath = "somewhere.com/another/PhotoPath/img.jpg",
                email = "newmail@gmail.com",
                description = "new description";
        // assert that the specialist does not have the values that will be updated
        assert s != null && !s.getName().equals(name) && !s.getEmail().equals(email) && !s.getPhotoPath().equals(photoPath) && !s.getDescription().equals(description);

        usersService.updateBasicProperties(id, name, email, photoPath, description);
        s = specialistsService.getSpecialistById(id);
        
        // checks that the fields were updated
        assert s != null && s.getName().equals(name) && s.getEmail().equals(email) && s.getPhotoPath().equals(photoPath) && s.getDescription().equals(description);
    }
}
