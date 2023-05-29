package com.flab.goodchoice.coupon.api;

import com.flab.goodchoice.coupon.application.CouponCommandService;
import com.flab.goodchoice.coupon.application.CouponQueryService;
import com.flab.goodchoice.coupon.dto.CouponInfoResponse;
import com.flab.goodchoice.coupon.dto.CreateCouponRequest;
import com.flab.goodchoice.coupon.dto.ModifyCouponRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/coupons")
@RestController
public class CouponController {

    private final CouponQueryService couponQueryService;
    private final CouponCommandService couponCommandService;

    public CouponController(CouponQueryService couponQueryService, CouponCommandService couponCommandService) {
        this.couponQueryService = couponQueryService;
        this.couponCommandService = couponCommandService;
    }

    @PostMapping
    public UUID createCoupon(@RequestBody final CreateCouponRequest createCouponRequest) {
        validation(createCouponRequest.couponName(), createCouponRequest.stock());

        return couponCommandService.createCoupon(createCouponRequest.couponName(), createCouponRequest.stock(), createCouponRequest.couponType(), createCouponRequest.discountValue());
    }

    @GetMapping
    public List<CouponInfoResponse> getAllCoupons() {
        return couponQueryService.getAllCoupons();
    }

    @GetMapping("/{couponToken}")
    public CouponInfoResponse getCoupon(@PathVariable final UUID couponToken) {
        return couponQueryService.getCoupon(couponToken);
    }

    @PutMapping("/{couponToken}")
    public UUID modifyCoupon(@PathVariable final UUID couponToken, @RequestBody final ModifyCouponRequest modifyCouponRequest) {
        validation(modifyCouponRequest.couponName(), modifyCouponRequest.stock());

        return couponCommandService.modifyCoupon(couponToken, modifyCouponRequest.couponName(), modifyCouponRequest.stock());
    }

    @DeleteMapping("/{couponToken}")
    public UUID removeCoupon(@PathVariable final UUID couponToken) {
        return couponCommandService.removeCoupon(couponToken);
    }

    private void validation(final String couponName, final int stock) {
        if (!StringUtils.hasText(couponName)) {
            throw new IllegalArgumentException("쿠폰명을 입력해주세요.");
        }

        if (stock < 0) {
            throw new IllegalArgumentException("쿠폰 갯수는 음수가 될수 없습니다.");
        }
    }
}
