package com.tminto.controller;


import com.tminto.common.R;
import com.tminto.domain.CrmBanner;
import com.tminto.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Tminto
 * @since 2022-10-03
 */
@RestController
@RequestMapping("/educms/banner")
public class CrmBannerController {

    @Autowired
    private CrmBannerService crmBannerService;

    @Cacheable(key = "'banner'",value = "list")
    @GetMapping("/list")
    public R getAllBanner() {
        List<CrmBanner> banners = crmBannerService.list(null);
        return R.ok().data("list", banners);
    }

}

