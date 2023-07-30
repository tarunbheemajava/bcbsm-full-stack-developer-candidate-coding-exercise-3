package com.feedback.controller;

import com.feedback.bean.Review;
import com.feedback.dto.ReviewDto;
import com.feedback.enums.Role;
import com.feedback.repository.ReviewRepository;
import com.feedback.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import com.feedback.util.AppsConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(AppsConstants.REVIEW_END_POINT)
@AllArgsConstructor
public class ReviewController {
    private ReviewRepository reviewRepository;
    private AuthenticationService authenticationService;

    @PreAuthorize("hasRole('ROLE_ADMIN','ROLE_USER')")
    @PostMapping
    public Review submitReview(@RequestBody ReviewDto reviewDto) {
        Review review=Review.builder()
                .comment(reviewDto.getComment())
                .rating(reviewDto.getRating())
                .username(authenticationService.getCurrentUsername())
                .build();
        return reviewRepository.save(review);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping
    public List<Review> fetchReview() {
        List<String> userRoles = authenticationService.getCurrentUserRoles();
        if (!CollectionUtils.isEmpty(userRoles)) {
            if (userRoles.contains(AppsConstants.ADMIN_ROLE)) return reviewRepository.findAll();
            else return reviewRepository.findAllByUsername(authenticationService.getCurrentUsername());
        } else {
            throw new ResponseStatusException(HttpStatus.NON_AUTHORITATIVE_INFORMATION, String.format("user not authenticated "));
        }
    }

}
