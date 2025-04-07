package org.vhandmade_shop.app.pages

import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.StyleVariable
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.dom.Div
import org.vhandmade_shop.app.components.layouts.PageLayout
import com.varabyte.kobweb.compose.css.JustifyItems
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.vhandmade_shop.app.components.widgets.ProductCard
import org.vhandmade_shop.app.models.Product
import org.vhandmade_shop.app.api.MockProductService
import kotlinx.coroutines.launch

val HeroContainerStyle = CssStyle {
    base { Modifier.fillMaxWidth().gap(2.cssRem) }
    Breakpoint.MD { Modifier.margin { top(20.vh) } }
}

val HomeGridStyle = CssStyle.base {
    Modifier
        .gap(0.5.cssRem)
        .width(70.cssRem)
        .height(18.cssRem)
}

private val GridCellColorVar by StyleVariable<Color>()
val HomeGridCellStyle = CssStyle.base {
    Modifier
        .backgroundColor(GridCellColorVar.value())
        .boxShadow(blurRadius = 0.6.cssRem, color = GridCellColorVar.value())
        .borderRadius(1.cssRem)
}

@Composable
private fun GridCell(color: Color, row: Int, column: Int, width: Int? = null, height: Int? = null) {
    Div(
        HomeGridCellStyle.toModifier()
            .setVariable(GridCellColorVar, color)
            .gridItem(row, column, width, height)
            .toAttrs()
    )
}

@Page
@Composable
fun HomePage() {
    PageLayout("Головна") {
        var products by remember { mutableStateOf<List<Product>>(emptyList()) }
        val scope = rememberCoroutineScope() // Для запуску корутин
        val productService = remember { MockProductService() }

        LaunchedEffect(Unit) {
            scope.launch {
                products = productService.getFeaturedProducts()
            }
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(top = 2.cssRem, bottom = 2.cssRem),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            H2(attrs = Modifier.margin(bottom = 1.5.cssRem).toAttrs()) {
                SpanText("Рекомендовані товари")
            }

            if (products.isNotEmpty()) {
                SimpleGrid(
                    numColumns = numColumns(base = 1, sm = 2, md = 3, lg = 4),
                    modifier = Modifier.gap(1.cssRem)
                        .width(90.vw)
                        .justifyItems(JustifyItems.Center)
                ) {
                    products.forEach { product ->
                        ProductCard(product)
                    }
                }
            } else {
                SpanText("Завантаження товарів...")
            }

            Spacer()
        }
    }
}