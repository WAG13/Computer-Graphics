package ui;

import algorithm.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;
    private static final Color BACKGROUND = Color.WHITE;
    private static final int RADIUS = 12;
    private static final int LINE_WIDTH = 4;
    private static final int VERTEX_LINE_WIDTH = 5;
    private static final int EDGE_LINE_WIDTH = 5;
    private static final Color VERTEX_STROKE = Color.WHITE;
    private static final Color VERTEX_FILL = Color.rgb(39, 174, 96);
    private static final int FONT_SIZE = 15;
    private static final Color EDGE_LINE_COLOR = Color.rgb(39, 174, 96);
    private static final Color CIRCLE_LINE_COLOR = Color.rgb(189, 195, 199, 0.5);

    @FXML
    AnchorPane mainPane;

    @FXML
    Button button;

    private List<Vector> points;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        points = new ArrayList<>();

        Canvas vertexCanvas = new Canvas(WIDTH, HEIGHT);
        Canvas edgesCanvas = new Canvas(WIDTH, HEIGHT);
        Canvas animationCanvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext vertexLayer = vertexCanvas.getGraphicsContext2D();
        GraphicsContext edgesLayer = edgesCanvas.getGraphicsContext2D();
        GraphicsContext animationLayer = animationCanvas.getGraphicsContext2D();

        clearLayer(animationCanvas);
        initializeSettings(vertexLayer, edgesLayer, animationLayer);

        vertexCanvas.setOnMouseClicked(event -> {
            double x = event.getX(), y = event.getY();
            Vector clickPoint = new Vector(x, y);

            points.add(clickPoint);

            drawFillVertex(vertexLayer, clickPoint, RADIUS);
            drawStrokeVertex(vertexLayer, clickPoint, RADIUS);
        });

        vertexCanvas.setOnMouseMoved(event -> {
            clearLayer(animationCanvas);
            animationLayer.fillText((int) event.getX() + "," + (int) event.getY(), WIDTH - 60, HEIGHT - 10);
        });

        button.setOnAction(event -> {
            clearLayer(edgesCanvas);

            List<Triangle> triangles = DelaunayTriangulation.calculate(points);

            for (Triangle triangle : triangles) {
                drawEdge(edgesLayer, triangle.getA(), triangle.getB());
                drawEdge(edgesLayer, triangle.getB(), triangle.getC());
                drawEdge(edgesLayer, triangle.getC(), triangle.getA());
                drawCircle(edgesLayer, Circle.fromTriangle(triangle));
            }
        });

        mainPane.getChildren().add(animationCanvas);
        mainPane.getChildren().add(edgesCanvas);
        mainPane.getChildren().add(vertexCanvas);
    }

    private void initializeSettings(GraphicsContext vertexLayer, GraphicsContext edgesLayer, GraphicsContext animationLayer) {
        edgesLayer.setFill(Color.BLACK);
        edgesLayer.setStroke(VERTEX_FILL);
        edgesLayer.setLineWidth(EDGE_LINE_WIDTH);
        edgesLayer.setFont(new Font(FONT_SIZE));
        edgesLayer.setLineCap(StrokeLineCap.ROUND);
        edgesLayer.setLineJoin(StrokeLineJoin.ROUND);
        animationLayer.setStroke(VERTEX_STROKE);
        animationLayer.setLineWidth(LINE_WIDTH);
        animationLayer.setLineCap(StrokeLineCap.ROUND);
        animationLayer.setLineJoin(StrokeLineJoin.ROUND);
        vertexLayer.setStroke(VERTEX_STROKE);
        vertexLayer.setFill(VERTEX_FILL);
        vertexLayer.setLineWidth(VERTEX_LINE_WIDTH);
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText(message);
        alert.setContentText("Please, try again");
        alert.showAndWait();
    }

    private void clearLayer(Canvas canvas) {
        clearLayer(canvas, BACKGROUND);
    }

    private void clearLayer(Canvas canvas, Paint fill) {
        GraphicsContext context = canvas.getGraphicsContext2D();
        Paint previousFill = context.getFill();
        context.setFill(fill);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        context.setFill(previousFill);
    }

    private void drawFillVertex(GraphicsContext context, Vector point, double radius) {
        context.fillOval(point.getX() - radius, point.getY() - radius, 2 * radius, 2 * radius);
    }

    private void drawStrokeVertex(GraphicsContext context, Vector point, double radius) {
        context.strokeOval(point.getX() - radius, point.getY() - radius, 2 * radius, 2 * radius);
    }

    private void drawEdge(GraphicsContext context, Vector from, Vector to) {
        context.setStroke(EDGE_LINE_COLOR);
        context.setLineWidth(EDGE_LINE_WIDTH);
        context.strokeLine(from.getX(), from.getY(), to.getX(), to.getY());
    }

    private void drawCircle(GraphicsContext context, Circle circle) {
        context.setStroke(CIRCLE_LINE_COLOR);
        context.setLineWidth(EDGE_LINE_WIDTH);
        double radius = circle.getRadius();
        double w = radius * 2;
        context.strokeOval(circle.getCenter().getX() - radius, circle.getCenter().getY() - radius, w, w);
    }
}
