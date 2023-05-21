package com.flab.goodchoice.coupon.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.goodchoice.coupon.domain.repositories.CouponPublishRepository;
import com.flab.goodchoice.coupon.domain.repositories.CouponRepository;
import com.flab.goodchoice.coupon.domain.repositories.MemberRepository;
import com.flab.goodchoice.coupon.dto.CouponInfoResponse;
import com.flab.goodchoice.coupon.dto.CouponPublishRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
class CouponUseSynchronismTest {
    @Autowired
    protected MockMvc mvc;
    @Autowired
    private CouponUseService couponUseService;
    @Autowired
    private CouponQueryService couponQueryService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private CouponPublishRepository couponPublishRepository;

    @Autowired
    protected ObjectMapper objectMapper;

    final UUID couponToken = UUID.fromString("26ac301b-9833-4a1d-8f98-ae11ca8d7a80");
    final int couponCount = 100;

    @DisplayName("쿠폰 선착순 발급 동시성 테스트")
    @Test
    @Transactional
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

        CouponInfoResponse response = couponQueryService.getCouponDetail(couponToken);
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
}
