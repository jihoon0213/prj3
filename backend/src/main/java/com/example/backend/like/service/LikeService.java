package com.example.backend.like.service;

import com.example.backend.board.entity.Board;
import com.example.backend.board.repository.BoardRepository;
import com.example.backend.like.dto.BoardLikeDto;
import com.example.backend.like.dto.LikeForm;
import com.example.backend.like.entity.BoardLike;
import com.example.backend.like.entity.BoardLikeId;
import com.example.backend.like.repository.BoardLikeRepository;
import com.example.backend.member.entity.Member;
import com.example.backend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeService {

    private final BoardLikeRepository boardLikeRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public void update(LikeForm likeForm, Authentication authentication) {
        // 로그인 안했으면 exception
        if (authentication == null) {
            throw new RuntimeException("로그인 하세요.");
        }
        // 게시물 번호와 로그인이메일로 like 데이터 얻어서
        var boardLike = boardLikeRepository
                .findByBoardIdAndMemberEmail(likeForm.getBoardId(), authentication.getName());

        // 있으면 지우기
        if (boardLike.isPresent()) {
            boardLikeRepository.delete(boardLike.get());
        } else {
            // 없으면 insert
            BoardLike boardLikeEntity = new BoardLike();
            Board board = boardRepository.findById(likeForm.getBoardId()).get();
            Member member = memberRepository.findById(authentication.getName()).get();
            boardLikeEntity.setBoard(board);
            boardLikeEntity.setMember(member);

            BoardLikeId boardLikeId = new BoardLikeId();
            boardLikeId.setBoardId(likeForm.getBoardId());
            boardLikeId.setMemberEmail(authentication.getName());
            boardLikeEntity.setId(boardLikeId);
            boardLikeRepository.save(boardLikeEntity);
        }


    }

    public BoardLikeDto get(Integer boardId, Authentication authentication) {
        Long count = boardLikeRepository.countByBoardId(boardId);
        Boolean liked = false;
        if (authentication != null) {
            var row = boardLikeRepository
                    .findByBoardIdAndMemberEmail(boardId, authentication.getName());
            liked = row.isPresent();
        }
        BoardLikeDto boardLikeDto = new BoardLikeDto();
        boardLikeDto.setCount(count);
        boardLikeDto.setLiked(liked);

        return boardLikeDto;
    }
}