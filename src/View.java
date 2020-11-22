import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.MatteBorder;

public class View {
    JPanel AdjPanel,AdjPanelInfo,DistPanel,DistPanelInfo,WegPanel,WegPanelInfo,InfoPanel,InfoPanel1,mainPanel;
    JPanel  panel = new JPanel();
    Graph   graph = new Graph();
    Integer size =graph.readCSVFile().length;
    JFrame  frame = new JFrame("Testing");
    JButton[][] btn = new JButton[size][size];
    JButton[][] btn1 = new JButton[size][size];
    JButton[][] btn2 = new JButton[size][size];
    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File"); // Create File menu
    JMenu elementMenu = new JMenu("Weiter" ); // Create Elements menu


    public static void main(String[] args) {

        init();
    }


    public static void init()
    {
        Graph g = new Graph();
        g.initAll();
        new View();
        ArrayList<ArrayList<Integer>> adjListArray =  g.convert(g.AdjacencyMatrix2);
        g.printArrayList(adjListArray);
        System.out.print("Articulation: ");
        g.AP();
        System.out.println("\nBirdge: ");
        g.bridge();
        //System.out.println(b.shortestBridge(g.getAdjacencyMatrix2()));
    }
    public View() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }
                addLayouts();

            }
        });
    }

    public void addLayouts()
    {
        panel.setLayout(new GridBagLayout());
        TestPane t = new TestPane();
        t.addAdjMatrix();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setJMenuBar(menuBar); // Add the menu bar to the window
        fileMenu.add(graph.newMenuItem);
        menuBar.add(fileMenu); // Add the file menu
        menuBar.add(elementMenu); // Add the element menu
        mainPanel = new JPanel(new GridLayout(2,4));
        AdjPanelInfo.add(new JScrollPane(AdjPanel));
        AdjPanelInfo.setSize(20,10);
        AdjPanelInfo.add(new JLabel("AdjMatrix", JLabel.CENTER), BorderLayout.PAGE_START);
        DistPanelInfo.add(new JScrollPane(DistPanel));
        DistPanelInfo.setSize(20,10);
        DistPanelInfo.add(new JLabel("DistMatrix", JLabel.CENTER), BorderLayout.PAGE_START);
        WegPanelInfo.add(new JScrollPane(WegPanel));
        WegPanelInfo.setSize(20,10);
        WegPanelInfo.add(new JLabel("WegMatrix", JLabel.CENTER), BorderLayout.PAGE_START);
        InfoPanel.add(InfoPanel1);
        InfoPanel.setSize(20,10);
        InfoPanel.add(new JLabel("Info", JLabel.CENTER), BorderLayout.PAGE_START);
        mainPanel.add(AdjPanelInfo);
        mainPanel.add(DistPanelInfo);
        mainPanel.add(WegPanelInfo);
        mainPanel.add(new JScrollPane(InfoPanel));
        mainPanel.setSize(600, 900);
        frame.add(mainPanel);
        frame.setSize(900, 1000);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public class TestPane {
        Graph g = new Graph();

        public TestPane() {
            g.initAll();

            // create Labels for Infos
            JLabel radius = new JLabel("Radius:                     " + g.getRadius());
            Font f = radius.getFont();
            radius.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
            JLabel durchmesser = new JLabel("Durchmesser:         " + g.getDurchmesser());
            durchmesser.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
            JLabel extrencität = new JLabel("Extrencicität:          " + g.exzentrizitaet());
            extrencität.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
            JLabel zentrum = new JLabel("Zentrum:                 " + g.getZentrum());
            zentrum.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
            JLabel Komponents = new JLabel("KomponentsNum:  " + g.komponentenanzahl());
            Komponents.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
            JLabel KomponentsOutput = new JLabel("Komponents:          " + g.komponenteAusgeben());
            KomponentsOutput.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
            JLabel Zusammenhengend = new JLabel("Zusammenhengend:  " + g.isZusammenhaengend());
            Zusammenhengend.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
            JLabel Articulation = new JLabel("Articulation:            " + g.apPrint);
            Articulation.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
            JLabel Bridge = new JLabel("Brücken:                  " + g.bridgePrint);
            Bridge.setFont(f.deriveFont(f.getStyle() | Font.BOLD));

            // Create Panels
            AdjPanel = new JPanel();
            DistPanel = new JPanel();
            WegPanel = new JPanel();
            AdjPanelInfo = new JPanel(new BorderLayout());
            DistPanelInfo = new JPanel(new BorderLayout());
            WegPanelInfo = new JPanel(new BorderLayout());
            InfoPanel = new JPanel(new BorderLayout());
            InfoPanel1 = new JPanel(new BorderLayout());
            AdjPanel.setLayout(new GridLayout(size, size));
            DistPanel.setLayout(new GridLayout(size, size));
            WegPanel.setLayout(new GridLayout(size, size));
            AdjPanel.setBorder(new MatteBorder(2, 2, 2, 2, Color.WHITE));
            DistPanel.setBorder(new MatteBorder(2, 2, 2, 2, Color.WHITE));
            WegPanel.setBorder(new MatteBorder(2, 2, 2, 2, Color.WHITE));
            panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

            GridBagConstraints constraints = new GridBagConstraints();
            constraints.gridy = 1;
            // Add all Info into Panel Info
            // constraints.insets = new Insets(0,0,2,0);
            panel.add(extrencität, constraints);
            constraints.gridy++;
            constraints.gridwidth = 10;
            //alignment for each label must be explicitly set
            constraints.anchor = GridBagConstraints.WEST;
            panel.add(durchmesser, constraints);
            constraints.gridy++;
            constraints.anchor = GridBagConstraints.WEST;
            panel.add(radius, constraints);
            constraints.gridy++;
            constraints.anchor = GridBagConstraints.WEST;
            panel.add(zentrum, constraints);
            constraints.gridy++;
            panel.add(Bridge, constraints);
            constraints.gridy++;
            panel.add(Articulation, constraints);
            constraints.gridy++;
            panel.add(Komponents, constraints);
            constraints.gridy++;
            panel.add(KomponentsOutput, constraints);
            constraints.gridy++;
            panel.add(Zusammenhengend, constraints);
            constraints.gridy++;

            // Add Panel of Infos to the Infopanel
            InfoPanel.add(panel, BorderLayout.WEST);
            //Create Matrix of buttons

        }

        public void addAdjMatrix() {
            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    if (row == col) {
                        //Adjatrix
                        String m = g.getAdjacencyMatrix2()[row][col].toString();
                        btn[row][col] = new JButton(m);
                        AdjPanel.add(btn[row][col], BorderLayout.SOUTH);
                        btn[row][col].setBackground(Color.BLUE);
                        AdjPanelInfo.add(AdjPanel, BorderLayout.SOUTH);
                        //DistanceMatrix
                        String m1 = g.getDistanceMatix()[row][col].toString();
                        btn1[row][col] = new JButton(m1);
                        DistPanel.add(btn1[row][col]);
                        btn1[row][col].setBackground(Color.BLUE);
                        //WegMatrix
                        String m2 = g.getWegmatrix()[row][col].toString();
                        btn2[row][col] = new JButton(m2);
                        WegPanel.add(btn2[row][col]);
                        btn2[row][col].setBackground(Color.BLUE);
                    } else {
                        //AdjMatrix
                        String m = g.getAdjacencyMatrix2()[row][col].toString();
                        btn[row][col] = new JButton(m);
                        //AdjMatrix
                        int finalCol = col;
                        int finalRow = row;
                        btn[row][col].addActionListener(e -> {
                            if (e.getSource() == btn[finalRow][finalCol] && btn[finalRow][finalCol].getText() == "1") {
                                btn[finalRow][finalCol].setText("0");
                                btn[finalCol][finalRow].setText("0");
                                System.out.println("Hallooooooooooo");

                            } else {
                                btn[finalRow][finalCol].setText("1");
                                btn[finalCol][finalRow].setText("1");
                                System.out.println("Hallooooooooooo");
                            }

                            int num = 0;
                            num = Integer.parseInt(btn[finalRow][finalCol].getText());
                            Integer[][] num2 = new Integer[size][size];
                            num2[finalRow][finalCol] = num;
                            Graph g1 = new Graph();
                            g1.initialize2(btn);
                            g1.multiply();
                            g1.ermittle();
                            g1.exzentrizitaet();
                            g1.radiusUndDurchmesser();
                            //addDistancMatrix2(g1.getDistanceMatix());
                            ArrayList<ArrayList<Integer>> adjListArray = g1.convert(g1.AdjacencyMatrix2);
                            g1.printArrayList(adjListArray);
                            System.out.print("Articulation: ");
                            g1.AP();
                            System.out.println("\nBirdge: ");
                            g1.bridge();
                            g1.printGraph();
                            ;

                        });
                        //AdjPanel.add(btn[row][col]);

                        AdjPanel.add(btn[row][col]);
                        //DistanceMatrix
                        String m1 = g.getDistanceMatix()[row][col].toString();
                        btn1[row][col] = new JButton(m1);
                        DistPanel.add(btn1[row][col]);
                        //WegMatrix
                        String m2 = g.getWegmatrix()[row][col].toString();
                        btn2[row][col] = new JButton(m2);
                        WegPanel.add(btn2[row][col]);
                    }
                }
            }
        }

        public void addDistMatrix() {
            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    if (row == col) {
                        //DistanceMatrix
                        String m1 = g.getDistanceMatix()[row][col].toString();
                        btn1[row][col] = new JButton(m1);
                        DistPanel.add(btn1[row][col]);
                        btn1[row][col].setBackground(Color.BLUE);
                    } else {
                        //DistanceMatrix
                        String m1 = g.getDistanceMatix()[row][col].toString();
                        btn1[row][col] = new JButton(m1);
                        DistPanel.add(btn1[row][col]);

                    }
                }
            }
        }

        public void addWegMatrix() {
            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    if (row == col) {
                        //WegMatrix
                        String m2 = g.getWegmatrix()[row][col].toString();
                        btn2[row][col] = new JButton(m2);
                        WegPanel.add(btn2[row][col]);
                        btn2[row][col].setBackground(Color.BLUE);
                    } else {
                        //WegMatrix
                        String m2 = g.getWegmatrix()[row][col].toString();
                        btn2[row][col] = new JButton(m2);
                        WegPanel.add(btn2[row][col]);
                    }
                }
            }
        }
    }
}