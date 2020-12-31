package br.com.dickinho.hack.config;

import java.util.Collection;
import java.util.Collections;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
@EnableMongoRepositories(basePackages = "br.com.dickinho.hack.repository")
public class MongoConfig /*extends AbstractMongoClientConfiguration*/ {

	/*@Override
	public MongoClient mongoClient() {
		ConnectionString conn = new ConnectionString("mongodb://192.168.15.12:27017/pcsearchcrawler");
		
		MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
	            .applyConnectionString(conn)
	            .build();
	        
	        return MongoClients.create(mongoClientSettings);
	}

	@Override
	protected String getDatabaseName() {
		return "pcsearchcrawler";
	}

	  @Override
	    public Collection getMappingBasePackages() {
	        return Collections.singleton("br.com.dickinho");
	    }*/
}
