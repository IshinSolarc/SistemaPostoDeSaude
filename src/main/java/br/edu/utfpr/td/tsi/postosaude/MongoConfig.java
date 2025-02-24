package br.edu.utfpr.td.tsi.postosaude;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.connection.ConnectionPoolSettings;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    private String uri = "mongodb://localhost:27017";
    private String database = "postosaude";

    @Override
    protected String getDatabaseName() {
        return database;
    }

    @Bean
    @Override
    public MongoClient mongoClient() {
        final ConnectionString connectionString = new ConnectionString(uri);
        final MongoClientSettings.Builder mongoClientSettings = MongoClientSettings.builder().applyConnectionString(connectionString)
                .applyToConnectionPoolSettings(builder -> builder.applySettings(connectionPoolSettings()));
        return MongoClients.create(mongoClientSettings.build());
    }



    private ConnectionPoolSettings connectionPoolSettings() {
        return ConnectionPoolSettings.builder()
                .maxSize(50)
                .maxWaitTime(20, TimeUnit.SECONDS)
                .maxConnectionIdleTime(20, TimeUnit.SECONDS)
                .maxConnectionLifeTime(60, TimeUnit.SECONDS).build();
    }

}