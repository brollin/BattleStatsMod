package battlestatsmod;

public class DataCell {
    public String header;
    public String text;
    public int width;
    public int height;

    public DataCell(String header, String text, int width, int height) {
        this.header = header;
        this.text = text;
        this.width = width;
        this.height = height;
    }
}
