package com.fitlife.app.service.excercise;

import com.fitlife.app.dataClass.request.FetchExerciseRequest;
import com.fitlife.app.dataClass.request.PageRequest;
import com.fitlife.app.model.exercise.BodyPart;
import com.fitlife.app.model.exercise.Equipment;
import com.fitlife.app.model.exercise.Target;
import com.fitlife.app.repository.jpa.exercise.*;
import com.fitlife.app.service.generic.GenericSearchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.fitlife.app.model.exercise.Exercise;

import java.lang.reflect.Type;
import java.util.*;

@SuppressWarnings("rawtypes")
@Service
public class ExerciseService extends GenericSearchService<Exercise,Long> {

	private  final  Map<Type, CrudRepository> repositoryMap = new HashMap<>();
	public ExerciseService(ExerciseRepository exerciseRepository, TargetRepository targetRepository, EquipmentRepository equipmentRepository, BodyPartRepository bodyPartRepository) {
		super(exerciseRepository);
		repositoryMap.put(Target.class,targetRepository);
		repositoryMap.put(BodyPart.class,bodyPartRepository);
		repositoryMap.put(Equipment.class,equipmentRepository);
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
