package com.fitlife.app.Service.NewsHealth;

import com.fitlife.app.DTO.DataClass.NewsHealthDTO;
import com.fitlife.app.DTO.Request.SearchNewsRequest;
import com.fitlife.app.Model.NewsHealth.NewsHealth;
import com.fitlife.app.Repository.NewsHealthRepository;
import com.fitlife.app.Service.Generic.GenericSearchService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsHealthServiceImpl extends GenericSearchService<NewsHealth, Long> implements  INewsHealthService {
    private final ModelMapper modelMapper;

    public NewsHealthServiceImpl(NewsHealthRepository repository, ModelMapper modelMapper) {
        super(repository);
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<NewsHealthDTO> searchNews(SearchNewsRequest request) {
        final List<Specification<NewsHealth>> filte = List.of(
            specification("content", request.content())
        );
        final List<Specification<NewsHealth>> search = List.of(
                specification("content", request.content())
        );
        final  Specification<NewsHealth> specification = allOf(filte).or(anyOf(search));

        if(request.pageRequest() == null){
            return super.search(specification).map(item -> modelMapper.map(item, NewsHealthDTO.class));
        }
        return super.search(specification,request.pageRequest()).map(item -> modelMapper.map(item, NewsHealthDTO.class));
    }

    @Override
    public NewsHealthDTO findNewsById(Long id) {
        return modelMapper.map(super.findById(id),NewsHealthDTO.class);
    }

}
