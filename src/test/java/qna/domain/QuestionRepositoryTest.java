package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class QuestionRepositoryTest {
    @Autowired
    private QuestionRepository repository;

    @Test
    void insert_이후_select() {
        // given
        final Question question = new Question("title", "contends");

        // when
        repository.save(question);

        // then
        assertThat(question.getId()).isNotNull();
        assertThat(question).isEqualTo(repository.findById(question.getId()).get());
    }

    @Test
    void update_이후_select() {
        // given
        final Question question = new Question("title", "contents");
        repository.save(question);
        final User newWriter = UserTest.JAVAJIGI;

        // when
        question.writeBy(newWriter);

        // then
        assertThat(question.getWriterId()).isEqualTo(newWriter.getId());
        assertThat(question.getWriterId()).isEqualTo(repository.findById(question.getId()).get().getWriterId());
    }

    @Test
    void 삭제되지_않은_질문글들을_조회할_수_있어야_한다() {
        // given
        final Question question1 = new Question("title1", "contents1");
        final Question question2 = new Question("title2", "contents2");
        repository.save(question1);
        repository.save(question2);

        // when
        final List<Question> questions = repository.findByDeletedFalse();

        // then
        assertThat(questions.containsAll(Arrays.asList(QuestionTest.Q1, QuestionTest.Q2)));
    }

    @Test
    void 삭제되지_않은_질문글을_질문글_아이디로_조회할_수_있어야_한다() {
        // given
        final Question question = new Question("title", "contents");
        repository.save(question);

        // when
        final Question selected = repository.findByIdAndDeletedFalse(question.getId()).get();

        // then
        assertThat(selected).isNotNull();
        assertThat(selected).isEqualTo(question);
    }
}
