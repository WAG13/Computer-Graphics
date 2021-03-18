import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());
        final Drawer drawer = new Drawer();
        content.add(drawer, BorderLayout.CENTER);

        JPanel controls = new JPanel();

        JButton chooseRegionButton = new JButton("Choose region");
        JButton newPointsSetButton = new JButton("Add points");

        ActionListener actionListener = e -> {
            if(e.getSource() == chooseRegionButton) {
                drawer.setChoosingRegionMode();
            } else if(e.getSource() == newPointsSetButton) {
                drawer.setDrawingPointsMode();
            }
        };

        chooseRegionButton.addActionListener(actionListener);
        newPointsSetButton.addActionListener(actionListener);

        controls.add(newPointsSetButton);
        controls.add(chooseRegionButton);

        content.add(controls, BorderLayout.NORTH);

        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

