package mouda.util.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mouda.util.dto.BoardSubmitRequest;
import mouda.util.service.BoardService;

@Controller
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;

	@PostMapping("/boards")
	public ResponseEntity<Void> submitBoard(@RequestBody @Valid BoardSubmitRequest request) {
		Long savedId = boardService.submitPost(request);

		return ResponseEntity.created(URI.create("/boards" + savedId)).build();
	}
}
