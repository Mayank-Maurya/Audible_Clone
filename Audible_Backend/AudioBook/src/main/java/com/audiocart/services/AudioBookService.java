package com.audiocart.services;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.audiocart.dto.AudioBookDTO;
import com.audiocart.dto.FilterLengthDTO;
import com.audiocart.dto.FiltersDTO;
import com.audiocart.dto.LengthDataDTO;
import com.audiocart.dto.ReleasesDataDTO;
import com.audiocart.entity.AudioBook;
import com.audiocart.repository.AudioBookRepository;

@Service("audioBookService")
public class AudioBookService {

	@Autowired
	AudioBookRepository audioBookRepository;
	
	public Long getABListSize() {
		return audioBookRepository.count();
	}
	
	public List<AudioBookDTO> getAll(){
		List<AudioBookDTO> ans = audioBookRepository.findAll().stream().map(AudioBook::toDTO).toList();
		return ans;
	}
	
	public List<AudioBookDTO> getABList(Integer pageSize , Integer pageNum){
		Pageable pageable = PageRequest.of(pageNum-1, pageSize);
		return audioBookRepository.findAll(pageable).stream().map( AudioBook::toDTO).collect(Collectors.toList());
	}

	public Integer saveAudioBook(AudioBookDTO audioBook) {
		return audioBookRepository.save(audioBook.toEntity()).getAudioBookId();
	}
	
	public List<AudioBookDTO> getSearchList(String keyword,Integer pageSize , Integer pageNum,String sortyBy,boolean isAsc){
		
		Pageable pageable;
		if(isAsc)
		{
			pageable = PageRequest.of(pageNum-1, pageSize,Sort.by(sortyBy).ascending());
		}else {
			pageable = PageRequest.of(pageNum-1, pageSize,Sort.by(sortyBy).descending());
		}
		
		if(keyword.equals("All")) {
			return audioBookRepository
					.getAllAudioBooks(pageable)
					.stream()
					.map(AudioBook::toDTO)
					.collect(Collectors.toList());
		}
		
		return audioBookRepository
				.getBySearch(keyword,pageable)
				.stream()
				.map(AudioBook::toDTO)
				.collect(Collectors.toList());
		
	}
	
	public List<AudioBookDTO> getSearchList(String keyword,Integer pageSize , Integer pageNum,String sortyBy,boolean isAsc,FiltersDTO filtersDTO){
		
		List<AudioBookDTO> result;
		List<String> genreList;
		Integer startMinLength=999999999;
		Integer endMaxLength=-1;
		Integer maxRelease=-1;
		Float rating;
		LocalDate release;
		Pageable pageable;
		
		genreList = filtersDTO.getGenreList().stream().map(data-> new String(data.getData())).toList();
		
		for(LengthDataDTO data:filtersDTO.getLengthList())
		{
			startMinLength = Integer.min(startMinLength, data.getData().getStart());
		}
		
		for(LengthDataDTO data:filtersDTO.getLengthList())
		{
			endMaxLength = Integer.max(endMaxLength, data.getData().getEnd());
		}
		
		for(ReleasesDataDTO data:filtersDTO.getReleasesList())
		{
			maxRelease = Integer.max(maxRelease, data.getData());
		}
		
		rating = filtersDTO.getRating();
		release = LocalDate.now().minusDays(maxRelease);
		
		if(isAsc)
		{
			pageable = PageRequest.of(pageNum-1, pageSize,Sort.by(sortyBy).ascending());
		}else {
			pageable = PageRequest.of(pageNum-1, pageSize,Sort.by(sortyBy).descending());
		}
		
		Integer isGenreEmpty = genreList.size()==0?1:0;
		Integer isReleaseEmpty = maxRelease==-1?1:0;
		Integer isRatingEmpty = rating==-1?1:0;
		if(genreList.size()>0 || (startMinLength!=999999999 
				&& endMaxLength!=-1) || maxRelease!=-1 || rating!=0F) {
			if(keyword.equals("All")) {
				
				result = audioBookRepository
							.getBySearchAndFilter(genreList,isGenreEmpty,startMinLength,endMaxLength,release,isReleaseEmpty,rating,isRatingEmpty,pageable)
							.stream()
							.map(AudioBook::toDTO)
							.collect(Collectors.toList());
			}else {
			
				result = audioBookRepository
							.getBySearchAndFilter(keyword,genreList,isGenreEmpty,startMinLength,endMaxLength,release,isReleaseEmpty,rating,isRatingEmpty,pageable)
							.stream()
							.map(AudioBook::toDTO)
							.collect(Collectors.toList());
			}
		}else {
			if(keyword.equals("All")) {
				result = audioBookRepository
							.getAllAudioBooks(pageable)
							.stream()
							.map(AudioBook::toDTO)
							.collect(Collectors.toList());
			}else {
			
				result = audioBookRepository
							.getBySearch(keyword,pageable)
							.stream()
							.map(AudioBook::toDTO)
							.collect(Collectors.toList());
			}
		}
		
		return result;
		
		}

	public Integer getSearchListSize(String keyWord) {
		
		if(keyWord.equals("All")) {
			return audioBookRepository
					.getAllAudioBooks()
					.size();
		}
		return audioBookRepository
				.getBySearch(keyWord)
				.size();
	}

	public List<String> getFiltreGenreList() {
		List<String> genrelist = new ArrayList<>();
		genrelist.add("Comedy");
		genrelist.add("Romance");
		genrelist.add("Motivational");
		genrelist.add("Life Lessons");
		genrelist.add("Story");
		return genrelist;
	}

	public List<FilterLengthDTO> getFiltreLengthList() {
		List<FilterLengthDTO> genrelist = new ArrayList<>();
		genrelist.add(new FilterLengthDTO(60,180));
		genrelist.add(new FilterLengthDTO(180,360));
		genrelist.add(new FilterLengthDTO(360,540));
		genrelist.add(new FilterLengthDTO(540,720));
		return genrelist;
	}

	public List<Integer> getFilterReleasesList() {
		List<Integer> releasesList = new ArrayList<>();
		releasesList.add(30);
		releasesList.add(60);
		releasesList.add(90);
		return releasesList;
	}
	
	
	
	
	
}
