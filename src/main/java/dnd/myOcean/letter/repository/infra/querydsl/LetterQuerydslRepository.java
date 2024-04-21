package dnd.myOcean.letter.repository.infra.querydsl;

import dnd.myOcean.letter.domain.dto.response.SendLetterResponse;
import dnd.myOcean.letter.domain.dto.response.StoredLetterResponse;
import dnd.myOcean.letter.repository.infra.querydsl.dto.LetterReadCondition;
import org.springframework.data.domain.Page;

public interface LetterQuerydslRepository {

    Page<SendLetterResponse> findAllPagedSendLetter(LetterReadCondition cond);

    Page<StoredLetterResponse> findAllPagedStoredLetter(LetterReadCondition cond);
}
