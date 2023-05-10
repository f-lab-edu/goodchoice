package com.flab.goodchoice.coupon.api;

import com.flab.goodchoice.coupon.application.CouponService;
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

    private final CouponService couponService;

    public CouponController(final CouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping
    public UUID create(@RequestBody final CreateCouponRequest createCouponRequest) {
        validation(createCouponRequest.couponName(), createCouponRequest.stock());

        return couponService.create(createCouponRequest.couponName(), createCouponRequest.stock());
    }

    @GetMapping
    public List<CouponInfoResponse> getAllCoupons() {
        return couponService.getAllCoupons();
    }

    @GetMapping("/{couponToken}")
    public CouponInfoResponse getCouponDetail(@PathVariable final UUID couponToken) {
        return couponService.getCouponDetail(couponToken);
    }

    @PutMapping("/{couponToken}")
    public UUID modifyCoupon(@PathVariable final UUID couponToken, @RequestBody final ModifyCouponRequest modifyCouponRequest) {
        validation(modifyCouponRequest.couponName(), modifyCouponRequest.stock());

        return couponService.modifyCoupon(couponToken, modifyCouponRequest.couponName(), modifyCouponRequest.stock());
    }

    private void validation(final String couponName, final int stock) {
        if (!StringUtils.hasText(couponName)) {
            throw new IllegalArgumentException("쿠폰명을 입력해주세요.");
        }

        if (stock < 0) {
            throw new IllegalArgumentException("쿠폰 갯수는 음수가 될수 없습니다.");
        }
    }

    @DeleteMapping("/{couponToken}")
    public UUID deleteCoupon(@PathVariable final UUID couponToken) {
        return couponService.deleteCoupon(couponToken);
    }
}
