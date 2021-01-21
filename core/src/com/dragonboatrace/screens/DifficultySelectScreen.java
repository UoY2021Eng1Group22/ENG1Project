package com.dragonboatrace.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.dragonboatrace.DragonBoatRace;
import com.dragonboatrace.entities.Button;
import com.dragonboatrace.entities.EntityType;
import com.dragonboatrace.entities.boats.BoatType;
import com.dragonboatrace.tools.Settings;

/**
 * Displays the screen that allows the player to choose a boat at the beginning of the game.
 *
 * @author Benji Garment, Joe Wrieden
 */
public class DifficultySelectScreen implements Screen {

    /**
     * Texture of the easy boat preview.
     */
    private final Texture easyImage;

    /**
     * Texture of the medium boat preview.
     */
    private final Texture mediumImage;

    /**
     * Texture of the hard boat preview.
     */
    private final Texture hardImage;


    /**
     * Button to select the easy boat.
     */
    private final Button easyButton;

    /**
     * Button to select the medium boat.
     */
    private final Button mediumButton;

    /**
     * Button to select the hard boat.
     */
    private final Button hardButton;


    /**
     * Instance of the main game, used to have a collective spritebatch which gives better performance.
     */
    private final DragonBoatRace game;


    private final BitmapFont font;
    private final GlyphLayout layout;

    private final int buttonWidth;
    private final String type;

    /**
     * Creates a new screen to display the boat options to the player.
     *
     * @param game The instance of the {@link DragonBoatRace} game.
     */
    public DifficultySelectScreen(DragonBoatRace game, String type) {

        this.game = game;
        this.type = type;

        this.buttonWidth = EntityType.BUTTON.getWidth();
        float spacing = (Gdx.graphics.getWidth() - buttonWidth * 3.0f) / 4.0f;
        this.easyButton = new Button(new Vector2(spacing, 100), "easy_button_active.png", "easy_button_inactive.png");
        this.mediumButton = new Button(new Vector2(spacing + (buttonWidth + spacing), 100), "medium_button_active.png", "medium_button_inactive.png");
        this.hardButton = new Button(new Vector2(spacing + (buttonWidth + spacing) * 2, 100), "hard_button_active.png", "hard_button_inactive.png");

        this.easyImage = new Texture("easy_difficulty.png");
        this.mediumImage = new Texture("medium_difficulty.png");
        this.hardImage = new Texture("hard_difficulty.png");





        /* Font related items */
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("osaka-re.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size *= 10.0 / Settings.SCALAR;
        parameter.color = Color.WHITE;
        font = generator.generateFont(parameter);
        layout = new GlyphLayout();
        layout.setText(font, "Choose your Difficulty:");

    }


    @Override
    public void show() {

    }

    /**
     * Renders the boat selection screen.
     *
     * @param delta The time passed since the last frame.
     */
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.game.getBatch().begin();

        font.draw(this.game.getBatch(), "Choose your Difficulty:", (Gdx.graphics.getWidth() - layout.width) / 2, Gdx.graphics.getHeight() - 100);

        float scale = ((float) this.buttonWidth / EntityType.BOAT.getWidth()) / 2.0f;

        this.game.getBatch().draw(this.easyImage, this.easyButton.getHitBox().getX() + ((this.easyButton.getHitBox().getWidth() - this.buttonWidth / 2f) / 2f), 150 + EntityType.BUTTON.getHeight(), this.buttonWidth / 2f, EntityType.BOAT.getHeight() * scale);
        this.easyButton.render(this.game.getBatch());

        this.game.getBatch().draw(this.mediumImage, this.mediumButton.getHitBox().getX() + ((this.mediumButton.getHitBox().getWidth() - this.buttonWidth / 2f) / 2f), 150 + EntityType.BUTTON.getHeight(), this.buttonWidth / 2f, EntityType.BOAT.getHeight() * scale);
        this.mediumButton.render(this.game.getBatch());

        this.game.getBatch().draw(this.hardImage, this.hardButton.getHitBox().getX() + ((this.hardButton.getHitBox().getWidth() - this.buttonWidth / 2f) / 2f), 150 + EntityType.BUTTON.getHeight(), this.buttonWidth / 2f, EntityType.BOAT.getHeight() * scale);
        this.hardButton.render(this.game.getBatch());

        if (type == "fast") {
            if (this.easyButton.isHovering() && Gdx.input.isTouched()) {
                this.game.setScreen(new MainGameScreen(this.game, BoatType.FASTEASY));
            } else if (this.mediumButton.isHovering() && Gdx.input.isTouched()) {
                this.game.setScreen(new MainGameScreen(this.game, BoatType.FAST));
            } else if (this.hardButton.isHovering() && Gdx.input.isTouched()) {
                this.game.setScreen(new MainGameScreen(this.game, BoatType.FASTHARD));
            }
        }
        else if (type == "agile") {
            if (this.easyButton.isHovering() && Gdx.input.isTouched()) {
                this.game.setScreen(new MainGameScreen(this.game, BoatType.AGILEEASY));
            } else if (this.mediumButton.isHovering() && Gdx.input.isTouched()) {
                this.game.setScreen(new MainGameScreen(this.game, BoatType.AGILE));
            } else if (this.hardButton.isHovering() && Gdx.input.isTouched()) {
                this.game.setScreen(new MainGameScreen(this.game, BoatType.AGILEHARD));
            }
        }
        else if (type == "strong") {
            if (this.easyButton.isHovering() && Gdx.input.isTouched()) {
                this.game.setScreen(new MainGameScreen(this.game, BoatType.STRONGEASY));
            } else if (this.mediumButton.isHovering() && Gdx.input.isTouched()) {
                this.game.setScreen(new MainGameScreen(this.game, BoatType.STRONG));
            } else if (this.hardButton.isHovering() && Gdx.input.isTouched()) {
                this.game.setScreen(new MainGameScreen(this.game, BoatType.STRONGHARD));
            }
        }
        else if (type == "endurance") {
            if (this.easyButton.isHovering() && Gdx.input.isTouched()) {
                this.game.setScreen(new MainGameScreen(this.game, BoatType.ENDURANCEEASY));
            } else if (this.mediumButton.isHovering() && Gdx.input.isTouched()) {
                this.game.setScreen(new MainGameScreen(this.game, BoatType.ENDURANCE));
            } else if (this.hardButton.isHovering() && Gdx.input.isTouched()) {
                this.game.setScreen(new MainGameScreen(this.game, BoatType.ENDURANCEHARD));
            }
        }

        this.game.getBatch().end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }
}
