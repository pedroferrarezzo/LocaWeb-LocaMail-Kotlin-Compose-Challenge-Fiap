package br.com.fiap.locawebmailapp.model

import androidx.room.Embedded


data class EmailComAlteracao(
        @Embedded
        val email: Email,

        @Embedded
        val alteracao: Alteracao
)