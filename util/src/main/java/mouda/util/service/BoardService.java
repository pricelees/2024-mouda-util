package mouda.util.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mouda.util.domain.Board;
import mouda.util.domain.Post;
import mouda.util.domain.repository.BoardRepository;
import mouda.util.domain.repository.PostRepository;
import mouda.util.dto.BoardSubmitRequest;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository boardRepository;
	private final PostRepository postRepository;

	public Long submitPost(BoardSubmitRequest request) {
		Board board = boardRepository.findByTeamName(request.team());
		Post post = Post.builder()
			.board(board)
			.author(request.author())
			.content(request.content())
			.createdAt(LocalDateTime.now())
			.build();

		return postRepository.save(post).getId();
	}
}
