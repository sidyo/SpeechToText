# SpeechToText

This application transcribes a series of audio .wav files and transcribes them with a manual emotion insert.

Create a environment variable named GOOGLE_APPLICATION_CREDENTIALS with the json key created following this tutorial: https://cloud.google.com/speech-to-text/docs/quickstart-client-libraries

To use this application you create a folder inside src/main/resources and place the audio files with the same name as the folde adding a sequential number.
E.g: Folder name: Shrek
     Audio files names: Shrek0.wav , Shrek1.wav , Shrek2.wav...
Then as arguments for the application you provide the folder name and the number of audio files inside the folder.

Example run command: gradle run --console=plain -q --args='Shrek 10'

Tone Analyzer Dependency:
API_KEY
API_VERSION
API_SERVICE_URL
https://www.ibm.com/br-pt/cloud/watson-tone-analyzer
