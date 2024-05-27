package br.com.fiap.locawebmailapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.fiap.locawebmailapp.model.Email
import br.com.fiap.locawebmailapp.model.EmailComAlteracao
import br.com.fiap.locawebmailapp.model.Pasta

@Dao
interface EmailDao {
    @Insert
    fun criarEmail(email: Email): Long
    @Update
    fun atualizarEmail(email: Email)
    @Delete
    fun excluirEmail(email: Email)

    @Query("SELECT DISTINCT * FROM T_LCW_EMAIL INNER JOIN T_LCW_ALTERACAO ON T_LCW_EMAIL.id_email = T_LCW_ALTERACAO.alt_id_email WHERE editavel = 0 AND arquivado = 0 AND excluido = 0 AND spam = 0")
    fun listarTodosEmails(): List<EmailComAlteracao>


    @Query("SELECT * FROM T_LCW_EMAIL INNER JOIN T_LCW_ALTERACAO ON T_LCW_EMAIL.id_email = T_LCW_ALTERACAO.alt_id_email where T_LCW_ALTERACAO.alt_id_usuario = :id_usuario AND remetente = :remetente AND editavel = 0 AND arquivado = 0 AND excluido = 0 AND spam = 0")
    fun listarEmailsEnviadosPorRemetente(remetente: String, id_usuario: Long): List<EmailComAlteracao>


    @Query("SELECT * FROM T_LCW_EMAIL INNER JOIN T_LCW_ALTERACAO ON T_LCW_EMAIL.id_email = T_LCW_ALTERACAO.alt_id_email where T_LCW_ALTERACAO.alt_id_usuario = :id_usuario AND editavel = 0 AND arquivado = 0 AND excluido = 0 AND spam = 0 AND id_pasta IS NULL")
    fun listarEmailsPorDestinatario(id_usuario: Long): List<EmailComAlteracao>

    @Query("SELECT * FROM T_LCW_EMAIL INNER JOIN T_LCW_ALTERACAO ON T_LCW_EMAIL.id_email = T_LCW_ALTERACAO.alt_id_email where T_LCW_ALTERACAO.alt_id_usuario = :id_usuario AND editavel = 0")
    fun listarEmailsAi(id_usuario: Long): List<EmailComAlteracao>



    @Query("SELECT * FROM T_LCW_EMAIL where remetente = :remetente AND editavel = 1")
    fun listarEmailsEditaveisPorRemetente(remetente: String): List<Email>

    @Query("SELECT * FROM T_LCW_EMAIL where id_email = :id_email")
    fun listarEmailPorId(id_email: Long): Email


    @Query("SELECT * FROM T_LCW_EMAIL INNER JOIN T_LCW_ALTERACAO ON T_LCW_EMAIL.id_email = T_LCW_ALTERACAO.alt_id_email where T_LCW_ALTERACAO.alt_id_usuario = :id_usuario AND importante = 1 AND editavel = 0 AND arquivado = 0 AND excluido = 0 AND spam = 0")
    fun listarEmailsImportantesPorIdUsuario(id_usuario: Long): List<EmailComAlteracao>

    @Query("SELECT * FROM T_LCW_EMAIL INNER JOIN T_LCW_ALTERACAO ON T_LCW_EMAIL.id_email = T_LCW_ALTERACAO.alt_id_email where T_LCW_ALTERACAO.alt_id_usuario = :id_usuario AND agenda_atrelada = 1 AND editavel = 0 AND arquivado = 0 AND excluido = 0 AND spam = 0")
    fun listarEmailsSociaisPorIdUsuario(id_usuario: Long): List<EmailComAlteracao>

    @Query("SELECT * FROM T_LCW_EMAIL INNER JOIN T_LCW_ALTERACAO ON T_LCW_EMAIL.id_email = T_LCW_ALTERACAO.alt_id_email where T_LCW_ALTERACAO.alt_id_usuario = :id_usuario AND arquivado = 1 AND excluido = 0 AND spam = 0 AND editavel = 0")
    fun listarEmailsArquivadosPorIdUsuario(id_usuario: Long): List<EmailComAlteracao>

    @Query("SELECT * FROM T_LCW_EMAIL INNER JOIN T_LCW_ALTERACAO ON T_LCW_EMAIL.id_email = T_LCW_ALTERACAO.alt_id_email where T_LCW_ALTERACAO.alt_id_usuario = :id_usuario AND spam = 1 AND editavel = 0 AND excluido = 0")
    fun listarEmailsSpamPorIdUsuario(id_usuario: Long): List<EmailComAlteracao>

    @Query("SELECT * FROM T_LCW_EMAIL INNER JOIN T_LCW_ALTERACAO ON T_LCW_EMAIL.id_email = T_LCW_ALTERACAO.alt_id_email where T_LCW_ALTERACAO.alt_id_usuario = :id_usuario AND excluido = 1 AND editavel = 0")
    fun listarEmailsLixeiraPorIdUsuario(id_usuario: Long): List<EmailComAlteracao>

    @Query("SELECT * FROM T_LCW_EMAIL INNER JOIN T_LCW_ALTERACAO ON T_LCW_EMAIL.id_email = T_LCW_ALTERACAO.alt_id_email where T_LCW_ALTERACAO.alt_id_usuario = :id_usuario AND id_pasta = :id_pasta AND arquivado = 0 AND excluido = 0 AND spam = 0 AND editavel = 0")
    fun listarEmailsPorPastaEIdUsuario(id_usuario: Long, id_pasta: Long): List<EmailComAlteracao>
}