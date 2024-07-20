package battlestatsmod;

public class DataCell {
    public String header;
    public String texturePath;
    public String text;
    public int width;
    public int height;

    public DataCell(String header, String texturePath, String text, int width, int height) {
        this.header = header;
        this.texturePath = texturePath;
        this.text = text.equals("0") ? "-" : text;
        this.width = width;
        this.height = height;
    }

}
