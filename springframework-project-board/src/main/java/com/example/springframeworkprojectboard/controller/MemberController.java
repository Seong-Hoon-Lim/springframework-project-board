package com.example.springframeworkprojectboard.controller;

import com.example.springframeworkprojectboard.dto.MemberDto;
import com.example.springframeworkprojectboard.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Log4j2
public class MemberController {

    private final MemberServiceImpl memberService;

    @PostMapping(value = "/checkAccount")
    public void checkDuplicateAccount(HttpServletRequest req, HttpServletResponse resp) {
        log.info("MemberController: POST - checkDuplicateAccount()");
        resp.setContentType("application/json");
        String account = req.getParameter("account");

        try {
            String result = memberService.hasDuplicateMember(account) ? "true" : "false";
            String jsonResult = String.format("{\"result\":\"%s\"}", result);
            resp.getWriter().print(jsonResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/member_register")
    public String showRegisterForm() {
        return "member/member_register";
    }

    @PostMapping("/member_register")
    public String createMember(@ModelAttribute MemberDto memberDto,
                               HttpServletRequest req,
                               Model model) {
        log.info("MemberController: POST - createMember()");
        String year = req.getParameter("birthyy");
        String month = req.getParameterValues("birthmm")[0];
        String day = req.getParameter("birthdd");
        memberDto.setBirth(year + "/" + month + "/" + day);

        String mail1 = req.getParameter("mail1");
        String mail2 = req.getParameterValues("mail2")[0];
        memberDto.setEmail(mail1 + "@" + mail2);

        model.addAttribute("member", memberDto);
        try {
            if (!memberService.hasDuplicateMember(memberDto.getAccount())) {
                memberService.registerMember(memberDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/member/login";
    }

    @GetMapping({"/member_view", "/member_modify"})
    public void showMember(long memberId, HttpServletRequest req, Model model) {
        log.info("MemberController: GET - showMember()");
        HttpSession session = req.getSession();
        try {
            MemberDto memberDto = memberService.getMember(memberId);
            model.addAttribute("member", memberDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/member_modify")
    public String modifyMember(@ModelAttribute MemberDto memberDto,
                               HttpServletRequest req,
                               Long memberId,
                               Model model) {
        log.info("MemberController: POST - modifyMember()");
        String year = req.getParameter("birthyy");
        String month = req.getParameterValues("birthmm")[0];
        String day = req.getParameter("birthdd");
        memberDto.setBirth(year + "/" + month + "/" + day);

        String mail1 = req.getParameter("mail1");
        String mail2 = req.getParameterValues("mail2")[0];
        memberDto.setEmail(mail1 + "@" + mail2);

        model.addAttribute("member", memberDto);
        try {
            if (Objects.equals(memberId, memberDto.getId())) {
                memberService.modifyMember(memberDto);
                return "redirect:/board/board_list";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/member/member_modify?memberId=" + memberDto.getId();
    }

    @PostMapping("/member_remove")
    public String removeMember(long memberId, HttpServletRequest req) {
        log.info("MemberController: POST - removeMember()");
        try {
            memberService.removeMember(memberId);
            req.getSession().invalidate();
            return "redirect:/board/board_list";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "member/member_modify";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "member/login";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest req, Model model) {
        log.info("MemberController: POST - login()");
        try {
            String account = req.getParameter("account");
            MemberDto memberDto = memberService.getMember(account);
            String name = memberDto.getName();
            if ((String) req.getSession().getAttribute("sessionMemberAccount") == null) {
                req.getSession().setAttribute("sessionMemberAccount", account);
                req.getSession().setAttribute("sessionMemberName", name);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "member/login";
        }
        return "redirect:/board/board_list";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest req) {
        req.getSession().invalidate();
        return "redirect:/board/board_list";
    }

}
