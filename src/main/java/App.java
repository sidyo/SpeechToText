import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class App {
    private static final String BASE_PATH = "src/main/resources/";

    public static void main(String[] args) {
     EmotionDetector emotionDetector= new EmotionDetector();
    if(args.length != 2){
            System.out.println("This application receives 2 parameters in the following order:");
            System.out.println("Name of the folder containing the audio files.");
            System.out.println("The number of audio files.");
            System.exit(-1);
        }

        String folder = args[0];
        int numberOfFiles = Integer.parseInt(args[1]);

        SpeechToText speechToText = new SpeechToText();

        List<SpeechResult> audioTranscriptions = new LinkedList<>();
        for (int i = 0; i < numberOfFiles; i++) {
            String fileName = folder+"/"+folder+i+".wav";
            try {
                SpeechResult transcription = speechToText.buildSpeechResult(fileName);
                transcription.setEmotion(emotionDetector.findEmotion(transcription.getTranscription()));
                audioTranscriptions.add(transcription);
                System.out.println("---------");
            }catch (IOException ex){
                System.out.println("Please configure a environment variable named 'GOOGLE_APPLICATION_CREDENTIALS' with the path to the JSON key for the Google Cloud Service Account.");
                System.exit(-1);
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            }
        }

        try (FileWriter writer = new FileWriter(BASE_PATH+folder+"/"+folder+"Transcription.txt");
             BufferedWriter bw = new BufferedWriter(writer)) {
            for (SpeechResult r :
                    audioTranscriptions) {
                bw.write(r.toFileText()+"\n");
            };
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }

        System.out.println("Transcription file created in "+BASE_PATH+folder+"/"+folder+"Transcription.txt");
    }
}
