package br.com.fiap.locawebmailapp.utils

import br.com.fiap.locawebmailapp.model.Email
import br.com.fiap.locawebmailapp.model.RespostaEmail

fun respostaEmailParaEmail(respostaEmail: RespostaEmail): Email {
    return Email(
        id_email = respostaEmail.id_email,
        id_usuario = respostaEmail.id_usuario,
        remetente = respostaEmail.remetente,
        destinatario = respostaEmail.destinatario,
        cc = respostaEmail.cc,
        cco = respostaEmail.cco,
        assunto = respostaEmail.assunto,
        corpo = respostaEmail.corpo,
        editavel = respostaEmail.editavel,
        enviado = respostaEmail.enviado,
        horario = respostaEmail.horario,
        data = respostaEmail.data
    )
}