package com.tminto.service;

import com.tminto.domain.RegisterVo;
import com.tminto.domain.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author Tminto
 * @since 2022-10-05
 */
@Mapper
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember ucenterMember);

    void register(RegisterVo registerVo);
}
