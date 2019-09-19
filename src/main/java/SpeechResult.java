import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class SpeechResult {
    String fileName;
    String AudioText;
    double audioDuration;
    String Emotion;
}
