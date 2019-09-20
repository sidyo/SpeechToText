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
        if(emotion.equals("Happy")){
           this.emotion = emotion;
        }else{
            System.out.println("Unsuported Emotion. Please choose between Happy");
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
