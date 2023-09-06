package com.example.springframeworkprojectboard.service;

import com.example.springframeworkprojectboard.domain.Ripple;
import com.example.springframeworkprojectboard.dto.RippleDto;
import com.example.springframeworkprojectboard.mapper.BoardMapper;
import com.example.springframeworkprojectboard.mapper.RippleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class RippleServiceImpl implements RippleService {

    private final ModelMapper mapper;
    private final RippleMapper rippleMapper;
    private final BoardMapper boardMapper;

    @Override
    public void registerRipple(RippleDto rippleDto) throws Exception {
        Ripple ripple = mapper.map(rippleDto, Ripple.class);
        log.info("RippleService: registerRipple() - registerRipple: {}", ripple);
        rippleMapper.save(ripple);
        //댓글 갯수 조회 및 업데이트
        int rippleCount = rippleMapper.findRippleCountByBoardId(ripple.getBoardId());

        Map<String, Object> params = new HashMap<>();
        params.put("rippleCnt", rippleCount);
        params.put("id", ripple.getBoardId());

        boardMapper.updateRippleCountByBoardId(params);
    }



    @Override
    public List<RippleDto> getRippleList(long boardId) throws Exception {
        log.info("RippleService: getRippleList()");
        List<Ripple> ripples = rippleMapper.findListByBoardId(boardId);
        List<RippleDto> rippleList = ripples.stream()
                .map(ripple -> mapper.map(ripple, RippleDto.class))
                .collect(Collectors.toList());

        return rippleList;
    }

    @Override
    public void removeRipple(long rippleId) throws Exception {
        log.info("RippleService: removeRipple()");
        long boardId = rippleMapper.findBoardByRippleId(rippleId);
        rippleMapper.deleteRippleByRippleId(rippleId);
        //댓글 갯수 조회 및 업데이트
        int rippleCount = rippleMapper.findRippleCountByBoardId(boardId);
//        boardMapper.updateRippleCountByBoardId(boardId, rippleCount);

    }
}
