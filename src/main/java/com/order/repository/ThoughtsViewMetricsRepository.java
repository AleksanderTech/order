package com.order.repository;

import com.order.entity.ThoughtsViewMetrics;
import org.jooq.DSLContext;

import java.util.Optional;

import static com.order.database.jooq.Tables.ORDER_USER;
import static com.order.database.jooq.Tables.SORT_ORDER;
import static com.order.database.jooq.tables.ThoughtsViewMetrics.THOUGHTS_VIEW_METRICS;

public class ThoughtsViewMetricsRepository {
    private final DSLContext dslContext;

    public ThoughtsViewMetricsRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public Optional<ThoughtsViewMetrics> viewMetrics(long userId) {
        return dslContext.select(THOUGHTS_VIEW_METRICS.LEFT_POSITION,
                THOUGHTS_VIEW_METRICS.TOP_POSITION,
                THOUGHTS_VIEW_METRICS.RIGHT_POSITION,
                THOUGHTS_VIEW_METRICS.BOTTOM_POSITION,
                THOUGHTS_VIEW_METRICS.WIDTH,
                THOUGHTS_VIEW_METRICS.HEIGHT)
                .from(THOUGHTS_VIEW_METRICS)
                .join(ORDER_USER)
                .on(THOUGHTS_VIEW_METRICS.USER_ID.eq(ORDER_USER.ID))
                .where(THOUGHTS_VIEW_METRICS.USER_ID.eq(userId))
                .fetchOptional(record -> ThoughtsViewMetrics.builder()
                        .leftPosition(record.getValue(THOUGHTS_VIEW_METRICS.LEFT_POSITION))
                        .topPosition(record.getValue(THOUGHTS_VIEW_METRICS.TOP_POSITION))
                        .rightPosition(record.getValue(THOUGHTS_VIEW_METRICS.RIGHT_POSITION))
                        .bottomPosition(record.getValue(THOUGHTS_VIEW_METRICS.BOTTOM_POSITION))
                        .width(record.getValue(THOUGHTS_VIEW_METRICS.WIDTH))
                        .height(record.getValue(THOUGHTS_VIEW_METRICS.HEIGHT))
                        .build());
    }

    public void save(long userId, ThoughtsViewMetrics thoughtsViewMetrics) {
        viewMetrics(userId).ifPresentOrElse(metrics -> {
                    dslContext.update(THOUGHTS_VIEW_METRICS)
                            .set(THOUGHTS_VIEW_METRICS.LEFT_POSITION, thoughtsViewMetrics.leftPosition)
                            .set(THOUGHTS_VIEW_METRICS.TOP_POSITION, thoughtsViewMetrics.topPosition)
                            .set(THOUGHTS_VIEW_METRICS.RIGHT_POSITION, thoughtsViewMetrics.rightPosition)
                            .set(THOUGHTS_VIEW_METRICS.BOTTOM_POSITION, thoughtsViewMetrics.bottomPosition)
                            .set(THOUGHTS_VIEW_METRICS.WIDTH, thoughtsViewMetrics.width)
                            .set(THOUGHTS_VIEW_METRICS.HEIGHT, thoughtsViewMetrics.height)
                            .where(THOUGHTS_VIEW_METRICS.USER_ID.eq(userId))
                            .execute();
                },
                () -> {
                    dslContext.insertInto(THOUGHTS_VIEW_METRICS,
                            THOUGHTS_VIEW_METRICS.USER_ID,
                            THOUGHTS_VIEW_METRICS.LEFT_POSITION,
                            THOUGHTS_VIEW_METRICS.TOP_POSITION,
                            THOUGHTS_VIEW_METRICS.RIGHT_POSITION,
                            THOUGHTS_VIEW_METRICS.BOTTOM_POSITION,
                            THOUGHTS_VIEW_METRICS.WIDTH,
                            THOUGHTS_VIEW_METRICS.HEIGHT)
                            .values(userId,
                                    thoughtsViewMetrics.leftPosition,
                                    thoughtsViewMetrics.topPosition,
                                    thoughtsViewMetrics.rightPosition,
                                    thoughtsViewMetrics.bottomPosition,
                                    thoughtsViewMetrics.width,
                                    thoughtsViewMetrics.height)
                            .execute();
                });
    }
}
