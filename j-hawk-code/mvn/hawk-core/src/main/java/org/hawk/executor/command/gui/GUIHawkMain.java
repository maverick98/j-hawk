/*
 * This file is part of j-hawk
 *  
 *
 * 
 *
 *  
 *  
 *  
 *  
 *
 * 
 * 
 *
 * 
 */
package org.hawk.executor.command.gui;

import java.net.URL;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.commons.file.FileUtil;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

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
        "        echo \"Hello World\"",
        "",
        "}"
    });



    private BorderPane root;
    private ExecutorService executor;
    protected static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage =stage;
       
        try {
            URL url = Thread.currentThread().getContextClassLoader().getResource("fxml/gui.fxml");
            root = FXMLLoader.load(url);
            this.configureCodeArea();
            Scene scene = new Scene(root,java.awt.Toolkit.getDefaultToolkit().getScreenSize().width,
            java.awt.Toolkit.getDefaultToolkit().getScreenSize().height);
            scene.getStylesheets().add(Thread.currentThread().getContextClassLoader().getResource("hawk-keywords.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("j-hawk");
            primaryStage.getIcons().add(new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("images/logo.png")));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        executor = Executors.newSingleThreadExecutor();

    }

    private void configureCodeArea() {
        ScrollPane scrollPane = (ScrollPane) root.getChildren().get(1);

        CodeArea codeArea = (CodeArea) scrollPane.getContent();
        codeArea.setMinHeight(500);
        codeArea.setMinWidth(900);
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        codeArea.richChanges()
                .filter(ch -> !ch.getInserted().equals(ch.getRemoved()))
                .successionEnds(Duration.ofMillis(500))
                .supplyTask(this::computeHighlightingAsync)
                .awaitLatest(codeArea.richChanges())
                .filterMap(t -> {
                    if (t.isSuccess()) {
                        return Optional.of(t.get());
                    } else {
                        t.getFailure().printStackTrace();
                        return Optional.empty();
                    }
                })
                .subscribe(this::applyHighlighting);
        String sampleCode = getSampleCode();
        codeArea.replaceText(0, 0, sampleCode);

    }
    
    private String getSampleCode(){
        String data=null;
        try{
            data = FileUtil.readFile("scripts/cric.hawk");
        }catch(Exception ex){
           
        }
        if(data == null){
            data=sampleCode;
        }
        return data;
    }

    @Override
    public void stop() {
        executor.shutdown();
    }

    private Task<StyleSpans<Collection<String>>> computeHighlightingAsync() {
        ScrollPane scrollPane = (ScrollPane) root.getChildren().get(1);

        CodeArea codeArea = (CodeArea) scrollPane.getContent();
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
        ScrollPane scrollPane = (ScrollPane) root.getChildren().get(1);

        CodeArea codeArea = (CodeArea) scrollPane.getContent();
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
