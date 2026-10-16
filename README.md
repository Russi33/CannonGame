# 🎯 Cannon Game - Aplicação Android (Java)

Trabalho prático desenvolvido para a disciplina de Programação III / Programação Android, baseado no projeto clássico **Cannon Game** (Livro: *Android para Programadores - Deitel*).

## 👨‍💻 Autores
* **Eduardo Reis Russi Souza**
* **Erik Souza Lopes**

---

## 🚀 Sobre o Projeto
O **Cannon Game** é um jogo arcade 2D em que o jogador controla um canhão posicionado no canto esquerdo da tela. O objetivo é disparar bolas de canhão e destruir todos os alvos móveis situados no lado direito antes que o tempo se esgoste.

### 🎮 Mecânicas do Jogo
* **Controle de Mira e Disparo:** O canhão se alinha e dispara automaticamente na direção do toque do jogador na tela.
* **Obstáculo (*Blocker*):** Uma barra preta central que se move na vertical, rebatendo as bolas de canhão e aplicando uma penalidade de tempo de **-2.0s** em caso de colisão.
* **Alvos (*Targets*):** Alvos azuis móveis na borda direita. Atingir cada um deles concede um bônus de tempo de **+3.0s** e elimina o alvo.
* **Condição de Vitória:** Destruir todos os 7 alvos antes do tempo zerar.
* **Condição de Derrota:** O tempo esgotar (`0.0s`) antes de eliminar todos os alvos.
* **Reinício de Partida:** Ao terminar a partida (*Game Over* ou *Vitória*), um simples toque na tela reinicia o jogo instantaneamente.

---

## 🌟 Funcionalidades e Bônus Implementados (+0.5 Bônus)
Para atender e superar todos os requisitos do trabalho acadêmico, foram implementadas as seguintes integrações:

1. **Efeitos Sonoros (`SoundPool`)**:
    - Áudios gravados e gerenciados via `res/raw`:
        - Disparo do canhão (`cannon_fire.wav`)
        - Colisão com os alvos (`target_hit.wav`)
        - Rebatida no obstáculo (`blocker_hit.wav`)
2. **Gerenciamento de Estado e Ciclo de Vida**:
    - Tratamento adequado de pausa e retomada (`onPause` / `onResume`) para prevenir congelamento de threads.
    - Orientação travada em modo paisagem (`landscape`) no `AndroidManifest.xml`.
3. **Persistência de Recordes (`SharedPreferences` - Extra)**:
    - Salvamento automático do **melhor desempenho** (menor quantidade de tiros para vencer).
    - Exibição em tempo real da pontuação recorde na interface gráfica do jogo.

---

## 🛠️ Tecnologias e Arquitetura
* **Linguagem:** Java
* **Plataforma:** Android (API Min / Target alinhados com o Android Studio moderno)
* **Desenho em Tela:** `SurfaceView`, `Canvas` e `Paint` para renderização manual em 60 FPS.
* **Multithreading:** `CannonThread` dedicada ao loop principal do jogo (*update & draw*).
* **Persistência:** `SharedPreferences` para salvamento local de dados.
* **Áudio:** `SoundPool` com `AudioAttributes` para baixa latência.

---

## 📁 Estrutura de Arquivos Principais

```text
app/src/main/
├── java/com/example/cannongame/
│   ├── MainActivity.java      # Gerencia o ciclo de vida da Activity
│   ├── CannonView.java        # Visão principal, loop do jogo, colisões e áudio
│   ├── CannonThread.java      # Thread em segundo plano para renderização gráfica
│   ├── Cannon.java            # Lógica da mira e disparo do canhão
│   ├── Cannonball.java        # Física e ciclo de vida do projétil
│   ├── GameElement.java       # Classe base para elementos com colisão (RectF)
│   ├── Blocker.java           # Movimento do obstáculo central
│   └── Target.java            # Movimento e recompensas dos alvos
└── res/
    └── raw/                   # Efeitos sonoros (.wav)
```

---

## 📱 Como Executar o Projeto
1. Clone o repositório:
   ```bash
   git clone https://github.com/Russi33/CannonGame.git
   ```
2. Abra o projeto no **Android Studio**.
3. Aguarde a sincronização automática do **Gradle**.
4. Conecte um dispositivo Android via USB (com *Depuração USB* ativa) ou utilize o Emulador (Pixel 7 / Landscape).
5. Clique em **Run 'app'** (`Shift + F10`).