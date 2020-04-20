describe('Tournament Tests', () => {
    beforeEach(()=> {

    })

    afterEach( () => {

    })


    it('login checks open tournaments list', () => {
        cy.exec('PGPASSWORD=tgllvg99 psql -d tutordb -U pedro -h localhost -c "INSERT INTO tournaments VALUES (1001 , \'2020-04-20 14:19:00\' , \'2020-04-20 14:20:00\' , \'2020-04-19 15:18:25\' , \'Torneio1\' , 676);" ')
        cy.exec('PGPASSWORD=tgllvg99 psql -d tutordb -U pedro -h localhost -c "INSERT INTO tournaments VALUES (1002 , \'2020-04-18 14:19:00\' , \'2020-04-25 14:20:00\' , \'2020-04-17 14:19:00\' , \'Torneio2\' , 676);" ')
        cy.exec('PGPASSWORD=tgllvg99 psql -d tutordb -U pedro -h localhost -c "INSERT INTO tournaments VALUES (1004 , \'2020-04-18 18:00:00\' , \'2022-04-25 14:20:00\' , \'2020-04-17 14:19:00\' , \'Torneio5\' , 676);" ')
        cy.exec('PGPASSWORD=tgllvg99 psql -d tutordb -U pedro -h localhost -c "INSERT INTO tournaments VALUES (1003 , \'2020-04-18 14:19:00\' , \'2020-04-19 14:20:00\' , \'2020-04-18 14:19:00\' , \'Torneio3\' , 676);" ')
        cy.exec('PGPASSWORD=tgllvg99 psql -d tutordb -U pedro -h localhost -c "INSERT INTO tournaments VALUES (1005 , \'2020-04-17 10:19:00\' , \'2020-04-19 14:20:00\' , \'2020-04-16 14:19:00\' , \'Torneio4\' , 676);" ')
        cy.exec('PGPASSWORD=tgllvg99 psql -d tutordb -U pedro -h localhost -c "INSERT INTO tournaments_signed_users VALUES (1001 , 676);" ')
        cy.exec('PGPASSWORD=tgllvg99 psql -d tutordb -U pedro -h localhost -c "INSERT INTO tournaments_signed_users VALUES (1002 , 676);" ')
        cy.exec('PGPASSWORD=tgllvg99 psql -d tutordb -U pedro -h localhost -c "INSERT INTO tournaments_signed_users VALUES (1003 , 676);" ')
        cy.exec('PGPASSWORD=tgllvg99 psql -d tutordb -U pedro -h localhost -c "INSERT INTO tournaments_signed_users VALUES (1004 , 676);" ')
        cy.exec('PGPASSWORD=tgllvg99 psql -d tutordb -U pedro -h localhost -c "INSERT INTO tournaments_signed_users VALUES (1005 , 676);" ')


        cy.demoStudentLogin()
        cy.listTournaments()

        cy.listOpenTournaments()

        //asserts
        cy.contains('Torneio5')
            .parent()
            .should('have.length', 1)
            .children()
            .should('have.length', 4)

        cy.contains('Torneio2')
            .parent()
            .should('have.length', 1)
            .children()
            .should('have.length', 4)

    });

    it('login checks closed tournaments list, ', () => {
        cy.listClosedTournaments()

        //asserts
        cy.contains('Torneio4')
            .parent()
            .should('have.length', 1)
            .children()
            .should('have.length', 3)

        cy.contains('Torneio3')
            .parent()
            .should('have.length', 1)
            .children()
            .should('have.length', 3)

    });

    it('login tries to cancel a  closed tournament', () => {
        cy.listTournaments()
        cy.cancelTournament('Torneio4')
        cy.closeErrorMessage()
    });

    it('login tries to sign into a  closed tournament', () => {
        cy.listTournaments()
        cy.enrollTournament('Torneio4')
        cy.closeErrorMessage()
    });

    it('login tries to sign into an open tournament', () => {
        cy.listTournaments()
        cy.enrollTournament('Torneio2')
        cy.closeErrorMessage()
    });


    it('login creates two tournaments with same name and deletes it', () => {
        cy.listTournaments()

        cy.createTournament('Tournament Title2','2020-09-22 12:12','2020-10-22 12:12','5',['Adventure Builder'])

        cy.log('try to create with the same name')

        cy.createTournament('Tournament Title2','2020-09-23 12:12','2020-10-24 12:12','5',['Adventure Builder'])

        cy.closeErrorMessage()

        cy.log('close dialog')

        cy.get('[data-cy="closeButton"]').click()

        cy.listTournaments()

        cy.cancelTournament('Tournament Title2')
    });


    it('login creates tournament and tries to enroll twice', () => {
        cy.listTournaments()

        cy.createTournament('Tournament Title2','2020-09-22 12:12','2020-10-22 12:12','5',['Adventure Builder'])

        cy.enrollTournament('Tournament Title2')

        cy.log('try to enroll twice')

        cy.enrollTournament('Tournament Title2')

        cy.closeErrorMessage()

        cy.log('close dialog')

        cy.cancelTournament('Tournament Title2')
    });

    it('login creates a tournament, enrolls in it and deletes it', () => {
        cy.listTournaments()

        cy.createTournament('Tournament Title1','2020-09-22 12:12','2020-10-22 12:12','5',['Adventure Builder'])

        cy.enrollTournament('Tournament Title1')

        cy.cancelTournament('Tournament Title1')

    });

});