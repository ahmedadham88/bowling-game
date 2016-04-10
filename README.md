# BOWLING APPLICATION

This Application is simulating a bowling application. It can be ran from the command line and it will automatically run the game and return for the you your score and the number of strikes you did within this game. The other way of showing this application is also throw a simple GUI that you interact with it by rolling/getting the final score.

### Procedure

  - The Application has a Twitter integration as well. When the game ends, it tries to post to your twitter account automatically if you set it up correctly and you can do that as follows:
    - Setup a developer twitter account 
```sh
https://apps.twitter.com/
```
    - Create a new Application
    - Allow yourself the Read/Write Access
    - Copy your ConsumerKey, ConsumerSecret, AccessToken and AccessTokenSecret codes to your config.properties

  - For running the application, using the command line, and when you are within the same folder as this README file and the pom.xml file:
    - **Either** import the project as a maven project, clean install it and run:
      - AppStart for console interaction
      - MainSwingGUI for a user interface interaction
    - **OR** using the command line do as follows:
```sh
mvn clean install
```
```sh
java -jar target/BowlingGame-<version>.jar
```

**NOTE:** The application will throw an exception if you do not setup your twitter developer account and add the keys to the config.properties

Hope you enjoy it!
