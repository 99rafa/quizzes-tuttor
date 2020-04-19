package pt.ulisboa.tecnico.socialsoftware.tutor.tournament.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Topic
import pt.ulisboa.tecnico.socialsoftware.tutor.question.repository.TopicRepository
import pt.ulisboa.tecnico.socialsoftware.tutor.tournament.TournamentService
import pt.ulisboa.tecnico.socialsoftware.tutor.tournament.domain.Tournament
import pt.ulisboa.tecnico.socialsoftware.tutor.tournament.dto.TournamentDto
import pt.ulisboa.tecnico.socialsoftware.tutor.tournament.repository.TournamentRepository
import pt.ulisboa.tecnico.socialsoftware.tutor.user.User
import pt.ulisboa.tecnico.socialsoftware.tutor.user.UserRepository
import spock.lang.Specification

@DataJpaTest
class EnrollInTournamentPerformanceTest extends Specification {

    static final Integer KEY = 0
    static final User.Role ROLE = User.Role.STUDENT
    static final String TOURNAMENT_TITLE = "Tournament"
    static final String CREATION_DATE = "2020-09-22 12:12"
    static final String AVAILABLE_DATE = "2020-09-23 12:12"
    static final String CONCLUSION_DATE = "2020-09-24 12:12"


    @Autowired
    TournamentService tournamentService

    @Autowired
    TournamentRepository tournamentRepository

    @Autowired
    TopicRepository topicRepository

    @Autowired
    UserRepository userRepository

    def "performance testing to enroll 500 users in 1 tournament"() {
        given: "a tournament"

        def topic = new Topic()
        topicRepository.save(topic)

        def topiclist = new ArrayList<Integer>()
        topiclist.add(1)

        def tournament = new TournamentDto()
        tournament.setTitle(TOURNAMENT_TITLE)
        tournament.setAvailableDate(AVAILABLE_DATE)
        tournament.setConclusionDate(CONCLUSION_DATE)
        tournament.setCreationDate(CREATION_DATE)
        tournamentRepository.save(new Tournament(tournament))


        and: "500 users"

        ArrayList<User> userList = new ArrayList<User>()
        1.upto(500, {

            def user = new User()
            user.setKey(KEY + it.intValue())
            user.setId(KEY + it.intValue())
            user.setRole(ROLE)
            userList.add(user)
            userRepository.save(user)


        })

        when:
        1.upto(500,{
            println(userList.get(it.intValue()-1))
            tournamentService.enrollInTournament(1,userList.get(it.intValue()-1).getId())})

            then:
            true
        }



    @TestConfiguration
    static class TournamentServiceImplTestContextConfiguration {

        @Bean
        TournamentService tournamentService() {
            return new TournamentService()
        }

    }
}