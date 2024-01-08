package pt.uminho.di.chalktyk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import pt.uminho.di.chalktyk.services.ISeedService;
import pt.uminho.di.chalktyk.services.SeedService;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;

@Component
public class DatabaseSeeder implements ApplicationRunner {

    private final ISeedService seedService;

    @Autowired
    public DatabaseSeeder(ISeedService seedService) {
        this.seedService = seedService;
    }

    @Override
    public void run(ApplicationArguments args) throws BadInputException, NotFoundException, InterruptedException {
        // Execute your seeding logic here
        seedService.seed();
    }
}
