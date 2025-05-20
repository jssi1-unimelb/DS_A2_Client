// Jiachen Si 1085839
package Main.GUI;

import Main.DrawObjects.*;
import Main.DrawObjects.Rectangle;
import Main.WhiteboardClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

public class DrawingArea extends JPanel implements ToolListener, KeyListener, WhiteboardUpdateListener {

    public BufferedImage whiteboard;
    public boolean isOpen = true;
    private final ToolsArea tools;
    private int clickCount;
    private int toolClickLimit;
    private final ArrayList<Coord> points = new ArrayList<>();
    private String currentText;
    private FreeDraw freeDraw;
    private boolean inFreeDraw = false;
    private final WhiteboardClient client;

    public DrawingArea(int width, int height, ToolsArea tools, WhiteboardClient client) {
        this.freeDraw = null;
        this.client = client;
        tools.addListener(this);
        addKeyListener(this);
        this.currentText = "";
        this.whiteboard = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Whiteboard has white background
        Graphics g2D = whiteboard.createGraphics();
        g2D.setColor(Color.WHITE);
        g2D.fillRect(0, 0, width, height);
        g2D.dispose();

        this.tools = tools;
        this.clickCount = 0;
        this.toolClickLimit = 2; // Default tool is line
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.WHITE);
        this.setFocusable(true); // Must be true for key listener to work
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(!isOpen) {
                    client.updateConsole("Error: Whiteboard has been closed");
                    return;
                }
                if(!client.liveConnection) {
                    client.updateConsole("Error: You are not connected to a server");
                    return;
                }

