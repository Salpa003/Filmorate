package com.yandex.filmorate.controller;

import com.yandex.filmorate.model.db.GenreEntity;
import com.yandex.filmorate.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/genres")
public class GenreController {

    @Autowired
    private GenreService service;

    @GetMapping
    public List<GenreEntity> getAllGenres() {
        return service.getAllGenres();
    }

    @GetMapping("/{id}")
    public GenreEntity getGenre(@PathVariable("id") Long id) {
        return service.getGenre(id);
    }
}
//GET /genres — возвращает список объектов, содержащих жанр;
//GET /genres/{id} — возвращает объект, содержащий жанр, с идентификатором id.
//Пример возвращаемого значения:
//{
//“id”: 1,
//“name”: “Комедия”
//}