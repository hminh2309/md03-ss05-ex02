package com.example.session05.models.dtos;

import lombok.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PageResponse<T> {
    private List<T> items;
    private int page;
    private int size;
    private int totalItems;
    private int totalPages;
    private boolean isLast;
}