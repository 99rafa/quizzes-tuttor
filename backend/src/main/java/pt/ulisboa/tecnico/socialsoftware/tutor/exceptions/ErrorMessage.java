package pt.ulisboa.tecnico.socialsoftware.tutor.exceptions;

public enum ErrorMessage {
    TOURNAMENT_AVAILABLE_DATE_NOT_READY("Tournament available didnt come yet"),
    TOURNAMENT_CONCLUSION_DATE_PASSED("Tournament conclusion date already passed"),
    TOURNAMENT_ALREADY_OPEN("Cant Open an already open tournament"),
    TOURNAMENT_INVALID_STATE("Tournament state is empty or invalid"),
    TOURNAMENT_HAS_NO_CREATOR("Tournament has no creator"),
    TOURNAMENT_CANCELER_IS_NOT_CREATOR("Cant cancel/update a tournament you didnt create"),
    TOURNAMENT_ALREADY_CLOSED("Cant close/open an already closed tournament"),
    TOURNAMENT_ID_NOT_EXISTS("Invalid tournament id"),
    TOURNAMENT_TITLE_IS_EMPTY("This tournament title is empty"),
    TOURNAMENT_INVALID_DATE("This tournament date is invalid"),
    TOURNAMENT_EMPTY_DATE("This tournament date is empty"),
    TOURNAMENT_NO_TOPICS("This tournament has no topics"),
    TOURNAMENT_IS_NOT_OPEN("Tournament is already closed"),
    TOURNAMENT_TITLE_ALREADY_USED("There is already a tournament with the title %s "),
    TOURNAMENT_ALREADY_HAS_USERS("Cant delete a tournament that has signed users"),

    USER_IS_NOT_STUDENT("This user is not a student"),
    USER_IS_ALREADY_ENROLLED("This user is already enrolled"),

    QUIZ_NOT_FOUND("Quiz not found with id %d"),
    QUIZ_QUESTION_NOT_FOUND("Quiz question not found with id %d"),
    QUIZ_ANSWER_NOT_FOUND("Quiz answer not found with id %d"),
    QUESTION_ANSWER_NOT_FOUND("Question answer not found with id %d"),

    INVALID_ACADEMIC_TERM_FOR_COURSE_EXECUTION("Invalid academic term for course execution"),
    INVALID_ACRONYM_FOR_COURSE_EXECUTION("Invalid acronym for course execution"),
    INVALID_CONTENT_FOR_OPTION("Invalid content for option"),
    INVALID_CONTENT_FOR_QUESTION("Invalid content for question"),
    INVALID_NAME_FOR_COURSE("Invalid name for course"),
    INVALID_NAME_FOR_TOPIC("Invalid name for topic"),
    INVALID_SEQUENCE_FOR_OPTION("Invalid sequence for option"),
    INVALID_SEQUENCE_FOR_QUESTION_ANSWER("Invalid sequence for question answer"),
    INVALID_TITLE_FOR_ASSESSMENT("Invalid title for assessment"),
    INVALID_TITLE_FOR_QUESTION("Invalid title for question"),
    INVALID_URL_FOR_IMAGE("Invalid url for image"),
    INVALID_TYPE_FOR_COURSE("Invalid type for course"),
    INVALID_TYPE_FOR_COURSE_EXECUTION("Invalid type for course execution"),
    INVALID_AVAILABLE_DATE_FOR_QUIZ("Invalid available date for quiz"),
    INVALID_CONCLUSION_DATE_FOR_QUIZ("Invalid conclusion date for quiz"),
    INVALID_RESULTS_DATE_FOR_QUIZ("Invalid results date for quiz"),
    INVALID_TITLE_FOR_QUIZ("Invalid title for quiz"),
    INVALID_TYPE_FOR_QUIZ("Invalid type for quiz"),
    INVALID_QUESTION_SEQUENCE_FOR_QUIZ("Invalid question sequence for quiz"),

