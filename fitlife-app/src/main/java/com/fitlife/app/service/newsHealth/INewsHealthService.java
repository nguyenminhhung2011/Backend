package com.fitlife.app.service.newsHealth;

import com.fitlife.app.dataClass.dto.NewsHealthDTO;
import com.fitlife.app.dataClass.request.SearchNewsRequest;
import org.springframework.data.domain.Page;

public interface INewsHealthService {
    Page<NewsHealthDTO> searchNews(SearchNewsRequest request);

    NewsHealthDTO findNewsById( Long id);
}
