package pt.uminho.di.chalktyk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.transaction.annotation.Transactional;
import pt.uminho.di.chalktyk.models.subscriptions.Subscription;
import pt.uminho.di.chalktyk.models.users.InstitutionManager;
import pt.uminho.di.chalktyk.models.users.Specialist;
import pt.uminho.di.chalktyk.services.IInstitutionsService;
import pt.uminho.di.chalktyk.models.institutions.Institution;
import pt.uminho.di.chalktyk.services.ISpecialistsService;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.Collections;


@SpringBootTest
public class InstitutionsServiceTest {
    private final IInstitutionsService institutionService;
    private final ISpecialistsService iSpecialistsService;

    @Autowired
    public InstitutionsServiceTest(IInstitutionsService institutionService, ISpecialistsService iSpecialistsService){
        this.institutionService = institutionService;
        this.iSpecialistsService = iSpecialistsService;
    }

    @Test
    @Transactional
    public void test() throws NotFoundException, BadInputException {
        Institution inst = new Institution("UM", "desc", "logoPath");
        inst.setDescription("Universidade do Minho");
        inst.setLogoPath("image.png");

        //institutionService.createInstitution(inst);

        String id = iSpecialistsService.createSpecialist(new Specialist(null, "Luis", "","lisinho@gmail.com", "HOmem bonito", null));
        institutionService.addSpecialistsToInstitution("UM", Collections.singletonList("id"));

        Institution i = institutionService.getSpecialistInstitution(id);
        assertEquals(i.getName(),"UM");
        assertEquals(i.getDescription(),"Universidade do Minho");

        /* 
        MerchandiseEntity pants = new MerchandiseEntity(ORIGINAL_TITLE, BigDecimal.ONE);
        pants = repository.save(pants);

        Long originalId = pants.getId();

        // Update using setters
        pants.setTitle(UPDATED_TITLE);
        pants.setPrice(BigDecimal.TEN);
        pants.setBrand(UPDATED_BRAND);

        Optional<MerchandiseEntity> resultOp = repository.findById(originalId);

        assertTrue(resultOp.isPresent());
        MerchandiseEntity result = resultOp.get();
        */
        /*
        Institution inst2 = institutionService.getInstitutionById("UM");

        assertEquals(inst2.getName(), "UM");
        assertEquals(inst2.getDescription(), "Universidade do Minho");
        assertEquals(inst2.getLogoPath(), "image.png");*/
    }


    @Test
    @Transactional
    public void testGetInstitutionBySpecialistId() throws NotFoundException, BadInputException {
        Institution inst = new Institution("UM", "desc", "logoPath");
        inst.setDescription("Universidade do Minho");
        inst.setLogoPath("image.png");

        institutionService.createInstitutionAndManager(inst, new InstitutionManager(null, "InstManager1" , "instmanager1/photo.png", "instmanager1@gmail.com", "instmanager1 description", null));

        String id = iSpecialistsService.createSpecialist(new Specialist(null, "Luis", "","lisinho@gmail.com", "HOmem bonito", null));

        institutionService.addSpecialistsToInstitution("UM", Collections.singletonList("yo"));

        Institution i = institutionService.getSpecialistInstitution(id);
        assertEquals(i.getName(),"UM");
        assertEquals(i.getDescription(),"Universidade do Minho");
    }

    @Test
    @Transactional
    public void createSpecialist() throws BadInputException {
        String id = iSpecialistsService.createSpecialist(new Specialist(null, "Luis", "","lisinho@gmail.com", "HOmem bonito", null));
        assertTrue(iSpecialistsService.existsSpecialistById(id));
    }

}
