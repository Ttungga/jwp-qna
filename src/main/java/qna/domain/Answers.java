package qna.domain;

import java.util.HashSet;
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

    public boolean allWrittenBy(final User writer) {
        return answers.stream()
                .allMatch(answer -> answer.isOwner(writer));
    }
}
