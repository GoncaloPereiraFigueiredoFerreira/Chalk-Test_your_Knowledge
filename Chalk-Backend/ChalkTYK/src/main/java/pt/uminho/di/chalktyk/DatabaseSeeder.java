package pt.uminho.di.chalktyk;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import pt.uminho.di.chalktyk.services.ISeedService;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;

@Component
@Slf4j
public class DatabaseSeeder implements ApplicationRunner {

    private final ISeedService seedService;

    @Autowired
    public DatabaseSeeder(ISeedService seedService) {
        this.seedService = seedService;
    }

    @Override
    public void run(ApplicationArguments args) {
        // Execute your seeding logic here
        try {
            if(seedService.seed())
                log.info("Seeded database.");
        } catch (BadInputException | NotFoundException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
