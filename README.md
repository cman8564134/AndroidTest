# AndroidTest
This project is developed in a Test Driven Development (TDD) development process following the MVVM (View-ViewModel-Model) architecture to simulate an Android application with Login/Signup/Home feature. Although MVVM is used, I did not used any data bindings as a one way information flow would be sufficient for the app. That being said, data bindings can easily be achieved (if there is a need in the future) using the existing architecture of the project.
This project also incorporated several best practice for material design such as :- login -> signup and Launch Screen/App Loading Screen as Splash Screen (Following best practices of Google: https://material.io/guidelines/patterns/launch-screens.html# ). 
This project also includes test codes for instrumental and unit testing. Code coverage is at 100% for all classes. 
This project uses the following technologies:-
1) Android/Java
2) Mockito
3) Espresso
4) JUnit4
5) Realm database

As Realm database do not YET (as of March 2018) support unit testing, the part of code that uses Realm are not YET unit testable.
