package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class AnswerRepositoryTest {
    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    final Question question = new Question("title", "contents");

    @BeforeEach
    void setUp() {
        questionRepository.save(question);
    }

    @Test
    void insert_이후_select() {
        // given
        final Answer answer = new Answer(UserTest.JAVAJIGI, question, "answer");

        // when
        answerRepository.save(answer);

        // then
        assertThat(answer.getId()).isNotNull();
        assertThat(answer).isEqualTo(answerRepository.findById(answer.getId()).get());
    }

    @Test
    void update_이후_select() {
        // given
        final Answer answer = new Answer(UserTest.JAVAJIGI, question, "answer");
        answerRepository.save(answer);
        final Question newQuestion = new Question("new title", "new contents");
        ;

        // when
        answer.toQuestion(newQuestion);

        // then
        assertThat(answer.getQuestionId()).isEqualTo(newQuestion.getId());
        assertThat(answer.getQuestionId()).isEqualTo(answerRepository.findById(answer.getId()).get().getQuestionId());
    }

    @Test
    void 질문글의_아이디로_삭제되지_않은_답변들을_조회할_수_있어야_한다() {
        // given
        final Answer answer1 = new Answer(UserTest.JAVAJIGI, question, "answer1");
        final Answer answer2 = new Answer(UserTest.SANJIGI, question, "answer2");
        answerRepository.save(answer1);
        answerRepository.save(answer2);

        // when
        final List<Answer> answers = answerRepository.findByQuestionIdAndDeletedFalse(question.getId());

        // then
        assertThat(answers.containsAll(Arrays.asList(answer1, answer2))).isTrue();
    }

    @Test
    void 삭제되지_않은_답변을_답변_아이디로_조회할_수_있어야_한다() {
        // given
        final Answer answer = new Answer(UserTest.JAVAJIGI, QuestionTest.Q1, "answer1");
        answerRepository.save(answer);

        // when
        final Answer selected = answerRepository.findByIdAndDeletedFalse(answer.getId()).get();

        // then
        assertThat(selected).isNotNull();
        assertThat(selected).isEqualTo(answer);
    }
}
