package es.neifi.myfinance.shared.Infrastructure.configurations;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FireStoreConfiguration {

    public FireStoreConfiguration() throws IOException {

        // Use the application default credentials

        InputStream serviceAccount = new FileInputStream("src/main/resources/fire-admin-sdk.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .build();
        FirebaseApp.initializeApp(options);


    }


}
