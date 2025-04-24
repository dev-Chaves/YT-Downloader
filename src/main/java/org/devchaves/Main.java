package org.devchaves;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        //yt-dlp -x --audio-format mp3 <URL>
        System.out.println("Copie a URL aqui: ");

        Scanner sc1 = new Scanner(System.in);

        String url = sc1.nextLine();

        ProcessBuilder pb = new ProcessBuilder();

        pb.command("yt-dlp", "-f", "--audio-format", "mp3", url);

        try {
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;

            while ((line = reader.readLine()) != null){
                System.out.println(line);
            }

            int exitCode = process.waitFor();
            System.out.println("Processo finalizado!" + exitCode);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
