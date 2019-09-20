import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class SpeechResult {
    private String fileName;
    private double duration;
    private String transcription;
    private String emotion;

    public void setEmotion(String emotion){
        try{
            Emotions emotions = Emotions.valueOf(emotion.toUpperCase());
            this.emotion = emotions.emotion;

        }catch(IllegalArgumentException e){
            System.out.print("Unsuported Emotion. Please choose one of the following: ");
            Emotions[] availableEmotions = Emotions.values();
            for (int i = 0; i < availableEmotions.length; i++) {
                System.out.print(availableEmotions[i].emotion);
                System.out.print(i!=availableEmotions.length-1? ", ": ".\n");
            }
        }
    }

    public String toFileText(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(fileName)
                .append(";")
                .append(duration)
                .append(";")
                .append(transcription)
                .append(";")
                .append(emotion);
        return stringBuilder.toString();
    }
}
