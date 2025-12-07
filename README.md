# GameNest

Aplicativo Android com Jetpack Compose para listar jogos e exibir detalhes de itens compráveis. Navegação por `Intent` entre telas.

## Estrutura do Projeto
- `app/src/main/AndroidManifest.xml`
  - Declara `MainActivity` (launcher) e `GameDetailActivity` (`android:exported="true"`).
- `app/src/main/java/com/example/gamenest/view/activities/MainActivity.kt`
  - Tela principal com `Scaffold`, `TopAppBar`, `BottomNavBar` e lista de `GameCard`.
  - Navega para detalhes com `Intent` e passa `Game` serializável.
- `app/src/main/java/com/example/gamenest/view/activities/GameDetailActivity.kt`
  - Mostra imagem do jogo, descrição e lista de itens.
  - Usa `ModalBottomSheet` para simular compra.
  - `SafeImage` valida `imageRes` antes de renderizar.
- `app/src/main/java/com/example/gamenest/view/components/Components.kt`
  - `GameCard` (card com imagem e título) e `BottomNavBar` (barra inferior).
- `app/src/main/java/com/example/gamenest/controller/GameController.kt`
  - Fornece dados de exemplo: jogos `FORTNITE` e `FC26` com itens.
- `app/src/main/java/com/example/gamenest/model/*.kt`
  - `Game` e `ShopItem` (modelos serializáveis).
- `app/src/main/java/com/example/gamenest/ui/theme/*`
  - Tema Material 3 (`GameNestTheme`).
- `app/src/main/java/com/example/gamenest/view/previews/MainPreview.kt`
  - Prévia dedicada da tela principal (opcional, pode usar a `MainActivity`).
- `app/src/main/res/drawable/*`
  - Imagens: `fortnite.png`, `fc26.png`, `fortnitelogo.png`, `packfort1/2/3.png`, `fifapoints1/2/3.png`, ícones `img_item1..img_item6`.

## Fluxo de Navegação
- `MainActivity` → clique em `GameCard` → `Intent(this, GameDetailActivity)` com `putExtra("game", game)`.
- `GameDetailActivity` recupera com API 33+: `getSerializableExtra("game", Game::class.java)` e, para versões anteriores, usa a API legada com supressão.

## UI e Previews
- `MainActivity.kt`
  - `TopAppBar` com `windowInsets = WindowInsets(0)` para aproximar do topo.
  - Cabeçalho `Text("Olá, Jogador")` acima da lista e espaçamento entre cards (`20.dp`).
  - `@Preview(showBackground = true)` para visualização rápida.
- `GameDetailActivity.kt`
  - Imagem superior com `statusBarsPadding()` e `Box` com `top = 12.dp` para não colar na status bar.
  - Previews para `FORTNITE` e `FC26`.

## Recursos e Imagens
- `SafeImage` evita falhas de preview quando `imageRes` é inválido, usando `ic_profile` como fallback.
- Itens de `FORTNITE`: `Sun & Scales Pack`, `Cross Comms Pack`, etc.
- Itens de `FC26`: packs `fifapoints1/2/3`.

## Execução e Build
- Compilar:
  - `./gradlew.bat assembleDebug --no-daemon --stacktrace`
- Se o Windows bloquear `R.jar`:
  - `./gradlew.bat --stop`
  - Apagar `app/build`
  - Reabrir o projeto e sincronizar.
- Preview:
  - Selecionar a função `MainPreview` ou as previews da `GameDetailActivity` e clicar em “Build & Refresh”.

## Convenções
- Compose Material 3
- Formatação de preço com `Locale.US` para estabilidade de preview.
- Sem `try/catch` envolvendo chamadas `Composable`.

## Personalizações rápidas
- Trocar cabeçalho em `MainActivity` (texto, estilo, espaçamento).
- Ajustar espaçamento da imagem da `GameDetail` (`statusBarsPadding` e `padding(top = ...)`).
- Padronizar `imageRes` dos itens para os drawables confirmados.

