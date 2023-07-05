package xml;

class ToJson {
    private String xml = "";

    public ToJson(String xml) {
        this.xml = xml;
    }

    protected String convert() {
        return this.xml;
    }
}
