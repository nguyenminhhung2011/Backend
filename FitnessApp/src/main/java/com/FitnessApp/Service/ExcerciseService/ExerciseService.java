package com.FitnessApp.Service.ExcerciseService;

import com.FitnessApp.DTO.Request.FetchExerciseRequest;
import com.FitnessApp.DTO.Request.PageRequest;
import com.FitnessApp.Model.Exercise.BodyPart;
import com.FitnessApp.Model.Exercise.Equipment;
import com.FitnessApp.Model.Exercise.Target;
import com.FitnessApp.Repository.BodyPartRepository;
import com.FitnessApp.Repository.EquipmentRepository;
import com.FitnessApp.Repository.TargetRepository;
import com.FitnessApp.Service.Generic.GenericSearchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Service;

import com.FitnessApp.Model.Exercise.Exercise;
import com.FitnessApp.Repository.ExerciseRepository;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;

@Service
public class ExerciseService extends GenericSearchService<Exercise> {

	private  final  Map<Type, CrudRepository> repositoryMap = new HashMap<>();
	public ExerciseService(ExerciseRepository exerciseRepository, TargetRepository targetRepository, EquipmentRepository equipmentRepository, BodyPartRepository bodyPartRepository) {
		super(exerciseRepository);
		repositoryMap.put(Target.class,targetRepository);
		repositoryMap.put(BodyPart.class,bodyPartRepository);
		repositoryMap.put(Equipment.class,equipmentRepository);
	}

	public Page<Exercise> searchExercise(FetchExerciseRequest request) {
		final Class<FetchExerciseRequest> test = FetchExerciseRequest.class;

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


	public <T> List<T> getListDataOf(Type type){
		if (repositoryMap.containsKey(type)) {
			List<T> result = new ArrayList<>();

			final Iterable<T> item = repositoryMap.get(type).findAll();

			item.forEach(result::add);

			return result;
		}

		else {
			return new ArrayList<>();
		}
	}

}
