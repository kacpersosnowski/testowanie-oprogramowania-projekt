const assert = import('assert');
import { Builder, By, Key, until } from 'selenium-webdriver';
import { describe, it, after } from 'mocha';


const testTask = {
    id: 1,
    title: 'Test task',
    description: 'Test',
    done: false,
    deadline: '2024-01-01',
    priority: 1,
};

const testTask2 = {
    id: 2,
    title: 'Task 2',
    description: 'Some description',
    done: true,
    deadline: '2024-01-05',
    priority: 3,
};

const newTask = {
    id: 3,
    title: 'My task 3',
    description: 'Task description 3',
    done: false,
    deadline: '2024-01-12',
    priority: 2,
};

const getTaskLink = (id) => `a[href="/edit/${id}"]`;

describe('Task Manager Tests', function () {
    let driver;

    before(async function () {
        driver = await new Builder().forBrowser('chrome').build();
    });

    after(async function () {
        await driver.quit();
    });

    it('Getting tasks works', async function () {
        this.timeout(5000);

        await driver.get('http://localhost:5173');
        await driver.wait(until.elementLocated(By.css('.task-list-item')), 2000);

        const taskListItems = await driver.findElements(By.css('.task-list-item'));
        assert.ok(taskListItems.length > 0, 'No tasks found on the page');
    });

    it('Searching tasks works', async function () {
        await driver.get('http://localhost:5173');
        await driver.findElement(By.css('#default-search')).sendKeys('test');
        await driver.findElement(By.css('#search-submit')).click();

        
    });

    it('Sorting tasks chronologically works', async function () {
        await driver.get('http://localhost:5173');
        await driver.findElement(By.css('#sort-option')).click();
        await driver.findElement(By.css('#Date-tasks-sort')).click();
        await driver.findElement(By.css('#sort-submit')).click();

        
    });

    it('Filtering completed tasks works', async function () {
        await driver.get('http://localhost:5173');
        await driver.findElement(By.css('#filter-option')).click();
        await driver.findElement(By.css('#Done-tasks-filter')).click();
        await driver.findElement(By.css('#filter-submit')).click();

        
    });

    it('Filtering uncompleted tasks works', async function () {
        await driver.get('http://localhost:5173');
        await driver.findElement(By.css('#filter-option')).click();
        await driver.findElement(By.css('#Undone-tasks-filter')).click();
        await driver.findElement(By.css('#filter-submit')).click();

        
    });

    it('Filtering tasks by priority works', async function () {
        await driver.get('http://localhost:5173');
        await driver.findElement(By.css('#filter-option')).click();
        await driver.findElement(By.css('#number-input')).sendKeys('1');
        await driver.findElement(By.css('#filter-submit')).click();

        
    });

    it('Clicking a task fills task data in the task form', async function () {
        await driver.get('http://localhost:5173');
        await driver.wait(until.elementLocated(By.css('a[href="/edit/1"]')), 5000);
        await driver.findElement(By.css('a[href="/edit/1"]')).click();

        const titleValue = await driver.findElement(By.css('input[name="title"]')).getAttribute('value');
        const descriptionValue = await driver.findElement(By.css('textarea[name="description"]')).getAttribute('value');
        const deadlineValue = await driver.findElement(By.css('input[name="deadline"]')).getAttribute('value');
        const priorityValue = await driver.findElement(By.css('input[name="priority"]')).getAttribute('value');
        const doneValue = await driver.findElement(By.css('input[name="done"]')).isSelected();

        assert.strictEqual(titleValue, testTask.title);
        assert.strictEqual(descriptionValue, testTask.description);
        assert.strictEqual(deadlineValue, testTask.deadline);
        assert.strictEqual(priorityValue, testTask.priority.toString());
        assert.strictEqual(doneValue, testTask.done);
    });

    it('Creating a new task works', async function () {
        await driver.get('http://localhost:5173');
        await driver.findElement(By.css('button[data-tooltip-target="tooltip-create-task"]')).click();
        await driver.findElement(By.css('input[name="title"]')).sendKeys(newTask.title);
        await driver.findElement(By.css('textarea[name="description"]')).sendKeys(newTask.description);
        await driver.findElement(By.css('input[name="deadline"]')).sendKeys(newTask.deadline);
        await driver.findElement(By.css('input[name="priority"]')).sendKeys(newTask.priority.toString());
        await driver.findElement(By.css('button[type="submit"]')).click();

        
    });

    it('Updating a task works', async function () {
        await driver.get('http://localhost:5173');
        await driver.findElement(By.css(getTaskLink(testTask.id))).click();

        await driver.findElement(By.css('input[name="title"]')).clear();
        await driver.findElement(By.css('input[name="title"]')).sendKeys('New title');
        await driver.findElement(By.css('textarea[name="description"]')).clear();
        await driver.findElement(By.css('textarea[name="description"]')).sendKeys('New description');
        await driver.findElement(By.css('input[name="deadline"]')).clear();
        await driver.findElement(By.css('input[name="deadline"]')).sendKeys('2024-01-19');
        await driver.findElement(By.css('input[name="priority"]')).clear();
        await driver.findElement(By.css('input[name="priority"]')).sendKeys('3');
        await driver.findElement(By.css('button[type="submit"]')).click();

        
    });

    it('Deleting a task works', async function () {
        await driver.get('http://localhost:5173');
        await driver.findElement(By.css(getTaskLink(testTask.id))).click();
        await driver.findElement(By.css('#delete-task')).click();

        
    });
});
