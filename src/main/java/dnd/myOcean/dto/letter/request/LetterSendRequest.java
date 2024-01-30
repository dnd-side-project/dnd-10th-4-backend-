package dnd.myOcean.dto.letter.request;

import dnd.myOcean.domain.member.Gender;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Null;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LetterSendRequest {

    @Null
    private String email;

    @NotEmpty
    private String content;

    private Gender gender;

    private Integer ageRangeStart;

    private Integer ageRangeEnd;
}
