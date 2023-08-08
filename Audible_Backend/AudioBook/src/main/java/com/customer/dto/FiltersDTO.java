package com.customer.dto;

import java.util.List;

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
public class FiltersDTO {
	List<GenreDataDTO> genreList;
	List<LengthDataDTO> lengthList;
	List<ReleasesDataDTO> releasesList;
	Float rating;
}
