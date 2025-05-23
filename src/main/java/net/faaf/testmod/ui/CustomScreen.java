package net.faaf.testmod.ui;

import net.faaf.testmod.augment.AugmentManager;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class CustomScreen extends Screen {

    private String currentTooltip;
    private final List<Identifier> randomAugments;

    public CustomScreen(String augmentType) {
        super(Text.of("Augment Selection"));
        this.randomAugments = AugmentManager.getRandomAugmentsByType(augmentType);
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int buttonWidth = 100; // Largura do botão
        int buttonHeight = 150; // Altura do botão
        int buttonSpacing = 20; // Espaçamento entre os botões
        int totalWidth = (randomAugments.size() * (buttonWidth + buttonSpacing)) - buttonSpacing;

        // Iterar pelos augments aleatórios e criar botões para cada um
        for (int i = 0; i < randomAugments.size(); i++) {
            Identifier augmentId = randomAugments.get(i);
            String augmentName = augmentId.getPath().replace("_", " ").toUpperCase();

            // Ajustar a posição dos botões
            int xPosition = centerX - (totalWidth / 2) + (i * (buttonWidth + buttonSpacing));
            int yPosition = centerY - (buttonHeight / 2); // Centralizar verticalmente

            ButtonWidget augmentButton = ButtonWidget.builder(
                    Text.of(augmentName),
                    button -> {
                        if (client.player != null) {
                            AugmentManager.applyAugment(client.player, augmentId);
                        }
                        this.close();
                    }
            ).dimensions(xPosition, yPosition, buttonWidth, buttonHeight).build();

            this.addDrawableChild(augmentButton);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);

        // Desenhar bordas coloridas ao redor dos botões
        for (int i = 0; i < this.children().size(); i++) {
            if (this.children().get(i) instanceof ButtonWidget button) {
                Identifier augmentId = randomAugments.get(i);

                // Determinar a cor da borda com base na raridade
                int borderColor = getBorderColor(augmentId);

                // Desenhar a borda
                context.fill(button.getX() - 2, button.getY() - 2, button.getX() + button.getWidth() + 2, button.getY(), borderColor); // Topo
                context.fill(button.getX() - 2, button.getY(), button.getX(), button.getY() + button.getHeight(), borderColor); // Esquerda
                context.fill(button.getX() + button.getWidth(), button.getY(), button.getX() + button.getWidth() + 2, button.getY() + button.getHeight(), borderColor); // Direita
                context.fill(button.getX() - 2, button.getY() + button.getHeight(), button.getX() + button.getWidth() + 2, button.getY() + button.getHeight() + 2, borderColor); // Base
            }
        }

        super.render(context, mouseX, mouseY, delta);

        // Renderizar tooltip com fundo se necessário
        if (currentTooltip != null) {
            context.drawTooltip(this.textRenderer, Text.of(currentTooltip), mouseX, mouseY);
        }
    }

    private void renderBackground(DrawContext context) {
        context.fillGradient(0, 0, this.width, this.height, 0xFF000000, 0xFF111111);
    }

    private int getBorderColor(Identifier augmentId) {
        String path = augmentId.getPath();
        if (path.startsWith("iron")) {
            return 0xFF808080; // Cinza para iron
        } else if (path.startsWith("gold")) {
            return 0xFFFFD700; // Dourado/Amarelo para gold
        } else if (path.startsWith("diamond")) {
            return 0xFF00FFFF; // Ciano para diamond
        } else {
            return 0xFFFFFFFF; // Branco (fallback)
        }
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        currentTooltip = null;

        // Verifica se o mouse está sobre algum botão e atualiza a descrição
        this.children().forEach(child -> {
            if (child instanceof ButtonWidget button) {
                if (button.isMouseOver(mouseX, mouseY)) {
                    for (Identifier augmentId : randomAugments) {
                        if (button.getMessage().getString().equals(augmentId.getPath().replace("_", " ").toUpperCase())) {
                            currentTooltip = AugmentManager.getAugmentDescription(augmentId);
                            break;
                        }
                    }
                }
            }
        });

        super.mouseMoved(mouseX, mouseY);
    }
}
