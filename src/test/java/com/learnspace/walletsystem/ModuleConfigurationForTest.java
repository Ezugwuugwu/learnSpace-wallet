package com.learnspace.walletsystem;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@ComponentScan(basePackages = "com.wallet-system.**.**.*")
public class ModuleConfigurationForTest {
}
