package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Test;

class AnswersTest {
    private User writer = new User(1L, "writer1", "password", "name", "email");
    private Question question = new Question(1L, "title", "contents");

    @Test
    void deleteAlongWithQuestion_호출_시_모든_답변들이_삭제되어야_한다() throws Exception {
        // given
        final Answer answer1 = new Answer(1L, writer, question, "contents");
        final Answer answer2 = new Answer(2L, writer, question, "contents");
        final Answer answer3 = new Answer(3L, writer, question, "contents");

        final Answers answers = new Answers();
        answers.add(answer1);
        answers.add(answer2);
        answers.add(answer3);

        // when
        final List<DeleteHistory> deleteHistories = answers.deleteAlongWithQuestion(writer);

        // then
        assertThat(answer1.isDeleted() && answer2.isDeleted() && answer3.isDeleted()).isTrue();
        assertThat(deleteHistories.size()).isEqualTo(3);
        assertThat(deleteHistories).isInstanceOf(LinkedList.class);
    }
}
