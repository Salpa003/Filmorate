//package com.yandex.filmorate.repository;
//
//import com.yandex.filmorate.entity.FilmsLikes;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import java.util.Set;
//
//public interface FilmsLikesRepository extends JpaRepository<FilmsLikes, Long> {
//
//    void removeByFilmIdAndUserId(Long filmId, Long userId);
//
//    @Query("""
//                SELECT fl.filmId
//                FROM FilmsLikes fl
//                GROUP BY fl.filmId
//                ORDER BY COUNT(fl.userId) DESC
//                LIMIT :c""")
//    Set<Long> getTopFilms(Long c);
//}
