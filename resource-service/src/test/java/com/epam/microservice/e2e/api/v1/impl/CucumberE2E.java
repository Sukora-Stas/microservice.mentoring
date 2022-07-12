package com.epam.microservice.e2e.api.v1.impl;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:feature")
public class CucumberE2E {
}
