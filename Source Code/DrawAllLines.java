import javafx.geometry.Point2D;

import javafx.scene.layout.Pane;

import java.util.ArrayList;

import java.util.Scanner;

public class DrawAllLines {

    //function that receives the System file and gets the lines from it then draws all the lines

    public static void drawAllLines(String fileString, Pane pane, ArrayList<Block> blocks) {

        Scanner scanner = new Scanner(fileString); // to read the string line by line

        String temp; // store each line temporarily

        while (scanner.hasNextLine()) {

            temp = scanner.nextLine();

            //for the needed part of the string

            if (temp.equals("  <Line>")) {

                int outID = 0;

                ArrayList<Integer> inIDs = new ArrayList<>();

                ArrayList<Integer> inPorts = new ArrayList<>();

                boolean branched = false;

                while (!temp.equals("  </Line>")) { // till the end of the line block of code

                    //get src ID

                    if (temp.contains("    <P Name=\"Src\">")) {

                        int startIndex = temp.indexOf('>') + 1;

                        int endIndex = temp.indexOf('#', startIndex);

                        String no = temp.substring(startIndex, endIndex);

                        outID = Integer.parseInt(no);

                    }

                    //get dst ID and the end port

                    if (temp.contains("    <P Name=\"Dst\">")) {

                        int startIndex = temp.indexOf('>') + 1;

                        int endIndex = temp.indexOf('#', startIndex);

                        String no = temp.substring(startIndex, endIndex);

                        inIDs.add(Integer.parseInt(no));

                        startIndex = temp.indexOf(':', endIndex) + 1;

                        endIndex = temp.indexOf('<', startIndex);

                        no = temp.substring(startIndex, endIndex);

                        inPorts.add(Integer.parseInt(no));

                    }

                    //check if branched line

                    if (temp.contains("    <Branch>")) {

                        branched = true;

                    }

                    temp = scanner.nextLine();

                }

                //determine the needed points

                Point2D startPoint = determineStartPoint(outID, blocks);

                ArrayList<Point2D> endPoints = determineEndPoints(inIDs, inPorts, blocks);

                //draw branched line

                if(branched){

                    Point2D branchPoint = null;

                    if (startPoint != null) {

                        branchPoint = calcBranchedPoint(startPoint, endPoints);

                    }

                    BranchedLineDraw(pane, branchPoint,startPoint,endPoints);

                }

                //draw Arrow line

                else {

                    ArrowLineDraw(pane, startPoint, endPoints);

                }

                //repeat for each Line code block

            }

        }

    }

    private static void ArrowLineDraw(Pane pane, Point2D startPoint, ArrayList<Point2D> endPoints) {

        ArrowLine a=new ArrowLine(startPoint, endPoints.get(0));

        a.draw(pane);

    }

    private static void BranchedLineDraw(Pane pane, Point2D branchPoint, Point2D startPoint, ArrayList<Point2D> endPoints) {

        BranchedLine b =new BranchedLine(branchPoint,startPoint,endPoints);

        b.draw(pane);

    }

    private static ArrayList<Point2D> determineEndPoints(ArrayList<Integer> inIDs, ArrayList<Integer> inPorts, ArrayList<Block> blocks) {

        int inPortsCount = 0;

        ArrayList<Point2D> endPoints = new ArrayList<>();

        for (int inId : inIDs) {

            int inPort = inPorts.get(inPortsCount++);

            for (Block block : blocks) {

                if (block.getId() == inId) {

                    endPoints.add(block.portPosition(inPort));

                }

            }

        }

        return endPoints;

    }

    private static Point2D determineStartPoint(int inId, ArrayList<Block> blocks) {

        for (Block block : blocks) {

            if (block.getId() == inId) {

                double x = block.getTopLeft().getX() + block.getWidth();

                double y = block.getTopLeft().getY() + block.getWidth() / 2;

                //fbrk

                if(block.getName().contains("Unit Delay")){

                    x = block.getTopLeft().getX();

                }

                //end fbrk

                return new Point2D(x, y);

            }

        }

        return null;

    }

    private static Point2D calcBranchedPoint(Point2D startPoint, ArrayList<Point2D> endPoints) {

        double y = startPoint.getY();

        double x = getMaxX(endPoints) -15;

        return new Point2D(x, y);

    }

    private static double getMaxX(ArrayList<Point2D> endPoints) {

        double maxX = Double.NEGATIVE_INFINITY;

        for (Point2D point : endPoints) {

            if (point.getX() > maxX) {

                maxX = point.getX();

            }

        }

        return maxX;

    }
