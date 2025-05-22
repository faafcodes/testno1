package net.faaf.testmod.ui;

import net.faaf.testmod.augment.AugmentManager;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class CustomScreen extends Screen {

    private String currentTooltip;

    public CustomScreen() {
        super(Text.of("Augment Selection"));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        // Botão para Dano Melee
        ButtonWidget meleeButton = ButtonWidget.builder(
                Text.of("Dano Melee"),
                button -> {
                    AugmentManager.applyMeleeDamageBoost(client.player);
                    this.close();
                }
        ).dimensions(centerX - 160, centerY, 100, 20).build();

        this.addDrawableChild(meleeButton);

        // Botão para Resistência
        ButtonWidget resistanceButton = ButtonWidget.builder(
                Text.of("Resistência"),
                button -> {
                    AugmentManager.applyResistance(client.player);
                    this.close();
                }
        ).dimensions(centerX - 50, centerY, 100, 20).build();

        this.addDrawableChild(resistanceButton);

        // Botão para Velocidade
        ButtonWidget speedButton = ButtonWidget.builder(
                Text.of("Velocidade"),
                button -> {
                    AugmentManager.applySpeed(client.player);
                    this.close();
                }
        ).dimensions(centerX + 60, centerY, 100, 20).build();

        this.addDrawableChild(speedButton);
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
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        currentTooltip = null;

        // Verifica se o mouse está sobre algum botão e atualiza a descrição
        this.children().forEach(child -> {
            if (child instanceof ButtonWidget button) {
                if (button.isMouseOver(mouseX, mouseY)) {
                    if (button.getMessage().getString().equals("Dano Melee")) {
                        currentTooltip = AugmentManager.getAugmentDescription(AugmentManager.MELEE_DAMAGE_BOOST_ID);
                    } else if (button.getMessage().getString().equals("Resistência")) {
                        currentTooltip = AugmentManager.getAugmentDescription(AugmentManager.RESISTANCE_ID);
                    } else if (button.getMessage().getString().equals("Velocidade")) {
                        currentTooltip = AugmentManager.getAugmentDescription(AugmentManager.SPEED_ID);
                    }
                }
            }
        });

        super.mouseMoved(mouseX, mouseY);
    }
}
