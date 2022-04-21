package com.hbfintech.repay.center;


import com.hbfintech.repay.center.domain.FlowTest;
import com.hbfintech.repay.center.domain.RepayTest;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.test.context.TestPropertySource;

@RunWith(Cucumber.class)
//@Suite.SuiteClasses({FlowTest.class, RepayTest.class})
@CucumberOptions(
        features = "classpath:feature/",
        glue = "com.hbfintech.repay.center.step"
)
@TestPropertySource(locations = "classpath:application.yml")
public class CucumberRunnerTests {
}
