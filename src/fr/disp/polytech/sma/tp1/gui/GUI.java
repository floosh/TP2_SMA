/* 
 * $Id$
 * 
 * Copyright (C) 2004-2007 St&eacute;phane GALLAND.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * This program is free software; you can redistribute it and/or modify
 */
package fr.disp.polytech.sma.tp1.gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import javax.print.attribute.standard.JobMessageFromOperator;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;

import org.arakhne.tinyMAS.core.AgentIdentifier;
import org.arakhne.tinyMAS.core.Kernel;
import org.arakhne.tinyMAS.core.KernelAdapter;
import org.arakhne.tinyMAS.situatedEnvironment.body.AgentBody;

import fr.disp.polytech.sma.tp1.Simulation;
import fr.disp.polytech.sma.tp1.data.box.AABoundingBox;
import fr.disp.polytech.sma.tp1.sma.environment.AnimatBody;
import fr.disp.polytech.sma.tp1.sma.environment.AnimatPerception;
import fr.disp.polytech.sma.tp1.sma.environment.AnimatViewPerception;
import fr.disp.polytech.sma.tp1.sma.environment.EntityDescription;
import fr.disp.polytech.sma.tp1.sma.environment.WorldModel;
import fr.disp.polytech.sma.tp1.sma.environment.objet.EnvironmentObject;
import fr.disp.polytech.sma.tp1.sma.environment.objet.PerceptionType;
import fr.disp.polytech.sma.tp1.sma.environment.objet.StandardEntity;
import java.util.ConcurrentModificationException;

public class GUI extends JFrame {

    private static final long serialVersionUID = 5606976766146702008L;

    protected static final double SPOT_RADIUS = 20.;
    protected static final double DIRECTION_RADIUS = 60.;

    private static final boolean SHOW_ICON = true;

    private JLabel speed;
    private JLabel angle;
    private Vector2d target = new Vector2d();

    private final WeakReference<Kernel<?, ?, ?, ?>> kernel;
    protected final World world;

