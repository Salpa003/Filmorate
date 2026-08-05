package com.yandex.filmorate.controller;

import com.yandex.filmorate.entity.RatingEntity;
import com.yandex.filmorate.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mpa")
public class RatingController {
    @Autowired
    private RatingService service;

    @GetMapping
    public List<RatingEntity> getAllRatings() {
        return service.getAllRatings();
    }

    @GetMapping("/{id}")
    public RatingEntity getRatingById(@PathVariable("id") Integer id) {
        return service.getRatingById(id);
    }

}
