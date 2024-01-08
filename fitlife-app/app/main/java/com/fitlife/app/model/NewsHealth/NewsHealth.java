package com.fitlife.app.model.NewsHealth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fitlife.app.dataclass.views.NewsHealthViews;
import com.fitlife.app.dataclass.views.UserViews;
import com.fitlife.app.model.User.UserProfile;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class NewsHealth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // This indicates auto-generation of IDs
    @JsonView(value = {NewsHealthViews.Summary.class, UserViews.Summary.class,})
    private Long id;

    @JsonView(value = {NewsHealthViews.Summary.class})
    @JsonProperty("author")
    private String author;

    @JsonView(value = {NewsHealthViews.Summary.class})
    @JsonProperty("title")
    @Column(length = 1024)
    private String title;

    @JsonView(value = {NewsHealthViews.Summary.class})
    @JsonProperty("description")
    @Column(length = 1024)
    private String description;

    @JsonView(value = {NewsHealthViews.Summary.class})
    @JsonProperty("url")
    @Column(length = 1024)
    private String url;

    @JsonView(value = {NewsHealthViews.Summary.class})
    @JsonProperty("urlToImage")
    @Column(length = 1024)
    private String urlToImage;

    @JsonView(value = {NewsHealthViews.Summary.class})
    @JsonProperty("content")
    @Column(length = 1024)
    private String content;

    @JsonView(value = {NewsHealthViews.Summary.class})
    @JsonProperty("publishedAt")
    @Column(length = 1024)
    private String publishedAt;


    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST,CascadeType.MERGE}
    )
    @JoinTable(
            name = "news_favorite",
            joinColumns = @JoinColumn(name = "news_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_profile_id",referencedColumnName = "id")
    )
    private List<UserProfile> newsUser;

}
