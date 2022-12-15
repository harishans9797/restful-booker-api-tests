## RestFul Booker Test Automation Suite
This project is intended to do automated regression testing on the Restful Booker API.

## Run build
If you want to build project you need to execute following command in terminal:

`mvn clean install -Dmaven.test.skip=true`

## Run regression
If you would like to run all tests in parallel, execute following command in terminal:

`mvn test -Denv=$ENV`

For `$ENV` for now you can only use `qa`.
