describe('Tournament Tests', () => {
    beforeEach(()=> {
        cy.demoStudentLogin()
        cy.listTournaments()
    })

    afterEach( () => {
        cy.contains('Logout').click()
    })

    it('login creates a tournament, enrolls in it and deletes it', () => {
        cy.createTournament('Tournament Title1','2020-09-22 12:12','2020-10-22 12:12','5',['Adventure Builder'])

        cy.enrollTournament('Tournament Title1')

        cy.cancelTournament('Tournament Title1')

    });


    it('login creates two tournaments with same name and deletes it', () => {
        cy.createTournament('Tournament Title2','2020-09-22 12:12','2020-10-22 12:12','5',['Adventure Builder'])

        cy.log('try to create with the same name')

        cy.createTournament('Tournament Title2','2020-09-23 12:12','2020-10-24 12:12','5',['Adventure Builder'])

        cy.closeErrorMessage()

        cy.log('close dialog')

        cy.get('[data-cy="closeButton"]').click()

        cy.listTournaments()

        cy.cancelTournament('Tournament Title2')
    });

    it('login creates tournament and tries to enroll twice and then cancels it', () => {
        cy.createTournament('Tournament Title2','2020-09-22 12:12','2020-10-22 12:12','5',['Adventure Builder'])

        cy.enrollTournament('Tournament Title2')

        cy.log('try to enroll twice')

        cy.enrollTournament('Tournament Title2')

        cy.closeErrorMessage()

        cy.cancelTournament('Tournament Title2')

    });
});
