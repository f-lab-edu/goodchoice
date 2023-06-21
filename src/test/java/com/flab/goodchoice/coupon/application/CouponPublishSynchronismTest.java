package com.flab.goodchoice.coupon.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.goodchoice.coupon.domain.Coupon;
import com.flab.goodchoice.coupon.domain.CouponType;
import com.flab.goodchoice.coupon.domain.State;
import com.flab.goodchoice.coupon.dto.CouponInfoResponse;
import com.flab.goodchoice.coupon.dto.CouponPublishRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
class CouponPublishSynchronismTest {
    @Autowired
    protected MockMvc mvc;
    @Autowired
    private CouponInfoService couponQueryService;
    @Autowired
    private CouponCommand couponCommand;

    @Autowired
    protected ObjectMapper objectMapper;

    UUID couponToken;

    Coupon coupon;
    Coupon saveCoupon;

    @BeforeEach
    void setUp() {
        couponToken = UUID.randomUUID();
    }

    @DisplayName("쿠폰 선착순 발급 동시성 테스트")
    @Test
    @Disabled
    void couponSynchroniseTest() throws InterruptedException {
        int couponCount = 100;

        createCoupon(couponCount);

        couponSynchroniseTest(couponCount, "/api/coupons/publish");

        CouponInfoResponse response = couponQueryService.getCoupon(couponToken);
        assertThat(response.stock()).isEqualTo(0);
    }

    @DisplayName("쿠폰 선착순 발급 동시성 테스트 with Redisson AOP")
    @Test
    @Disabled
    void couponSynchroniseTestWithRedissonAop() throws InterruptedException {
        int couponCount = 100;

        createCoupon(couponCount);

        couponSynchroniseTest(couponCount, "/api/coupons/publish/redisson");

        CouponInfoResponse response = couponQueryService.getCoupon(couponToken);
        assertThat(response.stock()).isEqualTo(0);
    }

    private void couponSynchroniseTest(int couponCount, String url) throws InterruptedException {
        CyclicBarrier barrier = new CyclicBarrier(couponCount);
        ExecutorService executorService = Executors.newFixedThreadPool(couponCount);

        for (int i = 1; i <= couponCount; i++) {
            Long memberId = (long) i;
            executorService.execute(() -> {
                try {
                    barrier.await();
                    createCouponPublish(memberId, url);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }

        Thread.sleep(15000);
    }

    @DisplayName("한 회원당 하나의 쿠폰 선착순 발급 동시성 테스트 with Redisson AOP")
    @Test
    @Disabled
    void userDuplicationCouponSynchroniseTestWithRedissonAop() throws InterruptedException {
        duplicationCouponSynchroniseTest("/api/coupons/publish/redisson");

        CouponInfoResponse response = couponQueryService.getCoupon(couponToken);
        assertThat(response.stock()).isEqualTo(100);
    }

    private void duplicationCouponSynchroniseTest(String url) throws InterruptedException {
        int couponCount = 200;

        createCoupon(couponCount);

        CyclicBarrier barrier = new CyclicBarrier(couponCount);
        ExecutorService executorService = Executors.newFixedThreadPool(couponCount);

        for (int i = 1; i <= couponCount; i++) {
            Long memberId = (long) i % 100;

            if (memberId == 0) memberId = 100L;

            Long finalMemberId = memberId;
            executorService.execute(() -> {
                try {
                    barrier.await();
                    createCouponPublish(finalMemberId, url);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }

        Thread.sleep(25000);
    }

    private void createCoupon(int couponCount) {
        coupon = new Coupon(couponToken, "10% 할인", couponCount, CouponType.DISCOUNT, 10, State.ACTIVITY);
        saveCoupon = couponCommand.save(coupon);
    }

    private void createCouponPublish(Long memberId, String url) throws Exception {
        CouponPublishRequest request = new CouponPublishRequest(memberId, couponToken);

        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }
}
