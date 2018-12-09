# SongPlayer-Android
A simple mp3 playing app

This program pulls HTML from a hardcoded web URL which contains mp3 files and corresponding jpg files for each song. It parses the name and URL for each song from the HTML, stores it in a singleton list class, and populates a scrollable list of the songs. The user can click on any song in the list, which will pull up a fragment displaying the associated image for the song and start playing the song. Hitting the back button returns to the list while the song will continue to play until finished or the user chooses another song from the list.
