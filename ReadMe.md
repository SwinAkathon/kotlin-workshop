Kotlin Workshop
==================

# Kotlin Frontend Development with Jetpack Compose

## References
1. [Jetpack Compose for Android Developers](https://developer.android.com/courses/jetpack-compose/course)

## Basics
+ [Compose essentials](https://developer.android.com/courses/pathways/jetpack-compose-for-android-developers-1): Topics 1-7 (Topic 7: exclude ViewModel)
  + (statement management basic) automatically update CalculatorScreen's dimension and layout on device rotation

+ [Layouts, theming and animation](https://developer.android.com/courses/pathways/jetpack-compose-for-android-developers-2): Topics 1-5

- [Compose Layouts/Fundamentals of Compose Layouts and Modifiers](https://developer.android.com/courses/pathways/jetpack-compose-for-android-developers-2)

### Examples
1. [Calculator Basics](https://github.com/SwinAkathon/kotlin-workshop/tree/basic)
2. BasicsCodelab
3. BasicLayoutsCodelab
4. BasicStateCodelab
5. (Optional) MigrationCodelab
5. ThemingCodeLab
6. (Optional) AnimationCodeLab

## Intermediate
+ (state management in-sync with configuration update) manages calculator state using ViewModel to retain state on rotation and app switch
+ [Architecture and State](https://developer.android.com/courses/pathways/jetpack-compose-for-android-developers-3):
  - Topic 2: Architecting your Compose UI
  - Topic 8: State holders and state production
- [State in Jetpack Compose/State in ViewModel](https://developer.android.com/codelabs/jetpack-compose-state)

### Examples
1. (+) [Calculator (Intermediate)](calculator)
2. [Jetchat](https://github.com/android/compose-samples/tree/main/Jetchat)
  
## Advanced
+ [Architecture and state](https://developer.android.com/courses/pathways/jetpack-compose-for-android-developers-3): Topics 1,3-7
  
### Examples
1. (Topic 5) AdvancedStateAndSideEffectsCodelab
2. (+) (Topic 7) NavigationCodelab
3. Calculator (Advanced)

# Kotlin Frontend with Large Data Stream Processing

## Prerequisites
1. [Advanced coroutines with Kotlin Flow and LiveData](https://developer.android.com/codelabs/advanced-kotlin-coroutines#0)
   2. Example: advanced-coroutines-codelab

## References
1. [Jetpack Paging library](https://developer.android.com/topic/libraries/architecture/paging/v3-overview)
   1. [Android Paging Basics codelab](https://developer.android.com/codelabs/android-paging-basics#0)
   2. [Android Paging Advanced codelab](https://developer.android.com/codelabs/android-paging#0)

## Handles a large object stream and displays them on a Composable with pagination
Example: CustomerStreamApp