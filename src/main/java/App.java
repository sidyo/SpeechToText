import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class App {

    public static void main(String[] args){
        if(args.length != 3){
            System.out.println("This application receives 3 parameters in the following order:");
            System.out.println("Name of the folder containing the audio files.");
            System.out.println("The format of the audio files.");
            System.out.println("The number of audio files.");
            System.exit(-1);
        }
        String folder = args[0];
        String fileFormat = args[1];
        int numberOfFiles = Integer.parseInt(args[2]);
        SpeechToText speechToText = null;
        try {
            speechToText = new SpeechToText();
        }catch (IOException ex){
            System.out.println("Please configure a environment variable named 'GOOGLE_APPLICATION_CREDENTIALS' with the path to the JSON key for the Google Cloud Service Account.");
            System.exit(-1);
        }
        List<SpeechResult> audioTranscription = new LinkedList<>();
        for (int i = 0; i < numberOfFiles; i++) {
            String fileName = folder+"/"+folder+i+"."+fileFormat;
            try {
                audioTranscription.add(speechToText.buildSpeechResult(fileName));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            }
        }
        for (SpeechResult r :
                audioTranscription) {
            System.out.println(r);
        }

    }
}
