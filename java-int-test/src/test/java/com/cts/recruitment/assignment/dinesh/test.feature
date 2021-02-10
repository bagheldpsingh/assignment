Feature: This is just demo testing to show Karate feature and complate CTS
  interview process

  Background:
    * def requestInput = read('recordinput.json')
    * def baseUrl = 'http://localhost:8080/'

  Scenario: To test statement validation api health check url
    Given url  baseUrl
    And path 'records/health/check'
    And header Content-Type = 'application/json'
    When method get
    Then status 200
    Then match response == 'KeepAlive'

  Scenario: To test statement validation api post methods with all valid fields
    Given url  baseUrl
    And path 'records/validate'
    And header Content-Type = 'application/json'
    And request requestInput
    When method post
    Then status 200
    Then match response.results == 'SUCCESSFUL'

  Scenario: To test statement validation with invalid Json input without txnReference and except 400
    * set requestInput[1].txnReference = ''
    Given url  baseUrl
    And path 'records/validate'
    And header Content-Type = 'application/json'
    And request requestInput
    When method post
    Then status 400
    Then match response.results == 'BAD_REQUEST'

  Scenario: To test statement validation with valid Json input without descriptions and except 200
    * set requestInput[1].Description = ''
    Given url  baseUrl
    And path 'records/validate'
    And header Content-Type = 'application/json'
    And request requestInput
    When method post
    Then status 200
    Then match response.results == 'SUCCESSFUL'

  Scenario: To test statement validation with valid Json input without startBalance and except 400
    * set requestInput[1].startBalance = ''
    Given url  baseUrl
    And path 'records/validate'
    And header Content-Type = 'application/json'
    And request requestInput
    When method post
    Then status 400
    Then match response.results == 'BAD_REQUEST'

  Scenario: To test statement record with duplicate reference present in input
    * set requestInput[1].txnReference = '10104'
    Given url  baseUrl
    And path 'records/validate'
    And request requestInput
    And header Content-Type = 'application/json'
    When method post
    Then status 200
    Then match response.results == 'DUPLICATE_REFERENCE'
    Then match response.errorRecords[1].reference == '10104'

  Scenario: To test statement record with invalid sum present in input
    * set requestInput[1].startBalance = '699'
    Given url  baseUrl
    And path 'records/validate'
    And request requestInput
    And header Content-Type = 'application/json'
    When method post
    Then status 200
    Then match response.results == 'INCORRECT_END_BALANCE'
    Then match response.errorRecords[0].reference == '10102'

  Scenario: To test statement record with invalid sum and duplicate refernce present in input
    * set requestInput[1].startBalance = '699'
    * set requestInput[1].txnReference = '10104'
    Given url  baseUrl
    And path 'records/validate'
    And request requestInput
    And header Content-Type = 'application/json'
    When method post
    Then status 200
    Then match response.results == 'DUPLICATE_REFERENCE_INCORRECT_END_BALANCE'
    Then match response.errorRecords[0].reference == '10104'




