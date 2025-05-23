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
        // Obter 3 augments aleatórios do tipo especificado ao inicializar a tela
        this.randomAugments = AugmentManager.getRandomAugmentsByType(augmentType);
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int buttonWidth = 100;
        int buttonHeight = 20;
        int buttonSpacing = 110; // Espaçamento horizontal entre os botões

        // Iterar pelos augments aleatórios e criar botões para cada um
        for (int i = 0; i < randomAugments.size(); i++) {
            Identifier augmentId = randomAugments.get(i);
            String augmentName = augmentId.getPath().replace("_", " ").toUpperCase();

            ButtonWidget augmentButton = ButtonWidget.builder(
                    Text.of(augmentName),
                    button -> {
                        if (client.player != null) {
                            AugmentManager.applyAugment(client.player, augmentId);
                        }
                        this.close();
                    }
            ).dimensions(centerX - buttonSpacing + (i * buttonSpacing), centerY, buttonWidth, buttonHeight).build();

            this.addDrawableChild(augmentButton);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        super.render(context, mouseX, mouseY, delta);

        // Renderizar tooltip com fundo se necessário
        if (currentTooltip != null) {
            context.drawTooltip(this.textRenderer, Text.of(currentTooltip), mouseX, mouseY);
        }
    }

    private void renderBackground(DrawContext context) {
        context.fillGradient(0, 0, this.width, this.height, 0xFF000000, 0xFF111111);
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
