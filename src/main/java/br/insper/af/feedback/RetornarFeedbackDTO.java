package br.insper.af.feedback;

public record RetornarFeedbackDTO(String id, String titulo, String descricao, Integer nota, String emailUsuario) {
}
