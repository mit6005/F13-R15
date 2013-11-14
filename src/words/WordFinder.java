package words;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingUtilities;

import words.WordFinder;
import words.WordList;

/**
 * WordFinder is an interface for searching a word list.
 * When the user types any part of a word, the interface
 * displays all the words that match.
 */
public class WordFinder extends JFrame {

    private static final long serialVersionUID = 1L;
    
    private final WordList words = new WordList();
    
    private final JList list;
    
    private final JTextField find;
    
    /**
     * Make a WordFinder window.
     */
    public WordFinder() {
        super("Word Finder");
        
        // call System.exit() when user closes the window
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        Container cp = this.getContentPane();
        
        // sets the layout manager of the content pane to a GroupLayout
        
        GroupLayout layout = new GroupLayout(cp);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        cp.setLayout(layout);
        ParallelGroup hGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);

        //cp.add(new JLabel("Find: "), BorderLayout.WEST);
        
        //cp.add(find = new JTextField(20), BorderLayout.CENTER);
        
        find = new JTextField(20);
        JLabel findLabel = new JLabel("Find: ");
        
        
        SequentialGroup h1 = layout.createSequentialGroup();
        h1.addComponent(findLabel);
        h1.addComponent(find);
        
        ParallelGroup vGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        SequentialGroup v1 = layout.createSequentialGroup();
        ParallelGroup v2 = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
        v1.addComponent(findLabel);
        v2.addComponent(find);

        
        /*
         * add an action listener to `find' that outputs matching words
         *         to the console
         */
        
        // ...
        
        // adds a JList inside a JScrollPane that shows matching words
        
        list = new JList(new DefaultListModel());
        JScrollPane scroller = new JScrollPane(list);
        v1.addComponent(scroller);
        SequentialGroup h2 = layout.createSequentialGroup();
        h2.addComponent(scroller);
        
        hGroup.addGroup(h1);
        hGroup.addGroup(h2);
        layout.setHorizontalGroup(hGroup);
        vGroup.addGroup(v1);
        vGroup.addGroup(v2);
        
        layout.setVerticalGroup(vGroup);
        
        this.pack();
        
        try {
            InputStream in = new FileInputStream("words");
            loadWords(in);
        } catch (IOException ioe) { }
    }
    
    private void loadWords(InputStream in) throws IOException {
        words.load(in);
        // ...
    }
    
    private void doFind() {
        String query = find.getText();
        
        List<String> matches = words.find(query);
        DefaultListModel listModel = (DefaultListModel) list.getModel();
        listModel.removeAllElements();
        for (String match : matches) {
            listModel.addElement(match);
        }
        
        //updateCounter(query);
        find.selectAll();
        find.grabFocus();
    }
    
    /**
     * Main method.  Makes and displays a WordFinder window.
     * @param args Command-line arguments.  Ignored.
     */
    public static void main(String[] args) {
        // In general, Swing objects should only be accessed from
        // the event-handling thread -- not from the main thread
        // or other threads you create yourself.  SwingUtilities.invokeLater()
        // is a standard idiom for switching to the event-handling thread.
        SwingUtilities.invokeLater(new Runnable() {
            public void run () {
                // Make and display the WordFinder window.
                new WordFinder().setVisible(true);
            }
        });
    }    
}
