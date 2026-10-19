package com.example.session05.services;

import com.example.session05.models.dtos.CourseResponse;
import com.example.session05.models.dtos.PageResponse;
import com.example.session05.models.entities.Course;
import com.example.session05.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public PageResponse<CourseResponse> getPagedCourses(int page, int size, String sortBy, Sort.Direction direction) {
        if (page < 0) page = 0;

        Sort sort;
        if (sortBy == null || sortBy.trim().isEmpty()) {
            sort = Sort.by(direction, "id");
        } else {
            sort = Sort.by(direction, sortBy);
        }

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Course> coursePage = courseRepository.findAll(pageable);

        // 1. Chuyển đổi danh sách nội dung (content) từ Entity sang DTO
        List<CourseResponse> items = coursePage.getContent().stream()
                .map(this::convertToCourseResponse)
                .collect(Collectors.toList());

        // 2. Map các thông tin phân trang vào PageResponse wrapper mới tạo
        return PageResponse.<CourseResponse>builder()
                .items(items)
                .page(coursePage.getNumber())
                .size(coursePage.getSize())
                .totalItems((int) coursePage.getTotalElements())
                .totalPages(coursePage.getTotalPages())
                .isLast(coursePage.isLast())
                .build();
    }

    private CourseResponse convertToCourseResponse(Course course) {
        return CourseResponse.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .build();
    }
}