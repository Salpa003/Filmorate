package com.yandex.filmorate.service;

import com.yandex.filmorate.exception.NotFoundException;
import com.yandex.filmorate.entity.GenreEntity;
import com.yandex.filmorate.repository.GenreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {
    @Autowired
    private GenreRepository repository;

    @Transactional
    public List<GenreEntity> getAllGenres() {
       return repository.findAll();
    }

    @Transactional
    public GenreEntity getGenre(Integer id) {
        Optional<GenreEntity> byId = repository.findById(id);
        if (byId.isEmpty())
            throw new NotFoundException("Genre not found!");
        return byId.get();
    }
}

