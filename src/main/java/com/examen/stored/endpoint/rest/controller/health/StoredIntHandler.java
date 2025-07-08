package com.examen.stored.endpoint.rest.controller.health;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

@RestController
public class StoredIntHandler {

    private static final String FILE_NAME = "stored-int.txt";
    private static final String TEMP_DIR = System.getProperty("java.io.tmpdir");
    private static final Path FILE_PATH = Paths.get(TEMP_DIR, FILE_NAME);

    @GetMapping("/stored-int")
    public ResponseEntity<Integer> getStoredInt() {
        try {
            // a) Lire le nombre stocké dans le fichier s'il existe
            if (Files.exists(FILE_PATH)) {
                String content = Files.readString(FILE_PATH);
                int storedNumber = Integer.parseInt(content.trim());
                return ResponseEntity.ok(storedNumber);
            } else {
                // b) Créer le fichier avec un nombre aléatoire
                Random random = new Random();
                int randomNumber = random.nextInt(1000000); // Nombre aléatoire entre 0 et 999999

                Files.writeString(FILE_PATH, String.valueOf(randomNumber));
                return ResponseEntity.ok(randomNumber);
            }
        } catch (IOException e) {
            // En cas d'erreur, retourner un nouveau nombre aléatoire
            Random random = new Random();
            int randomNumber = random.nextInt(1000000);
            return ResponseEntity.ok(randomNumber);
        } catch (NumberFormatException e) {
            // Si le fichier contient un format invalide, générer un nouveau nombre
            Random random = new Random();
            int randomNumber = random.nextInt(1000000);
            try {
                Files.writeString(FILE_PATH, String.valueOf(randomNumber));
            } catch (IOException ioException) {
                // Ignorer l'erreur d'écriture
            }
            return ResponseEntity.ok(randomNumber);
        }
    }
}
