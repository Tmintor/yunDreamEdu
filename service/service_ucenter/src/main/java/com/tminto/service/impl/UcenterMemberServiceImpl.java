package com.tminto.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tminto.domain.RegisterVo;
import com.tminto.domain.UcenterMember;
import com.tminto.mapper.UcenterMemberMapper;
import com.tminto.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tminto.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author Tminto
 * @since 2022-10-05
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String login(UcenterMember ucenterMember) {

        String mobile = ucenterMember.getMobile();
        String password = ucenterMember.getPassword();
        String md5 = DigestUtils.md5DigestAsHex(password.getBytes());

        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new RuntimeException("账号或密码不许为空");
        }

        QueryWrapper<UcenterMember> ucenterMemberQueryWrapper = new QueryWrapper<>();
        ucenterMemberQueryWrapper.eq("mobile", mobile);
        ucenterMemberQueryWrapper.eq("password", md5);
        UcenterMember user = baseMapper.selectOne(ucenterMemberQueryWrapper);

        if (StringUtils.isEmpty(user)) {
            throw new RuntimeException("账号或密码错误");
        }

        if (user.getIsDisabled()) {
            throw new RuntimeException("用户被禁用");
        }

        return JwtUtils.getJwtToken(user.getId(), user.getNickname());
    }

    @Override
    public void register(RegisterVo registerVo) {

        //获取注册基本信息
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();

        //判断是否为空
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(mobile) || StringUtils.isEmpty(nickname) || StringUtils.isEmpty(password)) {
            throw new RuntimeException("注册失败");
        }
        //判断验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);

        if (!code.equals(redisCode)) {
            throw new RuntimeException("验证码错误");
        }

        //判断手机号在数据库中是否存在
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            throw new RuntimeException("手机号已存在");
        }

        //将数据添加到数据库
        UcenterMember ucenterMember = new UcenterMember();
        ucenterMember.setMobile(mobile);
        ucenterMember.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        ucenterMember.setNickname(nickname);
        ucenterMember.setIsDisabled(false);//用户不禁用
        ucenterMember.setAvatar("https://edu-longyang.oss-cn-beijing.aliyuncs.com/fa104ef58c4e5bc4270d911da1d1b34d.jpg");
        baseMapper.insert(ucenterMember);

    }
}
