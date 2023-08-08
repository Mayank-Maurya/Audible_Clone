package com.customer.controller;

import java.io.IOException;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customer.dto.AudioBookDTO;
import com.customer.dto.FilterLengthDTO;
import com.customer.dto.FiltersDTO;
import com.customer.services.AudioBookService;
import com.customer.utils.FileUtils;

@RestController
@RequestMapping(value = "/audiobook")
@CrossOrigin
public class AudioBookController {

	@Autowired
	AudioBookService audioBookService;
	
	@GetMapping(value="/")
	public ResponseEntity<List<AudioBookDTO>> getAllAudioBooks() {
		return new ResponseEntity<>(audioBookService.getAll(), HttpStatus.OK);
	}

	@GetMapping(value = "/list/size")
	public ResponseEntity<Long> getAudioBookListSize() {
		return new ResponseEntity<>(audioBookService.getABListSize(), HttpStatus.OK);
	}

	@GetMapping(value = "/list/{pageSize}/{pageNum}")
	public ResponseEntity<List<AudioBookDTO>> getAudioBookList(@PathVariable("pageSize") Integer pageSize,
			@PathVariable("pageNum") Integer pageNum) {
		return new ResponseEntity<>(audioBookService.getABList(pageSize, pageNum), HttpStatus.OK);
	}

	@GetMapping(value = "/list/search/{keyWord}/{pageSize}/{pageNum}/{sortBy}/{isAsc}")
	public ResponseEntity<List<AudioBookDTO>> searchList(@PathVariable("keyWord") String keyWord,
			@PathVariable("pageSize") Integer pageSize, @PathVariable("pageNum") Integer pageNum,
			@PathVariable("sortBy") String sortBy, @PathVariable("isAsc") Boolean isAsc) {
		return new ResponseEntity<>(audioBookService.getSearchList(keyWord, pageSize, pageNum, sortBy, isAsc),
				HttpStatus.OK);
	}

	@PostMapping(value = "/list/search/{keyWord}/{pageSize}/{pageNum}/{sortBy}/{isAsc}")
	public ResponseEntity<List<AudioBookDTO>> searchListwithFilers(@PathVariable("keyWord") String keyWord,
			@PathVariable("pageSize") Integer pageSize, @PathVariable("pageNum") Integer pageNum,
			@PathVariable("sortBy") String sortBy, @PathVariable("isAsc") Boolean isAsc,
			@RequestBody FiltersDTO filterDTO) {
		return new ResponseEntity<>(
				audioBookService.getSearchList(keyWord, pageSize, pageNum, sortBy, isAsc, filterDTO), HttpStatus.OK);
	}

	@GetMapping(value = "/list/search/size/{keyWord}")
	public ResponseEntity<Integer> searchList(@PathVariable("keyWord") String keyWord) {
		return new ResponseEntity<>(audioBookService.getSearchListSize(keyWord), HttpStatus.OK);
	}

	@PostMapping(value = "/post/save")
	public ResponseEntity<String> saveAudioBook(@RequestBody AudioBookDTO audioBookDTO) {
		Integer id = audioBookService.saveAudioBook(audioBookDTO);
		return new ResponseEntity<>("AudioBook Created with id : " + id, HttpStatus.CREATED);
	}

	@GetMapping(value = "/audio/{name}")
	public ResponseEntity<?> getSampleAudio(@PathVariable("name") String name) {

		FileUtils fileUtils = new FileUtils();

		Resource resource;

		try {
			resource = fileUtils.getFile(name);
		} catch (IOException e) {
			return ResponseEntity.internalServerError().build();
		}

		if (resource == null) {
			return new ResponseEntity<>("file not found", HttpStatus.NOT_FOUND);
		}

		String contentType = "application/octet-stream";
		String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, headerValue).body(resource);

	}

	@GetMapping(value = "/image/{name}")
	public ResponseEntity<?> getImage(@PathVariable("name") String name) {

		FileUtils fileUtils = new FileUtils();

		Resource resource;

		try {

			resource = fileUtils.getImage(name);

		} catch (IOException e) {

			return ResponseEntity.internalServerError().build();

		}

		if (resource == null) {

			return new ResponseEntity<>("file not found", HttpStatus.NOT_FOUND);

		}

		String contentType = "application/octet-stream";

		String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

		return ResponseEntity.ok()

				.contentType(MediaType.parseMediaType(contentType))

				.header(HttpHeaders.CONTENT_DISPOSITION, headerValue)

				.body(resource);

	}

	@GetMapping(value = "/filters/genre/list")
	public ResponseEntity<List<String>> getFilterGenreList() {
		return new ResponseEntity<>(audioBookService.getFiltreGenreList(), HttpStatus.OK);
	}

	@GetMapping(value = "/filters/length/list")
	public ResponseEntity<List<FilterLengthDTO>> getFilterLengthList() {
		return new ResponseEntity<>(audioBookService.getFiltreLengthList(), HttpStatus.OK);
	}

	@GetMapping(value = "/filters/releases/list")
	public ResponseEntity<List<Integer>> getFilterReleasesList() {
		return new ResponseEntity<>(audioBookService.getFilterReleasesList(), HttpStatus.OK);
	}

}
