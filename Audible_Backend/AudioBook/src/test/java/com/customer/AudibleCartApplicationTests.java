package com.customer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.customer.dto.AudioBookDTO;
import com.customer.dto.FilterLengthDTO;
import com.customer.dto.FiltersDTO;
import com.customer.dto.GenreDataDTO;
import com.customer.dto.LengthDataDTO;
import com.customer.dto.ReleasesDataDTO;
import com.customer.entity.AudioBook;
import com.customer.repository.AudioBookRepository;
import com.customer.services.AudioBookService;

@SpringBootTest
class AudibleCartApplicationTests {

	@Mock
    private AudioBookRepository audioBookRepository;
 

    @InjectMocks
    private AudioBookService audioBookService = new AudioBookService();
    
    
    @Test
    public void getABListSizeValid() {
    	Mockito.when(audioBookRepository.count()).thenReturn(10L);
    	Assertions.assertEquals(10L,audioBookService.getABListSize());
    }
    
    @Test
    public void getABListSizeInvalid() {
    	Mockito.when(audioBookRepository.count()).thenReturn(10L);
    	Assertions.assertNotEquals(11L,audioBookService.getABListSize());
    }
    
    
    @Test
    public void getAllValid() {
    	List<AudioBook> audioBookList = new ArrayList();
    	audioBookList.add(new AudioBook(1,null,null,null,null,null,null,null,null,null,null,null,null,null));
    	Mockito.when(audioBookRepository.findAll()).thenReturn(audioBookList);
    	List<AudioBookDTO> audioBookDTOList = new ArrayList<>();
    	audioBookDTOList.add(new AudioBookDTO(1,null,null,null,null,null,null,null,null,null,null,null,null,null));
    	Assertions.assertEquals(audioBookDTOList,audioBookService.getAll());
    }
    
    @Test
    public void getAllInvalid() {
    	List<AudioBook> audioBookList = new ArrayList<>();
    	audioBookList.add(new AudioBook(1,null,null,null,null,null,null,null,null,null,null,null,null,null));
    	Mockito.when(audioBookRepository.findAll()).thenReturn(audioBookList);
    	List<AudioBookDTO> audioBookDTOList = new ArrayList<>();
    	audioBookDTOList.add(new AudioBookDTO(2,null,null,null,null,null,null,null,null,null,null,null,null,null));
    	Assertions.assertNotEquals(audioBookDTOList,audioBookService.getAll());
    }
    
    @Test
    public void getABListValid() {
    	List<AudioBook> audioBookList = new ArrayList<>();
    	audioBookList.add(new AudioBook(1,null,null,null,null,null,null,null,null,null,null,null,null,null));
    	Pageable pageable = PageRequest.of(0, 1);
    	Page<AudioBook> books = new PageImpl<>(audioBookList);
    	Mockito.when(audioBookRepository.findAll(pageable)).thenReturn(books);
    	List<AudioBookDTO> audioBookDTOList = new ArrayList<>();
    	audioBookDTOList.add(new AudioBookDTO(1,null,null,null,null,null,null,null,null,null,null,null,null,null));
    	Assertions.assertEquals(audioBookDTOList,audioBookService.getABList(1,1));
    }
    
    @Test
    public void getABListInvalid() {
    	List<AudioBook> audioBookList = new ArrayList<>();
    	audioBookList.add(new AudioBook(1,null,null,null,null,null,null,null,null,null,null,null,null,null));
    	Pageable pageable = PageRequest.of(0, 1);
    	Page<AudioBook> books = new PageImpl<>(audioBookList);
    	Mockito.when(audioBookRepository.findAll(pageable)).thenReturn(books);
    	List<AudioBookDTO> audioBookDTOList = new ArrayList<>();
    	audioBookDTOList.add(new AudioBookDTO(2,null,null,null,null,null,null,null,null,null,null,null,null,null));
    	Assertions.assertNotEquals(audioBookDTOList,audioBookService.getABList(1,1));
    }
    
