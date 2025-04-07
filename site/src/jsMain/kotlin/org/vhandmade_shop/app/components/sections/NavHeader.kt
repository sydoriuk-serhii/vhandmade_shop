package org.vhandmade_shop.app.components.sections

import androidx.compose.runtime.*
import com.varabyte.kobweb.browser.dom.ElementTarget
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.functions.clamp
import com.varabyte.kobweb.compose.foundation.layout.Box 
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.css.Cursor 
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.forms.TextInput
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.icons.CloseIcon
import com.varabyte.kobweb.silk.components.icons.HamburgerIcon
import com.varabyte.kobweb.silk.components.icons.MoonIcon
import com.varabyte.kobweb.silk.components.icons.SunIcon
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.navigation.UncoloredLinkVariant
import com.varabyte.kobweb.silk.components.navigation.UndecoratedLinkVariant
import com.varabyte.kobweb.silk.components.overlay.Overlay
import com.varabyte.kobweb.silk.components.overlay.OverlayVars
import com.varabyte.kobweb.silk.components.overlay.PopupPlacement
import com.varabyte.kobweb.silk.components.overlay.Tooltip
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.animation.Keyframes
import com.varabyte.kobweb.silk.style.animation.toAnimation
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.breakpoint.displayIfAtLeast
import com.varabyte.kobweb.silk.style.breakpoint.displayUntil
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import kotlinx.browser.localStorage
import org.jetbrains.compose.web.css.*
import org.vhandmade_shop.app.components.widgets.IconButton
import org.vhandmade_shop.app.toSitePalette


val NavHeaderStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .padding(leftRight = 2.cssRem, topBottom = 1.cssRem)
        .backgroundColor(colorMode.toSitePalette().nearBackground)
        .position(Position.Sticky)
        .top(0.px)
        .zIndex(10)
}

val SearchInputStyle = CssStyle.base {
    Modifier
        .padding(leftRight = 0.75.cssRem, topBottom = 0.3.cssRem)
        .borderRadius(15.px)
        .border(width = 0.px)
}

val LanguageItemStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .padding(0.5.cssRem)
        .cursor(Cursor.Pointer)
        .borderRadius(4.px)
}

@Composable
private fun NavLink(path: String, text: String, modifier: Modifier = Modifier) {
    Link(
        path,
        text,
        modifier = modifier.padding(leftRight = 1.cssRem),
        variant = UndecoratedLinkVariant.then(UncoloredLinkVariant)
    )
}

@Composable
private fun MenuItems(isSideMenu: Boolean = false) {
    val itemModifier = if (isSideMenu) Modifier else Modifier
    NavLink("/catalog", "Каталог", itemModifier)
    NavLink("/about", "Про нас", itemModifier)
}

@Composable
private fun ColorModeButton() {
    var colorMode by ColorMode.currentState
    IconButton(onClick = { colorMode = colorMode.opposite }) {
        if (colorMode.isLight) MoonIcon() else SunIcon()
    }
    Tooltip(ElementTarget.PreviousSibling, "Змінити тему", placement = PopupPlacement.BottomRight)
}

@Composable
private fun CartButton() {
    val cartItemCount = 0 
    Link("/cart") {
        IconButton(onClick = { /* Навігація через Link */ }) {
            SpanText("🛒")
            if (cartItemCount > 0) {
                SpanText("($cartItemCount)", Modifier.fontSize(0.8.em).margin(left = 0.3.cssRem))
            }
        }
    }
    Tooltip(ElementTarget.PreviousSibling, "Кошик", placement = PopupPlacement.Bottom)
}

@Composable
private fun ProfileButton() {
    Link("/account") {
        IconButton(onClick = { /* Навігація через Link */ }) {
            SpanText("👤")
        }
    }
    Tooltip(ElementTarget.PreviousSibling, "Профіль / Вхід", placement = PopupPlacement.BottomRight)
}

@Composable
private fun HamburgerButton(onClick: () -> Unit) {
    IconButton(onClick) { HamburgerIcon() }
}

@Composable
private fun CloseButton(onClick: () -> Unit) {
    IconButton(onClick) { CloseIcon() }
}


val SideMenuSlideInAnim = Keyframes {
    from { Modifier.translateX(100.percent) }
    to { Modifier.translateX(0.percent) }
}

enum class SideMenuState { CLOSED, OPEN, CLOSING; /* ... */ fun close() = when (this) {CLOSED -> CLOSED; OPEN -> CLOSING; CLOSING -> CLOSING} }






