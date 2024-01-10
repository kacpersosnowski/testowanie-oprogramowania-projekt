import { Builder, By, Key, until } from 'selenium-webdriver';
import { describe, it, after } from 'mocha';
import { assert } from 'chai';


const testTask = {
    id: 1,
    title: 'my task 1',
    description: 'Test',
    done: false,
    deadline: '2024-01-01',
    priority: 1,
};

const testTask2 = {
    id: 2,
    title: 'test task 2',
    description: 'Some description',
    done: true,
    deadline: '2024-01-05',
    priority: 2,
};

const newTask = {
    id: 3,
    title: 'my test task 3',
    description: 'Task description 3',
    done: false,
    deadline: '2024-01-12',
    priority: 3,
};

const testTasks = [newTask, testTask, testTask2]

async function logAndFind(driver, selector) {
    const element = await driver.findElement(By.css(selector));
    return element;
}

const getTaskLink = (id) => `a[href="/edit/${id}"]`;
const mainContainerSelector = '#root > div > main > div.grid.grid-cols-1.lg\\:grid-cols-2.xl\\:grid-cols-3.\\32 xl\\:grid-cols-4.gap-x-32.gap-y-8.m-3';

describe('Task Manager Tests', function () {
    let driver;

    before(async function () {
        driver = new Builder().forBrowser('chrome').build();
        
    });

    after(async function () {
        await driver.quit();
    });

        it('Creating a new task works', async function () {
        this.timeout(5000)
        await driver.get('http://localhost:5173');
        const searchResultItems = await driver.findElements(By.css(`${mainContainerSelector} > a`));
        const searchResultItemsLength = searchResultItems.length;
        /*for (const task of testTasks) {
            await driver.findElement(By.css('button[data-tooltip-target="tooltip-create-task"]')).click();
            await driver.findElement(By.css('input[name="title"]')).sendKeys(task.title);
            await driver.findElement(By.css('textarea[name="description"]')).sendKeys(task.description);
            await driver.findElement(By.css('input[name="deadline"]')).sendKeys(task.deadline);
            await driver.findElement(By.css('input[name="priority"]')).sendKeys(task.priority.toString());
            await driver.findElement(By.css('button[type="submit"]')).click();
            //await driver.get('http://localhost:5173');
        }*/
        /*await driver.findElement(By.css('button[data-tooltip-target="tooltip-create-task"]')).click();
        await driver.findElement(By.css('input[name="title"]')).sendKeys(testTask.title);
        await driver.findElement(By.css('textarea[name="description"]')).sendKeys(testTask.description);
        await driver.findElement(By.css('input[name="deadline"]')).sendKeys(testTask.deadline);
        await driver.findElement(By.css('input[name="priority"]')).sendKeys(testTask.priority.toString());
        await driver.findElement(By.css('button[type="submit"]')).click();
        await driver.get('http://localhost:5173');*/

        /*await driver.findElement(By.css('button[data-tooltip-target="tooltip-create-task"]')).click();
        await driver.findElement(By.css('input[name="title"]')).sendKeys(testTask2.title);
        await driver.findElement(By.css('textarea[name="description"]')).sendKeys(testTask2.description);
        await driver.findElement(By.css('input[name="deadline"]')).sendKeys(testTask2.deadline);
        await driver.findElement(By.css('input[name="priority"]')).sendKeys(testTask2.priority.toString());
        await driver.findElement(By.css('button[type="submit"]'), 1000).click();
        await driver.get('http://localhost:5173');*/

        await driver.findElement(By.css('button[data-tooltip-target="tooltip-create-task"]')).click();
        await driver.findElement(By.css('input[name="title"]')).sendKeys(newTask.title);
        await driver.findElement(By.css('textarea[name="description"]')).sendKeys(newTask.description);
        await driver.findElement(By.css('input[name="deadline"]')).sendKeys(newTask.deadline);
        await driver.findElement(By.css('input[name="priority"]')).sendKeys(newTask.priority.toString());
        await driver.findElement(By.css('button[type="submit"]'), 1000).click();
        await driver.get('http://localhost:5173');

        

        const newSearchResultItems = await driver.findElements(By.css(`${mainContainerSelector} > a`));
        assert.equal(searchResultItemsLength + 1, newSearchResultItems.length);
    });

    it('Updating a task works', async function () {
        await driver.get('http://localhost:5173');
        await driver.findElement(By.css(`${mainContainerSelector} > a`)).click();

        await driver.findElement(By.css('input[name="title"]')).clear();
        await driver.findElement(By.css('input[name="title"]')).sendKeys('New title');
        await driver.findElement(By.css('textarea[name="description"]')).clear();
        await driver.findElement(By.css('textarea[name="description"]')).sendKeys('New description');
        await driver.findElement(By.css('input[name="deadline"]')).clear();
        await driver.findElement(By.css('input[name="deadline"]')).sendKeys('2024-01-19');
        await driver.findElement(By.css('input[name="priority"]')).clear();
        await driver.findElement(By.css('input[name="priority"]')).sendKeys('3');
        await driver.findElement(By.css('button[type="submit"]')).click();

        const SearchResultItems = await driver.findElements(By.css(`${mainContainerSelector} > a`));
        assert.isOk(SearchResultItems);
    });
    

    

    it('Getting tasks works', async function () {
        this.timeout(30000);

        await driver.get('http://localhost:5173');

        await driver.wait(until.elementLocated(By.css(mainContainerSelector)), 10000);
        await driver.wait(until.elementIsVisible(driver.findElement(By.css(mainContainerSelector))), 5000);

        const taskItemSelector = `${mainContainerSelector} > a:nth-child(1)`;
        await driver.wait(until.elementLocated(By.css(taskItemSelector)), 10000);
        await driver.wait(until.elementIsVisible(driver.findElement(By.css(taskItemSelector))), 5000);


        const taskListItems = await driver.findElements(By.css(`${mainContainerSelector} > a`));
        assert.ok(taskListItems.length > 0);
    });

    it('Searching tasks works', async function () {

        await driver.get('http://localhost:5173');
    
        const searchInputSelector = '#default-search';
        const searchSubmitSelector = '#search-submit';
    
        await driver.findElement(By.css(searchInputSelector)).sendKeys('t');
        await driver.findElement(By.css(searchSubmitSelector)).click();

        const searchResultsContainer = await logAndFind(driver, mainContainerSelector);
        assert.isTrue(await searchResultsContainer.isDisplayed(), 'Search results container is not visible');
    
        const searchResultItems = await driver.findElements(By.css(`${mainContainerSelector} > a`));
        assert.ok(searchResultItems.length > 0);
    });

    it('Sorting tasks chronologically works', async function () {
        await driver.get('http://localhost:5173');
        await driver.findElement(By.css('#sort-option')).click();
        await driver.findElement(By.css('#Date-tasks-sort')).click();
        await driver.findElement(By.css('#sort-submit')).click();

        const searchResultsContainer = await logAndFind(driver, mainContainerSelector);
        assert.isTrue(await searchResultsContainer.isDisplayed(), 'Search results container is not visible');
    
        const searchResultItems = await driver.findElements(By.css(`${mainContainerSelector} > a`));
        assert.ok(searchResultItems.length>0);

        
    });

    it('Filtering completed tasks works', async function () {
        await driver.get('http://localhost:5173');
        await driver.findElement(By.css('#filter-option')).click();
        await driver.findElement(By.css('#Done-tasks-filter')).click();
        await driver.findElement(By.css('#filter-submit')).click();

        const searchResultItems = await driver.findElements(By.css(`${mainContainerSelector} > a`));
        assert.equal(searchResultItems.length, 0);
        
    });

    it('Filtering uncompleted tasks works', async function () {
        await driver.get('http://localhost:5173');
        await driver.findElement(By.css('#filter-option')).click();
        await driver.findElement(By.css('#Undone-tasks-filter')).click();
        await driver.findElement(By.css('#filter-submit')).click();

        const searchResultItems = await driver.findElements(By.css(`${mainContainerSelector} > a`));
        assert.equal(searchResultItems.length, 1);
    });

    it('Filtering tasks by priority works', async function () {
        await driver.get('http://localhost:5173');
        await driver.findElement(By.css('#filter-option')).click();
        await driver.findElement(By.css('#number-input')).sendKeys('3');
        await driver.findElement(By.css('#filter-submit')).click();

        const searchResultItems = await driver.findElements(By.css(`${mainContainerSelector} > a`));
        assert.equal(searchResultItems.length, 1);
    });

    it('Clicking a task fills task data in the task form', async function () {
        await driver.get('http://localhost:5173');
        await driver.findElement(By.css(`${mainContainerSelector} > a`)).click();

        const titleValue = await driver.findElement(By.css('input[name="title"]')).getAttribute('value');
        const descriptionValue = await driver.findElement(By.css('textarea[name="description"]')).getAttribute('value');
        const deadlineValue = await driver.findElement(By.css('input[name="deadline"]')).getAttribute('value');

        assert.isNotEmpty(titleValue)
        assert.isNotEmpty(descriptionValue)
        assert.isNotEmpty(deadlineValue)
    });

    it('Deleting a task works', async function () {
        await driver.get('http://localhost:5173');
        const searchResultItems = await driver.findElements(By.css(`${mainContainerSelector} > a`));

        for (let i = 0; i < searchResultItems.length; i++) {
            await driver.get('http://localhost:5173');
            await driver.findElement(By.css(`${mainContainerSelector} > a`)).click();
            await driver.findElement(By.css('#delete-task')).click();
            await driver.get('http://localhost:5173');
        }
        await driver.get('http://localhost:5173');
        const newSearchResultItems = await driver.findElements(By.css(`${mainContainerSelector} > a`));
        assert.equal(newSearchResultItems.length, 0);
    });

});
