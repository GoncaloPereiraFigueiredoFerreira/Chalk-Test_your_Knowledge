package pt.uminho.di.chalktyk;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import pt.uminho.di.chalktyk.services.IInstitutionsService;
import pt.uminho.di.chalktyk.models.nonrelational.institutions.Institution;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;


@SpringBootTest
public class InstitutionsServiceTest {
    private IInstitutionsService service;

    @Autowired
    public InstitutionsServiceTest(IInstitutionsService service){
        this.service = service;
    }

    @Test
    public void test() throws NotFoundException, BadInputException {
        Institution inst = new Institution("UM");
        inst.setDescription("Universidade do Minho");
        inst.setLogoPath("image.png");

        service.createInstitution(inst);
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
        Institution inst2 = service.getInstitutionById("UM");

        assertEquals(inst2.getName(), "UM");
        assertEquals(inst2.getDescription(), "Universidade do Minho");
        assertEquals(inst2.getLogoPath(), "image.png");
    }
}
