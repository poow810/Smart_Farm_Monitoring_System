# Class Diagram

```mermaid
classDiagram



namespace Client_Side {          
    class Client {
	    ip
        id
        password
        requestServer()
    }
}

namespace Server_side {
    class MainServer {
        
        connectDatabase()
        requestData()
    }
    class AuthServer {

        authenticateUser()
        getUserPermissions()
        connectDatabase()
    }
    class AILearningServer {
        trainModel() 
        updateModel()
        connectDatabase()
    }
}

namespace Database {
    class MainDatabase {
        temperature
        time
        humidity
        illuminance
        plantImage
        plantVideo
        storeData()
        retrieveData()
    }
    class userDatabase{
        userId
        userPassword
        userRank
    }

}

namespace sensor {
    class ArduinoServer {
        getTemperature()
        getHumidity()
        getLightLevel()
    }
    class RaspberryPiServer {
        captureImage()
        recordVideo()
    }
}

    Client -- MainServer : requests >

    MainServer -- MainDatabase : connects >


    AuthServer -- userDatabase : checks user level >
    AuthServer -- MainServer : checks user permissions >


    AILearningServer -- MainDatabase : communicates >

    ArduinoServer -- MainServer : sends data >
    RaspberryPiServer -- MainServer : sends data > 
```