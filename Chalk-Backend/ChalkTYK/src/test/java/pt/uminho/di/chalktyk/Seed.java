package pt.uminho.di.chalktyk;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.transaction.annotation.Transactional;
import pt.uminho.di.chalktyk.services.*;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;
import pt.uminho.di.chalktyk.services.exceptions.ServiceException;

@SpringBootTest
@Transactional(noRollbackFor = ServiceException.class)
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
