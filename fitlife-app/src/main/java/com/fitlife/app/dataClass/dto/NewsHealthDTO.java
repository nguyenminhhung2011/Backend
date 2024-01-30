package com.fitlife.app.dataClass.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsHealthDTO {
    private Long id;
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String content;
    private String publishedAt;
}
