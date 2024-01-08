package com.fitlife.app.service.NewsHealth;

import com.fitlife.app.dataclass.dto.NewsHealthDTO;
import com.fitlife.app.dataclass.request.SearchNewsRequest;
import org.springframework.data.domain.Page;

public interface INewsHealthService {
    Page<NewsHealthDTO> searchNews(SearchNewsRequest request);

    NewsHealthDTO findNewsById( Long id);
}
