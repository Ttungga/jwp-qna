package qna.domain;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

@Embeddable
public class Answers {
    @OneToMany(mappedBy = "question")
    private Set<Answer> answers = new HashSet<>();

    protected Answers() {
    }

    public void add(final Answer answer) {
        answers.add(answer);
    }

    public void remove(final Answer answer) {
        answers.remove(answer);
    }

    public List<DeleteHistory> deleteAlongWithQuestion(final User questionWriter) {
        final List<DeleteHistory> deleteHistories = new LinkedList<>();
        for (final Answer answer : answers) {
            deleteHistories.add(answer.delete(questionWriter));
        }
        return deleteHistories;
    }
}
