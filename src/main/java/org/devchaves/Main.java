package org.devchaves;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Path downloadsDir = Paths.get("/home/jao/Projetos/YT-Downloader/downloads");

        if (!Files.exists(downloadsDir)) {
            try {
                Files.createDirectories(downloadsDir);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Copie a URL aqui: ");
        Scanner sc1 = new Scanner(System.in);
        String url = sc1.nextLine();

        System.out.println("Escolha um formato:");
        Scanner sc2 = new Scanner(System.in);
        String format = sc2.nextLine();

        ProcessBuilder mp3 = new ProcessBuilder(
                "/usr/bin/yt-dlp",
                "-P", downloadsDir.toString(),
                "-x",
                "--audio-format", "mp3",
                url
        );

        ProcessBuilder mp4 = new ProcessBuilder(
                "/usr/bin/yt-dlp",
                "-P", downloadsDir.toString(), // Diretório de destino
                "-o", "%(title)s.%(ext)s",   // Nome do arquivo de saída (opcional, mantém o título do vídeo)
                "-f", "bestvideo[ext=mp4]+bestaudio[ext=m4a]/best[ext=mp4]", // Seleciona o melhor vídeo MP4 e áudio
                url
        );


        mp3.redirectErrorStream(true);

        if(format.equals("mp3")){
            try {
                Process process = mp3.start();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }

                int exitCode = process.waitFor();
                System.out.println("Download finalizado! Arquivo salvo em: " + downloadsDir);
                System.out.println("Processo finalizado! Código de saída: " + exitCode);

            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        mp4.redirectErrorStream(true);

        if (format.equals("mp4")){
            try {
                Process process = mp4.start();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }

                int exitCode = process.waitFor();
                System.out.println("Download finalizado! Arquivo salvo em: " + downloadsDir);
                System.out.println("Processo finalizado! Código de saída: " + exitCode);
            }
            catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
