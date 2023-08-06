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

                        ps.setString(1, token);
                        ps.setLong(2, entity.getMemberId());
                        ps.setLong(3, entity.getCouponId());
                        ps.setBoolean(4, false);
                        ps.setString(5, LocalDateTime.now().toString());
                        ps.setString(6, LocalDateTime.now().toString());
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
                        ps.setObject(1, entity.getId());
                    }

                    @Override
                    public int getBatchSize() {
                        return couponIssueFailedEntities.size();
                    }
                });
    }
}