                super.mouseClicked(e);
                requestFocusInWindow(); // Focus this component to receive keyboard input
                Coord point = new Coord(e.getX(), e.getY());
                points.add(point);
                clickCount++;
                if (clickCount == toolClickLimit) {
                    clickCount = 0;
                    drawShape();
                    if (!tools.tool.equals("draw") && !tools.tool.equals("erase") && !tools.tool.equals("text")) {
                        points.clear();
                    }
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(client.liveConnection && isOpen) {
                    if (inFreeDraw && (tools.tool.equals("draw") || tools.tool.equals("erase"))) {
                        points.clear();
                        client.sendWhiteboardUpdateRequest(freeDraw);
                        freeDraw = null; // Clear the obj
                        inFreeDraw = false;
                    }
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(client.liveConnection && isOpen) {
                    if (inFreeDraw && (tools.tool.equals("draw") || tools.tool.equals("erase"))) {
                        points.clear();
                        client.sendWhiteboardUpdateRequest(freeDraw);
                        freeDraw = null; // Clear the obj
                        inFreeDraw = false;
                    }
                }
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(client.liveConnection && isOpen) {
                    super.mouseDragged(e);
                    switch (tools.tool) {
                        case "draw":
                        case "erase":
                            points.add(new Coord(e.getX(), e.getY()));
                            drawShape();
                            repaint();
                            break;
                    }
                }
            }
        });
    }

    @Override
    public void closeWhiteboard() {
        updateWhiteboard(new BufferedImage(700, 600, BufferedImage.TYPE_INT_ARGB)); // Clear the whiteboard
        isOpen = false;
    }

    // Helper function that creates the shape to drawn
    private void drawShape() {
        String tool = tools.tool;
        switch (tool) {
            case "line":
                drawLine();
                break;
            case "triangle":
                drawTriangle();
                break;
            case "oval":
                drawOval();
                break;
            case "rectangle":
                drawRectangle();
                break;
            case "draw", "erase":
                drawCircleOnCursor();
                break;
            case "text":
                drawText();
                break;
        }
    }

    // Helper function to get coord of the top left point of the rectangle to be rendered
    // Required as "drawRect()" draws a filled rectangle when either width or height is negative
    private Coord getTopLeftCorner(Coord p1, Coord p2, int width, int height) {
        if (width < 0 && height < 0) { // Top left quadrant
            return p2;
        } else if (width < 0 && height >= 0) { // Bottom Left quadrant
            return new Coord(p2.x, p1.y);
        } else if (width >= 0 && height < 0) { // Top right quadrant
            return new Coord(p1.x, p2.y);
        }
        return p1; // Bottom right quadrant
    }

    // Anything that inherits from the "component" class has the "paint()" method
    // paint() is invoked automatically behind the scenes when we invoke a component such as
    // a JFrame. No need to explicitly call it.
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(whiteboard, 0, 0, null);
    }

    @Override
    public void onToolChange() {
        // Tool change, clear points
        this.clickCount = 0;
        points.clear();
        switch (tools.tool) {
            case "draw":
            case "erase":
                toolClickLimit = 1;
                break;
            case "text":
                currentText = "";
                toolClickLimit = 1;
                break;
            case "line":
            case "rectangle":
                toolClickLimit = 2;
                break;
            case "triangle":
            case "oval":
                toolClickLimit = 3;
                break;
        }
    }

    private void drawLine() {
        Coord start = points.getFirst();
        Coord end = points.get(1);
        Line line = new Line(start, end, tools.colour, tools.size);
        line.draw(whiteboard);
        client.sendWhiteboardUpdateRequest(line);
    }

    private void drawTriangle() {
        int[] xPoints = points.stream().mapToInt(Coord::getX).toArray();
        int[] yPoints = points.stream().mapToInt(Coord::getY).toArray();
        Triangle triangle = new Triangle(xPoints, yPoints, tools.colour, tools.size);
        triangle.draw(whiteboard);
        client.sendWhiteboardUpdateRequest(triangle);
    }

    private void drawOval() {
        Coord p1 = points.getFirst();
        Coord p2 = points.get(1);
        Coord p3 = points.get(2);
        int width = Math.abs(p1.x - p2.x) * 2;
        int height = Math.abs(p1.y - p3.y) * 2;
        Coord start = new Coord(p1.x - width / 2, p1.y - height / 2);
        Oval oval = new Oval(start, width, height, tools.colour, tools.size);
        oval.draw(whiteboard);
        client.sendWhiteboardUpdateRequest(oval);
    }

    private void drawRectangle() {
        Coord start = points.getFirst();
        Coord end = points.get(1);
        int width = end.x - start.x;
        int height = end.y - start.y;
        Coord point = getTopLeftCorner(start, end, width, height);
        Rectangle rect = new Rectangle(point, Math.abs(width), Math.abs(height), tools.colour, tools.size);
        rect.draw(whiteboard);
        client.sendWhiteboardUpdateRequest(rect);
    }

    private void drawCircleOnCursor() {
        if(freeDraw == null) {
            freeDraw = new FreeDraw();
            inFreeDraw = true;
        }

        Coord curr = points.getLast();
        Coord start = new Coord(curr.x - tools.size / 2, curr.y - tools.size / 2);
        Color colour = tools.tool.equals("erase") ? Color.WHITE : tools.colour;
        CircleOnCursor circle = new CircleOnCursor(start, colour, tools.size);
        circle.draw(whiteboard);
        freeDraw.addShape(circle);

        // Line interpolation
        if (points.size() > 1) {
            Coord prev = points.get(points.size() - 2);
            Line line = new Line(prev, curr, colour, tools.size);
            line.draw(whiteboard);
            freeDraw.addShape(line);
        }
    }

    private void drawText() {
        Coord start = points.getFirst();
        Graphics2D g2D = whiteboard.createGraphics();

        g2D.setColor(tools.colour);
        g2D.setFont(new Font("Dialog", Font.PLAIN, tools.size));
        g2D.setStroke(new BasicStroke(tools.size));
        g2D.drawString(currentText, start.x, start.y);
        g2D.dispose();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        ArrayList<Integer> invalidKeyCodes = new ArrayList<>();
        Collections.addAll(invalidKeyCodes, 8, 12, 16, 17, 18, 19, 20, 33, 34, 35, 36, 37, 38, 39, 40, 112, 113, 114, 115,
                116, 117, 118, 119, 120, 121, 122, 123, 127, 144, 155, 524, 525);

        if(tools.tool.equals("text")) {
            if(e.getKeyCode() == KeyEvent.VK_ENTER) {

                // Add text to history
                Text text = new Text(points.getFirst(), currentText, tools.colour, tools.size);
                text.draw(whiteboard);
                client.sendWhiteboardUpdateRequest(text);

                currentText = ""; // clear string
                points.clear();
            } else {
                if((!invalidKeyCodes.contains(e.getKeyCode()))) { // Valid key
//                    if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
//                        if(!currentText.isEmpty()) {
//                            currentText = currentText.substring(0, currentText.length() - 1);
//                        }
//                    } else { // Valid key
//                        currentText += e.getKeyChar();
//                    }
                    currentText += e.getKeyChar();
                    drawText();
                    repaint();
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void updateWhiteboard(BufferedImage whiteboard) {
        isOpen = true;
        this.whiteboard = whiteboard;
        repaint();
    }
}