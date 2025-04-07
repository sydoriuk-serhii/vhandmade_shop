package org.vhandmade_shop.app.components.widgets

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.*
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.*
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.H3
import org.vhandmade_shop.app.models.Product

@Composable
fun ProductCard(product: Product, modifier: Modifier = Modifier) {
    Link(
        path = "/product/${product.id}",
        modifier = modifier
            .textDecorationLine(TextDecorationLine.None)
            .borderRadius(8.px)
            .boxShadow(offsetX = 0.px, offsetY = 2.px, blurRadius = 4.px, color = rgba(0,0,0, 0.1))
            .overflow(Overflow.Hidden) 
            .width(250.px)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                src = product.imageUrls.firstOrNull() ?: "/placeholder.png",
                description = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.px)
                    .objectFit(ObjectFit.Cover)
            )
            
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.px),
                verticalArrangement = Arrangement.spacedBy(8.px)
            ) {
                // Назва товару
                H3(attrs = Modifier.margin(topBottom = 0.px).fontSize(1.1.em).toAttrs()) {
                    SpanText(product.name)
                }
                // Ціна товару
                SpanText(
                    text = "${product.price.asDynamic().toFixed(2)} грн",
                    modifier = Modifier.fontWeight(FontWeight.Bold).fontSize(1.2.em)
                )
                // Можна додати кнопку "В кошик" або рейтинг тут
            }
        }
    }
}