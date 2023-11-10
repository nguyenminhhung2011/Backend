package com.FitnessApp.Service.ExcerciseService;

import com.FitnessApp.DTO.Request.FetchExerciseRequest;
import com.FitnessApp.DTO.Request.PageRequest;
import com.FitnessApp.Service.Generic.GenericSearchService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.FitnessApp.Model.Exercise;
import com.FitnessApp.Repository.ExerciseRepository;
import com.FitnessApp.Service.Generic.GenericService;

import java.util.List;

@Service
public class ExerciseService extends GenericSearchService<Exercise> {
	public ExerciseService(ExerciseRepository exerciseRepository) {
		super(exerciseRepository);
	}

	public Page<Exercise> searchExercise(FetchExerciseRequest request) {
		final List<Specification<Exercise>> filter = List.of(
				specification("name", request.name()),
				specification("target", request.target()),
				specification("bodyPart", request.bodyPart())
		);

		final List<Specification<Exercise>> search = List.of(
				specification("name", request.search()),
				specification("target", request.search()),
				specification("bodyPart", request.search())
		);

		final Specification<Exercise> specifications = allOf(filter).or(anyOf(search));

		if (request.pageRequest()==null) {
			return super.search(specifications);
		}
		else {
			return super.search(specifications,request.pageRequest());
		}
	}

	public Page<Exercise> fetchExercises(PageRequest pageRequest) {
		return genericRepository.findAll(
				org.springframework.data.domain.PageRequest
						.of(pageRequest.page(), pageRequest.perPage(), Sort.by("id").ascending()
				)
		);
	}



}
