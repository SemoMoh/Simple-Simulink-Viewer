import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.Scanner;

public class Blocks {
    private ArrayList<String> strings;
    private ArrayList<Block> blocks;

    public Blocks()
    {
        strings =new ArrayList<>();
        blocks =new ArrayList<>();

    }

    public void cutString(String system)
    {

        Scanner cursor = new Scanner(system);
        String line = cursor.nextLine();
        while (!(line.contains("</System>")))
        {
            if(line.contains("<Block"))
            {
                StringBuffer s = new StringBuffer();
                while(!line.contains("</Block>")) {
                    s.append(line).append("\n");
                    line = cursor.nextLine();
                }
                s.append("</Block>");
                strings.add(s.toString());
            }

            line = cursor.nextLine();
        }
    }

    public ArrayList<String> getStrings() {
        return strings;
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }
    public void extractBlock(String block)
    {
        Scanner cursor = new Scanner(block);
        Block b1 =new Block();
        String line = cursor.nextLine();
        // <Block BlockType="Scope" Name="Scope" SID="7">
        while (!line.contains("</Block>"))
        {

            if (line.contains("<Block"))
            {
                String type = line.substring(line.indexOf("BlockType=")+10,line.indexOf("Name")-2);
                String id = line.substring(line.indexOf("ID=")+4,line.indexOf(">")-1);
                b1.setId(Integer.parseInt(id));
                b1.setType(type);
            }
            //<P Name="Position">[1130, 209, 1160, 241]</P>
            if(line.contains("Position"))
            {
                String position = line.substring(line.indexOf(">[")+2,line.indexOf("]<"));
                String[] s = position.split(", ");
                b1.setLeft(Integer.parseInt(s[0]));
                b1.setTop(Integer.parseInt(s[1]));
                b1.setRight(Integer.parseInt(s[2]));
                b1.setBottom(Integer.parseInt(s[3]));

            }
            //<P Name="Ports">[3, 1]</P>
            //modifications by eslam:
            if(line.contains("    <P Name=\"Ports\">")){
                int startIndex = line.indexOf('[') + 1;
                int endIndex = line.indexOf(',', startIndex);
                if (endIndex == -1) {
                    endIndex = line.indexOf(']', startIndex);
                }
                String firstNumber = line.substring(startIndex, endIndex);
                b1.setPorts(Integer.parseInt(firstNumber));
            }

            line = cursor.nextLine();
        }
        //end modifications
        this.blocks.add(b1);
    }

    public void addBlocks(String system)
    {

        this.cutString(system);
        for (int counter = 0; counter<this.getStrings().size();counter++)
        {
            extractBlock(this.getStrings().get(counter));

            this.getBlocks().get(counter).setRectangle();
        }

    }
    public void drawBlock(Pane pane)
    {
        for (int counter = 0;counter<blocks.size();counter++)
        {
            pane.getChildren().add(blocks.get(counter).getRectangle());
            pane.getChildren().add(blocks.get(counter).getRectangleShadow());
            pane.getChildren().add(blocks.get(counter).getTextt());
            pane.getChildren().add(blocks.get(counter).getSymbolImage());
            System.out.println(blocks.get(counter).getName());
        }
    }
}
