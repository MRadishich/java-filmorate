package ru.yandex.practicum.filmorate.storage.review;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import ru.yandex.practicum.filmorate.model.Review;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SqlGroup({
        @Sql(value = "/test/review-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/test/clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
})
public class ReviewStorageTest {
    @Autowired
    private ReviewStorage reviewStorage;
    @Autowired
    private MapRowToReview reviewMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void test1_shouldSaveReviewInDataBase() {
        Review review = Review.builder()
                .content("The best film")
                .isPositive(Boolean.FALSE)
                .userId(3L)
                .filmId(3L)
                .build();

        reviewStorage.save(review);

        String sql = "SELECT * FROM reviews " +
                "WHERE content = ? " +
                "AND is_positive = ? " +
                "AND user_id = ? " +
                "AND film_id = ? ";

        List<Review> savedReviews = jdbcTemplate.query(sql,
                reviewMapper,
                review.getContent(),
                review.getIsPositive(),
                review.getUserId(),
                review.getFilmId()
        );

        int expectedSize = 1;

        assertEquals(expectedSize, savedReviews.size());
        assertEquals(3, savedReviews.get(0).getId());
    }

    @Test
    public void test2_shouldThrowExceptionWhenSaveReviewWithUnknownUserId() {
        long incorrectUserId = 30L;
        Review review = Review.builder()
                .content("The best film")
                .isPositive(Boolean.FALSE)
                .userId(incorrectUserId)
                .filmId(3L)
                .build();

        assertThrows(DataIntegrityViolationException.class, () -> reviewStorage.save(review));
    }

    @Test
    public void test3_shouldThrowExceptionWhenSaveReviewWithUnknownFilmId() {
        long incorrectFilmId = 30L;
        Review review = Review.builder()
                .content("The best film")
                .isPositive(Boolean.FALSE)
                .userId(3L)
                .filmId(incorrectFilmId)
                .build();

        assertThrows(DataIntegrityViolationException.class, () -> reviewStorage.save(review));
    }

    @Test
    public void test4_shouldReturnSavedReviewWithId() {
        Review review = Review.builder()
                .content("The best film")
                .isPositive(Boolean.FALSE)
                .userId(3L)
                .filmId(3L)
                .build();

        Review savedReview = reviewStorage.save(review);

        assertNotNull(savedReview.getId());
    }

    @Test
    public void test5_shouldUpdateReviewInDataBase() {
        long reviewId = 1L;

        Review review = Review.builder()
                .id(reviewId)
                .content("Updated Good film")
                .isPositive(Boolean.FALSE)
                .userId(1L)
                .filmId(1L)
                .useful(1)
                .build();

        reviewStorage.save(review);

        String sql = "SELECT * FROM reviews " +
                "WHERE id = ?";

        Review updatedReview = jdbcTemplate.queryForObject(sql, reviewMapper, reviewId);


        assertNotNull(updatedReview);
        assertEquals(review.getId(), updatedReview.getId());
        assertEquals(review.getUseful(), updatedReview.getUseful());
        assertEquals(review.getUserId(), updatedReview.getUserId());
        assertEquals(review.getFilmId(), updatedReview.getFilmId());
        assertEquals(review.getIsPositive(), updatedReview.getIsPositive());
        assertEquals(review.getContent(), updatedReview.getContent());
    }

    @Test
    public void test6_shouldReturnReviewById() {
        Review expectedReview = new Review(
                2L,
                "Bad film",
                Boolean.FALSE,
                2L,
                2L,
                0
        );

        Optional<Review> foundReview = reviewStorage.findById(2L);

        assertThat(foundReview)
                .isPresent()
                .hasValueSatisfying(review -> {
                            assertThat(review).hasFieldOrPropertyWithValue("id", expectedReview.getId());
                            assertThat(review).hasFieldOrPropertyWithValue("content", expectedReview.getContent());
                            assertThat(review).hasFieldOrPropertyWithValue("isPositive", expectedReview.getIsPositive());
                            assertThat(review).hasFieldOrPropertyWithValue("userId", expectedReview.getUserId());
                            assertThat(review).hasFieldOrPropertyWithValue("filmId", expectedReview.getFilmId());
                            assertThat(review).hasFieldOrPropertyWithValue("useful", expectedReview.getUseful());
                        }
                );
    }

    @Test
    public void test7_shouldDeleteReviewById() {
        String sql = "SELECT * FROM reviews WHERE id = ?";
        List<Review> foundReviews = jdbcTemplate.query(sql, reviewMapper, 1L);

        assertEquals(1, foundReviews.size());

        reviewStorage.deleteById(1L);

        foundReviews = jdbcTemplate.query(sql, reviewMapper, 1L);

        assertTrue(foundReviews.isEmpty());
    }

    @Test
    public void test8_shouldIncreaseUsefulByOne() {
        long reviewId = 1L;
        String sql = "SELECT useful FROM reviews WHERE id = ?";

        Integer useful = jdbcTemplate.queryForObject(sql, Integer.class, reviewId);

        assertEquals(1, useful);

        reviewStorage.changeUseful(reviewId, 1);

        sql = "SELECT useful FROM reviews WHERE id = ?";

        useful = jdbcTemplate.queryForObject(sql, Integer.class, reviewId);

        assertEquals(2, useful);
    }

    @Test
    public void test9_shouldDecreaseUsefulByOne() {
        long reviewId = 1L;
        String sql = "SELECT useful FROM reviews WHERE id = ?";

        Integer useful = jdbcTemplate.queryForObject(sql, Integer.class, reviewId);

        assertEquals(1, useful);

        reviewStorage.changeUseful(reviewId, -1);

        sql = "SELECT useful FROM reviews WHERE id = ?";

        useful = jdbcTemplate.queryForObject(sql, Integer.class, reviewId);

        assertEquals(0, useful);
    }

    @Test
    public void test10_shouldReturnTrueWhenReviewExist() {
        long reviewId = 1L;

        assertTrue(reviewStorage.existsById(reviewId));
    }

    @Test
    public void test11_shouldReturnFalseWhenReviewNotExist() {
        long reviewId = 10L;

        assertFalse(reviewStorage.existsById(reviewId));
    }

    @Test
    public void test12_shouldReturnOnlyOneMostUsefulReview() {
        String sql = "INSERT INTO reviews (content, is_positive, user_id, film_id, useful) " +
                "VALUES ('Bad film', false, 2, 2, 5)";

        jdbcTemplate.update(sql);

        List<Review> review = new ArrayList<>(reviewStorage.findMostUsefulReviews(1));

        assertEquals(1, review.size());
        assertEquals(5, review.get(0).getUseful());
        assertEquals(2L, review.get(0).getUserId());
        assertEquals(2L, review.get(0).getFilmId());
        assertEquals(3L, review.get(0).getId());
    }

    @Test
    public void test13_shouldReturnOnlyOneMostUsefulReviewByFilmId() {
        String sql = "INSERT INTO reviews (content, is_positive, user_id, film_id, useful) " +
                "VALUES ('Bad film', false, 2, 3, 5)";

        jdbcTemplate.update(sql);

        List<Review> review = new ArrayList<>(reviewStorage.findMostUsefulReviewsByFilmId(3, 1));

        assertEquals(1, review.size());
        assertEquals(5, review.get(0).getUseful());
        assertEquals(2L, review.get(0).getUserId());
        assertEquals(3L, review.get(0).getFilmId());
        assertEquals(3L, review.get(0).getId());
    }
}
