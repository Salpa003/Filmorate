package com.yandex.filmorate.service;

import com.yandex.filmorate.exception.NotFoundException;
import com.yandex.filmorate.model.db.RatingEntity;
import com.yandex.filmorate.model.db.RatingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {
    @Autowired
    private RatingRepository repository;

    @Transactional
    public List<RatingEntity> getAllRatings() {
        return repository.findAll();
    }

    @Transactional
    public RatingEntity getRatingById(Integer id) {
        Optional<RatingEntity> maybe = repository.findById(id);
        if (maybe.isEmpty())
            throw new NotFoundException("Rating not found!");
        return repository.findById(id).get();
    }
}
