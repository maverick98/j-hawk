/*
 * This file is part of hawkeye
 * CopyLeft (C) 2012-2013 Manoranjan Sahu, All Rights are left.
 *

 * 
 *
 * 
 */
package org.hawk.ide;

/**
 *
 * @author manoranjan
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pivot.beans.BXML;
import org.apache.pivot.beans.BXMLSerializer;
import org.apache.pivot.collections.ArrayList;
import org.apache.pivot.collections.List;
import org.apache.pivot.collections.Map;
import org.apache.pivot.wtk.Alert;
import org.apache.pivot.wtk.Application;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ButtonPressListener;
import org.apache.pivot.wtk.Checkbox;
import org.apache.pivot.wtk.ColorChooserButton;
import org.apache.pivot.wtk.ColorChooserButtonSelectionListener;
import org.apache.pivot.wtk.DesktopApplicationContext;
import org.apache.pivot.wtk.Display;
import org.apache.pivot.wtk.FileBrowserSheet;
import org.apache.pivot.wtk.HorizontalAlignment;
import org.apache.pivot.wtk.ListButton;
import org.apache.pivot.wtk.ListButtonSelectionListener;
import org.apache.pivot.wtk.ListView;
import org.apache.pivot.wtk.MessageType;
import org.apache.pivot.wtk.PushButton;
import org.apache.pivot.wtk.Sheet;
import org.apache.pivot.wtk.SheetCloseListener;
import org.apache.pivot.wtk.TextPane;
import org.apache.pivot.wtk.TextPaneCharacterListener;
import org.apache.pivot.wtk.Window;
import org.apache.pivot.wtk.content.ListButtonDataRenderer;
import org.apache.pivot.wtk.content.ListViewItemRenderer;
import org.apache.pivot.wtk.content.NumericSpinnerData;
import org.apache.pivot.wtk.text.Document;
import org.apache.pivot.wtk.text.Element;
import org.apache.pivot.wtk.text.Node;
import org.apache.pivot.wtk.text.Paragraph;
import org.apache.pivot.wtk.text.PlainTextSerializer;
import org.apache.pivot.wtk.text.TextNode;
import org.apache.pivot.wtk.text.TextSpan;
import org.commons.file.FileUtil;
import org.commons.io.IOUtil;
import static org.hawk.constant.HawkConstant.ENCODING;
import org.common.di.AppContainer;
import org.commons.event.HawkEvent;
import org.commons.event.HawkEventPayload;

import org.hawk.executor.DefaultScriptExecutor;
import org.hawk.executor.command.interpreter.ScriptInterpretationDebugCommand;
import org.hawk.executor.command.plugin.deploy.PluginDeploymentCommand;

import org.hawk.logger.HawkLogger;
import org.hawk.module.cache.AllModuleCache;
import org.hawk.output.HawkOutput;
import org.hawk.output.event.HawkErrorEvent;
import org.hawk.output.event.HawkIOException;
import org.hawk.output.event.HawkOutputEvent;
import org.hawk.util.HawkUtil;
import org.commons.resource.ResourceUtil;
import org.commons.event.callback.HawkEventCallbackRegistry;
import org.commons.event.callback.IHawkEventCallback;
import org.commons.event.callback.IHawkEventCallbackRegistry;
import org.commons.event.exception.HawkEventException;
import org.hawk.executor.command.interpreter.ScriptExecutor;

public class HawkIDE extends Application.Adapter {

    private static final HawkLogger logger = HawkLogger.getLogger(HawkIDE.class.getName());
    private Window window = null;

    @BXML
    private TextPane inputTextPane = null;
    @BXML
    private TextPane outputTextPane = null;
    @BXML
    private PushButton openFileButton = null;
    @BXML
    private PushButton saveFileButton = null;
    @BXML
    private PushButton saveAsFileButton = null;
    @BXML
    private PushButton boldButton = null;
    @BXML
    private ColorChooserButton foregroundColorChooserButton = null;
    @BXML
    private ColorChooserButton backgroundColorChooserButton = null;
    @BXML
    private ListButton fontFamilyListButton = null;
    @BXML
    private ListButton fontSizeListButton = null;
    @BXML
    private Checkbox wrapTextCheckbox = null;
    @BXML
    private PushButton alignLeftButton = null;
    @BXML
    private PushButton alignCentreButton = null;
    @BXML
    private PushButton alignRightButton = null;
    @BXML
    private PushButton compileButton = null;
    @BXML
    private PushButton runButton = null;

    @BXML
    private PushButton apiButton = null;
    @BXML
    private PushButton deployButton = null;
    private File loadedFile = null;
    private boolean loadedFileModified = false;
    PlainTextSerializer serializer = new PlainTextSerializer();

    public HawkOutput getHawkOutput() {

        return AppContainer.getInstance().getBean(HawkOutput.class);
    }

    public HawkIDE() {
        System.out.println("in CTOR ::" + this);
    }

    private static class FontFamilyItemRenderer extends ListViewItemRenderer {

        @Override
        public void render(Object item, int index, ListView listView, boolean selected,
                boolean checked, boolean highlighted, boolean disabled) {
            super.render(item, index, listView, selected, checked, highlighted, disabled);
            if (item != null) {
                String fontFamilyName = (String) item;
                label.getStyles().put("font", Font.decode(fontFamilyName + "-12"));
            }
        }
    }

    private static class FontFamilyDataRenderer extends ListButtonDataRenderer {

        @Override
        public void render(Object data, Button button, boolean highlight) {
            super.render(data, button, highlight);
            if (data != null) {
                String fontFamilyName = (String) data;
                label.getStyles().put("font", Font.decode(fontFamilyName + "-12"));
            }
        }
    }

    @Override
    public void startup(Display display, Map<String, String> properties) throws Exception {
        System.out.println("in startup ::" + this);
        HawkIOEventCallback callback = new HawkIOEventCallback();
        IHawkEventCallbackRegistry hawkPluginCallbackRegistry = AppContainer.getInstance().getBean(HawkEventCallbackRegistry.class);
        try {
            hawkPluginCallbackRegistry.register(
                    HawkOutputEvent.class,
                    callback
            );
        } catch (HawkEventException ex) {
            Logger.getLogger(HawkIDE.class.getName()).log(Level.SEVERE, null, ex);
        }

        BXMLSerializer bxmlSerializer = new BXMLSerializer();

        window = (Window) bxmlSerializer.readObject(HawkIDE.class, "/text_pane_demo.bxml");
        bxmlSerializer.bind(this, HawkIDE.class);

        window.setTitle(HawkUtil.getHawkIDETitle());
        window.setMaximized(true);

        fontFamilyListButton.setListData(new ArrayList<String>(
                GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()));
        fontSizeListButton.setSelectedItem(fontFamilyListButton.getListData().get(0));
        fontFamilyListButton.setItemRenderer(new FontFamilyItemRenderer());
        fontFamilyListButton.setDataRenderer(new FontFamilyDataRenderer());

        fontSizeListButton.setListData(new NumericSpinnerData(12, 30, 1));
        fontSizeListButton.setSelectedItem(12);

        openFileButton.getButtonPressListeners().add(new ButtonPressListener() {
            @Override
            public void buttonPressed(Button button) {
                final FileBrowserSheet fileBrowserSheet = new FileBrowserSheet();

                fileBrowserSheet.setMode(FileBrowserSheet.Mode.OPEN);
                fileBrowserSheet.open(window, new SheetCloseListener() {
                    @Override
                    public void sheetClosed(Sheet sheet) {
                        if (sheet.getResult()) {
                            loadedFile = fileBrowserSheet.getSelectedFile();
                            BufferedReader bfr = null;
                            InputStreamReader isr = null;
                            FileInputStream fis = null;
                            try {
                                fis = new FileInputStream(loadedFile);
                                isr = new InputStreamReader(fis, Charset.forName(ENCODING));
                                bfr = new BufferedReader(isr);

                                inputTextPane.setDocument(serializer.readObject(bfr));
                                StringBuilder sb = new StringBuilder();
                                sb.append(HawkUtil.getHawkIDETitle());
                                sb.append("-");
                                sb.append(loadedFile.getCanonicalPath());
                                window.setTitle(sb.toString());
                            } catch (IOException ex) {
                                Alert.alert(ex.getMessage(), window);
                            } finally {
                                ResourceUtil.close(bfr, isr, fis);
                            }
                        }
                    }
                });
            }
        });
        saveFileButton.getButtonPressListeners().add(new ButtonPressListener() {
            @Override
            public void buttonPressed(Button button) {
                if (loadedFile != null) {
                    BufferedWriter bfr = null;
                    OutputStreamWriter osw = null;
                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(loadedFile);
                        osw = new OutputStreamWriter(fos, Charset.forName(ENCODING));
                        bfr = new BufferedWriter(osw);

                        serializer.writeObject(inputTextPane.getDocument(), bfr);

                        StringBuilder sb = new StringBuilder();
                        sb.append(HawkUtil.getHawkIDETitle());
                        sb.append("-");
                        sb.append(loadedFile.getCanonicalPath());
                        window.setTitle(sb.toString());
                    } catch (IOException ex) {
                        Alert.alert(ex.getMessage(), window);
                    } finally {
                        ResourceUtil.close(bfr, osw, fos);
                    }
                }
            }
        });
        saveAsFileButton.getButtonPressListeners().add(new ButtonPressListener() {
            @Override
            public void buttonPressed(Button button) {
                final FileBrowserSheet fileBrowserSheet = new FileBrowserSheet();

                if (loadedFile != null) {
                    fileBrowserSheet.setSelectedFile(loadedFile);
                }

                fileBrowserSheet.setMode(FileBrowserSheet.Mode.SAVE_AS);
                fileBrowserSheet.open(window, new SheetCloseListener() {
                    @Override
                    public void sheetClosed(Sheet sheet) {
                        if (sheet.getResult()) {
                            File selectedFile = fileBrowserSheet.getSelectedFile();

                            BufferedWriter bfr = null;
                            OutputStreamWriter osw = null;
                            FileOutputStream fos = null;
                            try {
                                fos = new FileOutputStream(selectedFile);
                                osw = new OutputStreamWriter(fos, Charset.forName(ENCODING));
                                bfr = new BufferedWriter(osw);

                                serializer.writeObject(inputTextPane.getDocument(), bfr);

                                loadedFile = selectedFile;
                                window.setTitle(HawkUtil.getHawkIDETitle() + "-" + loadedFile.getCanonicalPath());
                            } catch (IOException ex) {
                                Alert.alert(ex.getMessage(), window);
                            } finally {
                                ResourceUtil.close(bfr, osw, fos);
                            }
                        }
                    }
                });
            }
        });

        compileButton.getButtonPressListeners().add(new ButtonPressListener() {
            @Override
            public void buttonPressed(Button button) {

                new HawkIDEExecutor() {
                    @Override
                    public boolean executeHawkTask() throws Exception {
                        DefaultScriptExecutor scriptInterpreter = AppContainer.getInstance().getBean(ScriptExecutor.class);
                        boolean compiled = scriptInterpreter.compile(hawkInputFile);
                        if (compiled) {
                            Alert.alert(MessageType.INFO, "Compiled successfully!!!", window);

                        } else {
                            Alert.alert(MessageType.ERROR, "Compilation failed", window);

                        }
                        return compiled;
                    }
                }.execute(true);

            }
        });
        runButton.getButtonPressListeners().add(new ButtonPressListener() {
            @Override
            public void buttonPressed(Button button) {
                new HawkIDEExecutor() {
                    @Override
                    public boolean executeHawkTask() throws Exception {

                        ScriptInterpretationDebugCommand runCommand = new ScriptInterpretationDebugCommand();
                        runCommand.setHawkScriptFile(hawkInputFile.getPath());
                        
                        runCommand.setHawkScriptData(FileUtil.readFile(runCommand.getHawkScriptFile()));
                       
                        runCommand.execute();
                        /*
                         DefaultScriptExecutor scriptInterpreter = AppContainer.getInstance().getBean(SCRIPTEXECUTOR, DefaultScriptExecutor.class);
                         boolean compiled = scriptInterpreter.compile(hawkInputFile);
                         boolean result = false;
                         if (compiled) {
                         result = scriptInterpreter.interpret();
                        
                         } else {
                         result = false;
                         Alert.alert(MessageType.ERROR, "Compilation failed", window);
                        
                         }
                         return result;*/
                        return true;
                    }
                }.execute(true);
            }
        });

        apiButton.getButtonPressListeners().add(new ButtonPressListener() {
            @Override
            public void buttonPressed(Button button) {
                new HawkIDEExecutor() {
                    @Override
                    public boolean executeHawkTask() throws Exception {
                        AppContainer.getInstance().getBean(AllModuleCache.class).showTasks();
                        return true;
                    }
                }.execute(false);
            }
        });

        deployButton.getButtonPressListeners().add(new ButtonPressListener() {
            @Override
            public void buttonPressed(Button button) {
                final FileBrowserSheet fileBrowserSheet = new FileBrowserSheet();

                fileBrowserSheet.setMode(FileBrowserSheet.Mode.OPEN);
                fileBrowserSheet.open(window, new SheetCloseListener() {
                    @Override
                    public void sheetClosed(Sheet sheet) {
                        if (sheet.getResult()) {

                            final File settingFile = fileBrowserSheet.getSelectedFile();
                            class Result {

                                boolean deployed = false;

                                public boolean isDeployed() {
                                    return deployed;
                                }

                                public void setDeployed(boolean deployed) {
                                    this.deployed = deployed;
                                }

                            }
                            final Result result = new Result();
                            new HawkIDEExecutor() {
                                @Override
                                public boolean executeHawkTask() throws Exception {
                                    try {

                                        PluginDeploymentCommand pluginDeploymentCommand = new PluginDeploymentCommand();
                                        pluginDeploymentCommand.setPluginArchive(settingFile.getAbsolutePath());
                                        result.setDeployed(pluginDeploymentCommand.execute());
                                    } catch (Exception ex) {
                                        Alert.alert(ex.getMessage(), window);
                                    }

                                    //Fix  me
                                    return true;
                                }
                            }.execute(false);
                            if (result.isDeployed()) {
                                System.out.println("plugin {" + settingFile.getName() + "} is deployed successfully!!!");
                                Alert.alert("plugin {" + settingFile.getName() + "} is deployed successfully!!!", window);
                            }
                        }
                    }
                });
            }
        });

        boldButton.getButtonPressListeners().add(new ButtonPressListener() {
            @Override
            public void buttonPressed(Button button) {
                applyStyleToSelection(new StyleApplicator() {
                    @Override
                    public void apply(TextSpan span) {
                        if (span.getFont() != null) {
                            Font font = span.getFont();
                            if (font.getStyle() == Font.PLAIN) {
                                font = font.deriveFont(Font.BOLD);
                            } else if (font.getStyle() == Font.BOLD) {
                                font = font.deriveFont(Font.PLAIN);
                            } else {
                                // the font is BOLD+ITALIC
                                font = font.deriveFont(Font.ITALIC);
                            }
                            span.setFont(font);
                        } else {
                            span.setFont("Arial BOLD 12");
                        }
                    }
                });
            }
        });

        foregroundColorChooserButton.getColorChooserButtonSelectionListeners().add(
                new ColorChooserButtonSelectionListener() {
                    @Override
                    public void selectedColorChanged(ColorChooserButton colorChooserButton,
                            Color previousSelectedColor) {
                        applyStyleToSelection(new StyleApplicator() {
                            @Override
                            public void apply(TextSpan span) {
                                span.setForegroundColor(foregroundColorChooserButton.getSelectedColor());
                            }
                        });
                    }
                });

        backgroundColorChooserButton.getColorChooserButtonSelectionListeners().add(
                new ColorChooserButtonSelectionListener() {
                    @Override
                    public void selectedColorChanged(ColorChooserButton colorChooserButton,
                            Color previousSelectedColor) {
                        applyStyleToSelection(new StyleApplicator() {
                            @Override
                            public void apply(TextSpan span) {
                                span.setBackgroundColor(backgroundColorChooserButton.getSelectedColor());
                            }
                        });
                    }
                });

        ListButtonSelectionListener fontButtonPressListener = new ListButtonSelectionListener.Adapter() {
            @Override
            public void selectedItemChanged(ListButton listButton, Object previousSelectedItem) {
                int selectedFontSize = (Integer) fontSizeListButton.getSelectedItem();
                String selectedFontFamily = (String) fontFamilyListButton.getSelectedItem();
                final Font derivedFont = Font.decode(selectedFontFamily + " " + selectedFontSize);

                applyStyleToSelection(new StyleApplicator() {
                    @Override
                    public void apply(TextSpan span) {
                        span.setFont(derivedFont);
                    }
                });
            }
        };
        fontFamilyListButton.getListButtonSelectionListeners().add(fontButtonPressListener);
        fontSizeListButton.getListButtonSelectionListeners().add(fontButtonPressListener);

        wrapTextCheckbox.getButtonPressListeners().add(new ButtonPressListener() {
            @Override
            public void buttonPressed(Button button) {
                inputTextPane.getStyles().put("wrapText", wrapTextCheckbox.isSelected());
            }
        });

        alignLeftButton.getButtonPressListeners().add(new ButtonPressListener() {
            @Override
            public void buttonPressed(Button button) {
                applyAlignmentStyle(HorizontalAlignment.LEFT);
            }
        });

        alignCentreButton.getButtonPressListeners().add(new ButtonPressListener() {
            @Override
            public void buttonPressed(Button button) {
                applyAlignmentStyle(HorizontalAlignment.CENTER);
            }
        });

        alignRightButton.getButtonPressListeners().add(new ButtonPressListener() {
            @Override
            public void buttonPressed(Button button) {
                applyAlignmentStyle(HorizontalAlignment.RIGHT);
            }
        });
        inputTextPane.getTextPaneCharacterListeners().add(new TextPaneCharacterListener() {
            @Override
            public void charactersInserted(TextPane tp, int i, int i1) {
                loadedFileModified = true;
                String existingTitle = window.getTitle();
                if (!existingTitle.contains("*")) {
                    existingTitle = existingTitle + "*";
                }
                window.setTitle(existingTitle);

            }

            @Override
            public void charactersRemoved(TextPane tp, int i, int i1) {
                loadedFileModified = true;
                String existingTitle = window.getTitle();
                if (!existingTitle.contains("*")) {
                    existingTitle = existingTitle + "*";
                }
                window.setTitle(existingTitle);
            }
        });
        window.open(display);
        inputTextPane.requestFocus();

        // HawkConfigHelper.configure();
    }

    private abstract class HawkIDEExecutor {

        File hawkOutputFile = null;
        File hawkInputFile = null;

        public File getHawkInputFile() {
            return hawkInputFile;
        }

        public void setHawkInputFile(File hawkInputFile) {
            this.hawkInputFile = hawkInputFile;
        }

        public File getHawkOutputFile() {
            return hawkOutputFile;
        }

        public void setHawkOutputFile(File hawkOutputFile) {
            this.hawkOutputFile = hawkOutputFile;
        }

        public abstract boolean executeHawkTask() throws Exception;

        public boolean execute(boolean writeToHawk) {
            boolean status = true;
            hawkOutputFile = null;
            hawkInputFile = null;
            //PrintWriter outWriter = null;
            PrintWriter hawkBfr = null;

            //OutputStreamWriter outOsw = null;
            OutputStreamWriter hawkOsw = null;

            FileOutputStream hawkFos = null;

            if (writeToHawk && inputTextPane.getDocument() == null) {

                return false;
            }
            try {
                /* if (readFromHawk) {
                 // hawkOutputFile = File.createTempFile("out", "log");
                 // outWriter = new PrintWriter(hawkOutputFile);
                
                 // getHawkOutput().setOutput(outWriter);
                 //  getHawkOutput().setError(outWriter);
                 }*/
                if (writeToHawk) {
                    hawkInputFile = File.createTempFile("tmp", "hawk");
                    hawkFos = new FileOutputStream(hawkInputFile);
                    hawkOsw = new OutputStreamWriter(hawkFos, Charset.forName(ENCODING));
                    hawkBfr = new PrintWriter(hawkOsw);

                    serializer.writeObject(inputTextPane.getDocument(), hawkBfr);
                }
                this.executeHawkTask();

            } catch (Throwable th) {
                th.printStackTrace();
                try {
                    outputTextPane.setDocument(serializer.readObject(IOUtil.createReader(th.getMessage())));
                } catch (Exception ex1) {
                    logger.error(ex1);
                }

            } finally {

                if (hawkOutputFile != null) {
                    status = hawkOutputFile.delete();
                }
                if (hawkInputFile != null) {
                    status = hawkInputFile.delete();
                }
                // ResourceUtil.close(outWriter);
                ResourceUtil.close(hawkBfr, hawkOsw, hawkFos);
                /*if (readFromHawk) {
                
                 try {
                 outputTextPane.setDocument(serializer.readObject(FileUtil.readFileAsStream(hawkOutputFile)));
                 } catch (Throwable ex) {
                 Logger.getLogger(HawkIDE.class.getName()).log(Level.SEVERE, null, ex);
                 }
                 }*/
            }
            return status;
        }
    }

    private interface StyleApplicator {

        void apply(TextSpan span);
    }

    private void applyAlignmentStyle(HorizontalAlignment horizontalAlignment) {
        Node node = inputTextPane.getDocument().getDescendantAt(inputTextPane.getSelectionStart());
        while (node != null && !(node instanceof Paragraph)) {
            node = node.getParent();
        }
        if (node != null) {
            Paragraph paragraph = (Paragraph) node;
            paragraph.setHorizontalAlignment(horizontalAlignment);
        }
    }

    /**
     * debugging tools
     */
    private void dumpDocumentNode(Node node, PrintStream printStream, final int indent) {
        for (int i = 0; i < indent; i++) {
            printStream.append("  ");
        }
        printStream.append("<" + node.getClass().getSimpleName() + ">");
        if (node instanceof TextNode) {
            TextNode textNode = (TextNode) node;
            String text = textNode.getText();
            printStream.append(text);
            printStream.append("</" + node.getClass().getSimpleName() + ">");
            printStream.println();
        } else {
            printStream.println();
            if (node instanceof Element) {
                Element element = (Element) node;

                for (Node childNode : element) {
                    dumpDocumentNode(childNode, printStream, indent + 1);
                }
            } else {
                String text = node.toString();
                printStream.append(text);
            }
            for (int i = 0; i < indent; i++) {
                printStream.append("  ");
            }
            printStream.append("</" + node.getClass().getSimpleName() + ">");
            printStream.println();
        }
    }

    private void applyStyleToSelection(StyleApplicator styleApplicator) {
        org.apache.pivot.wtk.Span span = inputTextPane.getSelection();
        if (span == null) {
            return;
        }
        applyStyle(inputTextPane.getDocument(), span, styleApplicator);
    }

    private void applyStyle(Document document, org.apache.pivot.wtk.Span selectionSpan,
            StyleApplicator styleApplicator) {
        // I can't apply the styles while iterating over the tree, because I
        // need to update the tree.
        // So first collect a list of all the nodes in the tree.
        List<Node> nodeList = new ArrayList<>();
        collectNodes(document, nodeList);

        final int selectionStart = inputTextPane.getSelectionStart();
        final int selectionLength = inputTextPane.getSelectionLength();

        for (Node node : nodeList) {
            if (node instanceof TextSpan) {
                TextSpan span = (TextSpan) node;
                int documentOffset = node.getDocumentOffset();
                int characterCount = node.getCharacterCount();
                org.apache.pivot.wtk.Span textSpan = new org.apache.pivot.wtk.Span(documentOffset,
                        documentOffset + characterCount);
                if (selectionSpan.intersects(textSpan)) {
                    applyStyleToSpanNode(selectionSpan, styleApplicator, span, characterCount,
                            textSpan);
                }
            }
            if (node instanceof org.apache.pivot.wtk.text.TextNode) {
                org.apache.pivot.wtk.text.TextNode textNode = (org.apache.pivot.wtk.text.TextNode) node;
                int documentOffset = node.getDocumentOffset();
                int characterCount = node.getCharacterCount();
                org.apache.pivot.wtk.Span textSpan = new org.apache.pivot.wtk.Span(documentOffset,
                        documentOffset + characterCount);
                if (selectionSpan.intersects(textSpan)) {
                    applyStyleToTextNode(selectionSpan, styleApplicator, textNode, characterCount,
                            textSpan);
                }
            }
        }

        // maintain the selected range
        inputTextPane.setSelection(selectionStart, selectionLength);
    }

    private static void applyStyleToTextNode(org.apache.pivot.wtk.Span selectionSpan,
            StyleApplicator styleApplicator, org.apache.pivot.wtk.text.TextNode textNode,
            int characterCount, org.apache.pivot.wtk.Span textSpan) {
        if (selectionSpan.contains(textSpan)) {
            // if the text-node is contained wholly inside the selection, remove
            // the text-node, replace it with a Span, and apply the style
            Element parent = textNode.getParent();
            TextSpan newSpanNode = new TextSpan();
            newSpanNode.add(new TextNode(textNode.getText()));
            styleApplicator.apply(newSpanNode);
            int index = parent.remove(textNode);
            parent.insert(newSpanNode, index);
        } else if (selectionSpan.start <= textSpan.start) {
            // if the selection covers the first part of the text-node, split
            // off the first part of the text-node, and apply the style to it
            int intersectionLength = selectionSpan.end - textSpan.start;
            String part1 = textNode.getSubstring(0, intersectionLength);
            String part2 = textNode.getSubstring(intersectionLength, characterCount);

            TextSpan newSpanNode = new TextSpan();
            newSpanNode.add(new TextNode(part1));
            styleApplicator.apply(newSpanNode);

            Element parent = textNode.getParent();
            int index = parent.remove(textNode);
            parent.insert(newSpanNode, index);
            parent.insert(new TextNode(part2), index + 1);
        } else if (selectionSpan.end >= textSpan.end) {
            // if the selection covers the last part of the text-node, split off
            // the last part of the text-node, and apply the style to
            // it
            int intersectionStart = selectionSpan.start - textSpan.start;
            String part1 = textNode.getSubstring(0, intersectionStart);
            String part2 = textNode.getSubstring(intersectionStart, characterCount);

            TextSpan newSpanNode = new TextSpan();
            newSpanNode.add(new TextNode(part2));
            styleApplicator.apply(newSpanNode);

            Element parent = textNode.getParent();
            int index = parent.remove(textNode);
            parent.insert(new TextNode(part1), index);
            parent.insert(newSpanNode, index + 1);
        } else {
            // if the selection covers an internal part of the text-node, split
            // the
            // text-node into 3 parts, and apply the style to the second part
            int part2Start = selectionSpan.start - textSpan.start;
            int part2End = selectionSpan.end - textSpan.start + 1;
            String part1 = textNode.getSubstring(0, part2Start);
            String part2 = textNode.getSubstring(part2Start, part2End);
            String part3 = textNode.getSubstring(part2End, characterCount);

            TextSpan newSpanNode = new TextSpan();
            newSpanNode.add(new TextNode(part2));
            styleApplicator.apply(newSpanNode);

            Element parent = textNode.getParent();
            int index = parent.remove(textNode);
            parent.insert(new TextNode(part1), index);
            parent.insert(newSpanNode, index + 1);
            parent.insert(new TextNode(part3), index + 2);
        }
    }

    private static void applyStyleToSpanNode(org.apache.pivot.wtk.Span selectionSpan,
            StyleApplicator styleApplicator, TextSpan spanNode,
            int characterCount, org.apache.pivot.wtk.Span textSpan) {
        if (selectionSpan.contains(textSpan)) {
            // if the span-node is contained wholly inside the
            // selection, apply the style
            styleApplicator.apply(spanNode);
        } else if (selectionSpan.start <= textSpan.start) {
            // if the selection covers the first part of the span-node, split
            // off the first part of the span-node, and apply the style to it
            int intersectionLength = selectionSpan.end - textSpan.start;
            TextSpan node1 = spanNode.getRange(0, intersectionLength);
            styleApplicator.apply(node1);
            Node node2 = spanNode.getRange(intersectionLength, characterCount - intersectionLength);
            Element parent = spanNode.getParent();
            int index = parent.remove(spanNode);
            parent.insert(node1, index);
            parent.insert(node2, index + 1);
        } else if (selectionSpan.end >= textSpan.end) {
            // if the selection covers the last part of the span-node, split off
            // the last part of the span-node, and apply the style to it
            int intersectionStart = selectionSpan.start - textSpan.start;
            TextSpan part1 = spanNode.getRange(0, intersectionStart);
            TextSpan part2 = spanNode.getRange(intersectionStart,
                    characterCount - intersectionStart);

            styleApplicator.apply(part2);

            Element parent = spanNode.getParent();
            int index = parent.remove(spanNode);
            parent.insert(part1, index);
            parent.insert(part2, index + 1);
        } else {
            // if the selection covers an internal part of the span-node, split
            // the
            // span-node into 3 parts, and apply the style to the second part
            int part2Start = selectionSpan.start - textSpan.start;
            int part2End = selectionSpan.end - textSpan.start;
            TextSpan part1 = spanNode.getRange(0, part2Start);
            TextSpan part2 = spanNode.getRange(part2Start, part2End
                    - part2Start);
            TextSpan part3 = spanNode.getRange(part2End, characterCount
                    - part2End);

            styleApplicator.apply(part2);

            Element parent = spanNode.getParent();
            int index = parent.remove(spanNode);
            parent.insert(part1, index);
            parent.insert(part2, index + 1);
            parent.insert(part3, index + 2);
        }
    }

    private void collectNodes(org.apache.pivot.wtk.text.Node node, List<Node> nodeList) {
        // don't worry about the text-nodes that are children of Span nodes.
        if (node instanceof TextSpan) {
            return;
        }
        if (node instanceof org.apache.pivot.wtk.text.Element) {
            Element element = (Element) node;
            for (Node child : element) {
                nodeList.add(child);
                collectNodes(child, nodeList);
            }
        }
    }

    @Override
    public boolean shutdown(boolean optional) {
        if (window != null) {
            window.close();
        }

        return false;
    }

    public class HawkIOEventCallback implements IHawkEventCallback {

        private StringBuilder buffer = new StringBuilder();

        public StringBuilder getBuffer() {
            return buffer;
        }

        public String getCurrentBuffer() {
            return this.getBuffer().toString();
        }

        public void setBuffer(StringBuilder buffer) {
            this.buffer = buffer;
        }

        public boolean addBuffer(String data) {
            buffer.append(data);
            buffer.append("\n");
            return true;
        }

        @Override
        public Integer getSequence() {
            return 2;
        }

        @Override
        public int compareTo(IHawkEventCallback o) {
            return this.getSequence().compareTo(o.getSequence());
        }

        @HawkEvent(type = HawkOutputEvent.class)
        public boolean onHawkOutput(HawkEventPayload hawkPluginPayload) throws HawkIOException {
            System.out.println("Recvd hawkoutput event in IDE {" + hawkPluginPayload.getPayload() + "}");
            this.addBuffer(hawkPluginPayload.getPayload().toString());
            ByteArrayInputStream bais = null;
            InputStreamReader isr = null;
            BufferedReader bfr = null;
            try {
                bais = new ByteArrayInputStream(this.getCurrentBuffer().getBytes(Charset.forName(ENCODING)));
                isr = new InputStreamReader(bais, Charset.forName(ENCODING));
                bfr = new BufferedReader(isr);

                outputTextPane.setDocument(serializer.readObject(bfr));

            } catch (Exception ex1) {
                ex1.printStackTrace();
            } finally {
                ResourceUtil.close(bfr, isr, bais);

            }
            return true;
        }

        @HawkEvent(type = HawkErrorEvent.class)
        public boolean onHawkError(HawkEventPayload hawkPluginPayload) throws HawkIOException {
            System.out.println("Recvd hawkerror event");
            return true;
        }

    }

    public static void main(String[] args) {

        DesktopApplicationContext.main(HawkIDE.class, args);

    }
}
