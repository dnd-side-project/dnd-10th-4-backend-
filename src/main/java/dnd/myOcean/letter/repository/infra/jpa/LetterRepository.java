package dnd.myOcean.letter.repository.infra.jpa;

import dnd.myOcean.letter.domain.Letter;
import dnd.myOcean.letter.repository.infra.querydsl.LetterQuerydslRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LetterRepository extends JpaRepository<Letter, Long>, LetterQuerydslRepository {

    @Query("SELECT l FROM Letter l JOIN FETCH l.receiver lr WHERE l.id = :id AND lr.id = :receiverId AND l.hasReplied = false AND l.isStored = false AND l.isDeleteByReceiver = false")
    Optional<Letter> findReceivedLetter(@Param("id") Long id,
                                        @Param("receiverId") Long receiverId);

    @Query("SELECT l FROM Letter l JOIN FETCH l.receiver lr WHERE lr.id = :receiverId AND l.hasReplied = false AND l.isStored = false AND l.isDeleteByReceiver = false")
    List<Letter> findAllReceivedLetters(@Param("receiverId") Long receiverId);

    @Query("SELECT l FROM Letter l JOIN FETCH l.sender ls WHERE l.id = :id AND ls.id = :senderId AND l.hasReplied = true AND l.isStored = false AND l.isDeleteBySender = false")
    Optional<Letter> findRepliedLetter(@Param("id") Long id,
                                       @Param("senderId") Long senderId);

    @Query("SELECT l FROM Letter l JOIN FETCH l.sender sr WHERE sr.id = :senderId AND l.hasReplied = true AND l.isStored = false AND l.isDeleteBySender = false")
    List<Letter> findAllRepliedLetter(@Param("senderId") Long senderId);

    @Query("SELECT l FROM Letter l JOIN FETCH l.sender ls WHERE l.id = :id AND ls.id = :senderId AND l.isDeleteBySender = false")
    Optional<Letter> findSendLetter(@Param("id") Long id,
                                    @Param("senderId") Long senderId);

    @Query("SELECT l FROM Letter l JOIN FETCH l.receiver lr WHERE l.id = :id AND lr.id = :receiverId")
    Optional<Letter> findByIdAndReceiverId(@Param("id") Long id,
                                           @Param("receiverId") Long receiverId);

    @Query("SELECT l FROM Letter l JOIN FETCH l.receiver lr WHERE l.id = :id AND lr.id = :receiverId AND l.isStored = false"
            + " AND l.letterType = 'Onboarding'")
    Optional<Letter> findByIdAndReceiverIdAndOnboardingLetter(@Param("id") Long id,
                                                              @Param("receiverId") Long receiverId);

    @Query("SELECT l FROM Letter l JOIN FETCH l.sender ls WHERE (l.id = :id AND ls.id = :senderId AND l.isStored = true) OR (l.letterType = 'Onboarding' AND l.id = :id)")
    Optional<Letter> findStoredLetter(@Param("id") Long id,
                                      @Param("senderId") Long senderId);

    @Query("SELECT l FROM Letter l WHERE l.uuid = :uuid")
    List<Letter> findAllByUuid(@Param("uuid") String uuid);

    @Modifying
    @Query("DELETE FROM Letter l WHERE l.createDate <= :expirationDate and l.hasReplied = false and l.letterType != 'Onboarding'")
    void deleteDiscardedLetters(@Param("expirationDate") LocalDateTime expirationDate);

    @Query("SELECT l FROM Letter l WHERE l.letterType = 'Onboarding' and l.receiver.id = :receiverId")
    boolean existsOnboardingLetter(@Param("receiverId") Long receiverId);
}
