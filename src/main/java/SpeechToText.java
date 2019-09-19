import com.google.cloud.speech.v1.*;
import com.google.protobuf.ByteString;
import lombok.extern.slf4j.Slf4j;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
public class SpeechToText {
    RecognitionConfig config;

    private static final String BASE_PATH = "src/main/resources/";

    public SpeechToText() throws IOException {
        config = RecognitionConfig.newBuilder()
                .setLanguageCode("en-US")
                .build();
    }

    public SpeechResult buildSpeechResult(String fileName) throws IOException, UnsupportedAudioFileException {
        try (SpeechClient speechClient = SpeechClient.create()) {
            System.out.println("Transcripting file.");
            String filePath = BASE_PATH + fileName;
            Path path = Paths.get(filePath);
            byte[] data = Files.readAllBytes(path);
            ByteString audioBytes = ByteString.copyFrom(data);
            System.out.println(path.toString());
            RecognitionAudio audio = RecognitionAudio.newBuilder()
                    .setContent(audioBytes)
                    .build();
            RecognizeResponse response = speechClient.recognize(config, audio);
            System.out.println("Transcription done.");
            return SpeechResult.builder()
                    .fileName(filePath)
                    .AudioText(buildTranscription(response.getResultsList()))
                    .audioDuration(getAudioDuration(filePath))
                    .build();
        }
    }

    private double getAudioDuration(String filePath) throws IOException, UnsupportedAudioFileException {
        File file = new File(filePath);
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
        AudioFormat format = audioInputStream.getFormat();
        long audioFileLength = file.length();
        long frameSize = format.getFrameSize();
        float frameRate = format.getFrameRate();
        return (audioFileLength / (frameSize * frameRate));
    }

    private String buildTranscription(List<SpeechRecognitionResult> response) {
        StringBuilder resultBuilder = new StringBuilder();
        for (SpeechRecognitionResult recognitionResult : response) {
            resultBuilder.append(recognitionResult.getAlternatives(0).getTranscript()).append(" ");
        }
        return resultBuilder.toString().trim();
    }
}