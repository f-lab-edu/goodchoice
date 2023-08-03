package com.flab.goodchoiceapi.coupon.api;

import com.flab.goodchoiceapi.common.response.GoodChoiceCommonResponse;
import com.flab.goodchoiceapi.coupon.application.CouponIssueFacade;
import com.flab.goodchoiceapi.coupon.dto.CouponIssueRequest;
import com.flab.goodchoicecoupon.exception.CouponError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/coupons")
@RestController
public class CouponIssueController {

    private final CouponIssueFacade couponIssueFacade;

    public CouponIssueController(CouponIssueFacade couponIssueFacade) {
        this.couponIssueFacade = couponIssueFacade;
    }

    @PostMapping("/issue")
    public GoodChoiceCommonResponse<Void> createCouponIssue(@RequestBody CouponIssueRequest couponIssueRequest) {
        boolean result = couponIssueFacade.couponIssue(couponIssueRequest.memberId(), couponIssueRequest.couponToken());
        if (result) {
            return GoodChoiceCommonResponse.success("쿠폰 등록이 성공하였습니다.");
        }

        return GoodChoiceCommonResponse.fail(CouponError.FAIL_CREATE_COUPON.getErrorCode(), CouponError.FAIL_CREATE_COUPON.getMessage());
    }

    @PostMapping("/issue/redisson")
    public GoodChoiceCommonResponse<Void> createCouponIssueRedissonAop(@RequestBody CouponIssueRequest couponIssueRequest) {
        boolean result = couponIssueFacade.couponIssueRedissonAop(couponIssueRequest.memberId(), couponIssueRequest.couponToken());
        if (result) {
            return GoodChoiceCommonResponse.success("쿠폰 등록이 성공하였습니다.");
        }

        return GoodChoiceCommonResponse.fail(CouponError.FAIL_CREATE_COUPON.getErrorCode(), CouponError.FAIL_CREATE_COUPON.getMessage());
    }
}