    ASSESSMENT_NOT_FOUND("Assessment not found with id %d"),
    COURSE_EXECUTION_NOT_FOUND("Course execution not found with id %d"),
    OPTION_NOT_FOUND("Option not found with id %d"),
    QUESTION_NOT_FOUND("Question not found with id %d"),
    TOPIC_CONJUNCTION_NOT_FOUND("Topic Conjunction not found with id %d"),
    SUBMISSION_NOT_FOUND("Submission not found with id %d"),
    SUBMISSION_ALREADY_EVALUATED("Submission was previously evaluated"),

    SUBMISSION_CANNOT_BE_EDITED("Question was not approved so it cannot be edited"),
    SUBMISSION_CANNOT_BE_RESUBMITED("Question is approved and cannot be re-submitted"),
    QUESTION_CANNOT_BE_AVAILABLE("Questions not approved cannot be made available"),
    QUESTION_ALREADY_AVAILABLE("Question is already available"),
    TOPICS_CANNOT_BE_EDITED("Topics cannot be edited in this stage"),


    TOPIC_NOT_FOUND("Topic not found with id %d"),
    USER_NOT_FOUND("User not found with id %d"),
    COURSE_NOT_FOUND("Course not found with name %s"),

    CANNOT_DELETE_COURSE_EXECUTION("The course execution cannot be deleted %s"),
    USERNAME_NOT_FOUND("Username %d not found"),

    QUIZ_USER_MISMATCH("Quiz %s is not assigned to student %s"),
    QUIZ_MISMATCH("Quiz Answer Quiz %d does not match Quiz Question Quiz %d"),
    QUESTION_OPTION_MISMATCH("Question %d does not have option %d"),
    COURSE_EXECUTION_MISMATCH("Course Execution %d does not have quiz %d"),

    DUPLICATE_TOPIC("Duplicate topic: %s"),
    DUPLICATE_USER("Duplicate user: %s"),
    DUPLICATE_COURSE_EXECUTION("Duplicate course execution: %s"),

    USERS_IMPORT_ERROR("Error importing users: %s"),
    QUESTIONS_IMPORT_ERROR("Error importing questions: %s"),
    TOPICS_IMPORT_ERROR("Error importing topics: %s"),
    ANSWERS_IMPORT_ERROR("Error importing answers: %s"),
    QUIZZES_IMPORT_ERROR("Error importing quizzes: %s"),

    QUESTION_IS_USED_IN_QUIZ("Question is used in quiz %s"),
    USER_NOT_ENROLLED("%s - Not enrolled in any available course"),
    QUIZ_NO_LONGER_AVAILABLE("This quiz is no longer available"),
    QUIZ_NOT_YET_AVAILABLE("This quiz is not yet available"),

    NO_CORRECT_OPTION("Question does not have a correct option"),
    NOT_ENOUGH_QUESTIONS("Not enough questions to create a quiz"),
    ONE_CORRECT_OPTION_NEEDED("Questions need to have 1 and only 1 correct option"),
    CANNOT_CHANGE_ANSWERED_QUESTION("Can not change answered question"),
    QUIZ_HAS_ANSWERS("Quiz already has answers"),
    QUIZ_ALREADY_COMPLETED("Quiz already completed"),
    QUIZ_ALREADY_STARTED("Quiz was already started"),
    QUIZ_QUESTION_HAS_ANSWERS("Quiz question has answers"),
    FENIX_ERROR("Fenix Error"),
    AUTHENTICATION_ERROR("Authentication Error"),
    FENIX_CONFIGURATION_ERROR("Incorrect server configuration files for fenix"),
    NOT_TEACHER_ERROR("User is not teacher"),
    NOT_STUDENT_ERROR("User is not student"),



    ACCESS_DENIED("You do not have permission to view this resource"),
    CANNOT_OPEN_FILE("Cannot open file");

    public final String label;

    ErrorMessage(String label) {
        this.label = label;
    }
}