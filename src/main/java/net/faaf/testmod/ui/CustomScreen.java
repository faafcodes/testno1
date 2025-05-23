package net.faaf.testmod.ui;

import net.faaf.testmod.augment.AugmentManager;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class CustomScreen extends Screen {

    private String currentTooltip;
    private final List<Identifier> randomAugments;

    public CustomScreen(String augmentType) {
        super(Text.of("Augment Selection"));
        this.randomAugments = AugmentManager.getRandomAugmentsByType(augmentType);
    }

    private final List<ButtonWidget> augmentButtons = new ArrayList<>();

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int rerollButtonSize = 20; // Tamanho do botão de reroll
        int buttonWidth = 100; // Largura do botão
        int buttonHeight = 150; // Altura do botão
        int buttonSpacing = 20; // Espaçamento entre os botões
        int totalWidth = (randomAugments.size() * (buttonWidth + buttonSpacing)) - buttonSpacing;

        for (int i = 0; i < randomAugments.size(); i++) {
            Identifier augmentId = randomAugments.get(i);
            String augmentName = augmentId.getPath().replace("_", " ").toUpperCase();

            int xPosition = centerX - (totalWidth / 2) + (i * (buttonWidth + buttonSpacing));
            int yPosition = centerY - (buttonHeight / 2);

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
            augmentButtons.add(augmentButton); // Adicionar à lista de augment buttons

            int rerollX = xPosition + (buttonWidth / 2) - (rerollButtonSize / 2);
            int rerollY = yPosition + buttonHeight + 5;

            ButtonWidget rerollButton = ButtonWidget.builder(
                    Text.of("⟳"),
                    button -> {
                        // Ação de reroll será adicionada posteriormente
                    }
            ).dimensions(rerollX, rerollY, rerollButtonSize, rerollButtonSize).build();

            this.addDrawableChild(rerollButton);
        }
    }


    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);

        for (int i = 0; i < augmentButtons.size(); i++) {
            ButtonWidget button = augmentButtons.get(i);
            Identifier augmentId = randomAugments.get(i);

            int borderColor = getBorderColor(augmentId);

            context.fill(button.getX() - 2, button.getY() - 2, button.getX() + button.getWidth() + 2, button.getY(), borderColor); // Topo
            context.fill(button.getX() - 2, button.getY(), button.getX(), button.getY() + button.getHeight(), borderColor); // Esquerda
            context.fill(button.getX() + button.getWidth(), button.getY(), button.getX() + button.getWidth() + 2, button.getY() + button.getHeight(), borderColor); // Direita
            context.fill(button.getX() - 2, button.getY() + button.getHeight(), button.getX() + button.getWidth() + 2, button.getY() + button.getHeight() + 2, borderColor); // Base
        }

        super.render(context, mouseX, mouseY, delta);

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

        for (int i = 0; i < augmentButtons.size(); i++) {
            ButtonWidget button = augmentButtons.get(i);
            if (button.isMouseOver(mouseX, mouseY)) {
                Identifier augmentId = randomAugments.get(i);
                currentTooltip = AugmentManager.getAugmentDescription(augmentId);
                break;
            }
        }

        super.mouseMoved(mouseX, mouseY);
    }
}
