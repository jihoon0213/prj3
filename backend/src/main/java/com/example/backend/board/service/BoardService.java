package com.example.backend.board.service;

import com.example.backend.board.dto.*;
import com.example.backend.board.entity.Board;
import com.example.backend.board.entity.BoardFile;
import com.example.backend.board.entity.BoardFileId;
import com.example.backend.board.repository.BoardFileRepository;
import com.example.backend.board.repository.BoardRepository;
import com.example.backend.comment.repository.CommentRepository;
import com.example.backend.like.repository.BoardLikeRepository;
import com.example.backend.member.entity.Member;
import com.example.backend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final BoardFileRepository boardFileRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final S3Client s3Client;

    @Value("${image.prefix}")
    private String imagePrefix;
    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    private void deleteFile(String objectKey) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest
                .builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();

        s3Client.deleteObject(deleteObjectRequest);
    }

    private void uploadFile(MultipartFile file, String objectKey) {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest
                    .builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .acl(ObjectCannedACL.PUBLIC_READ)
                    .build();

            s3Client
                    .putObject(putObjectRequest,
                            RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("파일 전송이 실패하였습니다.");
        }
    }

    public void add(BoardAddForm dto, Authentication authentication) {
        if (authentication == null) {
            throw new RuntimeException("권한이 없습니다.");
        }

        // entity에 dto의 값 들 옮겨 담고
        Board board = new Board();
        board.setTitle(dto.getTitle());
        board.setContent(dto.getContent());

        Member author = memberRepository.findById(authentication.getName()).get();
        board.setAuthor(author);

        // repository에 save 실행
        boardRepository.save(board);

        // file 저장하기
        saveFiles(board, dto.getFiles());

    }

    private void saveFiles(Board board, List<MultipartFile> files) {
        if (files != null && files.size() > 0) {
            for (MultipartFile file : files) {
                if (file != null && file.getSize() > 0) {
                    // board_file 테이블에 새 레코드 입력
                    BoardFile boardFile = new BoardFile();
                    // entity 내용 채우기
                    BoardFileId id = new BoardFileId();
                    id.setBoardId(board.getId());
                    id.setName(file.getOriginalFilename());
                    boardFile.setBoard(board);
                    boardFile.setId(id);

                    // repository로 저장
                    boardFileRepository.save(boardFile);

                    // 실제 파일 aws s3 에 저장
                    String objectKey = "prj3/board/" + board.getId() + "/" + file.getOriginalFilename();
                    uploadFile(file, objectKey);

                }
            }
        }
    }

    public boolean validate(BoardDto dto) {
        if (dto.getTitle() == null || dto.getTitle().trim().isBlank()) {
            return false;
        }

        if (dto.getContent() == null || dto.getContent().trim().isBlank()) {
            return false;
        }

        return true;
    }

    public Map<String, Object> list(String keyword, Integer pageNumber) {
//        return boardRepository.findAllByOrderByIdDesc();
        Page<BoardListDto> boardListDtoPage
                = boardRepository.findAllBy(keyword, PageRequest.of(pageNumber - 1, 10));

        int totalPages = boardListDtoPage.getTotalPages(); // 마지막 페이지
        int rightPageNumber = ((pageNumber - 1) / 10 + 1) * 10;
        int leftPageNumber = rightPageNumber - 9;
        rightPageNumber = Math.min(rightPageNumber, totalPages);
        leftPageNumber = Math.max(leftPageNumber, 1);

        var pageInfo = Map.of("totalPages", totalPages,
                "rightPageNumber", rightPageNumber,
                "leftPageNumber", leftPageNumber,
                "currentPageNumber", pageNumber);

        return Map.of("pageInfo", pageInfo,
                "boardList", boardListDtoPage.getContent());
    }

    public BoardDto getBoardById(Integer id) {
        BoardDto board = boardRepository.findBoardById(id);
        List<BoardFile> fileList = boardFileRepository.findByBoardId(id);
        List<BoardFileDto> files = new ArrayList<>();
        for (BoardFile boardFile : fileList) {
            BoardFileDto fileDto = new BoardFileDto();
            fileDto.setName(boardFile.getId().getName());
            fileDto.setPath(imagePrefix + "prj3/board/" + id + "/" + boardFile.getId().getName());
            files.add(fileDto);
        }

        board.setFiles(files);

//        BoardDto boardDto = new BoardDto();
//        boardDto.setId(board.getId());
//        boardDto.setTitle(board.getTitle());
//        boardDto.setContent(board.getContent());
//        boardDto.setAuthor(board.getAuthor());
//        boardDto.setInsertedAt(board.getInsertedAt());

        return board;

    }

    public void deleteById(Integer id, Authentication authentication) {
        if (authentication == null) {
            throw new RuntimeException("권한이 없습니다.");
        }

        Board db = boardRepository.findById(id).get();

        if (db.getAuthor().getEmail().equals(authentication.getName())) {
            // 좋아요
            boardLikeRepository.deleteByBoard(db);

            // s3의 파일
            ///  1. 파일 목록 얻고
            List<String> fileNames = boardFileRepository.listFileNameByBoard(db);
            /// 2. 실제 파일 지우기
            for (String fileName : fileNames) {
                String objectKey = "prj3/board/" + db.getId() + "/" + fileName;
                deleteFile(objectKey);
            }


            // 파일
            boardFileRepository.deleteByBoard(db);

            // 댓글
            commentRepository.deleteByBoardId(id);
            boardRepository.deleteById(id);
        } else {
            throw new RuntimeException("권한이 없습니다.");
        }
    }

    public void update(BoardUpdateForm boardDto, Authentication authentication) {
        if (authentication == null) {
            throw new RuntimeException("권한이 없습니다.");
        }

        // 조회
        Board db = boardRepository.findById(boardDto.getId()).get();

        if (db.getAuthor().getEmail().equals(authentication.getName())) {
            // 변경
            db.setTitle(boardDto.getTitle());
            db.setContent(boardDto.getContent());

            // 파일 지우기
            deleteFiles(db, boardDto.getDeleteFiles());

            // 파일 추가
            saveFiles(db, boardDto.getFiles());


            // 저장
            boardRepository.save(db);

        } else {
            throw new RuntimeException("권한이 없습니다.");
        }


    }

    private void deleteFiles(Board db, String[] deleteFiles) {
        if (deleteFiles != null && deleteFiles.length > 0) {
            for (String file : deleteFiles) {
                // board_file table 의 record 지우고
                BoardFileId boardFileId = new BoardFileId();
                boardFileId.setBoardId(db.getId());
                boardFileId.setName(file);
                boardFileRepository.deleteById(boardFileId);

                // s3의 파일 지우기
                String objectKey = "prj3/board/" + db.getId() + "/" + file;
                deleteFile(objectKey);


            }
        }
    }

    public boolean validateForAdd(BoardAddForm dto) {
        if (dto.getTitle() == null || dto.getTitle().trim().isBlank()) {
            return false;
        }

        if (dto.getContent() == null || dto.getContent().trim().isBlank()) {
            return false;
        }

        return true;
    }

    public boolean validateForUpdate(BoardUpdateForm boardDto) {
        if (boardDto.getTitle() == null || boardDto.getTitle().trim().isBlank()) {
            return false;
        }

        if (boardDto.getContent() == null || boardDto.getContent().trim().isBlank()) {
            return false;
        }

        return true;
    }
}