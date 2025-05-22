package net.faaf.testmod.ui;

import net.faaf.testmod.augment.AugmentManager;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class CustomScreen extends Screen {

    public CustomScreen() {
        super(Text.of("Augment Selection"));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        // Botão para Dano Melee
        this.addDrawableChild(ButtonWidget.builder(
                Text.of("Dano Melee"),
                button -> {
                    AugmentManager.applyMeleeDamageBoost(client.player);
                    this.close();
                }
        ).dimensions(centerX - 160, centerY, 100, 20).build());

        // Botão para Resistência
        this.addDrawableChild(ButtonWidget.builder(
                Text.of("Resistência"),
                button -> {
                    AugmentManager.applyResistance(client.player);
                    this.close();
                }
        ).dimensions(centerX - 50, centerY, 100, 20).build());

        // Botão para Velocidade
        this.addDrawableChild(ButtonWidget.builder(
                Text.of("Velocidade"),
                button -> {
                    AugmentManager.applySpeed(client.player);
                    this.close();
                }
        ).dimensions(centerX + 60, centerY, 100, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
    }

    private void renderBackground(DrawContext context) {
    }
}
