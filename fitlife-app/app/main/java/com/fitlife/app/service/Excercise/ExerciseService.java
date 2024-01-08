package com.fitlife.app.service.Excercise;

import com.fitlife.app.dataclass.request.FetchExerciseRequest;
import com.fitlife.app.dataclass.request.PageRequest;
import com.fitlife.app.model.Exercise.BodyPart;
import com.fitlife.app.model.Exercise.Equipment;
import com.fitlife.app.model.Exercise.Target;
import com.fitlife.app.repository.Exercise.BodyPartRepository;
import com.fitlife.app.repository.Exercise.EquipmentRepository;
import com.fitlife.app.repository.Exercise.TargetRepository;
import com.fitlife.app.service.Generic.GenericSearchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.fitlife.app.model.Exercise.Exercise;
import com.fitlife.app.repository.Exercise.ExerciseRepository;

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
