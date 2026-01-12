package com.execodex.app.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.r2dbc.postgresql.codec.Interval;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.dialect.PostgresDialect;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class R2dbcConfiguration {

    @Bean
    public R2dbcCustomConversions r2dbcCustomConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(IntervalToDurationConverter.INSTANCE);
        converters.add(DurationToIntervalConverter.INSTANCE);
        return R2dbcCustomConversions.of(PostgresDialect.INSTANCE, converters);
    }

    @ReadingConverter
    public enum IntervalToDurationConverter implements Converter<Interval, Duration> {
        INSTANCE;

        @Override
        public Duration convert(Interval source) {
            return source.getDuration();
        }
    }

    @WritingConverter
    public enum DurationToIntervalConverter implements Converter<Duration, Interval> {
        INSTANCE;

        @Override
        public Interval convert(Duration source) {
            return Interval.of(source);
        }
    }
}
