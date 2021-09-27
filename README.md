# JAVA-CRON-PARSER
## Implementation of Deliveroo Task

## The solution consists of following components:
- Cron - Repersents each given cron fields and file name 
- Field - ENUM value of each cron fields
- CronParser - responsible parsing each cron fields
- CronProcessor - The main class which proecsses the cron parsing requests 
- InvalidTokenException- Generic exception class to handle invalid tokens while parsing

## Implementation limitations
Due to time limitations the provided implementation is not perfect, the main focus is to 
provide a working cron expression parser with limited functionality
In particular the following still can be improved:
- Add input validaiton
- Add Proper exceptions handing. For now the we have extended a Exception class to throw all errors in InvalidTokenException
- Add Testcases, for testing all scenarios
- Add support for other cron expressions like: L, W, #, ?( for day of week and day of month values)

## Build instructions
Maven is used to build the project
After Maven installed and project is check out from the repository, execute the following command:
mvn clean install

### Run instructions:
 In command line execute:
 java -jar cron-1.0-SNAPSHOT.jar **"CRON_EXPRESSION"**
