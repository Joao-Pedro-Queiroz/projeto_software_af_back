package br.insper.af.feedback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedbacks")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RetornarFeedbackDTO cadastrarFeedback(@RequestBody CadastrarFeedbackDTO dto, @AuthenticationPrincipal Jwt jwt) {
        String emailUsuario = jwt.getClaimAsString("https://musica-insper.com/email");
        return feedbackService.cadastrarFeedback(dto, emailUsuario);
    }

    @GetMapping
    public List<Feedback> listarFeedbacks() {
        return feedbackService.listarFeedbacks();
    }

    @GetMapping("/{id}")
    public Feedback buscarFeedback(@PathVariable String id) {
        return feedbackService.buscarFeedback(id);
    }

    @DeleteMapping("/{id}")
    public void excluirFeedback(@PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
        List<String> roles = jwt.getClaimAsStringList("https://musica-insper.com/roles");
        feedbackService.excluirFeedback(id, roles);
    }
}
