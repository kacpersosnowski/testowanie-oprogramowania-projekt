# Run tests

## Testcafe

### From docker:

Run command: **docker compose exec testcafe testcafe remote tests/tests.ts**
Then navigate to the given url.

### Locally:

Install testcafe: **npm install -g testcafe**
Go to the testcafe directory and run command: **testcafe chrome tests.ts**


## Selenium

### Locally:

Install selenium webdriver: **npm install selenium-webdriver**
Install mocha testrunner: **npm install mocha**

Run: **npx mocha [Path to test file]**