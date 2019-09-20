# SpeechToText

This application transcribes a series of audio .wav files and transcribes them with a manual emotion insert.

To use this application you create a folder inside src/main/resources and place the audio files with the same name as the folde adding a sequential number.
E.g: Folder name: Shrek
     Audio files names: Shrek0.wav , Shrek1.wav , Shrek2.wav...
Then as arguments for the application you provide the folder name and the number of audio files inside the folder.

Example run command: gradle run --console=plain -q --args='Shrek 10'
