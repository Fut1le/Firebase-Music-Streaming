# Firebase-Music-Streaming

[github]:           https://github.com/Fut1le/Firebase-Music-Streaming
[platform-badge]:   https://img.shields.io/badge/Platform-Android-F3745F.svg
[minsdk-badge]:     https://img.shields.io/badge/minSdkVersion-16-F3745F.svg

[![platform-badge]][github]
[![minsdk-badge]][github]

<!------------------------------------------------------------------------->

Android application for streaming music in real time using Firebase.

## Features
- You can upload music to storage
- You can listen your music from storage
- You can easily modify the program code
- Availability of notifications
- Track covers

## Screenshots
<div style="dispaly:flex">
    <img src="https://github.com/Fut1le/Firebase-Music-Streaming/blob/main/images/1.jpg" width="32%">
    <img src="https://github.com/Fut1le/Firebase-Music-Streaming/blob/main/images/2.jpg" width="32%">
    <img src="https://github.com/Fut1le/Firebase-Music-Streaming/blob/main/images/3.jpg" width="32%">
</div>

## How to setup my app
1. Clone this repo
2. Open in Adroid Studio
3. Create new Firebase project
4. Rename the project package name
5. Add **google-services.json** in /app folder
6. In the Firebase console switch on Storage & Real-time Database
7. Edit the Firebase Storage rules
8. Have fun! 😏

# Firebase Storage Rules
```
service firebase.storage {
    match /b/YOUR_APP_ID.appspot.com/o {
        match /{allPaths=**} {
            allow read, write: if true;
        }
    }
}
```
# Firebase Realtime Database Rules
```
{
  "rules": {
    ".read": true,
    ".write": true
  }
}
```
