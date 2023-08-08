package com.customer.repository;


import java.time.LocalDate;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.customer.entity.AudioBook;


public interface AudioBookRepository extends JpaRepository<AudioBook, Integer> {
	
	@Query("select a from AudioBook a where concat(a.title,' ',a.author,' ',a.genre,' ',a.description,' ',a.publisherName) like %?1%")
	public List<AudioBook> getBySearch( String search, Pageable pageable);
	
	@Query("select a from AudioBook a where concat(a.title,' ',a.author,' ',a.genre,' ',a.description,' ',a.publisherName) like %?1%")
	public List<AudioBook> getBySearch( String search);
	
	@Query("select a from AudioBook a")
	public List<AudioBook> getAllAudioBooks(Pageable pageable);
	
	@Query("select a from AudioBook a")
	public List<AudioBook> getAllAudioBooks();
	
	
	@Query("select a from AudioBook a " +
	"where (((a.genre IN (:genreList)) OR :isEmpty=1) AND (CONVERT(a.avgRating,DECIMAL(2,1))<= CONVERT(:rating,DECIMAL(2,1)) OR :isRatingEmpty=1 ) AND  (a.length >= :startMinLength OR :startMinLength=999999999 ) AND (a.length <= :endMaxLength OR :endMaxLength=-1)  AND (a.releaseDate >= :maxRelease OR :isReleaseEmpty=1))")
	public List<AudioBook> getBySearchAndFilter(
			@Param("genreList") List<String> genreList,
			@Param("isEmpty") Integer isEmpty,
			@Param("startMinLength") Integer startMinLength,
			@Param("endMaxLength") Integer endMaxLength,
			@Param("maxRelease") LocalDate maxRelease,
			@Param("isReleaseEmpty") Integer isReleaseEmpty,
			@Param("rating") Float rating,
			@Param("isRatingEmpty") Integer isRatingEmpty,
			Pageable pageable);
	
	@Query("select a from AudioBook a where "
			+"concat(a.title,' ',a.author,' ',a.genre,' ',a.description,' ',a.publisherName) like %:search% "
			+"AND (((a.genre IN (:genreList)) OR :isEmpty=1) AND (CONVERT(a.avgRating,DECIMAL(2,1))<= CONVERT(:rating,DECIMAL(2,1)) OR :isRatingEmpty=1 ) AND  (a.length >= :startMinLength OR :startMinLength=999999999 ) AND (a.length <= :endMaxLength OR :endMaxLength=-1)  AND (a.releaseDate >= :maxRelease OR :isReleaseEmpty=1))")
			public List<AudioBook> getBySearchAndFilter(
					@Param("search") String search,
					@Param("genreList") List<String> genreList,
					@Param("isEmpty") Integer isEmpty,
					@Param("startMinLength") Integer startMinLength,
					@Param("endMaxLength") Integer endMaxLength,
					@Param("maxRelease") LocalDate maxRelease,
					@Param("isReleaseEmpty") Integer isReleaseEmpty,
					@Param("rating") Float rating,
					@Param("isRatingEmpty") Integer isRatingEmpty,
					Pageable pageable);
	
//	@Query("select a from AudioBook a where a.length>= MIN(?1)")
//	public List<AudioBook> getBySearchAndFilter1(List<Integer> startLength);
	
	
	
	
	
	

}
