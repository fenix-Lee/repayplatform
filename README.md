# 还款中心重构

此工程主要是repay center工程的一个重构版本，旨在创新思想和突破。  
Notice:
- 此main版本是基于jdk11构建的，如果本地只有jdk8可以切换到jdk8分支上构建

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