# BGMI Server - Expo app (expo-migration branch)

This folder contains an Expo-managed React Native app that coexists with the original Flutter implementation.

How to run
1. From the repository root:

   cd mobile/expo-app
   npm install
   npx expo start

2. To open on Android or iOS (Expo Go):
   - Press 'a' in the Metro terminal to open Android
   - Press 'i' to open iOS simulator

Notes
- This is a JS/TS Expo app. If you need to use native (unimodule) packages not supported by Expo Go, run `expo prebuild` to generate native projects.
- I copied the existing React Native TSX app into this folder and added the common navigation + gesture / reanimated setup.
- Keep the original Flutter files under mobile/ (they are preserved). A snapshot of the Flutter pubspec is stored in mobile/flutter-backup/.
