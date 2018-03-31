/*
 * This file is part of j-hawk
 * CopyLeft (C) BigBang<->BigCrunch Manoranjan Sahu, All Rights are left.
 *
 * 
 *
 * 1) If you modify a source file, make a comment in it containing your name and the date.
 * 2) If you distribute a modified version, you must do it under the GPL 2.
 * 3) Developers are encouraged but not required to notify the j-hawk maintainers at abeautifulmind98@gmail.com
 * when they make a useful addition. It would be nice if significant contributions could be merged into the main distribution.
 *
 * 
 * 
 *
 * 
 */
package org.hawk.ide;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.common.di.AppContainer;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.hawk.config.HawkConfigHelper;
import org.hawk.executor.command.interpreter.ScriptInterpretationPerfCommand;
import org.hawk.output.HawkOutput;

public class GUIHawkMain extends Application {

    private static final String[] KEYWORDS = new String[]{
        "break",
        "wait",
        "reset", "do", "function", "else",
        "exec", "if", "main", "exec-parallel", "exec-bg",
        "for", "struct", "var", "varx", "return",
        "alias", "echo", "echon", "break", "think",
        "curThreadName", "read-line", "new", "while"
    };

    private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
    private static final String PAREN_PATTERN = "\\(|\\)";
    private static final String BRACE_PATTERN = "\\{|\\}";
    private static final String BRACKET_PATTERN = "\\[|\\]";
    private static final String SEMICOLON_PATTERN = "\\;";
    private static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
    private static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";

    private static final Pattern PATTERN = Pattern.compile(
            "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
            + "|(?<PAREN>" + PAREN_PATTERN + ")"
            + "|(?<BRACE>" + BRACE_PATTERN + ")"
            + "|(?<BRACKET>" + BRACKET_PATTERN + ")"
            + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
            + "|(?<STRING>" + STRING_PATTERN + ")"
            + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
    );

    private static final String sampleCode = String.join("\n", new String[]{
        "    /*",
        "     * multi-line comment",
        "     */",
        "    function main()",
        " {",
        "        // single-line comment",
        "",
        "}"
    });

    public static void main(String[] args) {
        launch(args);
    }

    private CodeArea codeArea;
    private ExecutorService executor;
    private TextArea outputArea;

    @Override
    public void start(Stage primaryStage) {
        try {
            HawkConfigHelper.configure();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);

        }

        executor = Executors.newSingleThreadExecutor();
        codeArea = this.createCodeArea();
        outputArea = new TextArea();

        VBox vbox = new VBox();
        Scene scene = new Scene(vbox, 1200, 600);

        scene.getStylesheets().add(Thread.currentThread().getContextClassLoader().getResource("hawk-keywords.css").toExternalForm());
        MenuBar menuBar = this.createMenuBar();
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        vbox.getChildren().addAll(menuBar);
        vbox.getChildren().addAll(codeArea);
        vbox.getChildren().addAll(outputArea);

        primaryStage.setScene(scene);
        primaryStage.setTitle("j-hawk");
        primaryStage.getIcons().add(new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("images/logo.png")));
        primaryStage.show();
    }

    private CodeArea createCodeArea() {
        CodeArea codeArea1 = new CodeArea();

        codeArea1.setMinHeight(600);
        codeArea1.setMinWidth(1200);
        codeArea1.setParagraphGraphicFactory(LineNumberFactory.get(codeArea1));
        codeArea1.richChanges()
                .filter(ch -> !ch.getInserted().equals(ch.getRemoved()))
                .successionEnds(Duration.ofMillis(500))
                .supplyTask(this::computeHighlightingAsync)
                .awaitLatest(codeArea1.richChanges())
                .filterMap(t -> {
                    if (t.isSuccess()) {
                        return Optional.of(t.get());
                    } else {
                        t.getFailure().printStackTrace();
                        return Optional.empty();
                    }
                })
                .subscribe(this::applyHighlighting);
        codeArea1.replaceText(0, 0, sampleCode);
        return codeArea1;
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        Menu menuFile = new Menu("File");

        MenuItem newFileMenuItem = new MenuItem("New");
        menuFile.getItems().add(newFileMenuItem);

        MenuItem openFileMenuItem = new MenuItem("Open");
        menuFile.getItems().add(openFileMenuItem);

        MenuItem saveFileMenuItem = new MenuItem("Save");
        menuFile.getItems().add(saveFileMenuItem);

        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction((ActionEvent t) -> {
            System.exit(0);
        });
        menuFile.getItems().add(exitMenuItem);

        Menu menuEdit = new Menu("Edit");

        Menu runMenu = new Menu("Run");
        MenuItem runHawkMenuItem = new MenuItem("Run Hawk");
        runHawkMenuItem.setOnAction((ActionEvent actionEvent) -> {
            try {
                GUIHawkOutputWriter gUIHawkOutputWriter = new GUIHawkOutputWriter(outputArea);
                ScriptInterpretationPerfCommand scriptInterpretationCommand = AppContainer.getInstance().getBean(ScriptInterpretationPerfCommand.class);
                scriptInterpretationCommand.setHawkScriptData(codeArea.getText());
                HawkOutput hawkOutput = AppContainer.getInstance().getBean(HawkOutput.class);
                hawkOutput.setHawkOutputWriter(gUIHawkOutputWriter);
                scriptInterpretationCommand.execute();
            } catch (Exception ex) {
                Logger.getLogger(GUIHawkMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        runMenu.getItems().add(runHawkMenuItem);

        Menu toolMenu = new Menu("Tools");

        Menu helpMenu = new Menu("Help");
        MenuItem aboutMenuItem = new MenuItem("About");
        helpMenu.getItems().add(aboutMenuItem);

        menuBar.getMenus().addAll(menuFile, menuEdit, runMenu, toolMenu, helpMenu);
        return menuBar;
    }

    @Override
    public void stop() {
        executor.shutdown();
    }

    private Task<StyleSpans<Collection<String>>> computeHighlightingAsync() {
        String text = codeArea.getText();
        Task<StyleSpans<Collection<String>>> task = new Task<StyleSpans<Collection<String>>>() {
            @Override
            protected StyleSpans<Collection<String>> call() throws Exception {
                return computeHighlighting(text);
            }
        };
        executor.execute(task);
        return task;
    }

    private void applyHighlighting(StyleSpans<Collection<String>> highlighting) {
        codeArea.setStyleSpans(0, highlighting);
    }

    private static StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder
                = new StyleSpansBuilder<>();
        while (matcher.find()) {
            String styleClass
                    = matcher.group("KEYWORD") != null ? "keyword"
                            : matcher.group("PAREN") != null ? "paren"
                                    : matcher.group("BRACE") != null ? "brace"
                                            : matcher.group("BRACKET") != null ? "bracket"
                                                    : matcher.group("SEMICOLON") != null ? "semicolon"
                                                            : matcher.group("STRING") != null ? "string"
                                                                    : matcher.group("COMMENT") != null ? "comment"
                                                                            : null; /* never happens */ assert styleClass != null;
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }
}
