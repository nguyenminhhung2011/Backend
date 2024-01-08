package com.fitlife.app.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fitlife.app.dataclass.request.SearchNewsRequest;
import com.fitlife.app.dataclass.views.NewsHealthViews;
import com.fitlife.app.service.NewsHealth.INewsHealthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/news")
public class NewsHealthController {

    private  final INewsHealthService newsHealthService;

    public NewsHealthController(INewsHealthService newsHealthService) {
        this.newsHealthService = newsHealthService;
    }

    @GetMapping("/search")
    @JsonView(NewsHealthViews.Summary.class)
    public ResponseEntity<?> searchNews(@RequestBody SearchNewsRequest request){
        return ResponseEntity.ok(newsHealthService.searchNews(request));
    }

    @GetMapping("{id}")
    @JsonView(NewsHealthViews.Summary.class)
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(newsHealthService.findNewsById(id));
    }
}