@Composable
fun LanguageSwitcher(modifier: Modifier = Modifier) {
    var currentLanguage by remember {
        mutableStateOf(localStorage.getItem("app_lang") ?: "uk")
    }
    var isDropdownOpen by remember { mutableStateOf(false) }
    val ctx = rememberPageContext() 

    fun changeLanguage(langCode: String) {
        currentLanguage = langCode
        localStorage.setItem("app_lang", langCode)
        isDropdownOpen = false
        println("Language changed to: $langCode") // Заглушка
        // ---------------
    }

    Box(modifier = modifier) { 
        IconButton(
            onClick = { isDropdownOpen = !isDropdownOpen }
        ) {
            SpanText(currentLanguage.uppercase(), Modifier.fontWeight(FontWeight.Bold))
        }

        // Випадаюче меню
//        if (isDropdownOpen) {
//            Popup(
//                // Позиціонування відносно батьківського Box (потрібно перевірити Target)
//                // Або можна передати ElementRef кнопки, якщо потрібно точніше
//                placement = PopupPlacement.BottomEnd, // Спробуємо розмістити знизу-справа
//                // Закривати при кліку поза меню
//                onDismissRequest = { isDropdownOpen = false },
//                // Можна додати відступи, якщо треба
//                modifier = Modifier.margin(top = 0.5.cssRem)
//            ) {
//                // Контент випадаючого меню
//                Surface(
//                    modifier = Modifier
//                        .minWidth(120.px) // Мінімальна ширина
//                        .borderRadius(6.px)
//                        .boxShadow(blurRadius = 8.px, color = rgba(0, 0, 0, 0.1))
//                        .padding(0.5.cssRem), // Внутрішні відступи
//                    elevation = 4.dp // Додає тінь
//                ) {
//                    Column(horizontalAlignment = Alignment.Start) {
//                        // Елемент для української мови
//                        Box(
//                            LanguageItemStyle.toModifier()
//                                .thenIf(currentLanguage == "uk", Modifier.fontWeight(FontWeight.Bold)) // Виділяємо поточну
//                                .onClick { changeLanguage("uk") }
//                        ) {
//                            SpanText("Українська")
//                        }
//                        // Елемент для англійської мови
//                        Box(
//                            LanguageItemStyle.toModifier()
//                                .thenIf(currentLanguage == "en", Modifier.fontWeight(FontWeight.Bold)) // Виділяємо поточну
//                                .onClick { changeLanguage("en") }
//                        ) {
//                            SpanText("English")
//                        }
//                    }
//                }
//            }
//        }
    }
}









@Composable
fun NavHeader() {
    var desktopSearchText by remember { mutableStateOf("") }

    Row(NavHeaderStyle.toModifier(), verticalAlignment = Alignment.CenterVertically) {
        Link("/") {
            Image(
                src = "/logo.png",
                description = "Logo",
                modifier = Modifier.height(2.5.cssRem).display(DisplayStyle.Block)
            )
        }
        Spacer()
        
        Row(Modifier.gap(1.cssRem).displayIfAtLeast(Breakpoint.MD), verticalAlignment = Alignment.CenterVertically) {
            MenuItems()
            Spacer()
            TextInput(
                text = desktopSearchText,
                placeholder = "Пошук товарів...",
                modifier = SearchInputStyle.toModifier().width(250.px),
                onTextChange = { newText -> desktopSearchText = newText } 
            )
            CartButton()
            ProfileButton()
            ColorModeButton()
        }

        // Мобільна
        Row(Modifier.fontSize(1.5.cssRem).gap(1.cssRem).displayUntil(Breakpoint.MD), verticalAlignment = Alignment.CenterVertically) {
            var menuState by remember { mutableStateOf(SideMenuState.CLOSED) }
            CartButton()
            ColorModeButton()
            HamburgerButton(onClick = { menuState = SideMenuState.OPEN })
            if (menuState != SideMenuState.CLOSED) {
                SideMenu(menuState = menuState, close = { menuState = menuState.close() }, onAnimationEnd = { if (menuState == SideMenuState.CLOSING) menuState = SideMenuState.CLOSED })
            }
        }
    }
}


@Composable
private fun SideMenu(menuState: SideMenuState, close: () -> Unit, onAnimationEnd: () -> Unit) {
    var mobileSearchText by remember { mutableStateOf("") }
    val sideMenuBackgroundColor = ColorMode.current.toSitePalette().nearBackground

    Overlay(Modifier.setVariable(OverlayVars.BackgroundColor, Colors.Transparent).onClick { close() }) {
        key(menuState) {
            Column(
                Modifier
                    .fillMaxHeight()
                    .width(clamp(15.cssRem, 50.percent, 20.cssRem))
                    .align(Alignment.CenterEnd)
                    .padding(top = 1.cssRem, leftRight = 1.5.cssRem)
                    .gap(1.5.cssRem)
                    .backgroundColor(sideMenuBackgroundColor)
                    .boxShadow(offsetX = (-5).px, color = rgba(0, 0, 0, 0.1))
                    .animation(SideMenuSlideInAnim.toAnimation(/* ... параметри ... */))
                    .borderRadius(topLeft = 1.cssRem, bottomLeft = 1.cssRem)
                    .onClick { it.stopPropagation() }
                    .onAnimationEnd { onAnimationEnd() },
                horizontalAlignment = Alignment.Start
            ) {
                Box(modifier = Modifier.align(Alignment.End)) {
                    CloseButton(onClick = { close() })
                }
                // ---------------------------------

                TextInput(
                    text = mobileSearchText, 
                    placeholder = "Пошук...",
                    modifier = SearchInputStyle.toModifier().fillMaxWidth().margin(bottom = 1.cssRem),
                    onTextChange = { newText -> mobileSearchText = newText }
                )

                Column(Modifier.fillMaxWidth().gap(1.cssRem).fontSize(1.2.cssRem), horizontalAlignment = Alignment.Start) {
                    MenuItems(isSideMenu = true)
                }
                Spacer()
                Row(Modifier.fillMaxWidth().gap(1.cssRem).padding(top = 1.cssRem)) {
                    CartButton()
                    ProfileButton()
                }
            }
        }
    }
}