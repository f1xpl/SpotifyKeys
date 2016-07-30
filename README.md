# SpotifyKeys
Now you can control your Spotify application by steering wheel keys and device's hard keys. Just simply bind key to next/previous/toggle play/launch action.

### IMPORTANT NOTE: Before you start using the application, please check if "Device Broadcast Status" is enabled in spotify settings. Without enabling this option you will not be able to use SpotifyKeys. More info can be found here https://developer.spotify.com/technologies/spotify-android-sdk/android-media-notifications/#disqus_thread

### Features:
## - Possible to bind multiple keys (device's hard keys and steering wheel keys) for next/previous/toggle play action
## - Bind steering wheel keys or device's hard keys for switching to the next track (only when Spotify's playback is active)
## - Bind steering wheel keys or device's hard keys for switching to the previous track (only when Spotify's playback is active)
## - Bind steering wheel keys or device's hard keys for toggle play
## - Bind steering wheel keys or device's hard keys to launch Spotify

Application has been originally developed for MTCD GS 1.61 (Android 5.1.1) device but by using microntek "virtual keys" broadcast layer it should work also for other resellers (e.g. Joying, KGL).

Please note that any key that you want to bind in SpotifyKeys should be bound first in original KeyStudy app from Factory Settings.

**It is standalone application. You DO NOT NEED TO ROOT your device in order to make SpotifyKeys work. Any Xposed framework or other 3rd party stuff is not needed as well.**

SpotifyKeys has autorun feature - it means that Android should launch it in background automatically after bootup. Just install it, "learn" key codes and forget :).

## **In order to remove learnt key use a Long Press.**

For more info, troubleshooting and feedback please see http://forum.xda-developers.com/android-auto/android-head-units/mtcd-spotifykeys-control-spotify-using-t3428930.
