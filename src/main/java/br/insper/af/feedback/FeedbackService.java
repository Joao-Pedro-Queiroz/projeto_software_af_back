package br.insper.af.feedback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    public RetornarFeedbackDTO cadastrarFeedback(CadastrarFeedbackDTO dto, String emailUsuario) {

        Feedback feedback = new Feedback();
        feedback.setTitulo(dto.titulo());
        feedback.setDescricao(dto.descricao());
        feedback.setNota(dto.nota());
        feedback.setEmailUsuario(emailUsuario);

        feedback = feedbackRepository.save(feedback);
        return new RetornarFeedbackDTO(feedback.getId(), feedback.getTitulo(), feedback.getDescricao(), feedback.getNota(), feedback.getEmailUsuario());
    }

    public List<Feedback> listarFeedbacks() {
        return feedbackRepository.findAll();
    }

    public Feedback buscarFeedback(String id) {
        return feedbackRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void excluirFeedback(String id, List<String> roles) {
        Feedback feedback = buscarFeedback(id);

        if (!roles.contains("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }else {
            feedbackRepository.delete(feedback);
        }
    }
}
