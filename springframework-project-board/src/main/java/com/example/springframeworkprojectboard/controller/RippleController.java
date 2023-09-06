package com.example.springframeworkprojectboard.controller;

import com.example.springframeworkprojectboard.dto.RippleDto;
import com.example.springframeworkprojectboard.service.RippleServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/ripple")
@RequiredArgsConstructor
@Log4j2
public class RippleController {

    private final RippleServiceImpl rippleService;

    @PostMapping("/ripple_register")
    public @ResponseBody String createRipple(@ModelAttribute RippleDto rippleDto, HttpServletRequest req) {
        log.info("RippleController: POST - createRipple()");
        HttpSession session = req.getSession();
        JSONObject json = new JSONObject();

        rippleDto.setMemberId((Long) session.getAttribute("sessionMemberId"));
        rippleDto.setMemberName((String) session.getAttribute("sessionMemberAccount"));
        rippleDto.setIp(req.getRemoteAddr());
        try {
            if (session.getAttribute("sessionMemberAccount") != null) {
                rippleService.registerRipple(rippleDto);

                json.put("result", "true");
                json.put("message", "댓글이 성공적으로 등록되었습니다.");
            } else {
                json.put("result", "false");
                json.put("message", "로그인이 필요합니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result", "false");
            json.put("message", "댓글 등록에 실패했습니다.");
        }

        return json.toJSONString();
    }

    @GetMapping("/ripple_list")
    public @ResponseBody String ListRipples(Long boardId, HttpServletRequest req) {
        log.info("RippleController: GET - ListRipples()");
        JSONArray jsonArray = new JSONArray();
        try {
            List<RippleDto> rippleList = rippleService.getRippleList(boardId);
            for (RippleDto ripple : rippleList) {
                JSONObject json = new JSONObject();

                json.put("id", ripple.getId());
                json.put("boardId", ripple.getBoardId());
                json.put("memberName", ripple.getMemberName());
                json.put("content", ripple.getContent());
                json.put("ip", ripple.getIp());
                json.put("createAt", ripple.getCreatedAt());
                if (ripple.getMemberName().equals((String) req.getSession().getAttribute("sessionMemberAccount"))) {
                    json.put("login", true);
                } else {
                    json.put("login", false);
                }
                jsonArray.add(json);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("에러발생: ", e);
        }
        return jsonArray.toJSONString();
    }

    @PostMapping("/ripple_remove")
    public @ResponseBody String removeRipple(Long rippleId, HttpServletRequest req) {
        log.info("RippleController: POST - removeRipple()");
        HttpSession session = req.getSession();
        JSONObject json = new JSONObject();
        try {
            if (session.getAttribute("sessionMemberAccount") != null) {
                rippleService.removeRipple(rippleId);
                json.put("result", "true");
                json.put("message", "댓글이 성공적으로 삭제되었습니다.");
            } else {
                json.put("result", "false");
                json.put("message", "로그인이 필요합니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result", "false");
            json.put("message", "댓글 삭제에 실패했습니다.");
        }
        return json.toJSONString();
    }

}