    // Constructor
    public GUI(final Simulation kernel) {
        setTitle("OA"); //$NON-NLS-1$

        this.kernel = new WeakReference<Kernel<?, ?, ?, ?>>(kernel);

        Container content = getContentPane();

        content.setLayout(new BorderLayout());

        this.world = new World();

        JButton closeBt = new JButton("Quit"); //$NON-NLS-1$
        closeBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getKernel().stop();
            }
        });
        content.add(BorderLayout.SOUTH, closeBt);

        this.world.setPreferredSize(new Dimension(700, 700));

        // Configuration du kernel pour la GUI
        kernel.addKernelListener(new KernelAdapter() {
            @Override
            public void kernelRefreshAllowed(Kernel<?, ?, ?, ?> aKernel) {
                if (aKernel == GUI.this.getKernel()) {
                    changeState();
                }
            }

            @Override
            public void kernelStarted(Kernel<?, ?, ?, ?> aKernel) {
                if (aKernel == GUI.this.getKernel()) {
                    changeState();
                }
            }
        });

        // ============ CENTER
        JScrollPane worldPanel = new JScrollPane(this.world);

        content.add(BorderLayout.CENTER, worldPanel);
        this.world.setPreferredSize(new Dimension(700, 700));

        // ============ EAST
        JPanel controlPanel = new JPanel(new GridLayout(3, 1));
        content.add(BorderLayout.EAST, controlPanel);

        // === Options
        JPanel optionsDisplayPanel = new JPanel();
        GridBagConstraints gbConst = new GridBagConstraints();
        gbConst.fill = GridBagConstraints.HORIZONTAL;

        GridBagLayout gridBagLayout = new GridBagLayout();
        // gridBagLayout.setConstraints(optionsDisplayPanel, gbConst);
        optionsDisplayPanel.setLayout(gridBagLayout);

        speed = new JLabel("speed :");
        angle = new JLabel("angle :");
        optionsDisplayPanel.setBorder(BorderFactory
                .createTitledBorder("info"));

        gbConst.weighty = 0.5;
        gbConst.gridx = 0;
        gbConst.gridy = 0;
        gridBagLayout.setConstraints(speed, gbConst);
        optionsDisplayPanel.add(speed);

        gbConst.weighty = 0.5;
        gbConst.gridx = 0;
        gbConst.gridy = 1;
        gridBagLayout.setConstraints(angle, gbConst);
        optionsDisplayPanel.add(angle);

        // Button Play
        JPanel playControlPanel = new JPanel();
        final JButton startPauseBtn = new JButton("Pause");
        startPauseBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                if (arg0.getSource() == startPauseBtn) {
                    if (startPauseBtn.getText().compareTo("Play") == 0) {
                        startPauseBtn.setText("Pause");
                    } else {
                        kernel.pause();
                        startPauseBtn.setText("Play");
                    }
                }
            }
        });

        // Button Restart
        final JButton restartStopBtn = new JButton("Stop");
        restartStopBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (restartStopBtn.getText().compareTo("Stop") == 0) {
                    restartStopBtn.setText("Restart");
                } else {
                    restartStopBtn.setText("Stop");
                }
            }
        });

        // Button Quit
        JButton quitBtn = new JButton("Quitter");
        quitBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                getKernel().stop();
            }
        });
        playControlPanel.add(startPauseBtn);
        playControlPanel.add(restartStopBtn);
        playControlPanel.add(quitBtn);

        controlPanel.add(optionsDisplayPanel);
        controlPanel.add(playControlPanel);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                Kernel<?, ?, ?, ?> bindKernel = GUI.this.getKernel();
                bindKernel.stop();
            }

            @Override
            public void windowClosing(WindowEvent e) {
                Kernel<?, ?, ?, ?> bindedKernel = GUI.this.getKernel();
                bindedKernel.stop();
            }
        });

        /*
		 * this.world.addMouseMotionListener(new MouseMotionListener() { public
		 * void mouseDragged(MouseEvent e) { if (GUI.this.target!=null)
		 * GUI.this.target.setPosition(e.getX(), e.getY()); } public void
		 * mouseMoved(MouseEvent e) { if (GUI.this.target!=null)
		 * GUI.this.target.setPosition(e.getX(), e.getY()); } });
		 * 
		 * this.world.addMouseListener(new MouseListener() { public void
		 * mouseClicked(MouseEvent e) {} public void mouseEntered(MouseEvent e)
		 * { GUI.this.target = new Target(e.getX(), e.getY());
		 * getEnvironment().setTarget(GUI.this.target); } public void
		 * mouseExited(MouseEvent e) { GUI.this.target = null;
		 * getEnvironment().setTarget(null); } public void
		 * mousePressed(MouseEvent e) {} public void mouseReleased(MouseEvent e)
		 * {} });
         */
        pack();
    }

    public JLabel getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed.setText(speed);

    }

    public JLabel getAngle() {
        return angle;
    }

    public void setAngle(String angle) {
        this.angle.setText(angle);
    }

    protected WorldModel getEnvironment() {
        return (WorldModel) getKernel().getEnvironment();
    }

    protected void changeState() {
        WorldModel environment = getEnvironment();
        if (environment != null) {
            this.world.setPositions(environment.getState());
        }
    }

    protected Kernel<?, ?, ?, ?> getKernel() {
        return this.kernel.get();
    }

    public Vector2d getTarget() {
        return this.world.target = target;
    }

    public void setTarget(Vector2d direction) {
        this.world.target = new Vector2d(direction);
    }

    private class World extends JPanel {

        public Vector2d target = new Vector2d();

        private static final long serialVersionUID = -3664274989335722916L;

        private Map<AgentIdentifier, EntityDescription> positions = null;

        private int scaleFactor = 1;

        private int s = 700 / 2;

        public World() {
            //
        }

        public void setPositions(
                Map<AgentIdentifier, EntityDescription> positions) {
            this.positions = positions;
            repaint();
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g2d = (Graphics2D) g;

            drawObject(g2d);

            drawAgents(g2d);

        }

        private void drawObject(Graphics2D g2d) {
            EntityDescription p;
            ArrayList<EnvironmentObject> grid = new ArrayList<EnvironmentObject>(getEnvironment().getObjects());

            for (EnvironmentObject environmentObject : grid) {

                try {
                    if (environmentObject instanceof StandardEntity) {
                        StandardEntity b = (StandardEntity) environmentObject;
                        g2d.setColor(Color.black);

                        int x = (int) (b.getPosX() * scaleFactor);
                        int y = (int) (b.getPosY() * scaleFactor);

                        ((Graphics2D) g2d).fill(new Ellipse2D.Double(s - x - (scaleFactor * 1) / 2, s - y - (scaleFactor * 1) / 2, 4, 4));

                    }
                } catch (ConcurrentModificationException e) {
                    System.err.println(e.getMessage());
                }
            }
        }

        private void drawAgents(Graphics2D g2d) {
            EntityDescription p;
            WorldModel grid = getEnvironment();

            try {
                for (AnimatBody body : grid.getAllAgentBodies()) {
                    switch(body.getType()) {
                        case SHEEP:
                            g2d.setColor(Color.BLUE);
                            break;
                        case WOLF:
                            g2d.setColor(Color.RED);
                            break;
                    }

                    //g2d.drawLine(x, y, x + dx, y + dy);

                    int x = (int) (body.getLocation().x * scaleFactor);
                    int y = (int) (body.getLocation().y * scaleFactor);

                    ((Graphics2D) g2d).fill(new Ellipse2D.Double(s - x - (scaleFactor * 1) / 2, s - y - (scaleFactor * 1) / 2, 4, 4));

                }
            } catch (ConcurrentModificationException e) {
                System.err.println(e.getMessage());
            }

        }
    }

}
