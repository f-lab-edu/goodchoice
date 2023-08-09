package com.flab.goodchoicebatch.infrastructure;

import com.flab.goodchoicecoupon.infrastructure.entity.CouponIssueFailedEntity;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public class CouponBatchWriterRepository {

    private final static int SQL_PARAMETER_ONE = 1;
    private final static int SQL_PARAMETER_TWO = 2;
    private final static int SQL_PARAMETER_THREE = 3;
    private final static int SQL_PARAMETER_FOUR = 4;
    private final static int SQL_PARAMETER_FIVE = 5;
    private final static int SQL_PARAMETER_SIX = 6;

    private final JdbcTemplate jdbcTemplate;

    public CouponBatchWriterRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Transactional
    public void createCouponIssue(List<? extends CouponIssueFailedEntity> couponIssueFailedEntities) {
        String sql = "INSERT INTO coupon_issue (coupon_issue_token, member_id, coupon_id, used_yn, created_at, used_at) VALUES (UUID_TO_BIN(?), ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        CouponIssueFailedEntity entity = couponIssueFailedEntities.get(i);
                        String token = UUID.randomUUID().toString().trim();

                        ps.setString(SQL_PARAMETER_ONE, token);
                        ps.setLong(SQL_PARAMETER_TWO, entity.getMemberId());
                        ps.setLong(SQL_PARAMETER_THREE, entity.getCouponId());
                        ps.setBoolean(SQL_PARAMETER_FOUR, false);
                        ps.setString(SQL_PARAMETER_FIVE, LocalDateTime.now().toString());
                        ps.setString(SQL_PARAMETER_SIX, LocalDateTime.now().toString());
                    }

                    @Override
                    public int getBatchSize() {
                        return couponIssueFailedEntities.size();
                    }
                });
    }

    @Transactional
    public void modifyCouponIssueFailed(List<? extends CouponIssueFailedEntity> couponIssueFailedEntities) {
        String sql = "UPDATE coupon_issue_failed SET restored_yn = true WHERE id = ?";

        jdbcTemplate.batchUpdate(sql,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        CouponIssueFailedEntity entity = couponIssueFailedEntities.get(i);
                        ps.setObject(SQL_PARAMETER_ONE, entity.getId());
                    }

                    @Override
                    public int getBatchSize() {
                        return couponIssueFailedEntities.size();
                    }
                });
    }
}
