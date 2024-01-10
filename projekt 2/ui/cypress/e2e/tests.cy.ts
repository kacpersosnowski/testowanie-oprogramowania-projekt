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

describe('Task Manager Tests', () => {
  beforeEach(() => {
    cy.intercept('GET', 'http://localhost:8080/tasks', [
      testTask,
      testTask2,
    ]).as('getTasks');
    cy.intercept(
      'GET',
      'http://localhost:8080/tasks/filter/title?search=test',
      [testTask],
    ).as('searchTasks');
    cy.intercept('GET', 'http://localhost:8080/tasks/chronologically', [
      testTask,
      testTask2,
    ]).as('sortTasks');
    cy.intercept('GET', 'http://localhost:8080/tasks/completed', [
      testTask2,
    ]).as('filterCompletedTasks');
    cy.intercept('GET', 'http://localhost:8080/tasks/uncompleted', [
      testTask,
    ]).as('filterUncompletedTasks');
    cy.intercept('GET', 'http://localhost:8080/tasks/filter/priority/1', [
      testTask,
    ]).as('filterPriorityTasks');
    cy.intercept('POST', 'http://localhost:8080/tasks', {
      body: newTask,
      statusCode: 200,
    }).as('createTask');
    cy.intercept('PUT', `http://localhost:8080/tasks/${testTask.id}`, {
      body: testTask,
      statusCode: 200,
    }).as('updateTask');
    cy.intercept('DELETE', `http://localhost:8080/tasks/${testTask.id}`, {
      body: {},
      statusCode: 200,
    }).as('deleteTask');

    cy.visit('http://localhost:5173');
  });

  it('Getting tasks works', () => {
    cy.wait('@getTasks');
    cy.get('a[href^="/edit/"]').should('have.length', 2);
  });

  it('Searching tasks works', () => {
    cy.get('#default-search').type('test');
    cy.get('#search-submit').click();
    cy.wait('@searchTasks');
    cy.get('a[href^="/edit/"]').should('have.length', 1);
  });

  it('Sorting tasks chronologically works', () => {
    cy.get('#sort-option').click();
    cy.get('#Date-tasks-sort').click();
    cy.get('#sort-submit').click();
    cy.wait('@sortTasks');
    cy.get('a[href^="/edit/"]').should('have.length', 2);
  });

  it('Filtering completed tasks works', () => {
    cy.get('#filter-option').click();
    cy.get('#Done-tasks-filter').click();
    cy.get('#filter-submit').click();
    cy.wait('@filterCompletedTasks');
    cy.get('a[href^="/edit/"]').should('have.length', 1);
  });

  it('Filtering uncompleted tasks works', () => {
    cy.get('#filter-option').click();
    cy.get('#Undone-tasks-filter').click();
    cy.get('#filter-submit').click();
    cy.wait('@filterUncompletedTasks');
    cy.get('a[href^="/edit/"]').should('have.length', 1);
  });

  it('Filtering tasks by priority works', () => {
    cy.get('#filter-option').click();
    cy.get('#number-input').type('1');
    cy.get('#filter-submit').click();
    cy.wait('@filterPriorityTasks');
    cy.get('a[href^="/edit/"]').should('have.length', 1);
  });

  it('Clicking a task fills task data in the task form', () => {
    cy.get(`a[href="/edit/${testTask.id}"]`).click();
    cy.get('input[name="title"]').should('have.value', testTask.title);
    cy.get('textarea[name="description"]').should(
      'have.value',
      testTask.description,
    );
    cy.get('input[name="deadline"]').should('have.value', testTask.deadline);
    cy.get('input[name="priority"]').should(
      'have.value',
      testTask.priority.toString(),
    );
    cy.get('input[name="done"]').should('have.prop', 'checked', testTask.done);
  });

  it('Creating a new task works', () => {
    cy.get('button[data-tooltip-target="tooltip-create-task"]').click();
    cy.get('input[name="title"]').type(newTask.title);
    cy.get('textarea[name="description"]').type(newTask.description);
    cy.get('input[name="deadline"]').type(newTask.deadline);
    cy.get('input[name="priority"]').type(newTask.priority.toString());
    cy.get('button[type="submit"]').click();
    cy.wait('@createTask');
  });

  it('Updating a task works', () => {
    cy.get(`a[href="/edit/${testTask.id}"]`).click();
    cy.get('input[name="title"]').clear().type('New title');
    cy.get('textarea[name="description"]').clear().type('New description');
    cy.get('input[name="deadline"]').clear().type('2024-01-19');
    cy.get('input[name="priority"]').clear().type('3');
    cy.get('button[type="submit"]').click();
    cy.wait('@updateTask');
  });

  it('Deleting a task works', () => {
    cy.get(`a[href="/edit/${testTask.id}"]`).click();
    cy.get('#delete-task').click();
    cy.wait('@deleteTask');
  });
});
