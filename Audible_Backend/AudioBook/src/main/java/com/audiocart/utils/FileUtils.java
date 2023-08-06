package com.audiocart.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;



public class FileUtils {

	private Path foundFile;
	private Resource resource;

	private Path foundFile1;
	private Resource resource1;

	public Resource getFile(String fileCode) throws IOException {
		foundFile = null;
		Stream<Path> s1 = null;

		try {
			Path uploadDirectory = Paths.get("AudioBook/src/main/resources/assets/audio");
			s1 = Files.list(uploadDirectory);
			s1.forEach(file -> {
				try {
					String[] split1 = file.getFileName().toString().split("\\.");
					if (split1[0].equals(fileCode)) {
						foundFile = file;
						try {
							this.resource = new UrlResource(foundFile.toUri());
						} catch (MalformedURLException ignored) {
						}
						throw new IOException();
					}
				} catch (IOException ignored) {
				}
			});
			s1.close();
		} catch (IOException ignored) {
		}
		return resource;
	}

	public Resource getImage(String fileCode) throws IOException {
		foundFile1 = null;
		try {
			Path uploadDirectory = Paths.get("AudioBook/src/main/resources/assets/images");
			Stream<Path> s2 = Files.list(uploadDirectory);
			s2.forEach(file -> {
				try {
					String[] split2 = file.getFileName().toString().split("\\.");
					if (split2[0].equals(fileCode)) {
						foundFile1 = file;
						try {
							this.resource1 = new UrlResource(foundFile1.toUri());
						} catch (MalformedURLException ignored) {
						}
						throw new IOException();
					}
				} catch (IOException ignored) {
				}
			});
			s2.close();
		}catch (Exception ignored){
		}
		return resource1;

	}

}
