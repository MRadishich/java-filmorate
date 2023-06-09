package ru.yandex.practicum.filmorate.validation;

import ru.yandex.practicum.filmorate.dto.FilmDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class ReleaseDateValidator implements ConstraintValidator<ReleaseDate, LocalDate> {
    private static final LocalDate START_DATE = LocalDate.parse(FilmDto.MIN_DATE_RELEASE);

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {
        return date.isAfter(START_DATE) || date.isEqual(START_DATE);
    }
}
