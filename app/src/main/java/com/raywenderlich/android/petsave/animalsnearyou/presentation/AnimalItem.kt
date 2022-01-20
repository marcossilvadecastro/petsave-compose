package com.raywenderlich.android.petsave.animalsnearyou.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.raywenderlich.android.petsave.R
import com.raywenderlich.android.petsave.common.presentation.model.UIAnimal


@Composable
fun AnimalItem(animalUI: UIAnimal) {

    Surface(
        shape = MaterialTheme.shapes.medium,
        elevation = 1.dp,
        modifier = Modifier
            .padding(all = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .border(1.5.dp, MaterialTheme.colors.primary),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.dog_placeholder),
                contentDescription = "Animal to adopt"
            )
            Text(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .padding(horizontal = 4.dp),
                text = animalUI.name,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.primaryVariant
            )
        }
    }
}

@Preview
@Composable
fun AnimalItemPreview() {
    AnimalItem(
        animalUI = UIAnimal(
            1L,
            "Caramelo",
            "https://dl5zpyw5k3jeb.cloudfront.net//photos//pets//54298546//1//?bust=1642629363\\u0026width=300"
        )
    )
}