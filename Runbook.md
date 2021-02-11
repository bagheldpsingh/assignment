# Runbook for Customer Statement Processor API

This API is built using Springboot, Jersey and Maven. For integration test It is using Karate.  It is stateless api and not storing any data.
It will read statement record in Json format and validate it based on below requirements

## Input

The format is as follows: Table 1. Record description

| Field                  | Description                                      | 
|------------------------|------------------------------------------------- |
| Transaction reference  |  A numeric value                                   |
| Account number         |  An IBAN                                         |
| Start Balance          |  The starting balance in Euros                   |
| Mutation               |  Either an addition (+) or a deduction (-)       |
| Description            | Free text                                        |
| End Balance            | The end balance in Euros                         |

## Requirements

1. All transaction references should be unique
2. The end balance needs to be validated ( Start Balance +/- Mutation = End Balance )

## Expected Output

| Http Status Code  | Condition                                                         |  Response format |
|---                |---                                                                |---               |
| 200               |When there are no duplicate reference and correct end balance     | `{"result" : "SUCCESSFUL", "errorRecords" : []}`|
| 200               |When there are duplicate reference and correct balance             |[duplicateReferenceAndcorrectBalance Json](./duplicateReferenceAndcorrectBalance.json)|
| 200               |When there are no duplicate reference and In correct balance       |[IncorrectBalance Json](./IncorrectBalance.json)|
| 200               |When there are duplicate reference and In correct balance          |[duplicateReferenceAndIncorrectBalance Json](./duplicateReferenceAndIncorrectBalance.json)|
| 400               |Error during parsing JSON                                          | `{"result" : "BAD_REQUEST", "errorRecords" : []}`|
| 500               |Any other situation                                                |`{"result" : "INTERNAL_SERVER_ERROR","errorRecords" : [] }`|

## Run Instructions 
It is Maven base project so for executing it below common from terminal or IDE.
### To Run API
1. mvn clean install
2. mvn spring-boot:run pom.xml

### To Run Test

#### Pre Conditions
 1. Application should be running on Localhost and port 8080

#### Steps to Execute Test
1. Run TestRunner.class as Junit
