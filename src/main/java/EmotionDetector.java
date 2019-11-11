import com.ibm.cloud.sdk.core.http.HttpConfigOptions;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.tone_analyzer.v3.ToneAnalyzer;
import com.ibm.watson.tone_analyzer.v3.model.ToneAnalysis;
import com.ibm.watson.tone_analyzer.v3.model.ToneOptions;
import com.ibm.watson.tone_analyzer.v3.model.ToneScore;
import lombok.ToString;
import lombok.Value;

import java.util.List;

@ToString
public class EmotionDetector {

    private ToneAnalyzer toneAnalyzer;

    public EmotionDetector() {
        String apiKey = System.getenv("API_KEY");
        String version = System.getenv("API_VERSION");
        String serviceUrl = System.getenv("API_SERVICE_URL");
        IamAuthenticator authenticator = new IamAuthenticator(apiKey);
        toneAnalyzer = new ToneAnalyzer(version, authenticator);
        toneAnalyzer.setServiceUrl(serviceUrl);
        HttpConfigOptions configOptions = new HttpConfigOptions.Builder()
                .disableSslVerification(true)
                .build();
        toneAnalyzer.configureClient(configOptions);
    }

    public String findEmotion(String text) {
        System.out.println("Detecting emotion.");
        ToneOptions toneOptions = new ToneOptions.Builder()
                .text(text)
                .build();

        ToneAnalysis toneAnalysis = toneAnalyzer.tone(toneOptions).execute().getResult();

        String emotion = recognizeResponse(toneAnalysis.getDocumentTone().getTones());
        System.out.println(emotion);
        return emotion;
    }

    private String recognizeResponse(List<ToneScore> tones) {
        boolean hasSadness = false;
        for (int i = 0; i < tones.size(); i++) {
            ToneScore tone = tones.get(i);
            if ("confident".equals(tone.getToneId())) {
                System.out.println("Removed confident.");
                tones.remove(tone);
            }
        }
        if (tones.isEmpty()) {
            System.out.println("No emotion recognized. Default Neutral.");
            return "neutral";
        }

        ToneScore max = tones.get(0);
        for (ToneScore tone : tones) {
            if (tone.getScore() > max.getScore() && tone.getToneId().equals("an")) {
                max = tone;
            }
            if (tone.getToneId().equals("sadness")) {
                hasSadness = true;
            }
        }
        switch (max.getToneId()) {
            case "joy":
                return "happiness";
            case "anger":
                return "anger";
            case "sadness":
                return "sadness";
            case "tentative":
                return "surprise";
            case "fear":
                if (hasSadness) {
                    return "disgust";
                } else return "neutral";
            default:
                return "neutral";
        }

    }


}
