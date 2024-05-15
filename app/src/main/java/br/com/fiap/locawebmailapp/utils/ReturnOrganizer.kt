package br.com.fiap.locawebmailapp.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import br.com.fiap.locawebmailapp.R

@Composable
fun returnOrganizer(organizer: String): String {
    if (organizer.equals("1")) {
        return stringResource(id = R.string.calendar_organizer_you)
    }

    else {
        return organizer
    }
}