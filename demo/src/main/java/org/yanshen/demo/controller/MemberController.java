package org.yanshen.demo.controller;

import org.yanshen.demo.vo.MemberVo;
import org.yanshen.demo.vo.Response;
import org.yanshen.springframework.formatter.HashidsFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yanshen
 * Created by yanshen on 2018/12/24.
 */
@RestController
@RequestMapping("/members")
public class MemberController {

    private Logger logger = LoggerFactory.getLogger(MemberController.class);

    private List<MemberVo> memberVos;

    @PostConstruct
    public void init() {
        memberVos = new ArrayList<>();
        MemberVo zhangsan = new MemberVo();
        zhangsan.setMemberId(1);
        zhangsan.setLoginName("zhangsan");
        zhangsan.setNickName("张三");
        zhangsan.setPassword("123456");
        memberVos.add(zhangsan);

        MemberVo lisi = new MemberVo();
        lisi.setMemberId(2);
        lisi.setLoginName("lisi");
        lisi.setNickName("李四");
        lisi.setPassword("123456");
        memberVos.add(lisi);
    }

    @GetMapping
    public Response findMemberAll() {
        logger.info("查询会员列表");
        List<MemberVo> memberVos = this.memberVos;
        return new Response(0, memberVos);
    }

    @PostMapping
    public Response save(@RequestBody MemberVo memberVo) {
        logger.info("添加会员 memberId=" + memberVo.getMemberId());
        print(memberVo);
        return new Response(0, "success");
    }

    @GetMapping("/query")
    public Response query(MemberVo memberVo) {
        logger.info("查询会员, memberId=" + memberVo.getMemberId());
        for (MemberVo item: memberVos) {
            if (item.getMemberId() == memberVo.getMemberId()) {
                return new Response(0, item);
            }
        }
        return new Response(0, null);
    }

    @GetMapping("/{memberId}")
    public Response getByMemberId(@HashidsFormat @PathVariable Integer memberId) {
        logger.info("查询会员 by getByMemberId, memberId=" + memberId);
        for (MemberVo item: memberVos) {
            if (item.getMemberId() == memberId) {
                return new Response(0, item);
            }
        }
        return new Response(0, null);
    }

    @GetMapping("/get")
    public Response get(@HashidsFormat @RequestParam Integer memberId) {
        logger.info("查询会员 by get, memberId=" + memberId);
        for (MemberVo item: memberVos) {
            if (item.getMemberId() == memberId) {
                return new Response(0, item);
            }
        }
        return new Response(0, null);
    }

    private void print(MemberVo memberVo) {
        logger.info("member info\n" + memberVo.toString());
    }
}
