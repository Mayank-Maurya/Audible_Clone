package com.audiocart.entity;

import java.time.LocalDate;


import com.audiocart.dto.AudioBookDTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="audiobook")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AudioBook {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer audioBookId;
	String title;
	String author;
	String narrator;
	Integer length;
	String genre;
	String description;
	String coverImagePath;
	Float avgRating;
	LocalDate releaseDate;
	String publisherName;
	Float price;
	String sampleAudioPath;
	String mainAudioPath;
	
	public AudioBookDTO toDTO() {
		return new AudioBookDTO(this.audioBookId,this.title,this.author,this.narrator
				,this.length,this.genre,this.description,this.coverImagePath,this.avgRating
				,this.releaseDate,this.publisherName,this.price,this.sampleAudioPath,
				this.mainAudioPath);
	}
	
}
