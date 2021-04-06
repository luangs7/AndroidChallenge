package com.luan.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Shapes
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luan.common.ui.theme.Shapes


@Composable
fun ButtonView(
    modifier: Modifier = Modifier,
    label: String,
    onClickAction: () -> Unit
) {
    Button(
        onClick = { onClickAction.invoke() },
        modifier = modifier
            .padding(20.dp)
            .background(shape = RoundedCornerShape(6.dp), color = Color.Red)
    ) {
        Text(
            text = label,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}