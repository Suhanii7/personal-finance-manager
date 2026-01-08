package Maven.config;

import Maven.entity.Category;
import Maven.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner initDatabase(CategoryRepository repository) {
        return args -> {
            if (repository.findByUserIsNull().isEmpty()) {
                repository.save(createCat("Salary", "INCOME"));
                repository.save(createCat("Food", "EXPENSE"));
                repository.save(createCat("Rent", "EXPENSE"));
                repository.save(createCat("Transportation", "EXPENSE"));
                repository.save(createCat("Entertainment", "EXPENSE"));
                repository.save(createCat("Healthcare", "EXPENSE"));
                repository.save(createCat("Utilities", "EXPENSE"));
            }
        };
    }

    private Category createCat(String name, String type) {
        Category c = new Category();
        c.setName(name);
        c.setType(type);
        c.setCustom(false);
        return c;
    }
}