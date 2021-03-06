describe('Student resubmits questions walkthrough', () => {
  beforeEach(() => {
    cy.demoStudentLogin();
    cy.accessStudentQuestionsPage();
    cy.submitQuestion(
      'Demo Question',
      'What is the best subject in the Computer engineering course at IST?',
      ['ES', 'AMS', 'GESTAO', 'LP'],
      true
    );
    cy.log('close dialog');
    cy.logout();
    cy.demoTeacherLogin();
    cy.accessTeacherSubmissionsPage();
  });

  afterEach(() => {
    cy.contains('Logout').click();
  });

  it('login submits question, then it is rejected by teacher, then resubmits', () => {
    cy.evaluateSubmission('Demo Question', false, 'Question sucks.', [false, true, true, true]);
    cy.log('close dialog');
    cy.logout();
    cy.demoStudentLogin();
    cy.accessStudentQuestionsPage();
    cy.reSubmitSubmission(
      'Demo Question',
      'Demo Question edited',
      'What is the best subject in the Computer engineering course at IST-Alameda?',
      ['ES', 'AMS', 'SD', 'SO']
    );
    cy.log('close dialog');
    cy.exec( 'psql tutordb < tests/e2e/specs/sql/deleteResubmittedSubmission.sql'
    );
  });

  it('login submits question, then it is approved by teacher, then tries to resubmit', () => {
    cy.evaluateSubmission(
      'Demo Question',
      true,
      'Question well structured and scientifically correct.', [false,false,false,false]
    );
    cy.log('close dialog');
    cy.logout();
    cy.demoStudentLogin();
    cy.accessStudentQuestionsPage();
    cy.reSubmitSubmission(
      'Demo Question',
      'Demo Question edited',
      'What is the best subject in the Computer engineering course at IST-Alameda?',
      ['ES', 'AMS', 'SD', 'SO']
    );
    cy.closeErrorMessage();
    cy.log('close dialog');
    cy.get('[data-cy="cancelSubmissionButton"]').click();

    cy.exec('psql tutordb < tests/e2e/specs/sql/deleteSubmission.sql');
  });
});
