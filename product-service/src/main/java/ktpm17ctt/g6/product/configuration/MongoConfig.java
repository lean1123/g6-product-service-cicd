package ktpm17ctt.g6.product.configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@EnableMongoAuditing
public class MongoConfig {
    @Value("${app.mongodb.username}")
    private String MONGO_USERNAME;
    @Value("${app.mongodb.password}")
    private String MONGO_PASSWORD;
    @Bean
    public MongoClient mongoClient() {
        String mongoUri = String.format("mongodb+srv://%s:%s@cluster0.8sxd9.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0",
                MONGO_USERNAME, MONGO_PASSWORD);
        return MongoClients.create(mongoUri);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "shoes-store");
    }
}

