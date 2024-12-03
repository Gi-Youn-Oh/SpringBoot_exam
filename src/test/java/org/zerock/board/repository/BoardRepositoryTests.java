package org.zerock.board.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;
import org.zerock.board.service.BoardService;
import org.zerock.board.dto.BoardDTO;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@EnableJpaAuditing
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;
    
    @Test
    @Transactional
    @Rollback(false) // HeidiSQL에서 register확인용 원래는 Transactional에 의해 Rollback됨
    public void testRegisterAndRemove() {
        // 테스트 데이터를 먼저 등록
        BoardDTO boardDTO = BoardDTO.builder()
                .title("Delete Test Title")
                .content("Delete Test Content")
                .writerEmail("user100@aaa.com") // 고정 test member writerEmail사용
                .build();
        // 등록
        Long bno = boardService.register(boardDTO);
        assertThat(bno).isNotNull();
        System.out.println("Successfully test data register: " + bno); // 정상 등록 확인용 콘솔

        // 등록한 게시글 삭제
        boardService.remove(bno);

        // 게시글이 삭제 확인
        Optional<Board> result = boardRepository.findById(bno);
        assertThat(result).isEmpty(); // 삭제된 경우 비어 있어야 함
        System.out.println("Successfully deleted Board ID: " + bno); // 정상 삭제 확인용 콘솔
    }

}
