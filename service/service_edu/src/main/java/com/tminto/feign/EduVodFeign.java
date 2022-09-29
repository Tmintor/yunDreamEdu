package com.tminto.feign;

import com.tminto.common.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 吴员外
 * @date 2022/9/28 23:41
 */
@FeignClient("service-vod")
public interface EduVodFeign {

    @DeleteMapping("/edu/vod/{id}")
    public R deleteVod(@PathVariable("id") String id);

}
