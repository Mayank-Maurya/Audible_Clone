package com.audiocart.dto;

import java.time.LocalDate;

import com.audiocart.entity.AudioBook;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AudioBookDTO {
	
	private Integer audioBookId;
    private String title;
    private String author;
    private String narrator;
    private Integer length;
    private String genre;
    private String description;
    private String coverImagePath;
    private Float avgRating;
    private LocalDate releaseDate;
    private String publisherName;
    private Float price;
    private String sampleAudioPath;
    private String mainAudioPath;
	public AudioBook toEntity() {
		return new AudioBook(this.audioBookId,this.title,this.author,this.narrator,this.length,this.genre,this.description,this.coverImagePath
				,this.avgRating,this.releaseDate,this.publisherName,this.price,this.sampleAudioPath,this.mainAudioPath);
	}
	
}
