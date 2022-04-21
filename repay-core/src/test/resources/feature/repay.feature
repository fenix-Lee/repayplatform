# This is a demo for cucumber test
@RepayCenter
Feature: repay flow business test

    # The first example for cucumber test
    Scenario: assert a repay flow
        Given insert following "productRepayFlowPO" in database by using "repayFlowRepository"
            | repay_apply_id  | internal_order_num |
            |      3          |    cucumber        |
        When I search this flow, which id is 3 with "repayFlowRepository"
        Then I am be told the field "serialNo" is "cucumber"