    @Test
    public void getSearchListValid() {
    	FiltersDTO filtersDTO = new FiltersDTO();
    	filtersDTO.setReleasesList(new ArrayList<ReleasesDataDTO>());
    	filtersDTO.setGenreList(new ArrayList<GenreDataDTO>());
    	filtersDTO.setLengthList(new ArrayList<LengthDataDTO>());
    	filtersDTO.setRating(0F);
		String keyword="All";
		Integer pageSize=1;
		Integer pageNum=1;
		String sortyBy="author";
		boolean isAsc=true;
    	List<String> genreList = new ArrayList();
    	List<AudioBookDTO> result = new ArrayList<>();
		Integer startMinLength=999999999;
		Integer endMaxLength=-1;
		Integer maxRelease=-1;
		Float rating=0F;
		LocalDate release;
		Pageable pageable = PageRequest.of(0, 1,Sort.by(sortyBy).ascending());
		Integer isGenreEmpty = genreList.size()==0?1:0;
		Integer isReleaseEmpty = maxRelease==-1?1:0;
		Integer isRatingEmpty = rating==-1?1:0;
		
		List<AudioBook> audioBookList = new ArrayList<>();
    	audioBookList.add(new AudioBook(1,null,null,null,null,null,null,null,null,null,null,null,null,null));
    	
    	Mockito.when(audioBookRepository.getAllAudioBooks(pageable)).thenReturn(audioBookList);
    	
    	List<AudioBookDTO> audioBookDTOList = new ArrayList<>();
    	audioBookDTOList.add(new AudioBookDTO(1,null,null,null,null,null,null,null,null,null,null,null,null,null));
    	Assertions.assertEquals(audioBookDTOList,audioBookService.getSearchList(keyword,pageSize,pageNum,sortyBy,isAsc,filtersDTO) );
    }
    
    
    
    @Test
    public void getSearchListValidd() {
    	FiltersDTO filtersDTO = new FiltersDTO();
    	filtersDTO.setReleasesList(new ArrayList<ReleasesDataDTO>());
    	filtersDTO.setGenreList(new ArrayList<GenreDataDTO>());
    	filtersDTO.setLengthList(new ArrayList<LengthDataDTO>());
    	filtersDTO.setRating(0F);
		String keyword="aman";
		Integer pageSize=1;
		Integer pageNum=1;
		String sortyBy="author";
		boolean isAsc=true;
    	List<String> genreList = new ArrayList();
    	List<AudioBookDTO> result = new ArrayList<>();
		Integer startMinLength=999999999;
		Integer endMaxLength=-1;
		Integer maxRelease=-1;
		Float rating=0F;
		LocalDate release;
		Pageable pageable = PageRequest.of(0, 1,Sort.by(sortyBy).ascending());
		Integer isGenreEmpty = genreList.size()==0?1:0;
		Integer isReleaseEmpty = maxRelease==-1?1:0;
		Integer isRatingEmpty = rating==-1?1:0;
		
		List<AudioBook> audioBookList = new ArrayList<>();
    	audioBookList.add(new AudioBook(1,null,null,null,null,null,null,null,null,null,null,null,null,null));
    	
    	Mockito.when(audioBookRepository.getBySearch(keyword,pageable)).thenReturn(audioBookList);
    	
    	List<AudioBookDTO> audioBookDTOList = new ArrayList<>();
    	audioBookDTOList.add(new AudioBookDTO(1,null,null,null,null,null,null,null,null,null,null,null,null,null));
    	Assertions.assertEquals(audioBookDTOList,audioBookService.getSearchList(keyword,pageSize,pageNum,sortyBy,isAsc));
    }
    

@Test
    public void getSearchListValiddd() {
    	FiltersDTO filtersDTO = new FiltersDTO();
    	filtersDTO.setReleasesList(new ArrayList<ReleasesDataDTO>());
    	GenreDataDTO g = new GenreDataDTO();
    	g.setData("hii");
    	g.setIsSelected(true);
    	List<GenreDataDTO> l = new ArrayList<GenreDataDTO>();
    	l.add(g);
    	filtersDTO.setGenreList(l);
    	filtersDTO.setLengthList(new ArrayList<LengthDataDTO>());
    	filtersDTO.setRating(1F);
		String keyword="All";
		Integer pageSize=1;
		Integer pageNum=1;
		String sortyBy="author";
		boolean isAsc=true;
    	List<String> genreList = new ArrayList();
    	List<AudioBookDTO> result = new ArrayList<>();
		Integer startMinLength=999999999;
		Integer endMaxLength=-1;
		Integer maxRelease=-1;
		Float rating=0F;
		LocalDate release=LocalDate.now();
		Pageable pageable = PageRequest.of(0, 1,Sort.by(sortyBy).ascending());
		Integer isGenreEmpty = genreList.size()==0?1:0;
		Integer isReleaseEmpty = maxRelease==-1?1:0;
		Integer isRatingEmpty = rating==-1?1:0;
		
		List<AudioBook> audioBookList = new ArrayList<>();
    	audioBookList.add(new AudioBook(1,null,null,null,null,null,null,null,null,null,null,null,null,null));
    	
    	Mockito.when(audioBookRepository.getBySearchAndFilter(genreList,isGenreEmpty,startMinLength,endMaxLength,release,isReleaseEmpty,rating,isRatingEmpty,pageable)).thenReturn(audioBookList);
    	
    	List<AudioBookDTO> audioBookDTOList = new ArrayList<>();
    	audioBookDTOList.add(new AudioBookDTO(1,null,null,null,null,null,null,null,null,null,null,null,null,null));
    	Assertions.assertNotEquals(audioBookDTOList,audioBookService.getSearchList(keyword,pageSize,pageNum,sortyBy,isAsc,filtersDTO) );
    }
    
