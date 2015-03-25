# Introduction #

Android stress indicator is a android application that measures the "loudness"
of your voice during calls.

The app uses the [funf framework](http://code.google.com/p/funf-open-sensing-framework/) .
It is tested on android 4.0.3

## Installation ##


## How it works? ##


  * It records your call using Android's Audio Recorder.

  * While recording it is calculating the [RMS (Root Mean Square)](http://en.wikipedia.org/wiki/Root_mean_square) value of a window of 256 samples. So, if we record a call of 30 seconds with a sample rate of 8000 Hz  then we will have 8000/256 = 32 RMS values.

  * Then we compare each of these values with the RMS value of silence and give a score to each value based on how much the current RMS value differs from the silence RMS value. The higher the score the more loud the call were.


## Where it stores the data ##

  * For each call it stores the date and time the call happened, the phone number and the score of the call.

  * These data are stored in a local database only accessible from your phone.

  * Also all the RMS values computed are send to a central server anonymously for statistical reasons. The above is completely safe, there is no way for someone to reconstruct your call.


## What kind of reports are available ##

## Known bugs ##