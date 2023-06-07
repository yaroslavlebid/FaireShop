package com.faire.faireshop.presentation.products.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.faire.faireshop.R
import com.faire.faireshop.ui.theme.Typography

@Composable
fun EmptyListFullScreenMessage(modifier: Modifier = Modifier, onReloadProducts: () -> Unit) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column {
            Text(
                text = stringResource(R.string.empty_list_message),
                style = Typography.body1,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Button(
                onClick = onReloadProducts,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = stringResource(R.string.refresh))
            }
        }
    }
}