    @Test
    public void getSearchListSizeValid(){
    	List<AudioBook> audioBookList = new ArrayList<>();
    	audioBookList.add(new AudioBook(1,null,null,null,null,null,null,null,null,null,null,null,null,null));
    	
    	Mockito.when(audioBookRepository.getBySearch("aman")).thenReturn(audioBookList);
    	
    	List<AudioBookDTO> audioBookDTOList = new ArrayList<>();
    	audioBookDTOList.add(new AudioBookDTO(1,null,null,null,null,null,null,null,null,null,null,null,null,null));
    	Assertions.assertEquals(1,audioBookService.getSearchListSize("aman"));
    
    }
    
    @Test
    public void getSearchListSizeValidd(){
    	List<AudioBook> audioBookList = new ArrayList<>();
    	audioBookList.add(new AudioBook(1,null,null,null,null,null,null,null,null,null,null,null,null,null));
    	
    	Mockito.when(audioBookRepository.getAllAudioBooks()).thenReturn(audioBookList);
    	
    	List<AudioBookDTO> audioBookDTOList = new ArrayList<>();
    	audioBookDTOList.add(new AudioBookDTO(1,null,null,null,null,null,null,null,null,null,null,null,null,null));
    	Assertions.assertEquals(1,audioBookService.getSearchListSize("All"));
    
    }
    
    
    @Test
    public void getFiltreGenreList() {
    	
    	List<String> genrelist = new ArrayList<>();
		genrelist.add("Comedy");
		genrelist.add("Romance");
		genrelist.add("Motivational");
		genrelist.add("Life Lessons");
		genrelist.add("Story");
		
    	Assertions.assertEquals(genrelist,audioBookService.getFiltreGenreList());
    }
    
    @Test
    public void getFiltreLengthList() {
		List<FilterLengthDTO> genrelist = new ArrayList<>();
		genrelist.add(new FilterLengthDTO(60,180));
		genrelist.add(new FilterLengthDTO(180,360));
		genrelist.add(new FilterLengthDTO(360,540));
		genrelist.add(new FilterLengthDTO(540,720));
    	Assertions.assertEquals(genrelist,audioBookService.getFiltreLengthList());

	}

    @Test
	public void getFilterReleasesList() {
		List<Integer> releasesList = new ArrayList<>();
		releasesList.add(30);
		releasesList.add(60);
		releasesList.add(90);
    	Assertions.assertEquals(releasesList,audioBookService.getFilterReleasesList());

	}

}
