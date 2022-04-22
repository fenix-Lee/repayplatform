package com.hbfintech.repay.center;


import com.hbfintech.repay.center.domain.FlowTest;
import com.hbfintech.repay.center.domain.RepayTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.test.context.TestPropertySource;

@RunWith(Suite.class)
@Suite.SuiteClasses({FlowTest.class, RepayTest.class})
//@CucumberOptions(
//        features = "classpath:feature/",
//        glue = "com.hbfintech.repay.center.step"
//)
@TestPropertySource(locations = "classpath:application.yml")
public class CucumberRunnerTests {
}
