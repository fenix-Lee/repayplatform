# 还款中心重构

此工程主要是repay center工程的一个重构版本，旨在创新思想和突破。

Notice:
- main分支是基于jdk11构建的，如果本地只有jdk8可以切换到jdk8分支上构建([Reasons to move to Java 11](https://docs.microsoft.com/en-us/java/openjdk/reasons-to-move-to-java-11))

## 单元测试+BDD

在repay-core模块下里有关于原生junit和BDD的案例

找到`CucumberRunnerTests.java`这个类
- 如果使用`@RunWith(Suite.class)`和`@Suite.SuiteClasses({...})` 则跑的是原生Junit
- 如果使用`@RunWith(Cucumber.class)` 和`@CucumberOptions(...)` 则跑的是BDD

### 关于Junit

现在Junit的一个痛点是：容易依赖环境。很明显，环境一改变就会造成Junit用例跑不过
1. 内存数据库。在此案例里使用的是h2数据库，每次启动单元测试时都会启动一次并且执行初始化脚本
2. mock. 这个没什么好说的，都懂

### 关于BDD

BDD链接: [Cucumber Test Engine](https://cucumber.io/)

BDD全称是Behavior Driven Development的首字母缩写

- 从思想上来说，BDD是一种TDD的升级版。目前TDD的一个痛点是测试人员和开发人员之间应该用什么编程语言来衔接，选择Junit，测试就增加了学习的成本
  更重要的是，这个测试用例只有开发跟测试才能看懂，第三方要想看懂就必须去学习Java。要是选用别的方式，开发人员就需要增加成本让本地工程兼容此方法
  并顺利执行起来。在这个背景下，BDD应运而生, 用人的自然语言架起开发和测试的桥梁同时大大降低第三方人员学习的成本。

```gherkin
Feature: Guess the word

  # The first example has two steps
  Scenario: Maker starts a game
    When the Maker starts a game
    Then the Maker waits for a Breaker to join

  # The second example has three steps
  Scenario: Breaker joins a game
    Given the Maker has started a game with the word "silky"
    When the Breaker joins the Maker's game
    Then the Breaker must guess a word with 5 characters
```

- 从技术角度来说，BDD是Junit的一种上层封装，底层是通过语言匹配到对应的编程语言方法上然后执行(行为描述支持多国语言同时也支持多种编程语言衔接)

## Conclusion
最后说一点自己的观点。  

当时跟xxx简单的交流过一下关于我们目前的项目管理流程，也提过一些建议。当时就提到过单元测试的问题，也要求说先写一个demo出来，然后看怎么去推进这个事情。这里也借此表达一下自己的观点

- **从技术的角度来说**，这个BDD框架跟我们以前接触的Junit本质上没有太大区别。今天为什么我们没有很好的落地单元测试？是Junit不好么？还是技术不到位不会写？换句话讲，今天我们换了一个测试框架xxx，既然我们没有很好落地Junit,凭什么就能很好的执行xxx框架呢？

- 为什么说我提到的BDD能解决这个问题？说实话，我不确定，因为这里面的讲究很多。这个就涉及到开发模式和流程上了。大家可能听过DDD，也有些人听过TDD，但是BDD就不清楚了。今天写的这个demo在这里不是说换一个新的测试框架，技术本身就在那里，花点时间谁都能写。而真正能解决问题是BDD中的D(driven)而不是我们开发人员怎么去搞这个技术

- 个人理解，使用这种开发模式，底层逻辑是把单元测试均摊到每一个人头上。技术层面我们只要定义好step definition以及底层实现。feature文件可以交给测试，甚至是产品去写。这样，写好的feature文件就可以直接在项目中运行，开发人员就可以把精力集中在业务逻辑上，而不是花费时间和精力去写自己项目中单元测试。(同时也可以生成测试报告，以后在有领导怀疑我们的单元测试，我们直接就可以把报告丢给他看)

## Communication
- 如对BDD感兴趣，欢迎在slack上交流 workspace: hbfintech.slack.com
