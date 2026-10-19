package com.example.session05.models.dtos;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CourseResponse {
    private Long id;
    private String title;
    private String description;
}