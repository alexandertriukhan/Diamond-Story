# Diamond-Story
Match 3 game for mobile (Android / iOS), written in Kotlin on libGDX.

## Requirements
- JDK 17+ (21 recommended), e.g. `brew install openjdk@21` and `export JAVA_HOME=/opt/homebrew/opt/openjdk@21`
- Game assets in `android/assets/` (kept in a separate repository, not in this one)
- Android: Android SDK (`sdk.dir` in `local.properties` or `ANDROID_HOME`). Without it the `android` module is skipped.
- iOS: macOS with Xcode

## Running
#### Desktop (development)
`./gradlew desktop:run`
#### Android
`./gradlew android:installDebug android:run`
#### iOS
`./gradlew ios:launchIPhoneSimulator`, or `ios:launchIOSDevice` / `ios:createIPA` (needs signing setup)

## Credits
- UI skin: [Glassy UI](https://github.com/czyzby/gdx-skins/tree/master/glassy) by Raymond "Raeleus" Buckley, [CC BY 4.0](http://creativecommons.org/licenses/by/4.0/)
- Font: Jolly Lodger by Font Diner, SIL Open Font License
