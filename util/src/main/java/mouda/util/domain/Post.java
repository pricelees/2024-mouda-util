package mouda.util.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
public class Post {

	@Id
	@GeneratedValue
	private Long id;

	private String author;
	private String content;
	private boolean isPublic;

	@ManyToOne
	@JoinColumn(name = "board_id")
	private Board board;

	private LocalDateTime createdAt;

	@Builder
	public Post(String author, String content, boolean isPublic, Board board, LocalDateTime createdAt) {
		this.author = author;
		this.content = content;
		this.isPublic = isPublic;
		this.board = board;
		this.createdAt = createdAt;
	}
}
