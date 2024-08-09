import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LotteryApp extends JFrame {
    private List<JPanel> prizePanels;
    private List<Integer> availablePrizes;
    private JButton startButton;
    private Timer timer;
    private Random random;
    private int currentIndex;

    public LotteryApp() {
        setTitle("抽奖系统");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initializePrizePanels();
        initializeStartButton();

        random = new Random();
        availablePrizes = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            availablePrizes.add(i);
        }

        setVisible(true);
    }

    private void initializePrizePanels() {
        JPanel gridPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        prizePanels = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            JLabel imageLabel = new JLabel(new ImageIcon("path/to/image" + (i + 1) + ".jpg"));
            JLabel textLabel = new JLabel("奖品" + (i + 1));

            imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            panel.add(Box.createVerticalGlue());
            panel.add(imageLabel);
            panel.add(Box.createVerticalStrut(10));
            panel.add(textLabel);
            panel.add(Box.createVerticalGlue());

            prizePanels.add(panel);
            gridPanel.add(panel);
        }

        add(gridPanel, BorderLayout.CENTER);
    }

    private void initializeStartButton() {
        startButton = new JButton("开始抽奖");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startLottery();
            }
        });
        add(startButton, BorderLayout.SOUTH);
    }

    private void startLottery() {
        if (timer != null && timer.isRunning()) {
            return;
        }

        if (availablePrizes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "所有奖品已抽完！");
            return;
        }

        currentIndex = availablePrizes.get(0);
        timer = new Timer(100, new ActionListener() {
            private int count = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                prizePanels.get(currentIndex).setBackground(null);
                currentIndex = availablePrizes.get(random.nextInt(availablePrizes.size()));
                prizePanels.get(currentIndex).setBackground(Color.YELLOW);

                count++;
                if (count >= 20) {
                    timer.stop();
                    selectPrize();
                }
            }
        });
        timer.start();
    }

    private void selectPrize() {
        prizePanels.get(currentIndex).setBackground(Color.GREEN);
        availablePrizes.remove(Integer.valueOf(currentIndex));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LotteryApp();
            }
        });
    }
}