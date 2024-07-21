package mouda.util.domain.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

import mouda.util.domain.Board;
import mouda.util.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	Post findAllByAuthor(String author);

	Post findAllByBoard(Board board);

	Post findAllByCreatedAtBetween(LocalDateTime startFrom, LocalDateTime endAt);
}
