package com.faire.faireshop.presentation.products.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.faire.faireshop.domain.Currency
import com.faire.faireshop.domain.Price
import com.faire.faireshop.domain.Product
import com.faire.faireshop.ui.theme.FaireShopTheme
import com.faire.faireshop.ui.theme.Typography

@Composable
fun ProductItem(item: Product, modifier: Modifier = Modifier, showDivider: Boolean = true) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .height(60.dp)
                    .width(60.dp)
                    .align(CenterVertically),
                model = item.imageUrl,
                contentDescription = item.name,
                contentScale = ContentScale.Fit
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp)
            ) {
                Text(
                    item.name,
                    style = Typography.h6,
                    color = MaterialTheme.colors.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 16.sp
                )
                Text(item.details, style = Typography.body1, color = MaterialTheme.colors.onSurface)
            }
            PriceText(
                modifier = Modifier
                    .align(CenterVertically)
                    .padding(start = 8.dp),
                price = item.wholeSalePrice
            )
        }
        if (showDivider) {
            Divider()
        }
    }
}

@Composable
fun PriceText(price: Price, modifier: Modifier = Modifier) {
    Text(modifier = modifier, text = "${price.currency.sign} ${price.price}")
}

@Composable
@Preview(showBackground = true)
fun ProductItemPreview() {
    FaireShopTheme {
        ProductItem(
            item = Product(
                "",
                "Shooting Arrows Notecards",
                "https://cdn.faire.com/res/hszgttpjt/image/upload/04760a693446356634eb9a41f4612632932d92a945aae3329078e928acb4e71c/1519715814.jpg",
                details = "Keep in touch with these fun cards!",
                wholeSalePrice = Price(9.25f, Currency.USD)
            )
        )
    }
}