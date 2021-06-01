import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());
        final FigureDrawer drawArea = new FigureDrawer();
        content.add(drawArea, BorderLayout.CENTER);

        JPanel controls = new JPanel();
        JButton newPointsButton = new JButton("Reset");
        JButton okButton = new JButton("QuickHull");

        ActionListener actionListener = e -> {
            if(e.getSource() == newPointsButton) {
                drawArea.clearData();
            } else
            if(e.getSource() == okButton) {
                drawArea.createConvexHull();
            }
        };

        newPointsButton.addActionListener(actionListener);
        okButton.addActionListener(actionListener);
        controls.add(newPointsButton);
        controls.add(okButton);
        content.add(controls, BorderLayout.NORTH);

        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
