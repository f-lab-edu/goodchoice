package com.flab.goodchoice.coupon.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.goodchoice.coupon.domain.Coupon;
import com.flab.goodchoice.coupon.domain.CouponType;
import com.flab.goodchoice.coupon.domain.State;
import com.flab.goodchoice.coupon.dto.CouponInfoResponse;
import com.flab.goodchoice.coupon.dto.CouponPublishRequest;
import com.flab.goodchoice.coupon.infrastructure.repositories.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;
import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
class CouponUseSynchronismTest {
    @Autowired
    protected MockMvc mvc;
    @Autowired
    private CouponQueryService couponQueryService;
    @Autowired
    private CouponCommand couponCommand;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired private MemberRepository memberRepository;

    UUID couponToken;
    int couponCount = 100;

    Coupon coupon;
    Coupon saveCoupon;

    @BeforeEach
    void setUp() {
        couponToken = UUID.randomUUID();

        coupon = new Coupon(couponToken, "10% 할인", couponCount, CouponType.DISCOUNT, 10, State.ACTIVITY);
        saveCoupon = couponCommand.save(coupon);
    }

    @DisplayName("쿠폰 선착순 발급 동시성 테스트")
    @Test
    void couponSynchroniseTest() throws InterruptedException {
        CyclicBarrier barrier = new CyclicBarrier(couponCount);
        ExecutorService executorService = Executors.newFixedThreadPool(couponCount);

        for (int i = 1; i <= couponCount; i++) {
            Long memberId = (long) i;
            executorService.execute(() -> {
                try {
                    barrier.await();
                    createCouponPublish(memberId);
                } catch (InterruptedException | BrokenBarrierException e) {

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }

        Thread.sleep(5000);

        CouponInfoResponse response = couponQueryService.getCoupon(couponToken);
        assertThat(response.stock()).isEqualTo(0);
    }

    void createCouponPublish(Long memberId) throws Exception {
        CouponPublishRequest request = new CouponPublishRequest(memberId, couponToken);

        mvc.perform(post("/api/coupons/publish")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @DisplayName("쿠폰 선착순 발급 동시성 테스트 with Rettuce AOP")
    @Test
    void couponSynchroniseTestWithRettuceAop() throws InterruptedException {
        CyclicBarrier barrier = new CyclicBarrier(couponCount);
        ExecutorService executorService = Executors.newFixedThreadPool(couponCount);

        for (int i = 1; i <= couponCount; i++) {
            Long memberId = (long) i;
            executorService.execute(() -> {
                try {
                    barrier.await();
                    createCouponPublishWithRettuceAop(memberId);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }

        Thread.sleep(15000);

        CouponInfoResponse response = couponQueryService.getCoupon(couponToken);
        assertThat(response.stock()).isEqualTo(0);
    }

    void createCouponPublishWithRettuceAop(Long memberId) throws Exception {
        CouponPublishRequest request = new CouponPublishRequest(memberId, couponToken);

        mvc.perform(post("/api/coupons/publish/rettuce")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @DisplayName("쿠폰 선착순 발급 동시성 테스트 with Redisson AOP")
    @Test
    void couponSynchroniseTestWithRedissonAop() throws InterruptedException {
        CyclicBarrier barrier = new CyclicBarrier(couponCount);
        ExecutorService executorService = Executors.newFixedThreadPool(couponCount);

        for (int i = 1; i <= couponCount; i++) {
            Long memberId = (long) i;
            executorService.execute(() -> {
                try {
                    barrier.await();
                    createCouponPublishWithRedissonAop(memberId);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }

        Thread.sleep(15000);

        CouponInfoResponse response = couponQueryService.getCoupon(couponToken);
        assertThat(response.stock()).isEqualTo(0);
    }

    void createCouponPublishWithRedissonAop(Long memberId) throws Exception {
        CouponPublishRequest request = new CouponPublishRequest(memberId, couponToken);

        mvc.perform(post("/api/coupons/publish/redisson")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }
}
