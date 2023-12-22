package com.fitlife.app.Service.NewsHealth;

import com.fitlife.app.DTO.DataClass.NewsHealthDTO;
import com.fitlife.app.DTO.Request.SearchNewsRequest;
import org.springframework.data.domain.Page;

public interface INewsHealthService {
    Page<NewsHealthDTO> searchNews(SearchNewsRequest request);

    NewsHealthDTO findNewsById( Long id);
}
