/**
 * Created by Zulkifl on 8/5/2015.
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.*;

public class NewDialog extends JDialog{

    private final static JPanel nextPanel = new JPanel(new BorderLayout());
    private final static JPanel labelPanel = new JPanel(new BorderLayout());
    private final static JButton nextButton = new JButton("\u25BA");
    private final static JPanel textPanel = new JPanel(new BorderLayout());
    private final static JTextField textField = new JTextField();
    private JLabel slideLabel;
    private JLabel picLabel = new JLabel();
    private final int NUM_RESPONSES = 40;

    private String[] responses = new String[NUM_RESPONSES + 1];
    private int prevIndex = NUM_RESPONSES;
    private ArrayList<Integer> randNums = new ArrayList();

    public NewDialog(){
        initializeRandNum();

        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("closing...");
                System.exit(0);
            }
        });

        setLayout(new FlowLayout());
        setResizable(true);
        setVisible(true);
        setSize(600, 600);

        add(labelPanel,BorderLayout.SOUTH);
        add(textPanel,BorderLayout.SOUTH);
        add(nextPanel,BorderLayout.SOUTH);

        slideLabel = new JLabel();
        labelPanel.add(slideLabel, BorderLayout.CENTER);
        labelPanel.setVisible(true);

        textField.setPreferredSize(new Dimension(200,50));
        textPanel.add(textField);
        textPanel.setVisible(true);

        nextPanel.add(nextButton, BorderLayout.WEST);
        nextPanel.setVisible(true);

        add(picLabel);

        nextButton.addActionListener(new ActionListener() {
            int counter = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                if(counter < NUM_RESPONSES) {
                    int imageIndex = randNums.get(counter);
                    picLabel.setIcon(new ImageIcon(generateImage(imageIndex)));
                    picLabel.setText(counter+1 + "");
                    testResponses(textField.getText(),prevIndex);
                    System.out.println(prevIndex + ": " + textField.getText());
                    prevIndex = imageIndex;
                    counter++;
                    if (counter == 40){
                        picLabel.setText("LAST ONE!");
                    }
                    revalidate();
                    repaint();
                } else {
                    System.exit(0);
                }
            }
        });

    }

    private void initializeRandNum(){
        for (int i = 0; i < NUM_RESPONSES; i++) {
            randNums.add(i);
        }
        Collections.shuffle(randNums);
    }

    public void testResponses(String response, int index){
        responses[index] = response;
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("TestResponses.txt")));
            for (int i = 0; i < 40; i++){
                String out = i + ": " + responses[i] + "\n";
                bufferedWriter.write(out);
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Image generateImage(int randNum) {
        String filename = "IshiharaImages/" + randNum + "-38.jpg";
        Image ishiharaImage = null;
        try {
            ishiharaImage = ImageIO.read(getClass().getResourceAsStream(filename));
            ishiharaImage = ishiharaImage.getScaledInstance(400, 400, Image.SCALE_DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ishiharaImage;
    }
}
