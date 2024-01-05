import { RequestLogger, RequestMock, Selector, fixture } from 'testcafe';

const logger = RequestLogger();

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

const requestMock = RequestMock()
  .onRequestTo({ url: 'http://localhost:8080/tasks', method: 'GET' })
  .respond([testTask, testTask2], 200, { 'access-control-allow-origin': '*' })
  .onRequestTo('http://localhost:8080/tasks/filter/title?search=test')
  .respond([testTask], 200, { 'access-control-allow-origin': '*' })
  .onRequestTo('http://localhost:8080/tasks/chronologically')
  .respond([testTask, testTask2], 200, { 'access-control-allow-origin': '*' })
  .onRequestTo('http://localhost:8080/tasks/completed')
  .respond([testTask2], 200, { 'access-control-allow-origin': '*' })
  .onRequestTo('http://localhost:8080/tasks/uncompleted')
  .respond([testTask], 200, { 'access-control-allow-origin': '*' })
  .onRequestTo('http://localhost:8080/tasks/filter/priority/1')
  .respond([testTask], 200, { 'access-control-allow-origin': '*' })
  .onRequestTo({ url: 'http://localhost:8080/tasks', method: 'POST' })
  .respond(newTask, 200, { 'access-control-allow-origin': '*' })
  .onRequestTo({
    url: `http://localhost:8080/tasks/${testTask.id}`,
    method: 'PUT',
  })
  .respond(testTask, 200, { 'access-control-allow-origin': '*' })
  .onRequestTo({
    url: `http://localhost:8080/tasks/${testTask.id}`,
    method: 'DELETE',
  })
  .respond({}, 200, { 'access-control-allow-origin': '*' });

fixture`Task Manager Tests`.page`http://localhost:5173`.requestHooks(
  requestMock,
  logger,
);

const getReturnedTasks = () => Selector('a[href^="/edit/"]');
const getTaskLink = (id: number) => Selector(`a[href="/edit/${id}"]`);

test('Getting tasks works', async (t) => {
  await t
    .expect(
      logger.contains(
        (record) =>
          record.request.url.endsWith('tasks') &&
          record.response.statusCode === 200,
      ),
    )
    .ok();
  await t.expect(getReturnedTasks().count).eql(2);
});

test('Searching tasks works', async (t) => {
  await t.typeText('#default-search', 'test').click('#search-submit');
  await t
    .expect(
      logger.contains(
        (record) =>
          record.request.url.includes('title?search=test') &&
          record.response.statusCode === 200,
      ),
    )
    .ok();
  await t.expect(getReturnedTasks().count).eql(1);
});

test('Sorting tasks chronologically works', async (t) => {
  await t.click('#sort-option').click('#Date-tasks-sort').click('#sort-submit');
  await t
    .expect(
      logger.contains(
        (record) =>
          record.request.url.includes('tasks/chronologically') &&
          record.response.statusCode === 200,
      ),
    )
    .ok();
  await t.expect(getReturnedTasks().count).eql(2);
});

test('Filtering completed tasks works', async (t) => {
  await t
    .click('#filter-option')
    .click('#Done-tasks-filter')
    .click('#filter-submit');
  await t
    .expect(
      logger.contains(
        (record) =>
          record.request.url.includes('tasks/completed') &&
          record.response.statusCode === 200,
      ),
    )
    .ok();
  await t.expect(getReturnedTasks().count).eql(1);
});

test('Filtering uncompleted tasks works', async (t) => {
  await t
    .click('#filter-option')
    .click('#Undone-tasks-filter')
    .click('#filter-submit');
  await t
    .expect(
      logger.contains(
        (record) =>
          record.request.url.includes('tasks/uncompleted') &&
          record.response.statusCode === 200,
      ),
    )
    .ok();
  await t.expect(getReturnedTasks().count).eql(1);
});

test('Filtering tasks by priority works', async (t) => {
  await t
    .click('#filter-option')
    .typeText('#number-input', '1')
    .click('#filter-submit');
  await t
    .expect(
      logger.contains(
        (record) =>
          record.request.url.includes('filter/priority/1') &&
          record.response.statusCode === 200,
      ),
    )
    .ok();
  await t.expect(getReturnedTasks().count).eql(1);
});

test('Clicking a task fills task data in the task form', async (t) => {
  await t
    .click(getTaskLink(testTask.id))
    .expect(Selector('input[name="title"]').value)
    .eql(testTask.title)
    .expect(Selector('textarea[name="description"]').value)
    .eql(testTask.description)
    .expect(Selector('input[name="deadline"]').value)
    .eql(testTask.deadline)
    .expect(Selector('input[name="priority"]').value)
    .eql(testTask.priority.toString())
    .expect(Selector('input[name="done"]').checked)
    .eql(testTask.done);
});

test('Creating a new task works', async (t) => {
  await t
    .click(Selector('button[data-tooltip-target="tooltip-create-task"]'))
    .typeText(Selector('input[name="title"]'), newTask.title)
    .typeText(Selector('textarea[name="description"]'), newTask.description)
    .typeText(Selector('input[name="deadline"]'), newTask.deadline)
    .typeText(Selector('input[name="priority"]'), newTask.priority.toString())
    .click(Selector('button[type="submit"]'));

  await t
    .expect(
      logger.contains(
        (record) =>
          record.request.url.endsWith('tasks') &&
          record.request.method === 'post' &&
          record.response.statusCode === 200,
      ),
    )
    .ok();
});

test('Updating a task works', async (t) => {
  await t
    .click(getTaskLink(testTask.id))
    .selectText(Selector('input[name="title"]'))
    .pressKey('delete')
    .typeText(Selector('input[name="title"]'), 'New title')
    .selectText(Selector('textarea[name="description"]'))
    .pressKey('delete')
    .typeText(Selector('textarea[name="description"]'), 'New description')
    .selectText(Selector('input[name="deadline"]'))
    .pressKey('delete')
    .typeText(Selector('input[name="deadline"]'), '2024-01-19')
    .selectText(Selector('input[name="priority"]'))
    .pressKey('delete')
    .typeText(Selector('input[name="priority"]'), '3')
    .click(Selector('button[type="submit"]'));

  await t
    .expect(
      logger.contains(
        (record) =>
          record.request.url.endsWith(`tasks/${testTask.id}`) &&
          record.request.method === 'put' &&
          record.response.statusCode === 200,
      ),
    )
    .ok();
});

test('Deleting a task works', async (t) => {
  await t.click(getTaskLink(testTask.id)).click('#delete-task');

  await t
    .expect(
      logger.contains(
        (record) =>
          record.request.url.endsWith(`tasks/${testTask.id}`) &&
          record.request.method === 'delete' &&
          record.response.statusCode === 200,
      ),
    )
    .ok();
});
